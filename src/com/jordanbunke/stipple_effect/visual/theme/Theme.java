package com.jordanbunke.stipple_effect.visual.theme;

import com.jordanbunke.stipple_effect.visual.theme.logic.ThemeLogic;

import java.awt.*;

public class Theme {
    public final Color
            textLight, textDark,
            affixTextLight, affixTextDark,
            textMenuHeading, textShortcut,
            workspaceBackground, panelBackground, panelDivisions,
            scrollBackground, dialogVeil, selectionFill,
            buttonBody, stubButtonBody, dropdownOptionBody,
            defaultSliderCore, defaultSliderBall, buttonOutline,
            highlightOutline, highlightOverlay, invalid,
            splashText, splashFlashingText, splashBackground,
            checkerboard1, checkerboard2;

    public final ThemeLogic logic;
    public final String subtitle;

    Theme(
            final Color textLight, final Color textDark,
            final Color affixTextLight, final Color affixTextDark,
            final Color textMenuHeading, final Color textShortcut,

            final Color workspaceBackground, final Color panelBackground,
            final Color panelDivisions,

            final Color scrollBackground,
            final Color dialogVeil,
            final Color selectionFill,
            final Color buttonBody,
            final Color stubButtonBody,
            final Color dropdownOptionBody,
            final Color defaultSliderCore,
            final Color defaultSliderBall,
            final Color buttonOutline,

            final Color highlightOutline, final Color highlightOverlay, final Color invalid,

            final Color splashText, final Color splashFlashingText,
            final Color splashBackground,

            final Color checkerboard1,
            final Color checkerboard2,

            final ThemeLogic logic, final String subtitle
    ) {
        this.textLight = textLight;
        this.textDark = textDark;
        this.affixTextLight = affixTextLight;
        this.affixTextDark = affixTextDark;
        this.textMenuHeading = textMenuHeading;
        this.textShortcut = textShortcut;

        this.workspaceBackground = workspaceBackground;
        this.panelBackground = panelBackground;
        this.panelDivisions = panelDivisions;

        this.scrollBackground = scrollBackground;
        this.dialogVeil = dialogVeil;
        this.selectionFill = selectionFill;
        this.buttonBody = buttonBody;
        this.stubButtonBody = stubButtonBody;
        this.dropdownOptionBody = dropdownOptionBody;
        this.defaultSliderCore = defaultSliderCore;
        this.defaultSliderBall = defaultSliderBall;
        this.buttonOutline = buttonOutline;

        this.highlightOutline = highlightOutline;
        this.highlightOverlay = highlightOverlay;
        this.invalid = invalid;

        this.splashText = splashText;
        this.splashFlashingText = splashFlashingText;
        this.splashBackground = splashBackground;

        this.checkerboard1 = checkerboard1;
        this.checkerboard2 = checkerboard2;

        this.logic = logic;
        this.subtitle = subtitle;
    }
}
