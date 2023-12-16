package com.jordanbunke.stipple_effect.utility;

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
import com.jordanbunke.delta_time.menus.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.context.ProjectInfo;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.menu_elements.DynamicTextButton;
import com.jordanbunke.stipple_effect.menu_elements.TextLabel;
import com.jordanbunke.stipple_effect.menu_elements.dialog.ApproveDialogButton;
import com.jordanbunke.stipple_effect.menu_elements.dialog.TextBox;
import com.jordanbunke.stipple_effect.menu_elements.scrollable.HorizontalSlider;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

public class DialogAssembly {
    public static void setDialogToSave() {
        // text labels
        final TextLabel
                folderLabel = makeDialogLeftLabel(0, "Folder: "),
                nameLabel = makeDialogLeftLabel(1, "Name: "),
                scaleUpLabel = makeDialogLeftLabel(2, "Scale Factor: "),
                saveAsTypeLabel = makeDialogLeftLabel(3, "Save As: "),
                xDivsLabel = makeDialogLeftLabel(4, "X frames: "),
                yDivsLabel = makeDialogRightLabel(xDivsLabel, "Y frames: ");

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
                    StippleEffect.get().getContext().getProjectInfo()
                            .setFolder(folder);
                },
                () -> {
                    final StringBuilder folderPathName = new StringBuilder();
                    final String ELLIPSE = "...";

                    Path folder = StippleEffect.get().getContext()
                            .getProjectInfo().getFolder();
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
        final TextBox nameTextBox = new TextBox(
                getDialogContentOffsetFromLabel(nameLabel),
                Constants.DIALOG_CONTENT_W_ALLOWANCE, MenuElement.Anchor.LEFT_TOP,
                StippleEffect.get().getContext().getProjectInfo().getName(),
                TextBox.getFileNameTextValidator(),
                s -> StippleEffect.get().getContext().getProjectInfo().setName(s),
                Constants.MAX_NAME_LENGTH);

        // scale up slider
        final HorizontalSlider scaleUpSlider = new HorizontalSlider(
                getDialogContentOffsetFromLabel(scaleUpLabel),
                Constants.DIALOG_CONTENT_W_ALLOWANCE, MenuElement.Anchor.LEFT_TOP,
                Constants.MIN_SCALE_UP, Constants.MAX_SCALE_UP,
                StippleEffect.get().getContext().getProjectInfo().getScaleUp(),
                su -> StippleEffect.get().getContext()
                        .getProjectInfo().setScaleUp(su));
        scaleUpSlider.updateAssets();

        final DynamicLabel scaleUpValue = makeDynamicFromLeftLabel(
                scaleUpLabel, () -> StippleEffect.get().getContext()
                        .getProjectInfo().getScaleUp() + "x");

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

            behaviours[i] = () -> StippleEffect.get().getContext()
                    .getProjectInfo().setSaveType(next);
        }

        final Supplier<Integer> updateIndexLogic = () -> {
            final ProjectInfo.SaveType saveType = StippleEffect.get()
                    .getContext().getProjectInfo().getSaveType();

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

        // frameDims iff PNG_STITCHED
        final TextBox xDivsTextBox = new TextBox(
                getDialogContentOffsetFromLabel(xDivsLabel),
                Constants.SMALL_TEXT_BOX_W, MenuElement.Anchor.LEFT_TOP,
                String.valueOf(StippleEffect.get().getContext()
                        .getProjectInfo().getFrameDimsX()),
                TextBox.getIntTextValidator(1, Constants.MAX_NUM_FRAMES),
                s -> StippleEffect.get().getContext().getProjectInfo()
                        .setFrameDimsX(Integer.parseInt(s)), 3);
        final TextBox yDivsTextBox = new TextBox(
                getDialogContentOffsetFromLabel(yDivsLabel),
                Constants.SMALL_TEXT_BOX_W, MenuElement.Anchor.LEFT_TOP,
                String.valueOf(StippleEffect.get().getContext()
                        .getProjectInfo().getFrameDimsY()),
                TextBox.getIntTextValidator(1, Constants.MAX_NUM_FRAMES),
                s -> StippleEffect.get().getContext().getProjectInfo()
                        .setFrameDimsY(Integer.parseInt(s)), 3);

        final MenuElementGrouping pngStitchedContents = new MenuElementGrouping(
                xDivsLabel, yDivsLabel, xDivsTextBox, yDivsTextBox);

        // TODO - other additionals

        final ThinkingMenuElement basedOnSaveType = new ThinkingMenuElement(() ->
                switch (StippleEffect.get().getContext().getProjectInfo().getSaveType()) {
            case PNG_STITCHED -> pngStitchedContents;
            case GIF -> null; // TODO
            default -> new PlaceholderMenuElement();
        });

        // content assembly
        final MenuElementGrouping contents = new MenuElementGrouping(
                folderLabel, nameLabel, scaleUpLabel, saveAsTypeLabel,
                folderButton, nameTextBox, scaleUpSlider, scaleUpValue,
                saveAsToggle, basedOnSaveType);
        setDialog(assembleDialog("Save Project...", contents,
                () -> StippleEffect.get().getContext().getProjectInfo()
                        .hasSaveAssociation() && xDivsTextBox.isValid() &&
                        yDivsTextBox.isValid(), "Save",
                () -> StippleEffect.get().getContext().getProjectInfo().save()));
    }

