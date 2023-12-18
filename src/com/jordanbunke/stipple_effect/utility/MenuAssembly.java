package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.Menu;
import com.jordanbunke.delta_time.menus.MenuBuilder;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.OnionSkinMode;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.menu_elements.SelectableListItemButton;
import com.jordanbunke.stipple_effect.menu_elements.colors.ColorButton;
import com.jordanbunke.stipple_effect.menu_elements.colors.ColorSelector;
import com.jordanbunke.stipple_effect.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.menu_elements.scrollable.HorizontalScrollingMenuElement;
import com.jordanbunke.stipple_effect.menu_elements.scrollable.HorizontalSlider;
import com.jordanbunke.stipple_effect.menu_elements.scrollable.ScrollableMenuElement;
import com.jordanbunke.stipple_effect.menu_elements.scrollable.VerticalScrollingMenuElement;
import com.jordanbunke.stipple_effect.tools.*;

import java.util.Arrays;
import java.util.List;

public class MenuAssembly {

    private static final Tool[] ALL_TOOLS = new Tool[] {
            Hand.get(), Zoom.get(),
            StipplePencil.get(), Pencil.get(), Brush.get(), Eraser.get(),
            Fill.get(), ColorPicker.get(),
            Wand.get(), StippleSelect.get() // TODO - populate with remaining selection tools | , BoxSelect.get(), MoveSelection.get(), PickUpSelection.get()
    };

    public static Menu stub() {
        return new Menu();
    }

