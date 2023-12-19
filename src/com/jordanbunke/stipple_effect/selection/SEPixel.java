package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.utility.Coord2D;

import java.awt.*;

public record SEPixel(Coord2D point, Color color) {

    public SEPixel move(final Coord2D displacement) {
        return new SEPixel(point.displace(displacement), color);
    }

    @Override
    public String toString() {
        return color + " at " + point;
    }
}
