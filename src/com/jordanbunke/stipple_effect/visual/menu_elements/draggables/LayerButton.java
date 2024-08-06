package com.jordanbunke.stipple_effect.visual.menu_elements.draggables;

import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.AbstractTextButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.Alignment;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;

import java.util.List;

// TODO: Click + Drag tool tip
public final class LayerButton extends AbstractTextButton implements Draggable {
    public static final DragLogic<LayerButton> logic;

    private final SEContext c;
    private final int index;
    private final String label;
    private boolean selected;

    static {
        logic = new DragLogic<>(() -> {});
    }

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

        // layer buttons are added instantiated in reverse order
        logic.add(0, this);
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
    public void process(final InputEventLogger eventLogger) {
        final Coord2D mousePos = eventLogger.getAdjustedMousePosition();
        final boolean mouseInBounds = mouseIsWithinBounds(mousePos);

        setHighlighted(isSelected() || mouseInBounds);

        if (!mouseInBounds)
            return;

        final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();
        for (GameEvent e : unprocessed) {
            if (e instanceof GameMouseEvent mouseEvent &&
                    mouseEvent.matchesAction(GameMouseEvent.Action.CLICK)) {
                mouseEvent.markAsProcessed();
                execute();
                return;
            }
        }
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void lockPosition() {

    }

    @Override
    public void reachedDestination(final int destinationIndex) {

    }

    @Override
    public void drag() {

    }
}
