package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

public class IconButton extends SimpleMenuButton {
    private final String code;

    public IconButton(
            final String code, final Coord2D position,
            final Runnable onClick,
            final GameImage icon, final GameImage highlighted
    ) {
        super(position, Constants.ICON_DIMS, Anchor.LEFT_TOP,
                true, onClick, icon, highlighted);

        this.code = code;
    }

    public static IconButton make(
            final String code, final Coord2D position, final Runnable onClick
    ) {
        final GameImage icon = GraphicsUtils.loadIcon(code);

        return new IconButton(code, position, onClick,
                icon, GraphicsUtils.highlightIconButton(icon));
    }

    @Override
    public void update(final double deltaTime) {
        if (isHighlighted())
            StippleEffect.get().sendToolTipUpdate(code);
    }
}
