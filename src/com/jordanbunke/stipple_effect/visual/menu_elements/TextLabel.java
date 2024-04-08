package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.awt.*;

public class TextLabel extends StaticMenuElement {
    private TextLabel(final Coord2D position, final GameImage image) {
        super(position, new Coord2D(image.getWidth(), image.getHeight()),
                Anchor.LEFT_TOP, image);
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
        final GameImage label = GraphicsUtils.uiText(c, textSize).addText(text).build().draw();
        return new TextLabel(position, label);
    }
}
