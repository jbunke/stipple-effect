package com.jordanbunke.stipple_effect.selection;

public class SEClipboard {
    private static final SEClipboard INSTANCE;

    private boolean hasContents;


    private SEClipboard() {

    }

    static {
        INSTANCE = new SEClipboard();
    }

    public static SEClipboard get() {
        return INSTANCE;
    }
}
