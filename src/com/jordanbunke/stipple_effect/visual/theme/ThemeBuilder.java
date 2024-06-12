package com.jordanbunke.stipple_effect.visual.theme;

import com.jordanbunke.stipple_effect.visual.theme.logic.*;

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
    private ThemeLogic logic;
    private String subtitle;

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

        // theme logic
        logic = DefaultThemeLogic.get();

        subtitle = "For indie game developers by an indie game developer";
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
                checkerboard1, checkerboard2, logic, subtitle);
    }

    public static Theme def() {
        return new ThemeBuilder().build();
    }

    public static Theme zo() {
        final ThemeBuilder zoTB = new ThemeBuilder()
                .setLogic(ZoThemeLogic.get())
                .setSubtitle("Honour your ancestors!");

        // backgrounds
        zoTB.setPanelBackground(BOURGOGNE)
                .setWorkspaceBackground(DEEP_OCEAN)
                .setScrollBackground(TRANSPARENT)
                .setSplashBackground(HAITI_PURPLE);

        // text
        zoTB.setTextLight(GOLD)
                .setTextDark(BLACK)
                .setAffixTextDark(HAITI_BLUE)
                .setAffixTextLight(HAITI_RED)
                .setTextMenuHeading(CEU)
                .setTextShortcut(CEU)
                .setSplashText(GOLD)
                .setSplashFlashingText(GOLD);

        // UI element bodies
        zoTB.setDefaultSliderCore(HAITI_BLUE)
                .setDefaultSliderBall(HAITI_RED)
                .setDropdownOptionBody(BLACK)
                .setButtonBody(HAITI_RED)
                .setInvalid(BLACK)
                .setStubButtonBody(BOURGOGNE);

        // UI element outlines
        zoTB.setButtonOutline(BLACK)
                .setHighlightOutline(GOLD)
                .setPanelDivisions(HAITI_PURPLE);

        // selection
        zoTB.setSelectionFill(TRANSLUCENT_GOLD_2)
                .setHighlightOverlay(TRANSLUCENT_GOLD_1);

        zoTB.setCheckerboard1(GREY).setCheckerboard2(MID_DARK_GREY);

        return zoTB.setDialogVeil(TRANSLUCENT_GOLD_1).build();
    }

    public static Theme neon() {
        final ThemeBuilder neonTB = new ThemeBuilder()
                .setLogic(NeonThemeLogic.get())
                .setSubtitle("Night owls welcome!");

        // backgrounds
        neonTB.setPanelBackground(BLACK)
                .setWorkspaceBackground(RADAR)
                .setScrollBackground(BLACK)
                .setSplashBackground(BLACK);

        // text
        neonTB.setTextLight(WHITE)
                .setTextDark(DARK_GREY)
                .setAffixTextDark(PURPLE)
                .setAffixTextLight(VERDANT)
                .setTextMenuHeading(PASTEL_BLUE)
                .setSplashFlashingText(PURPLE)
                .setSplashText(WHITE);

        // UI element bodies
        neonTB.setDefaultSliderCore(BLACK)
                .setDefaultSliderBall(BLACK)
                .setDropdownOptionBody(RED)
                .setButtonBody(PURPLE)
                .setInvalid(BLACK)
                .setStubButtonBody(BLACK);

        // UI element outlines
        neonTB.setButtonOutline(CHITIN)
                .setPanelDivisions(PURPLE)
                .setHighlightOutline(VERDANT);

        // selection
        neonTB.setSelectionFill(TRANSLUCENT_VERDANT_2)
                .setHighlightOverlay(TRANSLUCENT_VERDANT_1);

        neonTB.setCheckerboard1(MID_DARK_GREY)
                .setCheckerboard2(DARK_GREY);

        return neonTB.build();
    }

    public static Theme bunkering() {
        final ThemeBuilder bunkerTB = new ThemeBuilder()
                .setLogic(BunkeringThemeLogic.get())
                .setSubtitle("Save the Niger Delta!");

        // backgrounds
        bunkerTB.setSplashBackground(NAIJ)
                .setPanelBackground(DARK_OIL)
                .setWorkspaceBackground(FOLIAGE)
                .setScrollBackground(TRANSPARENT);

        // text
        bunkerTB.setTextLight(GOLD)
                .setTextDark(DARK_OIL)
                .setAffixTextDark(BLACK)
                .setAffixTextLight(WHITE)
                .setTextMenuHeading(NAIJ)
                .setTextShortcut(NAIJ)
                .setSplashText(WHITE)
                .setSplashFlashingText(GOLD);

        // UI element bodies
        bunkerTB.setDefaultSliderCore(NAIJ)
                .setDefaultSliderBall(WHITE)
                .setDropdownOptionBody(NAIJ)
                .setStubButtonBody(RED)
                .setInvalid(RED)
                .setButtonBody(BLACK);

        // UI element outlines
        bunkerTB.setButtonOutline(DARK_GOLD)
                .setHighlightOutline(GOLD)
                .setPanelDivisions(GOLD);

        // selection
        bunkerTB.setHighlightOverlay(TRANSLUCENT_GOLD_1)
                .setSelectionFill(TRANSLUCENT_GOLD_2);

        bunkerTB.setCheckerboard2(MID_DARK_GREY).setCheckerboard1(GREY);

        return bunkerTB.build();
    }

    public static Theme asylum() {
        final ThemeBuilder asylumTB = new ThemeBuilder()
                .setLogic(AsylumThemeLogic.get())
                .setSubtitle("Take care of your mental health!");

        // backgrounds
        asylumTB.setSplashBackground(BLACK)
                .setPanelBackground(WHITE)
                .setWorkspaceBackground(LIGHT_GREY)
                .setScrollBackground(WHITE);

        // text
        asylumTB.setTextLight(WHITE)
                .setTextDark(BLACK)
                .setAffixTextDark(DARK_GREY)
                .setAffixTextLight(LIGHT_GREY)
                .setTextMenuHeading(DARK_GREY)
                .setTextShortcut(GREY)
                .setSplashText(WHITE)
                .setSplashFlashingText(WHITE);

        // UI element bodies
        asylumTB.setDefaultSliderCore(WHITE)
                .setDefaultSliderBall(LIGHT_GREY)
                .setDropdownOptionBody(BLACK)
                .setStubButtonBody(WHITE)
                .setInvalid(WHITE)
                .setButtonBody(MID_DARK_GREY);

        // UI element outlines
        asylumTB.setButtonOutline(BLACK)
                .setHighlightOutline(GREY)
                .setPanelDivisions(BLACK);

        // selection
        asylumTB.setHighlightOverlay(TRANSLUCENT_GREY_1)
                .setSelectionFill(TRANSLUCENT_GREY_2);

        asylumTB.setCheckerboard2(WHITE).setCheckerboard1(LIGHTEST_GREY);

        return asylumTB.build();
    }

    public static Theme ramallah() {
        final ThemeBuilder ramallahTB = new ThemeBuilder()
                .setLogic(RamallahThemeLogic.get())
                .setSubtitle("Be on the right side of history!");

        // backgrounds
        ramallahTB.setSplashBackground(SAND)
                .setPanelBackground(WHITE)
                .setWorkspaceBackground(DARK_RED)
                .setScrollBackground(TRANSPARENT);

        // text
        ramallahTB.setTextLight(WHITE)
                .setTextDark(BLACK)
                .setAffixTextDark(DARK_GREY)
                .setAffixTextLight(LIGHTEST_GREY)
                .setTextMenuHeading(NAIJ)
                .setTextShortcut(RED)
                .setSplashText(BLACK)
                .setSplashFlashingText(DARK_RED);

        // UI element bodies
        ramallahTB.setDefaultSliderCore(NAIJ)
                .setDefaultSliderBall(NAIJ)
                .setDropdownOptionBody(NAIJ)
                .setStubButtonBody(GREY)
                .setInvalid(GREY)
                .setButtonBody(BLACK);

        // UI element outlines
        ramallahTB.setButtonOutline(WHITE)
                .setHighlightOutline(RED)
                .setPanelDivisions(BLACK);

        // selection
        ramallahTB.setHighlightOverlay(VEIL)
                .setSelectionFill(VEIL);

        ramallahTB.setCheckerboard1(GREY)
                .setCheckerboard2(MID_DARK_GREY);

        return ramallahTB.setDialogVeil(DARK_RED).build();
    }

    public ThemeBuilder setLogic(final ThemeLogic logic) {
        this.logic = logic;
        return this;
    }

    public ThemeBuilder setSubtitle(final String subtitle) {
        this.subtitle = subtitle;
        return this;
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
