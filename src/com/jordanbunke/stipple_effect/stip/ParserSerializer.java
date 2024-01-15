package com.jordanbunke.stipple_effect.stip;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.stipple_effect.layer.OnionSkinMode;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.ProjectInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.ColorTextBox;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ParserSerializer {
    private static final char NL = '\n', INDENT = '\t',
            ENCLOSER_OPEN = '{', ENCLOSER_CLOSE = '}';

    private static final String CONTENT_SEPARATOR = ",", TAG_SEPARATOR = ":", FILE_STANDARD = "1.0";

    private static final int NOT_FOUND = -1;

    // tags
    private static final String

            INVALID_TAG = "invalid",
            FILE_STANDARD_TAG = "file_standard",
            LAYERS_TAG = "layers",
            LAYER_TAG = "layer",
            LAYER_NAME_TAG = "layer_name",
            LAYER_LINKED_STATUS_TAG = "is_linked",
            LAYER_ENABLED_STATUS_TAG = "is_enabled",
            LAYER_ONION_SKIN_TAG = "onion_skin",
            LAYER_OPACITY_TAG = "opacity",
            FRAME_COUNT_TAG = "frame_count",
            FRAMES_TAG = "frames",
            FRAME_TAG = "frame",
            COLOR_TAG = "cols",
            DIMENSION_TAG = "dims";

    public static SEContext load(final String file, final Path filepath) {
        final String contents = file
                .replaceAll("\n", "")
                .replaceAll("\r", "")
                .replaceAll("\t", "");
        final ProjectState state = deserializeProjectState(contents);

        return new SEContext(new ProjectInfo(filepath), state,
                state.getImageWidth(), state.getImageHeight());
    }

    private static ProjectState deserializeProjectState(final String contents) {
        final SerialBlock[] stateBlocks = deserializeBlocksAtDepthLevel(contents);

        final List<SELayer> layers = new ArrayList<>();
        int frameCount = 1, w = 1, h = 1;

        for (SerialBlock block : stateBlocks) {
            switch (block.tag()) {
                case DIMENSION_TAG -> {
                    final String[] vals = block.value().split(CONTENT_SEPARATOR);

                    if (vals.length == 2) {
                        w = Integer.parseInt(vals[0]);
                        h = Integer.parseInt(vals[1]);
                    }
                }
                case FRAME_COUNT_TAG -> frameCount = Integer.parseInt(block.value());
                case LAYERS_TAG -> {
                    final SerialBlock[] layerBlocks = deserializeBlocksAtDepthLevel(block.value());

                    for (SerialBlock layerBlock : layerBlocks)
                        if (layerBlock.tag().equals(LAYER_TAG))
                            layers.add(deserializeLayer(layerBlock.value()));
                }
            }
        }

        return ProjectState.makeFromNativeFile(w, h, layers, frameCount);
    }

    private static SELayer deserializeLayer(final String contents) {
        final SerialBlock[] blocks = deserializeBlocksAtDepthLevel(contents);

        final List<GameImage> frames = new ArrayList<>();
        double opacity = 1.0;
        boolean enabled = true, framesLinked = false;
        OnionSkinMode osm = OnionSkinMode.NONE;
        String name = "";

        for (SerialBlock block : blocks) {
            switch (block.tag()) {
                case LAYER_NAME_TAG -> name = block.value();
                case LAYER_ENABLED_STATUS_TAG ->
                        enabled = Boolean.parseBoolean(block.value());
                case LAYER_LINKED_STATUS_TAG ->
                        framesLinked = Boolean.parseBoolean(block.value());
                case LAYER_ONION_SKIN_TAG ->
                        osm = OnionSkinMode.valueOf(block.value());
                case LAYER_OPACITY_TAG ->
                        opacity = Double.parseDouble(block.value());
                case FRAMES_TAG -> {
                    final SerialBlock[] frameBlocks = deserializeBlocksAtDepthLevel(block.value());

                    for (SerialBlock frameBlock : frameBlocks)
                        if (frameBlock.tag().equals(FRAME_TAG))
                            frames.add(deserializeFrame(frameBlock.value()));
                }
            }
        }

        return new SELayer(frames, frames.isEmpty()
                ? GameImage.dummy() : frames.get(0),
                opacity, enabled, framesLinked, osm, name);
    }

    private static GameImage deserializeFrame(final String contents) {
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

    private static Color deserializeColor(final String contents) {
        final int LENGTH_OF_SECTION = 2, R = 0, G = 2, B = 4, A = 6;

        final int r = ColorTextBox.hexToInt(contents.substring(
                R, R + LENGTH_OF_SECTION)),
                g = ColorTextBox.hexToInt(contents.substring(
                        G, G + LENGTH_OF_SECTION)),
                b = ColorTextBox.hexToInt(contents.substring(
                        B, B + LENGTH_OF_SECTION)),
                a = ColorTextBox.hexToInt(contents.substring(
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
        openWithTag(sb, FILE_STANDARD_TAG).append(FILE_STANDARD)
                .append(ENCLOSER_CLOSE).append(NL);

        final int w = state.getImageWidth(), h = state.getImageHeight(),
                frameCount = state.getFrameCount();

        // dims definition
        openWithTag(sb, DIMENSION_TAG).append(w).append(CONTENT_SEPARATOR)
                .append(h).append(ENCLOSER_CLOSE).append(NL);

        // frame count definition
        openWithTag(sb, FRAME_COUNT_TAG).append(frameCount)
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

        // name definition
        indent(sb, indentLevel + 1);
        openWithTag(sb, LAYER_NAME_TAG).append(layer.getName())
                .append(ENCLOSER_CLOSE).append(NL);

        // is enabled definition
        indent(sb, indentLevel + 1);
        openWithTag(sb, LAYER_ENABLED_STATUS_TAG).append(layer.isEnabled())
                .append(ENCLOSER_CLOSE).append(NL);

        // is linked definition
        indent(sb, indentLevel + 1);
        openWithTag(sb, LAYER_LINKED_STATUS_TAG).append(layer.areFramesLinked())
                .append(ENCLOSER_CLOSE).append(NL);

        // onion skin definition
        indent(sb, indentLevel + 1);
        openWithTag(sb, LAYER_ONION_SKIN_TAG).append(layer.getOnionSkinMode())
                .append(ENCLOSER_CLOSE).append(NL);

        // opacity definition
        indent(sb, indentLevel + 1);
        openWithTag(sb, LAYER_OPACITY_TAG).append(layer.getOpacity())
                .append(ENCLOSER_CLOSE).append(NL);

        // frames tag opener
        indent(sb, indentLevel + 1);
        openWithTag(sb, FRAMES_TAG).append(NL);

        // frames
        for (int i = 0; i < frameCount; i++)
            sb.append(serializeFrame(layer.getFrame(i), i + 1 < frameCount));

        // frames tag closer
        indent(sb, indentLevel + 1);
        sb.append(ENCLOSER_CLOSE).append(NL);

        // layer tag closer
        indent(sb, indentLevel);
        sb.append(ENCLOSER_CLOSE).append(notLast
                ? CONTENT_SEPARATOR : "").append(NL);

        return sb.toString();
    }

    private static String serializeFrame(
            final GameImage frame, final boolean notLast
    ) {
        final StringBuilder sb = new StringBuilder();
        final int indentLevel = 3;

        // frame tag opener
        indent(sb, indentLevel);
        openWithTag(sb, FRAME_TAG).append(NL);

        // dims
        final int w = frame.getWidth(), h = frame.getHeight();

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
                sb.append(serializeColor(ImageProcessing
                        .colorAtPixel(frame, x, y)));

                if (x + 1 < w || y + 1 < h)
                    sb.append(CONTENT_SEPARATOR);
            }

            sb.append(NL);
        }

        // color closer
        indent(sb, indentLevel + 1);
        sb.append(ENCLOSER_CLOSE).append(NL);

        // frame tag closer
        indent(sb, indentLevel);
        sb.append(ENCLOSER_CLOSE).append(notLast
                ? CONTENT_SEPARATOR : "").append(NL);

        return sb.toString();
    }

    private static String serializeColor(final Color c) {
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
