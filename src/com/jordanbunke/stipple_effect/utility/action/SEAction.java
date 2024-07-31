package com.jordanbunke.stipple_effect.utility.action;

import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.DropdownItem;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.preview.PreviewWindow;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Outliner;
import com.jordanbunke.stipple_effect.selection.SEClipboard;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.tools.*;
import com.jordanbunke.stipple_effect.utility.DialogVals;
import com.jordanbunke.stipple_effect.utility.EnumUtils;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;
import com.jordanbunke.stipple_effect.visual.DialogAssembly;
import com.jordanbunke.stipple_effect.visual.menu_elements.navigation.logic.GatewayActionItem;
import com.jordanbunke.stipple_effect.visual.menu_elements.navigation.logic.SimpleActionItem;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.jordanbunke.delta_time.events.Key.*;

public enum SEAction {
    // placeholder
    DUMMY(ActionCodes.NONE, c -> {}, null),

    // miscellaneous
    SAVE(ActionCodes.SAVE, c -> c.projectInfo.hasSaveAssociation(),
            c -> c.projectInfo.save(), new KeyShortcut(true, false, S)),
    OPEN_FILE(ActionCodes.OPEN_FILE, fromSE(StippleEffect::openProject),
            new KeyShortcut(true, false, O)),
    PREVIEW(ActionCodes.PREVIEW, null, PreviewWindow::set,
            new KeyShortcut(false, true, SPACE),
            (c, eventLogger) -> eventLogger.unpressAllKeys(), true),
    ALL_UI(ActionCodes.ALL_UI, c -> Layout.showAllPanels(),
            new KeyShortcut(true, true, A)),
    MINIMAL_UI(ActionCodes.MINIMAL_UI, c -> Layout.minimalUI(),
            new KeyShortcut(true, true, A)),
    TOGGLE_PANELS(ActionCodes.NONE, c -> Layout.togglePanels(),
            new KeyShortcut(true, true, A)),
    HIDE_PIXEL_GRID(ActionCodes.PIXEL_GRID_ON,
            SEContext::couldRenderPixelGrid,
            c -> c.renderInfo.setPixelGrid(false),
            new KeyShortcut(true, false, G), null, false),
    SHOW_PIXEL_GRID(ActionCodes.PIXEL_GRID_OFF,
            SEContext::couldRenderPixelGrid,
            c -> c.renderInfo.setPixelGrid(true),
            new KeyShortcut(true, false, G), null, false),
    TOGGLE_PIXEL_GRID(ActionCodes.NONE, c -> {
        if (c.couldRenderPixelGrid()) {
            c.renderInfo.togglePixelGrid();

            if (!Layout.isToolbarShowing())
                StatusUpdates.setPixelGrid(c.renderInfo.isPixelGridOn());
        } else
            StatusUpdates.cannotSetPixelGrid();
    }, new KeyShortcut(true, false, G)),
    SET_PIXEL_GRID_SELECTION(ActionCodes.SET_PIXEL_GRID_SELECTION,
            SEContext::setPixelGridAndCheckerboard,
            new KeyShortcut(true, false, B)),
    SET_PIXEL_GRID_CANVAS(ActionCodes.SET_PIXEL_GRID_CANVAS,
            SEContext::setPixelGridAndCheckerboard,
            new KeyShortcut(true, false, B)),
    SNAP_TO_CENTER(ActionCodes.NONE, SEContext::snapToCenterOfImage,
            KeyShortcut.single(ENTER)),
    SNAP_TO_TP(ActionCodes.NONE, SEContext::snapToTargetPixel,
            new KeyShortcut(false, true, ENTER)),
    TOGGLE_ALIGNMENT(ActionCodes.NONE,
            condFromSE(s -> s.getTool().equals(TextTool.get())),
            plain(TextTool.get()::toggleAlignment),
            new KeyShortcut(true, false, K)),
    SWAP_COLORS(ActionCodes.SWAP_COLORS, fromSE(StippleEffect::swapColors),
            new KeyShortcut(false, true, C)),
    WINDOWED(ActionCodes.WINDOWED,
            fromSE(StippleEffect::toggleFullscreen),
            KeyShortcut.single(ESCAPE)),
    FULLSCREEN(ActionCodes.FULLSCREEN,
            fromSE(StippleEffect::toggleFullscreen),
            KeyShortcut.single(ESCAPE)),

