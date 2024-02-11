package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.visual.SECursor;

public final class PolygonSelect extends ToolWithMode {
    private static final PolygonSelect INSTANCE;

    // TODO - fields

    static {
        INSTANCE = new PolygonSelect();
    }

    private PolygonSelect() {
        // TODO
    }

    public static PolygonSelect get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Polygon Select";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        // TODO
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        // TODO
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        // TODO
    }

    @Override
    public boolean hasToolContentPreview() {
        return false; // TODO
    }

    @Override
    public GameImage getToolContentPreview() {
        return GameImage.dummy(); // TODO
    }

    @Override
    public String getCursorCode() {
        return SECursor.RETICLE; // TODO - remove method once ToolWithMode-compliant cursors have been drawn
    }
}
