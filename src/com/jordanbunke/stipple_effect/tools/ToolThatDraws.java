package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.RNG;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class ToolThatDraws extends Tool {
    public enum Mode {
        NORMAL, DITHERING, BLEND, NOISE
    }

    private static Mode mode = Mode.NORMAL;

    private Coord2D lastTP;
    private int lastFrameIndex;

    ToolThatDraws() {
        reset();
    }

    public void updateLast(final SEContext context) {
        lastFrameIndex = context.getState().getFrameIndex();
        lastTP = context.getTargetPixel();
    }

    public boolean isUnchanged(final SEContext context) {
        final boolean sameFrame = stillSameFrame(context),
                sameTP = lastTP.equals(context.getTargetPixel());

        return sameFrame && sameTP;
    }

    public void reset() {
        lastTP = Constants.NO_VALID_TARGET;
        lastFrameIndex = -1;
    }

    public Coord2D getLastTP() {
        return lastTP;
    }

    public boolean stillSameFrame(final SEContext context) {
        return lastFrameIndex == context.getState().getFrameIndex();
    }

    public void fillLineSpace(
            final Coord2D from, final Coord2D to,
            final BiConsumer<Integer, Integer> action
    ) {
        final int xDiff = to.x - from.x,
                yDiff = to.y - from.y,
                xUnit = (int)Math.signum(xDiff),
                yUnit = (int)Math.signum(yDiff);
        if (!from.equals(Constants.NO_VALID_TARGET) &&
                (Math.abs(xDiff) > 1 || Math.abs(yDiff) > 1)) {
            if (Math.abs(xDiff) > Math.abs(yDiff)) {
                for (int x = xUnit; Math.abs(x) < Math.abs(xDiff); x += xUnit) {
                    final int y = (int)Math.round(x * (yDiff / (double)xDiff));
                    action.accept(x, y);
                }
            } else {
                for (int y = yUnit; Math.abs(y) < Math.abs(yDiff); y += yUnit) {
                    final int x = (int)Math.round(y * (xDiff / (double)yDiff));
                    action.accept(x, y);
                }
            }
        }
    }

    public static void setMode(final Mode mode) {
        ToolThatDraws.mode = mode;
    }

    public static Mode getMode() {
        return mode;
    }

    public static BiFunction<Integer, Integer, Color> getColorMode(
            final GameMouseEvent me) {
        return switch (ToolThatDraws.getMode()) {
            case BLEND -> {
                final Color primary = StippleEffect.get().getPrimary(),
                        secondary = StippleEffect.get().getSecondary();
                yield (x, y) -> new Color(
                        (primary.getRed() + secondary.getRed()) / 2,
                        (primary.getGreen() + secondary.getGreen()) / 2,
                        (primary.getBlue() + secondary.getBlue()) / 2,
                        (primary.getAlpha() + secondary.getAlpha()) / 2
                );
            }
            case NOISE -> {
                final Color primary = StippleEffect.get().getPrimary(),
                        secondary = StippleEffect.get().getSecondary();
                final int pr = primary.getRed(), pg = primary.getGreen(),
                        pb = primary.getBlue(), pa = primary.getAlpha(),
                        sr = secondary.getRed(), sg = secondary.getGreen(),
                        sb = secondary.getBlue(), sa = secondary.getAlpha();
                yield (x, y) -> new Color(
                        RNG.randomInRange(Math.min(pr, sr), Math.max(pr, sr)),
                        RNG.randomInRange(Math.min(pg, sg), Math.max(pg, sg)),
                        RNG.randomInRange(Math.min(pb, sb), Math.max(pb, sb)),
                        RNG.randomInRange(Math.min(pa, sa), Math.max(pa, sa))
                );
            }
            case DITHERING -> (x, y) -> (x + y) % 2 == 0
                    ? StippleEffect.get().getPrimary()
                    : StippleEffect.get().getSecondary();
            case NORMAL -> me.button == GameMouseEvent.Button.LEFT
                    ? (x, y) -> StippleEffect.get().getPrimary()
                    : (x, y) -> StippleEffect.get().getSecondary();
        };
    }
}
