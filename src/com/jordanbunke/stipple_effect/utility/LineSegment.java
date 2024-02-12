package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;

import java.util.function.BiFunction;

public record LineSegment(Coord2D pa, Coord2D pb) {
    public int diffX() {
        return pb.x - pa.x;
    }

    public int diffY() {
        return pb.y - pa.y;
    }

    public boolean isSlopeUndefined() {
        return diffX() == 0;
    }

    public boolean isSinglePoint() {
        return pa.equals(pb);
    }

    public double slope() {
        if (isSlopeUndefined())
            return Double.NaN;

        return diffY() / (double) diffX();
    }

    public double yIntercept() {
        if (pa.y == pb.y && pa.x != pb.x)
            return pa.y;

        final double m = slope();

        return Double.isNaN(m) ? Double.NaN : pa.y - (m * pa.x);
    }

    public boolean pointOnLine(
            final double px, final double py, final boolean includeVertices
    ) {
        final double margin = 0.1, m = slope(), b = yIntercept();
        final boolean satisfiesEquation = isSlopeUndefined()
                ? (int)Math.round(px) == pa.x
                : Math.abs(((px * m) + b) - py) <= margin;

        final int lesserBoundX = Math.min(pa.x, pb.x),
                greaterBoundX = Math.max(pa.x, pb.x),
                lesserBoundY = Math.min(pa.y, pb.y),
                greaterBoundY = Math.max(pa.y, pb.y);
        final BiFunction<Double, Integer, Boolean> gt = includeVertices
                ? (pDim, ref) -> pDim >= ref
                : (pDim, ref) -> pDim > ref, lt = includeVertices
                ? (pDim, ref) -> pDim <= ref
                : (pDim, ref) -> pDim < ref;
        final boolean isInBounds =
                gt.apply(px, lesserBoundX) && lt.apply(px, greaterBoundX) &&
                gt.apply(py, lesserBoundY) && lt.apply(py, greaterBoundY);

        return satisfiesEquation && isInBounds;
    }
}