    // non-project dialogs
    SETTINGS(ActionCodes.SETTINGS,
            plain(DialogAssembly::setDialogToProgramSettings),
            new KeyShortcut(true, false, E)),
    INFO(ActionCodes.INFO, plain(DialogAssembly::setDialogToInfo),
            new KeyShortcut(true, false, H)),
    AUTOMATION_SCRIPT(ActionCodes.AUTOMATION_SCRIPT,
            fromSE(StippleEffect::openAutomationScript),
            new KeyShortcut(true, false, W)),
    EXIT_PROGRAM(ActionCodes.EXIT_PROGRAM,
            plain(DialogAssembly::setDialogToExitProgramAYS), null),
    PANEL_MANAGER(ActionCodes.PANEL_MANAGER,
            plain(DialogAssembly::setDialogToPanelManager),
            new KeyShortcut(false, true, ESCAPE)),
    NEW_PROJECT(ActionCodes.NEW_PROJECT,
            plain(DialogAssembly::setDialogToNewProject),
            new KeyShortcut(true, false, N)),
    NEW_FONT(ActionCodes.NEW_FONT,
            condFromSE(s -> s.getTool().equals(TextTool.get())),
            plain(DialogAssembly::setDialogToNewFont),
            new KeyShortcut(false, true, T)),

    // project dialogs
    SAVE_AS(ActionCodes.SAVE_AS, DialogAssembly::setDialogToSave,
            new KeyShortcut(true, true, S)),
    HISTORY(ActionCodes.HISTORY, DialogAssembly::setDialogToHistory,
            new KeyShortcut(false, true, Y)),

    // sizing
    PAD(ActionCodes.PAD, DialogAssembly::setDialogToPad,
            new KeyShortcut(true, true, R)),
    RESIZE(ActionCodes.RESIZE, DialogAssembly::setDialogToResize,
            new KeyShortcut(true, false, R)),
    STITCH_SPLIT(ActionCodes.STITCH_SPLIT_FRAMES, SEContext::stitchOrSplit,
            new KeyShortcut(true, true, G)),
    CROP_TO_SELECTION(ActionCodes.CROP_TO_SELECTION,
            c -> c.getState().hasSelection(), SEContext::cropToSelection,
            new KeyShortcut(true, true, X)),

    // layers
    NEW_LAYER(ActionCodes.NEW_LAYER, c -> c.getState().canAddLayer(),
            SEContext::addLayer, new KeyShortcut(true, false, L),
            null, false),
    DUPLICATE_LAYER(ActionCodes.DUPLICATE_LAYER, c -> c.getState().canAddLayer(),
            SEContext::duplicateLayer, new KeyShortcut(true, true, L),
            null, false),
    REMOVE_LAYER(ActionCodes.REMOVE_LAYER, c -> c.getState().canRemoveLayer(),
            SEContext::removeLayer, new KeyShortcut(true, true, BACKSPACE),
            null, false),
    MOVE_LAYER_UP(ActionCodes.MOVE_LAYER_UP, c -> c.getState().canMoveLayerUp(),
            SEContext::moveLayerUp, new KeyShortcut(true, true, UP_ARROW),
            null, false),
    MOVE_LAYER_DOWN(ActionCodes.MOVE_LAYER_DOWN,
            c -> c.getState().canMoveLayerDown(), SEContext::moveLayerDown,
            new KeyShortcut(true, true, DOWN_ARROW),
            null, false),
    MERGE_WITH_LAYER_BELOW(ActionCodes.MERGE_WITH_LAYER_BELOW,
            c -> c.getState().canMoveLayerDown(),
            SEContext::mergeWithLayerBelow, new KeyShortcut(true, true, M),
            null, false),
    FLATTEN(ActionCodes.FLATTEN, c -> c.getState().canRemoveLayer(),
            SEContext::flatten, new KeyShortcut(true, false, M)),

