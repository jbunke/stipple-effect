package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.context.SEContext;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public final class Fill extends ToolThatSearches {
    private static final Fill INSTANCE;

    static {
        INSTANCE = new Fill();
    }

    private Fill() {

    }

    public static Fill get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Fill";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (context.hasTargetPixel() && me.button != GameMouseEvent.Button.MIDDLE) {
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();
            final Coord2D tp = context.getTargetPixel();

            final GameImage image = context.getState().getEditingLayer()
                    .getFrame(context.getState().getFrameIndex());
            final Color initial = ImageProcessing.colorAtPixel(image, tp.x, tp.y),
                    fillColor = me.button == GameMouseEvent.Button.LEFT
                            ? StippleEffect.get().getPrimary()
                            : StippleEffect.get().getSecondary();

            final Set<Coord2D> searched = new HashSet<>(),
                    matched = new HashSet<>();

            // search
            if (ToolWithMode.getMode() == Mode.GLOBAL)
                globalSearch(image, initial, matched);
            else
                recursiveAdjacentSearch(image, initial, tp, searched, matched);

            // assemble edit mask
            final GameImage edit = new GameImage(w, h);

            for (Coord2D m : matched)
                edit.dot(fillColor, m.x, m.y);

            context.editImage(edit.submit());
            context.getState().markAsCheckpoint(true, context);
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {

    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {

    }
}
