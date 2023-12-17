package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;

public final class Eraser extends ToolWithBreadth {
    private static final Eraser INSTANCE;

    private boolean erasing;

    static {
        INSTANCE = new Eraser();
    }

    private Eraser() {
        erasing = false;
    }

    public static Eraser get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Eraser";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (context.isTargetingPixelOnCanvas()) {
            erasing = true;
            reset();
            context.getState().markAsCheckpoint(false, context);
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        if (erasing) {
            if (context.isTargetingPixelOnCanvas()) {
                final int w = context.getState().getImageWidth(),
                        h = context.getState().getImageHeight();
                final Coord2D tp = context.getTargetPixel();

                if (isUnchanged(context))
                    return;

                final boolean[][] eraseMask = new boolean[w][h];
                populateAround(eraseMask, tp);

                final int xDiff = tp.x - getLastTP().x, yDiff = tp.y - getLastTP().y,
                        xUnit = (int) Math.signum(xDiff),
                        yUnit = (int) Math.signum(yDiff);
                if (!getLastTP().equals(Constants.NO_VALID_TARGET) &&
                        (Math.abs(xDiff) > 1 || Math.abs(yDiff) > 1)) {
                    if (Math.abs(xDiff) > Math.abs(yDiff)) {
                        for (int x = 1; x < Math.abs(xDiff); x++) {
                            final int y = (int) (x * Math.abs(yDiff / (double) xDiff));
                            populateAround(eraseMask, getLastTP().displace(xUnit * x, yUnit * y));
                        }
                    } else {
                        for (int y = 1; y < Math.abs(yDiff); y++) {
                            final int x = (int) (y * Math.abs(xDiff / (double) yDiff));
                            populateAround(eraseMask, getLastTP().displace(xUnit * x, yUnit * y));
                        }
                    }
                }

                context.erase(eraseMask);
                updateLast(context);
            } else
                reset();
        }
    }

    private void populateAround(final boolean[][] eraseMask, final Coord2D tp) {
        final int remainder = getBreadth() % 2, halfB = (getBreadth() / 2) + remainder;

        for (int x = tp.x - halfB; x < tp.x + halfB; x++)
            for (int y = tp.y - halfB; y < tp.y + halfB; y++) {
                if (x < 0 || y < 0 || x >= eraseMask.length || y >= eraseMask[x].length)
                    continue;

                final double distance = remainder == 1
                        ? Coord2D.unitDistanceBetween(new Coord2D(x, y), tp)
                        : Coord2D.unitDistanceBetween(
                        new Coord2D((2 * x) + 1, (2 * y) + 1),
                        new Coord2D(tp.x * 2, tp.y * 2)),
                        threshold = remainder == 1
                                ? getBreadth() / 2. : (double) getBreadth();

                if (distance <= threshold)
                    eraseMask[x][y] = true;
            }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (erasing) {
            erasing = false;
            context.getState().markAsCheckpoint(true, context);
            me.markAsProcessed();
        }
    }
}
