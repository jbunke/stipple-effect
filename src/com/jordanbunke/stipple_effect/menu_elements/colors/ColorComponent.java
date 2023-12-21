package com.jordanbunke.stipple_effect.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementContainer;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.menu_elements.TextLabel;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;
import com.jordanbunke.stipple_effect.utility.IconCodes;

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
        super(new Coord2D(), new Coord2D(), Anchor.LEFT_TOP, false);

        final boolean isFull = alignment == Alignment.FULL;

        final int globalOffsetX = alignment == Alignment.RIGHT
                ? Constants.COLOR_PICKER_W / 2 : 0,
                globalOffsetY = Constants.COLOR_SELECTOR_OFFSET_Y +
                        (indexY * Constants.COLOR_SELECTOR_INC_Y),
                width = isFull
                        ? Constants.COLOR_PICKER_W : Constants.COLOR_PICKER_W / 2,
                indent = Constants.TOOL_NAME_X;
        final Coord2D startingPos = Constants.getColorsPosition().displace(
                globalOffsetX, globalOffsetY),
                buttonDims = new Coord2D(Constants.BUTTON_DIM, Constants.BUTTON_DIM);

        final List<MenuElement> elements = new ArrayList<>();

        // label
        elements.add(TextLabel.make(
                startingPos.displace(indent, Constants.COLOR_LABEL_OFFSET_Y),
                label, Constants.WHITE));

        // value
        elements.add(new DynamicLabel(startingPos.displace(
                width - indent, Constants.COLOR_LABEL_OFFSET_Y),
                Anchor.RIGHT_TOP, Constants.WHITE,
                () -> String.valueOf(getter.get()),
                Constants.DYNAMIC_LABEL_W_ALLOWANCE));

        // increment and decrement buttons
        final GameImage baseDec = GraphicsUtils.loadIcon(IconCodes.DECREMENT),
                highlightDec = GraphicsUtils.highlightIconButton(baseDec),
                baseInc = GraphicsUtils.loadIcon(IconCodes.INCREMENT),
                highlightInc = GraphicsUtils.highlightIconButton(baseInc);

        elements.add(new SimpleMenuButton(startingPos.displace((width / 2) -
                (Constants.BUTTON_INC / 2), -Constants.BUTTON_BORDER_PX),
                buttonDims, Anchor.CENTRAL, true,
                () -> setter.accept(spectralFunction
                        .apply(Math.max(getter.get() - 1, 0))),
                baseDec, highlightDec));
        elements.add(new SimpleMenuButton(startingPos.displace((width / 2) +
                (Constants.BUTTON_INC / 2), -Constants.BUTTON_BORDER_PX),
                buttonDims, Anchor.CENTRAL, true,
                () -> setter.accept(spectralFunction
                        .apply(Math.min(getter.get() + 1, 255))),
                baseInc, highlightInc));

        // slider
        elements.add(new ColorSlider(startingPos.displace(width / 2,
                (int)(Constants.COLOR_SELECTOR_INC_Y * 0.45)),
                (isFull ? Constants.FULL_COLOR_SLIDER_W :
                        Constants.HALF_COLOR_SLIDER_W) + Constants.SLIDER_BALL_DIM,
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
