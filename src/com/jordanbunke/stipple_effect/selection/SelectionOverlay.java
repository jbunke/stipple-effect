package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public final class SelectionOverlay {
    private static final int TL = 0, BR = 1, DIM = 2, TRP = 3, BOUNDS = 4;

    // directional frontiers
    private final Set<Coord2D> left, right, top, bottom;
    private final GameImage filled;
    private Coord2D tl;
    private final int w, h;

    private float lastZ;
    private Coord2D lastTL;
    private int lastWW, lastWH;
    private Coord2D lastRender;
    private boolean wasFilled, couldTransform;
    private GameImage last;

    public SelectionOverlay(final Set<Coord2D> selection) {
        lastZ = Constants.MIN_ZOOM;
        lastTL = new Coord2D();
        lastRender = new Coord2D();
        lastWW = 0;
        lastWH = 0;

        left = new HashSet<>();
        right = new HashSet<>();
        top = new HashSet<>();
        bottom = new HashSet<>();

        tl = SelectionUtils.topLeft(selection);
        final Coord2D br = SelectionUtils.bottomRight(selection);

        w = br.x - tl.x;
        h = br.y - tl.y;

        filled = new GameImage(w, h);
        final Color fillC = Settings.getTheme().selectionFill.get();
        filled.setColor(fillC);

        selection.forEach(p -> {
            if (!selection.contains(p.displace(-1, 0))) left.add(p);
            if (!selection.contains(p.displace(1, 0))) right.add(p);
            if (!selection.contains(p.displace(0, -1))) top.add(p);
            if (!selection.contains(p.displace(0, 1))) bottom.add(p);

            filled.dot(p.x - tl.x, p.y - tl.y);
        });

        filled.free();
    }

    public void updateTL(final Set<Coord2D> selection) {
        lastTL = tl;
        tl = SelectionUtils.topLeft(selection);

        if (!lastTL.equals(tl)) {
            final Coord2D displacement = new Coord2D(
                    tl.x - lastTL.x, tl.y - lastTL.y);

            final List<Set<Coord2D>> dirs = List.of(left, right, top, bottom);

            for (Set<Coord2D> dir : dirs) {
                final Set<Coord2D> bank = new HashSet<>(dir);
                dir.clear();

                bank.forEach(p -> dir.add(p.displace(displacement)));
            }
        }
    }

    public GameImage draw(
            final float z, final Coord2D render, final int ww, final int wh,
            final boolean isFilled, final boolean canTransform
    ) {
        if (z < Constants.ZOOM_FOR_OVERLAY)
            return GameImage.dummy();

        final boolean sameSelection = wasFilled == isFilled &&
                couldTransform == canTransform && lastTL.equals(tl),
                sameWindow = lastZ == z && lastRender.equals(render) &&
                        lastWW == ww && lastWH == wh;

        if (last != null && sameSelection && sameWindow)
            return last;

        final GameImage overlay = new GameImage(ww, wh);

        final Coord2D fillRender = render.displace(
                (int)(tl.x * z), (int)(tl.y * z));

        if (fillRender.x > ww || fillRender.y > wh ||
                fillRender.x + (int)(w * z) <= 0 ||
                fillRender.y + (int)(h * z) <= 0)
            return GameImage.dummy();

        final Coord2D[] bounds = getRenderBounds(z, fillRender, ww, wh);

        if (isFilled)
            overlay.draw(filled.section(bounds[TL], bounds[BR]),
                    bounds[TRP].x, bounds[TRP].y,
                    (int)(bounds[DIM].x * z), (int)(bounds[DIM].y * z));

        final Color inside = Settings.getTheme().buttonOutline.get(),
                outside = Settings.getTheme().highlightOutline.get();

        final int zint = (int) z;
        final Predicate<Coord2D> inBounds = p -> {
            final Coord2D adj = p.displace(-tl.x, -tl.y);

            return adj.x >= bounds[TL].x && adj.y >= bounds[TL].y &&
                    adj.x <= bounds[BR].x && adj.y <= bounds[BR].y;
        };
        final Function<Coord2D, Coord2D> renderCoord = c ->
                render.displace(c.x * zint, c.y * zint);

        left.stream().filter(inBounds).forEach(p -> {
            final Coord2D c = renderCoord.apply(p);

            overlay.fillRectangle(inside, c.x, c.y, 1, zint);
            overlay.fillRectangle(outside, c.x - 1, c.y, 1, zint);
        });
        right.stream().filter(inBounds).forEach(p -> {
            final Coord2D c = renderCoord.apply(p).displace(zint, 0);

            overlay.fillRectangle(inside, c.x - 1, c.y, 1, zint);
            overlay.fillRectangle(outside, c.x, c.y, 1, zint);
        });
        top.stream().filter(inBounds).forEach(p -> {
            final Coord2D c = renderCoord.apply(p);

            overlay.fillRectangle(inside, c.x, c.y, zint, 1);
            overlay.fillRectangle(outside, c.x, c.y - 1, zint, 1);
        });
        bottom.stream().filter(inBounds).forEach(p -> {
            final Coord2D c = renderCoord.apply(p).displace(0, zint);

            overlay.fillRectangle(inside, c.x, c.y - 1, zint, 1);
            overlay.fillRectangle(outside, c.x, c.y, zint, 1);
        });

        if (canTransform) {
            final GameImage NODE = GraphicsUtils.TRANSFORM_NODE;

            final int BEG = 0, MID = 1, END = 2, VALS = 3;
            final int[] xs = new int[VALS], ys = new int[VALS];

            xs[BEG] = fillRender.x - (NODE.getWidth() / 2);
            ys[BEG] = fillRender.y - (NODE.getWidth() / 2);

            xs[END] = xs[BEG] + (w * zint);
            ys[END] = ys[BEG] + (h * zint);

            xs[MID] = (xs[BEG] + xs[END]) / 2;
            ys[MID] = (ys[BEG] + ys[END]) / 2;

            for (int x = BEG; x <= END; x++)
                for (int y = BEG; y <= END; y++)
                    if (x != MID || y != MID)
                        overlay.draw(NODE, xs[x], ys[y]);
        }

        lastZ = z;
        lastRender = render;
        lastWW = ww;
        lastWH = wh;

        wasFilled = isFilled;
        couldTransform = canTransform;
        lastTL = tl;

        last = overlay.submit();

        return overlay;
    }

    private Coord2D[] getRenderBounds(
            final float z, final Coord2D render, final int ww, final int wh
    ) {
        final Coord2D[] bounds = new Coord2D[BOUNDS];

        final int tlx, tly, brx, bry;

        final float modX = render.x % z, modY = render.y % z;

        tlx = Math.max((int)(-render.x / z), 0);
        tly = Math.max((int)(-render.y / z), 0);
        brx = Math.min((int)((ww - render.x) / z) + 1, w);
        bry = Math.min((int)((wh - render.y) / z) + 1, h);

        bounds[TL] = new Coord2D(tlx, tly);
        bounds[BR] = new Coord2D(brx, bry);
        bounds[DIM] = new Coord2D(brx - tlx, bry - tly);
        bounds[TRP] = new Coord2D(
                tlx == 0 ? render.x : (int) modX,
                tly == 0 ? render.y : (int) modY
        );

        return bounds;
    }
}
