package com.jordanbunke.stipple_effect.visual.menu_elements.navigation;

import com.jordanbunke.delta_time.menu.MenuBuilder;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.AbstractNavbar;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.DropdownItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.NestedItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.AbstractVerticalScrollBox;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollBox;

import java.util.Arrays;

public final class Navbar extends AbstractNavbar {
    public Navbar(
            final Coord2D position, final NestedItem... submenus
    ) {
        super(position, new Bounds2D(Arrays.stream(submenus)
                .map(d -> GraphicsUtils.bespokeTextMenuElementWidth(d.label))
                .reduce(0, Integer::sum),
                Layout.STD_TEXT_BUTTON_H), Layout.NAV_RENDER_ORDER, submenus);

        make();
    }

    public Navbar(final NestedItem... submenus) {
        this(new Coord2D(), submenus);
    }

    @Override
    protected AbstractVerticalScrollBox makeDDContainer(final Coord2D position) {
        int deltaX = 0;

        final int size = getSize();
        final MenuBuilder mb = new MenuBuilder();

        for (int i = 0; i < size; i++) {
            final DropdownItem item = getItem(i);

            if (!(item instanceof NestedItem subItem))
                continue;

            final Submenu sub = new Submenu(
                    position.displace(deltaX, 0), subItem, this);
            mb.add(sub);

            deltaX += sub.getWidth();
        }

        return VerticalScrollBox.forFixedDropdown(position,
                deltaX, size, true, mb.build().getMenuElements());
    }
}
