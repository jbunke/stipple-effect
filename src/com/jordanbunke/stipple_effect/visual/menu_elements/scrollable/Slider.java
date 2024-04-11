package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.AbstractSlider;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.funke.core.ConcreteProperty;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Slider extends AbstractSlider {
    private GameImage baseImage, highlightedImage, slidingImage;

    public Slider(
            final Coord2D position, final Coord2D dimensions, final Anchor anchor,
            final int minValue, final int maxValue, final Supplier<Integer> getter,
            final Consumer<Integer> setter, final boolean canSetImplicitly
    ) {
        super(position, dimensions, anchor, minValue, maxValue,
                new ConcreteProperty<>(getter, setter), canSetImplicitly,
                Layout.SLIDER_BALL_DIM);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    // graphical
    public GameImage[] drawSliderBalls() {
        final int BASE = 0, HIGHLIGHTED = 1, SELECTED = 2;
        final int sbd = Layout.SLIDER_BALL_DIM;

        final int sbcd = sbd - (2 * Layout.BUTTON_BORDER_PX);

        final GameImage[] sliderBalls = new GameImage[] {
                new GameImage(sbd, sbd),
                GameImage.dummy(),
                new GameImage(sbd, sbd)
        };

        sliderBalls[BASE].fillRectangle(
                GraphicsUtils.buttonBorderColor(false), 0, 0, sbd, sbd);
        sliderBalls[SELECTED].fillRectangle(
                GraphicsUtils.buttonBorderColor(true), 0, 0, sbd, sbd);

        sliderBalls[BASE].fillRectangle(getSliderBallCoreColor(),
                Layout.BUTTON_BORDER_PX, Layout.BUTTON_BORDER_PX, sbcd, sbcd);
        sliderBalls[SELECTED].fillRectangle(getSliderBallCoreColor(),
                Layout.BUTTON_BORDER_PX, Layout.BUTTON_BORDER_PX, sbcd, sbcd);

        sliderBalls[HIGHLIGHTED] =
                GraphicsUtils.drawHighlightedButton(sliderBalls[BASE]);

        return sliderBalls;
    }

    public GameImage drawSliderCore(final int w, final int h) {
        final GameImage sliderCore = new GameImage(w, h);

        sliderCore.fillRectangle(
                Settings.getTheme().getDefaultSliderCore(), 0, 0, w, h);

        return sliderCore.submit();
    }

    public Color getSliderBallCoreColor() {
        return Settings.getTheme().getDefaultSliderBall();
    }

    public abstract void drawSlider(final GameImage slider);
    public abstract Coord2D getSliderBallRenderPos(final int sliderBallRenderDim);

    @Override
    protected GameImage drawSliding() {
        return slidingImage;
    }

    @Override
    protected GameImage drawHighlighted() {
        return highlightedImage;
    }

    @Override
    protected GameImage drawBasic() {
        return baseImage;
    }

    public void updateAssets() {
        final int sbd = Layout.SLIDER_BALL_DIM,
                sd = getSizeDimension() - sbd;
        final GameImage slider = new GameImage(getWidth(), getHeight());

        drawSlider(slider);
        slider.free();

        final GameImage b = new GameImage(slider), h = new GameImage(slider),
                s = new GameImage(slider);

        // slider balls
        final GameImage[] sliderBalls = drawSliderBalls();

        final int sliderBallRenderDim = (int)(getSliderFraction() * sd);
        final Coord2D sliderBallRenderPos =
                getSliderBallRenderPos(sliderBallRenderDim);

        b.draw(sliderBalls[0].submit(), sliderBallRenderPos.x, sliderBallRenderPos.y);
        h.draw(sliderBalls[1].submit(), sliderBallRenderPos.x, sliderBallRenderPos.y);
        s.draw(sliderBalls[2].submit(), sliderBallRenderPos.x, sliderBallRenderPos.y);

        baseImage = b.submit();
        highlightedImage = h.submit();
        slidingImage = s.submit();
    }
}
