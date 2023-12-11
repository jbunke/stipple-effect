package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.ImageContext;

public final class Eraser extends ToolWithRadius {
    private static final Eraser INSTANCE;

    private boolean erasing;
    private Coord2D lastTP;

    static {
        INSTANCE = new Eraser();
    }

    private Eraser() {
        erasing = false;
        lastTP = new Coord2D(-1, -1);
    }

    public static Eraser get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Eraser";
    }

    @Override
    public String getBottomBarText() {
        return getName() + " (" + (1 + (2 * getRadius())) + " px)";
    }

    @Override
    public void onMouseDown(final ImageContext context, final GameMouseEvent me) {
        if (context.hasTargetPixel()) {
            erasing = true;
            lastTP = new Coord2D(-1, -1);
            context.getState().markAsCheckpoint();
        }
    }

    @Override
    public void update(final ImageContext context, final Coord2D mousePosition) {
        if (erasing) {
            if (context.hasTargetPixel()) {
                final int w = context.getState().getImageWidth(),
                        h = context.getState().getImageHeight();
                final Coord2D tp = context.getTargetPixel();

                if (tp.equals(lastTP))
                    return;

                final boolean[][] eraseMask = new boolean[w][h];
                populateAround(eraseMask, tp.x, tp.y);

                final int xDiff = tp.x - lastTP.x, yDiff = tp.y - lastTP.y,
                        xUnit = (int) Math.signum(xDiff),
                        yUnit = (int) Math.signum(yDiff);
                if (!lastTP.equals(new Coord2D(-1, -1)) &&
                        (Math.abs(xDiff) > 1 || Math.abs(yDiff) > 1)) {
                    if (Math.abs(xDiff) > Math.abs(yDiff)) {
                        for (int x = 1; x < Math.abs(xDiff); x++) {
                            final int y = (int) (x * Math.abs(yDiff / (double) xDiff));
                            populateAround(eraseMask, lastTP.x + (xUnit * x), lastTP.y + (yUnit * y));
                        }
                    } else {
                        for (int y = 1; y < Math.abs(yDiff); y++) {
                            final int x = (int) (y * Math.abs(xDiff / (double) yDiff));
                            populateAround(eraseMask, lastTP.x + (xUnit * x), lastTP.y + (yUnit * y));
                        }
                    }
                }

                context.erase(eraseMask);
                lastTP = tp;
            } else
                lastTP = new Coord2D(-1, -1);
        }
    }

    private void populateAround(final boolean[][] eraseMask, final int tx, final int ty) {
        for (int x = Math.max(tx - getRadius(), 0); x <= Math.min(tx + getRadius(), eraseMask.length - 1); x++) {
            for (int y = Math.max(ty - getRadius(), 0); y <= Math.min(ty + getRadius(), eraseMask[x].length - 1); y++) {
                if (Coord2D.unitDistanceBetween(new Coord2D(x, y), new Coord2D(tx, ty)) <= getRadius())
                    eraseMask[x][y] = true;
            }
        }
    }

    @Override
    public void onMouseUp(final ImageContext context, final GameMouseEvent me) {
        erasing = false;
        context.getState().markAsCheckpoint();
    }
}
