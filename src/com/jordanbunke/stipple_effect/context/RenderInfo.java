package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.delta_time.utility.Coord2D;

public class RenderInfo {
    private static final int MIN_SCALE = 1, MAX_SCALE = 20;

    private Coord2D anchor;
    private int scaleUp;

    public RenderInfo(final int imageWidth, final int imageHeight) {
        this.anchor = new Coord2D(imageWidth / 2, imageHeight / 2);
        this.scaleUp = MIN_SCALE;
    }

    public void setScaleUp(int scaleUp) {
        this.scaleUp = scaleUp;
    }

    public void scaleUp() {
        if (scaleUp < MAX_SCALE)
            scaleUp++;
    }

    public void scaleDown() {
        if (scaleUp > MIN_SCALE)
            scaleUp--;
    }

    public void setAnchor(Coord2D anchor) {
        this.anchor = anchor;
    }

    public int getScaleUp() {
        return scaleUp;
    }

    public Coord2D getAnchor() {
        return anchor;
    }
}
