package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.math.Geometry;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;

public final class ColorWheel extends TwoDimSampler {
    private GameImage wheel;
    private final GameImage background;

    private final int radius;
    private final Coord2D localOffset, middle;

    public ColorWheel(final Coord2D position, final Bounds2D dimensions) {
        super(position, dimensions);

        final int w = dimensions.width(), h = dimensions.height(),
                minDim = Math.min(w, h);
        radius = (minDim / 2) - BUFFER_PX;

        localOffset = new Coord2D(BUFFER_PX + ((w - minDim) / 2),
                BUFFER_PX + ((h - minDim) / 2));
        middle = localOffset.displace(radius, radius);

        background = drawBackground();
        updateAssets(StippleEffect.get().getSelectedColor());
    }

    @Override
    protected GameImage drawBackground() {
        final Theme t = Settings.getTheme();
        final int w = getWidth(),
                h = getHeight();
        final GameImage background = new GameImage(w, h);

        final Color light = t.checkerboard1, dark = t.checkerboard2;

        final int squareDim = Layout.SLIDER_OFF_DIM / 4;

        for (int x = 0; x < w; x += squareDim) {
            for (int y = 0; y < h; y += squareDim) {
                final Color square = (x + y) % 2 == 0 ? light : dark;

                background.fillRectangle(square, x, y, squareDim, squareDim);
            }
        }

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (Coord2D.unitDistanceBetween(middle,
                        new Coord2D(x, y)) >= radius)
                    background.setRGB(x, y, SEColors.transparent().getRGB());

        return background.submit();
    }

    @Override
    protected void updateAssets(final Color c) {
        final int w = getWidth(), h = getHeight(),
                ew = getEffectiveWidth(), eh = getEffectiveHeight();
        final GameImage wheel = new GameImage(w, h);

        // border
        final int ox = localOffset.x, oy = localOffset.y;
        wheel.drawOval(Settings.getTheme().buttonOutline,
                2f * BORDER_PX, ox, oy, ew, eh);

        // background
        wheel.draw(background);

        final double hue = ColorMath.fetchHue(c),
                sat = ColorMath.fetchSat(c),
                value = ColorMath.fetchValue(c);

        // wheel
        for (int x = ox; x < ox + ew; x++) {
            for (int y = oy; y < oy + eh; y++) {
                final Coord2D p = new Coord2D(x, y);

                if (Coord2D.unitDistanceBetween(middle, p) > radius)
                    continue;

                final double pHue = getHue(p), pSat = getSat(p);
                final Color pixel = ColorMath.fromHSV(
                        pHue, pSat, value, c.getAlpha());
                wheel.dot(pixel, x, y);
            }
        }

        // pointer
        final Coord2D nodePos = getNodePos(hue, sat);
        drawNode(wheel, nodePos.x, nodePos.y);

        this.wheel = wheel.submit();
    }

    private Coord2D getNodePos(final double hue, final double sat) {
        final double angle = hue * Constants.CIRCLE,
                distance = sat * radius;

        return Geometry.projectPoint(middle, angle, distance);
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

    @Override
    protected int getEffectiveWidth() {
        return radius * 2;
    }

    @Override
    protected int getEffectiveHeight() {
        return radius * 2;
    }

    @Override
    protected void updateColor(final Coord2D localMP) {
        final double hue = getHue(localMP), sat = getSat(localMP);
        final Color c = StippleEffect.get().getSelectedColor();

        StippleEffect.get().setSelectedColor(
                ColorMath.fromColorWheel(hue, sat, c),
                ColorMath.LastHSVEdit.WHEEL);
    }

    @Override
    public void render(final GameImage canvas) {
        draw(wheel, canvas);
    }
}
