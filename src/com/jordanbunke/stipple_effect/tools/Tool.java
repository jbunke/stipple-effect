package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.ImageContext;

public interface Tool {
    String getName();
    void onMouseDown(final ImageContext context, final GameMouseEvent me);
    void update(final ImageContext context, final Coord2D mousePosition);
    void onMouseUp(final ImageContext context, final GameMouseEvent me);

    // TODO - icons
    // TODO - cursors

    // TODO tools - stippler, pencil, pen, eraser,
    //  color picker,
    //  stipple select, box select, move selection, move selected pixels, magic wand select

    // DONE tools
    // zoom, hand
}
