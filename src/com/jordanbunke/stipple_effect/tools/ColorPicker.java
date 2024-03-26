package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;

import java.awt.*;

public final class ColorPicker extends Tool {
    private static final ColorPicker INSTANCE;

    static {
        INSTANCE = new ColorPicker();
    }

    private ColorPicker() {

    }

    public static ColorPicker get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Color Picker";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (context.isTargetingPixelOnCanvas() &&
                me.button != GameMouseEvent.Button.MIDDLE) {
            final Coord2D tp = context.getTargetPixel();
            final int index = me.button == GameMouseEvent.Button.LEFT
                    ? StippleEffect.PRIMARY : StippleEffect.SECONDARY;

            final Color c = ImageProcessing.colorAtPixel(
                    context.getState().getActiveLayerFrame(), tp.x, tp.y);

            StippleEffect.get().setColorIndexAndColor(index, c);
        }
    }
}
