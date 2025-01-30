package com.jordanbunke.stipple_effect.project;

import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.tools.ToolWithBreadth;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

public class RenderInfo {
    public static final int TL = 0, BR = 1, DIM = 2, TRP = 3, BOUNDS = 4;

    private final boolean pixelGridApplicable;

    private Coord2D anchor;
    private ZoomLevel zoomLevel;
    private boolean pixelGridOn;

    public RenderInfo(final int imageWidth, final int imageHeight) {
        pixelGridApplicable = true;

        anchor = new Coord2D(imageWidth / 2, imageHeight / 2);
        zoomLevel = ZoomLevel.fromZ(Constants.DEF_ZOOM);
        pixelGridOn = Settings.isPixelGridOnByDefault();
    }

    private RenderInfo() {
        pixelGridApplicable = false;

        anchor = new Coord2D();
        zoomLevel = ZoomLevel.fromZ(Constants.DEF_ZOOM);
        pixelGridOn = false;
    }

    public static RenderInfo forPreview() {
        return new RenderInfo();
    }

    public void zoomInPreview() {
        zoomPreview(zoomLevel.in());
    }

    public void zoomOutPreview() {
        zoomPreview(zoomLevel.out());
    }

    private void zoomPreview(final ZoomLevel zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public void zoomIn(final Coord2D targetPixel) {
        zoom(zoomLevel.in(), targetPixel);
    }

    public void zoomOut(final Coord2D targetPixel) {
        zoom(zoomLevel.out(), targetPixel);
    }

    private void zoom(final ZoomLevel to, final Coord2D targetPixel) {
        final float before = zoomLevel.z;

        setZoomLevel(to);
        adjustAnchorFromZoom(targetPixel, before, zoomLevel.z);
    }

    public Coord2D localRenderPosition(final int localW, final int localH) {
        final Coord2D middle = new Coord2D(localW / 2, localH / 2);

        return new Coord2D(middle.x - (int)(zoomLevel.z * anchor.x),
                middle.y - (int)(zoomLevel.z * anchor.y));
    }

    private void adjustAnchorFromZoom(
            final Coord2D tp, final float before, final float after
    ) {
        if (!tp.equals(Constants.NO_VALID_TARGET)) {
            final int pixelXDiff = (int)((tp.x - anchor.x) * before),
                    pixelYDiff = (int)((tp.y - anchor.y) * before);

            final Coord2D adjusted = before == after
                    ? anchor.displace((int)Math.signum(pixelXDiff),
                        (int)Math.signum(pixelYDiff))
                    : new Coord2D(tp.x - Math.round(pixelXDiff / after),
                        tp.y - Math.round(pixelYDiff / after));

            setAnchor(adjusted);
        }
    }

    public void setZoomLevel(final ZoomLevel zoomLevel) {
        final boolean redrawOverlays = this.zoomLevel != zoomLevel &&
                zoomLevel.z >= Constants.ZOOM_FOR_OVERLAY;

        this.zoomLevel = zoomLevel;

        if (redrawOverlays)
            ToolWithBreadth.redrawToolOverlays();
    }

    public void setAnchor(final Coord2D anchor) {
        final ProjectState state = StippleEffect.get().getContext().getState();
        final int w = state.getImageWidth(), h = state.getImageHeight();

        setAnchor(anchor, w, h);
    }

    public void setAnchor(final Coord2D anchor, final int w, final int h) {
        this.anchor = new Coord2D(
                MathPlus.bounded(0, anchor.x, w),
                MathPlus.bounded(0, anchor.y, h));
    }

    public void updateAnchor(final int w, final int h) {
        setAnchor(anchor, w, h);
    }

    public void center(final int w, final int h) {
        setAnchor(new Coord2D(w / 2, h / 2), w, h);
    }

    public void incrementAnchor(final Coord2D delta) {
        setAnchor(anchor.displace(delta));
    }

    public void togglePixelGrid() {
        setPixelGrid(!pixelGridOn);
    }

    public void setPixelGrid(final boolean pixelGridOn) {
        if (pixelGridApplicable)
            this.pixelGridOn = pixelGridOn;
    }

    public String getZoomText() {
        return zoomLevel.toString();
    }

    public float getZoomFactor() {
        return zoomLevel.z;
    }

    public ZoomLevel getZoomLevel() {
        return zoomLevel;
    }

    public Coord2D getAnchor() {
        return anchor;
    }

    public boolean isPixelGridOn() {
        return pixelGridApplicable && pixelGridOn;
    }

    public static Coord2D[] renderBounds(
            final Coord2D renderPosition, final int w, final int h, final float z
    ) {
        final int ww = Layout.getWorkspaceWidth(),
                wh = Layout.getWorkspaceHeight();
        final Coord2D[] bounds = new Coord2D[BOUNDS];

        if (renderPosition.x > ww || renderPosition.y > wh ||
                renderPosition.x + (int)(w * z) <= 0 ||
                renderPosition.y + (int)(h * z) <= 0)
            return new Coord2D[] {};

        final int tlx, tly, brx, bry;

        final float modX = renderPosition.x % z, modY = renderPosition.y % z;

        tlx = Math.max((int)(-renderPosition.x / z), 0);
        tly = Math.max((int)(-renderPosition.y / z), 0);
        brx = Math.min((int)((ww - renderPosition.x) / z) + 1, w);
        bry = Math.min((int)((wh - renderPosition.y) / z) + 1, h);

        bounds[TL] = new Coord2D(tlx, tly);
        bounds[BR] = new Coord2D(brx, bry);
        bounds[DIM] = new Coord2D(brx - tlx, bry - tly);
        bounds[TRP] = new Coord2D(
                tlx == 0 ? renderPosition.x : (int) modX,
                tly == 0 ? renderPosition.y : (int) modY);

        return bounds;
    }
}
