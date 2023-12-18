package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.HashSet;
import java.util.Set;

public final class PencilSelect extends ToolWithMode {
    private static final PencilSelect INSTANCE;

    private boolean drawing;
    private Coord2D lastTP;

    static {
        INSTANCE = new PencilSelect();
    }

    private PencilSelect() {
        drawing = false;
        lastTP = Constants.NO_VALID_TARGET;
    }

    public static PencilSelect get() {
        return INSTANCE;
    }


    @Override
    public String getName() {
        return "Pencil Select";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (context.isTargetingPixelOnCanvas()) {
            drawing = true;

            final Coord2D tp = context.getTargetPixel();
            context.editSelection(new HashSet<>(Set.of(tp)));
            lastTP = tp;
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        if (drawing && context.isTargetingPixelOnCanvas()) {
            final Coord2D tp = context.getTargetPixel();

            if (tp.equals(lastTP))
                return;

            context.editSelection(new HashSet<>(Set.of(tp)));
            lastTP = tp;
        }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (drawing) {
            drawing = false;
            context.getState().markAsCheckpoint(true, context);
            me.markAsProcessed();
        }
    }
}
