package com.jordanbunke.stipple_effect.project;

import com.jordanbunke.delta_time.events.*;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.DeltaTimeGlobal;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.LayerMerger;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.visual.DialogAssembly;
import com.jordanbunke.stipple_effect.selection.*;
import com.jordanbunke.stipple_effect.state.ActionType;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.state.StateManager;
import com.jordanbunke.stipple_effect.tools.*;
import com.jordanbunke.stipple_effect.utility.*;
import com.jordanbunke.stipple_effect.visual.PreviewWindow;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class SEContext {
    private static final int TL = 0, BR = 1, DIM = 2, TRP = 3, BOUNDS = 4;

    public final ProjectInfo projectInfo;
    private final StateManager stateManager;
    public final RenderInfo renderInfo;
    public final PlaybackInfo playbackInfo;

    private boolean inWorkspaceBounds;
    private Coord2D targetPixel;

    private GameImage selectionOverlay, checkerboard;

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

        redrawCheckerboard();
    }

    public void initializeRender() {
        snapToCenterOfImage();
        renderInfo.setZoomFactor(Constants.DEF_ZOOM);
    }

    public void redrawSelectionOverlay() {
        final Set<Coord2D> selection = getState().getSelection();

        final Tool tool = StippleEffect.get().getTool();

        final boolean movable = Tool.canMoveSelectionBounds(tool) ||
                tool.equals(Wand.get());

        selectionOverlay = getState().hasSelection()
                ? SelectionUtils.drawOverlay(selection, (x, y) ->
                        selection.contains(new Coord2D(x, y)),
                renderInfo.getZoomFactor(),
                movable, tool instanceof MoverTool) : GameImage.dummy();
    }

    private Coord2D[] getImageRenderBounds(
            final Coord2D render, final int w, final int h, final float z
    ) {
        final int ww = Layout.getWorkspaceWidth(),
                wh = Layout.getWorkspaceHeight();
        final Coord2D[] bounds = new Coord2D[BOUNDS];

        if (render.x > ww || render.y > wh ||
                render.x + (int)(w * z) <= 0 ||
                render.y + (int)(h * z) <= 0)
            return new Coord2D[] {};

        final int tlx, tly, brx, bry;

        final float modX = render.x % z, modY = render.y % z;

        tlx = Math.max((int)(-render.x / z), 0);
        tly = Math.max((int)(-render.y / z), 0);
        brx = Math.min((int)((ww - render.x) / z) + 1, w);
        bry = Math.min((int)((wh - render.y) / z) + 1, h);

        bounds[TL] = new Coord2D(tlx, tly);
        bounds[BR] = new Coord2D(brx, bry);
        bounds[DIM] = new Coord2D(brx - tlx, bry - tly);
        bounds[TRP] = new Coord2D(
                tlx == 0 ? render.x : (int) modX,
                tly == 0 ? render.y : (int) modY
        );

        return bounds;
    }

    public GameImage drawWorkspace() {
        final int ww = Layout.getWorkspaceWidth(),
                wh = Layout.getWorkspaceHeight();

        final GameImage workspace = new GameImage(ww, wh);

        // background
        workspace.fillRectangle(Constants.BACKGROUND, 0, 0, ww, wh);

        // math
        final float zoomFactor = renderInfo.getZoomFactor();
        final Coord2D render = getImageRenderPositionInWorkspace();
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();
        final Coord2D[] bounds = getImageRenderBounds(render, w, h, zoomFactor);

        if (bounds.length == BOUNDS) {
            // transparency checkerboard
            workspace.draw(checkerboard.section(bounds[TL], bounds[BR]),
                    bounds[TRP].x, bounds[TRP].y,
                    (int)(bounds[DIM].x * zoomFactor),
                    (int)(bounds[DIM].y * zoomFactor));

            // canvas
            GameImage canvas = getState().draw(true,
                    true, getState().getFrameIndex());

            workspace.draw(canvas.section(bounds[TL], bounds[BR]),
                    bounds[TRP].x, bounds[TRP].y,
                    (int)(bounds[DIM].x * zoomFactor),
                    (int)(bounds[DIM].y * zoomFactor));
        }

        // OVERLAYS
        final Tool tool = StippleEffect.get().getTool();

        if (inWorkspaceBounds) {
            // brush / eraser overlay
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

        // selection overlay - drawing box
        if (tool instanceof OverlayTool overlayTool && overlayTool.isDrawing()) {
            final Coord2D tl = overlayTool.getTopLeft();
            final GameImage boxOverlay = overlayTool.getSelectionOverlay();

            workspace.draw(boxOverlay,
                    (render.x + (int)(tl.x * zoomFactor))
                            - Constants.OVERLAY_BORDER_PX,
                    (render.y + (int)(tl.y * zoomFactor))
                            - Constants.OVERLAY_BORDER_PX);
        }

        // persistent selection overlay
        if (getState().hasSelection()) {
            final Coord2D tl = SelectionUtils.topLeft(getState().getSelection());

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
        processAdditionalMouseEvents(eventLogger);

        if (DeltaTimeGlobal.getStatusOf(Constants.TYPING_CODE)
                .orElse(Boolean.FALSE) instanceof Boolean b && !b) {
            processSingleKeyInputs(eventLogger);
            processCompoundKeyInputs(eventLogger);
        }
    }

    private void processTools(
            final InputEventLogger eventLogger, final Tool tool
    ) {
        if (tool instanceof ToolWithMode || tool.equals(BrushSelect.get())) {
            ToolWithMode.setGlobal(eventLogger.isPressed(Key.SHIFT));

            if (eventLogger.isPressed(Key.S)) {
                ToolWithMode.setMode(ToolWithMode.Mode.SUBTRACTIVE);
            } else if (eventLogger.isPressed(Key.CTRL)) {
                ToolWithMode.setMode(ToolWithMode.Mode.ADDITIVE);
            } else {
                ToolWithMode.setMode(ToolWithMode.Mode.SINGLE);
            }
        } else if (tool instanceof ToolThatDraws) {
            if (eventLogger.isPressed(Key.CTRL) &&
                    eventLogger.isPressed(Key.SHIFT)) {
                ToolThatDraws.setMode(ToolThatDraws.Mode.RANDOM_WITHIN_BOUNDS);
            } else if (eventLogger.isPressed(Key.CTRL)) {
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
                                mse.clicksScrolled * Constants.COLOR_SET_RGBA_INC, 0, 0, 0);
                    } else if (eventLogger.isPressed(Key.G)) {
                        mse.markAsProcessed();

                        StippleEffect.get().incrementSelectedColorRGBA(
                                0, mse.clicksScrolled * Constants.COLOR_SET_RGBA_INC, 0, 0);
                    } else if (eventLogger.isPressed(Key.B)) {
                        mse.markAsProcessed();

                        StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, mse.clicksScrolled * Constants.COLOR_SET_RGBA_INC, 0);
                    } else if (eventLogger.isPressed(Key.A)) {
                        mse.markAsProcessed();

                        StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, 0, mse.clicksScrolled * Constants.COLOR_SET_RGBA_INC);
                    } else if (StippleEffect.get().getTool() instanceof ToolWithBreadth twb) {
                        mse.markAsProcessed();

                        twb.setBreadth(twb.getBreadth() + mse.clicksScrolled);
                    } else if (StippleEffect.get().getTool() instanceof ToolThatSearches tts) {
                        mse.markAsProcessed();

                        tts.setTolerance(tts.getTolerance() + (mse.clicksScrolled *
                                Constants.SMALL_TOLERANCE_INC));
                    }
                } else if (inWorkspaceBounds) {
                    mse.markAsProcessed();

                    if (mse.clicksScrolled < 0)
                        renderInfo.zoomIn(targetPixel);
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
                    GameKeyEvent.newKeyStroke(Key.H, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToInfo
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.E, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToProgramSettings
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
                    GameKeyEvent.newKeyStroke(Key.R, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToResize
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
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    () -> getState().nextFrame()
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ENTER, GameKeyEvent.Action.PRESS),
                    playbackInfo::toggleMode
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> getState().setFrameIndex(0)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> getState().setFrameIndex(getState().getFrameCount() - 1)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                    () -> getState().editLayerAbove()
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                    () -> getState().editLayerBelow()
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.F, GameKeyEvent.Action.PRESS),
                    this::addFrame
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.L, GameKeyEvent.Action.PRESS),
                    () -> addLayer(true)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Q, GameKeyEvent.Action.PRESS),
                    this::toggleLayerLinking
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._1, GameKeyEvent.Action.PRESS),
                    () -> getState().getEditingLayer().setOnionSkinMode(
                            getState().getEditingLayer().getOnionSkinMode().next())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.BACKSPACE, GameKeyEvent.Action.PRESS),
                    this::removeFrame
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.X, GameKeyEvent.Action.PRESS),
                    this::cut
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.C, GameKeyEvent.Action.PRESS),
                    this::copy
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.V, GameKeyEvent.Action.PRESS),
                    () -> paste(false)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._4, GameKeyEvent.Action.PRESS),
                    () -> reflectSelection(true)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._5, GameKeyEvent.Action.PRESS),
                    () -> reflectSelection(false)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._9, GameKeyEvent.Action.PRESS),
                    () -> outlineSelection(DialogVals.getOutlineSideMask())
            );
        }

        // SHIFT but not CTRL
        if (!eventLogger.isPressed(Key.CTRL) && eventLogger.isPressed(Key.SHIFT)) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.BACKSPACE, GameKeyEvent.Action.PRESS),
                    () -> fillSelection(true)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    () -> {
                        PreviewWindow.set(this);
                        eventLogger.unpressAllKeys();
                    }
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.C, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().swapColors()
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.L, GameKeyEvent.Action.PRESS),
                    () -> DialogAssembly.setDialogToLayerSettings(getState().getLayerEditIndex())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.O, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToOutline
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._9, GameKeyEvent.Action.PRESS),
                    () -> outlineSelection(Outliner.getSingleOutlineMask())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._1, GameKeyEvent.Action.PRESS),
                    () -> {
                        final int index = getState().getLayerEditIndex();

                        if (getState().getEditingLayer().isEnabled())
                            disableLayer(index);
                        else
                            enableLayer(index);
                    }
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._2, GameKeyEvent.Action.PRESS),
                    () -> isolateLayer(getState().getLayerEditIndex())
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._3, GameKeyEvent.Action.PRESS),
                    this::enableAllLayers
            );

            // arrow keys only in these branches
            if (eventLogger.isPressed(Key.R)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                -Constants.COLOR_SET_RGBA_INC, 0, 0, 0)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                Constants.COLOR_SET_RGBA_INC, 0, 0, 0)
                );
            } else if (eventLogger.isPressed(Key.G)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, -Constants.COLOR_SET_RGBA_INC, 0, 0)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, Constants.COLOR_SET_RGBA_INC, 0, 0)
                );
            } else if (eventLogger.isPressed(Key.B)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, -Constants.COLOR_SET_RGBA_INC, 0)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, Constants.COLOR_SET_RGBA_INC, 0)
                );
            } else if (eventLogger.isPressed(Key.A)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, 0, -Constants.COLOR_SET_RGBA_INC)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, 0, Constants.COLOR_SET_RGBA_INC)
                );
            } else {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> playbackInfo.incrementFps(-Constants.PLAYBACK_FPS_INC)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> playbackInfo.incrementFps(Constants.PLAYBACK_FPS_INC)
                );
            }
        }

        // CTRL and SHIFT
        if (eventLogger.isPressed(Key.CTRL) && eventLogger.isPressed(Key.SHIFT)) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    () -> getState().previousFrame()
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                    this::moveLayerUp
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                    this::moveLayerDown
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                    this::moveFrameBack
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                    this::moveFrameForward
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
                    this::duplicateFrame
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.L, GameKeyEvent.Action.PRESS),
                    this::duplicateLayer
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.M, GameKeyEvent.Action.PRESS),
                    this::mergeWithLayerBelow
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.BACKSPACE, GameKeyEvent.Action.PRESS),
                    this::removeLayer
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.V, GameKeyEvent.Action.PRESS),
                    () -> paste(true)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._4, GameKeyEvent.Action.PRESS),
                    () -> reflectSelectionContents(true)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._5, GameKeyEvent.Action.PRESS),
                    () -> reflectSelectionContents(false)
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._9, GameKeyEvent.Action.PRESS),
                    () -> outlineSelection(Outliner.getDoubleOutlineMask())
            );
        }
    }

    private void processSingleKeyInputs(final InputEventLogger eventLogger) {
        if (!(eventLogger.isPressed(Key.CTRL) || eventLogger.isPressed(Key.SHIFT))) {
            // toggle playback
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    playbackInfo::togglePlaying);

            // snap to center of image
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ENTER, GameKeyEvent.Action.PRESS),
                    this::snapToCenterOfImage);

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
                    () -> StippleEffect.get().setTool(Zoom.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.H, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Hand.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.O, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(StipplePencil.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.P, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Pencil.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.B, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Brush.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.E, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Eraser.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.C, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(ColorPicker.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.F, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Fill.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.W, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(Wand.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.T, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(BrushSelect.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.X, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(BoxSelect.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.M, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(MoveSelection.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.U, GameKeyEvent.Action.PRESS),
                    () -> StippleEffect.get().setTool(PickUpSelection.get()));

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
            } else if (StippleEffect.get().getTool().equals(PickUpSelection.get())) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionContents(new Coord2D(0, -1), true)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionContents(new Coord2D(0, 1), true)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionContents(new Coord2D(-1, 0), true)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionContents(new Coord2D(1, 0), true)
                );
            } else if (Tool.canMoveSelectionBounds(StippleEffect.get().getTool())) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionBounds(new Coord2D(0, -1), true)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionBounds(new Coord2D(0, 1), true)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionBounds(new Coord2D(-1, 0), true)
                );
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionBounds(new Coord2D(1, 0), true)
                );
            } else if (StippleEffect.get().getTool().equals(Zoom.get())) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> {
                            renderInfo.zoomIn(targetPixel);
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
                wp = Layout.getWorkspacePosition();
        return new Coord2D(m.x - wp.x, m.y - wp.y);
    }

    private void setInWorkspaceBounds(final InputEventLogger eventLogger) {
        final Coord2D workspaceM = getMouseOffsetInWorkspace(eventLogger);
        inWorkspaceBounds =  workspaceM.x > 0 &&
                workspaceM.x < Layout.getWorkspaceWidth() &&
                workspaceM.y > 0 && workspaceM.y < Layout.getWorkspaceHeight();
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
                middle = new Coord2D(Layout.getWorkspaceWidth() / 2,
                        Layout.getWorkspaceHeight() / 2);

        return new Coord2D(middle.x - (int)(zoomFactor * anchor.x),
                middle.y - (int)(zoomFactor * anchor.y));
    }

    public void redrawCheckerboard() {
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final GameImage image = new GameImage(w, h);

        final int cb = Settings.getCheckerboardPixels();

        for (int x = 0; x < image.getWidth(); x += cb) {
            for (int y = 0; y < image.getHeight(); y += cb) {
                final Color c = ((x / cb) + (y / cb)) % 2 == 0
                        ? Constants.WHITE : Constants.ACCENT_BACKGROUND_LIGHT;

                image.fillRectangle(c, x, y, cb, cb);
            }
        }

        checkerboard = image.submit();
    }

    // non-state changes
    public void snapToCenterOfImage() {
        renderInfo.setAnchor(new Coord2D(
                getState().getImageWidth() / 2,
                getState().getImageHeight() / 2));
    }

    // copy - (not a state change unlike cut and paste)
    public void copy() {
        if (getState().hasSelection()) {
            SEClipboard.get().sendSelectionToClipboard(getState());
            StatusUpdates.sendToClipboard(true,
                    SEClipboard.get().getContents().getPixels());
        } else
            StatusUpdates.clipboardSendFailed(true);
    }

    // process all actions here and feed through state manager

    // SELECTION

    // outline selection
    public void outlineSelection(final boolean[] sideMask) {
        if (getState().hasSelection()) {
            ToolWithMode.setGlobal(false);
            ToolWithMode.setMode(ToolWithMode.Mode.SINGLE);

            editSelection(Outliner.outline(
                    getState().getSelection(), sideMask), true);
        }
    }

    public void resetContentOriginal() {
        if (getState().hasSelection() && getState().getSelectionMode() ==
                SelectionMode.CONTENTS) {
            final SelectionContents reset = getState()
                    .getSelectionContents().returnDisplaced(new Coord2D());

            final ProjectState result = getState()
                    .changeSelectionContents(reset)
                    .changeIsCheckpoint(true);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // move selection contents
    public void moveSelectionContents(
            final Coord2D displacement, final boolean checkpoint
    ) {
        if (getState().hasSelection() && getState().getSelectionMode() ==
                SelectionMode.CONTENTS) {
            final SelectionContents moved = getState()
                    .getSelectionContents().returnDisplaced(displacement);

            final ProjectState result = getState()
                    .changeSelectionContents(moved)
                    .changeIsCheckpoint(checkpoint);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // stretch selection contents
    public void stretchSelectionContents(
            final Set<Coord2D> initialSelection, final Coord2D change,
            final MoverTool.Direction direction, final boolean checkpoint
    ) {
        if (getState().hasSelection() && getState().getSelectionMode() ==
                SelectionMode.CONTENTS) {
            final SelectionContents stretched =
                    getState().getSelectionContents()
                    .returnStretched(initialSelection, change, direction);

            final ProjectState result = getState()
                    .changeSelectionContents(stretched)
                    .changeIsCheckpoint(checkpoint);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // rotate selection contents
    public void rotateSelectionContents(
            final Set<Coord2D> initialSelection, final double deltaR,
            final Coord2D pivot, final boolean[] offset, final boolean checkpoint
    ) {
        if (getState().hasSelection() && getState().getSelectionMode() ==
                SelectionMode.CONTENTS) {
            final SelectionContents rotated =
                    getState().getSelectionContents()
                            .returnRotated(initialSelection,
                                    deltaR, pivot, offset);

            final ProjectState result = getState()
                    .changeSelectionContents(rotated)
                    .changeIsCheckpoint(checkpoint);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // reflect selection contents
    public void reflectSelectionContents(final boolean horizontal) {
        if (getState().hasSelection()) {
            final boolean raiseAndDrop = getState().getSelectionMode() !=
                    SelectionMode.CONTENTS;

            if (raiseAndDrop)
                raiseSelectionToContents(false);

            final SelectionContents reflected = getState()
                    .getSelectionContents().returnReflected(
                            getState().getSelection(), horizontal);

            final ProjectState result = getState()
                    .changeSelectionContents(reflected)
                    .changeIsCheckpoint(!raiseAndDrop);
            stateManager.performAction(result, ActionType.CANVAS);

            if (raiseAndDrop)
                dropContentsToLayer(true, false);
            else
                redrawSelectionOverlay();
        }
    }

    // move selection
    public void moveSelectionBounds(final Coord2D displacement, final boolean checkpoint) {
        if (getState().hasSelection() && getState().getSelectionMode() ==
                SelectionMode.BOUNDS) {
            final Set<Coord2D> selection = getState().getSelection();

            final Set<Coord2D> moved = selection.stream().map(s ->
                    s.displace(displacement)).collect(Collectors.toSet());

            final ProjectState result = getState()
                    .changeSelectionBounds(moved)
                    .changeIsCheckpoint(checkpoint);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // stretch selection
    public void stretchSelectionBounds(
            final Set<Coord2D> initialSelection, final Coord2D change,
            final MoverTool.Direction direction, final boolean checkpoint
    ) {
        if (getState().hasSelection() && getState().getSelectionMode() ==
                SelectionMode.BOUNDS) {
            final Set<Coord2D> stretched = SelectionUtils
                    .stretchedPixels(initialSelection, change, direction);

            final ProjectState result = getState()
                    .changeSelectionBounds(stretched)
                    .changeIsCheckpoint(checkpoint);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // rotate selection
    public void rotateSelectionBounds(
            final Set<Coord2D> initialSelection, final double deltaR,
            final Coord2D pivot, final boolean[] offset, final boolean checkpoint
    ) {
        if (getState().hasSelection() && getState().getSelectionMode() ==
                SelectionMode.BOUNDS) {
            final Set<Coord2D> rotated = SelectionUtils
                    .rotatedPixels(initialSelection, deltaR, pivot, offset);

            final ProjectState result = getState()
                    .changeSelectionBounds(rotated)
                    .changeIsCheckpoint(checkpoint);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // reflect selection
    public void reflectSelection(final boolean horizontal) {
        if (getState().hasSelection()) {
            final boolean dropAndRaise = getState().getSelectionMode() !=
                    SelectionMode.BOUNDS;

            if (dropAndRaise)
                dropContentsToLayer(false, false);

            final Set<Coord2D> reflected = SelectionUtils
                    .reflectedPixels(getState().getSelection(), horizontal);

            final ProjectState result = getState()
                    .changeSelectionBounds(reflected)
                    .changeIsCheckpoint(!dropAndRaise);
            stateManager.performAction(result, ActionType.CANVAS);

            if (dropAndRaise)
                raiseSelectionToContents(true);

            redrawSelectionOverlay();
        }
    }

    // crop to selection
    public void cropToSelection() {
        if (getState().hasSelection()) {
            final boolean drop = getState().getSelectionMode() ==
                    SelectionMode.CONTENTS;

            if (drop)
                dropContentsToLayer(false, false);

            final Set<Coord2D> selection = getState().getSelection();

            final Coord2D tl = SelectionUtils.topLeft(selection),
                    br = SelectionUtils.bottomRight(selection);
            final int w = br.x - tl.x, h = br.y - tl.y;

            final List<SELayer> layers = getState().getLayers().stream()
                    .map(layer -> layer.returnPadded(
                            -tl.x, -tl.y, w, h)).toList();

            final ProjectState result = getState().resize(w, h, layers);
            stateManager.performAction(result, ActionType.CANVAS);

            moveSelectionBounds(new Coord2D(-tl.x, -tl.y), false);

            redrawCheckerboard();
        }
    }

    // cut
    public void cut() {
        if (getState().hasSelection()) {
            SEClipboard.get().sendSelectionToClipboard(getState());
            deleteSelectionContents();
            StatusUpdates.sendToClipboard(false,
                    SEClipboard.get().getContents().getPixels());
        } else
            StatusUpdates.clipboardSendFailed(false);
    }

    // paste
    public void paste(final boolean newLayer) {
        if (SEClipboard.get().hasContents()) {
            if (getState().hasSelectionContents())
                dropContentsToLayer(false, true);

            final SelectionContents toPaste = SEClipboard.get().getContents();

            if (newLayer)
                addLayer(true);

            final Coord2D tl = SelectionUtils.topLeft(toPaste.getPixels()),
                    br = SelectionUtils.bottomRight(toPaste.getPixels());
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final int x, y;

            x = tl.x < 0 ? 0 : (br.x >= w
                    ? Math.max(0, w - (br.x - tl.x)) : tl.x);
            y = tl.y < 0 ? 0 : (br.y >= h
                    ? Math.max(0, h - (br.y - tl.y)) : tl.y);

            final Coord2D displacement = new Coord2D(x, y)
                    .displace(-tl.x, -tl.y);

            stateManager.performAction(getState()
                    .changeSelectionContents(toPaste.returnDisplaced(
                            displacement)), ActionType.CANVAS);

            StippleEffect.get().autoAssignPickUpSelection();
        } else
            StatusUpdates.pasteFailed();
    }

    // raise selection to contents
    public void raiseSelectionToContents(final boolean checkpoint) {
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        Set<Coord2D> selection = new HashSet<>(getState().getSelection());

        if (selection.isEmpty()) {
            selection = new HashSet<>();

            for (int x = 0; x < w; x++)
                for (int y = 0; y < h; y++)
                    selection.add(new Coord2D(x, y));
        }

        final GameImage canvas = getState().getEditingLayer()
                .getFrame(getState().getFrameIndex());
        final SelectionContents selectionContents =
                new SelectionContents(canvas, selection);

        final boolean[][] eraseMask = new boolean[w][h];

        selection.stream().filter(s -> s.x >= 0 && s.y >= 0 &&
                s.x < w && s.y < h).forEach(s -> eraseMask[s.x][s.y] = true);

        erase(eraseMask, false);

        final ProjectState result = getState()
                .changeSelectionContents(selectionContents)
                .changeIsCheckpoint(checkpoint);
        stateManager.performAction(result, ActionType.CANVAS);
    }

    // drop contents to layer
    public void dropContentsToLayer(final boolean checkpoint, final boolean deselect) {
        if (getState().hasSelectionContents()) {
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();

            final SelectionContents contents = getState().getSelectionContents();

            stampImage(contents.getContentForCanvas(w, h), contents.getPixels());

            final ProjectState result = getState()
                    .changeSelectionBounds(deselect ? new HashSet<>()
                            : new HashSet<>(contents.getPixels()))
                    .changeIsCheckpoint(checkpoint);
            stateManager.performAction(result, ActionType.CANVAS);
            redrawSelectionOverlay();
        }
    }

    // delete selection contents
    public void deleteSelectionContents() {
        if (getState().hasSelection()) {
            final Set<Coord2D> selection = getState().getSelection();

            if (!selection.isEmpty()) {
                final int w = getState().getImageWidth(),
                        h = getState().getImageHeight();

                final boolean[][] eraseMask = new boolean[w][h];
                selection.forEach(s -> {
                    if (s.x >= 0 && s.x < w && s.y >= 0 && s.y < h)
                        eraseMask[s.x][s.y] = true;
                });

                erase(eraseMask, false);
            }

            stateManager.performAction(getState().changeSelectionBounds(
                    new HashSet<>()), ActionType.CANVAS);
        }
    }

    // fill selection
    public void fillSelection(final boolean secondary) {
        if (getState().hasSelection()) {
            final boolean dropAndRaise = getState().getSelectionMode() ==
                    SelectionMode.CONTENTS;

            if (dropAndRaise)
                dropContentsToLayer(false, false);

            final Set<Coord2D> selection = getState().getSelection();

            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();

            final GameImage edit = new GameImage(w, h);
            final Color c = secondary ? StippleEffect.get().getSecondary()
                    : StippleEffect.get().getPrimary();
            selection.forEach(s -> edit.dot(c, s.x, s.y));

            paintOverImage(edit);

            if (dropAndRaise)
                raiseSelectionToContents(true);
            else
                getState().markAsCheckpoint(true, this);
        }
    }

    // deselect
    public void deselect(final boolean checkpoint) {
        if (getState().hasSelection()) {
            if (getState().getSelectionMode() == SelectionMode.CONTENTS)
                dropContentsToLayer(checkpoint, true);
            else {
                final ProjectState result = getState().changeSelectionBounds(
                        new HashSet<>()).changeIsCheckpoint(checkpoint);
                stateManager.performAction(result, ActionType.CANVAS);
                redrawSelectionOverlay();
            }
        }
    }

    // select all
    public void selectAll() {
        final boolean dropAndRaise = getState().hasSelection() &&
                getState().getSelectionMode() == SelectionMode.CONTENTS;

        if (dropAndRaise)
            dropContentsToLayer(false, true);

        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final Set<Coord2D> selection = new HashSet<>();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                selection.add(new Coord2D(x, y));

        final ProjectState result = getState()
                .changeSelectionBounds(selection)
                .changeIsCheckpoint(!dropAndRaise);
        stateManager.performAction(result, ActionType.CANVAS);

        if (dropAndRaise || StippleEffect.get().getTool().equals(PickUpSelection.get()))
            raiseSelectionToContents(true);

        redrawSelectionOverlay();
    }

    // invert selection
    public void invertSelection() {
        final boolean dropAndRaise = getState().hasSelection() &&
                getState().getSelectionMode() == SelectionMode.CONTENTS;

        if (dropAndRaise)
            dropContentsToLayer(false, false);

        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final Set<Coord2D> willBe = new HashSet<>(),
                was = getState().getSelection();

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (!was.contains(new Coord2D(x, y)))
                    willBe.add(new Coord2D(x, y));

        final ProjectState result = getState()
                .changeSelectionBounds(willBe)
                .changeIsCheckpoint(!dropAndRaise);
        stateManager.performAction(result, ActionType.CANVAS);

        if (dropAndRaise || StippleEffect.get().getTool().equals(PickUpSelection.get()))
            raiseSelectionToContents(true);

        redrawSelectionOverlay();
    }

    // edit selection
    public void editSelection(final Set<Coord2D> edit, final boolean checkpoint) {
        final boolean drop = getState().hasSelection() &&
                getState().getSelectionMode() == SelectionMode.CONTENTS;

        if (drop)
            dropContentsToLayer(false, false);

        final Set<Coord2D> selection = new HashSet<>();
        final ToolWithMode.Mode mode = ToolWithMode.getMode();

        if (mode == ToolWithMode.Mode.ADDITIVE || mode == ToolWithMode.Mode.SUBTRACTIVE)
            selection.addAll(getState().getSelection());

        if (mode == ToolWithMode.Mode.SUBTRACTIVE)
            selection.removeAll(edit);
        else
            selection.addAll(edit);

        final ProjectState result = getState().changeSelectionBounds(
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
                .changeSelectionBounds(new HashSet<>()).changeIsCheckpoint(true);
        stateManager.performAction(result, ActionType.CANVAS);

        redrawCheckerboard();
    }

    public void resize() {
        final int w = DialogVals.getResizeWidth(),
                h = DialogVals.getResizeHeight();

        final List<SELayer> layers = getState().getLayers().stream()
                .map(layer -> layer.returnResized(w, h)).toList();

        final ProjectState result = getState().resize(w, h, layers)
                .changeSelectionBounds(new HashSet<>());
        stateManager.performAction(result, ActionType.CANVAS);

        redrawCheckerboard();
    }

    // IMAGE EDITING
    public void stampImage(final GameImage edit, final Set<Coord2D> pixels) {
        final int frameIndex = getState().getFrameIndex();
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());
        final SELayer replacement = getState().getEditingLayer()
                .returnStamped(edit, pixels, frameIndex);
        final int layerEditIndex = getState().getLayerEditIndex();
        layers.remove(layerEditIndex);
        layers.add(layerEditIndex, replacement);

        final ProjectState result = getState().changeLayers(layers)
                .changeIsCheckpoint(false);
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
    // move frame forward
    public void moveFrameForward() {
        // pre-check
        if (getState().canMoveFrameForward()) {
            final int frameIndex = getState().getFrameIndex(),
                    toIndex = frameIndex + 1;
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            layers.replaceAll(l -> l.returnFrameMovedForward(frameIndex));

            final ProjectState result = getState().changeFrames(layers,
                    toIndex, getState().getFrameCount());
            stateManager.performAction(result, ActionType.FRAME);
            StatusUpdates.movedFrame(frameIndex, toIndex);
        }
    }

    // move frame back
    public void moveFrameBack() {
        // pre-check
        if (getState().canMoveFrameBack()) {
            final int frameIndex = getState().getFrameIndex(),
                    toIndex = frameIndex - 1;
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            layers.replaceAll(l -> l.returnFrameMovedBack(frameIndex));

            final ProjectState result = getState().changeFrames(layers,
                    toIndex, getState().getFrameCount());
            stateManager.performAction(result, ActionType.FRAME);
            StatusUpdates.movedFrame(frameIndex, toIndex);
        }
    }

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

    // link frames in layer
    public void toggleLayerLinking() {
        final int layerIndex = getState().getLayerEditIndex();

        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size()) {
            if (layers.get(layerIndex).areFramesLinked())
                unlinkFramesInLayer(layerIndex);
            else
                linkFramesInLayer(layerIndex);
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
    public void addLayer(final boolean checkpoint) {
        // pre-check
        if (getState().canAddLayer()) {
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int addIndex = getState().getLayerEditIndex() + 1;
            layers.add(addIndex, SELayer.newLayer(w, h, getState().getFrameCount()));

            final ProjectState result = getState().changeLayers(
                    layers, addIndex).changeIsCheckpoint(checkpoint);
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
            StatusUpdates.movedLayer(toMove.getName(), removalIndex,
                    reinsertionIndex);
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
            StatusUpdates.movedLayer(toMove.getName(), removalIndex,
                    reinsertionIndex);
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
        final Coord2D tl = SelectionUtils.topLeft(selection),
                br = SelectionUtils.bottomRight(selection);
        final int w = br.x - tl.x, h = br.y - tl.y;
        final boolean multiple = selection.size() > 1;

        return selection.isEmpty() ? "No selection" : "Selection: " +
                selection.size() + "px " + (multiple ? "from " : "at ") + tl +
                (multiple ? (" to " + br + "; " + w + "x" + h +
                        " bounding box") : "");
    }

    public ProjectState getState() {
        return stateManager.getState();
    }

    public StateManager getStateManager() {
        return stateManager;
    }
}
