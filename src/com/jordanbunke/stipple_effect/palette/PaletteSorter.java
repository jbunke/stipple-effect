package com.jordanbunke.stipple_effect.palette;

import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.EnumUtils;

import java.awt.*;
import java.util.Comparator;
import java.util.function.Function;

public enum PaletteSorter {
    HUE, SATURATION, VALUE;

    @Override
    public String toString() {
        return EnumUtils.formattedName(this);
    }

    private static final Function<Color, Double>
            hf = ColorMath::rgbToHue,
            sf = ColorMath::rgbToSat,
            vf = ColorMath::rgbToValue;

    public Comparator<Color> getComparator() {
        final Function<Color, Double> first = switch (this) {
            case HUE -> hf;
            case SATURATION -> sf;
            case VALUE -> vf;
        }, second = this == SATURATION ? vf : sf, third = this == HUE ? vf : hf;

        return (c1, c2) -> {
            for (int i = 0; i < 3; i++) {
                final Function<Color, Double> func = switch (i) {
                    case 0 -> first;
                    case 1 -> second;
                    default -> third;
                };

                final double res = func.apply(c1) - func.apply(c2);

                if (res != 0d)
                    return (int)Math.signum(res);
            }

            return 0;
        };
    }
}
