package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.selection.SelectionMode;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.state.Operation;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public final class MoveSelection extends MoverTool<Selection> {
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
    boolean canBeMoved(final SEContext context) {
        return context.getState().hasSelection() &&
                context.getState().getSelectionMode() == SelectionMode.BOUNDS;
    }

    @Override
    Selection move(
            final SEContext context, final Coord2D displacement
    ) {
        return context.getState().getSelection().displace(displacement);
    }

    @Override
    Selection stretch(
            final SEContext context, final Set<Coord2D> initial,
            final Coord2D change, final Direction direction
    ) {
        // TODO
        return Selection.fromSet(SelectionUtils.stretchedPixels(initial, change, direction));
    }

    @Override
    Selection rotate(
            final SEContext context, final Set<Coord2D> initial,
            final double deltaR, final Coord2D pivot, final boolean[] offset
    ) {
        // TODO
        return Selection.fromSet(SelectionUtils.rotatedPixels(initial, deltaR, pivot, offset));
    }

    @Override
    void applyTransformation(
            final SEContext context, final Selection selection,
            final boolean transform
    ) {
        final ProjectState result = context.getState()
                .changeSelectionBounds(selection);
        context.getStateManager().performAction(result, transform
                ? Operation.TRANSFORM_SELECTION_BOUNDS
                : Operation.MOVE_SELECTION_BOUNDS);
    }

    @Override
    GameImage updateToolContentPreview(
            final SEContext context, final Selection transformation
    ) {
        final int w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();

        final GameImage toolContentPreview = new GameImage(w, h);

        final Set<Coord2D> frontier = new HashSet<>();

        transformation.pixelAlgorithm(w, h, (x, y) -> {
            final boolean
                    left = !transformation.selected(x - 1, y),
                    right = !transformation.selected(x + 1, y),
                    top = !transformation.selected(x, y - 1),
                    bottom = !transformation.selected(x, y + 1),
                    tl = !transformation.selected(x - 1, y - 1),
                    tr = !transformation.selected(x + 1, y - 1),
                    bl = !transformation.selected(x - 1, y + 1),
                    br = !transformation.selected(x + 1, y + 1);

            if (left || right || top || bottom || tl || tr || bl || br)
                frontier.add(new Coord2D(x, y));
        });

        final Theme t = Settings.getTheme();
        final Color fill = t.highlightOverlay, outline = t.highlightOutline;

        transformation.pixelAlgorithm(w, h,
                (x, y) -> toolContentPreview.dot(fill, x, y));
        frontier.forEach(p -> toolContentPreview.dot(outline, p.x, p.y));

        return toolContentPreview.submit();
    }

    @Override
    public boolean previewScopeIsGlobal() {
        return true;
    }
}
