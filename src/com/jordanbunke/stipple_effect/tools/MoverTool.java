package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.Set;
import java.util.function.BiConsumer;

public sealed abstract class MoverTool extends Tool
        permits MoveSelection, PickUpSelection {
    public enum TransformType {
        NONE, MOVE, STRETCH, ROTATE
    }

    public enum Direction {
        T, TL, TR, L, R, B, BL, BR, NA
    }

    private TransformType transformType;
    private Direction direction;
    private Coord2D startMousePosition, lastMousePosition,
            startTopLeft, startBottomRight;

    public MoverTool() {
        transformType = TransformType.NONE;
        direction = Direction.NA;

        startMousePosition = new Coord2D();
        lastMousePosition = new Coord2D();
        startTopLeft = Constants.NO_VALID_TARGET;
        startBottomRight = Constants.NO_VALID_TARGET;
    }

    abstract BiConsumer<Coord2D, Boolean> getMoverFunction(final SEContext context);

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (!context.getState().hasSelection())
            transformType = TransformType.NONE;
        else {
            final Set<Coord2D> selection = context.getState().getSelection();

            startMousePosition = me.mousePosition;
            lastMousePosition = me.mousePosition;
            startTopLeft = SelectionUtils.topLeft(selection);
            startBottomRight = SelectionUtils.bottomRight(selection);

            final float zoomFactor = context.renderInfo.getZoomFactor();

            final Coord2D tp = context.getTargetPixel();

            if (tp.equals(Constants.NO_VALID_TARGET)) {
                transformType = TransformType.MOVE;
                return;
            }

            final int left = startTopLeft.x, right = startBottomRight.x,
                    top = startTopLeft.y, bottom = startBottomRight.y;

            final boolean
                    atLeft = Math.abs(left - tp.x) * zoomFactor <=
                            Constants.STRETCH_PX_THRESHOLD,
                    atRight = Math.abs(right - tp.x) * zoomFactor <=
                            Constants.STRETCH_PX_THRESHOLD,
                    atTop = Math.abs(top - tp.y) * zoomFactor <=
                            Constants.STRETCH_PX_THRESHOLD,
                    atBottom = Math.abs(bottom - tp.y) * zoomFactor <=
                            Constants.STRETCH_PX_THRESHOLD;

            if (atLeft) {
                if (atTop) {
                    direction = Direction.TL;
                    transformType = TransformType.STRETCH;
                    return;
                } else if (atBottom) {
                    direction = Direction.BL;
                    transformType = TransformType.STRETCH;
                    return;
                } else if (tp.y > top && tp.y < bottom) {
                    direction = Direction.L;
                    transformType = TransformType.STRETCH;
                    return;
                }
            } else if (atRight) {
                if (atTop) {
                    direction = Direction.TR;
                    transformType = TransformType.STRETCH;
                    return;
                } else if (atBottom) {
                    direction = Direction.BR;
                    transformType = TransformType.STRETCH;
                    return;
                } else if (tp.y > top && tp.y < bottom) {
                    direction = Direction.R;
                    transformType = TransformType.STRETCH;
                    return;
                }
            } else if (atTop && tp.x > left && tp.x < right) {
                direction = Direction.T;
                transformType = TransformType.STRETCH;
                return;
            } else if (atBottom && tp.x > left && tp.x < right) {
                direction = Direction.B;
                transformType = TransformType.STRETCH;
                return;
            }

            // TODO - determinations for rotation

            transformType = TransformType.MOVE;
            direction = Direction.NA;
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        if (transformType != TransformType.NONE) {
            final float zoomFactor = context.renderInfo.getZoomFactor();

            if (mousePosition.equals(lastMousePosition))
                return;

            switch (transformType) {
                case MOVE -> {
                    final Coord2D topLeft = SelectionUtils.topLeft(
                            context.getState().getSelection());
                    final Coord2D displacement = new Coord2D(
                            -(int)((startMousePosition.x - mousePosition.x) / zoomFactor),
                            -(int)((startMousePosition.y - mousePosition.y) / zoomFactor)
                    ).displace(startTopLeft.x - topLeft.x, startTopLeft.y - topLeft.y);

                    getMoverFunction(context).accept(displacement, false);
                    lastMousePosition = mousePosition;
                }
                case STRETCH -> {
                    // TODO
                }
                case ROTATE -> {
                    // TODO
                }
            }
        }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (transformType != TransformType.NONE) {
            transformType = TransformType.NONE;
            context.getState().markAsCheckpoint(true, context);
            me.markAsProcessed();
        }
    }
}
