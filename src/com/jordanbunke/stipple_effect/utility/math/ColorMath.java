package com.jordanbunke.stipple_effect.utility.math;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.DialogVals;

import java.awt.*;
import java.util.Map;
import java.util.function.Function;

public class ColorMath {
    private static LastHSVEdit lastHSVEdit = LastHSVEdit.NONE;
    private static double lastHue = 0d, lastSat = 0d, lastValue = 0d;

    public enum LastHSVEdit {
        HUE, SAT, VAL, NONE
    }

    public static GameImage algo(
            final Function<Color, Color> internal,
            final Map<Color, Color> map,
            final GameImage source, final Selection selection
    ) throws RuntimeException {
        final int w = source.getWidth(), h = source.getHeight();
        final GameImage res = new GameImage(w, h);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final Color c = source.getColorAt(x, y);

                if (c.getAlpha() == 0)
                    continue;

                final Color cp;

                if (map.containsKey(c))
                    cp = map.get(c);
                else {
                    cp = internal.apply(c);

                    if (cp == null)
                        throw new RuntimeException("Color function did not return a value");

                    map.put(c, cp);
                }

                if (selection != null && !selection.selected(x, y))
                    res.setRGB(x, y, c.getRGB());
                else if (cp != null)
                    res.setRGB(x, y, cp.getRGB());
            }
        }

        return res.submit();
    }

    public static Color shiftHSV(final Color input) {
        final double h = rgbToHue(input),
                s = rgbToSat(input), v = rgbToValue(input);

        final int shiftH = DialogVals.getHueShift(),
                shiftS = DialogVals.getSatShift(),
                shiftV = DialogVals.getValueShift();
        final double scaleS = DialogVals.getSatScale(),
                scaleV = DialogVals.getValueScale();

        final double nh =
                normalize(h + scaleDown(shiftH, Constants.HUE_SCALE)),
                ns = MathPlus.bounded(0d, (DialogVals.isShiftingSat()
                        ? s + scaleDown(shiftS, Constants.SAT_SCALE)
                        : s * scaleS), 1d),
                nv = MathPlus.bounded(0d, (DialogVals.isShiftingValue()
                        ? v + scaleDown(shiftV, Constants.VALUE_SCALE)
                        : v * scaleV), 1d);

        return fromHSV(nh, ns, nv, input.getAlpha());
    }

    public static void setLastHSVEdit(final LastHSVEdit lastHSVEdit, final Color c) {
        ColorMath.lastHSVEdit = lastHSVEdit;

        if (ColorMath.lastHSVEdit == LastHSVEdit.NONE) {
            lastHue = rgbToHue(c);
            lastSat = rgbToSat(c);
            lastValue = rgbToValue(c);
        }
    }

    public static Color betweenColor(
            final double pos, final Color start, final Color end
    ) {
        if (pos <= 0d)
            return start;
        else if (pos >= 1d)
            return end;

        final int baseR = start.getRed(), baseG = start.getGreen(),
                baseB = start.getBlue(), baseA = start.getAlpha(),
                deltaR = end.getRed() - start.getRed(),
                deltaG = end.getGreen() - start.getGreen(),
                deltaB = end.getBlue() - start.getBlue(),
                deltaA = end.getAlpha() - start.getAlpha();

        return new Color(
                baseR + (int)Math.round(deltaR * pos),
                baseG + (int)Math.round(deltaG * pos),
                baseB + (int)Math.round(deltaB * pos),
                baseA + (int)Math.round(deltaA * pos)
        );
    }

    public static double diff(final Color a, final Color b) {
        final int MAX_DIFF = 255 * 4;

        final int rDiff = Math.abs(a.getRed() - b.getRed()),
                gDiff = Math.abs(a.getGreen() - b.getGreen()),
                bDiff = Math.abs(a.getBlue() - b.getBlue()),
                alphaDiff = Math.abs(a.getAlpha() - b.getAlpha());

        return (rDiff + gDiff + bDiff + alphaDiff) / (double) MAX_DIFF;
    }

    public static int hueGetter(final Color c) {
        return scaleUp(fetchHue(c), Constants.HUE_SCALE);
    }

    private static double fetchHue(final Color c) {
        return lastHSVEdit == LastHSVEdit.NONE ? rgbToHue(c) : lastHue;
    }

    public static int satGetter(final Color c) {
        return scaleUp(fetchSat(c), Constants.SAT_SCALE);
    }

    private static double fetchSat(final Color c) {
        return lastHSVEdit == LastHSVEdit.NONE ? rgbToSat(c) : lastSat;
    }

    public static int valueGetter(final Color c) {
        return scaleUp(fetchValue(c), Constants.VALUE_SCALE);
    }

    private static double fetchValue(final Color c) {
        return lastHSVEdit == LastHSVEdit.NONE ? rgbToValue(c) : lastValue;
    }

    private static int scaleUp(final double value, final int scaleMax) {
        return MathPlus.bounded(0, (int) Math.round(value * scaleMax), scaleMax);
    }

    private static double scaleDown(final int value, final int scaleMax) {
        return value / (double) scaleMax;
    }

    private static double normalize(final double n) {
        final double UNIT = 1d;

        double res = n;

        while (res < 0d)
            res += UNIT;

        while (res >= UNIT)
            res -= UNIT;

        return res;
    }

    public static double rgbToHue(final Color c) {
        final int R = 0, G = 1, B = 2;
        final double[] rgb = rgbAsArray(c);
        final double max = getMaxOfRGB(rgb), range = getRangeOfRGB(rgb),
                multiplier = scaleDown(Constants.HUE_SCALE / 6, Constants.HUE_SCALE);

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
        final double saturation = fetchSat(c), value = fetchValue(c),
                nHue = scaleDown(hue, Constants.HUE_SCALE);

        lastHue = nHue;
        return fromHSV(nHue, saturation, value, c.getAlpha());
    }

    public static Color satAdjustedColor(final int saturation, final Color c) {
        final double hue = fetchHue(c), value = fetchValue(c),
                nSat = scaleDown(saturation, Constants.SAT_SCALE);

        lastSat = nSat;
        return fromHSV(hue, nSat, value, c.getAlpha());
    }

    public static Color valueAdjustedColor(final int value, final Color c) {
        final double saturation = fetchSat(c), hue = fetchHue(c),
                nValue = scaleDown(value, Constants.VALUE_SCALE);

        lastValue = nValue;
        return fromHSV(hue, saturation, nValue, c.getAlpha());
    }

    public static Color fromHSV(
            final double hue, final double saturation,
            final double value, final int alpha
    ) {
        final double c = saturation * value,
                x = c * (1d - Math.abs(((hue / scaleDown(
                        Constants.HUE_SCALE / 6,
                        Constants.HUE_SCALE)) % 2) - 1)),
                m = value - c, r, g, b;

        if (hue < scaleDown((int)(Constants.HUE_SCALE * (1 / 6d)), Constants.HUE_SCALE)) {
            r = c;
            g = x;
            b = 0d;
        } else if (hue < scaleDown((int)(Constants.HUE_SCALE * (2 / 6d)), Constants.HUE_SCALE)) {
            r = x;
            g = c;
            b = 0d;
        } else if (hue < scaleDown((int)(Constants.HUE_SCALE * (3 / 6d)), Constants.HUE_SCALE)) {
            r = 0d;
            g = c;
            b = x;
        } else if (hue < scaleDown((int)(Constants.HUE_SCALE * (4 / 6d)), Constants.HUE_SCALE)) {
            r = 0d;
            g = x;
            b = c;
        } else if (hue < scaleDown((int)(Constants.HUE_SCALE * (5 / 6d)), Constants.HUE_SCALE)) {
            r = x;
            g = 0d;
            b = c;
        } else {
            r = c;
            g = 0d;
            b = x;
        }

        return new Color(
                scaleUp(r + m, Constants.RGBA_SCALE),
                scaleUp(g + m, Constants.RGBA_SCALE),
                scaleUp(b + m, Constants.RGBA_SCALE),
                alpha);
    }

    private static double getRangeOfRGB(final double[] rgb) {
        return getMaxOfRGB(rgb) - getMinOfRGB(rgb);
    }

    private static double getMaxOfRGB(final double[] rgb) {
        return MathPlus.max(rgb);
    }

    private static double getMinOfRGB(final double[] rgb) {
        return MathPlus.min(rgb);
    }

    private static double[] rgbAsArray(final Color c) {
        return new double[] {
                c.getRed() / (double) Constants.RGBA_SCALE,
                c.getGreen() / (double) Constants.RGBA_SCALE,
                c.getBlue() / (double) Constants.RGBA_SCALE
        };
    }
}
