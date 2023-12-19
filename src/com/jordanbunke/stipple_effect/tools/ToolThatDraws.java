package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;

public abstract class ToolThatDraws extends Tool {
    private Coord2D lastTP;
    private int lastFrameIndex;

    ToolThatDraws() {
        reset();
    }

    public void updateLast(final SEContext context) {
        lastFrameIndex = context.getState().getFrameIndex();
        lastTP = context.getTargetPixel();
    }

    public boolean isUnchanged(final SEContext context) {
        final boolean sameFrame = lastFrameIndex == context.getState().getFrameIndex(),
                sameTP = lastTP.equals(context.getTargetPixel());

        return sameFrame && sameTP;
    }

    public void reset() {
        lastTP = Constants.NO_VALID_TARGET;
        lastFrameIndex = -1;
    }

    public Coord2D getLastTP() {
        return lastTP;
    }
}
