package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;

public final class Zoom extends Tool {
    private static final Zoom INSTANCE;

    static {
        INSTANCE = new Zoom();
    }

    /** Though an empty constructor is implied, this is explicitly written
      * in order to restrict its scope to its class and enforce the singleton
      * pattern. */
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
        final Coord2D tp = context.getTargetPixel();

        switch (me.button) {
            case LEFT -> context.renderInfo.zoomIn(tp);
            case RIGHT -> context.renderInfo.zoomOut(tp);
        }
    }
}
