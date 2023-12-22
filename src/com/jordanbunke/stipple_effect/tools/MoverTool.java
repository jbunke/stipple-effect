package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.function.BiConsumer;

public sealed abstract class MoverTool extends Tool
        permits MoveSelection, PickUpSelection {
    private boolean moving;
    private Coord2D startMousePosition, lastMousePosition, startTopLeft;

    public MoverTool() {
        moving = false;
        startMousePosition = new Coord2D();
        lastMousePosition = new Coord2D();
        startTopLeft = Constants.NO_VALID_TARGET;
    }

    abstract BiConsumer<Coord2D, Boolean> getMoverFunction(final SEContext context);

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        moving = context.getState().hasSelection();

        if (moving) {
            startMousePosition = me.mousePosition;
            lastMousePosition = me.mousePosition;
            startTopLeft = SelectionUtils.topLeft(
                    context.getState().getSelection());
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        if (moving) {
            final float zoomFactor = context.renderInfo.getZoomFactor();

            if (mousePosition.equals(lastMousePosition))
                return;

            final Coord2D topLeft = SelectionUtils.topLeft(
                    context.getState().getSelection());
            final Coord2D displacement = new Coord2D(
                    -(int)((startMousePosition.x - mousePosition.x) / zoomFactor),
                    -(int)((startMousePosition.y - mousePosition.y) / zoomFactor)
            ).displace(startTopLeft.x - topLeft.x, startTopLeft.y - topLeft.y);

            getMoverFunction(context).accept(displacement, false);
            lastMousePosition = mousePosition;
        }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (moving) {
            moving = false;
            context.getState().markAsCheckpoint(true, context);
            me.markAsProcessed();
        }
    }
}
