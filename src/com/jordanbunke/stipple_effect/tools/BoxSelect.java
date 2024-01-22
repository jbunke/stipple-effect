package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.SECursor;

import java.util.HashSet;
import java.util.Set;

public final class BoxSelect extends ToolWithMode implements OverlayTool {
    private static final BoxSelect INSTANCE;

    private boolean drawing;
    private Coord2D pivotTP, endTP, topLeft, bottomRight;
    private GameImage selectionOverlay;

    static {
        INSTANCE = new BoxSelect();
    }

    private BoxSelect() {
        drawing = false;

        pivotTP = Constants.NO_VALID_TARGET;
        endTP = Constants.NO_VALID_TARGET;
        topLeft = Constants.NO_VALID_TARGET;
        bottomRight = Constants.NO_VALID_TARGET;

        selectionOverlay = GameImage.dummy();
    }

    public static BoxSelect get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Box Select";
    }

    @Override
    public String getCursorCode() {
        return drawing ? SECursor.RETICLE : super.getCursorCode();
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        final Coord2D tp = context.getTargetPixel();

        // this condition instead of constants.isTargetingPixelOnCanvas()
        // so that dragging can start off canvas
        if (!tp.equals(Constants.NO_VALID_TARGET)) {
            drawing = true;

            if (ToolWithMode.getMode() == Mode.SINGLE)
                context.deselect(false);

            pivotTP = tp;
            endTP = tp;
            topLeft = tp;
            bottomRight = tp;

            selectionOverlay = GraphicsUtils.drawSelectionOverlay(
                    context.renderInfo.getZoomFactor(),
                    new HashSet<>(Set.of(tp)), false, false);
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        if (drawing) {
            final Coord2D tp = context.getTargetPixel();

            if (tp.equals(endTP) || tp.equals(Constants.NO_VALID_TARGET))
                return;

            endTP = tp;

            final Set<Coord2D> bounds = new HashSet<>(pivotTP.equals(endTP)
                    ? Set.of(pivotTP) : Set.of(pivotTP, endTP));
            topLeft = SelectionUtils.topLeft(bounds);
            bottomRight = SelectionUtils.bottomRight(bounds);

            for (int x = topLeft.x; x < bottomRight.x; x++)
                for (int y = topLeft.y; y < bottomRight.y; y++)
                    bounds.add(new Coord2D(x, y));

            selectionOverlay = GraphicsUtils.drawSelectionOverlay(
                    context.renderInfo.getZoomFactor(), bounds, false, false);
        }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (drawing) {
            drawing = false;
            me.markAsProcessed();

            if (pivotTP.equals(endTP))
                return;

            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();

            final Set<Coord2D> box = new HashSet<>();

            for (int x = topLeft.x; x < bottomRight.x; x++)
                for (int y = topLeft.y; y < bottomRight.y; y++)
                    if (x >= 0 && x < w && y >= 0 && y < h)
                        box.add(new Coord2D(x, y));

            context.editSelection(box, true);
        }
    }

    @Override
    public Coord2D getTopLeft() {
        return topLeft;
    }

    @Override
    public GameImage getSelectionOverlay() {
        return selectionOverlay;
    }

    @Override
    public boolean isDrawing() {
        return drawing;
    }
}
