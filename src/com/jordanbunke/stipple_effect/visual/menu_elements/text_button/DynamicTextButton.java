package com.jordanbunke.stipple_effect.visual.menu_elements.text_button;

import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.util.function.Supplier;

public class DynamicTextButton extends AbstractTextButton {
    private final Supplier<String> getter;
    private String text;

    public DynamicTextButton(
            final Coord2D position, final int width,
            final Runnable onClick, final Supplier<String> getter
    ) {
        super(position, width, onClick, Alignment.CENTER, ButtonType.STANDARD);

        this.getter = getter;
        text = getter.get();

        updateAssets();
    }

    @Override
    public void update(final double deltaTime) {
        final String fetched = getter.get();

        if (!text.equals(fetched)) {
            text = fetched;
            updateAssets();
        }
    }

    private void updateAssets() {
        populateMatrix();
    }

    @Override
    public String getLabel() {
        return text;
    }
}
