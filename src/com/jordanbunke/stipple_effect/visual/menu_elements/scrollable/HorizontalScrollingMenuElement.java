package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseScrollEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;

import java.util.List;

public class HorizontalScrollingMenuElement extends ScrollingMenuElement {

    private int offsetX;

    private final HorizontalSlider slider;

    public HorizontalScrollingMenuElement(
            final Coord2D position, final Coord2D dimensions,
            final ScrollableMenuElement[] menuElements,
            final int realRightX, final int initialOffsetX
    ) {
        super(position, dimensions, menuElements);

        final int minOffsetX = 0, maxOffsetX = (realRightX - getX()) - getWidth();

        offsetX = -Math.max(minOffsetX, Math.min(initialOffsetX, maxOffsetX));

        final boolean canScroll = realRightX > position.x + dimensions.x;
        slider = canScroll ? makeSlider(maxOffsetX) : null;
    }

    private HorizontalSlider makeSlider(final int maxOffsetX) {
        final Coord2D position = new Coord2D(getX(), getY())
                .displace(0, getHeight() - Layout.SLIDER_OFF_DIM);

        final HorizontalSlider slider = new HorizontalSlider(
                position, getWidth(), Anchor.LEFT_TOP,
                0, maxOffsetX, () -> -offsetX, o -> setOffsetX(-o));
        slider.updateAssets();
        return slider;
    }

    public void setOffsetX(final int offsetX) {
        this.offsetX = offsetX;
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

        if (slider != null) {
            slider.process(eventLogger);

            if (mouseIsWithinBounds(eventLogger.getAdjustedMousePosition())) {
                final List<GameEvent> up = eventLogger.getUnprocessedEvents();

                for (GameEvent e : up) {
                    if (e instanceof GameMouseScrollEvent mse) {
                        mse.markAsProcessed();

                        slider.incrementValue(mse.clicksScrolled * Layout.PX_PER_SCROLL);
                    }
                }
            }
        }
    }

    @Override
    Coord2D calculateOffset() {
        return new Coord2D(offsetX, 0);
    }

    @Override
    boolean renderAndProcessChild(final ScrollableMenuElement child) {
        final Coord2D rp = getRenderPosition(), childRP = child.getRenderPosition();
        final int w = getWidth(), childW = child.getWidth();

        return rp.x <= childRP.x && rp.x + w >= childRP.x + childW;
    }
}
