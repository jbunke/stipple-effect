package com.jordanbunke.stipple_effect.menu_elements.scrollable;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Slider extends MenuElement {
    public final int minValue, maxValue;
    private int value, onClickDiffDim;

    private final Function<Coord2D, Integer> coordDimFunction;
    private final Function<Slider, Integer> sliderDimFunction;

    public final Consumer<Integer> setter;

    private boolean sliding, highlighted;

    private GameImage baseImage, highlightedImage, selectedImage;

    public Slider(
            final Coord2D position, final Coord2D dimensions,
            final Anchor anchor, final int minValue, final int maxValue,
            final int initialValue, final Consumer<Integer> setter,
            final Function<Coord2D, Integer> coordDimFunction,
            final Function<Slider, Integer> sliderDimFunction
    ) {
        super(position, dimensions, anchor, true);

        this.minValue = minValue;
        this.maxValue = maxValue;

        value = initialValue;

        this.setter = setter;

        sliding = false;
        onClickDiffDim = 0;

        this.coordDimFunction = coordDimFunction;
        this.sliderDimFunction = sliderDimFunction;
    }

    @Override
    public void update(final double deltaTime) {
        // done
    }

    @Override
    public void render(final GameImage canvas) {
        final GameImage asset = isSliding() ? selectedImage
                : (isHighlighted() ? highlightedImage : baseImage);

        draw(asset, canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        final Coord2D mp = eventLogger.getAdjustedMousePosition();
        final boolean mouseInBounds = mouseIsWithinBounds(mp);

        final int mpd = coordDimFunction.apply(mp);
        highlighted = mouseInBounds && isValidDimForHighlight(mpd);

        final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();
        for (GameEvent e : unprocessed) {
            if (e instanceof GameMouseEvent me) {
                switch (me.action) {
                    case DOWN -> {
                        if (mouseInBounds) {
                            me.markAsProcessed();
                            final boolean validDim = highlighted;

                            if (validDim) {
                                sliding = true;
                                onClickDiffDim = mpd - getSliderBallDim();
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
            final int sliderBallDim = mpd - onClickDiffDim;
            setValueFromSliderBallDim(sliderBallDim);
            setter.accept(value);
        }
    }

    // graphical
    public GameImage[] drawSliderBallShells() {
        final int sbd = Constants.SLIDER_BALL_DIM;

        final GameImage baseSliderBall = new GameImage(sbd, sbd),
                highlightedSliderBall = new GameImage(sbd, sbd),
                selectedSliderBall = new GameImage(sbd, sbd);

        baseSliderBall.fillRectangle(
                GraphicsUtils.buttonBorderColor(false), 0, 0, sbd, sbd);
        highlightedSliderBall.fillRectangle(
                GraphicsUtils.buttonBorderColorAlt(false), 0, 0, sbd, sbd);
        selectedSliderBall.fillRectangle(
                GraphicsUtils.buttonBorderColor(true), 0, 0, sbd, sbd);

        return new GameImage[] {
                baseSliderBall, highlightedSliderBall, selectedSliderBall
        };
    }

    public GameImage drawSliderCore(final int w, final int h) {
        final GameImage sliderCore = new GameImage(w, h);

        sliderCore.fillRectangle(Constants.WHITE, 0, 0, w, h);

        return sliderCore.submit();
    }

    public Color getSliderBallCoreColor() {
        return Constants.GREY;
    }

    // helper
    public double getSliderFraction() {
        return (value - minValue) / (double)(maxValue - minValue);
    }

    public int getValueFromSliderFraction(final double sliderFraction) {
        return minValue + (int)(sliderFraction * (maxValue - minValue));
    }

    public boolean isValidDimForHighlight(final int mouseDim) {
        return Math.abs(mouseDim - getSliderBallDim()) <= Constants.SLIDER_BALL_DIM / 2;
    }

    private int getSliderBallDim() {
        final Coord2D rp = getRenderPosition();
        final int sbd = Constants.SLIDER_BALL_DIM;
        return coordDimFunction.apply(rp) + (sbd / 2) +
                (int)(getSliderFraction() * (sliderDimFunction.apply(this) - sbd));
    }

    private void setValueFromSliderBallDim(final int sliderBallDim) {
        final int valueWas = getValue();

        final Coord2D rp = getRenderPosition();
        final int sbd = Constants.SLIDER_BALL_DIM,
                sd = sliderDimFunction.apply(this) - sbd,
                startDim = coordDimFunction.apply(rp) + (sbd / 2);
        final double sliderFraction = (sliderBallDim - startDim) / (double) sd;

        final int prospect = getValueFromSliderFraction(sliderFraction);
        value = Math.max(minValue, Math.min(prospect, maxValue));

        if (getValue() != valueWas)
            updateAssets();
    }

    public abstract void drawSlider(final GameImage slider);
    public abstract Coord2D getSliderBallRenderPos(final int sliderBallRenderDim);

    public void updateAssets() {
        final int sbd = Constants.SLIDER_BALL_DIM,
                sd = sliderDimFunction.apply(this) - sbd;
        final GameImage slider = new GameImage(getWidth(), getHeight());

        drawSlider(slider);
        slider.free();

        final GameImage b = new GameImage(slider), h = new GameImage(slider),
                s = new GameImage(slider);

        // slider ball shell
        final GameImage[] sliderBalls = drawSliderBallShells();

        // slider ball core
        final int sbcd = sbd - (2 * Constants.BUTTON_BORDER_PX);

        for (GameImage sliderBall : sliderBalls)
            sliderBall.fillRectangle(getSliderBallCoreColor(),
                    Constants.BUTTON_BORDER_PX, Constants.BUTTON_BORDER_PX,
                    sbcd, sbcd);

        final int sliderBallRenderDim = (int)(getSliderFraction() * sd);
        final Coord2D sliderBallRenderPos = getSliderBallRenderPos(sliderBallRenderDim);

        b.draw(sliderBalls[0].submit(), sliderBallRenderPos.x, sliderBallRenderPos.y);
        h.draw(sliderBalls[1].submit(), sliderBallRenderPos.x, sliderBallRenderPos.y);
        s.draw(sliderBalls[2].submit(), sliderBallRenderPos.x, sliderBallRenderPos.y);

        baseImage = b.submit();
        highlightedImage = h.submit();
        selectedImage = s.submit();
    }

    // GETTERS

    public boolean isHighlighted() {
        return highlighted;
    }

    public boolean isSliding() {
        return sliding;
    }

    public int getValue() {
        return value;
    }
}
