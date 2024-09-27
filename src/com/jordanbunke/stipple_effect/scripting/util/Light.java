package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.awt.*;

public final class Light {
    public final boolean point;

    private double luminosity, radius, z;
    private Coord2D position;
    private Color color;
    private double[] direction;

    private Light(
            final boolean point, final double luminosity,
            final double radius, final double z,
            final Coord2D position, final Color color,
            final double[] direction
    ) {
        this.point = point;

        this.luminosity = luminosity;
        this.radius = radius;
        this.z = z;
        this.position = position;
        this.color = color;
        this.direction = direction;
    }

    public static Light directionLight(
            final double luminosity, final Color color,
            final double[] direction
    ) {
        return new Light(false, luminosity,
                0d, 0d, new Coord2D(), color, direction);
    }

    public static Light pointLight(
            final double luminosity, final Color color,
            final double radius, final Coord2D position,
            final double z
    ) {
        return new Light(true, luminosity, radius, z,
                position, color, new double[0]);
    }

    public void setLuminosity(final double luminosity) {
        this.luminosity = luminosity;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public void setDirection(final double[] direction) {
        this.direction = direction;
    }

    public void setPosition(final Coord2D position) {
        this.position = position;
    }

    public void setRadius(final double radius) {
        this.radius = radius;
    }

    public void setZ(final double z) {
        this.z = z;
    }

    public double getLuminosity() {
        return luminosity;
    }

    public Color getColor() {
        return color;
    }

    public double[] getDirection() {
        return direction;
    }

    public Coord2D getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    public double getZ() {
        return z;
    }
}
