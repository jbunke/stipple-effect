package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.utility.Coord2D;

import java.awt.*;
import java.util.Set;
import java.util.stream.Collectors;

public class SelectionContents {
    private final GameImage content;
    private final Coord2D topLeft;
    private final Set<Coord2D> pixels;

    public SelectionContents(
            final GameImage canvas, final Set<Coord2D> selection
    ) {
        pixels = selection.stream().filter(s -> s.x >= 0 && s.y >= 0 &&
                s.x < canvas.getWidth() &&
                s.y < canvas.getHeight()).collect(Collectors.toSet());

        topLeft = SelectionUtils.topLeft(pixels);
        content = makeContentFromSelection(canvas, pixels);
    }

    private SelectionContents(
            final GameImage content, final Coord2D topLeft,
            final Set<Coord2D> pixels
    ) {
        this.content = content;
        this.topLeft = topLeft;
        this.pixels = pixels;
    }
    private GameImage makeContentFromSelection(
            final GameImage canvas, final Set<Coord2D> pixels
    ) {
        final Coord2D tl = SelectionUtils.topLeft(pixels),
                br = SelectionUtils.bottomRight(pixels);

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

    public SelectionContents returnDisplaced(final Coord2D displacement) {
        final Set<Coord2D> displacedPixels = pixels.stream().map(px ->
                px.displace(displacement)).collect(Collectors.toSet());

        return new SelectionContents(new GameImage(content),
                topLeft.displace(displacement), displacedPixels);
    }

    // TODO returnRotated

    // TODO returnStretched

    public GameImage getContentForCanvas(final int w, final int h) {
        final GameImage contentForCanvas = new GameImage(w, h);
        contentForCanvas.draw(content, topLeft.x, topLeft.y);
        return contentForCanvas.submit();
    }

    public Set<Coord2D> getPixels() {
        return pixels;
    }
}
