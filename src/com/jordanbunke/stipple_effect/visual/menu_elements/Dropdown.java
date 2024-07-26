package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.AbstractDropdownList;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.SimpleItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.AbstractVerticalScrollBox;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.Scrollable;
import com.jordanbunke.delta_time.utility.DeltaTimeGlobal;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollBox;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.Alignment;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.StaticTextButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.TextButton;
import com.jordanbunke.stipple_effect.visual.theme.logic.ThemeLogic;

import java.util.Arrays;
import java.util.Optional;
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

    @Override
    public void process(final InputEventLogger eventLogger) {
        final Optional<?> scope = DeltaTimeGlobal.getStatusOf(
                DeltaTimeGlobal.SC_CURSOR_CAPTURED);
        final AbstractVerticalScrollBox container = getContainer();

        if (scope.isEmpty() && isDroppedDown() &&
                container.mouseIsWithinBounds(
                        eventLogger.getAdjustedMousePosition()))
            DeltaTimeGlobal.setStatus(DeltaTimeGlobal.SC_CURSOR_CAPTURED,
                    container);

        super.process(eventLogger);
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

            scrollables[i] = new StaticTextButton(
                    position.displace(0, i * Layout.STD_TEXT_BUTTON_H),
                    getLabelTextFor(i), buttonWidth, () -> select(index),
                    Alignment.LEFT, ButtonType.DD_OPTION);
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
        final ThemeLogic tl = Settings.getTheme().logic;

        final int width = getWidth();
        final String text = getCurrentLabelText();
        final TextButton base = TextButton.of(text, width,
                Alignment.LEFT, ButtonType.DD_HEAD);

        final GameImage[] bases = new GameImage[] {
                tl.drawTextButton(base),
                tl.drawTextButton(base.sim(false, true))
        };

        final GameImage[] highlighted = new GameImage[] {
                tl.drawTextButton(base.sim(true, false)),
                tl.drawTextButton(base.sim(true, true))
        };

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
