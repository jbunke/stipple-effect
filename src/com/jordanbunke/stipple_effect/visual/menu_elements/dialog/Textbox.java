package com.jordanbunke.stipple_effect.visual.menu_elements.dialog;

import com.jordanbunke.delta_time.menu.menu_elements.ext.AbstractTextbox;
import com.jordanbunke.delta_time.menu.menu_elements.ext.drawing_functions.TextboxDrawingFunction;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Textbox extends AbstractTextbox {
    static {
        setTypingCode(Constants.TYPING_CODE);
    }

    public Textbox(
            final Coord2D position, final int width,
            final Anchor anchor, final String initialText,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter,
            final int maxLength
    ) {
        this(position, width, anchor, () -> "", initialText, () -> "",
                textValidator, setter, maxLength);
    }

    public Textbox(
            final Coord2D position, final int width, final Anchor anchor,
            final String prefix, final String initialText, final String suffix,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter,
            final int maxLength
    ) {
        this(position, width, anchor, () -> prefix, initialText, () -> suffix,
                textValidator, setter, maxLength);
    }

    public Textbox(
            final Coord2D position, final int width, final Anchor anchor,
            final Supplier<String> prefixGetter, final String initialText,
            final Supplier<String> suffixGetter,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter, final int maxLength
    ) {
        super(position, new Coord2D(width, Layout.STD_TEXT_BUTTON_H),
                anchor, prefixGetter, initialText, suffixGetter,
                textValidator, setter, GraphicsUtils::drawTextbox, maxLength);
    }

    public Textbox(
            final Coord2D position, final int width, final Anchor anchor,
            final Supplier<String> prefixGetter, final String initialText,
            final Supplier<String> suffixGetter,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter,
            final TextboxDrawingFunction fDraw, final int maxLength
    ) {
        super(position, new Coord2D(width, Layout.STD_TEXT_BUTTON_H),
                anchor, prefixGetter, initialText, suffixGetter,
                textValidator, setter, fDraw, maxLength);
    }

    public static Function<String, Boolean> getIntTextValidator(
            final int min, final int max
    ) {
        return s -> {
            try {
                final int value = Integer.parseInt(s);

                return value >= min && value <= max;
            } catch (NumberFormatException e) {
                return false;
            }
        };
    }

    public static Function<String, Boolean> getIntTextValidator(
            final Function<Integer, Boolean> embeddedLogic
    ) {
        return s -> {
            try {
                final int value = Integer.parseInt(s);

                return embeddedLogic.apply(value);
            } catch (NumberFormatException e) {
                return false;
            }
        };
    }

    public static Function<String, Boolean> getPositiveFloatValidator() {
        return s -> {
            try {
                final double value = Double.parseDouble(s);

                return value > 0d;
            } catch (NumberFormatException e) {
                return false;
            }
        };
    }

    public static boolean validateAsFileName(final String text) {
        return validateAsFileName(text, false);
    }

    public static boolean validateAsOptionallyEmptyFilename(final String text) {
        return validateAsFileName(text, true);
    }

    public static boolean validateAsFileName(final String text, final boolean allowEmpty) {
        final Set<Character> illegalCharSet = Set.of(
                '/', '\\', ':', '*', '?', '"', '<', '>', '|');

        return (allowEmpty || !text.isEmpty()) && illegalCharSet.stream()
                .map(c -> text.indexOf(c) == -1)
                .reduce((a, b) -> a && b).orElse(false);
    }
}
