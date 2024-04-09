package com.jordanbunke.stipple_effect.visual.color;

import java.awt.*;

public class SEColors {
    static final Color
            MID_DARK_GREY = new Color(80, 80, 80),
            BLACK = new Color(0, 0, 0),
            WHITE = new Color(255, 255, 255),
            LIGHT_GREY = new Color(192, 192, 192),
            NAVY = new Color(40, 40, 60),
            GREY = new Color(127, 127, 127),
            DARK_GREY = new Color(55, 55, 55),
            DARK_RED = new Color(100, 20, 20),
            VEIL = new Color(192, 192, 192, 200),
            PASTEL_BLUE = new Color(100, 100, 255),
            TRANSLUCENT_BLUE_1 = new Color(50, 80, 255, 100),
            TRANSLUCENT_BLUE_2 = new Color(50, 80, 255, 50),
            DARK_OIL = new Color(23, 16, 1),
            FOLIAGE = new Color(18, 45, 4),
            GOLD = new Color(210, 169, 34),
            DARK_GOLD = new Color(77, 60, 10),
            TRANSLUCENT_GOLD = new Color(210, 169, 34, 100),
            TRANSPARENT = new Color(0, 0, 0, 0);

    public static Color black() {
        return BLACK;
    }

    public static Color white() {
        return WHITE;
    }

    public static Color transparent() {
        return TRANSPARENT;
    }

    public static Color def() {
        return BLACK;
    }
}
