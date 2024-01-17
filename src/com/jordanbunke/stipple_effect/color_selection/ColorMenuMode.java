package com.jordanbunke.stipple_effect.color_selection;

public enum ColorMenuMode {
    RGBA_HSV, PALETTE;

    public ColorMenuMode toggle() {
        return values()[1 - ordinal()];
    }
}
