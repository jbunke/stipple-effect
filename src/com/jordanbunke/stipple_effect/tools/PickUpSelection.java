package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionContents;
import com.jordanbunke.stipple_effect.selection.SelectionMode;
import com.jordanbunke.stipple_effect.state.Operation;
import com.jordanbunke.stipple_effect.state.ProjectState;

import java.util.Set;

public final class PickUpSelection extends MoverTool<SelectionContents> {
    private static final PickUpSelection INSTANCE;

    static {
        INSTANCE = new PickUpSelection();
    }

    public static PickUpSelection get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Pick up selection";
    }

    @Override
    boolean canBeMoved(SEContext context) {
        return context.getState().hasSelection() &&
                context.getState().getSelectionMode() == SelectionMode.CONTENTS;
    }

    @Override
    SelectionContents move(
            final SEContext context, final Coord2D displacement
    ) {
        return context.getState().getSelectionContents()
                .returnDisplaced(displacement);
    }

    @Override
    SelectionContents stretch(
            final SEContext context, final Set<Coord2D> initial,
            final Coord2D change, final Direction direction
    ) {
        return context.getState().getSelectionContents()
                .returnStretched(initial, change, direction);
    }

    @Override
    SelectionContents rotate(
            final SEContext context, final Set<Coord2D> initial,
            final double deltaR, final Coord2D pivot, final boolean[] offset
    ) {
        return context.getState().getSelectionContents()
                .returnRotated(initial, deltaR, pivot, offset);
    }

    @Override
    void applyTransformation(
            final SEContext context, final SelectionContents transformation,
            final boolean transform
    ) {
        final ProjectState result = context.getState()
                .changeSelectionContents(transformation);
        context.getStateManager().performAction(result, transform
                ? Operation.TRANSFORM_SELECTION_CONTENTS
                : Operation.MOVE_SELECTION_CONTENTS);
    }

    public void engage(final SEContext context) {
        context.raiseSelectionToContents(false);
    }

    public void disengage(final SEContext context) {
        context.dropContentsToLayer(false, false);
    }

    @Override
    Runnable getMouseUpConsequence(final SEContext context) {
        return context::resetContentOriginal;
    }
}
