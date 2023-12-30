package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.fonts.Font;
import com.jordanbunke.delta_time.fonts.FontFamily;

import java.nio.file.Path;

public class SEFonts {
    public enum Code {
        PICCOLO, CLASSIC, SCHIEF, ZIFFER;

        public String forButtonText() {
            final String name = name();

            return name.charAt(0) + name.substring(1).toLowerCase();
        }

        public Code next() {
            final Code[] vals = Code.values();

            return this == vals[vals.length - 1]
                    ? PICCOLO : vals[ordinal() + 1];
        }

        public Font associated() {
            return switch (this) {
                case CLASSIC -> SEFonts.CLASSIC.getStandard();
                case SCHIEF -> SEFonts.CLASSIC.getItalics();
                case PICCOLO -> SEFonts.PICCOLO;
                case ZIFFER -> SEFonts.ZIFFER;
            };
        }
    }

    private static final Path FONT_FOLDER = Path.of("fonts");

    public static final FontFamily CLASSIC = new FontFamily("Classic",
            Font.loadFromSource(FONT_FOLDER, true, "font-classic",
                    false, 0.6, 2, false, true),
            null,
            Font.loadFromSource(FONT_FOLDER, true, "font-classic-italics",
                    false, 0.5, 2, false, true));

    public static final Font
            PICCOLO = Font.loadFromSource(FONT_FOLDER, true,
            "font-piccolo", true, 0.6, 1, false, true),
            ZIFFER = Font.loadFromSource(FONT_FOLDER, true,
                    "font-basic-bold", true, 0.6, 2, false, true);
}