    public static Menu buildProjectsMenu() {
        final MenuBuilder mb = new MenuBuilder();

        final String[] iconIDs = new String[] {
                "new_project",
                "open_file",
                "save",
                "save_as",
                "resize",
                "pad"
        };

        final boolean[] preconditions = new boolean[] {
                true,
                true,
                true,
                true,
                true,
                true
        };

        final Runnable[] behaviours = new Runnable[] {
                DialogAssembly::setDialogToNewProject,
                () -> StippleEffect.get().openProject(),
                () -> StippleEffect.get().getContext().getProjectInfo().save(),
                DialogAssembly::setDialogToSave,
                DialogAssembly::setDialogToResize,
                DialogAssembly::setDialogToPad
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
                    .getProjectInfo().getFormattedName(true, true);
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
                if (StippleEffect.get().getContexts().get(index).getProjectInfo().hasUnsavedChanges()) {
                    DialogAssembly.setDialogToCheckCloseProject(index);
                } else {
                    StippleEffect.get().removeContext(index);
                }
            };

            projectElements[amount + i] = new ScrollableMenuElement(
                    GraphicsUtils.generateIconButton("close_project", cpPos,
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

        final String[] iconIDs = new String[] {
                "new_frame",
                "duplicate_frame",
                "remove_frame",
                "to_first_frame",
                "previous",
                Constants.ICON_ID_GAP_CODE, // gap for play/stop button
                "next",
                "to_last_frame"
        };

        final boolean[] preconditions = new boolean[] {
                StippleEffect.get().getContext().getState().canAddFrame(),
                StippleEffect.get().getContext().getState().canAddFrame(),
                StippleEffect.get().getContext().getState().canRemoveFrame(),
                true,
                true,
                false,
                true,
                true
        };

        final Runnable[] behaviours = new Runnable[] {
                () -> StippleEffect.get().getContext().addFrame(),
                () -> StippleEffect.get().getContext().duplicateFrame(),
                () -> StippleEffect.get().getContext().removeFrame(),
                () -> StippleEffect.get().getContext().getState().setFrameIndex(0),
                () -> StippleEffect.get().getContext().getState().previousFrame(),
                () -> {},
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
                Constants.SEGMENT_TITLE_BUTTON_OFFSET_X + (5 * Constants.BUTTON_INC),
                Constants.ICON_BUTTON_OFFSET_Y);

        mb.add(generatePlayStopToggle(playStopTogglePos));

        // playback speed slider and dynamic label for playback speed

        final Coord2D playbackSliderPos = firstPos.displace(
                Constants.FRAME_PLAYBACK_SLIDER_OFFSET_X,
                -Constants.SEGMENT_TITLE_CONTENT_OFFSET_Y + Constants.ICON_BUTTON_OFFSET_Y);

        final HorizontalSlider slider = new HorizontalSlider(playbackSliderPos,
                Constants.FRAME_PLAYBACK_SLIDER_W, MenuElement.Anchor.LEFT_TOP,
                Constants.MIN_MILLIS_PER_FRAME, Constants.MAX_MILLIS_PER_FRAME,
                StippleEffect.get().getContext().getPlaybackInfo().getMillisPerFrame(),
                mpf -> StippleEffect.get().getContext()
                        .getPlaybackInfo().setMillisPerFrame(mpf));
        slider.updateAssets();
        mb.add(slider);

        final Coord2D labelPos = Constants.getFramesPosition().displace(
                Constants.FRAMES_W - Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        mb.add(new DynamicLabel(labelPos,
                MenuElement.Anchor.RIGHT_TOP, Constants.WHITE,
                () -> {
                    final int MILLIS_PER_SECOND = 1000;

                    final double fps = (MILLIS_PER_SECOND /
                            (double) StippleEffect.get().getContext()
                                    .getPlaybackInfo().getMillisPerFrame());

                    return (fps < 10d ? ((int)(fps * 10)) / 10d : String.valueOf((int) fps)) + " fps";
                },
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

    private static SimpleToggleMenuButton generatePlayStopToggle(final Coord2D pos) {
        // 0: is playing, button click should STOP; 1: vice-versa

        final GameImage playing = GraphicsUtils.getIcon("stop"),
                notPlaying = GraphicsUtils.getIcon("play");

        return new SimpleToggleMenuButton(pos,
                new Coord2D(Constants.BUTTON_DIM, Constants.BUTTON_DIM),
                MenuElement.Anchor.LEFT_TOP, true,
                new GameImage[] { playing, notPlaying },
                new GameImage[] {
                        GraphicsUtils.highlightIconButton(playing),
                        GraphicsUtils.highlightIconButton(notPlaying)
                },
                new Runnable[] {
                        () -> StippleEffect.get().getContext().getPlaybackInfo().stop(),
                        () -> StippleEffect.get().getContext().getPlaybackInfo().play()
                },
                () -> StippleEffect.get().getContext().getPlaybackInfo()
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
                "new_layer",
                "duplicate_layer",
                "remove_layer",
                "move_layer_up",
                "move_layer_down",
                "merge_with_layer_below",
                "enable_all_layers"
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
                () -> StippleEffect.get().getContext().addLayer(),
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
        final int amount = layers.size(), elementsPerLayer = 7;

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

            // opacity slider

            final int MAX_OPACITY = 255, index = i;

            final Coord2D osPos = pos.displace(
                    Constants.LAYER_BUTTON_W + Constants.BUTTON_OFFSET,
                    Constants.STD_TEXT_BUTTON_H / 2);

            final HorizontalSlider opacitySlider = new HorizontalSlider(osPos,
                    Constants.LAYER_OPACITY_SLIDER_W, MenuElement.Anchor.LEFT_CENTRAL,
                    0, MAX_OPACITY,
                    (int)(StippleEffect.get().getContext().getState().getLayers()
                            .get(index).getOpacity() * MAX_OPACITY),
                    o -> StippleEffect.get().getContext()
                            .changeLayerOpacity(o / (double) MAX_OPACITY, index, false));

            opacitySlider.updateAssets();
            layerButtons[amount + i] = new ScrollableMenuElement(opacitySlider);

            // visibility toggle

            final Coord2D vtPos = osPos.displace(Constants.LAYER_OPACITY_SLIDER_W +
                    Constants.BUTTON_OFFSET, 0);

            layerButtons[(2 * amount) + i] =
                    new ScrollableMenuElement(generateVisibilityToggle(index, vtPos));

            // isolate layer

            final Coord2D ilPos = vtPos.displace(Constants.BUTTON_INC,
                    (int)(Constants.BUTTON_DIM * -0.5));

            layerButtons[(3 * amount) + i] = new ScrollableMenuElement(
                    GraphicsUtils.generateIconButton("isolate_layer", ilPos, true,
                    () -> StippleEffect.get().getContext().isolateLayer(index)));

            // onion skin toggle

            final Coord2D onionPos = vtPos.displace(Constants.BUTTON_INC * 2, 0);

            layerButtons[(4 * amount) + i] =
                    new ScrollableMenuElement(generateOnionSkinToggle(index, onionPos));

            // frames linked toggle

            final Coord2D flPos = onionPos.displace(Constants.BUTTON_INC, 0);

            layerButtons[(5 * amount) + i] =
                    new ScrollableMenuElement(generateFramesLinkedToggle(index, flPos));

            // layer settings

            final Coord2D lsPos = ilPos.displace(Constants.BUTTON_INC * 3, 0);

            layerButtons[(6 * amount) + i] = new ScrollableMenuElement(
                    GraphicsUtils.generateIconButton("settings", lsPos, true,
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

        final GameImage enabled = GraphicsUtils.getIcon("layer_enabled"),
                disabled = GraphicsUtils.getIcon("layer_disabled");

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
                .map(osm -> GraphicsUtils.getIcon("onion_skin_" +
                        osm.name().toLowerCase()))
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

        final GameImage linked = GraphicsUtils.getIcon("frames_linked"),
                unlinked = GraphicsUtils.getIcon("frames_unlinked");

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
                mb, new String[] { "swap_colors" }, new boolean[] { true },
                new Runnable[] { () -> StippleEffect.get().swapColors() }, Constants.getColorsPosition()
        );

        final int NUM_COLORS = 2;

        for (int i = 0; i < NUM_COLORS; i++) {
            final int width = Constants.STD_TEXT_BUTTON_W;
            final Coord2D pos = Constants.getColorsPosition().displace(
                    Constants.COLOR_PICKER_W - (Constants.TOOL_NAME_X +
                            ((NUM_COLORS - i) * (width + Constants.BUTTON_OFFSET))),
                    Constants.BUTTON_OFFSET);

            mb.add(new ColorButton(pos, i));
        }

        mb.add(ColorSelector.make());

        return mb.build();
    }

    public static Menu buildToolButtonMenu() {
        final MenuBuilder mb = new MenuBuilder();

        for (int i = 0; i < ALL_TOOLS.length; i++) {
            mb.add(toolButtonFromTool(ALL_TOOLS[i], i));
        }

        // zoom slider
        final float base = 2f;
        final HorizontalSlider zoomSlider = new HorizontalSlider(
                Constants.getBottomBarPosition().displace(Constants.ZOOM_SLIDER_X,
                        Constants.BUTTON_OFFSET), Constants.ZOOM_SLIDER_W,
                MenuElement.Anchor.LEFT_TOP,
                (int)(Math.log(Constants.MIN_ZOOM) / Math.log(base)),
                (int)(Math.log(Constants.MAX_ZOOM) / Math.log(base)),
                (int)(Math.log(StippleEffect.get().getContext().getRenderInfo()
                        .getZoomFactor()) / Math.log(base)), i ->
                StippleEffect.get().getContext().getRenderInfo()
                        .setZoomFactor((float)Math.pow(base, i)));
        zoomSlider.updateAssets();

        mb.add(zoomSlider);

        return mb.build();
    }

    private static SimpleMenuButton toolButtonFromTool(
            final Tool tool, final int index
    ) {
        final Coord2D position = Constants.getToolsPosition().displace(
                Constants.BUTTON_OFFSET,
                Constants.BUTTON_OFFSET + (Constants.BUTTON_INC * index)
        );

        return new SimpleMenuButton(position, Constants.TOOL_ICON_DIMS,
                MenuElement.Anchor.LEFT_TOP, true, () -> StippleEffect.get().setTool(tool),
                StippleEffect.get().getTool().equals(tool) ? tool.getSelectedIcon() : tool.getIcon(),
                tool.getHighlightedIcon());
    }

}
