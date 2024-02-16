package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class BrushSelect extends ToolWithBreadth implements OverlayTool {
    private static final BrushSelect INSTANCE;

    private boolean selecting;
    private Set<Coord2D> selection;
    private GameImage selectionOverlay;

    static {
        INSTANCE = new BrushSelect();
    }

    private BrushSelect() {
        selecting = false;
        selection = new HashSet<>();
        selectionOverlay = GameImage.dummy();
    }

    public static BrushSelect get() {
        return INSTANCE;
    }

    @Override
    public String getCursorCode() {
        return super.getCursorCode() + "_" +
                ToolWithMode.getMode().name().toLowerCase();
    }

    @Override
    public String getName() {
        return "Brush Select";
    }

    private void drawSelection(final SEContext context) {
        selectionOverlay = GraphicsUtils.drawSelectionOverlay(
                context.renderInfo.getZoomFactor(), selection, false, false);
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (!context.getTargetPixel().equals(Constants.NO_VALID_TARGET)) {
            selecting = true;
            selection = new HashSet<>();

            if (ToolWithMode.getMode() == ToolWithMode.Mode.SINGLE)
                context.deselect(false);

            reset();
            context.getState().markAsCheckpoint(false);
        }

        selectionOverlay = GameImage.dummy();
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();

        if (selecting && !tp.equals(Constants.NO_VALID_TARGET)) {
            if (getLastTP().equals(tp))
                return;

            populateAround(tp);

            fillLineSpace(getLastTP(), tp, (x, y) ->
                    populateAround(getLastTP().displace(x, y)));

            drawSelection(context);
            updateLast(context);
        }
    }

    private void populateAround(final Coord2D tp) {
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++) {
                final Coord2D b = new Coord2D(x + (tp.x - halfB),
                        y + (tp.y - halfB));

                if (mask[x][y])
                    selection.add(b);
            }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (selecting) {
            selecting = false;
            me.markAsProcessed();

            // filter selection by image bounds
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();

            final Set<Coord2D> bounded = selection.stream()
                    .filter(p -> p.x >= 0 && p.y >= 0 && p.x < w && p.y < h)
                    .collect(Collectors.toSet());

            context.editSelection(bounded, true);
        }
    }

    @Override
    public Coord2D getTopLeft() {
        return SelectionUtils.topLeft(selection);
    }

    @Override
    public GameImage getSelectionOverlay() {
        return selectionOverlay;
    }

    @Override
    public boolean isDrawing() {
        return selecting;
    }
}
