package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;

public final class PickUpSelection extends Tool {
    private static final PickUpSelection INSTANCE;

    static {
        INSTANCE = new PickUpSelection();
    }

    private PickUpSelection() {

    }

    public static PickUpSelection get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Pick up selection";
    }

    // TODO: tool behaviour
    public void engage(final SEContext context) {

    }

    public void disengage(final SEContext context) {

    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {

    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {

    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {

    }
}
