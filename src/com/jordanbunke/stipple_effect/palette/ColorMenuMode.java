package com.jordanbunke.stipple_effect.palette;

public enum ColorMenuMode {
    RGBA_HSV, PALETTE;

    public ColorMenuMode toggle() {
        return values()[1 - ordinal()];
    }
}
