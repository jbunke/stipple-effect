package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.delta_time.utility.Coord2D;

public class RenderInfo {
    private Coord2D anchor;
    private int scaleUp;

    public RenderInfo(final int imageWidth, final int imageHeight) {
        this.anchor = new Coord2D(imageWidth / 2, imageHeight / 2);
        this.scaleUp = 1;
    }

    public void setScaleUp(int scaleUp) {
        this.scaleUp = scaleUp;
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
