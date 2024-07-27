package com.jordanbunke.stipple_effect.visual.menu_elements.navigation;

import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.AbstractDropdownMenu;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.AbstractNestedDropdownMenu;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.DropdownItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.AbstractVerticalScrollBox;
import com.jordanbunke.delta_time.utility.DeltaTimeGlobal;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;

import java.util.Optional;

public abstract class SEDropdownMenu extends AbstractNestedDropdownMenu {
    public SEDropdownMenu(
            final Coord2D position, final int width, final int renderOrder,
            final AbstractDropdownMenu parent, final DropdownItem[] items
    ) {
        super(position, new Bounds2D(width, Layout.STD_TEXT_BUTTON_H),
                Anchor.LEFT_TOP, renderOrder, parent, items);
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
}
