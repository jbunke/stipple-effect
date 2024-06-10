package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a collection of selected pixels
 * */
public final class Selection {
    public final Coord2D topLeft;
    public final Bounds2D bounds;
    private final boolean[][] matrix;

    private Set<Coord2D> pixels;

    public Selection(final Coord2D topLeft,
            final Bounds2D bounds, final boolean[][] matrix) {
        this.topLeft = topLeft;
        this.bounds = bounds;
        this.matrix = matrix;

        pixels = null;
    }

    private Selection(final Coord2D topLeft, final boolean[][] matrix) {
        this(topLeft, new Bounds2D(matrix.length, matrix[0].length), matrix);
    }

    private Selection(final boolean[][] matrix) {
        this(new Coord2D(), matrix);
    }

    public static Selection of(final boolean[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0)
            return null;

        return new Selection(matrix);
    }

    public static Selection atOf(final Coord2D topLeft,
             final boolean[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0)
            return null;

        return new Selection(topLeft, matrix);
    }

    public static Selection fromSet(final Set<Coord2D> pixels) {
        final Coord2D topLeft = SelectionUtils.topLeft(pixels),
                bottomRight = SelectionUtils.bottomRight(pixels);
        final Bounds2D bounds = new Bounds2D(bottomRight.x - topLeft.x,
                bottomRight.y - topLeft.y);
        final boolean[][] matrix = new boolean[bounds.width()][bounds.height()];

        for (final Coord2D pixel : pixels)
            matrix[pixel.x - topLeft.x][pixel.y - topLeft.y] = true;

        return new Selection(topLeft, bounds, matrix);
    }

    public boolean selected(final Coord2D check) {
        final int mx = topLeft.x - check.x, my = topLeft.y - check.y;

        return inBounds(check) && matrix[mx][my];
    }

    private boolean inBounds(final Coord2D check) {
        return check.x >= topLeft.x && check.y >= topLeft.y &&
                check.x < topLeft.x + bounds.width() &&
                check.y < topLeft.y + bounds.height();
    }

    public Set<Coord2D> getPixels() {
        if (pixels == null) {
            pixels = new HashSet<>();

            for (int x = 0; x < bounds.width(); x++)
                for (int y = 0; y < bounds.height(); y++)
                    if (matrix[x][y])
                        pixels.add(new Coord2D(topLeft.x + x, topLeft.y + y));
        }

        return pixels;
    }
}
