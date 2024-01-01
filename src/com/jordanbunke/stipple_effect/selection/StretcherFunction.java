package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.tools.MoverTool;

import java.util.Set;

@FunctionalInterface
public interface StretcherFunction {
    void accept(final Set<Coord2D> initialSelection, final Coord2D change,
                final MoverTool.Direction direction, final boolean checkpoint);
}
