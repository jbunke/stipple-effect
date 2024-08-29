package com.jordanbunke.stipple_effect.visual.menu_elements.layout;

import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.util.function.Consumer;

public final class HorizontalPanelAdjuster extends PanelAdjuster {
    public HorizontalPanelAdjuster(
            final Coord2D position, final int length,
            final int leftLeeway, final int rightLeeway,
            final Consumer<Integer> consequence
    ) {
        super(position, length, false, leftLeeway, rightLeeway, consequence);
    }
}
