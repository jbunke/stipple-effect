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
import com.jordanbunke.stipple_effect.context.SEContext;
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
                    c.getProjectInfo().setFolder(folder);
                },
                () -> {
                    final StringBuilder folderPathName = new StringBuilder();
                    final String ELLIPSE = "...";

                    Path folder = c.getProjectInfo().getFolder();
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
                c.getProjectInfo().getName(), s -> c.getProjectInfo().setName(s));

        // scale up slider
        final HorizontalSlider scaleUpSlider = new HorizontalSlider(
                getDialogContentOffsetFromLabel(scaleUpLabel),
                Constants.DIALOG_CONTENT_W_ALLOWANCE, MenuElement.Anchor.LEFT_TOP,
                Constants.MIN_SCALE_UP, Constants.MAX_SCALE_UP,
                c.getProjectInfo().getScaleUp(),
                su -> c.getProjectInfo().setScaleUp(su));
        scaleUpSlider.updateAssets();

        final DynamicLabel scaleUpValue = makeDynamicFromLeftLabel(
                scaleUpLabel, () -> c.getProjectInfo().getScaleUp() + "x");

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

            behaviours[i] = () -> c.getProjectInfo().setSaveType(next);
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

        // frameDims iff saveType is PNG_STITCHED
        final TextBox xDivsTextBox = makeDialogNumericTextBox(xDivsLabel,
                c.getProjectInfo().getFrameDimsX(), 1, Constants.MAX_NUM_FRAMES,
                i -> c.getProjectInfo().setFrameDimsX(i), 3);
        final TextBox yDivsTextBox = makeDialogNumericTextBox(yDivsLabel,
                c.getProjectInfo().getFrameDimsY(), 1, Constants.MAX_NUM_FRAMES,
                i -> c.getProjectInfo().setFrameDimsY(i), 3);

        final MenuElementGrouping pngStitchedContents = new MenuElementGrouping(
                xDivsLabel, yDivsLabel, xDivsTextBox, yDivsTextBox);

        // GIF playback speed iff saveType is GIF
        final HorizontalSlider playbackSpeedSlider = new HorizontalSlider(
                getDialogContentOffsetFromLabel(fpsLabel),
                (int)(Constants.DIALOG_CONTENT_W_ALLOWANCE * 0.9),
                MenuElement.Anchor.LEFT_TOP,
                Constants.MIN_MILLIS_PER_FRAME, Constants.MAX_MILLIS_PER_FRAME,
                c.getProjectInfo().getMillisPerFrame(),
                i -> c.getProjectInfo().setMillisPerFrame(i));
        playbackSpeedSlider.updateAssets();

        final DynamicLabel fpsValue = makeDynamicFromLeftLabel(
                fpsLabel, () -> {
                    final int MILLIS_PER_SECOND = 1000;

                    final double fps = (MILLIS_PER_SECOND /
                            (double) c.getProjectInfo().getMillisPerFrame());

                    return (fps < 10d ? ((int)(fps * 10)) / 10d : String.valueOf((int) fps)) + " fps";
                });

        final MenuElementGrouping gifContents = new MenuElementGrouping(
                fpsLabel, playbackSpeedSlider, fpsValue
        );

        final ThinkingMenuElement basedOnSaveType = new ThinkingMenuElement(() ->
                switch (c.getProjectInfo().getSaveType()) {
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
            final boolean enoughFrames = c.getProjectInfo().getFrameDimsX() *
                    c.getProjectInfo().getFrameDimsY() >= c.getState().getFrameCount();
            return c.getProjectInfo().hasSaveAssociation() &&
                    xDivsTextBox.isValid() && yDivsTextBox.isValid() &&
                    enoughFrames;
            }, "Save", () -> c.getProjectInfo().save()));
    }

    public static void setDialogToResize() {
        final SEContext c = StippleEffect.get().getContext();

        final int w = c.getState().getImageWidth(),
                h = c.getState().getImageHeight();

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
        final TextBox widthTextBox = makeDialogNumericTextBox(
                widthLabel, w, Constants.MIN_IMAGE_W, Constants.MAX_IMAGE_W,
                DialogVals::setResizeWidth, 3);
        final TextBox heightTextBox = makeDialogNumericTextBox(
                heightLabel, h, Constants.MIN_IMAGE_H, Constants.MAX_IMAGE_H,
                DialogVals::setResizeHeight, 3);

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
                leftLabel = makeDialogLeftLabel(1, "Pad left: "),
                rightLabel = makeDialogLeftLabel(2, "Pad right: "),
                topLabel = makeDialogLeftLabel(3, "Pad top: "),
                bottomLabel = makeDialogLeftLabel(4, "Pad bottom: "),
                context = makeDialogLeftLabel(0, "Current size: " + w + " x " + h);

        // pad textboxes
        final TextBox leftTextBox = makeDialogNumericTextBox(leftLabel,
                i -> i + DialogVals.getPadRight() + w <= Constants.MAX_IMAGE_W,
                DialogVals::setPadLeft);
        final TextBox topTextBox = makeDialogNumericTextBox(topLabel,
                i -> i + DialogVals.getPadBottom() + h <= Constants.MAX_IMAGE_H,
                DialogVals::setPadTop);
        final TextBox rightTextBox = makeDialogNumericTextBox(rightLabel,
                i -> i + DialogVals.getPadLeft() + w <= Constants.MAX_IMAGE_W,
                DialogVals::setPadRight);
        final TextBox bottomTextBox = makeDialogNumericTextBox(bottomLabel,
                i -> i + DialogVals.getPadTop() + h <= Constants.MAX_IMAGE_H,
                DialogVals::setPadBottom);

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
                "Current size: " + w + " x " + h + (tooBig
                        ? " ... too big as singleton" : "")),
                instruction = makeDialogLeftLabel(1,
                        "Scale down and/or split into more frames"),
                widthLabel = makeDialogLeftLabel(2, "Width in px: "),
                heightLabel = makeDialogRightLabel(widthLabel, "Height in px: "),
                xDivsLabel = makeDialogLeftLabel(4 - (tooBig ? 0 : 2), "X frames: "),
                yDivsLabel = makeDialogRightLabel(xDivsLabel, "Y frames: ");

        // downscale textboxes
        final TextBox widthTextBox = makeDialogNumericTextBox(
                widthLabel, w, Constants.MIN_IMAGE_W, w,
                DialogVals::setResizeWidth, 4);
        final TextBox heightTextBox = makeDialogNumericTextBox(
                heightLabel, h, Constants.MIN_IMAGE_H, h,
                DialogVals::setResizeHeight, 4);

        // division textboxes
        final TextBox xDivsTextBox = makeDialogNumericTextBox(
                xDivsLabel, 1, 1, Constants.MAX_NUM_FRAMES,
                DialogVals::setNewProjectXDivs, 3);
        final TextBox yDivsTextBox = makeDialogNumericTextBox(
                yDivsLabel, 1, 1, Constants.MAX_NUM_FRAMES,
                DialogVals::setNewProjectYDivs, 3);

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
        final TextBox widthTextBox = makeDialogNumericTextBox(
                widthLabel, Constants.DEFAULT_IMAGE_W, Constants.MIN_IMAGE_W,
                Constants.MAX_IMAGE_W, DialogVals::setNewProjectWidth, 3);
        final TextBox heightTextBox = makeDialogNumericTextBox(
                heightLabel, Constants.DEFAULT_IMAGE_H, Constants.MIN_IMAGE_H,
                Constants.MAX_IMAGE_H, DialogVals::setNewProjectHeight, 3);

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
            c.changeLayerOpacity(DialogVals.getLayerOpacity(), index);
            c.changeLayerName(DialogVals.getLayerName(), index);
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

    private static TextBox makeDialogNumericTextBox(
            final TextLabel label,
            final Function<Integer, Boolean> validatorLogic,
            final Consumer<Integer> setter
    ) {
        return makeDialogTextBox(label, Constants.SMALL_TEXT_BOX_W,
                String.valueOf(0), TextBox.getIntTextValidator(validatorLogic),
                s -> setter.accept(Integer.parseInt(s)), 3);
    }

    private static TextBox makeDialogNumericTextBox(
            final TextLabel label, final int initial,
            final int min, final int max,
            final Consumer<Integer> setter, final int maxLength
    ) {
        return makeDialogTextBox(label, Constants.SMALL_TEXT_BOX_W,
                String.valueOf(initial), TextBox.getIntTextValidator(min, max),
                s -> setter.accept(Integer.parseInt(s)), maxLength);
    }

    private static TextBox makeDialogNameTextBox(
            final TextLabel label, final String initial,
            final Consumer<String> setter
    ) {
        return makeDialogTextBox(label, Constants.DIALOG_CONTENT_W_ALLOWANCE,
                initial, TextBox.getFileNameTextValidator(), setter,
                Constants.MAX_NAME_LENGTH);
    }

    private static TextBox makeDialogTextBox(
            final TextLabel label, final int width, final String initial,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter, final int maxLength
    ) {
        return new TextBox(getDialogContentOffsetFromLabel(label),
                width, MenuElement.Anchor.LEFT_TOP, initial,
                textValidator, setter, maxLength);
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
