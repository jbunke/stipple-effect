package com.jordanbunke.stipple_effect.menu_elements;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementContainer;
import com.jordanbunke.delta_time.menus.menu_elements.invisible.PlaceholderMenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public class ValueElement extends MenuElementContainer {
    private static final int NUM_ELEMENTS = 5, SLIDER_INDEX = 0,
            LABEL_INDEX = 1, VALUE_INDEX = 2,
            INCREMENT_INDEX = 3, DECREMENT_INDEX = 4;

    private final int minValue, maxValue;
    private int value;

    private final Consumer<Color> setter;
    private final Function<Integer, Color> converter;

    private final MenuElement[] menuElements;

    public ValueElement(
            final int minValue, final int maxValue, final String label,
            final Function<Integer, Color> spectralFunction, final Consumer<Color> setter,
            final Callable<Integer> initialValueGetter, final Coord2D startingPosition
    ) {
        super(new Coord2D(), new Coord2D(), Anchor.LEFT_TOP, false);

        this.minValue = minValue;
        this.maxValue = maxValue;

        this.setter = setter;
        converter = spectralFunction;

        try {
            value = initialValueGetter.call();
        } catch (Exception e) {
            GameError.send("Couldn't assign value from initialValueGetter function.");
            value = minValue;
        }

        menuElements = new MenuElement[NUM_ELEMENTS];

        menuElements[SLIDER_INDEX] = new Slider(
                startingPosition.displace(Constants.COLOR_PICKER_W / 2, 0 /* TODO */),
                new Coord2D(Constants.SLIDER_W, Constants.SLIDER_H),
                this.minValue, this.maxValue, this.value,
                spectralFunction, this::setValue
        );
        menuElements[LABEL_INDEX] = new PlaceholderMenuElement();
        menuElements[VALUE_INDEX] = new PlaceholderMenuElement();
        menuElements[INCREMENT_INDEX] = new PlaceholderMenuElement();
        menuElements[DECREMENT_INDEX] = new PlaceholderMenuElement();

        // TODO - menu elements: up button, down button, label, value
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
