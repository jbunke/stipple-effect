package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.button.MenuButton;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SelectableListItemButton extends MenuButton {
    private final GameImage baseImage, highlightedImage, selectedImage;

    private final Supplier<Integer> selectedIndexGetter;

    private final int index;

    private boolean selected = false;

    public SelectableListItemButton(
            final Coord2D position, final Bounds2D dimensions, final Anchor anchor,
            final GameImage baseImage, final GameImage highlightedImage, final GameImage selectedImage,
            final int index, final Supplier<Integer> selectedIndexGetter,
            final Consumer<Integer> selectFunction
    ) {
        super(position, dimensions, anchor, true, () -> selectFunction.accept(index));

        this.baseImage = baseImage;
        this.highlightedImage = highlightedImage;
        this.selectedImage = selectedImage;

        this.selectedIndexGetter = selectedIndexGetter;
        this.index = index;

        updateSelection();
    }

    private void updateSelection() {
        selected = selectedIndexGetter.get() == index;
    }

    @Override
    public void update(final double deltaTime) {
        updateSelection();
    }

    @Override
    public void render(final GameImage canvas) {
        draw(selected ? selectedImage
                : (isHighlighted() ? highlightedImage : baseImage),
                canvas);
    }

    @Override
    public void debugRender(GameImage canvas, GameDebugger debugger) {

    }

    @Override
    public boolean isSelected() {
        return selected;
    }
}
