package com.jordanbunke.stipple_effect.project;

import com.jordanbunke.delta_time.events.*;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.LayerHelper;
import com.jordanbunke.stipple_effect.layer.OnionSkinMode;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.palette.PaletteLoader;
import com.jordanbunke.stipple_effect.preview.PreviewWindow;
import com.jordanbunke.stipple_effect.scripting.SEInterpreter;
import com.jordanbunke.stipple_effect.selection.*;
import com.jordanbunke.stipple_effect.state.Operation;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.state.StateManager;
import com.jordanbunke.stipple_effect.tools.*;
import com.jordanbunke.stipple_effect.utility.*;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.math.StitchSplitMath;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.DialogAssembly;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.awt.*;
import java.nio.file.Path;
import java.util.List;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SEContext {
    private static final int TL = 0, BR = 1, DIM = 2, TRP = 3, BOUNDS = 4;

    public final ProjectInfo projectInfo;
    private final StateManager stateManager;
    public final RenderInfo renderInfo;
    public final PlaybackInfo playbackInfo;

    private boolean inWorkspaceBounds;
    private Coord2D targetPixel;

    private GameImage checkerboard;
    private SelectionOverlay selectionOverlay;
    private Map<ZoomLevel, GameImage> pixelGridMap;

    public SEContext(
            final int imageWidth, final int imageHeight
    ) {
        this(null, ProjectState.makeNew(imageWidth, imageHeight),
                imageWidth, imageHeight);
    }

    public SEContext(
            final Path filepath, final ProjectState projectState,
            final int imageWidth, final int imageHeight
    ) {
        this.projectInfo = new ProjectInfo(this, filepath);
        stateManager = new StateManager(projectState);
        renderInfo = new RenderInfo(imageWidth, imageHeight);
        playbackInfo = new PlaybackInfo();

        targetPixel = Constants.NO_VALID_TARGET;
        inWorkspaceBounds = false;

        selectionOverlay = new SelectionOverlay();

        redrawCanvasAuxiliaries();
    }

    public void redrawSelectionOverlay() {
        selectionOverlay = new SelectionOverlay(getState().getSelection());
    }

    public void updateOverlayOffset() {
        selectionOverlay.updateTL(getState().getSelection());
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
        workspace.fillRectangle(
                Settings.getTheme().workspaceBackground, 0, 0, ww, wh);

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
            final GameImage canvas = getState().draw(true,
                    true, getState().getFrameIndex());

            workspace.draw(canvas.section(bounds[TL], bounds[BR]),
                    bounds[TRP].x, bounds[TRP].y,
                    (int)(bounds[DIM].x * zoomFactor),
                    (int)(bounds[DIM].y * zoomFactor));

            // pixel grid
            if (renderInfo.isPixelGridOn() && couldRenderPixelGrid()) {
                final GameImage pixelGrid = pixelGridMap.getOrDefault(
                        renderInfo.getZoomLevel(), GameImage.dummy());

                if (pixelGrid.getWidth() > GameImage.dummy().getWidth())
                    workspace.draw(pixelGrid.section(
                                    (int)(bounds[TL].x * zoomFactor),
                                    (int)(bounds[TL].y * zoomFactor),
                                    (int)(bounds[BR].x * zoomFactor),
                                    (int)(bounds[BR].y * zoomFactor)),
                            bounds[TRP].x, bounds[TRP].y);
            }
        }

        // OVERLAYS
        if (zoomFactor >= Constants.ZOOM_FOR_OVERLAY) {
            final Tool tool = StippleEffect.get().getTool();

            // brush / eraser overlay
            if (inWorkspaceBounds && tool instanceof ToolWithBreadth twb) {
                final GameImage overlay = twb.getOverlay();
                final int offset = twb.breadthOffset();

                workspace.draw(overlay, render.x +
                        Math.round((targetPixel.x - offset) * zoomFactor) -
                        Constants.OVERLAY_BORDER_PX, render.y +
                        Math.round((targetPixel.y - offset) * zoomFactor) -
                        Constants.OVERLAY_BORDER_PX);
            }

            // selection overlay - drawing box
            if (tool instanceof OverlayTool overlayTool &&
                    overlayTool.isDrawing()) {
                final GameImage overlay = overlayTool.getSelectionOverlay();

                workspace.draw(overlay,
                        render.x - Constants.OVERLAY_BORDER_PX,
                        render.y - Constants.OVERLAY_BORDER_PX);
            }

            // persistent selection overlay
            if (getState().hasSelection() &&
                    !(tool instanceof MoverTool<?> mt && mt.isMoving())) {
                final boolean movable = Tool.canMoveSelectionBounds(tool) ||
                        tool.equals(Wand.get());

                final GameImage selectionAsset =
                        selectionOverlay.draw(zoomFactor, render, ww, wh,
                                movable, tool instanceof MoverTool);

                workspace.draw(selectionAsset, 0, 0);
            }
        }

        return workspace.submit();
    }

    public void animate(final double deltaTime) {
        final ProjectState s = getState();

        if (playbackInfo.isPlaying()) {
            final boolean nextFrameDue =
                    playbackInfo.checkIfNextFrameDue(deltaTime,
                            s.getFrameDurations().get(s.getFrameIndex()));

            if (nextFrameDue)
                playbackInfo.executeAnimation(s);
        }
    }

    public void process(final InputEventLogger eventLogger) {
        setInWorkspaceBounds(eventLogger);
        setTargetPixel(eventLogger);
        processTools(eventLogger);
        processAdditionalMouseEvents(eventLogger);

        if (!Permissions.isTyping()) {
            processSingleKeyInputs(eventLogger);
            processCompoundKeyInputs(eventLogger);
        }
    }

    private void processTools(final InputEventLogger eventLogger) {
        final Tool tool = StippleEffect.get().getTool();

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
                ToolThatDraws.setMode(ToolThatDraws.Mode.NOISE);
            } else if (eventLogger.isPressed(Key.CTRL)) {
                ToolThatDraws.setMode(ToolThatDraws.Mode.DITHERING);
            } else if (eventLogger.isPressed(Key.SHIFT)) {
                ToolThatDraws.setMode(ToolThatDraws.Mode.BLEND);
            } else {
                ToolThatDraws.setMode(ToolThatDraws.Mode.NORMAL);
            }
        }

        if (tool instanceof ToggleModeTool tmt)
            tmt.setMode(eventLogger.isPressed(Key.CTRL));

        if (tool instanceof SnappableTool st)
            st.setSnap(eventLogger.isPressed(Key.SHIFT));

        if (tool instanceof MoverTool<?> mt)
            mt.setSnapToggled(eventLogger.isPressed(Key.CTRL));

        for (GameEvent e : eventLogger.getUnprocessedEvents()) {
            if (e instanceof GameMouseEvent me) {
                if (me.matchesAction(GameMouseEvent.Action.DOWN) &&
                        inWorkspaceBounds) {
                    tool.onMouseDown(this, me);
                    me.markAsProcessed();
                } else if (me.matchesAction(GameMouseEvent.Action.CLICK) &&
                        inWorkspaceBounds) {
                    tool.onClick(this, me);
                    me.markAsProcessed();
                } else if (me.matchesAction(GameMouseEvent.Action.UP)) {
                    tool.onMouseUp(this, me);
                }
            }
        }

        if (tool.equals(TextTool.get()))
            TextTool.get().process(this, eventLogger);

        tool.update(this, eventLogger.getAdjustedMousePosition());
    }

    public void processAdditionalMouseEvents(final InputEventLogger eventLogger) {
        final boolean typingNotBlocked = !Permissions.isTyping();

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
                    if (typingNotBlocked) {
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
                        } else if (eventLogger.isPressed(Key.H)) {
                            mse.markAsProcessed();

                            StippleEffect.get().incrementSelectedColorHue(
                                    mse.clicksScrolled * Constants.COLOR_SET_RGBA_INC);
                        } else if (eventLogger.isPressed(Key.S)) {
                            mse.markAsProcessed();

                            StippleEffect.get().incrementSelectedColorSaturation(
                                    mse.clicksScrolled * Constants.COLOR_SET_RGBA_INC);
                        } else if (eventLogger.isPressed(Key.V)) {
                            mse.markAsProcessed();

                            StippleEffect.get().incrementSelectedColorValue(
                                    mse.clicksScrolled * Constants.COLOR_SET_RGBA_INC);
                        } else if (eventLogger.isPressed(Key.A)) {
                            mse.markAsProcessed();

                            StippleEffect.get().incrementSelectedColorRGBA(
                                    0, 0, 0, mse.clicksScrolled * Constants.COLOR_SET_RGBA_INC);
                        }
                    }

                    // preceding block does not guarantee whether mse has been processed
                    if (mse.isProcessed())
                        continue;

                    if (StippleEffect.get().getTool() instanceof BreadthTool bt) {
                        mse.markAsProcessed();

                        final int cs = Settings.getScrollClicks(
                                mse.clicksScrolled,
                                Settings.Code.INVERT_BREADTH_DIRECTION);

                        bt.setBreadth(bt.getBreadth() + cs);
                    } else if (StippleEffect.get().getTool() instanceof ToolThatSearches) {
                        mse.markAsProcessed();

                        final int cs = Settings.getScrollClicks(mse.clicksScrolled,
                                Settings.Code.INVERT_TOLERANCE_DIRECTION);

                        ToolThatSearches.setTolerance(
                                ToolThatSearches.getTolerance() +
                                        (cs * Constants.SMALL_TOLERANCE_INC));
                    } else if (StippleEffect.get().getTool() instanceof TextTool tt) {
                        mse.markAsProcessed();

                        final int cs = Settings.getScrollClicks(mse.clicksScrolled,
                                Settings.Code.INVERT_FONT_SIZE_DIRECTION);

                        tt.setFontScale(tt.getFontScale() + cs);
                    }
                } else if (inWorkspaceBounds) {
                    mse.markAsProcessed();

                    if (Settings.getScrollClicks(mse.clicksScrolled,
                            Settings.Code.INVERT_ZOOM_DIRECTION) < 0)
                        renderInfo.zoomIn(targetPixel);
                    else
                        renderInfo.zoomOut(targetPixel);
                }
            }
    }

    private void processCompoundKeyInputs(final InputEventLogger eventLogger) {
        // CTRL but not SHIFT
        if (eventLogger.isPressed(Key.CTRL) && !eventLogger.isPressed(Key.SHIFT)) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Z, GameKeyEvent.Action.PRESS),
                    stateManager::undoToCheckpoint);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Y, GameKeyEvent.Action.PRESS),
                    stateManager::redoToCheckpoint);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.S, GameKeyEvent.Action.PRESS),
                    projectInfo::save);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.A, GameKeyEvent.Action.PRESS),
                    this::selectAll);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.D, GameKeyEvent.Action.PRESS),
                    () -> deselect(true));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.I, GameKeyEvent.Action.PRESS),
                    this::invertSelection);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    () -> {
                        getState().nextFrame();

                        if (!Layout.isFramesPanelShowing())
                            StatusUpdates.frameNavigation(
                                    getState().getFrameIndex(),
                                    getState().getFrameCount());
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ENTER, GameKeyEvent.Action.PRESS),
                    playbackInfo::toggleMode);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> {
                        getState().setFrameIndex(0);

                        if (!Layout.isFramesPanelShowing())
                            StatusUpdates.frameNavigation(
                                    getState().getFrameIndex(),
                                    getState().getFrameCount());
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> {
                        getState().setFrameIndex(getState().getFrameCount() - 1);

                        if (!Layout.isFramesPanelShowing())
                            StatusUpdates.frameNavigation(
                                    getState().getFrameIndex(),
                                    getState().getFrameCount());
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                    () -> {
                        getState().editLayerAbove();

                        if (!Layout.isLayersPanelShowing())
                            StatusUpdates.layerNavigation(
                                    getState().getEditingLayer().getName(),
                                    getState().getLayerEditIndex(),
                                    getState().getLayers().size());
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                    () -> {
                        getState().editLayerBelow();

                        if (!Layout.isLayersPanelShowing())
                            StatusUpdates.layerNavigation(
                                    getState().getEditingLayer().getName(),
                                    getState().getLayerEditIndex(),
                                    getState().getLayers().size());
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.G, GameKeyEvent.Action.PRESS),
                    () -> {
                        if (couldRenderPixelGrid()) {
                            renderInfo.togglePixelGrid();

                            if (!Layout.isToolbarShowing())
                                StatusUpdates.setPixelGrid(renderInfo.isPixelGridOn());
                        } else
                            StatusUpdates.cannotSetPixelGrid();
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.B, GameKeyEvent.Action.PRESS),
                    this::setPixelGridAndCheckerboard);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.F, GameKeyEvent.Action.PRESS),
                    this::addFrame);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.L, GameKeyEvent.Action.PRESS),
                    this::addLayer);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.M, GameKeyEvent.Action.PRESS),
                    this::flatten);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Q, GameKeyEvent.Action.PRESS),
                    this::toggleLayerLinking);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._1, GameKeyEvent.Action.PRESS),
                    () -> getState().getEditingLayer().setOnionSkinMode(
                            EnumUtils.next(getState().getEditingLayer().getOnionSkinMode())));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.BACKSPACE, GameKeyEvent.Action.PRESS),
                    this::removeFrame);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.X, GameKeyEvent.Action.PRESS),
                    this::cut);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.C, GameKeyEvent.Action.PRESS),
                    this::copy);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.V, GameKeyEvent.Action.PRESS),
                    () -> paste(false));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._4, GameKeyEvent.Action.PRESS),
                    () -> reflectSelection(true));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._5, GameKeyEvent.Action.PRESS),
                    () -> reflectSelection(false));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._9, GameKeyEvent.Action.PRESS),
                    () -> outlineSelection(DialogVals.getOutlineSideMask()));
        }

        // SHIFT but not CTRL
        if (!eventLogger.isPressed(Key.CTRL) && eventLogger.isPressed(Key.SHIFT)) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.BACKSPACE, GameKeyEvent.Action.PRESS),
                    () -> fillSelection(true));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ENTER, GameKeyEvent.Action.PRESS),
                    this::snapToTargetPixel);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    () -> {
                        PreviewWindow.set(this);
                        eventLogger.unpressAllKeys();
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.DELETE, GameKeyEvent.Action.PRESS),
                    () -> deleteSelectionContents(false));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.L, GameKeyEvent.Action.PRESS),
                    () -> DialogAssembly.setDialogToLayerSettings(getState().getLayerEditIndex()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.F, GameKeyEvent.Action.PRESS),
                    () -> DialogAssembly.setDialogToFrameProperties(getState().getFrameIndex()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._9, GameKeyEvent.Action.PRESS),
                    () -> outlineSelection(Outliner.getSingleOutlineMask()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._1, GameKeyEvent.Action.PRESS),
                    () -> {
                        final int index = getState().getLayerEditIndex();

                        if (getState().getEditingLayer().isEnabled())
                            disableLayer(index);
                        else
                            enableLayer(index);
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._2, GameKeyEvent.Action.PRESS),
                    () -> isolateLayer(getState().getLayerEditIndex()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._3, GameKeyEvent.Action.PRESS),
                    this::enableAllLayers);

            // arrow keys only in these branches
            if (eventLogger.isPressed(Key.R)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                -Constants.COLOR_SET_RGBA_INC, 0, 0, 0));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                Constants.COLOR_SET_RGBA_INC, 0, 0, 0));
            } else if (eventLogger.isPressed(Key.G)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, -Constants.COLOR_SET_RGBA_INC, 0, 0));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, Constants.COLOR_SET_RGBA_INC, 0, 0));
            } else if (eventLogger.isPressed(Key.B)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, -Constants.COLOR_SET_RGBA_INC, 0));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, Constants.COLOR_SET_RGBA_INC, 0));
            } else if (eventLogger.isPressed(Key.H)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorHue(
                                -Constants.COLOR_SET_RGBA_INC));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorHue(
                                Constants.COLOR_SET_RGBA_INC));
            } else if (eventLogger.isPressed(Key.S)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorSaturation(
                                -Constants.COLOR_SET_RGBA_INC));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorSaturation(
                                Constants.COLOR_SET_RGBA_INC));
            } else if (eventLogger.isPressed(Key.V)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorValue(
                                -Constants.COLOR_SET_RGBA_INC));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorValue(
                                Constants.COLOR_SET_RGBA_INC));
            } else if (eventLogger.isPressed(Key.A)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, 0, -Constants.COLOR_SET_RGBA_INC));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> StippleEffect.get().incrementSelectedColorRGBA(
                                0, 0, 0, Constants.COLOR_SET_RGBA_INC));
            } else {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> playbackInfo.incrementFps(-Constants.PLAYBACK_FPS_INC));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> playbackInfo.incrementFps(Constants.PLAYBACK_FPS_INC));
            }
        }

        // CTRL and SHIFT
        if (eventLogger.isPressed(Key.CTRL) && eventLogger.isPressed(Key.SHIFT)) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    () -> {
                        getState().previousFrame();

                        if (!Layout.isFramesPanelShowing())
                            StatusUpdates.frameNavigation(
                                    getState().getFrameIndex(),
                                    getState().getFrameCount());
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                    this::moveLayerUp);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                    this::moveLayerDown);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                    this::moveFrameBack);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                    this::moveFrameForward);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Z, GameKeyEvent.Action.PRESS),
                    () -> stateManager.undo(true));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Y, GameKeyEvent.Action.PRESS),
                    () -> stateManager.redo(true));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.X, GameKeyEvent.Action.PRESS),
                    this::cropToSelection);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.F, GameKeyEvent.Action.PRESS),
                    this::duplicateFrame);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.L, GameKeyEvent.Action.PRESS),
                    this::duplicateLayer);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.M, GameKeyEvent.Action.PRESS),
                    this::mergeWithLayerBelow);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.BACKSPACE, GameKeyEvent.Action.PRESS),
                    this::removeLayer);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.V, GameKeyEvent.Action.PRESS),
                    () -> paste(true));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._4, GameKeyEvent.Action.PRESS),
                    () -> reflectSelectionContents(true));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._5, GameKeyEvent.Action.PRESS),
                    () -> reflectSelectionContents(false));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._9, GameKeyEvent.Action.PRESS),
                    () -> outlineSelection(Outliner.getDoubleOutlineMask()));
        }
    }

    private void processSingleKeyInputs(final InputEventLogger eventLogger) {
        final Tool tool = StippleEffect.get().getTool();

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
                    () -> deleteSelectionContents(true));

            // tool modifications
            if (tool instanceof BreadthTool bt) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        bt::decreaseBreadth);
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        bt::increaseBreadth);
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> bt.setBreadth(bt.getBreadth() + Constants.BREADTH_INC));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> bt.setBreadth(bt.getBreadth() - Constants.BREADTH_INC));
            } else if (tool instanceof ToolThatSearches) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        ToolThatSearches::decreaseTolerance);
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        ToolThatSearches::increaseTolerance);
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> ToolThatSearches.setTolerance(
                                ToolThatSearches.getTolerance() +
                                        Constants.BIG_TOLERANCE_INC));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> ToolThatSearches.setTolerance(
                                ToolThatSearches.getTolerance() -
                                        Constants.BIG_TOLERANCE_INC));
            } else if (tool.equals(TextTool.get())) {
                final TextTool tt = TextTool.get();

                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> tt.setFontScale(tt.getFontScale() - 1));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> tt.setFontScale(tt.getFontScale() + 1));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> tt.setFontIndex(tt.getFontIndex() - 1));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> tt.setFontIndex(tt.getFontIndex() + 1));
            } else if (tool.equals(Hand.get())) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> renderInfo.incrementAnchor(new Coord2D(0, 1)));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> renderInfo.incrementAnchor(new Coord2D(0, -1)));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> renderInfo.incrementAnchor(new Coord2D(1, 0)));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> renderInfo.incrementAnchor(new Coord2D(-1, 0)));
            } else if (Tool.canMoveSelectionBounds(tool)) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionBounds(new Coord2D(0, -1), true));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionBounds(new Coord2D(0, 1), true));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionBounds(new Coord2D(-1, 0), true));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                        () -> moveSelectionBounds(new Coord2D(1, 0), true));
            } else if (tool.equals(Zoom.get())) {
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                        () -> renderInfo.zoomIn(targetPixel));
                eventLogger.checkForMatchingKeyStroke(
                        GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                        () -> renderInfo.zoomOut(targetPixel));
            }
        }

        // special case where shifting would constitute snap and is permitted
        if (!eventLogger.isPressed(Key.CTRL) && tool instanceof MoverTool<?> mt &&
                getState().hasSelection()) {
            final Selection selection = getState().getSelection();
            final int w = selection.bounds.width(),
                    h = selection.bounds.height();

            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.UP_ARROW, GameKeyEvent.Action.PRESS),
                    () -> mt.applyMove(this,
                            new Coord2D(0, -1 * (mt.isSnap() ? h : 1))));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.DOWN_ARROW, GameKeyEvent.Action.PRESS),
                    () -> mt.applyMove(this,
                            new Coord2D(0, mt.isSnap() ? h : 1)));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> mt.applyMove(this,
                            new Coord2D(-1 * (mt.isSnap() ? w : 1), 0)));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> mt.applyMove(this,
                            new Coord2D(mt.isSnap() ? w : 1, 0)));
        }
    }

    private Coord2D getMouseOffsetInWorkspace(final Coord2D mousePosition) {
        final Coord2D wp = Layout.getWorkspacePosition();
        return new Coord2D(mousePosition.x - wp.x, mousePosition.y - wp.y);
    }

    private void setInWorkspaceBounds(final InputEventLogger eventLogger) {
        final Coord2D workspaceM = getMouseOffsetInWorkspace(
                eventLogger.getAdjustedMousePosition());
        inWorkspaceBounds =  workspaceM.x > 0 &&
                workspaceM.x < Layout.getWorkspaceWidth() &&
                workspaceM.y > 0 && workspaceM.y < Layout.getWorkspaceHeight();
    }

    public Coord2D pixelFromMousePos(final Coord2D mousePosition) {
        final Coord2D workspaceM = getMouseOffsetInWorkspace(mousePosition);

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

        return new Coord2D(targetX, targetY);
    }

    public Coord2D modelMousePosForPixel(
            final Coord2D pixel
    ) {
        if (pixel.equals(Constants.NO_VALID_TARGET))
            return pixel;

        final float zoomFactor = renderInfo.getZoomFactor();
        final Coord2D wp = Layout.getWorkspacePosition(),
                render = wp.displace(getImageRenderPositionInWorkspace());

        return render.displace((int)(zoomFactor * pixel.x),
                (int)(zoomFactor * pixel.y));
    }

    private void setTargetPixel(final InputEventLogger eventLogger) {
        targetPixel = inWorkspaceBounds
                ? pixelFromMousePos(eventLogger.getAdjustedMousePosition())
                : Constants.NO_VALID_TARGET;
    }

    private Coord2D getImageRenderPositionInWorkspace() {
        final float zoomFactor = renderInfo.getZoomFactor();
        final Coord2D anchor = renderInfo.getAnchor(),
                middle = new Coord2D(Layout.getWorkspaceWidth() / 2,
                        Layout.getWorkspaceHeight() / 2);

        return new Coord2D(middle.x - (int)(zoomFactor * anchor.x),
                middle.y - (int)(zoomFactor * anchor.y));
    }

    public void redrawCanvasAuxiliaries() {
        redrawCheckerboard();
        redrawPixelGrid();
    }

    public void redrawCheckerboard() {
        final Theme t = Settings.getTheme();
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final GameImage image = new GameImage(w, h);

        final int cbx = Settings.getCheckerboardWPixels(),
                cby = Settings.getCheckerboardHPixels();

        for (int x = 0; x < w; x += cbx) {
            for (int y = 0; y < h; y += cby) {
                final Color c = ((x / cbx) + (y / cby)) % 2 == 0
                        ? t.checkerboard1 : t.checkerboard2;

                image.fillRectangle(c, x, y, cbx, cby);
            }
        }

        checkerboard = image.submit();
    }

    public boolean couldRenderPixelGrid() {
        final float z = renderInfo.getZoomFactor();
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final int px = Settings.getPixelGridXPixels(),
                py = Settings.getPixelGridYPixels(),
                xLines = w / px, yLines = h / py,
                narrowerLineGapPx = Math.min(px, py),
                widerGridDim = (int) Math.max(w * z, h * z);

        final boolean exceedsLineLimit =
                xLines + yLines > Layout.MAX_PIXEL_GRID_LINES,
                linesTooNarrowForZoomLevel =
                        z * narrowerLineGapPx < Constants.ZOOM_FOR_GRID,
                pixelGridImageTooLarge = widerGridDim > Layout.PIXEL_GRID_ZOOM_DIM_MAX;

        return !(exceedsLineLimit || linesTooNarrowForZoomLevel || pixelGridImageTooLarge);
    }

    public void releasePixelGrid() {
        if (pixelGridMap != null)
            pixelGridMap.clear();

        pixelGridMap = new HashMap<>();
    }

    public void redrawPixelGrid() {
        releasePixelGrid();

        if (!(renderInfo.isPixelGridOn() && couldRenderPixelGrid()))
            return;

        final Color a = new Color(0, 0, 0, 150),
                b = new Color(100, 100, 100, 150);

        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        for (final ZoomLevel zl : ZoomLevel.values()) {
            final int z = (int) Math.ceil(zl.z),
                    altPx = Math.max(2, z / Layout.PIXEL_GRID_COLOR_ALT_DIVS);

            final boolean tooSmall = z == 0,
                    tooLarge = Math.max(w, h) * z >
                            Layout.PIXEL_GRID_ZOOM_DIM_MAX;

            if (tooSmall || tooLarge)
                continue;

            final GameImage image =
                    new GameImage((int)(w * zl.z), (int)(h * zl.z));

            final int pgx = Settings.getPixelGridXPixels(),
                    pgy = Settings.getPixelGridYPixels();

            final GameImage vertGridLine = new GameImage(1, (int)(h * zl.z)),
                    horzGridLine = new GameImage((int)(w * zl.z), 1),
                    vertPixel = new GameImage(1, z),
                    horzPixel = new GameImage(z, 1);

            // populate lines
            // pixel stretches
            for (int zInc = 0; zInc < z; zInc += altPx) {
                final Color c = (zInc / altPx) % 2 == 0 ? a : b;

                vertPixel.fillRectangle(c, 0, zInc, 1, altPx);
                horzPixel.fillRectangle(c, zInc, 0, altPx, 1);
            }

            vertPixel.free();
            horzPixel.free();

            // vertical
            for (int y = 0; y < h; y++)
                vertGridLine.draw(vertPixel, 0, (int)(y * zl.z));

            vertGridLine.free();

            // horizontal
            for (int x = 0; x < w; x++)
                horzGridLine.draw(horzPixel, (int)(x * zl.z), 0);

            horzGridLine.free();

            // vertical grid
            for (int x = 0; x < w; x += pgx)
                image.draw(vertGridLine, (int)(x * zl.z), 0);

            // horizontal grid
            for (int y = 0; y < h; y += pgy)
                image.draw(horzGridLine, 0, (int)(y * zl.z));

            pixelGridMap.put(zl, image.submit());
        }
    }

    // non-state changes
    public void snapToCenterOfImage() {
        renderInfo.setAnchor(new Coord2D(
                getState().getImageWidth() / 2,
                getState().getImageHeight() / 2));
    }

    public void snapToTargetPixel() {
        if (isTargetingPixelOnCanvas())
            renderInfo.setAnchor(targetPixel);
    }

    // copy - (not a state change unlike cut and paste)
    public void copy() {
        if (getState().hasSelection()) {
            SEClipboard.get().sendSelectionToClipboard(getState());
            StatusUpdates.sendToClipboard(true,
                    getState().getSelection().size());
        } else
            StatusUpdates.clipboardSendFailed(true);
    }

    private void setPixelGridAndCheckerboard() {
        final boolean fromSelection = getState().hasSelection();
        final int w, h;

        if (fromSelection) {
            final Selection selection = getState().getSelection();

            w = selection.bounds.width();
            h = selection.bounds.height();
        } else {
            w = getState().getImageWidth();
            h = getState().getImageHeight();
        }

        if (w <= Layout.PIXEL_GRID_MAX && h <= Layout.PIXEL_GRID_MAX &&
                w >= Layout.PIXEL_GRID_MIN && h >= Layout.PIXEL_GRID_MIN) {
            Settings.setCheckerboardWPixels(w);
            Settings.setCheckerboardHPixels(h);
            Settings.setPixelGridXPixels(w);
            Settings.setPixelGridYPixels(h);

            Settings.apply();

            StatusUpdates.setCheckAndGridToBounds(w, h, fromSelection);
        } else
            StatusUpdates.cannotSetCheckAndGridToBounds(fromSelection);
    }

    // contents to palette
    public void contentsToPalette(final Palette palette) {
        dropContentsToLayer(false, false);

        final DialogVals.Scope scope = DialogVals.getScope();
        final List<Color> colors = new ArrayList<>();
        final ProjectState state = getState();

        final boolean includeDisabledLayers =
                DialogVals.isIncludeDisabledLayers();
        final Selection selection = state.hasSelection() &&
                !DialogVals.isIgnoreSelection() ? state.getSelection() : null;

        switch (scope) {
            case LAYER_FRAME -> extractColorsFromFrame(colors, state,
                    state.getFrameIndex(), state.getLayerEditIndex(), selection);
            case LAYER -> {
                final int frameCount = state.getFrameCount();

                for (int i = 0; i < frameCount; i++)
                    extractColorsFromFrame(colors, state,
                            i, state.getLayerEditIndex(), selection);
            }
            case FRAME -> {
                final int layerCount = state.getLayers().size();

                for (int i = 0; i < layerCount; i++)
                    if (includeDisabledLayers ||
                            state.getLayers().get(i).isEnabled())
                        extractColorsFromFrame(colors, state,
                            state.getFrameIndex(), i, selection);
            }
            case PROJECT -> {
                final int frameCount = state.getFrameCount(),
                        layerCount = state.getLayers().size();

                for (int f = 0; f < frameCount; f++)
                    for (int l = 0; l < layerCount; l++)
                        if (includeDisabledLayers ||
                                state.getLayers().get(l).isEnabled())
                            extractColorsFromFrame(colors, state, f, l, selection);
            }
        }

        for (Color c : colors)
            palette.addColor(c);
    }

    private void extractColorsFromFrame(
            final List<Color> colors, final ProjectState state,
            final int frameIndex, final int layerIndex,
            final Selection selection
    ) {
        final List<SELayer> layers = new ArrayList<>(state.getLayers());
        final SELayer layer = layers.get(layerIndex);

        PaletteLoader.addPaletteColorsFromImage(
                layer.getFrame(frameIndex), colors, selection);
    }

    // previewed state changes - not state changes in and of themselves

    public ProjectState prepHSVShift() {
        final ProjectState state = getState();

        final boolean dropAndRaise = state.hasSelection() &&
                state.getSelectionMode() == SelectionMode.CONTENTS;

        if (dropAndRaise)
            dropContentsToLayer(false, false);

        return runColorAlgorithm(ColorMath::shiftHSV);
    }

    public ProjectState prepColorScript(final HeadFuncNode script) {
        final ProjectState state = getState();

        final boolean dropAndRaise = state.hasSelection() &&
                state.getSelectionMode() == SelectionMode.CONTENTS;

        if (dropAndRaise)
            dropContentsToLayer(false, false);

        return runColorAlgorithm(c ->
                (Color) SEInterpreter.get().run(script, c));
    }

    // color algorithm auxiliaries
    public ProjectState runColorAlgorithm(
            final Function<Color, Color> internal
    ) {
        dropContentsToLayer(false, false);

        final DialogVals.Scope scope = DialogVals.getScope();
        ProjectState state = getState();

        final boolean includeDisabledLayers =
                DialogVals.isIncludeDisabledLayers();
        final Selection selection = state.hasSelection() &&
                !DialogVals.isIgnoreSelection() ? state.getSelection() : null;

        final Map<Color, Color> map = new HashMap<>();

        try {
            return switch (scope) {
                // case SELECTION -> runCAOnSelection(internal, map);
                case LAYER_FRAME -> runCAOnFrame(internal, map, state,
                        state.getFrameIndex(), state.getLayerEditIndex(), selection);
                case LAYER -> {
                    if (state.getEditingLayer().areFramesLinked()) {
                        yield runCAOnFrame(internal, map, state,
                                state.getFrameIndex(),
                                state.getLayerEditIndex(), selection);
                    } else {
                        final int frameCount = state.getFrameCount();

                        for (int i = 0; i < frameCount; i++)
                            state = runCAOnFrame(internal, map, state,
                                    i, state.getLayerEditIndex(), selection);

                        yield state;
                    }
                }
                case FRAME -> {
                    final int layerCount = state.getLayers().size();

                    for (int i = 0; i < layerCount; i++)
                        if (includeDisabledLayers ||
                                state.getLayers().get(i).isEnabled())
                            state = runCAOnFrame(internal, map, state,
                                    state.getFrameIndex(), i, selection);

                    yield state;
                }
                case PROJECT -> {
                    final int frameCount = state.getFrameCount(),
                            layerCount = state.getLayers().size();

                    for (int l = 0; l < layerCount; l++) {
                        if (!(includeDisabledLayers ||
                                state.getLayers().get(l).isEnabled()))
                            continue;

                        if (state.getLayers().get(l).areFramesLinked()) {
                            state = runCAOnFrame(internal, map, state,
                                    state.getFrameIndex(), l, selection);
                        } else {
                            for (int f = 0; f < frameCount; f++)
                                state = runCAOnFrame(internal, map,
                                        state, f, l, selection);
                        }
                    }

                    yield state;
                }
            };
        } catch (RuntimeException re) {
            return null;
        }
    }

    private ProjectState runCAOnFrame(
            final Function<Color, Color> internal,
            final Map<Color, Color> map,
            final ProjectState state,
            final int frameIndex, final int layerIndex,
            final Selection selection
    ) {
        final List<SELayer> layers = new ArrayList<>(state.getLayers());
        final SELayer layer = layers.get(layerIndex);

        final GameImage source = layer.getFrame(frameIndex),
                edit = ColorMath.algo(internal, map, source, selection);

        final SELayer replacement =
                layer.returnFrameReplaced(edit, frameIndex);
        layers.set(layerIndex, replacement);

        return state.changeLayers(layers).changeIsCheckpoint(false);
    }

    // state changes - process all actions here and feed through state manager

    // palettize
    public void palettize(final Palette palette) {
        final ProjectState state = getState();

        final boolean dropAndRaise = state.hasSelection() &&
                state.getSelectionMode() == SelectionMode.CONTENTS;

        if (dropAndRaise)
            dropContentsToLayer(false, false);

        final ProjectState res = runColorAlgorithm(palette::palettize);

        stateManager.performAction(res, Operation.PALETTIZE);

        if (dropAndRaise)
            raiseSelectionToContents(true);
        else
            getState().markAsCheckpoint(true);
    }

    // SELECTION

    // outline selection
    public void outlineSelection(final int[] sideMask) {
        if (getState().hasSelection()) {
            ToolWithMode.setGlobal(false);
            ToolWithMode.setMode(ToolWithMode.Mode.SINGLE);

            final Selection selection = getState().getSelection();

            editSelection(Outliner.outline(selection, sideMask), true);
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
            stateManager.performAction(result,
                    Operation.TRANSFORM_SELECTION_CONTENTS);

            if (raiseAndDrop)
                dropContentsToLayer(true, false);
        }
    }

    // move selection
    public void moveSelectionBounds(final Coord2D displacement, final boolean checkpoint) {
        if (getState().hasSelection() && getState().getSelectionMode() ==
                SelectionMode.BOUNDS) {
            final Selection moved = getState().getSelection()
                    .displace(displacement);

            final ProjectState result = getState()
                    .changeSelectionBounds(moved)
                    .changeIsCheckpoint(checkpoint);
            stateManager.performAction(result,
                    Operation.MOVE_SELECTION_BOUNDS);
        }
    }

    // reflect selection
    public void reflectSelection(final boolean horizontal) {
        if (getState().hasSelection()) {
            final boolean dropAndRaise = getState().getSelectionMode() !=
                    SelectionMode.BOUNDS;

            if (dropAndRaise)
                dropContentsToLayer(false, false);

            final Selection reflected = SelectionUtils.reflectedPixels(
                    getState().getSelection(), horizontal);

            final ProjectState result = getState()
                    .changeSelectionBounds(reflected)
                    .changeIsCheckpoint(!dropAndRaise);
            stateManager.performAction(result,
                    Operation.TRANSFORM_SELECTION_BOUNDS);

            if (dropAndRaise)
                raiseSelectionToContents(true);
        }
    }

    // crop to selection
    public void cropToSelection() {
        if (getState().hasSelection()) {
            final boolean drop = getState().getSelectionMode() ==
                    SelectionMode.CONTENTS;

            if (drop)
                dropContentsToLayer(false, false);

            final Selection selection = getState().getSelection();

            final Coord2D tl = selection.topLeft;
            final int w = selection.bounds.width(),
                    h = selection.bounds.height();

            final List<SELayer> layers = getState().getLayers().stream()
                    .map(layer -> layer.returnPadded(
                            -tl.x, -tl.y, w, h)).toList();

            final ProjectState result = getState().resize(w, h, layers);
            stateManager.performAction(result,
                    Operation.CROP_TO_SELECTION);

            moveSelectionBounds(new Coord2D(-tl.x, -tl.y), false);

            snapToCenterOfImage();
        }
    }

    // cut
    public void cut() {
        if (getState().hasSelection()) {
            SEClipboard.get().sendSelectionToClipboard(getState());
            final int pixelCount = getState().getSelection().size();
            deleteSelectionContents(true);
            StatusUpdates.sendToClipboard(false, pixelCount);
        } else
            StatusUpdates.clipboardSendFailed(false);
    }

    // paste
    public void paste(final boolean newLayer) {
        if (SEClipboard.get().hasContent()) {
            if (getState().hasSelectionContents())
                dropContentsToLayer(false, true);

            final SelectionContents toPaste = SEClipboard.get().getContent();

            if (newLayer)
                addLayer();

            final Selection selection = toPaste.getSelection();
            final Coord2D tl = selection.topLeft,
                    br = tl.displace(selection.bounds.width(),
                            selection.bounds.height());
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
                            displacement)), Operation.PASTE);

            StippleEffect.get().autoAssignPickUpSelection();
        } else
            StatusUpdates.pasteFailed();
    }

    // raise selection to contents
    public void raiseSelectionToContents(final boolean checkpoint) {
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        Selection selection = getState().getSelection();

        if (!selection.hasSelection())
            selection = Selection.allInBounds(w, h);

        final GameImage canvas = getState().getActiveLayerFrame();
        final SelectionContents selectionContents =
                SelectionContents.make(canvas, selection);

        final boolean[][] eraseMask = new boolean[w][h];
        selection.pixelAlgorithm(w, h, (x, y) -> eraseMask[x][y] = true);
        erase(eraseMask, false);

        final ProjectState result = getState()
                .changeSelectionContents(selectionContents)
                .changeIsCheckpoint(checkpoint);
        stateManager.performAction(result, Operation.RAISE);
    }

    // drop contents to layer
    public void dropContentsToLayer(final boolean checkpoint, final boolean deselect) {
        if (getState().hasSelectionContents()) {
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();

            final SelectionContents contents = getState().getSelectionContents();

            stampImage(contents.getContentForCanvas(w, h),
                    contents.getSelection());

            final ProjectState result = getState()
                    .changeSelectionBounds(deselect
                            ? Selection.EMPTY : contents.getSelection())
                    .changeIsCheckpoint(checkpoint);
            stateManager.performAction(result, Operation.DROP);
        }
    }

    // delete selection contents
    public void deleteSelectionContents(final boolean deselect) {
        if (getState().hasSelection()) {
            final Selection selection = getState().getSelection();

            if (selection.hasSelection()) {
                final int w = getState().getImageWidth(),
                        h = getState().getImageHeight();

                final boolean[][] eraseMask = new boolean[w][h];
                selection.pixelAlgorithm(w, h, (x, y) -> eraseMask[x][y] = true);
                erase(eraseMask, false);
            }

            stateManager.performAction(getState().changeSelectionBounds(
                    deselect ? Selection.EMPTY : selection),
                    Operation.DELETE_SELECTION_CONTENTS);
        }
    }

    // fill selection
    public void fillSelection(final boolean secondary) {
        if (getState().hasSelection()) {
            final boolean dropAndRaise = getState().getSelectionMode() ==
                    SelectionMode.CONTENTS;

            if (dropAndRaise)
                dropContentsToLayer(false, false);

            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();

            final GameImage edit = new GameImage(w, h);
            final int c = (secondary ? StippleEffect.get().getSecondary()
                    : StippleEffect.get().getPrimary()).getRGB();

            final Selection selection = getState().getSelection();
            selection.pixelAlgorithm(w, h, (x, y) -> edit.setRGB(x, y, c));
            stampImage(edit, selection);

            if (dropAndRaise)
                raiseSelectionToContents(true);
            else
                getState().markAsCheckpoint(true);
        }
    }

    // deselect
    public void deselect(final boolean checkpoint) {
        if (getState().hasSelection()) {
            if (getState().getSelectionMode() == SelectionMode.CONTENTS)
                dropContentsToLayer(checkpoint, true);
            else {
                final ProjectState result = getState().changeSelectionBounds(
                        Selection.EMPTY).changeIsCheckpoint(checkpoint);
                stateManager.performAction(result, Operation.DESELECT);
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

        final Selection selection = Selection.allInBounds(w, h);

        final ProjectState result = getState()
                .changeSelectionBounds(selection)
                .changeIsCheckpoint(!dropAndRaise);
        stateManager.performAction(result, Operation.SELECT);

        if (dropAndRaise || StippleEffect.get().getTool().equals(PickUpSelection.get()))
            raiseSelectionToContents(true);
    }

    // invert selection
    public void invertSelection() {
        final boolean dropAndRaise = getState().hasSelection() &&
                getState().getSelectionMode() == SelectionMode.CONTENTS;

        if (dropAndRaise)
            dropContentsToLayer(false, false);

        final int w = getState().getImageWidth(),
                h = getState().getImageHeight();

        final Selection was = getState().getSelection();
        final boolean[][] invertedMatrix = new boolean[w][h];

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                if (!was.selected(x, y))
                    invertedMatrix[x][y] = true;

        final Selection selection = Selection.of(invertedMatrix);

        final ProjectState result = getState()
                .changeSelectionBounds(selection)
                .changeIsCheckpoint(!dropAndRaise);
        stateManager.performAction(result, Operation.SELECT);

        if (dropAndRaise || StippleEffect.get().getTool().equals(PickUpSelection.get()))
            raiseSelectionToContents(true);
    }

    // edit selection
    public void editSelection(final Selection edit, final boolean checkpoint) {
        final boolean drop = getState().hasSelection() &&
                getState().getSelectionMode() == SelectionMode.CONTENTS;

        if (drop)
            dropContentsToLayer(false, false);

        final ToolWithMode.Mode mode = ToolWithMode.getMode();

        final Selection initial, selection;

        initial = mode.inheritSelection()
                ? getState().getSelection() : Selection.EMPTY;
        selection = mode == ToolWithMode.Mode.SUBTRACTIVE
                ? Selection.difference(initial, edit)
                : Selection.union(initial, edit);

        final ProjectState result = getState()
                .changeSelectionBounds(selection)
                .changeIsCheckpoint(checkpoint);
        stateManager.performAction(result, Operation.SELECT);
    }

    public void pad() {
        final int left = DialogVals.getPadLeft(),
                right = DialogVals.getPadRight(),
                top = DialogVals.getPadTop(),
                bottom = DialogVals.getPadBottom();

        pad(left, right, top, bottom);
    }

    public void pad(
            final int left, final int right, final int top, final int bottom
    ) {
        if (left == 0 && right == 0 && top == 0 && bottom == 0)
            return;

        final int w = left + getState().getImageWidth() + right,
                h = top + getState().getImageHeight() + bottom;

        final List<SELayer> layers = getState().getLayers().stream()
                .map(layer -> layer.returnPadded(left, top, w, h)).toList();

        final ProjectState result = getState().resize(w, h, layers)
                .changeSelectionBounds(Selection.EMPTY);
        stateManager.performAction(result, Operation.PAD);

        snapToCenterOfImage();
    }

    public void resize() {
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight(),
                rw = DialogVals.calculcateResizeWidth(w),
                rh = DialogVals.calculateResizeHeight(h);

        if (w == rw && h == rh)
            return;

        resize(rw, rh);
    }

    public void resize(final int rw, final int rh) {
        final List<SELayer> layers = getState().getLayers().stream()
                .map(layer -> layer.returnResized(rw, rh)).toList();

        final ProjectState result = getState().resize(rw, rh, layers)
                .changeSelectionBounds(Selection.EMPTY);
        stateManager.performAction(result, Operation.RESIZE);

        snapToCenterOfImage();
    }

    public void stitch() {
        final int frameWidth = getState().getImageWidth(),
                frameHeight = getState().getImageHeight(),
                frameCount = getState().getFrameCount(),
                w = StitchSplitMath.stitchedWidth(frameWidth, frameCount),
                h = StitchSplitMath.stitchedHeight(frameHeight, frameCount);

        final List<SELayer> layers = getState().getLayers().stream()
                .map(layer -> layer.returnStitched(frameWidth,
                        frameHeight, frameCount)).toList();

        final ProjectState result = getState().stitch(w, h,
                layers).changeSelectionBounds(Selection.EMPTY);
        stateManager.performAction(result, Operation.STITCH);

        snapToCenterOfImage();
    }

    public void split() {
        final int w = getState().getImageWidth(),
                h = getState().getImageHeight(),
                frameCount = StitchSplitMath.splitFrameCount(w, h);

        final List<SELayer> layers = getState().getLayers().stream()
                .map(layer -> layer.returnSplit(w, h, frameCount)).toList();

        final ProjectState result = getState().split(
                DialogVals.getFrameWidth(), DialogVals.getFrameHeight(),
                layers, frameCount).changeSelectionBounds(Selection.EMPTY);
        stateManager.performAction(result, Operation.SPLIT);

        snapToCenterOfImage();
    }

    // IMAGE EDITING
    public void setLayerFromScript(
            final SELayer layer, final int layerIndex
    ) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());
        layers.set(layerIndex, layer);

        final ProjectState result = getState().changeLayers(layers)
                .changeIsCheckpoint(true);
        stateManager.performAction(result, Operation.EDIT_IMAGE);
    }

    public void stampImage(final GameImage edit, final Selection selection) {
        editImage(f -> getState().getEditingLayer()
                .returnStamped(edit, selection, f), false);
    }

    public void paintOverImage(final GameImage edit) {
        editImage(f -> getState().getEditingLayer()
                .returnPaintedOver(edit, f), false);
    }

    // ERASING
    public void erase(final boolean[][] eraseMask, final boolean checkpoint) {
        editImage(f -> getState().getEditingLayer()
                .returnErased(eraseMask, f), checkpoint);
    }

    private void editImage(
            final Function<Integer, SELayer> fTransform,
            final boolean checkpoint
    ) {
        final int frameIndex = getState().getFrameIndex();

        final List<SELayer> layers = new ArrayList<>(getState().getLayers());
        final SELayer replacement = fTransform.apply(frameIndex);
        final int layerEditIndex = getState().getLayerEditIndex();
        layers.set(layerEditIndex, replacement);

        final ProjectState result = getState().changeLayers(layers)
                .changeIsCheckpoint(checkpoint);
        stateManager.performAction(result, Operation.EDIT_IMAGE);
    }

    // FRAME MANIPULATION
    // change frame duration
    public void changeFrameDuration(
            final double duration, final int frameIndex
    ) {
        final List<Double> frameDurations =
                new ArrayList<>(getState().getFrameDurations());
        frameDurations.set(frameIndex, duration);

        final ProjectState result =
                getState().changeFrameDurations(frameDurations);
        stateManager.performAction(result, Operation.CHANGE_FRAME_DURATION);
    }

    // move frame forward
    public void moveFrameForward() {
        final int frameIndex = getState().getFrameIndex(),
                toIndex = frameIndex + 1,
                frameCount = getState().getFrameCount();

        // pre-check
        if (getState().canMoveFrameForward()) {
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            layers.replaceAll(l -> l.returnFrameMovedForward(frameIndex));

            final List<Double> frameDurations =
                    new ArrayList<>(getState().getFrameDurations());
            final double movedDuration = frameDurations.remove(frameIndex);
            frameDurations.add(toIndex, movedDuration);

            final ProjectState result = getState().changeFrames(
                    layers, toIndex, frameCount, frameDurations);
            stateManager.performAction(result, Operation.MOVE_FRAME_FORWARD);
            StatusUpdates.movedFrame(frameIndex, toIndex, frameCount);
        } else if (!Layout.isFramesPanelShowing()) {
            StatusUpdates.cannotMoveFrame(frameIndex, true);
        }
    }

    // move frame back
    public void moveFrameBack() {
        final int frameIndex = getState().getFrameIndex(),
                toIndex = frameIndex - 1,
                frameCount = getState().getFrameCount();

        // pre-check
        if (getState().canMoveFrameBack()) {
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            layers.replaceAll(l -> l.returnFrameMovedBack(frameIndex));

            final List<Double> frameDurations =
                    new ArrayList<>(getState().getFrameDurations());
            final double movedDuration = frameDurations.remove(frameIndex);
            frameDurations.add(toIndex, movedDuration);

            final ProjectState result = getState().changeFrames(
                    layers, toIndex, frameCount, frameDurations);
            stateManager.performAction(result, Operation.MOVE_FRAME_BACK);
            StatusUpdates.movedFrame(frameIndex, toIndex, frameCount);
        } else if (!Layout.isFramesPanelShowing()) {
            StatusUpdates.cannotMoveFrame(frameIndex, false);
        }
    }

    // add frame
    public void addFrame() {
        // pre-check
        if (getState().canAddFrame()) {
            final int w = getState().getImageWidth(),
                    h = getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            final int addIndex = getState().getFrameIndex() + 1,
                    frameCount = getState().getFrameCount() + 1;
            layers.replaceAll(l -> l.returnAddedFrame(addIndex, w, h));

            final List<Double> frameDurations =
                    new ArrayList<>(getState().getFrameDurations());
            frameDurations.add(addIndex, Constants.DEFAULT_FRAME_DURATION);

            final ProjectState result = getState().changeFrames(
                    layers, addIndex, frameCount, frameDurations);
            stateManager.performAction(result, Operation.ADD_FRAME);

            if (!Layout.isFramesPanelShowing())
                StatusUpdates.addedFrame(false, addIndex - 1,
                        addIndex, frameCount);
        } else if (!Layout.isFramesPanelShowing()) {
            StatusUpdates.cannotAddFrame();
        }
    }

    // duplicate frame
    public void duplicateFrame() {
        // pre-check
        if (getState().canAddFrame()) {
            final int frameIndex = getState().getFrameIndex(),
                    frameCount = getState().getFrameCount() + 1;
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            layers.replaceAll(l -> l.returnDuplicatedFrame(frameIndex));

            final List<Double> frameDurations =
                    new ArrayList<>(getState().getFrameDurations());
            frameDurations.add(frameIndex + 1, frameDurations.get(frameIndex));

            final ProjectState result = getState().changeFrames(
                    layers, frameIndex + 1, frameCount, frameDurations);
            stateManager.performAction(result, Operation.DUPLICATE_FRAME);

            if (!Layout.isFramesPanelShowing())
                StatusUpdates.addedFrame(true, frameIndex,
                        frameIndex + 1, frameCount);
        } else if (!Layout.isFramesPanelShowing()) {
            StatusUpdates.cannotAddFrame();
        }
    }

    // remove frame
    public void removeFrame() {
        // pre-check
        if (getState().canRemoveFrame()) {
            final int frameIndex = getState().getFrameIndex(),
                    frameCount = getState().getFrameCount() - 1;
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());

            layers.replaceAll(l -> l.returnRemovedFrame(frameIndex));

            final List<Double> frameDurations =
                    new ArrayList<>(getState().getFrameDurations());
            frameDurations.remove(frameIndex);

            final ProjectState result = getState().changeFrames(
                    layers, frameIndex - 1, frameCount, frameDurations);
            stateManager.performAction(result, Operation.REMOVE_FRAME);

            if (!Layout.isFramesPanelShowing())
                StatusUpdates.removedFrame(frameIndex,
                        Math.max(0, frameIndex - 1), frameCount);
        } else if (!Layout.isFramesPanelShowing()) {
            StatusUpdates.cannotRemoveFrame();
        }
    }

    // LAYER MANIPULATION
    // enable all layers
    public void enableAllLayers() {
        final ProjectState result = getState().changeLayers(
                getState().getLayers().stream().map(SELayer::returnEnabled)
                        .collect(Collectors.toList()));
        stateManager.performAction(result,
                Operation.LAYER_VISIBILITY_CHANGE);
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
            stateManager.performAction(result,
                    Operation.LAYER_VISIBILITY_CHANGE);
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
            layers.set(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers);
            stateManager.performAction(result, Operation.LAYER_LINKING_CHANGE);

            if (!Layout.isLayersPanelShowing())
                StatusUpdates.changedLayerLinkedStatus(false,
                        layer.getName(), layerIndex, layers.size());
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
            layer.setOnionSkinMode(OnionSkinMode.NONE);
            layers.set(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers);
            stateManager.performAction(result, Operation.LAYER_LINKING_CHANGE);

            if (!Layout.isLayersPanelShowing())
                StatusUpdates.changedLayerLinkedStatus(true,
                        layer.getName(), layerIndex, layers.size());
        }
    }

    // disable layer
    public void disableLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() &&
                layers.get(layerIndex).isEnabled()) {
            final SELayer layer = layers.get(layerIndex).returnDisabled();
            layers.set(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers);
            stateManager.performAction(result,
                    Operation.LAYER_VISIBILITY_CHANGE);

            if (!Layout.isLayersPanelShowing())
                StatusUpdates.changedLayerVisibilityStatus(false,
                        layer.getName(), layerIndex, layers.size());
        }
    }

    // enable layer
    public void enableLayer(final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size() &&
                !layers.get(layerIndex).isEnabled()) {
            final SELayer layer = layers.get(layerIndex).returnEnabled();
            layers.set(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers);
            stateManager.performAction(result,
                    Operation.LAYER_VISIBILITY_CHANGE);

            if (!Layout.isLayersPanelShowing())
                StatusUpdates.changedLayerVisibilityStatus(true,
                        layer.getName(), layerIndex, layers.size());
        }
    }

    // change layer name
    public void changeLayerName(final String name, final int layerIndex) {
        final List<SELayer> layers = new ArrayList<>(getState().getLayers());

        // pre-check
        if (layerIndex >= 0 && layerIndex < layers.size()) {
            final SELayer layer = layers.get(layerIndex).returnRenamed(name);
            layers.set(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers);
            stateManager.performAction(result, Operation.CHANGE_LAYER_NAME);
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
            layers.set(layerIndex, layer);

            final ProjectState result = getState().changeLayers(layers)
                    .changeIsCheckpoint(markAsCheckpoint);
            stateManager.performAction(result, Operation.LAYER_OPACITY_CHANGE);
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
            final SELayer added = SELayer.newLayer(w, h, getState().getFrameCount());
            layers.add(addIndex, added);

            final ProjectState result = getState()
                    .changeLayers(layers, addIndex);
            stateManager.performAction(result, Operation.ADD_LAYER);

            if (!Layout.isLayersPanelShowing())
                StatusUpdates.addedLayer(added.getName(), addIndex, layers.size());
        } else if (!Layout.isLayersPanelShowing()) {
            StatusUpdates.cannotAddLayer();
        }
    }

    // add layer
    public void duplicateLayer() {
        // pre-check
        if (getState().canAddLayer()) {
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int addIndex = getState().getLayerEditIndex() + 1;
            final SELayer old = getState().getEditingLayer(),
                    duplicated = old.duplicate();
            layers.add(addIndex, duplicated);

            final ProjectState result = getState()
                    .changeLayers(layers, addIndex);
            stateManager.performAction(result, Operation.DUPLICATE_LAYER);

            if (!Layout.isLayersPanelShowing())
                StatusUpdates.duplicatedLayer(old.getName(),
                        duplicated.getName(), addIndex, layers.size());
        } else if (!Layout.isLayersPanelShowing()) {
            StatusUpdates.cannotAddLayer();
        }
    }

    // remove layer
    public void removeLayer() {
        // pre-check
        if (getState().canRemoveLayer()) {
            final List<SELayer> layers = new ArrayList<>(getState().getLayers());
            final int index = getState().getLayerEditIndex(),
                    setIndex = index > 0 ? index - 1 : index;
            final SELayer toRemove = layers.get(index);
            layers.remove(index);

            final ProjectState result = getState().changeLayers(
                    layers, setIndex);
            stateManager.performAction(result, Operation.REMOVE_LAYER);

            if (!Layout.isLayersPanelShowing())
                StatusUpdates.removedLayer(toRemove.getName(),
                        setIndex, layers.size());
        } else if (!Layout.isLayersPanelShowing()) {
            StatusUpdates.cannotRemoveLayer(
                    getState().getEditingLayer().getName());
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
            stateManager.performAction(result, Operation.MOVE_LAYER_DOWN);
            StatusUpdates.movedLayer(toMove.getName(), removalIndex,
                    reinsertionIndex, layers.size());
        } else if (!Layout.isLayersPanelShowing()) {
            StatusUpdates.cannotMoveLayer(
                    getState().getEditingLayer().getName(), false);
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
            stateManager.performAction(result, Operation.MOVE_LAYER_UP);
            StatusUpdates.movedLayer(toMove.getName(), removalIndex,
                    reinsertionIndex, layers.size());
        } else if (!Layout.isLayersPanelShowing()) {
            StatusUpdates.cannotMoveLayer(
                    getState().getEditingLayer().getName(), true);
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
            final SELayer merged = LayerHelper.merge(above, below,
                    getState().getFrameIndex(), getState().getFrameCount());
            layers.remove(above);
            layers.remove(below);
            layers.add(belowIndex, merged);

            final ProjectState result = getState().changeLayers(
                    layers, belowIndex);
            stateManager.performAction(result,
                    Operation.MERGE_WITH_LAYER_BELOW);

            if (!Layout.isLayersPanelShowing())
                StatusUpdates.mergedWithLayerBelow(above.getName(),
                        below.getName(), belowIndex, layers.size());
        } else if (!Layout.isLayersPanelShowing()) {
            StatusUpdates.cannotMergeWithLayerBelow(
                    getState().getEditingLayer().getName());
        }
    }

    // flatten layers
    public void flatten() {
        // pre-check - identical pass case as can remove layer
        if (getState().canRemoveLayer()) {
            final boolean drop = getState().hasSelection() &&
                    getState().getSelectionMode() == SelectionMode.CONTENTS;

            if (drop)
                dropContentsToLayer(false, true);

            final int frameCount = getState().getFrameCount();
            final List<GameImage> frames = new ArrayList<>();

            for (int i = 0; i < frameCount; i++)
                frames.add(getState().draw(false, false, i));

            final SELayer flattened = new SELayer(frames, frames.get(0),
                    Constants.OPAQUE, true, false, OnionSkinMode.NONE,
                    Constants.FLATTENED_LAYER_NAME);
            final ProjectState result = getState().changeLayers(
                    new ArrayList<>(List.of(flattened)), 0);
            stateManager.performAction(result, Operation.FLATTEN);

            if (!Layout.isLayersPanelShowing())
                StatusUpdates.flattened();
        } else if (!Layout.isLayersPanelShowing())
            StatusUpdates.cannotFlatten();
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
        final Selection selection = getState().getSelection();
        final Bounds2D bounds = selection.bounds;
        final Coord2D tl = selection.topLeft,
                br = selection.topLeft.displace(
                        bounds.width(), bounds.height());
        final boolean multiple = selection.size() > 1;

        return !selection.hasSelection() ? "No selection" : "Selection: " +
                selection.size() + "px " + (multiple ? "from " : "at ") + tl +
                (multiple ? (" to " + br + "; " + bounds.width() + "x" +
                        bounds.height() + " bounding box") : "");
    }

    public ProjectState getState() {
        return stateManager.getState();
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public GameImage getCheckerboard() {
        return checkerboard;
    }
}
