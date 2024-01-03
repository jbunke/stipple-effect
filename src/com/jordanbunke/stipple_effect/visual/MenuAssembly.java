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
import com.jordanbunke.stipple_effect.project.PlaybackInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.layer.OnionSkinMode;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.selection.SelectionMode;
import com.jordanbunke.stipple_effect.visual.menu_elements.SelectableListItemButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.ColorTextBox;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.ColorSelector;
import com.jordanbunke.stipple_effect.visual.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalScrollingMenuElement;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalSlider;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.ScrollableMenuElement;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollingMenuElement;
import com.jordanbunke.stipple_effect.tools.*;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;

import java.util.Arrays;
import java.util.List;

public class MenuAssembly {

    public static Menu stub() {
        return new Menu();
    }

    public static Menu buildProjectsMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        final String[] iconIDs = new String[] {
                IconCodes.SETTINGS,
                IconCodes.NEW_PROJECT, IconCodes.OPEN_FILE,
                IconCodes.SAVE, IconCodes.SAVE_AS,
                IconCodes.RESIZE, IconCodes.PAD,
                IconCodes.UNDO, IconCodes.GRANULAR_UNDO,
                IconCodes.GRANULAR_REDO, IconCodes.REDO
        };

        final boolean[] preconditions = new boolean[] {
                true, true, true, true, true, true, true,
                c.getStateManager().canUndo(),
                c.getStateManager().canUndo(),
                c.getStateManager().canRedo(),
                c.getStateManager().canRedo()
        };

        final Runnable[] behaviours = new Runnable[] {
                DialogAssembly::setDialogToProgramSettings,
                DialogAssembly::setDialogToNewProject,
                () -> StippleEffect.get().openProject(),
                c.projectInfo::save,
                DialogAssembly::setDialogToSave,
                DialogAssembly::setDialogToResize,
                DialogAssembly::setDialogToPad,
                () -> c.getStateManager().undoToCheckpoint(),
                () -> c.getStateManager().undo(true),
                () -> c.getStateManager().redo(true),
                () -> c.getStateManager().redoToCheckpoint()
        };

        populateButtonsIntoBuilder(mb, iconIDs, preconditions,
                behaviours, Constants.getProjectsPosition());

        // project previews

        final int amount = StippleEffect.get().getContexts().size(), elementsPerProject = 2,
                selectedIndex = StippleEffect.get().getContextIndex();

        final ScrollableMenuElement[] projectElements = new ScrollableMenuElement[amount * elementsPerProject];

        final Coord2D firstPos = Constants.getProjectsPosition()
                .displace(Constants.getSegmentContentDisplacement());
        int realRightX = firstPos.x, cumulativeWidth = 0, initialOffsetX = 0;

        for (int i = 0; i < amount; i++) {
            final String text = StippleEffect.get().getContexts().get(i)
                    .projectInfo.getFormattedName(true, true);
            final int paddedTextWidth = GraphicsUtils.uiText()
                    .addText(text).build().draw().getWidth() +
                    Constants.PROJECT_NAME_BUTTON_PADDING_W;

            final GameImage baseImage = GraphicsUtils.drawTextButton(paddedTextWidth,
                    text, false, Constants.GREY),
                    highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage),
                    selectedImage = GraphicsUtils.drawTextButton(paddedTextWidth,
                            text, true, Constants.GREY);

            int offsetX = 0;

            final Coord2D pos = firstPos.displace(cumulativeWidth, 0),
                    dims = new Coord2D(baseImage.getWidth(), baseImage.getHeight());

            offsetX += paddedTextWidth + Constants.BUTTON_OFFSET;

            projectElements[i] = new ScrollableMenuElement(new SelectableListItemButton(pos, dims,
                    MenuElement.Anchor.LEFT_TOP, baseImage, highlightedImage, selectedImage,
                    i, () -> StippleEffect.get().getContextIndex(),
                    s -> StippleEffect.get().setContextIndex(s)
            ));

