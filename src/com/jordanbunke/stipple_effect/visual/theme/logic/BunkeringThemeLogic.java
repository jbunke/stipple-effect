package com.jordanbunke.stipple_effect.visual.theme.logic;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;
import java.nio.file.Path;

public final class BunkeringThemeLogic extends ThemeLogic {
    private static final BunkeringThemeLogic INSTANCE;

    private final Path FOLDER;

    static {
        INSTANCE = new BunkeringThemeLogic();
    }

    private BunkeringThemeLogic() {
        FOLDER = THEMES_FOLDER.resolve("bunkering");
    }

    public static BunkeringThemeLogic get() {
        return INSTANCE;
    }

    @Override
    public GameImage transformIcon(final GameImage asset) {
        final Color tint = Settings.getTheme().highlightOutline;
        final double hue = ColorMath.rgbToHue(tint),
                sat = ColorMath.rgbToSat(tint),
                SAT_THRESHOLD = 0.15;

        return pixelWiseTransformation(asset, c -> {
            if (c.equals(SEColors.white()))
                return tint;
            else if (c.getAlpha() == Constants.RGBA_SCALE &&
                    ColorMath.rgbToSat(c) < SAT_THRESHOLD) {
                return ColorMath.fromHSV(hue, sat,
                        ColorMath.rgbToValue(c), c.getAlpha());
            }

            return c;
        });
    }

    @Override
    public GameImage highlightedIconOverlay() {
        return overlayTransformation(super.highlightedIconOverlay());
    }

    @Override
    public GameImage selectedIconOverlay() {
        return overlayTransformation(super.selectedIconOverlay());
    }

    private GameImage overlayTransformation(final GameImage asset) {
        final Color tint = Settings.getTheme().highlightOutline;
        return hueFromColorTransformation(asset, tint);
    }

    @Override
    public GameImage unclickableIcon(final GameImage baseIcon) {
        final Theme t = Settings.getTheme();

        return pixelWiseTransformation(baseIcon,
                c -> c.equals(t.highlightOutline)
                        ? SEColors.transparent()
                        : GraphicsUtils.greyscale(c));
    }

    @Override
    public GameImage[] loadSplash() {
        final int SPLASH_FRAMES = 10, SPLASH_SCALE_UP = 6;
        final String SPLASH_FRAME_BASE = "splash_";

        final GameImage[] frames = new GameImage[SPLASH_FRAMES];

        for (int i = 0; i < SPLASH_FRAMES; i++) {
            final Path framePath = FOLDER.resolve(
                    SPLASH_FRAME_BASE + i + ".png");

            frames[i] = ImageProcessing.scale(
                    ResourceLoader.loadImageResource(framePath),
                    SPLASH_SCALE_UP);
        }

        return frames;
    }
}
