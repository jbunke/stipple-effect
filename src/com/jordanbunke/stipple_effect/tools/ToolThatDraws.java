package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.MathPlus;
import com.jordanbunke.delta_time.utility.RNG;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.ToolOptionIncrementalRange;

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

        String getPercentage() {
            return switch (this) {
                case ZERO -> "0";
                case ONE -> "6.25";
                case TWO -> "12.5";
                case THREE -> "18.75";
                case FOUR -> "25";
                case FIVE -> "31.25";
                case SIX -> "37.5";
                case SEVEN -> "43.75";
                case FIFTY_FIFTY -> "50";
                case NINE -> "56.25";
                case TEN -> "62.5";
                case ELEVEN -> "68.75";
                case TWELVE -> "75";
                case THIRTEEN -> "81.25";
                case FOURTEEN -> "87.5";
                case FIFTEEN -> "93.75";
                case FULL -> "100";
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
    private static DitherStage ditherStage = DitherStage.FIFTY_FIFTY;

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

    private static void setDitherStage(final int index) {
        ditherStage = DitherStage.values()[MathPlus.bounded(0,
                index, DitherStage.values().length - 1)];
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
            case DITHERING -> (x, y) -> ditherStage.condition(x, y)
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

        // dither label
        final TextLabel ditherLabel = TextLabel.make(
                new Coord2D(getDitherTextX(), Layout.optionsBarTextY()),
                "Dither", Constants.WHITE);

        // dither content
        final String VALUE_SUFFIX = "% primary color";
        final ToolOptionIncrementalRange<Integer> dither =
                ToolOptionIncrementalRange.makeForInt(
                        ditherLabel, 1, 0, DitherStage.values().length - 1,
                        ToolThatDraws::setDitherStage,
                        () -> ditherStage.ordinal(), i -> i, i -> i,
                        ds -> DitherStage.values()[ds]
                                .getPercentage() + VALUE_SUFFIX,
                        "XX.XX" + VALUE_SUFFIX);

        return new MenuElementGrouping(super.buildToolOptionsBar(),
                ditherLabel, dither.decButton, dither.incButton,
                dither.slider, dither.value);
    }

    abstract int getDitherTextX();
}
