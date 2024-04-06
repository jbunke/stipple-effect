package com.jordanbunke.stipple_effect.visual.menu_elements.dialog;

import com.jordanbunke.delta_time.utility.Coord2D;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DynamicTextbox extends Textbox {
    private final Supplier<String> getter;

    public DynamicTextbox(
            final Coord2D position, final int width, final Anchor anchor,
            final String prefix, final String initialText, final String suffix,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter, final Supplier<String> getter,
            final int maxLength
    ) {
        super(position, width, anchor,
                prefix, initialText, suffix,
                textValidator, setter, maxLength);

        this.getter = getter;
    }

    @Override
    public void update(final double deltaTime) {
        if (!isTyping())
            setText(getter.get());

        super.update(deltaTime);
    }
}
