package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.container.MenuElementContainer;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ColorComponent extends MenuElementContainer {
    private final MenuElement[] menuElements;

    enum Alignment {
        LEFT, RIGHT, FULL
    }

    public ColorComponent(
            final int minValue, final int maxValue, final String label,
            final Alignment alignment, final int indexY,
            final Function<Integer, Color> spectralFunction,
            final Consumer<Color> setter, final Supplier<Integer> getter
    ) {
        super(new Coord2D(), new Bounds2D(1, 1), Anchor.LEFT_TOP, false);

        final boolean isFull = alignment == Alignment.FULL;

        final int globalOffsetX = alignment == Alignment.RIGHT
                ? Layout.getColorsWidth() / 2 : 0,
                globalOffsetY = Layout.COLOR_SELECTOR_OFFSET_Y +
                        (indexY * Layout.getColorSelectorIncY()),
                width = isFull
                        ? Layout.getColorsWidth() : Layout.getColorsWidth() / 2,
                indent = Layout.CONTENT_BUFFER_PX;
        final Coord2D startingPos = Layout.getColorsPosition()
                .displace(globalOffsetX, globalOffsetY);
        final Bounds2D buttonDims = new Bounds2D(
                Layout.BUTTON_DIM, Layout.BUTTON_DIM);

        final List<MenuElement> elements = new ArrayList<>();

        // label
        elements.add(TextLabel.make(
                startingPos.displace(indent, Layout.COLOR_LABEL_OFFSET_Y),
                label, Settings.getTheme().textLight));

        // value
        elements.add(new DynamicLabel(startingPos.displace(
                width - indent, Layout.COLOR_LABEL_OFFSET_Y),
                Anchor.RIGHT_TOP, Settings.getTheme().textLight,
                () -> String.valueOf(getter.get()),
                Layout.DYNAMIC_LABEL_W_ALLOWANCE));

        // increment and decrement buttons
        final GameImage baseDec = GraphicsUtils.loadIcon(IconCodes.DECREMENT),
                highlightDec = GraphicsUtils.highlightIconButton(baseDec),
                baseInc = GraphicsUtils.loadIcon(IconCodes.INCREMENT),
                highlightInc = GraphicsUtils.highlightIconButton(baseInc);

        elements.add(new SimpleMenuButton(startingPos.displace((width / 2) -
                (Layout.BUTTON_INC / 2), -Layout.BUTTON_BORDER_PX),
                buttonDims, Anchor.CENTRAL, true,
                () -> setter.accept(spectralFunction
                        .apply(Math.max(getter.get() - 1, 0))),
                baseDec, highlightDec));
        elements.add(new SimpleMenuButton(startingPos.displace((width / 2) +
                (Layout.BUTTON_INC / 2), -Layout.BUTTON_BORDER_PX),
                buttonDims, Anchor.CENTRAL, true,
                () -> setter.accept(spectralFunction
                        .apply(Math.min(getter.get() + 1, maxValue))),
                baseInc, highlightInc));

        // slider
        elements.add(new ColorSlider(startingPos.displace(width / 2,
                (int)(Layout.getColorSelectorIncY() * 0.35)),
                (isFull ? Layout.FULL_COLOR_SLIDER_W :
                        Layout.HALF_COLOR_SLIDER_W) + Layout.SLIDER_BALL_DIM,
                minValue, maxValue, getter, spectralFunction,
                i -> setter.accept(spectralFunction.apply(i))));

        menuElements = elements.toArray(MenuElement[]::new);
    }

    @Override
    public void update(final double deltaTime) {
        for (MenuElement menuElement : menuElements)
            menuElement.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        for (MenuElement menuElement : menuElements)
            menuElement.render(canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {
        for (MenuElement menuElement : menuElements)
            menuElement.debugRender(canvas, debugger);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        for (MenuElement menuElement : menuElements)
            menuElement.process(eventLogger);
    }

    @Override
    public MenuElement[] getMenuElements() {
        return menuElements;
    }

    @Override
    public boolean hasNonTrivialBehaviour() {
        return false;
    }
}
