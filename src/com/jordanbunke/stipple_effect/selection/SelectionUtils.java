package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

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
}
