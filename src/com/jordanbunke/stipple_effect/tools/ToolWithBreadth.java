package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;

public sealed abstract class ToolWithBreadth extends ToolThatDraws permits Brush, Eraser {
    private int breadth;
    private GameImage overlay;

    ToolWithBreadth() {
        breadth = Constants.DEFAULT_BRUSH_BREADTH;
    }

    public static void drawAllToolOverlays() {
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
        final int z = (int) StippleEffect.get().getContext()
                .getRenderInfo().getZoomFactor();

        final boolean[][] mask = breadthMask();
        final GameImage initialMaskFill = new GameImage(
                mask.length * z, mask[0].length * z);

        final Color marked = Constants.BLACK, outside = Constants.WHITE,
                inside = Constants.ACCENT_BACKGROUND_DARK;

        for (int x = 0; x < initialMaskFill.getWidth(); x += z)
            for (int y = 0; y < initialMaskFill.getHeight(); y += z)
                if (mask[x / z][y / z])
                    initialMaskFill.fillRectangle(marked,
                            x, y, z, z);
        initialMaskFill.free();

        final GameImage overlay = new GameImage(
                initialMaskFill.getWidth() + (2 * Constants.OVERLAY_BORDER_PX),
                initialMaskFill.getHeight() + (2 * Constants.OVERLAY_BORDER_PX));

        for (int x = 0; x < initialMaskFill.getWidth(); x++)
            for (int y = 0; y < initialMaskFill.getHeight(); y++) {
                final Color c = ImageProcessing.colorAtPixel(initialMaskFill, x, y);

                if (!c.equals(marked))
                    continue;

                final Coord2D o = new Coord2D(x + Constants.OVERLAY_BORDER_PX,
                        y + Constants.OVERLAY_BORDER_PX);

                // left is off canvas or not marked
                if (x - 1 < 0 || !ImageProcessing.colorAtPixel(
                        initialMaskFill, x - 1, y).equals(marked)) {
                    overlay.dot(inside, o.x, o.y);
                    overlay.dot(outside, o.x - 1, o.y);
                }
                // right is off canvas or not marked
                if (x + 1 >= initialMaskFill.getWidth() ||
                        !ImageProcessing.colorAtPixel(initialMaskFill,
                                x + 1, y).equals(marked)) {
                    overlay.dot(inside, o.x, o.y);
                    overlay.dot(outside, o.x + 1, o.y);
                }
                // top is off canvas or not marked
                if (y - 1 < 0 || !ImageProcessing.colorAtPixel(
                        initialMaskFill, x, y - 1).equals(marked)) {
                    overlay.dot(inside, o.x, o.y);
                    overlay.dot(outside, o.x, o.y - 1);
                }
                // bottom is off canvas or not marked
                if (y + 1 >= initialMaskFill.getHeight() ||
                        !ImageProcessing.colorAtPixel(initialMaskFill,
                                x, y + 1).equals(marked)) {
                    overlay.dot(inside, o.x, o.y);
                    overlay.dot(outside, o.x, o.y + 1);
                }
            }

        this.overlay = overlay.submit();
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
