package com.jordanbunke.stipple_effect.visual.menu_elements.dialog;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.button.MenuButtonStub;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.util.function.Supplier;

public class ApproveDialogButton extends MenuButtonStub {
    private final Supplier<Boolean> precondition;
    private final Runnable onApproval;

    private final GameImage base, notMet, highlighted;
    private final boolean clearDialog;

    private boolean met;

    public ApproveDialogButton(
            final Coord2D position, final Coord2D dimensions, final Anchor anchor,
            final Runnable onApproval, final boolean clearDialog,
            final Supplier<Boolean> precondition, final String text
    ) {
        super(position, dimensions, anchor, true);

        base = GraphicsUtils.drawTextButton(dimensions.x, text, false);
        highlighted = GraphicsUtils.drawHighlightedButton(base);
        notMet = GraphicsUtils.drawTextButton(
                dimensions.x, text, false,
                Settings.getTheme().getStubButtonBody());

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
