package com.jordanbunke.stipple_effect.menu_elements;

import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColorSelector extends MenuElementGrouping {
    private ColorSelector(final MenuElement... menuElements) {
        super(menuElements);
    }

    public static ColorSelector make() {
        final List<MenuElement> menuElements = new ArrayList<>();

        // red
        menuElements.add(new ColorComponent(0, 255, "Red",
                r -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(r, c.getGreen(), c.getBlue(), c.getAlpha());
                },
                c -> StippleEffect.get().setSelectedColor(c),
                () -> StippleEffect.get().getSelectedColor().getRed(),
                Constants.getColorsPosition().displace(0,
                        Constants.COLOR_SELECTOR_OFFSET_Y)
        ));
        // green
        menuElements.add(new ColorComponent(0, 255, "Green",
                g -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(c.getRed(), g, c.getBlue(), c.getAlpha());
                },
                c -> StippleEffect.get().setSelectedColor(c),
                () -> StippleEffect.get().getSelectedColor().getGreen(),
                Constants.getColorsPosition().displace(0,
                        Constants.COLOR_SELECTOR_OFFSET_Y +
                                Constants.COLOR_SELECTOR_INC_Y)
        ));
        // blue
        menuElements.add(new ColorComponent(0, 255, "Blue",
                b -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(c.getRed(), c.getGreen(), b, c.getAlpha());
                },
                c -> StippleEffect.get().setSelectedColor(c),
                () -> StippleEffect.get().getSelectedColor().getBlue(),
                Constants.getColorsPosition().displace(0,
                        Constants.COLOR_SELECTOR_OFFSET_Y +
                                (2 * Constants.COLOR_SELECTOR_INC_Y))
        ));
        // alpha
        menuElements.add(new ColorComponent(0, 255, "Opacity",
                a -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
                },
                c -> StippleEffect.get().setSelectedColor(c),
                () -> StippleEffect.get().getSelectedColor().getAlpha(),
                Constants.getColorsPosition().displace(0,
                        Constants.COLOR_SELECTOR_OFFSET_Y +
                                (3 * Constants.COLOR_SELECTOR_INC_Y))
        ));

        return new ColorSelector(menuElements.toArray(new MenuElement[0]));
    }
}
