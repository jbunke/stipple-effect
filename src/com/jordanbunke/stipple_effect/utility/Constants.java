package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.ProjectInfo;
import com.jordanbunke.stipple_effect.tools.*;

import java.awt.*;
import java.nio.file.Path;

public class Constants {
    public static final String ABOUT = "about", CHANGELOG = "changelog",
            NAME_CODE = "name", VERSION_CODE = "version";

    public static final Path PROGRAM_FILE = Path.of("program"),
            ICON_FOLDER = Path.of("icons"),
            CURSOR_FOLDER = Path.of("cursors"),
            MISC_FOLDER = Path.of("misc"),
            BLURB_FOLDER = Path.of("blurbs");

    public static final String[] ACCEPTED_RASTER_IMAGE_SUFFIXES = new String[] {
            "jpg",
            ProjectInfo.SaveType.PNG_STITCHED.getFileSuffix(),
            ProjectInfo.SaveType.NATIVE.getFileSuffix()
    };

    public static final Coord2D NO_VALID_TARGET = new Coord2D(Integer.MIN_VALUE, Integer.MIN_VALUE);

    public static final int CANVAS_W = 1920, CANVAS_H = 1080, SCREEN_H_BUFFER = 80;

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
            TP_X = (int)(CANVAS_W * 0.24),
            SIZE_X = (int)(CANVAS_W * 0.36),
            ZOOM_PCT_X = (int)(CANVAS_W * 0.47),
            ZOOM_SLIDER_X = (int)(CANVAS_W * 0.535),
            ZOOM_SLIDER_W = (int)(CANVAS_W * 0.1);

    public static final String BASE_LAYER_NAME = "Background", SUBSEQUENT_LAYER_PREFIX = "Layer ",
            UNTITLED_PROJECT_NAME = "[ Untitled ]", NO_FOLDER_SELECTED = "[ No folder selected ]",
            NATIVE_FILE_SUFFIX = "stef", OPEN_HIGHLIGHT = "{", CLOSE_HIGHLIGHT = "}",
            OPEN_SETTING_VAL = "{", CLOSE_SETTING_VAL = "}", SETTING_SEPARATOR = ":",
            ICON_ID_GAP_CODE = "", GENERIC_APPROVAL_TEXT = "Confirm", TYPING_CODE = "typing";

    public static final Color BACKGROUND = new Color(80, 80, 80),
            BLACK = new Color(0, 0, 0),
            WHITE = new Color(255, 255, 255),
            ACCENT_BACKGROUND_LIGHT = new Color(180, 180, 231),
            ACCENT_BACKGROUND_DARK = new Color(40, 40, 60),
            GREY = new Color(127, 127, 127),
            DARK = new Color(55, 55, 55),
            INVALID = new Color(100, 20, 20),
            VEIL = new Color(ACCENT_BACKGROUND_LIGHT.getRed(),
                    ACCENT_BACKGROUND_LIGHT.getGreen(),
                    ACCENT_BACKGROUND_LIGHT.getBlue(), 200),
            HIGHLIGHT_1 = new Color(100, 100, 255),
            HIGHLIGHT_2 = new Color(50, 80, 255, 100),
            OVERLAY_FILL_C = new Color(50, 80, 255, 50);

    public static final int DEFAULT_IMAGE_W = 32, DEFAULT_IMAGE_H = 32, OVERLAY_BORDER_PX = 6,
            MIN_IMAGE_W = 1, MIN_IMAGE_H = 1, MAX_IMAGE_W = 1000, MAX_IMAGE_H = 1000,
            SPLASH_TIMEOUT_SECS = 1800;

