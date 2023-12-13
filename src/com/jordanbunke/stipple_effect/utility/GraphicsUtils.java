package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.text.SEFonts;

import java.awt.*;
import java.nio.file.Path;

public class GraphicsUtils {
    public static final GameImage
            HIGHLIGHT_OVERLAY = ResourceLoader.loadImageResource(Constants.ICON_FOLDER.resolve("highlighted.png")),
            SELECT_OVERLAY = ResourceLoader.loadImageResource(Constants.ICON_FOLDER.resolve("selected.png"));

    public static TextBuilder uiText() {
        return uiText(Constants.WHITE);
    }

    public static TextBuilder uiText(final Color color) {
        return uiText(color, 1d);
    }

    public static TextBuilder uiText(final Color color, final double textSize) {
        return new TextBuilder(textSize, Text.Orientation.CENTER, color, SEFonts.CLASSIC.getStandard());
    }

    public static Color buttonBorderColor(final boolean selected) {
        return selected ? Constants.HIGHLIGHT_1 : Constants.BLACK;
    }

    public static Color buttonBorderColorAlt(final boolean selected) {
        return selected ? Constants.HIGHLIGHT_1 : Constants.WHITE;
    }

    public static GameImage drawTextButton(
            final int width, final String text,
            final boolean isSelected, final Color backgroundColor
    ) {
        final int height = Constants.STD_TEXT_BUTTON_H;

        final Color textColor = (backgroundColor.getRed() + backgroundColor.getGreen() +
                backgroundColor.getBlue()) / 3 > Constants.COLOR_BUTTON_AVG_C_THRESHOLD
                ? Constants.BLACK : Constants.WHITE;

        final GameImage nhi = new GameImage(width, height);
        nhi.fillRectangle(backgroundColor, 0, 0, width, height);
        final GameImage textImage = GraphicsUtils.uiText(textColor)
                .addText(text).build().draw();
        nhi.draw(textImage, (width - textImage.getWidth()) / 2, Constants.BUTTON_TEXT_OFFSET_Y);
        final Color frame = GraphicsUtils.buttonBorderColor(isSelected);
        nhi.drawRectangle(frame, 2f * Constants.BUTTON_BORDER_PX, 0, 0, width, height);

        return nhi.submit();
    }

    public static GameImage drawHighlightedButton(
            final GameImage nhi
    ) {
        final GameImage hi = new GameImage(nhi);
        final int w = hi.getWidth(), h = hi.getHeight();
        hi.fillRectangle(Constants.HIGHLIGHT_2, 0, 0, w, h);
        hi.drawRectangle(Constants.BLACK, 2f * Constants.BUTTON_BORDER_PX,
                0, 0, w, h);

        return hi.submit();
    }

    public static MenuElement generateIconButton(
            final String iconID, final Coord2D position,
            final boolean precondition, final Runnable behaviour
    ) {
        final boolean stub = !precondition || behaviour == null;

        final Coord2D dims = new Coord2D(Constants.BUTTON_DIM, Constants.BUTTON_DIM);

        final Path iconFile = Constants.ICON_FOLDER.resolve(
                iconID.toLowerCase() + ".png");
        final GameImage icon = ResourceLoader.loadImageResource(iconFile);

        if (stub)
            return new StaticMenuElement(position, dims,
                    MenuElement.Anchor.LEFT_TOP, greyscaleVersionOf(icon));

        final GameImage highlighted = new GameImage(icon);
        highlighted.draw(HIGHLIGHT_OVERLAY);

        return new SimpleMenuButton(position, dims,
                MenuElement.Anchor.LEFT_TOP, true, behaviour,
                icon, highlighted.submit());
    }

    private static GameImage greyscaleVersionOf(final GameImage image) {
        final int w = image.getWidth(), h = image.getHeight();
        final GameImage greyscale = new GameImage(w, h);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final Color cWas = ImageProcessing.colorAtPixel(image, x, y);
                final int rgbAvg = (cWas.getRed() + cWas.getGreen() + cWas.getBlue()) / 3;
                final Color cIs = new Color(rgbAvg, rgbAvg, rgbAvg, cWas.getAlpha());

                greyscale.dot(cIs, x, y);
            }
        }

        return greyscale.submit();
    }
}
