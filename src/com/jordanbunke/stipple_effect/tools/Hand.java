package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.visual.SECursor;

public final class Hand extends Tool {
    private static final Hand INSTANCE;

    private boolean panning;
    private Coord2D startMousePos, startAnchor;

    static {
        INSTANCE = new Hand();
    }

    private Hand() {
        panning = false;
        startMousePos = new Coord2D();
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
        startMousePos = me.mousePosition;
        startAnchor = context.renderInfo.getAnchor();
    }

    @Override
    public void update(
            final SEContext context, final Coord2D mousePosition
    ) {
        if (panning) {
            final float z = context.renderInfo.getZoomFactor();

            final Coord2D shift = new Coord2D(
                    (int)((startMousePos.x - mousePosition.x) / z),
                    (int)((startMousePos.y - mousePosition.y) / z));

            context.renderInfo.setAnchor(new Coord2D(
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
