package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.menu.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.menu_elements.Dropdown;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;

import java.util.Set;

public final class ShapeTool extends GeometryTool implements SnappableTool {
    private static final ShapeTool INSTANCE;

    private boolean ellipse;

    static {
        INSTANCE = new ShapeTool();
    }

    private ShapeTool() {
        super();

        ellipse = false;
    }

    public static ShapeTool get() {
        return INSTANCE;
    }

    @Override
    Coord2D snappedEndpoint(final Coord2D anchor, final Coord2D tp) {
        final Coord2D distance = tp.displace(-anchor.x, -anchor.y);

        final int magX = Math.abs(distance.x), magY = Math.abs(distance.y);

        return magX > magY
                ? anchor.displace(distance.x, distance.y > 0 ? magX : -magX)
                : anchor.displace(distance.x > 0 ? magY : -magY, distance.y);
    }

    @Override
    void geoDefinition(
            final Coord2D anchor, final Coord2D endpoint,
            final Set<Coord2D> selection
    ) {
        if (ellipse) {
            // TODO
        } else {
            final Coord2D corner3 = new Coord2D(anchor.x, endpoint.y),
                    corner4 = new Coord2D(endpoint.x, anchor.y);

            populateAround(anchor, selection);
            populateAround(endpoint, selection);
            populateAround(corner3, selection);
            populateAround(corner4, selection);

            fillLineSpace(anchor, corner3, (x, y) ->
                    populateAround(anchor.displace(x, y), selection));
            fillLineSpace(corner3, endpoint, (x, y) ->
                    populateAround(corner3.displace(x, y), selection));
            fillLineSpace(endpoint, corner4, (x, y) ->
                    populateAround(endpoint.displace(x, y), selection));
            fillLineSpace(corner4, anchor, (x, y) ->
                    populateAround(corner4.displace(x, y), selection));
        }
    }

    public void setEllipse(final boolean ellipse) {
        this.ellipse = ellipse;
    }

    @Override
    public String getBottomBarText() {
        return (ellipse ? "Ellipse " : "Rectangle ") +
                "Tool (" + getBreadth() + " px)";
    }

    @Override
    public String getName() {
        return "Shape Tool";
    }

    @Override
    public MenuElementGrouping buildToolOptionsBar() {
        final MenuElementGrouping inherited = super.buildToolOptionsBar();

        // shape label
        final TextLabel shapeLabel = TextLabel.make(
                new Coord2D(getDitherTextX(), Layout.optionsBarTextY()),
                "Shape");

        // shape dropdown
        final Dropdown shapeDropdown = Dropdown.forToolOptionsBar(
                Layout.optionsBarNextElementX(shapeLabel, false),
                new String[] { "Rectangle", "Ellipse" },
                new Runnable[] {
                        () -> setEllipse(false), () -> setEllipse(true)
                }, () -> ellipse ? 1 : 0);

        return new MenuElementGrouping(
                inherited, shapeLabel, shapeDropdown);
    }
}
