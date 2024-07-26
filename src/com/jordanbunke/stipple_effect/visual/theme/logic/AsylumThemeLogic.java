package com.jordanbunke.stipple_effect.visual.theme.logic;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.TextButton;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;
import java.nio.file.Path;

public final class AsylumThemeLogic extends ThemeLogic {
    private static final AsylumThemeLogic INSTANCE;

    private final Path FOLDER;

    static {
        INSTANCE = new AsylumThemeLogic();
    }

    private AsylumThemeLogic() {
        FOLDER = THEMES_FOLDER.resolve("asylum");
    }

    public static AsylumThemeLogic get() {
        return INSTANCE;
    }

    @Override
    public GameImage transformIcon(final GameImage asset) {
        return pixelWiseTransformation(asset, c -> c.equals(SEColors.white())
                ? SEColors.transparent() : GraphicsUtils.greyscale(c));
    }

    @Override
    public GameImage selectedIconOverlay() {
        return pixelWiseTransformation(
                super.selectedIconOverlay(),
                GraphicsUtils::greyscale);
    }

    @Override
    public GameImage highlightedIconOverlay() {
        return pixelWiseTransformation(
                super.highlightedIconOverlay(),
                GraphicsUtils::greyscale);
    }

    @Override
    public GameImage unclickableIcon(final GameImage baseIcon) {
        return new GameImage(baseIcon.getWidth(), baseIcon.getHeight());
    }

    @Override
    protected void drawTextButtonBackground(
            final GameImage img, final TextButton tb, final Color backgroundColor
    ) {
        final Theme t = Settings.getTheme();
        final int w = img.getWidth(), h = img.getHeight();

        img.fill(backgroundColor);

        if (tb.isSelected()) {
            final int INC = Layout.BUTTON_BORDER_PX;
            final Color c = t.highlightOutline;

            for (int x = INC; x < w - INC; x += INC)
                for (int y = INC; y < h - INC; y += INC)
                    if (((x / INC) + (y / INC)) % 2 == 0)
                        img.fillRectangle(c, x, y, INC, INC);
        }
    }

    @Override
    public GameImage[] loadSplash() {
        final int SPLASH_FRAMES = 20, SPLASH_SCALE_UP = 5;
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
