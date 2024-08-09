package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.Permissions;
import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;
import com.jordanbunke.stipple_effect.utility.action.SEAction;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

public class IconButton extends SimpleMenuButton {
    private final String code;

    public IconButton(
            final Coord2D position, final Anchor anchor,
            final Runnable onClick, final String code,
            final GameImage icon, final GameImage highlighted
    ) {
        super(position, Layout.ICON_DIMS, anchor,
                true, onClick, icon, highlighted);

        this.code = code;
    }

    public static IconButton fromAction(
            final Coord2D position, final Anchor anchor,
            final SEAction action, final SEContext c
    ) {
        final GameImage icon = GraphicsUtils.loadIcon(action.code);

        return new IconButton(position, anchor,
                () -> action.behaviour.accept(c), action.code,
                icon, GraphicsUtils.highlightIconButton(icon));
    }

    public static IconButton make(
            final String code, final Coord2D position, final Runnable onClick
    ) {
        final GameImage icon = GraphicsUtils.loadIcon(code);

        return new IconButton(position, Anchor.LEFT_TOP, onClick,
                code, icon, GraphicsUtils.highlightIconButton(icon));
    }

    public static IconButton makeNoTooltip(
            final String code, final Coord2D position, final Runnable onClick
    ) {
        final GameImage icon = GraphicsUtils.loadIcon(code);

        return new IconButton(position, Anchor.LEFT_TOP, onClick,
                ResourceCodes.NO_TOOLTIP, icon, GraphicsUtils.highlightIconButton(icon));
    }

    @Override
    public void update(final double deltaTime) {
        if (isHighlighted() && Permissions.isCursorFree() &&
                !code.equals(ResourceCodes.NO_TOOLTIP))
            StippleEffect.get().sendToolTipUpdate(code);
    }
}
