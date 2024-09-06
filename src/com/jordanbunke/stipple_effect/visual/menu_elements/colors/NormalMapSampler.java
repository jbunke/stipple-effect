package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;

import java.awt.*;

public final class NormalMapSampler extends AbstractColorMap {
    public NormalMapSampler(final Coord2D position, final Bounds2D dimensions) {
        super(position, dimensions);
    }

    @Override
    Color getPixelColor(final Color c, final Coord2D pixel) {
        final double x = x(pixel), y = y(pixel),
                z = Math.sqrt(1d - ((x * x) + (y * y)));

        return new Color(channel(x), channel(y),
                channel(z), Constants.RGBA_SCALE);
    }

    @Override
    Coord2D getNodePos(final Color c) {
        // TODO

        final double x = vectorDim(c.getRed()),
                y = vectorDim(c.getGreen());

        return middle.displace(
                (int)Math.round(x * radius),
                (int)Math.round(-y * radius));
    }

    @Override
    void updateColor(final Coord2D localMP) {
        // TODO - test

        final Color c = StippleEffect.get().getSelectedColor();

        StippleEffect.get().setSelectedColor(
                getPixelColor(c, localMP),
                ColorMath.LastHSVEdit.NONE);
    }

    private double x(final Coord2D pixel) {
        return MathPlus.bounded(-1d,
                (pixel.x - middle.x) / (double) radius, 1d);
    }

    private double y(final Coord2D pixel) {
        return MathPlus.bounded(-1d,
                (middle.y - pixel.y) / (double) radius, 1d);
    }

    private int channel(final double vectorDim) {
        final double adjusted = (vectorDim + 1d) / 2d;
        return (int) Math.round(Constants.RGBA_SCALE * adjusted);
    }

    private double vectorDim(final int channel) {
        final double scaled = channel / (double) Constants.RGBA_SCALE;
        return (2d * scaled) - 1d;
    }
}
