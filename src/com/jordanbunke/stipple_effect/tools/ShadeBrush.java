package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public final class ShadeBrush extends ToolWithBreadth {
    private static final ShadeBrush INSTANCE;

    private boolean painting;
    private Function<Color, Color> c;
    private Set<Coord2D> painted;
    private Palette palette;

    static {
        INSTANCE = new ShadeBrush();
    }

    private ShadeBrush() {
        painting = false;
        c = c -> c;
        painted = new HashSet<>();
        palette = null;
    }

    public static ShadeBrush get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Shade Brush";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (StippleEffect.get().hasPaletteContents() &&
                !context.getTargetPixel().equals(Constants.NO_VALID_TARGET) &&
                me.button != GameMouseEvent.Button.MIDDLE) {
            painting = true;
            palette = StippleEffect.get().getSelectedPalette();
            c = me.button == GameMouseEvent.Button.LEFT
                    ? palette::nextLeft : palette::nextRight;
            painted = new HashSet<>();

            reset();
            context.getState().markAsCheckpoint(false);
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();

        if (painting && !tp.equals(Constants.NO_VALID_TARGET)) {
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();
            final Set<Coord2D> selection = context.getState().getSelection();

            if (isUnchanged(context))
                return;

            if (!stillSameFrame(context))
                painted.clear();

            final GameImage edit = new GameImage(w, h),
                    current = context.getState().getActiveLayerFrame();
            populateAround(edit, current, tp, selection, w, h);

            fillLineSpace(getLastTP(), tp, (x, y) -> populateAround(edit,
                    current, getLastTP().displace(x, y), selection, w, h));

            context.paintOverImage(edit.submit());
            updateLast(context);
        }
    }

    private void populateAround(
            final GameImage edit, final GameImage current,
            final Coord2D tp, final Set<Coord2D> selection,
            final int w, final int h
    ) {
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++) {
                final Coord2D b = new Coord2D(x + (tp.x - halfB),
                        y + (tp.y - halfB));

                if (b.x < 0 || b.x >= w || b.y < 0 || b.y >= h)
                    continue;

                if (!(selection.isEmpty() || selection.contains(b)))
                    continue;

                final Color candidate = ImageProcessing
                        .colorAtPixel(current, b.x, b.y);
                if (mask[x][y] && !painted.contains(b) &&
                        palette.isIncluded(candidate)) {
                    edit.dot(c.apply(candidate), b.x, b.y);
                    painted.add(b);
                }
            }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (painting) {
            painting = false;
            context.getState().markAsCheckpoint(true);
            me.markAsProcessed();
        }
    }
}
