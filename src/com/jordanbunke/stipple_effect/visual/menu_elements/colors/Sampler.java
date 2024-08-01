package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.MenuBuilder;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.InvisibleMenuElement;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;

import java.awt.*;

public class Sampler extends InvisibleMenuElement {
    private MenuElement[] contents;
    private int lastIndex;

    public Sampler() {
        super();

        lastIndex = StippleEffect.get().getColorIndex();
        contents = makeContents();
    }

    @Override
    public void update(final double deltaTime) {
        final int index = StippleEffect.get().getColorIndex();

        if (index != lastIndex) {
            contents = makeContents();
            lastIndex = index;
        }

        for (MenuElement element : contents)
            element.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        for (MenuElement element : contents)
            element.render(canvas);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        for (MenuElement element : contents)
            element.process(eventLogger);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    private static MenuElement[] makeContents() {
        final MenuBuilder mb = new MenuBuilder();

        // red
        mb.add(new ColorComponent(0, Constants.RGBA_SCALE, "R",
                ColorComponent.Alignment.LEFT, 0, r -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(r, c.getGreen(), c.getBlue(), c.getAlpha());
                }, c -> StippleEffect.get().setSelectedColor(c, ColorMath.LastHSVEdit.NONE),
                () -> StippleEffect.get().getSelectedColor().getRed()));
        // green
        mb.add(new ColorComponent(0, Constants.RGBA_SCALE, "G",
                ColorComponent.Alignment.LEFT, 1, g -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(c.getRed(), g, c.getBlue(), c.getAlpha());
                }, c -> StippleEffect.get().setSelectedColor(c, ColorMath.LastHSVEdit.NONE),
                () -> StippleEffect.get().getSelectedColor().getGreen()));
        // blue
        mb.add(new ColorComponent(0, Constants.RGBA_SCALE, "B",
                ColorComponent.Alignment.LEFT, 2, b -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(c.getRed(), c.getGreen(), b, c.getAlpha());
                }, c -> StippleEffect.get().setSelectedColor(c, ColorMath.LastHSVEdit.NONE),
                () -> StippleEffect.get().getSelectedColor().getBlue()));
        // hue
        mb.add(new ColorComponent(0, Constants.HUE_SCALE, "H",
                ColorComponent.Alignment.RIGHT, 0,
                h -> ColorMath.hueAdjustedColor(h, StippleEffect.get().getSelectedColor()),
                c -> StippleEffect.get().setSelectedColor(c, ColorMath.LastHSVEdit.HUE),
                () -> ColorMath.hueGetter(StippleEffect.get().getSelectedColor())));
        // saturation
        mb.add(new ColorComponent(0, Constants.SAT_SCALE, "S",
                ColorComponent.Alignment.RIGHT, 1,
                s -> ColorMath.satAdjustedColor(s, StippleEffect.get().getSelectedColor()),
                c -> StippleEffect.get().setSelectedColor(c, ColorMath.LastHSVEdit.SAT),
                () -> ColorMath.satGetter(StippleEffect.get().getSelectedColor())));
        // value
        mb.add(new ColorComponent(0, Constants.VALUE_SCALE, "V",
                ColorComponent.Alignment.RIGHT, 2,
                v -> ColorMath.valueAdjustedColor(v, StippleEffect.get().getSelectedColor()),
                c -> StippleEffect.get().setSelectedColor(c, ColorMath.LastHSVEdit.VAL),
                () -> ColorMath.valueGetter(StippleEffect.get().getSelectedColor())));
        // alpha
        mb.add(new ColorComponent(0, Constants.RGBA_SCALE, "A (Opacity)",
                ColorComponent.Alignment.FULL, 3, a -> {
                    final Color c = StippleEffect.get().getSelectedColor();
                    return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
                }, c -> StippleEffect.get().setSelectedColor(c, ColorMath.LastHSVEdit.NONE),
                () -> StippleEffect.get().getSelectedColor().getAlpha()));

        return mb.build().getMenuElements();
    }
}
