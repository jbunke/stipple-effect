package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.delta_time.events.*;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.LayerMerger;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.selection.SelectionBounds;
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
    public final ProjectInfo projectInfo;
    private final StateManager stateManager;
    public final RenderInfo renderInfo;
    public final PlaybackInfo playbackInfo;

    private boolean inWorkspaceBounds;
    private Coord2D targetPixel;

    private GameImage selectionOverlay;

    public SEContext(
            final int imageWidth, final int imageHeight
    ) {
        this(new ProjectInfo(), ProjectState.makeNew(imageWidth, imageHeight),
                imageWidth, imageHeight);
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
                ? SelectionBounds.drawOverlay(selection, (x, y) ->
                        selection.contains(new Coord2D(x, y)),
                (int) renderInfo.getZoomFactor(),
                !StippleEffect.get().getTool().equals(PickUpSelection.get()))
                : GameImage.dummy();
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

        if (inWorkspaceBounds) {
            if (tool instanceof ToolWithBreadth twb && zoomFactor >= Constants.ZOOM_FOR_OVERLAY) {
                final GameImage overlay = twb.getOverlay();
                final int offset = twb.breadthOffset();

                workspace.draw(overlay, render.x +
                        Math.round((targetPixel.x - offset) * zoomFactor) -
                        Constants.OVERLAY_BORDER_PX, render.y +
                        Math.round((targetPixel.y - offset) * zoomFactor) -
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
            final Coord2D tl = SelectionBounds.topLeft(getState().getSelection());

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
                playbackInfo.executeAnimation(getState());
        }
    }

    public void process(final InputEventLogger eventLogger, final Tool tool) {
        setInWorkspaceBounds(eventLogger);
        setTargetPixel(eventLogger);
        processTools(eventLogger, tool);
        processSingleKeyInputs(eventLogger);
        processCompoundKeyInputs(eventLogger);
        processAdditionalMouseEvents(eventLogger);
    }

    private void processTools(
            final InputEventLogger eventLogger, final Tool tool
    ) {
        if (StippleEffect.get().getTool() instanceof ToolWithMode) {
            ToolWithMode.setGlobal(eventLogger.isPressed(Key.SHIFT) &&
                    !eventLogger.isPressed(Key.CTRL));

            if (eventLogger.isPressed(Key.CTRL) &&
                    eventLogger.isPressed(Key.SHIFT)) {
                ToolWithMode.setMode(ToolWithMode.Mode.SUBTRACTIVE);
            } else if (eventLogger.isPressed(Key.CTRL)) {
                ToolWithMode.setMode(ToolWithMode.Mode.ADDITIVE);
            } else {
                ToolWithMode.setMode(ToolWithMode.Mode.SINGLE);
            }
        } else if (StippleEffect.get().getTool() instanceof ToolThatDraws) {
            if (eventLogger.isPressed(Key.CTRL)) {
                ToolThatDraws.setMode(ToolThatDraws.Mode.DITHERING);
            } else if (eventLogger.isPressed(Key.SHIFT)) {
                ToolThatDraws.setMode(ToolThatDraws.Mode.BLEND);
            } else {
                ToolThatDraws.setMode(ToolThatDraws.Mode.NORMAL);
            }
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

    public void processAdditionalMouseEvents(final InputEventLogger eventLogger) {
        final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();

        for (GameEvent e : unprocessed)
            if (e instanceof GameMouseScrollEvent mse) {
                if (eventLogger.isPressed(Key.CTRL)) {
                    mse.markAsProcessed();

                    if (mse.clicksScrolled < 0)
                        getState().previousFrame();
                    else
                        getState().nextFrame();
                } else if (eventLogger.isPressed(Key.SHIFT)) {
                    if (eventLogger.isPressed(Key.R)) {
                        mse.markAsProcessed();

                        StippleEffect.get().incrementSelectedColorRGBA(
                                mse.clicksScrolled, 0, 0, 0);
                    } else if (eventLogger.isPressed(Key.G)) {
                        mse.markAsProcessed();

                        StippleEffect.get().incrementSelectedColorRGBA(
                                0, mse.clicksScrolled, 0, 0);
                    } else if (eventLogger.isPressed(Key.B)) {
                        mse.markAsProcessed();

                        StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, mse.clicksScrolled, 0);
                    } else if (eventLogger.isPressed(Key.A)) {
                        mse.markAsProcessed();

                        StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, 0, mse.clicksScrolled);
                    }
                } else if (inWorkspaceBounds) {
                    mse.markAsProcessed();

                    if (mse.clicksScrolled < 0)
                        renderInfo.zoomIn();
                    else
                        renderInfo.zoomOut();

                    StippleEffect.get().rebuildToolButtonMenu();
                }
            }
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
                    projectInfo::save
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
                    () -> playbackInfo.incrementMillisPerFrame(-Constants.MILLIS_PER_FRAME_INC)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> playbackInfo.incrementMillisPerFrame(Constants.MILLIS_PER_FRAME_INC)
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
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.X, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO: cut
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.C, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO: copy
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.V, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO: paste
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.H, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToInfo
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
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    playbackInfo::toggleMode
            );
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
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.V, GameKeyEvent.Action.PRESS),
                    () -> {} // TODO: paste onto new layer
            );
        }
    }

    private void processSingleKeyInputs(final InputEventLogger eventLogger) {
        if (!(eventLogger.isPressed(Key.CTRL) || eventLogger.isPressed(Key.SHIFT))) {
            // playback
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    playbackInfo::togglePlaying
            );

            // snap to center of image
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ENTER, GameKeyEvent.Action.PRESS),
                    () -> renderInfo.setAnchor(new Coord2D(
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
                    () -> StippleEffect.get().setTool(PickUpSelection.get())
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
                        () -> renderInfo.incrementAnchor(new Coord2D(0, 1))
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> renderInfo.incrementAnchor(new Coord2D(0, -1))
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> renderInfo.incrementAnchor(new Coord2D(1, 0))
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> renderInfo.incrementAnchor(new Coord2D(-1, 0))
                );
            } else if (Tool.canMoveSelection(StippleEffect.get().getTool())) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelection(new Coord2D(0, -1), true)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelection(new Coord2D(0, 1), true)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelection(new Coord2D(-1, 0), true)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelection(new Coord2D(1, 0), true)
                );
            } else if (StippleEffect.get().getTool() instanceof Zoom) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> {
                            renderInfo.zoomIn();
                            StippleEffect.get().rebuildToolButtonMenu();
                        });
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> {
                            renderInfo.zoomOut();
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
        final Coord2D workspaceM = getMouseOffsetInWorkspace(eventLogger);
        inWorkspaceBounds =  workspaceM.x > 0 &&
                workspaceM.x < Constants.WORKSPACE_W &&
                workspaceM.y > 0 && workspaceM.y < Constants.WORKSPACE_H;
    }

    private void setTargetPixel(final InputEventLogger eventLogger) {
        final Coord2D workspaceM = getMouseOffsetInWorkspace(eventLogger);

        if (inWorkspaceBounds) {
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final float zoomFactor = renderInfo.getZoomFactor();
            final Coord2D render = getImageRenderPositionInWorkspace(),
                    bottomRight = new Coord2D(render.x + (int)(zoomFactor * w),
                            render.y + (int)(zoomFactor * h));
            final int targetX = (int)(((workspaceM.x - render.x) /
                    (double)(bottomRight.x - render.x)) * w) -
                    (workspaceM.x < render.x ? 1 : 0),
                    targetY = (int)(((workspaceM.y - render.y) /
                            (double)(bottomRight.y - render.y)) * h) -
                            (workspaceM.y < render.y ? 1 : 0);

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
    public void moveSelection(final Coord2D displacement, final boolean checkpoint) {
        final Set<Coord2D> selection = getState().getSelection();

        if (!selection.isEmpty()) {
            final Set<Coord2D> moved = selection.stream().map(s ->
                    s.displace(displacement)).collect(Collectors.toSet());

            final ProjectState result = getState().changeSelection(moved)
                    .changeIsCheckpoint(checkpoint);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // crop to selection
    public void cropToSelection() {
        final Set<Coord2D> selection = getState().getSelection();

        if (!selection.isEmpty()) {
            final Coord2D tl = SelectionBounds.topLeft(selection),
                    br = SelectionBounds.bottomRight(selection);
            final int w = br.x - tl.x, h = br.y - tl.y;

            final List<SELayer> layers = getState().getLayers().stream()
                    .map(layer -> layer.returnPadded(
                            -tl.x, -tl.y, w, h)).toList();

            final ProjectState result = getState().resize(w, h, layers);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();

            moveSelection(new Coord2D(-tl.x, -tl.y), false);
        }
    }

    // TODO - cut

    // TODO - copy

    // TODO - paste

    // delete selection contents
    public void deleteSelectionContents() {
        final Set<Coord2D> selection = getState().getSelection();

        if (!selection.isEmpty()) {
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();

            final boolean[][] eraseMask = new boolean[w][h];
            selection.forEach(s -> {
                if (s.x >= 0 && s.x < w && s.y >= 0 && s.y < h)
                    eraseMask[s.x][s.y] = true;
            });

            erase(eraseMask, true);
        }
    }

    // fill selection
    public void fillSelection(final boolean secondary) {
        final Set<Coord2D> selection = getState().getSelection();

        if (!selection.isEmpty()) {
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();

            final GameImage edit = new GameImage(w, h);
            final Color c = secondary ? StippleEffect.get().getSecondary()
                    : StippleEffect.get().getPrimary();
            selection.forEach(s -> edit.dot(c, s.x, s.y));

            paintOverImage(edit);
            getState().markAsCheckpoint(true, this);
        }
    }

    // deselect
    public void deselect(final boolean checkpoint) {
        if (!getState().getSelection().isEmpty()) {
            final ProjectState result = getState().changeSelection(
                    new HashSet<>()).changeIsCheckpoint(checkpoint);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // select all
    public void selectAll() {
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final Set<Coord2D> selection = new HashSet<>();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                selection.add(new Coord2D(x, y));

        final ProjectState result = getState().changeSelection(selection);
        stateManager.performAction(result, ActionType.CANVAS);
        redrawSelectionOverlay();
    }

    // invert selection
    public void invertSelection() {
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final Set<Coord2D> willBe = new HashSet<>(),
                was = getState().getSelection();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (!was.contains(new Coord2D(x, y)))
                    willBe.add(new Coord2D(x, y));

        final ProjectState result = getState().changeSelection(willBe);
        stateManager.performAction(result, ActionType.CANVAS);
        redrawSelectionOverlay();
    }

    // edit selection
    public void editSelection(final Set<Coord2D> edit, final boolean checkpoint) {
        final Set<Coord2D> selection = new HashSet<>();
        final ToolWithMode.Mode mode = ToolWithMode.getMode();

        if (mode == ToolWithMode.Mode.ADDITIVE || mode == ToolWithMode.Mode.SUBTRACTIVE)
            selection.addAll(getState().getSelection());

        if (mode == ToolWithMode.Mode.SUBTRACTIVE)
            selection.removeAll(edit);
        else
            selection.addAll(edit);

        final ProjectState result = getState().changeSelection(
                selection).changeIsCheckpoint(checkpoint);
        stateManager.performAction(result, ActionType.CANVAS);
        redrawSelectionOverlay();
    }

    public void pad() {
        final int left = DialogVals.getPadLeft(),
                right = DialogVals.getPadRight(),
                top = DialogVals.getPadTop(),
                bottom = DialogVals.getPadBottom(),
                w = left + getState().getImageWidth() + right,
                h = top + getState().getImageHeight() + bottom;

        final List<SELayer> layers = getState().getLayers().stream()
                .map(layer -> layer.returnPadded(left, top, w, h)).toList();

        final ProjectState result = getState().resize(w, h, layers)
                .changeSelection(new HashSet<>()).changeIsCheckpoint(true);
        stateManager.performAction(result, ActionType.CANVAS);
    }

    public void resize() {
        final int w = DialogVals.getResizeWidth(),
                h = DialogVals.getResizeHeight();

        final List<SELayer> layers = getState().getLayers().stream()
                .map(layer -> layer.returnResized(w, h)).toList();

        final ProjectState result = getState().resize(w, h, layers)
                .changeSelection(new HashSet<>());
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // IMAGE EDITING
    public void stampImage(final GameImage edit) {
        final int frameIndex = getState().getFrameIndex();
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());
        final SELayer replacement = getState().getEditingLayer()
                .returnStamped(edit, frameIndex);
        final int layerEditIndex = getState().getLayerEditIndex();
        layers.remove(layerEditIndex);
        layers.add(layerEditIndex, replacement);

        final ProjectState result = getState().changeLayers(layers);
        stateManager.performAction(result, ActionType.CANVAS);
    }

    public void paintOverImage(final GameImage edit) {
        final int frameIndex = getState().getFrameIndex();

        final List<SELayer> layers = new ArrayList<>(getState().getLayers());
        final SELayer replacement = getState().getEditingLayer()
                .returnPaintedOver(edit, frameIndex);
        final int layerEditIndex = getState().getLayerEditIndex();
        layers.remove(layerEditIndex);
        layers.add(layerEditIndex, replacement);

        final ProjectState result = getState().changeLayers(layers)
                .changeIsCheckpoint(false);
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // ERASING
    public void erase(final boolean[][] eraseMask, final boolean checkpoint) {
        final int frameIndex = getState().getFrameIndex();
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());
        final SELayer replacement = getState().getEditingLayer()
                .returnErased(eraseMask, frameIndex);
        final int layerEditIndex = getState().getLayerEditIndex();
        layers.remove(layerEditIndex);
        layers.add(layerEditIndex, replacement);

        final ProjectState result = getState().changeLayers(layers)
                .changeIsCheckpoint(checkpoint);
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // FRAME MANIPULATION
    // add frame
    public void addFrame() {
        // pre-check
        if (getState().canAddFrame()) {
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            final int addIndex = getState().getFrameIndex() + 1;
            layers.replaceAll(l -> l.returnAddedFrame(addIndex, w, h));

            final ProjectState result = getState().changeFrames(layers,
                    addIndex, getState().getFrameCount() + 1);
            stateManager.performAction(result, ActionType.FRAME);
        }
    }

    // duplicate frame
    public void duplicateFrame() {
        // pre-check
        if (getState().canAddFrame()) {
            final int frameIndex = getState().getFrameIndex();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            layers.replaceAll(l -> l.returnDuplicatedFrame(frameIndex));

            final ProjectState result = getState().changeFrames(
                    layers, frameIndex + 1,
                    getState().getFrameCount() + 1);
            stateManager.performAction(result, ActionType.FRAME);
        }
    }

    // remove frame
    public void removeFrame() {
        // pre-check
        if (getState().canRemoveFrame()) {
            final int frameIndex = getState().getFrameIndex();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            layers.replaceAll(l -> l.returnRemovedFrame(frameIndex));

            final ProjectState result = getState().changeFrames(layers,
                    frameIndex - 1, getState().getFrameCount() - 1);
            stateManager.performAction(result, ActionType.FRAME);
        }
    }

    // LAYER MANIPULATION
    // enable all layers
    public void enableAllLayers() {
        final ProjectState result = getState().changeLayers(
                getState().getLayers().stream().map(SELayer::returnEnabled)
                        .collect(Collectors.toList()));
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // isolate layer
    public void isolateLayer(final int layerIndex) {
        final List<SELayer> layers = getState().getLayers(),
                newLayers = new ArrayList<>();

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size()) {
            for (int i = 0; i < layers.size(); i++) {
                final SELayer layer = i == layerIndex
                        ? layers.get(i).returnEnabled()
                        : layers.get(i).returnDisabled();

                newLayers.add(layer);
            }

            final ProjectState result = getState().changeLayers(newLayers);
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // unlink frames in layer
    public void unlinkFramesInLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() &&
                layers.get(layerIndex).areFramesLinked()) {
            final SELayer layer = layers.get(layerIndex).returnUnlinkedFrames();
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers);
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // link frames in layer
    public void linkFramesInLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() &&
                !layers.get(layerIndex).areFramesLinked()) {
            final SELayer layer = layers.get(layerIndex).returnLinkedFrames(
                    getState().getFrameIndex());
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers);
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // disable layer
    public void disableLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() &&
                layers.get(layerIndex).isEnabled()) {
            final SELayer layer = layers.get(layerIndex).returnDisabled();
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers);
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // enable layer
    public void enableLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() &&
                !layers.get(layerIndex).isEnabled()) {
            final SELayer layer = layers.get(layerIndex).returnEnabled();
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers);
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // change layer name
    public void changeLayerName(final String name, final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size()) {
            final SELayer layer = layers.get(layerIndex).returnRenamed(name);
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers);
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
            final SELayer layer = layers.get(layerIndex).returnChangedOpacity(opacity);
            layers.remove(layerIndex);
            layers.add(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers)
                    .changeIsCheckpoint(markAsCheckpoint);
            stateManager.performAction(result, ActionType.CANVAS);
        }
    }

    // add layer
    public void addLayer() {
        // pre-check
        if (getState().canAddLayer()) {
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int addIndex = getState().getLayerEditIndex() + 1;
            layers.add(addIndex, SELayer.newLayer(w, h, getState().getFrameCount()));

            final ProjectState result = getState().changeLayers(
                    layers, addIndex);
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // add layer
    public void duplicateLayer() {
        // pre-check
        if (getState().canAddLayer()) {
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int addIndex = getState().getLayerEditIndex() + 1;
            layers.add(addIndex, getState().getEditingLayer().duplicate());

            final ProjectState result = getState()
                    .changeLayers(layers, addIndex);
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // remove layer
    public void removeLayer() {
        // pre-check
        if (getState().canRemoveLayer()) {
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int index = getState().getLayerEditIndex();
            layers.remove(index);

            final ProjectState result = getState().changeLayers(
                    layers, index > 0 ? index - 1 : index);
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // move layer down
    public void moveLayerDown() {
        // pre-check
        if (getState().canMoveLayerDown()) {
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int removalIndex = getState().getLayerEditIndex(),
                    reinsertionIndex = removalIndex - 1;

            final SELayer toMove = layers.get(removalIndex);
            layers.remove(removalIndex);
            layers.add(reinsertionIndex, toMove);

            final ProjectState result = getState().changeLayers(
                    layers, reinsertionIndex);
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // move layer up
    public void moveLayerUp() {
        // pre-check
        if (getState().canMoveLayerUp()) {
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int removalIndex = getState().getLayerEditIndex(),
                    reinsertionIndex = removalIndex + 1;

            final SELayer toMove = layers.get(removalIndex);
            layers.remove(removalIndex);
            layers.add(reinsertionIndex, toMove);

            final ProjectState result = getState().changeLayers(
                    layers, reinsertionIndex);
            stateManager.performAction(result, ActionType.LAYER);
        }
    }

    // merge with layer below
    public void mergeWithLayerBelow() {
        // pre-check - identical pass case as can move layer down
        if (getState().canMoveLayerDown()) {
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

            final ProjectState result = getState().changeLayers(
                    layers, belowIndex);
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

    public String getImageSizeText() {
        return getState().getImageWidth() + "x" + getState().getImageHeight();
    }

    public String getSelectionText() {
        final Set<Coord2D> selection = getState().getSelection();
        final Coord2D tl = SelectionBounds.topLeft(selection),
                br = SelectionBounds.bottomRight(selection);
        final boolean multiple = selection.size() > 1;

        return selection.isEmpty() ? "--" : selection.size() + "px " +
                (multiple ? "from " : "at ") + tl + (multiple ? (" to " + br) : "");
    }

    public ProjectState getState() {
        return stateManager.getState();
    }

    public StateManager getStateManager() {
        return stateManager;
    }
}
