package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.setting_group.DoubleToolSettingType;
import com.jordanbunke.stipple_effect.utility.setting_group.IntToolSettingType;
import com.jordanbunke.stipple_effect.utility.setting_group.ToolSettingType;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalSlider;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ToolOptionIncrementalRange<R extends Number> {
    public final IconButton decButton, incButton;
    public final HorizontalSlider slider;
    public final DynamicLabel value;

    public ToolOptionIncrementalRange(
            final TextLabel label,
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

        decButton = makeDecrement(label, fDecrement);
        incButton = makeIncrement(fIncrement);
        slider = makeSlider(minimum, maximum, setter, getter,
                toSliderConversion, fromSliderConversion);
        value = makeValue(getter, valueFormatter, widestTextCase);

        slider.updateAssets();
    }

    private IconButton makeDecrement(
            final TextLabel label, final Runnable fDecrement
    ) {
        return IconButton.makeNoTooltip(IconCodes.DECREMENT, new Coord2D(
                Layout.optionsBarNextElementX(label, false),
                Layout.optionsBarButtonY()), fDecrement);
    }

    private IconButton makeIncrement(
            final Runnable fIncrement
    ) {
        return IconButton.makeNoTooltip(IconCodes.INCREMENT, new Coord2D(
                Layout.optionsBarNextButtonX(decButton),
                Layout.optionsBarButtonY()), fIncrement);
    }

    private HorizontalSlider makeSlider(
            final R minimum, final R maximum,
            final Consumer<R> setter, final Supplier<R> getter,
            final Function<R, Integer> toSliderConversion,
            final Function<Integer, R> fromSliderConversion
    ) {
        return new HorizontalSlider(new Coord2D(
                Layout.optionsBarNextButtonX(incButton),
                Layout.optionsBarButtonY()),
                Layout.optionsBarSliderWidth(), MenuElement.Anchor.LEFT_TOP,
                toSliderConversion.apply(minimum), toSliderConversion.apply(maximum),
                () -> toSliderConversion.apply(getter.get()),
                i -> setter.accept(fromSliderConversion.apply(i)), false);
    }

    private DynamicLabel makeValue(
            final Supplier<R> getter,
            final Function<R, String> valueFormatter,
            final String widestTextCase
    ) {
        return new DynamicLabel(new Coord2D(
                Layout.optionsBarNextElementX(slider, false),
                Layout.optionsBarTextY()),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                () -> valueFormatter.apply(getter.get()),
                Layout.estimateDynamicLabelMaxWidth(widestTextCase));
    }

    public static ToolOptionIncrementalRange<Integer> makeForInt(
            final TextLabel label,
            final int unit, final int minimum, final int maximum,
            final Consumer<Integer> setter, final Supplier<Integer> getter,
            final Function<Integer, Integer> toSliderConversion,
            final Function<Integer, Integer> fromSliderConversion,
            final Function<Integer, String> valueFormatter,
            final String widestTextCase
    ) {
        return new ToolOptionIncrementalRange<>(
                label, null, null,
                unit, minimum, maximum, IntToolSettingType.get(),
                setter, getter, toSliderConversion, fromSliderConversion,
                valueFormatter, widestTextCase);
    }

    public static ToolOptionIncrementalRange<Double> makeForDouble(
            final TextLabel label,
            final Runnable fDecrement, final Runnable fIncrement,
            final double minimum, final double maximum,
            final Consumer<Double> setter, final Supplier<Double> getter,
            final Function<Double, Integer> toSliderConversion,
            final Function<Integer, Double> fromSliderConversion,
            final Function<Double, String> valueFormatter,
            final String widestTextCase
    ) {
        return new ToolOptionIncrementalRange<>(
                label, fDecrement, fIncrement,
                null, minimum, maximum, DoubleToolSettingType.get(),
                setter, getter, toSliderConversion, fromSliderConversion,
                valueFormatter, widestTextCase);
    }
}
