package com.jordanbunke.stipple_effect.color_selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PaletteLoader {
    // palette codes
    private static final String NES_PALETTE = "nes", WINDOWS_PALETTE = "win";

    public static List<Palette> loadOnStartup() {
        final List<Palette> palettes = new ArrayList<>();

        loadHardCodedPalettes(palettes);
        // TODO - *attempt* palette retrieval from reserved palette folder

        return palettes;
    }

    private static void loadHardCodedPalettes(final List<Palette> palettes) {
        palettes.add(new Palette("Black & White (1-bit)",
                new Color[] { Constants.BLACK, Constants.WHITE }));
        palettes.add(new Palette("NES", paletteColorsFromCode(NES_PALETTE)));
        palettes.add(new Palette("Windows",
                paletteColorsFromCode(WINDOWS_PALETTE)));
    }

    private static Color[] paletteColorsFromCode(final String code) {
        final Path filepath = Constants.PALETTE_FOLDER.resolve(code + ".png");
        final GameImage image = ResourceLoader.loadImageResource(filepath);
        final List<Color> colors = new ArrayList<>();

        addPaletteColorsFromImage(image, colors, null);
        return colors.toArray(Color[]::new);
    }

    public static void addPaletteColorsFromImage(
            final GameImage image, final List<Color> colors,
            final Set<Coord2D> pixels
    ) {
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                final Color c = ImageProcessing.colorAtPixel(image, x, y);

                if (c.getAlpha() == 0)
                    continue;

                if (!colors.contains(c) && (pixels == null ||
                        pixels.contains(new Coord2D(x, y)))) {
                    colors.add(c);

                    if (colors.size() >= Constants.MAX_PALETTE_SIZE)
                        return;
                }
            }
    }
}
