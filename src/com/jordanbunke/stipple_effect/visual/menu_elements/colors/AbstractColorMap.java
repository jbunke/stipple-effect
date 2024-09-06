package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;

public abstract class AbstractColorMap extends TwoDimSampler {
    private GameImage wheel;
    final GameImage background;

    final int radius;
    final Coord2D localOffset, middle;

    AbstractColorMap(final Coord2D position, final Bounds2D dimensions) {
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

    void setWheel(final GameImage wheel) {
        this.wheel = wheel;
    }

    @Override
    final GameImage drawBackground() {
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
    final void updateAssets(final Color c) {
        final int w = getWidth(), h = getHeight(),
                ew = getEffectiveWidth(), eh = getEffectiveHeight();
        final GameImage wheel = new GameImage(w, h);

        // border
        final int ox = localOffset.x, oy = localOffset.y;
        wheel.drawOval(Settings.getTheme().buttonOutline,
                2f * BORDER_PX, ox, oy, ew, eh);

        // background
        wheel.draw(background);

        // wheel
        for (int x = ox; x < ox + ew; x++) {
            for (int y = oy; y < oy + eh; y++) {
                final Coord2D p = new Coord2D(x, y);

                if (Coord2D.unitDistanceBetween(middle, p) > radius)
                    continue;

                final Color pixel = getPixelColor(c, p);
                wheel.dot(pixel, x, y);
            }
        }

        // pointer
        final Coord2D nodePos = getNodePos(c);
        drawNode(wheel, nodePos.x, nodePos.y);

        setWheel(wheel.submit());
    }

    @Override
    final int getEffectiveWidth() {
        return radius * 2;
    }

    @Override
    final int getEffectiveHeight() {
        return radius * 2;
    }

    @Override
    public final void render(final GameImage canvas) {
        draw(wheel, canvas);
    }
}
