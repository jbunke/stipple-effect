package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.SECursor;

import java.awt.*;
import java.util.Set;

public final class LineTool extends ToolWithBreadth {
    private static final LineTool INSTANCE;

    private boolean drawing;
    private Color c;
    private GameImage toolContentPreview;
    private Coord2D anchor;

    static {
        INSTANCE = new LineTool();
    }

    private LineTool() {
        drawing = false;
        c = Constants.BLACK;
        toolContentPreview = GameImage.dummy();

        anchor = Constants.NO_VALID_TARGET;
    }

    public static LineTool get() {
        return INSTANCE;
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
        }
    }

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

            populateAround(toolContentPreview, anchor, selection);
            populateAround(toolContentPreview, tp, selection);

            fillLineSpace(anchor, tp, (x, y) -> populateAround(
                    toolContentPreview, anchor.displace(x, y), selection));

            updateLast(context);
        }
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {
        if (drawing) {
            drawing = false;

            context.paintOverImage(toolContentPreview);

            context.getState().markAsCheckpoint(true, context);
            me.markAsProcessed();
        }
    }

    private void populateAround(
            final GameImage edit, final Coord2D tp,
            final Set<Coord2D> selection
    ) {
        final int halfB = breadthOffset();
        final boolean[][] mask = breadthMask();

        for (int x = 0; x < mask.length; x++)
            for (int y = 0; y < mask[x].length; y++) {
                final Coord2D b = new Coord2D(x + (tp.x - halfB),
                        y + (tp.y - halfB));

                if (!(selection.isEmpty() || selection.contains(b)))
                    continue;

                if (mask[x][y])
                    edit.dot(c, b.x, b.y);
            }
    }

    @Override
    public boolean hasToolContentPreview() {
        return drawing;
    }

    @Override
    public GameImage getToolContentPreview(final ProjectState state) {
        return toolContentPreview;
    }

    @Override
    public String getCursorCode() {
        return SECursor.RETICLE; // TODO - override ToolWithBreadth accessor with same behaviour as Tool accessor if you add static line_tool.png cursor
    }

    @Override
    public String getName() {
        return "Line Tool";
    }

    @Override
    public String getBottomBarText() {
        return getName() + " (" + getBreadth() + " px)";
    }
}
