package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.utility.Coord2D;

import java.util.Set;

public class Selection {
    public static Coord2D topLeft(final Set<Coord2D> pixels) {
        int lowestX = Integer.MAX_VALUE, lowestY = Integer.MAX_VALUE;

        for (Coord2D pixel : pixels) {
            lowestX = Math.min(lowestX, pixel.x);
            lowestY = Math.min(lowestY, pixel.y);
        }

        return new Coord2D(lowestX, lowestY);
    }

    public static Coord2D bottomRight(final Set<Coord2D> pixels) {
        int lowestX = Integer.MIN_VALUE, lowestY = Integer.MIN_VALUE;

        for (Coord2D pixel : pixels) {
            lowestX = Math.max(lowestX, pixel.x + 1);
            lowestY = Math.max(lowestY, pixel.y + 1);
        }

        return new Coord2D(lowestX, lowestY);
    }
}