    // layer visibility
    TOGGLE_LAYER_VISIBILITY(ActionCodes.NONE, c -> {
        final ProjectState state = c.getState();
        final int index = state.getLayerEditIndex();

        if (state.getEditingLayer().isEnabled())
            c.disableLayer(index);
        else
            c.enableLayer(index);
    }, new KeyShortcut(false, true, _1)),
    ISOLATE_LAYER(ActionCodes.NONE,
            c -> c.isolateLayer(c.getState().getLayerEditIndex()),
            new KeyShortcut(false, true, _2)),
    ENABLE_ALL_LAYERS(ActionCodes.NONE, SEContext::enableAllLayers,
            new KeyShortcut(false, true, _3)),

    // current layer
    LAYER_SETTINGS(ActionCodes.LAYER_SETTINGS,
            c -> DialogAssembly.setDialogToLayerSettings(
                    c.getState().getLayerEditIndex()),
            new KeyShortcut(false, true, L)),
    TOGGLE_LAYER_LINKING(ActionCodes.NONE, SEContext::toggleLayerLinking,
            new KeyShortcut(true, false, Q)),
    CYCLE_LAYER_ONION_SKIN_MODE(ActionCodes.NONE,
            c -> {
        final SELayer layer = c.getState().getEditingLayer();
        layer.setOnionSkinMode(EnumUtils.next(layer.getOnionSkinMode()));
    }, new KeyShortcut(true, false, _1)),

    // frames
    NEW_FRAME(ActionCodes.NEW_FRAME,
            c -> c.getState().canAddFrame(), SEContext::addFrame,
            new KeyShortcut(true, false, F), null, false),
    DUPLICATE_FRAME(ActionCodes.DUPLICATE_FRAME,
            c -> c.getState().canAddFrame(), SEContext::duplicateFrame,
            new KeyShortcut(true, true, F), null, false),
    REMOVE_FRAME(ActionCodes.REMOVE_FRAME,
            c -> c.getState().canRemoveFrame(), SEContext::removeFrame,
            new KeyShortcut(true, false, BACKSPACE), null, false),
    MOVE_FRAME_BACK(ActionCodes.MOVE_FRAME_BACK,
            c -> c.getState().canMoveFrameBack(), SEContext::moveFrameBack,
            new KeyShortcut(true, true, LEFT_ARROW), null, false),
    MOVE_FRAME_FORWARD(ActionCodes.MOVE_FRAME_FORWARD,
            c -> c.getState().canMoveFrameForward(),
            SEContext::moveFrameForward,
            new KeyShortcut(true, true, RIGHT_ARROW), null, false),
    FRAME_PROPERTIES(ActionCodes.FRAME_PROPERTIES,
            c -> DialogAssembly.setDialogToFrameProperties(
                    c.getState().getFrameIndex()),
            new KeyShortcut(false, true, F)),

    // playback
    TO_FIRST_FRAME(ActionCodes.TO_FIRST_FRAME, null,
            c -> c.getState().setFrameIndex(0),
            new KeyShortcut(true, false, LEFT_ARROW),
            (c, el) -> c.tryFrameNavigationStatusUpdate(), false),
    PREVIOUS_FRAME(ActionCodes.PREVIOUS, null,
            c -> c.getState().previousFrame(),
            new KeyShortcut(true, true, SPACE),
            (c, el) -> c.tryFrameNavigationStatusUpdate(), false),
    NEXT_FRAME(ActionCodes.NEXT, null,
            c -> c.getState().nextFrame(),
            new KeyShortcut(true, false, SPACE),
            (c, el) -> c.tryFrameNavigationStatusUpdate(), false),
    TO_LAST_FRAME(ActionCodes.TO_LAST_FRAME, null,
            c -> c.getState().setFrameIndex(c.getState().getFrameCount() - 1),
            new KeyShortcut(true, false, RIGHT_ARROW),
            (c, el) -> c.tryFrameNavigationStatusUpdate(), false),
    TOGGLE_PLAYING(ActionCodes.NONE, null,
            c -> c.playbackInfo.togglePlaying(), KeyShortcut.single(SPACE)),
    CYCLE_PLAYBACK_MODE(ActionCodes.NONE, null,
            c -> c.playbackInfo.toggleMode(),
            new KeyShortcut(true, false, ENTER)),

