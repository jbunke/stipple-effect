package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.color.SEColors;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public final class Brush extends ToolWithBreadth {
    private static final Brush INSTANCE;

    private boolean painting;
    private BiFunction<Integer, Integer, Color> c;
    private Set<Coord2D> painted;

    static {
        INSTANCE = new Brush();
    }

    private Brush() {
        painting = false;
        c = (x, y) -> SEColors.def();
        painted = new HashSet<>();
    }

    public static Brush get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Brush";
    }

    @Override
    public void onMouseDown(
            final SEContext context, final GameMouseEvent me
    ) {
        if (!context.getTargetPixel().equals(Constants.NO_VALID_TARGET) &&
                me.button != GameMouseEvent.Button.MIDDLE) {
            painting = true;
            c = ToolThatDraws.getColorMode(me);
            painted = new HashSet<>();

            reset();
            context.getState().markAsCheckpoint(false);
        }
    }

    @Override
    public void update(
            final SEContext context, final Coord2D mousePosition
    ) {
        final Coord2D tp = context.getTargetPixel();

        if (painting && !tp.equals(Constants.NO_VALID_TARGET)) {
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();
            final Set<Coord2D> selection = context.getState().getSelection();

            if (isUnchanged(context))
                return;

            if (!stillSameFrame(context))
                painted.clear();

            final GameImage edit = new GameImage(w, h);
            populateAround(edit, tp, selection);

            fillLineSpace(getLastTP(), tp, (x, y) -> populateAround(
                    edit, getLastTP().displace(x, y), selection));

            context.paintOverImage(edit.submit());
            updateLast(context);
        }
    }

    private void populateAround(
            final GameImage edit, final Coord2D tp,
            final Set<Coord2D> selection
    ) {
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++) {
                final Coord2D b = new Coord2D(x + (tp.x - halfB),
                        y + (tp.y - halfB));

                if (!(selection.isEmpty() || selection.contains(b)))
                    continue;

                if (mask[x][y] && !painted.contains(b)) {
                    edit.dot(c.apply(b.x, b.y), b.x, b.y);
                    painted.add(b);
                }
            }
    }

    @Override
    public void onMouseUp(
            final SEContext context, final GameMouseEvent me
    ) {
        if (painting) {
            painting = false;
            context.getState().markAsCheckpoint(true);
            me.markAsProcessed();
        }
    }
}
