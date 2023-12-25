package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.utility.Coord2D;

import java.util.HashSet;
import java.util.Set;

public class Outliner {
    public static final int NUM_SIDES = Side.values().length;

    public enum Side {
        T, L, R, B, TL, TR, BL, BR;

        public Coord2D relativeCoordinate() {
            final int x, y;

            x = switch (this) {
                case L, BL, TL -> -1;
                case B, T -> 0;
                default -> 1;
            };

            y = switch (this) {
                case T, TL, TR -> -1;
                case L, R -> 0;
                default -> 1;
            };

            return new Coord2D(x, y);
        }
    }

    public static boolean[] getSingleOutlineMask() {
        return new boolean[] { true, true, true, true,
                false, false, false, false };
    }

    public static boolean[] getDoubleOutlineMask() {
        return new boolean[] { true, true, true, true,
                true, true, true, true };
    }

    public static Set<Coord2D> outline(
            final Set<Coord2D> selection, final boolean[] sideMask
    ) {
        if (sideMask.length != NUM_SIDES)
            return selection;

        final Set<Coord2D> outline = new HashSet<>();

        final Side[] sides = Side.values();

        for (Coord2D pixel : selection) {
            for (int i = 0; i < NUM_SIDES; i++) {
                if (!sideMask[i])
                    continue;

                final Coord2D wouldBe = pixel.displace(sides[i].relativeCoordinate());

                if (!selection.contains(wouldBe))
                    outline.add(wouldBe);
            }
        }

        return outline;
    }
}
