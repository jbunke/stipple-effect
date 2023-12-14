package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;

import java.awt.*;
import java.nio.file.Path;

public class Constants {
    public static final String PROGRAM_NAME = "Stipple Effect";

    public static final Path ICON_FOLDER = Path.of("icons");

    public static final int CANVAS_W = 960, CANVAS_H = 540, SCREEN_H_BUFFER = 80;

    public static final double TICK_HZ = 100d, FPS = 30d;

    public static final double OPAQUE = 1d, TRANSPARENT = 0d;

    public static final int WORKSPACE_W = 650, CONTEXTS_W = CANVAS_W / 2,
            FRAMES_W = CANVAS_W / 2, TOOLS_W = 24, COLOR_PICKER_W = 286,
            WORKSPACE_H = 432, COLOR_PICKER_H = WORKSPACE_H / 2, LAYERS_H = WORKSPACE_H / 2,
            CONTEXTS_H = 84, BOTTOM_BAR_H = 24;

    public static Coord2D getProjectsPosition() {
        return new Coord2D();
    }

    public static Coord2D getFramesPosition() {
        return getProjectsPosition().displace(CONTEXTS_W, 0);
    }

    public static Coord2D getToolsPosition() {
        return getProjectsPosition().displace(0, CONTEXTS_H);
    }

    public static Coord2D getWorkspacePosition() {
        return getToolsPosition().displace(TOOLS_W, 0);
    }

    public static Coord2D getLayersPosition() {
        return getWorkspacePosition().displace(WORKSPACE_W, 0);
    }

    public static Coord2D getColorsPosition() {
        return getLayersPosition().displace(0, LAYERS_H);
    }

    public static Coord2D getBottomBarPosition() {
        return getToolsPosition().displace(0, WORKSPACE_H);
    }

    public static Coord2D getSegmentContentDisplacement() {
        return new Coord2D(Constants.TOOL_NAME_X, Constants.SEGMENT_TITLE_CONTENT_OFFSET_Y);
    }

    public static final int TEXT_Y_OFFSET = -4,
            TOOL_NAME_X = (int)(CANVAS_W * 0.01),
            TP_X = (int)(CANVAS_W * 0.21),
            SIZE_X = (int)(CANVAS_W * 0.36),
            ZOOM_PCT_X = (int)(CANVAS_W * 0.51);

    public static final String BASE_LAYER_NAME = "Background", UNTITLED_PROJECT_NAME = "[ Untitled ]";

    public static final Color BACKGROUND = new Color(80, 80, 80),
            BLACK = new Color(0, 0, 0),
            WHITE = new Color(255, 255, 255),
            ACCENT_BACKGROUND_LIGHT = new Color(180, 180, 231),
            GREY = new Color(127, 127, 127),
            DARK = new Color(55, 55, 55),
            HIGHLIGHT_1 = new Color(100, 100, 255),
            HIGHLIGHT_2 = new Color(50, 80, 255, 100),
            ACCENT_BACKGROUND_DARK = new Color(30, 30, 90);

    public static final int DEFAULT_IMAGE_W = 48, DEFAULT_IMAGE_H = 48, CHECKER_INCREMENT = 4;

    public static final int BUTTON_DIM = 20, BUTTON_OFFSET = 2,
            BUTTON_INC = BUTTON_DIM + BUTTON_OFFSET, BUTTON_BORDER_PX = 2,
            SEGMENT_TITLE_BUTTON_OFFSET_X = 74, SEGMENT_TITLE_CONTENT_OFFSET_Y = 30,
            LAYER_BUTTON_W = 110, LAYER_OPACITY_SLIDER_W = 90, LAYERS_ABOVE_TO_DISPLAY = 2,
            FRAME_BUTTON_W = 40,
            PROJECT_NAME_BUTTON_PADDING_W = 20, SPACE_BETWEEN_PROJECT_BUTTONS_X = 8, PROJECTS_BEFORE_TO_DISPLAY = 1,
            VERT_SCROLL_WINDOW_W = COLOR_PICKER_W - (2 * TOOL_NAME_X), VERT_SCROLL_WINDOW_H = (int)(LAYERS_H * 0.8),
            FRAME_SCROLL_WINDOW_W = FRAMES_W - (2 * TOOL_NAME_X), FRAME_SCROLL_WINDOW_H = (int)(CONTEXTS_H * 0.56),
            STD_TEXT_BUTTON_W = 88, STD_TEXT_BUTTON_H = 25,
            STD_TEXT_BUTTON_INC = STD_TEXT_BUTTON_H + BUTTON_OFFSET, BUTTON_TEXT_OFFSET_Y = -6,
            COLOR_SELECTOR_OFFSET_Y = 44, COLOR_SELECTOR_INC_Y = 44, COLOR_BUTTON_AVG_C_THRESHOLD = 100,
            COLOR_SLIDER_W = 256, SLIDER_OFF_DIM = 20, SLIDER_BALL_DIM = 20, SLIDER_THINNING = 4,
            COLOR_LABEL_OFFSET_Y = -18, DYNAMIC_LABEL_H = 40, DYNAMIC_LABEL_W_ALLOWANCE = 100;

    public static final Coord2D TOOL_ICON_DIMS = new Coord2D(BUTTON_DIM, BUTTON_DIM);

    // specific tools
    public static final int DEFAULT_BRUSH_BREADTH = 3, MIN_BREADTH = 1, MAX_BREADTH = 200,
            MAX_NUM_LAYERS = 100, MAX_NUM_FRAMES = 100, /* TODO: test and tweak */ MAX_NUM_STATES = 5000;
}
