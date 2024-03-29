package com.jordanbunke.stipple_effect.utility.math;

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
            final double px, final double py
    ) {
        final double margin = 0.1, m = slope(), b = yIntercept();
        final boolean satisfiesEquation = isSlopeUndefined()
                ? (int)Math.round(px) == pa.x
                : Math.abs(((px * m) + b) - py) <= margin;

        if (!satisfiesEquation)
            return false;

        // greater and lesser bounds
        final int lbx = Math.min(pa.x, pb.x),
                gbx = Math.max(pa.x, pb.x),
                lby = Math.min(pa.y, pb.y),
                gby = Math.max(pa.y, pb.y);
        // greater- and less than functions
        final BiFunction<Double, Integer, Boolean>
                gt = (pDim, ref) -> pDim >= ref,
                lt = (pDim, ref) -> pDim <= ref;
        final boolean
                isInBoundsX = pa.x == pb.x ? px == pa.x
                        : gt.apply(px, lbx) && lt.apply(px, gbx),
                isInBoundsY = pa.y == pb.y ? py == pa.y
                        : gt.apply(py, lby) && lt.apply(py, gby);

        return isInBoundsX && isInBoundsY;
    }
}
