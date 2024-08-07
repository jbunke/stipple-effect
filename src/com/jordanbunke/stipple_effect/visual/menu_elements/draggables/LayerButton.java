package com.jordanbunke.stipple_effect.visual.menu_elements.draggables;

import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Layout;

public final class LayerButton extends DraggableTextButton<LayerButton> {
    public static final DragLogic<LayerButton> logic;

    static {
        logic = new DragLogic<>(StippleEffect.get()::rebuildFlipbookMenu);
    }

    public LayerButton(
            final Coord2D position, final int index,
            final SEContext c, final SELayer layer
    ) {
        super(position, Layout.LAYER_BUTTON_W,
                i -> c.getState().setLayerEditIndex(i),
                index, determineLabel(layer), c,
                () -> c.getState().getLayerEditIndex());

        // layer buttons are added instantiated in reverse order
        logic.add(0, this);
    }

    private static String determineLabel(final SELayer layer) {
        final String name = layer.getName();

        return name.length() > Layout.LAYER_NAME_LENGTH_CUTOFF
                ? name.substring(0, Layout.LAYER_NAME_LENGTH_CUTOFF) + "..."
                : name;
    }

    @Override
    public DragLogic<LayerButton> getLogic() {
        return logic;
    }

    @Override
    public void reachedDestination(final int destinationIndex) {
        c.moveLayer(index, destinationIndex);
    }

    @Override
    public void drag() {
        final int DIM = Layout.STD_TEXT_BUTTON_INC,
                movingIndex = logic.getMovingIndex();
        final int deltaY = (int) Math.round(
                -logic.deltaMousePosition().y / (double) DIM),
                diff;

        if (movingIndex == index) {
            logic.setWouldBeIndex(index + deltaY);

            diff = (logic.getWouldBeIndex() - index) * DIM;
        } else {
            final int adjustedIndex = index + (movingIndex > index
                    ? (logic.getWouldBeIndex() > index ? 0 : 1)
                    : (logic.getWouldBeIndex() < index ? 0 : -1));

            diff = (adjustedIndex - index) * DIM;
        }

        setY(lockedPos.y - diff);
    }
}
