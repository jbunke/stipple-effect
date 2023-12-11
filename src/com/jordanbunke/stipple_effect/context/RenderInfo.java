package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.delta_time.utility.Coord2D;

public class RenderInfo {
    private static final float MIN_ZOOM = 1 / 16f, MAX_ZOOM = 32f, DEF_ZOOM = 4f;

    private Coord2D anchor;
    private float zoomFactor;

    public RenderInfo(final int imageWidth, final int imageHeight) {
        this.anchor = new Coord2D(imageWidth / 2, imageHeight / 2);
        this.zoomFactor = DEF_ZOOM;
    }

    public void setZoomFactor(final float zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public void zoomIn() {
        if (zoomFactor < MAX_ZOOM)
            zoomFactor *= 2f;
    }

    public void zoomOut() {
        if (zoomFactor > MIN_ZOOM)
            zoomFactor /= 2f;
    }

    public void setAnchor(final Coord2D anchor) {
        this.anchor = anchor;
    }

    public void incrementAnchor(final Coord2D delta) {
        this.anchor = new Coord2D(anchor.x + delta.x, anchor.y + delta.y);
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
