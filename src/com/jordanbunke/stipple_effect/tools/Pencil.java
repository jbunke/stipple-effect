package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.context.ImageContext;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;

public final class Pencil extends Tool {
    private static final Pencil INSTANCE;

    private boolean drawing;
    private Color c;

    static {
        INSTANCE = new Pencil();
    }

    private Pencil() {
        drawing = false;
        c = Constants.BLACK;
    }

    public static Pencil get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Pencil";
    }

    @Override
    public void onMouseDown(
            final ImageContext context, final GameMouseEvent me
    ) {
        if (context.hasTargetPixel() && me.button != GameMouseEvent.Button.MIDDLE) {
            drawing = true;
            c = me.button == GameMouseEvent.Button.LEFT
                    ? StippleEffect.get().getPrimary()
                    : StippleEffect.get().getSecondary();
        }
    }

    @Override
    public void update(
            final ImageContext context, final Coord2D mousePosition
    ) {
        if (drawing) {
            if (context.hasTargetPixel()) {
                final int w = context.getStates().getState().getImageWidth(),
                        h = context.getStates().getState().getImageHeight();
                final Coord2D tp = context.getTargetPixel();

                final GameImage edit = new GameImage(w, h);
                edit.dot(c, tp.x, tp.y);
                context.editImage(edit.submit());
            } else
                drawing = false;
        }
    }

    @Override
    public void onMouseUp(
            final ImageContext context, final GameMouseEvent me
    ) {
        drawing = false;
    }
}
