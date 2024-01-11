package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.visual.DialogAssembly;
import com.jordanbunke.stipple_effect.visual.SEFonts;

import java.nio.file.Path;
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
            CHECKERBOARD_PX = "checkerboard_px";

    // code-function associations
    private static final Map<String, Supplier<Object>> writerGetterMap = Map.ofEntries(
            Map.entry(FULLSCREEN_ON_STARTUP, Settings::isFullscreenOnStartup),
            Map.entry(FONT, Settings::getProgramFont),
            Map.entry(DEFAULT_INDEX_PREFIX, Settings::getDefaultIndexPrefix),
            Map.entry(DEFAULT_INDEX_SUFFIX, Settings::getDefaultIndexSuffix),
            Map.entry(CHECKERBOARD_PX, Settings::getCheckerboardPixels)
    );

    // SETTINGS - set to defaults if settings cannot be read
    // booleans
    private static boolean fullscreenOnStartup = false;

    // int
    private static int checkerboardPixels = Layout.DEFAULT_CHECKERBOARD_DIM;

    // object
    private static String defIndexPrefix = "_", defIndexSuffix = "";
    private static SEFonts.Code programFont = SEFonts.Code.CLASSIC;

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
                case CHECKERBOARD_PX -> setIntSettingSafely(value,
                        Layout.DEFAULT_CHECKERBOARD_DIM,
                        i -> setCheckerboardPixels(i, true));
                case DEFAULT_INDEX_PREFIX -> setDefaultIndexPrefix(value, true);
                case DEFAULT_INDEX_SUFFIX -> setDefaultIndexSuffix(value, true);
                case FONT -> setProgramFont(value, true);
            }
        }
    }

    public static void write() {
        FileIO.safeMakeDirectory(SETTINGS_FILE.getParent());

        final StringBuilder sb = new StringBuilder();

        for (String code : writerGetterMap.keySet())
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

    public static void setCheckerboardPixels(
            final int checkerboardPixels, final boolean isStartup
    ) {
        Settings.checkerboardPixels = checkerboardPixels;

        if (!isStartup)
            StippleEffect.get().getContext().redrawCheckerboard();
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

            Settings.programFont = SEFonts.Code.CLASSIC;
        }

        if (!isStartup) {
            DialogAssembly.setDialogToProgramSettings();
            StippleEffect.get().rebuildAllMenusWithText();
        }
    }

    // getters
    public static boolean isFullscreenOnStartup() {
        return fullscreenOnStartup;
    }

    public static int getCheckerboardPixels() {
        return checkerboardPixels;
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