            // close project button

            final Coord2D cpPos = pos.displace(offsetX,
                    (Constants.STD_TEXT_BUTTON_H - Constants.BUTTON_DIM) / 2);

            offsetX += Constants.BUTTON_DIM + Constants.SPACE_BETWEEN_PROJECT_BUTTONS_X;

            final int index = i;
            final Runnable closeBehaviour = () -> {
                if (StippleEffect.get().getContexts().get(index).projectInfo.hasUnsavedChanges()) {
                    DialogAssembly.setDialogToCheckCloseProject(index);
                } else {
                    StippleEffect.get().removeContext(index);
                }
            };

            projectElements[amount + i] = new ScrollableMenuElement(
                    GraphicsUtils.generateIconButton(IconCodes.CLOSE_PROJECT, cpPos,
                            true, closeBehaviour));

            cumulativeWidth += offsetX;
            realRightX = cpPos.x + Constants.BUTTON_DIM;

            if (i == selectedIndex - Constants.PROJECTS_BEFORE_TO_DISPLAY)
                initialOffsetX = pos.x - firstPos.x;
        }

        mb.add(new HorizontalScrollingMenuElement(firstPos,
                new Coord2D(Constants.FRAME_SCROLL_WINDOW_W, Constants.FRAME_SCROLL_WINDOW_H),
                projectElements, realRightX, initialOffsetX));

        return mb.build();
    }

    public static Menu buildFramesMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        final String[] iconIDs = new String[] {
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
        };

        final boolean[] preconditions = new boolean[] {
                c.getState().canAddFrame(),
                c.getState().canAddFrame(),
                c.getState().canRemoveFrame(),
                c.getState().canMoveFrameBack(),
                c.getState().canMoveFrameForward(),
                true,
                true,
                false, // placeholder
                true,
                true
        };

        final Runnable[] behaviours = new Runnable[] {
                () -> StippleEffect.get().getContext().addFrame(),
                () -> StippleEffect.get().getContext().duplicateFrame(),
                () -> StippleEffect.get().getContext().removeFrame(),
                () -> StippleEffect.get().getContext().moveFrameBack(),
                () -> StippleEffect.get().getContext().moveFrameForward(),
                () -> StippleEffect.get().getContext().getState().setFrameIndex(0),
                () -> StippleEffect.get().getContext().getState().previousFrame(),
                () -> {}, // placeholder
                () -> StippleEffect.get().getContext().getState().nextFrame(),
                () -> StippleEffect.get().getContext().getState().setFrameIndex(
                        StippleEffect.get().getContext().getState().getFrameCount() - 1
                )
        };

        populateButtonsIntoBuilder(mb, iconIDs, preconditions,
                behaviours, Constants.getFramesPosition());

        final Coord2D firstPos = Constants.getFramesPosition()
                .displace(Constants.getSegmentContentDisplacement());

        // play/stop as toggle

        final Coord2D playStopTogglePos = Constants.getFramesPosition().displace(
                Constants.SEGMENT_TITLE_BUTTON_OFFSET_X + (7 * Constants.BUTTON_INC),
                Constants.ICON_BUTTON_OFFSET_Y);

        mb.add(generatePlayStopToggle(playStopTogglePos));

        // playback mode toggle button

        final Coord2D playbackModeTogglePos = Constants.getFramesPosition().displace(
                Constants.SEGMENT_TITLE_BUTTON_OFFSET_X + (10 * Constants.BUTTON_INC),
                Constants.ICON_BUTTON_OFFSET_Y);
        mb.add(generatePlaybackModeToggle(playbackModeTogglePos));

        // playback speed slider and dynamic label for playback speed

        final Coord2D playbackSliderPos = firstPos.displace(
                Constants.FRAME_PLAYBACK_SLIDER_OFFSET_X,
                -Constants.SEGMENT_TITLE_CONTENT_OFFSET_Y + Constants.ICON_BUTTON_OFFSET_Y);

        final HorizontalSlider slider = new HorizontalSlider(playbackSliderPos,
                Constants.FRAME_PLAYBACK_SLIDER_W, MenuElement.Anchor.LEFT_TOP,
                Constants.MIN_PLAYBACK_FPS, Constants.MAX_PLAYBACK_FPS,
                StippleEffect.get().getContext().playbackInfo.getFps(),
                mpf -> StippleEffect.get().getContext()
                        .playbackInfo.setFps(mpf));
        slider.updateAssets();
        mb.add(slider);

        final Coord2D labelPos = Constants.getFramesPosition().displace(
                Constants.FRAMES_W - Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        mb.add(new DynamicLabel(labelPos,
                MenuElement.Anchor.RIGHT_TOP, Constants.WHITE,
                () -> StippleEffect.get().getContext().playbackInfo.getFps() + " fps",
                Constants.DYNAMIC_LABEL_W_ALLOWANCE));

        // frame content

        final int amount = StippleEffect.get().getContext().getState().getFrameCount(),
                elementsPerFrame = 1;

        final ScrollableMenuElement[] frameElements =
                new ScrollableMenuElement[amount * elementsPerFrame];

        int realRightX = firstPos.x;

        for (int i = 0; i < amount; i++) {
            final GameImage baseImage = GraphicsUtils.drawTextButton(Constants.FRAME_BUTTON_W,
                    String.valueOf(i + 1), false, Constants.GREY),
                    highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage),
                    selectedImage = GraphicsUtils.drawTextButton(Constants.FRAME_BUTTON_W,
                            String.valueOf(i + 1), true, Constants.GREY);

            final Coord2D pos = firstPos.displace(
                    i * (Constants.FRAME_BUTTON_W + Constants.BUTTON_OFFSET), 0),
                    dims = new Coord2D(baseImage.getWidth(), baseImage.getHeight());

            frameElements[i] = new ScrollableMenuElement(new SelectableListItemButton(pos, dims,
                    MenuElement.Anchor.LEFT_TOP, baseImage, highlightedImage, selectedImage,
                    i, () -> StippleEffect.get().getContext().getState().getFrameIndex(),
                    s -> StippleEffect.get().getContext().getState().setFrameIndex(s)
            ));

            realRightX = pos.x + dims.x;
        }

        mb.add(new HorizontalScrollingMenuElement(firstPos,
                new Coord2D(Constants.FRAME_SCROLL_WINDOW_W, Constants.FRAME_SCROLL_WINDOW_H),
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

        final GameImage[] baseIcons = Arrays.stream(validModes).map(
                mode -> GraphicsUtils.loadIcon(mode.getIconCode())
        ).toArray(GameImage[]::new);

        return new SimpleToggleMenuButton(pos,
                new Coord2D(Constants.BUTTON_DIM, Constants.BUTTON_DIM),
                MenuElement.Anchor.LEFT_TOP, true,
                baseIcons,
                Arrays.stream(baseIcons).map(GraphicsUtils::highlightIconButton)
                        .toArray(GameImage[]::new),
                Arrays.stream(validModes).map(mode ->
                        (Runnable) () -> {}).toArray(Runnable[]::new),
                () -> StippleEffect.get().getContext().playbackInfo
                        .getMode().buttonIndex(),
                () -> StippleEffect.get().getContext().playbackInfo.toggleMode());
    }

    private static SimpleToggleMenuButton generatePlayStopToggle(final Coord2D pos) {
        // 0: is playing, button click should STOP; 1: vice-versa

        final GameImage playing = GraphicsUtils.loadIcon(IconCodes.STOP),
                notPlaying = GraphicsUtils.loadIcon(IconCodes.PLAY);

        return new SimpleToggleMenuButton(pos,
                new Coord2D(Constants.BUTTON_DIM, Constants.BUTTON_DIM),
                MenuElement.Anchor.LEFT_TOP, true,
                new GameImage[] { playing, notPlaying },
                new GameImage[] {
                        GraphicsUtils.highlightIconButton(playing),
                        GraphicsUtils.highlightIconButton(notPlaying)
                },
                new Runnable[] {
                        () -> StippleEffect.get().getContext().playbackInfo.stop(),
                        () -> StippleEffect.get().getContext().playbackInfo.play()
                },
                () -> StippleEffect.get().getContext().playbackInfo
                        .isPlaying() ? 0 : 1, () -> {});
    }

    private static int frameButtonXDisplacement() {
        return (StippleEffect.get().getContext().getState().getFrameIndex() -
                Constants.FRAMES_BEFORE_TO_DISPLAY) *
                (Constants.FRAME_BUTTON_W + Constants.BUTTON_OFFSET);
    }

    public static Menu buildLayersMenu() {
        final MenuBuilder mb = new MenuBuilder();

        final String[] iconIDs = new String[] {
                IconCodes.NEW_LAYER,
                IconCodes.DUPLICATE_LAYER,
                IconCodes.REMOVE_LAYER,
                IconCodes.MOVE_LAYER_UP,
                IconCodes.MOVE_LAYER_DOWN,
                IconCodes.MERGE_WITH_LAYER_BELOW,
                IconCodes.ENABLE_ALL_LAYERS
        };

        final boolean[] preconditions = new boolean[] {
                StippleEffect.get().getContext().getState().canAddLayer(),
                StippleEffect.get().getContext().getState().canAddLayer(),
                StippleEffect.get().getContext().getState().canRemoveLayer(),
                StippleEffect.get().getContext().getState().canMoveLayerUp(),
                StippleEffect.get().getContext().getState().canMoveLayerDown(),
                StippleEffect.get().getContext().getState().canMoveLayerDown(),
                true
        };

        final Runnable[] behaviours = new Runnable[] {
                () -> StippleEffect.get().getContext().addLayer(true),
                () -> StippleEffect.get().getContext().duplicateLayer(),
                () -> StippleEffect.get().getContext().removeLayer(),
                () -> StippleEffect.get().getContext().moveLayerUp(),
                () -> StippleEffect.get().getContext().moveLayerDown(),
                () -> StippleEffect.get().getContext().mergeWithLayerBelow(),
                () -> StippleEffect.get().getContext().enableAllLayers()
        };

        populateButtonsIntoBuilder(mb, iconIDs, preconditions,
                behaviours, Constants.getLayersPosition());

        // layer content

        final List<SELayer> layers = StippleEffect.get().getContext().getState().getLayers();
        final int amount = layers.size(), elementsPerLayer = 6;

        final ScrollableMenuElement[] layerButtons = new ScrollableMenuElement[amount * elementsPerLayer];

        final Coord2D firstPos = Constants.getLayersPosition()
                .displace(Constants.getSegmentContentDisplacement());
        int realBottomY = firstPos.y;

        for (int i = amount - 1; i >= 0; i--) {
            final SELayer layer = layers.get(i);

            final String name = layer.getName(),
                    text = name.length() > Constants.LAYER_NAME_LENGTH_CUTOFF
                            ? name.substring(0, Constants.LAYER_NAME_LENGTH_CUTOFF) + "..."
                            : name;

            final GameImage baseImage = GraphicsUtils.drawTextButton(Constants.LAYER_BUTTON_W,
                    text, false, Constants.GREY),
                    highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage),
                    selectedImage = GraphicsUtils.drawTextButton(Constants.LAYER_BUTTON_W,
                            text, true, Constants.GREY);

            final Coord2D pos = firstPos.displace(0,
                    (amount - (i + 1)) * Constants.STD_TEXT_BUTTON_INC),
                    dims = new Coord2D(baseImage.getWidth(), baseImage.getHeight());

            layerButtons[i] = new ScrollableMenuElement(new SelectableListItemButton(pos, dims,
                    MenuElement.Anchor.LEFT_TOP, baseImage, highlightedImage, selectedImage,
                    i, () -> StippleEffect.get().getContext().getState().getLayerEditIndex(),
                    s -> StippleEffect.get().getContext().getState().setLayerEditIndex(s)
            ));

            final int index = i;

            // visibility toggle

            final Coord2D vtPos = pos.displace(Constants.LAYER_BUTTON_W +
                    Constants.BUTTON_OFFSET, Constants.STD_TEXT_BUTTON_H / 2);

            layerButtons[amount + i] =
                    new ScrollableMenuElement(generateVisibilityToggle(index, vtPos));

            // isolate layer

            final Coord2D ilPos = vtPos.displace(Constants.BUTTON_INC,
                    (int)(Constants.BUTTON_DIM * -0.5));

            layerButtons[(2 * amount) + i] = new ScrollableMenuElement(
                    GraphicsUtils.generateIconButton(IconCodes.ISOLATE_LAYER, ilPos, true,
                    () -> StippleEffect.get().getContext().isolateLayer(index)));

            // onion skin toggle

            final Coord2D onionPos = vtPos.displace(Constants.BUTTON_INC * 2, 0);

            layerButtons[(3 * amount) + i] =
                    new ScrollableMenuElement(generateOnionSkinToggle(index, onionPos));

            // frames linked toggle

            final Coord2D flPos = onionPos.displace(Constants.BUTTON_INC, 0);

            layerButtons[(4 * amount) + i] =
                    new ScrollableMenuElement(generateFramesLinkedToggle(index, flPos));

            // layer settings

            final Coord2D lsPos = ilPos.displace(Constants.BUTTON_INC * 3, 0);

            layerButtons[(5 * amount) + i] = new ScrollableMenuElement(
                    GraphicsUtils.generateIconButton(IconCodes.LAYER_SETTINGS,
                            lsPos, true,
                            () -> DialogAssembly.setDialogToLayerSettings(index)));

            realBottomY = pos.y + dims.y;
        }

        final int initialOffsetY = layerButtonYDisplacement(amount);

        mb.add(new VerticalScrollingMenuElement(firstPos,
                new Coord2D(Constants.VERT_SCROLL_WINDOW_W, Constants.VERT_SCROLL_WINDOW_H),
                layerButtons, realBottomY, initialOffsetY));

        return mb.build();
    }

    private static SimpleToggleMenuButton generateVisibilityToggle(
            final int index, final Coord2D pos
    ) {
        // 0: is enabled, button click should DISABLE; 1: vice-versa

        final GameImage enabled = GraphicsUtils.loadIcon(IconCodes.LAYER_ENABLED),
                disabled = GraphicsUtils.loadIcon(IconCodes.LAYER_DISABLED);

        return new SimpleToggleMenuButton(pos,
                new Coord2D(Constants.BUTTON_DIM, Constants.BUTTON_DIM),
                MenuElement.Anchor.LEFT_CENTRAL, true,
                new GameImage[] { enabled, disabled },
                new GameImage[] {
                        GraphicsUtils.highlightIconButton(enabled),
                        GraphicsUtils.highlightIconButton(disabled)
                },
                new Runnable[] {
                        () -> StippleEffect.get().getContext().disableLayer(index),
                        () -> StippleEffect.get().getContext().enableLayer(index)
                },
                () -> StippleEffect.get().getContext().getState()
                        .getLayers().get(index).isEnabled() ? 0 : 1,
                () -> {});
    }

    private static SimpleToggleMenuButton generateOnionSkinToggle(
            final int index, final Coord2D pos
    ) {
        final GameImage[]
                baseSet = Arrays.stream(OnionSkinMode.values())
                .map(osm -> GraphicsUtils.loadIcon(osm.getIconCode()))
                .toArray(GameImage[]::new),
                highlightedSet = Arrays.stream(baseSet).map(GraphicsUtils::highlightIconButton)
                        .toArray(GameImage[]::new);
        final Runnable[] behaviours = Arrays.stream(OnionSkinMode.values()).map(
                osm -> (Runnable) () -> {
                    final int nextIndex = (osm.ordinal() + 1) %
                            OnionSkinMode.values().length;
                    StippleEffect.get().getContext().getState()
                            .getLayers().get(index).setOnionSkinMode(
                                    OnionSkinMode.values()[nextIndex]);
                }).toArray(Runnable[]::new);

        return new SimpleToggleMenuButton(pos,
                new Coord2D(Constants.BUTTON_DIM, Constants.BUTTON_DIM),
                MenuElement.Anchor.LEFT_CENTRAL, true,
                baseSet, highlightedSet, behaviours,
                () -> StippleEffect.get().getContext().getState()
                        .getLayers().get(index).getOnionSkinMode().ordinal(),
                () -> {});
    }

    private static SimpleToggleMenuButton generateFramesLinkedToggle(
            final int index, final Coord2D pos
    ) {
        // 0: is unlinked, button click should LINK; 1: vice-versa

        final GameImage linked = GraphicsUtils.loadIcon(IconCodes.FRAMES_LINKED),
                unlinked = GraphicsUtils.loadIcon(IconCodes.FRAMES_UNLINKED);

        return new SimpleToggleMenuButton(pos,
                new Coord2D(Constants.BUTTON_DIM, Constants.BUTTON_DIM),
                MenuElement.Anchor.LEFT_CENTRAL, true,
                new GameImage[] { unlinked, linked },
                new GameImage[] {
                        GraphicsUtils.highlightIconButton(unlinked),
                        GraphicsUtils.highlightIconButton(linked)
                },
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
                Constants.LAYERS_ABOVE_TO_DISPLAY) + 1)) * Constants.STD_TEXT_BUTTON_INC;
    }

    private static void populateButtonsIntoBuilder(
            final MenuBuilder mb, final String[] iconIDs,
            final boolean[] preconditions, final Runnable[] behaviours,
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
                    .displace(Constants.SEGMENT_TITLE_BUTTON_OFFSET_X,
                            Constants.ICON_BUTTON_OFFSET_Y)
                    .displace(i * Constants.BUTTON_INC, 0);
            mb.add(GraphicsUtils.generateIconButton(iconIDs[i],
                    pos, preconditions[i], behaviours[i]));
        }
    }

    public static Menu buildColorsMenu() {
        final MenuBuilder mb = new MenuBuilder();

        populateButtonsIntoBuilder(
                mb, new String[] { IconCodes.SWAP_COLORS },
                new boolean[] { true },
                new Runnable[] { () -> StippleEffect.get().swapColors() },
                Constants.getColorsPosition()
        );

        final int NUM_COLORS = 2;

        for (int i = 0; i < NUM_COLORS; i++) {
            final int offsetY = Constants.getSegmentContentDisplacement().y;
            final Coord2D pos = Constants.getColorsPosition().displace(
                    (Constants.COLOR_PICKER_W / 4) +
                            (i * (Constants.COLOR_PICKER_W / 2)), offsetY);

            final ColorTextBox colorTextBox = ColorTextBox.make(pos, i);

            mb.add(colorTextBox);

            final int index = i;
            final Coord2D dims = new Coord2D(colorTextBox.getWidth(),
                    colorTextBox.getHeight());
            final GatewayMenuElement highlight = new GatewayMenuElement(
                    new StaticMenuElement(pos, dims, MenuElement.Anchor.CENTRAL_TOP,
                            GraphicsUtils.drawSelectedTextBox(
                                    new GameImage(dims.x, dims.y))),
                    () -> StippleEffect.get().getColorIndex() == index);
            mb.add(highlight);
        }

        mb.add(new ColorSelector());

        return mb.build();
    }

    public static Menu buildToolButtonMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        for (int i = 0; i < Constants.ALL_TOOLS.length; i++) {
            mb.add(toolButtonFromTool(Constants.ALL_TOOLS[i], i));
        }

        // zoom slider
        final float base = 2f;
        final HorizontalSlider zoomSlider = new HorizontalSlider(
                Constants.getBottomBarPosition().displace(Constants.ZOOM_SLIDER_X,
                        Constants.BUTTON_OFFSET), Constants.ZOOM_SLIDER_W,
                MenuElement.Anchor.LEFT_TOP,
                (int)(Math.log(Constants.MIN_ZOOM) / Math.log(base)),
                (int)(Math.log(Constants.MAX_ZOOM) / Math.log(base)),
                (int)(Math.log(StippleEffect.get().getContext().renderInfo
                        .getZoomFactor()) / Math.log(base)), i ->
                StippleEffect.get().getContext().renderInfo
                        .setZoomFactor((float)Math.pow(base, i)));
        zoomSlider.updateAssets();

        mb.add(zoomSlider);

        // outline button
        final Coord2D outlinePos = Constants.getToolsPosition()
                .displace(Constants.BUTTON_OFFSET,
                Constants.WORKSPACE_H - Constants.BUTTON_INC);

        final MenuElement outlineButton = GraphicsUtils.
                generateIconButton(IconCodes.OUTLINE, outlinePos,
                        true, DialogAssembly::setDialogToOutline);
        mb.add(outlineButton);

        // reflection buttons
        final MenuElement verticalReflectionButton = GraphicsUtils.
                generateIconButton(IconCodes.VERTICAL_REFLECTION,
                        outlinePos.displace(0, -Constants.BUTTON_INC),
                        c.getState().hasSelection(), () -> {
                    if (c.getState().getSelectionMode() == SelectionMode.BOUNDS)
                        c.reflectSelection(false);
                    else
                        c.reflectSelectionContents(false);
                        }
                );
        mb.add(verticalReflectionButton);
        final MenuElement horizontalReflectionButton = GraphicsUtils.
                generateIconButton(IconCodes.HORIZONTAL_REFLECTION,
                        outlinePos.displace(0, -2 * Constants.BUTTON_INC),
                        c.getState().hasSelection(), () -> {
                            if (c.getState().getSelectionMode() == SelectionMode.BOUNDS)
                                c.reflectSelection(true);
                            else
                                c.reflectSelectionContents(true);
                        }
                );
        mb.add(horizontalReflectionButton);

        // help button
        final GameImage helpIcon = GraphicsUtils.loadIcon(IconCodes.INFO),
                helpHighlighted = new GameImage(GraphicsUtils.HIGHLIGHT_OVERLAY);
        helpHighlighted.draw(helpIcon);

        final SimpleMenuButton helpButton = new SimpleMenuButton(
                Constants.getBottomBarPosition().displace(
                        Constants.CANVAS_W - Constants.BUTTON_DIM,
                        Constants.BUTTON_OFFSET), Constants.ICON_DIMS,
                MenuElement.Anchor.LEFT_TOP, true,
                DialogAssembly::setDialogToInfo,
                helpIcon, helpHighlighted.submit()
        );

        mb.add(helpButton);

        return mb.build();
    }

    private static SimpleMenuButton toolButtonFromTool(
            final Tool tool, final int index
    ) {
        final Coord2D position = Constants.getToolsPosition().displace(
                Constants.BUTTON_OFFSET,
                Constants.BUTTON_OFFSET + (Constants.BUTTON_INC * index)
        );

        return new SimpleMenuButton(position, Constants.ICON_DIMS,
                MenuElement.Anchor.LEFT_TOP, true, () -> StippleEffect.get().setTool(tool),
                StippleEffect.get().getTool().equals(tool) ? tool.getSelectedIcon() : tool.getIcon(),
                tool.getHighlightedIcon());
    }

}
