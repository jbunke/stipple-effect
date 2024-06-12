package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

public final class BrushSelect extends ToolWithBreadth implements OverlayTool {
    private static final BrushSelect INSTANCE;

    private boolean selecting;
    private boolean[][] pixels;
    private GameImage selectionOverlay;

    static {
        INSTANCE = new BrushSelect();
    }

    private BrushSelect() {
        selecting = false;
        pixels = new boolean[0][];
        selectionOverlay = GameImage.dummy();
    }

    public static BrushSelect get() {
        return INSTANCE;
    }

    @Override
    public String getCursorCode() {
        return super.getCursorCode() + "_" +
                ToolWithMode.getMode().name().toLowerCase();
    }

    @Override
    public String getName() {
        return "Brush Select";
    }

    private void drawSelection(final SEContext context) {
        selectionOverlay = GraphicsUtils.drawSelectionOverlay(
                pixels.length, pixels.length == 0 ? 0 : pixels[0].length,
                context.renderInfo.getZoomFactor(), pixels);
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (!context.getTargetPixel().equals(Constants.NO_VALID_TARGET)) {
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();

            selecting = true;
            pixels = new boolean[w][h];

            if (ToolWithMode.getMode() == ToolWithMode.Mode.SINGLE)
                context.deselect(false);

            reset();
        }

        selectionOverlay = GameImage.dummy();
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();

        if (selecting && !tp.equals(Constants.NO_VALID_TARGET)) {
            if (getLastTP().equals(tp))
                return;

            populateAround(tp);

            fillLineSpace(getLastTP(), tp, (x, y) ->
                    populateAround(getLastTP().displace(x, y)));

            drawSelection(context);
            updateLast(context);
        }
    }

    private void populateAround(final Coord2D tp) {
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++)
                if (mask[x][y]) {
                    final int px = x + (tp.x - halfB),
                            py = y + (tp.y - halfB);

                    if (inBounds(px, py)) pixels[px][py] = true;
                }
    }

    private boolean inBounds(final int x, final int y) {
        return x >= 0 && y >= 0 && x < pixels.length && y < pixels[x].length;
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (selecting) {
            selecting = false;
            me.markAsProcessed();

            context.editSelection(Selection.of(pixels), true);
        }
    }

    @Override
    public GameImage getSelectionOverlay() {
        return selectionOverlay;
    }

    @Override
    public boolean isDrawing() {
        return selecting;
    }
}
