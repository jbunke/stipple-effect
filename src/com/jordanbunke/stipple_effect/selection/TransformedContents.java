package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;

import java.util.Set;

public class TransformedContents {
    private static final double IDENTITY_SCALE = 1d;

    private final SelectionContents original;

    // transformations
    private final Coord2D translation;
    private final double scaleX, scaleY; // TODO
    private final boolean reflectionX, reflectionY; // TODO

    // results of transformations
    private final GameImage preview;
    private final Set<Coord2D> resultantPixels;

    public TransformedContents(
            final SelectionContents original, final int w, final int h
    ) {
        this.original = original;

        translation = new Coord2D();
        scaleX = IDENTITY_SCALE;
        scaleY = IDENTITY_SCALE;
        reflectionX = false;
        reflectionY = false;

        preview = generateContentCanvasOverlay(w, h);
        resultantPixels = generateResultantPixels();
    }

    private Set<Coord2D> generateResultantPixels() {
        // TODO
        return original.getPixels();
    }

    private GameImage generateContentCanvasOverlay(
            final int w, final int h
    ) {
        final Coord2D pos = original.getTopLeft().displace(translation);
        final GameImage preview = new GameImage(w, h);

        // TODO: reflection pre-processing

        final GameImage content = original.getContent();

        preview.draw(content, pos.x, pos.y,
                (int)(content.getWidth() * scaleX),
                (int)(content.getHeight() * scaleY));

        return preview.submit();
    }

    public GameImage getPreview() {
        return preview;
    }

    public Set<Coord2D> getResultantPixels() {
        return resultantPixels;
    }
}
