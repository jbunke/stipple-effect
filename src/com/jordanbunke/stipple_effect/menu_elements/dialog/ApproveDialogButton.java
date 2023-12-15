package com.jordanbunke.stipple_effect.menu_elements.dialog;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.button.MenuButtonStub;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.util.concurrent.Callable;

public class ApproveDialogButton extends MenuButtonStub {
    private final Callable<Boolean> precondition;
    private final Runnable onApproval;

    private final GameImage base, notMet, highlighted;

    private boolean met;

    public ApproveDialogButton(
            final Coord2D position, final Coord2D dimensions,
            final Anchor anchor, final Runnable onApproval,
            final Callable<Boolean> precondition, final String text
    ) {
        super(position, dimensions, anchor, true);

        base = GraphicsUtils.drawTextButton(dimensions.x, text,
                false, Constants.GREY);
        highlighted = GraphicsUtils.drawHighlightedButton(base);
        notMet = GraphicsUtils.drawTextButton(dimensions.x, text,
                false, Constants.ACCENT_BACKGROUND_DARK);

        this.precondition = precondition;
        this.onApproval = onApproval;

        checkPrecondition();
    }

    @Override
    public void update(final double deltaTime) {
        checkPrecondition();
    }

    private void checkPrecondition() {
        try {
            met = precondition.call();
        } catch (Exception e) {
            met = false;
        }
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
            StippleEffect.get().clearDialog();
        }
    }
}
