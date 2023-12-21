package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.fonts.Font;
import com.jordanbunke.delta_time.fonts.FontFamily;

import java.nio.file.Path;

public class SEFonts {
    private static final Path FONT_FOLDER = Path.of("fonts");

    public static final FontFamily CLASSIC = new FontFamily("Classic",
            Font.loadFromSource(FONT_FOLDER, true, "font-classic",
                    false, 0.6, 2, false, true),
            null,
            Font.loadFromSource(FONT_FOLDER, true, "font-classic-italics",
                    false, 0.6, 0, false, false));

    public static final Font SMALL = Font.loadFromSource(FONT_FOLDER, true,
            "font-arcade", false, 1, false);
    public static final Font BASIC = Font.loadFromSource(FONT_FOLDER, true,
            "font-basic-bold", true, 0.6, 2, false, true);
}
