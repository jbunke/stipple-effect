package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.AbstractBidirectionalScrollBox;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.AbstractSlider;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.Scrollable;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.preview.PreviewImage;
import com.jordanbunke.stipple_effect.preview.PreviewWindow;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;

@Deprecated
public class PreviewHousingBox extends AbstractBidirectionalScrollBox {
    private PreviewHousingBox(
            final Coord2D position, final Bounds2D dimensions,
            final Scrollable[] menuElements,
            final int realRightX, final int realBottomY
    ) {
        super(position, dimensions, menuElements, Layout.PX_PER_SCROLL,
                GameImage::new, realRightX, realBottomY, 0, 0);
    }

    public static PreviewHousingBox make(
            final PreviewImage previewImage,
            final int zoomWidth, final int zoomHeight
    ) {
        final int BUFFER = (2 * PreviewWindow.BORDER);

        final Coord2D position = new Coord2D(
                Layout.PREV_TL_BUFFER,
                Layout.PREV_TL_BUFFER +
                        PreviewWindow.MENU_Y_ALLOTMENT_PX);
        final Bounds2D dimensions = new Bounds2D(
                Math.min(Constants.MAX_CANVAS_W, zoomWidth) + BUFFER,
                Math.min(Constants.MAX_CANVAS_H, zoomHeight) + BUFFER);
        final Scrollable[] menuElements = new Scrollable[] {
                new Scrollable(previewImage)
        };

        return new PreviewHousingBox(position, dimensions, menuElements,
                position.x + zoomWidth + BUFFER,
                position.y + zoomHeight + BUFFER);
    }

    @Override
    protected AbstractSlider makeHorizontalSlider(final int maxOffsetX) {
        final Coord2D position = new Coord2D(getX(), getY())
                .displace(0, getHeight() + (Layout.SLIDER_OFF_DIM / 2));

        final HorizontalSlider slider = new HorizontalSlider(position,
                getWidth(), Anchor.LEFT_TOP,
                0, maxOffsetX, () -> -getOffset().x, o -> setOffsetX(-o));
        slider.updateAssets();
        return slider;
    }

    @Override
    protected AbstractSlider makeVerticalSlider(final int maxOffsetY) {
        final Coord2D position = new Coord2D(getX(), getY())
                .displace(getWidth() + (Layout.SLIDER_OFF_DIM / 2), 0);

        final VerticalSlider slider = new VerticalSlider(position,
                getHeight(), Anchor.LEFT_TOP,
                0, maxOffsetY, () -> -getOffset().y, o -> setOffsetY(-o));
        slider.updateAssets();
        return slider;
    }

    @Override
    protected boolean renderAndProcessChild(final Scrollable child) {
        return true;
    }
}
