package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.tools.MoverTool;

import java.awt.*;
import java.util.Set;
import java.util.stream.Collectors;

public class SelectionContents {
    private final GameImage content;
    private final Coord2D topLeft;
    private final Set<Coord2D> pixels;

    private final SelectionContents original;

    public SelectionContents(
            final GameImage canvas, final Set<Coord2D> selection
    ) {
        pixels = selection.stream().filter(s -> s.x >= 0 && s.y >= 0 &&
                s.x < canvas.getWidth() &&
                s.y < canvas.getHeight()).collect(Collectors.toSet());

        topLeft = SelectionUtils.topLeft(pixels);
        content = makeContentFromSelection(canvas, pixels);

        original = new SelectionContents(content, topLeft, pixels);
    }

    private SelectionContents(
            final GameImage content, final Coord2D topLeft,
            final Set<Coord2D> pixels
    ) {
        this(content, topLeft, pixels,
                new SelectionContents(content, topLeft, pixels, null));
    }

    private SelectionContents(
            final GameImage content, final Coord2D topLeft,
            final Set<Coord2D> pixels, final SelectionContents original
    ) {
        this.content = content;
        this.topLeft = topLeft;
        this.pixels = pixels;

        this.original = original;
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

    public SelectionContents returnStretched(
            final Set<Coord2D> initialSelection, final Coord2D change,
            final MoverTool.Direction direction
    ) {
        final Set<Coord2D> pixels = SelectionUtils.stretchedPixels(
                initialSelection, change, direction);

        final Coord2D tl = SelectionUtils.topLeft(pixels),
                br = SelectionUtils.bottomRight(pixels);

        final int w = Math.max(1, br.x - tl.x),
                h = Math.max(1, br.y - tl.y);

        final GameImage content = new GameImage(w, h),
                sampleFrom = original == null ? this.content : original.content;

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (pixels.contains(tl.displace(x, y))) {
                    final int sampleX = (int)((x / (double) w) * sampleFrom.getWidth()),
                            sampleY = (int)((y / (double) h) * sampleFrom.getHeight());

                    content.dot(ImageProcessing.colorAtPixel(
                            sampleFrom, sampleX, sampleY), x, y);
                }

        return new SelectionContents(content.submit(), tl, pixels,
                original == null ? this : original);
    }

    public GameImage getContentForCanvas(final int w, final int h) {
        final GameImage contentForCanvas = new GameImage(w, h);
        contentForCanvas.draw(content, topLeft.x, topLeft.y);
        return contentForCanvas.submit();
    }

    public Set<Coord2D> getPixels() {
        return pixels;
    }
}
