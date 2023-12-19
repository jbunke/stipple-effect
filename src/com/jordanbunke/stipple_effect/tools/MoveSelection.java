package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.SEContext;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.utility.Constants;

public final class MoveSelection extends Tool {
    private static final MoveSelection INSTANCE;

    private boolean moving;
    private Coord2D startMousePosition, lastMousePosition, startTopLeft;

    static {
        INSTANCE = new MoveSelection();
    }

    private MoveSelection() {
        moving = false;
        startMousePosition = new Coord2D();
        lastMousePosition = new Coord2D();
        startTopLeft = Constants.NO_VALID_TARGET;
    }

    public static MoveSelection get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Move selection";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        moving = true;
        startMousePosition = me.mousePosition;
        lastMousePosition = me.mousePosition;
        startTopLeft = Selection.topLeft(context.getState().getSelection());
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        if (moving) {
            final float zoomFactor = context.getRenderInfo().getZoomFactor();

            if (mousePosition.equals(lastMousePosition))
                return;

            final Coord2D topLeft = Selection.topLeft(
                    context.getState().getSelection());
            final Coord2D displacement = new Coord2D(
                    -(int)((startMousePosition.x - mousePosition.x) / zoomFactor),
                    -(int)((startMousePosition.y - mousePosition.y) / zoomFactor)
            ).displace(startTopLeft.x - topLeft.x, startTopLeft.y - topLeft.y);

            // TODO: performance optimization - preview with overlay
            //  but don't compute move selection until mouse up event
            context.moveSelection(displacement);
            lastMousePosition = mousePosition;
        }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (moving) {
            moving = false;
            me.markAsProcessed();
        }
    }
}
