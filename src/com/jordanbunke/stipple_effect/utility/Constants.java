package com.jordanbunke.stipple_effect.utility;

import java.awt.*;

public class Constants {
    public static final String PROGRAM_NAME = "Stipple Effect";

    public static final int CANVAS_W = 900, CANVAS_H = 500, SCALE_UP = 2;

    public static final double TICK_HZ = 50d, FPS = 30d;

    public static final double OPAQUE = 1d, TRANSPARENT = 0d;

    public static final int WORKSPACE_W = 650, TOOLS_W = 36, COLOR_PICKER_W = 214,
            WORKSPACE_H = 400, COLOR_PICKER_H = 200, LAYERS_H = 200, CONTEXTS_H = 76, BOTTOM_BAR_H = 24;

    public static final int BOTTOM_BAR_TEXT_Y_OFFSET = -4, TP_X = (int)(CANVAS_W * 0.01), SIZE_X = (int)(CANVAS_W * 0.16);

    public static final Color BACKGROUND = new Color(80, 80, 80),
            BLACK = new Color(0, 0, 0),
            WHITE = new Color(255, 255, 255),
            LIGHT_GREY = new Color(170, 170, 170),
            ACCENT_BACKGROUND = new Color(30, 30, 90);

    public static final int DEFAULT_IMAGE_WIDTH = 48, DEFAULT_IMAGE_HEIGHT = 48, CHECKER_INCREMENT = 4;
}
