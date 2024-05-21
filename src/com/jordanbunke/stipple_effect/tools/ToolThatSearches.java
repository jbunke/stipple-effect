package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.funke.core.ConcreteProperty;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.visual.menu_elements.Checkbox;
import com.jordanbunke.stipple_effect.visual.menu_elements.IncrementalRangeElements;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;

import java.awt.*;
import java.util.*;

public sealed abstract class ToolThatSearches extends ToolWithMode permits Fill, Wand {
    static final String
            NO_TOLERANCE = "exact match",
            MAX_TOLERANCE = "trivial";

    private static double tolerance;
    private static boolean searchDiag;
    private static final Map<Color, Boolean> hasCheckedMap;

    static {
        tolerance = Constants.DEFAULT_TOLERANCE;
        searchDiag = false;

        hasCheckedMap = new HashMap<>();
    }

    @Override
    public String getBottomBarText() {
        return getName() + " (" + getToleranceText() + ")";
    }

    private static String getToleranceText() {
        return tolerance == Constants.EXACT_COLOR_MATCH
                ? NO_TOLERANCE
                : (tolerance == Constants.MAX_TOLERANCE
                        ? MAX_TOLERANCE : (int)(tolerance * 100) + "% tolerance");
    }

    public static double getTolerance() {
        return tolerance;
    }

    public static boolean isSearchDiag() {
        return searchDiag;
    }

    public static void setTolerance(final double tolerance) {
        ToolThatSearches.tolerance =
                MathPlus.bounded(Constants.EXACT_COLOR_MATCH,
                        tolerance, Constants.MAX_TOLERANCE);
    }

    public static void increaseTolerance() {
        setTolerance(tolerance + Constants.SMALL_TOLERANCE_INC);
    }

    public static void decreaseTolerance() {
        setTolerance(tolerance - Constants.SMALL_TOLERANCE_INC);
    }

    public static void setSearchDiag(final boolean searchDiag) {
        ToolThatSearches.searchDiag = searchDiag;
    }

    public static Set<Coord2D> search(final GameImage image, final Coord2D target) {
        final Color initial = image.getColorAt(target.x, target.y);

        return ToolWithMode.isGlobal()
                ? globalSearch(image, initial)
                : contiguousSearch(image, initial, target);
    }

    public static Set<Coord2D> contiguousSearch(
            final GameImage image, final Color initial, final Coord2D target
    ) {
        hasCheckedMap.clear();

        final Set<Coord2D> matched = new HashSet<>(), searched = new HashSet<>();
        final int w = image.getWidth(), h = image.getHeight();

        final Stack<Coord2D> searching = new Stack<>();
        searching.push(target);

        while (!searching.isEmpty()) {
            final Coord2D active = searching.pop();
            searched.add(active);

            final Color pixel = image.getColorAt(active.x, active.y);

            final boolean result =
                    pixelMatchesToleranceCondition(initial, pixel);
            hasCheckedMap.put(pixel, result);

            if (result) {
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

    public static Set<Coord2D> globalSearch(
            final GameImage image, final Color initial
    ) {
        hasCheckedMap.clear();

        final Set<Coord2D> matched = new HashSet<>();
        final int w = image.getWidth(), h = image.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final Color pixel = image.getColorAt(x, y);

                final boolean result =
                        pixelMatchesToleranceCondition(initial, pixel);
                hasCheckedMap.put(pixel, result);

                if (result)
                    matched.add(new Coord2D(x, y));
            }
        }

        return matched;
    }

    public static boolean pixelMatchesToleranceCondition(
            final Color initial, final Color pixel
    ) {
        if (hasCheckedMap.containsKey(pixel))
            return hasCheckedMap.get(pixel);

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
        // tolerance label
        final TextLabel toleranceLabel = TextLabel.make(
                getFirstOptionLabelPosition(), "Tolerance");

        final int SLIDER_MULT = 100;
        final IncrementalRangeElements<Double> tolerance =
                IncrementalRangeElements.makeForDouble(toleranceLabel,
                        Layout.optionsBarButtonY(), Layout.optionsBarTextY(),
                        ToolThatSearches::decreaseTolerance,
                        ToolThatSearches::increaseTolerance,
                        Constants.EXACT_COLOR_MATCH, Constants.MAX_TOLERANCE,
                        ToolThatSearches::setTolerance,
                        ToolThatSearches::getTolerance,
                        t -> (int) (SLIDER_MULT * t),
                        sv -> sv / (double) SLIDER_MULT,
                        t -> getToleranceText().replace(" tolerance", ""),
                        MathPlus.findBest(
                                NO_TOLERANCE, 0, String::length,
                                (a, b) -> a < b, NO_TOLERANCE, MAX_TOLERANCE));

        // diagonal label
        final TextLabel diagonalLabel = Layout.optionsBarNextSectionLabel(
                tolerance.value, "Search diagonally adjacent pixels?");

        // diagonal checkbox
        final Checkbox diagonalCheckbox = new Checkbox(new Coord2D(
                Layout.optionsBarNextElementX(diagonalLabel, false),
                Layout.optionsBarButtonY()), new ConcreteProperty<>(
                ToolThatSearches::isSearchDiag, ToolThatSearches::setSearchDiag
        ));

        return new MenuElementGrouping(super.buildToolOptionsBar(),
                toleranceLabel, tolerance.decButton, tolerance.incButton,
                tolerance.slider, tolerance.value,
                diagonalLabel, diagonalCheckbox);
    }
}
