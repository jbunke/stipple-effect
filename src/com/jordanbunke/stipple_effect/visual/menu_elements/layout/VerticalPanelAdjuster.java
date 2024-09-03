package com.jordanbunke.stipple_effect.visual.menu_elements.layout;

import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.util.function.Consumer;

public final class VerticalPanelAdjuster extends PanelAdjuster {

    public VerticalPanelAdjuster(
            final Coord2D position, final int width,
            final int upLeeway, final int downLeeway,
            final Consumer<Integer> consequence
    ) {
        super(position, width, true, upLeeway, downLeeway, consequence);
    }

    public void setUpLeeway(final int upLeeway) {
        setMinusLeeway(upLeeway);
    }

    public void setDownLeeway(final int downLeeway) {
        setPlusLeeway(downLeeway);
    }
}
