package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.Menu;
import com.jordanbunke.delta_time.menu.MenuBuilder;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.LogicItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.NestedItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.SimpleItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.Scrollable;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.GatewayMenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.OnionSkinMode;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.preview.PreviewWindow;
import com.jordanbunke.stipple_effect.project.PlaybackInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.project.ZoomLevel;
import com.jordanbunke.stipple_effect.selection.SelectionMode;
import com.jordanbunke.stipple_effect.tools.Tool;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.EnumUtils;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.*;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.ColorSelector;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.ColorTextbox;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.PaletteColorButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.navigation.Navbar;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalScrollBox;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollBox;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.Alignment;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.TextButton;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class MenuAssembly {

    public static Menu stub() {
        return new Menu();
    }

    public static Menu buildProjectsMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        mb.add(new Navbar(
                new NestedItem("Program",
                        new SimpleItem(IconCodes.SETTINGS,
                                DialogAssembly::setDialogToProgramSettings),
                        new SimpleItem(IconCodes.INFO,
                                DialogAssembly::setDialogToInfo),
                        new SimpleItem(IconCodes.AUTOMATION_SCRIPT,
                                StippleEffect.get()::openAutomationScript),
                        new SimpleItem(IconCodes.EXIT_PROGRAM,
                                DialogAssembly::setDialogToExitProgramAYS)),
                new NestedItem("File",
                        new SimpleItem(IconCodes.NEW_PROJECT,
                                DialogAssembly::setDialogToNewProject),
                        new SimpleItem(IconCodes.OPEN_FILE,
                                StippleEffect.get()::openProject),
                        new SimpleItem(IconCodes.SAVE, c.projectInfo::save),
                        new SimpleItem(IconCodes.SAVE_AS,
                                DialogAssembly::setDialogToSave)),
                new NestedItem("Edit",
                        new NestedItem("State control",
                                new LogicItem(IconCodes.UNDO,
                                        c.getStateManager()::canUndo,
                                        c.getStateManager()::undoToCheckpoint),
                                new LogicItem(IconCodes.GRANULAR_UNDO,
                                        c.getStateManager()::canUndo,
                                        () -> c.getStateManager().undo(true)),
                                new LogicItem(IconCodes.REDO,
                                        c.getStateManager()::canRedo,
                                        c.getStateManager()::redoToCheckpoint),
                                new LogicItem(IconCodes.GRANULAR_REDO,
                                        c.getStateManager()::canRedo,
                                        () -> c.getStateManager().redo(true)),
                                new SimpleItem(IconCodes.HISTORY,
                                        DialogAssembly::setDialogToHistory)),
                        new NestedItem("Sizing",
                                new SimpleItem(IconCodes.RESIZE,
                                        DialogAssembly::setDialogToResize),
                                new SimpleItem(IconCodes.PAD,
                                        DialogAssembly::setDialogToPad),
                                new SimpleItem(IconCodes.STITCH_SPLIT_FRAMES,
                                        DialogAssembly::setDialogToStitchFramesTogether))),
                new NestedItem("View",
                        new SimpleItem(IconCodes.PREVIEW,
                                () -> PreviewWindow.set(c)),
                        new SimpleItem(IconCodes.PANEL_MANAGER,
                                        DialogAssembly::setDialogToPanelManager))));

        // panel expand / collapse
        final Coord2D panelIconPos = Layout.getProjectsPosition().displace(
                Layout.getProjectsWidth() - Layout.BUTTON_INC,
                Layout.ICON_BUTTON_OFFSET_Y);

        if (!Layout.isProjectsExpanded())
            mb.add(IconButton.make(IconCodes.EXPAND_PANEL, panelIconPos,
                    () -> Layout.adjustPanels(() -> Layout.setProjectsExpanded(true))));
        else
            mb.add(GraphicsUtils.generateIconButton(IconCodes.COLLAPSE_PANEL,
                    panelIconPos, () -> !Layout.isFramesPanelShowing(),
                    () -> Layout.adjustPanels(() ->
                            Layout.setProjectsExpanded(false))));

        // early break if collapsed
        if (!Layout.isProjectsExpanded())
            return mb.build();

        // project previews

        final int amount = StippleEffect.get().getContexts().size(),
                selectedIndex = StippleEffect.get().getContextIndex();

        final MenuBuilder toScroll = new MenuBuilder();

        final Coord2D firstPos = Layout.getProjectsPosition()
                .displace(Layout.getSegmentContentDisplacement());
        int realRightX = firstPos.x, cumulativeWidth = 0, initialOffsetX = 0;

        for (int i = 0; i < amount; i++) {
            int offsetX = 0;

            final Coord2D pos = firstPos.displace(cumulativeWidth, 0);
            final ProjectButton projectButton = ProjectButton.make(pos, i);
            toScroll.add(projectButton);

            offsetX += projectButton.getWidth() + Layout.BUTTON_OFFSET;

            // close project button

            final Coord2D cpPos = pos.displace(offsetX,
                    (Layout.STD_TEXT_BUTTON_H - Layout.BUTTON_DIM) / 2);

            offsetX += Layout.BUTTON_DIM + Layout.SPACE_BETWEEN_PROJECT_BUTTONS_X;

            final int index = i;
            final Runnable closeBehaviour = () -> {
                if (StippleEffect.get().getContexts().get(index).projectInfo.hasUnsavedChanges())
                    DialogAssembly.setDialogToCloseProjectAYS(index);
                else
                    StippleEffect.get().removeContext(index);
            };

            toScroll.add(IconButton.make(IconCodes.CLOSE_PROJECT, cpPos, closeBehaviour));

            cumulativeWidth += offsetX;
            realRightX = cpPos.x + Layout.BUTTON_DIM;

            if (i == selectedIndex - Layout.PROJECTS_BEFORE_TO_DISPLAY)
                initialOffsetX = pos.x - firstPos.x;
        }

        mb.add(new HorizontalScrollBox(firstPos,
                new Bounds2D(Layout.getProjectScrollWindowWidth(),
                        Layout.TOP_PANEL_SCROLL_WINDOW_H),
                Arrays.stream(toScroll.build().getMenuElements())
                        .map(Scrollable::new)
                        .toArray(Scrollable[]::new),
                realRightX, initialOffsetX));

        return mb.build();
    }

    public static Menu buildFlipbookMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();
        final Coord2D panelPos = Layout.getFlipbookPosition();

        mb.add(TextLabel.make(panelPos.displace(
                        Layout.CONTENT_BUFFER_PX, Layout.TEXT_Y_OFFSET),
                "Flipbook"));

        addHidePanelToMenuBuilder(mb,
                panelPos.displace(Layout.getFlipbookWidth(), 0),
                () -> Layout.setFlipbookPanelShowing(false));

        // layer controls
        populateButtonsIntoBuilder(mb,
                new String[] {
                        IconCodes.NEW_LAYER,
                        IconCodes.DUPLICATE_LAYER,
                        IconCodes.REMOVE_LAYER,
                        IconCodes.MOVE_LAYER_UP,
                        IconCodes.MOVE_LAYER_DOWN,
                        IconCodes.MERGE_WITH_LAYER_BELOW,
                        IconCodes.FLATTEN
                },
                getPreconditions(
                        c.getState()::canAddLayer,
                        c.getState()::canAddLayer,
                        c.getState()::canRemoveLayer,
                        c.getState()::canMoveLayerUp,
                        c.getState()::canMoveLayerDown,
                        c.getState()::canMoveLayerDown,
                        c.getState()::canRemoveLayer),
                new Runnable[] {
                        c::addLayer,
                        c::duplicateLayer,
                        c::removeLayer,
                        c::moveLayerUp,
                        c::moveLayerDown,
                        c::mergeWithLayerBelow,
                        c::flatten
                }, panelPos, true);

        // frame controls
        populateButtonsIntoBuilder(mb,
                new String[] {
                        IconCodes.NEW_FRAME,
                        IconCodes.DUPLICATE_FRAME,
                        IconCodes.REMOVE_FRAME,
                        IconCodes.MOVE_FRAME_BACK,
                        IconCodes.MOVE_FRAME_FORWARD,
                        // gap between frame operations and navigation/playback
                        Constants.ICON_ID_GAP_CODE,
                        IconCodes.FRAME_PROPERTIES,
                        // gap between frame operations and navigation/playback
                        Constants.ICON_ID_GAP_CODE,
                        IconCodes.TO_FIRST_FRAME,
                        IconCodes.PREVIOUS,
                        // gap for play/stop button
                        Constants.ICON_ID_GAP_CODE,
                        IconCodes.NEXT,
                        IconCodes.TO_LAST_FRAME
                },
                getPreconditions(
                        c.getState()::canAddFrame,
                        c.getState()::canAddFrame,
                        c.getState()::canRemoveFrame,
                        c.getState()::canMoveFrameBack,
                        c.getState()::canMoveFrameForward,
                        () -> false, // placeholder
                        () -> true,
                        () -> false, // placeholder
                        () -> true,
                        () -> true,
                        () -> false, // placeholder
                        () -> true,
                        () -> true),
                new Runnable[] {
                        c::addFrame,
                        c::duplicateFrame,
                        c::removeFrame,
                        c::moveFrameBack,
                        c::moveFrameForward,
                        () -> {}, // placeholder
                        () -> DialogAssembly.setDialogToFrameProperties(
                                c.getState().getFrameIndex()),
                        () -> {}, // placeholder
                        () -> c.getState().setFrameIndex(0),
                        c.getState()::previousFrame,
                        () -> {}, // placeholder
                        c.getState()::nextFrame,
                        () -> c.getState().setFrameIndex(
                                c.getState().getFrameCount() - 1)
                }, panelPos);

        final int PLAY_STOP_INDEX = 10,
                PLAYBACK_MODE_INDEX = 13,
                AFTER_PLAYBACK_MODE = 15;

        // play/stop as toggle
        final Coord2D disp = new Coord2D(Layout.BUTTON_INC, 0),
                playStopTogglePos = panelPos.displace(
                        Layout.PANEL_TITLE_BUTTON_OFFSET_X +
                                (PLAY_STOP_INDEX * Layout.BUTTON_INC),
                        Layout.ICON_BUTTON_OFFSET_Y);
        mb.add(generatePlayStopToggle(playStopTogglePos));

        // playback mode toggle button
        final Coord2D playbackModeTogglePos = playStopTogglePos.displace(
                disp.scale(PLAYBACK_MODE_INDEX - PLAY_STOP_INDEX));
        mb.add(generatePlaybackModeToggle(playbackModeTogglePos));

        // playback
        final Coord2D labelPos = playbackModeTogglePos.displace(
                disp.scale(AFTER_PLAYBACK_MODE - PLAYBACK_MODE_INDEX));

        final TextLabel playbackLabel = TextLabel.make(labelPos, "");
        final IncrementalRangeElements<Integer> playback =
                IncrementalRangeElements.makeForInt(playbackLabel,
                        panelPos.y + Layout.ICON_BUTTON_OFFSET_Y,
                        panelPos.y + Layout.TEXT_Y_OFFSET, 1,
                        Constants.MIN_PLAYBACK_FPS, Constants.MAX_PLAYBACK_FPS,
                        c.playbackInfo::setFps, c.playbackInfo::getFps,
                        fps -> fps, fps -> fps, fps -> fps + " FPS",
                        "XXX FPS");
        mb.addAll(playbackLabel, playback.decButton, playback.incButton,
                playback.slider, playback.value);

        return mb.build();
    }

    public static Menu buildFramesMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        mb.add(TextLabel.make(Layout.getFramesPosition().displace(
                        Layout.CONTENT_BUFFER_PX, Layout.TEXT_Y_OFFSET),
                "Frames"));

        populateButtonsIntoBuilder(mb,
                new String[] {
                        IconCodes.NEW_FRAME,
                        IconCodes.DUPLICATE_FRAME,
                        IconCodes.REMOVE_FRAME,
                        IconCodes.MOVE_FRAME_BACK,
                        IconCodes.MOVE_FRAME_FORWARD,
                        // gap between frame operations and navigation/playback
                        Constants.ICON_ID_GAP_CODE,
                        IconCodes.FRAME_PROPERTIES,
                        // gap between frame operations and navigation/playback
                        Constants.ICON_ID_GAP_CODE,
                        IconCodes.TO_FIRST_FRAME,
                        IconCodes.PREVIOUS,
                        // gap for play/stop button
                        Constants.ICON_ID_GAP_CODE,
                        IconCodes.NEXT,
                        IconCodes.TO_LAST_FRAME
                },
                getPreconditions(
                        () -> c.getState().canAddFrame(),
                        () -> c.getState().canAddFrame(),
                        () -> c.getState().canRemoveFrame(),
                        () -> c.getState().canMoveFrameBack(),
                        () -> c.getState().canMoveFrameForward(),
                        () -> false, // placeholder
                        () -> true,
                        () -> false, // placeholder
                        () -> true,
                        () -> true,
                        () -> false, // placeholder
                        () -> true,
                        () -> true),
                new Runnable[] {
                        c::addFrame,
                        c::duplicateFrame,
                        c::removeFrame,
                        c::moveFrameBack,
                        c::moveFrameForward,
                        () -> {}, // placeholder
                        () -> DialogAssembly.setDialogToFrameProperties(
                                c.getState().getFrameIndex()),
                        () -> {}, // placeholder
                        () -> c.getState().setFrameIndex(0),
                        () -> c.getState().previousFrame(),
                        () -> {}, // placeholder
                        () -> c.getState().nextFrame(),
                        () -> c.getState().setFrameIndex(
                                c.getState().getFrameCount() - 1)
                }, Layout.getFramesPosition());

        addHidePanelToMenuBuilder(mb, Layout.getFramesPosition()
                        .displace(Layout.getFramesWidth(), 0),
                () -> Layout.setFramesPanelShowing(false));

        final int PLAY_STOP_INDEX = 10,
                PLAYBACK_MODE_INDEX = 13,
                AFTER_PLAYBACK_MODE = 15;

        // play/stop as toggle
        final Coord2D playStopTogglePos = Layout.getFramesPosition().displace(
                Layout.PANEL_TITLE_BUTTON_OFFSET_X +
                        (PLAY_STOP_INDEX * Layout.BUTTON_INC),
                Layout.ICON_BUTTON_OFFSET_Y);
        mb.add(generatePlayStopToggle(playStopTogglePos));

        // playback mode toggle button
        final Coord2D playbackModeTogglePos = Layout.getFramesPosition().displace(
                Layout.PANEL_TITLE_BUTTON_OFFSET_X +
                        (PLAYBACK_MODE_INDEX * Layout.BUTTON_INC),
                Layout.ICON_BUTTON_OFFSET_Y);
        mb.add(generatePlaybackModeToggle(playbackModeTogglePos));

        // playback
        final Coord2D labelPos = Layout.getFramesPosition().displace(
                Layout.PANEL_TITLE_BUTTON_OFFSET_X +
                        (AFTER_PLAYBACK_MODE * Layout.BUTTON_INC),
                Layout.TEXT_Y_OFFSET);

        final TextLabel playbackLabel = TextLabel.make(labelPos, "");
        final IncrementalRangeElements<Integer> playback =
                IncrementalRangeElements.makeForInt(playbackLabel,
                        Layout.ICON_BUTTON_OFFSET_Y, Layout.TEXT_Y_OFFSET, 1,
                        Constants.MIN_PLAYBACK_FPS, Constants.MAX_PLAYBACK_FPS,
                        c.playbackInfo::setFps, c.playbackInfo::getFps,
                        fps -> fps, fps -> fps, fps -> fps + " FPS",
                        "XXX FPS");
        mb.addAll(playbackLabel, playback.decButton, playback.incButton,
                playback.slider, playback.value);

        // frame content

        final int amount = c.getState().getFrameCount();

        final MenuBuilder frameElements = new MenuBuilder();

        final Coord2D firstPos = Layout.getFramesPosition()
                .displace(Layout.getSegmentContentDisplacement());

        int realRightX = firstPos.x;

        for (int i = 0; i < amount; i++) {
            final Coord2D pos = firstPos.displace(
                    i * (Layout.FRAME_BUTTON_W + Layout.BUTTON_OFFSET), 0);
            frameElements.add(SelectableListItemButton.make(
                    pos, Layout.FRAME_BUTTON_W, String.valueOf(i + 1),
                    i, () -> c.getState().getFrameIndex(),
                    s -> c.getState().setFrameIndex(s)));

            realRightX = pos.x + Layout.FRAME_BUTTON_W;
        }

        mb.add(new HorizontalScrollBox(firstPos,
                new Bounds2D(Layout.getFrameScrollWindowWidth(),
                        Layout.TOP_PANEL_SCROLL_WINDOW_H),
                Arrays.stream(frameElements.build().getMenuElements())
                        .map(Scrollable::new)
                        .toArray(Scrollable[]::new),
                realRightX, frameButtonXDisplacement()));

        return mb.build();
    }

    private static SimpleToggleMenuButton generatePlaybackModeToggle(final Coord2D pos) {
        final PlaybackInfo.Mode[] validModes = new PlaybackInfo.Mode[] {
                PlaybackInfo.Mode.FORWARDS,
                PlaybackInfo.Mode.BACKWARDS,
                PlaybackInfo.Mode.LOOP,
                PlaybackInfo.Mode.PONG_FORWARDS
        };

        final String[] codes = Arrays.stream(validModes).map(
                PlaybackInfo.Mode::getIconCode).toArray(String[]::new);

        return IconToggleButton.make(pos, codes,
                Arrays.stream(validModes).map(mode ->
                        (Runnable) () -> {}).toArray(Runnable[]::new),
                () -> StippleEffect.get().getContext().playbackInfo
                        .getMode().buttonIndex(),
                () -> StippleEffect.get().getContext().playbackInfo.toggleMode());
    }

    private static SimpleToggleMenuButton generatePlayStopToggle(final Coord2D pos) {
        // 0: is playing, button click should STOP; 1: vice-versa
        final String[] codes = new String[] { IconCodes.STOP, IconCodes.PLAY };

        return IconToggleButton.make(pos, codes,
                new Runnable[] {
                        () -> StippleEffect.get().getContext()
                                .playbackInfo.stop(),
                        () -> StippleEffect.get().getContext()
                                .playbackInfo.play()
                },
                () -> StippleEffect.get().getContext()
                        .playbackInfo.isPlaying() ? 0 : 1, () -> {});
    }

    private static int frameButtonXDisplacement() {
        return (StippleEffect.get().getContext().getState().getFrameIndex() -
                Layout.FRAMES_BEFORE_TO_DISPLAY) *
                (Layout.FRAME_BUTTON_W + Layout.BUTTON_OFFSET);
    }

    public static Menu buildLayersMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        mb.add(TextLabel.make(Layout.getLayersPosition().displace(
                        Layout.CONTENT_BUFFER_PX, Layout.TEXT_Y_OFFSET),
                "Layers"));

        populateButtonsIntoBuilder(mb,
                new String[] {
                        IconCodes.NEW_LAYER,
                        IconCodes.DUPLICATE_LAYER,
                        IconCodes.REMOVE_LAYER,
                        IconCodes.MOVE_LAYER_UP,
                        IconCodes.MOVE_LAYER_DOWN,
                        IconCodes.MERGE_WITH_LAYER_BELOW,
                        IconCodes.FLATTEN
                },
                getPreconditions(
                        () -> c.getState().canAddLayer(),
                        () -> c.getState().canAddLayer(),
                        () -> c.getState().canRemoveLayer(),
                        () -> c.getState().canMoveLayerUp(),
                        () -> c.getState().canMoveLayerDown(),
                        () -> c.getState().canMoveLayerDown(),
                        () -> c.getState().canRemoveLayer()),
                new Runnable[] {
                        c::addLayer,
                        c::duplicateLayer,
                        c::removeLayer,
                        c::moveLayerUp,
                        c::moveLayerDown,
                        c::mergeWithLayerBelow,
                        c::flatten
                }, Layout.getLayersPosition());

        addHidePanelToMenuBuilder(mb, Layout.getLayersPosition()
                        .displace(Layout.getLayersWidth(), 0),
                () -> Layout.setLayersPanelShowing(false));

        // layer content

        final List<SELayer> layers = c.getState().getLayers();
        final int amount = layers.size();

        final MenuBuilder layerButtons = new MenuBuilder();

        final Coord2D firstPos = Layout.getLayersPosition()
                .displace(Layout.getSegmentContentDisplacement());
        int realBottomY = firstPos.y;

        for (int i = amount - 1; i >= 0; i--) {
            final SELayer layer = layers.get(i);

            final String name = layer.getName(),
                    text = name.length() > Layout.LAYER_NAME_LENGTH_CUTOFF
                            ? name.substring(0, Layout.LAYER_NAME_LENGTH_CUTOFF) + "..."
                            : name;

            final Coord2D pos = firstPos.displace(0,
                    (amount - (i + 1)) * Layout.STD_TEXT_BUTTON_INC);

            layerButtons.add(SelectableListItemButton.make(
                    pos, Layout.LAYER_BUTTON_W, text, i,
                    () -> c.getState().getLayerEditIndex(),
                    s -> c.getState().setLayerEditIndex(s)));

            final int index = i;

            // visibility toggle
            final Coord2D vtPos = pos.displace(
                    Layout.LAYER_BUTTON_W + Layout.BUTTON_OFFSET,
                    (Layout.STD_TEXT_BUTTON_H / 2)  - (Layout.BUTTON_DIM / 2));
            layerButtons.add(new LayerVisibilityButton(vtPos, index));

            // frames linked toggle
            final Coord2D flPos = vtPos.displace(Layout.BUTTON_INC, 0);
            layerButtons.add(generateFramesLinkedToggle(index, flPos));

            // onion skin toggle
            final Coord2D onionPos = vtPos.displace(Layout.BUTTON_INC * 2, 0);
            layerButtons.add(generateOnionSkinToggle(index, onionPos));

            // layer settings
            final Coord2D lsPos = vtPos.displace(Layout.BUTTON_INC * 3, 0);
            layerButtons.add(IconButton.make(IconCodes.LAYER_SETTINGS, lsPos,
                    () -> DialogAssembly.setDialogToLayerSettings(index)));

            realBottomY = pos.y + Layout.STD_TEXT_BUTTON_H;
        }

        final int initialOffsetY = layerButtonYDisplacement(amount);

        mb.add(new VerticalScrollBox(firstPos,
                new Bounds2D(Layout.VERT_SCROLL_WINDOW_W,
                        Layout.getVertScrollWindowHeight()),
                Arrays.stream(layerButtons.build().getMenuElements())
                        .map(Scrollable::new).toArray(Scrollable[]::new),
                realBottomY, initialOffsetY));

        return mb.build();
    }

    private static MenuElement generateOnionSkinToggle(
            final int index, final Coord2D pos
    ) {
        final SEContext c = StippleEffect.get().getContext();
        final String[] codes = EnumUtils.stream(OnionSkinMode.class)
                .map(OnionSkinMode::getIconCode).toArray(String[]::new);

        final Runnable[] behaviours = EnumUtils.stream(OnionSkinMode.class).map(
                osm -> (Runnable) () -> {
                    final int nextIndex = (osm.ordinal() + 1) %
                            OnionSkinMode.values().length;
                    c.getState().getLayers().get(index).setOnionSkinMode(
                                    OnionSkinMode.values()[nextIndex]);
                }).toArray(Runnable[]::new);

        return GraphicsUtils.generateIconToggleButton(pos, codes, behaviours,
                () -> c.getState().getLayers().get(index).getOnionSkinMode().ordinal(),
                () -> {},
                () -> !c.getState().getLayers().get(index).areFramesLinked(),
                OnionSkinMode.NONE.getIconCode());
    }

    private static MenuElement generateFramesLinkedToggle(
            final int index, final Coord2D pos
    ) {
        // 0: is unlinked, button click should LINK; 1: vice-versa
        final String[] codes = new String[] {
                IconCodes.FRAMES_UNLINKED,
                IconCodes.FRAMES_LINKED
        };

        return IconToggleButton.make(pos, codes,
                new Runnable[] {
                        () -> StippleEffect.get().getContext().linkFramesInLayer(index),
                        () -> StippleEffect.get().getContext().unlinkFramesInLayer(index)
                },
                () -> StippleEffect.get().getContext().getState()
                        .getLayers().get(index).areFramesLinked() ? 1 : 0,
                () -> {});
    }

    private static int layerButtonYDisplacement(final int amount) {
        return (amount - ((StippleEffect.get().getContext().getState().getLayerEditIndex() +
                Layout.LAYERS_ABOVE_TO_DISPLAY) + 1)) * Layout.STD_TEXT_BUTTON_INC;
    }

    private static void populateButtonsIntoBuilder(
            final MenuBuilder mb, final String[] iconIDs,
            final Supplier<Boolean>[] preconditions, final Runnable[] behaviours,
            final Coord2D panelPos
    ) {
        populateButtonsIntoBuilder(mb, iconIDs,
                preconditions, behaviours, panelPos, false);
    }

    private static void populateButtonsIntoBuilder(
            final MenuBuilder mb, final String[] iconIDs,
            final Supplier<Boolean>[] preconditions, final Runnable[] behaviours,
            final Coord2D panelPos, final boolean vertical
    ) {
        if (iconIDs.length != preconditions.length || iconIDs.length != behaviours.length) {
            GameError.send("Lengths of button assembly argument arrays did not match; " +
                    "buttons were not populated into menu builder.");
            return;
        }

        final Coord2D displacement = vertical
                ? new Coord2D(0, Layout.BUTTON_INC)
                : new Coord2D(Layout.BUTTON_INC, 0);
        Coord2D pos = panelPos.displace(
                vertical ? Layout.CONTENT_BUFFER_PX
                        : Layout.PANEL_TITLE_BUTTON_OFFSET_X,
                Layout.ICON_BUTTON_OFFSET_Y +
                        (vertical ? displacement.y * 2 : 0));

        for (int i = 0; i < iconIDs.length; i++) {
            if (!iconIDs[i].equals(Constants.ICON_ID_GAP_CODE))
                mb.add(GraphicsUtils.generateIconButton(iconIDs[i],
                        pos, preconditions[i], behaviours[i]));

            pos = pos.displace(displacement);
        }
    }

    @SafeVarargs
    private static Supplier<Boolean>[] getPreconditions(final Supplier<Boolean>... preconditions) {
        return preconditions;
    }

    public static Menu buildColorsMenu() {
        final MenuBuilder mb = new MenuBuilder();

        mb.add(TextLabel.make(Layout.getColorsPosition().displace(
                Layout.CONTENT_BUFFER_PX, Layout.TEXT_Y_OFFSET),
                "Colors"));

        populateButtonsIntoBuilder(
                mb, new String[] {
                        IconCodes.SWAP_COLORS,
                        IconCodes.COLOR_MENU_MODE,
                        IconCodes.HSV_SHIFT,
                        IconCodes.COLOR_SCRIPT
                },
                getPreconditions(
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true
                ),
                new Runnable[] {
                        () -> StippleEffect.get().swapColors(),
                        () -> StippleEffect.get().toggleColorMenuMode(),
                        DialogAssembly::setDialogToHSVShift,
                        DialogAssembly::setDialogToColorScript
                },
                Layout.getColorsPosition()
        );

        addHidePanelToMenuBuilder(mb, Layout.getColorsPosition()
                .displace(Layout.getColorsWidth(), 0),
                () -> Layout.setColorsPanelShowing(false));

        final int NUM_COLORS = 2;

        for (int i = 0; i < NUM_COLORS; i++) {
            final int offsetY = Layout.getSegmentContentDisplacement().y * 2;
            final Coord2D labelPos = Layout.getColorsPosition().displace(
                    Layout.getSegmentContentDisplacement()).displace(
                            i * (Layout.getColorsWidth() / 2), 0),
                    textBoxPos = Layout.getColorsPosition().displace(
                            (Layout.getColorsWidth() / 4) + (i *
                                    (Layout.getColorsWidth() / 2)), offsetY);

            mb.add(TextLabel.make(labelPos, switch (i) {
                case 0 -> "Primary";
                case 1 -> "Secondary";
                default -> "Other";
            }));

            final ColorTextbox colorTextBox = ColorTextbox.make(textBoxPos, i);
            mb.add(colorTextBox);

            final int index = i;
            final Bounds2D dims = new Bounds2D(colorTextBox.getWidth(),
                    colorTextBox.getHeight());
            final GatewayMenuElement highlight = new GatewayMenuElement(
                    new StaticMenuElement(textBoxPos, dims, MenuElement.Anchor.CENTRAL_TOP,
                            GraphicsUtils.drawSelectedTextbox(
                                    new GameImage(dims.width(), dims.height()))),
                    () -> StippleEffect.get().getColorIndex() == index);
            mb.add(highlight);
        }

        switch (StippleEffect.get().getColorMenuMode()) {
            case RGBA_HSV -> mb.add(new ColorSelector());
            case PALETTE -> addPaletteMenuElements(mb);
        }

        return mb.build();
    }

    private static void addHidePanelToMenuBuilder(
            final MenuBuilder mb, final Coord2D topRight,
            final Runnable onClick
    ) {
        final Coord2D pos = topRight.displace(-Layout.BUTTON_INC,
                Layout.ICON_BUTTON_OFFSET_Y);
        mb.add(IconButton.make(IconCodes.HIDE_PANEL, pos,
                () -> Layout.adjustPanels(onClick)));
    }

    private static void addPaletteMenuElements(final MenuBuilder mb) {
        final Coord2D startingPos = Layout.getColorsPosition().displace(
                Layout.CONTENT_BUFFER_PX, Layout.COLOR_SELECTOR_OFFSET_Y +
                        Layout.COLOR_LABEL_OFFSET_Y),
                paletteOptionsRef = startingPos.displace(
                        -Layout.CONTENT_BUFFER_PX, -Layout.TEXT_Y_OFFSET),
                selColOptionsRef = paletteOptionsRef.displace(
                        0, Layout.BUTTON_INC);
        final int contentWidth = Layout.getColorsWidth() -
                        (2 * Layout.CONTENT_BUFFER_PX);

        final StippleEffect s = StippleEffect.get();
        final List<Palette> palettes = s.getPalettes();
        final int index = s.getPaletteIndex();
        final boolean hasPaletteContents = s.hasPaletteContents();

        // palette label
        mb.add(TextLabel.make(startingPos, "Palette"));

        // palette options
        populateButtonsIntoBuilder(
                mb, new String[] {
                        IconCodes.NEW_PALETTE,
                        IconCodes.IMPORT_PALETTE,
                        IconCodes.CONTENTS_TO_PALETTE,
                        IconCodes.DELETE_PALETTE,
                        IconCodes.SAVE_PALETTE,
                        IconCodes.SORT_PALETTE,
                        IconCodes.PALETTIZE,
                        IconCodes.PALETTE_SETTINGS
                },
                getPreconditions(
                        () -> true,
                        () -> true,
                        () -> hasPaletteContents && s
                                .getSelectedPalette().isMutable(),
                        () -> hasPaletteContents,
                        () -> hasPaletteContents && s
                                .getSelectedPalette().isMutable(),
                        () -> hasPaletteContents,
                        () -> hasPaletteContents,
                        () -> hasPaletteContents && s
                                .getSelectedPalette().isMutable()
                ),
                new Runnable[] {
                        s::newPalette,
                        s::openPalette,
                        () -> DialogAssembly.setDialogToAddContentsToPalette(
                                s.getSelectedPalette()),
                        s::deletePalette,
                        () -> DialogAssembly.setDialogToSavePalette(
                                s.getSelectedPalette()),
                        () -> DialogAssembly.setDialogToSortPalette(
                                s.getSelectedPalette()),
                        () -> DialogAssembly.setDialogToPalettize(
                                s.getSelectedPalette()),
                        () -> DialogAssembly.setDialogToPaletteSettings(
                                s.getSelectedPalette())
                }, paletteOptionsRef);
        populateButtonsIntoBuilder(
                mb, new String[] {
                        IconCodes.ADD_TO_PALETTE,
                        IconCodes.REMOVE_FROM_PALETTE,
                        IconCodes.MOVE_LEFT_IN_PALETTE,
                        IconCodes.MOVE_RIGHT_IN_PALETTE
                },
                getPreconditions(
                        () -> hasPaletteContents && s.getSelectedPalette().isMutable(),
                        () -> hasPaletteContents && s.getSelectedPalette().isMutable(),
                        () -> hasPaletteContents && s.getSelectedPalette()
                                .canMoveLeft(s.getSelectedColor()),
                        () -> hasPaletteContents && s.getSelectedPalette()
                                .canMoveRight(s.getSelectedColor())
                ),
                new Runnable[] {
                        s::addColorToPalette,
                        s::removeColorFromPalette,
                        s::moveColorLeftInPalette,
                        s::moveColorRightInPalette
                }, selColOptionsRef);

        // dropdown menu
        final List<Runnable> behaviours = new ArrayList<>();

        for (int i = 0; i < palettes.size(); i++) {
            final int toSet = i;
            behaviours.add(() -> s.setPaletteIndex(toSet));
        }

        final Coord2D dropdownPos = startingPos.displace(0,
                Layout.getSegmentContentDisplacement().y + Layout.BUTTON_INC);
        final int dropDownHAllowance = Layout.getColorsHeight() / 3;

        mb.add(hasPaletteContents
                ? new Dropdown(dropdownPos, contentWidth,
                MenuElement.Anchor.LEFT_TOP, dropDownHAllowance,
                Dropdown.DEFAULT_RENDER_ORDER,
                palettes.stream().map(Palette::getName).toArray(String[]::new),
                behaviours.toArray(Runnable[]::new), () -> index)
                : new StaticMenuElement(dropdownPos,
                new Bounds2D(contentWidth, Layout.STD_TEXT_BUTTON_H),
                MenuElement.Anchor.LEFT_TOP,
                Settings.getTheme().logic.drawTextButton(
                        TextButton.of("No palettes", contentWidth,
                                Alignment.CENTER, ButtonType.STUB))));

        // palette buttons
        if (hasPaletteContents) {
            final Coord2D container = dropdownPos.displace(0,
                    Layout.STD_TEXT_BUTTON_INC);
            final int fitsOnLine = (contentWidth - Layout.SLIDER_OFF_DIM) /
                    Layout.PALETTE_DIMS.width();
            final int height = Layout.getColorsHeight() -
                    ((container.y - Layout.getColorsPosition().y) +
                            Layout.CONTENT_BUFFER_PX);

            final List<PaletteColorButton> buttons = new ArrayList<>();
            final Color[] colors = s.getSelectedPalette().getColors();

            for (int i = 0; i < colors.length; i++) {
                final int x = i % fitsOnLine, y = i / fitsOnLine;
                final Coord2D pos = container.displace(
                        x * Layout.PALETTE_DIMS.width(),
                        y * Layout.PALETTE_DIMS.height());

                buttons.add(new PaletteColorButton(pos, colors[i], s.getSelectedPalette()));
            }

            mb.add(new VerticalScrollBox(
                    container, new Bounds2D(contentWidth, height),
                    buttons.stream().map(Scrollable::new)
                            .toArray(Scrollable[]::new),
                    container.displace(0, (colors.length / fitsOnLine) *
                            Layout.PALETTE_DIMS.height()).y, 0));
        }
    }

    public static Menu buildToolButtonMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        final Tool[] all = Tool.getAll();
        for (int i = 0; i < all.length; i++) {
            mb.add(toolButtonFromTool(all[i], i));
        }

        addHidePanelToMenuBuilder(mb, Layout.getToolsPosition()
                        .displace(Layout.getToolsWidth(), 0),
                () -> Layout.setToolbarShowing(false));

        // outline button
        final Coord2D outlinePos = Layout.getToolsPosition()
                .displace(Layout.BUTTON_OFFSET,
                        Layout.getToolsHeight() - Layout.BUTTON_INC);

        final MenuElement outlineButton = GraphicsUtils.
                generateIconButton(IconCodes.OUTLINE, outlinePos,
                        () -> true, DialogAssembly::setDialogToOutline);
        mb.add(outlineButton);

        // reflection buttons
        final MenuElement verticalReflectionButton = GraphicsUtils.
                generateIconButton(IconCodes.VERTICAL_REFLECTION,
                        outlinePos.displace(0, -Layout.BUTTON_INC),
                        () -> c.getState().hasSelection(), () -> {
                            if (c.getState().getSelectionMode() == SelectionMode.BOUNDS)
                                c.reflectSelection(false);
                            else
                                c.reflectSelectionContents(false);
                        }
                );
        mb.add(verticalReflectionButton);
        final MenuElement horizontalReflectionButton = GraphicsUtils.
                generateIconButton(IconCodes.HORIZONTAL_REFLECTION,
                        outlinePos.displace(0, -2 * Layout.BUTTON_INC),
                        () -> c.getState().hasSelection(), () -> {
                            if (c.getState().getSelectionMode() == SelectionMode.BOUNDS)
                                c.reflectSelection(true);
                            else
                                c.reflectSelectionContents(true);
                        });
        mb.add(horizontalReflectionButton);

        // pixel grid
        final MenuElement pixelGridToggleButton = GraphicsUtils
                .generateIconToggleButton(
                        outlinePos.displace(0, -3 * Layout.BUTTON_INC),
                        new String[] {
                                IconCodes.PIXEL_GRID_OFF,
                                IconCodes.PIXEL_GRID_ON
                        },
                        new Runnable[] {
                                () -> c.renderInfo.setPixelGrid(true),
                                () -> c.renderInfo.setPixelGrid(false)
                        },
                        () -> c.renderInfo.isPixelGridOn() ? 1 : 0, () -> {},
                        c::couldRenderPixelGrid, IconCodes.PIXEL_GRID_OFF);
        mb.add(pixelGridToggleButton);

        final Tool tool = StippleEffect.get().getTool();

        if (tool.hasToolOptionsBar())
            mb.add(tool.buildToolOptionsBar());

        return mb.build();
    }

    private static SimpleMenuButton toolButtonFromTool(
            final Tool tool, final int index
    ) {
        final Coord2D position = Layout.getToolsPosition().displace(
                Layout.BUTTON_OFFSET, Layout.BUTTON_OFFSET +
                        (Layout.BUTTON_INC * (index + 1)));

        return new IconButton(tool.convertNameToFilename(),
                position, () -> StippleEffect.get().setTool(tool),
                StippleEffect.get().getTool().equals(tool)
                        ? tool.getSelectedIcon() : tool.getIcon(),
                tool.getHighlightedIcon());
    }

    public static Menu buildBottomBarMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        final int bottomBarTextY = Layout.getBottomBarPosition().y +
                Layout.TEXT_Y_OFFSET,
                bottomBarButtonY = Layout.getBottomBarPosition().y +
                        Layout.BUTTON_OFFSET;

        // active tool
        final Indicator toolIndicator = new Indicator(new Coord2D(
                Layout.BUTTON_OFFSET, bottomBarButtonY),
                IconCodes.IND_TOOL);
        final DynamicLabel toolLabel = DynamicLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(toolIndicator, false),
                bottomBarTextY),
                () -> StippleEffect.get().getTool().getBottomBarText(),
                Layout.getBottomBarToolWidth());
        mb.addAll(toolIndicator, toolLabel);

        // target pixel
        final Indicator targetIndicator = new Indicator(new Coord2D(
                Layout.getBottomBarTargetPixelX(), bottomBarButtonY),
                IconCodes.IND_TARGET);
        final DynamicLabel targetLabel = DynamicLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(targetIndicator, false),
                bottomBarTextY),
                c::getTargetPixelText, Layout.getBottomBarTargetPixelWidth());
        mb.addAll(targetIndicator, targetLabel);

        // canvas size
        final Indicator boundsIndicator = new Indicator(new Coord2D(
                Layout.getBottomBarCanvasSizeX(), bottomBarButtonY),
                IconCodes.IND_BOUNDS);
        final DynamicLabel boundsLabel = DynamicLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(boundsIndicator, false),
                bottomBarTextY),
                c::getImageSizeText, Layout.getBottomBarCanvasSizeWidth());
        mb.addAll(boundsIndicator, boundsLabel);

        // zoom
        final Indicator zoomIndicator = new Indicator(
                new Coord2D(Layout.getBottomBarZoomPercentageX(),
                        bottomBarButtonY), IconCodes.IND_ZOOM);
        final IncrementalRangeElements<Float> zoom =
                IncrementalRangeElements.makeForFloat(zoomIndicator,
                        Layout.getBottomBarPosition().y + Layout.BUTTON_OFFSET,
                        bottomBarTextY,
                        () -> c.renderInfo.zoomOut(Constants.NO_VALID_TARGET),
                        () -> c.renderInfo.zoomIn(Constants.NO_VALID_TARGET),
                        ZoomLevel.MIN.z, ZoomLevel.MAX.z,
                        z -> c.renderInfo.setZoomLevel(ZoomLevel.fromZ(z)),
                        c.renderInfo::getZoomFactor,
                        z -> ZoomLevel.fromZ(z).ordinal(),
                        sv -> ZoomLevel.values()[sv].z,
                        f -> c.renderInfo.getZoomText(), "XXX.XX%");
        mb.addAll(zoomIndicator, zoom.decButton, zoom.incButton,
                zoom.slider, zoom.value);

        // selection
        mb.add(DynamicLabel.make(new Coord2D(Layout.width() -
                Layout.CONTENT_BUFFER_PX, bottomBarTextY),
                MenuElement.Anchor.RIGHT_TOP, c::getSelectionText,
                Layout.width() -
                (Layout.getBottomBarZoomSliderX() + Layout.getUISliderWidth())));

        return mb.build();
    }
}
