package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.DeltaTimeGlobal;

public class Permissions {
    public static boolean isTyping() {
        return DeltaTimeGlobal.getStatusOf(Constants.TYPING_CODE)
                .orElse(false) instanceof Boolean b && b;
    }

    public static boolean isCursorFree() {
        return DeltaTimeGlobal.getStatusOf(
                DeltaTimeGlobal.SC_CURSOR_CAPTURED).isEmpty();
    }

    public static boolean selectionIsCels() {
        return DeltaTimeGlobal.getStatusOf(Constants.CEL_SELECTION)
                .orElse(false) instanceof Boolean b && b;
    }
}
