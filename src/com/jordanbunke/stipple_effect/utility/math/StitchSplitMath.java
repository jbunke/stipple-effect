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
        return splitFramesX(w) * splitFramesY(h);
    }

    public static int splitFramesX(final int w) {
        final int xDivs = DialogVals.getSplitColumns(),
                fw = DialogVals.getFrameWidth(),
                remX = w % fw;

        return xDivs + (remX == 0 || DialogVals.isTruncateSplitX() ? 0 : 1);
    }

    public static int splitFramesY(final int h) {
        final int yDivs = DialogVals.getSplitRows(),
                fh = DialogVals.getFrameHeight(),
                remY = h % fh;

        return yDivs + (remY == 0 || DialogVals.isTruncateSplitY() ? 0 : 1);
    }
}
