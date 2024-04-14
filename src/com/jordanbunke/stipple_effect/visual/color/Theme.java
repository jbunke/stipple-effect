package com.jordanbunke.stipple_effect.visual.color;

import com.jordanbunke.stipple_effect.utility.EnumUtils;

import java.awt.*;
import java.util.function.Supplier;

public enum Theme {
    DEFAULT(
            // text
            SEColors.WHITE, SEColors.BLACK,
            SEColors.GREY, SEColors.MID_DARK_GREY,
            SEColors.BLACK, SEColors.PASTEL_BLUE,
            // panels, backgrounds & sections
            SEColors.MID_DARK_GREY, SEColors.NAVY, SEColors.DARK_GREY,
            SEColors.DARK_GREY, SEColors.VEIL, SEColors.TRANSLUCENT_BLUE_2,
            // button backgrounds
            SEColors.GREY, SEColors.DARK_GREY, SEColors.BLACK,
            // slider
            SEColors.LIGHT_GREY, SEColors.GREY,
            // button outline
            SEColors.BLACK,
            // highlight
            SEColors.PASTEL_BLUE, SEColors.TRANSLUCENT_BLUE_1,
            // invalid
            SEColors.DARK_RED,
            // splash screen
            SEColors.LIGHT_GREY, SEColors.GREY, SEColors.NAVY,
            // checkerboard
            SEColors.WHITE, SEColors.LIGHT_GREY),
    MINIMALIST(
            // text
            SEColors.WHITE, SEColors.BLACK,
            SEColors.GREY, SEColors.MID_DARK_GREY,
            SEColors.BLACK, SEColors.PASTEL_BLUE,
            // panels, backgrounds & sections
            SEColors.MID_DARK_GREY, SEColors.NAVY, SEColors.BLACK,
            SEColors.NAVY, SEColors.VEIL, SEColors.TRANSLUCENT_BLUE_2,
            // button backgrounds
            SEColors.GREY, SEColors.DARK_GREY, SEColors.BLACK,
            // slider
            SEColors.LIGHT_GREY, SEColors.GREY,
            // button outline
            SEColors.BLACK,
            // highlight
            SEColors.PASTEL_BLUE, SEColors.TRANSLUCENT_BLUE_1,
            // invalid
            SEColors.DARK_RED,
            // splash screen
            SEColors.LIGHT_GREY, SEColors.GREY, SEColors.NAVY,
            // checkerboard
            SEColors.WHITE, SEColors.LIGHT_GREY),
    BUNKERING(
            // text
            SEColors.WHITE, SEColors.BLACK,
            SEColors.GREY, SEColors.MID_DARK_GREY,
            SEColors.BLACK, SEColors.GOLD,
            // panels, backgrounds & sections
            SEColors.DARK_GOLD, SEColors.DARK_OIL, SEColors.BLACK,
            SEColors.FOLIAGE, SEColors.VEIL, SEColors.TRANSLUCENT_GOLD,
            // button backgrounds
            SEColors.BLACK, SEColors.GREY, SEColors.BLACK,
            // slider
            SEColors.FOLIAGE, SEColors.GREY,
            // button outline
            SEColors.DARK_GOLD,
            // highlight
            SEColors.GOLD, SEColors.TRANSLUCENT_GOLD,
            // invalid
            SEColors.DARK_RED,
            // splash screen
            SEColors.GOLD, SEColors.GREY, SEColors.DARK_OIL,
            // checkerboard
            SEColors.GREY, SEColors.MID_DARK_GREY)
    ;

    public final Supplier<Color>
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
            final Color checkerboard2
    ) {
        this.textLight = () -> textLight;
        this.textDark = () -> textDark;
        this.affixTextLight = () -> affixTextLight;
        this.affixTextDark = () -> affixTextDark;
        this.textMenuHeading = () -> textMenuHeading;
        this.textShortcut = () -> textShortcut;

        this.workspaceBackground = () -> workspaceBackground;
        this.panelBackground = () -> panelBackground;
        this.panelDivisions = () -> panelDivisions;

        this.scrollBackground = () -> scrollBackground;
        this.dialogVeil = () -> dialogVeil;
        this.selectionFill = () -> selectionFill;
        this.buttonBody = () -> buttonBody;
        this.stubButtonBody = () -> stubButtonBody;
        this.dropdownOptionBody = () -> dropdownOptionBody;
        this.defaultSliderCore = () -> defaultSliderCore;
        this.defaultSliderBall = () -> defaultSliderBall;
        this.buttonOutline = () -> buttonOutline;

        this.highlightOutline = () -> highlightOutline;
        this.highlightOverlay = () -> highlightOverlay;
        this.invalid = () -> invalid;

        this.splashText = () -> splashText;
        this.splashFlashingText = () -> splashFlashingText;
        this.splashBackground = () -> splashBackground;

        this.checkerboard1 = () -> checkerboard1;
        this.checkerboard2 = () -> checkerboard2;
    }

    public String forButtonText() {
        return EnumUtils.formattedName(this);
    }
}
