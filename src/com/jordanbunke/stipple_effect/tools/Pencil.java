package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;

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
        c = (x, y) -> SEColors.def();
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
            context.getState().markAsCheckpoint(false);
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

            fillLineSpace(getLastTP(), tp, (x, y) ->
                            edit.dot(getLastTP().x + x, getLastTP().y + y));

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
            context.getState().markAsCheckpoint(true);
            me.markAsProcessed();
        }
    }

    @Override
    public boolean hasToolOptionsBar() {
        return true;
    }

    @Override
    int getDitherTextX() {
        return getFirstOptionLabelPosition().x;
    }
}
