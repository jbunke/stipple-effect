package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.context.ImageContext;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;

public final class Pencil extends Tool {
    private static final Pencil INSTANCE;

    private boolean drawing;
    private Color c;
    private Coord2D lastTP;

    static {
        INSTANCE = new Pencil();
    }

    private Pencil() {
        drawing = false;
        c = Constants.BLACK;
        lastTP = new Coord2D(-1, -1);
    }

    public static Pencil get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Pencil";
    }

    @Override
    public void onMouseDown(
            final ImageContext context, final GameMouseEvent me
    ) {
        if (context.hasTargetPixel() && me.button != GameMouseEvent.Button.MIDDLE) {
            drawing = true;
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
        if (drawing) {
            if (context.hasTargetPixel()) {
                final int w = context.getState().getImageWidth(),
                        h = context.getState().getImageHeight();
                final Coord2D tp = context.getTargetPixel();

                if (tp.equals(lastTP))
                    return;

                final GameImage edit = new GameImage(w, h);
                edit.dot(c, tp.x, tp.y);

                final int xDiff = tp.x - lastTP.x, yDiff = tp.y - lastTP.y,
                        xUnit = (int)Math.signum(xDiff),
                        yUnit = (int)Math.signum(yDiff);
                if (!lastTP.equals(new Coord2D(-1, -1)) &&
                        (Math.abs(xDiff) > 1 || Math.abs(yDiff) > 1)) {
                    if (Math.abs(xDiff) > Math.abs(yDiff)) {
                        for (int x = 1; x < Math.abs(xDiff); x++) {
                            final int y = (int)(x * Math.abs(yDiff / (double)xDiff));
                            edit.dot(lastTP.x + (xUnit * x), lastTP.y + (yUnit * y));
                        }
                    } else {
                        for (int y = 1; y < Math.abs(yDiff); y++) {
                            final int x = (int)(y * Math.abs(xDiff / (double)yDiff));
                            edit.dot(lastTP.x + (xUnit * x), lastTP.y + (yUnit * y));
                        }
                    }
                }

                context.editImage(edit.submit());
                lastTP = tp;
            } else
                drawing = false;
        }
    }

    @Override
    public void onMouseUp(
            final ImageContext context, final GameMouseEvent me
    ) {
        if (drawing) {
            drawing = false;
            context.getState().markAsCheckpoint();
            me.markAsProcessed();
        }
    }
}
