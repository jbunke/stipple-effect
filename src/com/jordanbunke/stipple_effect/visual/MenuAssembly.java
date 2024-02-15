package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.Menu;
import com.jordanbunke.delta_time.menus.MenuBuilder;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.invisible.GatewayMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.layer.OnionSkinMode;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.PlaybackInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.SelectionMode;
import com.jordanbunke.stipple_effect.tools.Tool;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.menu_elements.*;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.ColorSelector;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.ColorTextBox;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.PaletteColorButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalScrollingMenuElement;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalSlider;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.ScrollableMenuElement;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollingMenuElement;

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

        mb.add(TextLabel.make(Layout.getProjectsPosition().displace(
                        Layout.CONTENT_BUFFER_PX, Layout.TEXT_Y_OFFSET),
                "Projects", Constants.WHITE));

        populateButtonsIntoBuilder(mb,
                new String[] {
                        IconCodes.SETTINGS,
                        IconCodes.NEW_PROJECT, IconCodes.OPEN_FILE,
                        IconCodes.SAVE, IconCodes.SAVE_AS,
                        IconCodes.RESIZE, IconCodes.PAD, IconCodes.PREVIEW,
                        IconCodes.UNDO, IconCodes.GRANULAR_UNDO,
                        IconCodes.GRANULAR_REDO, IconCodes.REDO
                },
                getPreconditions(
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> c.getStateManager().canUndo(),
                        () -> c.getStateManager().canUndo(),
                        () -> c.getStateManager().canRedo(),
                        () -> c.getStateManager().canRedo()),
                new Runnable[] {
                        DialogAssembly::setDialogToProgramSettings,
                        DialogAssembly::setDialogToNewProject,
                        () -> StippleEffect.get().openProject(),
                        c.projectInfo::save,
                        DialogAssembly::setDialogToSave,
                        DialogAssembly::setDialogToResize,
                        DialogAssembly::setDialogToPad,
                        () -> PreviewWindow.set(c),
                        () -> c.getStateManager().undoToCheckpoint(),
                        () -> c.getStateManager().undo(true),
                        () -> c.getStateManager().redo(true),
                        () -> c.getStateManager().redoToCheckpoint()
                }, Layout.getProjectsPosition());

        // exit program button
        final Coord2D exitProgPos = Layout.getProjectsPosition().displace(
                Layout.getProjectsWidth() - (Layout.CONTENT_BUFFER_PX + Layout.BUTTON_DIM),
                Layout.ICON_BUTTON_OFFSET_Y);
        mb.add(IconButton.make(IconCodes.EXIT_PROGRAM, exitProgPos,
                DialogAssembly::setDialogToExitProgramAYS));

        // panel expand / collapse
        final Coord2D panelIconPos = exitProgPos.displace(-Layout.BUTTON_INC, 0);

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

        final int amount = StippleEffect.get().getContexts().size(), elementsPerProject = 2,
                selectedIndex = StippleEffect.get().getContextIndex();

        final ScrollableMenuElement[] projectElements = new ScrollableMenuElement[amount * elementsPerProject];

        final Coord2D firstPos = Layout.getProjectsPosition()
                .displace(Layout.getSegmentContentDisplacement());
        int realRightX = firstPos.x, cumulativeWidth = 0, initialOffsetX = 0;

        for (int i = 0; i < amount; i++) {
            final String text = StippleEffect.get().getContexts().get(i)
                    .projectInfo.getFormattedName(true, true);
            final int paddedTextWidth = GraphicsUtils.uiText()
                    .addText(text).build().draw().getWidth() +
                    Layout.PROJECT_NAME_BUTTON_PADDING_W;

            final GameImage baseImage = GraphicsUtils.drawTextButton(paddedTextWidth,
                    text, false, Constants.GREY),
                    highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage),
                    selectedImage = GraphicsUtils.drawTextButton(paddedTextWidth,
                            text, true, Constants.GREY);

            int offsetX = 0;

            final Coord2D pos = firstPos.displace(cumulativeWidth, 0),
                    dims = new Coord2D(baseImage.getWidth(), baseImage.getHeight());

            offsetX += paddedTextWidth + Layout.BUTTON_OFFSET;

            projectElements[i] = new ScrollableMenuElement(new SelectableListItemButton(pos, dims,
                    MenuElement.Anchor.LEFT_TOP, baseImage, highlightedImage, selectedImage,
                    i, () -> StippleEffect.get().getContextIndex(),
                    s -> StippleEffect.get().setContextIndex(s)));

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

            projectElements[amount + i] = new ScrollableMenuElement(
                    IconButton.make(IconCodes.CLOSE_PROJECT, cpPos, closeBehaviour));

            cumulativeWidth += offsetX;
            realRightX = cpPos.x + Layout.BUTTON_DIM;

            if (i == selectedIndex - Layout.PROJECTS_BEFORE_TO_DISPLAY)
                initialOffsetX = pos.x - firstPos.x;
        }

        mb.add(new HorizontalScrollingMenuElement(firstPos, new Coord2D(
                Layout.getProjectScrollWindowWidth(), Layout.TOP_PANEL_SCROLL_WINDOW_H),
                projectElements, realRightX, initialOffsetX));

        return mb.build();
    }

    public static Menu buildFramesMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        mb.add(TextLabel.make(Layout.getFramesPosition().displace(
                        Layout.CONTENT_BUFFER_PX, Layout.TEXT_Y_OFFSET),
                "Frames", Constants.WHITE));

        populateButtonsIntoBuilder(mb,
                new String[] {
                        IconCodes.NEW_FRAME,
                        IconCodes.DUPLICATE_FRAME,
                        IconCodes.REMOVE_FRAME,
                        IconCodes.MOVE_FRAME_BACK,
                        IconCodes.MOVE_FRAME_FORWARD,
                        IconCodes.TO_FIRST_FRAME,
                        IconCodes.PREVIOUS,
                        Constants.ICON_ID_GAP_CODE, // gap for play/stop button
                        IconCodes.NEXT,
                        IconCodes.TO_LAST_FRAME
                },
                getPreconditions(
                        () -> c.getState().canAddFrame(),
                        () -> c.getState().canAddFrame(),
                        () -> c.getState().canRemoveFrame(),
                        () -> c.getState().canMoveFrameBack(),
                        () -> c.getState().canMoveFrameForward(),
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

        final Coord2D firstPos = Layout.getFramesPosition()
                .displace(Layout.getSegmentContentDisplacement());

        // play/stop as toggle

        final Coord2D playStopTogglePos = Layout.getFramesPosition().displace(
                Layout.SEGMENT_TITLE_BUTTON_OFFSET_X + (7 * Layout.BUTTON_INC),
                Layout.ICON_BUTTON_OFFSET_Y);

        mb.add(generatePlayStopToggle(playStopTogglePos));

        // playback mode toggle button

        final Coord2D playbackModeTogglePos = Layout.getFramesPosition().displace(
                Layout.SEGMENT_TITLE_BUTTON_OFFSET_X + (10 * Layout.BUTTON_INC),
                Layout.ICON_BUTTON_OFFSET_Y);
        mb.add(generatePlaybackModeToggle(playbackModeTogglePos));

        // playback speed slider and dynamic label for playback speed

        final Coord2D labelPos = Layout.getFramesPosition().displace(
                Layout.getFramesWidth() - (Layout.CONTENT_BUFFER_PX +
                        Layout.BUTTON_INC), Layout.TEXT_Y_OFFSET);

        mb.add(new DynamicLabel(labelPos,
                MenuElement.Anchor.RIGHT_TOP, Constants.WHITE,
                () -> c.playbackInfo.getFps() + " fps",
                Layout.DYNAMIC_LABEL_W_ALLOWANCE));

        final Coord2D playbackSliderPos = Layout.getFramesPosition().displace(
                Layout.getFramesWidth() - Layout.DYNAMIC_LABEL_W_ALLOWANCE,
                Layout.ICON_BUTTON_OFFSET_Y);

        final HorizontalSlider slider = new HorizontalSlider(playbackSliderPos,
                Layout.getUISliderWidth(), MenuElement.Anchor.RIGHT_TOP,
                Constants.MIN_PLAYBACK_FPS, Constants.MAX_PLAYBACK_FPS,
                c.playbackInfo::getFps, c.playbackInfo::setFps);
        slider.updateAssets();
        mb.add(slider);

        // frame content

        final int amount = c.getState().getFrameCount(),
                elementsPerFrame = 1;

        final ScrollableMenuElement[] frameElements =
                new ScrollableMenuElement[amount * elementsPerFrame];

        int realRightX = firstPos.x;

        for (int i = 0; i < amount; i++) {
            final GameImage baseImage = GraphicsUtils.drawTextButton(Layout.FRAME_BUTTON_W,
                    String.valueOf(i + 1), false, Constants.GREY),
                    highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage),
                    selectedImage = GraphicsUtils.drawTextButton(Layout.FRAME_BUTTON_W,
                            String.valueOf(i + 1), true, Constants.GREY);

            final Coord2D pos = firstPos.displace(
                    i * (Layout.FRAME_BUTTON_W + Layout.BUTTON_OFFSET), 0),
                    dims = new Coord2D(baseImage.getWidth(), baseImage.getHeight());

            frameElements[i] = new ScrollableMenuElement(new SelectableListItemButton(pos, dims,
                    MenuElement.Anchor.LEFT_TOP, baseImage, highlightedImage, selectedImage,
                    i, () -> c.getState().getFrameIndex(),
                    s -> c.getState().setFrameIndex(s)
            ));

            realRightX = pos.x + dims.x;
        }

        mb.add(new HorizontalScrollingMenuElement(firstPos, new Coord2D(
                Layout.getFrameScrollWindowWidth(), Layout.TOP_PANEL_SCROLL_WINDOW_H),
                frameElements, realRightX, frameButtonXDisplacement()));

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
                "Layers", Constants.WHITE));

        populateButtonsIntoBuilder(mb,
                new String[] {
                        IconCodes.NEW_LAYER,
                        IconCodes.DUPLICATE_LAYER,
                        IconCodes.REMOVE_LAYER,
                        IconCodes.MOVE_LAYER_UP,
                        IconCodes.MOVE_LAYER_DOWN,
                        IconCodes.MERGE_WITH_LAYER_BELOW
                },
                getPreconditions(
                        () -> c.getState().canAddLayer(),
                        () -> c.getState().canAddLayer(),
                        () -> c.getState().canRemoveLayer(),
                        () -> c.getState().canMoveLayerUp(),
                        () -> c.getState().canMoveLayerDown(),
                        () -> c.getState().canMoveLayerDown()),
                new Runnable[] {
                        c::addLayer,
                        c::duplicateLayer,
                        c::removeLayer,
                        c::moveLayerUp,
                        c::moveLayerDown,
                        c::mergeWithLayerBelow
                }, Layout.getLayersPosition());

        addHidePanelToMenuBuilder(mb, Layout.getLayersPosition()
                        .displace(Layout.getLayersWidth(), 0),
                () -> Layout.setLayersPanelShowing(false));

        // layer content

        final List<SELayer> layers = c.getState().getLayers();
        final int amount = layers.size(), elementsPerLayer = 5;

        final ScrollableMenuElement[] layerButtons = new ScrollableMenuElement[amount * elementsPerLayer];

        final Coord2D firstPos = Layout.getLayersPosition()
                .displace(Layout.getSegmentContentDisplacement());
        int realBottomY = firstPos.y;

        for (int i = amount - 1; i >= 0; i--) {
            final SELayer layer = layers.get(i);

            final String name = layer.getName(),
                    text = name.length() > Layout.LAYER_NAME_LENGTH_CUTOFF
                            ? name.substring(0, Layout.LAYER_NAME_LENGTH_CUTOFF) + "..."
                            : name;

            final GameImage baseImage = GraphicsUtils.drawTextButton(Layout.LAYER_BUTTON_W,
                    text, false, Constants.GREY),
                    highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage),
                    selectedImage = GraphicsUtils.drawTextButton(Layout.LAYER_BUTTON_W,
                            text, true, Constants.GREY);

            final Coord2D pos = firstPos.displace(0,
                    (amount - (i + 1)) * Layout.STD_TEXT_BUTTON_INC),
                    dims = new Coord2D(baseImage.getWidth(), baseImage.getHeight());

            layerButtons[i] = new ScrollableMenuElement(new SelectableListItemButton(pos, dims,
                    MenuElement.Anchor.LEFT_TOP, baseImage, highlightedImage, selectedImage,
                    i, () -> c.getState().getLayerEditIndex(),
                    s -> c.getState().setLayerEditIndex(s)
            ));

            final int index = i;

            // visibility toggle
            final Coord2D vtPos = pos.displace(
                    Layout.LAYER_BUTTON_W + Layout.BUTTON_OFFSET,
                    (Layout.STD_TEXT_BUTTON_H / 2)  - (Layout.BUTTON_DIM / 2));
            layerButtons[amount + i] = new ScrollableMenuElement(
                    new LayerVisibilityButton(vtPos, index));

            // frames linked toggle
            final Coord2D flPos = vtPos.displace(Layout.BUTTON_INC, 0);
            layerButtons[(2 * amount) + i] =
                    new ScrollableMenuElement(generateFramesLinkedToggle(index, flPos));

            // onion skin toggle
            final Coord2D onionPos = vtPos.displace(Layout.BUTTON_INC * 2, 0);
            layerButtons[(3 * amount) + i] =
                    new ScrollableMenuElement(generateOnionSkinToggle(index, onionPos));

            // layer settings
            final Coord2D lsPos = vtPos.displace(Layout.BUTTON_INC * 3, 0);
            layerButtons[(4 * amount) + i] = new ScrollableMenuElement(
                    GraphicsUtils.generateIconButton(IconCodes.LAYER_SETTINGS,
                            lsPos, () -> true,
                            () -> DialogAssembly.setDialogToLayerSettings(index)));

            realBottomY = pos.y + dims.y;
        }

        final int initialOffsetY = layerButtonYDisplacement(amount);

        mb.add(new VerticalScrollingMenuElement(firstPos, new Coord2D(
                Layout.VERT_SCROLL_WINDOW_W, Layout.getVertScrollWindowHeight()),
                layerButtons, realBottomY, initialOffsetY));

        return mb.build();
    }

    private static MenuElement generateOnionSkinToggle(
            final int index, final Coord2D pos
    ) {
        final SEContext c = StippleEffect.get().getContext();
        final String[] codes = Arrays.stream(OnionSkinMode.values())
                .map(OnionSkinMode::getIconCode).toArray(String[]::new);

        final Runnable[] behaviours = Arrays.stream(OnionSkinMode.values()).map(
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
            final Coord2D segmentPosition
    ) {
        if (iconIDs.length != preconditions.length || iconIDs.length != behaviours.length) {
            GameError.send("Lengths of button assembly argument arrays did not match; " +
                    "buttons were not populated into menu builder.");
            return;
        }

        for (int i = 0; i < iconIDs.length; i++) {
            if (iconIDs[i].equals(Constants.ICON_ID_GAP_CODE))
                continue;

            final Coord2D pos = segmentPosition
                    .displace(Layout.SEGMENT_TITLE_BUTTON_OFFSET_X,
                            Layout.ICON_BUTTON_OFFSET_Y)
                    .displace(i * Layout.BUTTON_INC, 0);
            mb.add(GraphicsUtils.generateIconButton(iconIDs[i],
                    pos, preconditions[i], behaviours[i]));
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
                "Colors", Constants.WHITE));

        populateButtonsIntoBuilder(
                mb, new String[] {
                        IconCodes.SWAP_COLORS,
                        IconCodes.COLOR_MENU_MODE,
                },
                getPreconditions(
                        () -> true,
                        () -> true
                ),
                new Runnable[] {
                        () -> StippleEffect.get().swapColors(),
                        () -> StippleEffect.get().toggleColorMenuMode(),
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
            }, Constants.WHITE));

            final ColorTextBox colorTextBox = ColorTextBox.make(textBoxPos, i);
            mb.add(colorTextBox);

            final int index = i;
            final Coord2D dims = new Coord2D(colorTextBox.getWidth(),
                    colorTextBox.getHeight());
            final GatewayMenuElement highlight = new GatewayMenuElement(
                    new StaticMenuElement(textBoxPos, dims, MenuElement.Anchor.CENTRAL_TOP,
                            GraphicsUtils.drawSelectedTextBox(
                                    new GameImage(dims.x, dims.y))),
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
        mb.add(TextLabel.make(startingPos, "Palette", Constants.WHITE));

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

        //dropdown menu
        final List<Runnable> behaviours = new ArrayList<>();

        for (int i = 0; i < palettes.size(); i++) {
            final int toSet = i;
            behaviours.add(() -> s.setPaletteIndex(toSet));
        }

        final Coord2D dropdownPos = startingPos.displace(0,
                Layout.getSegmentContentDisplacement().y + Layout.BUTTON_INC);
        final int dropDownHAllowance = Layout.getColorsHeight() / 3;

        mb.add(hasPaletteContents
                ? new DropDownMenu(dropdownPos, contentWidth,
                MenuElement.Anchor.LEFT_TOP, dropDownHAllowance,
                palettes.stream().map(Palette::getName).toArray(String[]::new),
                behaviours.toArray(Runnable[]::new), () -> index)
                : new StaticMenuElement(dropdownPos,
                new Coord2D(contentWidth, Layout.STD_TEXT_BUTTON_H),
                MenuElement.Anchor.LEFT_TOP, GraphicsUtils.drawTextButton(
                contentWidth, "No palettes", false, Constants.DARK)));

        // palette buttons
        if (hasPaletteContents) {
            final Coord2D container = dropdownPos.displace(0,
                    Layout.STD_TEXT_BUTTON_INC);
            final int fitsOnLine = (contentWidth - Layout.SLIDER_OFF_DIM) /
                    Layout.PALETTE_DIMS.x;
            final int height = Layout.getColorsHeight() -
                    ((container.y - Layout.getColorsPosition().y) +
                            Layout.CONTENT_BUFFER_PX);

            final List<PaletteColorButton> buttons = new ArrayList<>();
            final Color[] colors = s.getSelectedPalette().getColors();

            for (int i = 0; i < colors.length; i++) {
                final int x = i % fitsOnLine, y = i / fitsOnLine;
                final Coord2D pos = container.displace(
                        x * Layout.PALETTE_DIMS.x, y * Layout.PALETTE_DIMS.y);

                buttons.add(new PaletteColorButton(pos, colors[i], s.getSelectedPalette()));
            }

            mb.add(new VerticalScrollingMenuElement(
                    container, new Coord2D(contentWidth, height),
                    buttons.stream().map(ScrollableMenuElement::new)
                            .toArray(ScrollableMenuElement[]::new),
                    container.displace(0, (colors.length / fitsOnLine) *
                            Layout.PALETTE_DIMS.y).y, 0));
        }
    }

    public static Menu buildToolButtonMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        final Tool[] all = Tool.getAll();
        for (int i = 0; i < all.length; i++) {
            mb.add(toolButtonFromTool(all[i], i));
        }

        // outline button
        final Coord2D outlinePos = Layout.getToolsPosition()
                .displace(Layout.BUTTON_OFFSET,
                        Layout.getWorkspaceHeight() - Layout.BUTTON_INC);

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

        return mb.build();
    }

    private static SimpleMenuButton toolButtonFromTool(
            final Tool tool, final int index
    ) {
        final Coord2D position = Layout.getToolsPosition().displace(
                Layout.BUTTON_OFFSET,
                Layout.BUTTON_OFFSET + (Layout.BUTTON_INC * index)
        );

        return new IconButton(tool.convertNameToFilename(),
                position, () -> StippleEffect.get().setTool(tool),
                StippleEffect.get().getTool().equals(tool)
                        ? tool.getSelectedIcon() : tool.getIcon(),
                tool.getHighlightedIcon());
    }

    public static Menu buildBottomBarMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        // DYNAMIC LABELS
        final int bottomBarTextY = Layout.getBottomBarPosition().y +
                Layout.TEXT_Y_OFFSET;

        // active tool
        mb.add(new DynamicLabel(
                new Coord2D(Layout.CONTENT_BUFFER_PX, bottomBarTextY),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                () -> StippleEffect.get().getTool().getBottomBarText(),
                Layout.getBottomBarToolWidth()));

        // target pixel
        mb.add(new DynamicLabel(
                new Coord2D(Layout.getBottomBarTargetPixelX(), bottomBarTextY),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                c::getTargetPixelText, Layout.getBottomBarTargetPixelWidth()));

        // canvas size
        mb.add(new DynamicLabel(new Coord2D(
                Layout.getBottomBarCanvasSizeX(), bottomBarTextY),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                c::getImageSizeText, Layout.getBottomBarCanvasSizeWidth()));

        // zoom percentage
        mb.add(new DynamicLabel(new Coord2D(
                Layout.getBottomBarZoomPercentageX(), bottomBarTextY),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                c.renderInfo::getZoomText, Layout.getBottomBarZoomPercentageWidth()));

        // selection
        mb.add(new DynamicLabel(new Coord2D(Layout.width() -
                (Layout.CONTENT_BUFFER_PX + (2 * Layout.BUTTON_INC)), bottomBarTextY),
                MenuElement.Anchor.RIGHT_TOP, Constants.WHITE,
                c::getSelectionText, Layout.width() -
                (Layout.getBottomBarZoomSliderX() + Layout.getUISliderWidth())));


        // zoom slider
        final float base = 2f;
        final HorizontalSlider zoomSlider = new HorizontalSlider(
                Layout.getBottomBarPosition().displace(
                        Layout.getBottomBarZoomSliderX(),
                        Layout.BUTTON_OFFSET),
                Layout.getUISliderWidth(),
                MenuElement.Anchor.LEFT_TOP,
                (int)(Math.log(Constants.MIN_ZOOM) / Math.log(base)),
                (int)(Math.log(Constants.MAX_ZOOM) / Math.log(base)),
                () -> (int)(Math.log(c.renderInfo.getZoomFactor()) / Math.log(base)),
                i -> c.renderInfo.setZoomFactor((float)Math.pow(base, i)));
        zoomSlider.updateAssets();

        mb.add(zoomSlider);

        // help button
        final Coord2D helpButtonPos = Layout.getBottomBarPosition().displace(
                Layout.width() - Layout.BUTTON_DIM, Layout.BUTTON_OFFSET);
        mb.add(IconButton.make(IconCodes.INFO, helpButtonPos,
                DialogAssembly::setDialogToInfo));

        // panel manager button
        mb.add(IconButton.make(IconCodes.PANEL_MANAGER,
                helpButtonPos.displace(-Layout.BUTTON_INC, 0),
                DialogAssembly::setDialogToPanelManager));

        return mb.build();
    }
}
