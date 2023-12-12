package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.LayerCombiner;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.state.ImageState;
import com.jordanbunke.stipple_effect.state.StateManager;
import com.jordanbunke.stipple_effect.tools.*;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ImageContext {
    private static final Coord2D UNTARGETED = new Coord2D(-1, -1);

    private final StateManager states;
    private final RenderInfo renderInfo;

    private Coord2D targetPixel;

    public ImageContext(final GameImage image) {
        this(new ImageState(image), image.getWidth(), image.getHeight());
    }

    public ImageContext(
            final int imageWidth, final int imageHeight
    ) {
        this(new ImageState(imageWidth, imageHeight), imageWidth, imageHeight);
    }

    private ImageContext(
            final ImageState imageState, final int imageWidth, final int imageHeight
    ) {
        this.states = new StateManager(imageState);
        this.renderInfo = new RenderInfo(imageWidth, imageHeight);
        this.targetPixel = UNTARGETED;
    }

    public GameImage drawWorkspace() {
        final GameImage workspace = new GameImage(Constants.WORKSPACE_W, Constants.WORKSPACE_H);
        workspace.fillRectangle(Constants.BACKGROUND, 0, 0,
                Constants.WORKSPACE_W, Constants.WORKSPACE_H);

        final float zoomFactor = renderInfo.getZoomFactor();
        final Coord2D render = getImageRenderPositionInWorkspace();
        final int w = states.getState().getImageWidth(),
                h = states.getState().getImageHeight();
        workspace.draw(generateCheckers(), render.x, render.y);
        workspace.draw(states.getState().draw(), render.x, render.y,
                (int)(w * zoomFactor), (int)(h * zoomFactor));

        return workspace.submit();
    }

    public void process(final InputEventLogger eventLogger, final Tool tool) {
        setTargetPixel(eventLogger);
        processTools(eventLogger, tool);
        processSingleKeyInputs(eventLogger);
        processCompoundKeyInputs(eventLogger);
        // TODO
    }

    private void processTools(
            final InputEventLogger eventLogger, final Tool tool
    ) {
        for (GameEvent e : eventLogger.getUnprocessedEvents()) {
            if (e instanceof GameMouseEvent me) {
                if (me.matchesAction(GameMouseEvent.Action.DOWN) && isInWorkshopBounds(eventLogger)) {
                    tool.onMouseDown(this, me);
                    me.markAsProcessed();
                } else if (me.matchesAction(GameMouseEvent.Action.UP)) {
                    tool.onMouseUp(this, me);
                }
            }
        }

        tool.update(this, eventLogger.getAdjustedMousePosition());
    }

    private void processCompoundKeyInputs(final InputEventLogger eventLogger) {
        // CTRL but not SHIFT
        if (eventLogger.isPressed(Key.CTRL) && !eventLogger.isPressed(Key.SHIFT)) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Z, GameKeyEvent.Action.PRESS),
                    states::undoToCheckpoint
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Y, GameKeyEvent.Action.PRESS),
                    states::redoToCheckpoint
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.S, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO - context needs a field for a save path and a save mode ->
                    // PNG, static vs animated,
                    // animated can be frame by frame alongside, saved as separate files, or as GIF
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.A, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO - select all
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.D, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO - deselect
            );
        }

        // SHIFT but not CTRL
        if (!eventLogger.isPressed(Key.CTRL) && eventLogger.isPressed(Key.SHIFT)) {
            // TODO - populate with SHIFT + ? shortcuts
        }

        // CTRL and SHIFT
        if (eventLogger.isPressed(Key.CTRL) && eventLogger.isPressed(Key.SHIFT)) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Z, GameKeyEvent.Action.PRESS),
                    states::undo
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Y, GameKeyEvent.Action.PRESS),
                    states::redo
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.S, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO - save as
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.X, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO - crop to selection
            );
        }
    }

    private void processSingleKeyInputs(final InputEventLogger eventLogger) {
        if (!(eventLogger.isPressed(Key.CTRL) || eventLogger.isPressed(Key.SHIFT))) {
            // set tools
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Z, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Zoom.get())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.H, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Hand.get())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.O, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(StipplePencil.get())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.P, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Pencil.get())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.B, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Brush.get())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.E, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Eraser.get())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.C, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(ColorPicker.get())
            );

            // tool modifications
            if (StippleEffect.get().getTool() instanceof ToolWithRadius twr) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        twr::increaseRadius
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        twr::decreaseRadius
                );
            } else if (StippleEffect.get().getTool() instanceof Hand) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> getRenderInfo().incrementAnchor(new Coord2D(0, 1))
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> getRenderInfo().incrementAnchor(new Coord2D(0, -1))
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> getRenderInfo().incrementAnchor(new Coord2D(1, 0))
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> getRenderInfo().incrementAnchor(new Coord2D(-1, 0))
                );
            } else if (StippleEffect.get().getTool() instanceof Zoom) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> getRenderInfo().zoomIn()
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> getRenderInfo().zoomOut()
                );
            }

            // TODO
        }
    }

    private boolean isInWorkshopBounds(final InputEventLogger eventLogger) {
        final Coord2D m = eventLogger.getAdjustedMousePosition();
        final Coord2D workshopM = new Coord2D(m.x - Constants.TOOLS_W, m.y - Constants.CONTEXTS_H);
        return workshopM.x > 0 && workshopM.x < Constants.WORKSPACE_W &&
                        workshopM.y > 0 && workshopM.y < Constants.WORKSPACE_H;
    }

    private void setTargetPixel(final InputEventLogger eventLogger) {
        final Coord2D m = eventLogger.getAdjustedMousePosition();
        final Coord2D workshopM = new Coord2D(m.x - Constants.TOOLS_W, m.y - Constants.CONTEXTS_H);

        if (isInWorkshopBounds(eventLogger)) {
            final int w = states.getState().getImageWidth(),
                    h = states.getState().getImageHeight();
            final float zoomFactor = renderInfo.getZoomFactor();
            final Coord2D render = getImageRenderPositionInWorkspace(),
                    bottomLeft = new Coord2D(render.x + (int)(zoomFactor * w),
                            render.y + (int)(zoomFactor * h));
            final int targetX = (int)(((workshopM.x - render.x) / (double)(bottomLeft.x - render.x)) * w),
                    targetY = (int)(((workshopM.y - render.y) / (double)(bottomLeft.y - render.y)) * h);

            targetPixel = (targetX >= 0 && targetX < w && targetY >= 0 && targetY < h)
                    ? new Coord2D(targetX, targetY) : UNTARGETED;
        } else
            targetPixel = UNTARGETED;
    }

    private Coord2D getImageRenderPositionInWorkspace() {
        final float zoomFactor = renderInfo.getZoomFactor();
        final Coord2D anchor = renderInfo.getAnchor(),
                middle = new Coord2D(Constants.WORKSPACE_W / 2, Constants.WORKSPACE_H / 2);

        return new Coord2D(middle.x - (int)(zoomFactor * anchor.x),
                middle.y - (int)(zoomFactor * anchor.y));
    }

    private GameImage generateCheckers() {
        final int w = states.getState().getImageWidth(),
                h = states.getState().getImageHeight();
        final float zoomFactor = renderInfo.getZoomFactor();

        final GameImage image = new GameImage((int)(w * zoomFactor), (int)(h * zoomFactor));

        for (int x = 0; x < image.getWidth(); x += Constants.CHECKER_INCREMENT) {
            for (int y = 0; y < image.getHeight(); y += Constants.CHECKER_INCREMENT) {
                final Color c = ((x / Constants.CHECKER_INCREMENT) +
                        (y / Constants.CHECKER_INCREMENT)) % 2 == 0
                        ? Constants.WHITE : Constants.LIGHT_GREY;

                image.fillRectangle(c, x, y, Constants.CHECKER_INCREMENT,
                        Constants.CHECKER_INCREMENT);
            }
        }

        return image.submit();
    }

    // TODO - process all actions here and feed through state manager

    // TODO - TOOL

    // IMAGE EDITING
    public void editImage(final GameImage edit) {
        // build resultant state
        final int w = states.getState().getImageWidth(),
                h = states.getState().getImageHeight();
        final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
        final SELayer replacement = states.getState().getEditingLayer().returnEdit(edit);
        final int index = states.getState().getLayerEditIndex();
        layers.remove(index);
        layers.add(index, replacement);

        final ImageState result = new ImageState(w, h, layers, index, false);
        states.performAction(result);
    }

    // ERASING
    public void erase(final boolean[][] eraseMask) {
        // build resultant state
        final int w = states.getState().getImageWidth(),
                h = states.getState().getImageHeight();
        final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
        final SELayer replacement = states.getState().getEditingLayer().returnEdit(eraseMask);
        final int index = states.getState().getLayerEditIndex();
        layers.remove(index);
        layers.add(index, replacement);

        final ImageState result = new ImageState(w, h, layers, index, false);
        states.performAction(result);
    }

    // LAYER MANIPULATION
    // add layer
    public void addLayer() {
        // build resultant state
        final int w = states.getState().getImageWidth(),
                h = states.getState().getImageHeight();
        final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
        final int addIndex = states.getState().getLayerEditIndex() + 1;
        layers.add(addIndex, new SELayer(w, h));

        final ImageState result = new ImageState(w, h, layers, addIndex);
        states.performAction(result);
    }

    // remove layer
    public void removeLayer() {
        // pre-check
        if (states.getState().canRemoveLayer()) {
            // build resultant state
            final int w = states.getState().getImageWidth(),
                    h = states.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
            final int index = states.getState().getLayerEditIndex();
            layers.remove(index);

            final ImageState result = new ImageState(w, h, layers, index > 0 ? index - 1 : index);
            states.performAction(result);
        }
    }

    // move layer down
    public void moveLayerDown() {
        // pre-check
        if (states.getState().canMoveLayerDown()) {
            // build resultant state
            final int w = states.getState().getImageWidth(),
                    h = states.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
            final int removalIndex = states.getState().getLayerEditIndex(),
                    reinsertionIndex = removalIndex - 1;

            final SELayer toMove = layers.get(removalIndex);
            layers.remove(removalIndex);
            layers.add(reinsertionIndex, toMove);

            final ImageState result = new ImageState(w, h, layers, reinsertionIndex);
            states.performAction(result);
        }
    }

    // move layer up
    public void moveLayerUp() {
        // pre-check
        if (states.getState().canMoveLayerUp()) {
            // build resultant state
            final int w = states.getState().getImageWidth(),
                    h = states.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
            final int removalIndex = states.getState().getLayerEditIndex(),
                    reinsertionIndex = removalIndex + 1;

            final SELayer toMove = layers.get(removalIndex);
            layers.remove(removalIndex);
            layers.add(reinsertionIndex, toMove);

            final ImageState result = new ImageState(w, h, layers, reinsertionIndex);
            states.performAction(result);
        }
    }

    // combine with layer below
    public void combineWithLayerBelow() {
        // pre-check - identical pass case as can move layer down
        if (states.getState().canMoveLayerDown()) {
            // build resultant state
            final int w = states.getState().getImageWidth(),
                    h = states.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
            final int aboveIndex = states.getState().getLayerEditIndex(),
                    belowIndex = aboveIndex - 1;

            final SELayer above = layers.get(aboveIndex),
                    below = layers.get(belowIndex);
            final SELayer combined = LayerCombiner.combine(above, below);
            layers.remove(above);
            layers.remove(below);
            layers.add(belowIndex, combined);

            final ImageState result = new ImageState(w, h, layers, belowIndex);
            states.performAction(result);
        }
    }

    // GETTERS
    public boolean hasTargetPixel() {
        return targetPixel != UNTARGETED;
    }

    public Coord2D getTargetPixel() {
        return targetPixel;
    }

    public String getTargetPixelText() {
        return hasTargetPixel() ? targetPixel.toString() : "--";
    }

    public String getCanvasSizeText() {
        return states.getState().getImageWidth() + " x " + states.getState().getImageHeight();
    }

    public RenderInfo getRenderInfo() {
        return renderInfo;
    }

    public ImageState getState() {
        return states.getState();
    }
}
