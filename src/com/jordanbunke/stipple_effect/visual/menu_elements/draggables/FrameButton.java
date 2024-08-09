package com.jordanbunke.stipple_effect.visual.menu_elements.draggables;

import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Layout;

public final class FrameButton extends DraggableTextButton<FrameButton> {
    public static final DragLogic<FrameButton> logic;

    static {
        logic = new DragLogic<>(StippleEffect.get()::rebuildFlipbookMenu);
    }

    public FrameButton(
            final Coord2D position, final int index, final SEContext c
    ) {
        super(position, Layout.FRAME_BUTTON_W,
                i -> c.getState().setFrameIndex(i),
                index, String.valueOf(index + 1), c,
                () -> c.getState().getFrameIndex());

        logic.add(this);
    }

    @Override
    public DragLogic<FrameButton> getLogic() {
        return logic;
    }

    @Override
    public void reachedDestination(final int destinationIndex) {
        c.moveFrame(index, destinationIndex);
    }

    @Override
    public void drag() {
        final int DIM = Layout.FRAME_BUTTON_W + Layout.BUTTON_OFFSET,
                movingIndex = logic.getMovingIndex();
        final int deltaX = (int) Math.round(
                logic.deltaMousePosition().x / (double) DIM),
                diff;

        if (movingIndex == index) {
            logic.setWouldBeIndex(index + deltaX);

            diff = (logic.getWouldBeIndex() - index) * DIM;
        } else {
            final int adjustedIndex = index + (movingIndex > index
                    ? (logic.getWouldBeIndex() > index ? 0 : 1)
                    : (logic.getWouldBeIndex() < index ? 0 : -1));

            diff = (adjustedIndex - index) * DIM;
        }

        setX(lockedPos.x + diff);
    }
}
