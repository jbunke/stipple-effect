package com.jordanbunke.stipple_effect.state;

import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class StateManager {
    private int index;
    private final List<ProjectState> states;
    private ActionType lastActionType;

    public StateManager(final ProjectState initialState) {
        this.states = new ArrayList<>(List.of(initialState));
        index = 0;
        lastActionType = ActionType.MAJOR;
    }

    public boolean canUndo() {
        return index > 0;
    }

    public void undo(final boolean redraw) {
        if (canUndo()) {
            index--;

            StippleEffect.get().getContext().getProjectInfo().markAsEdited();

            if (redraw) {
                ActionType.MAJOR.consequence();
                StippleEffect.get().getContext().redrawSelectionOverlay();
            }
        }
    }

    public void undoToCheckpoint() {
        final int was = index;

        do {
            undo(false);
        } while (canUndo() && !getState().isCheckpoint());

        if (was != index) {
            ActionType.MAJOR.consequence();
            StippleEffect.get().getContext().redrawSelectionOverlay();
        }
    }

    public boolean canRedo() {
        return index + 1 < states.size();
    }

    public void redo(final boolean redraw) {
        if (canRedo()) {
            index++;

            StippleEffect.get().getContext().getProjectInfo().markAsEdited();

            if (redraw) {
                ActionType.MAJOR.consequence();
                StippleEffect.get().getContext().redrawSelectionOverlay();
            }
        }
    }

    public void redoToCheckpoint() {
        final int was = index;

        do {
            redo(false);
        } while (canRedo() && !getState().isCheckpoint());

        if (was != index) {
            ActionType.MAJOR.consequence();
            StippleEffect.get().getContext().redrawSelectionOverlay();
        }
    }

    public void performAction(final ProjectState resultantState, final ActionType actionType) {
        lastActionType = actionType;

        // clear REDO stack
        while (states.size() > index + 1)
            states.remove(states.size() - 1);

        // trim to max state size allowed
        while (states.size() > Constants.MAX_NUM_STATES)
            states.remove(0);

        states.add(resultantState);
        index = states.size() - 1;

        StippleEffect.get().getContext().getProjectInfo().markAsEdited();

        if (resultantState.isCheckpoint())
            actionType.consequence();
    }

    public void processLastConsequence() {
        lastActionType.consequence();
    }

    public ProjectState getState() {
        return states.get(index);
    }
}
