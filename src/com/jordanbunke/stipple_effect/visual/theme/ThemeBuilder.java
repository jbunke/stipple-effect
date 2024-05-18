package com.jordanbunke.stipple_effect.visual.theme;

import java.awt.*;

import static com.jordanbunke.stipple_effect.visual.theme.SEColors.*;

public class ThemeBuilder {
    private Color textLight, textDark,
            affixTextLight, affixTextDark,
            textMenuHeading, textShortcut,
            workspaceBackground, panelBackground, panelDivisions,
            scrollBackground, dialogVeil, selectionFill,
            buttonBody, stubButtonBody, dropdownOptionBody,
            defaultSliderCore, defaultSliderBall, buttonOutline,
            highlightOutline, highlightOverlay, invalid,
            splashText, splashFlashingText, splashBackground,
            checkerboard1, checkerboard2;

    private ThemeBuilder() {
        // DEFAULTS

        textLight = WHITE;
        textDark = BLACK;
        affixTextLight = GREY;
        affixTextDark = MID_DARK_GREY;
        textMenuHeading = LIGHT_GREY;
        textShortcut = PASTEL_BLUE;
        // panels, backgrounds & sections
        workspaceBackground = MID_DARK_GREY;
        panelBackground = NAVY;
        panelDivisions = BLACK;
        scrollBackground = NAVY;
        dialogVeil = VEIL;
        selectionFill = TRANSLUCENT_BLUE_2;
        // button backgrounds
        buttonBody = GREY;
        stubButtonBody = DARK_GREY;
        dropdownOptionBody = BLACK;
        // slider
        defaultSliderCore = LIGHT_GREY;
        defaultSliderBall = GREY;
        // button outline
        buttonOutline = BLACK;
        // highlight
        highlightOutline = PASTEL_BLUE;
        highlightOverlay = TRANSLUCENT_BLUE_1;
        // invalid
        invalid = DARK_RED;
        // splash screen
        splashText = LIGHT_GREY;
        splashFlashingText = GREY;
        splashBackground = NAVY;
        // checkerboard
        checkerboard1 = WHITE;
        checkerboard2 = LIGHT_GREY;
    }

    public Theme build() {
        return new Theme(
                textLight, textDark, affixTextLight, affixTextDark,
                textMenuHeading, textShortcut,
                workspaceBackground, panelBackground, panelDivisions,
                scrollBackground, dialogVeil, selectionFill,
                buttonBody, stubButtonBody, dropdownOptionBody,
                defaultSliderCore, defaultSliderBall, buttonOutline,
                highlightOutline, highlightOverlay, invalid,
                splashText, splashFlashingText, splashBackground,
                checkerboard1, checkerboard2);
    }

    public static Theme def() {
        return new ThemeBuilder().build();
    }

    public static Theme zo() {
        return new ThemeBuilder()
                .setPanelBackground(BONE)
                .setPanelDivisions(DARK_RED)
                .setTextLight(PURPLE)
                .setTextDark(DARK_GOLD)
                .setAffixTextDark(BLACK)
                .setAffixTextLight(WHITE)
                .setTextShortcut(RED)
                .setTextMenuHeading(DARK_RED)
                .setDropdownOptionBody(GOLD)
                .setDefaultSliderCore(WHITE)
                .setDefaultSliderBall(CREAM)
                .setWorkspaceBackground(DARK_RED)
                .setScrollBackground(CRACKED)
                .setButtonOutline(DARK_RED)
                .setHighlightOutline(RED)
                .setButtonBody(CREAM)
                .setSplashBackground(DARK_RED)
                .setSplashText(RED)
                .setSplashFlashingText(GOLD)
                .setHighlightOverlay(TRANSLUCENT_GOLD)
                .setDialogVeil(TRANSLUCENT_GOLD)
                .setCheckerboard1(BONE)
                .setCheckerboard2(CREAM)
                .build();
    }

    public static Theme neon() {
        return new ThemeBuilder()
                .setPanelBackground(BLACK)
                .setPanelDivisions(WHITE)
                .setTextLight(WHITE)
                .setTextDark(DARK_GREY)
                .setAffixTextDark(PURPLE)
                .setAffixTextLight(VERDANT)
                .setTextMenuHeading(PASTEL_BLUE)
                .setDropdownOptionBody(RED)
                .setDefaultSliderCore(BLACK)
                .setDefaultSliderBall(PURPLE)
                .setWorkspaceBackground(DARK_GREY)
                .setScrollBackground(BLACK)
                .setButtonOutline(WHITE)
                .setHighlightOutline(VERDANT)
                .setButtonBody(PURPLE)
                .setSplashBackground(BLACK)
                .setSplashText(WHITE)
                .setSplashFlashingText(PURPLE)
                .build();
    }

