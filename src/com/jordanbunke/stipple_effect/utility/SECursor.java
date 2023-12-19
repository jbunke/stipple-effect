package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SECursor {
    public static final String MAIN_CURSOR = "main", RETICLE = "reticle",
            HAND_OPEN = "hand_open", HAND_GRAB = "hand_grab";
    private static final String
            BRUSH = "brush", COLOR_PICKER = "color_picker", ERASER = "eraser",
            PENCIL = "pencil", STIPPLE_PENCIL = "stipple_pencil",
            MOVE_SELECTION = "move_selection", PICK_UP_SELECTION = "pick_up_selection",
            FILL_GLOBAL = "fill_global", FILL_ADDITIVE = "fill_additive",
            FILL_SUBTRACTIVE = "fill_subtractive", FILL_SINGLE = "fill_single",
            WAND_GLOBAL = "wand_global", WAND_ADDITIVE = "wand_additive",
            WAND_SUBTRACTIVE = "wand_subtractive", WAND_SINGLE = "wand_single",
            BOX_SELECT_GLOBAL = "box_select_global",
            BOX_SELECT_ADDITIVE = "box_select_additive",
            BOX_SELECT_SUBTRACTIVE = "box_select_subtractive",
            BOX_SELECT_SINGLE = "box_select_single",
            PENCIL_SELECT_GLOBAL = "pencil_select_global",
            PENCIL_SELECT_ADDITIVE = "pencil_select_additive",
            PENCIL_SELECT_SUBTRACTIVE = "pencil_select_subtractive",
            PENCIL_SELECT_SINGLE = "pencil_select_single",
            ZOOM = "zoom";

    private static final Set<String> CURSOR_CODES = Set.of(
            MAIN_CURSOR, RETICLE, HAND_GRAB, HAND_OPEN,
            BRUSH, COLOR_PICKER, ERASER, PENCIL, STIPPLE_PENCIL,
            MOVE_SELECTION, PICK_UP_SELECTION,
            FILL_ADDITIVE, FILL_GLOBAL, FILL_SINGLE, FILL_SUBTRACTIVE,
            WAND_ADDITIVE, WAND_GLOBAL, WAND_SINGLE, WAND_SUBTRACTIVE,
            BOX_SELECT_ADDITIVE, BOX_SELECT_GLOBAL,
            BOX_SELECT_SINGLE, BOX_SELECT_SUBTRACTIVE,
            PENCIL_SELECT_ADDITIVE, PENCIL_SELECT_GLOBAL,
            PENCIL_SELECT_SINGLE, PENCIL_SELECT_SUBTRACTIVE,
            ZOOM);

    private static final Map<String, GameImage> CURSOR_MAP = new HashMap<>();

    static {
        for (String code : CURSOR_CODES)
            CURSOR_MAP.put(code, ResourceLoader.loadImageResource(
                    Constants.CURSOR_FOLDER.resolve(code + ".png")));
    }

    public static GameImage fetchCursor(final String cursorCode) {
        return CURSOR_MAP.getOrDefault(cursorCode, CURSOR_MAP.get(MAIN_CURSOR));
    }
}
