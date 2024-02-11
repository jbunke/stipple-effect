package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.MathPlus;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.SECursor;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class PolygonSelect extends ToolWithMode {
    private static final PolygonSelect INSTANCE;

    private boolean selecting;
    private Coord2D lastTP;
    private List<Coord2D> vertices;
    private Set<Coord2D> edges;
    private GameImage toolContentPreview;

    static {
        INSTANCE = new PolygonSelect();
    }

    private PolygonSelect() {
        selecting = false;
        lastTP = Constants.NO_VALID_TARGET;
        vertices = new ArrayList<>();
        edges = new HashSet<>();

        toolContentPreview = GameImage.dummy();
    }

    public static PolygonSelect get() {
        return INSTANCE;
    }

    private enum Direction {
        LEFT, RIGHT, UP, DOWN;

        Coord2D getIncrement() {
            return switch (this) {
                case LEFT -> new Coord2D(-1, 0);
                case RIGHT -> new Coord2D(1, 0);
                case UP -> new Coord2D(0, -1);
                case DOWN -> new Coord2D(0, 1);
            };
        }

        int getBoundDistance(
                final Coord2D tl, final Coord2D br, final Coord2D pixel
        ) {
            return switch (this) {
                case LEFT -> pixel.x - tl.x;
                case RIGHT -> br.x - pixel.x;
                case UP -> pixel.y - tl.y;
                case DOWN -> br.y - pixel.y;
            };
        }
    }

    @Override
    public String getName() {
        return "Polygon Select";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        final Coord2D tp = context.getTargetPixel();

        if (ToolWithMode.getMode() == ToolWithMode.Mode.SINGLE)
            context.deselect(false);

        if (me.button == GameMouseEvent.Button.MIDDLE || tp.equals(Constants.NO_VALID_TARGET))
            reset();
        else if (me.button == GameMouseEvent.Button.RIGHT && selecting) {
            vertices.remove(vertices.size() - 1);

            if (vertices.isEmpty())
                reset();
            else {
                edges = new HashSet<>();

                for (int i = 1; i < vertices.size(); i++)
                    addEdge(vertices.get(i - 1), vertices.get(i));
            }
        } else {
            // bounds check
            final int w = context.getState().getImageWidth(),
                    h = context.getState().getImageHeight();

            if (tp.x < 0 || tp.x >= w || tp.y < 0 || tp.y >= h)
                return;

            if (selecting) {
                // add to selection
                addEdge(getLastVertex(), tp);

                if (tp.equals(vertices.get(0))) {
                    // finish selection

                    // define bounding box
                    final Coord2D tl = SelectionUtils.topLeft(new HashSet<>(vertices)),
                            br = SelectionUtils.bottomRight(new HashSet<>(vertices));

                    // define selection and populate
                    final Set<Coord2D> selection = new HashSet<>(edges);

                    for (int x = tl.x; x < br.x; x++) {
                        for (int y = tl.y; y < br.y; y++) {
                            final Coord2D pixel = new Coord2D(x, y);
                            final Direction bestDir = MathPlus.findBest(
                                    Direction.LEFT, Integer.MAX_VALUE,
                                    d -> d.getBoundDistance(tl, br, pixel),
                                    (a, b) -> a < b, Direction.values());

                            final int distance = bestDir.getBoundDistance(tl, br, pixel);
                            int edgeEncounters = 0;
                            boolean lastWasEdge = false;
                            Coord2D scanPixel = pixel;

                            for (int i = 0; i <= distance + 1; i++) {
                                final boolean isEdge = edges.contains(scanPixel);

                                if (lastWasEdge && !isEdge)
                                    edgeEncounters++;

                                lastWasEdge = isEdge;
                                scanPixel = scanPixel.displace(bestDir.getIncrement());
                            }

                            if (edgeEncounters % 2 == 1)
                                selection.add(pixel);
                        }
                    }

                    // edit selection
                    context.editSelection(selection, true);
                    reset();
                } else
                    vertices.add(tp);
            } else {
                // Start selection
                selecting = true;
                vertices = new ArrayList<>();
                vertices.add(tp);
            }
        }

        if (selecting)
            updateToolContentPreview(context);
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        final Coord2D tp = context.getTargetPixel();

        if (!selecting || tp.equals(Constants.NO_VALID_TARGET) || tp.equals(lastTP))
            return;

        updateToolContentPreview(context);

        lastTP = tp;
    }

    @Override
    public void onMouseUp(final SEContext context, final GameMouseEvent me) {}

    private void updateToolContentPreview(final SEContext context) {
        final int w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();
        final Coord2D tp = context.getTargetPixel(), first = vertices.get(0);

        final Color border = tp.equals(first)
                ? Constants.HIGHLIGHT_1
                : Constants.HIGHLIGHT_2;

        toolContentPreview = new GameImage(w, h);

        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (x == 0 && y == 0)
                    continue;

                final Color c = (x + y) % 2 == 0
                        ? Constants.WHITE
                        : Constants.BLACK;

                toolContentPreview.dot(c, first.x + x, first.y + y);
            }
        }

        edges.forEach(e ->
                toolContentPreview.dot(border, e.x, e.y));
        defineLine(getLastVertex(), tp).forEach(next ->
                toolContentPreview.dot(border, next.x, next.y));
        vertices.forEach(v ->
                toolContentPreview.dot(Constants.HIGHLIGHT_1, v.x, v.y));
    }

    private void addEdge(final Coord2D v1, final Coord2D v2) {
        edges.addAll(defineLine(v1, v2));
    }

    private Set<Coord2D> defineLine(final Coord2D v1, final Coord2D v2) {
        final Set<Coord2D> line = new HashSet<>();

        line.add(v1);
        line.add(v2);

        final int diffX = v2.x - v1.x, diffY = v2.y - v1.y,
                biggerDim = (int) Math.abs(MathPlus.maxMagnitude(diffX, diffY));

        for (int i = 1; i < biggerDim; i++) {
            final double fraction = i / (double) biggerDim;
            final Coord2D displacement = new Coord2D(
                    (int)Math.round(diffX * fraction),
                    (int)Math.round(diffY * fraction)),
                    edgePoint = v1.displace(displacement);
            line.add(edgePoint);
        }

        return line;
    }

    private Coord2D getLastVertex() {
        return vertices.get(vertices.size() - 1);
    }

    private void reset() {
        selecting = false;
        lastTP = Constants.NO_VALID_TARGET;
        vertices = new ArrayList<>();
        edges = new HashSet<>();

        toolContentPreview = GameImage.dummy();
    }

    @Override
    public boolean hasToolContentPreview() {
        return selecting;
    }

    @Override
    public GameImage getToolContentPreview() {
        return toolContentPreview;
    }

    @Override
    public String getCursorCode() {
        return SECursor.RETICLE; // TODO - remove method once ToolWithMode-compliant cursors have been drawn
    }
}
