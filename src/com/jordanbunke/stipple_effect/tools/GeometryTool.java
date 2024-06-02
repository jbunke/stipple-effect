package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public sealed abstract class GeometryTool extends ToolWithBreadth
        implements SnappableTool permits LineTool, ShapeTool {
    private boolean drawing, snap;
    private Color c;
    private GameImage toolContentPreview;
    private Coord2D anchor;
    private Set<Coord2D> included;

    protected GeometryTool() {
        drawing = false;
        snap = false;
        c = SEColors.def();
        toolContentPreview = GameImage.dummy();

        anchor = Constants.NO_VALID_TARGET;
        included = new HashSet<>();
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        final Coord2D tp = context.getTargetPixel();

        if (!tp.equals(Constants.NO_VALID_TARGET) &&
                me.button != GameMouseEvent.Button.MIDDLE) {
            drawing = true;
            c = me.button == GameMouseEvent.Button.LEFT
                    ? StippleEffect.get().getPrimary()
                    : StippleEffect.get().getSecondary();
            toolContentPreview = GameImage.dummy();

            reset();
            anchor = tp;
            included = new HashSet<>();
        }
    }

    // TODO - update
    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();

        if (drawing && !tp.equals(Constants.NO_VALID_TARGET)) {
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();
            final Set<Coord2D> selection = context.getState().getSelection();

            if (tp.equals(getLastTP()))
                return;

            toolContentPreview = new GameImage(w, h);
            included = new HashSet<>();

            final Coord2D endpoint = isSnap()
                    ? snappedEndpoint(anchor, tp) : tp;
            geoDefinition(anchor, endpoint, selection);

            updateLast(context);
        }
    }

    abstract Coord2D snappedEndpoint(final Coord2D anchor, final Coord2D tp);
    abstract void geoDefinition(final Coord2D anchor, final Coord2D endpoint,
                                final Set<Coord2D> selection);

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (drawing) {
            drawing = false;

            context.paintOverImage(toolContentPreview);

            context.getState().markAsCheckpoint(true);
            me.markAsProcessed();
        }
    }

    protected void populateAround(
            final Coord2D tp, final Set<Coord2D> selection
    ) {
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++) {
                final Coord2D b = new Coord2D(x + (tp.x - halfB),
                        y + (tp.y - halfB));

                if (!(selection.isEmpty() || selection.contains(b)))
                    continue;

                if (mask[x][y] && !included.contains(b)) {
                    toolContentPreview.dot(c, b.x, b.y);
                    included.add(b);
                }
            }
    }

    @Override
    public void setSnap(final boolean snap) {
        this.snap = snap;
    }

    @Override
    public boolean hasToolContentPreview() {
        return drawing;
    }

    @Override
    public GameImage getToolContentPreview() {
        return toolContentPreview;
    }

    @Override
    public boolean isSnap() {
        return snap;
    }
}
