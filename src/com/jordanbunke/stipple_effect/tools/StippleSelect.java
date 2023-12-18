package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.SEContext;

import java.util.HashSet;
import java.util.Set;

public final class StippleSelect extends ToolWithMode {
    private static final StippleSelect INSTANCE;

    static {
        INSTANCE = new StippleSelect();
    }

    private StippleSelect() {

    }

    public static StippleSelect get() {
        return INSTANCE;
    }


    @Override
    public String getName() {
        return "Stipple Select";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (context.isTargetingPixelOnCanvas())
            context.editSelection(new HashSet<>(Set.of(context.getTargetPixel())));
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {

    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {

    }
}
