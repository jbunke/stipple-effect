package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.utility.Coord2D;

import java.awt.*;

public class MenuHelper {
    public static SimpleMenuButton makeTextButton(
            final Coord2D position, final int width, final String text, final Runnable behaviour,
            final boolean isSelected, final Color backgroundColor
    ) {
        final int height = Constants.STD_TEXT_BUTTON_H;

        final Color textColor = (backgroundColor.getRed() + backgroundColor.getGreen() +
                backgroundColor.getBlue()) / 3 > 127
                ? Constants.BLACK : Constants.WHITE;

        final GameImage nhi = new GameImage(width, height);
        nhi.fillRectangle(backgroundColor, 0, 0, width, height);
        final GameImage textImage = GraphicsUtils.uiText(textColor)
                .addText(text).build().draw();
        nhi.draw(textImage, (width - textImage.getWidth()) / 2, Constants.TEXT_Y_OFFSET);
        final Color frame = isSelected ? Constants.HIGHLIGHT_1 : Constants.BLACK;
        nhi.drawRectangle(frame, 2f * Constants.BUTTON_BORDER_PX, 0, 0, width, height);

        final GameImage hi = new GameImage(nhi);
        hi.fillRectangle(Constants.HIGHLIGHT_2, 0, 0, width, height);
        hi.drawRectangle(Constants.BLACK, 2f * Constants.BUTTON_BORDER_PX, 0, 0, width, height);

        return new SimpleMenuButton(position, new Coord2D(width, height),
                MenuElement.Anchor.LEFT_TOP, true, behaviour,
                nhi.submit(), hi.submit());
    }
}
