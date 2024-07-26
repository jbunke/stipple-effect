package com.jordanbunke.stipple_effect.visual.menu_elements.navigation;

import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.DropdownItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.LogicItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.NestedItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.SimpleItem;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.ThinkingMenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollBox;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.*;

import java.util.ArrayList;
import java.util.List;

public final class NestedDropdown extends SEDropdownMenu {
    private final String title;

    public NestedDropdown(
            final Coord2D position, final int width,
            final SEDropdownMenu parent, final NestedItem contents
    ) {
        super(position, width, parent.getRenderOrder() + 1,
                parent, contents.getItems());

        this.title = contents.label;

        make();
    }

    @Override
    protected VerticalScrollBox makeDDContainer(final Coord2D position) {
        final int size = getSize();
        final MenuElement[] contents = new MenuElement[size];

        final List<Integer> widths = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            final DropdownItem item = getItem(i);

            final int width = item instanceof NestedItem
                    ? GraphicsUtils.dropdownMenuHeaderWidth(item.label)
                    : GraphicsUtils.dropdownMenuLeafWidth(item.label);
            widths.add(width);
        }

        final int width = widths.stream()
                .reduce(Math::max).orElse(Layout.STD_TEXT_BUTTON_W);

        for (int i = 0; i < size; i++) {
            final DropdownItem item = getItem(i);

            final Coord2D contentPos = position
                    .displace(0, Layout.STD_TEXT_BUTTON_H * i);

            if (item instanceof NestedItem ni)
                contents[i] = new NestedDropdown(contentPos, width, this, ni);
            else if (item instanceof LogicItem li) {
                final Runnable doAndClose = () -> {
                    li.run();
                    transitiveClose();
                };

                contents[i] = new ThinkingMenuElement(() -> li.get()
                        ? new StaticTextButton(contentPos, li.label,
                        width, doAndClose, Alignment.LEFT, ButtonType.DD_MENU_LEAF)
                        : new StaticMenuElement(contentPos, Anchor.LEFT_TOP,
                        Settings.getTheme().logic.drawDropdownMenuLeafStub(
                                TextButton.of(li.label, width, Alignment.LEFT,
                                        ButtonType.DD_MENU_LEAF))));
            }
            else if (item instanceof SimpleItem si) {
                final Runnable doAndClose = () -> {
                    si.run();
                    transitiveClose();
                };

                contents[i] = new StaticTextButton(contentPos, si.label,
                        width, doAndClose, Alignment.LEFT, ButtonType.DD_MENU_LEAF);
            }
        }

        return VerticalScrollBox.forFixedDropdown(
                position, width, size, false, contents);
    }

    @Override
    protected DropdownHeader makeDDButton() {
        return new DropdownHeader(getPosition(), title, getWidth(),
                this::toggleDropDown, this, DropdownExpansion.RIGHT);
    }
}
