package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.AbstractVerticalScrollBox;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.Scrollable;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

public class VerticalScrollBox extends AbstractVerticalScrollBox {
    public VerticalScrollBox(
            final Coord2D position, final Bounds2D dimensions,
            final Scrollable[] menuElements,
            final int realBottomY, final int initialOffsetY
    ) {
        super(position, dimensions, menuElements,
                Settings.getTheme().logic::drawScrollBoxBackground,
                Layout.PX_PER_SCROLL, realBottomY, initialOffsetY);
    }

    protected VerticalSlider makeSlider(final int maxOffsetY) {
        final Coord2D position = new Coord2D(getX(), getY())
                .displace(getWidth() - Layout.SLIDER_OFF_DIM, 0);

        final VerticalSlider slider = new VerticalSlider(
                position, getHeight(), Anchor.LEFT_TOP,
                0, maxOffsetY, () -> -getOffset().y, o -> setOffsetY(-o));
        slider.updateAssets();
        return slider;
    }
}
