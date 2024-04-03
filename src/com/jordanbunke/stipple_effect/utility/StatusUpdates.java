package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.stip.ParserSerializer;

import java.awt.*;
import java.nio.file.Path;
import java.util.Set;

public class StatusUpdates {
    // not permitted
    public static void cannotSetPixelGrid() {
        actionNotPermitted(
                "turn pixel grid on or off",
                "the pixel grid cannot be rendered for this project's current dimensions and/or zoom level"
        );
    }

    public static void cannotMergeWithLayerBelow(final String layerName) {
        actionNotPermitted("merge \"" + layerName + "\" with the layer below",
                "it is the bottommost layer");
    }

    public static void cannotMoveLayer(
            final String layerName, final boolean triedForward
    ) {
        cannotMove(true, "\"" + layerName + "\"", triedForward);
    }

    public static void cannotMoveFrame(
            final int index, final boolean triedForward
    ) {
        cannotMove(false, String.valueOf(index + 1), triedForward);
    }

    private static void cannotMove(
            final boolean isLayer, final String name, final boolean triedForward
    ) {
        final String thing = isLayer ? "layer" : "frame",
                direction = isLayer
                        ? (triedForward ? "up" : "down")
                        : (triedForward ? "forward" : "back"),
                extreme = isLayer
                        ? (triedForward ? "highest" : "lowest")
                        : (triedForward ? "last" : "first");

        actionNotPermitted("move " + thing + " " + name + " " + direction,
                "it is already the " + extreme + " " + thing + " in the project");
    }

    public static void cannotAddLayer() {
        cannotAdd("layer", Constants.MAX_NUM_LAYERS);
    }

    public static void cannotAddFrame() {
        cannotAdd("frame", Constants.MAX_NUM_FRAMES);
    }

    private static void cannotAdd(final String thing, final int maximum) {
        actionNotPermitted("add a " + thing + " to the project",
                "the maximum (" + maximum + ") has already been reached");
    }

    public static void cannotRemoveFrame() {
        cannotRemove("This", "frame");
    }

    public static void cannotRemoveLayer(final String layerName) {
        cannotRemove("\"" + layerName + "\"", "layer");
    }

    private static void cannotRemove(final String name, final String thing) {
        StippleEffect.get().sendStatusUpdate(name + " is the only " +
                thing + " in the project; it cannot be removed");
    }

    public static void noPalette() {
        StippleEffect.get().sendStatusUpdate(
                "There is no palette selected for this action to be performed");
    }

    public static void cannotShiftColorPalette(
            final Palette p, final Color c, final boolean isLeft
    ) {
        final boolean colorInPalette = p.canRemove(c);

        final String dir = isLeft ? "left" : "right";

        actionNotPermitted("shift the selected color " + processColor(c) +
                        " to the " + dir + " in \"" + p.getName() + "\"",
                p.isMutable() ? (colorInPalette
                        ? ("it is already the " + dir + "most color")
                        : "it is not in the palette")
                        : "\"" + p.getName() + "\" is immutable");
    }

    public static void cannotColorPalette(
            final boolean add, final Palette p, final Color c
    ) {
        actionNotPermitted((add ? "add" : "remove") + " the selected color " +
                        processColor(c) + (add ? " to " : " from ") +
                        "\"" + p.getName() + "\"",
                processColor(c) + (add ? " is already" : " is not") +
                        " in the palette");
    }

    public static void cannotSelectColorPalette(
            final Palette p, final Color c, final boolean isLeft
    ) {
        final String dir = isLeft ? "left" : "right";

        actionNotPermitted("select the next included color to the " + dir +
                        " in the palette \"" + p.getName() + "\"",
                "the current selected color " + processColor(c) +
                        " is not included in the palette");
    }

    private static void actionNotPermitted(
            final String attempt, final String reason
    ) {
        StippleEffect.get().sendStatusUpdate(
                "Cannot " + attempt + " because " + reason);
    }

    // toolbar actions
    public static void setPixelGrid(final boolean on) {
        final String value = on ? "ON" : "OFF";
        StippleEffect.get().sendStatusUpdate("Turned pixel grid " + value);
    }

    // color actions
    public static void colorSliderAdjustment(
            final String slider, final int sliderVal, final Color result
    ) {
        StippleEffect.get().sendStatusUpdate(slider + " slider set to " +
                sliderVal + "; selected color is " + processColor(result));
    }

    public static void swapColors() {
        final Color primary = StippleEffect.get().getPrimary(),
                secondary = StippleEffect.get().getSecondary();

        StippleEffect.get().sendStatusUpdate(
                "Primary: " + processColor(primary) +
                        " | Secondary: " + processColor(secondary));
    }

    public static void addColorToPalette(final Palette p, final Color c) {
        StippleEffect.get().sendStatusUpdate("Added " + processColor(c) +
                " to \"" + p.getName() + "\"");
    }

    public static void removeColorFromPalette(final Palette p, final Color c) {
        StippleEffect.get().sendStatusUpdate("Removed " + processColor(c) +
                " from \"" + p.getName() + "\"");
    }

    public static void moveLeftInPalette(final Palette p, final Color c) {
        StippleEffect.get().sendStatusUpdate("Shifted " + processColor(c) +
                " to the left in \"" + p.getName() + "\"");
    }

