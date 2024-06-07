package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

public class ShiftOrScale {
    public final boolean isShifting;
    public final int shift, min, max;
    public final double scale;

    public ShiftOrScale(final Object v) {
        isShifting = v instanceof Integer;

        shift = isShifting ? (int) v : 0;
        scale = isShifting ? 0.0 : (double) v;
        min = isShifting ? Constants.MIN_SV_SHIFT : 0;
        max = isShifting
                ? Constants.MAX_SV_SHIFT
                : (int) Constants.MAX_SV_SCALE;
    }

    public boolean outOfBounds() {
        if (isShifting)
            return shift < min || shift > max;

        return scale < min || scale > max;
    }

    public void oobNotification(
            final String property, final String attempt,
            final TextPosition position
    ) {
        final String op = isShifting ? "shift" : "scale",
                variable = property.charAt(0) + "_" + op,
                tail = isShifting ? "" : ".0";

        StatusUpdates.scriptActionNotPermitted(
                attempt, "the " + property + " " + op + " (" +
                        (isShifting ? shift : scale) +
                        ") is out of bounds (" + min + tail +
                        " <= " + variable + " <= " + max + tail + ")",
                position);
    }
}
