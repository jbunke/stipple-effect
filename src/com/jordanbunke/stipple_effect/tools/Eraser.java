package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.state.ProjectState;

import java.util.function.Consumer;
import java.util.function.Predicate;

public final class Eraser extends Scrubber<boolean[][]> {
    private static final Eraser INSTANCE;

    static {
        INSTANCE = new Eraser();
    }

    private Eraser() {}

    public static Eraser get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Eraser";
    }

    @Override
    boolean additionalMouseDown(final SEContext context, final GameMouseEvent me) {
        return true;
    }

    @Override
    void prepAction(final SEContext context, final GameMouseEvent me) {}

    @Override
    void onFrameChange() {}

    @Override
    boolean[][] createData(int w, int h) {
        return new boolean[w][h];
    }

    @Override
    Predicate<Coord2D> defAdditionalCond(final ProjectState s) {
        return px -> true;
    }

    @Override
    Predicate<Coord2D> defInternalCond(final boolean[][] eraseMask) {
        return px -> !eraseMask[px.x][px.y];
    }

    @Override
    Consumer<Coord2D> defOnPass(final boolean[][] eraseMask) {
        return px -> eraseMask[px.x][px.y] = true;
    }

    @Override
    void perform(final SEContext context, final boolean[][] eraseMask) {
        context.erase(eraseMask, false);
    }
}