    public static void moveRightInPalette(final Palette p, final Color c) {
        StippleEffect.get().sendStatusUpdate("Shifted " + processColor(c) +
                " to the right in \"" + p.getName() + "\"");
    }

    public static void selectNextPaletteColor(
            final Palette p, final Color c, final boolean isLeft
    ) {
        final Color next = isLeft ? p.nextLeft(c) : p.nextRight(c);

        StippleEffect.get().sendStatusUpdate(
                "Selected next included color to the " +
                        (isLeft ? "left" : "right") + " of " +
                        processColor(c) + " in \"" + p.getName() +
                        "\": " + processColor(next));
    }

    private static String processColor(final Color c) {
        return Constants.OPEN_COLOR + ParserSerializer.serializeColor(c, true) +
                Constants.CLOSE_COLOR;
    }

    // frame actions
    public static void movedFrame(
            final int fromIndex, final int toIndex, final int frameCount
    ) {
        StippleEffect.get().sendStatusUpdate(
                "Moved selected frame from position (" + (fromIndex + 1) +
                        "/" + frameCount + ") to (" + (toIndex + 1) +
                        "/" + frameCount + ")");
    }

    public static void addedFrame(
            final boolean duplicated, final int wasIndex,
            final int index, final int frameCount
    ) {
        StippleEffect.get().sendStatusUpdate((duplicated
                ? "Duplicated frame from (" + (wasIndex + 1) + " to "
                : "Added frame (") + (index + 1) + "/" +
                frameCount + ")");
    }

    public static void removedFrame(
            final int wasIndex, final int index, final int frameCount
    ) {
        StippleEffect.get().sendStatusUpdate(
                "Removed frame " + (wasIndex + 1) + "; active frame: (" +
                        (index + 1) + "/" + frameCount + ")");
    }

    public static void frameNavigation(
            final int index, final int frameCount
    ) {
        StippleEffect.get().sendStatusUpdate(
                "Active frame: (" + (index + 1) + "/" + frameCount + ")");
    }

    // layer actions
    public static void changedLayerLinkedStatus(
            final boolean linked, final String layerName,
            final int index, final int layerCount
    ) {
        StippleEffect.get().sendStatusUpdate((linked ? "L" : "Unl") +
                "inked frames in layer \"" + layerName + "\" (" +
                (index + 1) + "/" + layerCount + ")");
    }

    public static void changedLayerVisibilityStatus(
            final boolean visible, final String layerName,
            final int index, final int layerCount
    ) {
        StippleEffect.get().sendStatusUpdate((visible ? "En" : "Dis") +
                "abled layer \"" + layerName + "\" (" +
                (index + 1) + "/" + layerCount + ")");
    }

    public static void addedLayer(
            final String layerName, final int index, final int layerCount
    ) {
        StippleEffect.get().sendStatusUpdate(
                "Added layer \"" + layerName + "\" (" +
                        (index + 1) + "/" + layerCount + ")");
    }

    public static void duplicatedLayer(
            final String oldLayerName, final String newLayerName,
            final int index, final int layerCount
    ) {
        StippleEffect.get().sendStatusUpdate("Duplicated layer \"" +
                oldLayerName + "\" as \"" + newLayerName + "\" (" +
                (index + 1) + "/" + layerCount + ")");
    }

    public static void removedLayer(
            final String layerName, final int index, final int layerCount
    ) {
        StippleEffect.get().sendStatusUpdate(
                "Removed layer \"" + layerName + "\"; active layer: (" +
                        (index + 1) + "/" + layerCount + ")");
    }

    public static void layerNavigation(
            final String layerName, final int index, final int layerCount
    ) {
        StippleEffect.get().sendStatusUpdate(
                "Active layer: \"" + layerName + "\" (" +
                        (index + 1) + "/" + layerCount + ")");
    }

    public static void mergedWithLayerBelow(
            final String aboveName, final String belowName,
            final int index, final int layerCount
    ) {
        StippleEffect.get().sendStatusUpdate("Merged layer \"" +
                aboveName + "\" onto \"" + belowName + "\" (" +
                (index + 1) + "/" + layerCount + ")");
    }

    public static void movedLayer(
            final String name, final int fromIndex, final int toIndex,
            final int layerCount
    ) {
        StippleEffect.get().sendStatusUpdate("Moved layer \"" +
                name + "\" " + (fromIndex > toIndex ? "down" : "up") +
                " from (" + (fromIndex + 1) + "/" + layerCount +
                ") to (" + (toIndex + 1) + "/" + layerCount + ")");
    }

    public static void stateChangeFailed(final boolean triedUndo) {
        StippleEffect.get().sendStatusUpdate(
                "Cannot " + (triedUndo ? "undo" : "redo") + "; reached " +
                        (triedUndo ? "beginning" : "end") + " of project state stack"
        );
    }

    public static void dumpedStates(
            final int dumped, final long kbsFreed
    ) {
        StippleEffect.get().sendStatusUpdate("Dumped " + dumped + " state" +
                (dumped > 1 ? "s" : "") + " due to low memory; freed " +
                kbsFreed + " KBs of memory");
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

    public static void saving() {
        StippleEffect.get().sendStatusUpdate(
                "Saving... do not close " + StippleEffect.PROGRAM_NAME +
                        " until the project has been saved");
    }

    public static void saveFailed() {
        StippleEffect.get().sendStatusUpdate("Failed to save file(s);" +
                " the project's save settings are invalid.");
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
