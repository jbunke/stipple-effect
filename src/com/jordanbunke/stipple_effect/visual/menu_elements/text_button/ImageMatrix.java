package com.jordanbunke.stipple_effect.visual.menu_elements.text_button;

public final class ImageMatrix {
    public static final int DIM = 2;
    private static final int TRUE = 1;

    public static int indexFromValue(final boolean value) {
        return value ? 1 : 0;
    }

    public static boolean valueFromInt(final int index) {
        return index == TRUE;
    }
}
