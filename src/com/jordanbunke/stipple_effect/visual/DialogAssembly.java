package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.menus.Menu;
import com.jordanbunke.delta_time.menus.MenuBuilder;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.menus.menu_elements.invisible.PlaceholderMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.invisible.ThinkingMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.invisible.TimedMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.visual.AnimationMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.visual.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.DynamicTextButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.dialog.ApproveDialogButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.dialog.TextBox;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalSlider;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.ScrollableMenuElement;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollingMenuElement;
import com.jordanbunke.stipple_effect.project.ProjectInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Outliner;
import com.jordanbunke.stipple_effect.tools.Tool;
import com.jordanbunke.stipple_effect.utility.*;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DialogAssembly {
    public static void setDialogToSave() {
        final SEContext c = StippleEffect.get().getContext();

        // text labels
        final TextLabel
                folderLabel = makeDialogLeftLabel(0, "Folder: "),
                nameLabel = makeDialogLeftLabel(1, "Name: "),
                scaleUpLabel = makeDialogLeftLabel(2, "Scale factor: "),
                saveAsTypeLabel = makeDialogLeftLabel(3, "Save as: "),
                xDivsLabel = makeDialogLeftLabel(4, "X frames: "),
                yDivsLabel = makeDialogRightLabel(xDivsLabel, "Y frames: "),
                indexPrefixLabel = makeDialogLeftLabel(4, "Prefix: "),
                indexSuffixLabel = makeDialogRightLabel(indexPrefixLabel, "Suffix: "),
                countFromLabel = makeDialogLeftLabel(5, "Count from: "),
                fpsLabel = makeDialogLeftLabel(4, "Frame rate: ");

        // folder selection button
        final DynamicTextButton folderButton = new DynamicTextButton(
                getDialogContentOffsetFromLabel(folderLabel),
                Layout.getDialogContentWidthAllowance(),
                MenuElement.Anchor.LEFT_TOP,
                () -> {
                    FileIO.setDialogToFoldersOnly();
                    final Optional<File> opened = FileIO.openFileFromSystem();

                    if (opened.isEmpty())
                        return;

                    final Path folder = opened.get().toPath();
                    c.projectInfo.setFolder(folder);
                },
                () -> {
                    final StringBuilder folderPathName = new StringBuilder();
                    final String ELLIPSE = "...";

                    Path folder = c.projectInfo.getFolder();
                    int placements = 0;

                    if (folder == null)
                        return Constants.NO_FOLDER_SELECTED;

                    do {
                        final Path filename = folder.getFileName();

                        final String level = filename != null
                                ? filename.toString()
                                : folder.getRoot().toString();

                        if (placements == 0)
                            folderPathName.insert(0, level);
                        else if (folderPathName.length() + File.separator.length() +
                                level.length() <= Constants.MAX_NAME_LENGTH) {
                            folderPathName.insert(0, File.separator);
                            folderPathName.insert(0, level);
                        } else {
                            folderPathName.insert(0, File.separator);
                            folderPathName.insert(0, ELLIPSE);
                            break;
                        }

                        placements++;
                        folder = folder.getParent();
                    } while (folder != null);

                    return folderPathName.toString();
                });

        // name text box
        final TextBox nameTextBox = makeDialogNameTextBox(nameLabel,
                c.projectInfo.getName(), c.projectInfo::setName);

        // scale up slider
        final HorizontalSlider scaleUpSlider = new HorizontalSlider(
                getDialogContentOffsetFromLabel(scaleUpLabel),
                Layout.getDialogContentWidthAllowance(), MenuElement.Anchor.LEFT_TOP,
                Constants.MIN_SCALE_UP, Constants.MAX_SCALE_UP,
                c.projectInfo.getScaleUp(),
                c.projectInfo::setScaleUp);
        scaleUpSlider.updateAssets();

        final DynamicLabel scaleUpValue = makeDynamicFromLeftLabel(
                scaleUpLabel, () -> c.projectInfo.getScaleUp() + "x");

        // save as toggle
        final ProjectInfo.SaveType[] saveOptions = ProjectInfo.SaveType.validOptions();

        final int toggleWidth = Layout.getDialogContentWidthAllowance();

        final GameImage[] baseSet = Arrays.stream(saveOptions)
                .map(so -> GraphicsUtils.drawTextButton(toggleWidth,
                        so.getButtonText(), false, Constants.GREY))
                .toArray(GameImage[]::new),
                highlightedSet = Arrays.stream(baseSet).map(GraphicsUtils::drawHighlightedButton)
                        .toArray(GameImage[]::new);

        final Runnable[] behaviours = new Runnable[saveOptions.length];

        for (int i = 0; i < saveOptions.length; i++) {
            final ProjectInfo.SaveType next =
                    saveOptions[(i + 1) % saveOptions.length];

            behaviours[i] = () -> c.projectInfo.setSaveType(next);
        }

        final Supplier<Integer> updateIndexLogic = () -> {
            final ProjectInfo.SaveType saveType = StippleEffect.get()
                    .getContext().projectInfo.getSaveType();

            for (int i = 0; i < saveOptions.length; i++)
                if (saveType == saveOptions[i])
                    return i;

            return 0;
        };

        final SimpleToggleMenuButton saveAsToggle = new SimpleToggleMenuButton(
                getDialogContentOffsetFromLabel(saveAsTypeLabel),
                new Coord2D(toggleWidth, Layout.STD_TEXT_BUTTON_H),
                MenuElement.Anchor.LEFT_TOP, true, baseSet,
                highlightedSet, behaviours, updateIndexLogic, () -> {});

        // frameDims iff saveType is PNG_STITCHED
        final TextBox xDivsTextBox = DialogAssembly.makeDialogNumericalTextBox(xDivsLabel,
                c.projectInfo.getFrameDimsX(), 1, Constants.MAX_NUM_FRAMES,
                "", c.projectInfo::setFrameDimsX, 3);
        final TextBox yDivsTextBox = DialogAssembly.makeDialogNumericalTextBox(yDivsLabel,
                c.projectInfo.getFrameDimsY(), 1, Constants.MAX_NUM_FRAMES,
                "", c.projectInfo::setFrameDimsY, 3);

        final MenuElementGrouping pngStitchedContents = new MenuElementGrouping(
                xDivsLabel, yDivsLabel, xDivsTextBox, yDivsTextBox);

        // GIF playback speed iff saveType is GIF
        final HorizontalSlider playbackSpeedSlider = new HorizontalSlider(
                getDialogContentOffsetFromLabel(fpsLabel),
                (int)(Layout.getDialogContentWidthAllowance() * 0.9),
                MenuElement.Anchor.LEFT_TOP,
                Constants.MIN_PLAYBACK_FPS, Constants.MAX_PLAYBACK_FPS,
                c.projectInfo.getFps(),
                c.projectInfo::setFps);
        playbackSpeedSlider.updateAssets();

        final DynamicLabel fpsValue = makeDynamicFromLeftLabel(
                fpsLabel, () -> c.projectInfo.getFps() + " fps");

        final MenuElementGrouping gifContents = new MenuElementGrouping(
                fpsLabel, playbackSpeedSlider, fpsValue
        );

        // Extra file naming options IFF saveType is PNG_SEPARATE
        final TextBox indexPrefixTextBox = makeDialogCustomTextBox(
                indexPrefixLabel, Layout.SMALL_TEXT_BOX_W,
                DialogAssembly::getDialogContentOffsetFromLabel,
                () -> "", c.projectInfo.getIndexPrefix(), () -> "",
                TextBox::validateAsOptionallyEmptyFilename,
                c.projectInfo::setIndexPrefix, 5);
        final TextBox indexSuffixTextBox = makeDialogCustomTextBox(
                indexSuffixLabel, Layout.SMALL_TEXT_BOX_W,
                DialogAssembly::getDialogContentOffsetFromLabel,
                () -> "", c.projectInfo.getIndexSuffix(), () -> "",
                TextBox::validateAsOptionallyEmptyFilename,
                c.projectInfo::setIndexSuffix, 5);
        final TextBox countFromTextBox = makeDialogCustomTextBox(
                countFromLabel, Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                DialogAssembly::getDialogContentOffsetFromLabel,
                c.projectInfo::getIndexPrefix,
                String.valueOf(c.projectInfo.getCountFrom()),
                c.projectInfo::getIndexSuffix,
                TextBox.getIntTextValidator(0, Constants.ARBITRARY_MAX_COUNT),
                s -> c.projectInfo.setCountFrom(Integer.parseInt(s)), 4);

        final MenuElementGrouping pngSeparateContents = new MenuElementGrouping(
                indexPrefixLabel, indexPrefixTextBox,
                indexSuffixLabel, indexSuffixTextBox,
                countFromLabel, countFromTextBox);

        // save type decision maker

        final ThinkingMenuElement basedOnSaveType = new ThinkingMenuElement(() ->
                switch (c.projectInfo.getSaveType()) {
                    case PNG_STITCHED -> c.getState().getFrameCount() > 1
                            ? pngStitchedContents : new PlaceholderMenuElement();
                    case PNG_SEPARATE -> pngSeparateContents;
                    case GIF, MP4 -> gifContents;
                    default -> new PlaceholderMenuElement();
        });

        // content assembly
        final MenuElementGrouping contents = new MenuElementGrouping(
                folderLabel, nameLabel, scaleUpLabel, saveAsTypeLabel,
                folderButton, nameTextBox, scaleUpSlider, scaleUpValue,
                saveAsToggle, basedOnSaveType);
        setDialog(assembleDialog("Save Project...", contents,
                () -> {
            if (c.projectInfo.getSaveType() == ProjectInfo.SaveType.PNG_STITCHED) {
                final boolean enoughFrames = c.projectInfo.getFrameDimsX() *
                        c.projectInfo.getFrameDimsY() >= c.getState().getFrameCount();
                return c.projectInfo.hasSaveAssociation() &&
                        xDivsTextBox.isValid() && yDivsTextBox.isValid() &&
                        enoughFrames;
            }

            return c.projectInfo.hasSaveAssociation();
            }, "Save", c.projectInfo::save, true));
    }

    public static void setDialogToResize() {
        final SEContext c = StippleEffect.get().getContext();

        final int w = c.getState().getImageWidth(),
                h = c.getState().getImageHeight();

        DialogVals.setResizeWidth(w);
        DialogVals.setResizeHeight(h);

        // text labels
        final TextLabel
                widthLabel = makeDialogLeftLabel(1, "Width: "),
                heightLabel = makeDialogLeftLabel(2, "Height: "),
                context = makeDialogLeftLabel(0, "Current size: " + w + "x" + h),
                explanation = makeValidDimensionsBottomLabel();

        // dim textboxes
        final TextBox widthTextBox = DialogAssembly.makeDialogNumericalTextBox(
                widthLabel, w, Constants.MIN_IMAGE_W, Constants.MAX_IMAGE_W,
                "px", DialogVals::setResizeWidth, 3);
        final TextBox heightTextBox = DialogAssembly.makeDialogNumericalTextBox(
                heightLabel, h, Constants.MIN_IMAGE_H, Constants.MAX_IMAGE_H,
                "px", DialogVals::setResizeHeight, 3);

        // dynamic scale checker
        final DynamicLabel scaleChecker = new DynamicLabel(
                heightLabel.getRenderPosition().displace(0,
                        Layout.DIALOG_CONTENT_INC_Y),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                () -> {
                    final int rw = DialogVals.getResizeWidth(),
                            rh = DialogVals.getResizeHeight();

                    final double initialRatio = w / (double) h,
                            previewRatio = rw / (double) rh;

                    final StringBuilder sb = new StringBuilder();

                    sb.append("Ratio ").append(initialRatio == previewRatio
                            ? "preserved" : "changed");

                    if (w == rw)
                        sb.append("; X: no change");
                    else if (rw % w == 0)
                        sb.append("; X: ").append(rw / w).append("x bigger");
                    else if (w % rw == 0)
                        sb.append("; X: ").append(w / rw).append("x smaller");

                    if (h == rh)
                        sb.append("; Y: no change");
                    else if (rh % h == 0)
                        sb.append("; Y: ").append(rh / h).append("x bigger");
                    else if (h % rh == 0)
                        sb.append("; Y: ").append(h / rh).append("x smaller");

                    return sb.toString();
                }, Layout.getDialogWidth() - (2 * Layout.CONTENT_BUFFER_PX)
        );

        final MenuElementGrouping contents = new MenuElementGrouping(
                context, widthLabel, heightLabel, scaleChecker, explanation,
                widthTextBox, heightTextBox);
        setDialog(assembleDialog("Resize Canvas...", contents,
                () -> widthTextBox.isValid() && heightTextBox.isValid(),
                Constants.GENERIC_APPROVAL_TEXT, c::resize, true));
    }

    public static void setDialogToPad() {
        final SEContext c = StippleEffect.get().getContext();

        final int w = c.getState().getImageWidth(),
                h = c.getState().getImageHeight();

        DialogVals.setPadLeft(0);
        DialogVals.setPadBottom(0);
        DialogVals.setPadTop(0);
        DialogVals.setPadRight(0);

        // text labels
        final TextLabel
                leftLabel = makeDialogLeftLabel(1, "Left: "),
                rightLabel = makeDialogLeftLabel(2, "Right: "),
                topLabel = makeDialogLeftLabel(3, "Top: "),
                bottomLabel = makeDialogLeftLabel(4, "Bottom: "),
                context = makeDialogLeftLabel(0, "Current size: " + w + "x" + h),
                explanation = makeValidDimensionsBottomLabel();

        // pad textboxes
        final TextBox leftTextBox = makeDialogPadTextBox(leftLabel, i -> {
            final int pw = i + DialogVals.getPadRight() + w;
            return pw <= Constants.MAX_IMAGE_W && pw > 0;
        }, DialogVals::setPadLeft);
        final TextBox topTextBox = makeDialogPadTextBox(topLabel, i -> {
            final int ph = i + DialogVals.getPadBottom() + h;
            return ph <= Constants.MAX_IMAGE_H && ph > 0;
        }, DialogVals::setPadTop);
        final TextBox rightTextBox = makeDialogPadTextBox(rightLabel, i -> {
            final int pw = i + DialogVals.getPadLeft() + w;
            return pw <= Constants.MAX_IMAGE_W && pw > 0;
        }, DialogVals::setPadRight);
        final TextBox bottomTextBox = makeDialogPadTextBox(bottomLabel, i -> {
            final int ph = i + DialogVals.getPadTop() + h;
            return ph <= Constants.MAX_IMAGE_W && ph > 0;
        }, DialogVals::setPadBottom);

        // size preview
        final DynamicLabel preview = new DynamicLabel(
                bottomLabel.getRenderPosition().displace(0,
                        Layout.DIALOG_CONTENT_INC_Y),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                () -> {
                    final int pw = DialogVals.getPadLeft() +
                            DialogVals.getPadRight() + w,
                            ph = DialogVals.getPadTop() +
                                    DialogVals.getPadBottom() + h;

                    return "Preview size: " + pw + "x" + ph;
                }, Layout.getDialogWidth() - (2 * Layout.CONTENT_BUFFER_PX)
        );

        final MenuElementGrouping contents = new MenuElementGrouping(
                context, leftLabel, rightLabel, topLabel, bottomLabel,
                preview, explanation,
                leftTextBox, rightTextBox, topTextBox, bottomTextBox);
        setDialog(assembleDialog("Pad Canvas...", contents,
                () -> leftTextBox.isValid() && rightTextBox.isValid() &&
                        topTextBox.isValid() && bottomTextBox.isValid(),
                Constants.GENERIC_APPROVAL_TEXT, c::pad, true));
    }

    public static void setDialogToOpenPNG(final GameImage image, final Path filepath) {
        final int w = image.getWidth(), h = image.getHeight();
        final boolean tooBig = w > Constants.MAX_IMAGE_W || h > Constants.MAX_IMAGE_H;

        DialogVals.setResizeWidth(w);
        DialogVals.setResizeHeight(h);

        DialogVals.setNewProjectXDivs(1);
        DialogVals.setNewProjectYDivs(1);

        // text labels
        final TextLabel context = makeDialogLeftLabel(0,
                "Current size: " + w + "x" + h + (tooBig
                        ? " ... too big as singleton" : "")),
                instruction = makeDialogLeftLabel(1,
                        "Scale down and/or split into more frames"),
                widthLabel = makeDialogLeftLabel(2, "Width: "),
                heightLabel = makeDialogRightLabel(widthLabel, "Height: "),
                xDivsLabel = makeDialogLeftLabel(4 - (tooBig ? 0 : 2), "X frames: "),
                yDivsLabel = makeDialogRightLabel(xDivsLabel, "Y frames: "),
                explanation = makeValidDimensionsBottomLabel();

        // downscale textboxes
        final TextBox widthTextBox = DialogAssembly.makeDialogNumericalTextBox(
                widthLabel, w, Constants.MIN_IMAGE_W, w,
                "px", DialogVals::setResizeWidth, 4);
        final TextBox heightTextBox = DialogAssembly.makeDialogNumericalTextBox(
                heightLabel, h, Constants.MIN_IMAGE_H, h,
                "px", DialogVals::setResizeHeight, 4);

        // division textboxes
        final TextBox xDivsTextBox = DialogAssembly.makeDialogNumericalTextBox(
                xDivsLabel, 1, 1, Constants.MAX_NUM_FRAMES,
                "", DialogVals::setNewProjectXDivs, 3);
        final TextBox yDivsTextBox = DialogAssembly.makeDialogNumericalTextBox(
                yDivsLabel, 1, 1, Constants.MAX_NUM_FRAMES,
                "", DialogVals::setNewProjectYDivs, 3);

        // wrap downscale components in optional group in case N/A
        final MenuElementGrouping optional = tooBig
                ? new MenuElementGrouping(instruction, widthLabel, heightLabel,
                widthTextBox, heightTextBox, explanation)
                : new MenuElementGrouping();

        // precondition
        final Supplier<Boolean> precondition = () -> {
            final int fw = DialogVals.getResizeWidth() / DialogVals.getNewProjectXDivs(),
                    fh = DialogVals.getResizeHeight() / DialogVals.getNewProjectYDivs();

            final boolean boxesValid = widthTextBox.isValid() &&
                    heightTextBox.isValid() && xDivsTextBox.isValid() &&
                    yDivsTextBox.isValid();

            return boxesValid && fw <= Constants.MAX_IMAGE_W &&
                    fh <= Constants.MAX_IMAGE_H;
        };

        final MenuElementGrouping contents = new MenuElementGrouping(context,
                xDivsLabel, yDivsLabel, xDivsTextBox, yDivsTextBox, optional);
        setDialog(assembleDialog("Import file " +
                        filepath.getFileName().toString(), contents,
                precondition, "Import",
                () -> StippleEffect.get().newProjectFromFile(image, filepath), false));
    }

    public static void setDialogToNewProject() {
        // text labels
        final TextLabel
                widthLabel = makeDialogLeftLabel(1, "Width: "),
                heightLabel = makeDialogLeftLabel(2, "Height: "),
                explanation = makeValidDimensionsBottomLabel();

        // dim textboxes
        final TextBox widthTextBox = DialogAssembly.makeDialogNumericalTextBox(
                widthLabel, Constants.DEFAULT_IMAGE_W, Constants.MIN_IMAGE_W,
                Constants.MAX_IMAGE_W, "px", DialogVals::setNewProjectWidth, 3);
        final TextBox heightTextBox = DialogAssembly.makeDialogNumericalTextBox(
                heightLabel, Constants.DEFAULT_IMAGE_H, Constants.MIN_IMAGE_H,
                Constants.MAX_IMAGE_H, "px", DialogVals::setNewProjectHeight, 3);

        final MenuElementGrouping contents = new MenuElementGrouping(
                widthLabel, heightLabel, explanation, widthTextBox, heightTextBox);
        setDialog(assembleDialog("New Project...", contents,
                () -> widthTextBox.isValid() && heightTextBox.isValid(),
                "Create", () -> StippleEffect.get().newProject(), true));
    }

    public static void setDialogToCheckCloseProject(final int index) {
        final GameImage warningText = GraphicsUtils.uiText(Constants.WHITE)
                .addText("All unsaved changes will be lost...").build().draw();
        final StaticMenuElement warning = new StaticMenuElement(
                Layout.getCanvasMiddle(), new Coord2D(warningText.getWidth(),
                warningText.getHeight()), MenuElement.Anchor.CENTRAL, warningText);

        final MenuElementGrouping contents = new MenuElementGrouping(warning);
        setDialog(assembleDialog("Close the project " +
                        StippleEffect.get().getContexts().get(index).projectInfo
                                .getFormattedName(false, true)
                        + "?", contents,
                () -> true, Constants.GENERIC_APPROVAL_TEXT,
                () -> StippleEffect.get().removeContext(index), true));
    }

    public static void setDialogToOutline() {
        final SEContext c = StippleEffect.get().getContext();
        final MenuBuilder mb = new MenuBuilder();

        // presets: single & double
        final TextLabel presets = makeDialogLeftLabel(0, "Presets: ");
        mb.add(presets);

        // no selection notification
        if (!c.getState().hasSelection())
            mb.add(makeDialogLeftLabelAtBottom("Cannot outline; nothing is selected"));

        // buttons for setting presets
        final GameImage baseSingle = GraphicsUtils.drawTextButton(
                Layout.STD_TEXT_BUTTON_W, "Single", false, Constants.GREY),
                baseDouble = GraphicsUtils.drawTextButton(
                        Layout.STD_TEXT_BUTTON_W,
                        "Double", false, Constants.GREY);
        final SimpleMenuButton singlePreset = new SimpleMenuButton(
                getDialogContentOffsetFromLabel(presets),
                new Coord2D(Layout.STD_TEXT_BUTTON_W, Layout.STD_TEXT_BUTTON_H),
                MenuElement.Anchor.LEFT_TOP, true,
                () -> DialogVals.setOutlineSideMask(Outliner.getSingleOutlineMask()),
                baseSingle, GraphicsUtils.drawHighlightedButton(baseSingle));
        final SimpleMenuButton doublePreset = new SimpleMenuButton(
                getDialogContentBigOffsetFromLabel(presets),
                new Coord2D(Layout.STD_TEXT_BUTTON_W, Layout.STD_TEXT_BUTTON_H),
                MenuElement.Anchor.LEFT_TOP, true,
                () -> DialogVals.setOutlineSideMask(Outliner.getDoubleOutlineMask()),
                baseDouble, GraphicsUtils.drawHighlightedButton(baseDouble));
        mb.add(singlePreset);
        mb.add(doublePreset);

        // direction buttons
        final Coord2D buttonPos = Layout.getCanvasMiddle();

        mb.add(new StaticMenuElement(buttonPos, Layout.ICON_DIMS,
                MenuElement.Anchor.CENTRAL, GraphicsUtils.SELECT_OVERLAY));

        final GameImage highlight, included, excluded;

        highlight = GraphicsUtils.HIGHLIGHT_OVERLAY;
        included = GraphicsUtils.loadIcon(IconCodes.INCLUDED);
        excluded = GraphicsUtils.loadIcon(IconCodes.EXCLUDED);

        Arrays.stream(Outliner.Direction.values()).forEach(direction -> {
            final Coord2D rc = direction.relativeCoordinate();
            final int index = direction.ordinal();

            mb.add(new SimpleToggleMenuButton(buttonPos.displace(
                    rc.x * Layout.BUTTON_INC, rc.y * Layout.BUTTON_INC),
                    Layout.ICON_DIMS, MenuElement.Anchor.CENTRAL,
                    true, new GameImage[] { included, excluded },
                    new GameImage[] { highlight, highlight },
                    new Runnable[] { () -> {}, () -> {} },
                    () -> DialogVals.isThisOutlineSide(index) ? 0 : 1,
                    () -> DialogVals.toggleThisOutlineSide(index)));
        });

        final MenuElementGrouping contents =
                new MenuElementGrouping(mb.build().getMenuElements());
        setDialog(assembleDialog("Outline selection options...", contents,
                () -> c.getState().hasSelection(), "Outline",
                () -> c.outlineSelection(DialogVals.getOutlineSideMask()),
                true));
    }

    public static void setDialogToInfo() {
        setDialog(assembleInfoDialog());
    }

    public static void setDialogToProgramSettings() {
        final MenuBuilder mb = new MenuBuilder();

        Arrays.stream(DialogVals.SettingScreen.values()).forEach(ss -> {
            final GameImage baseSS = GraphicsUtils.drawTextButton(
                    Layout.STD_TEXT_BUTTON_W, ss.toString(),
                    false, Constants.GREY),
                    highlighedSS = GraphicsUtils.drawHighlightedButton(baseSS);

            final Coord2D ssPos = Layout.getDialogPosition().displace(
                    Layout.CONTENT_BUFFER_PX + (ss.ordinal() *
                            (Layout.STD_TEXT_BUTTON_W + Layout.BUTTON_OFFSET)),
                    Layout.CONTENT_BUFFER_PX +
                            (int)(1.5 * Layout.STD_TEXT_BUTTON_INC));

            mb.add(new SimpleMenuButton(ssPos,
                    new Coord2D(baseSS.getWidth(), baseSS.getHeight()),
                    MenuElement.Anchor.LEFT_TOP, true,
                    () -> DialogVals.setSettingScreen(ss),
                    baseSS, highlighedSS));
        });

        // decision logic
        final Map<DialogVals.SettingScreen, VerticalScrollingMenuElement>
                settingScreens = new HashMap<>();

        for (DialogVals.SettingScreen settingScreen : DialogVals.SettingScreen.values()) {
            settingScreens.put(settingScreen, assembleScroller(settingScreen));
        }

        final ThinkingMenuElement screenDecider = new ThinkingMenuElement(
                () -> settingScreens.get(DialogVals.getSettingScreen()));

        final MenuElementGrouping contents = new MenuElementGrouping(
                new MenuElementGrouping(mb.build().getMenuElements()),
                screenDecider);
        setDialog(assembleDialog("Program Settings", contents,
                () -> true, "Apply", Settings::write, true));
    }

    public static void setDialogToLayerSettings(final int index) {
        final SEContext c = StippleEffect.get().getContext();
        final SELayer layer = c.getState().getLayers().get(index);

        DialogVals.setLayerOpacity(layer.getOpacity());
        DialogVals.setLayerName(layer.getName());

        // text labels
        final TextLabel layerNameLabel = makeDialogLeftLabel(1, "Name: "),
                opacityLabel = makeDialogLeftLabel(2, "Opacity: ");

        // name textbox
        final TextBox layerNameTextBox = makeDialogNameTextBox(
                layerNameLabel, layer.getName(), DialogVals::setLayerName);

        // opacity slider
        final int MAX_OPACITY = 255;

        final HorizontalSlider opacitySlider = new HorizontalSlider(
                getDialogContentOffsetFromLabel(opacityLabel),
                Layout.getDialogContentWidthAllowance(),
                MenuElement.Anchor.LEFT_TOP, 0, MAX_OPACITY,
                (int)(layer.getOpacity() * MAX_OPACITY),
                o -> DialogVals.setLayerOpacity(o / (double) MAX_OPACITY));
        opacitySlider.updateAssets();

        // opacity value
        final DynamicLabel opacityValue = makeDynamicFromLeftLabel(opacityLabel,
                () -> String.valueOf((int)(DialogVals.getLayerOpacity() * MAX_OPACITY)));

        final MenuElementGrouping contents = new MenuElementGrouping(
                layerNameLabel, opacityLabel, layerNameTextBox,
                opacitySlider, opacityValue);
        setDialog(assembleDialog(layer.getName() + "  |  Layer Settings",
                contents, layerNameTextBox::isValid,
                Constants.GENERIC_APPROVAL_TEXT, () -> {
            c.changeLayerOpacity(DialogVals.getLayerOpacity(), index, true);
            c.changeLayerName(DialogVals.getLayerName(), index);
        }, true));
    }

    public static void setDialogToSplashScreen() {
        // TODO - redesign splash

        final MenuBuilder mb = new MenuBuilder();

        // timer
        mb.add(new TimedMenuElement(
                (int)(Constants.SPLASH_TIMEOUT_SECS * Constants.TICK_HZ),
                () -> StippleEffect.get().clearDialog()));

        final int w = Layout.width(), h = Layout.height();

        // background
        final GameImage background = new GameImage(w, h);
        background.free();

        background.fillRectangle(Constants.ACCENT_BACKGROUND_LIGHT, 0, 0, w, h);
        mb.add(new SimpleMenuButton(new Coord2D(), new Coord2D(w, h),
                MenuElement.Anchor.LEFT_TOP, true,
                () -> StippleEffect.get().clearDialog(), background, background));

        // title
        final GameImage title = GraphicsUtils.uiText(Constants.BLACK, 3d)
                .addText(StippleEffect.PROGRAM_NAME).build().draw();

        mb.add(new StaticMenuElement(new Coord2D(w / 2, (int)(h * 0.6)),
                new Coord2D(title.getWidth(), title.getHeight()),
                MenuElement.Anchor.CENTRAL_TOP, title));

        // subtitle
        final GameImage subtitle = GraphicsUtils.uiText(
                Constants.ACCENT_BACKGROUND_DARK)
                .addText("Pixel art editor and animator").addLineBreak()
                .addText("built on Delta Time by Flinker Flitzer")
                .build().draw();

        mb.add(new StaticMenuElement(new Coord2D(w / 2, (int)(h * 0.75)),
                new Coord2D(subtitle.getWidth(), subtitle.getHeight()),
                MenuElement.Anchor.CENTRAL_TOP, subtitle));

        // version
        final GameImage version = GraphicsUtils.uiText(
                Constants.ACCENT_BACKGROUND_DARK)
                .addText("v" + StippleEffect.VERSION).build().draw();

        mb.add(new StaticMenuElement(new Coord2D(w / 2, h),
                new Coord2D(version.getWidth(), version.getHeight()),
                MenuElement.Anchor.CENTRAL_BOTTOM, version));

        // gateway
        final GameImage ctc = GraphicsUtils.uiText(Constants.GREY)
                .addText("Click anywhere to continue").build().draw();

        mb.add(new AnimationMenuElement(new Coord2D(w - Layout.CONTENT_BUFFER_PX, h),
                new Coord2D(ctc.getWidth(), ctc.getHeight()),
                MenuElement.Anchor.RIGHT_BOTTOM, (int)(Constants.TICK_HZ / 2),
                ctc, GameImage.dummy()));

        // animation frames
        final GameImage[] frames = SplashLoader.loadAnimationFrames();
        mb.add(new AnimationMenuElement(Layout.getCanvasMiddle(),
                new Coord2D(frames[0].getWidth(), frames[0].getHeight()),
                MenuElement.Anchor.CENTRAL, 10,
                SplashLoader.loadAnimationFrames()));

        setDialog(mb.build());
    }

    private static void setDialog(final Menu dialog) {
        StippleEffect.get().setDialog(dialog);
    }

    private static GameImage[] makeBooleanToggleButtonSet() {
        return makeToggleButtonSet("Yes", "No");
    }

    private static GameImage[] makeToggleButtonSet(
            final String... buttonTexts
    ) {
        return Arrays.stream(buttonTexts).map(
                t -> GraphicsUtils.drawTextButton(
                        Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE, t,
                        false, Constants.GREY)
        ).toArray(GameImage[]::new);
    }

    private static DynamicLabel makeDynamicFromLeftLabel(
            final TextLabel label, final Supplier<String> getter
    ) {
        final Coord2D pos = new Coord2D(Layout.width() -
                (label.getX() + Layout.BUTTON_INC), label.getY());

        return new DynamicLabel(pos, MenuElement.Anchor.RIGHT_TOP,
                Constants.WHITE, getter, Layout.DIALOG_DYNAMIC_W_ALLOWANCE);
    }

    private static TextBox makeDialogPadTextBox(
            final TextLabel label,
            final Function<Integer, Boolean> validatorLogic,
            final Consumer<Integer> setter
    ) {
        return makeDialogNumericalTextBox(label,
                String.valueOf(0), "px", TextBox.getIntTextValidator(validatorLogic),
                s -> setter.accept(Integer.parseInt(s)), 4);
    }

    private static TextBox makeDialogNumericalTextBox(
            final TextLabel label, final int initial,
            final int min, final int max, final String suffix,
            final Consumer<Integer> setter, final int maxLength
    ) {
        return makeDialogNumericalTextBox(label, String.valueOf(initial),
                suffix, TextBox.getIntTextValidator(min, max),
                s -> setter.accept(Integer.parseInt(s)), maxLength);
    }

    private static TextBox makeDialogNumericalTextBox(
            final TextLabel label, final String initial, final String suffix,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter, final int maxLength
    ) {
        return new TextBox(getDialogContentOffsetFromLabel(label),
                Layout.SMALL_TEXT_BOX_W, MenuElement.Anchor.LEFT_TOP,
                "", initial, suffix, textValidator, setter, maxLength);
    }

    private static TextBox makeDialogCustomTextBox(
            final TextLabel label, final int width,
            final Function<TextLabel, Coord2D> offsetFunction,
            final Supplier<String> prefixGetter, final String initial,
            final Supplier<String> suffixGetter,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter, final int length
    ) {
        return new TextBox(offsetFunction.apply(label), width,
                MenuElement.Anchor.LEFT_TOP, prefixGetter, initial, suffixGetter,
                textValidator, setter, () -> Constants.GREY, length);
    }

    private static TextBox makeDialogNameTextBox(
            final TextLabel label, final String initial,
            final Consumer<String> setter
    ) {
        return new TextBox(getDialogContentOffsetFromLabel(label),
                Layout.getDialogContentWidthAllowance(), MenuElement.Anchor.LEFT_TOP,
                initial, TextBox::validateAsFileName, setter,
                Constants.MAX_NAME_LENGTH);
    }

    private static TextLabel makeValidDimensionsBottomLabel() {
        return makeDialogLeftLabelAtBottom("Valid image sizes run from " +
                Constants.MIN_IMAGE_W + "x" + Constants.MIN_IMAGE_H + " to " +
                Constants.MAX_IMAGE_W + "x" + Constants.MAX_IMAGE_H + ".");
    }

    private static TextLabel makeDialogLeftLabelAtBottom(final String text) {
        final int y = Layout.getCanvasMiddle()
                .displace(0, Layout.getDialogHeight() / 2)
                .displace(0, -(Layout.DIALOG_CONTENT_INC_Y +
                        Layout.CONTENT_BUFFER_PX)).y;

        return TextLabel.make(new Coord2D(Layout.getDialogContentInitial().x,
                y), text, Constants.WHITE);
    }

    private static TextLabel makeDialogLeftLabel(final int index, final String text) {
        return TextLabel.make(Layout.getDialogContentInitial()
                .displace(0, index * Layout.DIALOG_CONTENT_INC_Y),
                text, Constants.WHITE);
    }

    private static TextLabel makeDialogRightLabel(final TextLabel leftLabel, final String text) {
        return TextLabel.make(leftLabel.getRenderPosition().displace(
                Layout.getDialogWidth() / 2, 0), text, Constants.WHITE);
    }

    private static Coord2D getDialogContentOffsetFromLabel(final TextLabel label) {
        return label.getRenderPosition().displace(
                Layout.DIALOG_CONTENT_OFFSET_X,
                Layout.DIALOG_CONTENT_COMP_OFFSET_Y);
    }

    private static Coord2D getDialogContentBigOffsetFromLabel(final TextLabel label) {
        return label.getRenderPosition().displace(
                Layout.DIALOG_CONTENT_BIG_OFFSET_X,
                Layout.DIALOG_CONTENT_COMP_OFFSET_Y);
    }

    private static VerticalScrollingMenuElement assembleScroller(
            final DialogVals.SettingScreen settingScreen
    ) {
        final MenuBuilder mb = new MenuBuilder();

        // title
        final Coord2D titlePosition = Layout.getDialogPosition().displace(
                Layout.CONTENT_BUFFER_PX + Layout.BUTTON_BORDER_PX,
                (int)(3.5 * Layout.STD_TEXT_BUTTON_INC));
        mb.add(TextLabel.make(titlePosition, settingScreen.getTitle(),
                Constants.BLACK, 2d));
        final int initialYIndex = 4;

        // initialize in every execution path
        final MenuElement bottomLabel = switch (settingScreen) {
            case STARTUP -> {
                // text labels
                final TextLabel screenModeLabel = makeDialogLeftLabel(
                        initialYIndex, "Fullscreen on startup: ");

                // toggle buttons
                final GameImage[] smBases = makeBooleanToggleButtonSet();
                final SimpleToggleMenuButton screenModeButton = new SimpleToggleMenuButton(
                        getDialogContentBigOffsetFromLabel(screenModeLabel),
                        new Coord2D(Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                                Layout.STD_TEXT_BUTTON_H),
                        MenuElement.Anchor.LEFT_TOP, true,
                        smBases, Arrays.stream(smBases)
                        .map(GraphicsUtils::drawHighlightedButton)
                        .toArray(GameImage[]::new),
                        new Runnable[] {
                                () -> Settings.setFullscreenOnStartup(false),
                                () -> Settings.setFullscreenOnStartup(true)
                        }, () -> Settings.isFullscreenOnStartup() ? 0 : 1, () -> {});

                mb.add(screenModeLabel);
                mb.add(screenModeButton);

                // update as new settings are added to category
                yield screenModeLabel;
            }
            case FORMAT -> {
                // text labels
                final TextLabel indexPrefixLabel = makeDialogLeftLabel(
                        initialYIndex, "Default frame prefix: "),
                        indexSuffixLabel = makeDialogLeftLabel(
                                initialYIndex + 1, "Default frame suffix: ");

                // textboxes
                final TextBox indexPrefixTextBox = makeDialogCustomTextBox(
                        indexPrefixLabel, Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                        DialogAssembly::getDialogContentBigOffsetFromLabel,
                        () -> "", Settings.getDefaultIndexPrefix(), () -> "",
                        TextBox::validateAsOptionallyEmptyFilename,
                        s -> Settings.setDefaultIndexPrefix(s, false), 5);
                final TextBox indexSuffixTextBox = makeDialogCustomTextBox(
                        indexSuffixLabel, Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                        DialogAssembly::getDialogContentBigOffsetFromLabel,
                        () -> "", Settings.getDefaultIndexSuffix(), () -> "",
                        TextBox::validateAsOptionallyEmptyFilename,
                        s -> Settings.setDefaultIndexSuffix(s, false), 5);

                mb.add(indexPrefixLabel);
                mb.add(indexSuffixLabel);
                mb.add(indexPrefixTextBox);
                mb.add(indexSuffixTextBox);

                // update as new settings are added to category
                yield indexSuffixLabel;
            }
            case VISUAL -> {
                // text labels
                final TextLabel checkerboardLabel = makeDialogLeftLabel(
                        initialYIndex, "Checkerboard size: "),
                        fontLabel = makeDialogLeftLabel(
                                initialYIndex + 1, "Program font: ");

                // toggle buttons
                final GameImage[] fontBases = makeToggleButtonSet(
                        Arrays.stream(SEFonts.Code.values())
                                .map(SEFonts.Code::forButtonText)
                                .toArray(String[]::new));
                final SimpleToggleMenuButton fontButton = new SimpleToggleMenuButton(
                        getDialogContentBigOffsetFromLabel(fontLabel),
                        new Coord2D(Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                                Layout.STD_TEXT_BUTTON_H),
                        MenuElement.Anchor.LEFT_TOP, true,
                        fontBases, Arrays.stream(fontBases)
                        .map(GraphicsUtils::drawHighlightedButton)
                        .toArray(GameImage[]::new),
                        Arrays.stream(SEFonts.Code.values()).map(
                                code -> (Runnable) () -> Settings.setProgramFont(
                                        code.next().name(), false)
                        ).toArray(Runnable[]::new),
                        () -> Settings.getProgramFont().ordinal(), () -> {});

                // sliders
                final HorizontalSlider checkerboardSlider = new HorizontalSlider(
                        getDialogContentBigOffsetFromLabel(checkerboardLabel),
                        Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                        MenuElement.Anchor.LEFT_TOP, 0, 5,
                        (int)Math.round(Math.log(Settings.getCheckerboardPixels())
                                / Math.log(2d)),
                        exp -> Settings.setCheckerboardPixels(
                                (int)Math.pow(2d, exp), false));
                checkerboardSlider.updateAssets();
                final DynamicLabel checkerboardValue = makeDynamicFromLeftLabel(
                        checkerboardLabel,
                        () -> String.valueOf(Settings.getCheckerboardPixels()));

                mb.add(checkerboardLabel);
                mb.add(fontLabel);
                mb.add(fontButton);
                mb.add(checkerboardSlider);
                mb.add(checkerboardValue);

                // update as new settings are added to category
                yield fontLabel;
            }
        };

        // scrolling container
        final int scrollerEndY = (Layout.getCanvasMiddle().y +
                Layout.getDialogHeight() / 2) - ((2 * Layout.CONTENT_BUFFER_PX) +
                Layout.STD_TEXT_BUTTON_H);

        final Coord2D scrollerPos = Layout.getDialogPosition().displace(0,
                (4 * Layout.STD_TEXT_BUTTON_INC) +
                        Layout.TEXT_Y_OFFSET - Layout.BUTTON_DIM),
                scrollerDims = new Coord2D(Layout.getDialogWidth(),
                        scrollerEndY - scrollerPos.y);

        final int realBottomY = bottomLabel.getRenderPosition().y +
                bottomLabel.getHeight() + Layout.STD_TEXT_BUTTON_H;

        return new VerticalScrollingMenuElement(scrollerPos, scrollerDims,
                Arrays.stream(mb.build().getMenuElements()).map(
                        ScrollableMenuElement::new).toArray(
                                ScrollableMenuElement[]::new), realBottomY, 0);
    }

    private static VerticalScrollingMenuElement assembleScroller(
            final DialogVals.InfoScreen infoScreen, final int scrollerEndY
    ) {
        final int dialogW = (int)(Layout.width() * 0.7),
                incY = Layout.DIALOG_CONTENT_INC_Y;

        final Coord2D contentStart = new Coord2D(Layout.getCanvasMiddle().x -
                (dialogW / 2) + Layout.CONTENT_BUFFER_PX + Layout.BUTTON_BORDER_PX,
                4 * Layout.STD_TEXT_BUTTON_INC);
        final Set<MenuElement> contentAssembler = new HashSet<>();

        int initialbottomY = 0;

        final double titleSize = 2d;
        final TextLabel headingLabel = TextLabel.make(contentStart.displace(
                0, initialbottomY),
                infoScreen.getTitle(), Constants.BLACK, titleSize);
        initialbottomY += (int)(incY * titleSize) + Layout.BUTTON_INC;

        contentAssembler.add(headingLabel);

        final int deltaBottomY = switch (infoScreen) {
            case ABOUT -> assembleInfoScreenContents(
                    new String[] { Constants.ABOUT }, new String[] { "" },
                    contentAssembler, contentStart, initialbottomY
            );
            case PROJECT -> assembleProjectInfoScreenContents(
                    contentAssembler, contentStart, initialbottomY);
            case FRAMES -> assembleFramesInfoScreen(contentAssembler,
                    contentStart, initialbottomY);
            case LAYERS -> assembleLayersInfoScreen(contentAssembler,
                    contentStart, initialbottomY);
            case TOOLS -> assembleToolsInfoScreenContents(contentAssembler,
                    contentStart, initialbottomY);
            case MORE -> assembleInfoScreenContents(
                    new String[] {
                            IconCodes.SWAP_COLORS,
                            IconCodes.HORIZONTAL_REFLECTION,
                            IconCodes.VERTICAL_REFLECTION,
                            IconCodes.OUTLINE,
                            IconCodes.GENERAL,
                            IconCodes.CLIPBOARD_SHORTCUTS,
                            IconCodes.SELECTION_SHORTCUTS,
                            IconCodes.COLOR_SHORTCUTS
                    },
                    new String[] {
                            "Swap primary and secondary color",
                            // TODO - icon color shortcuts: toggle slider vs. palette mode
                            "Horizontal reflection",
                            "Vertical reflection",
                            "Outline",
                            "General",
                            "Clipboard shortcuts",
                            "Selection shortcuts",
                            "Advanced color shortcuts"
                    },
                    contentAssembler, contentStart, initialbottomY
            );
            case CHANGELOG -> assembleInfoScreenContents(
                    new String[] { IconCodes.CHANGELOG },
                    new String[] { "" },
                    contentAssembler, contentStart, initialbottomY
            );
        };

        final ScrollableMenuElement[] scrollingElements =
                contentAssembler.stream().map(ScrollableMenuElement::new)
                        .toArray(ScrollableMenuElement[]::new);

        final Coord2D wrapperDims = new Coord2D(dialogW - (2 * Layout.BUTTON_BORDER_PX),
                scrollerEndY - (contentStart.y + Layout.TEXT_Y_OFFSET));

        // assemble contents into scrolling element
        return new VerticalScrollingMenuElement(contentStart.displace(
                -Layout.CONTENT_BUFFER_PX, Layout.TEXT_Y_OFFSET), wrapperDims,
                scrollingElements, initialbottomY + deltaBottomY +
                contentStart.y, 0);
    }

    private static int assembleInfoScreenContents(
            final String[] iconAndBlurbCodes, final String[] headings,
            final Set<MenuElement> contentAssembler, final Coord2D contentStart,
            final int initialBottomY
    ) {
        if (iconAndBlurbCodes.length != headings.length) {
            GameError.send("Length of file codes and headings arrays did not match...");
            return 0;
        }

        final int indent = (2 * Layout.BUTTON_INC),
                incY = Layout.DIALOG_CONTENT_INC_Y;

        int bottomY = initialBottomY;

        for (int i = 0; i < iconAndBlurbCodes.length; i++) {
            final String code = iconAndBlurbCodes[i];
            final boolean hasIcon = !code.startsWith(IconCodes.NO_ICON_PREFIX);

            if (hasIcon) {
                final StaticMenuElement icon = new StaticMenuElement(
                        contentStart.displace(0, bottomY),
                        MenuElement.Anchor.LEFT_TOP, GraphicsUtils.loadIcon(code));
                contentAssembler.add(icon);
            }

            final TextLabel name = TextLabel.make(contentStart.displace(
                            (hasIcon ? Layout.BUTTON_INC : 0) + Layout.CONTENT_BUFFER_PX,
                            bottomY + Layout.TEXT_Y_OFFSET - Layout.BUTTON_BORDER_PX),
                    headings[i], hasIcon ? Constants.HIGHLIGHT_1 : Constants.GREY);
            contentAssembler.add(name);

            bottomY += incY;

            final String[] blurb = ParserUtils.getBlurb(code);

            for (String line : blurb) {
                final String[] lineSegments = ParserUtils.extractHighlight(line);

                int offsetX = 0;

                for (int j = 0; j < lineSegments.length; j++) {
                    final TextLabel segmentText = TextLabel.make(
                            contentStart.displace(indent + offsetX,
                                    bottomY + Layout.TEXT_Y_OFFSET),
                            lineSegments[j], j % 2 == 1
                                    ? Constants.HIGHLIGHT_1 : Constants.WHITE);

                    contentAssembler.add(segmentText);
                    offsetX += segmentText.getWidth() + Layout.BUTTON_BORDER_PX;
                }

                bottomY += incY;
            }

            bottomY += incY;
        }

        return bottomY - initialBottomY;
    }

    private static int assembleProjectInfoScreenContents(
            final Set<MenuElement> contentAssembler, final Coord2D contentStart,
            final int initialBottomY
    ) {
        return assembleInfoScreenContents(
                new String[] {
                        IconCodes.INFO,
                        IconCodes.SETTINGS,
                        IconCodes.NEW_PROJECT,
                        IconCodes.OPEN_FILE,
                        IconCodes.SAVE,
                        IconCodes.SAVE_AS,
                        IconCodes.RESIZE,
                        IconCodes.PAD,
                        IconCodes.PREVIEW,
                        IconCodes.UNDO,
                        IconCodes.GRANULAR_UNDO,
                        IconCodes.GRANULAR_REDO,
                        IconCodes.REDO
                },
                new String[] {
                        "Info", "Program Settings",
                        "New Project", "Import", "Save", "Save As...",
                        "Resize", "Pad", "Preview",
                        "Undo", "Granular Undo", "Granular Redo", "Redo"
                }, contentAssembler, contentStart, initialBottomY
        );
    }

    private static int assembleLayersInfoScreen(
            final Set<MenuElement> contentAssembler, final Coord2D contentStart,
            final int initialBottomY
    ) {
        return assembleInfoScreenContents(
                new String[] {
                        IconCodes.NEW_LAYER,
                        IconCodes.DUPLICATE_LAYER,
                        IconCodes.REMOVE_LAYER,
                        IconCodes.MOVE_LAYER_UP,
                        IconCodes.MOVE_LAYER_DOWN,
                        IconCodes.MERGE_WITH_LAYER_BELOW,
                        IconCodes.LAYER_VISIBILITY,
                        IconCodes.LAYER_ENABLED,
                        IconCodes.LAYER_DISABLED,
                        IconCodes.ISOLATE_LAYER,
                        IconCodes.ENABLE_ALL_LAYERS,
                        IconCodes.ONION_SKIN,
                        IconCodes.ONION_SKIN_NONE,
                        IconCodes.ONION_SKIN_PREVIOUS,
                        IconCodes.ONION_SKIN_NEXT,
                        IconCodes.ONION_SKIN_BOTH,
                        IconCodes.FRAME_LOCKING,
                        IconCodes.FRAMES_LINKED,
                        IconCodes.FRAMES_UNLINKED,
                        IconCodes.LAYER_SETTINGS
                },
                new String[] {
                        "New layer",
                        "Duplicate layer",
                        "Remove layer",
                        "Move layer up",
                        "Move layer down",
                        "Merge with layer below",
                        "Layer visibility controls",
                        "Layer is visible/enabled",
                        "Layer is invisible/disabled",
                        "Isolate layer",
                        "Enable all layers",
                        "Onion skin modes",
                        "Disabled",
                        "Previous frame",
                        "Next frame",
                        "Previous and next frame",
                        "Layer frame status",
                        "Frames are linked / layer is static",
                        "Frames are free / layer is dynamic",
                        "Layer settings"
                }, contentAssembler, contentStart, initialBottomY
        );
    }

    private static int assembleFramesInfoScreen(
            final Set<MenuElement> contentAssembler, final Coord2D contentStart,
            final int initialBottomY
    ) {
        return assembleInfoScreenContents(
                new String[] {
                        IconCodes.NEW_FRAME,
                        IconCodes.DUPLICATE_FRAME,
                        IconCodes.REMOVE_FRAME,
                        IconCodes.MOVE_FRAME_FORWARD,
                        IconCodes.MOVE_FRAME_BACK,
                        IconCodes.TO_FIRST_FRAME,
                        IconCodes.PREVIOUS,
                        IconCodes.NEXT,
                        IconCodes.TO_LAST_FRAME,
                        IconCodes.PLAY,
                        IconCodes.PLAYBACK_MODES,
                        IconCodes.FORWARDS,
                        IconCodes.BACKWARDS,
                        IconCodes.LOOP,
                        IconCodes.PONG
                },
                new String[] {
                        "New frame",
                        "Duplicate frame",
                        "Remove frame",
                        "Move frame forward",
                        "Move frame back",
                        "Navigate to first frame",
                        "To previous frame",
                        "To next frame",
                        "Navigate to last frame",
                        "Play animation / Stop animation playback",
                        "Playback modes",
                        "Forwards",
                        "Backwards",
                        "Looping",
                        "Oscillating"
                }, contentAssembler, contentStart, initialBottomY
        );
    }

    private static int assembleToolsInfoScreenContents(
            final Set<MenuElement> contentAssembler, final Coord2D contentStart,
            final int initialBottomY
    ) {
        return assembleInfoScreenContents(
                Arrays.stream(Constants.ALL_TOOLS)
                        .map(Tool::convertNameToFilename).toArray(String[]::new),
                Arrays.stream(Constants.ALL_TOOLS)
                        .map(Tool::getName).toArray(String[]::new),
                contentAssembler, contentStart, initialBottomY);
    }

    private static Menu assembleInfoDialog() {
        final int dialogW = (int)(Layout.width() * 0.7);

        final MenuBuilder mb = new MenuBuilder();

        // background
        final GameImage backgroundImage = new GameImage(dialogW,
                Layout.height() - (2 * Layout.BUTTON_DIM));
        backgroundImage.fillRectangle(Constants.ACCENT_BACKGROUND_DARK,
                0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());
        backgroundImage.drawRectangle(Constants.BLACK,
                2f * Layout.BUTTON_BORDER_PX, 0, 0,
                backgroundImage.getWidth(), backgroundImage.getHeight());

        final StaticMenuElement background =
                new StaticMenuElement(Layout.getCanvasMiddle(),
                        MenuElement.Anchor.CENTRAL, backgroundImage.submit());
        mb.add(background);

        // title
        mb.add(TextLabel.make(background.getRenderPosition().displace(
                        Layout.CONTENT_BUFFER_PX + Layout.BUTTON_BORDER_PX,
                        Layout.TEXT_Y_OFFSET + Layout.BUTTON_BORDER_PX),
                StippleEffect.PROGRAM_NAME + " v" + StippleEffect.VERSION +
                        "  |  Help & Information", Constants.WHITE));

        // close button
        final GameImage baseImage = GraphicsUtils.drawTextButton(
                Layout.STD_TEXT_BUTTON_W, "Close", false, Constants.GREY),
                highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage);

        final Coord2D cancelPos = background.getRenderPosition()
                .displace(background.getWidth(), background.getHeight())
                .displace(-Layout.CONTENT_BUFFER_PX, -Layout.CONTENT_BUFFER_PX);

        mb.add(new SimpleMenuButton(cancelPos,
                new Coord2D(baseImage.getWidth(), baseImage.getHeight()),
                MenuElement.Anchor.RIGHT_BOTTOM, true,
                () -> StippleEffect.get().clearDialog(),
                baseImage, highlightedImage));

        // contents
        Arrays.stream(DialogVals.InfoScreen.values()).forEach(is -> {
                    final GameImage baseIS = GraphicsUtils.drawTextButton(
                            Layout.STD_TEXT_BUTTON_W, is.toString(),
                            false, Constants.GREY),
                            highlighedIS = GraphicsUtils.drawHighlightedButton(baseIS);

                    final Coord2D isPos = background.getRenderPosition().displace(
                            Layout.CONTENT_BUFFER_PX + (is.ordinal() *
                                    (Layout.STD_TEXT_BUTTON_W + Layout.BUTTON_OFFSET)),
                            Layout.CONTENT_BUFFER_PX +
                                            (int)(1.5 * Layout.STD_TEXT_BUTTON_INC));

                    mb.add(new SimpleMenuButton(isPos,
                            new Coord2D(baseIS.getWidth(), baseIS.getHeight()),
                            MenuElement.Anchor.LEFT_TOP, true,
                            () -> DialogVals.setInfoScreen(is),
                            baseIS, highlighedIS));
                });

        final int scrollerEndY = (background.getRenderPosition().y +
                background.getHeight()) - ((2 * Layout.CONTENT_BUFFER_PX) +
                baseImage.getHeight());

        final Map<DialogVals.InfoScreen, VerticalScrollingMenuElement>
                infoScreens = new HashMap<>();

        for (DialogVals.InfoScreen infoScreen : DialogVals.InfoScreen.values()) {
            infoScreens.put(infoScreen, assembleScroller(infoScreen, scrollerEndY));
        }

        final ThinkingMenuElement screenDecider = new ThinkingMenuElement(
                () -> infoScreens.get(DialogVals.getInfoScreen()));

        mb.add(screenDecider);
        return mb.build();
    }

    private static Menu assembleDialog(
            final String title, final MenuElementGrouping contents,
            final Supplier<Boolean> precondition, final String approveText,
            final Runnable onApproval, final boolean clearDialog
    ) {
        final MenuBuilder mb = new MenuBuilder();

        // background
        final GameImage backgroundImage = new GameImage(
                Layout.getDialogWidth(), Layout.getDialogHeight());
        backgroundImage.fillRectangle(Constants.ACCENT_BACKGROUND_DARK,
                0, 0, Layout.getDialogWidth(), Layout.getDialogHeight());

        final StaticMenuElement background =
                new StaticMenuElement(Layout.getCanvasMiddle(), new Coord2D(
                        Layout.getDialogWidth(), Layout.getDialogHeight()),
                        MenuElement.Anchor.CENTRAL, backgroundImage.submit());
        mb.add(background);

        // title
        mb.add(TextLabel.make(background.getRenderPosition().displace(
                        Layout.CONTENT_BUFFER_PX + Layout.BUTTON_BORDER_PX,
                        Layout.TEXT_Y_OFFSET + Layout.BUTTON_BORDER_PX),
                title, Constants.WHITE));

        // cancel button
        final GameImage baseImage = GraphicsUtils.drawTextButton(
                Layout.STD_TEXT_BUTTON_W, "Cancel", false, Constants.GREY),
                highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage);

        final Coord2D cancelPos = background.getRenderPosition()
                .displace(background.getWidth(), background.getHeight())
                .displace(-Layout.CONTENT_BUFFER_PX, -Layout.CONTENT_BUFFER_PX);

        mb.add(new SimpleMenuButton(cancelPos,
                new Coord2D(baseImage.getWidth(), baseImage.getHeight()),
                MenuElement.Anchor.RIGHT_BOTTOM, true,
                () -> StippleEffect.get().clearDialog(),
                baseImage, highlightedImage));

        // approve button
        final Coord2D approvePos = cancelPos.displace(-(baseImage.getWidth() +
                Layout.BUTTON_OFFSET), 0);

        mb.add(new ApproveDialogButton(approvePos,
                new Coord2D(baseImage.getWidth(), baseImage.getHeight()),
                MenuElement.Anchor.RIGHT_BOTTOM, onApproval, clearDialog,
                precondition, approveText));

        // contents come before border to ensure proper rendering
        mb.add(contents);

        // border
        final GameImage borderImage = new GameImage(
                Layout.getDialogWidth(), Layout.getDialogHeight());
        borderImage.drawRectangle(Constants.BLACK,
                2f * Layout.BUTTON_BORDER_PX, 0, 0,
                Layout.getDialogWidth(), Layout.getDialogHeight());

        final StaticMenuElement border =
                new StaticMenuElement(Layout.getCanvasMiddle(), new Coord2D(
                        Layout.getDialogWidth(), Layout.getDialogHeight()),
                        MenuElement.Anchor.CENTRAL, borderImage.submit());
        mb.add(border);

        return mb.build();
    }
}
