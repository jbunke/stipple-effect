package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class VerticalSlider extends Slider {
    public VerticalSlider(
            final Coord2D position, final int height, final Anchor anchor,
            final int minValue, final int maxValue,
            final Supplier<Integer> getter, final Consumer<Integer> setter
    ) {
        super(position, new Coord2D(Layout.SLIDER_OFF_DIM, height), anchor,
                minValue, maxValue, getter, setter, true);
    }

    @Override
    public void drawSlider(final GameImage slider) {
        final int sbd = Layout.SLIDER_BALL_DIM, sd = getHeight() - sbd,
                sw = getWidth() - (2 * Layout.SLIDER_THINNING);

        // slider core
        slider.draw(drawSliderCore(sw, sd), Layout.SLIDER_THINNING, Layout.SLIDER_BALL_DIM / 2);

        // slider outline
        slider.drawRectangle(Settings.getTheme().getButtonOutline(),
                Layout.BUTTON_BORDER_PX,
                Layout.SLIDER_THINNING + (Layout.BUTTON_BORDER_PX / 2),
                Layout.SLIDER_BALL_DIM / 2,
                getWidth() - (Layout.BUTTON_BORDER_PX + (2 * Layout.SLIDER_THINNING)), sd
        );
    }

    @Override
    public Coord2D getSliderBallRenderPos(final int sliderBallRenderDim) {
        return new Coord2D(0, sliderBallRenderDim);
    }

    @Override
    protected int getCoordDimension(final Coord2D position) {
        return position.y;
    }

    @Override
    protected int getSizeDimension() {
        return getHeight();
    }
}