    // layer navigation
    LAYER_ABOVE(ActionCodes.NONE, null,
            c -> c.getState().editLayerAbove(),
            new KeyShortcut(true, false, UP_ARROW),
            (c, el) -> c.tryLayerNavigationStatusUpdate(), false),
    LAYER_BELOW(ActionCodes.NONE, null,
            c -> c.getState().editLayerBelow(),
            new KeyShortcut(true, false, DOWN_ARROW),
            (c, el) -> c.tryLayerNavigationStatusUpdate(), false),

    // clipboard
    CUT(ActionCodes.CUT, c -> c.getState().hasSelection(),
            SEContext::cut, new KeyShortcut(true, false, X)),
    COPY(ActionCodes.COPY, c -> c.getState().hasSelection(),
            SEContext::copy, new KeyShortcut(true, false, C)),
    PASTE(ActionCodes.PASTE, c -> SEClipboard.get().hasContent(),
            c -> c.paste(false), new KeyShortcut(true, false, V)),
    PASTE_NEW_LAYER(ActionCodes.PASTE_NEW_LAYER,
            c -> SEClipboard.get().hasContent(),
            c -> c.paste(true), new KeyShortcut(true, true, V)),

    // palette
    NEW_PALETTE(ActionCodes.NEW_PALETTE, fromSE(StippleEffect::newPalette),
            new KeyShortcut(false, true, N)),
    IMPORT_PALETTE(ActionCodes.IMPORT_PALETTE,
            fromSE(StippleEffect::openPalette), null),
    DELETE_PALETTE(ActionCodes.DELETE_PALETTE,
            condFromSE(StippleEffect::hasPaletteContents),
            fromSE(StippleEffect::deletePalette), null),
    SAVE_PALETTE(ActionCodes.SAVE_PALETTE, mutablePalette(),
            fromSE(s -> DialogAssembly.setDialogToSavePalette(
                    s.getSelectedPalette())),
            new KeyShortcut(true, false, P)),
    SORT_PALETTE(ActionCodes.SORT_PALETTE,
            condFromSE(StippleEffect::hasPaletteContents),
            fromSE(s -> DialogAssembly.setDialogToSortPalette(
                    s.getSelectedPalette())),
            new KeyShortcut(false, true, M)),
    PALETTE_SETTINGS(ActionCodes.PALETTE_SETTINGS, mutablePalette(),
            fromSE(s -> DialogAssembly.setDialogToPaletteSettings(
                    s.getSelectedPalette())),
            new KeyShortcut(false, true, E)),

    // state control
    UNDO(ActionCodes.UNDO, c -> c.stateManager.canUndo(),
            c -> c.stateManager.undoToCheckpoint(),
            new KeyShortcut(true, false, Z), null, false),
    REDO(ActionCodes.REDO, c -> c.stateManager.canRedo(),
            c -> c.stateManager.redoToCheckpoint(),
            new KeyShortcut(true, false, Y), null, false),
    GRANULAR_UNDO(ActionCodes.GRANULAR_UNDO, c -> c.stateManager.canUndo(),
            c -> c.stateManager.undo(true),
            new KeyShortcut(true, true, Z), null, false),
    GRANULAR_REDO(ActionCodes.GRANULAR_REDO, c -> c.stateManager.canRedo(),
            c -> c.stateManager.redo(true),
            new KeyShortcut(true, true, Y), null, false),

