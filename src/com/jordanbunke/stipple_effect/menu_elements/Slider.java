package com.jordanbunke.stipple_effect.menu_elements;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Slider extends MenuElement {
    private final int minValue, maxValue;
    private int value;

    private final Function<Integer, Color> spectralFunction;
    private final Consumer<Integer> setter;

    private boolean sliding;
    private int onClickDiffX;

    private GameImage spectrum;

    public Slider(
            final Coord2D position, final Coord2D dimensions,
            final int minValue, final int maxValue, final int initialValue,
            final Function<Integer, Color> spectralFunction,
            final Consumer<Integer> setter
    ) {
        super(position, dimensions, Anchor.CENTRAL, true);

        this.minValue = minValue;
        this.maxValue = maxValue;

        value = initialValue;

        this.spectralFunction = spectralFunction;
        this.setter = setter;

        sliding = false;
        onClickDiffX = 0;

        updateAssets();
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        final Coord2D mp = eventLogger.getAdjustedMousePosition();
        final boolean mouseInBounds = mouseIsWithinBounds(mp);

        final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();
        for (GameEvent e : unprocessed) {
            if (e instanceof GameMouseEvent me) {
                switch (me.action) {
                    case DOWN -> {
                        if (mouseInBounds) {
                            me.markAsProcessed();
                            final boolean validX = Math.abs(mp.x - getSliderBallX()) <=
                                    Constants.SLIDER_BALL_W / 2;

                            if (validX) {
                                sliding = true;
                                onClickDiffX = mp.x - getSliderBallX();
                            }
                        }
                    }
                    case UP -> {
                        if (sliding) {
                            me.markAsProcessed();
                            sliding = false;
                        }
                    }
                }
            }
        }

        if (sliding) {
            final int sliderBallX = mp.x - onClickDiffX;
            setValueFromSliderBallX(sliderBallX);
            setter.accept(value);
        }
    }

    @Override
    public void update(final double deltaTime) {

    }

    @Override
    public void render(final GameImage canvas) {
        draw(spectrum, canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    // helper
    private double getSliderFraction() {
        return (value - minValue) / (double)(maxValue - minValue);
    }

    private int getSliderBallX() {
        final Coord2D rp = getRenderPosition();
        return rp.x + (int)(getSliderFraction() * getWidth());
    }

    private void setValueFromSliderBallX(final int sliderBallX) {
        final int valueWas = value;

        final Coord2D rp = getRenderPosition();
        final double sliderFraction = (sliderBallX - rp.x) / (double) getWidth();

        final int prospect = getValueFromSliderFraction(sliderFraction);
        this.value = Math.max(minValue, Math.min(prospect, maxValue));

        if (value != valueWas)
            updateAssets();
    }

    private int getValueFromSliderFraction(final double sliderFraction) {
        return minValue + (int)(sliderFraction * (maxValue - minValue));
    }

    private void updateAssets() {
        spectrum = new GameImage(getWidth(), getHeight());

        for (int x = 0; x < spectrum.getWidth(); x++) {
            spectrum.fillRectangle(
                    spectralFunction.apply(getValueFromSliderFraction(x / (double) getWidth())),
                    x, 0, 1, getHeight());
        }

        // TODO
        spectrum.fillRectangle(Constants.WHITE,
                (int)(getSliderFraction() * getWidth()) -
                        (Constants.SLIDER_BALL_W / 2),
                0, Constants.SLIDER_BALL_W, Constants.SLIDER_H);

        spectrum.free();
    }
}