    public static void setDialogToResize() {
        final int w = StippleEffect.get().getContext().getState().getImageWidth(),
                h = StippleEffect.get().getContext().getState().getImageHeight();

        DialogVals.setResizeWidth(w);
        DialogVals.setResizeHeight(h);

        // text labels
        final TextLabel
                widthLabel = makeDialogLeftLabel(1, "Width in px: "),
                heightLabel = makeDialogLeftLabel(2, "Height in px: "),
                context = makeDialogLeftLabel(0, "Current size: " + w + " x " + h),
                explanation = makeDialogLeftLabel(5,
                        "Valid image sizes run from " + Constants.MIN_IMAGE_W +
                                " x " + Constants.MIN_IMAGE_H + " to " +
                                Constants.MAX_IMAGE_W + " x " + Constants.MAX_IMAGE_H + ".");

        // dim textboxes
        final TextBox widthTextBox = new TextBox(
                getDialogContentOffsetFromLabel(widthLabel),
                Constants.SMALL_TEXT_BOX_W,
                MenuElement.Anchor.LEFT_TOP, String.valueOf(w),
                TextBox.getIntTextValidator(Constants.MIN_IMAGE_W, Constants.MAX_IMAGE_W),
                s -> DialogVals.setResizeWidth(Integer.parseInt(s)), 3);
        final TextBox heightTextBox = new TextBox(
                getDialogContentOffsetFromLabel(heightLabel),
                Constants.SMALL_TEXT_BOX_W,
                MenuElement.Anchor.LEFT_TOP, String.valueOf(h),
                TextBox.getIntTextValidator(Constants.MIN_IMAGE_H, Constants.MAX_IMAGE_H),
                s -> DialogVals.setResizeHeight(Integer.parseInt(s)), 3);

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
                Constants.GENERIC_APPROVAL_TEXT,
                () -> StippleEffect.get().getContext().resize()));
    }

    public static void setDialogToPad() {
        final int w = StippleEffect.get().getContext().getState().getImageWidth(),
                h = StippleEffect.get().getContext().getState().getImageHeight();

        // text labels
        final TextLabel
                leftLabel = makeDialogLeftLabel(1, "Pad left: "),
                rightLabel = makeDialogLeftLabel(2, "Pad right: "),
                topLabel = makeDialogLeftLabel(3, "Pad top: "),
                bottomLabel = makeDialogLeftLabel(4, "Pad bottom: "),
                context = makeDialogLeftLabel(0, "Current size: " + w + " x " + h);

        // pad textboxes
        final TextBox leftTextBox = new TextBox(
                getDialogContentOffsetFromLabel(leftLabel),
                Constants.SMALL_TEXT_BOX_W,
                MenuElement.Anchor.LEFT_TOP, String.valueOf(0),
                TextBox.getIntTextValidator(
                        i -> i + DialogVals.getPadRight() + w <= Constants.MAX_IMAGE_W
                ),
                s -> DialogVals.setPadLeft(Integer.parseInt(s)), 3);
        final TextBox topTextBox = new TextBox(
                getDialogContentOffsetFromLabel(topLabel),
                Constants.SMALL_TEXT_BOX_W,
                MenuElement.Anchor.LEFT_TOP, String.valueOf(0),
                TextBox.getIntTextValidator(
                        i -> i + DialogVals.getPadBottom() + h <= Constants.MAX_IMAGE_H
                ),
                s -> DialogVals.setPadTop(Integer.parseInt(s)), 3);
        final TextBox rightTextBox = new TextBox(
                getDialogContentOffsetFromLabel(rightLabel),
                Constants.SMALL_TEXT_BOX_W,
                MenuElement.Anchor.LEFT_TOP, String.valueOf(0),
                TextBox.getIntTextValidator(
                        i -> i + DialogVals.getPadLeft() + w <= Constants.MAX_IMAGE_W
                ),
                s -> DialogVals.setPadRight(Integer.parseInt(s)), 3);
        final TextBox bottomTextBox = new TextBox(
                getDialogContentOffsetFromLabel(bottomLabel),
                Constants.SMALL_TEXT_BOX_W,
                MenuElement.Anchor.LEFT_TOP, String.valueOf(0),
                TextBox.getIntTextValidator(
                        i -> i + DialogVals.getPadTop() + h <= Constants.MAX_IMAGE_H
                ),
                s -> DialogVals.setPadBottom(Integer.parseInt(s)), 3);

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

                    return "Preview size: " + pw + " x " + ph;
                }, Constants.DIALOG_W - (2 * Constants.TOOL_NAME_X)
        );

        final MenuElementGrouping contents = new MenuElementGrouping(
                context, leftLabel, rightLabel, topLabel, bottomLabel, preview,
                leftTextBox, rightTextBox, topTextBox, bottomTextBox
        );
        setDialog(assembleDialog("Pad Canvas...", contents,
                () -> leftTextBox.isValid() && rightTextBox.isValid() &&
                        topTextBox.isValid() && bottomTextBox.isValid(),
                Constants.GENERIC_APPROVAL_TEXT,
                () -> StippleEffect.get().getContext().pad()));
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
                "Current size: " + w + " x " + h + (tooBig
                        ? " ... too big as singleton" : "")),
                instruction = makeDialogLeftLabel(1,
                        "Scale down and/or split into more frames"),
                widthLabel = makeDialogLeftLabel(2, "Width in px: "),
                heightLabel = makeDialogRightLabel(widthLabel, "Height in px: "),
                xDivsLabel = makeDialogLeftLabel(4 - (tooBig ? 0 : 2), "X frames: "),
                yDivsLabel = makeDialogRightLabel(xDivsLabel, "Y frames: ");

        // downscale textboxes
        final TextBox widthTextBox = new TextBox(
                getDialogContentOffsetFromLabel(widthLabel),
                Constants.SMALL_TEXT_BOX_W,
                MenuElement.Anchor.LEFT_TOP, String.valueOf(w),
                TextBox.getIntTextValidator(Constants.MIN_IMAGE_W, w),
                s -> DialogVals.setResizeWidth(Integer.parseInt(s)), 4);
        final TextBox heightTextBox = new TextBox(
                getDialogContentOffsetFromLabel(heightLabel),
                Constants.SMALL_TEXT_BOX_W,
                MenuElement.Anchor.LEFT_TOP, String.valueOf(h),
                TextBox.getIntTextValidator(Constants.MIN_IMAGE_H, h),
                s -> DialogVals.setResizeHeight(Integer.parseInt(s)), 4);

        // division textboxes
        final TextBox xDivsTextBox = new TextBox(
                getDialogContentOffsetFromLabel(xDivsLabel),
                Constants.SMALL_TEXT_BOX_W,
                MenuElement.Anchor.LEFT_TOP, String.valueOf(1),
                TextBox.getIntTextValidator(1, Constants.MAX_NUM_FRAMES),
                s -> DialogVals.setNewProjectXDivs(Integer.parseInt(s)), 3);
        final TextBox yDivsTextBox = new TextBox(
                getDialogContentOffsetFromLabel(yDivsLabel),
                Constants.SMALL_TEXT_BOX_W,
                MenuElement.Anchor.LEFT_TOP, String.valueOf(1),
                TextBox.getIntTextValidator(1, Constants.MAX_NUM_FRAMES),
                s -> DialogVals.setNewProjectYDivs(Integer.parseInt(s)), 3);

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
        setDialog(assembleDialog("Open from file " +
                        filepath.getFileName().toString(), contents,
                precondition, "Import",
                () -> StippleEffect.get().newProjectFromFile(image, filepath)));
    }

    public static void setDialogToNewProject() {
        // text labels
        final TextLabel
                widthLabel = makeDialogLeftLabel(1, "Width in px: "),
                heightLabel = makeDialogLeftLabel(2, "Height in px: "),
                explanation = makeDialogLeftLabel(4,
                        "Valid image sizes run from " + Constants.MIN_IMAGE_W +
                                " x " + Constants.MIN_IMAGE_H + " to " +
                                Constants.MAX_IMAGE_W + " x " + Constants.MAX_IMAGE_H + ".");

        // dim textboxes
        final TextBox widthTextBox = new TextBox(
                getDialogContentOffsetFromLabel(widthLabel),
                Constants.SMALL_TEXT_BOX_W, MenuElement.Anchor.LEFT_TOP,
                String.valueOf(Constants.DEFAULT_IMAGE_W),
                TextBox.getIntTextValidator(Constants.MIN_IMAGE_W, Constants.MAX_IMAGE_W),
                s -> DialogVals.setNewProjectWidth(Integer.parseInt(s)), 3);
        final TextBox heightTextBox = new TextBox(
                getDialogContentOffsetFromLabel(heightLabel),
                Constants.SMALL_TEXT_BOX_W, MenuElement.Anchor.LEFT_TOP,
                String.valueOf(Constants.DEFAULT_IMAGE_H),
                TextBox.getIntTextValidator(Constants.MIN_IMAGE_H, Constants.MAX_IMAGE_H),
                s -> DialogVals.setNewProjectHeight(Integer.parseInt(s)), 3);

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
                        StippleEffect.get().getContexts().get(index).getProjectInfo()
                                .getFormattedName(false, true)
                        + "?", contents,
                () -> true, Constants.GENERIC_APPROVAL_TEXT,
                () -> StippleEffect.get().removeContext(index)));
    }

    public static void setDialogToLayerSettings(final int index) {
        final SELayer layer = StippleEffect.get().getContext()
                .getState().getLayers().get(index);

        DialogVals.setLayerOpacity(layer.getOpacity());
        DialogVals.setLayerName(layer.getName());

        // text labels
        final TextLabel layerNameLabel = makeDialogLeftLabel(1, "Name: "),
                opacityLabel = makeDialogLeftLabel(2, "Opacity: ");

        // name textbox
        final TextBox layerNameTextBox = new TextBox(
                getDialogContentOffsetFromLabel(layerNameLabel),
                Constants.DIALOG_CONTENT_W_ALLOWANCE,
                MenuElement.Anchor.LEFT_TOP, String.valueOf(layer.getName()),
                TextBox.getFileNameTextValidator(),
                DialogVals::setLayerName, Constants.MAX_NAME_LENGTH);

        // opacity slider
        final int MAX_OPACITY = 255;

        final HorizontalSlider opacitySlider = new HorizontalSlider(
                getDialogContentOffsetFromLabel(opacityLabel),
                Constants.DIALOG_CONTENT_W_ALLOWANCE,
                MenuElement.Anchor.LEFT_TOP, 0, MAX_OPACITY,
                (int)(layer.getOpacity() * MAX_OPACITY),
                o -> DialogVals.setLayerOpacity(o / (double) MAX_OPACITY));
        opacitySlider.updateAssets();

        final MenuElementGrouping contents = new MenuElementGrouping(
                layerNameLabel, opacityLabel, layerNameTextBox, opacitySlider);
        setDialog(assembleDialog(layer.getName() + "  |  Layer Settings",
                contents, layerNameTextBox::isValid,
                Constants.GENERIC_APPROVAL_TEXT, () -> {
            StippleEffect.get().getContext().changeLayerOpacity(
                    DialogVals.getLayerOpacity(), index);
            StippleEffect.get().getContext().changeLayerName(
                    DialogVals.getLayerName(), index);
        }));
    }

    private static void setDialog(final Menu dialog) {
        StippleEffect.get().setDialog(dialog);
    }

    private static DynamicLabel makeDynamicFromLeftLabel(
            final TextLabel label, final Supplier<String> getter
    ) {
        final Coord2D pos = new Coord2D(Constants.CANVAS_W - label.getX(),
                label.getY());

        return new DynamicLabel(pos, MenuElement.Anchor.RIGHT_TOP,
                Constants.WHITE, getter, Constants.DIALOG_DYNAMIC_W_ALLOWANCE);
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
