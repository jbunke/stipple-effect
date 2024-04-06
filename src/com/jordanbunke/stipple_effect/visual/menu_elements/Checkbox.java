package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.button.MenuButtonStub;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Checkbox extends MenuButtonStub {
    private final Supplier<Boolean> getter;
    private final Consumer<Boolean> setter;

    private boolean isChecked;

    private final GameImage checked, checkedH, unchecked, uncheckedH;

    public Checkbox(
            final Coord2D position, final Anchor anchor,
            final Supplier<Boolean> getter, final Consumer<Boolean> setter
    ) {
        super(position, Layout.ICON_DIMS, anchor, true);

        this.getter = getter;
        this.setter = setter;

        isChecked = this.getter.get();

        checked = GraphicsUtils.drawCheckbox(false, true);
        unchecked = GraphicsUtils.drawCheckbox(false, false);
        checkedH = GraphicsUtils.drawCheckbox(true, true);
        uncheckedH = GraphicsUtils.drawCheckbox(true, false);
    }

    @Override
    public void execute() {
        setter.accept(!isChecked);
    }

    @Override
    public void update(final double deltaTime) {
        isChecked = getter.get();
    }

    @Override
    public void render(final GameImage canvas) {
        draw(isHighlighted()
                ? (isChecked ? checkedH : uncheckedH)
                : (isChecked ? checked : unchecked), canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
