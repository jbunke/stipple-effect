package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.SEContext;

import java.awt.*;
import java.util.List;

public final class ColorPicker extends Tool
        implements SnappableTool, ToggleModeTool {
    private static final ColorPicker INSTANCE;

    private Mode mode;
    private boolean ctrl, shift;

    static {
        INSTANCE = new ColorPicker();
    }

    private enum Mode {
        REGULAR, COMPOSED, LAYERS;

        public Color get(final Coord2D tp, final SEContext c) {
            return switch (this) {
                case REGULAR -> c.getState().getActiveCel()
                        .getColorAt(tp.x, tp.y);
                case LAYERS -> {
                    final int frameIndex = c.getState().getFrameIndex();
                    final List<SELayer> layers = c.getState().getLayers();

                    for (int l = layers.size() - 1; l >= 0; l--) {
                        final Color sampled = layers.get(l)
                                .getCel(frameIndex).getColorAt(tp.x, tp.y);
                        if (sampled.getAlpha() > 0)
                            yield sampled;
                    }

                    yield null;
                }
                case COMPOSED -> {
                    final int frameIndex = c.getState().getFrameIndex();
                    yield c.getState().draw(false, false,
                            frameIndex).getColorAt(tp.x, tp.y);
                }
            };
        }
    }

    private ColorPicker() {
        mode = Mode.REGULAR;

        ctrl = false;
        shift = true;
    }

    public static ColorPicker get() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Color Picker";
    }

    @Override
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {
        if (context.isTargetingPixelOnCanvas() &&
                me.button != GameMouseEvent.Button.MIDDLE) {
            final Coord2D tp = context.getTargetPixel();
            final int index = me.button == GameMouseEvent.Button.LEFT
                    ? StippleEffect.PRIMARY : StippleEffect.SECONDARY;

            final Color c = mode.get(tp, context);

            if (c != null)
                StippleEffect.get().setColorIndexAndColor(index, c);
        }
    }

    @Override
    public void setSnap(final boolean snap) {
        shift = snap;
        updateMode();
    }

    @Override
    public boolean isSnap() {
        return shift;
    }

    @Override
    public void setMode(final boolean mode) {
        ctrl = mode;
        updateMode();
    }

    private void updateMode() {
        if (ctrl ^ shift)
            mode = ctrl ? Mode.COMPOSED : Mode.LAYERS;
        else
            mode = Mode.REGULAR;
    }
}
