package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;

public final class SatValMatrix extends TwoDimSampler {
    private GameImage matrix;
    private final GameImage background;

    public SatValMatrix(final Coord2D position, final Bounds2D dimensions) {
        super(position, dimensions);

        background = drawBackground();
        updateAssets(StippleEffect.get().getSelectedColor());
    }

    @Override
    protected GameImage drawBackground() {
        final Theme t = Settings.getTheme();
        final int w = getEffectiveWidth(),
                h = getEffectiveHeight();
        final GameImage background = new GameImage(w, h);

        final Color light = t.checkerboard1, dark = t.checkerboard2;

        final int squareDim = Layout.SLIDER_OFF_DIM / 4;

        for (int x = 0; x < w; x += squareDim) {
            for (int y = 0; y < h; y += squareDim) {
                final Color square = (x + y) % 2 == 0 ? light : dark;

                background.fillRectangle(square, x, y, squareDim, squareDim);
            }
        }

        return background.submit();
    }

    @Override
    protected void updateAssets(final Color c) {
        final int w = getWidth(), h = getHeight();
        final GameImage matrix = new GameImage(w, h);

        // border
        matrix.drawRectangle(Settings.getTheme().buttonOutline,
                2f * BORDER_PX, BUFFER_PX, BUFFER_PX,
                getEffectiveWidth(), getEffectiveHeight());

        // checkerboard background
        matrix.draw(background, BUFFER_PX, BUFFER_PX);

        final double hue = ColorMath.fetchHue(c),
                sat = ColorMath.fetchSat(c),
                value = ColorMath.fetchValue(c);

        // matrix
        for (int x = BUFFER_PX; x < w - BUFFER_PX; x++) {
            for (int y = BUFFER_PX; y < h - BUFFER_PX; y++) {
                final double pVal = getVal(x), pSat = getSat(y);
                final Color pixel = ColorMath.fromHSV(
                        hue, pSat, pVal, c.getAlpha());
                matrix.dot(pixel, x, y);
            }
        }

        // pointer
        drawNode(matrix, getLocalXForVal(value), getLocalYForSat(sat));

        this.matrix = matrix.submit();
    }

    private int getLocalXForVal(final double value) {
        return BUFFER_PX + (int) Math.round(value * getEffectiveWidth());
    }

    private int getLocalYForSat(final double sat) {
        return BUFFER_PX + (int) Math.round(sat * getEffectiveHeight());
    }

    private double getVal(final int localX) {
        final int pastBorderX = localX - BUFFER_PX,
                effectiveW = getEffectiveWidth();

        return MathPlus.bounded(0d, pastBorderX / (double) effectiveW, 1d);
    }

    private double getSat(final int localY) {
        final int pastBorderY = localY - BUFFER_PX,
                effectiveH = getEffectiveHeight();

        return MathPlus.bounded(0d, pastBorderY / (double) effectiveH, 1d);
    }

    @Override
    protected int getEffectiveWidth() {
        return getWidth() - (2 * BUFFER_PX);
    }

    @Override
    protected int getEffectiveHeight() {
        return getHeight() - (2 * BUFFER_PX);
    }

    @Override
    protected void updateColor(final Coord2D localMP) {
        final double sat = getSat(localMP.y), value = getVal(localMP.x);
        final Color c = StippleEffect.get().getSelectedColor();

        StippleEffect.get().setSelectedColor(
                ColorMath.fromSatValMatrix(sat, value, c),
                ColorMath.LastHSVEdit.SAT_VAL);
    }

    @Override
    public void render(final GameImage canvas) {
        draw(matrix, canvas);
    }
}
