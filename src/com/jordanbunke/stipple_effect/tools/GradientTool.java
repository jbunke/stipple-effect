package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.ColorMath;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Geometry;

import java.awt.*;
import java.util.*;
import java.util.List;

public final class GradientTool extends ToolWithBreadth
        implements SnappableTool, ToggleModeTool {
    private static final GradientTool INSTANCE;

    private boolean drawing, linear, snap, backwards;
    private GameImage toolContentPreview;
    private Coord2D anchor;
    private List<Set<Coord2D>> gradientStages;

    static {
        INSTANCE = new GradientTool();
    }

    private GradientTool() {
        drawing = false;
        linear = false;
        snap = false;
        backwards = false;

        toolContentPreview = GameImage.dummy();

        anchor = Constants.NO_VALID_TARGET;
        gradientStages = new ArrayList<>();
    }

    public static GradientTool get() {
        return INSTANCE;
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        final Coord2D tp = context.getTargetPixel();

        if (!tp.equals(Constants.NO_VALID_TARGET) &&
                me.button != GameMouseEvent.Button.MIDDLE) {
            drawing = true;
            backwards = me.button == GameMouseEvent.Button.RIGHT;

            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();
            toolContentPreview = new GameImage(w, h);

            reset();
            anchor = tp;
            gradientStages = new ArrayList<>();
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();

        if (drawing && !tp.equals(Constants.NO_VALID_TARGET)) {
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();
            final Set<Coord2D> selection = context.getState().getSelection();

            if (tp.equals(getLastTP()))
                return;

            toolContentPreview = new GameImage(w, h);

            if (linear)
                updateLinearMode(selection, tp, w, h);
            else
                updateBrushMode(selection, tp, w, h);

            updateLast(context);
        }
    }

    private void updateBrushMode(
            final Set<Coord2D> selection, final Coord2D tp,
            final int w, final int h
    ) {
        final Set<Coord2D> gradientStage = new HashSet<>();

        populateAround(gradientStage, tp, selection);
        fillLineSpace(getLastTP(), tp, (x, y) -> populateAround(
                gradientStage, getLastTP().displace(x, y), selection));

        gradientStages.add(gradientStage);

        drawGradientStages(w, h);
    }

    private void updateLinearMode(
            final Set<Coord2D> selection, final Coord2D tp,
            final int w, final int h
    ) {
        final Coord2D endpoint;

        if (isSnap()) {
            final double angle = Geometry.normalizeAngle(
                    Geometry.calculateAngleInRad(tp, anchor)),
                    distance = Coord2D.unitDistanceBetween(anchor, tp),
                    snapped = Geometry.snapAngle(angle, Constants._15_SNAP_INC);
            endpoint = Geometry.projectPoint(anchor, snapped, distance);
        } else
            endpoint = tp;

        drawLinearGradient(endpoint, selection, w, h);
    }

    private void populateAround(
            final Set<Coord2D> gradientStage, final Coord2D tp,
            final Set<Coord2D> selection
    ) {
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++) {
                final Coord2D b = new Coord2D(x + (tp.x - halfB),
                        y + (tp.y - halfB));

                if (!(selection.isEmpty() || selection.contains(b)))
                    continue;

                if (mask[x][y])
                    gradientStage.add(b);
            }
    }

    private void drawLinearGradient(
            final Coord2D endpoint, final Set<Coord2D> selection,
            final int w, final int h
    ) {
        final boolean hasSelection = !selection.isEmpty();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final Coord2D pos = new Coord2D(x, y);
                if (hasSelection && !selection.contains(pos))
                    continue;

                final double c = getC(pos, endpoint);
                toolContentPreview.setRGB(x, y, getColor(c).getRGB());
            }
        }
    }

    private double getC(final Coord2D pos, final Coord2D endpoint) {
        final int diffY = endpoint.y - anchor.y,
                diffX = endpoint.x - anchor.x,
                deltaY = pos.y - anchor.y,
                deltaX = pos.x - anchor.x;

        if (diffX == 0)
            return diffY == 0 ? 0d : deltaY / (double) diffY;
        else {
            // y = mx + b where m is slope and b is the y-intercept
            final double m1 = diffY / (double) diffX,
                    b1 = anchor.y - (m1 * anchor.x);

            if (m1 == 0d)
                return deltaX / (double) diffX;
            else {
                // y = m2x + b2 represents the perpendicular line measured from the target point
                final double m2 = -1d / m1,
                        b2 = pos.y - (m2 * pos.x);

                final double x = (b2 - b1) / (m1 - m2);
                return (x - anchor.x) / (double) diffX;
            }
        }
    }

    private void drawGradientStages(final int w, final int h) {
        final int size = gradientStages.size();

        for (int g = 0; g < size; g++) {
            final double c = size == 1 ? 0d : g / (double) (size - 1);
            final Color stageColor = getColor(c);

            gradientStages.get(g).stream().filter(
                    p -> p.x >= 0 && p.x < w && p.y >= 0 && p.y < h)
                    .forEach(p -> toolContentPreview.setRGB(
                            p.x, p.y, stageColor.getRGB()));
        }
    }

    private Color getColor(final double c) {
        final Color p = StippleEffect.get().getPrimary(),
                s = StippleEffect.get().getSecondary();

        return ColorMath.betweenColor(backwards ? 1 - c : c, p, s);
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (drawing) {
            drawing = false;

            context.paintOverImage(toolContentPreview);

            context.getState().markAsCheckpoint(true, context);
            me.markAsProcessed();
        }
    }

    @Override
    public boolean hasToolContentPreview() {
        return drawing;
    }

    @Override
    public GameImage getToolContentPreview() {
        return toolContentPreview;
    }

    @Override
    public void setMode(final boolean linear) {
        if (!drawing)
            this.linear = linear;
    }

    @Override
    public void setSnap(final boolean snap) {
        this.snap = linear && snap;
    }

    @Override
    public boolean isSnap() {
        return snap;
    }

    @Override
    public String getName() {
        return "Gradient Tool";
    }

    @Override
    public GameImage getOverlay() {
        return linear ? GameImage.dummy() : super.getOverlay();
    }

    @Override
    public String getBottomBarText() {
        return linear
                ? "Linear Gradient"
                : super.getBottomBarText().replace("Tool", "Brush");
    }
}
