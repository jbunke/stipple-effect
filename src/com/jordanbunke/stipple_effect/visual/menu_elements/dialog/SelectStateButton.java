package com.jordanbunke.stipple_effect.visual.menu_elements.dialog;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.util.function.Supplier;

public class SelectStateButton extends SimpleMenuButton {
    private final Supplier<Boolean> selectableChecker;

    private final GameImage stub;

    private boolean selectable;

    private SelectStateButton(
            final Coord2D position, final Runnable onClick,
            final Supplier<Boolean> selectableChecker,
            final GameImage base, final GameImage highlighted,
            final GameImage stub
    ) {
        super(position, new Bounds2D(Layout.STD_TEXT_BUTTON_W,
                Layout.STD_TEXT_BUTTON_H), Anchor.LEFT_TOP,
                true, onClick, base, highlighted);

        this.stub = stub;
        this.selectableChecker = selectableChecker;

        update(0d);
    }

    public static SelectStateButton make(
            final Coord2D position, final Runnable onClick,
            final Supplier<Boolean> selectableChecker
    ) {
        final GameImage base = GraphicsUtils.drawTextButton(
                Layout.STD_TEXT_BUTTON_W, "Select", false),
                highlighted = GraphicsUtils.drawHighlightedButton(base),
                stub = GraphicsUtils.drawTextButton(
                        Layout.STD_TEXT_BUTTON_W, "Selected", false,
                        Settings.getTheme().stubButtonBody.get());

        return new SelectStateButton(position, onClick,
                selectableChecker, base, highlighted, stub);
    }

    @Override
    public void update(final double deltaTime) {
        selectable = selectableChecker.get();
    }

    @Override
    public void render(final GameImage canvas) {
        if (selectable)
            super.render(canvas);
        else
            draw(stub, canvas);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        if (selectable) super.process(eventLogger);
    }
}
