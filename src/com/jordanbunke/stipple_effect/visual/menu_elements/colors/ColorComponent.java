package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.MenuBuilder;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.container.MenuElementContainer;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.OnionSkin;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.visual.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;

import java.awt.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.jordanbunke.stipple_effect.utility.math.ColorMath.*;

public class ColorComponent extends MenuElementContainer {
    private final MenuElement[] menuElements;

    public static ColorComponent onionSkinHue(
            final Coord2D labelPos, final boolean back
    ) {
        final int scaleMax = Constants.HUE_SCALE;

        final Function<Integer, Color> fSpectral = i ->
                fromHSV(scaleDown(i, scaleMax), 1d, 1d, Constants.RGBA_SCALE);

        return new ColorComponent(scaleMax, "Hue", labelPos, fSpectral, c -> {
            final double hue = rgbToHue(c);

            if (back)
                OnionSkin.setDHueBack(hue);
            else
                OnionSkin.setDHueForward(hue);
        }, () -> scaleUp(back ? OnionSkin.getDHueBack()
                : OnionSkin.getDHueForward(), scaleMax));
    }

    public static ColorComponent hue(final Coord2D labelPos) {
        return rgbhsva(labelPos, "Hue",
                Constants.HUE_SCALE, LastHSVEdit.HUE,
                ColorMath::hueAdjustedColor, ColorMath::hueGetter);
    }

    public static ColorComponent sat(final Coord2D labelPos) {
        return rgbhsva(labelPos, "Saturation",
                Constants.SAT_SCALE, LastHSVEdit.SAT,
                ColorMath::satAdjustedColor, ColorMath::satGetter);
    }

    public static ColorComponent value(final Coord2D labelPos) {
        return rgbhsva(labelPos, "Value",
                Constants.VALUE_SCALE, LastHSVEdit.VAL,
                ColorMath::valueAdjustedColor, ColorMath::valueGetter);
    }

    public static ColorComponent red(final Coord2D labelPos) {
        return rgba(labelPos, "[R]ed", (r, c) ->
                new Color(r, c.getGreen(), c.getBlue(), c.getAlpha()),
                Color::getRed);
    }

    public static ColorComponent green(final Coord2D labelPos) {
        return rgba(labelPos, "[G]reen", (g, c) ->
                        new Color(c.getRed(), g, c.getBlue(), c.getAlpha()),
                Color::getGreen);
    }

    public static ColorComponent blue(final Coord2D labelPos) {
        return rgba(labelPos, "[B]lue", (b, c) ->
                        new Color(c.getRed(), c.getGreen(), b, c.getAlpha()),
                Color::getBlue);
    }

    public static ColorComponent alpha(final Coord2D labelPos) {
        return rgba(labelPos, "Alpha/Opacity", (a, c) ->
                        new Color(c.getRed(), c.getGreen(), c.getBlue(), a),
                Color::getAlpha);
    }

    private static ColorComponent rgba(
            final Coord2D labelPos, final String labelText,
            final BiFunction<Integer, Color, Color> fCMod,
            final Function<Color, Integer> componentGetter
    ) {
        return rgbhsva(labelPos, labelText,
                Constants.RGBA_SCALE, LastHSVEdit.NONE,
                fCMod, componentGetter);
    }

    private static ColorComponent rgbhsva(
            final Coord2D labelPos, final String labelText,
            final int scaleMax, final LastHSVEdit lastEdit,
            final BiFunction<Integer, Color, Color> fCMod,
            final Function<Color, Integer> componentGetter
    ) {
        final Function<Integer, Color> fSpectral = i -> {
            final Color c = StippleEffect.get().getSelectedColor();
            return fCMod.apply(i, c);
        };

        return new ColorComponent(
                scaleMax, labelText, labelPos, fSpectral,
                c -> StippleEffect.get().setSelectedColor(c, lastEdit),
                () -> componentGetter.apply(
                        StippleEffect.get().getSelectedColor()));
    }

    private ColorComponent(
            final int maxValue,
            final String labelText, final Coord2D labelPos,
            final Function<Integer, Color> spectralFunction,
            final Consumer<Color> setter, final Supplier<Integer> getter
    ) {
        super(new Coord2D(), new Bounds2D(1, 1), Anchor.LEFT_TOP, false);

        final MenuBuilder mb = new MenuBuilder();

        // label
        final TextLabel label = TextLabel.make(labelPos, labelText);
        mb.add(label);

        // value
        final int elementWidthAllowance =
                Layout.COLOR_COMP_W_ALLOWANCE - (2 * Layout.CONTENT_BUFFER_PX),
                valueX = labelPos.x + elementWidthAllowance;
        mb.add(DynamicLabel.make(new Coord2D(valueX, labelPos.y),
                Anchor.RIGHT_TOP, () -> String.valueOf(getter.get()),
                Layout.DYNAMIC_LABEL_W_ALLOWANCE));

        // increment and decrement buttons
        final Coord2D decPos = Layout.contentPositionAfterLabel(label);
        mb.add(IconButton.makeNoTooltip(ResourceCodes.DECREMENT,
                decPos,
                () -> setter.accept(spectralFunction.apply(
                        Math.max(getter.get() - 1, 0)))));
        mb.add(IconButton.makeNoTooltip(ResourceCodes.INCREMENT,
                decPos.displace(Layout.BUTTON_INC, 0),
                () -> setter.accept(spectralFunction.apply(
                        Math.min(getter.get() + 1, maxValue)))));

        // slider
        final Coord2D sliderPos = labelPos.displace(
                0, Layout.DIALOG_CONTENT_INC_Y);
        mb.add(new ColorSlider(sliderPos, elementWidthAllowance,
                0, maxValue, getter, spectralFunction,
                i -> setter.accept(spectralFunction.apply(i))));

        menuElements = mb.build().getMenuElements();
    }

    @Override
    public void update(final double deltaTime) {
        for (MenuElement menuElement : menuElements)
            menuElement.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        for (MenuElement menuElement : menuElements)
            menuElement.render(canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {
        for (MenuElement menuElement : menuElements)
            menuElement.debugRender(canvas, debugger);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        for (MenuElement menuElement : menuElements)
            menuElement.process(eventLogger);
    }

    @Override
    public MenuElement[] getMenuElements() {
        return menuElements;
    }

    @Override
    public boolean hasNonTrivialBehaviour() {
        return false;
    }
}
