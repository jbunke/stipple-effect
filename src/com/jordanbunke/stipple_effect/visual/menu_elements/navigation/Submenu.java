package com.jordanbunke.stipple_effect.visual.menu_elements.navigation;

import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.DropdownItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.NestedItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.SimpleItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.AbstractVerticalScrollBox;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollBox;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.*;

import java.util.ArrayList;
import java.util.List;

public final class Submenu extends SEDropdownMenu {
    private final String title;
    private final Navbar navbar;

    Submenu(
            final Coord2D position, final NestedItem contents,
            final Navbar navbar
    ) {
        super(position, GraphicsUtils.bespokeTextMenuElementWidth(contents.label),
                Layout.NAV_RENDER_ORDER, navbar, contents.getItems());

        this.title = contents.label;
        this.navbar = navbar;

        make();
    }

    @Override
    protected Coord2D contentsDisplacement() {
        return new Coord2D(0, getHeight());
    }

    @Override
    protected AbstractVerticalScrollBox makeDDContainer(final Coord2D position) {
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
            else if (item instanceof SimpleItem si) {
                final Runnable doAndClose = () -> {
                    si.run();
                    close();
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
        final Runnable onClick = () -> {
            toggleDropDown();

            if (navbar != null)
                navbar.closeRest(this);
        };

        return new DropdownHeader(getPosition(), title,
                getWidth(), onClick, DropdownExpansion.DOWN);
    }
}
