package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.visual.DialogAssembly;
import com.jordanbunke.stipple_effect.visual.SEFonts;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Settings {
    private static final Path SETTINGS_FILE = Path.of("data", ".settings");

    // codes
    private static final String
            FULLSCREEN_ON_STARTUP = "fullscreen_on_startup",
            FONT = "program_font",
            DEFAULT_INDEX_PREFIX = "def_index_prefix",
            DEFAULT_INDEX_SUFFIX = "def_index_suffix",
            CHECKERBOARD_WIDTH = "checkerboard_w_px",
            CHECKERBOARD_HEIGHT = "checkerboard_h_px",
            PIXEL_GRID_ON_BY_DEF = "pixel_grid_on_by_default",
            PIXEL_GRID_X_PX = "pixel_grid_x_px",
            PIXEL_GRID_Y_PX = "pixel_grid_y_px",
            DEFAULT_CANVAS_W_PX = "def_canvas_w_px",
            DEFAULT_CANVAS_H_PX = "def_canvas_h_px";

    // code-function associations
    private static final Map<String, Supplier<Object>> writerGetterMap = Map.ofEntries(
            Map.entry(FULLSCREEN_ON_STARTUP, Settings::isFullscreenOnStartup),
            Map.entry(PIXEL_GRID_ON_BY_DEF, Settings::isPixelGridOnByDefault),
            Map.entry(FONT, Settings::getProgramFont),
            Map.entry(DEFAULT_INDEX_PREFIX, Settings::getDefaultIndexPrefix),
            Map.entry(DEFAULT_INDEX_SUFFIX, Settings::getDefaultIndexSuffix),
            Map.entry(CHECKERBOARD_WIDTH, Settings::getCheckerboardWPixels),
            Map.entry(CHECKERBOARD_HEIGHT, Settings::getCheckerboardHPixels),
            Map.entry(PIXEL_GRID_X_PX, Settings::getPixelGridXPixels),
            Map.entry(PIXEL_GRID_Y_PX, Settings::getPixelGridYPixels),
            Map.entry(DEFAULT_CANVAS_W_PX, Settings::getDefaultCanvasWPixels),
            Map.entry(DEFAULT_CANVAS_H_PX, Settings::getDefaultCanvasHPixels)
    );

    // SETTINGS - set to defaults if settings cannot be read
    // booleans
    private static boolean fullscreenOnStartup = false,
            pixelGridOnByDefault = false;

    // int
    private static int
            checkerboardWPixels = Layout.DEFAULT_CHECKERBOARD_DIM,
            checkerboardHPixels = Layout.DEFAULT_CHECKERBOARD_DIM,
            pixelGridXPixels = Layout.DEFAULT_PIXEL_GRID_DIM,
            pixelGridYPixels = Layout.DEFAULT_PIXEL_GRID_DIM,
            defaultCanvasWPixels = Constants.DEFAULT_CANVAS_W,
            defaultCanvasHPixels = Constants.DEFAULT_CANVAS_H;

    // object
    private static String defIndexPrefix = "_", defIndexSuffix = "";
    private static SEFonts.Code programFont = SEFonts.DEFAULT_FONT;

    public static void read() {
        final String file = FileIO.readFile(SETTINGS_FILE);

        if (file == null)
            return;

        final String[] settingsLines = FileIO.readFile(SETTINGS_FILE).split("\n");

        for (String line : settingsLines) {
            final String[] codeAndValue =
                    ParserUtils.splitIntoCodeAndValue(line);

            if (codeAndValue.length != ParserUtils.DESIRED)
                continue;

            final String code = codeAndValue[ParserUtils.CODE],
                    value = codeAndValue[ParserUtils.VALUE];

            switch (code) {
                case FULLSCREEN_ON_STARTUP -> setFullscreenOnStartup(Boolean.parseBoolean(value));
                case PIXEL_GRID_ON_BY_DEF -> setPixelGridOnByDefault(Boolean.parseBoolean(value));
                case CHECKERBOARD_WIDTH -> setIntSettingSafely(value,
                        Layout.DEFAULT_CHECKERBOARD_DIM,
                        i -> setCheckerboardWPixels(i, true));
                case CHECKERBOARD_HEIGHT -> setIntSettingSafely(value,
                        Layout.DEFAULT_CHECKERBOARD_DIM,
                        i -> setCheckerboardHPixels(i, true));
                case PIXEL_GRID_X_PX -> setIntSettingSafely(value,
                        Layout.DEFAULT_PIXEL_GRID_DIM,
                        i -> setPixelGridXPixels(i, true));
                case PIXEL_GRID_Y_PX -> setIntSettingSafely(value,
                        Layout.DEFAULT_PIXEL_GRID_DIM,
                        i -> setPixelGridYPixels(i, true));
                case DEFAULT_CANVAS_W_PX -> setIntSettingSafely(
                        value, Constants.DEFAULT_CANVAS_W,
                        Settings::setDefaultCanvasWPixels);
                case DEFAULT_CANVAS_H_PX -> setIntSettingSafely(
                        value, Constants.DEFAULT_CANVAS_H,
                        Settings::setDefaultCanvasHPixels);
                case DEFAULT_INDEX_PREFIX -> setDefaultIndexPrefix(value, true);
                case DEFAULT_INDEX_SUFFIX -> setDefaultIndexSuffix(value, true);
                case FONT -> setProgramFont(value, true);
            }
        }
    }

    public static void write() {
        FileIO.safeMakeDirectory(SETTINGS_FILE.getParent());

        final StringBuilder sb = new StringBuilder();

        final List<String> codes = new ArrayList<>(writerGetterMap.keySet());
        codes.sort(Comparator.naturalOrder());

        for (String code : codes)
            sb.append(code).append(Constants.SETTING_SEPARATOR)
                    .append(Constants.OPEN_SETTING_VAL)
                    .append(writerGetterMap.get(code).get())
                    .append(Constants.CLOSE_SETTING_VAL)
                    .append("\n");

        FileIO.writeFile(SETTINGS_FILE, sb.toString());
    }

    // save setters
    public static void setIntSettingSafely(
            final String value, final int def, final Consumer<Integer> setter
    ) {
        int validated;

        try {
            validated = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            validated = def;
        }

        setter.accept(validated);
    }

    // setters
    public static void setFullscreenOnStartup(final boolean fullscreenOnStartup) {
        Settings.fullscreenOnStartup = fullscreenOnStartup;
    }

    public static void setPixelGridOnByDefault(final boolean pixelGridOnByDefault) {
        Settings.pixelGridOnByDefault = pixelGridOnByDefault;
    }

    public static void setCheckerboardWPixels(
            final int checkerboardWPixels, final boolean isStartup
    ) {
        Settings.checkerboardWPixels = checkerboardWPixels;

        if (!isStartup)
            StippleEffect.get().getContext().redrawCheckerboard();
    }

    public static void setCheckerboardHPixels(
            final int checkerboardHPixels, final boolean isStartup
    ) {
        Settings.checkerboardHPixels = checkerboardHPixels;

        if (!isStartup)
            StippleEffect.get().getContext().redrawCheckerboard();
    }

    public static void setPixelGridXPixels(
            final int pixelGridXPixels, final boolean isStartup
    ) {
        Settings.pixelGridXPixels = pixelGridXPixels;

        if (!isStartup) {
            final SEContext c = StippleEffect.get().getContext();
            c.renderInfo.setPixelGrid(true);
            c.redrawPixelGrid();
        }
    }

    public static void setPixelGridYPixels(
            final int pixelGridYPixels, final boolean isStartup
    ) {
        Settings.pixelGridYPixels = pixelGridYPixels;

        if (!isStartup) {
            final SEContext c = StippleEffect.get().getContext();
            c.renderInfo.setPixelGrid(true);
            c.redrawPixelGrid();
        }
    }

    public static void setDefaultCanvasWPixels(
            final int defaultCanvasWPixels
    ) {
        Settings.defaultCanvasWPixels = defaultCanvasWPixels;
    }

    public static void setDefaultCanvasHPixels(
            final int defaultCanvasHPixels
    ) {
        Settings.defaultCanvasHPixels = defaultCanvasHPixels;
    }

    public static void setDefaultIndexPrefix(
            final String defIndexPrefix, final boolean isStartup
    ) {
        Settings.defIndexPrefix = defIndexPrefix;

        if (!isStartup)
            StippleEffect.get().getContexts().forEach(
                    c -> c.projectInfo.setIndexPrefix(defIndexPrefix));
    }

    public static void setDefaultIndexSuffix(
            final String defIndexSuffix, final boolean isStartup
    ) {
        Settings.defIndexSuffix = defIndexSuffix;

        if (!isStartup)
            StippleEffect.get().getContexts().forEach(
                    c -> c.projectInfo.setIndexSuffix(defIndexSuffix));
    }

    public static void setProgramFont(final String fontCode, final boolean isStartup) {
        try {
            Settings.programFont = SEFonts.Code.valueOf(fontCode);
        } catch (IllegalArgumentException e) {
            if (!isStartup)
                StatusUpdates.invalidFontCode(fontCode);

            Settings.programFont = SEFonts.DEFAULT_FONT;
        }

        if (!isStartup) {
            DialogAssembly.setDialogToProgramSettings();
            StippleEffect.get().rebuildAllMenus();
        }
    }

    // getters
    public static boolean isFullscreenOnStartup() {
        return fullscreenOnStartup;
    }

    public static boolean isPixelGridOnByDefault() {
        return pixelGridOnByDefault;
    }

    public static int getCheckerboardWPixels() {
        return checkerboardWPixels;
    }

    public static int getCheckerboardHPixels() {
        return checkerboardHPixels;
    }

    public static int getPixelGridXPixels() {
        return pixelGridXPixels;
    }

    public static int getPixelGridYPixels() {
        return pixelGridYPixels;
    }

    public static int getDefaultCanvasWPixels() {
        return defaultCanvasWPixels;
    }

    public static int getDefaultCanvasHPixels() {
        return defaultCanvasHPixels;
    }

    public static String getDefaultIndexPrefix() {
        return defIndexPrefix;
    }

    public static String getDefaultIndexSuffix() {
        return defIndexSuffix;
    }

    public static SEFonts.Code getProgramFont() {
        return programFont;
    }
}
