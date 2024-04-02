package com.jordanbunke.stipple_effect.state;

import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

import java.util.*;

public class StateManager {
    private int index;
    private final List<ProjectState> states;

    public StateManager(final ProjectState initialState) {
        this.states = new ArrayList<>(List.of(initialState));
        index = 0;
    }

    public boolean canUndo() {
        return index > 0;
    }

    public void undo(final boolean redraw) {
        if (canUndo()) {
            final ProjectState was = getState();
            index--;

            if (redraw)
                updateStateMetadataAndAssets(was);
        } else
            StatusUpdates.stateChangeFailed(true);
    }

    public void undoToCheckpoint() {
        final int was = index;

        do {
            undo(false);
        } while (canUndo() && !getState().isCheckpoint());

        if (was != index)
            updateStateMetadataAndAssets(was);
    }

    public boolean canRedo() {
        return index + 1 < states.size();
    }

    public void redo(final boolean redraw) {
        if (canRedo()) {
            index++;

            if (redraw)
                updateStateMetadataAndAssets(getState());
        } else
            StatusUpdates.stateChangeFailed(false);
    }

    public void redoToCheckpoint() {
        final int was = index;

        do {
            redo(false);
        } while (canRedo() && !getState().isCheckpoint());

        if (was != index)
            updateStateMetadataAndAssets(was);
    }

    public void performAction(
            final ProjectState resultantState, final Operation operation
    ) {
        resultantState.tag(operation);

        // clear REDO stack
        while (states.size() > index + 1)
            states.remove(states.size() - 1);

        manageMemory();

        // add to state stack and set as active state
        states.add(resultantState);
        index = states.size() - 1;

        updateStateMetadataAndAssets(resultantState);
    }

    private void manageMemory() {
        final long BYTES_IN_KB = 0x400L; // 1024
        final int GC_EVERY_X_DUMPS = 10;

        final Runtime r = Runtime.getRuntime();
        final long mem = r.freeMemory();

        if (mem < Constants.DUMP_STATES_MEM_THRESHOLD &&
                canDumpState()) {
            final long entryMem = mem / BYTES_IN_KB;
            int dumped = 0;

            while (canDumpState() &&
                    r.freeMemory() < Constants.DUMP_STATES_MEM_THRESHOLD *
                            Constants.DUMP_STATES_CUSHION_FACTOR) {
                states.remove(0);
                dumped++;

                if (dumped % GC_EVERY_X_DUMPS == 0)
                    System.gc();
            }

            System.gc();
            final long exitMem = r.freeMemory() / BYTES_IN_KB;

            StatusUpdates.dumpedStates(dumped, exitMem - entryMem);
        }
    }

    private boolean canDumpState() {
        return states.size() > Constants.MIN_NUM_STATES && index > 0;
    }

    private void updateStateMetadataAndAssets(final int was) {
        final int inc = (int) Math.signum(index - was);
        boolean edited = false,
                redrawSelectionOverlay = false,
                redrawCanvasAuxiliaries = false;

        final Set<ActionType> triggeredActions = new HashSet<>();

        for (int i = was; inc > 0 ? i <= index : i >= index;
             i += inc) {
            final ProjectState s = states.get(i);

            edited |= s.getOperation().constitutesEdit();
            redrawSelectionOverlay |= s.getOperation()
                    .triggersSelectionOverlayRedraw();
            redrawCanvasAuxiliaries |= s.getOperation()
                    .triggersCanvasAuxiliaryRedraw();

            triggeredActions.add(s.getOperation().getActionType());
        }

        final SEContext c = StippleEffect.get().getContext();

        if (edited)
            c.projectInfo.markAsEdited();

        if (redrawSelectionOverlay)
            c.redrawSelectionOverlay();

        if (redrawCanvasAuxiliaries)
            c.redrawCanvasAuxiliaries();

        for (ActionType actionType : triggeredActions)
            actionType.consequence();
    }

    private void updateStateMetadataAndAssets(final ProjectState state) {
        markAsEditedIfEdited(state);
        redrawSelectionOverlayIfTriggered(state);
        redrawCanvasAuxiliariesIfTriggered(state);

        if (state.isCheckpoint())
            state.getOperation().getActionType().consequence();
    }

    private void markAsEditedIfEdited(final ProjectState state) {
        if (state.getOperation().constitutesEdit())
            StippleEffect.get().getContext().projectInfo.markAsEdited();
    }

    private void redrawSelectionOverlayIfTriggered(final ProjectState state) {
        if (state.getOperation().triggersSelectionOverlayRedraw())
            StippleEffect.get().getContext().redrawSelectionOverlay();
    }

    private void redrawCanvasAuxiliariesIfTriggered(final ProjectState state) {
        if (state.getOperation().triggersCanvasAuxiliaryRedraw())
            StippleEffect.get().getContext().redrawCanvasAuxiliaries();
    }

    public ProjectState getState() {
        return states.get(index);
    }
}
