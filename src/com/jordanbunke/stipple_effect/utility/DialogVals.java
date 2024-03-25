package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.palette.PaletteSorter;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Outliner;
import com.jordanbunke.stipple_effect.visual.SEFonts;

import java.nio.file.Path;

public class DialogVals {
    private static int
            newProjectWidth = Constants.DEFAULT_IMAGE_W,
            newProjectHeight = Constants.DEFAULT_IMAGE_H,
            newProjectXDivs = 1, newProjectYDivs = 1,
            resizeWidth = Constants.DEFAULT_IMAGE_W,
            resizeHeight = Constants.DEFAULT_IMAGE_H,
            padLeft = 0, padRight = 0, padTop = 0, padBottom = 0,
            newFontPixelSpacing = Constants.DEFAULT_FONT_PX_SPACING;
    private static double layerOpacity = Constants.OPAQUE;
    private static boolean
            hasLatinEx = false,
            charSpecificSpacing = true;
    private static boolean[] outlineSideMask = Outliner.getSingleOutlineMask();
    private static String
            layerName = "",
            paletteName = "",
            fontName = "";
    private static Path paletteFolder = null;
    private static InfoScreen infoScreen = InfoScreen.ABOUT;
    private static SettingScreen settingScreen = SettingScreen.STARTUP;
    private static PaletteSorter paletteSorter = PaletteSorter.HUE;
    private static ContentType contentType = ContentType.SELECTION;
    private static UploadStatus
            asciiStatus = UploadStatus.UNATTEMPTED,
            latinExStatus = UploadStatus.UNATTEMPTED;
    private static GameImage
            asciiImage = null,
            latinExImage = null,
            fontPreviewImage = GameImage.dummy();

    public enum UploadStatus {
        UNATTEMPTED, VALIDATED, INVALID;

        public String getMessage() {
            return "[ " + switch (this) {
                case INVALID -> "Invalid / upload failed";
                case VALIDATED -> "Validated image!";
                case UNATTEMPTED -> "Nothing uploaded";
            } + " ]";
        }

        public boolean isValid() {
            return this == VALIDATED;
        }
    }

    public enum InfoScreen {
        ABOUT, PROJECT, TOOLS, LAYERS, FRAMES, COLORS, MORE, CHANGELOG;

        public String getTitle() {
            return this == MORE ? "More Shortcuts & Advanced Info" : toString();
        }

        @Override
        public String toString() {
            return EnumUtils.formattedName(this);
        }
    }

    public enum SettingScreen {
        STARTUP, FORMAT, VISUAL;

        public String getTitle() {
            return (this == STARTUP ? this + " / default" : this) + " settings";
        }

        @Override
        public String toString() {
            return EnumUtils.formattedName(this);
        }
    }

    public enum ContentType {
        SELECTION, PROJECT, LAYER_FRAME, LAYER, FRAME;

        public ContentType next(final SEContext c) {
            final ContentType[] vs = values();
            final ContentType next = vs[(ordinal() + 1) % vs.length];

            return next.get(c);
        }

        public ContentType get(final SEContext c) {
            // skip SELECTION iff context has no selection
            if (!c.getState().hasSelection() && this == SELECTION)
                return next(c);

            return this;
        }

        @Override
        public String toString() {
            if (this == LAYER_FRAME)
                return "Layer-Frame";

            return EnumUtils.formattedName(this);
        }
    }

    public static void setPaletteFolder(final Path paletteFolder) {
        DialogVals.paletteFolder = paletteFolder;
    }

    public static void setOutlineSideMask(final boolean[] outlineSideMask) {
        DialogVals.outlineSideMask = outlineSideMask;
    }

    public static void toggleThisOutlineSide(final int index) {
        outlineSideMask[index] = !outlineSideMask[index];
    }

    public static void setInfoScreen(final InfoScreen infoScreen) {
        DialogVals.infoScreen = infoScreen;
    }

    public static void setSettingScreen(final SettingScreen settingScreen) {
        DialogVals.settingScreen = settingScreen;
    }

    public static void cyclePaletteSorter() {
        paletteSorter = EnumUtils.next(paletteSorter);
    }

    public static void cycleContentType(final SEContext c) {
        contentType = contentType.get(c).next(c);
    }

    public static void setLayerName(final String layerName) {
        DialogVals.layerName = layerName;
    }

    public static void setPaletteName(final String paletteName) {
        DialogVals.paletteName = paletteName;
    }

    public static void setLayerOpacity(final double layerOpacity) {
        DialogVals.layerOpacity = layerOpacity;
    }

    public static void setNewProjectHeight(final int newProjectHeight) {
        DialogVals.newProjectHeight = newProjectHeight;
    }

    public static void setNewProjectWidth(final int newProjectWidth) {
        DialogVals.newProjectWidth = newProjectWidth;
    }

    public static void setPadBottom(final int padBottom) {
        DialogVals.padBottom = padBottom;
    }

    public static void setPadLeft(final int padLeft) {
        DialogVals.padLeft = padLeft;
    }

    public static void setPadRight(final int padRight) {
        DialogVals.padRight = padRight;
    }

    public static void setPadTop(final int padTop) {
        DialogVals.padTop = padTop;
    }

