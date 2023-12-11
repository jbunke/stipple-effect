package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.stipple_effect.text.SEFonts;

import java.awt.*;

public class GraphicsUtils {
    public static TextBuilder uiText() {
        return uiText(Constants.BLACK);
    }

    public static TextBuilder uiText(final Color color) {
        return uiText(color, 1d);
    }

    public static TextBuilder uiText(final Color color, final double textSize) {
        return new TextBuilder(textSize, Text.Orientation.CENTER, color, SEFonts.CLASSIC.getStandard());
    }
}
