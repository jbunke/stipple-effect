package com.jordanbunke.stipple_effect.utility;

public enum SamplerMode {
    RGB_SLIDERS, HSV_SLIDERS, SAT_VAL_MATRIX, COLOR_WHEEL;

    @Override
    public String toString() {
        return switch (this) {
            case RGB_SLIDERS -> "RGB Sliders";
            case HSV_SLIDERS -> "HSV Sliders";
            case SAT_VAL_MATRIX -> "Sat-Value Matrix";
            default -> EnumUtils.formattedName(this);
        };
    }
}
