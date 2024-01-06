package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.function.Consumer;

public class HorizontalSlider extends Slider {

    public HorizontalSlider(
            final Coord2D position, final int width, final Anchor anchor,
            final int minValue, final int maxValue, final int initialValue,
            final Consumer<Integer> setter, final boolean canSetImplicitly
    ) {
        super(position, new Coord2D(width, Constants.SLIDER_OFF_DIM), anchor,
                minValue, maxValue, initialValue,
                setter, canSetImplicitly, c -> c.x, MenuElement::getWidth);
    }

    public HorizontalSlider(
            final Coord2D position, final int width, final Anchor anchor,
            final int minValue, final int maxValue, final int initialValue,
            final Consumer<Integer> setter
    ) {
        this(position, width, anchor, minValue, maxValue, initialValue,
                setter, true);
    }

    @Override
    public void drawSlider(final GameImage slider) {
        final int sbd = Constants.SLIDER_BALL_DIM, sd = getWidth() - sbd,
                sh = getHeight() - (2 * Constants.SLIDER_THINNING);

        // slider core
        slider.draw(drawSliderCore(sd, sh), Constants.SLIDER_BALL_DIM / 2, Constants.SLIDER_THINNING);

        // slider outline
        slider.drawRectangle(Constants.BLACK, Constants.BUTTON_BORDER_PX, Constants.SLIDER_BALL_DIM / 2,
                Constants.SLIDER_THINNING + (Constants.BUTTON_BORDER_PX / 2), sd,
                getHeight() - (Constants.BUTTON_BORDER_PX + (2 * Constants.SLIDER_THINNING)));
    }

    @Override
    public Coord2D getSliderBallRenderPos(final int sliderBallRenderDim) {
        return new Coord2D(sliderBallRenderDim, 0);
    }
}
