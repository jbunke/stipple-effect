package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.*;
import com.jordanbunke.stipple_effect.visual.menu_elements.Checkbox;
import com.jordanbunke.stipple_effect.visual.menu_elements.DropdownMenu;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public final class GradientTool extends ToolWithBreadth
        implements SnappableTool, ToggleModeTool {
    private static final GradientTool INSTANCE;

    private boolean drawing, global, snap, backwards, dithered, masked;
    private GameImage toolContentPreview;
    private Coord2D anchor;
    private List<Set<Coord2D>> gradientStages;
    private Shape shape;
    private Scope scope;

    public enum Scope {
        CANVAS, RANGE
    }

    public enum Shape {
        LINEAR, RADIAL, SPIRAL;

        public BiFunction<Coord2D, Coord2D, Double> cGetter() {
            return switch (this) {
                case LINEAR -> INSTANCE::getLinearC;
                case RADIAL -> INSTANCE::getRadialC;
                case SPIRAL -> INSTANCE::getSpiralC;
            };
        }
    }

    static {
        INSTANCE = new GradientTool();
    }

    private GradientTool() {
        drawing = false;
        global = false;
        snap = false;
        backwards = false;
        dithered = false;
        masked = false;
        scope = Scope.CANVAS;

        shape = Shape.LINEAR;

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

            if (global)
                updateGlobalMode(selection, tp, w, h);
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

    private void updateGlobalMode(
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

        drawGlobalGradient(endpoint, selection, w, h);
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

    private void drawGlobalGradient(
            final Coord2D endpoint, final Set<Coord2D> selection,
            final int w, final int h
    ) {
        final boolean hasSelection = !selection.isEmpty();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final Coord2D pos = new Coord2D(x, y);
                if (hasSelection && !selection.contains(pos))
                    continue;

                final double c = shape.cGetter().apply(pos, endpoint);

                if (scope == Scope.CANVAS || (c >= 0d && c <= 1d))
                    toolContentPreview.setRGB(x, y, (dithered
                            ? getDitherColor(x, y, getDitherStage(c))
                            : getColor(c)).getRGB());
            }
        }
    }

    private double getRadialC(final Coord2D pos, final Coord2D endpoint) {
        final double
                totalDistance = Coord2D.unitDistanceBetween(anchor, endpoint),
                distance = Coord2D.unitDistanceBetween(anchor, pos);

        return totalDistance == 0d ? 0d
                : distance / totalDistance;
    }

    private double getSpiralC(final Coord2D pos, final Coord2D endpoint) {
        final double
                initialAngle = Geometry.calculateAngleInRad(endpoint, anchor),
                angle = Geometry.calculateAngleInRad(pos, anchor),
                normalized = Geometry.normalizeAngle(angle - initialAngle);

        return normalized / Constants.CIRCLE;
    }

    private double getLinearC(final Coord2D pos, final Coord2D endpoint) {
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

            if (dithered) {
                final DitherStage ds = getDitherStage(c);

                gradientStages.get(g).stream().filter(
                                p -> p.x >= 0 && p.x < w && p.y >= 0 && p.y < h)
                        .forEach(p -> toolContentPreview.setRGB(
                                p.x, p.y, getDitherColor(p.x, p.y, ds).getRGB()));
            } else {
                final Color stageColor = getColor(c);

                gradientStages.get(g).stream().filter(
                                p -> p.x >= 0 && p.x < w && p.y >= 0 && p.y < h)
                        .forEach(p -> toolContentPreview.setRGB(
                                p.x, p.y, stageColor.getRGB()));
            }
        }
    }

    private Color getColor(final double c) {
        final Color p = StippleEffect.get().getPrimary(),
                s = StippleEffect.get().getSecondary();

        return ColorMath.betweenColor(backwards ? 1 - c : c, p, s);
    }

    private DitherStage getDitherStage(final double c) {
        final DitherStage[] ds = DitherStage.values();

        return MathPlus.findBest(
                DitherStage.FIFTY_FIFTY, DitherStage.FIFTY_FIFTY, d -> d,
                (d1, d2) -> Math.abs(d1.getFraction() - c) <=
                        Math.abs(d2.getFraction() - c), ds);
    }

    private Color getDitherColor(
            final int x, final int y, final DitherStage ds
    ) {
        final Color p = StippleEffect.get().getPrimary(),
                s = StippleEffect.get().getSecondary();

        return ds.condition(x, y)
                ? (backwards ? p : s)
                : (backwards ? s : p);
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (drawing) {
            drawing = false;

            context.paintOverImage(toolContentPreview);

            context.getState().markAsCheckpoint(true);
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
    public void setMode(final boolean global) {
        if (!drawing)
            this.global = global;
    }

    public void setDithered(final boolean dithered) {
        this.dithered = dithered;
    }

    public void setMasked(final boolean masked) {
        this.masked = masked;
    }

    public void setScope(final Scope scope) {
        this.scope = scope;
    }

    public void setShape(final Shape shape) {
        this.shape = shape;
    }

    @Override
    public void setSnap(final boolean snap) {
        this.snap = global && snap;
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
        return global ? GameImage.dummy() : super.getOverlay();
    }

    @Override
    public String getBottomBarText() {
        return global
                ? EnumUtils.formattedName(shape) + " Gradient"
                : super.getBottomBarText().replace("Tool", "Brush");
    }

    @Override
    public MenuElementGrouping buildToolOptionsBar() {
        // dithered label
        final TextLabel ditheredLabel = TextLabel.make(
                new Coord2D(getDitherTextX(), Layout.optionsBarTextY()),
                "Dithered?", Constants.WHITE);

        // dithered checkbox
        final Checkbox ditheredCheckbox = new Checkbox(new Coord2D(
                ditheredLabel.getX() + ditheredLabel.getWidth() +
                        Layout.CONTENT_BUFFER_PX, Layout.optionsBarButtonY()),
                MenuElement.Anchor.LEFT_TOP,
                () -> dithered, this::setDithered);

        // shape label
        final TextLabel shapeLabel = TextLabel.make(new Coord2D(
                        ditheredCheckbox.getX() + Layout.BUTTON_DIM +
                                Layout.optionsBarSectionBuffer(),
                        Layout.optionsBarTextY()), "Shape", Constants.WHITE);

        // shape dropdown
        final DropdownMenu shapeDropdown = new DropdownMenu(new Coord2D(
                shapeLabel.getX() + shapeLabel.getWidth() +
                        Layout.CONTENT_BUFFER_PX,
                Layout.getToolOptionsBarPosition().y +
                        ((Layout.TOOL_OPTIONS_BAR_H -
                                Layout.STD_TEXT_BUTTON_H) / 2)),
                Layout.optionsBarSliderWidth(), MenuElement.Anchor.LEFT_TOP,
                (int) (Layout.TOOL_OPTIONS_BAR_H * 5.5),
                Arrays.stream(Shape.values()).map(EnumUtils::formattedName)
                        .toArray(String[]::new),
                Arrays.stream(Shape.values()).map(s -> (Runnable) () ->
                        setShape(s)).toArray(Runnable[]::new), shape::ordinal);

        // scope label
        final TextLabel scopeLabel = TextLabel.make(new Coord2D(
                shapeDropdown.getX() + shapeDropdown.getWidth() +
                        Layout.optionsBarSectionBuffer(),
                        Layout.optionsBarTextY()),
                "Scope", Constants.WHITE);

        // scope dropdown
        final DropdownMenu scopeDropdown = new DropdownMenu(new Coord2D(
                scopeLabel.getX() + scopeLabel.getWidth() +
                        Layout.CONTENT_BUFFER_PX,
                Layout.getToolOptionsBarPosition().y +
                        ((Layout.TOOL_OPTIONS_BAR_H -
                                Layout.STD_TEXT_BUTTON_H) / 2)),
                Layout.optionsBarSliderWidth(),
                MenuElement.Anchor.LEFT_TOP,
                (int) (Layout.TOOL_OPTIONS_BAR_H * 5.5),
                Arrays.stream(Scope.values())
                        .flatMap(scope -> Stream.of(
                                new Pair<>(scope, false),
                                new Pair<>(scope, true)))
                        .map(p -> EnumUtils.formattedName(p.first()) +
                                (p.second() ? " (masked)" : ""))
                        .toArray(String[]::new),
                Arrays.stream(Scope.values())
                        .flatMap(scope -> Stream.of(
                                new Pair<>(scope, false),
                                new Pair<>(scope, true)))
                        .map(p -> (Runnable) () -> {
                            setScope(p.first());
                            setMasked(p.second());
                        }).toArray(Runnable[]::new),
                () -> (scope.ordinal() * 2) + (masked ? 1 : 0));

        return new MenuElementGrouping(super.buildToolOptionsBar(),
                ditheredLabel, ditheredCheckbox, shapeLabel, shapeDropdown,
                scopeLabel, scopeDropdown);
    }
}
