package com.jordanbunke.stipple_effect.menu_elements.scrollable;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.button.MenuButton;
import com.jordanbunke.delta_time.utility.Coord2D;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class SelectableListItemButton extends MenuButton {
    private final GameImage baseImage, highlightedImage, selectedImage;

    private final Callable<Integer> selectedIndexGetter;

    private final int index;

    private boolean selected = false;

    public SelectableListItemButton(
            final Coord2D position, final Coord2D dimensions,
            final Anchor anchor, final boolean visible,
            final GameImage baseImage, final GameImage highlightedImage, final GameImage selectedImage,
            final int index,
            final Callable<Integer> selectedIndexGetter,
            final Consumer<Integer> selectFunction
    ) {
        super(position, dimensions, anchor, visible, () -> selectFunction.accept(index));

        this.baseImage = baseImage;
        this.highlightedImage = highlightedImage;
        this.selectedImage = selectedImage;

        this.selectedIndexGetter = selectedIndexGetter;
        this.index = index;

        updateSelection();
    }

    private void updateSelection() {
        try {
            selected = selectedIndexGetter.call() == index;
        } catch (Exception e) {
            GameError.send("Couldn't fetch selected index for list item button; " +
                    "button not selected implicitly");
            selected = false;
        }
    }

    @Override
    public void update(final double deltaTime) {
        updateSelection();
    }

    @Override
    public void render(final GameImage canvas) {
        draw(isHighlighted() ? highlightedImage
                : (selected ? selectedImage : baseImage),
                canvas);
    }

    @Override
    public void debugRender(GameImage canvas, GameDebugger debugger) {

    }
}
