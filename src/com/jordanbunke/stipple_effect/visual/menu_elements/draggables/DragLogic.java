package com.jordanbunke.stipple_effect.visual.menu_elements.draggables;

import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class DragLogic<T extends Draggable> {
    private static final int NONE = Constants.NO_SELECTION;

    private final List<T> elements = new ArrayList<>();
    private boolean moving;
    private int movingIndex, wouldBeIndex;
    private Coord2D onClickPos, lastMousePos;

    private final Runnable menuUpdateOnRelease;

    public DragLogic(final Runnable menuUpdateOnRelease) {
        this.menuUpdateOnRelease = menuUpdateOnRelease;

        moving = false;
        movingIndex = NONE;
        wouldBeIndex = NONE;
    }

    public void process(
            final InputEventLogger eventLogger, final int index
    ) {
        final Coord2D mousePos = eventLogger.getAdjustedMousePosition();

        if (isMoving()) {
            lastMousePos = mousePos;

            for (GameEvent e : eventLogger.getUnprocessedEvents()) {
                if (e instanceof GameMouseEvent me &&
                        me.matchesAction(GameMouseEvent.Action.UP)) {
                    me.markAsProcessed();

                    release();
                    return;
                }
            }

            elements.get(index).drag();
        } else if (movingIndex != NONE) {
            for (GameEvent e : eventLogger.getUnprocessedEvents()) {
                if (e instanceof GameMouseEvent me &&
                        me.matchesAction(GameMouseEvent.Action.UP)) {
                    me.markAsProcessed();

                    cancel();

                    return;
                }
            }

            if (onClickPos != mousePos) {
                startMoving();
                elements.get(movingIndex).updateAssetForMoving();
            }
        }
    }

    public void prepare(final Coord2D mousePos, final int elementIndex) {
        onClickPos = mousePos;
        movingIndex = elementIndex;
        wouldBeIndex = elementIndex;

        for (Draggable e : elements)
            e.lockPosition();
    }

    public void startMoving() {
        moving = true;
    }

    private void cancel() {
        movingIndex = NONE;
        wouldBeIndex = NONE;
    }

    private void release() {
        final int sourceIndex = movingIndex, destinationIndex = wouldBeIndex;

        final Draggable draggable = elements.get(sourceIndex);
        draggable.updateAssetFromMoving();

        moving = false;
        movingIndex = NONE;
        wouldBeIndex = NONE;

        if (destinationIndex != sourceIndex) {
            draggable.reachedDestination(destinationIndex);
            menuUpdateOnRelease.run();
        }
    }

    public boolean isMoving() {
        return moving;
    }

    public void reset() {
        elements.clear();
    }

    public void add(final int index, final T element) {
        elements.add(index, element);
    }

    public void add(final T element) {
        add(elements.size(), element);
    }

    public int size() {
        return elements.size();
    }

    public Coord2D deltaMousePosition() {
        return lastMousePos.displace(onClickPos.scale(-1));
    }

    public int getMovingIndex() {
        return movingIndex;
    }

    public int getWouldBeIndex() {
        return wouldBeIndex;
    }

    public void setWouldBeIndex(final int wouldBeIndex) {
        this.wouldBeIndex = MathPlus.bounded(0, wouldBeIndex,
                elements.size() - 1);
    }
}
