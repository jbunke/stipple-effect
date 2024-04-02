package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.ProjectInfo;

import java.awt.*;
import java.nio.file.Path;

public class Constants {
    public static final String ABOUT = "about",
            NAME_CODE = "name", VERSION_CODE = "version",
            NATIVE_STANDARD_CODE = "native_standard",
            PALETTE_STANDARD_CODE = "palette_standard";

    public static final Path PROGRAM_FILE = Path.of("program"),
            ICON_FOLDER = Path.of("icons"),
            PALETTE_FOLDER = Path.of("palettes"),
            CURSOR_FOLDER = Path.of("cursors"),
            MISC_FOLDER = Path.of("misc"),
            TOOL_TIP_FOLDER = Path.of("tooltips"),
            BLURB_FOLDER = Path.of("blurbs");

    public static final String[] ACCEPTED_RASTER_IMAGE_SUFFIXES = new String[] {
            "jpg",
            ProjectInfo.SaveType.PNG_STITCHED.getFileSuffix()
    };

    public static final Coord2D NO_VALID_TARGET = new Coord2D(Integer.MIN_VALUE, Integer.MIN_VALUE);

    public static final double TICK_HZ = 100d, FPS = 30d;

    public static final double OPAQUE = 1d, ONION_SKIN_OPACITY = 0.5,
            CIRCLE = Math.PI * 2, _45_SNAP_INC = CIRCLE / 8d,
            _15_SNAP_INC = _45_SNAP_INC / 3d;

    public static final String BASE_LAYER_NAME = "Background", SUBSEQUENT_LAYER_PREFIX = "Layer ",
            UNTITLED_PROJECT_NAME = "[ Untitled ]", NO_FOLDER_SELECTED = "[ No folder selected ]",
            NATIVE_FILE_SUFFIX = "stip", PALETTE_FILE_SUFFIX = "stippal",
            ASCII_TEMPLATE = "ascii", LATIN_EXTENDED_TEMPLATE = "latin-extended",
            COLOR_TOOL_TIP_PREFIX = "color:",
            OPEN_HIGHLIGHT = "{", CLOSE_HIGHLIGHT = "}",
            OPEN_COLOR = "<#", CLOSE_COLOR = ">",
            OPEN_SETTING_VAL = "{", CLOSE_SETTING_VAL = "}", SETTING_SEPARATOR = ":",
            ICON_ID_GAP_CODE = "", TYPING_CODE = "typing",
            GENERIC_APPROVAL_TEXT = "Confirm", CLOSE_DIALOG_TEXT = "Close";

    public static final Color BACKGROUND = new Color(80, 80, 80),
            BLACK = new Color(0, 0, 0),
            WHITE = new Color(255, 255, 255),
            ACCENT_BACKGROUND_LIGHT = new Color(192, 192, 192),
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

    public static final int DEFAULT_CANVAS_W = 32, DEFAULT_CANVAS_H = 32,
            MIN_CANVAS_W = 1, MIN_CANVAS_H = 1, MAX_CANVAS_W = 800, MAX_CANVAS_H = 800,
            OVERLAY_BORDER_PX = 6, SPLASH_TIMEOUT_SECS = 1800, NO_SELECTION = -1,
            MAX_PALETTE_SIZE = 300;

    public static final int DEFAULT_BRUSH_BREADTH = 3,
            MIN_BREADTH = 1, MAX_BREADTH = 100, BREADTH_INC = 5,
            MIN_FONT_SCALE = 1, MAX_FONT_SCALE = 10,
            DEFAULT_FONT_PX_SPACING = 2, MIN_FONT_PX_SPACING = 0, MAX_FONT_PX_SPACING = 10,
            MAX_NUM_LAYERS = 100, MAX_NUM_FRAMES = 100,
            MIN_NUM_STATES = 100, DUMP_STATES_CUSHION_FACTOR = 2,
            MILLIS_IN_SECOND = 1000, MIN_PLAYBACK_FPS = 1, MAX_PLAYBACK_FPS = 24,
            PLAYBACK_FPS_INC = 1, DEFAULT_PLAYBACK_FPS = 10,
            TIMER_MAX_OUT = 4, MILLIS_PER_TIMER_INC = 100,
            RGBA_SCALE = 255, COLOR_SET_RGBA_INC = 5,
            HUE_SCALE = 360, SAT_SCALE = RGBA_SCALE, VALUE_SCALE = RGBA_SCALE,
            STATUS_UPDATE_DURATION_MILLIS = 5000, TOOL_TIP_MILLIS_THRESHOLD = 500,
            MIN_SCALE_UP = 1, MAX_SCALE_UP = 20, DEFAULT_SAVE_SCALE_UP = MIN_SCALE_UP,
            MAX_NAME_LENGTH = 40, ARBITRARY_MAX_COUNT = 10000,
            STRETCH_PX_THRESHOLD = 2, ROTATE_PX_THRESHOLD = 5;

    public static final long DUMP_STATES_MEM_THRESHOLD = 0x1F400000L; // 500 MB

    public static final float MIN_ZOOM = 1 / 16f, MAX_ZOOM = 64f, DEF_ZOOM = 4f,
            ZOOM_FOR_OVERLAY = 1f, ZOOM_FOR_GRID = DEF_ZOOM;

    public static final double EXACT_COLOR_MATCH = 0d,
            DEFAULT_TOLERANCE = EXACT_COLOR_MATCH, MAX_TOLERANCE = 1d,
            SMALL_TOLERANCE_INC = 0.01, BIG_TOLERANCE_INC = SMALL_TOLERANCE_INC * 10,
            UNBIASED = 0.5d, MIN_BIAS = 0d, MAX_BIAS = 1d, BIAS_INC = 0.01;
}
