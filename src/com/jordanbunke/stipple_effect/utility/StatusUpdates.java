package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.stipple_effect.StippleEffect;

public class StatusUpdates {
    public static void movedFrame(final int fromIndex, final int toIndex) {
        StippleEffect.get().sendStatusUpdate(
                "Moved selected frame from position " +
                        (fromIndex + 1) + " to " + (toIndex + 1));
    }

    public static void movedLayer(
            final String name, final int fromIndex, final int toIndex
    ) {
        StippleEffect.get().sendStatusUpdate("Moved layer \"" +
                name + "\" " + (fromIndex > toIndex ? "down" : "up") +
                " from position " + (fromIndex + 1) + " to " + (toIndex + 1));
    }

    public static void stateChangeFailed(final boolean wasUndo) {
        StippleEffect.get().sendStatusUpdate(
                "Cannot " + (wasUndo ? "undo" : "redo") + "; reached " +
                        (wasUndo ? "beginning" : "end") + " of project state stack"
        );
    }
}
