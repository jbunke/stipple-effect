package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.SECursor;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class HorizontalSlider extends Slider {

    public HorizontalSlider(
            final Coord2D position, final int width, final Anchor anchor,
            final int minValue, final int maxValue,
            final Supplier<Integer> getter, final Consumer<Integer> setter,
            final boolean canSetImplicitly
    ) {
        super(position, new Bounds2D(width, Layout.SLIDER_OFF_DIM), anchor,
                minValue, maxValue, getter, setter, canSetImplicitly);
    }

    public HorizontalSlider(
            final Coord2D position, final int width, final Anchor anchor,
            final int minValue, final int maxValue,
            final Supplier<Integer> getter, final Consumer<Integer> setter
    ) {
        this(position, width, anchor, minValue, maxValue,
                getter, setter, true);
    }

    @Override
    public void drawSlider(final GameImage slider) {
        final int sbd = Layout.SLIDER_BALL_DIM, sd = getWidth() - sbd,
                sh = getHeight() - (2 * Layout.SLIDER_THINNING);

        // slider core
        slider.draw(drawSliderCore(sd, sh),
                Layout.SLIDER_BALL_DIM / 2, Layout.SLIDER_THINNING);

        // slider outline
        Settings.getTheme().logic.drawHorizontalSliderOutline(slider);
    }

    @Override
    public Coord2D getSliderBallRenderPos(final int sliderBallRenderDim) {
        return new Coord2D(sliderBallRenderDim, 0);
    }

    @Override
    protected int getCoordDimension(final Coord2D position) {
        return position.x;
    }

    @Override
    protected int getSizeDimension() {
        return getWidth();
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        super.process(eventLogger);

        if (isSliding())
            SECursor.setCursorCode(SECursor.SLIDE_HORZ);
    }
}
