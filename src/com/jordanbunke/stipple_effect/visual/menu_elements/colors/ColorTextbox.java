package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.menu.menu_elements.ext.drawing_functions.TextboxDrawingFunction;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;
import com.jordanbunke.stipple_effect.visual.menu_elements.dialog.Textbox;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ColorTextbox extends Textbox {
    private static final int HEX_CODE_LENGTH = 6;

    private final int index;
    private final Supplier<Color> getter;

    private ColorTextbox(
            final Coord2D position, final Anchor anchor,
            final int index, final String initialText,
            final Supplier<Color> getter, final Consumer<String> setter
    ) {
        super(position, Layout.COLOR_TEXTBOX_W, anchor, () -> "#",
                initialText, () -> "", ColorTextbox::validateAsHexCode,
                setter, ColorTextbox.getFDraw(), HEX_CODE_LENGTH);

        this.index = index;
        this.getter = getter;

        updateAssets();
    }

    public static ColorTextbox make(
            final Coord2D position, final Anchor anchor, final int index
    ) {
        final Supplier<Color> getter = () ->
                StippleEffect.get().getColorAtIndex(index);
        final Consumer<String> setter = s ->
                ColorTextbox.setColorFromHexCode(s, index);
        final String initialText = colorToHexCode(getter.get());

        return new ColorTextbox(position, anchor, index,
                initialText, getter, setter);
    }

    private static TextboxDrawingFunction getFDraw() {
        return (dimensions, prefix, text, suffix, cursorIndex,
                selectionIndex, valid, highlighted, typing) -> {
            final int width = dimensions.x;
            final Color accent = typing
                    ? Settings.getTheme().highlightOutline
                    : Settings.getTheme().buttonOutline,
                    background = ColorTextbox.getColorFromHexCode(text);

            return GraphicsUtils.drawTextbox(
                    width, prefix, text, suffix,
                    cursorIndex, selectionIndex, highlighted,
                    accent, background);
        };
    }

    private static Color getColorFromHexCode(final String hexCode) {
        if (!validateAsHexCode(hexCode))
            return SEColors.transparent();

        final int LENGTH_OF_SECTION = 2, R = 0, G = 2, B = 4;

        final int r = hexToInt(hexCode.substring(R, R + LENGTH_OF_SECTION)),
                g = hexToInt(hexCode.substring(G, G + LENGTH_OF_SECTION)),
                b = hexToInt(hexCode.substring(B, B + LENGTH_OF_SECTION));

        return new Color(r, g, b);
    }

    public static void setColorFromHexCode(final String hexCode, final int index) {
        final Color rgb = getColorFromHexCode(hexCode);

        final int alpha = StippleEffect.get().getColorAtIndex(index).getAlpha();

        StippleEffect.get().setColorIndexAndColor(index,
                new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), alpha));
    }

    public static int hexToInt(final String hexSequence) {
        if (hexSequence.isEmpty() || !validHexSequence(hexSequence)) {
            GameError.send("String \"" + hexSequence +
                    "\" is not a valid hex sequence.");
            return 0;
        }

        final int BASE = 16;
        int accumulator = 0, power = 0;

        for (int i = hexSequence.length() - 1; i >= 0; i--) {
            final char c = hexSequence.toLowerCase().charAt(i);
            final int placeValue = isNumeric(c)
                    ? c - '0' : 10 + (c - 'a');

            accumulator += placeValue * Math.pow(BASE, power);
            power++;
        }

        return accumulator;
    }

    public static boolean validateAsHexCode(final String hexColorCode) {
        return hexColorCode.length() == HEX_CODE_LENGTH &&
                validHexSequence(hexColorCode);
    }

    private static boolean validHexSequence(final String hexSequence) {
        for (char c : hexSequence.toCharArray())
            if (!(isNumeric(c) || isAlpha(c)))
                return false;

        return true;
    }

    private static boolean isNumeric(final char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isAlpha(final char c) {
        return (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    @Override
    public void execute() {
        super.execute();

        StippleEffect.get().setColorIndex(index);
    }

    private static String colorToHexCode(final Color c) {
        final String r = Integer.toHexString(c.getRed());
        final String g = Integer.toHexString(c.getGreen());
        final String b = Integer.toHexString(c.getBlue());

        return (r.length() == 1 ? ("0") + r : r) +
                (g.length() == 1 ? ("0") + g : g) +
                (b.length() == 1 ? ("0") + b : b);
    }

    @Override
    public void update(final double deltaTime) {
        if (!isTyping())
            setText(colorToHexCode(getter.get()));

        super.update(deltaTime);
    }
}
