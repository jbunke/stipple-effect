package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.util.HashSet;
import java.util.Set;

public final class BrushSelect extends ToolWithBreadth implements OverlayTool {
    private static final BrushSelect INSTANCE;

    private boolean selecting;
    private boolean[][] pixels;
    private GameImage selectionOverlay;

    private final Set<Coord2D> recent, now;

    static {
        INSTANCE = new BrushSelect();
    }

    private BrushSelect() {
        selecting = false;
        pixels = new boolean[0][];
        selectionOverlay = GameImage.dummy();

        recent = new HashSet<>();
        now = new HashSet<>();
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

            recent.clear();
            now.clear();

            if (ToolWithMode.getMode() == ToolWithMode.Mode.SINGLE)
                context.deselect(false);

            reset();
        }

        selectionOverlay = GameImage.dummy();
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();
        final ProjectState s = context.getState();

        if (selecting && !tp.equals(Constants.NO_VALID_TARGET)) {
            if (getLastTP().equals(tp))
                return;

            final int w = s.getImageWidth(), h = s.getImageHeight();

            populateAround(tp);

            final Coord2D lastTP = getLastTP();

            if (!lastTP.equals(Constants.NO_VALID_TARGET)) {
                fillLineSpace(lastTP, tp, (x, y) ->
                        populateAround(lastTP.displace(x, y)));
                fillGaps(w, h, lastTP, tp, this::couldBeNextToGap,
                        (x, y) -> {
                    if (inBounds(x, y)) {
                        pixels[x][y] = true;
                        now.add(new Coord2D(x, y));
                    }
                });
            }

            drawSelection(context);
            updateLast(context);

            recent.clear();
            recent.addAll(now);
            now.clear();
        }
    }

    private boolean couldBeNextToGap(final int x, final int y) {
        final Coord2D pixel = new Coord2D(x, y);

        return recent.contains(pixel) || now.contains(pixel);
    }

    private void populateAround(final Coord2D tp) {
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++)
                if (mask[x][y]) {
                    final int px = x + (tp.x - halfB),
                            py = y + (tp.y - halfB);

                    if (inBounds(px, py)) {
                        pixels[px][py] = true;
                        now.add(new Coord2D(px, py));
                    }
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
