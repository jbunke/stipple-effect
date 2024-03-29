package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.invisible.ThinkingMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconToggleButton;

import java.awt.*;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GraphicsUtils {
    public static final GameImage
            HIGHLIGHT_OVERLAY = loadIcon("highlighted"),
            SELECT_OVERLAY = loadIcon("selected"),
            TRANSFORM_NODE = ResourceLoader.loadImageResource(
                    Constants.MISC_FOLDER.resolve("transform_node.png"));

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
            final int cursorIndex, final int selectionIndex,
            final boolean isHighlighted, final Color accentColor,
            final Color backgroundColor
    ) {
        final Color mainTextC = textButtonColorFromBackgroundColor(
                        backgroundColor, true),
                affixTextC = textButtonColorFromBackgroundColor(
                        backgroundColor, false);

        final int left = Math.min(cursorIndex, selectionIndex),
                right = Math.max(cursorIndex, selectionIndex);

        final boolean hasSelection = left != right,
                cursorAtRight = cursorIndex == right;

        final int height = Layout.STD_TEXT_BUTTON_H,
                px = Layout.BUTTON_BORDER_PX;

        final GameImage nhi = new GameImage(width, height);
        nhi.fillRectangle(backgroundColor, 0, 0, width, height);

        // text and cursor

        final String preSel = text.substring(0, left),
                sel = text.substring(left, right),
                postSel = text.substring(right);
        final GameImage
                prefixImage = uiText(affixTextC).addText(prefix).build().draw(),
                suffixImage = uiText(affixTextC).addText(suffix).build().draw(),
                preSelImage = uiText(mainTextC).addText(preSel).build().draw(),
                selImage = uiText(mainTextC).addText(sel).build().draw(),
                postSelImage = uiText(mainTextC).addText(postSel).build().draw();

        Coord2D textPos = new Coord2D(2 * px, Layout.BUTTON_TEXT_OFFSET_Y);

        // possible prefix
        nhi.draw(prefixImage, textPos.x, textPos.y);
        if (!prefix.isEmpty())
            textPos = textPos.displace(prefixImage.getWidth() + px, 0);

        // main text prior to possible selection
        nhi.draw(preSelImage, textPos.x, textPos.y);
        if (!preSel.isEmpty())
            textPos = textPos.displace(preSelImage.getWidth() + px, 0);

        // possible selection text
        if (hasSelection) {
            if (!cursorAtRight)
                textPos = textPos.displace(2 * px, 0);

            nhi.draw(selImage, textPos.x, textPos.y);
            nhi.fillRectangle(Constants.HIGHLIGHT_2, textPos.x - px, 0,
                    selImage.getWidth() + (2 * px), height);
            textPos = textPos.displace(selImage.getWidth() + px, 0);
        }

        // cursor
        nhi.fillRectangle(mainTextC, textPos.x - (cursorAtRight
                ? 0 : selImage.getWidth() + (3 * px)), 0, px, height);
        if (cursorAtRight)
            textPos = textPos.displace(2 * px, 0);

        // main text following possible selection
        nhi.draw(postSelImage, textPos.x, textPos.y);
        if (!postSel.isEmpty())
            textPos = textPos.displace(postSelImage.getWidth() + px, 0);

        // possible suffix
        nhi.draw(suffixImage, textPos.x, textPos.y);

        // border
        nhi.drawRectangle(accentColor, 2f * px,
                0, 0, width, height);

        // highlighting
        if (isHighlighted)
            return drawHighlightedButton(nhi.submit());
        else
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

    public static SimpleMenuButton makeStandardTextButton(
            final String text, final Coord2D pos, final Runnable onClick
    ) {
        final GameImage base = drawTextButton(Layout.STD_TEXT_BUTTON_W,
                text, false, Constants.GREY);
        return new SimpleMenuButton(pos, new Coord2D(Layout.STD_TEXT_BUTTON_W,
                Layout.STD_TEXT_BUTTON_H), MenuElement.Anchor.LEFT_TOP,
                true, onClick, base, drawHighlightedButton(base));
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

    public static GameImage drawSelectionOverlay(
            final double z, final Set<Coord2D> selection,
            final boolean filled, final boolean canTransform
    ) {
        final Coord2D tl = SelectionUtils.topLeft(selection),
                br = SelectionUtils.bottomRight(selection);

        final Set<Coord2D> adjusted = selection.stream()
                .map(p -> p.displace(-tl.x, -tl.y))
                .collect(Collectors.toSet());
        final int w = br.x - tl.x, h = br.y - tl.y;

        return drawOverlay(w, h, z, adjusted, Constants.BLACK,
                Constants.HIGHLIGHT_1, filled, canTransform);
    }

    public static GameImage drawOverlay(
            final int w, final int h, final double z,
            final BiFunction<Integer, Integer, Boolean> maskValidator,
            final Color inside, final Color outside,
            final boolean filled, final boolean canTransform
    ) {
        final Set<Coord2D> mask = new HashSet<>();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (maskValidator.apply(x, y))
                    mask.add(new Coord2D(x, y));

        return drawOverlay(w, h, z, mask, inside, outside,
                filled, canTransform);
    }

    private static GameImage drawOverlay(
            final int w, final int h, final double z,
            final Set<Coord2D> selection,
            final Color inside, final Color outside,
            final boolean filled, final boolean canTransform
    ) {
        final int zoomInc = (int)Math.max(Constants.ZOOM_FOR_OVERLAY, z),
                scaleUpW = Math.max(1, w * zoomInc),
                scaleUpH = Math.max(1, h * zoomInc);

        final GameImage overlay = new GameImage(
                scaleUpW + (2 * Constants.OVERLAY_BORDER_PX),
                scaleUpH + (2 * Constants.OVERLAY_BORDER_PX));

        selection.stream().filter(
                p -> p.x >= 0 && p.x < w && p.y >= 0 && p.y < h
        ).forEach(pixel -> {
            boolean leftFrontier = false,
                    rightFrontier = false,
                    topFrontier = false,
                    bottomFrontier = false;

            // frontier defined as unmarked or off canvas
            if (pixel.x - 1 < 0 || !selection.contains(pixel.displace(-1, 0)))
                leftFrontier = true;
            if (pixel.x + 1 >= w || !selection.contains(pixel.displace(1, 0)))
                rightFrontier = true;
            if (pixel.y - 1 < 0 || !selection.contains(pixel.displace(0, -1)))
                topFrontier = true;
            if (pixel.y + 1 >= h || !selection.contains(pixel.displace(0, 1)))
                bottomFrontier = true;

            final Coord2D o = new Coord2D(
                    Constants.OVERLAY_BORDER_PX + (zoomInc * pixel.x),
                    Constants.OVERLAY_BORDER_PX + (zoomInc * pixel.y)
            );

            if (filled)
                overlay.fillRectangle(Constants.OVERLAY_FILL_C,
                        o.x, o.y, zoomInc, zoomInc);

            if (leftFrontier) {
                overlay.fillRectangle(inside, o.x, o.y, 1, zoomInc);
                overlay.fillRectangle(outside, o.x - 1, o.y, 1, zoomInc);
            }
            if (rightFrontier) {
                overlay.fillRectangle(inside, (o.x + zoomInc) - 1, o.y, 1, zoomInc);
                overlay.fillRectangle(outside, o.x + zoomInc, o.y, 1, zoomInc);
            }
            if (topFrontier) {
                overlay.fillRectangle(inside, o.x, o.y, zoomInc, 1);
                overlay.fillRectangle(outside, o.x, o.y - 1, zoomInc, 1);
            }
            if (bottomFrontier) {
                overlay.fillRectangle(inside, o.x, (o.y + zoomInc) - 1, zoomInc, 1);
                overlay.fillRectangle(outside, o.x, o.y + zoomInc, zoomInc, 1);
            }
        });

        if (canTransform) {
            final Coord2D tl = SelectionUtils.topLeft(selection),
                    br = SelectionUtils.bottomRight(selection);

            final int BEG = 0, MID = 1, END = 2;
            final int[] xs = new int[] {
                    tl.x * zoomInc,
                    (int)(((tl.x + br.x) / 2d) * zoomInc),
                    br.x * zoomInc
            }, ys = new int[] {
                    tl.y * zoomInc,
                    (int)(((tl.y + br.y) / 2d) * zoomInc),
                    br.y * zoomInc
            };

            for (int x = BEG; x <= END; x++)
                for (int y = BEG; y <= END; y++)
                    if (x != MID || y != MID)
                        overlay.draw(TRANSFORM_NODE, xs[x], ys[y]);
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
            final Supplier<Boolean> precondition, final Runnable behaviour
    ) {
        final IconButton icon = IconButton.make(iconID, position, behaviour);
        final StaticMenuElement stub = new StaticMenuElement(position,
                new Coord2D(Layout.BUTTON_DIM, Layout.BUTTON_DIM),
                MenuElement.Anchor.LEFT_TOP,
                greyscaleVersionOf(loadIcon(iconID)));

        return new ThinkingMenuElement(() -> precondition.get() ? icon : stub);
    }

    public static MenuElement generateIconToggleButton(
            final Coord2D position, final String[] codes,
            final Runnable[] behaviours,
            final Supplier<Integer> updateIndexLogic, final Runnable global,
            final Supplier<Boolean> precondition, final String stubIconCode
    ) {
        final IconToggleButton icon = IconToggleButton.make(position,
                codes, behaviours, updateIndexLogic, global);
        final StaticMenuElement stub = new StaticMenuElement(position,
                new Coord2D(Layout.BUTTON_DIM, Layout.BUTTON_DIM),
                MenuElement.Anchor.LEFT_TOP,
                greyscaleVersionOf(loadIcon(stubIconCode)));

        return new ThinkingMenuElement(() -> precondition.get() ? icon : stub);
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