    // selection
    DESELECT(ActionCodes.DESELECT, c -> c.getState().hasSelection(),
            c -> c.deselect(true), new KeyShortcut(true, false, D)),
    SELECT_ALL(ActionCodes.SELECT_ALL, SEContext::selectAll,
            new KeyShortcut(true, false, A)),
    INVERT_SELECTION(ActionCodes.INVERT_SELECTION, SEContext::invertSelection,
            new KeyShortcut(true, false, I)),
    FILL_PRIMARY(ActionCodes.FILL_PRIMARY,
            c -> c.getState().hasSelection(),
            c -> c.fillSelection(false), KeyShortcut.single(BACKSPACE)),
    FILL_SECONDARY(ActionCodes.FILL_SECONDARY,
            c -> c.getState().hasSelection(),
            c -> c.fillSelection(true),
            new KeyShortcut(false, true, BACKSPACE)),
    DELETE_SELECTION_CONTENTS(ActionCodes.DELETE_SELECTION_CONTENTS,
            c -> c.getState().hasSelection(),
            c -> c.deleteSelectionContents(true),
            KeyShortcut.single(DELETE)),
    DELETE_SELECTION_CONTENTS_NO_DESELECT(ActionCodes.NONE,
            c -> c.getState().hasSelection(),
            c -> c.deleteSelectionContents(false),
            new KeyShortcut(false, true, DELETE)),
    HORZ_BOUNDS_REFLECTION(ActionCodes.HORZ_BOUNDS_REFLECTION,
            c -> c.getState().hasSelection(), c -> c.reflectSelection(true),
            new KeyShortcut(true, false, _4), null, false),
    VERT_BOUNDS_REFLECTION(ActionCodes.VERT_BOUNDS_REFLECTION,
            c -> c.getState().hasSelection(), c -> c.reflectSelection(false),
            new KeyShortcut(true, false, _5), null, false),
    HORZ_CONTENTS_REFLECTION(ActionCodes.HORZ_CONTENTS_REFLECTION,
            c -> c.getState().hasSelection(),
            c -> c.reflectSelectionContents(true),
            new KeyShortcut(true, true, _4), null, false),
    VERT_CONTENTS_REFLECTION(ActionCodes.VERT_CONTENTS_REFLECTION,
            c -> c.getState().hasSelection(),
            c -> c.reflectSelectionContents(false),
            new KeyShortcut(true, true, _5), null, false),
    CONFIGURE_OUTLINE(ActionCodes.OUTLINE,
            DialogAssembly::setDialogToOutline,
            new KeyShortcut(false, true, O)),
    LAST_OUTLINE(ActionCodes.LAST_OUTLINE,
            c -> c.getState().hasSelection(),
            c -> c.outlineSelection(DialogVals.getOutlineSideMask()),
            new KeyShortcut(true, false, _9), null, false),
    SINGLE_OUTLINE(ActionCodes.SINGLE_OUTLINE,
            c -> c.getState().hasSelection(),
            c -> c.outlineSelection(Outliner.getSingleOutlineMask()),
            new KeyShortcut(false, true, _9), null, false),
    DOUBLE_OUTLINE(ActionCodes.DOUBLE_OUTLINE,
            c -> c.getState().hasSelection(),
            c -> c.outlineSelection(Outliner.getDoubleOutlineMask()),
            new KeyShortcut(true, true, _9), null, false),

    // actions
    HSV_SHIFT(ActionCodes.HSV_SHIFT, DialogAssembly::setDialogToHSVShift,
            new KeyShortcut(true, true, H)),
    COLOR_SCRIPT(ActionCodes.COLOR_SCRIPT,
            DialogAssembly::setDialogToColorScript,
            new KeyShortcut(true, true, W)),
    CONTENTS_TO_PALETTE(ActionCodes.CONTENTS_TO_PALETTE, mutablePalette(),
            c -> DialogAssembly.setDialogToAddContentsToPalette(c,
                    StippleEffect.get().getSelectedPalette()),
            new KeyShortcut(false, true, D)),
    PALETTIZE(ActionCodes.PALETTIZE,
            condFromSE(StippleEffect::hasPaletteContents),
            c -> DialogAssembly.setDialogToPalettize(c,
                    StippleEffect.get().getSelectedPalette()),
            new KeyShortcut(false, true, P)),

