package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.Menu;
import com.jordanbunke.delta_time.menus.MenuBuilder;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.menus.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.context.ProjectInfo;
import com.jordanbunke.stipple_effect.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.menu_elements.TextLabel;
import com.jordanbunke.stipple_effect.menu_elements.dialog.ApproveDialogButton;
import com.jordanbunke.stipple_effect.menu_elements.scrollable.HorizontalSlider;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class DialogAssembly {
    public static void setDialogToSave() {
        // text labels
        final TextLabel
                folderLabel = makeDialogLeftLabel(0, "Folder: "),
                nameLabel = makeDialogLeftLabel(1, "Name: "),
                scaleUpLabel = makeDialogLeftLabel(2, "Scale Factor: "),
                saveAsTypeLabel = makeDialogLeftLabel(3, "Save As: ");

        // TODO - folder selection file dialog opener
        // TODO - name text box

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

        final Callable<Integer> updateIndexLogic = () -> {
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

        // TODO - frameDims iff PNG_STITCHED

        // content assembly
        final MenuElementGrouping contents = new MenuElementGrouping(
                folderLabel,
                nameLabel,
                scaleUpLabel,
                saveAsTypeLabel,

                scaleUpSlider,
                scaleUpValue,
                saveAsToggle
                // TODO - rest
        );
        setDialog(assembleDialog("Save Project...", contents,
                () -> StippleEffect.get().getContext().getProjectInfo()
                        .hasSaveAssociation(), "Save",
                () -> StippleEffect.get().getContext().getProjectInfo().save()));
    }

    public static void setDialogToResize() {
        // TODO
        final MenuElementGrouping contents = new MenuElementGrouping();
        setDialog(assembleDialog("Resize Canvas...", contents,
                () -> true, Constants.GENERIC_APPROVAL_TEXT, () -> {}));
    }

    public static void setDialogToPad() {
        // TODO
        final MenuElementGrouping contents = new MenuElementGrouping();
        setDialog(assembleDialog("Pad Canvas...", contents,
                () -> true, Constants.GENERIC_APPROVAL_TEXT, () -> {}));
    }

    public static void setDialogToNewProject() {
        // TODO
        final MenuElementGrouping contents = new MenuElementGrouping();
        setDialog(assembleDialog("New Project...", contents,
                () -> true, "Create", () -> {}));
    }

    public static void setDialogToCheckCloseProject(final int index) {
        final GameImage warningText = GraphicsUtils.uiText(Constants.WHITE)
                .addText("All unsaved changes will be lost...").build().draw();
        final StaticMenuElement warning = new StaticMenuElement(
                Constants.getCanvasMiddle(), new Coord2D(warningText.getWidth(),
                warningText.getHeight()), MenuElement.Anchor.CENTRAL, warningText);

        final MenuElementGrouping contents = new MenuElementGrouping(warning);
        setDialog(assembleDialog("Close the project " +
                        StippleEffect.get().getContexts().get(index)
                                .getProjectInfo() + "?", contents,
                () -> true, Constants.GENERIC_APPROVAL_TEXT,
                () -> StippleEffect.get().removeContext(index)));
    }

    public static void setDialogToLayerSettings(final int index) {
        // TODO: for renaming and manually setting opacity
        final MenuElementGrouping contents = new MenuElementGrouping();
        setDialog(assembleDialog(StippleEffect.get().getContext().getState()
                .getLayers().get(index).getName() + "  |  Layer Settings",
                contents, () -> true, Constants.GENERIC_APPROVAL_TEXT, () -> {}));
    }

    private static void setDialog(final Menu dialog) {
        StippleEffect.get().setDialog(dialog);
    }

    private static DynamicLabel makeDynamicFromLeftLabel(
            final TextLabel label, final Callable<String> getter
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

    private static Coord2D getDialogContentOffsetFromLabel(final TextLabel label) {
        return label.getRenderPosition().displace(
                Constants.DIALOG_CONTENT_OFFSET_X,
                Constants.DIALOG_CONTENT_COMP_OFFSET_Y);
    }

    private static Menu assembleDialog(
            final String title, final MenuElementGrouping contents,
            final Callable<Boolean> precondition,
            final String approveText, final Runnable onApproval
    ) {
        // TODO - precondition, text, and behaviour for OK / approval button

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
