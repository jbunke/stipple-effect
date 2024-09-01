package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.state.ProjectState;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public sealed abstract class AbstractBrush
        extends Scrubber<GameImage>
        permits Brush, ShadeBrush, ScriptBrush {
    private final Set<Coord2D> painted;
    private GameImage current;

    AbstractBrush() {
        painted = new HashSet<>();
        current = GameImage.dummy();
    }

    abstract void setColorGetter(final SEContext context, final GameMouseEvent me);

    abstract boolean paintCondition(final Color existing, final int x, final int y);
    abstract Color getColor(final Color existing, final int x, final int y);

    @Override
    boolean additionalMouseDown(final SEContext context, final GameMouseEvent me) {
        return me.button != GameMouseEvent.Button.MIDDLE;
    }

    @Override
    void prepAction(final SEContext context, final GameMouseEvent me) {
        setColorGetter(context, me);
        painted.clear();
        current = GameImage.dummy();
    }

    @Override
    void onFrameChange() {
        painted.clear();
    }

    @Override
    GameImage createData(final int w, final int h) {
        return new GameImage(w, h);
    }

    @Override
    Predicate<Coord2D> defAdditionalCond(final ProjectState s) {
        current = s.getActiveCel();

        return px -> paintCondition(current.getColorAt(px.x, px.y), px.x, px.y);
    }

    @Override
    Predicate<Coord2D> defInternalCond(final GameImage edit) {
        return px -> !painted.contains(px);
    }

    @Override
    Consumer<Coord2D> defOnPass(final GameImage edit) {
        return px -> {
            final int x = px.x, y = px.y;

            edit.dot(getColor(current.getColorAt(x, y), x, y), x, y);
            painted.add(px);
        };
    }

    @Override
    void perform(final SEContext context, final GameImage edit) {
        context.paintOverImage(edit.submit());
    }
}
