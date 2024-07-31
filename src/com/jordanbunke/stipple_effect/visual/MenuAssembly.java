package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.Menu;
import com.jordanbunke.delta_time.menu.MenuBuilder;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.DropdownItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.dropdown.NestedItem;
import com.jordanbunke.delta_time.menu.menu_elements.ext.scroll.Scrollable;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.GatewayMenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.OnionSkinMode;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.project.PlaybackInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.project.ZoomLevel;
import com.jordanbunke.stipple_effect.selection.SelectionMode;
import com.jordanbunke.stipple_effect.tools.Tool;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.EnumUtils;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.action.ActionCodes;
import com.jordanbunke.stipple_effect.utility.action.SEAction;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.*;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.ColorSelector;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.ColorTextbox;
import com.jordanbunke.stipple_effect.visual.menu_elements.colors.PaletteColorButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.layout.VerticalPanelAdjuster;
import com.jordanbunke.stipple_effect.visual.menu_elements.navigation.Navbar;
import com.jordanbunke.stipple_effect.visual.menu_elements.navigation.logic.ThinkingActionItem;
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

import static com.jordanbunke.stipple_effect.utility.action.SEAction.*;

public class MenuAssembly {

    public static Menu stub() {
        return new Menu();
    }

    public static Menu buildProjectsMenu() {
        final MenuBuilder mb = new MenuBuilder();
        final StippleEffect s = StippleEffect.get();
        final SEContext c = s.getContext();

        mb.add(new Navbar(
                new NestedItem(StippleEffect.PROGRAM_NAME,
                        SETTINGS.toItem(c),
                        INFO.toItem(c),
                        AUTOMATION_SCRIPT.toItem(c),
                        EXIT_PROGRAM.toItem(c)),
                new NestedItem("File",
                        NEW_PROJECT.toItem(c),
                        OPEN_FILE.toItem(c),
                        SAVE.toItem(c),
                        SAVE_AS.toItem(c)),
                new NestedItem("Edit",
                        new NestedItem("State control",
                                UNDO.toItem(c),
                                GRANULAR_UNDO.toItem(c),
                                REDO.toItem(c),
                                GRANULAR_REDO.toItem(c),
                                HISTORY.toItem(c)),
                        new NestedItem("Sizing",
                                RESIZE.toItem(c),
                                PAD.toItem(c),
                                STITCH_SPLIT.toItem(c),
                                CROP_TO_SELECTION.toItem(c)),
                        new NestedItem("Clipboard",
                                Arrays.stream(clipboardActions())
                                        .map(a -> a.toItem(c))
                                        .toArray(DropdownItem[]::new))),
                new NestedItem("Layer", Arrays.stream(layerActions())
                        .map(a -> a.toItem(c))
                        .toArray(DropdownItem[]::new)),
                new NestedItem("Frame", Arrays.stream(frameActions())
                        .map(a -> a.toItem(c))
                        .toArray(DropdownItem[]::new)),
                new NestedItem("Selection",
                        new NestedItem("Modify selection",
                                Arrays.stream(selectionModificationActions())
                                        .map(a -> a.toItem(c))
                                        .toArray(DropdownItem[]::new)),
                        new NestedItem("Operate on selection",
                                FILL_PRIMARY.toItem(c),
                                FILL_SECONDARY.toItem(c),
                                DELETE_SELECTION_CONTENTS.toItem(c)),
                        new NestedItem("Reflection",
                                Arrays.stream(reflectionActions())
                                        .map(a -> a.toItem(c))
                                        .toArray(DropdownItem[]::new)),
                        new NestedItem("Outline",
                                CONFIGURE_OUTLINE.toItem(c),
                                LAST_OUTLINE.toItem(c),
                                SINGLE_OUTLINE.toItem(c),
                                DOUBLE_OUTLINE.toItem(c))),
                new NestedItem("View",
                        PREVIEW.toItem(c),
                        new NestedItem("Grid and checkerboard",
                                new ThinkingActionItem(c,
                                        anon -> anon.renderInfo.isPixelGridOn()
                                                ? HIDE_PIXEL_GRID : SHOW_PIXEL_GRID,
                                        SHOW_PIXEL_GRID, HIDE_PIXEL_GRID),
                                new ThinkingActionItem(c,
                                        anon -> anon.getState().hasSelection()
                                                ? SET_PIXEL_GRID_SELECTION
                                                : SET_PIXEL_GRID_CANVAS,
                                        SET_PIXEL_GRID_SELECTION,
                                        SET_PIXEL_GRID_CANVAS)),
                        new NestedItem("Layout",
                                PANEL_MANAGER.toItem(c),
                                new ThinkingActionItem(c,
                                        na -> Layout.areAllPanelsShowing()
                                                ? MINIMAL_UI : ALL_UI,
                                        ALL_UI, MINIMAL_UI))),
                new NestedItem("Actions", Arrays.stream(actionsMenuActions())
                        .map(a -> a.toItem(c))
                        .toArray(DropdownItem[]::new)),
                new NestedItem("Palette",
                        NEW_PALETTE.toItem(c),
                        IMPORT_PALETTE.toItem(c),
                        DELETE_PALETTE.toItem(c),
                        SAVE_PALETTE.toItem(c),
                        SORT_PALETTE.toItem(c),
                        PALETTE_SETTINGS.toItem(c))));

        // panel expand / collapse
        final Coord2D panelIconPos = Layout.getProjectsPosition().displace(
                Layout.getProjectsWidth() - Layout.BUTTON_INC,
                Layout.ICON_BUTTON_OFFSET_Y);

        if (!Layout.isProjectsExpanded())
            mb.add(IconButton.make(ActionCodes.EXPAND_PANEL, panelIconPos,
                    () -> Layout.adjustPanels(() -> Layout.setProjectsExpanded(true))));
        else
            mb.add(IconButton.make(ActionCodes.COLLAPSE_PANEL, panelIconPos,
                    () -> Layout.adjustPanels(() -> Layout.setProjectsExpanded(false))));

        // early break if collapsed
        if (!Layout.isProjectsExpanded())
            return mb.build();

        // project previews

        final int amount = StippleEffect.get().getContexts().size(),
                selectedIndex = StippleEffect.get().getContextIndex();

        final MenuBuilder toScroll = new MenuBuilder();

        final Coord2D firstPos = Layout.getProjectsPosition()
                .displace(Layout.getPanelContentDisplacement());
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

            toScroll.add(IconButton.make(ActionCodes.CLOSE_PROJECT, cpPos, closeBehaviour));

            cumulativeWidth += offsetX;
            realRightX = cpPos.x + Layout.BUTTON_DIM;

            if (i == selectedIndex - Layout.PROJECTS_BEFORE_TO_DISPLAY)
                initialOffsetX = pos.x - firstPos.x;
        }

