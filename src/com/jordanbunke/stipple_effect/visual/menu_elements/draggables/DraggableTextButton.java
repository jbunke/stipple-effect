package com.jordanbunke.stipple_effect.visual.menu_elements.draggables;

import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.AbstractTextButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.Alignment;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class DraggableTextButton<T extends DraggableTextButton<T>>
        extends AbstractTextButton implements Draggable {
    protected Coord2D lockedPos;

    protected final SEContext c;
    protected final int index;
    private final String label;
    private boolean selected;

    private final Supplier<Integer> indexComparison;

    public DraggableTextButton(
            final Coord2D position, final int width,
            final Consumer<Integer> indexOnClick,
            final int index, final String label, final SEContext c,
            final Supplier<Integer> indexComparison
    ) {
        super(position, width, () -> indexOnClick.accept(index),
                Alignment.CENTER, ButtonType.STANDARD);

        this.c = c;
        this.index = index;
        this.label = label;

        this.indexComparison = indexComparison;

        populateMatrix();
        update(0d);

        lockPosition();
    }

    public abstract DragLogic<T> getLogic();

    @Override
    public final void lockPosition() {
        lockedPos = getPosition();
    }

    @Override
    public final void update(final double deltaTime) {
        selected = index == indexComparison.get();
    }

    @Override
    public final void process(final InputEventLogger eventLogger) {
        final Coord2D mousePos = eventLogger.getAdjustedMousePosition();
        final boolean mouseInBounds = mouseIsWithinBounds(mousePos);

        setHighlighted(!getLogic().isMoving() && (isSelected() || mouseInBounds));

        if (mouseInBounds) {
            final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();
            for (GameEvent e : unprocessed) {
                if (e instanceof GameMouseEvent me) {
                    switch (me.action) {
                        case CLICK -> {
                            me.markAsProcessed();
                            execute();
                            return;
                        }
                        case DOWN -> {
                            me.markAsProcessed();
                            getLogic().prepare(mousePos, index);
                        }
                    }
                }
            }
        }

        getLogic().process(eventLogger, index);
    }

    @Override
    public final boolean isSelected() {
        return selected;
    }

    @Override
    public final String getLabel() {
        return label;
    }

    @Override
    public final void incrementX(final int deltaX) {
        if (getLogic().isMoving())
            lockedPos = lockedPos.displace(deltaX, 0);
        else
            super.incrementX(deltaX);
    }

    @Override
    public final void incrementY(final int deltaY) {
        if (getLogic().isMoving())
            lockedPos = lockedPos.displace(0, deltaY);
        else
            super.incrementY(deltaY);
    }
}
