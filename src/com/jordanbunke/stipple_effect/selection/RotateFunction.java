package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.util.Set;

@FunctionalInterface
public interface RotateFunction {
    void accept(final Set<Coord2D> initialSelection, final double deltaR,
                final Coord2D pivot, final boolean[] offset, final boolean checkpoint);
}
