package com.jordanbunke.stipple_effect.visual.menu_elements.navigation.logic;

import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.LogicItem;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.ThinkingMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.util.function.Supplier;

public final class GatewayActionItem extends LogicItem implements SEDropdownItem {
    public final boolean close;

    public GatewayActionItem(
            final String label, final Supplier<Boolean> precondition,
            final Runnable behaviour, final boolean close
    ) {
        super(label, precondition, behaviour);

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

        return new ThinkingMenuElement(() -> get()
                ? makeButton(position, width, label, behaviour)
                : makeStub(position, width, label));
    }
}
