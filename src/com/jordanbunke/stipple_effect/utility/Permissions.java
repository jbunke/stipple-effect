package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.stipple_effect.preview.EmbeddedPreview;

import java.util.Optional;
import java.util.Set;

import static com.jordanbunke.delta_time.utility.DeltaTimeGlobal.*;
import static com.jordanbunke.stipple_effect.utility.action.ResourceCodes.*;

public class Permissions {
    public static boolean isTyping() {
        return getStatusOf(Constants.TYPING_CODE)
                .orElse(false) instanceof Boolean b && b;
    }

    public static boolean isCursorFree() {
        return getStatusOf(SC_CURSOR_CAPTURED).isEmpty();
    }

    public static boolean canSendToolTip(final String code) {
        final Set<String> previewCodes = Set.of(IMPORT_SCRIPT,
                REMOVE_SCRIPT, IMPORT_PREVIEW, IMPORT_PER_LAYER);

        final Optional<Object> status = getStatusOf(SC_CURSOR_CAPTURED);

        return status.isEmpty() ||
                (status.get() instanceof EmbeddedPreview &&
                        previewCodes.contains(code));
    }

    public static boolean selectionIsCels() {
        return getStatusOf(Constants.CEL_SELECTION)
                .orElse(false) instanceof Boolean b && b;
    }
}
