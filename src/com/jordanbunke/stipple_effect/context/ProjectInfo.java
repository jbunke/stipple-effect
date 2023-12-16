package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.GameImageIO;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.DialogAssembly;
import com.jordanbunke.stipple_effect.utility.DialogVals;

import java.nio.file.Path;

public class ProjectInfo {
    private static final int X = 0, Y = 1;

    private Path folder;
    private String name;
    private SaveType saveType;

    private boolean editedSinceLastSave;

    private final int[] frameDims;
    private int millisPerFrame, scaleUp;

    public enum SaveType {
        PNG_STITCHED, PNG_SEPARATE, GIF, NATIVE;

        public String getFileSuffix() {
            return switch (this) {
                case PNG_SEPARATE, PNG_STITCHED -> "png";
                case GIF -> "gif";
                case NATIVE -> Constants.NATIVE_FILE_SUFFIX;
            };
        }

        public String getButtonText() {
            return switch (this) {
                case PNG_STITCHED -> "Single PNG";
                case PNG_SEPARATE -> "Separate PNGs per frame";
                case GIF -> "Animated GIF";
                case NATIVE -> Constants.PROGRAM_NAME + " file (." + getFileSuffix() + ")";
            };
        }

        public static SaveType[] validOptions() {
            if (StippleEffect.get().getContext().getState().getFrameCount() == 1)
                return new SaveType[] { PNG_STITCHED, NATIVE };

            return SaveType.values();
        }
    }

    public ProjectInfo() {
        this(null);
    }

    public ProjectInfo(final Path filepath) {
        if (filepath == null) {
            folder = null;
            name = "";
        } else {
            folder = filepath.getParent();
            final String filename = filepath.getFileName().toString();

            name = filename.contains(".") ? filename.substring(0,
                    filename.lastIndexOf(".")) : filename;
        }

        editedSinceLastSave = false;
        saveType = SaveType.PNG_STITCHED;
        frameDims = new int[] {
                DialogVals.getNewProjectXDivs(),
                DialogVals.getNewProjectYDivs()
        };
        millisPerFrame = Constants.DEFAULT_MILLIS_PER_FRAME;
        scaleUp = Constants.DEFAULT_SAVE_SCALE_UP;
    }

    public void save() {
        if (!hasSaveAssociation()) {
            DialogAssembly.setDialogToSave();
            return;
        }

        final int w = StippleEffect.get().getContext().getState().getImageWidth(),
                h = StippleEffect.get().getContext().getState().getImageHeight(),
                frameCount = StippleEffect.get().getContext().getState().getFrameCount();

        switch (saveType) {
            case NATIVE -> {
                // TODO
            }
            case GIF -> {
                final GameImage[] images = new GameImage[frameCount];

                for (int i = 0; i < frameCount; i++) {
                    images[i] = StippleEffect.get().getContext()
                            .getState().draw(false, i);

                    if (scaleUp > 1)
                        images[i] = ImageProcessing.scale(images[i], scaleUp);
                }

                GameImageIO.writeGif(buildFilepath(), images, millisPerFrame);
            }
            case PNG_SEPARATE -> {
                for (int i = 0; i < frameCount; i++) {
                    final GameImage image = StippleEffect.get().getContext()
                            .getState().draw(false, i);

                    GameImageIO.writeImage(buildFilepath("_" + i),
                            scaleUp > 1 ? ImageProcessing.scale(image, scaleUp) : image);
                }

            }
            case PNG_STITCHED -> {
                final boolean validDimensions = frameCount == frameDims[X] * frameDims[Y];

                if (!validDimensions) {
                    if (frameDims[X] == 1) {
                        frameDims[X] = frameCount;
                        frameDims[Y] = 1;
                    } else {
                        if (frameDims[X] > frameCount)
                            frameDims[X] = frameCount;

                        frameDims[Y] = (int) Math.ceil(frameCount / (double) frameDims[X]);
                    }
                }

                final GameImage stitched = new GameImage(
                        w * frameDims[X],
                        h * frameDims[Y]);

                for (int i = 0; i < frameCount; i++) {
                    final int x = i % frameDims[X],
                            y = i / frameDims[X];

                    stitched.draw(StippleEffect.get().getContext().getState().draw(
                            false, i), w * x, h * y);
                }

                GameImageIO.writeImage(buildFilepath(), scaleUp > 1
                        ? ImageProcessing.scale(stitched.submit(), scaleUp)
                        : stitched.submit());
            }
        }

        editedSinceLastSave = false;
        StippleEffect.get().rebuildProjectsMenu();
    }

    public void markAsEdited() {
        editedSinceLastSave = true;
    }

    private Path buildFilepath(final String nameSuffix) {
        return folder.resolve(name + nameSuffix + "." + saveType.getFileSuffix());
    }

    private Path buildFilepath() {
        return buildFilepath("");
    }

    public boolean hasSaveAssociation() {
        return folder != null && !name.equals("");
    }

    public boolean hasUnsavedChanges() {
        return editedSinceLastSave;
    }

    public Path getFolder() {
        return folder;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName(
            final boolean includeEditMark, final boolean abbreviate
    ) {
        final StringBuilder sb = new StringBuilder();

        if (includeEditMark && editedSinceLastSave)
            sb.append("* ");

        if (hasSaveAssociation()) {
            if (abbreviate && name.length() > Constants.MAX_NAME_LENGTH)
                sb.append(name, 0, Constants.MAX_NAME_LENGTH / 2)
                        .append("...").append(name.substring(name.length() - 4));
            else
                sb.append(name);
        } else
            sb.append(Constants.UNTITLED_PROJECT_NAME);

        return sb.toString();
    }

    public int getMillisPerFrame() {
        return millisPerFrame;
    }

    public int getScaleUp() {
        return scaleUp;
    }

    public int getFrameDimsX() {
        return frameDims[X];
    }

    public int getFrameDimsY() {
        return frameDims[Y];
    }

    public SaveType getSaveType() {
        return saveType;
    }

    public void setFrameDimsX(final int v) {
        this.frameDims[X] = v;
    }

    public void setFrameDimsY(final int v) {
        this.frameDims[Y] = v;
    }
    public void setSaveType(final SaveType saveType) {
        this.saveType = saveType;
    }

    public void setMillisPerFrame(int millisPerFrame) {
        this.millisPerFrame = millisPerFrame;
    }

    public void setScaleUp(final int scaleUp) {
        this.scaleUp = scaleUp;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setFolder(final Path folder) {
        this.folder = folder;
    }
}
