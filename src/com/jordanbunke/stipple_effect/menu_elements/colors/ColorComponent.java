package com.jordanbunke.stipple_effect.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementContainer;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public class ColorComponent extends MenuElementContainer {
    private static final int NUM_ELEMENTS = 3, SLIDER_INDEX = 0,
            LABEL_INDEX = 1, VALUE_INDEX = 2;

    private int value;

    private final Consumer<Color> setter;
    private final Function<Integer, Color> converter;

    private final MenuElement[] menuElements;

    public ColorComponent(
            final int minValue, final int maxValue, final String label,
            final Function<Integer, Color> spectralFunction, final Consumer<Color> setter,
            final Callable<Integer> initialValueGetter, final Coord2D startingPosition
    ) {
        super(new Coord2D(), new Coord2D(), Anchor.LEFT_TOP, false);

        this.setter = setter;
        converter = spectralFunction;

        try {
            value = initialValueGetter.call();
        } catch (Exception e) {
            GameError.send("Couldn't assign value from initialValueGetter function.");
            value = minValue;
        }

        menuElements = new MenuElement[NUM_ELEMENTS];

        menuElements[SLIDER_INDEX] = new ColorSlider(
                startingPosition.displace(Constants.COLOR_PICKER_W / 2,
                        (int)(Constants.COLOR_SELECTOR_INC_Y * 0.45)),
                Constants.COLOR_SLIDER_W + Constants.SLIDER_BALL_DIM,
                minValue, maxValue, this.value, spectralFunction, this::setValue);
        menuElements[LABEL_INDEX] = TextLabel.make(
                startingPosition.displace(Constants.TOOL_NAME_X, Constants.COLOR_LABEL_OFFSET_Y),
                label, Constants.BLACK);
        menuElements[VALUE_INDEX] = new DynamicLabel(
                startingPosition.displace(Constants.COLOR_PICKER_W - Constants.TOOL_NAME_X,
                        Constants.COLOR_LABEL_OFFSET_Y),
                Anchor.RIGHT_TOP, Constants.BLACK, () -> String.valueOf(initialValueGetter.call()),
                Constants.DYNAMIC_LABEL_W_ALLOWANCE
        );
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

    public void setValue(int value) {
        this.value = value;
        setter.accept(converter.apply(this.value));
    }
}