    public static Theme bunkering() {
        return new ThemeBuilder()
                .setTextShortcut(GOLD)
                .setWorkspaceBackground(DARK_GOLD)
                .setPanelBackground(DARK_OIL)
                .setScrollBackground(FOLIAGE)
                .setSelectionFill(TRANSLUCENT_GOLD)
                .setButtonBody(BLACK)
                .setStubButtonBody(GREY)
                .setDefaultSliderCore(FOLIAGE)
                .setButtonOutline(DARK_GOLD)
                .setHighlightOutline(GOLD)
                .setHighlightOverlay(TRANSLUCENT_GOLD)
                .setSplashText(GOLD)
                .setSplashBackground(DARK_OIL)
                .setCheckerboard1(GREY)
                .setCheckerboard2(MID_DARK_GREY).build();
    }

    public ThemeBuilder setTextLight(final Color textLight) {
        this.textLight = textLight;
        return this;
    }

    public ThemeBuilder setTextDark(final Color textDark) {
        this.textDark = textDark;
        return this;
    }

    public ThemeBuilder setAffixTextLight(final Color affixTextLight) {
        this.affixTextLight = affixTextLight;
        return this;
    }

    public ThemeBuilder setAffixTextDark(final Color affixTextDark) {
        this.affixTextDark = affixTextDark;
        return this;
    }

    public ThemeBuilder setTextMenuHeading(final Color textMenuHeading) {
        this.textMenuHeading = textMenuHeading;
        return this;
    }

    public ThemeBuilder setTextShortcut(final Color textShortcut) {
        this.textShortcut = textShortcut;
        return this;
    }

    public ThemeBuilder setWorkspaceBackground(final Color workspaceBackground) {
        this.workspaceBackground = workspaceBackground;
        return this;
    }

    public ThemeBuilder setPanelBackground(final Color panelBackground) {
        this.panelBackground = panelBackground;
        return this;
    }

    public ThemeBuilder setPanelDivisions(final Color panelDivisions) {
        this.panelDivisions = panelDivisions;
        return this;
    }

    public ThemeBuilder setScrollBackground(final Color scrollBackground) {
        this.scrollBackground = scrollBackground;
        return this;
    }

    public ThemeBuilder setDialogVeil(final Color dialogVeil) {
        this.dialogVeil = dialogVeil;
        return this;
    }

    public ThemeBuilder setSelectionFill(final Color selectionFill) {
        this.selectionFill = selectionFill;
        return this;
    }

    public ThemeBuilder setButtonBody(final Color buttonBody) {
        this.buttonBody = buttonBody;
        return this;
    }

    public ThemeBuilder setStubButtonBody(final Color stubButtonBody) {
        this.stubButtonBody = stubButtonBody;
        return this;
    }

    public ThemeBuilder setDropdownOptionBody(final Color dropdownOptionBody) {
        this.dropdownOptionBody = dropdownOptionBody;
        return this;
    }

    public ThemeBuilder setDefaultSliderCore(final Color defaultSliderCore) {
        this.defaultSliderCore = defaultSliderCore;
        return this;
    }

    public ThemeBuilder setDefaultSliderBall(final Color defaultSliderBall) {
        this.defaultSliderBall = defaultSliderBall;
        return this;
    }

    public ThemeBuilder setButtonOutline(final Color buttonOutline) {
        this.buttonOutline = buttonOutline;
        return this;
    }

    public ThemeBuilder setHighlightOutline(final Color highlightOutline) {
        this.highlightOutline = highlightOutline;
        return this;
    }

    public ThemeBuilder setHighlightOverlay(final Color highlightOverlay) {
        this.highlightOverlay = highlightOverlay;
        return this;
    }

    public ThemeBuilder setInvalid(final Color invalid) {
        this.invalid = invalid;
        return this;
    }

    public ThemeBuilder setSplashText(final Color splashText) {
        this.splashText = splashText;
        return this;
    }

    public ThemeBuilder setSplashFlashingText(final Color splashFlashingText) {
        this.splashFlashingText = splashFlashingText;
        return this;
    }

    public ThemeBuilder setSplashBackground(final Color splashBackground) {
        this.splashBackground = splashBackground;
        return this;
    }

    public ThemeBuilder setCheckerboard1(final Color checkerboard1) {
        this.checkerboard1 = checkerboard1;
        return this;
    }

    public ThemeBuilder setCheckerboard2(final Color checkerboard2) {
        this.checkerboard2 = checkerboard2;
        return this;
    }
}
