package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.util.HashSet;
import java.util.Set;

public class Outliner {
    public static final int NUM_DIRS = Direction.values().length;

    public enum Direction {
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
        if (sideMask.length != NUM_DIRS)
            return selection;

        final Set<Coord2D> outline = new HashSet<>();

        final Direction[] directions = Direction.values();

        for (Coord2D pixel : selection)
            for (int i = 0; i < NUM_DIRS; i++) {
                /* TODO: reimplement with sideMask as int array for pixels in dim
                 *
                 *   final Coord2D relative = directions[i].relativeCoordinate();
                 *   Coord2D shifted = pixel.displace(relative);
                 *
                 *   for (int s = 0; s < sideMask[i] && s < Constants.MAX_OUTLINE_PX &&
                 *           !selection.contains(shifted); s++) {
                 *       outline.add(shifted);
                 *       shifted = shifted.displace(relative);
                 *   }
                 *
                 *   shifted = pixel.displace(-relative.x, -relative.y);
                 *
                 *   for (int s = 0; s < -sideMask[i] && s < Constants.MAX_OUTLINE_PX &&
                 *           selection.contains(shifted); s++) {
                 *       outline.add(shifted);
                 *       shifted = shifted.displace(-relative.x, -relative.y);
                 *   }
                 * */

                if (!sideMask[i])
                    continue;

                final Coord2D wouldBe = pixel.displace(directions[i].relativeCoordinate());

                if (!selection.contains(wouldBe))
                    outline.add(wouldBe);
            }

        return outline;
    }
}
