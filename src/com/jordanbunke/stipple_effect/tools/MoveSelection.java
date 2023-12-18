package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.SEContext;

public final class MoveSelection extends Tool {
    private static final MoveSelection INSTANCE;

    private boolean moving;
    private Coord2D lastMousePosition;

    static {
        INSTANCE = new MoveSelection();
    }

    private MoveSelection() {
        moving = false;
        lastMousePosition = new Coord2D();
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
        lastMousePosition = me.mousePosition;
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        if (moving) {
            final float zoomFactor = context.getRenderInfo().getZoomFactor();

            final Coord2D displacement = new Coord2D(
                    -(int)((lastMousePosition.x - mousePosition.x) / zoomFactor),
                    -(int)((lastMousePosition.y - mousePosition.y) / zoomFactor)
            );

            if (displacement.equals(new Coord2D()))
                return;

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
