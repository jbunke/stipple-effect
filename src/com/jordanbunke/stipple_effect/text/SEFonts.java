package com.jordanbunke.stipple_effect.text;

import com.jordanbunke.delta_time.fonts.FontFamily;

import java.nio.file.Path;

public class SEFonts {
    private static final Path FONT_FOLDER = Path.of("fonts");

    public static final FontFamily CLASSIC = FontFamily.loadFromSources(
            "Classic", FONT_FOLDER, true, "font-classic",
            FontFamily.NOT_AVAILABLE, "font-classic-italics", 2, 0, 0, true, false
    );
}
