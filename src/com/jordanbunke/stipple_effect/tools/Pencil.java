package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.Set;
import java.util.function.BiFunction;

public final class Pencil extends ToolThatDraws {
    private static final Pencil INSTANCE;

    private boolean drawing;
    private BiFunction<Integer, Integer, Color> c;

    static {
        INSTANCE = new Pencil();
    }

    private Pencil() {
        drawing = false;
        c = (x, y) -> Constants.BLACK;
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
            final SEContext context, final GameMouseEvent me
    ) {
        if (context.isTargetingPixelOnCanvas() &&
                me.button != GameMouseEvent.Button.MIDDLE) {
            drawing = true;
            c = ToolThatDraws.getColorMode(me);
            reset();
            context.getState().markAsCheckpoint(false, context);
        }
    }

    @Override
    public void update(
            final SEContext context, final Coord2D mousePosition
    ) {
        final Coord2D tp = context.getTargetPixel();

        if (drawing && !tp.equals(Constants.NO_VALID_TARGET)) {
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();
            final Set<Coord2D> selection = context.getState().getSelection();

            if (isUnchanged(context))
                return;

            final GameImage edit = new GameImage(w, h);

            if (selection.isEmpty() || selection.contains(tp))
                edit.dot(c.apply(tp.x, tp.y), tp.x, tp.y);

            final int xDiff = tp.x - getLastTP().x, yDiff = tp.y - getLastTP().y,
                    xUnit = (int)Math.signum(xDiff),
                    yUnit = (int)Math.signum(yDiff);
            if (!getLastTP().equals(Constants.NO_VALID_TARGET) &&
                    (Math.abs(xDiff) > 1 || Math.abs(yDiff) > 1)) {
                if (Math.abs(xDiff) > Math.abs(yDiff)) {
                    for (int x = 1; x < Math.abs(xDiff); x++) {
                        final int y = (int)(x * Math.abs(yDiff / (double)xDiff));
                        edit.dot(getLastTP().x + (xUnit * x), getLastTP().y + (yUnit * y));
                    }
                } else {
                    for (int y = 1; y < Math.abs(yDiff); y++) {
                        final int x = (int)(y * Math.abs(xDiff / (double)yDiff));
                        edit.dot(getLastTP().x + (xUnit * x), getLastTP().y + (yUnit * y));
                    }
                }
            }

            context.paintOverImage(edit.submit());
            updateLast(context);
        }
    }

    @Override
    public void onMouseUp(
            final SEContext context, final GameMouseEvent me
    ) {
        if (drawing) {
            drawing = false;
            context.getState().markAsCheckpoint(true, context);
            me.markAsProcessed();
        }
    }
}
