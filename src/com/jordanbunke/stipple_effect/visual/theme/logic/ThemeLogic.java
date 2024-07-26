package com.jordanbunke.stipple_effect.visual.theme.logic;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.ParserUtils;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.DropdownExpansion;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.TextButton;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;
import java.nio.file.Path;
import java.util.function.Function;

public abstract class ThemeLogic {
    protected static final Path THEMES_FOLDER = Path.of("themes");

    public GameImage transformIcon(final GameImage asset) {
        return contextualTransformation(asset, this::transformIconPixel);
    }

    Color transformIconPixel(
            final GameImage asset, final int x, final int y
    ) {
        return asset.getColorAt(x, y);
    }

    public GameImage unclickableIcon(final GameImage baseIcon) {
        return pixelWiseTransformation(baseIcon, c -> c.equals(SEColors.white())
                ? SEColors.transparent() : GraphicsUtils.greyscale(c));
    }

    public abstract GameImage[] loadSplash();

    public int ticksPerSplashFrame() {
        return 5;
    }

    public GameImage drawScrollBoxBackground(final int w, final int h) {
        final GameImage background = new GameImage(w, h);
        background.fillRectangle(
                Settings.getTheme().scrollBackground, 0, 0, w, h);
        return background.submit();
    }

    public GameImage drawCheckbox(
            final boolean highlighted, final boolean checked
    ) {
        final int w = Layout.ICON_DIMS.width(), h = Layout.ICON_DIMS.height();

        final GameImage checkbox = new GameImage(w, h);
        checkbox.fillRectangle(Settings.getTheme().buttonBody, 0, 0, w, h);

        if (checked)
            checkbox.draw(GraphicsUtils.CHECKMARK);

        final Color frame = buttonBorderColor(false);
        checkbox.drawRectangle(frame, 2f * Layout.BUTTON_BORDER_PX,
                0, 0, w, h);

        return highlighted ? highlightButton(checkbox.submit())
                : checkbox.submit();
    }

    public GameImage drawSliderBall(
            final boolean selected, final boolean highlighted, final Color fill
    ) {
        final int sbd = Layout.SLIDER_BALL_DIM,
                sbcd = sbd - (2 * Layout.BUTTON_BORDER_PX);

        final GameImage ball = new GameImage(sbd, sbd);
        final Color border = buttonBorderColor(selected);

        ball.fillRectangle(border, 0, 0, sbd, sbd);
        ball.fillRectangle(fill, Layout.BUTTON_BORDER_PX,
                Layout.BUTTON_BORDER_PX, sbcd, sbcd);

        ball.free();

        return highlighted ? highlightSliderBall(ball) : ball;
    }

    public void drawHorizontalSliderOutline(final GameImage vesselWithCore) {
        final int sd = vesselWithCore.getWidth() - Layout.SLIDER_BALL_DIM;

        vesselWithCore.drawRectangle(Settings.getTheme().buttonOutline,
                Layout.BUTTON_BORDER_PX, Layout.SLIDER_BALL_DIM / 2,
                Layout.SLIDER_THINNING + (Layout.BUTTON_BORDER_PX / 2), sd,
                vesselWithCore.getHeight() - (Layout.BUTTON_BORDER_PX +
                        (2 * Layout.SLIDER_THINNING)));
    }

    public void drawVerticalSliderOutline(final GameImage vesselWithCore) {
        final int sd = vesselWithCore.getHeight() - Layout.SLIDER_BALL_DIM;

        vesselWithCore.drawRectangle(Settings.getTheme().buttonOutline,
                Layout.BUTTON_BORDER_PX,
                Layout.SLIDER_THINNING + (Layout.BUTTON_BORDER_PX / 2),
                Layout.SLIDER_BALL_DIM / 2,
                vesselWithCore.getWidth() - (Layout.BUTTON_BORDER_PX +
                        (2 * Layout.SLIDER_THINNING)), sd);
    }

    public final GameImage drawDropdownMenuLeafStub(final TextButton tb) {
        return drawDropdownMenuLeaf(tb, false);
    }

