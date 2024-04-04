package com.jordanbunke.stipple_effect.utility.math;

import com.jordanbunke.stipple_effect.utility.DialogVals;

public class StitchSplitMath {
    public static boolean isHorizontal() {
        return DialogVals.getSequenceOrder() ==
                DialogVals.SequenceOrder.HORIZONTAL;
    }

    public static int stitchedWidth(
            final int frameWidth, final int frameCount
    ) {
        return frameWidth * stitchedFramesX(frameCount);
    }

    public static int stitchedFramesX(final int frameCount) {
        final int fpd = DialogVals.getFramesPerDim(), fpcd =
                DialogVals.calculateFramesPerComplementaryDim(frameCount);
        return isHorizontal() ? fpd : fpcd;
    }

    public static int stitchedHeight(
            final int frameHeight, final int frameCount
    ) {
        return frameHeight * stitchedFramesY(frameCount);
    }

    public static int stitchedFramesY(final int frameCount) {
        final int fpd = DialogVals.getFramesPerDim(), fpcd =
                DialogVals.calculateFramesPerComplementaryDim(frameCount);
        return isHorizontal() ? fpcd : fpd;
    }

    public static int splitFrameCount(
            final int w, final int h
    ) {
        return splitColumns(w) * splitRows(h);
    }

    public static int splitColumns(final int w) {
        final int columns = DialogVals.getSplitColumns(),
                fw = DialogVals.getFrameWidth(),
                remX = w % fw;

        return columns + (remX == 0 || DialogVals.isTruncateSplitX() ? 0 : 1);
    }

    public static int splitRows(final int h) {
        final int rows = DialogVals.getSplitRows(),
                fh = DialogVals.getFrameHeight(),
                remY = h % fh;

        return rows + (remY == 0 || DialogVals.isTruncateSplitY() ? 0 : 1);
    }

    public static int importColumns(final int w) {
        final int columns = DialogVals.getImportColumns(),
                fw = DialogVals.getImportFrameWidth(),
                remX = w % fw;

        return columns + (remX == 0 || DialogVals.isTruncateSplitX() ? 0 : 1);
    }

    public static int importRows(final int h) {
        final int rows = DialogVals.getImportRows(),
                fh = DialogVals.getImportFrameHeight(),
                remY = h % fh;

        return rows + (remY == 0 || DialogVals.isTruncateSplitY() ? 0 : 1);
    }
}
