package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public sealed abstract class AbstractBrush
        extends ToolWithBreadth
        permits Brush, ShadeBrush, ScriptBrush {
    private boolean painting;
    private final Set<Coord2D> painted, recentPainted, paintedNow;

    AbstractBrush() {
        painting = false;
        painted = new HashSet<>();
        recentPainted = new HashSet<>();
        paintedNow = new HashSet<>();
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (!context.getTargetPixel().equals(Constants.NO_VALID_TARGET) &&
                me.button != GameMouseEvent.Button.MIDDLE) {
            painting = true;
            setColorGetter(context, me);
            painted.clear();
            recentPainted.clear();
            paintedNow.clear();

            reset();
            context.getState().markAsCheckpoint(false);
        }
    }

    abstract void setColorGetter(final SEContext context, final GameMouseEvent me);

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();
        final ProjectState s = context.getState();

        if (painting && !tp.equals(Constants.NO_VALID_TARGET)) {
            final int w = s.getImageWidth(), h = s.getImageHeight();
            final Selection selection = s.getSelection();

            if (isUnchanged(context))
                return;

            if (!stillSameFrame(context))
                painted.clear();

            final GameImage edit = new GameImage(w, h),
                    current = s.getActiveCel();
            populateAround(edit, current, tp, selection, w, h);

            final Coord2D lastTP = getLastTP();

            if (!lastTP.equals(Constants.NO_VALID_TARGET)) {
                fillLineSpace(lastTP, tp, (x, y) -> populateAround(edit,
                        current, lastTP.displace(x, y), selection, w, h));
                fillGaps(w, h, lastTP, tp, this::couldBeNextToGap,
                        (x, y) -> {
                    // TODO - temp
                            final Coord2D pixel = new Coord2D(x, y);

                            if (!painted.contains(pixel)) {
                                edit.dot(SEColors.red(),
                                        // getColor(current.getColorAt(x, y),x, y),
                                        x, y);

                                painted.add(pixel);
                                paintedNow.add(pixel);
                            }
                        });
            }

            context.paintOverImage(edit.submit());
            updateLast(context);

            recentPainted.clear();
            recentPainted.addAll(paintedNow);
            paintedNow.clear();
        }
    }

    private boolean couldBeNextToGap(final int x, final int y) {
        final Coord2D pixel = new Coord2D(x, y);

        return recentPainted.contains(pixel) ||
                paintedNow.contains(pixel);
    }

    private void populateAround(
            final GameImage edit, final GameImage current,
            final Coord2D tp, final Selection selection,
            final int w, final int h
    ) {
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();
        final boolean empty = !selection.hasSelection();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++) {
                final Coord2D b = new Coord2D(x + (tp.x - halfB),
                        y + (tp.y - halfB));

                if (b.x < 0 || b.x >= w || b.y < 0 || b.y >= h)
                    continue;

                if (!(empty || selection.selected(b)))
                    continue;

                final Color existing = current.getColorAt(b.x, b.y);
                if (mask[x][y] && !painted.contains(b) &&
                        paintCondition(existing, b.x, b.y)) {
                    edit.dot(getColor(existing, b.x, b.y), b.x, b.y);
                    painted.add(b);
                    paintedNow.add(b);
                }
            }
    }

    abstract boolean paintCondition(final Color existing, final int x, final int y);
    abstract Color getColor(final Color existing, final int x, final int y);

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (painting) {
            painting = false;
            context.getState().markAsCheckpoint(true);
            me.markAsProcessed();
        }
    }
}
