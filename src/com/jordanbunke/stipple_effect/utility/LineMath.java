package com.jordanbunke.stipple_effect.utility;

public class LineMath {
    public static boolean linesIntersect(
            final LineSegment l1, final LineSegment l2
    ) {
        if (l1.isSinglePoint() || l2.isSinglePoint())
            return false;

        if (l1.isSlopeUndefined() && l2.isSlopeUndefined())
            return false;
        else if (l1.slope() == l2.slope())
            return false;
        else {
            final int X = 0, Y = 1;
            final double[] intersection = intersection(l1, l2);
            final double x = intersection[X], y = intersection[Y];

            return l1.pointOnLine(x, y) && l2.pointOnLine(x, y);
        }
    }

    public static double[] intersection(
            final LineSegment l1, final LineSegment l2
    ) {
        final double m1 = l1.slope(), m2 = l2.slope(),
                b1 = l1.yIntercept(), b2 = l2.yIntercept(),
                x, y;

        if (l1.isSlopeUndefined()) {
            x = l1.pa().x;
            y = (m2 * x) + b2;
        }
        else if (l2.isSlopeUndefined()) {
            x = l2.pa().x;
            y = (m1 * x) + b1;
        }
        else {
            x = (b2 - b1) / (m1 - m2);
            y = (m1 * x) + b1;
        }

        return new double[] {
                pixelErrorCorrection(x), pixelErrorCorrection(y)
        };
    }

    public static double pixelErrorCorrection(final double pDim) {
        final double margin = 0.0001;

        return Math.abs(pDim - (int)Math.round(pDim)) < margin
                ? (int)Math.round(pDim) : pDim;
    }
}
