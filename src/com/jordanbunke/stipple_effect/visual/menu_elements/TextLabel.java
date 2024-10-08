package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.theme.logic.ThemeLogic;

import java.awt.*;

public class TextLabel extends StaticMenuElement {
    private TextLabel(
            final Coord2D position, final GameImage image, final Anchor anchor
    ) {
        super(position, new Bounds2D(image.getWidth(), image.getHeight()),
                anchor, image);
    }

    public static TextLabel make(
            final Coord2D position, final String text
    ) {
        return make(position, text, ThemeLogic.intuitTextColor(
                Settings.getTheme().panelBackground, true));
    }

    public static TextLabel make(
            final Coord2D position, final String text, final Color c
    ) {
        return make(position, text, c, 1d);
    }

    public static TextLabel make(
            final Coord2D position, final String text,
            final Color c, final double textSize
    ) {
        return make(position, text, c, textSize, Anchor.LEFT_TOP);
    }

    public static TextLabel make(
            final Coord2D position, final String text,
            final Color c, final double textSize,
            final Anchor anchor
    ) {
        final GameImage label = GraphicsUtils.uiText(c, textSize)
                .addText(text).build().draw();
        return new TextLabel(position, label, anchor);
    }
}
