package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.stipple_effect.utility.Constants;

public abstract class ToolWithBreadth extends Tool {
    private int breadth;

    ToolWithBreadth() {
        breadth = Constants.DEFAULT_BRUSH_BREADTH;
    }

    @Override
    public String getBottomBarText() {
        return getName() + " (" + getBreadth() + " px)";
    }

    public int getBreadth() {
        return breadth;
    }

    public void setBreadth(final int breadth) {
        if (breadth >= Constants.MIN_BREADTH && breadth <= Constants.MAX_BREADTH)
            this.breadth = breadth;
    }

    public void increaseRadius() {
        if (breadth < Constants.MAX_BREADTH)
            breadth++;
    }

    public void decreaseRadius() {
        if (breadth > Constants.MIN_BREADTH)
            breadth--;
    }
}