    public static void setResizeHeight(final int resizeHeight) {
        DialogVals.resizeHeight = resizeHeight;
    }

    public static void setResizeWidth(final int resizeWidth) {
        DialogVals.resizeWidth = resizeWidth;
    }

    public static void setNewProjectXDivs(final int newProjectXDivs) {
        DialogVals.newProjectXDivs = newProjectXDivs;
    }

    public static void setNewProjectYDivs(final int newProjectYDivs) {
        DialogVals.newProjectYDivs = newProjectYDivs;
    }

    public static void setNewFontPixelSpacing(final int newFontPixelSpacing) {
        setNewFontPixelSpacing(newFontPixelSpacing, true);
    }

    public static void setNewFontPixelSpacing(
            final int newFontPixelSpacing, final boolean attemptPreviewUpdate
    ) {
        DialogVals.newFontPixelSpacing = newFontPixelSpacing;

        if (attemptPreviewUpdate)
            SEFonts.attemptPreviewUpdate();
    }

    public static void setHasLatinEx(final boolean hasLatinEx) {
        setHasLatinEx(hasLatinEx, true);
    }

    public static void setHasLatinEx(
            final boolean hasLatinEx, final boolean attemptPreviewUpdate
    ) {
        DialogVals.hasLatinEx = hasLatinEx;

        if (attemptPreviewUpdate)
            SEFonts.attemptPreviewUpdate();
    }

    public static void setCharSpecificSpacing(final boolean charSpecificSpacing) {
        setCharSpecificSpacing(charSpecificSpacing, true);
    }

    public static void setCharSpecificSpacing(
            final boolean charSpecificSpacing, final boolean attemptPreviewUpdate
    ) {
        DialogVals.charSpecificSpacing = charSpecificSpacing;

        if (attemptPreviewUpdate)
            SEFonts.attemptPreviewUpdate();
    }

    public static void setFontName(final String fontName) {
        DialogVals.fontName = fontName;
    }

    public static void resetAscii() {
        asciiStatus = UploadStatus.UNATTEMPTED;
        asciiImage = null;
    }

    public static void asciiFailedValidation() {
        asciiStatus = UploadStatus.INVALID;
        asciiImage = null;
    }

    public static void asciiPassedValidation(
            final GameImage asciiImage
    ) {
        asciiStatus = UploadStatus.VALIDATED;
        DialogVals.asciiImage = asciiImage;

        SEFonts.attemptPreviewUpdate();
    }

    public static void resetLatinEx() {
        latinExStatus = UploadStatus.UNATTEMPTED;
        latinExImage = null;
    }

    public static void latinExFailedValidation() {
        latinExStatus = UploadStatus.INVALID;
        latinExImage = null;
    }

    public static void latinExPassedValidation(
            final GameImage latinExImage
    ) {
        latinExStatus = UploadStatus.VALIDATED;
        DialogVals.latinExImage = latinExImage;

        SEFonts.attemptPreviewUpdate();
    }

    public static void setFontPreviewImage(
            final GameImage fontPreviewImage
    ) {
        DialogVals.fontPreviewImage = fontPreviewImage;
    }

    public static Path getPaletteFolder() {
        return paletteFolder;
    }

    public static InfoScreen getInfoScreen() {
        return infoScreen;
    }

    public static SettingScreen getSettingScreen() {
        return settingScreen;
    }

    public static PaletteSorter getPaletteSorter() {
        return paletteSorter;
    }

    public static ContentType getContentType(final SEContext c) {
        return contentType.get(c);
    }

    public static int getNewProjectHeight() {
        return newProjectHeight;
    }

    public static int getNewProjectWidth() {
        return newProjectWidth;
    }

    public static int getPadBottom() {
        return padBottom;
    }

    public static int getPadLeft() {
        return padLeft;
    }

    public static int getPadRight() {
        return padRight;
    }

    public static int getPadTop() {
        return padTop;
    }

    public static int getResizeHeight() {
        return resizeHeight;
    }

    public static int getResizeWidth() {
        return resizeWidth;
    }

    public static double getLayerOpacity() {
        return layerOpacity;
    }

    public static String getLayerName() {
        return layerName;
    }

    public static String getPaletteName() {
        return paletteName;
    }

    public static int getNewProjectXDivs() {
        return newProjectXDivs;
    }

    public static int getNewProjectYDivs() {
        return newProjectYDivs;
    }

    public static int getNewFontPixelSpacing() {
        return newFontPixelSpacing;
    }

    public static boolean hasLatinEx() {
        return hasLatinEx;
    }

    public static boolean isCharSpecificSpacing() {
        return charSpecificSpacing;
    }

    public static String getFontName() {
        return fontName;
    }

    public static UploadStatus getAsciiStatus() {
        return asciiStatus;
    }

    public static UploadStatus getLatinExStatus() {
        return latinExStatus;
    }

    public static GameImage getAsciiImage() {
        return asciiImage;
    }

    public static GameImage getLatinExImage() {
        return latinExImage;
    }

    public static GameImage getFontPreviewImage() {
        return fontPreviewImage;
    }

    public static boolean isThisOutlineSide(final int index) {
        return outlineSideMask[index];
    }

    public static boolean[] getOutlineSideMask() {
        return outlineSideMask;
    }
}
