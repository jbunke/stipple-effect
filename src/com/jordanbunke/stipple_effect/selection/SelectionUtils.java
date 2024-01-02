package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.tools.MoverTool;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class SelectionUtils {
    public static GameImage drawOverlay(
            final Set<Coord2D> selection,
            final BiFunction<Integer, Integer, Boolean> maskValidator,
            final double z, final boolean movable, final boolean canTransform
    ) {
        final Coord2D topLeft = topLeft(selection),
                bottomRight = bottomRight(selection);

        final int w = bottomRight.x - topLeft.x,
                h = bottomRight.y - topLeft.y;

        return GraphicsUtils.drawOverlay(w, h, z,
                (x, y) -> maskValidator.apply(x + topLeft.x, y + topLeft.y),
                Constants.BLACK, Constants.HIGHLIGHT_1, movable, canTransform);
    }

    public static Coord2D topLeft(final Set<Coord2D> pixels) {
        int lowestX = Integer.MAX_VALUE, lowestY = Integer.MAX_VALUE;

        for (Coord2D pixel : pixels) {
            lowestX = Math.min(lowestX, pixel.x);
            lowestY = Math.min(lowestY, pixel.y);
        }

        return new Coord2D(lowestX, lowestY);
    }

    public static Coord2D bottomRight(final Set<Coord2D> pixels) {
        int lowestX = Integer.MIN_VALUE, lowestY = Integer.MIN_VALUE;

        for (Coord2D pixel : pixels) {
            lowestX = Math.max(lowestX, pixel.x + 1);
            lowestY = Math.max(lowestY, pixel.y + 1);
        }

        return new Coord2D(lowestX, lowestY);
    }

    public static Set<Coord2D> stretchedPixels(
            final Set<Coord2D> oldPixels, final Coord2D change,
            final MoverTool.Direction direction
    ) {
        final Coord2D oldTL = SelectionUtils.topLeft(oldPixels),
                oldBR = SelectionUtils.bottomRight(oldPixels);

        final int oldW = oldBR.x - oldTL.x,
                oldH = oldBR.y - oldTL.y;

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

        final Set<Coord2D> pixels = new HashSet<>();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++){
                final Coord2D oldPixel = oldTL.displace(
                        (int)((x / (double) w) * oldW),
                        (int)((y / (double) h) * oldH));

                if (oldPixels.contains(oldPixel))
                    pixels.add(tl.displace(x, y));
            }

        return pixels;
    }

    public static double calculateAngleInRad(
            final Coord2D point, final Coord2D pivot
    ) {
        return Math.atan2(point.y - pivot.y, point.x - pivot.x);
    }

    public static double calculateAngleInRad(
            final double px, final double py,
            final double pivotX, final double pivotY
    ) {
        return Math.atan2(py - pivotY, px - pivotX);
    }

    public static Set<Coord2D> rotatedPixels(
            final Set<Coord2D> initialSelection,
            final double deltaR, final Coord2D pivot, final boolean[] offset
    ) {
        final int X = 0, Y = 1;

        final double[] realPivot = new double[] {
                pivot.x + (offset[X] ? -0.5 : 0d),
                pivot.y + (offset[Y] ? -0.5 : 0d)
        };
        final Set<Coord2D> pixels = new HashSet<>();

        initialSelection.forEach(i -> {
            final double distance = Math.sqrt(
                    Math.pow(realPivot[X] - i.x, 2) +
                            Math.pow(realPivot[Y] - i.y, 2)),
                    angle = calculateAngleInRad(i.x, i.y,
                            realPivot[X], realPivot[Y]),
                    newAngle = angle + deltaR;

            final double deltaX = distance * Math.cos(newAngle),
                    deltaY = distance * Math.sin(newAngle);

            final Coord2D newPixel = new Coord2D(
                    (int)Math.round(realPivot[X] + deltaX),
                    (int)Math.round(realPivot[Y] + deltaY)
            );
            pixels.add(newPixel);
        });

        final Coord2D tl = topLeft(pixels), br = bottomRight(pixels);

        for (int x = tl.x; x < br.x; x++) {
            for (int y = tl.y; y < br.y; y++) {
                if (pixels.contains(new Coord2D(x, y)))
                    continue;

                final double distance = Math.sqrt(
                        Math.pow(realPivot[X] - x, 2) +
                                Math.pow(realPivot[Y] - y, 2)),
                        angle = calculateAngleInRad(x, y,
                                realPivot[X], realPivot[Y]),
                        oldAngle = angle - deltaR;

                final double deltaX = distance * Math.cos(oldAngle),
                        deltaY = distance * Math.sin(oldAngle);

                final Coord2D oldPixel = new Coord2D(
                        (int)Math.round(realPivot[X] + deltaX),
                        (int)Math.round(realPivot[Y] + deltaY)
                );

                if (initialSelection.contains(oldPixel))
                    pixels.add(new Coord2D(x, y));
            }
        }

        return pixels;
    }
}
