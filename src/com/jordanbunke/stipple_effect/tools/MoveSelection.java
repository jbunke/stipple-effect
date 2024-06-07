package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionMode;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.state.Operation;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

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
    boolean canBeMoved(final SEContext context) {
        return context.getState().hasSelection() &&
                context.getState().getSelectionMode() == SelectionMode.BOUNDS;
    }

    @Override
    Set<Coord2D> move(
            final SEContext context, final Coord2D displacement
    ) {
        final Set<Coord2D> selection = context.getState().getSelection();

        return selection.parallelStream().map(s ->
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
    GameImage updateToolContentPreview(
            final SEContext context, final Set<Coord2D> transformation
    ) {
        final int w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();

        final GameImage toolContentPreview = new GameImage(w, h);

        final Set<Coord2D> frontier = transformation.stream().filter(
                p -> p.x >= 0 && p.x < w && p.y >= 0 && p.y < h
        ).filter(pixel -> {
            final boolean
                    left = !transformation.contains(pixel.displace(-1, 0)),
                    right = !transformation.contains(pixel.displace(1, 0)),
                    top = !transformation.contains(pixel.displace(0, -1)),
                    bottom = !transformation.contains(pixel.displace(0, 1)),
                    tl = !transformation.contains(pixel.displace(-1, -1)),
                    tr = !transformation.contains(pixel.displace(1, -1)),
                    bl = !transformation.contains(pixel.displace(-1, 1)),
                    br = !transformation.contains(pixel.displace(1, 1));

            return left || right || top || bottom || tl || tr || bl || br;
        }).collect(Collectors.toSet());

        transformation.forEach(p -> toolContentPreview.dot(
                Settings.getTheme().highlightOverlay, p.x, p.y));
        frontier.forEach(p -> toolContentPreview.dot(
                Settings.getTheme().highlightOutline, p.x, p.y));

        return toolContentPreview;
    }

    @Override
    public boolean previewScopeIsGlobal() {
        return true;
    }
}
