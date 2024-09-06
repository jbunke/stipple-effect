package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;

import java.awt.*;

public final class NormalMapSampler extends AbstractColorMap {
    public static final int NONE = 1, MAX = 50;

    private static int quantization = NONE;

    public NormalMapSampler(final Coord2D position, final Bounds2D dimensions) {
        super(position, dimensions);
    }

    @Override
    Color getPixelColor(final Color c, final Coord2D pixel) {
        double x = x(pixel), y = y(pixel);
        double hypotenuse = Math.sqrt((x * x) + (y * y));

        if (hypotenuse > 1d) {
            x /= hypotenuse;
            y /= hypotenuse;
        }

        final int r = channel(x), g = channel(y);

        if (notQuantized()) {
            final double z = Math.sqrt(1d - ((x * x) + (y * y)));
            return new Color(r, g, channel(z), Constants.RGBA_SCALE);
        }

        // determines x and y from quantized red and green values
        x = vectorDim(r);
        y = vectorDim(g);
        hypotenuse = Math.sqrt((x * x) + (y * y));

        if (hypotenuse > 1d) {
            x /= hypotenuse;
            y /= hypotenuse;
        }

        final double z = Math.sqrt(1d - ((x * x) + (y * y)));
        return new Color(r, g, channel(z), Constants.RGBA_SCALE);
    }

    @Override
    Coord2D getNodePos(final Color c) {
        double x = vectorDim(c.getRed()) * radius,
                y = -vectorDim(c.getGreen()) * radius;

        final double hypotenuse = Math.sqrt((x * x) + (y * y));

        if (hypotenuse > radius) {
            x *= radius / hypotenuse;
            y *= radius / hypotenuse;
        }

        return middle.displace(
                (int)Math.round(x), (int)Math.round(y));
    }

    @Override
    void updateColor(final Coord2D localMP) {
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
        return quantize((int) Math.round(Constants.RGBA_SCALE * adjusted));
    }

    private int quantize(final int input) {
        if (notQuantized())
            return input;

        final int quantized = ((input / quantization) * quantization) +
                (quantization / 2);
        return MathPlus.bounded(0, quantized, Constants.RGBA_SCALE);
    }

    private boolean notQuantized() {
        return quantization == NONE;
    }

    public void setQuantization(final int quantization) {
        NormalMapSampler.quantization =
                MathPlus.bounded(NONE, quantization, MAX);

        final Color c = StippleEffect.get().getSelectedColor(),
                input = getPixelColor(c, getNodePos(c));

        updateAssets(input);
    }

    public int getQuantization() {
        return quantization;
    }

    private double vectorDim(final int channel) {
        final double scaled = channel / (double) Constants.RGBA_SCALE;
        return (2d * scaled) - 1d;
    }
}
