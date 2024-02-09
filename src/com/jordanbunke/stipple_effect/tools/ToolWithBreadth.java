package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.SECursor;

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
        Arrays.stream(Constants.ALL_TOOLS).forEach(t -> {
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
        this.breadth = Math.max(Constants.MIN_BREADTH,
                Math.min(breadth, Constants.MAX_BREADTH));

        drawOverlay();
    }
}
