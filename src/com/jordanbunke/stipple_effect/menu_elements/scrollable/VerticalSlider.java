package com.jordanbunke.stipple_effect.menu_elements.scrollable;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.function.Consumer;

public class VerticalSlider extends Slider {
    public VerticalSlider(
            final Coord2D position, final int height, final Anchor anchor,
            final int minValue, final int maxValue, final int initialValue,
            final Consumer<Integer> setter
    ) {
        super(position, new Coord2D(Constants.SLIDER_OFF_DIM, height), anchor,
                minValue, maxValue, initialValue,
                setter, true, c -> c.y, MenuElement::getHeight);
    }

    @Override
    public void drawSlider(final GameImage slider) {
        final int sbd = Constants.SLIDER_BALL_DIM, sd = getHeight() - sbd,
                sw = getWidth() - (2 * Constants.SLIDER_THINNING);

        // slider core
        slider.draw(drawSliderCore(sw, sd), Constants.SLIDER_THINNING, Constants.SLIDER_BALL_DIM / 2);

        // slider outline
        slider.drawRectangle(Constants.BLACK, Constants.BUTTON_BORDER_PX,
                Constants.SLIDER_THINNING + (Constants.BUTTON_BORDER_PX / 2),
                Constants.SLIDER_BALL_DIM / 2,
                getWidth() - (Constants.BUTTON_BORDER_PX + (2 * Constants.SLIDER_THINNING)), sd
        );
    }

    @Override
    public Coord2D getSliderBallRenderPos(final int sliderBallRenderDim) {
        return new Coord2D(0, sliderBallRenderDim);
    }
}