    // set tools
    SET_TOOL_HAND(ActionCodes.NONE,
            fromSE(s -> s.setTool(Hand.get())),
            KeyShortcut.single(H)),
    SET_TOOL_ZOOM(ActionCodes.NONE,
            fromSE(s -> s.setTool(Zoom.get())),
            KeyShortcut.single(Z)),
    SET_TOOL_STIPPLE_PENCIL(ActionCodes.NONE,
            fromSE(s -> s.setTool(StipplePencil.get())),
            KeyShortcut.single(O)),
    SET_TOOL_PENCIL(ActionCodes.NONE,
            fromSE(s -> s.setTool(Pencil.get())),
            KeyShortcut.single(P)),
    SET_TOOL_BRUSH(ActionCodes.NONE,
            fromSE(s -> s.setTool(Brush.get())),
            KeyShortcut.single(B)),
    SET_TOOL_SHADE_BRUSH(ActionCodes.NONE,
            fromSE(s -> s.setTool(ShadeBrush.get())),
            KeyShortcut.single(D)),
    SET_TOOL_SCRIPT_BRUSH(ActionCodes.NONE,
            fromSE(s -> s.setTool(ScriptBrush.get())),
            KeyShortcut.single(Q)),
    SET_TOOL_ERASER(ActionCodes.NONE,
            fromSE(s -> s.setTool(Eraser.get())),
            KeyShortcut.single(E)),
    SET_TOOL_GRADIENT(ActionCodes.NONE,
            fromSE(s -> s.setTool(GradientTool.get())),
            KeyShortcut.single(G)),
    SET_TOOL_LINE(ActionCodes.NONE,
            fromSE(s -> s.setTool(LineTool.get())),
            KeyShortcut.single(L)),
    SET_TOOL_SHAPE(ActionCodes.NONE,
            fromSE(s -> s.setTool(ShapeTool.get())),
            KeyShortcut.single(R)),
    SET_TOOL_TEXT(ActionCodes.NONE,
            fromSE(s -> s.setTool(TextTool.get())),
            KeyShortcut.single(T)),
    SET_TOOL_FILL(ActionCodes.NONE,
            fromSE(s -> s.setTool(Fill.get())),
            KeyShortcut.single(F)),
    SET_TOOL_COLOR_PICKER(ActionCodes.NONE,
            fromSE(s -> s.setTool(ColorPicker.get())),
            KeyShortcut.single(C)),
    SET_TOOL_WAND(ActionCodes.NONE,
            fromSE(s -> s.setTool(Wand.get())),
            KeyShortcut.single(W)),
    SET_TOOL_BOX_SELECT(ActionCodes.NONE,
            fromSE(s -> s.setTool(BoxSelect.get())),
            KeyShortcut.single(X)),
    SET_TOOL_POLYGON_SELECT(ActionCodes.NONE,
            fromSE(s -> s.setTool(PolygonSelect.get())),
            KeyShortcut.single(Y)),
    SET_TOOL_BRUSH_SELECT(ActionCodes.NONE,
            fromSE(s -> s.setTool(BrushSelect.get())),
            KeyShortcut.single(V)),
    SET_TOOL_MOVE_SELECTION(ActionCodes.NONE,
            fromSE(s -> s.setTool(MoveSelection.get())),
            KeyShortcut.single(M)),
    SET_TOOL_PICK_UP_SELECTION(ActionCodes.NONE,
            fromSE(s -> s.setTool(PickUpSelection.get())),
            KeyShortcut.single(U)),

