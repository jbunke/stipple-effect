package com.jordanbunke.stipple_effect.visual.menu_elements.text_button;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.button.MenuButton;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

import java.util.function.Function;

public abstract class AbstractTextButton extends MenuButton implements TextButton {
    private final Alignment alignment;
    private final ButtonType buttonType;

    private final GameImage[][] imageMatrix;

    public AbstractTextButton(
            final Coord2D position, final int width,
            final Runnable onClick,
            final Alignment alignment,
            final ButtonType buttonType
    ) {
        super(position, new Bounds2D(width, Layout.STD_TEXT_BUTTON_H),
                Anchor.LEFT_TOP, true, onClick);

        this.alignment = alignment;
        this.buttonType = buttonType;

        this.imageMatrix = new GameImage[ImageMatrix.DIM][ImageMatrix.DIM];
    }

    protected final void populateMatrix() {
        final Function<Integer, Boolean> fVal = ImageMatrix::valueFromInt;

        for (int selected = 0; selected < ImageMatrix.DIM; selected++)
            for (int highlighted = 0; highlighted < ImageMatrix.DIM; highlighted++)
                imageMatrix[selected][highlighted] =
                        Settings.getTheme().logic.drawTextButton(
                                sim(fVal.apply(selected), fVal.apply(highlighted)));
    }

    @Override
    public final void render(final GameImage canvas) {
        final Function<Boolean, Integer> fInd = ImageMatrix::indexFromValue;

        draw(imageMatrix[fInd.apply(isSelected())]
                [fInd.apply(isHighlighted())], canvas);
    }

    @Override
    public final void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    @Override
    public final Alignment getAlignment() {
        return alignment;
    }

    @Override
    public final ButtonType getButtonType() {
        return buttonType;
    }
}
