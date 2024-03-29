package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.RotateFunction;
import com.jordanbunke.stipple_effect.selection.StretcherFunction;

import java.util.function.BiConsumer;

public final class MoveSelection extends MoverTool {
    private static final MoveSelection INSTANCE;

    static {
        INSTANCE = new MoveSelection();
    }

    public static MoveSelection get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Move selection";
    }


    @Override
    public BiConsumer<Coord2D, Boolean> getMoverFunction(final SEContext context) {
        return context::moveSelectionBounds;
    }

    @Override
    StretcherFunction getStretcherFunction(final SEContext context) {
        return context::stretchSelectionBounds;
    }

    @Override
    RotateFunction getRotateFunction(final SEContext context) {
        return context::rotateSelectionBounds;
    }

    @Override
    Runnable getMouseUpConsequence(final SEContext context) {
        return () -> context.getState().markAsCheckpoint(true);
    }
}
