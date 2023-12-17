package com.jordanbunke.stipple_effect.tools;

public abstract class ToolWithMode extends Tool {
    private static Mode mode;

    public enum Mode {
        LOCAL, GLOBAL, ADDITIVE
    }

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        ToolWithMode.mode = mode;
    }
}