    protected GameImage drawDropdownMenuLeaf(final TextButton tb, final boolean conditionPassed) {
        final Theme t = Settings.getTheme();
        final String code = tb.getLabel();

        final GameImage baseIcon = GraphicsUtils.loadIcon(code),
                icon = conditionPassed ? baseIcon : unclickableIcon(baseIcon);
        final int w = tb.getWidth(), h = Layout.STD_TEXT_BUTTON_H,
                textY = Layout.TEXT_Y_OFFSET;

        final GameImage button = new GameImage(w, h);

        final Color backgroundColor = t.dropdownOptionBody;
        final Color actionCol = intuitTextColor(backgroundColor, conditionPassed),
                shortcutCol = intuitTextColor(backgroundColor, false);
        button.fill(backgroundColor);

        button.draw(icon, Layout.BUTTON_OFFSET, Layout.BUTTON_OFFSET);

        final String[] lines = ParserUtils.getToolTip(code);

        if (lines.length >= 1) {
            final String[] segments = ParserUtils.extractHighlight(lines[0]);

            final GameImage actionImage = switch (segments.length) {
                case 2 -> {
                    final String action = segments[0].replace("|", "").trim(),
                            shortcut = segments[1].trim();

                    final GameImage shortcutImage = GraphicsUtils.uiText(shortcutCol)
                            .addText(shortcut).build().draw();
                    button.draw(shortcutImage, w - (shortcutImage.getWidth() +
                            Layout.CONTENT_BUFFER_PX), textY);

                    yield GraphicsUtils.uiText(actionCol)
                            .addText(action).build().draw();
                }
                case 1 -> GraphicsUtils.uiText(actionCol)
                        .addText(segments[0]).build().draw();
                default -> GameImage.dummy();
            };

            final int actionTextX = Layout.CONTENT_BUFFER_PX + Layout.BUTTON_INC;
            button.draw(actionImage, actionTextX, textY);

            if (tb.isHighlighted()) {
                final int underlineY = Layout.STD_TEXT_BUTTON_H - 4;
                button.drawLine(actionCol, 1f, actionTextX, underlineY,
                        actionTextX + actionImage.getWidth(), underlineY);
            }
        }

        return button.submit();
    }

    public GameImage drawTextButton(final TextButton tb) {
        final Theme t = Settings.getTheme();
        final ButtonType type = tb.getButtonType();

        if (type == ButtonType.DD_MENU_LEAF)
            return drawDropdownMenuLeaf(tb, true);

        final Color backgroundColor = switch (type) {
            case DD_OPTION -> t.dropdownOptionBody;
            case STUB -> t.stubButtonBody;
            default -> t.buttonBody;
        };

        final boolean drawBorder = type != ButtonType.DD_OPTION;

        final Color textColor = intuitTextColor(backgroundColor, true);
        final GameImage textImage = GraphicsUtils.uiText(textColor)
                .addText(tb.getLabel()).build().draw();

        final int w = Math.max(tb.getWidth(), textImage.getWidth() +
                (4 * Layout.BUTTON_BORDER_PX)),
                h = Layout.STD_TEXT_BUTTON_H;

        final GameImage nhi = new GameImage(w, h);
        nhi.fill(backgroundColor);

        final int x = switch (tb.getAlignment()) {
            case LEFT -> (2 * Layout.BUTTON_BORDER_PX);
            case CENTER -> (w - textImage.getWidth()) / 2;
            case RIGHT -> w - (textImage.getWidth() + (2 * Layout.BUTTON_BORDER_PX));
        };

        nhi.draw(textImage, x, Layout.TEXT_Y_OFFSET);

        // dropdown list button
        if (type == ButtonType.DD_HEAD) {
            final GameImage icon = GraphicsUtils.loadIcon(
                    tb.isSelected() ? IconCodes.COLLAPSE : IconCodes.EXPAND);

            nhi.draw(icon, w - (Layout.BUTTON_INC), Layout.BUTTON_BORDER_PX);
        }

        if (drawBorder) {
            final Color frame = buttonBorderColor(tb.isSelected());
            nhi.drawRectangle(frame, 2f * Layout.BUTTON_BORDER_PX, 0, 0, w, h);
        }

        return tb.isHighlighted() ? highlightButton(nhi.submit()) : nhi.submit();
    }

    public GameImage drawDropdownHeader(
            final TextButton ddh, final DropdownExpansion expansion
    ) {
        final Theme t = Settings.getTheme();

        final int w = ddh.getWidth(), h = Layout.STD_TEXT_BUTTON_H,
                textY = Layout.TEXT_Y_OFFSET,
                underlineY = Layout.STD_TEXT_BUTTON_H - 4;

        final GameImage button = new GameImage(w, h);

        final Color backgroundColor = ddh.isSelected() ? t.buttonBody
                : (expansion == DropdownExpansion.DOWN
                ? t.panelBackground : t.dropdownOptionBody);
        final Color textColor = intuitTextColor(backgroundColor, true);
        button.fill(backgroundColor);

        final GameImage text = GraphicsUtils.uiText(textColor)
                .addText(ddh.getLabel()).build().draw();
        button.draw(text, Layout.CONTENT_BUFFER_PX, textY);

        if (ddh.isHighlighted() && !ddh.isSelected())
            button.drawLine(textColor, 1f, Layout.CONTENT_BUFFER_PX,
                    underlineY, Layout.CONTENT_BUFFER_PX + text.getWidth(),
                    underlineY);

        if (expansion == DropdownExpansion.RIGHT) {
            final String expText = ddh.isSelected() ? "<" : ">";
            final GameImage exp = GraphicsUtils.uiText(textColor)
                    .addText(expText).build().draw();
            button.draw(exp, w - (exp.getWidth() +
                    Layout.CONTENT_BUFFER_PX), textY);
        }

        if (ddh.isSelected())
            button.drawRectangle(t.dropdownOptionBody, 4f, 0, 0, w, h);

        return button.submit();
    }

