package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;
import com.jordanbunke.stipple_effect.utility.SECursor;

import java.awt.*;

public sealed abstract class ToolWithBreadth extends ToolThatDraws permits Brush, Eraser {
    private int breadth;
    private GameImage overlay;

    ToolWithBreadth() {
        breadth = Constants.DEFAULT_BRUSH_BREADTH;
    }

    public static void redrawToolOverlays() {
        Brush.get().drawOverlay();
        Eraser.get().drawOverlay();
    }

    public int breadthOffset() {
        return (breadth / 2) + (breadth % 2);
    }

    boolean[][] breadthMask() {
        final int remainder = breadth % 2, halfB = breadthOffset();

        final boolean[][] mask = new boolean[halfB * 2][halfB * 2];

        for (int x = 0; x < halfB * 2; x++)
            for (int y = 0; y < halfB * 2; y++) {
                final double distance = remainder == 1
                        ? Coord2D.unitDistanceBetween(new Coord2D(x, y),
                        new Coord2D(halfB, halfB))
                        : Coord2D.unitDistanceBetween(
                        new Coord2D((2 * x) + 1, (2 * y) + 1),
                        new Coord2D(halfB * 2, halfB * 2));
                double threshold = remainder == 1
                        ? breadth / 2. : (double) breadth;

                // special case
                if (breadth == 3)
                    threshold = Math.floor(threshold);

                if (distance <= threshold)
                    mask[x][y] = true;
            }

        return mask;
    }

    public void drawOverlay() {
        final boolean[][] mask = breadthMask();
        final Color outside = Constants.WHITE,
                inside = Constants.ACCENT_BACKGROUND_DARK;

        this.overlay = GraphicsUtils.drawOverlay(mask.length, mask[0].length,
                (int) StippleEffect.get().getContext()
                        .getRenderInfo().getZoomFactor(),
                (x, y) -> mask[x][y], inside, outside, false);
    }

    @Override
    public String getCursorCode() {
        return SECursor.RETICLE;
    }

    @Override
    public String getBottomBarText() {
        return getName() + " (" + getBreadth() + " px)";
    }

    public int getBreadth() {
        return breadth;
    }

    public GameImage getOverlay() {
        return overlay;
    }

    public void setBreadth(final int breadth) {
        this.breadth = Math.max(Constants.MIN_BREADTH,
                Math.min(breadth, Constants.MAX_BREADTH));

        drawOverlay();
    }

    public void increaseBreadth() {
        setBreadth(breadth + 1);
    }

    public void decreaseBreadth() {
        setBreadth(breadth - 1);
    }
}
