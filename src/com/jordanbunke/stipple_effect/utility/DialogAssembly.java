package com.jordanbunke.stipple_effect.utility;

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
import com.jordanbunke.stipple_effect.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.menu_elements.DynamicTextButton;
import com.jordanbunke.stipple_effect.menu_elements.TextLabel;
import com.jordanbunke.stipple_effect.menu_elements.dialog.ApproveDialogButton;
import com.jordanbunke.stipple_effect.menu_elements.dialog.TextBox;
import com.jordanbunke.stipple_effect.menu_elements.scrollable.HorizontalSlider;
import com.jordanbunke.stipple_effect.menu_elements.scrollable.ScrollableMenuElement;
import com.jordanbunke.stipple_effect.menu_elements.scrollable.VerticalScrollingMenuElement;
import com.jordanbunke.stipple_effect.project.ProjectInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.tools.Tool;

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
                fpsLabel = makeDialogLeftLabel(4, "Frame rate: ");

        // folder selection button
        final DynamicTextButton folderButton = new DynamicTextButton(
                getDialogContentOffsetFromLabel(folderLabel),
                Constants.DIALOG_CONTENT_W_ALLOWANCE,
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
                        final String level = folder.getFileName().toString();

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
                Constants.DIALOG_CONTENT_W_ALLOWANCE, MenuElement.Anchor.LEFT_TOP,
                Constants.MIN_SCALE_UP, Constants.MAX_SCALE_UP,
                c.projectInfo.getScaleUp(),
                c.projectInfo::setScaleUp);
        scaleUpSlider.updateAssets();

        final DynamicLabel scaleUpValue = makeDynamicFromLeftLabel(
                scaleUpLabel, () -> c.projectInfo.getScaleUp() + "x");

        // save as toggle
        final ProjectInfo.SaveType[] saveOptions = ProjectInfo.SaveType.validOptions();

        final int toggleWidth = Constants.DIALOG_CONTENT_W_ALLOWANCE;

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
                new Coord2D(toggleWidth, Constants.STD_TEXT_BUTTON_H),
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
                (int)(Constants.DIALOG_CONTENT_W_ALLOWANCE * 0.9),
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

        final ThinkingMenuElement basedOnSaveType = new ThinkingMenuElement(() ->
                switch (c.projectInfo.getSaveType()) {
            case PNG_STITCHED -> c.getState().getFrameCount() > 1
                    ? pngStitchedContents : new PlaceholderMenuElement();
            case GIF -> gifContents;
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
            }, "Save", c.projectInfo::save));
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
                explanation = makeDialogLeftLabel(5,
                        "Valid image sizes run from " + Constants.MIN_IMAGE_W +
                                "x" + Constants.MIN_IMAGE_H + " to " +
                                Constants.MAX_IMAGE_W + "x" + Constants.MAX_IMAGE_H + ".");

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
                        Constants.DIALOG_CONTENT_INC_Y),
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
                }, Constants.DIALOG_W - (2 * Constants.TOOL_NAME_X)
        );

        final MenuElementGrouping contents = new MenuElementGrouping(
                context, widthLabel, heightLabel, scaleChecker, explanation,
                widthTextBox, heightTextBox);
        setDialog(assembleDialog("Resize Canvas...", contents,
                () -> widthTextBox.isValid() && heightTextBox.isValid(),
                Constants.GENERIC_APPROVAL_TEXT, c::resize));
    }

    public static void setDialogToPad() {
        final SEContext c = StippleEffect.get().getContext();

        final int w = c.getState().getImageWidth(),
                h = c.getState().getImageHeight();

        // text labels
        final TextLabel
                leftLabel = makeDialogLeftLabel(1, "Left: "),
                rightLabel = makeDialogLeftLabel(2, "Right: "),
                topLabel = makeDialogLeftLabel(3, "Top: "),
                bottomLabel = makeDialogLeftLabel(4, "Bottom: "),
                context = makeDialogLeftLabel(0, "Current size: " + w + "x" + h);

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
                        Constants.DIALOG_CONTENT_INC_Y),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                () -> {
                    final int pw = DialogVals.getPadLeft() +
                            DialogVals.getPadRight() + w,
                            ph = DialogVals.getPadTop() +
                                    DialogVals.getPadBottom() + h;

                    return "Preview size: " + pw + "x" + ph;
                }, Constants.DIALOG_W - (2 * Constants.TOOL_NAME_X)
        );

        final MenuElementGrouping contents = new MenuElementGrouping(
                context, leftLabel, rightLabel, topLabel, bottomLabel, preview,
                leftTextBox, rightTextBox, topTextBox, bottomTextBox
        );
        setDialog(assembleDialog("Pad Canvas...", contents,
                () -> leftTextBox.isValid() && rightTextBox.isValid() &&
                        topTextBox.isValid() && bottomTextBox.isValid(),
                Constants.GENERIC_APPROVAL_TEXT, c::pad));
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
                yDivsLabel = makeDialogRightLabel(xDivsLabel, "Y frames: ");

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
                widthTextBox, heightTextBox)
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
                () -> StippleEffect.get().newProjectFromFile(image, filepath)));
    }

    public static void setDialogToNewProject() {
        // text labels
        final TextLabel
                widthLabel = makeDialogLeftLabel(1, "Width: "),
                heightLabel = makeDialogLeftLabel(2, "Height: "),
                explanation = makeDialogLeftLabel(4,
                        "Valid image sizes run from " + Constants.MIN_IMAGE_W +
                                "x" + Constants.MIN_IMAGE_H + " to " +
                                Constants.MAX_IMAGE_W + "x" + Constants.MAX_IMAGE_H + ".");

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
                "Create", () -> StippleEffect.get().newProject()));
    }

    public static void setDialogToCheckCloseProject(final int index) {
        final GameImage warningText = GraphicsUtils.uiText(Constants.WHITE)
                .addText("All unsaved changes will be lost...").build().draw();
        final StaticMenuElement warning = new StaticMenuElement(
                Constants.getCanvasMiddle(), new Coord2D(warningText.getWidth(),
                warningText.getHeight()), MenuElement.Anchor.CENTRAL, warningText);

        final MenuElementGrouping contents = new MenuElementGrouping(warning);
        setDialog(assembleDialog("Close the project " +
                        StippleEffect.get().getContexts().get(index).projectInfo
                                .getFormattedName(false, true)
                        + "?", contents,
                () -> true, Constants.GENERIC_APPROVAL_TEXT,
                () -> StippleEffect.get().removeContext(index)));
    }

    public static void setDialogToInfo() {
        setDialog(assembleInfoDialog());
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
                Constants.DIALOG_CONTENT_W_ALLOWANCE,
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
        }));
    }

    public static void setDialogToProgramSettings() {
        // text labels
        final TextLabel screenModeLabel = makeDialogLeftLabel(1, "Fullscreen on startup: "),
                checkerboardLabel = makeDialogLeftLabel(2, "Checkerboard size: "),
                fontLabel = makeDialogLeftLabel(3, "Program font: ");

        // toggle buttons
        final GameImage[] smBases = makeBooleanToggleButtonSet();
        final SimpleToggleMenuButton screenModeButton = new SimpleToggleMenuButton(
                getDialogContentBigOffsetFromLabel(screenModeLabel),
                new Coord2D(Constants.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                        Constants.STD_TEXT_BUTTON_H),
                MenuElement.Anchor.LEFT_TOP, true,
                smBases, Arrays.stream(smBases)
                .map(GraphicsUtils::drawHighlightedButton)
                .toArray(GameImage[]::new),
                new Runnable[] {
                        () -> Settings.setFullscreenOnStartup(false),
                        () -> Settings.setFullscreenOnStartup(true)
                }, () -> Settings.isFullscreenOnStartup() ? 0 : 1, () -> {});

        final GameImage[] fontBases = makeToggleButtonSet(
                Arrays.stream(SEFonts.Code.values())
                        .map(SEFonts.Code::forButtonText)
                        .toArray(String[]::new));
        final SimpleToggleMenuButton fontButton = new SimpleToggleMenuButton(
                getDialogContentBigOffsetFromLabel(fontLabel),
                new Coord2D(Constants.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                        Constants.STD_TEXT_BUTTON_H),
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
                Constants.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                MenuElement.Anchor.LEFT_TOP, 0, 5,
                (int)Math.round(Math.log(Settings.getCheckerboardPixels())
                        / Math.log(2d)),
                exp -> Settings.setCheckerboardPixels(
                        (int)Math.pow(2d, exp), false));
        checkerboardSlider.updateAssets();
        final DynamicLabel checkerboardValue = makeDynamicFromLeftLabel(
                checkerboardLabel,
                () -> String.valueOf(Settings.getCheckerboardPixels()));

        // textboxes


        final MenuElementGrouping contents = new MenuElementGrouping(
                screenModeLabel, screenModeButton,
                checkerboardLabel, checkerboardSlider, checkerboardValue,
                fontLabel, fontButton
        );
        setDialog(assembleDialog("Program Settings",
                contents, () -> true, "Apply", () -> {}));
    }

    public static void setDialogToSplashScreen() {
        final MenuBuilder mb = new MenuBuilder();

        // timer
        mb.add(new TimedMenuElement(
                (int)(Constants.SPLASH_TIMEOUT_SECS * Constants.TICK_HZ),
                () -> StippleEffect.get().clearDialog()));

        final int w = Constants.CANVAS_W, h = Constants.CANVAS_H;

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
                .addText(StippleEffect.VERSION).build().draw();

        mb.add(new StaticMenuElement(new Coord2D(w / 2, h),
                new Coord2D(version.getWidth(), version.getHeight()),
                MenuElement.Anchor.CENTRAL_BOTTOM, version));

        // gateway
        final GameImage ctc = GraphicsUtils.uiText(Constants.GREY)
                .addText("Click anywhere to continue").build().draw();

        mb.add(new AnimationMenuElement(new Coord2D(w - Constants.TOOL_NAME_X, h),
                new Coord2D(ctc.getWidth(), ctc.getHeight()),
                MenuElement.Anchor.RIGHT_BOTTOM, (int)(Constants.TICK_HZ / 2),
                ctc, GameImage.dummy()));

        // windowed
        final GameImage win = GraphicsUtils.uiText(Constants.GREY)
                .addText("Press [Escape] to toggle fullscreen").build().draw();

        mb.add(new AnimationMenuElement(new Coord2D(Constants.TOOL_NAME_X, h),
                new Coord2D(ctc.getWidth(), ctc.getHeight()),
                MenuElement.Anchor.LEFT_BOTTOM, (int)(Constants.TICK_HZ / 2),
                win, GameImage.dummy()));

        // TODO - mb.add(new AnimationMenuElement());

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
                        Constants.DIALOG_CONTENT_SMALL_W_ALLOWANCE, t,
                        false, Constants.GREY)
        ).toArray(GameImage[]::new);
    }

    private static DynamicLabel makeDynamicFromLeftLabel(
            final TextLabel label, final Supplier<String> getter
    ) {
        final Coord2D pos = new Coord2D(Constants.CANVAS_W - label.getX(),
                label.getY());

        return new DynamicLabel(pos, MenuElement.Anchor.RIGHT_TOP,
                Constants.WHITE, getter, Constants.DIALOG_DYNAMIC_W_ALLOWANCE);
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
                Constants.SMALL_TEXT_BOX_W, MenuElement.Anchor.LEFT_TOP,
                "", initial, suffix, textValidator, setter, maxLength);
    }

    private static TextBox makeDialogNameTextBox(
            final TextLabel label, final String initial,
            final Consumer<String> setter
    ) {
        return new TextBox(getDialogContentOffsetFromLabel(label),
                Constants.DIALOG_CONTENT_W_ALLOWANCE, MenuElement.Anchor.LEFT_TOP,
                initial, TextBox::validateAsFileName, setter,
                Constants.MAX_NAME_LENGTH);
    }

    private static TextLabel makeDialogLeftLabel(final int index, final String text) {
        return TextLabel.make(Constants.getDialogContentInitial()
                .displace(0, index * Constants.DIALOG_CONTENT_INC_Y),
                text, Constants.WHITE);
    }

    private static TextLabel makeDialogRightLabel(final TextLabel leftLabel, final String text) {
        return TextLabel.make(leftLabel.getRenderPosition().displace(Constants.DIALOG_W / 2, 0),
                text, Constants.WHITE);
    }

    private static Coord2D getDialogContentOffsetFromLabel(final TextLabel label) {
        return label.getRenderPosition().displace(
                Constants.DIALOG_CONTENT_OFFSET_X,
                Constants.DIALOG_CONTENT_COMP_OFFSET_Y);
    }

    private static Coord2D getDialogContentBigOffsetFromLabel(final TextLabel label) {
        return label.getRenderPosition().displace(
                Constants.DIALOG_CONTENT_BIG_OFFSET_X,
                Constants.DIALOG_CONTENT_COMP_OFFSET_Y);
    }

    private static VerticalScrollingMenuElement assembleScroller(
            final DialogVals.InfoScreen infoScreen
    ) {
        final int dialogW = (int)(Constants.CANVAS_W * 0.7),
                incY = Constants.DIALOG_CONTENT_INC_Y;

        final Coord2D contentStart = new Coord2D(Constants.getCanvasMiddle().x -
                (dialogW / 2) + Constants.TOOL_NAME_X + Constants.BUTTON_BORDER_PX,
                (4 * Constants.STD_TEXT_BUTTON_INC));
        final Set<MenuElement> contentAssembler = new HashSet<>();

        int initialbottomY = 0;

        final double titleSize = 2d;
        final TextLabel headingLabel = TextLabel.make(contentStart.displace(
                0, initialbottomY),
                infoScreen.getTitle(), Constants.BLACK, titleSize);
        initialbottomY += (int)(incY * titleSize) + Constants.BUTTON_INC;

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
                            IconCodes.GENERAL,
                            IconCodes.ON_RESOLUTION,
                            IconCodes.CLIPBOARD_SHORTCUTS,
                            IconCodes.SELECTION_SHORTCUTS,
                            IconCodes.COLOR_SHORTCUTS
                    },
                    new String[] {
                            "Swap primary and secondary color",
                            // TODO - icon color shortcuts: toggle slider vs. palette mode
                            "General",
                            "On resolution",
                            "Clipboard shortcuts",
                            "Selection shortcuts",
                            "Advanced color shortcuts"
                    },
                    contentAssembler, contentStart, initialbottomY
            );
            case CHANGELOG -> assembleInfoScreenContents(
                    new String[] { Constants.CHANGELOG },
                    new String[] { "" },
                    contentAssembler, contentStart, initialbottomY
            );
        };

        final ScrollableMenuElement[] scrollingElements =
                contentAssembler.stream().map(ScrollableMenuElement::new)
                        .toArray(ScrollableMenuElement[]::new);

        final Coord2D wrapperDims = new Coord2D(dialogW -
                (2 * Constants.BUTTON_BORDER_PX),
                (int)(Constants.CANVAS_H * 0.75) - Constants.STD_TEXT_BUTTON_INC);

        // assemble contents into scrolling element
        return new VerticalScrollingMenuElement(contentStart.displace(
                -Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET), wrapperDims,
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

        final int indent = (2 * Constants.BUTTON_INC),
                incY = Constants.DIALOG_CONTENT_INC_Y;

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
                            (hasIcon ? Constants.BUTTON_INC : 0) + Constants.TOOL_NAME_X,
                            bottomY + Constants.TEXT_Y_OFFSET - Constants.BUTTON_BORDER_PX),
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
                                    bottomY + Constants.TEXT_Y_OFFSET),
                            lineSegments[j], j % 2 == 1
                                    ? Constants.HIGHLIGHT_1 : Constants.WHITE);

                    contentAssembler.add(segmentText);
                    offsetX += segmentText.getWidth() + Constants.BUTTON_BORDER_PX;
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
                        IconCodes.UNDO,
                        IconCodes.GRANULAR_UNDO,
                        IconCodes.GRANULAR_REDO,
                        IconCodes.REDO
                },
                new String[] {
                        "Info", "Program Settings",
                        "New Project", "Import", "Save", "Save As...",
                        "Resize", "Pad",
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
        final int dialogW = (int)(Constants.CANVAS_W * 0.7);

        final MenuBuilder mb = new MenuBuilder();

        // background
        final GameImage backgroundImage = new GameImage(dialogW,
                Constants.CANVAS_H - (2 * Constants.BUTTON_DIM));
        backgroundImage.fillRectangle(Constants.ACCENT_BACKGROUND_DARK,
                0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());
        backgroundImage.drawRectangle(Constants.BLACK,
                2f * Constants.BUTTON_BORDER_PX, 0, 0,
                backgroundImage.getWidth(), backgroundImage.getHeight());

        final StaticMenuElement background =
                new StaticMenuElement(Constants.getCanvasMiddle(),
                        MenuElement.Anchor.CENTRAL, backgroundImage.submit());
        mb.add(background);

        // title
        mb.add(TextLabel.make(background.getRenderPosition().displace(
                        Constants.TOOL_NAME_X + Constants.BUTTON_BORDER_PX,
                        Constants.TEXT_Y_OFFSET + Constants.BUTTON_BORDER_PX),
                StippleEffect.PROGRAM_NAME + " v" + StippleEffect.VERSION +
                        "  |  Help & Information", Constants.WHITE));

        // close button
        final GameImage baseImage = GraphicsUtils.drawTextButton(
                Constants.STD_TEXT_BUTTON_W, "Close", false, Constants.GREY),
                highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage);

        final Coord2D cancelPos = background.getRenderPosition()
                .displace(background.getWidth(), background.getHeight())
                .displace(-Constants.TOOL_NAME_X, -Constants.TOOL_NAME_X);

        mb.add(new SimpleMenuButton(cancelPos,
                new Coord2D(baseImage.getWidth(), baseImage.getHeight()),
                MenuElement.Anchor.RIGHT_BOTTOM, true,
                () -> StippleEffect.get().clearDialog(),
                baseImage, highlightedImage));

        // contents
        Arrays.stream(DialogVals.InfoScreen.values()).forEach(is -> {
                    final GameImage baseIS = GraphicsUtils.drawTextButton(
                            Constants.STD_TEXT_BUTTON_W, is.toString(),
                            false, Constants.GREY),
                            highlighedIS = GraphicsUtils.drawHighlightedButton(baseIS);

                    final Coord2D isPos = background.getRenderPosition().displace(
                            Constants.TOOL_NAME_X + (is.ordinal() *
                                    (Constants.STD_TEXT_BUTTON_W + Constants.BUTTON_OFFSET)),
                                    Constants.TOOL_NAME_X +
                                            (int)(1.5 * Constants.STD_TEXT_BUTTON_INC));

                    mb.add(new SimpleMenuButton(isPos,
                            new Coord2D(baseIS.getWidth(), baseIS.getHeight()),
                            MenuElement.Anchor.LEFT_TOP, true,
                            () -> DialogVals.setInfoScreen(is),
                            baseIS, highlighedIS));
                });

        final Map<DialogVals.InfoScreen, VerticalScrollingMenuElement>
                infoScreens = new HashMap<>();

        for (DialogVals.InfoScreen infoScreen : DialogVals.InfoScreen.values()) {
            infoScreens.put(infoScreen, assembleScroller(infoScreen));
        }

        final ThinkingMenuElement screenDecider = new ThinkingMenuElement(
                () -> infoScreens.get(DialogVals.getInfoScreen()));

        mb.add(screenDecider);
        return mb.build();
    }

    private static Menu assembleDialog(
            final String title, final MenuElementGrouping contents,
            final Supplier<Boolean> precondition,
            final String approveText, final Runnable onApproval
    ) {
        final MenuBuilder mb = new MenuBuilder();

        // background
        final GameImage backgroundImage = new GameImage(
                Constants.DIALOG_W, Constants.DIALOG_H);
        backgroundImage.fillRectangle(Constants.ACCENT_BACKGROUND_DARK,
                0, 0, Constants.DIALOG_W, Constants.DIALOG_H);
        backgroundImage.drawRectangle(Constants.BLACK,
                2f * Constants.BUTTON_BORDER_PX, 0, 0,
                Constants.DIALOG_W, Constants.DIALOG_H);

        final StaticMenuElement background =
                new StaticMenuElement(Constants.getCanvasMiddle(),
                        new Coord2D(Constants.DIALOG_W, Constants.DIALOG_H),
                        MenuElement.Anchor.CENTRAL, backgroundImage.submit());
        mb.add(background);

        // title
        mb.add(TextLabel.make(background.getRenderPosition().displace(
                Constants.TOOL_NAME_X + Constants.BUTTON_BORDER_PX,
                        Constants.TEXT_Y_OFFSET + Constants.BUTTON_BORDER_PX),
                title, Constants.WHITE));

        // cancel button
        final GameImage baseImage = GraphicsUtils.drawTextButton(
                Constants.STD_TEXT_BUTTON_W, "Cancel", false, Constants.GREY),
                highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage);

        final Coord2D cancelPos = background.getRenderPosition()
                .displace(background.getWidth(), background.getHeight())
                .displace(-Constants.TOOL_NAME_X, -Constants.TOOL_NAME_X);

        mb.add(new SimpleMenuButton(cancelPos,
                new Coord2D(baseImage.getWidth(), baseImage.getHeight()),
                MenuElement.Anchor.RIGHT_BOTTOM, true,
                () -> StippleEffect.get().clearDialog(),
                baseImage, highlightedImage));

        // approve button
        final Coord2D approvePos = cancelPos.displace(-(baseImage.getWidth() +
                Constants.BUTTON_OFFSET), 0);

        mb.add(new ApproveDialogButton(approvePos,
                new Coord2D(baseImage.getWidth(), baseImage.getHeight()),
                MenuElement.Anchor.RIGHT_BOTTOM, onApproval,
                precondition, approveText));

        mb.add(contents);
        return mb.build();
    }
}
