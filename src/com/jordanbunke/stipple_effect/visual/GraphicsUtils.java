package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.ThinkingMenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.tools.Tool;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;
import com.jordanbunke.stipple_effect.visual.theme.Theme;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconToggleButton;

import java.awt.*;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GraphicsUtils {
    public static GameImage HIGHLIGHT_OVERLAY,
            SELECT_OVERLAY, TRANSFORM_NODE, CHECKMARK;
    private static final Map<String, GameImage> iconMap;

    static {
        TRANSFORM_NODE = loadUtil("transform_node");
        CHECKMARK = loadUtil("checkmark");

        iconMap = new HashMap<>();

        final Theme theme = Settings.getTheme();

        HIGHLIGHT_OVERLAY = theme.logic.highlightedIconOverlay();
        SELECT_OVERLAY = theme.logic.selectedIconOverlay();
    }

    private static GameImage loadUtil(final String code) {
        final GameImage asset = ResourceLoader.loadImageResource(
                Constants.MISC_FOLDER.resolve(code + ".png"));

        return Settings.getTheme().logic.transformIcon(asset);
    }

    public static void refreshAssets() {
        final Theme theme = Settings.getTheme();

        TRANSFORM_NODE = loadUtil("transform_node");
        CHECKMARK = loadUtil("checkmark");

        HIGHLIGHT_OVERLAY = theme.logic.highlightedIconOverlay();
        SELECT_OVERLAY = theme.logic.selectedIconOverlay();

        iconMap.clear();

        SECursor.refresh();
        Arrays.stream(Tool.getAll()).forEach(Tool::refreshIcons);
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

    public static GameImage drawDropdownButton(
            final int width, final String text, final boolean selected
    ) {
        final GameImage base = drawTextButton(
                width, text, selected, ButtonType.DD_HEAD);

        final GameImage icon = loadIcon(
                selected ? IconCodes.COLLAPSE : IconCodes.EXPAND);

        base.draw(icon, base.getWidth() - (Layout.BUTTON_INC),
                Layout.BUTTON_BORDER_PX);

        return base.submit();
    }

    public static GameImage drawTextButton(
            final int width, final String text,
            final boolean selected, final ButtonType type
    ) {
        return Settings.getTheme().logic
                .drawTextButton(width, text, selected, type);
    }

    public static GameImage drawTextButton(
            final int width, final String text, final boolean selected
    ) {
        return drawTextButton(width, text, selected, ButtonType.STANDARD);
    }

    public static SimpleMenuButton makeStandardTextButton(
            final String text, final Coord2D pos, final Runnable onClick
    ) {
        final GameImage base =
                drawTextButton(Layout.STD_TEXT_BUTTON_W, text, false);
        return new SimpleMenuButton(pos, new Bounds2D(Layout.STD_TEXT_BUTTON_W,
                Layout.STD_TEXT_BUTTON_H), MenuElement.Anchor.LEFT_TOP,
                true, onClick, base, highlightButton(base));
    }

    public static SimpleMenuButton makeBespokeTextButton(
            final String text, final Coord2D pos, final Runnable onClick
    ) {
        final int w = uiText(SEColors.def())
                .addText(text).build().draw()
                .getWidth() + Layout.CONTENT_BUFFER_PX;

        final GameImage base = drawTextButton(w, text, false);
        return new SimpleMenuButton(pos, new Bounds2D(w,
                Layout.STD_TEXT_BUTTON_H), MenuElement.Anchor.LEFT_TOP,
                true, onClick, base, highlightButton(base));
    }

    public static GameImage drawSelectedTextbox(final GameImage bounds) {
        final GameImage selected = new GameImage(bounds);
        final int w = selected.getWidth();
        selected.draw(loadIcon(IconCodes.BULLET_POINT),
                w - Layout.BUTTON_INC, Layout.BUTTON_BORDER_PX);

        return selected.submit();
    }

    public static GameImage highlightButton(final GameImage nhi) {
        return Settings.getTheme().logic.highlightButton(nhi);
    }

    public static GameImage drawSelectionOverlay(
            final double z, final Set<Coord2D> selection
    ) {
        final Coord2D tl = SelectionUtils.topLeft(selection),
                br = SelectionUtils.bottomRight(selection);

        final Set<Coord2D> adjusted = selection.stream()
                .map(p -> p.displace(-tl.x, -tl.y))
                .collect(Collectors.toSet());
        final int w = br.x - tl.x, h = br.y - tl.y;

        return drawOverlay(w, h, z, adjusted,
                Settings.getTheme().buttonOutline,
                Settings.getTheme().highlightOutline,
                false, false);
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

        populateOverlay(selection, zoomInc, overlay, overlay, inside, outside,
                filled ? Settings.getTheme().selectionFill : null);

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

    public static void populateOverlay(
            final Set<Coord2D> selection, final int zoomInc,
            final GameImage frontier, final GameImage filled,
            final Color inside, final Color outside, final Color fill
    ) {
        selection.forEach(pixel -> {
            final boolean
                    left = !selection.contains(pixel.displace(-1, 0)),
                    right = !selection.contains(pixel.displace(1, 0)),
                    top = !selection.contains(pixel.displace(0, -1)),
                    bottom = !selection.contains(pixel.displace(0, 1));

            final Coord2D o = new Coord2D(
                    Constants.OVERLAY_BORDER_PX + (zoomInc * pixel.x),
                    Constants.OVERLAY_BORDER_PX + (zoomInc * pixel.y));

            if (fill != null)
                filled.fillRectangle(fill, o.x, o.y, zoomInc, zoomInc);

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
        });
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
            final String iconID, final Coord2D position,
            final Supplier<Boolean> precondition, final Runnable behaviour
    ) {
        final IconButton icon = IconButton.make(iconID, position, behaviour);
        final StaticMenuElement stub = new StaticMenuElement(position,
                Layout.ICON_DIMS, MenuElement.Anchor.LEFT_TOP,
                Settings.getTheme().logic.unclickableIcon(loadIcon(iconID)));

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

    public enum ButtonType {
        STANDARD, STUB, DD_HEAD, DD_OPTION;

        public boolean isDropdown() {
            return switch (this) {
                case DD_HEAD, DD_OPTION -> true;
                default -> false;
            };
        }
    }
}
