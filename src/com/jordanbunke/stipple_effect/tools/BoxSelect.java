package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.SECursor;

import java.util.HashSet;
import java.util.Set;

public final class BoxSelect extends ToolWithMode implements OverlayTool, SnappableTool {
    private static final BoxSelect INSTANCE;

    private boolean drawing, snap;
    private Coord2D pivotTP, endTP, topLeft, bottomRight;
    private GameImage selectionOverlay;

    static {
        INSTANCE = new BoxSelect();
    }

    private BoxSelect() {
        drawing = false;
        snap = false;

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
        return drawing ? SECursor.RETICLE
                : convertNameToFilename() + "_" + getMode().name().toLowerCase();
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        final Coord2D tp = snapToClosestGridPosition(
                context, context.getTargetPixel());

        // this condition instead of constants.isTargetingPixelOnCanvas()
        // so that dragging can start off canvas
        if (!tp.equals(Constants.NO_VALID_TARGET)) {
            drawing = true;

            if (ToolWithMode.getMode() == Mode.SINGLE)
                context.deselect(false);

            final Set<Coord2D> bounds = new HashSet<>(Set.of(tp));

            pivotTP = tp;
            endTP = tp;
            topLeft = tp;
            bottomRight = SelectionUtils.bottomRight(bounds);

            selectionOverlay = GraphicsUtils.drawSelectionOverlay(
                    context.renderInfo.getZoomFactor(), bounds, false, false);
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
            topLeft = snapToClosestGridPosition(context,
                    SelectionUtils.topLeft(bounds));
            bottomRight = snapToClosestGridPosition(context,
                    SelectionUtils.bottomRight(bounds));

            bounds.clear();
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

    private Coord2D snapToClosestGridPosition(
            final SEContext context, final Coord2D pos
    ) {
        if (!(isSnap() && canSnap(context)))
            return pos;

        final int pgX = Settings.getPixelGridXPixels(),
                pgY = Settings.getPixelGridYPixels(),
                x = (int) Math.round(pos.x / (double) pgX),
                y = (int) Math.round(pos.y / (double) pgY);

        return new Coord2D(x * pgX, y * pgY);
    }

    private boolean canSnap(final SEContext context) {
        return context.renderInfo.isPixelGridOn() &&
                context.couldRenderPixelGrid();
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

    @Override
    public void setSnap(boolean snap) {
        this.snap = snap;
    }

    @Override
    public boolean isSnap() {
        return snap;
    }
}
