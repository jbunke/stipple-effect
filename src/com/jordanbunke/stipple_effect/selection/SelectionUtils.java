package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.tools.MoverTool;
import com.jordanbunke.stipple_effect.utility.math.Geometry;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SelectionUtils {
    private static final int X = 0, Y = 1;

    public static Coord2D topLeft(final Collection<Coord2D> pixels) {
        int lowestX = Integer.MAX_VALUE, lowestY = Integer.MAX_VALUE;

        for (Coord2D pixel : pixels) {
            lowestX = Math.min(lowestX, pixel.x);
            lowestY = Math.min(lowestY, pixel.y);
        }

        return new Coord2D(lowestX, lowestY);
    }

    public static Coord2D bottomRight(final Collection<Coord2D> pixels) {
        int lowestX = Integer.MIN_VALUE, lowestY = Integer.MIN_VALUE;

        for (Coord2D pixel : pixels) {
            lowestX = Math.max(lowestX, pixel.x + 1);
            lowestY = Math.max(lowestY, pixel.y + 1);
        }

        return new Coord2D(lowestX, lowestY);
    }

    public static Selection stretchedPixels(
            final Selection oldPixels, final Coord2D change,
            final MoverTool.Direction direction
    ) {
        final int oldW = oldPixels.bounds.width(),
                oldH = oldPixels.bounds.height();
        final Coord2D oldTL = oldPixels.topLeft,
                oldBR = oldTL.displace(oldW, oldH);

        final Coord2D tl = switch (direction) {
            case TL, T, L -> oldTL.displace(change);
            case TR -> oldTL.displace(0, change.y);
            case BL -> oldTL.displace(change.x, 0);
            default -> oldTL;
        };

        final Coord2D br = switch (direction) {
            case BR, B, R -> oldBR.displace(change);
            case TR -> oldBR.displace(change.x, 0);
            case BL -> oldBR.displace(0, change.y);
            default -> oldBR;
        };

        final int w = br.x - tl.x, h = br.y - tl.y;

        if (w < 1 || h < 1)
            return Selection.EMPTY;

        final boolean[][] matrix = new boolean[w][h];

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++) {
                final Coord2D oldPixel = oldTL.displace(
                        (int)((x / (double) w) * oldW),
                        (int)((y / (double) h) * oldH));

                matrix[x][y] = oldPixels.selected(oldPixel);
            }

        return Selection.atOf(tl, matrix);
    }

    public static Selection rotatedPixels(
            final Selection initialSelection,
            final double deltaR, final Coord2D pivot, final boolean[] offset
    ) {
        final double[] realPivot = new double[] {
                pivot.x + (offset[X] ? -0.5 : 0d),
                pivot.y + (offset[Y] ? -0.5 : 0d)
        };

        final int initW = initialSelection.bounds.width(),
                initH = initialSelection.bounds.height();
        final Coord2D initTL = initialSelection.topLeft,
                initBR = initTL.displace(initW, initH),
                initTR = new Coord2D(initBR.x, initTL.y),
                initBL = new Coord2D(initTL.x, initBR.y);

        final Set<Coord2D> initCorners = Set.of(initTL, initBR, initTR, initBL),
                rotatedCorners = new HashSet<>();

        initCorners.forEach(c -> {
            final double distance = Math.sqrt(
                    Math.pow(realPivot[X] - c.x, 2) +
                            Math.pow(realPivot[Y] - c.y, 2)),
                    angle = Geometry.calculateAngleInRad(c.x, c.y,
                            realPivot[X], realPivot[Y]),
                    newAngle = angle + deltaR;

            final double deltaX = distance * Math.cos(newAngle),
                    deltaY = distance * Math.sin(newAngle);

            final Coord2D newPixel = new Coord2D(
                    (int)Math.round(realPivot[X] + deltaX),
                    (int)Math.round(realPivot[Y] + deltaY)
            );

            rotatedCorners.add(newPixel);
        });

        final Coord2D tl = topLeft(rotatedCorners),
                br = bottomRight(rotatedCorners);
        final int w = br.x - tl.x, h = br.y - tl.y;

        final boolean[][] matrix = new boolean[w][h];

        for (int x = tl.x; x < br.x; x++) {
            for (int y = tl.y; y < br.y; y++) {
                final double distance = Math.sqrt(
                        Math.pow(realPivot[X] - x, 2) +
                                Math.pow(realPivot[Y] - y, 2)),
                        angle = Geometry.calculateAngleInRad(x, y,
                                realPivot[X], realPivot[Y]),
                        oldAngle = angle - deltaR;

                final double deltaX = distance * Math.cos(oldAngle),
                        deltaY = distance * Math.sin(oldAngle);

                final Coord2D oldPixel = new Coord2D(
                        (int)Math.round(realPivot[X] + deltaX),
                        (int)Math.round(realPivot[Y] + deltaY)
                );

                if (initialSelection.selected(oldPixel))
                    matrix[x - tl.x][y - tl.y] = true;
            }
        }

        return Selection.forRotation(tl, matrix);
    }

    public static Selection reflectedPixels(
            final Selection initialSelection, final boolean horizontal
    ) {
        final int w = initialSelection.bounds.width(),
                h = initialSelection.bounds.height();
        final Coord2D tl = initialSelection.topLeft;

        final boolean[][] matrix = new boolean[w][h];

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++) {
                final boolean was = initialSelection
                        .selected(tl.displace(x, y));

                final int refX = horizontal ? (w - 1) - x : x,
                        refY = horizontal ? y : (h - 1) - y;
                matrix[refX][refY] = was;
            }

        return Selection.atOf(tl, matrix);
    }
}
