package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.ThinkingMenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.tools.Tool;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.ParserUtils;
import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.CelButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconToggleButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.Alignment;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.StaticTextButton;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GraphicsUtils {
    public static GameImage HIGHLIGHT_OVERLAY,
            SELECT_OVERLAY, TRANSFORM_NODE, COLOR_NODE, CHECKMARK;
    private static final Map<CelButton.Status, GameImage> stencilMap;
    private static final Map<String, GameImage> iconMap;

    static {
        TRANSFORM_NODE = loadUtil("transform_node");
        COLOR_NODE = loadUtil("color_node");
        CHECKMARK = loadUtil("checkmark");

        iconMap = new HashMap<>();

        stencilMap = new HashMap<>();
        fetchStencils();

        final Theme theme = Settings.getTheme();

        HIGHLIGHT_OVERLAY = theme.logic.highlightedIconOverlay();
        SELECT_OVERLAY = theme.logic.selectedIconOverlay();
    }

    public static GameImage loadStencil(final CelButton.Status status) {
        return stencilMap.get(status);
    }

    private static GameImage loadUtil(final String code) {
        final GameImage asset = ResourceLoader.loadImageResource(
                Constants.MISC_FOLDER.resolve(code + ".png"));

        return Settings.getTheme().logic.transformIcon(asset);
    }

    public static void refreshAssets() {
        final Theme theme = Settings.getTheme();

        TRANSFORM_NODE = loadUtil("transform_node");
        COLOR_NODE = loadUtil("color_node");
        CHECKMARK = loadUtil("checkmark");

        HIGHLIGHT_OVERLAY = theme.logic.highlightedIconOverlay();
        SELECT_OVERLAY = theme.logic.selectedIconOverlay();

        iconMap.clear();

        SECursor.refresh();
        Arrays.stream(Tool.getAll()).forEach(Tool::refreshIcons);
    }

    private static void fetchStencils() {
        for (CelButton.Status status : CelButton.Status.values())
            stencilMap.put(status, ResourceLoader.loadImageResource(
                    Constants.MISC_FOLDER.resolve(
                            status.name().toLowerCase() + "_stencil.png")));
    }

    public static TextBuilder uiText(final Color color) {
        return uiText(color, 1d);
    }

    public static TextBuilder uiText(final Color color, final double textSize) {
        return new TextBuilder(textSize, Text.Orientation.CENTER, color,
                Settings.getProgramFont().associated());
    }

    public static GameImage drawTextbox(
            final Coord2D dimensions,
            final String prefix, final String text, final String suffix,
            final int cursorIndex, final int selectionIndex,
            final boolean valid, final boolean highlighted, final boolean typing
    ) {
        final Theme t = Settings.getTheme();

        final Color accent = typing ? t.highlightOutline : t.buttonOutline,
                background = valid ? t.buttonBody : t.invalid;

        return drawTextbox(dimensions.x, prefix, text, suffix,
                cursorIndex, selectionIndex, highlighted, accent, background);
    }

    public static GameImage drawTextbox(
            final int width,
            final String prefix, final String text, final String suffix,
            final int cursorIndex, final int selectionIndex,
            final boolean highlighted, final Color accentColor,
            final Color backgroundColor
    ) {
        return Settings.getTheme().logic.drawTextbox(
                width, prefix, text, suffix, cursorIndex, selectionIndex,
                highlighted, accentColor, backgroundColor);
    }

    public static StaticTextButton makeStandardTextButton(
            final String text, final Coord2D pos, final Runnable onClick
    ) {
        return new StaticTextButton(pos, text,
                Layout.STD_TEXT_BUTTON_W, onClick,
                Alignment.CENTER, ButtonType.STANDARD);
    }

    public static StaticTextButton makeBespokeTextButton(
            final String text, final Coord2D pos, final Runnable onClick
    ) {
        final int w = bespokeTextMenuElementWidth(text);

        return new StaticTextButton(pos, text, w, onClick,
                Alignment.CENTER, ButtonType.STANDARD);
    }

    public static int bespokeTextMenuElementWidth(final String text) {
        return uiText(SEColors.def())
                .addText(text).build().draw()
                .getWidth() + Layout.CONTENT_BUFFER_PX;
    }

    public static int dropdownMenuHeaderWidth(final String text) {
        return bespokeTextMenuElementWidth(text) +
                Layout.DD_MENU_HEADER_RIGHT_BUFFER;
    }

    public static int dropdownMenuLeafWidth(final String code) {
        final int FAIL = 1, buttonWidth = ResourceCodes.hasIcon(code)
                ? Layout.BUTTON_INC : 0;
        final String[] lines = ParserUtils.getToolTip(code);

        if (lines.length != 1)
            return FAIL;

        final String[] segments = ParserUtils.extractHighlight(lines[0]);

        return switch (segments.length) {
            case 1 -> {
                final String action = segments[0].trim();

                yield buttonWidth + bespokeTextMenuElementWidth(action) +
                        Layout.CONTENT_BUFFER_PX;
            }
            case 2 -> {
                final String action = segments[0].replace("|", "").trim(),
                        shortcut = ParserUtils.getShortcut(segments[1].trim());

                yield buttonWidth + bespokeTextMenuElementWidth(action) +
                        Layout.DD_MENU_LEAF_MIDDLE_BUFFER +
                        bespokeTextMenuElementWidth(shortcut);
            }
            default -> FAIL;
        };
    }

    public static GameImage drawSelectedTextbox(final GameImage bounds) {
        final GameImage selected = new GameImage(bounds);
        final int w = selected.getWidth();
        selected.draw(loadIcon(ResourceCodes.BULLET_POINT),
                w - Layout.BUTTON_INC, Layout.BUTTON_BORDER_PX);

        return selected.submit();
    }

    public static GameImage drawSelectionOverlay(
            final int w, final int h,
            final double z, final boolean[][] mask
    ) {
        return drawOverlay(w, h, z, mask,
                Settings.getTheme().buttonOutline,
                Settings.getTheme().highlightOutline);
    }

    public static GameImage drawOverlay(
            final int w, final int h, final double z,
            final boolean[][] mask,
            final Color inside, final Color outside
    ) {
        final int scaleUpW = Math.max(1, (int)(w * z)),
                scaleUpH = Math.max(1, (int)(h * z));

        final GameImage overlay = new GameImage(
                scaleUpW + (2 * Constants.OVERLAY_BORDER_PX),
                scaleUpH + (2 * Constants.OVERLAY_BORDER_PX));

        populateOverlay(mask, z, overlay, inside, outside);

        return overlay.submit();
    }

    private static void populateOverlay(
            final boolean[][] mask, final double z,
            final GameImage frontier,
            final Color inside, final Color outside
    ) {
        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++) {
                if (!mask[x][y])
                    continue;

                final boolean left = x == 0 || !mask[x - 1][y],
                        right = x == mask.length - 1 || !mask[x + 1][y],
                        top = y == 0 || !mask[x][y - 1],
                        bottom = y == mask[x].length - 1 || !mask[x][y + 1];

                final Coord2D o = new Coord2D(
                        Constants.OVERLAY_BORDER_PX + (int)(z * x),
                        Constants.OVERLAY_BORDER_PX + (int)(z * y));

                final int zoomInc = (int)Math.ceil(Math.max(
                        Constants.ZOOM_FOR_OVERLAY, z));

                if (left) {
                    frontier.fillRectangle(inside, o.x, o.y, 1, zoomInc);
                    frontier.fillRectangle(outside, o.x - 1, o.y, 1, zoomInc);
                }
                if (right) {
                    frontier.fillRectangle(inside, (o.x + zoomInc) - 1, o.y, 1, zoomInc);
                    frontier.fillRectangle(outside, o.x + zoomInc, o.y, 1, zoomInc);
                }
                if (top) {
                    frontier.fillRectangle(inside, o.x, o.y, zoomInc, 1);
                    frontier.fillRectangle(outside, o.x, o.y - 1, zoomInc, 1);
                }
                if (bottom) {
                    frontier.fillRectangle(inside, o.x, (o.y + zoomInc) - 1, zoomInc, 1);
                    frontier.fillRectangle(outside, o.x, o.y + zoomInc, zoomInc, 1);
                }
            }
    }

    public static GameImage loadNumkeyIcon(final int num) {
        if (num < 1 || num > 9)
            return GameImage.dummy();

        return loadIcon(ResourceCodes.NUMKEY_PREFIX + num);
    }

    public static GameImage loadIcon(final String code) {
        if (iconMap.containsKey(code))
            return new GameImage(iconMap.get(code));

        final GameImage asset = readIconAsset(code),
                themed = Settings.getTheme().logic.transformIcon(asset);

        iconMap.put(code, themed);

        return themed;
    }

    public static GameImage readIconAsset(final String code) {
        final Path iconFile = Constants.ICON_FOLDER.resolve(
                code.toLowerCase() + ".png");
        return ResourceLoader.loadImageResource(iconFile);
    }

    public static GameImage highlightIconButton(final GameImage icon) {
        final GameImage highlighted = new GameImage(HIGHLIGHT_OVERLAY);
        highlighted.draw(icon);

        return highlighted.submit();
    }

    public static MenuElement generateIconButton(
            final String code, final Coord2D position,
            final Supplier<Boolean> precondition, final Runnable behaviour
    ) {
        final IconButton icon = IconButton.make(code, position, behaviour);
        final StaticMenuElement stub = new StaticMenuElement(position,
                Layout.ICON_DIMS, MenuElement.Anchor.LEFT_TOP,
                Settings.getTheme().logic.unclickableIcon(loadIcon(code)));

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
                Layout.ICON_DIMS, MenuElement.Anchor.LEFT_TOP,
                Settings.getTheme().logic.unclickableIcon(loadIcon(stubIconCode)));

        return new ThinkingMenuElement(() -> precondition.get() ? icon : stub);
    }

    public static Color greyscale(final Color orig) {
        if (orig.getAlpha() == 0)
            return orig;

        final int avg = (orig.getRed() + orig.getGreen() + orig.getBlue()) / 3;
        return new Color(avg, avg, avg, orig.getAlpha());
    }
}
