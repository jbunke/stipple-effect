package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Represents a collection of selected pixels
 * */
public final class Selection {
    public static final Selection EMPTY;

    private static final int UNKNOWN = -1;

    static {
        EMPTY = new Selection(new boolean[][] { { false } });
    }

    public final Coord2D topLeft;
    public final Bounds2D bounds;
    private final boolean[][] matrix;

    private Set<Coord2D> pixels;
    private int size;

    private Selection(final Coord2D topLeft,
            final Bounds2D bounds, final boolean[][] matrix) {
        this.topLeft = topLeft;
        this.bounds = bounds;
        this.matrix = matrix;

        pixels = null;
        size = UNKNOWN;
    }

    private Selection(final Coord2D topLeft, final boolean[][] matrix) {
        this(topLeft, new Bounds2D(matrix.length, matrix[0].length), matrix);
    }

    private Selection(final boolean[][] matrix) {
        this(new Coord2D(), matrix);
    }

    public static Selection allInBounds(final int width, final int height) {
        final boolean[][] matrix = new boolean[width][height];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                matrix[x][y] = true;

        return new Selection(matrix);
    }

    public static Selection of(final boolean[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0)
            return EMPTY;

        return new Selection(matrix).crop();
    }

    public static Selection atOf(final Coord2D topLeft,
             final boolean[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0)
            return EMPTY;

        return new Selection(topLeft, matrix);
    }

    public static Selection fromPixels(final Set<Coord2D> pixels) {
        if (pixels == null || pixels.isEmpty())
            return EMPTY;

        final Coord2D topLeft = SelectionUtils.topLeft(pixels),
                bottomRight = SelectionUtils.bottomRight(pixels);
        final Bounds2D bounds = new Bounds2D(bottomRight.x - topLeft.x,
                bottomRight.y - topLeft.y);
        final boolean[][] matrix = new boolean[bounds.width()][bounds.height()];

        for (final Coord2D pixel : pixels)
            matrix[pixel.x - topLeft.x][pixel.y - topLeft.y] = true;

        final Selection selection = new Selection(topLeft, bounds, matrix);
        selection.pixels = new HashSet<>(pixels);
        selection.size = selection.pixels.size();

        return selection;
    }

    public static Selection union(final Selection a, final Selection b) {
        if (!a.hasSelection())
            return b;

        if (!b.hasSelection())
            return a;

        final Coord2D topLeft = new Coord2D(
                Math.min(a.topLeft.x, b.topLeft.x),
                Math.min(a.topLeft.y, b.topLeft.y)
        ), bottomRight = new Coord2D(
                Math.max(a.bottomRight().x, b.bottomRight().x),
                Math.max(a.bottomRight().y, b.bottomRight().y)
        );

        final int w = bottomRight.x - topLeft.x,
                h = bottomRight.y - topLeft.y;

        final boolean[][] res = new boolean[w][h];

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++) {
                final Coord2D px = new Coord2D(topLeft.x + x, topLeft.y + y);

                res[x][y] = a.selected(px) || b.selected(px);
            }

