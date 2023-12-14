package com.jordanbunke.stipple_effect.menu_elements.scrollable;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.Coord2D;

public class VerticalScrollingMenuElement extends ScrollingMenuElement {

    private final int realBottomY;
    private final boolean canScroll;

    public VerticalScrollingMenuElement(
            final Coord2D position, final Coord2D dimensions,
            final ScrollableMenuElement[] menuElements,
            final int realBottomY
    ) {
        super(position, dimensions, menuElements);

        this.realBottomY = realBottomY;
        this.canScroll = realBottomY > position.y + dimensions.y;
    }

    @Override
    public void render(final GameImage canvas) {
        super.render(canvas);

        // TODO - scroll bar
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        super.process(eventLogger);

        // TODO - process scroll bar
    }

    @Override
    Coord2D calculateOffset() {
        // TODO
        return new Coord2D(0, 0);
    }

    @Override
    boolean renderAndProcessChild(final ScrollableMenuElement child) {
        final Coord2D rp = getRenderPosition(), childRP = child.getRenderPosition();
        final int h = getHeight(), childH = child.getHeight();

        return rp.y <= childRP.y && rp.y + h >= childRP.y + childH;
    }
}
