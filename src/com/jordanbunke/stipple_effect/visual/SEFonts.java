package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.fonts.Font;
import com.jordanbunke.delta_time.fonts.FontFamily;

import java.nio.file.Path;

public class SEFonts {
    public static final Code DEFAULT_FONT = Code.SE_BOLD;

    public enum Code {
        SE_BOLD, SE, CLASSIC, SCHIEF, ZIFFER;

        public String forButtonText() {
            final String name = name();

            return switch (this) {
                case SE -> "SE";
                case SE_BOLD -> "SE Bold";
                default -> name.charAt(0) + name.substring(1).toLowerCase();
            };
        }

        public Code next() {
            final Code[] vals = Code.values();

            return this == vals[vals.length - 1]
                    ? vals[0] : vals[ordinal() + 1];
        }

        public Font associated() {
            return switch (this) {
                case CLASSIC -> SEFonts.CLASSIC.getStandard();
                case SCHIEF -> SEFonts.CLASSIC.getItalics();
                case ZIFFER -> SEFonts.ZIFFER;
                case SE -> SEFonts.SE.getStandard();
                case SE_BOLD -> SEFonts.SE.getBold();
            };
        }
    }

    private static final Path FONT_FOLDER = Path.of("fonts");

    public static final FontFamily CLASSIC = new FontFamily("Classic",
            Font.loadFromSource(FONT_FOLDER, true, "font-classic",
                    false, 0.6, 2, false, true),
            null,
            Font.loadFromSource(FONT_FOLDER, true, "font-classic-italics",
                    false, 0.5, 2, false, true)),
            SE = new FontFamily("SE",
                    Font.loadFromSource(FONT_FOLDER, true, "font-se",
                            true, 0.6, 2, false, true),
                    Font.loadFromSource(FONT_FOLDER, true, "font-se-bold",
                            true, 0.6, 2, false, true),
                    null);

    public static final Font
            ZIFFER = Font.loadFromSource(FONT_FOLDER, true, "font-basic-bold",
            true, 0.6, 2, false, true);
}
