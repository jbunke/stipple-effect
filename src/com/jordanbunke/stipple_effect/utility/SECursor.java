package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SECursor {
    public static final String MAIN_CURSOR = "main",
            HAND_OPEN = "hand_open", HAND_GRAB = "hand_grab";
    private static final String
            BRUSH = "brush", COLOR_PICKER = "color_picker", ERASER = "eraser",
            PENCIL = "pencil", STIPPLE_PENCIL = "stipple_pencil",
            FILL_GLOBAL = "fill_global", FILL_ADDITIVE = "fill_additive",
            FILL_SUBTRACTIVE = "fill_subtractive", FILL_SINGLE = "fill_single",
            WAND_GLOBAL = "wand_global", WAND_ADDITIVE = "wand_additive",
            WAND_SUBTRACTIVE = "wand_subtractive", WAND_SINGLE = "wand_single",
            STIPPLE_SELECT_GLOBAL = "stipple_select_global",
            STIPPLE_SELECT_ADDITIVE = "stipple_select_additive",
            STIPPLE_SELECT_SUBTRACTIVE = "stipple_select_subtractive",
            STIPPLE_SELECT_SINGLE = "stipple_select_single",
            ZOOM = "zoom";

    // TODO - fill out cursor codes
    private static final Set<String> CURSOR_CODES = Set.of(
            MAIN_CURSOR, HAND_GRAB, HAND_OPEN,
            BRUSH, COLOR_PICKER, ERASER, PENCIL, STIPPLE_PENCIL,
            FILL_ADDITIVE, FILL_GLOBAL, FILL_SINGLE, FILL_SUBTRACTIVE,
            WAND_ADDITIVE, WAND_GLOBAL, WAND_SINGLE, WAND_SUBTRACTIVE,
            STIPPLE_SELECT_ADDITIVE, STIPPLE_SELECT_GLOBAL,
            STIPPLE_SELECT_SINGLE, STIPPLE_SELECT_SUBTRACTIVE,
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
