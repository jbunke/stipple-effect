package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;

import java.awt.*;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (context.isTargetingPixelOnCanvas() &&
                me.button != GameMouseEvent.Button.MIDDLE) {
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();
            final Coord2D tp = context.getTargetPixel();

            final GameImage image = context.getState().getActiveLayerFrame();
            final Color fillColor = me.button == GameMouseEvent.Button.LEFT
                    ? StippleEffect.get().getPrimary()
                    : StippleEffect.get().getSecondary();

            // search
            final Set<Coord2D> selection = context.getState().getSelection(),
                    matched = search(image, tp).stream()
                            .filter(m -> selection.isEmpty() || selection.contains(m))
                            .collect(Collectors.toSet());

            // assemble edit mask
            final GameImage edit = new GameImage(w, h);
            final int rgb = fillColor.getRGB();

            matched.forEach(m -> edit.setRGB(m.x, m.y, rgb));

            context.stampImage(edit.submit(), matched);
            context.getState().markAsCheckpoint(true);
        }
    }
}
