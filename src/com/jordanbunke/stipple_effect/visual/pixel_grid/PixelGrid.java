package com.jordanbunke.stipple_effect.visual.pixel_grid;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.project.ZoomLevel;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PixelGrid {
    private static final Map<ZoomLevel, GridSections> gridMap;

    static {
        gridMap = new HashMap<>();
        redraw();
    }

    public static boolean hasFor(final ZoomLevel zl) {
        return gridMap.containsKey(zl);
    }

    public static GridSections get(final ZoomLevel zl) {
        return gridMap.get(zl);
    }

    private static void clear() {
        gridMap.clear();
    }

    public static void redraw() {
        clear();

        final int pgx = Settings.getPixelGridXPixels(),
                pgy = Settings.getPixelGridYPixels();

        for (final ZoomLevel zl : ZoomLevel.values()) {
            final int z = (int) Math.ceil(zl.z),
                    altPx = Math.max(2, z / Layout.PIXEL_GRID_COLOR_ALT_DIVS);

            final boolean tooSmall = z == 0 ||
                    (int)(Math.min(pgx, pgy) * zl.z) == 0;

            if (tooSmall)
                continue;

            final GameImage vert = new GameImage(1, (int)(pgy * zl.z)),
                    horz = new GameImage((int)(pgx * zl.z), 1),
                    vertPixel = new GameImage(1, z),
                    horzPixel = new GameImage(z, 1);

            final Color a = new Color(0, 0, 0, 150),
                    b = new Color(100, 100, 100, 150);

            // populate lines
            // pixel stretches
            for (int zInc = 0; zInc < z; zInc += altPx) {
                final Color c = (zInc / altPx) % 2 == 0 ? a : b;

                vertPixel.fillRectangle(c, 0, zInc, 1, altPx);
                horzPixel.fillRectangle(c, zInc, 0, altPx, 1);
            }

            vertPixel.free();
            horzPixel.free();

            // vertical
            for (int y = 0; y < pgy; y++)
                vert.draw(vertPixel, 0, (int)(y * zl.z));

            vert.free();

            // horizontal
            for (int x = 0; x < pgx; x++)
                horz.draw(horzPixel, (int)(x * zl.z), 0);

            horz.free();

            gridMap.put(zl, new GridSections(vert, horz));
        }
    }
}
