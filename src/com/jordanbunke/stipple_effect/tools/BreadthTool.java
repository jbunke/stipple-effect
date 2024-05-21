package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.utility.math.Coord2D;

public interface BreadthTool {
    default int breadthOffset() {
        return (getBreadth() / 2) + (getBreadth() % 2);
    }

    default boolean[][] breadthMask() {
        final int remainder = getBreadth() % 2, halfB = breadthOffset();

        final boolean[][] mask = new boolean[halfB * 2][halfB * 2];

        for (int x = 0; x < halfB * 2; x++)
            for (int y = 0; y < halfB * 2; y++) {
                final double distance = remainder == 1
                        ? Coord2D.unitDistanceBetween(new Coord2D(x, y),
                        new Coord2D(halfB, halfB))
                        : Coord2D.unitDistanceBetween(
                        new Coord2D((2 * x) + 1, (2 * y) + 1),
                        new Coord2D(halfB * 2, halfB * 2));
                double threshold = remainder == 1
                        ? getBreadth() / 2. : (double) getBreadth();

                // special case
                if (getBreadth() == 3)
                    threshold = Math.floor(threshold);

                if (distance <= threshold)
                    mask[x][y] = true;
            }

        return mask;
    }

    int getBreadth();
    void setBreadth(final int breadth);

    default void increaseBreadth() {
        setBreadth(getBreadth() + 1);
    }

    default void decreaseBreadth() {
        setBreadth(getBreadth() - 1);
    }
}
