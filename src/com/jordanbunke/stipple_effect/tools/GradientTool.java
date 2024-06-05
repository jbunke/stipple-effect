package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.GatewayMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.funke.core.ConcreteProperty;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.EnumUtils;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.math.Geometry;
import com.jordanbunke.stipple_effect.visual.menu_elements.Checkbox;
import com.jordanbunke.stipple_effect.visual.menu_elements.Dropdown;
import com.jordanbunke.stipple_effect.visual.menu_elements.IncrementalRangeElements;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public final class GradientTool extends ToolWithBreadth
        implements SnappableTool, ToggleModeTool {
    private static final GradientTool INSTANCE;

    private boolean drawing, global, snap, backwards, dithered,
            masked, bounded, contiguous;
    private GameImage toolContentPreview;
    private Coord2D anchor;
    private List<Set<Coord2D>> gradientStages;
    private Shape shape;
    private Set<Coord2D> accessible;

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
        bounded = false;
        contiguous = true;

        shape = Shape.LINEAR;

        accessible = new HashSet<>();

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

            initializeMask(context);
        }
    }

    private void initializeMask(final SEContext context) {
        final int w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();

        final GameImage frame = context.getState().getActiveLayerFrame();

        final Color maskColor = masked && anchorInBounds(w, h)
                ? frame.getColorAt(anchor.x, anchor.y)
                : null;
        accessible = maskColor != null ? search(frame, maskColor) : new HashSet<>();
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();

        if (drawing && !tp.equals(Constants.NO_VALID_TARGET)) {
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();

            if (tp.equals(getLastTP()))
                return;

            toolContentPreview = new GameImage(w, h);

            if (global)
                updateGlobalMode(context, tp, w, h);
            else
                updateBrushMode(context, tp, w, h);

            updateLast(context);
        }
    }

    private void updateBrushMode(
            final SEContext context, final Coord2D tp,
            final int w, final int h
    ) {
        final Set<Coord2D>
                selection = context.getState().getSelection(),
                gradientStage = new HashSet<>();

        populateAround(gradientStage, tp, selection);
        fillLineSpace(getLastTP(), tp, (x, y) -> populateAround(
                gradientStage, getLastTP().displace(x, y), selection));

        gradientStages.add(gradientStage);

        drawGradientStages(w, h);
    }

    private void updateGlobalMode(
            final SEContext context, final Coord2D tp,
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

        drawGlobalGradient(endpoint, context, w, h);
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
            final Coord2D endpoint, final SEContext context,
            final int w, final int h
    ) {
        final Set<Coord2D> selection = context.getState().getSelection();
        final boolean hasSelection = !selection.isEmpty();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final Coord2D pos = new Coord2D(x, y);
                if (hasSelection && !selection.contains(pos))
                    continue;

                final double c = shape.cGetter().apply(pos, endpoint);

                final boolean satisfiesMask = !masked ||
                        (anchorInBounds(w, h) &&
                                accessible.contains(new Coord2D(x, y))),
                        satisfiesScope = isCValid(c),
                        replacePixel = satisfiesMask && satisfiesScope;

                if (replacePixel)
                    toolContentPreview.setRGB(x, y, (dithered
                            ? getDitherColor(x, y, DitherStage.get(c))
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

    private boolean isCValid(final double c) {
        return !bounded || (c >= 0d && c <= 1d);
    }

    private boolean anchorInBounds(
            final int w, final int h
    ) {
        return anchor.x >= 0 && anchor.x < w &&
                anchor.y >= 0 && anchor.y < h;
    }

    private Set<Coord2D> search(
            final GameImage frame, final Color maskColor
    ) {
        return contiguous
                ? ToolThatSearches.contiguousSearch(frame, maskColor, anchor)
                : ToolThatSearches.globalSearch(frame, maskColor);
    }

    private void drawGradientStages(final int w, final int h) {
        final int size = gradientStages.size();

        for (int g = 0; g < size; g++) {
            final double c = size == 1 ? 0d : g / (double) (size - 1);

            if (dithered) {
                final DitherStage ds = DitherStage.get(c);

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

    public void setBounded(final boolean bounded) {
        this.bounded = bounded;
    }

    public void setContiguous(final boolean contiguous) {
        this.contiguous = contiguous;
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
        // inherited content called first to trigger correct ditherTextX assignment
        final MenuElementGrouping inherited = super.buildToolOptionsBar();

        // dithered label
        final TextLabel ditheredLabel = TextLabel.make(
                new Coord2D(getAfterBreadthTextX(), Layout.optionsBarTextY()),
                "Dithered");

        // dithered checkbox
        final Checkbox ditheredCheckbox = new Checkbox(new Coord2D(
                Layout.optionsBarNextElementX(ditheredLabel, false),
                Layout.optionsBarButtonY()), new ConcreteProperty<>(
                () -> dithered, this::setDithered));

        // shape label
        final TextLabel shapeLabel = TextLabel.make(new Coord2D(
                        Layout.optionsBarNextElementX(ditheredCheckbox, true),
                        Layout.optionsBarTextY()), "Shape");

        // shape dropdown
        final Dropdown shapeDropdown = Dropdown.forToolOptionsBar(
                Layout.optionsBarNextElementX(shapeLabel, false),
                EnumUtils.stream(Shape.class).map(EnumUtils::formattedName)
                        .toArray(String[]::new),
                EnumUtils.stream(Shape.class).map(s -> (Runnable) () ->
                        setShape(s)).toArray(Runnable[]::new),
                shape::ordinal);

        // bounded label
        final TextLabel boundedLabel = TextLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(shapeDropdown, true),
                Layout.optionsBarTextY()), "Bounded");

        // bounded checkbox
        final Checkbox boundedCheckbox = new Checkbox(new Coord2D(
                Layout.optionsBarNextElementX(boundedLabel, false),
                Layout.optionsBarButtonY()), new ConcreteProperty<>(
                        () -> bounded, this::setBounded));

        // masked label
        final TextLabel maskedLabel = TextLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(boundedCheckbox, true),
                Layout.optionsBarTextY()), "Mask");

        // masked checkbox
        final Checkbox maskedCheckbox = new Checkbox(new Coord2D(
                Layout.optionsBarNextElementX(maskedLabel, false),
                Layout.optionsBarButtonY()), new ConcreteProperty<>(
                () -> masked, this::setMasked));

        // contiguous label
        final TextLabel contiguousLabel = TextLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(maskedCheckbox, true),
                Layout.optionsBarTextY()), "Contiguous");

        // contiguous checkbox
        final Checkbox contiguousCheckbox = new Checkbox(new Coord2D(
                Layout.optionsBarNextElementX(contiguousLabel, false),
                Layout.optionsBarButtonY()), new ConcreteProperty<>(
                () -> contiguous, this::setContiguous));

        // tolerance
        final TextLabel toleranceLabel = Layout.optionsBarNextSectionLabel(
                contiguousCheckbox, "Tol.");

        final int PERCENT = 100;
        final IncrementalRangeElements<Double> tolerance =
                IncrementalRangeElements.makeForDouble(toleranceLabel,
                        Layout.optionsBarButtonY(), Layout.optionsBarTextY(),
                        ToolThatSearches::decreaseTolerance,
                        ToolThatSearches::increaseTolerance,
                        Constants.EXACT_COLOR_MATCH, Constants.MAX_TOLERANCE,
                        ToolThatSearches::setTolerance,
                        ToolThatSearches::getTolerance,
                        t -> (int) (PERCENT * t),
                        sv -> sv / (double) PERCENT,
                        t -> ((int) (PERCENT * t)) + "%",
                        MathPlus.findBest(ToolThatSearches.NO_TOLERANCE,
                                0, String::length, (a, b) -> a < b,
                                ToolThatSearches.NO_TOLERANCE,
                                ToolThatSearches.MAX_TOLERANCE));

        final GatewayMenuElement maskUnlocks = new GatewayMenuElement(
                new MenuElementGrouping(
                        contiguousLabel, contiguousCheckbox,
                        toleranceLabel, tolerance.decButton,
                        tolerance.incButton, tolerance.slider,
                        tolerance.value), () -> masked);

        return new MenuElementGrouping(inherited,
                ditheredLabel, ditheredCheckbox, shapeLabel, shapeDropdown,
                boundedLabel, boundedCheckbox,
                maskedLabel, maskedCheckbox, maskUnlocks);
    }
}
