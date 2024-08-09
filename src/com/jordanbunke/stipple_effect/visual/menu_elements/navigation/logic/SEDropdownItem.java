package com.jordanbunke.stipple_effect.visual.menu_elements.navigation.logic;

import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.Alignment;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.StaticTextButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.TextButton;

public interface SEDropdownItem {
    MenuElement make(final Runnable closeDropdown,
                     final Coord2D position, final int width);

    default StaticTextButton makeButton(
            final Coord2D position, final int width,
            final String label, final Runnable behaviour
    ) {
        return new StaticTextButton(position, label, width,
                behaviour, Alignment.LEFT, ButtonType.DD_MENU_LEAF);
    }

    default StaticMenuElement makeStub(
            final Coord2D position, final int width,
            final String label
    ) {
        return new StaticMenuElement(position, MenuElement.Anchor.LEFT_TOP,
                Settings.getTheme().logic.drawDropdownMenuLeafStub(
                        TextButton.of(label, width, Alignment.LEFT,
                                ButtonType.DD_MENU_LEAF)));
    }
}
