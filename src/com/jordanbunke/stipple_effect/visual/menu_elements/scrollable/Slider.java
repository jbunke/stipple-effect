package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.AbstractSlider;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.funke.core.ConcreteProperty;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Slider extends AbstractSlider {
    private GameImage baseImage, highlightedImage, slidingImage;

    public Slider(
            final Coord2D position, final Bounds2D dimensions, final Anchor anchor,
            final int minValue, final int maxValue, final Supplier<Integer> getter,
            final Consumer<Integer> setter, final boolean canSetImplicitly
    ) {
        super(position, dimensions, anchor, minValue, maxValue,
                new ConcreteProperty<>(getter, setter), canSetImplicitly,
                Layout.SLIDER_BALL_DIM);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {}

    // graphical
    public GameImage[] drawSliderBalls() {
        final Theme t = Settings.getTheme();
        final Color fill = getSliderBallCoreColor();

        return new GameImage[] {
                t.logic.drawSliderBall(false, false, fill),
                t.logic.drawSliderBall(false, true, fill),
                t.logic.drawSliderBall(true, false, fill)
        };
    }

    public GameImage drawSliderCore(final int w, final int h) {
        final GameImage sliderCore = new GameImage(w, h);

        sliderCore.fillRectangle(
                Settings.getTheme().defaultSliderCore, 0, 0, w, h);

        return sliderCore.submit();
    }

    public Color getSliderBallCoreColor() {
        return Settings.getTheme().defaultSliderBall;
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
