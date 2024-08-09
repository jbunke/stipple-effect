package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.project.SEContext;

import java.awt.*;
import java.util.function.Function;

public final class ShadeBrush extends AbstractBrush {
    private static final ShadeBrush INSTANCE;

    private Function<Color, Color> c;
    private Palette palette;

    static {
        INSTANCE = new ShadeBrush();
    }

    private ShadeBrush() {
        c = c -> c;
        palette = null;
    }

    public static ShadeBrush get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Shade Brush";
    }

    @Override
    void setColorGetter(final SEContext context, final GameMouseEvent me) {
        palette = StippleEffect.get().getSelectedPalette();

        if (palette != null)
            c = me.button == GameMouseEvent.Button.LEFT
                    ? palette::nextLeft : palette::nextRight;
    }

    @Override
    boolean paintCondition(final Color existing, final int x, final int y) {
        return palette != null && palette.isIncluded(existing);
    }

    @Override
    Color getColor(final Color existing, final int x, final int y) {
        return c.apply(existing);
    }
}
