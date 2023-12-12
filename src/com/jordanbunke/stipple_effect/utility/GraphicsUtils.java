package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.stipple_effect.text.SEFonts;

import java.awt.*;

public class GraphicsUtils {
    public static TextBuilder uiText() {
        return uiText(Constants.WHITE);
    }

    public static TextBuilder uiText(final Color color) {
        return uiText(color, 1d);
    }

    public static TextBuilder uiText(final Color color, final double textSize) {
        return new TextBuilder(textSize, Text.Orientation.CENTER, color, SEFonts.CLASSIC.getStandard());
    }

    public static Color buttonBorderColor(final boolean selected) {
        return selected ? Constants.HIGHLIGHT_1 : Constants.BLACK;
    }

    public static Color buttonBorderColorAlt(final boolean selected) {
        return selected ? Constants.HIGHLIGHT_1 : Constants.WHITE;
    }

    public static GameImage drawTextButton(
            final int width, final String text,
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
        final Color frame = GraphicsUtils.buttonBorderColor(isSelected);
        nhi.drawRectangle(frame, 2f * Constants.BUTTON_BORDER_PX, 0, 0, width, height);

        return nhi.submit();
    }

    public static GameImage drawHighlightedButton(
            final GameImage nhi
    ) {
        final GameImage hi = new GameImage(nhi);
        final int w = hi.getWidth(), h = hi.getHeight();
        hi.fillRectangle(Constants.HIGHLIGHT_2, 0, 0, w, h);
        hi.drawRectangle(Constants.BLACK, 2f * Constants.BUTTON_BORDER_PX,
                0, 0, w, h);

        return hi.submit();
    }
}
