package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.ProjectInfo;

import java.awt.*;
import java.nio.file.Path;

public class Constants {
    public static final String PROGRAM_NAME = "Stipple Effect";

    public static final Path ICON_FOLDER = Path.of("icons");

    public static final String[] ACCEPTED_RASTER_IMAGE_SUFFIXES = new String[] {
            "jpg",
            ProjectInfo.SaveType.PNG_STITCHED.getFileSuffix(),
            ProjectInfo.SaveType.NATIVE.getFileSuffix()
    };

    public static final int CANVAS_W = 960, CANVAS_H = 540, SCREEN_H_BUFFER = 80;

    public static final double TICK_HZ = 100d, FPS = 30d;

    public static final double OPAQUE = 1d, ONION_SKIN_OPACITY = 0.5;

    public static final int CONTEXTS_W = CANVAS_W / 2,
            FRAMES_W = CANVAS_W / 2, TOOLS_W = 25, COLOR_PICKER_W = 286,
            WORKSPACE_W = CANVAS_W - (TOOLS_W + COLOR_PICKER_W),
            CONTEXTS_H = 84, BOTTOM_BAR_H = 24,
            WORKSPACE_H = CANVAS_H - (CONTEXTS_H + BOTTOM_BAR_H),
            COLOR_PICKER_H = WORKSPACE_H / 2, LAYERS_H = WORKSPACE_H / 2;

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
        return new Coord2D(TOOL_NAME_X, SEGMENT_TITLE_CONTENT_OFFSET_Y);
    }

    public static Coord2D getCanvasMiddle() {
        return new Coord2D(CANVAS_W / 2, CANVAS_H / 2);
    }

    public static Coord2D getDialogContentInitial() {
        return getCanvasMiddle().displace(-DIALOG_W / 2, -DIALOG_H / 2)
                .displace(Constants.TOOL_NAME_X + Constants.BUTTON_BORDER_PX,
                        Constants.TEXT_Y_OFFSET + Constants.BUTTON_BORDER_PX +
                                (int)(1.5 * STD_TEXT_BUTTON_INC));
    }

    public static final int TEXT_Y_OFFSET = -4,
            TOOL_NAME_X = (int)(CANVAS_W * 0.01),
            TP_X = (int)(CANVAS_W * 0.21),
            SIZE_X = (int)(CANVAS_W * 0.36),
            ZOOM_PCT_X = (int)(CANVAS_W * 0.51);

    public static final String BASE_LAYER_NAME = "Background", SUBSEQUENT_LAYER_PREFIX = "Ly. ",
            UNTITLED_PROJECT_NAME = "[ Untitled ]", NO_FOLDER_SELECTED = "[ No folder selected ]",
            NATIVE_FILE_SUFFIX = "stef",
            ICON_ID_GAP_CODE = "", GENERIC_APPROVAL_TEXT = "Confirm";

    public static final Color BACKGROUND = new Color(80, 80, 80),
            BLACK = new Color(0, 0, 0),
            WHITE = new Color(255, 255, 255),
            ACCENT_BACKGROUND_LIGHT = new Color(180, 180, 231),
            ACCENT_BACKGROUND_DARK = new Color(30, 30, 90),
            GREY = new Color(127, 127, 127),
            DARK = new Color(55, 55, 55),
            INVALID = new Color(100, 20, 20),
            VEIL = new Color(ACCENT_BACKGROUND_LIGHT.getRed(),
                    ACCENT_BACKGROUND_LIGHT.getGreen(),
                    ACCENT_BACKGROUND_LIGHT.getBlue(), 200),
            HIGHLIGHT_1 = new Color(100, 100, 255),
            HIGHLIGHT_2 = new Color(50, 80, 255, 100);

    public static final int DEFAULT_IMAGE_W = 32, DEFAULT_IMAGE_H = 32,
            MIN_IMAGE_W = 1, MIN_IMAGE_H = 1, MAX_IMAGE_W = 640, MAX_IMAGE_H = 360;

    public static final int CHECKER_INCREMENT = 4,
            BUTTON_DIM = 20, BUTTON_OFFSET = 2, ICON_BUTTON_OFFSET_Y = 3,
            BUTTON_INC = BUTTON_DIM + BUTTON_OFFSET, BUTTON_BORDER_PX = 2,
            SEGMENT_TITLE_BUTTON_OFFSET_X = 74, SEGMENT_TITLE_CONTENT_OFFSET_Y = 30,
            LAYER_BUTTON_W = 88, LAYER_OPACITY_SLIDER_W = 68,
            LAYERS_ABOVE_TO_DISPLAY = 2, LAYER_NAME_LENGTH_CUTOFF = 6,
            FRAME_BUTTON_W = 40, FRAMES_BEFORE_TO_DISPLAY = 5,
            PROJECT_NAME_BUTTON_PADDING_W = 20, SPACE_BETWEEN_PROJECT_BUTTONS_X = 8, PROJECTS_BEFORE_TO_DISPLAY = 1,
            VERT_SCROLL_WINDOW_W = COLOR_PICKER_W - (2 * TOOL_NAME_X), VERT_SCROLL_WINDOW_H = (int)(LAYERS_H * 0.8),
            FRAME_SCROLL_WINDOW_W = FRAMES_W - (2 * TOOL_NAME_X), FRAME_SCROLL_WINDOW_H = (int)(CONTEXTS_H * 0.56),
            FRAME_PLAYBACK_SLIDER_W = 155, FRAME_PLAYBACK_SLIDER_OFFSET_X = 241,
            DIALOG_W = CANVAS_W / 2, DIALOG_H = CANVAS_H / 2, DIALOG_CONTENT_INC_Y = 32,
            DIALOG_CONTENT_COMP_OFFSET_Y = 7, DIALOG_DYNAMIC_W_ALLOWANCE = 80,
            DIALOG_CONTENT_OFFSET_X = 135, DIALOG_CONTENT_W_ALLOWANCE = 280,
            STD_TEXT_BUTTON_W = 88, STD_TEXT_BUTTON_H = 25,
            STD_TEXT_BUTTON_INC = STD_TEXT_BUTTON_H + BUTTON_OFFSET, BUTTON_TEXT_OFFSET_Y = -6,
            COLOR_SELECTOR_OFFSET_Y = 44, COLOR_SELECTOR_INC_Y = 44, COLOR_BUTTON_AVG_C_THRESHOLD = 100,
            COLOR_SLIDER_W = 256, SLIDER_OFF_DIM = 20, SLIDER_BALL_DIM = 20, SLIDER_THINNING = 4,
            COLOR_LABEL_OFFSET_Y = -18, DYNAMIC_LABEL_H = 40, DYNAMIC_LABEL_W_ALLOWANCE = 150;

    public static final Coord2D TOOL_ICON_DIMS = new Coord2D(BUTTON_DIM, BUTTON_DIM);

    // specific tools
    public static final int DEFAULT_BRUSH_BREADTH = 3, MIN_BREADTH = 1, MAX_BREADTH = 200,
            MAX_NUM_LAYERS = 100, MAX_NUM_FRAMES = 100, /* TODO: test and tweak */ MAX_NUM_STATES = 5000,
            MIN_MILLIS_PER_FRAME = 33, MAX_MILLIS_PER_FRAME = 500,
            MILLIS_PER_FRAME_INC = 40, DEFAULT_MILLIS_PER_FRAME = 100,
            MIN_SCALE_UP = 1, MAX_SCALE_UP = 20, DEFAULT_SAVE_SCALE_UP = MIN_SCALE_UP,
            MAX_NAME_LENGTH = 25;
}
