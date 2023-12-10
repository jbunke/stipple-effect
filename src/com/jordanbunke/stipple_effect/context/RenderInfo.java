package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.delta_time.utility.Coord2D;

public class RenderInfo {
    private static final int MIN_SCALE = 1, MAX_SCALE = 20;

    private Coord2D anchor;
    private int zoomFactor;

    public RenderInfo(final int imageWidth, final int imageHeight) {
        this.anchor = new Coord2D(imageWidth / 2, imageHeight / 2);
        this.zoomFactor = MIN_SCALE;
    }

    public void setZoomFactor(int zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public void zoomIn() {
        if (zoomFactor < MAX_SCALE)
            zoomFactor++;
    }

    public void zoomOut() {
        if (zoomFactor > MIN_SCALE)
            zoomFactor--;
    }

    public void setAnchor(final Coord2D anchor) {
        this.anchor = anchor;
    }

    public void incrementAnchor(final Coord2D delta) {
        this.anchor = new Coord2D(anchor.x + delta.x, anchor.y + delta.y);
    }

    public int getZoomFactor() {
        return zoomFactor;
    }

    public Coord2D getAnchor() {
        return anchor;
    }
}