    // quick selects
    PROJECT_1(ActionCodes.NUMKEY_PREFIX + 1,
            fromSE(s -> s.setContextIndex(0)),
            KeyShortcut.single(_1)),
    PROJECT_2(ActionCodes.NUMKEY_PREFIX + 2,
            fromSE(s -> s.setContextIndex(1)),
            KeyShortcut.single(_2)),
    PROJECT_3(ActionCodes.NUMKEY_PREFIX + 3,
            fromSE(s -> s.setContextIndex(2)),
            KeyShortcut.single(_3)),
    PROJECT_4(ActionCodes.NUMKEY_PREFIX + 4,
            fromSE(s -> s.setContextIndex(3)),
            KeyShortcut.single(_4)),
    PROJECT_5(ActionCodes.NUMKEY_PREFIX + 5,
            fromSE(s -> s.setContextIndex(4)),
            KeyShortcut.single(_5)),
    PROJECT_6(ActionCodes.NUMKEY_PREFIX + 6,
            fromSE(s -> s.setContextIndex(5)),
            KeyShortcut.single(_6)),
    PROJECT_7(ActionCodes.NUMKEY_PREFIX + 7,
            fromSE(s -> s.setContextIndex(6)),
            KeyShortcut.single(_7)),
    PROJECT_8(ActionCodes.NUMKEY_PREFIX + 8,
            fromSE(s -> s.setContextIndex(7)),
            KeyShortcut.single(_8)),
    PROJECT_9(ActionCodes.NUMKEY_PREFIX + 9,
            fromSE(s -> s.setContextIndex(8)),
            KeyShortcut.single(_9));

    public final String code;
    public final Consumer<SEContext> behaviour;
    public final Predicate<SEContext> precondition;

    // key press
    public final BiConsumer<SEContext, InputEventLogger> postKeyPressBehaviour;
    public final KeyShortcut shortcut;

    // dropdown menu
    private final boolean closeDropdown;

    SEAction(
            final String code, final Consumer<SEContext> behaviour,
            final KeyShortcut shortcut
    ) {
        this(code, null, behaviour, shortcut);
    }

    SEAction(
            final String code,
            final Predicate<SEContext> precondition,
            final Consumer<SEContext> behaviour, final KeyShortcut shortcut
    ) {
        this(code, precondition, behaviour, shortcut, null, true);
    }

    SEAction(
            final String code,
            final Predicate<SEContext> precondition,
            final Consumer<SEContext> behaviour, final KeyShortcut shortcut,
            final BiConsumer<SEContext, InputEventLogger> postKeyPressBehaviour,
            final boolean closeDropdown
    ) {
        this.code = code;
        this.precondition = precondition;
        this.behaviour = behaviour;
        this.shortcut = shortcut;
        this.postKeyPressBehaviour = postKeyPressBehaviour;
        this.closeDropdown = closeDropdown;
    }

    public void tryForMatchingKeyStroke(
            final InputEventLogger eventLogger, final SEContext c
    ) {
        if (shortcut != null)
            shortcut.checkIfPressed(eventLogger, () -> {
                final boolean executed = tryExecute(c);

                if (executed && postKeyPressBehaviour != null)
                    postKeyPressBehaviour.accept(c, eventLogger);
            });
    }

    public void doForMatchingKeyStroke(
            final InputEventLogger eventLogger, final SEContext c
    ) {
        if (shortcut != null)
            shortcut.checkIfPressed(eventLogger, () -> {
                behaviour.accept(c);

                if (postKeyPressBehaviour != null)
                    postKeyPressBehaviour.accept(c, eventLogger);
            });
    }

    public boolean tryExecute(final SEContext c) {
        if (precondition == null || precondition.test(c)) {
            behaviour.accept(c);
            return true;
        }

        return false;
    }

    public DropdownItem toItem(final SEContext c) {
        if (precondition != null)
            return new GatewayActionItem(code, () -> precondition.test(c),
                    () -> behaviour.accept(c), closeDropdown);
        else
            return new SimpleActionItem(code,
                    () -> behaviour.accept(c), closeDropdown);
    }

