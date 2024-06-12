package com.jordanbunke.stipple_effect.tools;

public abstract class ToolWithMode extends Tool {
    private static Mode mode = Mode.SINGLE;

    private static boolean global = false;

    public enum Mode {
        SINGLE, ADDITIVE, SUBTRACTIVE;

        public boolean inheritSelection() {
            return this != SINGLE;
        }
    }

    @Override
    public String getCursorCode() {
        return super.getCursorCode() + "_" + (global ? "global" : mode.name().toLowerCase());
    }

    public static Mode getMode() {
        return mode;
    }

    public static boolean isGlobal() {
        return global;
    }

    public static void setGlobal(final boolean global) {
        ToolWithMode.global = global;
    }

    public static void setMode(Mode mode) {
        ToolWithMode.mode = mode;
    }
}
