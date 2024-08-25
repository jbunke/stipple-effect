package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.fonts.*;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.io.GameImageIO;
import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SaveConfig;
import com.jordanbunke.stipple_effect.utility.DialogVals;
import com.jordanbunke.stipple_effect.utility.EnumUtils;
import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;
import com.jordanbunke.stipple_effect.utility.ParserUtils;
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
                case CLASSIC -> SEFonts.CLASSIC;
                case SCHIEF -> SEFonts.SCHIEF;
                case ZIFFER -> SEFonts.ZIFFER;
                case SKINNY -> SEFonts.SKINNY;
                case DELTAN -> SEFonts.DELTAN;
            };
        }
    }

    public static final Path FONT_FOLDER = Path.of("fonts");

    private static final FontBuilder builder = new FontBuilder()
            .setPixelSpacing(2).setWhitespaceBreadthMultiplier(0.6);

    private static final Font
            DELTAN = builder.build(DeltaTimeFonts.DELTAN),
            SKINNY = builder.build(DeltaTimeFonts.SKINNY),
            CLASSIC = builder.build(FONT_FOLDER, true, "classic"),
            ZIFFER = builder.build(FONT_FOLDER, true, "ziffer"),
            SCHIEF = builder.setWhitespaceBreadthMultiplier(0.5)
                    .build(FONT_FOLDER, true, "schief");

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
        final String suffix = SaveConfig.SaveType.PNG_STITCHED.getFileSuffix();

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
                ResourceCodes.FONT_EXAMPLE_TEXT);
        final TextBuilder tb = new TextBuilder(1d, Text.Orientation.LEFT,
                Settings.getTheme().textLight, buildNewFont());
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

        return Font.loadFromFontSources(new FontSources(ascii, latinExtended),
                1d, pixelSpacing, false, charSpecificSpacing);
    }
}
