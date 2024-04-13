package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.DeltaTimeGlobal;

public class Permissions {
    public static boolean isTyping() {
        return DeltaTimeGlobal.getStatusOf(Constants.TYPING_CODE)
                .orElse(false) instanceof Boolean b && b;
    }
}
