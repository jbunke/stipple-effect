package com.jordanbunke.stipple_effect.stip;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.ProjectInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.state.ProjectState;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ParserSerializer {
    private static final String
            TAG_SEPARATOR = ":", CONTENT_SEPARATOR = ",",
            NL = "\n", INDENT = "\t", FILE_STANDARD = "1.0",
            ENCLOSER_OPEN = "{", ENCLOSER_CLOSE = "}";

    private static final String

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

    public static SEContext load(final Path filepath) {
        final String contents = FileIO.readFile(filepath)
                .replaceAll("\n", "")
                .replaceAll("\r", "")
                .replaceAll("\t", "");
        final ProjectState state = deserializeProjectState(contents);

        return new SEContext(new ProjectInfo(filepath), state,
                state.getImageWidth(), state.getImageHeight());
    }

    private static ProjectState deserializeProjectState(final String contents) {
        // TODO - temp
        return ProjectState.makeFromNativeFile(1, 1, new ArrayList<>(
                List.of(new SELayer(1, 1))
        ), 1);

        // return new ProjectState(w, h, layers, frameCount);
    }

    public static void save(final SEContext c, final Path filepath) {
        final String serialized = serializeProjectState(c.getState());
        FileIO.writeFile(filepath, serialized);
    }

    private static String serializeProjectState(final ProjectState state) {
        final StringBuilder sb = new StringBuilder();

        // metadata: save standard
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
        sb.append(INDENT.repeat(indentLevel));
    }
}