    public static final int DEFAULT_CHECKERBOARD_DIM = 4, CURSOR_DIM = 40,
            BUTTON_DIM = 20, BUTTON_OFFSET = 2, ICON_BUTTON_OFFSET_Y = 3,
            BUTTON_INC = BUTTON_DIM + BUTTON_OFFSET, BUTTON_BORDER_PX = 2,
            SEGMENT_TITLE_BUTTON_OFFSET_X = 74, SEGMENT_TITLE_CONTENT_OFFSET_Y = 30,
            LAYER_BUTTON_W = 136, LAYERS_ABOVE_TO_DISPLAY = 2, LAYER_NAME_LENGTH_CUTOFF = 10,
            FRAME_BUTTON_W = 40, FRAMES_BEFORE_TO_DISPLAY = 5, PX_PER_SCROLL = FRAME_BUTTON_W + BUTTON_OFFSET,
            PROJECT_NAME_BUTTON_PADDING_W = 20, SPACE_BETWEEN_PROJECT_BUTTONS_X = 8, PROJECTS_BEFORE_TO_DISPLAY = 1,
            VERT_SCROLL_WINDOW_W = COLOR_PICKER_W - (2 * TOOL_NAME_X), VERT_SCROLL_WINDOW_H = (int)(LAYERS_H * 0.8),
            FRAME_SCROLL_WINDOW_W = FRAMES_W - (2 * TOOL_NAME_X), FRAME_SCROLL_WINDOW_H = (int)(CONTEXTS_H * 0.56),
            FRAME_PLAYBACK_SLIDER_W = 87, FRAME_PLAYBACK_SLIDER_OFFSET_X = 309,
            DIALOG_W = CANVAS_W / 2, DIALOG_H = CANVAS_H / 2, DIALOG_CONTENT_INC_Y = 32,
            DIALOG_CONTENT_COMP_OFFSET_Y = 7, DIALOG_DYNAMIC_W_ALLOWANCE = 80,
            DIALOG_CONTENT_OFFSET_X = 115, DIALOG_CONTENT_BIG_OFFSET_X = 215,
            DIALOG_CONTENT_W_ALLOWANCE = 280, DIALOG_CONTENT_SMALL_W_ALLOWANCE = 180,
            SMALL_TEXT_BOX_W = 80,
            STD_TEXT_BUTTON_W = 88, STD_TEXT_BUTTON_H = 25,
            STD_TEXT_BUTTON_INC = STD_TEXT_BUTTON_H + BUTTON_OFFSET, BUTTON_TEXT_OFFSET_Y = -4,
            COLOR_SELECTOR_OFFSET_Y = 68, COLOR_SELECTOR_INC_Y = (int)(COLOR_PICKER_H / 5.5),
            COLOR_TEXTBOX_AVG_C_THRESHOLD = 100, COLOR_TEXTBOX_W = 116,
            SLIDER_OFF_DIM = 20, SLIDER_BALL_DIM = 20, SLIDER_THINNING = 4,
            FULL_COLOR_SLIDER_W = COLOR_PICKER_W - (SLIDER_BALL_DIM + 10),
            HALF_COLOR_SLIDER_W = (COLOR_PICKER_W / 2) - (SLIDER_BALL_DIM + 10),
            COLOR_LABEL_OFFSET_Y = -18, DYNAMIC_LABEL_H = 40, DYNAMIC_LABEL_W_ALLOWANCE = 150;

    public static final Coord2D ICON_DIMS = new Coord2D(BUTTON_DIM, BUTTON_DIM);

    public static final int DEFAULT_BRUSH_BREADTH = 3,
            MIN_BREADTH = 1, MAX_BREADTH = 200, BREADTH_INC = 5,
            MAX_NUM_LAYERS = 100, MAX_NUM_FRAMES = 100, MAX_NUM_STATES = 5000,
            MILLIS_IN_SECOND = 1000, MIN_PLAYBACK_FPS = 1, MAX_PLAYBACK_FPS = 30,
            PLAYBACK_FPS_INC = 1, DEFAULT_PLAYBACK_FPS = 10,
            RGBA_SCALE = 255, COLOR_SET_RGBA_INC = 5,
            HUE_SCALE = 360, SAT_SCALE = RGBA_SCALE, VALUE_SCALE = RGBA_SCALE,
            STATUS_UPDATE_DURATION_MILLIS = 5000,
            MIN_SCALE_UP = 1, MAX_SCALE_UP = 20, DEFAULT_SAVE_SCALE_UP = MIN_SCALE_UP,
            MAX_NAME_LENGTH = 25, ARBITRARY_MAX_COUNT = 10000,
            STRETCH_PX_THRESHOLD = 9, ROTATE_PX_THRESHOLD = 21;

    public static final float MIN_ZOOM = 1 / 16f, MAX_ZOOM = 64f, DEF_ZOOM = 4f, ZOOM_FOR_OVERLAY = 2f;

    public static final double EXACT_COLOR_MATCH = 0d,
            DEFAULT_TOLERANCE = EXACT_COLOR_MATCH, MAX_TOLERANCE = 1d,
            SMALL_TOLERANCE_INC = 0.01, BIG_TOLERANCE_INC = SMALL_TOLERANCE_INC * 10d;

    public static final Tool[] ALL_TOOLS = new Tool[] {
            Hand.get(), Zoom.get(),
            StipplePencil.get(), Pencil.get(), Brush.get(), Eraser.get(),
            Fill.get(), ColorPicker.get(),
            Wand.get(), BrushSelect.get(), BoxSelect.get(),
            MoveSelection.get(), PickUpSelection.get()
    };
}
