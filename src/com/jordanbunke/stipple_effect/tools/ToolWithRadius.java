package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.stipple_effect.utility.Constants;

public abstract class ToolWithRadius extends Tool {
    private int radius;

    ToolWithRadius() {
        radius = Constants.DEFAULT_BRUSH_RADIUS;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(final int radius) {
        if (radius >= Constants.MIN_RADIUS && radius <= Constants.MAX_RADIUS)
            this.radius = radius;
    }

    public void increaseRadius() {
        if (radius < Constants.MAX_RADIUS)
            radius++;
    }

    public void decreaseRadius() {
        if (radius > Constants.MIN_RADIUS)
            radius--;
    }
}
