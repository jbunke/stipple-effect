package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;

import java.awt.*;
import java.util.function.BiFunction;

public final class Brush extends AbstractBrush {
    private static final Brush INSTANCE;

    private BiFunction<Integer, Integer, Color> c;

    static {
        INSTANCE = new Brush();
    }

    private Brush() {
        c = (x, y) -> SEColors.def();
    }

    public static Brush get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Brush";
    }

    @Override
    void setColorGetter(final SEContext context, final GameMouseEvent me) {
        c = ToolThatDraws.getColorMode(me);
    }

    @Override
    boolean paintCondition(final Color existing, final int x, final int y) {
        return true;
    }

    @Override
    Color getColor(final Color existing, final int x, final int y) {
        return c.apply(x, y);
    }
}
