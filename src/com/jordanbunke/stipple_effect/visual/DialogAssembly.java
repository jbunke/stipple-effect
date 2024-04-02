package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.fonts.FontConstants;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.menus.Menu;
import com.jordanbunke.delta_time.menus.MenuBuilder;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.menus.menu_elements.invisible.GatewayMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.invisible.PlaceholderMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.invisible.ThinkingMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.invisible.TimedMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.visual.AnimationMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.palette.PaletteSorter;
import com.jordanbunke.stipple_effect.project.ProjectInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.selection.Outliner;
import com.jordanbunke.stipple_effect.selection.SEClipboard;
import com.jordanbunke.stipple_effect.selection.SelectionUtils;
import com.jordanbunke.stipple_effect.stip.ParserSerializer;
import com.jordanbunke.stipple_effect.tools.TextTool;
import com.jordanbunke.stipple_effect.tools.Tool;
import com.jordanbunke.stipple_effect.utility.*;
import com.jordanbunke.stipple_effect.utility.math.StitchSplitMath;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.*;
import com.jordanbunke.stipple_effect.visual.menu_elements.dialog.ApproveDialogButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.dialog.DynamicTextbox;
import com.jordanbunke.stipple_effect.visual.menu_elements.dialog.Textbox;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.HorizontalSlider;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.ScrollableMenuElement;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollingMenuElement;

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
                folderLabel = makeDialogLeftLabel(0, "Folder:"),
                nameLabel = makeDialogLeftLabel(1, "File name:"),
                scaleUpLabel = makeDialogLeftLabel(2, "Scale factor:"),
                saveAsTypeLabel = makeDialogLeftLabel(3, "Save as:"),
                xDivsLabel = makeDialogLeftLabel(4, "X frames:"),
                yDivsLabel = makeDialogRightLabel(xDivsLabel, "Y frames:"),
                indexPrefixLabel = makeDialogLeftLabel(4, "Prefix:"),
                indexSuffixLabel = makeDialogRightLabel(indexPrefixLabel, "Suffix:"),
                countFromLabel = makeDialogLeftLabel(5, "Count from:"),
                fpsLabel = makeDialogLeftLabel(4, "Frame rate:");

        // folder selection button
        final DynamicTextButton folderButton = makeFolderSelectionButton(
                folderLabel, c.projectInfo::getFolder, c.projectInfo::setFolder);

        // name text box
        final Textbox nameTextbox = makeDialogNameTextBox(nameLabel,
                c.projectInfo.getName(), c.projectInfo::setName);

        // scale up slider
        final HorizontalSlider scaleUpSlider = new HorizontalSlider(
                getDialogContentOffsetFromLabel(scaleUpLabel),
                Layout.getDialogContentWidthAllowance(), MenuElement.Anchor.LEFT_TOP,
                Constants.MIN_SCALE_UP, Constants.MAX_SCALE_UP,
                c.projectInfo::getScaleUp, c.projectInfo::setScaleUp);
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
        final Textbox xDivsTextbox = makeDialogNumericalTextBox(xDivsLabel,
                c.projectInfo.getFrameDimsX(), 1, Constants.MAX_NUM_FRAMES,
                "", c.projectInfo::setFrameDimsX, 3);
        final Textbox yDivsTextbox = makeDialogNumericalTextBox(yDivsLabel,
                c.projectInfo.getFrameDimsY(), 1, Constants.MAX_NUM_FRAMES,
                "", c.projectInfo::setFrameDimsY, 3);

        final MenuElementGrouping pngStitchedContents = new MenuElementGrouping(
                xDivsLabel, yDivsLabel, xDivsTextbox, yDivsTextbox);

        // GIF playback speed iff saveType is GIF
        final HorizontalSlider playbackSpeedSlider = new HorizontalSlider(
                getDialogContentOffsetFromLabel(fpsLabel),
                (int)(Layout.getDialogContentWidthAllowance() * 0.9),
                MenuElement.Anchor.LEFT_TOP,
                Constants.MIN_PLAYBACK_FPS, Constants.MAX_PLAYBACK_FPS,
                c.projectInfo::getFps, c.projectInfo::setFps);
        playbackSpeedSlider.updateAssets();

        final DynamicLabel fpsValue = makeDynamicFromLeftLabel(
                fpsLabel, () -> c.projectInfo.getFps() + " fps");

        final MenuElementGrouping gifContents = new MenuElementGrouping(
                fpsLabel, playbackSpeedSlider, fpsValue
        );

        // Extra file naming options IFF saveType is PNG_SEPARATE
        final Textbox indexPrefixTextbox = makeDialogCustomTextBox(
                indexPrefixLabel, Layout.SMALL_TEXT_BOX_W,
                DialogAssembly::getDialogContentOffsetFromLabel,
                () -> "", c.projectInfo.getIndexPrefix(), () -> "",
                Textbox::validateAsOptionallyEmptyFilename,
                c.projectInfo::setIndexPrefix, 5);
        final Textbox indexSuffixTextbox = makeDialogCustomTextBox(
                indexSuffixLabel, Layout.SMALL_TEXT_BOX_W,
                DialogAssembly::getDialogContentOffsetFromLabel,
                () -> "", c.projectInfo.getIndexSuffix(), () -> "",
                Textbox::validateAsOptionallyEmptyFilename,
                c.projectInfo::setIndexSuffix, 5);
        final Textbox countFromTextbox = makeDialogCustomTextBox(
                countFromLabel, Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                DialogAssembly::getDialogContentOffsetFromLabel,
                c.projectInfo::getIndexPrefix,
                String.valueOf(c.projectInfo.getCountFrom()),
                c.projectInfo::getIndexSuffix,
                Textbox.getIntTextValidator(0, Constants.ARBITRARY_MAX_COUNT),
                s -> c.projectInfo.setCountFrom(Integer.parseInt(s)), 4);

        final MenuElementGrouping pngSeparateContents = new MenuElementGrouping(
                indexPrefixLabel, indexPrefixTextbox,
                indexSuffixLabel, indexSuffixTextbox,
                countFromLabel, countFromTextbox);

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
                folderButton, nameTextbox, scaleUpSlider, scaleUpValue,
                saveAsToggle, basedOnSaveType);
        setDialog(assembleDialog("Save project...", contents,
                () -> {
            if (c.projectInfo.getSaveType() == ProjectInfo.SaveType.PNG_STITCHED) {
                final boolean enoughFrames = c.projectInfo.getFrameDimsX() *
                        c.projectInfo.getFrameDimsY() >= c.getState().getFrameCount();
                return c.projectInfo.hasSaveAssociation() &&
                        xDivsTextbox.isValid() && yDivsTextbox.isValid() &&
                        enoughFrames;
            }

            return c.projectInfo.hasSaveAssociation();
            }, "Save", c.projectInfo::save, true));
    }

    public static void setDialogToResize() {
        final SEContext c = StippleEffect.get().getContext();

        final int w = c.getState().getImageWidth(),
                h = c.getState().getImageHeight();

        DialogVals.setResizeScale(1d);
        DialogVals.setResizeScaleX(1d);
        DialogVals.setResizeScaleY(1d);

        DialogVals.setResizeWidth(w, w, h, false);
        DialogVals.setResizeHeight(h, w, h, false);

        final MenuBuilder mb = new MenuBuilder();

        // text labels
        final TextLabel context =
                makeDialogLeftLabel(0, "Current size: " + w + "x" + h),
                explanation = makeValidDimensionsBottomLabel();
        mb.addAll(context, explanation);

        final TextLabel preserveAspectRatioLabel =
                TextLabel.make(textBelowPos(context, 1),
                        "Preserve aspect ratio?", Constants.WHITE);
        final Checkbox preserveAspectRatioCheckbox = new Checkbox(
                getDialogContentOffsetFollowingLabel(preserveAspectRatioLabel),
                MenuElement.Anchor.LEFT_TOP,
                DialogVals::isResizePreserveAspectRatio,
                DialogVals::setResizePreserveAspectRatio);
        mb.addAll(preserveAspectRatioLabel, preserveAspectRatioCheckbox);

        final TextLabel resizeTypeLabel =
                TextLabel.make(textBelowPos(preserveAspectRatioLabel),
                        "Resize by:", Constants.WHITE);
        final DropdownMenu resizeByDropdown = DropdownMenu.forDialog(
                getDialogContentOffsetFollowingLabel(resizeTypeLabel),
                EnumUtils.stream(DialogVals.ResizeBy.class)
                        .map(DialogVals.ResizeBy::toString)
                        .toArray(String[]::new),
                EnumUtils.stream(DialogVals.ResizeBy.class)
                        .map(rb -> (Runnable) () -> DialogVals.setResizeBy(rb))
                        .toArray(Runnable[]::new),
                () -> DialogVals.getResizeBy().ordinal());
        mb.addAll(resizeTypeLabel, resizeByDropdown);

        // case 1: resize by scale and preserve aspect ratio
        final TextLabel universalScaleLabel = TextLabel.make(
                textBelowPos(resizeTypeLabel, 1),
                "Scale factor:", Constants.WHITE);
        final Textbox universalScaleTextbox = makeDialogCustomTextBox(
                universalScaleLabel, Layout.SMALL_TEXT_BOX_W,
                DialogAssembly::getDialogContentOffsetFollowingLabel,
                () -> "", String.valueOf(DialogVals.getResizeScale()), () -> "",
                Textbox.getPositiveFloatValidator(),
                s -> DialogVals.setResizeScale(Double.parseDouble(s)), 5);

        // case 2: resize by scale but aspect ratio is free
        final TextLabel scaleXLabel = TextLabel.make(
                textBelowPos(resizeTypeLabel, 1),
                "Scale factor (X):", Constants.WHITE),
                scaleYLabel = makeDialogRightLabel(
                        scaleXLabel, "Scale factor (Y):");
        final Textbox scaleXTextbox = makeDialogCustomTextBox(
                scaleXLabel, Layout.SMALL_TEXT_BOX_W,
                DialogAssembly::getDialogContentOffsetFollowingLabel,
                () -> "", String.valueOf(DialogVals.getResizeScaleX()),
                () -> "", Textbox.getPositiveFloatValidator(),
                s -> DialogVals.setResizeScaleX(Double.parseDouble(s)), 5),
                scaleYTextbox = makeDialogCustomTextBox(
                        scaleYLabel, Layout.SMALL_TEXT_BOX_W,
                        DialogAssembly::getDialogContentOffsetFollowingLabel,
                        () -> "", String.valueOf(DialogVals.getResizeScaleY()),
                        () -> "", Textbox.getPositiveFloatValidator(),
                        s -> DialogVals.setResizeScaleY(Double.parseDouble(s)), 5);

        // case 3: resize by pixels
        final TextLabel widthLabel = TextLabel.make(
                textBelowPos(resizeTypeLabel, 1),
                "Width:", Constants.WHITE),
                heightLabel = makeDialogRightLabel(widthLabel, "Height:");
        final DynamicTextbox widthTextbox =
                makeDialogPixelDynamicTextbox(widthLabel,
                        DialogAssembly::getDialogContentOffsetFollowingLabel,
                        Constants.MIN_CANVAS_W, Constants.MAX_CANVAS_W,
                        rw -> DialogVals.setResizeWidth(rw, w, h,
                                DialogVals.isResizePreserveAspectRatio()),
                        DialogVals::getResizeWidth, 3),
                heightTextbox = makeDialogPixelDynamicTextbox(
                        heightLabel,
                        DialogAssembly::getDialogContentOffsetFollowingLabel,
                        Constants.MIN_CANVAS_H, Constants.MAX_CANVAS_H,
                        rh -> DialogVals.setResizeHeight(rh, w, h,
                                DialogVals.isResizePreserveAspectRatio()),
                        DialogVals::getResizeHeight, 3);

        final ThinkingMenuElement resizeDecider = new ThinkingMenuElement(() -> {
            if (DialogVals.getResizeBy() == DialogVals.ResizeBy.PIXELS)
                return new MenuElementGrouping(widthLabel, heightLabel,
                        widthTextbox, heightTextbox);
            else if (DialogVals.isResizePreserveAspectRatio())
                return new MenuElementGrouping(
                        universalScaleLabel, universalScaleTextbox);

            return new MenuElementGrouping(
                    scaleXLabel, scaleXTextbox, scaleYLabel, scaleYTextbox);
        });
        mb.add(resizeDecider);

        // dynamic scale checker
        final String PREVIEW_PREFIX = "Preview size: ";
        final DynamicLabel preview = makeDynamicLabel(
                textBelowPos(resizeTypeLabel, 3), () -> {
                    final int widthCalc = DialogVals.calculcateResizeWidth(w),
                            heightCalc = DialogVals.calculateResizeHeight(h);
                    return PREVIEW_PREFIX + widthCalc + "x" + heightCalc;
                }, PREVIEW_PREFIX + "XXXXxXXXX");
        mb.add(preview);

        final MenuElementGrouping contents =
                new MenuElementGrouping(mb.build().getMenuElements());
        setDialog(assembleDialog("Resize canvas...", contents, () -> {
                    final int widthCalc = DialogVals.calculcateResizeWidth(w),
                            heightCalc = DialogVals.calculateResizeHeight(h);

                    return widthCalc >= Constants.MIN_CANVAS_W &&
                            widthCalc <= Constants.MAX_CANVAS_W &&
                            heightCalc >= Constants.MIN_CANVAS_H &&
                            heightCalc <= Constants.MAX_CANVAS_H;
                }, Constants.GENERIC_APPROVAL_TEXT, c::resize, true));
    }

    public static void setDialogToPad() {
        final SEContext c = StippleEffect.get().getContext();

        final int w = c.getState().getImageWidth(),
                h = c.getState().getImageHeight();

        DialogVals.setPadLeft(0);
        DialogVals.setPadBottom(0);
        DialogVals.setPadTop(0);
        DialogVals.setPadRight(0);

        final MenuBuilder mb = new MenuBuilder();

        // text labels
        final TextLabel
                context = makeDialogLeftLabel(0, "Current size: " + w + "x" + h),
                leftLabel = TextLabel.make(textBelowPos(context, 1), "Left:", Constants.WHITE),
                rightLabel = TextLabel.make(textBelowPos(leftLabel), "Right:", Constants.WHITE),
                topLabel = TextLabel.make(textBelowPos(rightLabel), "Top:", Constants.WHITE),
                bottomLabel = TextLabel.make(textBelowPos(topLabel), "Bottom:", Constants.WHITE),
                explanation = makeValidDimensionsBottomLabel();
        mb.addAll(context, explanation, leftLabel, rightLabel,
                topLabel, bottomLabel);

        // pad textboxes
        final Textbox leftTextbox = makeDialogPadTextBox(leftLabel, i -> {
            final int pw = i + DialogVals.getPadRight() + w;
            return pw <= Constants.MAX_CANVAS_W && pw > 0;
        }, DialogVals::setPadLeft, DialogVals::getPadLeft);
        final Textbox topTextbox = makeDialogPadTextBox(topLabel, i -> {
            final int ph = i + DialogVals.getPadBottom() + h;
            return ph <= Constants.MAX_CANVAS_H && ph > 0;
        }, DialogVals::setPadTop, DialogVals::getPadTop);
        final Textbox rightTextbox = makeDialogPadTextBox(rightLabel, i -> {
            final int pw = i + DialogVals.getPadLeft() + w;
            return pw <= Constants.MAX_CANVAS_W && pw > 0;
        }, DialogVals::setPadRight, DialogVals::getPadRight);
        final Textbox bottomTextbox = makeDialogPadTextBox(bottomLabel, i -> {
            final int ph = i + DialogVals.getPadTop() + h;
            return ph <= Constants.MAX_CANVAS_W && ph > 0;
        }, DialogVals::setPadBottom, DialogVals::getPadBottom);
        mb.addAll(leftTextbox, rightTextbox, topTextbox, bottomTextbox);

        // size preview
        final String PREVIEW_PREFIX = "Preview size: ";
        final DynamicLabel preview = makeDynamicLabel(
                textBelowPos(bottomLabel, 1), () -> {
                    final int pw = DialogVals.getPadLeft() +
                            DialogVals.getPadRight() + w,
                            ph = DialogVals.getPadTop() +
                                    DialogVals.getPadBottom() + h;

                    return PREVIEW_PREFIX + pw + "x" + ph;
                }, PREVIEW_PREFIX + "XXXXxXXXX");
        mb.add(preview);

        final MenuElementGrouping contents =
                new MenuElementGrouping(mb.build().getMenuElements());
        setDialog(assembleDialog("Pad canvas...", contents,
                () -> leftTextbox.isValid() && rightTextbox.isValid() &&
                        topTextbox.isValid() && bottomTextbox.isValid(),
                Constants.GENERIC_APPROVAL_TEXT, c::pad, true));
    }

    public static void setDialogToStitchFramesTogether() {
        final SEContext c = StippleEffect.get().getContext();
        final MenuBuilder mb = new MenuBuilder();

        final int w = c.getState().getImageWidth(),
                h = c.getState().getImageHeight(),
                fc = c.getState().getFrameCount();

        DialogVals.setFramesPerDim(fc);

        // current frame size
        final TextLabel frameSize = makeDialogLeftLabel(0,
                "Frame size: " + w + "x" + h + " px");
        mb.add(frameSize);

        // sequence order
        final TextLabel sequenceLabel = TextLabel.make(
                textBelowPos(frameSize, 1),
                "Sequence order:", Constants.WHITE);
        final DropdownMenu sequenceDropdown = DropdownMenu.forDialog(
                getDialogContentOffsetFollowingLabel(sequenceLabel),
                EnumUtils.stream(DialogVals.SequenceOrder.class)
                        .map(EnumUtils::formattedName).toArray(String[]::new),
                EnumUtils.stream(DialogVals.SequenceOrder.class)
                        .map(so -> (Runnable) () -> DialogVals.setSequenceOrder(so))
                        .toArray(Runnable[]::new),
                () -> DialogVals.getSequenceOrder().ordinal());
        mb.add(sequenceLabel);
        mb.add(sequenceDropdown);

        // frames per [dim]
        final String FPD_PREFIX = "Frames per ", FPD_SUFFIX = ":";
        final DynamicLabel framesPerDimLabel = makeDynamicLabel(
                textBelowPos(sequenceLabel), () -> FPD_PREFIX +
                        DialogVals.getSequenceOrder().dimName() + FPD_SUFFIX,
                FPD_PREFIX + "column" + FPD_SUFFIX);
        final Textbox framesPerDimTextbox = makeDialogNumericalTextBox(
                framesPerDimLabel,
                DialogAssembly::getDialogContentOffsetFollowingLabel,
                fc, 1, Constants.MAX_NUM_FRAMES, "",
                DialogVals::setFramesPerDim,
                String.valueOf(Constants.MAX_NUM_FRAMES).length());
        mb.add(framesPerDimLabel);
        mb.add(framesPerDimTextbox);

        // frames per complementary dim
        final String FPCD_PREFIX = "... and ", FPCD_INFIX = " frames per ",
                FPCD_INFIX_SING = FPCD_INFIX.replace("s ", " ");
        final DynamicLabel framesPerCompDim = makeDynamicLabel(
                textBelowPos(framesPerDimLabel), () -> {
                    final int comp = DialogVals
                            .calculateFramesPerComplementaryDim(fc);

                    return FPCD_PREFIX + comp + (
                            comp == 1 ? FPCD_INFIX_SING : FPCD_INFIX
                            ) + DialogVals.getSequenceOrder()
                            .complementaryDimName();
                }, FPCD_PREFIX +
                        Constants.MAX_NUM_FRAMES + FPCD_INFIX + "column");
        mb.add(framesPerCompDim);

        final Supplier<Boolean> fTooLarge =
                () -> StitchSplitMath.stitchedWidth(h, fc) > Constants.MAX_CANVAS_W ||
                        StitchSplitMath.stitchedHeight(h, fc) > Constants.MAX_CANVAS_H;

        // canvas size preview
        final String CSP_PREFIX = "Canvas size preview: ", CSP_INFIX = " px",
                CSP_OPT_SUFFIX = "... too large";
        final DynamicLabel canvasSizePreview = makeDynamicLabel(
                textBelowPos(framesPerCompDim, 1),
                () -> CSP_PREFIX + StitchSplitMath.stitchedWidth(w, fc) + "x" +
                        StitchSplitMath.stitchedHeight(h, fc) + CSP_INFIX +
                        (fTooLarge.get() ? CSP_OPT_SUFFIX : ""),
                CSP_PREFIX + "XXXXxXXXX" + CSP_INFIX + CSP_OPT_SUFFIX);
        mb.add(canvasSizePreview);

        final TextLabel maxBoundsLabel = makeValidDimensionsBottomLabel();
        mb.add(maxBoundsLabel);

        final MenuElementGrouping contents =
                new MenuElementGrouping(mb.build().getMenuElements());
        setDialog(assembleDialog("Stitch frames together...", contents,
                () -> framesPerDimTextbox.isValid() && !fTooLarge.get(),
                Constants.GENERIC_APPROVAL_TEXT, c::stitch, true));
    }

    public static void setDialogToSplitCanvasIntoFrames() {
        final SEContext c = StippleEffect.get().getContext();
        final MenuBuilder mb = new MenuBuilder();

        final int w = c.getState().getImageWidth(),
                h = c.getState().getImageHeight();

        DialogVals.setFrameWidth(Settings.getCheckerboardWPixels(), w);
        DialogVals.setFrameHeight(Settings.getCheckerboardHPixels(), h);

        final int initialXDivs = w / DialogVals.getFrameWidth(),
                initialYDivs = h / DialogVals.getFrameHeight();

        DialogVals.setXDivs(initialXDivs);
        DialogVals.setYDivs(initialYDivs);

        // current canvas size
        final TextLabel canvasSize = makeDialogLeftLabel(0,
                "Canvas size: " + w + "x" + h + " px");
        mb.add(canvasSize);

        // sequence order
        final TextLabel sequenceLabel = TextLabel.make(
                textBelowPos(canvasSize, 1),
                "Sequence order:", Constants.WHITE);
        final DropdownMenu sequenceDropdown = DropdownMenu.forDialog(
                getDialogContentOffsetFollowingLabel(sequenceLabel),
                EnumUtils.stream(DialogVals.SequenceOrder.class)
                        .map(EnumUtils::formattedName).toArray(String[]::new),
                EnumUtils.stream(DialogVals.SequenceOrder.class)
                        .map(so -> (Runnable) () -> DialogVals.setSequenceOrder(so))
                        .toArray(Runnable[]::new),
                () -> DialogVals.getSequenceOrder().ordinal());
        mb.addAll(sequenceLabel, sequenceDropdown);

        // X-axis divisions
        final TextLabel xDivsLabel = TextLabel.make(
                textBelowPos(sequenceLabel), "X-axis divisions:",
                Constants.WHITE);
        final DynamicTextbox xDivsTextbox = makeDialogDynamicTextbox(
                xDivsLabel, DialogAssembly::getDialogContentOffsetFollowingLabel,
                1, initialXDivs, Constants.MAX_NUM_FRAMES, "",
                x -> DialogVals.setXDivs(x, w), DialogVals::getXDivs,
                String.valueOf(Constants.MAX_NUM_FRAMES).length());
        mb.addAll(xDivsLabel, xDivsTextbox);

        // Y-axis divisions
        final TextLabel yDivsLabel = makeDialogRightLabel(
                xDivsLabel, "Y-axis divisions:");
        final DynamicTextbox yDivsTextbox = makeDialogDynamicTextbox(
                yDivsLabel, DialogAssembly::getDialogContentOffsetFollowingLabel,
                1, initialYDivs, Constants.MAX_NUM_FRAMES, "",
                y -> DialogVals.setYDivs(y, h), DialogVals::getYDivs,
                String.valueOf(Constants.MAX_NUM_FRAMES).length());
        mb.addAll(yDivsLabel, yDivsTextbox);

        // frame width
        final TextLabel frameWidthLabel = TextLabel.make(
                textBelowPos(xDivsLabel), "Frame width:",
                Constants.WHITE);
        final DynamicTextbox frameWidthTextbox =
                makeDialogPixelDynamicTextbox(frameWidthLabel,
                        DialogAssembly::getDialogContentOffsetFollowingLabel,
                        Constants.MIN_CANVAS_W, Constants.MAX_CANVAS_W,
                        fw -> DialogVals.setFrameWidth(fw, w),
                        DialogVals::getFrameWidth,
                        String.valueOf(Constants.MAX_CANVAS_W).length());
        mb.addAll(frameWidthLabel, frameWidthTextbox);

        // frame height
        final TextLabel frameHeightLabel = makeDialogRightLabel(
                frameWidthLabel, "Frame height:");
        final DynamicTextbox frameHeightTextbox =
                makeDialogPixelDynamicTextbox(frameHeightLabel,
                        DialogAssembly::getDialogContentOffsetFollowingLabel,
                        Constants.MIN_CANVAS_H, Constants.MAX_CANVAS_H,
                        fh -> DialogVals.setFrameHeight(fh, h),
                        DialogVals::getFrameHeight,
                        String.valueOf(Constants.MAX_CANVAS_H).length());
        mb.addAll(frameHeightLabel, frameHeightTextbox);

        final String[] remainderLabels =
                new String[] { "Extra frame", "Truncate" };

        // X-axis remainder
        final TextLabel xRemainderLabel = TextLabel.make(
                textBelowPos(frameWidthLabel), "X-axis remainder:",
                Constants.WHITE);
        final DropdownMenu xRemainderDropdown = DropdownMenu.forDialog(
                getDialogContentOffsetFollowingLabel(xRemainderLabel),
                remainderLabels, new Runnable[] {
                        () -> DialogVals.setTruncateSplitX(false),
                        () -> DialogVals.setTruncateSplitX(true)
                }, () -> DialogVals.isTruncateSplitX() ? 1 : 0);
        final GatewayMenuElement xRemainder = new GatewayMenuElement(
                new MenuElementGrouping(xRemainderLabel, xRemainderDropdown),
                () -> w % DialogVals.getFrameWidth() != 0);
        mb.add(xRemainder);

        // Y-axis remainder
        final TextLabel yRemainderLabel = makeDialogRightLabel(
                xRemainderLabel, "Y-axis remainder:");
        final DropdownMenu yRemainderDropdown = DropdownMenu.forDialog(
                getDialogContentOffsetFollowingLabel(yRemainderLabel),
                remainderLabels, new Runnable[] {
                        () -> DialogVals.setTruncateSplitY(false),
                        () -> DialogVals.setTruncateSplitY(true)
                }, () -> DialogVals.isTruncateSplitY() ? 1 : 0);
        final GatewayMenuElement yRemainder = new GatewayMenuElement(
                new MenuElementGrouping(yRemainderLabel, yRemainderDropdown),
                () -> h % DialogVals.getFrameHeight() != 0);
        mb.add(yRemainder);

        // preview
        final String PRV_PREFIX = "Preview: ",
                PRV_INFIX = " frames of ", PRV_INFIX_SING = " frame of ",
                PRV_SUFFIX_SING = " px", PRV_SUFFIX = PRV_SUFFIX_SING + " each";
        final DynamicLabel dimsPreview = makeDynamicLabel(
                textBelowPos(xRemainderLabel, 1), () -> {
                    final int fc = StitchSplitMath.splitFrameCount(w, h);

                    return PRV_PREFIX + fc +
                            (fc == 1 ? PRV_INFIX_SING : PRV_INFIX) +
                            DialogVals.getFrameWidth() + "x" +
                            DialogVals.getFrameHeight() +
                            (fc == 1 ? PRV_SUFFIX_SING : PRV_SUFFIX);
                },
                PRV_PREFIX + Constants.MAX_NUM_FRAMES + PRV_INFIX +
                        Constants.MAX_CANVAS_W + "x" + Constants.MAX_CANVAS_H + PRV_SUFFIX);
        mb.add(dimsPreview);

        // pixel loss
        final String NO_LOSS = "Perfect split!",
                TRUNC = "truncating ", PAD = "padding ",
                RIGHT = "rightmost ", BOTTOM = "bottommost ",
                PIXELS = " pixels";
        final DynamicLabel pixelLoss = makeDynamicLabel(
                textBelowPos(dimsPreview), () -> {
                    final boolean truncX = DialogVals.isTruncateSplitX(),
                            truncY = DialogVals.isTruncateSplitY();
                    final int fw = DialogVals.getFrameWidth(),
                            fh = DialogVals.getFrameHeight(),
                            remX = w % fw, remY = h % fh,
                            pxCountX = remX == 0 ? 0 :
                                    (truncX ? remX : (fw - remX)),
                            pxCountY = remY == 0 ? 0 :
                                    (truncY ? remY : (fh - remY));
                    final boolean perfectX = remX == 0,
                            perfectY = remY == 0,
                            noLoss = perfectX && perfectY;
                    final String xText = perfectX ? ""
                            : ((truncX ? TRUNC : PAD) +
                                    RIGHT + pxCountX + PIXELS),
                            yText = perfectY ? ""
                                    : ((truncY ? TRUNC : PAD) +
                                    BOTTOM + pxCountY + PIXELS);

                    final String pixelLossText = noLoss ? NO_LOSS : (xText +
                            (!(perfectX || perfectY) ? "; " : "") + yText);
                    return pixelLossText.substring(0, 1).toUpperCase() +
                            pixelLossText.substring(1);
                }, TRUNC + RIGHT + Constants.MAX_CANVAS_W + PIXELS + "; " +
                        TRUNC + BOTTOM + Constants.MAX_CANVAS_H + PIXELS);
        mb.add(pixelLoss);

        final MenuElementGrouping contents =
                new MenuElementGrouping(mb.build().getMenuElements());
        setDialog(assembleDialog("Split canvas into frames...", contents,
                () -> xDivsTextbox.isValid() && yDivsTextbox.isValid() &&
                        frameWidthTextbox.isValid() &&
                        frameHeightTextbox.isValid(),
                Constants.GENERIC_APPROVAL_TEXT, c::split, true));
    }

    // TODO - redesign and refactor
    public static void setDialogToOpenPNG(final GameImage image, final Path filepath) {
        final int w = image.getWidth(), h = image.getHeight();
        final boolean tooBig = w > Constants.MAX_CANVAS_W || h > Constants.MAX_CANVAS_H;

        DialogVals.setResizeWidth(w, w, h, false);
        DialogVals.setResizeHeight(h, w, h, false);

        DialogVals.setNewProjectXDivs(1);
        DialogVals.setNewProjectYDivs(1);

        // text labels
        final TextLabel context = makeDialogLeftLabel(0,
                "Current size: " + w + "x" + h + (tooBig
                        ? " ... too big as singleton" :"")),
                instruction = makeDialogLeftLabel(1,
                        "Scale down and/or split into more frames"),
                widthLabel = makeDialogLeftLabel(2, "Width:"),
                heightLabel = makeDialogRightLabel(widthLabel, "Height:"),
                xDivsLabel = makeDialogLeftLabel(4 - (tooBig ? 0 : 2), "X frames:"),
                yDivsLabel = makeDialogRightLabel(xDivsLabel, "Y frames:"),
                explanation = makeValidDimensionsBottomLabel();

        // downscale textboxes
        final Textbox widthTextbox = makeDialogNumericalTextBox(
                widthLabel, w, Constants.MIN_CANVAS_W, w, "px",
                rw -> DialogVals.setResizeWidth(rw, w, h,
                        DialogVals.isResizePreserveAspectRatio()), 4);
        final Textbox heightTextbox = makeDialogNumericalTextBox(
                heightLabel, h, Constants.MIN_CANVAS_H, h, "px",
                rh -> DialogVals.setResizeHeight(rh, w, h,
                        DialogVals.isResizePreserveAspectRatio()), 4);

        // division textboxes
        final Textbox xDivsTextbox = makeDialogNumericalTextBox(
                xDivsLabel, 1, 1, Constants.MAX_NUM_FRAMES,
                "", DialogVals::setNewProjectXDivs, 3);
        final Textbox yDivsTextbox = makeDialogNumericalTextBox(
                yDivsLabel, 1, 1, Constants.MAX_NUM_FRAMES,
                "", DialogVals::setNewProjectYDivs, 3);

        // wrap downscale components in optional group in case N/A
        final MenuElementGrouping optional = tooBig
                ? new MenuElementGrouping(instruction, widthLabel, heightLabel,
                widthTextbox, heightTextbox, explanation)
                : new MenuElementGrouping();

        // precondition
        final Supplier<Boolean> precondition = () -> {
            final int fw = DialogVals.getResizeWidth() / DialogVals.getNewProjectXDivs(),
                    fh = DialogVals.getResizeHeight() / DialogVals.getNewProjectYDivs();

            final boolean boxesValid = widthTextbox.isValid() &&
                    heightTextbox.isValid() && xDivsTextbox.isValid() &&
                    yDivsTextbox.isValid();

            return boxesValid && fw <= Constants.MAX_CANVAS_W &&
                    fh <= Constants.MAX_CANVAS_H;
        };

        final MenuElementGrouping contents = new MenuElementGrouping(context,
                xDivsLabel, yDivsLabel, xDivsTextbox, yDivsTextbox, optional);
        setDialog(assembleDialog("Import file " +
                        filepath.getFileName().toString(), contents,
                precondition, "Import",
                () -> StippleEffect.get().newProjectFromFile(image, filepath), false));
    }

    public static void setDialogToNewProject() {
        // initial canvas size suggestion determination
        final int NO_CLIPBOARD = 0;

        final int initialW, initialH, clipboardW, clipboardH;
        final boolean hasClipboard = SEClipboard.get().hasContents();

        if (hasClipboard) {
            final Set<Coord2D> clipboard = SEClipboard.get()
                    .getContents().getPixels();
            final Coord2D tl = SelectionUtils.topLeft(clipboard),
                    br = SelectionUtils.bottomRight(clipboard);

            clipboardW = br.x - tl.x;
            clipboardH = br.y - tl.y;

            initialW = clipboardW;
            initialH = clipboardH;
        } else {
            clipboardW = NO_CLIPBOARD;
            clipboardH = NO_CLIPBOARD;

            initialW = Settings.getDefaultCanvasWPixels();
            initialH = Settings.getDefaultCanvasHPixels();
        }

        DialogVals.setNewProjectWidth(initialW);
        DialogVals.setNewProjectHeight(initialH);

        // text labels
        final TextLabel
                presetLabel = makeDialogLeftLabel(0, "Size presets:"),
                widthLabel = TextLabel.make(
                        textBelowPos(presetLabel, 1),
                        "Width:", Constants.WHITE),
                heightLabel = makeDialogRightLabel(widthLabel, "Height:"),
                explanation = makeValidDimensionsBottomLabel();

        // preset buttons
        final SimpleMenuButton defaultPreset =
                GraphicsUtils.makeBespokeTextButton("From default",
                        getDialogContentOffsetFollowingLabel(presetLabel),
                        () -> {
                    DialogVals.setNewProjectWidth(Settings.getDefaultCanvasWPixels());
                    DialogVals.setNewProjectHeight(Settings.getDefaultCanvasHPixels());
                });
        final GatewayMenuElement clipboardPreset = new GatewayMenuElement(
                GraphicsUtils.makeBespokeTextButton("Clipboard contents",
                        defaultPreset.getRenderPosition()
                                .displace(defaultPreset.getWidth() +
                                        Layout.CONTENT_BUFFER_PX, 0), () -> {
                            DialogVals.setNewProjectWidth(clipboardW);
                            DialogVals.setNewProjectHeight(clipboardH);
                }), () -> hasClipboard);

        // dim textboxes
        final DynamicTextbox widthTextbox = makeDialogDynamicTextbox(
                widthLabel, DialogAssembly::getDialogContentOffsetFollowingLabel,
                Constants.MIN_CANVAS_W, initialW, Constants.MAX_CANVAS_W, "px",
                DialogVals::setNewProjectWidth,
                DialogVals::getNewProjectWidth, 3);
        final DynamicTextbox heightTextbox = makeDialogDynamicTextbox(
                heightLabel, DialogAssembly::getDialogContentOffsetFollowingLabel,
                Constants.MIN_CANVAS_H, initialW, Constants.MAX_CANVAS_H, "px",
                DialogVals::setNewProjectHeight,
                DialogVals::getNewProjectHeight, 3);

        final MenuElementGrouping contents = new MenuElementGrouping(
                presetLabel, defaultPreset, clipboardPreset,
                widthLabel, heightLabel, explanation,
                widthTextbox, heightTextbox);
        setDialog(assembleDialog("New project...", contents,
                () -> widthTextbox.isValid() && heightTextbox.isValid(),
                "Create", () -> StippleEffect.get().newProject(), true));
    }

    public static void setDialogToNewFont() {
        DialogVals.setNewFontPixelSpacing(Constants.DEFAULT_FONT_PX_SPACING, false);
        DialogVals.setHasLatinEx(false, false);
        DialogVals.setCharSpecificSpacing(true, false);
        DialogVals.setFontName("");

        DialogVals.resetAscii();
        DialogVals.resetLatinEx();

        DialogVals.setFontPreviewImage(GameImage.dummy());

        final MenuBuilder mb = new MenuBuilder();

        final int LINE_BREAK = 1;
        int lines = 0;

        // specification
        mb.add(makeDialogLeftLabel(lines, "Font source files must be PNGs and " +
                FontConstants.FONT_SOURCE_BASE_WIDTH + "x" +
                FontConstants.FONT_SOURCE_BASE_HEIGHT + " pixels (or a multple thereof)."));
        lines++;

        // import font templates
        final TextLabel importTemplatesLabel = makeDialogLeftLabel(
                lines, "You can use these font templates as references:");
        final SimpleMenuButton importTemplatesButton =
                GraphicsUtils.makeStandardTextButton("Import",
                        getDialogContentOffsetFollowingLabel(importTemplatesLabel),
                        () -> StippleEffect.get().openFontTemplateProjects());
        mb.add(importTemplatesLabel);
        mb.add(importTemplatesButton);
        lines += 1 + LINE_BREAK;

        // name
        final TextLabel nameLabel = makeDialogLeftLabel(lines, "Font name:");
        final Textbox nameTextbox = makeDialogCustomTextBox(nameLabel,
                Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                DialogAssembly::getDialogContentOffsetFollowingLabel,
                () -> "", DialogVals.getFontName(), () -> "",
                Textbox::validateAsFileName, DialogVals::setFontName,
                Constants.MAX_NAME_LENGTH);
        mb.add(nameLabel);
        mb.add(nameTextbox);
        lines++;

        // pixel spacing
        final TextLabel spacingLabel = makeDialogLeftLabel(lines, "Spacing:");
        final Textbox spacingTextbox = makeDialogNumericalTextBox(spacingLabel,
                DialogAssembly::getDialogContentOffsetFollowingLabel,
                DialogVals.getNewFontPixelSpacing(),
                Constants.MIN_FONT_PX_SPACING, Constants.MAX_FONT_PX_SPACING,
                "px", DialogVals::setNewFontPixelSpacing, 2);
        mb.add(spacingLabel);
        mb.add(spacingTextbox);
        // character-specific
        final TextLabel charSpecificLabel =
                makeDialogRightLabel(spacingLabel, "Character-specific spacing");
        final Checkbox charSpecificCheckbox = new Checkbox(
                getDialogContentOffsetFollowingLabel(charSpecificLabel),
                MenuElement.Anchor.LEFT_TOP,
                DialogVals::isCharSpecificSpacing,
                DialogVals::setCharSpecificSpacing);
        mb.add(charSpecificLabel);
        mb.add(charSpecificCheckbox);
        lines += 1 + LINE_BREAK;

        // ASCII
        final TextLabel asciiLabel = makeDialogLeftLabel(
                lines, "ASCII source file:");
        final SimpleMenuButton asciiButton =
                GraphicsUtils.makeStandardTextButton("Upload",
                        getDialogContentOffsetFollowingLabel(asciiLabel),
                        SEFonts::uploadASCIISourceFile);
        final DynamicLabel asciiConfirmation = new DynamicLabel(
                getDialogRightContentPositionForRow(lines),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                () -> DialogVals.getAsciiStatus().getMessage(),
                Layout.getDialogWidth());
        mb.add(asciiLabel);
        mb.add(asciiButton);
        mb.add(asciiConfirmation);
        lines++;

        // latin extended
        final TextLabel latinExQuestion = makeDialogLeftLabel(lines,
                "Does this font support Latin Extended characters?");
        final Checkbox latinExCheckbox = new Checkbox(
                getDialogContentOffsetFollowingLabel(latinExQuestion),
                MenuElement.Anchor.LEFT_TOP,
                DialogVals::hasLatinEx, DialogVals::setHasLatinEx);
        final TextLabel latinExLabel = makeDialogLeftLabel(
                lines + 1, "Latin Extended source file:");
        final SimpleMenuButton latinExButton =
                GraphicsUtils.makeStandardTextButton("Upload",
                        getDialogContentOffsetFollowingLabel(latinExLabel),
                        SEFonts::uploadLatinExtendedSourceFile);
        final DynamicLabel latinExConfirmation = new DynamicLabel(
                getDialogRightContentPositionForRow(lines + 1),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE,
                () -> DialogVals.getLatinExStatus().getMessage(),
                Layout.getDialogWidth());
        final MenuElementGrouping latinExContent = new MenuElementGrouping(
                latinExLabel, latinExButton, latinExConfirmation);
        final GatewayMenuElement latinExGateway = new GatewayMenuElement(
                latinExContent, DialogVals::hasLatinEx);
        mb.add(latinExQuestion);
        mb.add(latinExCheckbox);
        mb.add(latinExGateway);
        lines += 2 + LINE_BREAK;

        // partial precondition
        final Supplier<Boolean> rawFontCondition =
                () -> spacingTextbox.isValid() &&
                        DialogVals.getAsciiStatus().isValid() &&
                        (!DialogVals.hasLatinEx() ||
                                DialogVals.getLatinExStatus().isValid());

        // preview text
        final int previewRow = lines;
        final ThinkingMenuElement previewText = new ThinkingMenuElement(() -> {
            if (rawFontCondition.get())
                return new StaticMenuElement(
                        getDialogLeftContentPositionForRow(previewRow),
                        MenuElement.Anchor.LEFT_TOP,
                        DialogVals.getFontPreviewImage()
                );
            else
                return new PlaceholderMenuElement();
        });
        mb.add(previewText);

        final Supplier<Boolean> precondition =
                () -> nameTextbox.isValid() && rawFontCondition.get();
        final MenuElementGrouping contents =
                new MenuElementGrouping(mb.build().getMenuElements());
        setDialog(assembleDialog("Define a new font...", contents,
                precondition, "Add",
                () -> TextTool.get().addFont(), true));
    }

    public static void setDialogToExitProgramAYS() {
        setDialogToAYS("Exit " + StippleEffect.PROGRAM_NAME + "?",
                "All open projects will be closed without saving...",
                () -> StippleEffect.get().exitProgram());
    }

    public static void setDialogToCloseProjectAYS(final int index) {
        setDialogToAYS("Close the project \"" + StippleEffect.get()
                        .getContexts().get(index).projectInfo
                        .getFormattedName(false, true) + "\"?",
                "All unsaved changes will be lost...",
                () -> StippleEffect.get().removeContext(index));
    }

    private static void setDialogToAYS(
            final String actionLabel, final String consequence,
            final Runnable onApprove
    ) {
        final GameImage warningText = GraphicsUtils.uiText(Constants.WHITE)
                .addText(consequence).build().draw();
        final StaticMenuElement warning = new StaticMenuElement(
                Layout.getCanvasMiddle(), new Coord2D(warningText.getWidth(),
                warningText.getHeight()), MenuElement.Anchor.CENTRAL, warningText);

        final MenuElementGrouping contents = new MenuElementGrouping(warning);
        setDialog(assembleDialog(actionLabel, contents, () -> true,
                Constants.GENERIC_APPROVAL_TEXT, onApprove, true));
    }

    public static void setDialogToOutline() {
        final SEContext c = StippleEffect.get().getContext();
        final MenuBuilder mb = new MenuBuilder();

        // presets: single & double
        final TextLabel presets = makeDialogLeftLabel(0, "Presets:");
        mb.add(presets);

        // no selection notification
        if (!c.getState().hasSelection())
            mb.add(makeDialogLeftLabelAtBottom("Cannot outline; nothing is selected"));

        // buttons for setting presets
        final SimpleMenuButton singlePreset =
                GraphicsUtils.makeStandardTextButton("Single",
                getDialogContentOffsetFromLabel(presets),
                        () -> DialogVals.setOutlineSideMask(
                                Outliner.getSingleOutlineMask())),
                doublePreset = GraphicsUtils.makeStandardTextButton("Double",
                        getDialogContentBigOffsetFromLabel(presets),
                        () -> DialogVals.setOutlineSideMask(
                                Outliner.getDoubleOutlineMask()));
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

    public static void setDialogToPanelManager() {
        final MenuBuilder mb = new MenuBuilder();

        // presets: single & double
        final TextLabel presets = makeDialogLeftLabel(0, "Presets:");
        mb.add(presets);

        // buttons for panel arrangement presets
        final Coord2D showAllPos = getDialogContentOffsetFromLabel(presets),
                minimalUIPos = getDialogContentBigOffsetFromLabel(presets),
                noAnimationPos = minimalUIPos.displace(
                        minimalUIPos.x - showAllPos.x, 0);

        final SimpleMenuButton showAllPreset =
                GraphicsUtils.makeStandardTextButton(
                        "All", showAllPos, Layout::showAllPanels),
                minimalUIPreset = GraphicsUtils.makeStandardTextButton(
                        "Minimal", minimalUIPos, Layout::minimalUI),
                noAnimationPreset = GraphicsUtils.makeStandardTextButton(
                        "No Anim", noAnimationPos,
                        () -> Layout.adjustPanels(() -> {
                            Layout.setToolbarShowing(true);
                            Layout.setColorsPanelShowing(true);
                            Layout.setLayersPanelShowing(true);
                            Layout.setFramesPanelShowing(false);
                            Layout.setProjectsExpanded(false);
                        }));
        mb.add(showAllPreset);
        mb.add(minimalUIPreset);
        mb.add(noAnimationPreset);

        // vars
        final String TOOLBAR = "Toolbar", LAYERS = "Layers",
                COLORS = "Colors", FRAMES = "Frames", PROJECTS = "Projects";
        final int initialIndex = 2;
        final String[] labelTexts = new String[] { TOOLBAR,
                FRAMES, LAYERS, COLORS, PROJECTS };
        final Map<String, Consumer<Boolean>> adjustmentFunctionMap =
                Map.ofEntries(
                        Map.entry(TOOLBAR, Layout::setToolbarShowing),
                        Map.entry(FRAMES, Layout::setFramesPanelShowing),
                        Map.entry(LAYERS, Layout::setLayersPanelShowing),
                        Map.entry(COLORS, Layout::setColorsPanelShowing),
                        Map.entry(PROJECTS, Layout::setProjectsExpanded));
        final Map<String, Supplier<Boolean>> retrievalFunctionMap =
                Map.ofEntries(
                        Map.entry(TOOLBAR, Layout::isToolbarShowing),
                        Map.entry(FRAMES, Layout::isFramesPanelShowing),
                        Map.entry(LAYERS, Layout::isLayersPanelShowing),
                        Map.entry(COLORS, Layout::isColorsPanelShowing),
                        Map.entry(PROJECTS, Layout::isProjectsExpanded));

        for (int i = 0; i < labelTexts.length; i++) {
            final boolean isProject = labelTexts[i].equals(PROJECTS);

            // panel label
            final TextLabel label = makeDialogLeftLabel(
                    initialIndex + i, labelTexts[i] + ":");

            // panel toggle
            final String[] toggleText = isProject
                    ? new String[] { "Expanded", "Collapsed" }
                    : new String[] { "Visible", "Hidden" };
            final GameImage[] bases = makeToggleButtonSet(toggleText);
            final Consumer<Boolean> adj =
                    adjustmentFunctionMap.get(labelTexts[i]);
            final Supplier<Boolean> ret =
                    retrievalFunctionMap.get(labelTexts[i]);

            final SimpleToggleMenuButton toggle = new SimpleToggleMenuButton(
                    getDialogContentOffsetFromLabel(label),
                    new Coord2D(Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                            Layout.STD_TEXT_BUTTON_H),
                    MenuElement.Anchor.LEFT_TOP, true,
                    bases, Arrays.stream(bases)
                    .map(GraphicsUtils::drawHighlightedButton)
                    .toArray(GameImage[]::new),
                    new Runnable[] {
                            () -> Layout.adjustPanels(() -> adj.accept(false)),
                            () -> Layout.adjustPanels(() -> adj.accept(true))
                    },
                    () -> ret.get() ? 0 : 1, () -> {});

            if (isProject) {
                mb.add(new GatewayMenuElement(
                        new MenuElementGrouping(label, toggle),
                        () -> !Layout.isFramesPanelShowing()));
            } else {
                mb.add(label);
                mb.add(toggle);
            }
        }

        final MenuElementGrouping contents =
                new MenuElementGrouping(mb.build().getMenuElements());
        setDialog(assembleDialog("Panel Manager", contents, () -> false,
                Constants.CLOSE_DIALOG_TEXT, () -> {}, true));
    }

    public static void setDialogToSavePalette(final Palette palette) {
        final MenuBuilder mb = new MenuBuilder();

        // labels
        final TextLabel
                folderLabel = makeDialogLeftLabel(0, "Folder:"),
                nameLabel = makeDialogLeftLabel(1, "File name:");

        mb.add(folderLabel);
        mb.add(nameLabel);

        // folder button
        final DynamicTextButton folderButton =
                makeFolderSelectionButton(folderLabel,
                        DialogVals::getPaletteFolder,
                        DialogVals::setPaletteFolder);
        mb.add(folderButton);

        // name text box
        final String suggestedFilename = palette.getName().toLowerCase()
                .replaceAll(" ", "_");
        DialogVals.setPaletteName(suggestedFilename);
        final Textbox nameTextbox = makeDialogNameTextBox(nameLabel,
                suggestedFilename, DialogVals::setPaletteName);
        mb.add(nameTextbox);

        final MenuElementGrouping contents =
                new MenuElementGrouping(mb.build().getMenuElements());
        setDialog(assembleDialog("Save palette...",
                contents, () -> nameTextbox.isValid() &&
                        DialogVals.getPaletteFolder() != null, "Save",
                () -> {
                    final Path filepath = DialogVals.getPaletteFolder()
                            .resolve(DialogVals.getPaletteName() +
                                    "." + Constants.PALETTE_FILE_SUFFIX);
                    ParserSerializer.savePalette(palette, filepath);
                }, true));
    }

    public static void setDialogToSortPalette(final Palette palette) {
        final MenuBuilder mb = new MenuBuilder();
        final PaletteSorter[] vs = PaletteSorter.values();

        // label
        final TextLabel label = makeDialogLeftLabel(0, "Sort colors by:");

        // toggle
        final GameImage[] bases = makeToggleButtonSet(Arrays.stream(vs)
                .map(PaletteSorter::toString).toArray(String[]::new));

        final Runnable[] behaviours = new Runnable[vs.length];
        Arrays.fill(behaviours, (Runnable) () -> {});

        final SimpleToggleMenuButton toggle = new SimpleToggleMenuButton(
                getDialogContentOffsetFromLabel(label),
                new Coord2D(Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                        Layout.STD_TEXT_BUTTON_H),
                MenuElement.Anchor.LEFT_TOP, true,
                bases, Arrays.stream(bases)
                .map(GraphicsUtils::drawHighlightedButton)
                .toArray(GameImage[]::new), behaviours,
                () -> DialogVals.getPaletteSorter().ordinal(),
                DialogVals::cyclePaletteSorter);

        mb.add(label);
        mb.add(toggle);

        final MenuElementGrouping contents =
                new MenuElementGrouping(mb.build().getMenuElements());
        setDialog(assembleDialog(palette.getName() + " | Sort colors",
                contents, () -> true, "Sort", () -> {
                    palette.sort(DialogVals.getPaletteSorter());
                    StippleEffect.get().rebuildColorsMenu();
                }, true));
    }

    public static void setDialogToAddContentsToPalette(final Palette palette) {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        contentTypeCycleToggle(mb, c);

        final MenuElementGrouping contents =
                new MenuElementGrouping(mb.build().getMenuElements());
        setDialog(assembleDialog(palette.getName() + " | Add colors in project to palette",
                contents, () -> true, "Proceed", () -> {
                    c.contentsToPalette(palette);
                    StippleEffect.get().rebuildColorsMenu();
                }, true));
    }

    public static void setDialogToPalettize(final Palette palette) {
        final MenuBuilder mb = new MenuBuilder();
        final SEContext c = StippleEffect.get().getContext();

        contentTypeCycleToggle(mb, c);

        if (palette.size() == 0)
            mb.add(makeDialogLeftLabelAtBottom(
                    "This palette is empty; palettization will be trivial."));

        final MenuElementGrouping contents =
                new MenuElementGrouping(mb.build().getMenuElements());
        setDialog(assembleDialog(palette.getName() + " | Palettize project contents",
                contents, () -> true, "Proceed",
                () -> c.palettize(palette), true));
    }

    private static void contentTypeCycleToggle(
            final MenuBuilder mb, final SEContext c
    ) {
        final DialogVals.ContentType[] vs = DialogVals.ContentType.values();

        // label
        final TextLabel label = makeDialogLeftLabel(0, "Scope:");

        // toggle
        final GameImage[] bases = makeToggleButtonSet(Arrays.stream(vs)
                .map(DialogVals.ContentType::toString).toArray(String[]::new));

        final Runnable[] behaviours = new Runnable[vs.length];
        Arrays.fill(behaviours, (Runnable) () -> {});

        final SimpleToggleMenuButton toggle = new SimpleToggleMenuButton(
                getDialogContentOffsetFromLabel(label),
                new Coord2D(Layout.DIALOG_CONTENT_SMALL_W_ALLOWANCE,
                        Layout.STD_TEXT_BUTTON_H),
                MenuElement.Anchor.LEFT_TOP, true,
                bases, Arrays.stream(bases)
                .map(GraphicsUtils::drawHighlightedButton)
                .toArray(GameImage[]::new), behaviours,
                () -> DialogVals.getContentType(c).ordinal(),
                () -> DialogVals.cycleContentType(c));

        mb.add(label);
        mb.add(toggle);
    }

    public static void setDialogToPaletteSettings(final Palette palette) {
        DialogVals.setPaletteName(palette.getName());

        // text labels
        final TextLabel paletteNameLabel = makeDialogLeftLabel(1, "Name:");

        // name textbox
        DialogVals.setPaletteName(palette.getName());
        final Textbox paletteNameTextbox = makeDialogNameTextBox(
                paletteNameLabel, palette.getName(), DialogVals::setPaletteName);

        final MenuElementGrouping contents = new MenuElementGrouping(
                paletteNameLabel, paletteNameTextbox);
        setDialog(assembleDialog(palette.getName() + "  |  Palette Settings",
                contents, paletteNameTextbox::isValid,
                Constants.GENERIC_APPROVAL_TEXT,
                () -> {
                    palette.setName(DialogVals.getPaletteName());

                    StippleEffect.get().rebuildColorsMenu();
                }, true));
    }

    public static void setDialogToInfo() {
        setDialog(assembleInfoDialog());
    }

    public static void setDialogToProgramSettings() {
        final MenuBuilder mb = new MenuBuilder();

        Settings.initializeMenu();

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
        setDialog(assembleDialog("Program Settings", contents, () -> true,
                "Apply", Settings::apply, true));
    }

    public static void setDialogToLayerSettings(final int index) {
        final SEContext c = StippleEffect.get().getContext();
        final SELayer layer = c.getState().getLayers().get(index);

        DialogVals.setLayerOpacity(layer.getOpacity());
        DialogVals.setLayerName(layer.getName());

        // text labels
        final TextLabel layerNameLabel = makeDialogLeftLabel(1, "Name:"),
                opacityLabel = makeDialogLeftLabel(2, "Opacity:");

        // name textbox
        final Textbox layerNameTextbox = makeDialogNameTextBox(
                layerNameLabel, layer.getName(), DialogVals::setLayerName);

        // opacity slider
        final int MAX_OPACITY = 255;
        DialogVals.setLayerOpacity(layer.getOpacity());

        final HorizontalSlider opacitySlider = new HorizontalSlider(
                getDialogContentOffsetFromLabel(opacityLabel),
                Layout.getDialogContentWidthAllowance(),
                MenuElement.Anchor.LEFT_TOP, 0, MAX_OPACITY,
                () -> (int)(DialogVals.getLayerOpacity() * MAX_OPACITY),
                o -> DialogVals.setLayerOpacity(o / (double) MAX_OPACITY));
        opacitySlider.updateAssets();

        // opacity value
        final DynamicLabel opacityValue = makeDynamicFromLeftLabel(opacityLabel,
                () -> String.valueOf((int)(DialogVals.getLayerOpacity() * MAX_OPACITY)));

        final MenuElementGrouping contents = new MenuElementGrouping(
                layerNameLabel, opacityLabel, layerNameTextbox,
                opacitySlider, opacityValue);
        setDialog(assembleDialog(layer.getName() + "  |  Layer Settings",
                contents, layerNameTextbox::isValid,
                Constants.GENERIC_APPROVAL_TEXT, () -> {
            c.changeLayerOpacity(DialogVals.getLayerOpacity(), index, true);
            c.changeLayerName(DialogVals.getLayerName(), index);
        }, true));
    }

    public static void setDialogToSplashScreen() {
        final MenuBuilder mb = new MenuBuilder();

        // timer
        mb.add(new TimedMenuElement(
                (int)(Constants.SPLASH_TIMEOUT_SECS * Constants.TICK_HZ),
                () -> StippleEffect.get().clearDialog()));

        final int w = Layout.width(), h = Layout.height();

        // background
        final GameImage background = new GameImage(w, h);
        background.free();

        background.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0, w, h);
        mb.add(new SimpleMenuButton(new Coord2D(), new Coord2D(w, h),
                MenuElement.Anchor.LEFT_TOP, true,
                () -> StippleEffect.get().clearDialog(), background, background));

        // version
        final GameImage version = GraphicsUtils.uiText(
                Constants.ACCENT_BACKGROUND_LIGHT)
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
                MenuElement.Anchor.CENTRAL, 5, frames));

        // subtitle
        final GameImage subtitle = GraphicsUtils.uiText(
                        Constants.ACCENT_BACKGROUND_LIGHT)
                .addText("Pixel art editor and animator").addLineBreak()
                .addText("Jordan Bunke, 2023-2024")
                .build().draw();

        mb.add(new StaticMenuElement(new Coord2D(w / 2, h - (version.getHeight() * 2)),
                new Coord2D(subtitle.getWidth(), subtitle.getHeight()),
                MenuElement.Anchor.CENTRAL_BOTTOM, subtitle));

        setDialog(mb.build());
    }

    private static void setDialog(final Menu dialog) {
        StippleEffect.get().setDialog(dialog);
    }

    private static DynamicTextButton makeFolderSelectionButton(
            final TextLabel label,
            final Supplier<Path> getter, final Consumer<Path> setter
    ) {
        return new DynamicTextButton(
                getDialogContentOffsetFromLabel(label),
                Layout.getDialogContentWidthAllowance(),
                MenuElement.Anchor.LEFT_TOP,
                () -> {
                    FileIO.setDialogToFoldersOnly();
                    final Optional<File> opened = FileIO.openFileFromSystem();

                    if (opened.isEmpty())
                        return;

                    final Path folder = opened.get().toPath();
                    setter.accept(folder);
                    // c.projectInfo.setFolder(folder);
                },
                () -> {
                    final StringBuilder folderPathName = new StringBuilder();
                    final String ELLIPSE = "...";

                    Path folder = getter.get(); // c.projectInfo.getFolder();
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
            final MenuElement label, final Supplier<String> getter
    ) {
        final Coord2D pos = new Coord2D(
                Layout.getDialogContentRightBound(), label.getY());

        return new DynamicLabel(pos, MenuElement.Anchor.RIGHT_TOP,
                Constants.WHITE, getter, Layout.DIALOG_DYNAMIC_W_ALLOWANCE);
    }

    private static DynamicTextbox makeDialogPixelDynamicTextbox(
            final MenuElement label,
            final Function<MenuElement, Coord2D> offsetFunction,
            final int min, final int max,
            final Consumer<Integer> setter, final Supplier<Integer> getter,
            final int maxLength
    ) {
        return makeDialogDynamicTextbox(label, offsetFunction, min,
                getter.get(), max, "px", setter, getter, maxLength);
    }

    private static DynamicTextbox makeDialogDynamicTextbox(
            final MenuElement label,
            final Function<MenuElement, Coord2D> offsetFunction,
            final int min, final int initial, final int max,
            final String suffix,
            final Consumer<Integer> setter, final Supplier<Integer> getter,
            final int maxLength
    ) {
        return new DynamicTextbox(offsetFunction.apply(label),
                Layout.SMALL_TEXT_BOX_W, MenuElement.Anchor.LEFT_TOP,
                "", String.valueOf(initial), suffix,
                Textbox.getIntTextValidator(min, max),
                s -> setter.accept(Integer.parseInt(s)),
                () -> String.valueOf(getter.get()), maxLength);
    }

    private static DynamicTextbox makeDialogAffixDynamicTextbox(
            final MenuElement label, final Consumer<String> setter,
            final Supplier<String> getter
    ) {
        return new DynamicTextbox(
                getDialogContentOffsetFollowingLabel(label),
                Layout.SMALL_TEXT_BOX_W, MenuElement.Anchor.LEFT_TOP,
                "", getter.get(), "",
                Textbox::validateAsOptionallyEmptyFilename,
                setter, getter, 5);
    }

    private static DynamicTextbox makeDialogPadTextBox(
            final MenuElement label,
            final Function<Integer, Boolean> validatorLogic,
            final Consumer<Integer> setter, final Supplier<Integer> getter
    ) {
        return new DynamicTextbox(
                getDialogContentOffsetFromLabel(label),
                Layout.SMALL_TEXT_BOX_W, MenuElement.Anchor.LEFT_TOP,
                "", String.valueOf(0), "px",
                Textbox.getIntTextValidator(validatorLogic),
                s -> setter.accept(Integer.parseInt(s)),
                () -> String.valueOf(getter.get()), 4);
    }

    private static Textbox makeDialogNumericalTextBox(
            final MenuElement label, final int initial,
            final int min, final int max, final String suffix,
            final Consumer<Integer> setter, final int maxLength
    ) {
        return makeDialogNumericalTextBox(label,
                DialogAssembly::getDialogContentOffsetFromLabel,
                initial, min, max, suffix, setter, maxLength);
    }

    private static Textbox makeDialogNumericalTextBox(
            final MenuElement label,
            final Function<MenuElement, Coord2D> offsetFunction,
            final int initial, final int min, final int max,
            final String suffix, final Consumer<Integer> setter,
            final int maxLength
    ) {
        return makeDialogNumericalTextBox(label, offsetFunction,
                String.valueOf(initial), suffix,
                Textbox.getIntTextValidator(min, max),
                s -> setter.accept(Integer.parseInt(s)), maxLength);
    }

    private static Textbox makeDialogNumericalTextBox(
            final MenuElement label,
            final Function<MenuElement, Coord2D> offsetFunction,
            final String initial, final String suffix,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter, final int maxLength
    ) {
        return new Textbox(offsetFunction.apply(label),
                Layout.SMALL_TEXT_BOX_W, MenuElement.Anchor.LEFT_TOP,
                "", initial, suffix, textValidator, setter, maxLength);
    }

    private static Textbox makeDialogCustomTextBox(
            final MenuElement label, final int width,
            final Function<MenuElement, Coord2D> offsetFunction,
            final Supplier<String> prefixGetter, final String initial,
            final Supplier<String> suffixGetter,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter, final int length
    ) {
        return new Textbox(offsetFunction.apply(label), width,
                MenuElement.Anchor.LEFT_TOP, prefixGetter, initial, suffixGetter,
                textValidator, setter, () -> Constants.GREY, length);
    }

    private static Textbox makeDialogNameTextBox(
            final MenuElement label, final String initial,
            final Consumer<String> setter
    ) {
        return new Textbox(getDialogContentOffsetFromLabel(label),
                Layout.getDialogContentWidthAllowance(), MenuElement.Anchor.LEFT_TOP,
                initial, Textbox::validateAsFileName, setter,
                Constants.MAX_NAME_LENGTH);
    }

    private static DynamicLabel makeDynamicLabel(
            final Coord2D position, final Supplier<String> getter,
            final String widestTextCase
    ) {
        return new DynamicLabel(position,
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE, getter,
                Layout.estimateDynamicLabelMaxWidth(widestTextCase));
    }

    private static TextLabel makeValidDimensionsBottomLabel() {
        return makeDialogLeftLabelAtBottom("(Canvas sizes can range from " +
                Constants.MIN_CANVAS_W + "x" + Constants.MIN_CANVAS_H + " to " +
                Constants.MAX_CANVAS_W + "x" + Constants.MAX_CANVAS_H + ")");
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
        return TextLabel.make(getDialogLeftContentPositionForRow(index),
                text, Constants.WHITE);
    }

    private static TextLabel makeDialogRightLabel(final TextLabel leftLabel, final String text) {
        return TextLabel.make(getRightColumnFromLeftDisplacement(
                leftLabel.getRenderPosition()), text, Constants.WHITE);
    }

    private static Coord2D textBelowPos(final MenuElement reference) {
        return textBelowPos(reference, 0);
    }

    private static Coord2D textBelowPos(
            final MenuElement reference, final int lineBreaks
    ) {
        return reference.getRenderPosition().displace(0,
                Layout.DIALOG_CONTENT_INC_Y * (1 + lineBreaks));
    }

    private static Coord2D getDialogLeftContentPositionForRow(final int index) {
        return Layout.getDialogContentInitial()
                .displace(0, index * Layout.DIALOG_CONTENT_INC_Y);
    }

    private static Coord2D getDialogRightContentPositionForRow(final int index) {
        return getRightColumnFromLeftDisplacement(
                getDialogLeftContentPositionForRow(index));
    }

    private static Coord2D getRightColumnFromLeftDisplacement(final Coord2D left) {
        return left.displace(Layout.getDialogWidth() / 2, 0);
    }

    private static Coord2D getDialogContentOffsetFromLabel(final MenuElement label) {
        return label.getRenderPosition().displace(
                Layout.DIALOG_CONTENT_OFFSET_X,
                Layout.DIALOG_CONTENT_COMP_OFFSET_Y);
    }

    private static Coord2D getDialogContentBigOffsetFromLabel(final MenuElement label) {
        return label.getRenderPosition().displace(
                Layout.DIALOG_CONTENT_BIG_OFFSET_X,
                Layout.DIALOG_CONTENT_COMP_OFFSET_Y);
    }

    private static Coord2D getDialogContentOffsetFollowingLabel(final MenuElement label) {
        return label.getRenderPosition().displace(
                label.getWidth() + Layout.CONTENT_BUFFER_PX,
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
            case DEFAULT -> {
                // text labels
                final TextLabel fullscreenLabel = makeDialogLeftLabel(
                        initialYIndex, "Fullscreen on startup:"),
                        pixelGridDefaultLabel = TextLabel.make(
                                textBelowPos(fullscreenLabel, 1),
                                "Pixel grid on by default:", Constants.WHITE),
                        defaultNewProjectSizeLabel = TextLabel.make(
                                textBelowPos(pixelGridDefaultLabel, 1),
                                "Default canvas size for new projects",
                                Constants.WHITE),
                        newProjectWidthLabel = TextLabel.make(
                                textBelowPos(defaultNewProjectSizeLabel),
                                "Width:", Constants.WHITE),
                        newProjectHeightLabel = makeDialogRightLabel(
                                newProjectWidthLabel, "Height:");

                final Checkbox fullscreenCheckbox = new Checkbox(
                        getDialogContentOffsetFollowingLabel(fullscreenLabel),
                        MenuElement.Anchor.LEFT_TOP,
                        Settings::checkIsFullscreenOnStartup,
                        Settings::setFullscreenOnStartup),
                        pixelGridCheckbox = new Checkbox(
                                getDialogContentOffsetFollowingLabel(
                                        pixelGridDefaultLabel),
                                MenuElement.Anchor.LEFT_TOP,
                                Settings::checkIsPixelGridOnByDefault,
                                Settings::setPixelGridOnByDefault);

                final DynamicTextbox widthTextbox = makeDialogPixelDynamicTextbox(
                        newProjectWidthLabel,
                        DialogAssembly::getDialogContentOffsetFollowingLabel,
                        Constants.MIN_CANVAS_W, Constants.MAX_CANVAS_W,
                        Settings::setDefaultCanvasWPixels,
                        Settings::checkDefaultCanvasWPixels, 3),
                        heightTextbox = makeDialogPixelDynamicTextbox(
                                newProjectHeightLabel,
                                DialogAssembly::getDialogContentOffsetFollowingLabel,
                                Constants.MIN_CANVAS_H, Constants.MAX_CANVAS_H,
                                Settings::setDefaultCanvasHPixels,
                                Settings::checkDefaultCanvasHPixels, 3);

                mb.addAll(fullscreenLabel, fullscreenCheckbox,
                        pixelGridDefaultLabel, pixelGridCheckbox,
                        defaultNewProjectSizeLabel,
                        newProjectWidthLabel, widthTextbox,
                        newProjectHeightLabel, heightTextbox);

                // update as new settings are added to category
                yield pixelGridDefaultLabel;
            }
            case FORMAT -> {
                // text labels
                final TextLabel frameAffixLabel = makeDialogLeftLabel(initialYIndex,
                        "Default separate PNGs frame affixes"),
                        prefixLabel = TextLabel.make(
                                textBelowPos(frameAffixLabel),
                                "Prefix:", Constants.WHITE),
                        suffixLabel = makeDialogRightLabel(prefixLabel,
                                "Suffix:");
                final String FA_EXAMPLE = "Example: base_name", FA_MAX_VALUE = "WWWWW";
                final DynamicLabel frameAffixExample = makeDynamicLabel(
                        textBelowPos(prefixLabel), () -> FA_EXAMPLE +
                                Settings.getDefaultIndexPrefix() + "0" +
                                Settings.getDefaultIndexSuffix() + ".png",
                        FA_EXAMPLE + FA_MAX_VALUE + "0" + FA_MAX_VALUE + ".png");

                // textboxes
                final DynamicTextbox prefixTextbox =
                        makeDialogAffixDynamicTextbox(prefixLabel,
                                Settings::setDefaultIndexPrefix,
                                Settings::checkDefaultIndexPrefix),
                        suffixTextbox = makeDialogAffixDynamicTextbox(
                                suffixLabel, Settings::setDefaultIndexSuffix,
                                Settings::checkDefaultIndexSuffix);

                mb.addAll(frameAffixLabel, prefixLabel, suffixLabel,
                        frameAffixExample, prefixTextbox, suffixTextbox);

                // update as new settings are added to category
                yield suffixLabel;
            }
            case VISUAL -> {
                // text labels
                final TextLabel fontLabel = makeDialogLeftLabel(
                        initialYIndex, "Program font:"),
                        checkerboardLabel = TextLabel.make(
                                textBelowPos(fontLabel, 1),
                                "Checkerboard size", Constants.WHITE),
                        checkerboardWidthLabel = TextLabel.make(
                                textBelowPos(checkerboardLabel),
                                "Cell width:", Constants.WHITE),
                        checkerboardHeightLabel = makeDialogRightLabel(
                                checkerboardWidthLabel, "Cell height:"),
                        checkerboardContext = TextLabel.make(
                                textBelowPos(checkerboardWidthLabel),
                                "Valid checkerboard size values range from " +
                                        Layout.CHECKERBOARD_MIN + " to " +
                                        Layout.CHECKERBOARD_MAX + " pixels.",
                                Constants.WHITE),
                        pixelGridLabel = TextLabel.make(
                                textBelowPos(checkerboardContext, 1),
                                "Pixel grid", Constants.WHITE),
                        pixelGridXLabel = TextLabel.make(
                                textBelowPos(pixelGridLabel),
                                "X-axis increment:", Constants.WHITE),
                        pixelGridYLabel = makeDialogRightLabel(
                                pixelGridXLabel, "Y-axis increment:"),
                        pixelGridContext = TextLabel.make(
                                textBelowPos(pixelGridXLabel),
                                "Valid pixel grid size values range from " +
                                        Layout.PIXEL_GRID_MIN + " to " +
                                        Layout.PIXEL_GRID_MAX + " pixels.",
                                Constants.WHITE),
                        pixelGridLimits1 = TextLabel.make(
                                textBelowPos(pixelGridContext),
                                "There can be up to " +
                                        Layout.MAX_PIXEL_GRID_LINES +
                                        " pixel grid lines (X + Y) on",
                                Constants.WHITE),
                        pixelGridLimits2 = TextLabel.make(
                                textBelowPos(pixelGridLimits1),
                                "the canvas due to performance constraints.",
                                Constants.WHITE);

                final DropdownMenu fontDropdown = DropdownMenu.forDialog(
                        getDialogContentOffsetFollowingLabel(fontLabel),
                        EnumUtils.stream(SEFonts.Code.class)
                                .map(SEFonts.Code::forButtonText)
                                .toArray(String[]::new),
                        EnumUtils.stream(SEFonts.Code.class)
                                .map(code -> (Runnable) () -> Settings
                                        .setProgramFont(code))
                                .toArray(Runnable[]::new),
                        () -> Settings.checkProgramFont().ordinal());

                // textboxes
                final DynamicTextbox checkerboardXTextbox =
                        makeDialogPixelDynamicTextbox(checkerboardWidthLabel,
                                DialogAssembly::getDialogContentOffsetFollowingLabel,
                                Layout.CHECKERBOARD_MIN,
                                Layout.CHECKERBOARD_MAX,
                                Settings::setCheckerboardWPixels,
                                Settings::checkCheckerboardWPixels, 3);
                final DynamicTextbox checkerboardYTextbox =
                        makeDialogPixelDynamicTextbox(checkerboardHeightLabel,
                                DialogAssembly::getDialogContentOffsetFollowingLabel,
                                Layout.CHECKERBOARD_MIN,
                                Layout.CHECKERBOARD_MAX,
                                Settings::setCheckerboardHPixels,
                                Settings::checkCheckerboardHPixels, 3);
                final DynamicTextbox pixelGridXTextbox =
                        makeDialogPixelDynamicTextbox(pixelGridXLabel,
                                DialogAssembly::getDialogContentOffsetFollowingLabel,
                                Layout.PIXEL_GRID_MIN,
                                Layout.PIXEL_GRID_MAX,
                                Settings::setPixelGridXPixels,
                                Settings::checkPixelGridXPixels, 3);
                final DynamicTextbox pixelGridYTextbox =
                        makeDialogPixelDynamicTextbox(pixelGridYLabel,
                                DialogAssembly::getDialogContentOffsetFollowingLabel,
                                Layout.PIXEL_GRID_MIN,
                                Layout.PIXEL_GRID_MAX,
                                Settings::setPixelGridYPixels,
                                Settings::checkPixelGridYPixels, 3);

                mb.addAll(fontLabel, fontDropdown, checkerboardLabel,
                        checkerboardWidthLabel, checkerboardXTextbox,
                        checkerboardHeightLabel, checkerboardYTextbox,
                        checkerboardContext, pixelGridLabel,
                        pixelGridXLabel, pixelGridXTextbox,
                        pixelGridYLabel, pixelGridYTextbox,
                        pixelGridContext, pixelGridLimits1, pixelGridLimits2);

                // update as new settings are added to category
                yield pixelGridLimits2;
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
            case COLORS -> assembleColorsInfoScreen(contentAssembler,
                    contentStart, initialbottomY);
            case LAYERS -> assembleLayersInfoScreen(contentAssembler,
                    contentStart, initialbottomY);
            case TOOLS -> assembleToolsInfoScreenContents(contentAssembler,
                    contentStart, initialbottomY);
            case MORE -> assembleInfoScreenContents(
                    new String[] {
                            IconCodes.HORIZONTAL_REFLECTION,
                            IconCodes.VERTICAL_REFLECTION,
                            IconCodes.OUTLINE,
                            IconCodes.PIXEL_GRID_ON,
                            IconCodes.GENERAL,
                            IconCodes.CLIPBOARD_SHORTCUTS,
                            IconCodes.SELECTION_SHORTCUTS,
                            IconCodes.COLOR_SHORTCUTS
                    },
                    new String[] {
                            "Horizontal reflection",
                            "Vertical reflection",
                            "Outline",
                            "Pixel grid",
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

    private static int assembleColorsInfoScreen(
            final Set<MenuElement> contentAssembler, final Coord2D contentStart,
            final int initialBottomY
    ) {
        return assembleInfoScreenContents(
                new String[] {
                        IconCodes.SWAP_COLORS,
                        IconCodes.COLOR_MENU_MODE,
                        IconCodes.NEW_PALETTE,
                        IconCodes.IMPORT_PALETTE,
                        IconCodes.CONTENTS_TO_PALETTE,
                        IconCodes.DELETE_PALETTE,
                        IconCodes.SAVE_PALETTE,
                        IconCodes.SORT_PALETTE,
                        IconCodes.PALETTIZE,
                        IconCodes.PALETTE_SETTINGS,
                        IconCodes.ADD_TO_PALETTE,
                        IconCodes.REMOVE_FROM_PALETTE,
                        IconCodes.MOVE_LEFT_IN_PALETTE,
                        IconCodes.MOVE_RIGHT_IN_PALETTE
                },
                new String[] {
                        "Swap primary and secondary color",
                        "Toggle between palettes and RGBA-HSV color selection",
                        "Create a new palette",
                        "Import a " + StippleEffect.PROGRAM_NAME + " palette file (." +
                                Constants.PALETTE_FILE_SUFFIX + ")",
                        "Add colors from project contents to palette",
                        "Delete the selected palette",
                        "Save palette to file",
                        "Sort colors in palette",
                        "Palettize project contents",
                        "Open the settings dialog for the selected palette",
                        "Add selected color to palette",
                        "Remove selected color from palette",
                        "Shift the selected color one slot to the left in the selected palette",
                        "Shift the selected color one slot to the right in the selected palette"
                }, contentAssembler, contentStart, initialBottomY
        );
    }

    private static int assembleProjectInfoScreenContents(
            final Set<MenuElement> contentAssembler, final Coord2D contentStart,
            final int initialBottomY
    ) {
        return assembleInfoScreenContents(
                new String[] {
                        IconCodes.INFO,
                        IconCodes.PANEL_MANAGER,
                        IconCodes.SETTINGS,
                        IconCodes.NEW_PROJECT,
                        IconCodes.OPEN_FILE,
                        IconCodes.SAVE,
                        IconCodes.SAVE_AS,
                        IconCodes.RESIZE,
                        IconCodes.PAD,
                        IconCodes.STITCH_SPLIT_FRAMES,
                        IconCodes.PREVIEW,
                        IconCodes.UNDO,
                        IconCodes.GRANULAR_UNDO,
                        IconCodes.GRANULAR_REDO,
                        IconCodes.REDO
                },
                new String[] {
                        "Info", "Open panel manager", "Program Settings",
                        "New Project", "Import", "Save", "Save As...",
                        "Resize", "Pad", "Stitch or split frames", "Preview",
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
        final Tool[] all = Tool.getAll();
        return assembleInfoScreenContents(
                Arrays.stream(all).map(Tool::convertNameToFilename)
                        .toArray(String[]::new),
                Arrays.stream(all).map(Tool::getName).toArray(String[]::new),
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
                Layout.STD_TEXT_BUTTON_W, approveText.equals(
                        Constants.CLOSE_DIALOG_TEXT)
                        ? Constants.CLOSE_DIALOG_TEXT : "Cancel", false,
                Constants.GREY),
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
        if (!approveText.equals(Constants.CLOSE_DIALOG_TEXT)) {
            final Coord2D approvePos = cancelPos.displace(-(baseImage.getWidth() +
                    Layout.BUTTON_OFFSET), 0);

            mb.add(new ApproveDialogButton(approvePos,
                    new Coord2D(baseImage.getWidth(), baseImage.getHeight()),
                    MenuElement.Anchor.RIGHT_BOTTOM, onApproval, clearDialog,
                    precondition, approveText));
        }

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
