package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;

public final class Wand extends ToolThatSearches {
    private static final Wand INSTANCE;

    static {
        INSTANCE = new Wand();
    }

    private Wand() {

    }

    public static Wand get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Wand";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (context.isTargetingPixelOnCanvas()) {
            final Coord2D tp = context.getTargetPixel();

            final GameImage image = context.getState().getActiveCel();

            // search
            context.editSelection(search(image, tp), true);
        }
    }
}
