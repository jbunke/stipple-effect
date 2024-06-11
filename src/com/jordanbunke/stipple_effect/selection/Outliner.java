package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

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

    public static Selection outline(
            final Selection selection, final int[] sideMask
    ) {
        if (sideMask.length != NUM_DIRS)
            return selection;

        final Set<Coord2D> outline = new HashSet<>();
        final Map<Direction, Set<Coord2D>> frontier =
                calculateFrontier(selection, sideMask);

        final Direction[] directions = Direction.values();

        // edges
        for (int i = 0; i < FIRST_DIAG; i++) {
            if (sideMask[i] == 0)
                continue;

            final Direction dir = directions[i];
            final Coord2D relative = dir.relativeCoordinate();
            final int externalBound =
                    Math.min(sideMask[i], Constants.MAX_OUTLINE_PX),
                    internalBound =
                            Math.min(-sideMask[i], Constants.MAX_OUTLINE_PX);
            final boolean internal = sideMask[i] < 0;

            for (Coord2D pixel : frontier.get(dir)) {
                final Coord2D shift = internal
                        ? new Coord2D(-relative.x, -relative.y) : relative;
                final int bound = internal ? internalBound : externalBound;

                Coord2D shifted = internal ? pixel : pixel.displace(relative);

                if (internal && !frontier.get(directions[i]).contains(shifted))
                    continue;

                for (int s = 0; s < bound &&
                        selection.selected(shifted) == internal; s++) {
                    outline.add(shifted);
                    shifted = shifted.displace(shift);
                }
            }
        }

        // corners
        for (int i = FIRST_DIAG; i < NUM_DIRS; i++) {
            if (sideMask[i] == 0)
                continue;

            final Direction dir = directions[i];
            final Coord2D relative = dir.relativeCoordinate();
            final boolean internal = sideMask[i] < 0;

            for (Coord2D pixel : frontier.get(dir)) {
                final Coord2D shift = internal
                        ? new Coord2D(-relative.x, -relative.y) : relative;

                final Set<Coord2D> shiftCands = new HashSet<>();
                final int px = Math.abs(sideMask[i]),
                        start = internal ? 0 : 1, end = px + start;

                for (int x = start; x < end; x++)
                    for (int y = start; y < end; y++) {
                        final Coord2D shifted =
                                pixel.displace(shift.x * x, shift.y * y);

                        if (!outline.contains(shifted))
                            shiftCands.add(shifted);
                    }

                final Function<Coord2D, Boolean> pass =
                        c -> internal == selection.selected(c);
                shiftCands.stream().filter(cand -> (int) Math.round(
                        Coord2D.unitDistanceBetween(pixel, cand)) <= (end - 1) &&
                        pass.apply(cand)).forEach(outline::add);
            }
        }

        return Selection.fromPixels(outline);
    }

    private static Map<Direction, Set<Coord2D>> calculateFrontier(
            final Selection selection, final int[] sideMask
    ) {
        final Map<Direction, Set<Coord2D>> frontier = new HashMap<>();

        for (Direction dir : Direction.values()) {
            final Set<Coord2D> dirFrontier = new HashSet<>();
            final boolean diag = dir.ordinal() >= FIRST_DIAG,
                    internal = sideMask[dir.ordinal()] < 0;
            final Coord2D rc = dir.relativeCoordinate();

            final Predicate<Coord2D> check = px -> {
                if (selection.selected(px.displace(rc)))
                    return false;

                if (diag) {
                    final boolean
                            compX = selection.selected(px.displace(rc.x, 0)),
                            compY = selection.selected(px.displace(0, rc.y));

                    return internal ? compX && compY : !(compX || compY);
                }

                return true;
            };

            selection.pixelAlgorithm(0, 0, false, (x, y) -> {
                final Coord2D px = new Coord2D(x, y);

                if (check.test(px))
                    dirFrontier.add(px);
            });

            frontier.put(dir, dirFrontier);
        }

        return frontier;
    }
}
