package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;
import java.util.List;

public final class SatValMatrix extends MenuElement {
    private static final int BORDER_PX = Layout.BUTTON_BORDER_PX,
            BUFFER_PX = BORDER_PX * 2;

    private Color lastC;
    private final GameImage background;
    private GameImage matrix;
    private boolean interacting;

    public SatValMatrix(final Coord2D position, final Bounds2D dimensions) {
        super(position, dimensions, Anchor.LEFT_TOP, true);

        interacting = false;
        background = drawBackground();
        updateAssets(StippleEffect.get().getSelectedColor());
    }

    private GameImage drawBackground() {
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

    private void updateAssets(final Color c) {
        final int w = getWidth(), h = getHeight(),
                ew = getEffectiveWidth(), eh = getEffectiveHeight();
        final GameImage matrix = new GameImage(w, h);

        // border
        matrix.drawRectangle(Settings.getTheme().buttonOutline,
                2f * BORDER_PX, BUFFER_PX, BUFFER_PX, ew, eh);

        // checkerboard background
        matrix.draw(background, BUFFER_PX, BUFFER_PX);

        final double hue = ColorMath.rgbToHue(c),
                sat = ColorMath.rgbToSat(c),
                value = ColorMath.rgbToValue(c);

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
        final GameImage node = GraphicsUtils.COLOR_NODE;
        final int px = getLocalXForVal(value),
                py = getLocalYForSat(sat),
                halfNode = node.getWidth() / 2;
        matrix.draw(node, px - halfNode, py - halfNode);

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

    private int getEffectiveWidth() {
        return getWidth() - (2 * BUFFER_PX);
    }

    private int getEffectiveHeight() {
        return getHeight() - (2 * BUFFER_PX);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        final Coord2D mp = eventLogger.getAdjustedMousePosition();
        final boolean mouseInBounds = mouseIsWithinBounds(mp);
        final Coord2D localMP = mp.displace(getRenderPosition().scale(-1));

        if (interacting) {
            final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();
            for (GameEvent e : unprocessed) {
                if (e instanceof GameMouseEvent me &&
                        me.matchesAction(GameMouseEvent.Action.UP)) {
                    interacting = false;
                    me.markAsProcessed();
                    break;
                }
            }
        }

        if (mouseInBounds) {
            // check for mouse down and for click
            final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();
            for (GameEvent e : unprocessed) {
                if (e instanceof GameMouseEvent me &&
                        !me.matchesAction(GameMouseEvent.Action.UP)) {
                    switch (me.action) {
                        case DOWN -> interacting = true;
                        case CLICK -> {
                            updateColor(localMP);
                            interacting = false;
                        }
                    }

                    me.markAsProcessed();
                    break;
                }
            }
        }

        // adjustment logic
        if (interacting)
            updateColor(localMP);
    }

    private void updateColor(final Coord2D localMP) {
        final double sat = getSat(localMP.y), value = getVal(localMP.x);
        final Color c = StippleEffect.get().getSelectedColor(),
                newC = ColorMath.fromHSV(ColorMath.rgbToHue(c),
                        sat, value, c.getAlpha());

        StippleEffect.get().setSelectedColor(newC,
                ColorMath.LastHSVEdit.NONE);
    }

    @Override
    public void update(final double deltaTime) {
        final Color c = StippleEffect.get().getSelectedColor();

        if (!c.equals(lastC))
            updateAssets(c);

        lastC = c;
    }

    @Override
    public void render(final GameImage canvas) {
        draw(matrix, canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
