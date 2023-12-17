package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.Set;

public abstract class ToolThatSearches extends ToolWithMode {
    private double tolerance;

    ToolThatSearches() {
        tolerance = Constants.DEFAULT_TOLERANCE;
    }

    @Override
    public String getBottomBarText() {
        final String context = tolerance == Constants.EXACT_COLOR_MATCH
                ? "exact match" : (tolerance == Constants.MAX_TOLERANCE
                ? "trivial" : (int)(tolerance * 100) + "% tolerance");

        return getName() + " (" + context + ")";
    }

    public double getTolerance() {
        return tolerance;
    }

    public void setTolerance(final double tolerance) {
        this.tolerance = Math.max(Constants.EXACT_COLOR_MATCH,
                Math.min(tolerance, Constants.MAX_TOLERANCE));
    }

    public void increaseTolerance() {
        if (tolerance < Constants.MAX_TOLERANCE)
            setTolerance(tolerance + Constants.SMALL_TOLERANCE_INC);
    }

    public void decreaseTolerance() {
        if (tolerance > Constants.EXACT_COLOR_MATCH)
            setTolerance(tolerance - Constants.SMALL_TOLERANCE_INC);
    }

    public void recursiveAdjacentSearch(
            final GameImage image, final Color initial, final Coord2D target,
            final Set<Coord2D> searched, final Set<Coord2D> matched
    ) {
        final int w = image.getWidth(), h = image.getHeight();

        searched.add(target);
        final Color pixel = ImageProcessing.colorAtPixel(image, target.x, target.y);

        if (pixelMatchesToleranceCondition(initial, pixel)) {
            matched.add(target);

            // neighbours
            if (target.x > 0 && !searched.contains(target.displace(-1, 0)))
                recursiveAdjacentSearch(image, initial, target.displace(-1, 0),
                        searched, matched);
            if (target.x + 1 < w && !searched.contains(target.displace(1, 0)))
                recursiveAdjacentSearch(image, initial, target.displace(1, 0),
                        searched, matched);
            if (target.y > 0 && !searched.contains(target.displace(0, -1)))
                recursiveAdjacentSearch(image, initial, target.displace(0, -1),
                        searched, matched);
            if (target.y + 1 < h && !searched.contains(target.displace(0, 1)))
                recursiveAdjacentSearch(image, initial, target.displace(0, 1),
                        searched, matched);
        }
    }

    public void globalSearch(
            final GameImage image, final Color initial,
            final Set<Coord2D> matched
    ) {
        final int w = image.getWidth(), h = image.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final Color pixel = ImageProcessing.colorAtPixel(image, x, y);

                if (pixelMatchesToleranceCondition(initial, pixel))
                    matched.add(new Coord2D(x, y));
            }
        }
    }

    public boolean pixelMatchesToleranceCondition(
            final Color initial, final Color pixel
    ) {
        if (initial.equals(pixel))
            return true;

        // if both pixels are completely transparent,
        // ignore RGB as pixels are indistinguishable
        if (initial.getAlpha() == 0 && pixel.getAlpha() == 0)
            return true;

        final int MAX_DIFF = 255 * 4;

        final int rDiff = Math.abs(initial.getRed() - pixel.getRed()),
                gDiff = Math.abs(initial.getGreen() - pixel.getGreen()),
                bDiff = Math.abs(initial.getBlue() - pixel.getBlue()),
                alphaDiff = Math.abs(initial.getAlpha() - pixel.getAlpha());

        final double difference = (rDiff + gDiff + bDiff + alphaDiff) / (double) MAX_DIFF;

        return difference <= tolerance;
    }
}
