package com.jordanbunke.stipple_effect.utility;

public class OSUtils {
    private static final String NAME;
    private static final boolean WINDOWS;

    static {
        NAME = System.getProperty("os.name");
        WINDOWS = NAME.toLowerCase().contains("win");
    }

    public static boolean isWindows() {
        return WINDOWS;
    }
}
