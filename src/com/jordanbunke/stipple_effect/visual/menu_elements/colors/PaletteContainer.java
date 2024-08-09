package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.Scrollable;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.action.SEAction;
import com.jordanbunke.stipple_effect.visual.menu_elements.ActionButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class PaletteContainer extends MenuElement {
    private static PaletteContainer INSTANCE;
    private static int buttonsPerLine = 0;

    private VerticalScrollBox container;
    private final Palette palette;

    private PaletteContainer(
            final Coord2D position, final Bounds2D dimensions,
            final Palette palette
    ) {
        super(position, dimensions, Anchor.LEFT_TOP, true);

        this.palette = palette;
        makeContainer(false);
    }

    public static PaletteContainer get() {
        return INSTANCE;
    }

    public static PaletteContainer make(
            final Coord2D position, final Bounds2D dimensions,
            final Palette palette
    ) {
        INSTANCE = new PaletteContainer(position, dimensions, palette);

        return INSTANCE;
    }

    public void makeContainer(final boolean refresh) {
        final int initialOffsetY = refresh ? -container.getOffset().y : 0;

        buttonsPerLine = (getWidth() - Layout.SLIDER_OFF_DIM) /
                Layout.PALETTE_DIMS.width();

        final List<MenuElement> buttons = new ArrayList<>();
        final Color[] colors = palette.getColors();

        PaletteColorButton.logic.reset();

        for (int i = 0; i <= colors.length; i++) {
            final int x = i % buttonsPerLine, y = i / buttonsPerLine;
            final Coord2D pos = getPosition().displace(
                    x * Layout.PALETTE_DIMS.width(),
                    y * Layout.PALETTE_DIMS.height());

            buttons.add(i == colors.length
                    ? new ActionButton(pos.displace(
                    Layout.BUTTON_OFFSET, Layout.BUTTON_OFFSET),
                    SEAction.ADD_TO_PALETTE, null)
                    : new PaletteColorButton(pos, colors[i], palette, i));
        }

        container = new VerticalScrollBox(
                getPosition(), getDimensions(),
                buttons.stream().map(Scrollable::new)
                        .toArray(Scrollable[]::new),
                getY() + (((colors.length / buttonsPerLine) + 1) *
                        Layout.PALETTE_DIMS.height()), initialOffsetY);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        container.process(eventLogger);
    }

    @Override
    public void update(final double deltaTime) {
        container.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        container.render(canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    public static int getButtonsPerLine() {
        return buttonsPerLine;
    }
}
