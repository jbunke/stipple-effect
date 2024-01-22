package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class HorizontalSlider extends Slider {

    public HorizontalSlider(
            final Coord2D position, final int width, final Anchor anchor,
            final int minValue, final int maxValue,
            final Supplier<Integer> getter, final Consumer<Integer> setter,
            final boolean canSetImplicitly
    ) {
        super(position, new Coord2D(width, Layout.SLIDER_OFF_DIM), anchor,
                minValue, maxValue, getter, setter,
                canSetImplicitly, c -> c.x, MenuElement::getWidth);
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
        slider.draw(drawSliderCore(sd, sh), Layout.SLIDER_BALL_DIM / 2, Layout.SLIDER_THINNING);

        // slider outline
        slider.drawRectangle(Constants.BLACK, Layout.BUTTON_BORDER_PX, Layout.SLIDER_BALL_DIM / 2,
                Layout.SLIDER_THINNING + (Layout.BUTTON_BORDER_PX / 2), sd,
                getHeight() - (Layout.BUTTON_BORDER_PX + (2 * Layout.SLIDER_THINNING)));
    }

    @Override
    public Coord2D getSliderBallRenderPos(final int sliderBallRenderDim) {
        return new Coord2D(sliderBallRenderDim, 0);
    }
}
