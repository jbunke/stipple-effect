package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.delta_time.utility.DeltaTimeGlobal;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SECursor {
    private static final String DT_CURSOR_CODE = "dt-se:cursor_code";

    public static final String MAIN_CURSOR = "main", NONE = "none",
            RETICLE = "reticle", NO_SCRIPT = "no_script",
            HAND_OPEN = "hand_open", HAND_GRAB = "hand_grab", POINTER = "pointer",
            SLIDE_VERT = "slide_vert", SLIDE_HORZ = "slide_horz";
    private static final String
            BRUSH = "brush", ERASER = "eraser",
            COLOR_PICKER = "color_picker", LINE_TOOL = "line_tool",
            TEXT_TOOL = "text_tool", TEXT_TOOL_TYPING = "text_tool_typing",
            PENCIL = "pencil", STIPPLE_PENCIL = "stipple_pencil",
            MOVE_SELECTION = "move_selection",
            MOVE_SELECTION_VERT = "move_selection_vert",
            MOVE_SELECTION_HORZ = "move_selection_horz",
            MOVE_SELECTION_DIAG_TL = "move_selection_diag_tl",
            MOVE_SELECTION_DIAG_BL = "move_selection_diag_bl",
            MOVE_SELECTION_ROTATE_R = "move_selection_rotright",
            MOVE_SELECTION_ROTATE_L = "move_selection_rotleft",
            MOVE_SELECTION_ROTATE_T = "move_selection_rottop",
            MOVE_SELECTION_ROTATE_B = "move_selection_rotbottom",
            MOVE_SELECTION_ROTATE_TL = "move_selection_rottl",
            MOVE_SELECTION_ROTATE_TR = "move_selection_rottr",
            MOVE_SELECTION_ROTATE_BL = "move_selection_rotbl",
            MOVE_SELECTION_ROTATE_BR = "move_selection_rotbr",
            PICK_UP_SELECTION = "pick_up_selection",
            PICK_UP_SELECTION_VERT = "pick_up_selection_vert",
            PICK_UP_SELECTION_HORZ = "pick_up_selection_horz",
            PICK_UP_SELECTION_DIAG_TL = "pick_up_selection_diag_tl",
            PICK_UP_SELECTION_DIAG_BL = "pick_up_selection_diag_bl",
            PICK_UP_SELECTION_ROTATE_R = "pick_up_selection_rotright",
            PICK_UP_SELECTION_ROTATE_L = "pick_up_selection_rotleft",
            PICK_UP_SELECTION_ROTATE_T = "pick_up_selection_rottop",
            PICK_UP_SELECTION_ROTATE_B = "pick_up_selection_rotbottom",
            PICK_UP_SELECTION_ROTATE_TL = "pick_up_selection_rottl",
            PICK_UP_SELECTION_ROTATE_TR = "pick_up_selection_rottr",
            PICK_UP_SELECTION_ROTATE_BL = "pick_up_selection_rotbl",
            PICK_UP_SELECTION_ROTATE_BR = "pick_up_selection_rotbr",
            FILL_GLOBAL = "fill_global", FILL_ADDITIVE = "fill_additive",
            FILL_SUBTRACTIVE = "fill_subtractive", FILL_SINGLE = "fill_single",
            WAND_GLOBAL = "wand_global", WAND_ADDITIVE = "wand_additive",
            WAND_SUBTRACTIVE = "wand_subtractive", WAND_SINGLE = "wand_single",
            BOX_SELECT_GLOBAL = "box_select_global",
            BOX_SELECT_ADDITIVE = "box_select_additive",
            BOX_SELECT_SUBTRACTIVE = "box_select_subtractive",
            BOX_SELECT_SINGLE = "box_select_single",
            POLYGON_SELECT_GLOBAL = "polygon_select_global",
            POLYGON_SELECT_ADDITIVE = "polygon_select_additive",
            POLYGON_SELECT_SUBTRACTIVE = "polygon_select_subtractive",
            POLYGON_SELECT_SINGLE = "polygon_select_single",
            RETICLE_ADDITIVE = "reticle_additive",
            RETICLE_SUBTRACTIVE = "reticle_subtractive",
            RETICLE_SINGLE = "reticle_single",
            ZOOM = "zoom";

    private static final Set<String> CURSOR_CODES = Set.of(
            MAIN_CURSOR, RETICLE, NO_SCRIPT, HAND_GRAB, HAND_OPEN, POINTER,
            SLIDE_VERT, SLIDE_HORZ,
            BRUSH, ERASER, PENCIL, STIPPLE_PENCIL,
            COLOR_PICKER, LINE_TOOL, TEXT_TOOL, TEXT_TOOL_TYPING,
            MOVE_SELECTION, MOVE_SELECTION_VERT,
            MOVE_SELECTION_HORZ, MOVE_SELECTION_DIAG_BL,
            MOVE_SELECTION_DIAG_TL,
            MOVE_SELECTION_ROTATE_R, MOVE_SELECTION_ROTATE_L,
            MOVE_SELECTION_ROTATE_T, MOVE_SELECTION_ROTATE_B,
            MOVE_SELECTION_ROTATE_TL, MOVE_SELECTION_ROTATE_TR,
            MOVE_SELECTION_ROTATE_BL, MOVE_SELECTION_ROTATE_BR,
            PICK_UP_SELECTION, PICK_UP_SELECTION_VERT,
            PICK_UP_SELECTION_HORZ, PICK_UP_SELECTION_DIAG_BL,
            PICK_UP_SELECTION_DIAG_TL,
            PICK_UP_SELECTION_ROTATE_R, PICK_UP_SELECTION_ROTATE_L,
            PICK_UP_SELECTION_ROTATE_T, PICK_UP_SELECTION_ROTATE_B,
            PICK_UP_SELECTION_ROTATE_TL, PICK_UP_SELECTION_ROTATE_TR,
            PICK_UP_SELECTION_ROTATE_BL, PICK_UP_SELECTION_ROTATE_BR,
            FILL_ADDITIVE, FILL_GLOBAL, FILL_SINGLE, FILL_SUBTRACTIVE,
            WAND_ADDITIVE, WAND_GLOBAL, WAND_SINGLE, WAND_SUBTRACTIVE,
            BOX_SELECT_ADDITIVE, BOX_SELECT_GLOBAL,
            BOX_SELECT_SINGLE, BOX_SELECT_SUBTRACTIVE,
            POLYGON_SELECT_ADDITIVE, POLYGON_SELECT_GLOBAL,
            POLYGON_SELECT_SINGLE, POLYGON_SELECT_SUBTRACTIVE,
            RETICLE_ADDITIVE, RETICLE_SUBTRACTIVE, RETICLE_SINGLE,
            ZOOM);

    private static final Map<String, GameImage> CURSOR_MAP = new HashMap<>();

    static {
        refresh();
    }

    public static GameImage fetchCursor(final String cursorCode) {
        return CURSOR_MAP.getOrDefault(cursorCode, CURSOR_MAP.get(MAIN_CURSOR));
    }

    public static String systemCursorCode() {
        return (String) DeltaTimeGlobal.getStatusOf(DT_CURSOR_CODE)
                .orElse(SECursor.MAIN_CURSOR);
    }

    public static void clickableElement() {
        setCursorCode(POINTER);
    }

    public static void setCursorCode(final String code) {
        DeltaTimeGlobal.setStatus(DT_CURSOR_CODE, code);
    }

    public static void resetCursorCode() {
        setCursorCode(null);
    }

    public static void refresh() {
        CURSOR_MAP.clear();

        for (String code : CURSOR_CODES) {
            final GameImage asset = ResourceLoader.loadImageResource(
                    Constants.CURSOR_FOLDER.resolve(code + ".png")),
                    cursor = Settings.getTheme().logic.transformIcon(asset);

            CURSOR_MAP.put(code, cursor);
        }

        CURSOR_MAP.put(NONE, GameImage.dummy());
    }
}
