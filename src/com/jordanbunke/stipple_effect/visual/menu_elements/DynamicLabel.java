package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.ext.AbstractDynamicLabel;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.awt.*;
import java.util.function.Supplier;

public class DynamicLabel extends AbstractDynamicLabel {
    public DynamicLabel(
            final Coord2D position, final Anchor anchor,
            final Color textColor, final Supplier<String> getter,
            final String widestCase
    ) {
        super(position, widestCase, Layout.DYNAMIC_LABEL_H,
                anchor, textColor, getter, DynamicLabel::draw);
    }

    public DynamicLabel(
            final Coord2D position, final Anchor anchor,
            final Color textColor, final Supplier<String> getter,
            final int widthAllowance
    ) {
        super(position, new Coord2D(widthAllowance, Layout.DYNAMIC_LABEL_H),
                anchor, textColor, getter, DynamicLabel::draw);
    }

    private static GameImage draw(final String text, final Color textColor) {
        return GraphicsUtils.uiText(textColor).addText(text).build().draw();
    }
}
