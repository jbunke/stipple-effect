package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.math.LineMath;
import com.jordanbunke.stipple_effect.utility.math.LineSegment;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class PolygonSelect extends ToolWithMode implements SnappableTool {
    private static final PolygonSelect INSTANCE;

    private boolean selecting, finishing, wasFinishing;
    private Coord2D lastTP;
    private List<Coord2D> vertices;
    private Set<Coord2D> edges;
    private GameImage toolContentPreview;

    static {
        INSTANCE = new PolygonSelect();
    }

    private PolygonSelect() {
        selecting = false;
        finishing = false;
        wasFinishing = false;
        lastTP = Constants.NO_VALID_TARGET;
        vertices = new ArrayList<>();
        edges = new HashSet<>();

        toolContentPreview = GameImage.dummy();
    }

    public static PolygonSelect get() {
        return INSTANCE;
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

            if (selecting) {
                // add to selection
                if (finishing) {
                    final Coord2D first = vertices.get(0);
                    addEdge(getLastVertex(), first);
                    vertices.add(first);

                    finish(context);
                } else {
                    addEdge(getLastVertex(), tp);
                    vertices.add(tp);

                    if (tp.equals(vertices.get(0)))
                        finish(context);
                }
            } else if (tp.x >= 0 && tp.x < w && tp.y >= 0 && tp.y < h) {
                // Start selection
                // bounds check only necessary for first vertex
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

        if (!selecting || tp.equals(Constants.NO_VALID_TARGET) ||
                (tp.equals(lastTP) && finishing == wasFinishing))
            return;

        updateToolContentPreview(context);

        lastTP = tp;
        wasFinishing = finishing;
    }

    private void finish(final SEContext context) {
        // define bounding box
        final Coord2D tl = SelectionUtils.topLeft(
                new HashSet<>(vertices)).displace(-1, -1),
                br = SelectionUtils.bottomRight(
                        new HashSet<>(vertices)).displace(1, 1);

        // define selection and populate
        final Set<Coord2D> selection = new HashSet<>(edges),
                removalCandidates = new HashSet<>();

        for (int x = tl.x; x < br.x; x++) {
            for (int y = tl.y; y < br.y; y++) {
                final Coord2D pixel = new Coord2D(x, y);
                if (selection.contains(pixel))
                    continue;

                final LineSegment raycast = new LineSegment(pixel, tl);
                final Set<Coord2D> crossedVertices = new HashSet<>(),
                        doubleBackVertices = new HashSet<>();
                int edgeEncounters = 0;

                for (int i = 1; i < vertices.size(); i++) {
                    final LineSegment edge = new LineSegment(
                            vertices.get(i - 1), vertices.get(i));

                    if (LineMath.linesIntersect(raycast, edge)) {
                        final int X = 0, Y = 1;
                        final double[] p = LineMath.intersection(raycast, edge);

                        final boolean isPixel = p[X] - (int)p[X] == 0d &&
                                p[Y] - (int)p[Y] == 0d;

                        if (isPixel) {
                            final Coord2D point = new Coord2D((int)p[X], (int)p[Y]);

                            // ensures that multiple-time intersections
                            // of the same vertex are not counted as
                            // multiple edge encounters
                            if (vertices.contains(point)) {
                                removalCandidates.add(pixel);

                                /* raycast crosses edges adjoining vertex or
                                 * edges double back
                                 * ========================
                                 * Recall for next and previous definitions that
                                 * first and last vertex in vertices are the same
                                 */
                                final int vIndex = vertices.indexOf(point);
                                final Coord2D next = vIndex == vertices.size() - 1
                                        ? vertices.get(1)
                                        : vertices.get(vIndex + 1),
                                        previous = vIndex == 0
                                                ? vertices.get(vertices.size() - 2)
                                                : vertices.get(vIndex - 1);
                                final LineSegment adjacents =
                                        new LineSegment(previous, next);
                                final boolean sameSlope = adjacents.slope() == raycast.slope(),
                                        bothUndefined = adjacents.isSlopeUndefined() &&
                                                raycast.isSlopeUndefined();

                                if (sameSlope || bothUndefined)
                                    doubleBackVertices.add(point);
                                else {
                                    final double[] adjP = LineMath.intersection(raycast, adjacents);
                                    if (adjacents.pointOnLine(adjP[X], adjP[Y]))
                                        crossedVertices.add(point);
                                    else
                                        doubleBackVertices.add(point);
                                }
                            }
                        }

                        edgeEncounters++;
                    }
                }

                edgeEncounters -= crossedVertices.size();
                edgeEncounters -= (2 * doubleBackVertices.size());

                if (edgeEncounters % 2 == 1)
                    selection.add(pixel);
            }
        }

        // remove stragglers that were added by intersecting a single
        // vertex from outside the selection polygon
        final Set<Coord2D> toRemove = new HashSet<>();

        for (Coord2D pixel : removalCandidates)
            if (selection.contains(pixel) && !(
                    selection.contains(pixel.displace(-1, 0)) &&
                    selection.contains(pixel.displace(1, 0)) &&
                    selection.contains(pixel.displace(0, -1)) &&
                    selection.contains(pixel.displace(0, 1))))
                toRemove.add(pixel);

        selection.removeAll(toRemove);

        // edit selection
        context.editSelection(selection, true);
        reset();
    }

    private void updateToolContentPreview(final SEContext context) {
        final Theme t = Settings.getTheme();

        final int w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();
        final Coord2D tp = context.getTargetPixel(),
                first = vertices.get(0),
                prospect = finishing ? first : tp;

        final Color border = prospect.equals(first)
                ? t.highlightOutline : t.highlightOverlay;

        toolContentPreview = new GameImage(w, h);

        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (x == 0 && y == 0)
                    continue;

                final Color c = (x + y) % 2 == 0
                        ? t.textLight : t.textDark;

                toolContentPreview.dot(c, first.x + x, first.y + y);
            }
        }

        edges.forEach(e ->
                toolContentPreview.dot(border, e.x, e.y));
        defineLine(getLastVertex(), prospect).forEach(next ->
                toolContentPreview.dot(border, next.x, next.y));
        vertices.forEach(v ->
                toolContentPreview.dot(t.highlightOutline, v.x, v.y));
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
    public boolean previewScopeIsGlobal() {
        return true;
    }

    @Override
    public GameImage getToolContentPreview() {
        return toolContentPreview;
    }

    @Override
    public void setSnap(final boolean finishing) {
        this.finishing = finishing;
    }

    @Override
    public boolean isSnap() {
        return finishing;
    }
}
