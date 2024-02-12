package com.jordanbunke.stipple_effect.utility;

public class LineMath {
    public static boolean linesIntersect(
            final LineSegment l1, final LineSegment l2,
            final boolean startVertexOnly
    ) {
        final double m1 = l1.slope(), m2 = l2.slope(),
                b1 = l1.yIntercept(), b2 = l2.yIntercept();

        if (l1.isSinglePoint() || l2.isSinglePoint())
            return false;

        if (l1.isSlopeUndefined() && l2.isSlopeUndefined())
            return false;
        else if (m1 == m2)
            return false;
        else {
            final double x, y;

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

            return l1.pointOnLine(x, y, startVertexOnly) &&
                    l2.pointOnLine(x, y, startVertexOnly);
        }
    }
}
