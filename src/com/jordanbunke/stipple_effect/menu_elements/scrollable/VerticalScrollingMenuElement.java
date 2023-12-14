package com.jordanbunke.stipple_effect.menu_elements.scrollable;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;

public class VerticalScrollingMenuElement extends ScrollingMenuElement {

    private final int realBottomY;
    private int offsetY;

    private final VerticalSlider slider;

    public VerticalScrollingMenuElement(
            final Coord2D position, final Coord2D dimensions,
            final ScrollableMenuElement[] menuElements,
            final int realBottomY, final int initialOffsetY
    ) {
        super(position, dimensions, menuElements);

        this.realBottomY = realBottomY;

        final int minOffsetY = 0, maxOffsetY = (realBottomY - getY()) - getHeight();

        offsetY = -Math.max(minOffsetY, Math.min(initialOffsetY, maxOffsetY));

        final boolean canScroll = realBottomY > position.y + dimensions.y;
        slider = canScroll ? makeSlider(maxOffsetY) : null;
    }

    private VerticalSlider makeSlider(final int maxOffsetY) {
        final Coord2D position = new Coord2D(getX(), getY())
                .displace(getWidth(), 0)
                .displace(-Constants.SLIDER_OFF_DIM, 0);

        final VerticalSlider slider = new VerticalSlider(position, getHeight(), Anchor.LEFT_TOP,
                0, maxOffsetY, -offsetY, o -> setOffsetY(-o));
        slider.updateAssets();
        return slider;
    }

    public void setOffsetY(final int offsetY) {
        this.offsetY = offsetY;
    }

    @Override
    public void render(final GameImage canvas) {
        super.render(canvas);

        if (slider != null)
            slider.render(canvas);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        super.process(eventLogger);

        if (slider != null)
            slider.process(eventLogger);
    }

    @Override
    Coord2D calculateOffset() {
        return new Coord2D(0, offsetY);
    }

    @Override
    boolean renderAndProcessChild(final ScrollableMenuElement child) {
        final Coord2D rp = getRenderPosition(), childRP = child.getRenderPosition();
        final int h = getHeight(), childH = child.getHeight();

        return rp.y <= childRP.y && rp.y + h >= childRP.y + childH;
    }
}
