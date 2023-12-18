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
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++) {
                final Coord2D e = new Coord2D(x + (tp.x - halfB),
                        y + (tp.y - halfB));

                if (mask[x][y] && e.x >= 0 && e.y >= 0 &&
                        e.x < eraseMask.length && e.y < eraseMask.length)
                    eraseMask[e.x][e.y] = true;
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