    public GameImage highlightSliderBall(final GameImage baseSliderBall) {
        return highlightButton(baseSliderBall);
    }

    public GameImage highlightButton(final GameImage baseButton) {
        final GameImage hi = new GameImage(baseButton);
        final int w = hi.getWidth(), h = hi.getHeight();
        hi.fillRectangle(Settings.getTheme().highlightOverlay, 0, 0, w, h);
        hi.drawRectangle(Settings.getTheme().buttonOutline,
                2f * Layout.BUTTON_BORDER_PX, 0, 0, w, h);

        return hi.submit();
    }

    public Color buttonBorderColor(final boolean selected) {
        final Theme t = Settings.getTheme();

        return selected ? t.highlightOutline : t.buttonOutline;
    }

    public GameImage drawTextbox(
            final int width,
            final String prefix, final String text, final String suffix,
            final int cursorIndex, final int selectionIndex,
            final boolean highlighted, final Color accentColor,
            final Color backgroundColor
    ) {
        final Color mainTextC = intuitTextColor(backgroundColor, true),
                affixTextC = intuitTextColor(backgroundColor, false);

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
        final GameImage prefixImage = GraphicsUtils.uiText(affixTextC)
                .addText(prefix).build().draw(),
                suffixImage = GraphicsUtils.uiText(affixTextC)
                        .addText(suffix).build().draw(),
                preSelImage = GraphicsUtils.uiText(mainTextC)
                        .addText(preSel).build().draw(),
                selImage = GraphicsUtils.uiText(mainTextC)
                        .addText(sel).build().draw(),
                postSelImage = GraphicsUtils.uiText(mainTextC)
                        .addText(postSel).build().draw();

        Coord2D textPos = new Coord2D(2 * px, Layout.TEXT_Y_OFFSET);

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
            nhi.fillRectangle(Settings.getTheme().highlightOverlay,
                    textPos.x - px, 0, selImage.getWidth() + (2 * px), height);
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
        if (highlighted)
            return highlightButton(nhi.submit());
        else
            return nhi.submit();
    }

    public GameImage highlightedIconOverlay() {
        return GraphicsUtils.readIconAsset("highlighted");
    }

    public GameImage selectedIconOverlay() {
        return GraphicsUtils.readIconAsset("selected");
    }

    // helpers from here
    public static Color intuitTextColor(
            Color background, final boolean main
    ) {
        final Theme t = Settings.getTheme();

        if (background.getAlpha() == 0)
            background = t.panelBackground;

        final Color light = main ? t.textLight : t.affixTextLight,
                dark = main ? t.textDark : t.affixTextDark;

        final double diffL = ColorMath.diff(background, light),
                diffD = ColorMath.diff(background, dark);

        return diffD > diffL ? dark : light;
    }

    public static GameImage hueFromColorTransformation(
            final GameImage asset, final Color ref
    ) {
        final double hue = ColorMath.rgbToHue(ref);

        return pixelWiseTransformation(asset, c -> c.getAlpha() == 0 ? c
                : ColorMath.fromHSV(hue, ColorMath.rgbToSat(c),
                ColorMath.rgbToValue(c), c.getAlpha()));
    }

    public static GameImage contextualTransformation(
            final GameImage input, final ColorProducer f
    ) {
        final GameImage output = new GameImage(input);

        final int w = output.getWidth(), h = output.getHeight();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                output.setRGB(x, y, f.apply(input, x, y).getRGB());

        return output.submit();
    }

    public static GameImage pixelWiseTransformation(
            final GameImage input, final Function<Color, Color> f
    ) {
        final GameImage output = new GameImage(input);

        final int w = output.getWidth(), h = output.getHeight();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                output.setRGB(x, y, f.apply(input.getColorAt(x, y)).getRGB());

        return output.submit();
    }

    @FunctionalInterface
    interface ColorProducer {
        Color apply(final GameImage input, final int x, final int y);
    }
}
