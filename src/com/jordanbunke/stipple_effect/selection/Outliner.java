package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Outliner {
    public static final int NUM_DIRS = Direction.values().length,
            FIRST_DIAG = Direction.TL.ordinal();

    public enum Direction {
        T, L, R, B, TL, TR, BL, BR;

        public Direction opposite() {
            return switch (this) {
                case T -> B;
                case B -> T;
                case L -> R;
                case R -> L;
                case TL -> BR;
                case BR -> TL;
                case TR -> BL;
                case BL -> TR;
            };
        }

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

    public static int[] getSingleOutlineMask() {
        return new int[] { 1, 1, 1, 1, 0, 0, 0, 0 };
    }

    public static int[] getDoubleOutlineMask() {
        return new int[] { 1, 1, 1, 1, 1, 1, 1, 1 };
    }

    public static Set<Coord2D> outline(
            final Set<Coord2D> selection, final int[] sideMask
    ) {
        if (sideMask.length != NUM_DIRS)
            return selection;

        final Set<Coord2D> outline = new HashSet<>();

        final Direction[] directions = Direction.values();

        // edges
        for (int i = 0; i < FIRST_DIAG; i++)
            for (Coord2D pixel : selection) {
                final Coord2D relative = directions[i].relativeCoordinate();
                Coord2D shifted = pixel.displace(relative);

                for (int s = 0; s < sideMask[i] && s < Constants.MAX_OUTLINE_PX &&
                        !selection.contains(shifted); s++) {
                    outline.add(shifted);
                    shifted = shifted.displace(relative);
                }

                shifted = pixel.displace(-relative.x, -relative.y);

                for (int s = 0; s < -sideMask[i] && s < Constants.MAX_OUTLINE_PX &&
                        selection.contains(shifted); s++) {
                    outline.add(shifted);
                    shifted = shifted.displace(-relative.x, -relative.y);
                }
            }

        // corners
        for (int i = FIRST_DIAG; i < NUM_DIRS; i++)
            for (Coord2D pixel : selection) {
                final Coord2D relative = directions[i].relativeCoordinate(),
                        shift = sideMask[i] >= 0 ? relative
                                : new Coord2D(-relative.x, -relative.y);

                final Set<Coord2D> shiftCands = new HashSet<>();
                final int px = Math.abs(sideMask[i]);

                for (int x = 1; x <= px; x++)
                    for (int y = 1; y <= px; y++) {
                        final Coord2D shifted =
                                pixel.displace(shift.x * x, shift.y * y);
                        shiftCands.add(shifted);
                    }

                final int thickness = sideMask[i];
                final Function<Coord2D, Boolean> pass =
                        c -> (thickness > 0) != selection.contains(c);
                shiftCands.stream().filter(cand -> (int) Math.round(
                        Coord2D.unitDistanceBetween(pixel, cand)) <= px &&
                                pass.apply(cand)).forEach(outline::add);
            }

        return outline;
    }
}
