package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.ext.drawing_functions.ScrollBoxDrawingFunction;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.AbstractVerticalScrollBox;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.Scrollable;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

import java.util.Arrays;

public class VerticalScrollBox extends AbstractVerticalScrollBox {
    public VerticalScrollBox(
            final Coord2D position, final Bounds2D dimensions,
            final Scrollable[] menuElements,
            final int realBottomY, final int initialOffsetY
    ) {
        this(position, dimensions, menuElements, realBottomY, initialOffsetY,
                Settings.getTheme().logic::drawScrollBoxBackground);
    }

    public VerticalScrollBox(
            final Coord2D position, final Bounds2D dimensions,
            final Scrollable[] menuElements,
            final int realBottomY, final int initialOffsetY,
            final ScrollBoxDrawingFunction fDraw
    ) {
        super(position, dimensions, menuElements, fDraw,
                Layout.PX_PER_SCROLL, realBottomY, initialOffsetY);
    }

    public VerticalScrollBox(
            final Coord2D position, final Bounds2D dimensions,
            final Scrollable[] menuElements
    ) {
        super(position, dimensions, menuElements, GameImage::new, 0,
                position.y + dimensions.height(), 0);
    }

    public static VerticalScrollBox forFixedDropdown(
            final Coord2D position, final int width,
            final int size, final boolean navbar,
            final MenuElement[] menuElements
    ) {
        final VerticalScrollBox vsb = new VerticalScrollBox(position,
                new Bounds2D(width, Layout.STD_TEXT_BUTTON_H * (navbar ? 1 : size)),
                Arrays.stream(menuElements)
                        .map(Scrollable::new)
                        .toArray(Scrollable[]::new));
        vsb.setRenderBeyondBounds(true);

        return vsb;
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

    public boolean renderingChild(final Scrollable child) {
        return renderAndProcessChild(child);
    }
}
