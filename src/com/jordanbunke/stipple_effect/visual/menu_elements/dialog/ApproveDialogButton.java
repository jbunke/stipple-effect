package com.jordanbunke.stipple_effect.visual.menu_elements.dialog;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.button.MenuButtonStub;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.Permissions;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.SECursor;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.Alignment;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.TextButton;
import com.jordanbunke.stipple_effect.visual.theme.logic.ThemeLogic;

import java.util.function.Supplier;

public class ApproveDialogButton extends MenuButtonStub {
    private final Supplier<Boolean> precondition;
    private final Runnable onApproval;

    private final GameImage base, notMet, highlighted;
    private final boolean clearDialog;

    private boolean met;

    public ApproveDialogButton(
            final Coord2D position,
            final Runnable onApproval, final boolean clearDialog,
            final Supplier<Boolean> precondition, final String text
    ) {
        super(position, new Bounds2D(
                Layout.STD_TEXT_BUTTON_W, Layout.STD_TEXT_BUTTON_H
        ), Anchor.LEFT_TOP, true);

        final ThemeLogic tl = Settings.getTheme().logic;
        final TextButton tmpl = TextButton.of(text, Layout.STD_TEXT_BUTTON_W);

        base = tl.drawTextButton(tmpl);
        highlighted = tl.drawTextButton(tmpl.sim(false, true));
        notMet = tl.drawTextButton(TextButton.of(text,
                Layout.STD_TEXT_BUTTON_W, Alignment.CENTER, ButtonType.STUB));

        this.clearDialog = clearDialog;

        this.precondition = precondition;
        this.onApproval = onApproval;

        checkPrecondition();
    }

    @Override
    public void update(final double deltaTime) {
        checkPrecondition();
    }

    private void checkPrecondition() {
        met = precondition.get();
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        super.process(eventLogger);

        if (met && isHighlighted())
            SECursor.clickableElement();

        if (Permissions.isTyping())
            return;

        if (!(eventLogger.isPressed(Key.CTRL) ||
                eventLogger.isPressed(Key.SHIFT)))
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ENTER,
                            GameKeyEvent.Action.PRESS), this::execute);
    }

    @Override
    public void render(final GameImage canvas) {
        draw(met ? (isHighlighted() ? highlighted : base) : notMet, canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    @Override
    public void execute() {
        if (met) {
            onApproval.run();

            if (clearDialog)
                StippleEffect.get().clearDialog();
        }
    }
}