        final int scrollBoxW = Layout.getProjectScrollWindowWidth();
        final boolean requiresScrolling = realRightX > firstPos.x + scrollBoxW;
        final int scrollBoxH = Layout.TOP_PANEL_SCROLL_WINDOW_H -
                (requiresScrolling ? 0 : Layout.SLIDER_BALL_DIM);
        Layout.setProjectsRequiresScrolling(requiresScrolling);

        mb.add(new HorizontalScrollBox(firstPos,
                new Bounds2D(scrollBoxW, scrollBoxH),
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

        final int panelHeight = Layout.getFlipbookHeight(),
                panelWidth = Layout.getFlipbookWidth(),
                lbLeftBuffer = (2 * Layout.BUTTON_INC) - Layout.CONTENT_BUFFER_PX,
                lbTopBuffer = 3 * Layout.STD_TEXT_BUTTON_INC,
                fbOffsetFromLB = lbLeftBuffer + Layout.VERT_SCROLL_WINDOW_W +
                        Layout.CONTENT_BUFFER_PX;

        addHidePanelToMenuBuilder(mb,
                panelPos.displace(panelWidth, 0),
                () -> Layout.setFlipbookPanelShowing(false));

        final Coord2D firstFBPos = panelPos.displace(fbOffsetFromLB,
                Layout.PANEL_TITLE_CONTENT_OFFSET_Y),
                firstLBPos = panelPos.displace(lbLeftBuffer, lbTopBuffer);

        // aesthetic box
        final int abX = firstLBPos.x, abY = firstFBPos.y,
                abInPanelX = abX - panelPos.x, abInPanelY = abY - panelPos.y,
                abWidth = panelWidth - (abInPanelX + Layout.CONTENT_BUFFER_PX),
                abHeight = panelHeight - (abInPanelY + Layout.CONTENT_BUFFER_PX);
        mb.add(new VerticalScrollBox(
                new Coord2D(abX, abY), new Bounds2D(abWidth, abHeight),
                new Scrollable[0], abY + abHeight, 0));

        // playback
        final Coord2D labelPos = panelPos.displace(0,
                Layout.PANEL_TITLE_CONTENT_OFFSET_Y);

        final TextLabel playbackLabel = TextLabel.make(labelPos, "");
        final IncrementalRangeElements<Integer> playback =
                IncrementalRangeElements.makeForInt(playbackLabel,
                        labelPos.y + Layout.ICON_BUTTON_OFFSET_Y,
                        labelPos.y + Layout.TEXT_Y_OFFSET, 1,
                        Constants.MIN_PLAYBACK_FPS, Constants.MAX_PLAYBACK_FPS,
                        c.playbackInfo::setFps, c.playbackInfo::getFps,
                        fps -> fps, fps -> fps, fps -> fps + " FPS",
                        "XXX FPS");
        mb.addAll(playbackLabel, playback.decButton, playback.incButton,
                playback.slider, playback.value);

        // layer controls
        populateButtonsIntoBuilder(mb, new SEAction[] {
                NEW_LAYER, DUPLICATE_LAYER, REMOVE_LAYER,
                MOVE_LAYER_UP, MOVE_LAYER_DOWN, MERGE_WITH_LAYER_BELOW
        }, c, panelPos.displace(Layout.CONTENT_BUFFER_PX,
                lbTopBuffer + Layout.BUTTON_OFFSET), true);

        // playback controls
        populateButtonsIntoBuilder(mb, new SEAction[] {
                TO_FIRST_FRAME, PREVIOUS_FRAME, DUMMY,
                NEXT_FRAME, TO_LAST_FRAME
        }, c, panelPos.displace(Layout.CONTENT_BUFFER_PX,
                Layout.ICON_BUTTON_OFFSET_Y), false);

        final int PLAY_STOP_INDEX = 2,
                PLAYBACK_MODE_INDEX = 5;

        // play/stop as toggle
        final Coord2D disp = new Coord2D(Layout.BUTTON_INC, 0),
                playStopTogglePos =
                        panelPos.displace(Layout.CONTENT_BUFFER_PX +
                                        (PLAY_STOP_INDEX * Layout.BUTTON_INC),
                                Layout.ICON_BUTTON_OFFSET_Y);
        mb.add(generatePlayStopToggle(c, playStopTogglePos));

        // playback mode toggle button
        final Coord2D playbackModeTogglePos = playStopTogglePos.displace(
                disp.scale(PLAYBACK_MODE_INDEX - PLAY_STOP_INDEX));
        mb.add(generatePlaybackModeToggle(c, playbackModeTogglePos));

        // frame controls
        final Coord2D frameControlPos = panelPos.displace(fbOffsetFromLB, 0);
        populateButtonsIntoBuilder(mb, new SEAction[] {
                NEW_FRAME, DUPLICATE_FRAME, REMOVE_FRAME,
                MOVE_FRAME_BACK, MOVE_FRAME_FORWARD, DUMMY,
                FRAME_PROPERTIES
        }, c, frameControlPos.displace(0, Layout.ICON_BUTTON_OFFSET_Y), false);

        // layer buttons
        final List<SELayer> layers = c.getState().getLayers();
        final int layerCount = layers.size();
        final Scrollable[] layerButtons = new Scrollable[layerCount];

        final MenuBuilder layerElements = new MenuBuilder();

        int realBottomY = firstLBPos.y;

        for (int i = layerCount - 1; i >= 0; i--) {
            final SELayer layer = layers.get(i);

            final String name = layer.getName(),
                    text = name.length() > Layout.LAYER_NAME_LENGTH_CUTOFF
                            ? name.substring(0, Layout.LAYER_NAME_LENGTH_CUTOFF) + "..."
                            : name;

            final Coord2D pos = firstLBPos.displace(0,
                    (layerCount - (i + 1)) * Layout.STD_TEXT_BUTTON_INC);

            layerButtons[i] = new Scrollable(SelectableListItemButton.make(
                    pos, Layout.LAYER_BUTTON_W, text, i,
                    () -> c.getState().getLayerEditIndex(),
                    l -> c.getState().setLayerEditIndex(l)));
            layerElements.add(layerButtons[i]);

            // visibility toggle
            final Coord2D vtPos = pos.displace(
                    Layout.LAYER_BUTTON_W + Layout.BUTTON_OFFSET,
                    (Layout.STD_TEXT_BUTTON_H / 2)  - (Layout.BUTTON_DIM / 2));
            layerElements.add(new LayerVisibilityButton(vtPos, i));

            // frames linked toggle
            final Coord2D flPos = vtPos.displace(Layout.BUTTON_INC, 0);
            layerElements.add(generateFramesLinkedToggle(i, flPos));

            // onion skin toggle
            final Coord2D onionPos = vtPos.displace(Layout.BUTTON_INC * 2, 0);
            layerElements.add(generateOnionSkinToggle(i, onionPos));

            // layer settings
            final int index = i;
            final Coord2D lsPos = vtPos.displace(Layout.BUTTON_INC * 3, 0);
            layerElements.add(IconButton.make(ActionCodes.LAYER_SETTINGS, lsPos,
                    () -> DialogAssembly.setDialogToLayerSettings(index)));

            realBottomY = pos.y + Layout.STD_TEXT_BUTTON_H;
        }

        final int boxInPanelY = firstLBPos.y - panelPos.y,
                lbBoxHeight = panelHeight -
                        (boxInPanelY + Layout.CONTENT_BUFFER_PX);
        final VerticalScrollBox lbBox = new VerticalScrollBox(firstLBPos,
                new Bounds2D(Layout.VERT_SCROLL_WINDOW_W, lbBoxHeight),
                Arrays.stream(layerElements.build().getMenuElements())
                        .map(m -> m instanceof Scrollable
                                ? (Scrollable) m
                                : new Scrollable(m))
                        .toArray(Scrollable[]::new), realBottomY,
                layerButtonYDisplacement(c, layerCount), GameImage::new);

        // frame buttons
        final int frameCount = c.getState().getFrameCount();
        final Scrollable[] frameButtons = new Scrollable[frameCount];

        Coord2D fbPos = firstFBPos;

        for (int i = 0; i < frameCount; i++) {
            frameButtons[i] = new Scrollable(SelectableListItemButton.make(
                    fbPos, Layout.FRAME_BUTTON_W, String.valueOf(i + 1),
                    i, () -> c.getState().getFrameIndex(),
                    f -> c.getState().setFrameIndex(f)));

            fbPos = fbPos.displace(Layout.FRAME_BUTTON_W +
                    (i + 1 < frameCount ? Layout.BUTTON_OFFSET : 0), 0);
        }

        final int boxInPanelX = firstFBPos.x - panelPos.x,
                fbBoxWidth = panelWidth -
                        (boxInPanelX + Layout.CONTENT_BUFFER_PX);
        final HorizontalScrollBox fbBox = new HorizontalScrollBox(firstFBPos,
                new Bounds2D(fbBoxWidth, Layout.TOP_PANEL_SCROLL_WINDOW_H),
                frameButtons, fbPos.x, frameButtonXDisplacement(c),
                GameImage::new);
        mb.addAll(lbBox, fbBox);

        // cel buttons
        for (int l = 0; l < layerCount; l++)
            for (int f = 0; f < frameCount; f++)
                mb.add(new CelButton(c, l, f,
                        layerButtons[l], frameButtons[f], lbBox, fbBox));

        // panel adjuster
        mb.add(new VerticalPanelAdjuster(panelPos, panelWidth,
                Layout.getFlipbookUpLeeway(),
                Layout.getFlipbookDownLeeway(), dh -> {
            Layout.changeFlipbookHeight(dh);
            StippleEffect.get().rebuildAllMenus();
        }));

        return mb.build();
    }

    private static SimpleToggleMenuButton generatePlaybackModeToggle(
            final SEContext c, final Coord2D pos
    ) {
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
                () -> c.playbackInfo.getMode().buttonIndex(),
                c.playbackInfo::toggleMode);
    }

