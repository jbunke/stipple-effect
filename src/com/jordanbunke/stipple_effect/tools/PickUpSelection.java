package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.selection.SelectionContents;
import com.jordanbunke.stipple_effect.selection.SelectionMode;
import com.jordanbunke.stipple_effect.state.Operation;
import com.jordanbunke.stipple_effect.state.ProjectState;

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
    boolean canBeMoved(final SEContext context) {
        return context.getState().hasSelection() &&
                context.getState().getSelectionMode() == SelectionMode.CONTENTS;
    }

    @Override
    SelectionContents move(
            final SEContext context, final Coord2D displacement
    ) {
        if (canBeMoved(context))
            return context.getState().getSelectionContents()
                    .returnDisplaced(displacement);

        return null;
    }

    @Override
    SelectionContents stretch(
            final SEContext context, final Selection initial,
            final Coord2D change, final Direction direction
    ) {
        if (canBeMoved(context))
            return context.getState().getSelectionContents()
                    .returnStretched(initial, change, direction);

        return null;
    }

    @Override
    SelectionContents rotate(
            final SEContext context, final Selection initial,
            final double deltaR, final Coord2D pivot, final boolean[] offset
    ) {
        if (canBeMoved(context))
            return context.getState().getSelectionContents()
                    .returnRotated(initial, deltaR, pivot, offset);

        return null;
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
    GameImage updateToolContentPreview(
            final SEContext context, final SelectionContents transformation
    ) {
        final int w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();

        if (transformation == null)
            return GameImage.dummy();

        return transformation.getContentForCanvas(w, h);
    }

    @Override
    Selection getTransformationSelection(final SelectionContents transformation) {
        return transformation.getSelection();
    }
}
