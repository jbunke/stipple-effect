package com.jordanbunke.stipple_effect.palette;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PaletteLoader {
    // palette codes
    private static final String NES_PALETTE = "nes",
            PICO_8_PALETTE = "pico_8",
            WINDOWS_PALETTE = "win";

    public static List<Palette> loadOnStartup() {
        final List<Palette> palettes = new ArrayList<>();

        loadHardCodedPalettes(palettes);

        return palettes;
    }

    private static void loadHardCodedPalettes(final List<Palette> palettes) {
        palettes.add(new Palette("Black & White (1-bit)",
                new Color[] { SEColors.black(), SEColors.white() }, false));
        palettes.add(new Palette("NES",
                paletteColorsFromCode(NES_PALETTE), false));
        palettes.add(new Palette("PICO-8",
                paletteColorsFromCode(PICO_8_PALETTE), false));
        palettes.add(new Palette("Windows",
                paletteColorsFromCode(WINDOWS_PALETTE), false));
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
            final Selection selection
    ) {
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                if (colors.size() >= Constants.MAX_PALETTE_SIZE)
                    return;

                final Color c = image.getColorAt(x, y);

                if (c.getAlpha() == 0)
                    continue;

                if (!colors.contains(c) && (selection == null ||
                        selection.selected(x, y)))
                    colors.add(c);
            }
    }
}
