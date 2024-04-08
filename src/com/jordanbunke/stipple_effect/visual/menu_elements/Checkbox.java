package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.menu.menu_elements.ext.AbstractCheckbox;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.funke.core.Property;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

public class Checkbox extends AbstractCheckbox {
    public Checkbox(
            final Coord2D position, final Property<Boolean> property
    ) {
        super(position, Layout.ICON_DIMS, Anchor.LEFT_TOP, property,
                GraphicsUtils::drawCheckbox);
    }
}
