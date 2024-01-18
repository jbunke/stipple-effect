package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.visual.SEFonts;

import java.nio.file.Path;
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
                "to " + StippleEffect.PROGRAM_NAME + "'s clipboard"
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
                StippleEffect.PROGRAM_NAME + " clipboard is empty");
    }

    public static void invalidFontCode(final String attempt) {
        StippleEffect.get().sendStatusUpdate("The font code \"" + attempt +
                "\" is invalid; assigned to \"" + SEFonts.Code.CLASSIC.forButtonText() +
                "\" instead");
    }

    public static void saved(final Path filepath) {
        StippleEffect.get().sendStatusUpdate("Saved project to \"" +
                filepath + "\"");
    }

    public static void savedAllFrames(final Path folder) {
        StippleEffect.get().sendStatusUpdate(
                "Saved all frames in \"" + folder + "\"");
    }

    public static void savedPalette(final Path filepath) {
        StippleEffect.get().sendStatusUpdate("Saved palette to \"" +
                filepath + "\"");
    }

    public static void openFailed(final Path filepath) {
        StippleEffect.get().sendStatusUpdate("Couldn't open file \"" +
                filepath + "\"");
    }
}
