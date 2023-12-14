package com.jordanbunke.stipple_effect.menu_elements.colors;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.menu_elements.scrollable.HorizontalSlider;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class ColorSlider extends HorizontalSlider {
    private final Function<Integer, Color> spectralFunction;
    private Color cLastUpdate;

    public ColorSlider(
            final Coord2D position, final int width,
            final int minValue, final int maxValue, final int initialValue,
            final Function<Integer, Color> spectralFunction,
            final Consumer<Integer> setter
    ) {
        super(position, width, Anchor.CENTRAL, minValue, maxValue, initialValue, setter);

        this.spectralFunction = spectralFunction;

        cLastUpdate = StippleEffect.get().getSelectedColor();

        updateAssets();
    }

    @Override
    public void update(final double deltaTime) {
        final Color c = StippleEffect.get().getSelectedColor();

        if (!c.equals(cLastUpdate)) {
            updateAssets();
            cLastUpdate = c;
        }
    }

    public GameImage drawSliderCore(final int w, final int h) {
        final GameImage sliderCore = new GameImage(w, h);

        for (int x = 0; x < w; x++)
            sliderCore.fillRectangle(spectralFunction.apply(
                    getValueFromSliderFraction(x / (double) w)), x, 0, 1, h);

        return sliderCore.submit();
    }

    public Color getSliderBallCoreColor() {
        final GameImage core = new GameImage(1, 1);
        core.dot(Constants.ACCENT_BACKGROUND_LIGHT, 0, 0);
        core.dot(spectralFunction.apply(getValue()), 0, 0);
        core.free();

        return ImageProcessing.colorAtPixel(core, 0, 0);
    }
}
