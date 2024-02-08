package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.MathPlus;

public class Geometry {
    public static double calculateAngleInRad(
            final Coord2D point, final Coord2D pivot
    ) {
        return Math.atan2(point.y - pivot.y, point.x - pivot.x);
    }

    public static double calculateAngleInRad(
            final double px, final double py,
            final double pivotX, final double pivotY
    ) {
        return Math.atan2(py - pivotY, px - pivotX);
    }

    public static double normalizeAngle(final double r) {
        double nr = r;

        while (nr < 0d)
            nr += Constants.CIRCLE;

        while (nr >= Constants.CIRCLE)
            nr -= Constants.CIRCLE;

        return nr;
    }

    public static double snapAngle(final double r, final double snapIncRad) {
        final double[] angles = new double[(int)(Math.round(
                Constants.CIRCLE / snapIncRad))];

        for (int i = 0; i < angles.length; i++)
            angles[i] = i * snapIncRad;

        return MathPlus.findBestDouble(r, Double.MAX_VALUE, x -> x,
                (a, b) -> angleDiff(a, r) < angleDiff(b, r), angles);
    }

    public static double angleDiff(final double ref, final double a) {
        return Math.min(Math.abs(ref - a),
                Math.abs((Constants.CIRCLE + ref) - a));
    }

    public static Coord2D projectPoint(
            final Coord2D source, final double angle, final double distance
    ) {
        final int deltaX = (int)Math.round(Math.cos(angle) * distance),
                deltaY = (int)Math.round(Math.sin(angle) * distance);

        return source.displace(deltaX, deltaY);
    }
}
