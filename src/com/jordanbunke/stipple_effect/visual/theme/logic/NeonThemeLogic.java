package com.jordanbunke.stipple_effect.visual.theme.logic;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;

import java.awt.*;
import java.nio.file.Path;

public final class NeonThemeLogic extends ThemeLogic {
    private static final NeonThemeLogic INSTANCE;

    private final Path FOLDER;

    static {
        INSTANCE = new NeonThemeLogic();
    }

    private NeonThemeLogic() {
        FOLDER = THEMES_FOLDER.resolve("neon");
    }

    public static NeonThemeLogic get() {
        return INSTANCE;
    }

    @Override
    public GameImage highlightedIconOverlay() {
        return transformIcon(GraphicsUtils.readIconAsset("highlighted"));
    }

    @Override
    public GameImage selectedIconOverlay() {
        return transformIcon(GraphicsUtils.readIconAsset("selected"));
    }

    @Override
    Color transformIconPixel(final GameImage asset, final int x, final int y) {
        final Color orig = asset.getColorAt(x, y);

        if (orig.getAlpha() == 0)
            return orig;
        else {
            final int w = asset.getWidth(), h = asset.getHeight();

            final boolean white = orig.equals(SEColors.white()),
                    border = x == 0 || y == 0 || x + 1 == w || y + 1 == h;

            boolean transparentNeighbour = border;

            final int SIDES = 9;
            for (int i = 0; !transparentNeighbour && i < SIDES; i++) {
                final int deltaX = (i / 3) - 1, deltaY = (i % 3) - 1;

                if (deltaX == 0 && deltaY == 0)
                    continue;

                transparentNeighbour = isTransparent(asset,
                        x + deltaX, y + deltaY);
            }

            if (white && (border || transparentNeighbour))
                return Settings.getTheme().highlightOutline;
            else if (white)
                return SEColors.green();

            final double PURPLE_HUE = 5 / 6.0;
            return ColorMath.fromHSV(PURPLE_HUE, ColorMath.rgbToSat(orig),
                    ColorMath.rgbToValue(orig), orig.getAlpha());
        }
    }

    private boolean isTransparent(
            final GameImage asset, final int x, final int y
    ) {
        final Color c = asset.getColorAt(x, y);

        return c.getAlpha() == 0;
    }

    @Override
    public void drawHorizontalSliderOutline(final GameImage vesselWithCore) {
        final Color c = Settings.getTheme().buttonOutline;

        final int SECTION_PX = Layout.BUTTON_BORDER_PX,
                length = SECTION_PX * 3,
                w = vesselWithCore.getWidth(),
                h = vesselWithCore.getHeight();

        final int x = Layout.SLIDER_BALL_DIM / 2, rx = w - x,
                y = Layout.SLIDER_THINNING + (Layout.BUTTON_BORDER_PX / 2);

        vesselWithCore.setColor(c);

        // left bound
        vesselWithCore.drawLine(SECTION_PX, x, y, x, h - y);
        vesselWithCore.drawLine(SECTION_PX, x, y, x + length, y);
        vesselWithCore.drawLine(SECTION_PX, x, h - y, x + length, h - y);

        // right bound
        vesselWithCore.drawLine(SECTION_PX, rx, y, rx, h - y);
        vesselWithCore.drawLine(SECTION_PX, rx, y, rx - length, y);
        vesselWithCore.drawLine(SECTION_PX, rx, h - y, rx - length, h - y);
    }

    @Override
    public void drawVerticalSliderOutline(final GameImage vesselWithCore) {
        final Color c = Settings.getTheme().buttonOutline;

        final int SECTION_PX = Layout.BUTTON_BORDER_PX,
                length = SECTION_PX * 3,
                w = vesselWithCore.getWidth(),
                h = vesselWithCore.getHeight();

        final int y = Layout.SLIDER_BALL_DIM / 2, by = h - y,
                x = Layout.SLIDER_THINNING + (Layout.BUTTON_BORDER_PX / 2);

        vesselWithCore.setColor(c);

        // top bound
        vesselWithCore.drawLine(SECTION_PX, x, y, w - x, y);
        vesselWithCore.drawLine(SECTION_PX, x, y, x, y + length);
        vesselWithCore.drawLine(SECTION_PX, w - x, y, w - x, y + length);

        // bottom bound
        vesselWithCore.drawLine(SECTION_PX, x, by, w - x, by);
        vesselWithCore.drawLine(SECTION_PX, x, by, x, by - length);
        vesselWithCore.drawLine(SECTION_PX, w - x, by, w - x, by - length);
    }

    @Override
    public GameImage highlightSliderBall(final GameImage baseSliderBall) {
        final int w = baseSliderBall.getWidth(),
                h = baseSliderBall.getHeight();
        final GameImage highlighted = new GameImage(w, h);

        highlighted.fillRectangle(
                Settings.getTheme().buttonBody, 0, 0, w, h);

        return highlighted.submit();
    }

    @Override
    public GameImage unclickableIcon(final GameImage baseIcon) {
        return pixelWiseTransformation(baseIcon,
                c -> new Color(c.getRed(), 0, 0, c.getAlpha()));
    }

    @Override
    public GameImage[] loadSplash() {
        final int SPLASH_FRAMES = 8, SPLASH_SCALE_UP = 6;
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
