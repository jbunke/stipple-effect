package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.awt.*;

import static com.jordanbunke.stipple_effect.utility.math.ColorMath.arrayAsColor;
import static com.jordanbunke.stipple_effect.utility.math.ColorMath.rgbAsArray;

public class LightingUtils {
    public static final int NX = 0, NY = 1, NZ = 2, N_DIMS = 3;

    private static final double IN_FRONT = 1d;

    public enum LightDirection {
        DL_TOP(new double[] { 0d, 1d, IN_FRONT }),
        DL_LEFT(new double[] { -1d, 0d, IN_FRONT }),
        DL_RIGHT(new double[] { 1d, 0d, IN_FRONT }),
        DL_BOTTOM(new double[] { 0d, -1d, IN_FRONT }),
        DL_TL(new double[] { -1d, 1d, IN_FRONT }),
        DL_TR(new double[] { 1d, 1d, IN_FRONT }),
        DL_BL(new double[] { -1d, -1d, IN_FRONT }),
        DL_BR(new double[] { 1d, -1d, IN_FRONT }),
        DL_DIRECT(new double[] { 0d, 0d, IN_FRONT });

        public final double[] vector;

        LightDirection(final double[] vector) {
            normalizeVector(vector);
            this.vector = vector;
        }
    }

    public static double[] surfaceNormalFromMap(final Color normalMapC) {
        final double[] vector = rgbAsArray(normalMapC);

        for (int dim = 0; dim < N_DIMS; dim++)
            vector[dim] = (2 * vector[dim]) - 1d;

        normalizeVector(vector);

        return vector;
    }

    private static double length(final double[] vector) {
        return Math.sqrt((vector[NX] * vector[NX]) +
                (vector[NY] * vector[NY]) + (vector[NZ] * vector[NZ]));
    }

    private static void normalizeVector(final double[] vector) {
        final double length = length(vector);

        if (length != 0d)
            for (int dim = 0; dim < N_DIMS; dim++)
                vector[dim] /= length;
    }

    public static double dotProduct(
            final double[] normal, final double[] lightDir
    ) {
        final double dp = (normal[NX] * lightDir[NX]) +
                (normal[NY] * lightDir[NY]) + (normal[NZ] * lightDir[NZ]);

        return Math.max(dp, 0d);
    }

    public static double[] lightPixel(
            final Color textureC, final Color normalMapC, final Color lightC,
            final double luminosity, final double[] lightDir
    ) {
        final double[] normal = surfaceNormalFromMap(normalMapC);
        final double dotProduct = dotProduct(normal, lightDir),
                intensity = normalMapC.getAlpha() == 0
                        ? luminosity : dotProduct * luminosity;

        final double[] rgbTexture = rgbAsArray(textureC),
                rgbLight = rgbAsArray(lightC),
                rgbApplied = new double[N_DIMS];

        for (int dim = 0; dim < N_DIMS; dim++)
            rgbApplied[dim] = rgbTexture[dim] * rgbLight[dim] * intensity;

        return rgbApplied;
    }

    public static GameImage processLighting(
            final GameImage texture, final GameImage normalMap,
            final Light[] lights
    ) {
        if (texture.getWidth() != normalMap.getWidth() ||
                texture.getHeight() != normalMap.getHeight())
            return texture;

        final int w = texture.getWidth(), h = texture.getHeight();
        final GameImage lit = new GameImage(w, h);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final Color textureC = texture.getColorAt(x, y),
                        normalMapC = normalMap.getColorAt(x, y);

                if (textureC.getAlpha() == 0)
                    continue;

                final double[] accum = new double[N_DIMS];

                for (Light light : lights) {
                    final double luminosity;
                    final double[] lightDir;

                    if (light.point) {
                        final Coord2D lightSource = light.getPosition();
                        final double distance = Coord2D.unitDistanceBetween(
                                lightSource, new Coord2D(x, y)),
                                effect = 1d - (distance / light.getRadius());

                        luminosity = effect * light.getLuminosity();
                        lightDir = getLightDir(lightSource, x, y, light.getZ());
                    } else {
                        luminosity = light.getLuminosity();
                        lightDir = light.getDirection();
                    }

                    final double[] litPixel = lightPixel(textureC,
                            normalMapC, light.getColor(), luminosity, lightDir);

                    for (int dim = 0; dim < N_DIMS; dim++)
                        accum[dim] = accum[dim] + litPixel[dim];
                }

                lit.dot(arrayAsColor(accum), x, y);
            }
        }

        return lit.submit();
    }

    private static double[] getLightDir(
            final Coord2D lightSource, final int x, final int y, final double z
    ) {
        final double[] lightDir = new double[] {
                (double)(lightSource.x - x),
                (double)(y - lightSource.y),
                z
        };

        normalizeVector(lightDir);

        return lightDir;
    }
}
