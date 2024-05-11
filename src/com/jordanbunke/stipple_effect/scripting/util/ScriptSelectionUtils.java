package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptSet;
import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.util.HashSet;
import java.util.Set;

public class ScriptSelectionUtils {
    public static Set<Coord2D> convertSelection(
            final ScriptSet input, final int w, final int h
    ) {
            final Set<Coord2D> pixels = new HashSet<>();

            input.stream().map(px -> (ScriptArray) px).forEach(a -> {
                final int x = (int) a.get(0), y = (int) a.get(1);

                if (x >= 0 && x < w && y >= 0 && y < h)
                    pixels.add(new Coord2D(x, y));
            });

            return pixels;
    }
}
