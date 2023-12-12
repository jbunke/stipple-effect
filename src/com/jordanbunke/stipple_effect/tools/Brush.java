package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.context.ImageContext;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;

public final class Brush extends ToolWithRadius {
    private static final Brush INSTANCE;

    private boolean painting;
    private Color c;
    private Coord2D lastTP;

    static {
        INSTANCE = new Brush();
    }

    private Brush() {
        painting = false;
        c = Constants.BLACK;
        lastTP = new Coord2D(-1, -1);
    }

    public static Brush get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Brush";
    }

    @Override
    public String getBottomBarText() {
        return getName() + " (" + (1 + (2 * getRadius())) + " px)";
    }

    @Override
    public void onMouseDown(
            final ImageContext context, final GameMouseEvent me
    ) {
        if (context.hasTargetPixel() && me.button != GameMouseEvent.Button.MIDDLE) {
            painting = true;
            c = me.button == GameMouseEvent.Button.LEFT
                    ? StippleEffect.get().getPrimary()
                    : StippleEffect.get().getSecondary();
            lastTP = new Coord2D(-1, -1);
            context.getState().markAsCheckpoint();
        }
    }

    @Override
    public void update(
            final ImageContext context, final Coord2D mousePosition
    ) {
        if (painting) {
            if (context.hasTargetPixel()) {
                final int w = context.getState().getImageWidth(),
                        h = context.getState().getImageHeight();
                final Coord2D tp = context.getTargetPixel();

                if (tp.equals(lastTP))
                    return;

                final GameImage edit = new GameImage(w, h);
                populateAround(edit, tp.x, tp.y);

                final int xDiff = tp.x - lastTP.x, yDiff = tp.y - lastTP.y,
                        xUnit = (int)Math.signum(xDiff),
                        yUnit = (int)Math.signum(yDiff);
                if (!lastTP.equals(new Coord2D(-1, -1)) &&
                        (Math.abs(xDiff) > 1 || Math.abs(yDiff) > 1)) {
                    if (Math.abs(xDiff) > Math.abs(yDiff)) {
                        for (int x = 1; x < Math.abs(xDiff); x++) {
                            final int y = (int)(x * Math.abs(yDiff / (double)xDiff));
                            populateAround(edit, lastTP.x + (xUnit * x), lastTP.y + (yUnit * y));
                        }
                    } else {
                        for (int y = 1; y < Math.abs(yDiff); y++) {
                            final int x = (int)(y * Math.abs(xDiff / (double)yDiff));
                            populateAround(edit, lastTP.x + (xUnit * x), lastTP.y + (yUnit * y));
                        }
                    }
                }

                context.editImage(edit.submit());
                lastTP = tp;
            } else
                lastTP = new Coord2D(-1, -1);
        }
    }

    private void populateAround(final GameImage edit, final int tx, final int ty) {
        for (int x = tx - getRadius(); x <= tx + getRadius(); x++) {
            for (int y = ty - getRadius(); y <= ty + getRadius(); y++) {
                if (Math.round(Coord2D.unitDistanceBetween(
                        new Coord2D(x, y), new Coord2D(tx, ty))) <= getRadius())
                    edit.dot(c, x, y);
            }
        }
    }

    @Override
    public void onMouseUp(
            final ImageContext context, final GameMouseEvent me
    ) {
        if (painting) {
            painting = false;
            context.getState().markAsCheckpoint();
            me.markAsProcessed();
        }
    }
}
