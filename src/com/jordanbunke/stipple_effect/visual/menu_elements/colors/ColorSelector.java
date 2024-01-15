package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.invisible.InvisibleMenuElement;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorSelector extends InvisibleMenuElement {
    private MenuElement[] contents;
    private int lastIndex;

    public ColorSelector() {
        super();

        lastIndex = StippleEffect.get().getColorIndex();
        contents = makeContents();
    }

    @Override
    public void update(final double deltaTime) {
        final int index = StippleEffect.get().getColorIndex();

        if (index != lastIndex) {
            contents = makeContents();
            lastIndex = index;
        }

        for (MenuElement element : contents)
            element.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        for (MenuElement element : contents)
            element.render(canvas);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        for (MenuElement element : contents)
            element.process(eventLogger);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    private static MenuElement[] makeContents() {
        final List<MenuElement> menuElements = new ArrayList<>();

        // red
        menuElements.add(new ColorComponent(0, Constants.RGBA_SCALE, "R",
                ColorComponent.Alignment.LEFT, 0, r -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(r, c.getGreen(), c.getBlue(), c.getAlpha());
                }, c -> StippleEffect.get().setSelectedColor(c),
                () -> StippleEffect.get().getSelectedColor().getRed()));
        // green
        menuElements.add(new ColorComponent(0, Constants.RGBA_SCALE, "G",
                ColorComponent.Alignment.LEFT, 1, g -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(c.getRed(), g, c.getBlue(), c.getAlpha());
                }, c -> StippleEffect.get().setSelectedColor(c),
                () -> StippleEffect.get().getSelectedColor().getGreen()));
        // blue
        menuElements.add(new ColorComponent(0, Constants.RGBA_SCALE, "B",
                ColorComponent.Alignment.LEFT, 2, b -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(c.getRed(), c.getGreen(), b, c.getAlpha());
                }, c -> StippleEffect.get().setSelectedColor(c),
                () -> StippleEffect.get().getSelectedColor().getBlue()));
        // hue
        menuElements.add(new ColorComponent(0, Constants.HUE_SCALE, "H",
                ColorComponent.Alignment.RIGHT, 0,
                h -> hueAdjustedColor(h, StippleEffect.get().getSelectedColor()),
                c -> StippleEffect.get().setSelectedColor(c),
                () -> scale(rgbToHue(StippleEffect.get().getSelectedColor()),
                        Constants.HUE_SCALE)));
        // saturation
        menuElements.add(new ColorComponent(0, Constants.SAT_SCALE, "S",
                ColorComponent.Alignment.RIGHT, 1,
                s -> satAdjustedColor(s, StippleEffect.get().getSelectedColor()),
                c -> StippleEffect.get().setSelectedColor(c),
                () -> scale(rgbToSat(StippleEffect.get().getSelectedColor()),
                        Constants.SAT_SCALE)));
        // value
        menuElements.add(new ColorComponent(0, Constants.VALUE_SCALE, "V",
                ColorComponent.Alignment.RIGHT, 2,
                v -> valueAdjustedColor(v, StippleEffect.get().getSelectedColor()),
                c -> StippleEffect.get().setSelectedColor(c),
                () -> scale(rgbToValue(StippleEffect.get().getSelectedColor()),
                        Constants.VALUE_SCALE)));
        // alpha
        menuElements.add(new ColorComponent(0, Constants.RGBA_SCALE, "A (Opacity)",
                ColorComponent.Alignment.FULL, 3, a -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
                }, c -> StippleEffect.get().setSelectedColor(c),
                () -> StippleEffect.get().getSelectedColor().getAlpha()));

        return menuElements.toArray(MenuElement[]::new);
    }

    // HSV helpers
    private static int scale(final double value, final int scaleMax) {
        return Math.max(0, Math.min((int)(value * scaleMax), scaleMax));
    }

    private static double normalize(final int value, final int scaleMax) {
        return value / (double) scaleMax;
    }

    private static double rgbToHue(final Color c) {
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

    private static double rgbToSat(final Color c) {
        final double[] rgb = rgbAsArray(c);
        final double max = getMaxOfRGB(rgb);

        if (max == 0d)
            return 0;
        else
            return getRangeOfRGB(rgb) / max;
    }

    private static double rgbToValue(final Color c) {
        return getMaxOfRGB(rgbAsArray(c));
    }

    private static Color hueAdjustedColor(final int hue, final Color c) {
        final double saturation = rgbToSat(c), value = rgbToValue(c);
        return fromHSV(hue == Constants.HUE_SCALE ? 0d : normalize(hue,
                Constants.HUE_SCALE), saturation, value, c.getAlpha());
    }

    private static Color satAdjustedColor(final int saturation, final Color c) {
        final double hue = rgbToHue(c), value = rgbToValue(c);
        return fromHSV(hue == Constants.HUE_SCALE ? 0d : hue, normalize(
                saturation, Constants.SAT_SCALE), value, c.getAlpha());
    }

    private static Color valueAdjustedColor(final int value, final Color c) {
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
