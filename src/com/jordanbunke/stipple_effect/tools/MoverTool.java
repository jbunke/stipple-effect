package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.MathPlus;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.RotateFunction;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.selection.StretcherFunction;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public sealed abstract class MoverTool extends Tool
        permits MoveSelection, PickUpSelection {
    public enum TransformType {
        NONE, MOVE, STRETCH, ROTATE
    }

    public enum Direction {
        R, BR, B, BL, L, TL, T, TR, NA;

        double angle() {
            if (this == NA)
                return Double.MAX_VALUE;

            return ordinal() * Constants.SNAP_INC;
        }
    }

    private static boolean snap = false;

    private TransformType transformType, prospectiveType;
    private Direction direction;
    private Set<Coord2D> startSelection;
    private Coord2D startMousePosition, lastMousePosition,
            startTopLeft, startBottomRight, startTP, lastTP;

    public MoverTool() {
        transformType = TransformType.NONE;
        prospectiveType = TransformType.NONE;
        direction = Direction.NA;

        startSelection = new HashSet<>();

        startMousePosition = new Coord2D();
        lastMousePosition = new Coord2D();
        startTP = Constants.NO_VALID_TARGET;
        lastTP = Constants.NO_VALID_TARGET;
        startTopLeft = Constants.NO_VALID_TARGET;
        startBottomRight = Constants.NO_VALID_TARGET;
    }

    abstract BiConsumer<Coord2D, Boolean> getMoverFunction(final SEContext context);
    abstract StretcherFunction getStretcherFunction(final SEContext context);
    abstract RotateFunction getRotateFunction(final SEContext context);
    abstract Runnable getMouseUpConsequence(final SEContext context);

    public TransformType determineTransformType(
            final SEContext context
    ) {
        if (!context.getState().hasSelection())
            return TransformType.NONE;
        else {
            final Set<Coord2D> selection = context.getState().getSelection();

            startTopLeft = SelectionUtils.topLeft(selection);
            startBottomRight = SelectionUtils.bottomRight(selection);

            final Coord2D tp = context.getTargetPixel();

            if (tp.equals(Constants.NO_VALID_TARGET))
                return TransformType.MOVE;

            final int left = startTopLeft.x, right = startBottomRight.x,
                    top = startTopLeft.y, bottom = startBottomRight.y;

            final int leftProx = Math.abs(left - tp.x),
                    rightProx = Math.abs(right - tp.x),
                    topProx = Math.abs(top - tp.y),
                    bottomProx = Math.abs(bottom - tp.y);

            boolean atLeft = leftProx <= Constants.STRETCH_PX_THRESHOLD,
                    atRight = rightProx <= Constants.STRETCH_PX_THRESHOLD,
                    atTop = topProx <= Constants.STRETCH_PX_THRESHOLD,
                    atBottom = bottomProx <= Constants.STRETCH_PX_THRESHOLD;

            if (atLeft) {
                if (atTop) {
                    direction = Direction.TL;
                    return TransformType.STRETCH;
                } else if (atBottom) {
                    direction = Direction.BL;
                    return TransformType.STRETCH;
                } else if (tp.y > top && tp.y < bottom) {
                    direction = Direction.L;
                    return TransformType.STRETCH;
                }
            } else if (atRight) {
                if (atTop) {
                    direction = Direction.TR;
                    return TransformType.STRETCH;
                } else if (atBottom) {
                    direction = Direction.BR;
                    return TransformType.STRETCH;
                } else if (tp.y > top && tp.y < bottom) {
                    direction = Direction.R;
                    return TransformType.STRETCH;
                }
            } else if (atTop && tp.x > left && tp.x < right) {
                direction = Direction.T;
                return TransformType.STRETCH;
            } else if (atBottom && tp.x > left && tp.x < right) {
                direction = Direction.B;
                return TransformType.STRETCH;
            }

            atLeft = tp.x < left && leftProx <= Constants.ROTATE_PX_THRESHOLD;
            atRight = tp.x > right && rightProx <= Constants.ROTATE_PX_THRESHOLD;
            atTop = tp.y < top && topProx <= Constants.ROTATE_PX_THRESHOLD;
            atBottom = tp.y > bottom && bottomProx <= Constants.ROTATE_PX_THRESHOLD;

            if (atLeft || atRight || atTop || atBottom) {
                // direction = Direction.NA;
                direction = atLeft ? (atTop ? Direction.TL
                        : (atBottom ? Direction.BL : Direction.L)) :
                        (atRight ? (atTop ? Direction.TR
                                : (atBottom ? Direction.BR : Direction.R)) :
                                (atTop ? Direction.T : Direction.B));
                return TransformType.ROTATE;
            }

            direction = Direction.NA;
            return TransformType.MOVE;
        }
    }

    @Override
    public String getCursorCode() {
        final TransformType relevantType = transformType == TransformType.NONE
                ? prospectiveType : transformType;

        final String suffix = switch (relevantType) {
            case ROTATE -> switch (direction) {
                case R, NA -> "_rotright";
                case L -> "_rotleft";
                case T -> "_rottop";
                case B -> "_rotbottom";
                case TL -> "_rottl";
                case TR -> "_rottr";
                case BL -> "_rotbl";
                case BR -> "_rotbr";
            };
            case STRETCH -> switch (direction) {
                case NA -> "";
                case B, T -> "_vert";
                case L, R -> "_horz";
                case TL, BR -> "_diag_tl";
                case BL, TR -> "_diag_bl";
            };
            default -> "";
        };

        return super.getCursorCode() + suffix;
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        transformType = determineTransformType(context);
        prospectiveType = TransformType.NONE;

        if (context.getState().hasSelection()) {
            startSelection = new HashSet<>(context.getState().getSelection());

            startMousePosition = me.mousePosition;
            lastMousePosition = me.mousePosition;
            startTP = context.getTargetPixel();
            lastTP = startTP;
        }
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final float zoomFactor = context.renderInfo.getZoomFactor();

        if (mousePosition.equals(lastMousePosition))
            return;

        switch (transformType) {
            case NONE -> prospectiveType = determineTransformType(context);
            case MOVE -> {
                final Coord2D topLeft = SelectionUtils.topLeft(
                        context.getState().getSelection());
                final Coord2D displacement = new Coord2D(
                        -(int)((startMousePosition.x - mousePosition.x) / zoomFactor),
                        -(int)((startMousePosition.y - mousePosition.y) / zoomFactor)
                ).displace(startTopLeft.x - topLeft.x, startTopLeft.y - topLeft.y);

                getMoverFunction(context).accept(displacement, false);
            }
            case STRETCH -> {
                final Coord2D delta = new Coord2D(
                        (int)((mousePosition.x - startMousePosition.x) / zoomFactor),
                        (int)((mousePosition.y - startMousePosition.y) / zoomFactor)
                );

                final Coord2D change = switch (direction) {
                    case NA -> new Coord2D();
                    case B, T -> new Coord2D(0, delta.y);
                    case L, R -> new Coord2D(delta.x, 0);
                    default -> new Coord2D(delta.x, delta.y);
                };

                getStretcherFunction(context).accept(startSelection,
                        change, direction, false);
            }
            case ROTATE -> {
                final Coord2D tp = context.getTargetPixel();

                if (!(tp.equals(Constants.NO_VALID_TARGET) ||
                        tp.equals(startTP) || tp.equals(lastTP))) {
                    final Coord2D pivot = new Coord2D(
                            (startTopLeft.x + startBottomRight.x) / 2,
                            (startTopLeft.y + startBottomRight.y) / 2);
                    final boolean[] offset = new boolean[] {
                            (startTopLeft.x + startBottomRight.x) % 2 == 0,
                            (startTopLeft.y + startBottomRight.y) % 2 == 0
                    };
                    final double
                            initialAngle = SelectionUtils.calculateAngleInRad(startTP, pivot),
                            angle = SelectionUtils.calculateAngleInRad(tp, pivot),
                            candidate = ((initialAngle > angle
                                    ? Constants.CIRCLE : 0d) + angle) - initialAngle,
                            deltaR = snap ? SelectionUtils.snapAngle(candidate) : candidate;

                    direction = MathPlus.findBest(Direction.NA,
                            Double.MAX_VALUE, Direction::angle,
                            (a, b) -> SelectionUtils.angleDiff(angle, a) <
                                    SelectionUtils.angleDiff(angle, b),
                            Direction.values());

                    getRotateFunction(context).accept(startSelection,
                            deltaR, pivot, offset, false);

                    lastTP = tp;
                }
            }
        }

        lastMousePosition = mousePosition;
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (transformType != TransformType.NONE) {
            transformType = TransformType.NONE;
            getMouseUpConsequence(context).run();
            me.markAsProcessed();
        }
    }

    public static void setSnap(final boolean snap) {
        MoverTool.snap = snap;
    }
}
