package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.MathPlus;
import com.jordanbunke.stipple_effect.utility.ColorMath;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.menu_elements.Checkbox;
import com.jordanbunke.stipple_effect.visual.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalSlider;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public sealed abstract class ToolThatSearches extends ToolWithMode permits Fill, Wand {
    private double tolerance;
    private boolean searchDiag;

    ToolThatSearches() {
        tolerance = Constants.DEFAULT_TOLERANCE;
        searchDiag = false;
    }

    @Override
    public String getBottomBarText() {
        return getName() + " (" + getToleranceText() + ")";
    }

    private String getToleranceText() {
        return tolerance == Constants.EXACT_COLOR_MATCH
                ? "exact match" : (tolerance == Constants.MAX_TOLERANCE
                ? "trivial" : (int)(tolerance * 100) + "% tolerance");
    }

    public double getTolerance() {
        return tolerance;
    }

    public boolean isSearchDiag() {
        return searchDiag;
    }

    public void setTolerance(final double tolerance) {
        this.tolerance = MathPlus.bounded(Constants.EXACT_COLOR_MATCH,
                tolerance, Constants.MAX_TOLERANCE);
    }

    public void increaseTolerance() {
        setTolerance(tolerance + Constants.SMALL_TOLERANCE_INC);
    }

    public void decreaseTolerance() {
        setTolerance(tolerance - Constants.SMALL_TOLERANCE_INC);
    }

    public void setSearchDiag(final boolean searchDiag) {
        this.searchDiag = searchDiag;
    }

    public Set<Coord2D> search(
            final GameImage image, final Color initial, final Coord2D target
    ) {
        return ToolWithMode.isGlobal() ? globalSearch(image, initial)
                : adjacentSearch(image, initial, target);
    }

    private Set<Coord2D> adjacentSearch(
            final GameImage image, final Color initial, final Coord2D target
    ) {
        final Set<Coord2D> matched = new HashSet<>(), searched = new HashSet<>();
        final int w = image.getWidth(), h = image.getHeight();

        final Stack<Coord2D> searching = new Stack<>();
        searching.push(target);

        while (!searching.isEmpty()) {
            final Coord2D active = searching.pop();
            searched.add(active);

            final Color pixel = ImageProcessing.colorAtPixel(
                    image, active.x, active.y);

            if (pixelMatchesToleranceCondition(initial, pixel)) {
                matched.add(active);

                // neighbours
                if (active.x > 0 && !searched.contains(active.displace(-1, 0)))
                    searching.add(active.displace(-1, 0));
                if (active.x + 1 < w && !searched.contains(active.displace(1, 0)))
                    searching.add(active.displace(1, 0));
                if (active.y > 0 && !searched.contains(active.displace(0, -1)))
                    searching.add(active.displace(0, -1));
                if (active.y + 1 < h && !searched.contains(active.displace(0, 1)))
                    searching.add(active.displace(0, 1));

                // diagonals
                if (searchDiag) {
                    if (active.x > 0 && active.y > 0 &&
                            !searched.contains(active.displace(-1, -1)))
                        searching.add(active.displace(-1, -1));
                    if (active.x > 0 && active.y + 1 < h &&
                            !searched.contains(active.displace(-1, 1)))
                        searching.add(active.displace(-1, 1));
                    if (active.x + 1 < w && active.y > 0 &&
                            !searched.contains(active.displace(1, -1)))
                        searching.add(active.displace(1, -1));
                    if (active.x + 1 < w && active.y + 1 < h &&
                            !searched.contains(active.displace(1, 1)))
                        searching.add(active.displace(1, 1));
                }
            }
        }

        return matched;
    }

    private Set<Coord2D> globalSearch(
            final GameImage image, final Color initial
    ) {
        final Set<Coord2D> matched = new HashSet<>();
        final int w = image.getWidth(), h = image.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final Color pixel = ImageProcessing.colorAtPixel(image, x, y);

                if (pixelMatchesToleranceCondition(initial, pixel))
                    matched.add(new Coord2D(x, y));
            }
        }

        return matched;
    }

    public boolean pixelMatchesToleranceCondition(
            final Color initial, final Color pixel
    ) {
        if (initial.equals(pixel))
            return true;

        // if both pixels are completely transparent,
        // ignore RGB as pixels are indistinguishable
        if (initial.getAlpha() == 0 && pixel.getAlpha() == 0)
            return true;

        return ColorMath.diff(initial, pixel) <= tolerance;
    }

    @Override
    public boolean hasToolOptionsBar() {
        return true;
    }

    @Override
    public MenuElementGrouping buildToolOptionsBar() {
        final int optionsBarTextY = Layout.getToolOptionsBarPosition().y +
                Layout.TEXT_Y_OFFSET,
                optionsBarButtonY = Layout.getToolOptionsBarPosition().y +
                        Layout.BUTTON_OFFSET;

        // tolerance label
        final DynamicLabel toleranceLabel = new DynamicLabel(
                new Coord2D(getToleranceTextX(), optionsBarTextY),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                () -> "Tolerance: " + getToleranceText().replace(" tolerance", ""),
                getToleranceDecrementButtonX() - getToleranceTextX());

        // tolerance decrement and increment buttons
        final IconButton decButton = IconButton.makeNoTooltip(
                IconCodes.DECREMENT, new Coord2D(
                        getToleranceDecrementButtonX(), optionsBarButtonY),
                this::decreaseTolerance),
                incButton = IconButton.makeNoTooltip(IconCodes.INCREMENT,
                        new Coord2D(getToleranceIncrementButtonX(),
                                optionsBarButtonY),
                        this::increaseTolerance);

        // tolerance slider
        final int SLIDER_MULT = 100;
        final HorizontalSlider toleranceSlider = new HorizontalSlider(
                new Coord2D(getToleranceSliderX(), optionsBarButtonY),
                Layout.optionsBarSliderWidth(), MenuElement.Anchor.LEFT_TOP,
                0, (int) (SLIDER_MULT * Constants.MAX_TOLERANCE),
                () -> (int) (SLIDER_MULT * getTolerance()),
                x -> setTolerance(x / (double) SLIDER_MULT), false);
        toleranceSlider.updateAssets();

        // diagonal label
        final TextLabel diagonalLabel = TextLabel.make(
                new Coord2D(getDiagonalTextX(), optionsBarTextY),
                "Search diagonally adjacent pixels?", Constants.WHITE);

        // diagonal checkbox
        final Checkbox diagonalCheckbox = new Checkbox(new Coord2D(
                diagonalLabel.getX() + diagonalLabel.getWidth() +
                        Layout.CONTENT_BUFFER_PX, optionsBarButtonY),
                MenuElement.Anchor.LEFT_TOP,
                this::isSearchDiag, this::setSearchDiag);

        return new MenuElementGrouping(super.buildToolOptionsBar(),
                toleranceLabel, decButton, incButton, toleranceSlider,
                diagonalLabel, diagonalCheckbox);
    }

    private int getToleranceTextX() {
        return Layout.getToolOptionsBarPosition().x +
                (int)(Layout.getToolOptionsBarWidth() * 0.11);
    }

    private int getToleranceDecrementButtonX() {
        return Layout.getToolOptionsBarPosition().x +
                (int)(Layout.getToolOptionsBarWidth() * 0.26);
    }

    private int getToleranceIncrementButtonX() {
        return getToleranceDecrementButtonX() + Layout.BUTTON_INC;
    }

    private int getToleranceSliderX() {
        return getToleranceIncrementButtonX() + Layout.BUTTON_INC;
    }

    private int getDiagonalTextX() {
        return getToleranceSliderX() + Layout.optionsBarSliderWidth() +
                Layout.optionsBarSectionBuffer();
    }
}
