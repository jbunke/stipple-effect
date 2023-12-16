package com.jordanbunke.stipple_effect.utility;

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
    private static String layerName = "";

    public static void setLayerName(final String layerName) {
        DialogVals.layerName = layerName;
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

    public static int getNewProjectXDivs() {
        return newProjectXDivs;
    }

    public static int getNewProjectYDivs() {
        return newProjectYDivs;
    }
}
