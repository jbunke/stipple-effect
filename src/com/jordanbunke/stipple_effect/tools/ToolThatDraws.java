package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.menu.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.delta_time.utility.math.RNG;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.visual.menu_elements.IncrementalRangeElements;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class ToolThatDraws extends Tool {
    public enum Mode {
        NORMAL, DITHERING, BLEND, NOISE
    }

    public enum DitherStage {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN,
        FIFTY_FIFTY,
        NINE, TEN, ELEVEN, TWELVE, THIRTEEN, FOURTEEN, FIFTEEN, FULL;

        static DitherStage get(final double c) {
            final DitherStage[] ds = DitherStage.values();

            return MathPlus.findBest(
                    DitherStage.FIFTY_FIFTY, DitherStage.FIFTY_FIFTY, d -> d,
                    (d1, d2) -> Math.abs(d1.getFraction() - c) <=
                            Math.abs(d2.getFraction() - c), ds);
        }

        double getFraction() {
            final double pixels = 16d;

            return switch (this) {
                case ZERO -> 0d;
                case ONE -> 1 / pixels;
                case TWO -> 2 / pixels;
                case THREE -> 3 / pixels;
                case FOUR -> 4 / pixels;
                case FIVE -> 5 / pixels;
                case SIX -> 6 / pixels;
                case SEVEN -> 7 / pixels;
                case FIFTY_FIFTY -> 8 / pixels;
                case NINE -> 9 / pixels;
                case TEN -> 10 / pixels;
                case ELEVEN -> 11 / pixels;
                case TWELVE -> 12 / pixels;
                case THIRTEEN -> 13 / pixels;
                case FOURTEEN -> 14 / pixels;
                case FIFTEEN -> 15 / pixels;
                case FULL -> 1d;
            };
        }

        boolean condition(final int x, final int y) {
            return switch (this) {
                case ZERO -> false;
                case ONE -> x % 4 == 2 && y % 4 == 1;
                case TWO -> ONE.condition(x, y) ||
                        (x % 4 == 0 && y % 4 == 3);
                case THREE -> FOUR.condition(x, y) &&
                        !(x % 4 == 2 && y % 4 == 3);
                case FOUR -> x % 2 == 0 && y % 2 == 1;
                case FIVE -> FOUR.condition(x, y) ||
                        (x % 4 == 1 && y % 4 == 2);
                case SIX -> (x % 4) + (y % 4) == 3 ||
                        (x % 4 == 0 && y % 4 == 1) ||
                        (x % 4 == 2 && y % 4 == 3);
                case SEVEN -> SIX.condition(x, y) ||
                        (x % 4 == 3 && y % 4 == 2);
                case FIFTY_FIFTY -> x % 2 != y % 2;
                case NINE -> FIFTY_FIFTY.condition(x, y) ||
                        (x % 4 == 2 && y % 4 == 0);
                case TEN -> NINE.condition(x, y) ||
                        (x % 4 == 0 && y % 4 == 2);
                case ELEVEN -> TEN.condition(x, y) ||
                        ((x % 4) + (y % 4) == 0);
                case TWELVE -> !(x % 2 == 1 && y % 2 == 1);
                case THIRTEEN -> TWELVE.condition(x, y) ||
                        (x % 4 == 1 && y % 4 == 3);
                case FOURTEEN -> THIRTEEN.condition(x, y) ||
                        (x % 4 == 3 && y % 4 == 1);
                case FIFTEEN -> FOURTEEN.condition(x, y) ||
                        ((x % 4) + (y % 4) == 6);
                case FULL -> true;
            };
        }
    }

    private static Mode mode = Mode.NORMAL;
    private static double bias = Constants.UNBIASED;

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

    private static void setBias(final double bias) {
        ToolThatDraws.bias = MathPlus.bounded(
                Constants.MIN_BIAS, bias, Constants.MAX_BIAS);
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
                yield (x, y) -> ColorMath.betweenColor(
                        bias, secondary, primary);
            }
            case NOISE -> {
                final Color primary = StippleEffect.get().getPrimary(),
                        secondary = StippleEffect.get().getSecondary(),
                        reference, complement;

                if (bias < Constants.UNBIASED) {
                    reference = secondary;
                    complement = primary;
                } else {
                    reference = primary;
                    complement = secondary;
                }

                final double consideration = Constants.MAX_BIAS -
                        (2 * Math.abs(Constants.UNBIASED - bias));

                final int rr = reference.getRed(),
                        rg = reference.getGreen(),
                        rb = reference.getBlue(),
                        ra = reference.getAlpha(),
                        dr = (int) ((complement.getRed() - rr) * consideration),
                        dg = (int) ((complement.getGreen() - rg) * consideration),
                        db = (int) ((complement.getBlue() - rb) * consideration),
                        da = (int) ((complement.getAlpha() - ra) * consideration);

                yield (x, y) -> new Color(
                        RNG.randomInRange(Math.min(rr, rr + dr),
                                Math.max(rr, rr + dr) + 1),
                        RNG.randomInRange(Math.min(rg, rg + dg),
                                Math.max(rg, rg + dg) + 1),
                        RNG.randomInRange(Math.min(rb, rb + db),
                                Math.max(rb, rb + db) + 1),
                        RNG.randomInRange(Math.min(ra, ra + da),
                                Math.max(ra, ra + da) + 1));
            }
            case DITHERING -> (x, y) ->
                    DitherStage.get(bias).condition(x, y)
                            ? StippleEffect.get().getPrimary()
                            : StippleEffect.get().getSecondary();
            case NORMAL -> me.button == GameMouseEvent.Button.LEFT
                    ? (x, y) -> StippleEffect.get().getPrimary()
                    : (x, y) -> StippleEffect.get().getSecondary();
        };
    }

    @Override
    public MenuElementGrouping buildToolOptionsBar() {
        if (!this.equals(Brush.get()) && !this.equals(Pencil.get()))
            return super.buildToolOptionsBar();

        // bias label
        final TextLabel biasLabel = TextLabel.make(
                new Coord2D(getDitherTextX(), Layout.optionsBarTextY()),
                "Combination mode bias");

        // bias content
        final int PERCENT = 100;
        final String INFIX = "% towards ", SUFFIX = " color";
        final IncrementalRangeElements<Double> biasElems =
                IncrementalRangeElements.makeForDouble(biasLabel,
                        Layout.optionsBarButtonY(), Layout.optionsBarTextY(),
                        () -> setBias(bias - Constants.BIAS_INC),
                        () -> setBias(bias + Constants.BIAS_INC),
                        Constants.MIN_BIAS, Constants.MAX_BIAS,
                        ToolThatDraws::setBias, () -> bias,
                        b -> (int) (b * PERCENT), sv -> sv / (double) PERCENT,
                        b -> {
                    final int biasPercentage = (int)
                            (Math.abs(Constants.UNBIASED - b) * PERCENT * 2);
                    final String color = b < Constants.UNBIASED
                            ? "secondary" : "primary";

                    return biasPercentage + INFIX + color + SUFFIX;
                    }, "XXX" + INFIX + "secondary" + SUFFIX);

        return new MenuElementGrouping(
                super.buildToolOptionsBar(), biasLabel,
                biasElems.decButton, biasElems.incButton,
                biasElems.slider, biasElems.value
        );
    }

    abstract int getDitherTextX();
}
