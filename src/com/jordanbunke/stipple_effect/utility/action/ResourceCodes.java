package com.jordanbunke.stipple_effect.utility.action;

public final class ResourceCodes {
    public static final String
            PROGRAM = "program_icon",
            NEW_PROJECT = "new_project",
            OPEN_FILE = "open_file",
            CLOSE_PROJECT = "close_project",
            SAVE = "save",
            SAVE_AS = "save_as",
            RESIZE = "resize",
            PAD = "pad",
            STITCH_SPLIT_FRAMES = "stitch_split_frames",
            CROP_TO_SELECTION = "crop_to_selection",
            PREVIEW = "preview",
            UNDO = "undo",
            GRANULAR_UNDO = "granular_undo",
            GRANULAR_REDO = "granular_redo",
            REDO = "redo",
            HISTORY = "history",
            EXIT_PROGRAM = "exit_program",
            NEW_FRAME = "new_frame",
            DUPLICATE_FRAME = "duplicate_frame",
            REMOVE_FRAME = "remove_frame",
            MOVE_FRAME_FORWARD = "move_frame_forward",
            MOVE_FRAME_BACK = "move_frame_back",
            FRAME_PROPERTIES = "frame_properties",
            TO_FIRST_FRAME = "to_first_frame",
            PREVIOUS = "previous",
            NEXT = "next",
            TO_LAST_FRAME = "to_last_frame",
            PLAY = "play",
            STOP = "stop",
            LOOP = "loop",
            FORWARDS = "forwards",
            BACKWARDS = "backwards",
            PONG = "pong",
            NEW_LAYER = "new_layer",
            DUPLICATE_LAYER = "duplicate_layer",
            REMOVE_LAYER = "remove_layer",
            MOVE_LAYER_UP = "move_layer_up",
            MOVE_LAYER_DOWN = "move_layer_down",
            MERGE_WITH_LAYER_BELOW = "merge_with_layer_below",
            FLATTEN = "flatten",
            LAYER_ENABLED = "layer_enabled",
            LAYER_DISABLED = "layer_disabled",
            ONION_SKIN_NONE = "onion_skin_none",
            ONION_SKIN_PREVIOUS = "onion_skin_previous",
            ONION_SKIN_NEXT = "onion_skin_next",
            ONION_SKIN_BOTH = "onion_skin_both",
            FRAMES_LINKED = "frames_linked",
            FRAMES_UNLINKED = "frames_unlinked",
            LAYER_SETTINGS = "layer_settings",
            SWAP_COLORS = "swap_colors",
            COLOR_MENU_MODE = "color_menu_mode",
            HSV_SHIFT = "hsv_shift",
            COLOR_SCRIPT = "color_script",
            MOVE_LEFT_IN_PALETTE = "move_color_left_in_palette",
            MOVE_RIGHT_IN_PALETTE = "move_color_right_in_palette",
            NEW_PALETTE = "new_palette",
            DELETE_PALETTE = "delete_palette",
            PALETTE_SETTINGS = "palette_settings",
            ADD_TO_PALETTE = "add_color_to_palette",
            REMOVE_FROM_PALETTE = "remove_color_from_palette",
            IMPORT_PALETTE = "import_palette_file",
            SAVE_PALETTE = "save_palette",
            CONTENTS_TO_PALETTE = "contents_to_palette",
            SORT_PALETTE = "sort_palette",
            PALETTIZE = "palettize",
            DECREMENT = "decrement",
            INCREMENT = "increment",
            OUTLINE = "outline",
            VERTICAL_REFLECTION = "vertical_reflection",
            HORIZONTAL_REFLECTION = "horizontal_reflection",
            HORZ_BOUNDS_REFLECTION = "horz_bounds_reflection",
            VERT_BOUNDS_REFLECTION = "vert_bounds_reflection",
            HORZ_CONTENTS_REFLECTION = "horz_contents_reflection",
            VERT_CONTENTS_REFLECTION = "vert_contents_reflection",
            FILL_PRIMARY = "fill_primary",
            FILL_SECONDARY = "fill_secondary",
            PIXEL_GRID_OFF = "pixel_grid_off",
            PIXEL_GRID_ON = "pixel_grid_on",
            NEW_FONT = "new_font",
            DELETE_FONT = "delete_font",
            AUTOMATION_SCRIPT = "automation_script",
            IMPORT_SCRIPT = "import_script",
            REMOVE_SCRIPT = "remove_script",
            IMPORT_PREVIEW = "import_preview",
            SELECTION_REPRESENTATION = "sel_rep",
            NO_OUTLINE = "no_outline",
            SETTINGS = "settings",
            BULLET_POINT = "bullet_point",
            EXPAND = "expand",
            COLLAPSE = "collapse",
            PALETTE_BUTTON_BACKGROUND = "palette_button_bg",
            PRIMARY_SELECTION = "primary_selection",
            SECONDARY_SELECTION = "secondary_selection",
            EXCLUDED_FROM_PALETTE = "excluded_from_palette",
            HIDE_PANEL = "hide_panel",
            COLLAPSE_PANEL = "collapse_panel",
            EXPAND_PANEL = "expand_panel",
            PANEL_MANAGER = "panel_manager",
            INFO = "info";

