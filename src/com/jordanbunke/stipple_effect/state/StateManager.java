package com.jordanbunke.stipple_effect.state;

import java.util.ArrayList;
import java.util.List;

public class StateManager<T> {
    private int index;
    private final List<T> states;

    public StateManager(final T initialState) {
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

    public boolean canRedo() {
        return index + 1 < states.size();
    }

    public void redo() {
        if (canRedo())
            index++;
    }

    public void performAction(final T resultantState) {
        // clear REDO stack
        while (states.size() > index + 1)
            states.remove(states.size() - 1);

        states.add(resultantState);
        index = states.size() - 1;
    }

    public T getState() {
        return states.get(index);
    }
}
