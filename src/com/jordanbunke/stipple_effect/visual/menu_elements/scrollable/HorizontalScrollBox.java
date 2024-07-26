package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.AbstractHorizontalScrollBox;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.Scrollable;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

public class HorizontalScrollBox extends AbstractHorizontalScrollBox {
    public HorizontalScrollBox(
            final Coord2D position, final Bounds2D dimensions,
            final Scrollable[] menuElements,
            final int realRightX, final int initialOffsetX
    ) {
        super(position, dimensions, menuElements,
                Settings.getTheme().logic::drawScrollBoxBackground,
                Layout.PX_PER_SCROLL, realRightX, initialOffsetX);
    }

    protected HorizontalSlider makeSlider(final int maxOffsetX) {
        final Coord2D position = new Coord2D(getX(), getY())
                .displace(0, getHeight() - Layout.SLIDER_OFF_DIM);

        final HorizontalSlider slider = new HorizontalSlider(
                position, getWidth(), Anchor.LEFT_TOP,
                0, maxOffsetX, () -> -getOffset().x, o -> setOffsetX(-o));
        slider.updateAssets();
        return slider;
    }

    public boolean renderingChild(final Scrollable child) {
        return renderAndProcessChild(child);
    }
}
