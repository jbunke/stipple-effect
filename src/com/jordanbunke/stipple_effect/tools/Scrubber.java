package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public sealed abstract class Scrubber<T> extends ToolWithBreadth
        permits AbstractBrush, Eraser {
    boolean using;

    private final Set<Coord2D> recent, now;

    Scrubber() {
        using = false;

        recent = new HashSet<>();
        now = new HashSet<>();
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (!context.getTargetPixel().equals(Constants.NO_VALID_TARGET) &&
                additionalMouseDown(context, me)) {
            using = true;
            prepAction(context, me);

            recent.clear();
            now.clear();

            reset();
            context.getState().markAsCheckpoint(false);
        }
    }

    @Override
    public final void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();
        final ProjectState s = context.getState();

        if (using && !tp.equals(Constants.NO_VALID_TARGET)) {
            if (isUnchanged(context))
                return;

            if (!stillSameFrame(context))
                onFrameChange();

            final int w = s.getImageWidth(), h = s.getImageHeight();
            final Selection selection = s.getSelection();

            final T data = createData(w, h);
            final Predicate<Coord2D>
                    additionalCondition = defAdditionalCond(s),
                    internalCondition = defInternalCond(data);
            final Consumer<Coord2D> onPass = defOnPass(data);
            populateAround(tp, selection, w, h,
                    additionalCondition, internalCondition, onPass);

            final Coord2D lastTP = getLastTP();

            if (!lastTP.equals(Constants.NO_VALID_TARGET)) {
                fillLineSpace(lastTP, tp, (x, y) ->
                        populateAround(lastTP.displace(x, y), selection, w, h,
                                additionalCondition, internalCondition, onPass));
                fillGaps(w, h, lastTP, tp, this::couldBeNextToGap,
                        (x, y) -> {
                            final Coord2D pixel = new Coord2D(x, y);

                            if (internalCondition.test(pixel)) {
                                onPass.accept(pixel);
                                now.add(pixel);
                            }
                        });
            }

            perform(context, data);
            updateLast(context);

            recent.clear();
            recent.addAll(now);
            now.clear();
        }
    }

    @Override
    public final void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (using) {
            using = false;
            context.getState().markAsCheckpoint(true);
            me.markAsProcessed();
        }
    }

    private boolean couldBeNextToGap(final int x, final int y) {
        final Coord2D pixel = new Coord2D(x, y);

        return recent.contains(pixel) || now.contains(pixel);
    }

    private void populateAround(
            final Coord2D tp, final Selection selection,
            final int w, final int h,
            final Predicate<Coord2D> additionalCondition,
            final Predicate<Coord2D> internalCondition,
            final Consumer<Coord2D> onPass
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

                if (mask[x][y] && additionalCondition.test(b)) {
                    if (internalCondition.test(b))
                        onPass.accept(b);

                    now.add(b);
                }
            }
    }

    abstract boolean additionalMouseDown(final SEContext context, final GameMouseEvent me);
    abstract void prepAction(final SEContext context, final GameMouseEvent me);
    abstract void onFrameChange();
    abstract T createData(final int w, final int h);
    abstract Predicate<Coord2D> defAdditionalCond(final ProjectState s);
    abstract Predicate<Coord2D> defInternalCond(final T data);
    abstract Consumer<Coord2D> defOnPass(final T data);
    abstract void perform(final SEContext context, final T data);
}
