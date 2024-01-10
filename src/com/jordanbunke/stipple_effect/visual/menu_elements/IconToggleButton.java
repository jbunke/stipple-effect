package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

public class IconToggleButton extends SimpleToggleMenuButton {
    private final Function<Integer, String> iconCodeFunction;

    public IconToggleButton(
            Coord2D position, GameImage[] nonHighlightedImages,
            GameImage[] highlightedImages, Runnable[] chosenBehaviours,
            Supplier<Integer> updateIndexLogic, Runnable globalBehaviour,
            Function<Integer, String> iconCodeFunction
    ) {
        super(position, Constants.ICON_DIMS, Anchor.LEFT_TOP, true,
                nonHighlightedImages, highlightedImages, chosenBehaviours,
                updateIndexLogic, globalBehaviour);

        this.iconCodeFunction = iconCodeFunction;
    }

    public static IconToggleButton make(
            final Coord2D position, final String[] codes,
            final Runnable[] chosenBehaviours,
            final Supplier<Integer> updateIndexLogic,
            final Runnable globalBehaviour,
            final Function<Integer, String> iconCodeFunction
    ) {
        final GameImage[] icons = Arrays.stream(codes).
                map(GraphicsUtils::loadIcon).toArray(GameImage[]::new);

        return new IconToggleButton(position, icons,
                Arrays.stream(icons).map(GraphicsUtils::highlightIconButton).
                        toArray(GameImage[]::new),
                chosenBehaviours, updateIndexLogic,
                globalBehaviour, iconCodeFunction);
    }

    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);

        if (isHighlighted())
            StippleEffect.get().sendToolTipUpdate(
                    iconCodeFunction.apply(getIndex()));
    }
}
