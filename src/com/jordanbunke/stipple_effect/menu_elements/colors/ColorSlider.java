package com.jordanbunke.stipple_effect.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ColorSlider extends MenuElement {
    private final int minValue, maxValue;
    private int value;

    private final Function<Integer, Color> spectralFunction;
    private final Consumer<Integer> setter;
    private Color cLastUpdate;

    private boolean sliding, isHighlighted;
    private int onClickDiffX;

    private GameImage base, highlighted, selected;

    public ColorSlider(
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

        cLastUpdate = StippleEffect.get().getSelectedColor();

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
                            final boolean validX = isValidXForHighlight(mp.x);

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

        isHighlighted = mouseIsWithinBounds(mp) && isValidXForHighlight(mp.x);

        if (sliding) {
            final int sliderBallX = mp.x - onClickDiffX;
            setValueFromSliderBallX(sliderBallX);
            setter.accept(value);
        }
    }

    private boolean isValidXForHighlight(final int mouseX) {
        return Math.abs(mouseX - getSliderBallX()) <= Constants.SLIDER_BALL_DIM / 2;
    }

    @Override
    public void update(final double deltaTime) {
        final Color c = StippleEffect.get().getSelectedColor();

        if (!c.equals(cLastUpdate)) {
            updateAssets();
            cLastUpdate = c;
        }
    }

    @Override
    public void render(final GameImage canvas) {
        final GameImage asset = sliding ? selected
                : (isHighlighted ? highlighted : base);

        draw(asset, canvas);
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
        final int sbw = Constants.SLIDER_BALL_DIM;
        return rp.x + (sbw / 2) + (int)(getSliderFraction() * (getWidth() - sbw));
    }

    private void setValueFromSliderBallX(final int sliderBallX) {
        final int valueWas = value;

        final Coord2D rp = getRenderPosition();
        final int sbw = Constants.SLIDER_BALL_DIM, sw = getWidth() - sbw,
                startX = rp.x + (sbw / 2);
        final double sliderFraction = (sliderBallX - startX) / (double) sw;

        final int prospect = getValueFromSliderFraction(sliderFraction);
        this.value = Math.max(minValue, Math.min(prospect, maxValue));

        if (value != valueWas)
            updateAssets();
    }

    private int getValueFromSliderFraction(final double sliderFraction) {
        return minValue + (int)(sliderFraction * (maxValue - minValue));
    }

    private void updateAssets() {
        final GameImage spectrum = new GameImage(getWidth(), getHeight());
        final int sw = getWidth() - Constants.SLIDER_BALL_DIM;

        for (int x = 0; x < sw; x++) {
            spectrum.fillRectangle(
                    spectralFunction.apply(getValueFromSliderFraction(x / (double) sw)),
                    (Constants.SLIDER_BALL_DIM / 2) + x, Constants.SLIDER_THINNING,
                    1, getHeight() - (2 * Constants.SLIDER_THINNING));
        }

        spectrum.drawRectangle(Constants.BLACK, Constants.BUTTON_BORDER_PX, Constants.SLIDER_BALL_DIM / 2,
                Constants.SLIDER_THINNING + (Constants.BUTTON_BORDER_PX / 2), sw,
                getHeight() - (Constants.BUTTON_BORDER_PX + (2 * Constants.SLIDER_THINNING)));

        final GameImage baseSliderBall = new GameImage(Constants.SLIDER_BALL_DIM, Constants.SLIDER_OFF_DIM),
                highlightedSliderBall = new GameImage(Constants.SLIDER_BALL_DIM, Constants.SLIDER_OFF_DIM),
                selectedSliderBall = new GameImage(Constants.SLIDER_BALL_DIM, Constants.SLIDER_OFF_DIM);
        final GameImage[] sliderBalls = new GameImage[] {
                baseSliderBall, highlightedSliderBall, selectedSliderBall
        };

        baseSliderBall.fillRectangle(
                GraphicsUtils.buttonBorderColor(false), 0, 0,
                Constants.SLIDER_BALL_DIM, Constants.SLIDER_OFF_DIM);
        highlightedSliderBall.fillRectangle(
                GraphicsUtils.buttonBorderColorAlt(false), 0, 0,
                Constants.SLIDER_BALL_DIM, Constants.SLIDER_OFF_DIM);
        selectedSliderBall.fillRectangle(
                GraphicsUtils.buttonBorderColor(true), 0, 0,
                Constants.SLIDER_BALL_DIM, Constants.SLIDER_OFF_DIM);

        for (GameImage sliderBall : sliderBalls) {
            sliderBall.fillRectangle(
                    spectralFunction.apply(value),
                    Constants.BUTTON_BORDER_PX, Constants.BUTTON_BORDER_PX,
                    Constants.SLIDER_BALL_DIM - (2 * Constants.BUTTON_BORDER_PX),
                    Constants.SLIDER_OFF_DIM - (2 * Constants.BUTTON_BORDER_PX)
            );
        }

        spectrum.free();

        base = new GameImage(spectrum);
        highlighted = new GameImage(spectrum);
        selected = new GameImage(spectrum);

        final int sliderBallRenderX = (int)(getSliderFraction() * sw);

        base.draw(baseSliderBall.submit(), sliderBallRenderX, 0);
        highlighted.draw(highlightedSliderBall.submit(), sliderBallRenderX, 0);
        selected.draw(selectedSliderBall.submit(), sliderBallRenderX, 0);

        base.free();
        highlighted.free();
        selected.free();
    }
}