    private static Consumer<SEContext> fromSE(
            final Consumer<StippleEffect> behaviour
    ) {
        return c -> behaviour.accept(StippleEffect.get());
    }

    private static Consumer<SEContext> plain(final Runnable behaviour) {
        return c -> behaviour.run();
    }

    private static Predicate<SEContext> condFromSE(
            final Predicate<StippleEffect> precondition
    ) {
        return c -> precondition.test(StippleEffect.get());
    }

    private static Predicate<SEContext> mutablePalette() {
        return c -> {
            final StippleEffect s = StippleEffect.get();

            return s.hasPaletteContents() && s.getSelectedPalette().isMutable();
        };
    }

    public static SEAction[] quickSelectActions() {
        return new SEAction[] {
                PROJECT_1, PROJECT_2, PROJECT_3,
                PROJECT_4, PROJECT_5, PROJECT_6,
                PROJECT_7, PROJECT_8, PROJECT_9
        };
    }

    public static SEAction[] setToolActions() {
        return new SEAction[] {
                SET_TOOL_HAND, SET_TOOL_ZOOM,
                SET_TOOL_STIPPLE_PENCIL, SET_TOOL_PENCIL,
                SET_TOOL_BRUSH, SET_TOOL_SHADE_BRUSH, SET_TOOL_SCRIPT_BRUSH, SET_TOOL_ERASER,
                SET_TOOL_GRADIENT, SET_TOOL_LINE, SET_TOOL_SHAPE, SET_TOOL_TEXT,
                SET_TOOL_FILL, SET_TOOL_COLOR_PICKER,
                SET_TOOL_WAND, SET_TOOL_BOX_SELECT, SET_TOOL_POLYGON_SELECT, SET_TOOL_BRUSH_SELECT,
                SET_TOOL_MOVE_SELECTION, SET_TOOL_PICK_UP_SELECTION
        };
    }

    public static SEAction[] stateControlActions() {
        return new SEAction[] {
                UNDO, REDO, GRANULAR_UNDO, GRANULAR_REDO
        };
    }

    public static SEAction[] frameActions() {
        return new SEAction[] {
                NEW_FRAME, DUPLICATE_FRAME, REMOVE_FRAME,
                MOVE_FRAME_BACK, MOVE_FRAME_FORWARD, FRAME_PROPERTIES
        };
    }

    public static SEAction[] playbackActions() {
        return new SEAction[] {
                TO_FIRST_FRAME, PREVIOUS_FRAME, NEXT_FRAME, TO_LAST_FRAME,
                TOGGLE_PLAYING, CYCLE_PLAYBACK_MODE
        };
    }

    public static SEAction[] layerActions() {
        return new SEAction[] {
                NEW_LAYER, DUPLICATE_LAYER, REMOVE_LAYER,
                MOVE_LAYER_UP, MOVE_LAYER_DOWN,
                MERGE_WITH_LAYER_BELOW, FLATTEN
        };
    }

    public static SEAction[] clipboardActions() {
        return new SEAction[] {
                CUT, COPY, PASTE, PASTE_NEW_LAYER
        };
    }

    public static SEAction[] actionsMenuActions() {
        return new SEAction[] {
                HSV_SHIFT, COLOR_SCRIPT, CONTENTS_TO_PALETTE, PALETTIZE
        };
    }

    public static SEAction[] reflectionActions() {
        return new SEAction[] {
                HORZ_BOUNDS_REFLECTION, VERT_BOUNDS_REFLECTION,
                HORZ_CONTENTS_REFLECTION, VERT_CONTENTS_REFLECTION
        };
    }

    public static SEAction[] selectionModificationActions() {
        return new SEAction[] {
                DESELECT, SELECT_ALL, INVERT_SELECTION
        };
    }

    public static SEAction[] selectionOperationActions() {
        return new SEAction[] {
                FILL_PRIMARY, FILL_SECONDARY, DELETE_SELECTION_CONTENTS,
                DELETE_SELECTION_CONTENTS_NO_DESELECT
        };
    }
}
