package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.GameImageIO;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.LayerCombiner;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.state.ActionType;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.state.StateManager;
import com.jordanbunke.stipple_effect.tools.*;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SEContext {
    private static final Coord2D UNTARGETED = new Coord2D(-1, -1);

    private final ProjectInfo projectInfo;

    private final StateManager stateManager;
    private final RenderInfo renderInfo;

    private Coord2D targetPixel;

    public static SEContext fromFile(final Path filepath) {
        final GameImage image = GameImageIO.readImage(filepath);

        return new SEContext(new ProjectInfo(filepath), new ProjectState(image),
                image.getWidth(), image.getHeight());
    }

    public SEContext(
            final int imageWidth, final int imageHeight
    ) {
        this(new ProjectInfo(), new ProjectState(imageWidth, imageHeight), imageWidth, imageHeight);
    }

    private SEContext(
            final ProjectInfo projectInfo, final ProjectState projectState,
            final int imageWidth, final int imageHeight
    ) {
        this.projectInfo = projectInfo;

        this.stateManager = new StateManager(projectState);
        this.renderInfo = new RenderInfo(imageWidth, imageHeight);
        this.targetPixel = UNTARGETED;
    }

    public GameImage drawWorkspace() {
        final GameImage workspace = new GameImage(Constants.WORKSPACE_W, Constants.WORKSPACE_H);
        workspace.fillRectangle(Constants.BACKGROUND, 0, 0,
                Constants.WORKSPACE_W, Constants.WORKSPACE_H);

        final float zoomFactor = renderInfo.getZoomFactor();
        final Coord2D render = getImageRenderPositionInWorkspace();
        final int w = stateManager.getState().getImageWidth(),
                h = stateManager.getState().getImageHeight();
        workspace.draw(generateCheckers(), render.x, render.y);
        workspace.draw(stateManager.getState().draw(), render.x, render.y,
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
                if (me.matchesAction(GameMouseEvent.Action.DOWN) && isInWorkspaceBounds(eventLogger)) {
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
                    stateManager::undoToCheckpoint
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Y, GameKeyEvent.Action.PRESS),
                    stateManager::redoToCheckpoint
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.S, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO - context needs a field for a save path and a save mode ->
                    // PNG, static vs animated,
                    // animated can be frame-by-frame alongside, saved as separate files, or as GIF
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.A, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO - select all
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.D, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO - deselect
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO - previous frame
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO - next frame
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
                    () -> stateManager.undo(true)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Y, GameKeyEvent.Action.PRESS),
                    () -> stateManager.redo(true)
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
            if (StippleEffect.get().getTool() instanceof ToolWithBreadth twr) {
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

    private Coord2D getMouseOffsetInWorkspace(final InputEventLogger eventLogger) {
        final Coord2D
                m = eventLogger.getAdjustedMousePosition(),
                wp = Constants.getWorkspacePosition();
        return new Coord2D(m.x - wp.x, m.y - wp.y);
    }

    private boolean isInWorkspaceBounds(final InputEventLogger eventLogger) {
        final Coord2D workshopM = getMouseOffsetInWorkspace(eventLogger);
        return workshopM.x > 0 && workshopM.x < Constants.WORKSPACE_W &&
                        workshopM.y > 0 && workshopM.y < Constants.WORKSPACE_H;
    }

    private void setTargetPixel(final InputEventLogger eventLogger) {
        final Coord2D workshopM = getMouseOffsetInWorkspace(eventLogger);

        if (isInWorkspaceBounds(eventLogger)) {
            final int w = stateManager.getState().getImageWidth(),
                    h = stateManager.getState().getImageHeight();
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
        final int w = stateManager.getState().getImageWidth(),
                h = stateManager.getState().getImageHeight();
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
        final int w = stateManager.getState().getImageWidth(),
                h = stateManager.getState().getImageHeight();
        final List<SELayer> layers = new ArrayList<>(stateManager.getState().getLayers());
        final SELayer replacement = stateManager.getState().getEditingLayer().returnEdited(edit);
        final int index = stateManager.getState().getLayerEditIndex();
        layers.remove(index);
        layers.add(index, replacement);

        final ProjectState result = new ProjectState(w, h, layers, index, false);
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // ERASING
    public void erase(final boolean[][] eraseMask) {
        // build resultant state
        final int w = stateManager.getState().getImageWidth(),
                h = stateManager.getState().getImageHeight();
        final List<SELayer> layers = new ArrayList<>(stateManager.getState().getLayers());
        final SELayer replacement = stateManager.getState().getEditingLayer().returnEdited(eraseMask);
        final int index = stateManager.getState().getLayerEditIndex();
        layers.remove(index);
        layers.add(index, replacement);

        final ProjectState result = new ProjectState(w, h, layers, index, false);
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // LAYER MANIPULATION
    // enable all layers
    public void enableAllLayers() {
        // build resultant state
        final int w = stateManager.getState().getImageWidth(),
                h = stateManager.getState().getImageHeight();

        final ProjectState result = new ProjectState(w, h,
                stateManager.getState().getLayers().stream().map(SELayer::returnEnabled)
                        .collect(Collectors.toList()),
                stateManager.getState().getLayerEditIndex());
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // isolate layer
    public void isolateLayer(final int layerIndex) {
        final List<SELayer> layers = stateManager.getState().getLayers(),
                newLayers = new ArrayList<>();

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size()) {
            // build resultant state
            final int w = stateManager.getState().getImageWidth(),
                    h = stateManager.getState().getImageHeight();

            for (int i = 0; i < layers.size(); i++) {
                final SELayer layer = i == layerIndex
                        ? layers.get(i).returnEnabled()
                        : layers.get(i).returnDisabled();

                newLayers.add(layer);
            }

            final ProjectState result = new ProjectState(w, h, newLayers,
                    stateManager.getState().getLayerEditIndex());
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // disable layer
    public void disableLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(stateManager.getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() && layers.get(layerIndex).isEnabled()) {
            // build resultant state
            final int w = stateManager.getState().getImageWidth(),
                    h = stateManager.getState().getImageHeight();
            final SELayer layer = layers.get(layerIndex).returnDisabled();
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = new ProjectState(w, h, layers,
                    stateManager.getState().getLayerEditIndex());
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // enable layer
    public void enableLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(stateManager.getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() && !layers.get(layerIndex).isEnabled()) {
            // build resultant state
            final int w = stateManager.getState().getImageWidth(),
                    h = stateManager.getState().getImageHeight();
            final SELayer layer = layers.get(layerIndex).returnEnabled();
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = new ProjectState(w, h, layers,
                    stateManager.getState().getLayerEditIndex());
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // change layer opacity
    public void changeLayerOpacity(final double opacity, final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(stateManager.getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size()) {
            // build resultant state
            final int w = stateManager.getState().getImageWidth(),
                    h = stateManager.getState().getImageHeight();
            final SELayer layer = layers.get(layerIndex).returnChangedOpacity(opacity);
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = new ProjectState(w, h, layers,
                    stateManager.getState().getLayerEditIndex(), false);
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // add layer
    public void addLayer() {
        // pre-check
        if (stateManager.getState().canAddLayer()) {
            // build resultant state
            final int w = stateManager.getState().getImageWidth(),
                    h = stateManager.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(stateManager.getState().getLayers());
            final int addIndex = stateManager.getState().getLayerEditIndex() + 1;
            layers.add(addIndex, new SELayer(w, h));

            final ProjectState result = new ProjectState(w, h, layers, addIndex);
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // add layer
    public void duplicateLayer() {
        // pre-check
        if (stateManager.getState().canAddLayer()) {
            // build resultant state
            final int w = stateManager.getState().getImageWidth(),
                    h = stateManager.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(stateManager.getState().getLayers());
            final int addIndex = stateManager.getState().getLayerEditIndex() + 1;
            layers.add(addIndex, stateManager.getState().getEditingLayer().duplicate());

            final ProjectState result = new ProjectState(w, h, layers, addIndex);
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // remove layer
    public void removeLayer() {
        // pre-check
        if (stateManager.getState().canRemoveLayer()) {
            // build resultant state
            final int w = stateManager.getState().getImageWidth(),
                    h = stateManager.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(stateManager.getState().getLayers());
            final int index = stateManager.getState().getLayerEditIndex();
            layers.remove(index);

            final ProjectState result = new ProjectState(w, h, layers, index > 0 ? index - 1 : index);
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // move layer down
    public void moveLayerDown() {
        // pre-check
        if (stateManager.getState().canMoveLayerDown()) {
            // build resultant state
            final int w = stateManager.getState().getImageWidth(),
                    h = stateManager.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(stateManager.getState().getLayers());
            final int removalIndex = stateManager.getState().getLayerEditIndex(),
                    reinsertionIndex = removalIndex - 1;

            final SELayer toMove = layers.get(removalIndex);
            layers.remove(removalIndex);
            layers.add(reinsertionIndex, toMove);

            final ProjectState result = new ProjectState(w, h, layers, reinsertionIndex);
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // move layer up
    public void moveLayerUp() {
        // pre-check
        if (stateManager.getState().canMoveLayerUp()) {
            // build resultant state
            final int w = stateManager.getState().getImageWidth(),
                    h = stateManager.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(stateManager.getState().getLayers());
            final int removalIndex = stateManager.getState().getLayerEditIndex(),
                    reinsertionIndex = removalIndex + 1;

            final SELayer toMove = layers.get(removalIndex);
            layers.remove(removalIndex);
            layers.add(reinsertionIndex, toMove);

            final ProjectState result = new ProjectState(w, h, layers, reinsertionIndex);
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // combine with layer below
    public void combineWithLayerBelow() {
        // pre-check - identical pass case as can move layer down
        if (stateManager.getState().canMoveLayerDown()) {
            // build resultant state
            final int w = stateManager.getState().getImageWidth(),
                    h = stateManager.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(stateManager.getState().getLayers());
            final int aboveIndex = stateManager.getState().getLayerEditIndex(),
                    belowIndex = aboveIndex - 1;

            final SELayer above = layers.get(aboveIndex),
                    below = layers.get(belowIndex);
            final SELayer combined = LayerCombiner.combine(above, below);
            layers.remove(above);
            layers.remove(below);
            layers.add(belowIndex, combined);

            final ProjectState result = new ProjectState(w, h, layers, belowIndex);
            stateManager.performAction(result, ActionType.LAYER);
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
        return stateManager.getState().getImageWidth() + " x " + stateManager.getState().getImageHeight();
    }

    public RenderInfo getRenderInfo() {
        return renderInfo;
    }

    public ProjectState getState() {
        return stateManager.getState();
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }
}
