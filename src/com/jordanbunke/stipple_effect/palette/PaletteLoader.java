package com.jordanbunke.stipple_effect.palette;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;
import com.jordanbunke.stipple_effect.visual.menu_elements.navigation.logic.SimpleActionItem;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PaletteLoader {
    public enum Preset {
        BW, NES, PICO_8, WINDOWS;

        @Override
        public String toString() {
            return switch (this) {
                case BW -> "Black & White (1-bit)";
                case NES -> "NES";
                case PICO_8 -> "PICO-8";
                case WINDOWS -> "Windows";
            };
        }

        private String code() {
            return switch (this) {
                case BW -> "bw";
                case NES -> "nes";
                case PICO_8 -> "pico_8";
                case WINDOWS -> "win";
            };
        }

        private Palette load() {
            return new Palette(toString(), this == BW
                    ? new Color[] { SEColors.black(), SEColors.white() }
                    : paletteColorsFromCode(code()));
        }

        public SimpleActionItem toItem() {
            return new SimpleActionItem(
                    ResourceCodes.PALETTE_PRESET_PREFIX + code(),
                    () -> StippleEffect.get().addPalette(load(), true), true);
        }
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
