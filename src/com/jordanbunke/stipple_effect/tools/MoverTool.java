package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.MathPlus;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.RotateFunction;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.selection.StretcherFunction;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.math.Geometry;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public sealed abstract class MoverTool extends Tool implements SnappableTool
        permits MoveSelection, PickUpSelection {
    public enum TransformType {
        NONE, MOVE, STRETCH, ROTATE
    }

    public enum Direction {
        R, BR, B, BL, L, TL, T, TR, NA;

        double angle() {
            if (this == NA)
                return Double.MAX_VALUE;

            return ordinal() * Constants._45_SNAP_INC;
        }
    }

    private boolean snap = false, snapToggled = false;

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

    public abstract BiConsumer<Coord2D, Boolean> getMoverFunction(final SEContext context);
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

            final int middleX = left + ((right - left) / 2),
                    middleY = top + ((bottom - top) / 2);
            final boolean atMiddleX = Math.abs(tp.x - middleX) < Constants.ROTATE_PX_THRESHOLD,
                    atMiddleY = Math.abs(tp.y - middleY) < Constants.ROTATE_PX_THRESHOLD;

            if (atLeft || atRight) {
                if (atTop) {
                    direction = atLeft ? Direction.TL : Direction.TR;
                    return TransformType.ROTATE;
                } else if (atBottom) {
                    direction = atLeft ? Direction.BL : Direction.BR;
                    return TransformType.ROTATE;
                } else if (atMiddleY) {
                    direction = atLeft ? Direction.L : Direction.R;
                    return TransformType.ROTATE;
                }
            } else if (atTop && atMiddleX) {
                direction = Direction.T;
                return TransformType.ROTATE;
            } else if (atBottom && atMiddleX) {
                direction = Direction.B;
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
                final Set<Coord2D> selection = context.getState().getSelection();
                final Coord2D topLeft = SelectionUtils.topLeft(selection);

                Coord2D displacement = new Coord2D(
                        -(int)((startMousePosition.x - mousePosition.x) / zoomFactor),
                        -(int)((startMousePosition.y - mousePosition.y) / zoomFactor)
                ).displace(startTopLeft.x - topLeft.x, startTopLeft.y - topLeft.y);

                if (isSnap()) {
                    if (!snapToggled) {
                        // displace in multiples of own bounds
                        final Coord2D bounds = SelectionUtils.bounds(selection);

                        final int snappedX = bounds.x * (int)Math.round(
                                displacement.x / (double) bounds.x),
                                snappedY = bounds.y * (int)Math.round(
                                        displacement.y / (double) bounds.y);
                        displacement = new Coord2D(snappedX, snappedY);
                    } else if (canSnapToGrid(context)) {
                        // snap to top left of pixel grid
                        final int px = Settings.getPixelGridXPixels(),
                                py = Settings.getPixelGridYPixels();
                        final Coord2D prospective = topLeft.displace(displacement),
                                gridPos = new Coord2D(
                                        (prospective.x / px) * px,
                                        (prospective.y / py) * py),
                                shift = new Coord2D(
                                        gridPos.x - prospective.x,
                                        gridPos.y - prospective.y);

                        displacement = displacement.displace(shift);
                    }
                }

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
                            initialAngle = Geometry.calculateAngleInRad(startTP, pivot),
                            angle = Geometry.calculateAngleInRad(tp, pivot),
                            candidate = ((initialAngle > angle
                                    ? Constants.CIRCLE : 0d) + angle) - initialAngle,
                            deltaR = isSnap() ? Geometry.snapAngle(
                                    candidate, Constants._45_SNAP_INC) :
                                    candidate;

                    direction = MathPlus.findBest(Direction.NA,
                            Double.MAX_VALUE, Direction::angle,
                            (a, b) -> Geometry.angleDiff(angle, a) <
                                    Geometry.angleDiff(angle, b),
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

    private boolean canSnapToGrid(final SEContext context) {
        return context.renderInfo.isPixelGridOn() &&
                context.couldRenderPixelGrid();
    }

    public void setSnapToggled(final boolean snapToggled) {
        this.snapToggled = snapToggled;
    }

    @Override
    public void setSnap(final boolean snap) {
        this.snap = snap;
    }

    @Override
    public boolean isSnap() {
        return snap;
    }
}
