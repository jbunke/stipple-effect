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

    public void undo() {
        if (canUndo())
            index--;
    }

    public void undoToCheckpoint() {
        do {
            undo();
        } while (canUndo() && !getState().isCheckpoint());
    }

    public boolean canRedo() {
        return index + 1 < states.size();
    }

    public void redo() {
        if (canRedo())
            index++;
    }

    public void redoToCheckpoint() {
        do {
            redo();
        } while (canRedo() && !getState().isCheckpoint());
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
