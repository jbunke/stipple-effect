package com.jordanbunke.stipple_effect.visual.menu_elements.draggables;

public interface Draggable {
    void lockPosition();
    void reachedDestination(final int destinationIndex);
    void drag();

    default void updateAssetForMoving() {

    }

    default void updateAssetFromMoving() {

    }
}
