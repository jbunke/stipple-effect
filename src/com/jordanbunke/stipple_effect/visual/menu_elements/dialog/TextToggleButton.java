package com.jordanbunke.stipple_effect.visual.menu_elements.dialog;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.util.Arrays;
import java.util.function.Supplier;

public final class TextToggleButton extends SimpleToggleMenuButton {
    private TextToggleButton(
            final Coord2D position,
            final GameImage[] bases, final GameImage[] highlights,
            final Runnable[] chosenBehaviours,
            final Supplier<Integer> updateIndexLogic,
            final Runnable globalBehaviour
    ) {
        super(position, new Bounds2D(Layout.STD_TEXT_BUTTON_W,
                Layout.STD_TEXT_BUTTON_H), Anchor.LEFT_TOP, true,
                bases, highlights, chosenBehaviours,
                updateIndexLogic, globalBehaviour);
    }

    public static TextToggleButton make(
            final Coord2D position, final String[] texts,
            final Runnable[] chosenBehaviours,
            final Supplier<Integer> updateIndexLogic,
            final Runnable globalBehaviour
    ) {
        final GameImage[] bases = makeToggleButtonSet(texts),
                highlights = Arrays.stream(bases)
                        .map(GraphicsUtils::drawHighlightedButton)
                        .toArray(GameImage[]::new);

        return new TextToggleButton(position, bases, highlights,
                chosenBehaviours, updateIndexLogic, globalBehaviour);
    }

    private static GameImage[] makeToggleButtonSet(final String... buttonTexts) {
        return Arrays.stream(buttonTexts)
                .map(t -> GraphicsUtils.drawTextButton(
                        Layout.STD_TEXT_BUTTON_W, t, false))
                .toArray(GameImage[]::new);
    }
}
