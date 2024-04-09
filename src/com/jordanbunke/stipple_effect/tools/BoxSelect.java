package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.SECursor;
import com.jordanbunke.stipple_effect.visual.color.SEColors;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public final class BoxSelect extends ToolWithMode implements SnappableTool {
    private static final BoxSelect INSTANCE;

    private boolean drawing, snap;
    private Coord2D pivotTP, endTP, topLeft, bottomRight;
    private GameImage toolContentPreview;

    private int lastOffset;

    static {
        INSTANCE = new BoxSelect();
    }

    private BoxSelect() {
        drawing = false;
        snap = false;

        lastOffset = 0;

        pivotTP = Constants.NO_VALID_TARGET;
        endTP = Constants.NO_VALID_TARGET;
        topLeft = Constants.NO_VALID_TARGET;
        bottomRight = Constants.NO_VALID_TARGET;

        toolContentPreview = GameImage.dummy();
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
                context, context.getTargetPixel(), true);

        // this condition instead of constants.isTargetingPixelOnCanvas()
        // so that dragging can start off canvas
        if (!tp.equals(Constants.NO_VALID_TARGET)) {
            drawing = true;

            lastOffset = StippleEffect.get().getTimerCounter();

            if (ToolWithMode.getMode() == Mode.SINGLE)
                context.deselect(false);

            final Set<Coord2D> bounds = new HashSet<>(Set.of(tp));

            pivotTP = tp;
            endTP = tp;
            topLeft = tp;
            bottomRight = SelectionUtils.bottomRight(bounds);

            updateToolContentPreview(context);
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        if (drawing) {
            final Coord2D tp = context.getTargetPixel();

            if (tp.equals(endTP) || tp.equals(Constants.NO_VALID_TARGET)) {
                final int offset = StippleEffect.get().getTimerCounter();

                if (offset != lastOffset)
                    updateToolContentPreview(context);

                lastOffset = offset;
                return;
            }

            endTP = tp;

            final Set<Coord2D> bounds = new HashSet<>(pivotTP.equals(endTP)
                    ? Set.of(pivotTP) : Set.of(pivotTP, endTP));
            topLeft = snapToClosestGridPosition(context,
                    SelectionUtils.topLeft(bounds), true);
            bottomRight = snapToClosestGridPosition(context,
                    SelectionUtils.bottomRight(bounds), false);

            bounds.clear();
            for (int x = topLeft.x; x < bottomRight.x; x++)
                for (int y = topLeft.y; y < bottomRight.y; y++)
                    bounds.add(new Coord2D(x, y));

            updateToolContentPreview(context);
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
            final SEContext context, final Coord2D pos, final boolean in
    ) {
        if (!(isSnap() && canSnap(context)))
            return pos;

        final int pgX = Settings.getPixelGridXPixels(),
                pgY = Settings.getPixelGridYPixels(),
                x = ((int) (pos.x / (double) pgX)) +
                        (in ? 0 : (pos.x % pgX == 0 ? 0 : 1)),
                y = ((int) (pos.y / (double) pgY)) +
                        (in ? 0 : (pos.y % pgY == 0 ? 0 : 1));

        return new Coord2D(x * pgX, y * pgY);
    }

    private boolean canSnap(final SEContext context) {
        return context.renderInfo.isPixelGridOn() &&
                context.couldRenderPixelGrid();
    }

    private void updateToolContentPreview(final SEContext context) {
        final int w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();

        toolContentPreview = new GameImage(w, h);

        final boolean reverse = StippleEffect.get().isTimerToggle();
        final Color a = reverse ? SEColors.white() : SEColors.black(),
                b = reverse ? SEColors.black() : SEColors.white();
        final int offset = StippleEffect.get().getTimerCounter(), altPx = 4,
                distanceX = bottomRight.x - topLeft.x,
                distanceY = bottomRight.y - topLeft.y,
                realOffsetX = Math.min(offset, distanceX),
                realOffsetY = Math.min(offset, distanceY);

        if (realOffsetX > 0) {
            toolContentPreview.fillRectangle(b,
                    topLeft.x, topLeft.y, realOffsetX, 1);
            toolContentPreview.fillRectangle(a,
                    topLeft.x, bottomRight.y - 1, realOffsetX, 1);

            toolContentPreview.fillRectangle(b,
                    topLeft.x, topLeft.y, 1, realOffsetY);
            toolContentPreview.fillRectangle(a,
                    bottomRight.x - 1, topLeft.y, 1, realOffsetY);
        }

        for (int x = offset; x < distanceX; x += altPx) {
            final boolean isA = (x / altPx) % 2 == 0;

            final int tickLength = Math.min(altPx, distanceX - x);

            toolContentPreview.fillRectangle(isA ? a : b,
                    topLeft.x + x, topLeft.y, tickLength, 1);
            toolContentPreview.fillRectangle(isA ? b : a,
                    topLeft.x + x, bottomRight.y - 1, tickLength, 1);
        }

        for (int y = offset; y < distanceY; y += altPx) {
            final boolean isA = (y / altPx) % 2 == 0;

            final int tickLength = Math.min(altPx, distanceY - y);

            toolContentPreview.fillRectangle(isA ? a : b,
                    topLeft.x, topLeft.y + y, 1, tickLength);
            toolContentPreview.fillRectangle(isA ? b : a,
                    bottomRight.x - 1, topLeft.y + y, 1, tickLength);
        }
    }

    @Override
    public void setSnap(boolean snap) {
        this.snap = snap;
    }

    @Override
    public boolean isSnap() {
        return snap;
    }

    @Override
    public boolean hasToolContentPreview() {
        return drawing;
    }

    @Override
    public boolean previewScopeIsGlobal() {
        return true;
    }

    @Override
    public GameImage getToolContentPreview() {
        return toolContentPreview;
    }
}
