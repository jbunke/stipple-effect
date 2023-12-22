package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.stipple_effect.StippleEffect;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Settings {
    private static final Path SETTINGS_FILE = Path.of("data", ".settings");

    // codes
    private static final String FULLSCREEN_ON_STARTUP = "fullscreen_on_startup";
    private static final String CHECKERBOARD_PX = "checkerboard_px";

    // code-function associations
    private static final Map<String, Supplier<Object>> getterMap = Map.ofEntries(
            Map.entry(FULLSCREEN_ON_STARTUP, Settings::isFullscreenOnStartup),
            Map.entry(CHECKERBOARD_PX, Settings::getCheckerboardPixels)
    );

    // SETTINGS - set to defaults if settings cannot be read
    // booleans
    private static boolean fullscreenOnStartup = true;

    // int
    private static int checkerboardPixels = Constants.DEFAULT_CHECKERBOARD_DIM;

    public static void read() {
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
                        Constants.DEFAULT_CHECKERBOARD_DIM,
                        i -> setCheckerboardPixels(i, true));
            }
        }
    }

    public static void write() {
        final StringBuilder sb = new StringBuilder();

        for (String code : getterMap.keySet())
            sb.append(code).append(Constants.SETTING_SEPARATOR)
                    .append(Constants.OPEN_SETTING_VAL)
                    .append(getterMap.get(code))
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

    // getters
    public static boolean isFullscreenOnStartup() {
        return fullscreenOnStartup;
    }

    public static int getCheckerboardPixels() {
        return checkerboardPixels;
    }
}
