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
            final int width,
            final String prefix, final String text, final String suffix,
            final int cursorIndex,
            final boolean isHighlighted, final Color accentColor,
            final Color backgroundColor
    ) {
        final int height = Constants.STD_TEXT_BUTTON_H, px = Constants.BUTTON_BORDER_PX;

        final GameImage nhi = new GameImage(width, height);
        nhi.fillRectangle(backgroundColor, 0, 0, width, height);

        // text and cursor

        final String a = text.substring(0, cursorIndex), b = text.substring(cursorIndex);
        final GameImage
                prefixImage = uiText(Constants.BACKGROUND).addText(prefix).build().draw(),
                suffixImage = uiText(Constants.BACKGROUND).addText(suffix).build().draw(),
                aImage = uiText(Constants.BLACK).addText(a).build().draw(),
                bImage = uiText(Constants.BLACK).addText(b).build().draw();

        Coord2D textPos = new Coord2D(2 * px, Constants.BUTTON_TEXT_OFFSET_Y);

        nhi.draw(prefixImage, textPos.x, textPos.y);

        if (!prefix.isEmpty())
            textPos = textPos.displace(prefixImage.getWidth() + px, 0);

        nhi.draw(aImage, textPos.x, textPos.y);

        if (!a.isEmpty())
            textPos = textPos.displace(aImage.getWidth() + px, 0);

        nhi.fillRectangle(accentColor, textPos.x, 0, px, height);
        textPos = textPos.displace(2 * px, 0);

        nhi.draw(bImage, textPos.x, textPos.y);

        if (!b.isEmpty())
            textPos = textPos.displace(bImage.getWidth() + px, 0);

        nhi.draw(suffixImage, textPos.x, textPos.y);

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
        final Color textColor = (backgroundColor.getRed() + backgroundColor.getGreen() +
                backgroundColor.getBlue()) / 3 > Constants.COLOR_BUTTON_AVG_C_THRESHOLD
                ? Constants.BLACK : Constants.WHITE;
        final GameImage textImage = GraphicsUtils.uiText(textColor)
                .addText(text).build().draw();

        final int w = Math.max(width, textImage.getWidth() +
                (4 * Constants.BUTTON_BORDER_PX)),
                h = Constants.STD_TEXT_BUTTON_H;

        final GameImage nhi = new GameImage(w, h);
        nhi.fillRectangle(backgroundColor, 0, 0, w, h);

        nhi.draw(textImage, (w - textImage.getWidth()) / 2, Constants.BUTTON_TEXT_OFFSET_Y);
        final Color frame = GraphicsUtils.buttonBorderColor(isSelected);
        nhi.drawRectangle(frame, 2f * Constants.BUTTON_BORDER_PX, 0, 0, w, h);

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

        final int scaleUpW = Math.max(1, w * z), scaleUpH = Math.max(1, h * z);

        final GameImage overlay = new GameImage(
                scaleUpW + (2 * Constants.OVERLAY_BORDER_PX),
                scaleUpH + (2 * Constants.OVERLAY_BORDER_PX));

        for (int x = 0; x < scaleUpW; x += z)
            for (int y = 0; y < scaleUpH; y += z) {
                if (!maskValidator.apply(x / z, y / z))
                    continue;

                final Coord2D o = new Coord2D(x + Constants.OVERLAY_BORDER_PX,
                        y + Constants.OVERLAY_BORDER_PX);

                if (fill)
                    overlay.fillRectangle(Constants.OVERLAY_FILL_C, o.x, o.y, z, z);

                // left is off canvas or not marked
                if (x - z < 0 || !maskValidator.apply((x / z) - 1, y / z)) {
                    overlay.fillRectangle(inside, o.x, o.y, 1, z);
                    overlay.fillRectangle(outside, o.x - 1, o.y, 1, z);
                }
                // right is off canvas or not marked
                if (x + z >= scaleUpW || !maskValidator.apply((x / z) + 1, y / z)) {
                    overlay.fillRectangle(inside, (o.x + z) - 1, o.y, 1, z);
                    overlay.fillRectangle(outside, o.x + z, o.y, 1, z);
                }
                // top is off canvas or not marked
                if (y - z < 0 || !maskValidator.apply(x / z, (y / z) - 1)) {
                    overlay.fillRectangle(inside, o.x, o.y, z, 1);
                    overlay.fillRectangle(outside, o.x, o.y - 1, z, 1);
                }
                // bottom is off canvas or not marked
                if (y + z >= scaleUpH || !maskValidator.apply(x / z, (y / z) + 1)) {
                    overlay.fillRectangle(inside, o.x, (o.y + z) - 1, z, 1);
                    overlay.fillRectangle(outside, o.x, o.y + z, z, 1);
                }
            }

        return overlay.submit();
    }

    public static GameImage loadIcon(final String iconID) {
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

        final GameImage icon = loadIcon(iconID);

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
