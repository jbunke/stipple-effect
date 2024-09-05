package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;

import java.awt.*;

public final class NormalMapSampler extends AbstractColorMap {

    public NormalMapSampler(final Coord2D position, final Bounds2D dimensions) {
        super(position, dimensions);
    }

    @Override
    Color getPixelColor(final Color c, final Coord2D pixel) {
        // TODO
        return SEColors.red();
    }

    @Override
    Coord2D getNodePos(final Color c) {
        // TODO
        return middle;
    }

    @Override
    protected void updateColor(final Coord2D localMP) {
        // TODO
    }
}
