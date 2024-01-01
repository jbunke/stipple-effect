package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.StretcherFunction;

import java.util.function.BiConsumer;

public final class PickUpSelection extends MoverTool {
    private static final PickUpSelection INSTANCE;

    static {
        INSTANCE = new PickUpSelection();
    }

    public static PickUpSelection get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Pick up selection";
    }

    public void engage(final SEContext context) {
        context.raiseSelectionToContents(false);
    }

    public void disengage(final SEContext context) {
        context.dropContentsToLayer(false, false);
    }

    @Override
    BiConsumer<Coord2D, Boolean> getMoverFunction(final SEContext context) {
        return context::moveSelectionContents;
    }

    @Override
    StretcherFunction getStretcherFunction(final SEContext context) {
        return context::stretchSelectionContents;
    }
}
