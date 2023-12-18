package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.tools.ToolWithBreadth;
import com.jordanbunke.stipple_effect.utility.Constants;

public class RenderInfo {
    private Coord2D anchor;
    private float zoomFactor;

    public RenderInfo(final int imageWidth, final int imageHeight) {
        this.anchor = new Coord2D(imageWidth / 2, imageHeight / 2);
        this.zoomFactor = Constants.DEF_ZOOM;
    }

    public void zoomIn() {
        setZoomFactor(zoomFactor * 2f);
    }

    public void zoomOut() {
        setZoomFactor(zoomFactor / 2f);
    }

    public void setZoomFactor(final float zoomFactor) {
        this.zoomFactor = Math.max(Constants.MIN_ZOOM,
                Math.min(zoomFactor, Constants.MAX_ZOOM));

        if (this.zoomFactor >= Constants.ZOOM_FOR_OVERLAY)
            ToolWithBreadth.redrawToolOverlays();

        StippleEffect.get().getContext().redrawSelectionOverlay();
    }

    public void setAnchor(final Coord2D anchor) {
        this.anchor = anchor;
    }

    public void incrementAnchor(final Coord2D delta) {
        this.anchor = anchor.displace(delta);
    }

    public String getZoomText() {
        return zoomFactor >= 1 / 4f
                ? (int)(zoomFactor * 100) + "%"
                : (zoomFactor * 100) + "%";
    }

    public float getZoomFactor() {
        return zoomFactor;
    }

    public Coord2D getAnchor() {
        return anchor;
    }
}
