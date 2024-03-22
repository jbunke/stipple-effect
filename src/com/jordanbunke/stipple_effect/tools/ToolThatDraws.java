package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.MathPlus;
import com.jordanbunke.delta_time.utility.RNG;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalSlider;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class ToolThatDraws extends Tool {
    public enum Mode {
        NORMAL, DITHERING, BLEND, NOISE
    }

    public enum DitherStage {
        ONE, TWO, THREE, FOUR, FIVE, SIX,
        SEVEN,
        EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN;

        String getPercentage() {
            return switch (this) {
                case ONE -> "6.25"; // 1/16
                case TWO -> "12.5"; // 2/16
                case THREE -> "18.75"; // 3/16
                case FOUR -> "25"; // 4/16
                case FIVE -> "31.25"; // 5/16
                case SIX -> "37.5"; // 6/16
                case SEVEN -> "50"; // 8/16
                case EIGHT -> "62.5"; // 10/16
                case NINE -> "68.75"; // 11/16
                case TEN -> "75"; // 12/16
                case ELEVEN -> "81.25"; // 13/16
                case TWELVE -> "87.5"; // 14/16
                case THIRTEEN -> "93.75"; // 15/16
            };
        }

        boolean condition(final int x, final int y) {
            return switch (this) {
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
                case SEVEN -> x % 2 != y % 2;
                case EIGHT -> SEVEN.condition(x, y) ||
                        (x % 4 == 2 && y % 4 == 0) ||
                        (x % 4 == 0 && y % 4 == 2);
                case NINE -> EIGHT.condition(x, y) ||
                        ((x % 4) + (y % 4) == 0);
                case TEN -> !(x % 2 == 1 && y % 2 == 1);
                case ELEVEN -> TEN.condition(x, y) ||
                        (x % 4 == 1 && y % 4 == 3);
                case TWELVE -> ELEVEN.condition(x, y) ||
                        (x % 4 == 3 && y % 4 == 1);
                case THIRTEEN -> TWELVE.condition(x, y) ||
                        ((x % 4) + (y % 4) == 6);
            };
        }
    }

    private static Mode mode = Mode.NORMAL;
    private static DitherStage ditherStage = DitherStage.SEVEN;

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

        final int optionsBarTextY = Layout.getToolOptionsBarPosition().y +
                Layout.TEXT_Y_OFFSET,
                optionsBarButtonY = Layout.getToolOptionsBarPosition().y +
                        Layout.BUTTON_OFFSET;

        // dither label
        final DynamicLabel ditherLabel = new DynamicLabel(
                new Coord2D(getDitherTextX(), optionsBarTextY),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                () -> "Dither: " +
                        ditherStage.getPercentage() + "% primary",
                getDitherDecrementButtonX() - getDitherTextX());

        // dither decrement and increment buttons
        final IconButton decButton = IconButton.makeNoTooltip(
                IconCodes.DECREMENT, new Coord2D(
                        getDitherDecrementButtonX(), optionsBarButtonY),
                () -> setDitherStage(ditherStage.ordinal() - 1)),
                incButton = IconButton.makeNoTooltip(IconCodes.INCREMENT,
                        new Coord2D(getDitherIncrementButtonX(),
                                optionsBarButtonY),
                        () -> setDitherStage(ditherStage.ordinal() + 1));

        // dither slider
        final HorizontalSlider ditherSlider = new HorizontalSlider(
                new Coord2D(getDitherSliderX(), optionsBarButtonY),
                Layout.optionsBarSliderWidth(), MenuElement.Anchor.LEFT_TOP,
                0, DitherStage.values().length - 1,
                () -> ditherStage.ordinal(),
                x -> ditherStage = DitherStage.values()[x]);
        ditherSlider.updateAssets();

        return new MenuElementGrouping(super.buildToolOptionsBar(),
                ditherLabel, decButton, incButton, ditherSlider);
    }

    abstract int getDitherTextX();

    private int getDitherDecrementButtonX() {
        return getDitherTextX() + (int)(Layout.getToolOptionsBarWidth() * 0.12);
    }

    private int getDitherIncrementButtonX() {
        return getDitherDecrementButtonX() + Layout.BUTTON_INC;
    }

    private int getDitherSliderX() {
        return getDitherIncrementButtonX() + Layout.BUTTON_INC;
    }
}
