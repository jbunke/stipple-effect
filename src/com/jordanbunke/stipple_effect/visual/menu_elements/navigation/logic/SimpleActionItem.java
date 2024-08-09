package com.jordanbunke.stipple_effect.visual.menu_elements.navigation.logic;

import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.SimpleItem;
import com.jordanbunke.delta_time.utility.math.Coord2D;

public final class SimpleActionItem extends SimpleItem implements SEDropdownItem {
    public final boolean close;

    public SimpleActionItem(
            final String label, final Runnable behaviour, final boolean close
    ) {
        super(label, behaviour);

        this.close = close;
    }

    @Override
    public MenuElement make(
            final Runnable closeDropdown,
            final Coord2D position, final int width
    ) {
        final Runnable behaviour = close ? () -> {
            run();
            closeDropdown.run();
        } : this;

        return makeButton(position, width, label, behaviour);
    }
}
