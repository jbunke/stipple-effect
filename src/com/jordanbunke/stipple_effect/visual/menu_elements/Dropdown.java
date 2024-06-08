package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.AbstractDropdownList;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.SimpleItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.Scrollable;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollBox;

import java.util.Arrays;
import java.util.function.Supplier;

public class Dropdown extends AbstractDropdownList {
    public static final int DEFAULT_RENDER_ORDER = 1;

    private final int dropdownAllowanceY;

    public Dropdown(
            final Coord2D position, final int width, final Anchor anchor,
            final int dropdownAllowanceY, final int renderOrder,
            final String[] labels, final Runnable[] behaviours,
            final Supplier<Integer> initialIndexFunction
    ) {
        super(position, new Bounds2D(width, Layout.STD_TEXT_BUTTON_H),
                anchor, renderOrder, composeItems(labels, behaviours),
                initialIndexFunction);

        this.dropdownAllowanceY = dropdownAllowanceY;

        make();
    }

    private static SimpleItem[] composeItems(
            final String[] labels, final Runnable[] behaviours
    ) {
        assert labels.length == behaviours.length;

        final SimpleItem[] items = new SimpleItem[labels.length];

        for (int i = 0; i < items.length; i++)
            items[i] = new SimpleItem(labels[i], behaviours[i]);

        return items;
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
        return new Dropdown(pos, width, MenuElement.Anchor.LEFT_TOP,
                Layout.height() / 5, DEFAULT_RENDER_ORDER,
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
    protected VerticalScrollBox makeDDContainer(final Coord2D position) {
        final int size = getSize();
        final MenuElement[] scrollables = new MenuElement[size];

        final int buttonWidth = getWidth() - Layout.SLIDER_OFF_DIM;

        for (int i = 0; i < size; i++) {
            final int index = i;

            final GameImage nhi = GraphicsUtils.drawTextButton(
                    buttonWidth, getLabelTextFor(i), false,
                    GraphicsUtils.ButtonType.DD_OPTION);

            scrollables[i] = new SimpleMenuButton(
                    position.displace(0, i * Layout.STD_TEXT_BUTTON_H),
                    new Bounds2D(buttonWidth, Layout.STD_TEXT_BUTTON_H),
                    Anchor.LEFT_TOP, true, () -> select(index),
                    nhi, GraphicsUtils.highlightButton(nhi));
        }

        final Bounds2D dimensions = new Bounds2D(getWidth(),
                Math.min(dropdownAllowanceY, Layout.STD_TEXT_BUTTON_H * size));

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
                GraphicsUtils.drawDropdownButton(getWidth(), text, false),
                GraphicsUtils.drawDropdownButton(getWidth(), text, true)
        };

        final GameImage[] highlighted = Arrays.stream(bases)
                .map(GraphicsUtils::highlightButton)
                .toArray(GameImage[]::new);

        return new SimpleToggleMenuButton(new Coord2D(getX(), getY()),
                new Bounds2D(getWidth(), Layout.STD_TEXT_BUTTON_H),
                getAnchor(), true, bases, highlighted,
                new Runnable[] { () -> {}, () -> {} },
                () -> isDroppedDown() ? 1 : 0, this::toggleDropDown);
    }

    @Override
    protected Coord2D contentsDisplacement() {
        return new Coord2D(0, getHeight());
    }
}
