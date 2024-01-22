package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.stipple_effect.palette.PaletteSorter;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Outliner;

import java.nio.file.Path;

public class DialogVals {
    private static int
            newProjectWidth = Constants.DEFAULT_IMAGE_W,
            newProjectHeight = Constants.DEFAULT_IMAGE_H,
            newProjectXDivs = 1,
            newProjectYDivs = 1,
            resizeWidth = Constants.DEFAULT_IMAGE_W,
            resizeHeight = Constants.DEFAULT_IMAGE_H,
            padLeft = 0,
            padRight = 0,
            padTop = 0,
            padBottom = 0;
    private static double layerOpacity = Constants.OPAQUE;
    private static boolean[] outlineSideMask = Outliner.getSingleOutlineMask();
    private static String layerName = "", paletteName = "";
    private static Path paletteFolder = null;
    private static InfoScreen infoScreen = InfoScreen.ABOUT;
    private static SettingScreen settingScreen = SettingScreen.STARTUP;
    private static PaletteSorter paletteSorter = PaletteSorter.HUE;
    private static ContentType contentType = ContentType.SELECTION;

    public enum InfoScreen {
        ABOUT, PROJECT, TOOLS, LAYERS, FRAMES, COLORS, MORE, CHANGELOG;

        public String getTitle() {
            return this == MORE ? "More Shortcuts & Advanced Info" : toString();
        }

        @Override
        public String toString() {
            return enumPrintName(name());
        }
    }

    public enum SettingScreen {
        STARTUP, FORMAT, VISUAL;

        public String getTitle() {
            return this + " settings";
        }

        @Override
        public String toString() {
            return enumPrintName(name());
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

            return enumPrintName(name());
        }
    }

    private static String enumPrintName(final String name) {
        return name.charAt(0) + name.substring(1).toLowerCase();
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
        paletteSorter = paletteSorter.next();
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

    public static boolean isThisOutlineSide(final int index) {
        return outlineSideMask[index];
    }

    public static boolean[] getOutlineSideMask() {
        return outlineSideMask;
    }
}
