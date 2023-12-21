package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.utility.Coord2D;

import java.awt.*;
import java.util.Set;
import java.util.stream.Collectors;

public class SelectionContents {
    private static final double IDENTITY_SCALE = 1d;

    private final GameImage content;
    private final Coord2D topLeft;
    private final Set<Coord2D> pixels;

    // transformations
    private final Coord2D translation;
    private final double scaleX, scaleY; // TODO
    private final boolean reflectionX, reflectionY; // TODO

    // results of transformations
    private final GameImage preview;
    private final Set<Coord2D> resultantPixels;

    private SelectionContents(
            final GameImage canvas, final Set<Coord2D> selection
    ) {
        pixels = selection.stream().filter(
                s -> s.x >= 0 && s.y >= 0 && s.x < canvas.getWidth() && s.y < canvas.getHeight()
        ).collect(Collectors.toSet());

        topLeft = SelectionBounds.topLeft(pixels);
        content = makeContentFromSelection(canvas, pixels);

        translation = new Coord2D();
        scaleX = IDENTITY_SCALE;
        scaleY = IDENTITY_SCALE;
        reflectionX = false;
        reflectionY = false;

        preview = generateContentCanvasOverlay(
                canvas.getWidth(), canvas.getHeight());
        resultantPixels = generateResultantPixels();
    }

    private Set<Coord2D> generateResultantPixels() {
        // TODO
        return pixels;
    }

    private GameImage generateContentCanvasOverlay(
            final int w, final int h
    ) {
        final Coord2D pos = topLeft.displace(translation);
        final GameImage preview = new GameImage(w, h);

        // TODO: reflection pre-processing

        preview.draw(content, pos.x, pos.y,
                (int)(content.getWidth() * scaleX),
                (int)(content.getHeight() * scaleY));

        return preview.submit();
    }

    private GameImage makeContentFromSelection(
            final GameImage canvas, final Set<Coord2D> pixels
    ) {
        final Coord2D tl = SelectionBounds.topLeft(pixels),
                br = SelectionBounds.bottomRight(pixels);

        final int w = br.x - tl.x, h = br.y - tl.y;
        final GameImage content = new GameImage(w, h);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (!pixels.contains(new Coord2D(tl.x + x, tl.y + y)))
                    continue;

                final Color c = ImageProcessing.colorAtPixel(canvas, tl.x + x, tl.y + y);
                content.dot(c, x, y);
            }
        }

        return content.submit();
    }

    public GameImage getPreview() {
        return preview;
    }

    public Set<Coord2D> getResultantPixels() {
        return resultantPixels;
    }
}