        return new Selection(topLeft, new Bounds2D(w, h), res).crop();
    }

    public static Selection intersection(final Selection a, final Selection b) {
        final Coord2D topLeft = new Coord2D(
                Math.max(a.topLeft.x, b.topLeft.x),
                Math.max(a.topLeft.y, b.topLeft.y)
        ), bottomRight = new Coord2D(
                Math.min(a.bottomRight().x, b.bottomRight().x),
                Math.min(a.bottomRight().y, b.bottomRight().y)
        );

        final int w = bottomRight.x - topLeft.x,
                h = bottomRight.y - topLeft.y;

        final boolean[][] res = new boolean[w][h];

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++) {
                final Coord2D px = new Coord2D(topLeft.x + x, topLeft.y + y);

                res[x][y] = a.selected(px) && b.selected(px);
            }

        return new Selection(topLeft, new Bounds2D(w, h), res).crop();
    }

    public static Selection difference(final Selection a, final Selection b) {
        final boolean[][] res =
                new boolean[a.bounds.width()][a.bounds.height()];

        for (int x = 0; x < a.matrix.length; x++)
            for (int y = 0; y < a.matrix[x].length; y++)
                res[x][y] = a.matrix[x][y] &&
                        !b.selected(a.topLeft.x + x, a.topLeft.y + y);

        return new Selection(a.topLeft, a.bounds, res).crop();
    }

    private Coord2D bottomRight() {
        return topLeft.displace(bounds.width(), bounds.height());
    }

    private Selection crop() {
        if (!hasSelection())
            return EMPTY;

        final Coord2D cropTL = cropTL(), cropBR = cropBR();
        final int w = bounds.width() - (cropTL.x + cropBR.x),
                h = bounds.height() - (cropTL.y + cropBR.y);
        final boolean[][] res = new boolean[w][h];

        for (int x = 0; x < w; x++)
            System.arraycopy(matrix[x + cropTL.x], cropTL.y, res[x], 0, h);

        return new Selection(topLeft.displace(cropTL), res);
    }

    private Coord2D cropTL() {
        final int w = bounds.width();
        int leftmost = w, highest = bounds.height();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < highest; y++)
                if (matrix[x][y]) {
                    leftmost = Math.min(leftmost, x);
                    highest = y;
                }

        return new Coord2D(leftmost, highest);
    }

    private Coord2D cropBR() {
        final int w = bounds.width(), h = bounds.height();
        int rightmost = 0, lowest = 0;

        for (int x = w - 1; x >= 0; x--)
            for (int y = h - 1; y >= lowest; y--)
                if (matrix[x][y]) {
                    rightmost = Math.max(rightmost, x);
                    lowest = y;
                }

        return new Coord2D((w - 1) - rightmost, (h - 1) - lowest);
    }

    public Selection displace(final Coord2D displacement) {
        return new Selection(topLeft.displace(displacement), bounds, matrix);
    }

    public boolean selected(final Coord2D pixel) {
        return selected(pixel.x, pixel.y);
    }

    public boolean selected(final int x, final int y) {
        final int mx = x - topLeft.x, my = y - topLeft.y;

        return inBounds(x, y) && matrix[mx][my];
    }

    private boolean inBounds(final int x, final int y) {
        return x >= topLeft.x && y >= topLeft.y &&
                x < topLeft.x + bounds.width() &&
                y < topLeft.y + bounds.height();
    }

    public boolean hasSelection() {
        if (equals(EMPTY))
            return false;

        if (calculated())
            return !pixels.isEmpty();

        final int w = bounds.width(), h = bounds.height();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (matrix[x][y])
                    return true;

        return false;
    }

    public void pixelAlgorithm(
            final int w, final int h,
            final BiConsumer<Integer, Integer> algorithm
    ) {
        pixelAlgorithm(w, h, true, algorithm);
    }

    public void pixelAlgorithm(
            final int w, final int h, final boolean bounded,
            final BiConsumer<Integer, Integer> algorithm
    ) {
        if (calculated())
            getPixels().stream()
                    .filter(s -> !bounded ||
                            (s.x >= 0 && s.y >= 0 && s.x < w && s.y < h))
                    .forEach(s -> algorithm.accept(s.x, s.y));
        else if (bounded) {
            final int initX = Math.max(0, topLeft.x),
                    initY = Math.max(0, topLeft.y),
                    endX = Math.min(w, topLeft.x + bounds.width()),
                    endY = Math.min(h, topLeft.y + bounds.height());

            for (int x = initX; x < endX; x++)
                for (int y = initY; y < endY; y++)
                    if (selected(x, y))
                        algorithm.accept(x, y);
        } else
            for (int x = 0; x < matrix.length; x++)
                for (int y = 0; y < matrix[x].length; y++)
                    if (matrix[x][y])
                        algorithm.accept(topLeft.x + x, topLeft.y + y);
    }

    public int size() {
        if (!calculated())
            calculate();

        return size;
    }

    public Set<Coord2D> getPixels() {
        if (!calculated())
            calculate();

        return pixels;
    }

    public boolean calculated() {
        return pixels != null && size != UNKNOWN;
    }

    private void calculate() {
        pixels = new HashSet<>();

        for (int x = 0; x < bounds.width(); x++)
            for (int y = 0; y < bounds.height(); y++)
                if (matrix[x][y])
                    pixels.add(new Coord2D(topLeft.x + x, topLeft.y + y));

        size = pixels.size();
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Selection s && topLeft.equals(s.topLeft) &&
                bounds.equals(s.bounds)) {
            if (matrix.length == s.matrix.length) {
                for (int x = 0; x < matrix.length; x++) {
                    if (matrix[x].length != s.matrix.length)
                        return false;

                    for (int y = 0; y < matrix[x].length; y++)
                        if (matrix[x][y] != s.matrix[x][y])
                            return false;
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return topLeft.x + topLeft.y + bounds.width() + bounds.height();
    }
}
