package com.jordanbunke.stipple_effect.visual.theme;

import com.jordanbunke.stipple_effect.utility.EnumUtils;

public enum Themes {
    DEFAULT(ThemeBuilder.def()),
    ZO(ThemeBuilder.zo()),
    NEON(ThemeBuilder.neon()),
    BUNKERING(ThemeBuilder.bunkering()),
    ASYLUM(ThemeBuilder.asylum()),
    RAMALLAH(ThemeBuilder.ramallah());

    private final Theme theme;

    Themes(final Theme theme) {
        this.theme = theme;
    }

    public Theme get() {
        return theme;
    }

    public String forButtonText() {
        return EnumUtils.formattedName(this);
    }
}
