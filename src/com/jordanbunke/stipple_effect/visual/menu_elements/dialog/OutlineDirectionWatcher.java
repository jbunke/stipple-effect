package com.jordanbunke.stipple_effect.visual.menu_elements.dialog;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.button.MenuButtonStub;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.selection.Outliner;
import com.jordanbunke.stipple_effect.utility.DialogVals;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

public final class OutlineDirectionWatcher extends MenuButtonStub {
    private final Outliner.Direction direction;

    private int lastValue;
    private GameImage image, highlighted;

    public OutlineDirectionWatcher(
            final Coord2D position, final Outliner.Direction direction
    ) {
        super(position, Layout.OUTLINE_BUTTON_DIMS, Anchor.CENTRAL, true);

        this.direction = direction;
        lastValue = DialogVals.getThisOutlineSide(this.direction.ordinal());

        updateImage();
    }

    @Override
    public void execute() {
        DialogVals.setThisOutlineSide(direction.ordinal(), 0);
    }

    @Override
    public void update(final double deltaTime) {
        final int value = DialogVals.getThisOutlineSide(direction.ordinal());

        if (value != lastValue) {
            lastValue = value;

            updateImage();
        }
    }

    private void updateImage() {
        final String code = lastValue == 0 ? IconCodes.NO_OUTLINE
                : (lastValue > 0
                        ? IconCodes.OUTLINE_PREFIX +
                            direction.name().toLowerCase()
                        : IconCodes.OUTLINE_PREFIX +
                            direction.opposite().name().toLowerCase());

        image = GraphicsUtils.loadIcon(code);

        highlighted = GraphicsUtils.highlightButton(
                GraphicsUtils.loadIcon(IconCodes.NO_OUTLINE));
    }

    @Override
    public void render(final GameImage canvas) {
        draw(isHighlighted() ? highlighted : image, canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {}
}
