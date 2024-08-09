package com.jordanbunke.stipple_effect.visual.menu_elements.dialog;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.Alignment;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.TextButton;
import com.jordanbunke.stipple_effect.visual.theme.logic.ThemeLogic;

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
        final ThemeLogic tl = Settings.getTheme().logic;
        final int width = Layout.STD_TEXT_BUTTON_W;

        final TextButton tmpl = TextButton.of("Select", width);

        final GameImage base = tl.drawTextButton(tmpl),
                highlighted = tl.drawTextButton(tmpl.sim(false, true)),
                stub = tl.drawTextButton(TextButton.of("Selected",
                        width, Alignment.CENTER, ButtonType.STUB));

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
