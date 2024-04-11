package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.ext.AbstractDropdown;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.Scrollable;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollBox;

import java.util.Arrays;
import java.util.function.Supplier;

public class Dropdown extends AbstractDropdown {
    public static final int DEFAULT_RENDER_ORDER = 1;

    public Dropdown(
            final Coord2D position, final int width, final Anchor anchor,
            final int dropDownHAllowance, final int renderOrder,
            final String[] labels, final Runnable[] behaviours,
            final Supplier<Integer> initialIndexFunction
    ) {
        super(position, new Coord2D(width, Layout.STD_TEXT_BUTTON_H), anchor,
                dropDownHAllowance, Layout.STD_TEXT_BUTTON_H,
                renderOrder, labels, behaviours, initialIndexFunction);
    }

    public static Dropdown forDialog(
            final Coord2D pos, final String[] labels, final Runnable[] behaviours,
            final Supplier<Integer> initialIndexFunction
    ) {
        return forDialog(pos, Layout.optionsBarSliderWidth(),
                labels, behaviours, initialIndexFunction);
    }

    public static Dropdown forDialog(
            final Coord2D pos, final int width,
            final String[] labels, final Runnable[] behaviours,
            final Supplier<Integer> initialIndexFunction
    ) {
        return new Dropdown(pos, width,
                MenuElement.Anchor.LEFT_TOP, Layout.height() / 5,
                DEFAULT_RENDER_ORDER,
                labels, behaviours, initialIndexFunction);
    }

    public static Dropdown forToolOptionsBar(
            final int x, final String[] labels, final Runnable[] behaviours,
            final Supplier<Integer> initialIndexFunction
    ) {
        return new Dropdown(new Coord2D(x,
                Layout.getToolOptionsBarPosition().y +
                        ((Layout.TOOL_OPTIONS_BAR_H -
                                Layout.STD_TEXT_BUTTON_H) / 2)),
                Layout.optionsBarSliderWidth(), MenuElement.Anchor.LEFT_TOP,
                (int) (Layout.TOOL_OPTIONS_BAR_H * 5.5), DEFAULT_RENDER_ORDER,
                labels, behaviours, initialIndexFunction);
    }

    @Override
    protected VerticalScrollBox makeDDContainer(
            final Coord2D position, final int dropdownAllowanceY,
            final int heightPerOption, final Runnable[] behaviours
    ) {
        final int size = getSize();
        final MenuElement[] scrollables = new MenuElement[size];

        final int buttonWidth = getWidth() - Layout.SLIDER_OFF_DIM;

        for (int i = 0; i < size; i++) {
            final int index = i;

            final GameImage nhi = GraphicsUtils.drawTextButton(
                    buttonWidth, getLabelTextFor(i), false,
                    Settings.getTheme().getDropdownOptionBody(), true, false);

            scrollables[i] = new SimpleMenuButton(
                    position.displace(0, i * Layout.STD_TEXT_BUTTON_H),
                    new Coord2D(buttonWidth, Layout.STD_TEXT_BUTTON_H),
                    Anchor.LEFT_TOP, true,
                    () -> onSelection(index, behaviours[index]),
                    nhi, GraphicsUtils.drawHighlightedButton(nhi));
        }

        final Coord2D dimensions = new Coord2D(getWidth(),
                Math.min(dropdownAllowanceY, heightPerOption * size));

        return new VerticalScrollBox(position, dimensions,
                Arrays.stream(scrollables)
                        .map(Scrollable::new)
                        .toArray(Scrollable[]::new),
                position.y + (size * Layout.STD_TEXT_BUTTON_H), 0);
    }

    @Override
    protected SimpleToggleMenuButton makeDDButton() {
        final String text = getCurrentLabelText();

        final GameImage[] bases = new GameImage[] {
                GraphicsUtils.drawDropDownButton(getWidth(), text, false),
                GraphicsUtils.drawDropDownButton(getWidth(), text, true)
        };

        final GameImage[] highlighted = Arrays.stream(bases)
                .map(GraphicsUtils::drawHighlightedButton)
                .toArray(GameImage[]::new);

        return new SimpleToggleMenuButton(new Coord2D(getX(), getY()),
                new Coord2D(getWidth(), Layout.STD_TEXT_BUTTON_H),
                getAnchor(), true, bases, highlighted,
                new Runnable[] { () -> {}, () -> {} },
                () -> isDroppedDown() ? 1 : 0, this::toggleDropDown);
    }
}
