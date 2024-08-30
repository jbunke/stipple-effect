package com.jordanbunke.stipple_effect.stip;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.utility.math.Pair;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.OnionSkin;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.ColorTextbox;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserSerializer {
    private static final char NL = '\n', INDENT = '\t',
            ENCLOSER_OPEN = '{', ENCLOSER_CLOSE = '}';

    private static final String CONTENT_SEPARATOR = ",", TAG_SEPARATOR = ":",
            TRANSPARENT = "t";

    private static final int NOT_FOUND = -1;

    private static final double FS_INITIAL = 1.0,
            FS_LINKED_OPTIMIZATION_THRESHOLD = 1.1,
            FS_NEW_ONION_SKIN_THRESHOLD = 1.3;

    // tags
    private static final String

            INVALID_TAG = "invalid",
            FILE_STANDARD_TAG = "file_standard",
            PALETTE_NAME_TAG = "palette_name",
            LAYERS_TAG = "layers",
            LAYER_TAG = "layer",
            LAYER_NAME_TAG = "layer_name",
            LAYER_LINKED_STATUS_TAG = "is_linked",
            LAYER_ENABLED_STATUS_TAG = "is_enabled",
            LAYER_ONION_SKIN_TAG = "onion_skin",
            LAYER_OPACITY_TAG = "opacity",
            FRAME_COUNT_TAG = "frame_count",
            FRAME_DURATIONS_TAG = "frame_durations",
            FRAMES_TAG = "frames",
            LINKED_LAYER_TAG = "linked_layer",
            FRAME_TAG = "frame",
            COLOR_TAG = "cols",
            DIMENSION_TAG = "dims",
            SKIN_TYPE_TAG = "skin_type",
            HUE_BACK_TAG = "hue_back",
            HUE_FORWARD_TAG = "hue_forward",
            FADE_FACTOR_TAG = "fade_factor",
            LOOK_BACK_TAG = "look_back",
            LOOK_FORWARD_TAG = "look_forward",
            UNDER_TAG = "under";

    public static Palette loadPalette(final String file) {
        final String contents = file
                .replaceAll("\n", "")
                .replaceAll("\r", "")
                .replaceAll("\t", "");

        return deserializePalette(contents);
    }

    private static Palette deserializePalette(final String contents) {
        final SerialBlock[] paletteBlocks = deserializeBlocksAtDepthLevel(contents);

        Color[] colors = new Color[] {};
        String name = "";

        for (SerialBlock block : paletteBlocks) {
            switch (block.tag()) {
                case PALETTE_NAME_TAG -> name = block.value();
                case COLOR_TAG -> {
                    final String[] colorCodes = block.value()
                            .split(CONTENT_SEPARATOR);

                    colors = block.value().contains(CONTENT_SEPARATOR)
                            ? Arrays.stream(colorCodes)
                            .map(ParserSerializer::deserializeColor)
                            .toArray(Color[]::new)
                            : new Color[] {};
                }
            }
        }

        return new Palette(name, colors);
    }

    public static void savePalette(final Palette palette, final Path filepath) {
        final String serialized = serializePalette(palette);
        FileIO.writeFile(filepath, serialized);
        StatusUpdates.savedPalette(filepath);
    }

    private static String serializePalette(final Palette palette) {
        final StringBuilder sb = new StringBuilder();

        // metadata: file standard
        openWithTag(sb, FILE_STANDARD_TAG).append(StippleEffect.PALETTE_STANDARD)
                .append(ENCLOSER_CLOSE).append(NL);

        // palette name definition
        openWithTag(sb, PALETTE_NAME_TAG).append(palette.getName())
                .append(ENCLOSER_CLOSE).append(NL);

        // colors tag opener - inline so no NL
        openWithTag(sb, COLOR_TAG);

        // colors
        final Color[] colors = palette.getColors();

        for (int i = 0; i < colors.length; i++) {
            sb.append(serializeColor(colors[i], true));

            if (i + 1 < colors.length)
                sb.append(CONTENT_SEPARATOR);
        }

        // colors tag closer
        sb.append(ENCLOSER_CLOSE).append(NL);

        return sb.toString();
    }

    public static SEContext load(final String file, final Path filepath) {
        final String contents = file
                .replaceAll("\n", "")
                .replaceAll("\r", "")
                .replaceAll("\t", "");
        final ProjectState state = deserializeProjectState(contents);

        return new SEContext(filepath, state, state.getImageWidth(),
                state.getImageHeight());
    }

    private static ProjectState deserializeProjectState(final String contents) {
        final SerialBlock[] stateBlocks = deserializeBlocksAtDepthLevel(contents);

        final List<SELayer> layers = new ArrayList<>();
        int frameCount = 1, w = 1, h = 1;
        List<Double> frameDurations = ProjectState.defaultFrameDurations(frameCount);
        double fileStandard = FS_INITIAL;

        for (SerialBlock block : stateBlocks) {
            switch (block.tag()) {
                case DIMENSION_TAG -> {
                    final String[] vals = block.value().split(CONTENT_SEPARATOR);

                    if (vals.length == 2) {
                        w = Integer.parseInt(vals[0]);
                        h = Integer.parseInt(vals[1]);
                    }
                }
                case FILE_STANDARD_TAG ->
                        fileStandard = Double.parseDouble(block.value());
                case FRAME_COUNT_TAG ->
                        frameCount = Integer.parseInt(block.value());
                case FRAME_DURATIONS_TAG -> {
                    final String[] vals = block.value().split(CONTENT_SEPARATOR);

                    frameDurations = new ArrayList<>();

                    for (String val : vals)
                        frameDurations.add(Double.parseDouble(val));
                }
                case LAYERS_TAG -> {
                    final SerialBlock[] layerBlocks = deserializeBlocksAtDepthLevel(block.value());

                    for (SerialBlock layerBlock : layerBlocks)
                        if (layerBlock.tag().equals(LAYER_TAG))
                            layers.add(deserializeLayer(layerBlock.value(),
                                    fileStandard, frameCount));
                }
            }
        }

        return ProjectState.makeFromNativeFile(w, h, layers, frameCount, frameDurations);
    }

    private static SELayer deserializeLayer(
            final String contents, final double fileStandard,
            final int frameCount
    ) {
        final SerialBlock[] blocks = deserializeBlocksAtDepthLevel(contents);

        final List<GameImage> frames = new ArrayList<>();
        GameImage linked = GameImage.dummy();
        double opacity = 1.0;
        boolean enabled = true, framesLinked = false;
        OnionSkin os = OnionSkin.trivial();
        String name = "";

        for (SerialBlock block : blocks) {
            switch (block.tag()) {
                case LAYER_NAME_TAG -> name = block.value();
                case LAYER_ENABLED_STATUS_TAG ->
                        enabled = Boolean.parseBoolean(block.value());
                case LAYER_LINKED_STATUS_TAG ->
                        framesLinked = Boolean.parseBoolean(block.value());
                case LAYER_ONION_SKIN_TAG ->
                        os = deserializeOnionSkin(block.value(), fileStandard);
                case LAYER_OPACITY_TAG ->
                        opacity = Double.parseDouble(block.value());
                case LINKED_LAYER_TAG ->
                        linked = deserializeImage(block.value());
                case FRAMES_TAG -> {
                    final SerialBlock[] frameBlocks = deserializeBlocksAtDepthLevel(block.value());

                    for (SerialBlock frameBlock : frameBlocks)
                        if (frameBlock.tag().equals(FRAME_TAG))
                            frames.add(deserializeImage(frameBlock.value()));
                }
            }
        }

        final boolean loadFromLinked = framesLinked &&
                fileStandard >= FS_LINKED_OPTIMIZATION_THRESHOLD && frameCount > 0;

        if (loadFromLinked) {
            frames.clear();

            for (int i = 0; i < frameCount; i++)
                frames.add(linked);
        } else
            linked = frames.isEmpty() ? GameImage.dummy() : frames.get(0);

        return new SELayer(frames, linked, opacity, enabled,
                framesLinked, false, os, name);
    }

    private static OnionSkin deserializeOnionSkin(
            final String contents, final double fileStandard
    ) {
        if (fileStandard < FS_NEW_ONION_SKIN_THRESHOLD || contents.isEmpty())
            return OnionSkin.trivial();

        OnionSkin.reset();
        final SerialBlock[] blocks = deserializeBlocksAtDepthLevel(contents);

        for (SerialBlock block : blocks) {
            switch (block.tag()) {
                case SKIN_TYPE_TAG ->
                        OnionSkin.setDSkinType(OnionSkin.SkinType.valueOf(block.value()));
                case HUE_BACK_TAG ->
                        OnionSkin.setDHueBack(Double.parseDouble(block.value()));
                case HUE_FORWARD_TAG ->
                        OnionSkin.setDHueForward(Double.parseDouble(block.value()));
                case FADE_FACTOR_TAG ->
                        OnionSkin.setDFadeFactor(Double.parseDouble(block.value()));
                case LOOK_BACK_TAG ->
                        OnionSkin.setDLookBack(Integer.parseInt(block.value()));
                case LOOK_FORWARD_TAG ->
                        OnionSkin.setDLookForward(Integer.parseInt(block.value()));
                case UNDER_TAG ->
                        OnionSkin.setDUnder(Boolean.parseBoolean(block.value()));
            }
        }

        return OnionSkin.load();
    }

    private static GameImage deserializeImage(final String contents) {
        final int DIM_INDEX = 0, COL_INDEX = 1;

        final SerialBlock[] blocks = deserializeBlocksAtDepthLevel(contents);

        if (!(blocks.length == 2 &&
                blocks[DIM_INDEX].tag().equals(DIMENSION_TAG) &&
                blocks[COL_INDEX].tag().equals(COLOR_TAG)))
            return GameImage.dummy();

        // dims
        final String[] dims = blocks[DIM_INDEX].value().split(CONTENT_SEPARATOR);

        if (dims.length != 2)
            return GameImage.dummy();

        final int w = Integer.parseInt(dims[0]), h = Integer.parseInt(dims[1]);
        final GameImage frame = new GameImage(w, h);

        // colors
        final String[] pixels = blocks[COL_INDEX].value().split(CONTENT_SEPARATOR);

        if (pixels.length != w * h)
            return frame;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                final String pixel = pixels[(y * w) + x];
                final Color c = deserializeColor(pixel);

                frame.dot(c, x, y);
            }
        }

        return frame.submit();
    }

    public static Color deserializeColor(final String contents) {
        if (contents.equals(TRANSPARENT))
            return new Color(0, 0, 0, 0);

        final int LENGTH_OF_SECTION = 2, R = 0, G = 2, B = 4, A = 6;

        final int r = ColorTextbox.hexToInt(contents.substring(
                R, R + LENGTH_OF_SECTION)),
                g = ColorTextbox.hexToInt(contents.substring(
                        G, G + LENGTH_OF_SECTION)),
                b = ColorTextbox.hexToInt(contents.substring(
                        B, B + LENGTH_OF_SECTION)),
                a = ColorTextbox.hexToInt(contents.substring(
                        A, A + LENGTH_OF_SECTION));

        return new Color(r, g, b, a);
    }

    private static SerialBlock[] deserializeBlocksAtDepthLevel(final String contents) {
        final List<SerialBlock> blocks = new ArrayList<>();
        int processed = 0;

        while (processed < contents.length()) {
            while (contents.charAt(processed) != ENCLOSER_OPEN)
                processed++;

            final SerialBlock nextBlock = deserializeNextBlock(
                    contents, processed);

            if (!nextBlock.tag().equals(INVALID_TAG))
                blocks.add(nextBlock);

            processed += nextBlock.blockLength();
        }

        return blocks.toArray(SerialBlock[]::new);
    }

    private static SerialBlock deserializeNextBlock(final String contents, final int openIndex) {
        final int pastClosedIndex = indexPastClosed(contents, openIndex);
        final String blockString = contents.substring(openIndex, pastClosedIndex);

        return crackBlock(blockString);
    }

    private static SerialBlock crackBlock(final String blockString) {
        final String interior = blockString.substring(1, blockString.length() - 1);
        final int sepIndex = interior.indexOf(TAG_SEPARATOR);

        if (sepIndex == NOT_FOUND)
            return new SerialBlock(INVALID_TAG, "", blockString.length());

        final String tag = interior.substring(0, sepIndex);
        final String value = interior.substring(sepIndex +
                TAG_SEPARATOR.length());

        return new SerialBlock(tag, value, blockString.length());
    }

    private static int indexPastClosed(final String contents, final int openIndex) {
        int processed = openIndex, level = 0;

        while (processed < contents.length()) {
            if (contents.charAt(processed) == ENCLOSER_OPEN)
                level++;
            else if (contents.charAt(processed) == ENCLOSER_CLOSE) {
                level--;

                if (level == 0)
                    return processed + 1;
                else if (level < 0)
                    return NOT_FOUND;
            }

            processed++;
        }

        return NOT_FOUND;
    }

    public static void save(final SEContext c, final Path filepath) {
        final String serialized = serializeProjectState(c.getState());
        FileIO.writeFile(filepath, serialized);
    }

    private static String serializeProjectState(final ProjectState state) {
        final StringBuilder sb = new StringBuilder();

        // metadata: file standard
        openWithTag(sb, FILE_STANDARD_TAG).append(StippleEffect.NATIVE_STANDARD)
                .append(ENCLOSER_CLOSE).append(NL);

        final int w = state.getImageWidth(), h = state.getImageHeight(),
                frameCount = state.getFrameCount();
        final List<Double> frameDurations = state.getFrameDurations();
        final String durationsText = frameDurations.size() == 1
                ? String.valueOf(frameDurations.get(0))
                : frameDurations.stream().map(String::valueOf)
                    .reduce((a, b) -> a + CONTENT_SEPARATOR + b)
                    .orElse("1.0");

        // dims definition
        openWithTag(sb, DIMENSION_TAG).append(w).append(CONTENT_SEPARATOR)
                .append(h).append(ENCLOSER_CLOSE).append(NL);

        // frame count definition
        openWithTag(sb, FRAME_COUNT_TAG).append(frameCount)
                .append(ENCLOSER_CLOSE).append(NL);

        // frame durations definition
        openWithTag(sb, FRAME_DURATIONS_TAG).append(durationsText)
                .append(ENCLOSER_CLOSE).append(NL);

        // layers tag opener
        openWithTag(sb, LAYERS_TAG).append(NL);

        // layers
        final List<SELayer> layers = state.getLayers();

        for (int i = 0; i < layers.size(); i++)
            sb.append(serializeLayer(layers.get(i),
                    i + 1 < layers.size(), frameCount));

        // layers tag closer
        sb.append(ENCLOSER_CLOSE).append(NL);

        return sb.toString();
    }

    private static String serializeLayer(
            final SELayer layer, final boolean notLast,
            final int frameCount
    ) {
        final StringBuilder sb = new StringBuilder();
        final int indentLevel = 1;

        // layer tag opener
        indent(sb, indentLevel);
        openWithTag(sb, LAYER_TAG).append(NL);

        serializeSimpleAttributes(sb, indentLevel, buildAttributes(
                new Pair<>(LAYER_NAME_TAG, layer.getName()),
                new Pair<>(LAYER_ENABLED_STATUS_TAG, layer.isEnabled()),
                new Pair<>(LAYER_LINKED_STATUS_TAG, layer.areCelsLinked()),
                new Pair<>(LAYER_OPACITY_TAG, layer.getOpacity())));

        // onion skin definition
        sb.append(serializeOnionSkin(layer.getOnionSkin()));

        if (layer.areCelsLinked())
            sb.append(serializeImage(layer.getCel(0), false, true));
        else {
            // frames tag opener
            indent(sb, indentLevel + 1);
            openWithTag(sb, FRAMES_TAG).append(NL);

            // frames
            for (int i = 0; i < frameCount; i++)
                sb.append(serializeImage(layer.getCel(i),
                        i + 1 < frameCount, false));

            // frames tag closer
            indent(sb, indentLevel + 1);
            sb.append(ENCLOSER_CLOSE).append(NL);
        }

        // layer tag closer
        indent(sb, indentLevel);
        sb.append(ENCLOSER_CLOSE).append(notLast
                ? CONTENT_SEPARATOR : "").append(NL);

        return sb.toString();
    }

    private static String serializeOnionSkin(final OnionSkin onionSkin) {
        final StringBuilder sb = new StringBuilder();
        final int indentLevel = 2;

        // onion skin tag opener
        indent(sb, indentLevel);
        openWithTag(sb, LAYER_ONION_SKIN_TAG);

        // leave attribute blank if trivial
        if (!onionSkin.equals(OnionSkin.trivial())) {
            sb.append(NL);

            serializeSimpleAttributes(sb, indentLevel, buildAttributes(
                    new Pair<>(SKIN_TYPE_TAG, onionSkin.skinType),
                    new Pair<>(HUE_BACK_TAG, onionSkin.hueBack),
                    new Pair<>(HUE_FORWARD_TAG, onionSkin.hueForward),
                    new Pair<>(FADE_FACTOR_TAG, onionSkin.fadeFactor),
                    new Pair<>(LOOK_BACK_TAG, onionSkin.lookBack),
                    new Pair<>(LOOK_FORWARD_TAG, onionSkin.lookForward),
                    new Pair<>(UNDER_TAG, onionSkin.under)));

            indent(sb, indentLevel);
        }

        // onion skin tag closer
        sb.append(ENCLOSER_CLOSE).append(NL);

        return sb.toString();
    }

    @SafeVarargs
    private static Pair<String, Object>[] buildAttributes(
            final Pair<String, Object>... attributes
    ) {
        return attributes;
    }

    private static void serializeSimpleAttributes(
            final StringBuilder sb, final int indentLevel,
            final Pair<String, Object>[] tagValuePairs
    ) {
        for (Pair<String, Object> tvPair : tagValuePairs) {
            indent(sb, indentLevel + 1);
            openWithTag(sb, tvPair.a()).append(tvPair.b())
                    .append(ENCLOSER_CLOSE).append(NL);
        }
    }

    private static String serializeImage(
            final GameImage image, final boolean notLast, final boolean linked
    ) {
        final StringBuilder sb = new StringBuilder();
        final int indentLevel = 2 + (linked ? 0 : 1);

        // image type tag opener
        indent(sb, indentLevel);
        openWithTag(sb, linked ? LINKED_LAYER_TAG : FRAME_TAG).append(NL);

        // dims
        final int w = image.getWidth(), h = image.getHeight();

        // dims definition
        indent(sb, indentLevel + 1);
        openWithTag(sb, DIMENSION_TAG).append(w).append(CONTENT_SEPARATOR)
                .append(h).append(ENCLOSER_CLOSE).append(NL);

        // color tag opener
        indent(sb, indentLevel + 1);
        openWithTag(sb, COLOR_TAG).append(NL);

        for (int y = 0; y < h; y++) {
            indent(sb, indentLevel + 2);

            for (int x = 0; x < w; x++) {
                sb.append(serializeColor(image.getColorAt(x, y), false));

                if (x + 1 < w || y + 1 < h)
                    sb.append(CONTENT_SEPARATOR);
            }

            sb.append(NL);
        }

        // color closer
        indent(sb, indentLevel + 1);
        sb.append(ENCLOSER_CLOSE).append(NL);

        // image type tag closer
        indent(sb, indentLevel);
        sb.append(ENCLOSER_CLOSE).append(notLast
                ? CONTENT_SEPARATOR : "").append(NL);

        return sb.toString();
    }

    public static String serializeColor(
            final Color c, final boolean preserveRGBForTransparent
    ) {
        if (c.getAlpha() == 0 && !preserveRGBForTransparent)
            return TRANSPARENT;

        final String r = Integer.toHexString(c.getRed()),
                g = Integer.toHexString(c.getGreen()),
                b = Integer.toHexString(c.getBlue()),
                a = Integer.toHexString(c.getAlpha());

        return (r.length() == 1 ? ("0") + r : r) +
                (g.length() == 1 ? ("0") + g : g) +
                (b.length() == 1 ? ("0") + b : b) +
                (a.length() == 1 ? ("0") + a : a);
    }

    private static StringBuilder openWithTag(
            final StringBuilder sb, final String tag
    ) {
        sb.append(ENCLOSER_OPEN).append(tag).append(TAG_SEPARATOR);
        return sb;
    }

    private static void indent(
            final StringBuilder sb, final int indentLevel
    ) {
        sb.append(String.valueOf(INDENT).repeat(indentLevel));
    }
}
