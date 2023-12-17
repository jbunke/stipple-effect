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
        this.breadth = Math.max(Constants.MIN_BREADTH,
                Math.min(breadth, Constants.MAX_BREADTH));
    }

    public void increaseBreadth() {
        if (breadth < Constants.MAX_BREADTH)
            breadth++;
    }

    public void decreaseBreadth() {
        if (breadth > Constants.MIN_BREADTH)
            breadth--;
    }
}
