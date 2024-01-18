package com.jordanbunke.stipple_effect.utility;

import java.awt.*;
import java.util.Arrays;

public class ColorMath {
    public static double diff(final Color a, final Color b) {
        final int MAX_DIFF = 255 * 4;

        final int rDiff = Math.abs(a.getRed() - b.getRed()),
                gDiff = Math.abs(a.getGreen() - b.getGreen()),
                bDiff = Math.abs(a.getBlue() - b.getBlue()),
                alphaDiff = Math.abs(a.getAlpha() - b.getAlpha());

        return (rDiff + gDiff + bDiff + alphaDiff) / (double) MAX_DIFF;
    }

    public static int scale(final double value, final int scaleMax) {
        return Math.max(0, Math.min((int)(value * scaleMax), scaleMax));
    }

    private static double normalize(final int value, final int scaleMax) {
        return value / (double) scaleMax;
    }

    public static double rgbToHue(final Color c) {
        final int R = 0, G = 1, B = 2;
        final double[] rgb = rgbAsArray(c);
        final double max = getMaxOfRGB(rgb), range = getRangeOfRGB(rgb),
                multiplier = normalize(Constants.HUE_SCALE / 6, Constants.HUE_SCALE);

        if (range == 0d)
            return 0d;

        if (max == rgb[R]) {
            // red maximum case
            double value = (rgb[G] - rgb[B]) / range;

            while (value < 0)
                value += 6;
            while (value >= 6)
                value -= 6;

            return multiplier * value;
        } else if (max == rgb[G]) {
            // green maximum case
            return multiplier * (((rgb[B] - rgb[R]) / range) + 2);

        } else if (max == rgb[B]) {
            // blue maximum case
            return multiplier * (((rgb[R] - rgb[G]) / range) + 4);
        }

        return 0d;
    }

    public static double rgbToSat(final Color c) {
        final double[] rgb = rgbAsArray(c);
        final double max = getMaxOfRGB(rgb);

        if (max == 0d)
            return 0;
        else
            return getRangeOfRGB(rgb) / max;
    }

    public static double rgbToValue(final Color c) {
        return getMaxOfRGB(rgbAsArray(c));
    }

    public static Color hueAdjustedColor(final int hue, final Color c) {
        final double saturation = rgbToSat(c), value = rgbToValue(c);
        return fromHSV(hue == Constants.HUE_SCALE ? 0d : normalize(hue,
                Constants.HUE_SCALE), saturation, value, c.getAlpha());
    }

    public static Color satAdjustedColor(final int saturation, final Color c) {
        final double hue = rgbToHue(c), value = rgbToValue(c);
        return fromHSV(hue == Constants.HUE_SCALE ? 0d : hue, normalize(
                saturation, Constants.SAT_SCALE), value, c.getAlpha());
    }

    public static Color valueAdjustedColor(final int value, final Color c) {
        final double saturation = rgbToSat(c), hue = rgbToHue(c);
        return fromHSV(hue == Constants.HUE_SCALE ? 0d : hue, saturation,
                normalize(value, Constants.VALUE_SCALE), c.getAlpha());
    }

    private static Color fromHSV(
            final double hue, final double saturation,
            final double value, final int alpha
    ) {
        final double c = saturation * value,
                x = c * (1d - Math.abs(((hue / normalize(
                        Constants.HUE_SCALE / 6,
                        Constants.HUE_SCALE)) % 2) - 1)),
                m = value - c, r, g, b;

        if (hue < normalize((int)(Constants.HUE_SCALE * (1 / 6d)), Constants.HUE_SCALE)) {
            r = c;
            g = x;
            b = 0d;
        } else if (hue < normalize((int)(Constants.HUE_SCALE * (2 / 6d)), Constants.HUE_SCALE)) {
            r = x;
            g = c;
            b = 0d;
        } else if (hue < normalize((int)(Constants.HUE_SCALE * (3 / 6d)), Constants.HUE_SCALE)) {
            r = 0d;
            g = c;
            b = x;
        } else if (hue < normalize((int)(Constants.HUE_SCALE * (4 / 6d)), Constants.HUE_SCALE)) {
            r = 0d;
            g = x;
            b = c;
        } else if (hue < normalize((int)(Constants.HUE_SCALE * (5 / 6d)), Constants.HUE_SCALE)) {
            r = x;
            g = 0d;
            b = c;
        } else {
            r = c;
            g = 0d;
            b = x;
        }

        return new Color(
                scale(r + m, Constants.RGBA_SCALE),
                scale(g + m, Constants.RGBA_SCALE),
                scale(b + m, Constants.RGBA_SCALE),
                alpha);
    }

    private static double getRangeOfRGB(final double[] rgb) {
        return getMaxOfRGB(rgb) - getMinOfRGB(rgb);
    }

    private static double getMaxOfRGB(final double[] rgb) {
        return Arrays.stream(rgb).reduce(rgb[0], Math::max);
    }

    private static double getMinOfRGB(final double[] rgb) {
        return Arrays.stream(rgb).reduce(rgb[0], Math::min);
    }

    private static double[] rgbAsArray(final Color c) {
        return new double[] {
                c.getRed() / (double) Constants.RGBA_SCALE,
                c.getGreen() / (double) Constants.RGBA_SCALE,
                c.getBlue() / (double) Constants.RGBA_SCALE
        };
    }
}