    private static final String NO_ICON_PREFIX = "__";
    public static final String NONE = "",
            OUTLINE_PREFIX = "outline_", NUMKEY_PREFIX = "numkey_";

    public static final String
            ABOUT = NO_ICON_PREFIX + "about",
            CHANGELOG = NO_ICON_PREFIX + "changelog",
            ROADMAP = NO_ICON_PREFIX + "roadmap",
            SCRIPTING = NO_ICON_PREFIX + "scripting",
            GENERAL = NO_ICON_PREFIX + "general",
            CLIPBOARD_SHORTCUTS = NO_ICON_PREFIX + "clipboard_shortcuts",
            COPY = NO_ICON_PREFIX + "copy",
            CUT = NO_ICON_PREFIX + "cut",
            PASTE = NO_ICON_PREFIX + "paste",
            PASTE_NEW_LAYER = NO_ICON_PREFIX + "paste_new_layer",
            ALL_UI = NO_ICON_PREFIX + "all_ui",
            MINIMAL_UI = NO_ICON_PREFIX + "minimal_ui",
            SINGLE_OUTLINE = NO_ICON_PREFIX + "single_outline",
            DOUBLE_OUTLINE = NO_ICON_PREFIX + "double_outline",
            LAST_OUTLINE = NO_ICON_PREFIX + "last_outline",
            DESELECT = NO_ICON_PREFIX + "deselect",
            SELECT_ALL = NO_ICON_PREFIX + "select_all",
            INVERT_SELECTION = NO_ICON_PREFIX + "invert_selection",
            DELETE_SELECTION_CONTENTS = NO_ICON_PREFIX + "delete_selection_contents",
            SET_PIXEL_GRID_CANVAS = NO_ICON_PREFIX + "set_pixel_grid_canvas",
            SET_PIXEL_GRID_SELECTION = NO_ICON_PREFIX + "set_pixel_grid_selection",
            FULLSCREEN = NO_ICON_PREFIX + "fullscreen",
            WINDOWED = NO_ICON_PREFIX + "windowed",
            SELECTION_SHORTCUTS = NO_ICON_PREFIX + "selection_shortcuts",
            COLOR_SHORTCUTS = NO_ICON_PREFIX + "color_shortcuts",
            PLAYBACK_MODES = NO_ICON_PREFIX + "playback",
            LAYER_VISIBILITY = NO_ICON_PREFIX + "visibility",
            ONION_SKIN = NO_ICON_PREFIX + "onion_skin",
            FRAME_LOCKING = NO_ICON_PREFIX + "frame_locking",
            FONT_EXAMPLE_TEXT = NO_ICON_PREFIX + "font_example_text";

    private static final String INDICATOR_PREFIX = "indicator_";

    public static final String
            IND_TOOL = INDICATOR_PREFIX + "tool",
            IND_ZOOM = INDICATOR_PREFIX + "zoom",
            IND_TARGET = INDICATOR_PREFIX + "target",
            IND_BOUNDS = INDICATOR_PREFIX + "bounds";

    public static final String NO_TOOLTIP = "no_tooltip";

    public static boolean hasIcon(final String code) {
        return !(code.startsWith(NO_ICON_PREFIX) || code.equals(NONE));
    }
}