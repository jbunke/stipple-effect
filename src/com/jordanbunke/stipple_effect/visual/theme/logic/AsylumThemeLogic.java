package com.jordanbunke.stipple_effect.visual.theme.logic;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;
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
    public GameImage drawTextButton(final TextButton tb) {
        final Theme t = Settings.getTheme();
        final ButtonType type = tb.getButtonType();

        final Color backgroundColor = switch (type) {
            case DD_OPTION -> t.dropdownOptionBody;
            case STUB -> t.stubButtonBody;
            default -> t.buttonBody;
        };

        final boolean leftAligned = type.isDropdown();
        final boolean drawBorder = type != ButtonType.DD_OPTION;

        final Color textColor = intuitTextColor(backgroundColor, true);
        final GameImage textImage = GraphicsUtils.uiText(textColor)
                .addText(tb.getLabel()).build().draw();

        final int w = Math.max(tb.getWidth(), textImage.getWidth() +
                (4 * Layout.BUTTON_BORDER_PX)),
                h = Layout.STD_TEXT_BUTTON_H;

        final GameImage nhi = new GameImage(w, h);
        nhi.fillRectangle(backgroundColor, 0, 0, w, h);

        final int textX = leftAligned
                ? (2 * Layout.BUTTON_BORDER_PX)
                : (w - textImage.getWidth()) / 2;

        if (tb.isSelected()) {
            final int INC = Layout.BUTTON_BORDER_PX;
            final Color c = t.highlightOutline;

            for (int x = INC; x < w - INC; x += INC)
                for (int y = INC; y < h - INC; y += INC)
                    if (((x / INC) + (y / INC)) % 2 == 0)
                        nhi.fillRectangle(c, x, y, INC, INC);
        }

        nhi.draw(textImage, textX, Layout.BUTTON_TEXT_OFFSET_Y);

        if (drawBorder) {
            final Color frame = buttonBorderColor(tb.isSelected());
            nhi.drawRectangle(frame, 2f * Layout.BUTTON_BORDER_PX, 0, 0, w, h);
        }

        return nhi.submit();
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
