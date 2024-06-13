package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.ZoomLevel;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.awt.*;

public final class SelectionOverlay {
    private static final int TL = 0, BR = 1, DIM = 2, TRP = 3, BOUNDS = 4;
    private static final short INIT = 1,
            L_PRM = 3, R_PRM = 5, T_PRM = 7, B_PRM = 11;

    private final short[][] frontier;
    private final GameImage filled;
    private Coord2D tl;
    private final int w, h;

    private float lastZ;
    private Coord2D lastTL;
    private int lastWW, lastWH;
    private Coord2D lastRender;
    private boolean wasFilled, couldTransform;
    private GameImage last;

    public  SelectionOverlay() {
        this(Selection.EMPTY);
    }

    public SelectionOverlay(final Selection selection) {
        lastZ = ZoomLevel.MIN.z;
        lastTL = new Coord2D();
        lastRender = new Coord2D();
        lastWW = 0;
        lastWH = 0;

        tl = selection.topLeft;

        w = selection.bounds.width();
        h = selection.bounds.height();

        final int fillC = Settings.getTheme().selectionFill.getRGB();
        filled = new GameImage(w, h);
        frontier = new short[w][h];

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++) {
                frontier[x][y] = INIT;

                final int px = tl.x + x, py = tl.y + y;

                if (selection.selected(px, py)) {
                    filled.setRGB(x, y, fillC);

                    if (!selection.selected(px - 1, py))
                        frontier[x][y] *= L_PRM;
                    if (!selection.selected(px + 1, py))
                        frontier[x][y] *= R_PRM;
                    if (!selection.selected(px, py - 1))
                        frontier[x][y] *= T_PRM;
                    if (!selection.selected(px, py + 1))
                        frontier[x][y] *= B_PRM;
                }
            }

        filled.free();
    }

    public void updateTL(final Selection selection) {
        lastTL = tl;
        tl = selection.topLeft;
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

        final Color inside = Settings.getTheme().buttonOutline,
                outside = Settings.getTheme().highlightOutline;

        final int zint = (int) z;

        for (int x = bounds[TL].x; x < bounds[BR].x; x++) {
            for (int y = bounds[TL].y; y < bounds[BR].y; y++) {
                final int px = tl.x + x, py = tl.y + y;
                final Coord2D c = render.displace(px * zint, py * zint);

                final short fp = frontier[x][y];

                if (fp % L_PRM == 0) {
                    overlay.fillRectangle(inside, c.x, c.y, 1, zint);
                    overlay.fillRectangle(outside, c.x - 1, c.y, 1, zint);
                }
                if (fp % R_PRM == 0) {
                    overlay.fillRectangle(inside, c.x + zint - 1, c.y, 1, zint);
                    overlay.fillRectangle(outside, c.x + zint, c.y, 1, zint);
                }
                if (fp % T_PRM == 0) {
                    overlay.fillRectangle(inside, c.x, c.y, zint, 1);
                    overlay.fillRectangle(outside, c.x, c.y - 1, zint, 1);
                }
                if (fp % B_PRM == 0) {
                    overlay.fillRectangle(inside, c.x, c.y + zint - 1, zint, 1);
                    overlay.fillRectangle(outside, c.x, c.y + zint, zint, 1);
                }
            }
        }

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
