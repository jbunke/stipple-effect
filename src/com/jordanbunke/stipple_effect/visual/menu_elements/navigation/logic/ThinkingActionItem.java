package com.jordanbunke.stipple_effect.visual.menu_elements.navigation.logic;

import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.DropdownItem;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.PlaceholderMenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.ThinkingMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.action.ActionCodes;
import com.jordanbunke.stipple_effect.utility.action.SEAction;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class ThinkingActionItem extends DropdownItem implements SEDropdownItem {
    private final PlaceholderMenuElement failCase = new PlaceholderMenuElement();

    private final SEContext c;
    private final Function<SEContext, SEAction> logic;
    private final SEAction[] possibilities;

    private final Map<SEAction, MenuElement> dropdownOptions;

    public ThinkingActionItem(
            final SEContext c, final Function<SEContext, SEAction> logic,
            final SEAction... possibilities
    ) {
        super(widestLabel(c, possibilities));

        this.c = c;
        this.logic = logic;
        this.possibilities = possibilities;

        this.dropdownOptions = new HashMap<>();
    }

    private static String widestLabel(final SEContext c, final SEAction[] possibilities) {
        return MathPlus.findBest(ActionCodes.NONE, 0,
                GraphicsUtils::dropdownMenuHeaderWidth,
                (cand, pole) -> cand > pole,
                Arrays.stream(possibilities)
                        .map(p -> p.toItem(c).label)
                        .toArray(String[]::new));
    }

    @Override
    public MenuElement make(
            final Runnable closeDropdown, final Coord2D position, final int width
    ) {
        dropdownOptions.clear();

        for (SEAction possibility : possibilities) {
            final DropdownItem di = possibility.toItem(c);

            if (!(di instanceof SEDropdownItem sedi))
                continue;

            dropdownOptions.put(possibility,
                    sedi.make(closeDropdown, position, width));
        }

        return new ThinkingMenuElement(
                () -> dropdownOptions.getOrDefault(logic.apply(c), failCase));
    }
}
