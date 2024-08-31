package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.GatewayMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.EnumUtils;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.math.Geometry;
import com.jordanbunke.stipple_effect.utility.math.LineSegment;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.SECursor;
import com.jordanbunke.stipple_effect.visual.menu_elements.Dropdown;
import com.jordanbunke.stipple_effect.visual.menu_elements.IncrementalRangeElements;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;

import java.awt.*;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public sealed abstract class ToolWithBreadth extends ToolThatDraws
        permits AbstractBrush, Eraser, BrushSelect, GradientTool, GeometryTool {
    private static final double ANGLE_INC = Constants.CIRCLE / 144d;

    private int breadth;
    private BrushShape brushShape;
    private double angle;
    private GameImage overlay;

    // formatting only
    private int afterBreadthTextX;

    public enum BrushShape {
        CIRCLE, SQUARE, LINE;

        public boolean hasAngle() {
            return this == LINE;
        }

        public boolean gapProne() {
            return this == LINE;
        }
    }

    ToolWithBreadth() {
        breadth = Settings.getDefaultToolBreadth();
        brushShape = BrushShape.CIRCLE;
        angle = 0d;

        afterBreadthTextX = 0;
    }

    public static Stream<ToolWithBreadth> streamAll() {
        return Arrays.stream(Tool.getAll())
                .filter(t -> t instanceof ToolWithBreadth)
                .map(t -> (ToolWithBreadth) t);
    }

    public static void resetAll() {
        streamAll().forEach(t -> {
            t.setBreadth(Settings.getDefaultToolBreadth(), false, false);
            t.setAngle(0d, false, false);
            t.setBrushShape(BrushShape.CIRCLE, false, false);

            t.drawOverlay();
        });
    }

    private static void propagateBreadth(final ToolWithBreadth caller) {
        streamAll().filter(t -> !t.equals(caller)).forEach(t ->
                t.setBreadth(caller.breadth, true, false));
    }

    private static void propagateBrushShape(final ToolWithBreadth caller) {
        streamAll().filter(t -> !t.equals(caller)).forEach(t ->
                t.setBrushShape(caller.brushShape, true, false));
    }

    private static void propagateAngle(final ToolWithBreadth caller) {
        streamAll().filter(t -> !t.equals(caller)).forEach(t ->
                t.setAngle(caller.angle, true, false));
    }

    public static void redrawToolOverlays() {
        streamAll().forEach(ToolWithBreadth::drawOverlay);
    }

    public void drawOverlay() {
        final boolean[][] mask = breadthMask();
        final Color outside = SEColors.white(), inside = SEColors.black();

        this.overlay = GraphicsUtils.drawOverlay(mask.length, mask[0].length,
                StippleEffect.get().getContext().renderInfo.getZoomFactor(),
                mask, inside, outside);
    }

    public boolean[][] breadthMask() {
        final int b = getBreadth(),
                remainder = b % 2, halfB = breadthOffset();

        final boolean[][] mask = new boolean[halfB * 2][halfB * 2];
        final boolean odd = remainder == 1;

        switch (brushShape) {
            case CIRCLE -> {
                // 3 is a special case
                final double threshold = odd ? (b == 3 ? 1. : b / 2.) : (double) b;

                for (int x = 0; x < halfB * 2; x++)
                    for (int y = 0; y < halfB * 2; y++) {
                        final double distance = odd
                                ? Coord2D.unitDistanceBetween(new Coord2D(x, y),
                                new Coord2D(halfB, halfB))
                                : Coord2D.unitDistanceBetween(
                                new Coord2D((2 * x) + 1, (2 * y) + 1),
                                new Coord2D(halfB * 2, halfB * 2));

                        if (distance <= threshold)
                            mask[x][y] = true;
                    }
            }
            case SQUARE -> {
                final int offset = (halfB * 2) - b;

                for (int x = 0; x < b; x++)
                    for (int y = 0; y < b; y++)
                        mask[offset + x][offset + y] = true;
            }
            case LINE -> {
                final double complement =
                        angle + (angle < Math.PI ? Math.PI : -Math.PI);
                final Coord2D middle = new Coord2D(halfB, halfB),
                        from = Geometry.projectPoint(middle, angle, b / 2d),
                        to = Geometry.projectPoint(middle, complement, b / 2d);

                final LineSegment line = new LineSegment(from, to);
                final int distance = (int) Math.round(line.distance()),
                        max = (halfB * 2) - 1;

                for (int i = 0; i <= distance; i++) {
                    final Coord2D projected = line.pointAlongLineD(i);

                    if (projected.x < 0 || projected.y < 0 ||
                            projected.x > max || projected.y > max)
                        continue;

                    mask[projected.x][projected.y] = true;
                }
            }
        }

        return mask;
    }

    public int breadthOffset() {
        final int b = getBreadth();
        return (b / 2) + (b % 2);
    }

    @Override
    public String getCursorCode() {
        return SECursor.RETICLE;
    }

    @Override
    public String getBottomBarText() {
        return getName() + " (" + getBreadth() + " px)";
    }

    public int getBreadth() {
        return breadth;
    }

    public GameImage getOverlay() {
        return overlay;
    }

    public void setBreadth(
            final int breadth, final boolean redraw, final boolean ownCaller
    ) {
        this.breadth = MathPlus.bounded(
                Constants.MIN_BREADTH, breadth, Constants.MAX_BREADTH);

        if (redraw) {
            drawOverlay();

            if (ownCaller && Settings.isPropagateBreadth())
                propagateBreadth(this);
        }
    }

    public void setBreadth(final int breadth) {
        setBreadth(breadth, true, true);
    }

    public void setBrushShape(
            final BrushShape brushShape, final boolean redraw,
            final boolean ownCaller
    ) {
        this.brushShape = brushShape;

        if (redraw) {
            drawOverlay();

            if (ownCaller && Settings.isPropagateBreadth())
                propagateBrushShape(this);
        }
    }

    public void setBrushShape(final BrushShape brushShape) {
        setBrushShape(brushShape, true, true);
    }

    public void setAngle(
            final double angle, final boolean redraw, final boolean ownCaller
    ) {
        this.angle = MathPlus.bounded(0d, angle, Math.PI);

        if (redraw) {
            drawOverlay();

            if (ownCaller && Settings.isPropagateBreadth())
                propagateAngle(this);
        }
    }

    public void setAngle(final double angle) {
        setAngle(angle, true, true);
    }

    private void decreaseAngle() {
        setAngle(angle - ANGLE_INC);
    }

    private void increaseAngle() {
        setAngle(angle + ANGLE_INC);
    }

    public void increaseBreadth() {
        setBreadth(getBreadth() + 1);
    }

    public void decreaseBreadth() {
        setBreadth(getBreadth() - 1);
    }

    @Override
    public boolean hasToolOptionsBar() {
        return true;
    }

    @Override
    public MenuElementGrouping buildToolOptionsBar() {
        // breadth label
        final TextLabel breadthLabel = TextLabel.make(
                getFirstOptionLabelPosition(), "Breadth");

        // breadth content
        final int ARTICULATIONS = 40,
                SLIDER_MULT = (int) Math.pow(ARTICULATIONS, 3) /
                        Constants.MAX_BREADTH;
        final IncrementalRangeElements<Integer> breadth =
                IncrementalRangeElements.makeForInt(breadthLabel,
                        Layout.optionsBarButtonY(), Layout.optionsBarTextY(),
                        1, Constants.MIN_BREADTH, Constants.MAX_BREADTH,
                        this::setBreadth, this::getBreadth,
                        b -> (int) Math.cbrt(b * SLIDER_MULT),
                        sv -> ((int) Math.round(
                                Math.pow(sv, 3))) / SLIDER_MULT,
                        b -> b + " px", Constants.MAX_BREADTH + " px");

        // shape dropdown
        final Dropdown shapeDropdown = Dropdown.forToolOptionsBar(
                Layout.optionsBarNextElementX(breadth.value, true),
                EnumUtils.stream(BrushShape.class)
                        .map(EnumUtils::formattedName)
                        .toArray(String[]::new),
                EnumUtils.stream(BrushShape.class)
                        .map(s -> (Runnable) () -> setBrushShape(s))
                        .toArray(Runnable[]::new),
                brushShape::ordinal);

        // angle label
        final TextLabel angleLabel = TextLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(shapeDropdown, true),
                Layout.optionsBarTextY()), "Angle");

        final IncrementalRangeElements<Double> angle =
                IncrementalRangeElements.makeForDouble(angleLabel,
                        Layout.optionsBarButtonY(), Layout.optionsBarTextY(),
                        this::decreaseAngle, this::increaseAngle,
                        0d, Math.PI, this::setAngle, () -> this.angle,
                        Geometry::radToDegreesRounded, Geometry::degreesToRad,
                        rad -> Geometry.radToDegreesRounded(rad) + " deg",
                        "XXX deg");

        afterBreadthTextX = Layout.optionsBarNextElementX(angle.value, true);

        final GatewayMenuElement angleLogic =
                new GatewayMenuElement(new MenuElementGrouping(
                        angleLabel, angle.decButton, angle.incButton,
                        angle.slider, angle.value), () -> brushShape.hasAngle());

        return new MenuElementGrouping(super.buildToolOptionsBar(),
                breadthLabel, breadth.decButton, breadth.incButton,
                breadth.slider, breadth.value, shapeDropdown, angleLogic);
    }

    @Override
    int getAfterBreadthTextX() {
        return afterBreadthTextX;
    }

    void fillGaps(
            final int w, final int h, final Coord2D from, final Coord2D to,
            final BiPredicate<Integer, Integer> condition,
            final BiConsumer<Integer, Integer> action
    ) {
        if (!brushShape.gapProne())
            return;

        final int b = (getBreadth() / 2) + 1,
                xMin = Math.max(0, Math.min(from.x, to.x) - b),
                xMax = Math.min(w, Math.max(from.x, to.x) + b + 1),
                yMin = Math.max(0, Math.min(from.y, to.y) - b),
                yMax = Math.min(h, Math.max(from.y, to.y) + b + 1);

        for (int x = xMin; x < xMax; x++) {
            for (int y = yMin; y < yMax; y++) {
                final boolean oobLeft = x - 1 < 0,
                        oobRight = x + 1 >= w,
                        oobTop = y - 1 < 0,
                        oobBottom = y + 1 >= h,
                        left = !oobLeft && condition.test(x - 1, y),
                        right = !oobRight && condition.test(x + 1, y),
                        top = !oobTop && condition.test(x, y - 1),
                        bottom = !oobBottom && condition.test(x, y + 1),
                        met = (oobLeft || left) && (oobRight || right) &&
                                (oobTop || top) && (oobBottom || bottom);

                if (met)
                    action.accept(x, y);
            }
        }
    }
}
