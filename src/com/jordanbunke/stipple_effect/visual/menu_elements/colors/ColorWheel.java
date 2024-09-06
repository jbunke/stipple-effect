package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.math.Geometry;

import java.awt.*;

public final class ColorWheel extends AbstractColorMap {

    public ColorWheel(final Coord2D position, final Bounds2D dimensions) {
        super(position, dimensions);
    }

    @Override
    Color getPixelColor(Color c, Coord2D pixel) {
        return ColorMath.fromHSV(getHue(pixel), getSat(pixel),
                ColorMath.fetchValue(c), c.getAlpha());
    }

    @Override
    Coord2D getNodePos(final Color c) {
        final double hue = ColorMath.fetchHue(c),
                sat = ColorMath.fetchSat(c);

        final double angle = hue * Constants.CIRCLE,
                distance = sat * radius;

        return Geometry.projectPoint(middle, angle, distance);
    }

    @Override
    void updateColor(final Coord2D localMP) {
        final double hue = getHue(localMP), sat = getSat(localMP);
        final Color c = StippleEffect.get().getSelectedColor();

        StippleEffect.get().setSelectedColor(
                ColorMath.fromColorWheel(hue, sat, c),
                ColorMath.LastHSVEdit.WHEEL);
    }

    private double getHue(final Coord2D localMP) {
        final double angle = Geometry.normalizeAngle(
                Geometry.calculateAngleInRad(localMP, middle));

        return angle / Constants.CIRCLE;
    }

    private double getSat(final Coord2D localMP) {
        final double distance = Coord2D.unitDistanceBetween(middle, localMP);

        return MathPlus.bounded(0d, distance / (double) radius, 1d);
    }
}
