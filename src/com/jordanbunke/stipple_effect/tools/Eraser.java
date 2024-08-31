package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.state.ProjectState;
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
        if (!context.getTargetPixel().equals(Constants.NO_VALID_TARGET)) {
            erasing = true;

            reset();
            context.getState().markAsCheckpoint(false);
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();
        final ProjectState s = context.getState();

        if (erasing && !tp.equals(Constants.NO_VALID_TARGET)) {
            final int w = s.getImageWidth(), h = s.getImageHeight();
            final Selection selection = s.getSelection();

            if (isUnchanged(context))
                return;

            final boolean[][] eraseMask = new boolean[w][h];
            populateAround(eraseMask, tp, selection);

            fillLineSpace(getLastTP(), tp, (x, y) -> populateAround(
                    eraseMask, getLastTP().displace(x, y), selection));

            context.erase(eraseMask, false);
            updateLast(context);
        }
    }

    private void populateAround(
            final boolean[][] eraseMask, final Coord2D tp, Selection selection
    ) {
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();
        final boolean empty = !selection.hasSelection();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++) {
                final Coord2D e = new Coord2D(x + (tp.x - halfB),
                        y + (tp.y - halfB));

                if (!(empty || selection.selected(e)))
                    continue;

                if (mask[x][y] && e.x >= 0 && e.y >= 0 &&
                        e.x < eraseMask.length && e.y < eraseMask[e.x].length)
                    eraseMask[e.x][e.y] = true;
            }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (erasing) {
            erasing = false;
            context.getState().markAsCheckpoint(true);
            me.markAsProcessed();
        }
    }
}
