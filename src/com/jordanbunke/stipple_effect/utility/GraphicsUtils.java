package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.stipple_effect.text.SEFonts;

public class GraphicsUtils {
    public static TextBuilder uiText() {
        return new TextBuilder(1d, Text.Orientation.CENTER, Constants.WHITE, SEFonts.CLASSIC.getStandard());
    }
}
