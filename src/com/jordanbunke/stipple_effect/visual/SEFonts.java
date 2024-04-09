package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.fonts.Font;
import com.jordanbunke.delta_time.fonts.FontConstants;
import com.jordanbunke.delta_time.fonts.FontFamily;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.io.GameImageIO;
import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.ProjectInfo;
import com.jordanbunke.stipple_effect.utility.*;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

public class SEFonts {
    public static final Code DEFAULT_FONT = Code.DELTAN;

    public enum Code {
        DELTAN, SKINNY, CLASSIC, SCHIEF, ZIFFER;

        public String forButtonText() {
            return EnumUtils.formattedName(this);
        }

        public Font associated() {
            return switch (this) {
                case CLASSIC -> SEFonts.CLASSIC.getStandard();
                case SCHIEF -> SEFonts.CLASSIC.getItalics();
                case ZIFFER -> SEFonts.ZIFFER;
                case SKINNY -> SEFonts.DELTAN.getBold();
                case DELTAN -> SEFonts.DELTAN.getStandard();
            };
        }
    }

    public static final Path FONT_FOLDER = Path.of("fonts");

    public static final FontFamily CLASSIC = new FontFamily("Classic",
            Font.loadFromSource(FONT_FOLDER, true, "font-classic",
                    true, 0.6, 2, false, true),
            null,
            Font.loadFromSource(FONT_FOLDER, true, "font-classic-italics",
                    true, 0.5, 2, false, true)),
            DELTAN = new FontFamily("Deltan",
                    Font.loadFromSource(FONT_FOLDER, true, "font-deltan",
                            true, 0.6, 2, false, true),
                    Font.loadFromSource(FONT_FOLDER, true, "font-skinny-deltan",
                            true, 0.6, 2, false, true),
                    null);

    public static final Font
            ZIFFER = Font.loadFromSource(FONT_FOLDER, true, "font-basic-bold",
            true, 0.6, 2, false, true);

    public static void uploadASCIISourceFile() {
        uploadFontSourceFile(DialogVals::resetAscii,
                DialogVals::asciiFailedValidation,
                DialogVals::asciiPassedValidation);
    }

    public static void uploadLatinExtendedSourceFile() {
        uploadFontSourceFile(DialogVals::resetLatinEx,
                DialogVals::latinExFailedValidation,
                DialogVals::latinExPassedValidation);
    }

    private static void uploadFontSourceFile(
            final Runnable reset,
            final Runnable failed,
            final Consumer<GameImage> passed
    ) {
        final String suffix = ProjectInfo.SaveType.PNG_STITCHED.getFileSuffix();

        FileIO.setDialogToFilesOnly();
        final Optional<File> opened = FileIO.openFileFromSystem(
                new String[] { "Font source files (.png)" },
                new String[][] { new String[] { suffix } });
        StippleEffect.get().window.getEventLogger().unpressAllKeys();

        if (opened.isEmpty())
            reset.run();
        else {
            final Path filepath = opened.get().toPath();
            final String fileName = filepath.getFileName().toString();

            if (!fileName.endsWith(suffix)) {
                failed.run();
                return;
            }

            final GameImage image = GameImageIO.readImage(filepath);

            // validation
            final int w = image.getWidth(), h = image.getHeight(),
                    targetW = FontConstants.FONT_SOURCE_BASE_WIDTH,
                    targetH = FontConstants.FONT_SOURCE_BASE_HEIGHT;

            final boolean multiplesOfBounds = w % targetW == 0 && h % targetH == 0,
                    correctRatio = w / targetW == h / targetH;

            final boolean valid = multiplesOfBounds && correctRatio;

            if (valid)
                passed.accept(image);
            else
                failed.run();
        }
    }

    public static void attemptPreviewUpdate() {
        final boolean canUpdate =
                DialogVals.getAsciiStatus().isValid() &&
                        (!DialogVals.hasLatinEx() ||
                                DialogVals.getLatinExStatus().isValid());

        if (!canUpdate)
            return;

        final String[] previewText = ParserUtils.getBlurb(
                IconCodes.FONT_EXAMPLE_TEXT);
        final TextBuilder tb = new TextBuilder(1d, Text.Orientation.LEFT,
                Settings.getTheme().getTextLight(), buildNewFont());
        Arrays.stream(previewText).forEach(line -> {
            tb.addText(line);
            tb.addLineBreak();
        });
        final GameImage preview = tb.build().draw();
        DialogVals.setFontPreviewImage(preview);
    }

    public static Font buildNewFont() {
        final GameImage ascii = DialogVals.getAsciiImage(),
                latinExtended = DialogVals.getLatinExImage();

        final int pixelSpacing = DialogVals.getNewFontPixelSpacing();
        final boolean charSpecificSpacing = DialogVals.isCharSpecificSpacing();

        return Font.loadFromImages(ascii, latinExtended, 1d,
                pixelSpacing, false, charSpecificSpacing);
    }
}
