package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

public class IconButton extends SimpleMenuButton {
    private final String code;

    public IconButton(
            final String code, final Coord2D position,
            final Runnable onClick,
            final GameImage icon, final GameImage highlighted
    ) {
        super(position, Layout.ICON_DIMS, Anchor.LEFT_TOP,
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

    public static IconButton makeNoTooltip(
            final String code, final Coord2D position, final Runnable onClick
    ) {
        final GameImage icon = GraphicsUtils.loadIcon(code);

        return new IconButton(IconCodes.NO_TOOLTIP, position, onClick,
                icon, GraphicsUtils.highlightIconButton(icon));
    }

    @Override
    public void update(final double deltaTime) {
        if (isHighlighted() && !code.equals(IconCodes.NO_TOOLTIP))
            StippleEffect.get().sendToolTipUpdate(code);
    }
}