    private static SimpleToggleMenuButton generatePlayStopToggle(
            final SEContext c, final Coord2D pos
    ) {
        // 0: is playing, button click should STOP; 1: vice-versa
        final String[] codes = new String[]
                { ActionCodes.STOP, ActionCodes.PLAY };

        return IconToggleButton.make(pos, codes, new Runnable[] {
                c.playbackInfo::stop, c.playbackInfo::play
        }, () -> c.playbackInfo.isPlaying() ? 0 : 1, () -> {});
    }

    private static int frameButtonXDisplacement(final SEContext c) {
        return (c.getState().getFrameIndex() -
                Layout.FRAMES_BEFORE_TO_DISPLAY) *
                (Layout.FRAME_BUTTON_W + Layout.BUTTON_OFFSET);
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
                ActionCodes.FRAMES_UNLINKED,
                ActionCodes.FRAMES_LINKED
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

    private static int layerButtonYDisplacement(
            final SEContext c, final int amount
    ) {
        return (amount - ((c.getState().getLayerEditIndex() +
                Layout.LAYERS_ABOVE_TO_DISPLAY) + 1)) *
                Layout.STD_TEXT_BUTTON_INC;
    }

    private static void populateButtonsIntoBuilder(
            final MenuBuilder mb, final SEAction[] actions,
            final SEContext c, final Coord2D firstPos, final boolean vertical
    ) {
        final Coord2D displacement = vertical
                ? new Coord2D(0, Layout.BUTTON_INC)
                : new Coord2D(Layout.BUTTON_INC, 0);
        Coord2D pos = firstPos;

        for (SEAction action : actions) {
            if (!action.code.equals(ActionCodes.NONE))
                mb.add(GraphicsUtils.generateIconButton(pos, action, c));

            pos = pos.displace(displacement);
        }
    }

    // TODO - remove
    private static void populateButtonsIntoBuilder(
            final MenuBuilder mb, final String[] iconIDs,
            final Supplier<Boolean>[] preconditions,
            final Runnable[] behaviours, final Coord2D pos
    ) {
        populateButtonsIntoBuilder(mb, iconIDs,
                preconditions, behaviours, pos, false);
    }

    // TODO - remove
    private static void populateButtonsIntoBuilder(
            final MenuBuilder mb, final String[] iconIDs,
            final Supplier<Boolean>[] preconditions, final Runnable[] behaviours,
            final Coord2D firstPos, final boolean vertical
    ) {
        if (iconIDs.length != preconditions.length || iconIDs.length != behaviours.length) {
            GameError.send("Lengths of button assembly argument arrays did not match; " +
                    "buttons were not populated into menu builder.");
            return;
        }

        final Coord2D displacement = vertical
                ? new Coord2D(0, Layout.BUTTON_INC)
                : new Coord2D(Layout.BUTTON_INC, 0);
        Coord2D pos = firstPos;

        for (int i = 0; i < iconIDs.length; i++) {
            if (!iconIDs[i].equals(ActionCodes.NONE))
                mb.add(GraphicsUtils.generateIconButton(iconIDs[i],
                        pos, preconditions[i], behaviours[i]));

            pos = pos.displace(displacement);
        }
    }

    @SafeVarargs
    private static Supplier<Boolean>[] getPreconditions(
            final Supplier<Boolean>... preconditions
    ) {
        return preconditions;
    }

    public static Menu buildColorsMenu() {
        final MenuBuilder mb = new MenuBuilder();

        mb.add(TextLabel.make(Layout.getColorsPosition().displace(
                Layout.CONTENT_BUFFER_PX, Layout.TEXT_Y_OFFSET),
                "Colors"));

        // TODO: remove
        populateButtonsIntoBuilder(
                mb, new String[] {
                        ActionCodes.SWAP_COLORS,
                        ActionCodes.COLOR_MENU_MODE
                },
                getPreconditions(
                        () -> true,
                        () -> true
                ),
                new Runnable[] {
                        StippleEffect.get()::swapColors,
                        StippleEffect.get()::toggleColorMenuMode
                },
                Layout.getColorsPosition()
        );

        addHidePanelToMenuBuilder(mb, Layout.getColorsPosition()
                .displace(Layout.getColorsWidth(), 0),
                () -> Layout.setColorsPanelShowing(false));

        final int NUM_COLORS = 2;

        for (int i = 0; i < NUM_COLORS; i++) {
            final int offsetY = Layout.getPanelContentDisplacement().y * 2;
            final Coord2D labelPos = Layout.getColorsPosition().displace(
                    Layout.getPanelContentDisplacement()).displace(
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
        mb.add(IconButton.make(ActionCodes.HIDE_PANEL, pos,
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
                        ActionCodes.NEW_PALETTE,
                        ActionCodes.IMPORT_PALETTE,
                        ActionCodes.CONTENTS_TO_PALETTE,
                        ActionCodes.DELETE_PALETTE,
                        ActionCodes.SAVE_PALETTE,
                        ActionCodes.SORT_PALETTE,
                        ActionCodes.PALETTIZE,
                        ActionCodes.PALETTE_SETTINGS
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
                                s.getContext(), s.getSelectedPalette()),
                        s::deletePalette,
                        () -> DialogAssembly.setDialogToSavePalette(
                                s.getSelectedPalette()),
                        () -> DialogAssembly.setDialogToSortPalette(
                                s.getSelectedPalette()),
                        () -> DialogAssembly.setDialogToPalettize(
                                s.getContext(), s.getSelectedPalette()),
                        () -> DialogAssembly.setDialogToPaletteSettings(
                                s.getSelectedPalette())
                }, paletteOptionsRef);
        populateButtonsIntoBuilder(
                mb, new String[] {
                        ActionCodes.ADD_TO_PALETTE,
                        ActionCodes.REMOVE_FROM_PALETTE,
                        ActionCodes.MOVE_LEFT_IN_PALETTE,
                        ActionCodes.MOVE_RIGHT_IN_PALETTE
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
                Layout.getPanelContentDisplacement().y + Layout.BUTTON_INC);
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
                generateIconButton(outlinePos, CONFIGURE_OUTLINE, c);
        mb.add(outlineButton);

        // TODO: use thinking elements to update icons
        // reflection buttons
        final MenuElement verticalReflectionButton = GraphicsUtils.
                generateIconButton(ActionCodes.VERTICAL_REFLECTION,
                        outlinePos.displace(0, -Layout.BUTTON_INC),
                        () -> c.getState().hasSelection(), () -> {
                            if (c.getState().getSelectionMode() == SelectionMode.BOUNDS)
                                VERT_BOUNDS_REFLECTION.behaviour.accept(c);
                            else
                                VERT_CONTENTS_REFLECTION.behaviour.accept(c);
                        }
                );
        mb.add(verticalReflectionButton);
        final MenuElement horizontalReflectionButton = GraphicsUtils.
                generateIconButton(ActionCodes.HORIZONTAL_REFLECTION,
                        outlinePos.displace(0, -2 * Layout.BUTTON_INC),
                        () -> c.getState().hasSelection(), () -> {
                            if (c.getState().getSelectionMode() == SelectionMode.BOUNDS)
                                HORZ_BOUNDS_REFLECTION.behaviour.accept(c);
                            else
                                HORZ_CONTENTS_REFLECTION.behaviour.accept(c);
                        });
        mb.add(horizontalReflectionButton);

        // pixel grid
        final MenuElement pixelGridToggleButton = GraphicsUtils
                .generateIconToggleButton(
                        outlinePos.displace(0, -3 * Layout.BUTTON_INC),
                        new String[] {
                                ActionCodes.PIXEL_GRID_OFF,
                                ActionCodes.PIXEL_GRID_ON
                        },
                        new Runnable[] {
                                () -> c.renderInfo.setPixelGrid(true),
                                () -> c.renderInfo.setPixelGrid(false)
                        },
                        () -> c.renderInfo.isPixelGridOn() ? 1 : 0, () -> {},
                        c::couldRenderPixelGrid, ActionCodes.PIXEL_GRID_OFF);
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
                ActionCodes.IND_TOOL);
        final DynamicLabel toolLabel = DynamicLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(toolIndicator, false),
                bottomBarTextY),
                () -> StippleEffect.get().getTool().getBottomBarText(),
                Layout.getBottomBarToolWidth());
        mb.addAll(toolIndicator, toolLabel);

        // target pixel
        final Indicator targetIndicator = new Indicator(new Coord2D(
                Layout.getBottomBarTargetPixelX(), bottomBarButtonY),
                ActionCodes.IND_TARGET);
        final DynamicLabel targetLabel = DynamicLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(targetIndicator, false),
                bottomBarTextY),
                c::getTargetPixelText, Layout.getBottomBarTargetPixelWidth());
        mb.addAll(targetIndicator, targetLabel);

        // canvas size
        final Indicator boundsIndicator = new Indicator(new Coord2D(
                Layout.getBottomBarCanvasSizeX(), bottomBarButtonY),
                ActionCodes.IND_BOUNDS);
        final DynamicLabel boundsLabel = DynamicLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(boundsIndicator, false),
                bottomBarTextY),
                c::getImageSizeText, Layout.getBottomBarCanvasSizeWidth());
        mb.addAll(boundsIndicator, boundsLabel);

        // zoom
        final Indicator zoomIndicator = new Indicator(
                new Coord2D(Layout.getBottomBarZoomPercentageX(),
                        bottomBarButtonY), ActionCodes.IND_ZOOM);
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
