package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionMode;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.state.Operation;
import com.jordanbunke.stipple_effect.state.ProjectState;

import java.util.Set;
import java.util.stream.Collectors;

public final class MoveSelection extends MoverTool<Set<Coord2D>> {
    private static final MoveSelection INSTANCE;

    static {
        INSTANCE = new MoveSelection();
    }

    public static MoveSelection get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Move selection";
    }

    @Override
    boolean canBeMoved(SEContext context) {
        return context.getState().hasSelection() &&
                context.getState().getSelectionMode() == SelectionMode.BOUNDS;
    }

    @Override
    Set<Coord2D> move(
            final SEContext context, final Coord2D displacement
    ) {
        final Set<Coord2D> selection = context.getState().getSelection();

        return selection.stream().map(s ->
                s.displace(displacement)).collect(Collectors.toSet());
    }

    @Override
    Set<Coord2D> stretch(
            final SEContext context, final Set<Coord2D> initial,
            final Coord2D change, final Direction direction
    ) {
        return SelectionUtils.stretchedPixels(initial, change, direction);
    }

    @Override
    Set<Coord2D> rotate(
            final SEContext context, final Set<Coord2D> initial,
            final double deltaR, final Coord2D pivot, final boolean[] offset
    ) {
        return SelectionUtils.rotatedPixels(initial, deltaR, pivot, offset);
    }

    @Override
    void applyTransformation(
            final SEContext context, final Set<Coord2D> selection,
            final boolean transform
    ) {
        final ProjectState result = context.getState()
                .changeSelectionBounds(selection);
        context.getStateManager().performAction(result, transform
                ? Operation.TRANSFORM_SELECTION_BOUNDS
                : Operation.MOVE_SELECTION_BOUNDS);
    }

    @Override
    Runnable getMouseUpConsequence(final SEContext context) {
        return () -> context.getState().markAsCheckpoint(true);
    }
}
