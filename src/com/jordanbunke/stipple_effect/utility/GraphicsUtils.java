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

import java.awt.*;
import java.nio.file.Path;
import java.util.function.BiFunction;

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

    public static GameImage drawTextBox(
            final int width, final String text, final int cursorIndex,
            final boolean isHighlighted, final Color accentColor
    ) {
        final int height = Constants.STD_TEXT_BUTTON_H, px = Constants.BUTTON_BORDER_PX;

        final GameImage nhi = new GameImage(width, height);
        nhi.fillRectangle(Constants.GREY, 0, 0, width, height);

        // text and cursor

        final String a = text.substring(0, cursorIndex), b = text.substring(cursorIndex);
        final GameImage
                aImage = uiText(Constants.BLACK).addText(a).build().draw(),
                bImage = uiText(Constants.BLACK).addText(b).build().draw();

        final Coord2D textPos = new Coord2D(2 * px, Constants.BUTTON_TEXT_OFFSET_Y);
        nhi.draw(aImage, textPos.x, textPos.y);
        nhi.fillRectangle(accentColor, textPos.x + aImage.getWidth() + px, 0, px, height);
        nhi.draw(bImage, textPos.x + aImage.getWidth() + (3 * px), textPos.y);

        // border

        nhi.drawRectangle(accentColor, 2f * px,
                0, 0, width, height);

        if (isHighlighted) {
            return drawHighlightedButton(nhi.submit());
        }

        return nhi.submit();
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

    public static GameImage drawOverlay(
            final int w, final int h, final int z,
            final BiFunction<Integer, Integer, Boolean> maskValidator,
            final Color inside, final Color outside, final boolean fill
    ) {
        if (z == 0)
            return GameImage.dummy();

        final GameImage initialMaskFill = new GameImage(
                Math.max(1, w * z), Math.max(1, h * z));

        final Color marked = Constants.BLACK;

        for (int x = 0; x < initialMaskFill.getWidth(); x += z)
            for (int y = 0; y < initialMaskFill.getHeight(); y += z)
                if (maskValidator.apply(x / z, y / z))
                    initialMaskFill.fillRectangle(marked,
                            x, y, z, z);
        initialMaskFill.free();

        final GameImage overlay = new GameImage(
                initialMaskFill.getWidth() + (2 * Constants.OVERLAY_BORDER_PX),
                initialMaskFill.getHeight() + (2 * Constants.OVERLAY_BORDER_PX));

        for (int x = 0; x < initialMaskFill.getWidth(); x++)
            for (int y = 0; y < initialMaskFill.getHeight(); y++) {
                final Color c = ImageProcessing.colorAtPixel(initialMaskFill, x, y);

                if (!c.equals(marked))
                    continue;

                final Coord2D o = new Coord2D(x + Constants.OVERLAY_BORDER_PX,
                        y + Constants.OVERLAY_BORDER_PX);

                boolean matched = false;

                // left is off canvas or not marked
                if (x - 1 < 0 || !ImageProcessing.colorAtPixel(
                        initialMaskFill, x - 1, y).equals(marked)) {
                    overlay.dot(inside, o.x, o.y);
                    overlay.dot(outside, o.x - 1, o.y);
                    matched = true;
                }
                // right is off canvas or not marked
                if (x + 1 >= initialMaskFill.getWidth() ||
                        !ImageProcessing.colorAtPixel(initialMaskFill,
                                x + 1, y).equals(marked)) {
                    overlay.dot(inside, o.x, o.y);
                    overlay.dot(outside, o.x + 1, o.y);
                    matched = true;
                }
                // top is off canvas or not marked
                if (y - 1 < 0 || !ImageProcessing.colorAtPixel(
                        initialMaskFill, x, y - 1).equals(marked)) {
                    overlay.dot(inside, o.x, o.y);
                    overlay.dot(outside, o.x, o.y - 1);
                    matched = true;
                }
                // bottom is off canvas or not marked
                if (y + 1 >= initialMaskFill.getHeight() ||
                        !ImageProcessing.colorAtPixel(initialMaskFill,
                                x, y + 1).equals(marked)) {
                    overlay.dot(inside, o.x, o.y);
                    overlay.dot(outside, o.x, o.y + 1);
                    matched = true;
                }

                if (fill && !matched) {
                    overlay.dot(Constants.OVERLAY_FILL_C, o.x, o.y);
                }
            }

        return overlay.submit();
    }

    public static GameImage getIcon(final String iconID) {
        final Path iconFile = Constants.ICON_FOLDER.resolve(
                iconID.toLowerCase() + ".png");
        return ResourceLoader.loadImageResource(iconFile);
    }

    public static GameImage highlightIconButton(final GameImage icon) {
        final GameImage highlighted = new GameImage(icon);
        highlighted.draw(HIGHLIGHT_OVERLAY);

        return highlighted.submit();
    }

    public static MenuElement generateIconButton(
            final String iconID, final Coord2D position,
            final boolean precondition, final Runnable behaviour
    ) {
        final boolean stub = !precondition || behaviour == null;

        final Coord2D dims = new Coord2D(Constants.BUTTON_DIM, Constants.BUTTON_DIM);

        final GameImage icon = getIcon(iconID);

        if (stub)
            return new StaticMenuElement(position, dims,
                    MenuElement.Anchor.LEFT_TOP, greyscaleVersionOf(icon));

        final GameImage highlighted = highlightIconButton(icon);

        return new SimpleMenuButton(position, dims, MenuElement.Anchor.LEFT_TOP,
                true, behaviour, icon, highlighted);
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
