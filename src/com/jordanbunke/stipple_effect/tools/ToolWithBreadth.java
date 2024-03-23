package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.SECursor;
import com.jordanbunke.stipple_effect.visual.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalSlider;

import java.awt.*;
import java.util.Arrays;

public sealed abstract class ToolWithBreadth extends ToolThatDraws implements BreadthTool
        permits Brush, Eraser, BrushSelect, ShadeBrush, LineTool, GradientTool {
    private int breadth;
    private GameImage overlay;

    ToolWithBreadth() {
        breadth = Constants.DEFAULT_BRUSH_BREADTH;
    }

    public static void redrawToolOverlays() {
        Arrays.stream(Tool.getAll()).forEach(t -> {
            if (t instanceof ToolWithBreadth twb)
                twb.drawOverlay();
        });
    }

    public void drawOverlay() {
        final boolean[][] mask = breadthMask();
        final Color outside = Constants.WHITE,
                inside = Constants.ACCENT_BACKGROUND_DARK;

        this.overlay = GraphicsUtils.drawOverlay(mask.length, mask[0].length,
                StippleEffect.get().getContext().renderInfo.getZoomFactor(),
                (x, y) -> mask[x][y], inside, outside, false, false);
    }

    @Override
    public String getCursorCode() {
        return SECursor.RETICLE;
    }

    @Override
    public String getBottomBarText() {
        return getName() + " (" + getBreadth() + " px)";
    }

    @Override
    public int getBreadth() {
        return breadth;
    }

    public GameImage getOverlay() {
        return overlay;
    }

    @Override
    public void setBreadth(final int breadth) {
        this.breadth = MathPlus.bounded(
                Constants.MIN_BREADTH, breadth, Constants.MAX_BREADTH);

        drawOverlay();
    }

    @Override
    public boolean hasToolOptionsBar() {
        return true;
    }

    @Override
    public MenuElementGrouping buildToolOptionsBar() {
        final int optionsBarTextY = Layout.getToolOptionsBarPosition().y +
                Layout.TEXT_Y_OFFSET,
                optionsBarButtonY = Layout.getToolOptionsBarPosition().y +
                        Layout.BUTTON_OFFSET;

        // breadth label
        final DynamicLabel breadthLabel = new DynamicLabel(
                new Coord2D(getBreadthTextX(), optionsBarTextY),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                () -> "Breadth: " + getBreadth() + " px",
                getBreadthDecrementButtonX() - getBreadthTextX());

        // breadth decrement and increment buttons
        final IconButton decButton = IconButton.makeNoTooltip(
                IconCodes.DECREMENT, new Coord2D(
                        getBreadthDecrementButtonX(), optionsBarButtonY),
                () -> setBreadth(getBreadth() - 1)),
                incButton = IconButton.makeNoTooltip(IconCodes.INCREMENT,
                        new Coord2D(getBreadthIncrementButtonX(),
                                optionsBarButtonY),
                        () -> setBreadth(getBreadth() + 1));

        // breadth slider
        final int SLIDER_MULT = 320;
        final HorizontalSlider breadthSlider = new HorizontalSlider(
                new Coord2D(getBreadthSliderX(), optionsBarButtonY),
                Layout.optionsBarSliderWidth(), MenuElement.Anchor.LEFT_TOP,
                Constants.MIN_BREADTH,
                (int) Math.cbrt(Constants.MAX_BREADTH * SLIDER_MULT),
                () -> (int) Math.cbrt(getBreadth() * SLIDER_MULT),
                x -> setBreadth(((int) Math.round(
                        Math.pow(x, 3))) / SLIDER_MULT), false);
        breadthSlider.updateAssets();

        return new MenuElementGrouping(super.buildToolOptionsBar(),
                breadthLabel, decButton, incButton, breadthSlider);
    }

    private int getBreadthTextX() {
        return Layout.getToolOptionsBarPosition().x +
                (int)(Layout.getToolOptionsBarWidth() * 0.11);
    }

    private int getBreadthDecrementButtonX() {
        return Layout.getToolOptionsBarPosition().x +
                (int)(Layout.getToolOptionsBarWidth() * 0.21);
    }

    private int getBreadthIncrementButtonX() {
        return getBreadthDecrementButtonX() + Layout.BUTTON_INC;
    }

    private int getBreadthSliderX() {
        return getBreadthIncrementButtonX() + Layout.BUTTON_INC;
    }

    @Override
    int getDitherTextX() {
        return getBreadthSliderX() + Layout.optionsBarSliderWidth() +
                Layout.optionsBarSectionBuffer();
    }
}
