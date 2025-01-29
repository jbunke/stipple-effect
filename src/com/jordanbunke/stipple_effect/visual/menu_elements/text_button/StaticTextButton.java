package com.jordanbunke.stipple_effect.visual.menu_elements.text_button;

import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.visual.SECursor;

public final class StaticTextButton extends AbstractTextButton {
    private final String label;

    public StaticTextButton(
            final Coord2D position, final String label,
            final int width, final Runnable onClick,
            final Alignment alignment, final ButtonType buttonType
    ) {
        super(position, width, onClick, alignment, buttonType);

        this.label = label;
        populateMatrix();
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        super.process(eventLogger);

        if (isHighlighted())
            SECursor.clickableElement();
    }

    @Override
    public void update(final double deltaTime) {

    }

    @Override
    public String getLabel() {
        return label;
    }
}
