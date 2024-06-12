package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.ext.AbstractDynamicLabel;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;
import com.jordanbunke.stipple_effect.visual.theme.logic.ThemeLogic;

import java.awt.*;
import java.util.function.Supplier;

public class DynamicLabel extends AbstractDynamicLabel {
    private DynamicLabel(
            final Coord2D position, final Anchor anchor,
            final Color textColor, final Supplier<String> getter,
            final int widthAllowance
    ) {
        super(position, new Bounds2D(widthAllowance, Layout.DYNAMIC_LABEL_H),
                anchor, textColor, getter, DynamicLabel::draw);
    }

    public static DynamicLabel make(
            final Coord2D position, final Supplier<String> getter,
            final int widthAllowance
    ) {
        return make(position, Anchor.LEFT_TOP, getter, widthAllowance);
    }

    public static DynamicLabel make(
            final Coord2D position, final Anchor anchor,
            final Supplier<String> getter, final int widthAllowance
    ) {
        return new DynamicLabel(position, anchor,
                ThemeLogic.intuitTextColor(
                        Settings.getTheme().panelBackground, true),
                getter, widthAllowance);
    }

    public static int getWidth(final String maxWidthCase) {
        return draw(maxWidthCase, SEColors.def()).getWidth();
    }

    private static GameImage draw(final String text, final Color textColor) {
        return GraphicsUtils.uiText(textColor).addText(text).build().draw();
    }
}
