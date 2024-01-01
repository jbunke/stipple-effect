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
            final double z, final boolean moveable
    ) {
        final Coord2D topLeft = topLeft(selection),
                bottomRight = bottomRight(selection);

        final int w = bottomRight.x - topLeft.x,
                h = bottomRight.y - topLeft.y;

        return GraphicsUtils.drawOverlay(w, h, z,
                (x, y) -> maskValidator.apply(x + topLeft.x, y + topLeft.y),
                Constants.BLACK, Constants.HIGHLIGHT_1, moveable);
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

        final Coord2D corner1 = switch (direction) {
            case TL, T, L -> oldTL.displace(change);
            case TR -> oldTL.displace(0, change.y);
            case BL -> oldTL.displace(change.x, 0);
            default -> oldTL;
        };

        final Coord2D corner2 = switch (direction) {
            case BR, B, R -> oldBR.displace(change);
            case TR -> oldBR.displace(change.x, 0);
            case BL -> oldBR.displace(0, change.y);
            default -> oldBR;
        };

        final Coord2D tl = new Coord2D(Math.min(corner1.x, corner2.x),
                Math.min(corner1.y, corner2.y)),
                br = new Coord2D(Math.max(corner1.x, corner2.x),
                        Math.max(corner1.y, corner2.y));

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
}
