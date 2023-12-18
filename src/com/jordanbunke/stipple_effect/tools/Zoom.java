package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.context.SEContext;

public final class Zoom extends Tool {
    private static final Zoom INSTANCE;

    static {
        INSTANCE = new Zoom();
    }

    private Zoom() {

    }

    public static Zoom get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Zoom";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        switch (me.button) {
            case LEFT -> context.getRenderInfo().zoomIn();
            case RIGHT -> context.getRenderInfo().zoomOut();
        }

        context.getRenderInfo().setAnchor(context.getTargetPixel());

        StippleEffect.get().rebuildToolButtonMenu();
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {

    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {

    }
}
