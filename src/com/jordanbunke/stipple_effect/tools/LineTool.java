package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.math.Geometry;

public final class LineTool extends GeometryTool implements SnappableTool {
    private static final LineTool INSTANCE;

    static {
        INSTANCE = new LineTool();
    }

    private LineTool() {
        super();
    }

    public static LineTool get() {
        return INSTANCE;
    }

    @Override
    Coord2D snappedEndpoint(final Coord2D anchor, final Coord2D tp) {
        final double angle = Geometry.normalizeAngle(
                Geometry.calculateAngleInRad(tp, anchor)),
                distance = Coord2D.unitDistanceBetween(anchor, tp),
                snapped = Geometry.snapAngle(angle, Constants._15_SNAP_INC);

        return Geometry.projectPoint(anchor, snapped, distance);
    }

    @Override
    void geoDefinition(
            final Coord2D anchor, final Coord2D endpoint,
            final Selection selection
    ) {
        populateAround(anchor, selection);
        populateAround(endpoint, selection);

        fillLineSpace(anchor, endpoint, (x, y) ->
                populateAround(anchor.displace(x, y), selection));
    }

    @Override
    public String getName() {
        return "Line Tool";
    }
}
