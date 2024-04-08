package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

public class Indicator extends StaticMenuElement {
    private final String iconCode;
    private boolean highlighted;

    public Indicator(final Coord2D position, final String iconCode) {
        super(position, Layout.ICON_DIMS, Anchor.LEFT_TOP,
                GraphicsUtils.loadIcon(iconCode));

        this.iconCode = iconCode;
        highlighted = false;
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        highlighted = mouseIsWithinBounds(eventLogger.getAdjustedMousePosition());

        super.process(eventLogger);
    }

    @Override
    public void update(final double deltaTime) {
        if (!iconCode.equals(IconCodes.NO_TOOLTIP) && highlighted)
            StippleEffect.get().sendToolTipUpdate(iconCode);
    }
}
