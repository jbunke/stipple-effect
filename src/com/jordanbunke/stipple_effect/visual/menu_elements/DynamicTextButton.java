package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.button.MenuButton;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.util.function.Supplier;

public class DynamicTextButton extends MenuButton {
    private final Supplier<String> getter;
    private String text;

    private GameImage baseImage, highlightedImage;

    public DynamicTextButton(
            final Coord2D position, final int width,
            final Anchor anchor, final Runnable onClick,
            final Supplier<String> getter
    ) {
        super(position, new Bounds2D(width, Layout.STD_TEXT_BUTTON_H),
                anchor, true, onClick);

        this.getter = getter;
        text = getter.get();

        updateAssets();
    }

    @Override
    public void update(final double deltaTime) {
        final String fetched = getter.get();

        if (!text.equals(fetched)) {
            text = fetched;
            updateAssets();
        }
    }

    @Override
    public void render(final GameImage canvas) {
        draw(isHighlighted() ? highlightedImage : baseImage, canvas);
    }

    @Override
    public void debugRender(GameImage canvas, GameDebugger debugger) {

    }

    private void updateAssets() {
        baseImage = GraphicsUtils.drawTextButton(getWidth(), text, false);
        highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage);
    }
}
