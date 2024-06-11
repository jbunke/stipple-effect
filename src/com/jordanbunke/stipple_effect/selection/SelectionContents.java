package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.tools.MoverTool;
import com.jordanbunke.stipple_effect.utility.math.Geometry;

import java.awt.*;
import java.util.Set;

public class SelectionContents {
    private static final int X = 0, Y = 1;

    private final GameImage content;
    private final Selection selection;

    private final SelectionContents original;

    public static SelectionContents make(
            final GameImage canvas, final Selection selection
    ) {
        final GameImage content = makeContentFromSelection(canvas, selection);
        final SelectionContents original =
                new SelectionContents(content, selection);

        return new SelectionContents(content, selection, original);
    }

    private SelectionContents(
            final GameImage content, final Selection selection
    ) {
        this(content, selection,
                new SelectionContents(content, selection, null));
    }

    private SelectionContents(
            final GameImage content, final Selection selection,
            final SelectionContents original
    ) {
        this.content = content;
        this.selection = selection;
        this.original = original;
    }

    private static GameImage makeContentFromSelection(
            final GameImage canvas, final Selection selection
    ) {
        final Coord2D tl = selection.topLeft;

        final int w = selection.bounds.width(),
                h = selection.bounds.height();
        final GameImage content = new GameImage(w, h);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (!selection.selected(tl.x + x, tl.y + y))
                    continue;

                final Color c = canvas.getColorAt(tl.x + x, tl.y + y);
                content.dot(c, x, y);
            }
        }

        return content.submit();
    }

    public SelectionContents returnDisplaced(final Coord2D displacement) {
        return new SelectionContents(new GameImage(content),
                selection.displace(displacement));
    }

    public SelectionContents returnStretched(
            final Set<Coord2D> initialSelection, final Coord2D change,
            final MoverTool.Direction direction
    ) {
        final Set<Coord2D> pixels = SelectionUtils.stretchedPixels(
                initialSelection, change, direction);

        if (pixels.isEmpty())
            return new SelectionContents(GameImage.dummy(),
                    Selection.fromPixels(pixels),
                    original == null ? this : original);

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

                    content.dot(sampleFrom.getColorAt(sampleX, sampleY), x, y);
                }

        return new SelectionContents(content.submit(),
                Selection.fromPixels(pixels),
                original == null ? this : original);
    }

    public SelectionContents returnRotated(
            final Set<Coord2D> initialSelection,
            final double deltaR, final Coord2D pivot, final boolean[] offset
    ) {
        final Set<Coord2D> pixels = SelectionUtils.rotatedPixels(
                initialSelection, deltaR, pivot, offset);

        if (pixels.isEmpty())
            return new SelectionContents(GameImage.dummy(),
                    Selection.fromPixels(pixels),
                    original == null ? this : original);

        final Coord2D tl = SelectionUtils.topLeft(pixels),
                br = SelectionUtils.bottomRight(pixels);

        final int w = Math.max(1, br.x - tl.x),
                h = Math.max(1, br.y - tl.y);

        final GameImage content = new GameImage(w, h),
                sampleFrom = original == null ? this.content : original.content;

        final Coord2D sampleTL = original == null
                ? selection.topLeft : original.selection.topLeft;

        final double[] realPivot = new double[] {
                pivot.x + (offset[X] ? -0.5 : 0d),
                pivot.y + (offset[Y] ? -0.5 : 0d)
        };

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (pixels.contains(tl.displace(x, y))) {
                    final int gx = tl.x + x, gy = tl.y + y;

                    final double distance = Math.sqrt(
                            Math.pow(realPivot[X] - gx, 2) +
                                    Math.pow(realPivot[Y] - gy, 2)),
                            angle = Geometry.calculateAngleInRad(
                                    gx, gy, realPivot[X], realPivot[Y]),
                            oldAngle = angle - deltaR;

                    final double deltaX = distance * Math.cos(oldAngle),
                            deltaY = distance * Math.sin(oldAngle);

                    final int globalX = (int)Math.round(realPivot[X] + deltaX),
                            globalY = (int)Math.round(realPivot[Y] + deltaY),
                            sampleX = globalX - sampleTL.x,
                            sampleY = globalY - sampleTL.y;

                    if (sampleX >= 0 && sampleY >= 0 &&
                            sampleX < sampleFrom.getWidth() &&
                            sampleY < sampleFrom.getHeight())
                        content.dot(sampleFrom.getColorAt(sampleX, sampleY), x, y);
                }

        return new SelectionContents(content.submit(),
                Selection.fromPixels(pixels),
                original == null ? this : original);
    }

    public SelectionContents returnReflected(
            final Set<Coord2D> initialSelection, final boolean horizontal
    ) {
        final Set<Coord2D> pixels =
                SelectionUtils.reflectedPixels(initialSelection, horizontal);

        if (pixels.isEmpty())
            return new SelectionContents(GameImage.dummy(),
                    Selection.fromPixels(pixels));

        final Coord2D tl = SelectionUtils.topLeft(pixels),
                br = SelectionUtils.bottomRight(pixels),
                middle = new Coord2D((tl.x + br.x) / 2, (tl.y + br.y) / 2);
        final boolean[] offset = new boolean[] {
                (tl.x + br.x) % 2 == 0,
                (tl.y + br.y) % 2 == 0
        };

        final int w = Math.max(1, br.x - tl.x),
                h = Math.max(1, br.y - tl.y);

        final GameImage content = new GameImage(w, h);

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (pixels.contains(tl.displace(x, y))) {
                    final Coord2D pixel = tl.displace(x, y),
                            was = new Coord2D(
                                    horizontal ? middle.x + (middle.x -
                                            pixel.x) - (offset[X] ? 1 : 0) : pixel.x,
                                    horizontal ? pixel.y : middle.y +
                                            (middle.y - pixel.y) - (offset[Y] ? 1 : 0)
                            );

                    final int sampleX = was.x - selection.topLeft.x,
                            sampleY = was.y - selection.topLeft.y;

                    if (sampleX >= 0 && sampleY >= 0 &&
                            sampleX < this.content.getWidth() &&
                            sampleY < this.content.getHeight())
                        content.dot(this.content.getColorAt(sampleX, sampleY), x, y);
                }

        return new SelectionContents(content.submit(),
                Selection.fromPixels(pixels));
    }

    public GameImage getContentForCanvas(final int w, final int h) {
        final GameImage contentForCanvas = new GameImage(w, h);
        contentForCanvas.draw(content, selection.topLeft.x, selection.topLeft.y);
        return contentForCanvas.submit();
    }

    public Selection getSelection() {
        return selection;
    }

    public Set<Coord2D> getPixels() {
        return selection.getPixels();
    }
}
