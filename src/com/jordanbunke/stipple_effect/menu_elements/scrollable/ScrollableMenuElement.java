package com.jordanbunke.stipple_effect.menu_elements.scrollable;

import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementContainer;
import com.jordanbunke.delta_time.utility.Coord2D;

import java.util.function.BiConsumer;

public abstract class ScrollableMenuElement extends MenuElementContainer {
    final BiConsumer<MenuElement, Integer> incrementFunction;
    final MenuElement[] menuElements;

    public ScrollableMenuElement(
            final Coord2D position, final Coord2D dimensions,
            final MenuElement[] menuElements,
            final BiConsumer<MenuElement, Integer> incrementFunction
    ) {
        super(position, dimensions, Anchor.LEFT_TOP, true);

        this.menuElements = menuElements;
        this.incrementFunction = incrementFunction;
    }

    public void scroll(final int delta) {
        for (MenuElement me : menuElements)
            incrementFunction.accept(me, delta);
    }

    @Override
    public MenuElement[] getMenuElements() {
        return menuElements;
    }


}
