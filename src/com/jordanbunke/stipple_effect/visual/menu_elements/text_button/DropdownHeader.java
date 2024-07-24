package com.jordanbunke.stipple_effect.visual.menu_elements.text_button;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.button.MenuButton;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

import java.util.function.Function;

public final class DropdownHeader extends MenuButton implements TextButton {
    private final String label;
    private final DropdownExpansion expansion;
    private final GameImage[][] imageMatrix;

    public DropdownHeader(
            final Coord2D position, final String label,
            final int width, final Runnable onClick,
            final DropdownExpansion expansion
    ) {
        super(position, new Bounds2D(width, Layout.STD_TEXT_BUTTON_H),
                Anchor.LEFT_TOP, true, onClick);

        this.label = label;
        this.expansion = expansion;

        imageMatrix = populateMatrix();
    }

    private GameImage[][] populateMatrix() {
        final int DIM = ImageMatrix.DIM;

        final GameImage[][] imageMatrix = new GameImage[DIM][DIM];

        for (int selected = 0; selected < DIM; selected++)
            for (int highlighted = 0; highlighted < DIM; highlighted++)
                imageMatrix[selected][highlighted] =
                        Settings.getTheme().logic.drawDropdownHeader(
                                sim(ImageMatrix.valueFromInt(selected),
                                        ImageMatrix.valueFromInt(highlighted)), expansion);

        return imageMatrix;
    }

    @Override
    public void update(final double deltaTime) {

    }

    @Override
    public void render(final GameImage canvas) {
        final Function<Boolean, Integer> fInd = ImageMatrix::indexFromValue;

        draw(imageMatrix[fInd.apply(isSelected())][fInd.apply(isHighlighted())], canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public Alignment getAlignment() {
        return Alignment.LEFT;
    }

    @Override
    public ButtonType getButtonType() {
        return ButtonType.DD_MENU_HEADER;
    }
}
