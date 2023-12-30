package com.jordanbunke.stipple_effect.project;

import com.jordanbunke.anim.AnimWriter;
import com.jordanbunke.anim.GIFWriter;
import com.jordanbunke.anim.MP4Writer;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.GameImageIO;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.visual.DialogAssembly;
import com.jordanbunke.stipple_effect.selection.SelectionMode;
import com.jordanbunke.stipple_effect.utility.*;

import java.nio.file.Path;

public class ProjectInfo {
    private static final int X = 0, Y = 1;

    private Path folder;
    private String name, indexPrefix, indexSuffix;
    private SaveType saveType;

    private boolean editedSinceLastSave;

    private final int[] frameDims;
    private int fps, scaleUp, countFrom;

    public enum SaveType {
        PNG_STITCHED, PNG_SEPARATE, GIF, MP4, NATIVE;

        public String getFileSuffix() {
            return switch (this) {
                case PNG_SEPARATE, PNG_STITCHED -> "png";
                case GIF -> "gif";
                case MP4 -> "mp4";
                case NATIVE -> Constants.NATIVE_FILE_SUFFIX;
            };
        }

        public String getButtonText() {
            return switch (this) {
                case PNG_STITCHED -> "Single PNG";
                case PNG_SEPARATE -> "Separate PNGs per frame";
                case GIF -> "Animated GIF";
                case MP4 -> "MP4 Video";
                case NATIVE -> StippleEffect.PROGRAM_NAME + " file (." + getFileSuffix() + ")";
            };
        }

        public static SaveType[] validOptions() {
            // TODO - omitting NATIVE while serializing/saving and loading are not yet implemented
            if (StippleEffect.get().getContext().getState().getFrameCount() == 1)
                return new SaveType[] { PNG_STITCHED /*, NATIVE */ };

            return new SaveType[] { PNG_STITCHED, PNG_SEPARATE, GIF, MP4 }; // SaveType.values();
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
        fps = Constants.DEFAULT_PLAYBACK_FPS;
        scaleUp = Constants.DEFAULT_SAVE_SCALE_UP;

        countFrom = 1;
        indexPrefix = Settings.getDefaultIndexPrefix();
        indexSuffix = Settings.getDefaultIndexSuffix();
    }

    public void save() {
        if (!hasSaveAssociation()) {
            DialogAssembly.setDialogToSave();
            return;
        }

        final SEContext c = StippleEffect.get().getContext();

        if (c.getState().getSelectionMode() == SelectionMode.CONTENTS &&
                c.getState().hasSelection())
            c.dropContentsToLayer(true, false);

        final int w = c.getState().getImageWidth(),
                h = c.getState().getImageHeight(),
                frameCount = c.getState().getFrameCount();

        switch (saveType) {
            case NATIVE -> {
                // TODO
            }
            case GIF, MP4 -> {
                final GameImage[] images = new GameImage[frameCount];

                for (int i = 0; i < frameCount; i++) {
                    images[i] = c.getState().draw(false, false, i);

                    if (scaleUp > 1)
                        images[i] = ImageProcessing.scale(images[i], scaleUp);
                }

                final AnimWriter writer = saveType == SaveType.GIF
                        ? GIFWriter.get() : MP4Writer.get();

                final Thread animSaverThread = new Thread(() -> {
                    final Path filepath = buildFilepath();

                    writer.write(filepath, images,
                            Constants.MILLIS_IN_SECOND / fps);
                    StatusUpdates.saved(filepath);
                });

                animSaverThread.start();
            }
            case PNG_SEPARATE -> {
                for (int i = 0; i < frameCount; i++) {
                    final GameImage image = c.getState().draw(false, false, i);
                    final String suffix = indexPrefix +
                            (countFrom + i) + indexSuffix;

                    GameImageIO.writeImage(buildFilepath(suffix), scaleUp > 1
                            ? ImageProcessing.scale(image, scaleUp) : image);
                }

                StatusUpdates.savedAllFrames(folder);
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

                    stitched.draw(c.getState().draw(
                            false, false, i), w * x, h * y);
                }

                GameImageIO.writeImage(buildFilepath(), scaleUp > 1
                        ? ImageProcessing.scale(stitched.submit(), scaleUp)
                        : stitched.submit());
                StatusUpdates.saved(buildFilepath());
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

    public int getFps() {
        return fps;
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

    public String getIndexPrefix() {
        return indexPrefix;
    }

    public String getIndexSuffix() {
        return indexSuffix;
    }

    public int getCountFrom() {
        return countFrom;
    }

    public void setCountFrom(final int countFrom) {
        this.countFrom = countFrom;
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

    public void setFps(final int fps) {
        this.fps = fps;
    }

    public void setScaleUp(final int scaleUp) {
        this.scaleUp = scaleUp;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setIndexPrefix(final String indexPrefix) {
        this.indexPrefix = indexPrefix;
    }

    public void setIndexSuffix(final String indexSuffix) {
        this.indexSuffix = indexSuffix;
    }

    public void setFolder(final Path folder) {
        this.folder = folder;
    }
}
