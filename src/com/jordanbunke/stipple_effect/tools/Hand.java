package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.SEContext;
import com.jordanbunke.stipple_effect.utility.SECursor;

public final class Hand extends Tool {
    private static final Hand INSTANCE;

    private boolean panning;
    private Coord2D startMousePosition, startAnchor;

    static {
        INSTANCE = new Hand();
    }

    private Hand() {
        panning = false;
        startMousePosition = new Coord2D();
        startAnchor = new Coord2D();
    }

    public static Hand get() {
        return INSTANCE;
    }

    @Override
    public String getCursorCode() {
        return panning ? SECursor.HAND_GRAB : SECursor.HAND_OPEN;
    }

    @Override
    public String getName() {
        return "Hand";
    }

    @Override
    public void onMouseDown(
            final SEContext context, final GameMouseEvent me
    ) {
        panning = true;
        startMousePosition = me.mousePosition;
        startAnchor = context.getRenderInfo().getAnchor();
    }

    @Override
    public void update(
            final SEContext context, final Coord2D mousePosition
    ) {
        if (panning) {
            final float zoomFactor = context.getRenderInfo().getZoomFactor();

            final Coord2D shift = new Coord2D(
                    (int)((startMousePosition.x - mousePosition.x) / zoomFactor),
                    (int)((startMousePosition.y - mousePosition.y) / zoomFactor)
            );
            context.getRenderInfo().setAnchor(new Coord2D(
                    startAnchor.x + shift.x, startAnchor.y + shift.y));
        }
    }

    @Override
    public void onMouseUp(
            final SEContext context, final GameMouseEvent me
    ) {
        if (panning) {
            panning = false;
            me.markAsProcessed();
        }
    }
}
