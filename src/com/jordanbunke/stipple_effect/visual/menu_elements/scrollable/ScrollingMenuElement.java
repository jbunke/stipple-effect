package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;

public abstract class ScrollingMenuElement extends MenuElement {
    final ScrollableMenuElement[] menuElements;

    // cosmetic
    private final GameImage background;

    public ScrollingMenuElement(
            final Coord2D position, final Coord2D dimensions,
            final ScrollableMenuElement[] menuElements
    ) {
        super(position, dimensions, Anchor.LEFT_TOP, true);

        this.menuElements = menuElements;

        this.background = drawBackground();
    }

    private GameImage drawBackground() {
        final int w = getWidth(), h = getHeight();

        final GameImage background = new GameImage(w, h);
        background.fillRectangle(Constants.DARK, 0, 0, w, h);
        return background.submit();
    }

    @Override
    public void update(final double deltaTime) {
        final Coord2D offset = calculateOffset();

        for (ScrollableMenuElement sme : menuElements) {
            sme.setPositionFromOffset(offset);
            sme.update(deltaTime);
        }
    }

    @Override
    public void render(final GameImage canvas) {
        // background
        draw(background, canvas);

        // contents
        for (ScrollableMenuElement sme : menuElements)
            if (renderAndProcessChild(sme))
                sme.render(canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {
        for (ScrollableMenuElement sme : menuElements)
            if (renderAndProcessChild(sme))
                sme.debugRender(canvas, debugger);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        for (ScrollableMenuElement sme : menuElements)
            if (renderAndProcessChild(sme))
                sme.process(eventLogger);
    }

    abstract Coord2D calculateOffset();

    abstract boolean renderAndProcessChild(final ScrollableMenuElement child);
}
