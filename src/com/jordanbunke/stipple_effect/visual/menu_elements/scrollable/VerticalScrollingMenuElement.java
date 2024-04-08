package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseScrollEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;

import java.util.List;

public class VerticalScrollingMenuElement extends ScrollingMenuElement {

    private int offsetY;

    private final VerticalSlider slider;

    public VerticalScrollingMenuElement(
            final Coord2D position, final Coord2D dimensions,
            final ScrollableMenuElement[] menuElements,
            final int realBottomY, final int initialOffsetY
    ) {
        super(position, dimensions, menuElements);

        final int minOffsetY = 0, maxOffsetY = (realBottomY - getY()) - getHeight();

        offsetY = -Math.max(minOffsetY, Math.min(initialOffsetY, maxOffsetY));

        final boolean canScroll = realBottomY > position.y + dimensions.y;
        slider = canScroll ? makeSlider(maxOffsetY) : null;
    }

    private VerticalSlider makeSlider(final int maxOffsetY) {
        final Coord2D position = new Coord2D(getX(), getY())
                .displace(getWidth() - Layout.SLIDER_OFF_DIM, 0);

        final VerticalSlider slider = new VerticalSlider(position, getHeight(), Anchor.LEFT_TOP,
                0, maxOffsetY, () -> -offsetY, o -> setOffsetY(-o));
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

    // TODO - refactor this and Horizontal towards superclass
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
        return new Coord2D(0, offsetY);
    }

    @Override
    boolean renderAndProcessChild(final ScrollableMenuElement child) {
        final Coord2D rp = getRenderPosition(), childRP = child.getRenderPosition();
        final int h = getHeight(), childH = child.getHeight();

        return rp.y <= childRP.y && rp.y + h >= childRP.y + childH;
    }
}
