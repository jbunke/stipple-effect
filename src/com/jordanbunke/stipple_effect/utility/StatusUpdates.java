package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;

import java.util.Set;

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

    public static void stateChangeFailed(final boolean triedUndo) {
        StippleEffect.get().sendStatusUpdate(
                "Cannot " + (triedUndo ? "undo" : "redo") + "; reached " +
                        (triedUndo ? "beginning" : "end") + " of project state stack"
        );
    }

    public static void sendToClipboard(
            final boolean copied, final Set<Coord2D> selection
    ) {
        StippleEffect.get().sendStatusUpdate((copied ? "Copied" : "Cut") + " " +
                        selection.size() + " pixels " +
                        (copied ? "" : "from the canvas ") +
                "to " + Constants.PROGRAM_NAME + "'s clipboard!"
        );
    }

    public static void clipboardSendFailed(
            final boolean triedCopy
    ) {
        StippleEffect.get().sendStatusUpdate("Cannot " +
                (triedCopy ? "copy" : "cut") + "; there is nothing selected");
    }

    public static void pasteFailed() {
        StippleEffect.get().sendStatusUpdate("Cannot paste; the " +
                Constants.PROGRAM_NAME + " clipboard is empty");
    }
}
