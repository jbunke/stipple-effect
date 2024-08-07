package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.button.MenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.Scrollable;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalScrollBox;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollBox;
import com.jordanbunke.stipple_effect.visual.theme.logic.ThemeLogic;

public final class CelButton extends MenuButton {
    private static SelectionStatus selectionStatus;
    private static int framesFrom, layersFrom, framesTo, layersTo;

    private final int layerIndex, frameIndex;
    private final Scrollable frameButton, layerButton;
    private final VerticalScrollBox layers;
    private final HorizontalScrollBox frames;

    private final SEContext c;
    private GameImage base, highlighted, selected;

    // status
    private Status status;
    private boolean enabled;

    static {
        selectionStatus = SelectionStatus.NONE;

        framesFrom = 0;
        layersFrom = 0;
        framesTo = 0;
        layersTo = 0;
    }

    public enum SelectionStatus {
        NONE, SELECTING, SELECTED
    }

    public enum Status {
        LINKED, EMPTY, POPULATED
    }

    public static int getFramesFrom() {
        return framesFrom;
    }

    public static int getFramesTo() {
        return framesTo;
    }

    public static int getLayersFrom() {
        return layersFrom;
    }

    public static int getLayersTo() {
        return layersTo;
    }

    public CelButton(
            final SEContext c, final int layerIndex, final int frameIndex,
            final Scrollable layerButton, final Scrollable frameButton,
            final VerticalScrollBox layers, final HorizontalScrollBox frames
    ) {
        super(new Coord2D(frameButton.getX(), layerButton.getY()),
                new Bounds2D(Layout.FRAME_BUTTON_W, Layout.STD_TEXT_BUTTON_H),
                Anchor.LEFT_TOP, true, () -> {
            c.getState().setFrameIndex(frameIndex);
            c.getState().setLayerEditIndex(layerIndex);
        });

        this.frameButton = frameButton;
        this.layerButton = layerButton;
        this.frames = frames;
        this.layers = layers;

        this.c = c;
        this.frameIndex = frameIndex;
        this.layerIndex = layerIndex;

        status = Status.EMPTY;

        updateAssets();
        update(0d);
    }

    private void updateAssets() {
        final ThemeLogic tl = Settings.getTheme().logic;
        base = tl.drawCelButton(false, false, status, enabled);
        highlighted = tl.drawCelButton(false, true, status, enabled);
        selected = tl.drawCelButton(true, false, status, enabled);
    }

    @Override
    public void update(final double deltaTime) {
        setX(frameButton.getX());
        setY(layerButton.getY());

        final Status oldStatus = status;
        final boolean wasEnabled = enabled;

        final SELayer layer = c.getState().getLayers().get(layerIndex);
        final boolean populated = c.getState()
                .isCelPopulated(layerIndex, frameIndex),
                linked = layer.areFramesLinked();

        status = populated
                ? (linked ? Status.LINKED : Status.POPULATED)
                : Status.EMPTY;
        enabled = layer.isEnabled();

        if (oldStatus != status || wasEnabled != enabled)
            updateAssets();
    }

    @Override
    public void render(final GameImage canvas) {
        if (layers.renderingChild(layerButton) &&
                frames.renderingChild(frameButton))
            draw(isSelected() ? selected
                    : (isHighlighted() ? highlighted : base), canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    @Override
    public boolean isSelected() {
        return c.getState().getLayerEditIndex() == layerIndex &&
                c.getState().getFrameIndex() == frameIndex;
    }
}
