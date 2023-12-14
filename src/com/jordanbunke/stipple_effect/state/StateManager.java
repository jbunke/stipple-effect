package com.jordanbunke.stipple_effect.state;

import java.util.ArrayList;
import java.util.List;

public class StateManager {
    private int index;
    private final List<ImageState> states;

    public StateManager(final ImageState initialState) {
        this.states = new ArrayList<>(List.of(initialState));
        index = 0;
    }

    public boolean canUndo() {
        return index > 0;
    }

    public void undo(final boolean redraw) {
        if (canUndo()) {
            index--;

            if (redraw)
                ActionType.MAJOR.consequence();
        }
    }

    public void undoToCheckpoint() {
        final int was = index;

        do {
            undo(false);
        } while (canUndo() && !getState().isCheckpoint());

        if (was != index)
            ActionType.MAJOR.consequence();
    }

    public boolean canRedo() {
        return index + 1 < states.size();
    }

    public void redo(final boolean redraw) {
        if (canRedo()) {
            index++;

            if (redraw)
                ActionType.MAJOR.consequence();
        }
    }

    public void redoToCheckpoint() {
        final int was = index;

        do {
            redo(false);
        } while (canRedo() && !getState().isCheckpoint());

        if (was != index)
            ActionType.MAJOR.consequence();
    }

    public void performAction(final ImageState resultantState, final ActionType actionType) {
        // clear REDO stack
        while (states.size() > index + 1)
            states.remove(states.size() - 1);

        states.add(resultantState);
        index = states.size() - 1;

        actionType.consequence();
    }

    public ImageState getState() {
        return states.get(index);
    }
}
