package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.LayerMerger;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.state.ActionType;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.state.StateManager;
import com.jordanbunke.stipple_effect.tools.*;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.DialogAssembly;
import com.jordanbunke.stipple_effect.utility.DialogVals;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SEContext {
    private final ProjectInfo projectInfo;
    private final StateManager stateManager;
    private final RenderInfo renderInfo;
    private final PlaybackInfo playbackInfo;

    private boolean inWorkspaceBounds;
    private Coord2D targetPixel;

    private GameImage selectionOverlay;

    public SEContext(
            final int imageWidth, final int imageHeight
    ) {
        this(new ProjectInfo(), new ProjectState(imageWidth, imageHeight), imageWidth, imageHeight);
    }

    public SEContext(
            final ProjectInfo projectInfo, final ProjectState projectState,
            final int imageWidth, final int imageHeight
    ) {
        this.projectInfo = projectInfo;
        stateManager = new StateManager(projectState);
        renderInfo = new RenderInfo(imageWidth, imageHeight);
        playbackInfo = new PlaybackInfo();

        targetPixel = Constants.NO_VALID_TARGET;
        inWorkspaceBounds = false;

        redrawSelectionOverlay();
    }

    public void redrawSelectionOverlay() {
        final Set<Coord2D> selection = getState().getSelection();

        selectionOverlay = getState().hasSelection()
                ? Selection.drawOverlay(selection, (x, y) ->
                        selection.contains(new Coord2D(x, y)),
                (int) renderInfo.getZoomFactor()) : GameImage.dummy();
    }

    public GameImage drawWorkspace() {
        final GameImage workspace = new GameImage(Constants.WORKSPACE_W, Constants.WORKSPACE_H);
        workspace.fillRectangle(Constants.BACKGROUND, 0, 0,
                Constants.WORKSPACE_W, Constants.WORKSPACE_H);

        final float zoomFactor = renderInfo.getZoomFactor();
        final Coord2D render = getImageRenderPositionInWorkspace();
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();
        workspace.draw(generateCheckers(), render.x, render.y);
        workspace.draw(getState().draw(true, getState().getFrameIndex()), render.x, render.y,
                (int)(w * zoomFactor), (int)(h * zoomFactor));

        final Tool tool = StippleEffect.get().getTool();

        if (isTargetingPixelOnCanvas()) {
            if (tool instanceof ToolWithBreadth twb && zoomFactor >= Constants.ZOOM_FOR_OVERLAY) {
                final GameImage overlay = twb.getOverlay();
                final int offset = twb.breadthOffset();

                workspace.draw(overlay, render.x +
                        (int)((targetPixel.x - offset) * zoomFactor) -
                        Constants.OVERLAY_BORDER_PX, render.y +
                        (int)((targetPixel.y - offset) * zoomFactor) -
                        Constants.OVERLAY_BORDER_PX);
            }
        }

        if (tool instanceof BoxSelect boxSelect && boxSelect.isDrawing()) {
            final Coord2D tl = boxSelect.getTopLeft();
            final GameImage boxOverlay = boxSelect.getOverlay();

            workspace.draw(boxOverlay,
                    (render.x + (int)(tl.x * zoomFactor))
                            - Constants.OVERLAY_BORDER_PX,
                    (render.y + (int)(tl.y * zoomFactor))
                            - Constants.OVERLAY_BORDER_PX);
        }

        if (getState().hasSelection()) {
            final Coord2D tl = Selection.topLeft(getState().getSelection());

            workspace.draw(selectionOverlay,
                    (render.x + (int)(tl.x * zoomFactor))
                            - Constants.OVERLAY_BORDER_PX,
                    (render.y + (int)(tl.y * zoomFactor))
                            - Constants.OVERLAY_BORDER_PX);
        }

        return workspace.submit();
    }

    public void animate(final double deltaTime) {
        if (playbackInfo.isPlaying()) {
            final boolean nextFrameDue = playbackInfo.checkIfNextFrameDue(deltaTime);

            if (nextFrameDue)
                getState().nextFrame();
        }
    }

    public void process(final InputEventLogger eventLogger, final Tool tool) {
        setInWorkspaceBounds(eventLogger);
        setTargetPixel(eventLogger);
        processTools(eventLogger, tool);
        processSingleKeyInputs(eventLogger);
        processCompoundKeyInputs(eventLogger);
    }

    private void processTools(
            final InputEventLogger eventLogger, final Tool tool
    ) {
        if (StippleEffect.get().getTool() instanceof ToolWithMode) {
            ToolWithMode.setGlobal(eventLogger.isPressed(Key.SHIFT));

            if (eventLogger.isPressed(Key.CTRL))
                ToolWithMode.setMode(ToolWithMode.Mode.ADDITIVE);
            else if (eventLogger.isPressed(Key.S)) // TODO - Delta Time has an issue handling TAB and ALT
                ToolWithMode.setMode(ToolWithMode.Mode.SUBTRACTIVE);
            else
                ToolWithMode.setMode(ToolWithMode.Mode.SINGLE);
        }

        for (GameEvent e : eventLogger.getUnprocessedEvents()) {
            if (e instanceof GameMouseEvent me) {
                if (me.matchesAction(GameMouseEvent.Action.DOWN) &&
                        inWorkspaceBounds) {
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
                    GameKeyEvent.newKeyStroke(Key.N, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToNewProject
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.O, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().openProject()
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.S, GameKeyEvent.Action.PRESS),
                    () -> getProjectInfo().save()
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.A, GameKeyEvent.Action.PRESS),
                    this::selectAll
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.D, GameKeyEvent.Action.PRESS),
                    () -> deselect(true)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.I, GameKeyEvent.Action.PRESS),
                    this::invertSelection
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> getPlaybackInfo().incrementMillisPerFrame(-Constants.MILLIS_PER_FRAME_INC)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> getPlaybackInfo().incrementMillisPerFrame(Constants.MILLIS_PER_FRAME_INC)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    () -> getState().nextFrame()
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ENTER, GameKeyEvent.Action.PRESS),
                    () -> getState().setFrameIndex(getState().getFrameCount() - 1)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.R, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToResize
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.F, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().getContext().addFrame()
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.L, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().getContext().addLayer()
            );
        }

        // SHIFT but not CTRL
        if (!eventLogger.isPressed(Key.CTRL) && eventLogger.isPressed(Key.SHIFT)) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    () -> getState().previousFrame()
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ENTER, GameKeyEvent.Action.PRESS),
                    () -> getState().setFrameIndex(0)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.BACKSPACE, GameKeyEvent.Action.PRESS),
                    () -> fillSelection(true)
            );
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
                    DialogAssembly::setDialogToSave
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.R, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToPad
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.X, GameKeyEvent.Action.PRESS),
                    this::cropToSelection
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.F, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().getContext().duplicateFrame()
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.L, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().getContext().duplicateLayer()
            );
        }
    }

    private void processSingleKeyInputs(final InputEventLogger eventLogger) {
        if (!(eventLogger.isPressed(Key.CTRL) || eventLogger.isPressed(Key.SHIFT))) {
            // playback
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    () -> getPlaybackInfo().togglePlaying()
            );

            // snap to center of image
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ENTER, GameKeyEvent.Action.PRESS),
                    () -> getRenderInfo().setAnchor(new Coord2D(
                            getState().getImageWidth() / 2,
                            getState().getImageHeight() / 2)));

            // fill selection
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.BACKSPACE, GameKeyEvent.Action.PRESS),
                    () -> fillSelection(false));

            // delete selection contents
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.DELETE, GameKeyEvent.Action.PRESS),
                    this::deleteSelectionContents);

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
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.F, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Fill.get())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.W, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Wand.get())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.T, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(PencilSelect.get())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.X, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(BoxSelect.get())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.M, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(MoveSelection.get())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.U, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO - pick up selection
            );

            // tool modifications
            if (StippleEffect.get().getTool() instanceof ToolWithBreadth twr) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        twr::decreaseBreadth
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        twr::increaseBreadth
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> twr.setBreadth(twr.getBreadth() + Constants.BREADTH_INC)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> twr.setBreadth(twr.getBreadth() - Constants.BREADTH_INC)
                );
            } else if (StippleEffect.get().getTool() instanceof ToolThatSearches tts) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        tts::decreaseTolerance
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        tts::increaseTolerance
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> tts.setTolerance(tts.getTolerance() + Constants.BIG_TOLERANCE_INC)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> tts.setTolerance(tts.getTolerance() - Constants.BIG_TOLERANCE_INC)
                );
            } else if (StippleEffect.get().getTool().equals(Hand.get())) {
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
            } else if (StippleEffect.get().getTool().equals(MoveSelection.get())) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelection(new Coord2D(0, -1))
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelection(new Coord2D(0, 1))
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelection(new Coord2D(-1, 0))
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelection(new Coord2D(1, 0))
                );
            } else if (StippleEffect.get().getTool() instanceof Zoom) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> {
                            getRenderInfo().zoomIn();
                            StippleEffect.get().rebuildToolButtonMenu();
                        });
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> {
                            getRenderInfo().zoomOut();
                            StippleEffect.get().rebuildToolButtonMenu();
                        });
            }
        }
    }

    private Coord2D getMouseOffsetInWorkspace(final InputEventLogger eventLogger) {
        final Coord2D
                m = eventLogger.getAdjustedMousePosition(),
                wp = Constants.getWorkspacePosition();
        return new Coord2D(m.x - wp.x, m.y - wp.y);
    }

    private void setInWorkspaceBounds(final InputEventLogger eventLogger) {
        final Coord2D workshopM = getMouseOffsetInWorkspace(eventLogger);
        inWorkspaceBounds =  workshopM.x > 0 &&
                workshopM.x < Constants.WORKSPACE_W &&
                workshopM.y > 0 && workshopM.y < Constants.WORKSPACE_H;
    }

    private void setTargetPixel(final InputEventLogger eventLogger) {
        final Coord2D workshopM = getMouseOffsetInWorkspace(eventLogger);

        if (inWorkspaceBounds) {
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final float zoomFactor = renderInfo.getZoomFactor();
            final Coord2D render = getImageRenderPositionInWorkspace(),
                    bottomLeft = new Coord2D(render.x + (int)(zoomFactor * w),
                            render.y + (int)(zoomFactor * h));
            final int targetX = (int)(((workshopM.x - render.x) / (double)(bottomLeft.x - render.x)) * w),
                    targetY = (int)(((workshopM.y - render.y) / (double)(bottomLeft.y - render.y)) * h);

            targetPixel = new Coord2D(targetX, targetY);
        } else
            targetPixel = Constants.NO_VALID_TARGET;
    }

    private Coord2D getImageRenderPositionInWorkspace() {
        final float zoomFactor = renderInfo.getZoomFactor();
        final Coord2D anchor = renderInfo.getAnchor(),
                middle = new Coord2D(Constants.WORKSPACE_W / 2, Constants.WORKSPACE_H / 2);

        return new Coord2D(middle.x - (int)(zoomFactor * anchor.x),
                middle.y - (int)(zoomFactor * anchor.y));
    }

    private GameImage generateCheckers() {
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();
        final float zoomFactor = renderInfo.getZoomFactor();

        final GameImage image = new GameImage((int)(w * zoomFactor), (int)(h * zoomFactor));

        for (int x = 0; x < image.getWidth(); x += Constants.CHECKER_INCREMENT) {
            for (int y = 0; y < image.getHeight(); y += Constants.CHECKER_INCREMENT) {
                final Color c = ((x / Constants.CHECKER_INCREMENT) +
                        (y / Constants.CHECKER_INCREMENT)) % 2 == 0
                        ? Constants.WHITE : Constants.ACCENT_BACKGROUND_LIGHT;

                image.fillRectangle(c, x, y, Constants.CHECKER_INCREMENT,
                        Constants.CHECKER_INCREMENT);
            }
        }

        return image.submit();
    }

    // process all actions here and feed through state manager

    // SELECTION

    // move selection
    public void moveSelection(final Coord2D displacement) {
        final Set<Coord2D> selection = getState().getSelection();

        if (!selection.isEmpty()) {
            // build resultant state
            final Set<Coord2D> moved = selection.stream().map(s ->
                    s.displace(displacement)).collect(Collectors.toSet());

            final ProjectState result = new ProjectState(
                    getState().getImageWidth(), getState().getImageHeight(),
                    new ArrayList<>(getState().getLayers()),
                    getState().getLayerEditIndex(), getState().getFrameCount(),
                    getState().getFrameIndex(), moved);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // crop to selection
    public void cropToSelection() {
        final Set<Coord2D> selection = getState().getSelection();

        if (!selection.isEmpty()) {
            // build resultant state
            final Coord2D tl = Selection.topLeft(selection),
                    br = Selection.bottomRight(selection);
            final int w = br.x - tl.x, h = br.y - tl.y;

            final List<SELayer> layers = getState().getLayers().stream()
                    .map(layer -> layer.returnPadded(
                            -tl.x, -tl.y, w, h)).toList();

            final ProjectState result = new ProjectState(w, h, layers,
                    getState().getLayerEditIndex(), getState().getFrameCount(),
                    getState().getFrameIndex(), new HashSet<>());
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // TODO - cut

    // TODO - copy

    // delete selection contents
    public void deleteSelectionContents() {
        final Set<Coord2D> selection = getState().getSelection();

        if (!selection.isEmpty()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();

            final boolean[][] eraseMask = new boolean[w][h];
            selection.forEach(s -> eraseMask[s.x][s.y] = true);

            erase(eraseMask);
        }
    }

    // fill selection
    public void fillSelection(final boolean secondary) {
        final Set<Coord2D> selection = getState().getSelection();

        if (!selection.isEmpty()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();

            final GameImage edit = new GameImage(w, h);
            final Color c = secondary ? StippleEffect.get().getSecondary()
                    : StippleEffect.get().getPrimary();
            selection.forEach(s -> edit.dot(c, s.x, s.y));

            editImage(edit);
            getState().markAsCheckpoint(true, this);
        }
    }

    // deselect
    public void deselect(final boolean checkpoint) {
        final Set<Coord2D> selection = getState().getSelection();

        if (!selection.isEmpty()) {
            // build resultant state
            final ProjectState result = new ProjectState(
                    getState().getImageWidth(), getState().getImageHeight(),
                    new ArrayList<>(getState().getLayers()),
                    getState().getLayerEditIndex(), getState().getFrameCount(),
                    getState().getFrameIndex(), new HashSet<>(), checkpoint);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // select all
    public void selectAll() {
        // build resultant state
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final Set<Coord2D> selection = new HashSet<>();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                selection.add(new Coord2D(x, y));

        final ProjectState result = new ProjectState(w, h,
                new ArrayList<>(getState().getLayers()),
                getState().getLayerEditIndex(), getState().getFrameCount(),
                getState().getFrameIndex(), selection);
        stateManager.performAction(result, ActionType.CANVAS);
        redrawSelectionOverlay();
    }

    // invert selection
    public void invertSelection() {
        // build resultant state
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final Set<Coord2D> willBe = new HashSet<>(),
                was = getState().getSelection();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (!was.contains(new Coord2D(x, y)))
                    willBe.add(new Coord2D(x, y));

        final ProjectState result = new ProjectState(w, h,
                new ArrayList<>(getState().getLayers()),
                getState().getLayerEditIndex(), getState().getFrameCount(),
                getState().getFrameIndex(), willBe);
        stateManager.performAction(result, ActionType.CANVAS);
        redrawSelectionOverlay();
    }

    // edit selection
    public void editSelection(final Set<Coord2D> edit, final boolean checkpoint) {
        // build resultant state
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final Set<Coord2D> selection = new HashSet<>();
        final ToolWithMode.Mode mode = ToolWithMode.getMode();

        if (mode == ToolWithMode.Mode.ADDITIVE || mode == ToolWithMode.Mode.SUBTRACTIVE)
            selection.addAll(getState().getSelection());

        if (mode == ToolWithMode.Mode.SUBTRACTIVE)
            selection.removeAll(edit);
        else
            selection.addAll(edit);

        final ProjectState result = new ProjectState(w, h,
                new ArrayList<>(getState().getLayers()),
                getState().getLayerEditIndex(), getState().getFrameCount(),
                getState().getFrameIndex(), selection, checkpoint);
        stateManager.performAction(result, ActionType.CANVAS);
        redrawSelectionOverlay();
    }

    public void pad() {
        // build resultant state
        final int left = DialogVals.getPadLeft(),
                right = DialogVals.getPadRight(),
                top = DialogVals.getPadTop(),
                bottom = DialogVals.getPadBottom(),
                w = left + getState().getImageWidth() + right,
                h = top + getState().getImageHeight() + bottom,
                frameCount = getState().getFrameCount();

        final List<SELayer> layers = getState().getLayers().stream()
                .map(layer -> layer.returnPadded(left, top, w, h)).toList();

        final ProjectState result = new ProjectState(w, h,
                layers, getState().getLayerEditIndex(), frameCount,
                getState().getFrameIndex(), new HashSet<>());
        stateManager.performAction(result, ActionType.CANVAS);
    }

    public void resize() {
        // build resultant state
        final int w = DialogVals.getResizeWidth(),
                h = DialogVals.getResizeHeight(),
                frameCount = getState().getFrameCount();

        final List<SELayer> layers = getState().getLayers().stream()
                .map(layer -> layer.returnResized(w, h)).toList();

        final ProjectState result = new ProjectState(w, h,
                layers, getState().getLayerEditIndex(), frameCount,
                getState().getFrameIndex(), new HashSet<>());
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // IMAGE EDITING
    public void editImage(final GameImage edit) {
        // build resultant state
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight(),
                frameIndex = getState().getFrameIndex();
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());
        final SELayer replacement = getState().getEditingLayer()
                .returnEdited(edit, frameIndex);
        final int layerEditIndex = getState().getLayerEditIndex();
        layers.remove(layerEditIndex);
        layers.add(layerEditIndex, replacement);

        final ProjectState result = new ProjectState(w, h, layers,
                layerEditIndex, getState().getFrameCount(),
                frameIndex, getState().getSelection(), false);
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // ERASING
    public void erase(final boolean[][] eraseMask) {
        // build resultant state
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight(),
                frameIndex = getState().getFrameIndex();
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());
        final SELayer replacement = getState().getEditingLayer()
                .returnEdited(eraseMask, frameIndex);
        final int layerEditIndex = getState().getLayerEditIndex();
        layers.remove(layerEditIndex);
        layers.add(layerEditIndex, replacement);

        final ProjectState result = new ProjectState(w, h, layers,
                layerEditIndex, getState().getFrameCount(),
                getState().getFrameIndex(), getState().getSelection(), false);
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // FRAME MANIPULATION
    // add frame
    public void addFrame() {
        // pre-check
        if (getState().canAddFrame()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            final int addIndex = getState().getFrameIndex() + 1;
            layers.replaceAll(l -> l.returnAddedFrame(addIndex, w, h));

            final ProjectState result = new ProjectState(w, h, layers,
                    getState().getLayerEditIndex(),
                    getState().getFrameCount() + 1,
                    addIndex, getState().getSelection());
            stateManager.performAction(result, ActionType.FRAME);
        }
    }

    // duplicate frame
    public void duplicateFrame() {
        // pre-check
        if (getState().canAddFrame()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight(),
                    frameIndex = getState().getFrameIndex();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            layers.replaceAll(l -> l.returnDuplicatedFrame(frameIndex));

            final ProjectState result = new ProjectState(w, h,
                    layers, getState().getLayerEditIndex(),
                    getState().getFrameCount() + 1,
                    frameIndex + 1, getState().getSelection());
            stateManager.performAction(result, ActionType.FRAME);
        }
    }

    // remove frame
    public void removeFrame() {
        // pre-check
        if (getState().canRemoveFrame()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight(),
                    frameIndex = getState().getFrameIndex();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            layers.replaceAll(l -> l.returnRemovedFrame(frameIndex));

            final ProjectState result = new ProjectState(w, h,
                    layers, getState().getLayerEditIndex(),
                    getState().getFrameCount() - 1,
                    frameIndex, getState().getSelection());
            stateManager.performAction(result, ActionType.FRAME);
        }
    }

    // LAYER MANIPULATION
    // enable all layers
    public void enableAllLayers() {
        // build resultant state
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final ProjectState result = new ProjectState(w, h,
                getState().getLayers().stream().map(SELayer::returnEnabled)
                        .collect(Collectors.toList()),
                getState().getLayerEditIndex(), getState().getFrameCount(),
                getState().getFrameIndex(), getState().getSelection());
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // isolate layer
    public void isolateLayer(final int layerIndex) {
        final List<SELayer> layers = getState().getLayers(),
                newLayers = new ArrayList<>();

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();

            for (int i = 0; i < layers.size(); i++) {
                final SELayer layer = i == layerIndex
                        ? layers.get(i).returnEnabled()
                        : layers.get(i).returnDisabled();

                newLayers.add(layer);
            }

            final ProjectState result = new ProjectState(w, h, newLayers,
                    getState().getLayerEditIndex(), getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // unlink frames in layer
    public void unlinkFramesInLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() &&
                layers.get(layerIndex).areFramesLinked()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final SELayer layer = layers.get(layerIndex).returnUnlinkedFrames();
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = new ProjectState(w, h, layers,
                    getState().getLayerEditIndex(), getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // link frames in layer
    public void linkFramesInLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() &&
                !layers.get(layerIndex).areFramesLinked()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final SELayer layer = layers.get(layerIndex).returnLinkedFrames(
                    getState().getFrameIndex());
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = new ProjectState(w, h, layers,
                    getState().getLayerEditIndex(), getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // disable layer
    public void disableLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() && layers.get(layerIndex).isEnabled()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final SELayer layer = layers.get(layerIndex).returnDisabled();
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = new ProjectState(w, h, layers,
                    getState().getLayerEditIndex(), getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // enable layer
    public void enableLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() && !layers.get(layerIndex).isEnabled()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final SELayer layer = layers.get(layerIndex).returnEnabled();
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = new ProjectState(w, h, layers,
                    getState().getLayerEditIndex(), getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // change layer name
    public void changeLayerName(final String name, final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final SELayer layer = layers.get(layerIndex).returnRenamed(name);
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = new ProjectState(w, h, layers,
                    getState().getLayerEditIndex(), getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // change layer opacity
    public void changeLayerOpacity(
            final double opacity, final int layerIndex,
            final boolean markAsCheckpoint
    ) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final SELayer layer = layers.get(layerIndex).returnChangedOpacity(opacity);
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = new ProjectState(w, h, layers,
                    getState().getLayerEditIndex(), getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection(),
                    markAsCheckpoint);
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // add layer
    public void addLayer() {
        // pre-check
        if (getState().canAddLayer()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int addIndex = getState().getLayerEditIndex() + 1;
            layers.add(addIndex, SELayer.newLayer(w, h, getState().getFrameCount()));

            final ProjectState result = new ProjectState(w, h, layers,
                    addIndex, getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // add layer
    public void duplicateLayer() {
        // pre-check
        if (getState().canAddLayer()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int addIndex = getState().getLayerEditIndex() + 1;
            layers.add(addIndex, getState().getEditingLayer().duplicate());

            final ProjectState result = new ProjectState(w, h, layers,
                    addIndex, getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // remove layer
    public void removeLayer() {
        // pre-check
        if (getState().canRemoveLayer()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int index = getState().getLayerEditIndex();
            layers.remove(index);

            final ProjectState result = new ProjectState(w, h, layers,
                    index > 0 ? index - 1 : index, getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // move layer down
    public void moveLayerDown() {
        // pre-check
        if (getState().canMoveLayerDown()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int removalIndex = getState().getLayerEditIndex(),
                    reinsertionIndex = removalIndex - 1;

            final SELayer toMove = layers.get(removalIndex);
            layers.remove(removalIndex);
            layers.add(reinsertionIndex, toMove);

            final ProjectState result = new ProjectState(w, h, layers,
                    reinsertionIndex, getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // move layer up
    public void moveLayerUp() {
        // pre-check
        if (getState().canMoveLayerUp()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int removalIndex = getState().getLayerEditIndex(),
                    reinsertionIndex = removalIndex + 1;

            final SELayer toMove = layers.get(removalIndex);
            layers.remove(removalIndex);
            layers.add(reinsertionIndex, toMove);

            final ProjectState result = new ProjectState(w, h, layers,
                    reinsertionIndex, getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // merge with layer below
    public void mergeWithLayerBelow() {
        // pre-check - identical pass case as can move layer down
        if (getState().canMoveLayerDown()) {
            // build resultant state
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int aboveIndex = getState().getLayerEditIndex(),
                    belowIndex = aboveIndex - 1;

            final SELayer above = layers.get(aboveIndex),
                    below = layers.get(belowIndex);
            final SELayer merged = LayerMerger.merge(above, below,
                    getState().getFrameIndex(), getState().getFrameCount());
            layers.remove(above);
            layers.remove(below);
            layers.add(belowIndex, merged);

            final ProjectState result = new ProjectState(w, h, layers,
                    belowIndex, getState().getFrameCount(),
                    getState().getFrameIndex(), getState().getSelection());
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // GETTERS
    public boolean isTargetingPixelOnCanvas() {
        return targetPixel.x >= 0 && targetPixel.y >= 0 &&
                targetPixel.x < getState().getImageWidth() &&
                targetPixel.y < getState().getImageHeight();
    }

    public boolean isInWorkspaceBounds() {
        return inWorkspaceBounds;
    }

    public Coord2D getTargetPixel() {
        return targetPixel;
    }

    public String getTargetPixelText() {
        return isTargetingPixelOnCanvas() ? targetPixel.toString() : "--";
    }

    public String getCanvasSizeText() {
        return getState().getImageWidth() + "x" + getState().getImageHeight();
    }

    public String getSelectionText() {
        final Set<Coord2D> selection = getState().getSelection();
        final Coord2D tl = Selection.topLeft(selection),
                br = Selection.bottomRight(selection);
        final boolean multiple = selection.size() > 1;

        return selection.isEmpty() ? "--" : selection.size() + "px " +
                (multiple ? "from " : "at ") + tl + (multiple ? (" to " + br) : "");
    }

    public PlaybackInfo getPlaybackInfo() {
        return playbackInfo;
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
