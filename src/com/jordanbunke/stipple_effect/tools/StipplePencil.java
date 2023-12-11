package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.context.ImageContext;

import java.awt.*;

public final class StipplePencil extends Tool {
    private static final StipplePencil INSTANCE;

    static {
        INSTANCE = new StipplePencil();
    }

    private StipplePencil() {

    }

    public static StipplePencil get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Stipple Pencil";
    }

    @Override
    public void onMouseDown(
            final ImageContext context, final GameMouseEvent me
    ) {
        if (context.hasTargetPixel() && me.button != GameMouseEvent.Button.MIDDLE) {
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();
            final Coord2D tp = context.getTargetPixel();
            final Color c = me.button == GameMouseEvent.Button.LEFT
                    ? StippleEffect.get().getPrimary()
                    : StippleEffect.get().getSecondary();

            final GameImage edit = new GameImage(w, h);
            edit.dot(c, tp.x, tp.y);
            context.editImage(edit.submit());
            context.getState().markAsCheckpoint();
        }
    }

    @Override
    public void update(
            final ImageContext context, final Coord2D mousePosition
    ) {

    }

    @Override
    public void onMouseUp(
            final ImageContext context, final GameMouseEvent me
    ) {

    }
}
