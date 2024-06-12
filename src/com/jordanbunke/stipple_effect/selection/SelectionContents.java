package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.tools.MoverTool;
import com.jordanbunke.stipple_effect.utility.math.Geometry;

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
                final int px = tl.x + x, py = tl.y + y;
                final boolean outOfBounds =
                        px < 0 || px >= canvas.getWidth() ||
                        py < 0 || py >= canvas.getHeight();

                if (!selection.selected(px, py) || outOfBounds)
                    continue;

                final int c = canvas.getColorAt(px, py).getRGB();
                content.setRGB(x, y, c);
            }
        }

        return content.submit();
    }

    public SelectionContents returnDisplaced(final Coord2D displacement) {
        return new SelectionContents(new GameImage(content),
                selection.displace(displacement));
    }

    public SelectionContents returnStretched(
            final Selection initialSelection, final Coord2D change,
            final MoverTool.Direction direction
    ) {
        final Selection stretched = SelectionUtils.stretchedPixels(
                initialSelection, change, direction);

        if (!stretched.hasSelection())
            return new SelectionContents(GameImage.dummy(),
                    Selection.EMPTY, original == null ? this : original);

        final int w = stretched.bounds.width(),
                h = stretched.bounds.height();
        final Coord2D tl = stretched.topLeft;

        final GameImage content = new GameImage(w, h),
                sampleFrom = original == null ? this.content : original.content;

        final int sw = sampleFrom.getWidth(), sh = sampleFrom.getHeight();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (stretched.selected(tl.displace(x, y))) {
                    final int sampleX = Math.min(sw - 1,
                            (int)Math.round((x / (double) w) * sw)),
                            sampleY = Math.min(sh - 1,
                                    (int)Math.round((y / (double) h) * sh));

                    content.dot(sampleFrom.getColorAt(sampleX, sampleY), x, y);
                }

        return new SelectionContents(content.submit(),
                stretched, original == null ? this : original);
    }

    public SelectionContents returnRotated(
            final Selection initialSelection,
            final double deltaR, final Coord2D pivot, final boolean[] offset
    ) {
        final Selection rotated = SelectionUtils.rotatedPixels(
                initialSelection, deltaR, pivot, offset);

        if (!rotated.hasSelection())
            return new SelectionContents(GameImage.dummy(),
                    Selection.EMPTY, original == null ? this : original);

        final int w = rotated.bounds.width(),
                h = rotated.bounds.height();
        final Coord2D tl = rotated.topLeft;

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
                if (rotated.selected(tl.displace(x, y))) {
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
                rotated, original == null ? this : original);
    }

    public SelectionContents returnReflected(
            final Selection initialSelection, final boolean horizontal
    ) {
        final Selection reflected =
                SelectionUtils.reflectedPixels(initialSelection, horizontal);

        if (!reflected.hasSelection())
            return new SelectionContents(GameImage.dummy(), Selection.EMPTY);

        final int w = reflected.bounds.width(),
                h = reflected.bounds.height();
        final Coord2D tl = reflected.topLeft;

        final GameImage content = new GameImage(w, h);

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (reflected.selected(tl.displace(x, y))) {
                    final int sampleX = horizontal ? (w - 1) - x : x,
                            sampleY = horizontal ? y : (h - 1) - y;

                    if (sampleX >= 0 && sampleY >= 0 &&
                            sampleX < this.content.getWidth() &&
                            sampleY < this.content.getHeight())
                        content.dot(this.content.getColorAt(sampleX, sampleY), x, y);
                }

        return new SelectionContents(content.submit(), reflected);
    }

    public GameImage getContentForCanvas(final int w, final int h) {
        final GameImage contentForCanvas = new GameImage(w, h);
        contentForCanvas.draw(content, selection.topLeft.x, selection.topLeft.y);
        return contentForCanvas.submit();
    }

    public Selection getSelection() {
        return selection;
    }
}
