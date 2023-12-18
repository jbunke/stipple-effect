package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.SEContext;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.util.HashSet;
import java.util.Set;

public final class BoxSelect extends ToolWithMode {
    private static final BoxSelect INSTANCE;

    private boolean drawing;
    private Coord2D pivotTP, endTP, topLeft, bottomRight;
    private GameImage overlay;

    static {
        INSTANCE = new BoxSelect();
    }

    private BoxSelect() {
        drawing = false;

        pivotTP = Constants.NO_VALID_TARGET;
        endTP = Constants.NO_VALID_TARGET;
        topLeft = Constants.NO_VALID_TARGET;
        bottomRight = Constants.NO_VALID_TARGET;

        overlay = GameImage.dummy();
    }

    public static BoxSelect get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Box Select";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (context.isTargetingPixelOnCanvas()) {
            drawing = true;

            final Coord2D tp = context.getTargetPixel();

            if (ToolWithMode.getMode() == Mode.SINGLE)
                context.deselect();

            pivotTP = tp;
            endTP = tp;
            topLeft = tp;
            bottomRight = tp;
        }

        overlay = GameImage.dummy();
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        if (drawing) {
            final Coord2D tp = context.getTargetPixel();

            if (tp.equals(endTP))
                return;

            endTP = tp;

            final Set<Coord2D> bounds = new HashSet<>(pivotTP.equals(endTP)
                    ? Set.of(pivotTP) : Set.of(pivotTP, endTP));
            topLeft = Selection.topLeft(bounds);
            bottomRight = Selection.bottomRight(bounds);

            final int w = bottomRight.x - topLeft.x,
                    h = bottomRight.y - topLeft.y;

            overlay = GraphicsUtils.drawOverlay(w, h,
                    (int) context.getRenderInfo().getZoomFactor(),
                    (x, y) -> true, Constants.BLACK, Constants.HIGHLIGHT_1,
                    true);
        }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (drawing) {
            drawing = false;
            me.markAsProcessed();

            if (pivotTP.equals(endTP))
                return;

            final Set<Coord2D> box = new HashSet<>();

            for (int x = topLeft.x; x < bottomRight.x; x++)
                for (int y = topLeft.y; y < bottomRight.y; y++)
                    box.add(new Coord2D(x, y));

            context.editSelection(box);
        }
    }

    public boolean isDrawing() {
        return drawing;
    }

    public Coord2D getTopLeft() {
        return topLeft;
    }

    public GameImage getOverlay() {
        return overlay;
    }
}
