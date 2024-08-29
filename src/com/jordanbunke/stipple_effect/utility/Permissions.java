package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.stipple_effect.preview.EmbeddedPreview;

import java.util.Optional;

import static com.jordanbunke.delta_time.utility.DeltaTimeGlobal.SC_CURSOR_CAPTURED;
import static com.jordanbunke.delta_time.utility.DeltaTimeGlobal.getStatusOf;

public class Permissions {
    public static boolean isTyping() {
        return getStatusOf(Constants.TYPING_CODE)
                .orElse(false) instanceof Boolean b && b;
    }

    public static boolean isCursorFree() {
        return getStatusOf(SC_CURSOR_CAPTURED).isEmpty();
    }

    public static boolean canSendToolTip() {
        final Optional<Object> status = getStatusOf(SC_CURSOR_CAPTURED);

        if (status.isEmpty())
            return true;

        return status.get() instanceof EmbeddedPreview;
    }

    public static boolean selectionIsCels() {
        return getStatusOf(Constants.CEL_SELECTION)
                .orElse(false) instanceof Boolean b && b;
    }
}
