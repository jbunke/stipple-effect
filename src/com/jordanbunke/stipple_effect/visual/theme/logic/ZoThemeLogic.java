package com.jordanbunke.stipple_effect.visual.theme.logic;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.TextButton;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;
import java.nio.file.Path;

public final class ZoThemeLogic extends ThemeLogic {
    private static final ZoThemeLogic INSTANCE;

    private final Path FOLDER;
    private final GameImage VEVE;

    static {
        INSTANCE = new ZoThemeLogic();
    }

    private ZoThemeLogic() {
        FOLDER = THEMES_FOLDER.resolve("zo");
        VEVE = ResourceLoader.loadImageResource(FOLDER.resolve("veve.png"));
    }

    public static ZoThemeLogic get() {
        return INSTANCE;
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

        final Color textColor = tb.isSelected() ? t.highlightOutline
                : intuitTextColor(backgroundColor, true);
        final GameImage textImage = GraphicsUtils.uiText(textColor)
                .addText(tb.getLabel()).build().draw();

        final int w = Math.max(tb.getWidth(), textImage.getWidth() +
                (4 * Layout.BUTTON_BORDER_PX)),
                h = Layout.STD_TEXT_BUTTON_H;

        final GameImage nhi = new GameImage(w, h);
        nhi.fillRectangle(backgroundColor, 0, 0, w, h);

        final int x = leftAligned
                ? (2 * Layout.BUTTON_BORDER_PX)
                : (w - textImage.getWidth()) / 2;

        nhi.draw(textImage, x, Layout.TEXT_Y_OFFSET);

        if (drawBorder) {
            final Color frame = buttonBorderColor(tb.isSelected());
            nhi.drawRectangle(frame, 2f * Layout.BUTTON_BORDER_PX, 0, 0, w, h);
        }

        return nhi.submit();
    }

    @Override
    public GameImage drawScrollBoxBackground(final int w, final int h) {
        final GameImage scrollBox = new GameImage(w, h);

        final int vw = VEVE.getWidth(), vh = VEVE.getHeight(),
                timesX = w / vw, timesY = h / vh;

        for (int x = 0; x <= timesX; x++)
            for (int y = 0; y <= timesY; y++)
                scrollBox.draw(VEVE, x * vw, y * vh);

        return scrollBox.submit();
    }

    @Override
    public GameImage[] loadSplash() {
        final int SPLASH_FRAMES = 12, SPLASH_SCALE_UP = 5;
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
