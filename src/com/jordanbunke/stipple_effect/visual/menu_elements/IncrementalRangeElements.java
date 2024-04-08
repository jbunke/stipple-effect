package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.setting_group.DoubleToolSettingType;
import com.jordanbunke.stipple_effect.utility.setting_group.FloatToolSettingType;
import com.jordanbunke.stipple_effect.utility.setting_group.IntToolSettingType;
import com.jordanbunke.stipple_effect.utility.setting_group.ToolSettingType;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalSlider;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class IncrementalRangeElements<R extends Number> {
    public final IconButton decButton, incButton;
    public final HorizontalSlider slider;
    public final DynamicLabel value;

    public IncrementalRangeElements(
            final MenuElement preceding, final int buttonY, final int textY,
            Runnable fDecrement, Runnable fIncrement,
            final R unit, final R minimum, final R maximum,
            final ToolSettingType<R> settingType,
            final Consumer<R> setter, final Supplier<R> getter,
            final Function<R, Integer> toSliderConversion,
            final Function<Integer, R> fromSliderConversion,
            final Function<R, String> valueFormatter,
            final String widestTextCase
    ) {
        if (fDecrement == null)
            fDecrement = () -> setter.accept(settingType.subtraction().apply(getter.get(), unit));

        if (fIncrement == null)
            fIncrement = () -> setter.accept(settingType.addition().apply(getter.get(), unit));

        decButton = makeDecrement(preceding, buttonY, fDecrement);
        incButton = makeIncrement(buttonY, fIncrement);
        slider = makeSlider(buttonY, minimum, maximum, setter, getter,
                toSliderConversion, fromSliderConversion);
        value = makeValue(textY, getter, valueFormatter, widestTextCase);

        slider.updateAssets();
    }

    private IconButton makeDecrement(
            final MenuElement preceding, final int buttonY,
            final Runnable fDecrement
    ) {
        return IconButton.makeNoTooltip(IconCodes.DECREMENT, new Coord2D(
                Layout.optionsBarNextElementX(preceding, false),
                buttonY), fDecrement);
    }

    private IconButton makeIncrement(
            final int buttonY, final Runnable fIncrement
    ) {
        return IconButton.makeNoTooltip(IconCodes.INCREMENT, new Coord2D(
                Layout.optionsBarNextButtonX(decButton), buttonY),
                fIncrement);
    }

    private HorizontalSlider makeSlider(
            final int buttonY, final R minimum, final R maximum,
            final Consumer<R> setter, final Supplier<R> getter,
            final Function<R, Integer> toSliderConversion,
            final Function<Integer, R> fromSliderConversion
    ) {
        return new HorizontalSlider(new Coord2D(
                Layout.optionsBarNextButtonX(incButton), buttonY),
                Layout.optionsBarSliderWidth(), MenuElement.Anchor.LEFT_TOP,
                toSliderConversion.apply(minimum), toSliderConversion.apply(maximum),
                () -> toSliderConversion.apply(getter.get()),
                i -> setter.accept(fromSliderConversion.apply(i)), false);
    }

    private DynamicLabel makeValue(
            final int textY, final Supplier<R> getter,
            final Function<R, String> valueFormatter,
            final String widestTextCase
    ) {
        return new DynamicLabel(new Coord2D(
                Layout.optionsBarNextElementX(slider, false),
                textY), MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                () -> valueFormatter.apply(getter.get()), widestTextCase);
    }

    public static IncrementalRangeElements<Integer> makeForInt(
            final MenuElement preceding, final int buttonY, final int textY,
            final int unit, final int minimum, final int maximum,
            final Consumer<Integer> setter, final Supplier<Integer> getter,
            final Function<Integer, Integer> toSliderConversion,
            final Function<Integer, Integer> fromSliderConversion,
            final Function<Integer, String> valueFormatter,
            final String widestTextCase
    ) {
        return new IncrementalRangeElements<>(
                preceding, buttonY, textY, null, null,
                unit, minimum, maximum, IntToolSettingType.get(),
                setter, getter, toSliderConversion, fromSliderConversion,
                valueFormatter, widestTextCase);
    }

    public static IncrementalRangeElements<Double> makeForDouble(
            final MenuElement preceding, final int buttonY, final int textY,
            final Runnable fDecrement, final Runnable fIncrement,
            final double minimum, final double maximum,
            final Consumer<Double> setter, final Supplier<Double> getter,
            final Function<Double, Integer> toSliderConversion,
            final Function<Integer, Double> fromSliderConversion,
            final Function<Double, String> valueFormatter,
            final String widestTextCase
    ) {
        return new IncrementalRangeElements<>(
                preceding, buttonY, textY, fDecrement, fIncrement,
                null, minimum, maximum, DoubleToolSettingType.get(),
                setter, getter, toSliderConversion, fromSliderConversion,
                valueFormatter, widestTextCase);
    }

    public static IncrementalRangeElements<Float> makeForFloat(
            final MenuElement preceding, final int buttonY, final int textY,
            final Runnable fDecrement, final Runnable fIncrement,
            final float minimum, final float maximum,
            final Consumer<Float> setter, final Supplier<Float> getter,
            final Function<Float, Integer> toSliderConversion,
            final Function<Integer, Float> fromSliderConversion,
            final Function<Float, String> valueFormatter,
            final String widestTextCase
    ) {
        return new IncrementalRangeElements<>(
                preceding, buttonY, textY, fDecrement, fIncrement,
                null, minimum, maximum, FloatToolSettingType.get(),
                setter, getter, toSliderConversion, fromSliderConversion,
                valueFormatter, widestTextCase);
    }
}
