package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconButton;

import java.awt.*;
import java.nio.file.Path;
import java.util.function.BiFunction;

public class GraphicsUtils {
    public static final GameImage
            HIGHLIGHT_OVERLAY = loadIcon("highlighted"),
            SELECT_OVERLAY = loadIcon("selected"),
            TRANSFORM_NUB = ResourceLoader.loadImageResource(
                    Constants.MISC_FOLDER.resolve("transform_nub.png"));

    public static TextBuilder uiText() {
        return uiText(Constants.WHITE);
    }

    public static TextBuilder uiText(final Color color) {
        return uiText(color, 1d);
    }

    public static TextBuilder uiText(final Color color, final double textSize) {
        return new TextBuilder(textSize, Text.Orientation.CENTER, color,
                Settings.getProgramFont().associated());
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
        final Color mainTextC = textButtonColorFromBackgroundColor(
                        backgroundColor, true),
                affixTextC = textButtonColorFromBackgroundColor(
                        backgroundColor, false);

        final int height = Layout.STD_TEXT_BUTTON_H,
                px = Layout.BUTTON_BORDER_PX;

        final GameImage nhi = new GameImage(width, height);
        nhi.fillRectangle(backgroundColor, 0, 0, width, height);

        // text and cursor

        final String a = text.substring(0, cursorIndex), b = text.substring(cursorIndex);
        final GameImage
                prefixImage = uiText(affixTextC).addText(prefix).build().draw(),
                suffixImage = uiText(affixTextC).addText(suffix).build().draw(),
                aImage = uiText(mainTextC).addText(a).build().draw(),
                bImage = uiText(mainTextC).addText(b).build().draw();

        Coord2D textPos = new Coord2D(2 * px, Layout.BUTTON_TEXT_OFFSET_Y);

        nhi.draw(prefixImage, textPos.x, textPos.y);

        if (!prefix.isEmpty())
            textPos = textPos.displace(prefixImage.getWidth() + px, 0);

        nhi.draw(aImage, textPos.x, textPos.y);

        if (!a.isEmpty())
            textPos = textPos.displace(aImage.getWidth() + px, 0);

        nhi.fillRectangle(mainTextC, textPos.x, 0, px, height);
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

    public static GameImage drawDropDownButton(
            final int width, final String text,
            final boolean isSelected, final Color backgroundColor
    ) {
        final GameImage base = drawTextButton(width, text, isSelected,
                backgroundColor, true);

        final GameImage icon = GraphicsUtils.loadIcon(isSelected
                ? IconCodes.COLLAPSE : IconCodes.EXPAND);

        base.draw(icon, base.getWidth() - (Layout.BUTTON_INC), Layout.BUTTON_BORDER_PX);

        return base.submit();
    }

    public static GameImage drawTextButton(
            final int width, final String text,
            final boolean isSelected, final Color backgroundColor,
            final boolean leftAligned
    ) {
        final Color textColor = textButtonColorFromBackgroundColor(
                backgroundColor, true);
        final GameImage textImage = GraphicsUtils.uiText(textColor)
                .addText(text).build().draw();

        final int w = Math.max(width, textImage.getWidth() +
                (4 * Layout.BUTTON_BORDER_PX)),
                h = Layout.STD_TEXT_BUTTON_H;

        final GameImage nhi = new GameImage(w, h);
        nhi.fillRectangle(backgroundColor, 0, 0, w, h);

        final int x = leftAligned
                ? (2 * Layout.BUTTON_BORDER_PX)
                : (w - textImage.getWidth()) / 2;

        nhi.draw(textImage, x, Layout.BUTTON_TEXT_OFFSET_Y);
        final Color frame = GraphicsUtils.buttonBorderColor(isSelected);
        nhi.drawRectangle(frame, 2f * Layout.BUTTON_BORDER_PX, 0, 0, w, h);

        return nhi.submit();
    }

    public static GameImage drawTextButton(
            final int width, final String text,
            final boolean isSelected, final Color backgroundColor
    ) {
        return drawTextButton(width, text, isSelected, backgroundColor, false);
    }

    private static Color textButtonColorFromBackgroundColor(
            final Color b, final boolean main
    ) {
        return (b.getRed() + b.getGreen() + b.getBlue()) / 3 >
                Layout.COLOR_TEXTBOX_AVG_C_THRESHOLD
                ? (main ? Constants.BLACK : Constants.BACKGROUND)
                : (main ? Constants.WHITE : Constants.GREY);
    }

    public static GameImage drawSelectedTextBox(
            final GameImage bounds
    ) {
        final GameImage selected = new GameImage(bounds);
        final int w = selected.getWidth();
        selected.draw(loadIcon(IconCodes.BULLET_POINT),
                w - Layout.BUTTON_INC, Layout.BUTTON_BORDER_PX);

        return selected.submit();
    }

    public static GameImage drawHighlightedButton(
            final GameImage nhi
    ) {
        final GameImage hi = new GameImage(nhi);
        final int w = hi.getWidth(), h = hi.getHeight();
        hi.fillRectangle(Constants.HIGHLIGHT_2, 0, 0, w, h);
        hi.drawRectangle(Constants.BLACK, 2f * Layout.BUTTON_BORDER_PX,
                0, 0, w, h);

        return hi.submit();
    }

    public static GameImage drawOverlay(
            final int w, final int h, final double z,
            final BiFunction<Integer, Integer, Boolean> maskValidator,
            final Color inside, final Color outside,
            final boolean filled, final boolean canTransform
    ) {
        final int scaleUpW = (int)Math.max(1, w * z),
                scaleUpH = (int)Math.max(1, h * z),
                zoomInc = (int)(Math.max(1, z));

        final GameImage overlay = new GameImage(
                scaleUpW + (2 * Constants.OVERLAY_BORDER_PX),
                scaleUpH + (2 * Constants.OVERLAY_BORDER_PX));

        for (int x = 0; x < scaleUpW; x += zoomInc)
            for (int y = 0; y < scaleUpH; y += zoomInc) {
                if (!maskValidator.apply((int)(x / z), (int)(y / z)))
                    continue;

                final Coord2D o = new Coord2D(x + Constants.OVERLAY_BORDER_PX,
                        y + Constants.OVERLAY_BORDER_PX);

                if (filled)
                    overlay.fillRectangle(Constants.OVERLAY_FILL_C,
                            o.x, o.y, zoomInc, zoomInc);

                // left is off canvas or not marked
                if (x - z < 0 || !maskValidator.apply((int)((x / z) - 1), (int)(y / z))) {
                    overlay.fillRectangle(inside, o.x, o.y, 1, zoomInc);
                    overlay.fillRectangle(outside, o.x - 1, o.y, 1, zoomInc);
                }
                // right is off canvas or not marked
                if (x + z >= scaleUpW || !maskValidator.apply((int)((x / z) + 1), (int)(y / z))) {
                    overlay.fillRectangle(inside, (o.x + zoomInc) - 1, o.y, 1, zoomInc);
                    overlay.fillRectangle(outside, o.x + zoomInc, o.y, 1, zoomInc);
                }
                // top is off canvas or not marked
                if (y - z < 0 || !maskValidator.apply((int)(x / z), (int)((y / z) - 1))) {
                    overlay.fillRectangle(inside, o.x, o.y, zoomInc, 1);
                    overlay.fillRectangle(outside, o.x, o.y - 1, zoomInc, 1);
                }
                // bottom is off canvas or not marked
                if (y + z >= scaleUpH || !maskValidator.apply((int)(x / z), (int)((y / z) + 1))) {
                    overlay.fillRectangle(inside, o.x, (o.y + zoomInc) - 1, zoomInc, 1);
                    overlay.fillRectangle(outside, o.x, o.y + zoomInc, zoomInc, 1);
                }
            }

        if (canTransform) {
            final int BEG = 0, MID = 1, END = 2;
            final int[] xs = new int[] {
                    0, (overlay.getWidth() - TRANSFORM_NUB.getWidth()) / 2,
                    overlay.getWidth() - TRANSFORM_NUB.getWidth()
            }, ys = new int[] {
                    0, (overlay.getHeight() - TRANSFORM_NUB.getHeight()) / 2,
                    overlay.getHeight() - TRANSFORM_NUB.getHeight()
            };

            overlay.draw(TRANSFORM_NUB, xs[BEG], ys[BEG]);
            overlay.draw(TRANSFORM_NUB, xs[BEG], ys[END]);
            overlay.draw(TRANSFORM_NUB, xs[END], ys[BEG]);
            overlay.draw(TRANSFORM_NUB, xs[END], ys[END]);
            overlay.draw(TRANSFORM_NUB, xs[BEG], ys[MID]);
            overlay.draw(TRANSFORM_NUB, xs[END], ys[MID]);
            overlay.draw(TRANSFORM_NUB, xs[MID], ys[BEG]);
            overlay.draw(TRANSFORM_NUB, xs[MID], ys[END]);
        }

        return overlay.submit();
    }

    public static GameImage loadIcon(final String iconID) {
        final Path iconFile = Constants.ICON_FOLDER.resolve(
                iconID.toLowerCase() + ".png");
        return ResourceLoader.loadImageResource(iconFile);
    }

    public static GameImage highlightIconButton(final GameImage icon) {
        final GameImage highlighted = new GameImage(HIGHLIGHT_OVERLAY);
        highlighted.draw(icon);

        return highlighted.submit();
    }

    public static MenuElement generateIconButton(
            final String iconID, final Coord2D position,
            final boolean precondition, final Runnable behaviour
    ) {
        final boolean stub = !precondition || behaviour == null;

        if (stub)
            return new StaticMenuElement(position,
                    new Coord2D(Layout.BUTTON_DIM, Layout.BUTTON_DIM),
                    MenuElement.Anchor.LEFT_TOP,
                    greyscaleVersionOf(loadIcon(iconID)));

        return IconButton.make(iconID, position, behaviour);
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
