package com.jordanbunke.stipple_effect.visual.menu_elements.draggables;

import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.AbstractTextButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.Alignment;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;

public final class LayerButton extends AbstractTextButton {
    private final SEContext c;
    private final int index;
    private final String label;
    private boolean selected;

    public LayerButton(
            final Coord2D position, final int index,
            final SEContext c, final SELayer layer
    ) {
        super(position, Layout.LAYER_BUTTON_W,
                () -> c.getState().setLayerEditIndex(index),
                Alignment.CENTER, ButtonType.STANDARD);

        this.c = c;
        this.index = index;
        this.label = determineLabel(layer);

        populateMatrix();
        update(0d);
    }

    private String determineLabel(final SELayer layer) {
        final String name = layer.getName();

        return name.length() > Layout.LAYER_NAME_LENGTH_CUTOFF
                ? name.substring(0, Layout.LAYER_NAME_LENGTH_CUTOFF) + "..."
                : name;
    }

    @Override
    public void update(double deltaTime) {
        selected = index == c.getState().getLayerEditIndex();
    }

    @Override
    public void process(InputEventLogger eventLogger) {
        super.process(eventLogger);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
