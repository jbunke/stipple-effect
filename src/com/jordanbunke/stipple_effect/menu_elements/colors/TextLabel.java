package com.jordanbunke.stipple_effect.menu_elements.colors;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.awt.*;

public class TextLabel extends StaticMenuElement {
    private TextLabel(final Coord2D position, final GameImage image) {
        super(position, new Coord2D(image.getWidth(), image.getHeight()),
                Anchor.LEFT_TOP, image);
    }

    public static TextLabel make(final Coord2D position, final String text, final Color c) {
        final GameImage label = GraphicsUtils.uiText(c).addText(text).build().draw();
        return new TextLabel(position, label);
    }
}
