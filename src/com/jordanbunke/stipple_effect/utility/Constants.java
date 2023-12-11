package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;

import java.awt.*;

public class Constants {
    public static final String PROGRAM_NAME = "Stipple Effect";

    public static final int CANVAS_W = 960, CANVAS_H = 540, SCREEN_HEIGHT_BUFFER = 80;

    public static final double TICK_HZ = 100d, FPS = 30d;

    public static final double OPAQUE = 1d, TRANSPARENT = 0d;

    public static final int WORKSPACE_W = 680, CONTEXTS_W = CANVAS_W / 2,
            FRAMES_W = CANVAS_W / 2, TOOLS_W = 24, COLOR_PICKER_W = 256,
            WORKSPACE_H = 432, COLOR_PICKER_H = WORKSPACE_H / 2, LAYERS_H = WORKSPACE_H / 2,
            CONTEXTS_H = 84, BOTTOM_BAR_H = 24;

    public static final int TEXT_Y_OFFSET = -4,
            TOOL_NAME_X = (int)(CANVAS_W * 0.01),
            TP_X = (int)(CANVAS_W * 0.21),
            SIZE_X = (int)(CANVAS_W * 0.36),
            ZOOM_PCT_X = (int)(CANVAS_W * 0.51);

    public static final Color BACKGROUND = new Color(80, 80, 80),
            BLACK = new Color(0, 0, 0),
            WHITE = new Color(255, 255, 255),
            LIGHT_GREY = new Color(170, 170, 170),
            HIGHLIGHT_1 = new Color(100, 100, 255),
            HIGHLIGHT_2 = new Color(50, 80, 255, 100),
            ACCENT_BACKGROUND = new Color(30, 30, 90);

    public static final int DEFAULT_IMAGE_WIDTH = 48, DEFAULT_IMAGE_HEIGHT = 48, CHECKER_INCREMENT = 4;

    public static final int BUTTON_DIM = 20, STD_TEXT_BUTTON_W = 80, STD_TEXT_BUTTON_H = 24;
    public static final Coord2D TOOL_ICON_DIMS = new Coord2D(BUTTON_DIM, BUTTON_DIM);
    public static final int BUTTON_OFFSET = 2, BUTTON_INC = BUTTON_DIM + BUTTON_OFFSET, BUTTON_BORDER_PX = 2;

    // specific tools
    public static final int DEFAULT_BRUSH_RADIUS = 2, MIN_RADIUS = 0, MAX_RADIUS = 200;
}
