package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.utility.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.SECursor;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.ToolOptionIncrementalRange;

import java.awt.*;
import java.util.Arrays;

public sealed abstract class ToolWithBreadth extends ToolThatDraws implements BreadthTool
        permits Brush, Eraser, BrushSelect, ShadeBrush, LineTool, GradientTool {
    private int breadth;
    private GameImage overlay;

    // formatting only
    private int ditherTextX;

    ToolWithBreadth() {
        breadth = Constants.DEFAULT_BRUSH_BREADTH;

        ditherTextX = 0;
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
        // breadth label
        final TextLabel breadthLabel = TextLabel.make(
                getFirstOptionLabelPosition(),
                "Breadth", Constants.WHITE);

        // breadth content
        final int ARTICULATIONS = 40,
                SLIDER_MULT = (int) Math.pow(ARTICULATIONS, 3) /
                        Constants.MAX_BREADTH;
        final ToolOptionIncrementalRange<Integer> breadth =
                ToolOptionIncrementalRange.makeForInt(breadthLabel, 1,
                        Constants.MIN_BREADTH, Constants.MAX_BREADTH,
                        this::setBreadth, this::getBreadth,
                        b -> (int) Math.cbrt(b * SLIDER_MULT),
                        sv -> ((int) Math.round(
                                Math.pow(sv, 3))) / SLIDER_MULT,
                        b -> b + " px", Constants.MAX_BREADTH + " px");

        ditherTextX = Layout.optionsBarNextElementX(breadth.value, true);

        return new MenuElementGrouping(super.buildToolOptionsBar(),
                breadthLabel, breadth.decButton, breadth.incButton,
                breadth.slider, breadth.value);
    }

    @Override
    int getDitherTextX() {
        return ditherTextX;
    }
}
