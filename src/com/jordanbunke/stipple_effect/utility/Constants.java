package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;

import java.awt.*;

public class Constants {
    public static final String PROGRAM_NAME = "Stipple Effect";

    public static final int CANVAS_W = 900, CANVAS_H = 500, SCREEN_HEIGHT_BUFFER = 80;

    public static final double TICK_HZ = 100d, FPS = 30d;

    public static final double OPAQUE = 1d, TRANSPARENT = 0d;

    public static final int WORKSPACE_W = 650, TOOLS_W = 24, COLOR_PICKER_W = 226,
            WORKSPACE_H = 400, COLOR_PICKER_H = 200, LAYERS_H = 200, CONTEXTS_H = 76, BOTTOM_BAR_H = 24;

    public static final int BOTTOM_BAR_TEXT_Y_OFFSET = -4,
            TOOL_NAME_X = (int)(CANVAS_W * 0.01),
            TP_X = (int)(CANVAS_W * 0.21),
            SIZE_X = (int)(CANVAS_W * 0.36),
            ZOOM_PCT_X = (int)(CANVAS_W * 0.51);

    public static final Color BACKGROUND = new Color(80, 80, 80),
            BLACK = new Color(0, 0, 0),
            WHITE = new Color(255, 255, 255),
            LIGHT_GREY = new Color(170, 170, 170),
            HIGHLIGHT_1 = new Color(100, 100, 255),
            HIGHLIGHT_2 = new Color(50, 80, 255),
            ACCENT_BACKGROUND = new Color(30, 30, 90);

    public static final int DEFAULT_IMAGE_WIDTH = 48, DEFAULT_IMAGE_HEIGHT = 48, CHECKER_INCREMENT = 4;

    public static final Coord2D TOOL_ICON_DIMS = new Coord2D(20, 20);
    public static final int TOOL_ICON_OFFSET = 2, TOOL_ICON_INC = 22;

    // specific tools
    public static final int DEFAULT_BRUSH_RADIUS = 2;
}
