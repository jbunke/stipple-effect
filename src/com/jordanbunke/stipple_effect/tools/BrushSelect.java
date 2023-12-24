package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.HashSet;
import java.util.Set;

public final class BrushSelect extends ToolWithBreadth {
    private static final BrushSelect INSTANCE;

    private boolean selecting;
    private Set<Coord2D> selection;

    static {
        INSTANCE = new BrushSelect();
    }

    private BrushSelect() {
        selecting = false;
        selection = new HashSet<>();
    }

    public static BrushSelect get() {
        return INSTANCE;
    }

    @Override
    public String getCursorCode() {
        return super.getCursorCode() + "_" +
                ToolWithMode.getMode().name().toLowerCase();
    }

    @Override
    public String getName() {
        return "Brush Select";
    }

    private void drawSelection(final SEContext context) {
        context.editSelection(new HashSet<>(selection), false);
        selection = new HashSet<>();
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (!context.getTargetPixel().equals(Constants.NO_VALID_TARGET)) {
            selecting = true;
            selection = new HashSet<>();

            reset();
            context.getState().markAsCheckpoint(false, context);
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();

        if (selecting && !tp.equals(Constants.NO_VALID_TARGET)) {
            if (getLastTP().equals(tp))
                return;

            populateAround(tp);

            fillMouseSkips(tp, (x, y) ->
                    populateAround(getLastTP().displace(x, y)));

            drawSelection(context);
            updateLast(context);
        }
    }

    private void populateAround(final Coord2D tp) {
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++) {
                final Coord2D b = new Coord2D(x + (tp.x - halfB),
                        y + (tp.y - halfB));

                if (mask[x][y])
                    selection.add(b);
            }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (selecting) {
            selecting = false;
            context.getState().markAsCheckpoint(true, context);
            me.markAsProcessed();
        }
    }
}
