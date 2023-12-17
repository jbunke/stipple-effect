package com.jordanbunke.stipple_effect.layer;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class SELayer {
    private final List<GameImage> frames;
    private final GameImage frameLinkedContent;
    private final double opacity;
    private final boolean enabled, framesLinked;
    private OnionSkinMode onionSkinMode;
    private final String name;

    public static SELayer newLayer(
            final int w, final int h, final int frameCount
    ) {
        final List<GameImage> frames = new ArrayList<>();

        for (int f = 0; f < frameCount; f++) {
            frames.add(new GameImage(w, h));
        }

        return new SELayer(frames, new GameImage(w, h), Constants.OPAQUE,
                true, false, OnionSkinMode.NONE, giveLayerDefaultName());
    }

    public SELayer(final int imageWidth, final int imageHeight) {
        this(new ArrayList<>(List.of(new GameImage(imageWidth, imageHeight))),
                new GameImage(imageWidth, imageHeight), Constants.OPAQUE,
                true, false, OnionSkinMode.NONE, Constants.BASE_LAYER_NAME);
    }

    public SELayer(
            final List<GameImage> frames, final GameImage frameLinkedContent,
            final double opacity,
            final boolean enabled, final boolean framesLinked,
            final OnionSkinMode onionSkinMode, final String name
    ) {
        this.frames = frames;
        this.frameLinkedContent = frameLinkedContent;
        this.opacity = opacity;
        this.enabled = enabled;
        this.framesLinked = framesLinked;
        this.onionSkinMode = onionSkinMode;
        this.name = name;
    }

    private static String giveLayerDefaultName() {
        final int size = StippleEffect.get().getContext().getState().getLayers().size();
        return Constants.SUBSEQUENT_LAYER_PREFIX + (size + 1);
    }

    public SELayer duplicate() {
        return new SELayer(new ArrayList<>(frames), frameLinkedContent, opacity,
                enabled, framesLinked, onionSkinMode, name + " (copy)");
    }

    public SELayer returnEdited(final GameImage edit, final int frameIndex) {
        final List<GameImage> frames = new ArrayList<>(this.frames);
        final GameImage content = frames.get(frameIndex);

        final GameImage composed = new GameImage(content);
        composed.draw(edit);

        frames.set(frameIndex, composed.submit());

        return new SELayer(frames, frameLinkedContent, opacity, enabled,
                framesLinked, onionSkinMode, name);
    }

    public SELayer returnEdited(final boolean[][] eraserMask, final int frameIndex) {
        final List<GameImage> frames = new ArrayList<>(this.frames);
        final GameImage content = frames.get(frameIndex);

        final GameImage after = new GameImage(content);

        for (int x = 0; x < after.getWidth(); x++) {
            for (int y = 0; y < after.getHeight(); y++) {
                if (eraserMask[x][y])
                    after.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
            }
        }

        frames.set(frameIndex, after.submit());

        return new SELayer(frames, frameLinkedContent, opacity, enabled,
                framesLinked, onionSkinMode, name);
    }

    public SELayer returnResized(final int w, final int h) {
        final List<GameImage> resizedFrames = new ArrayList<>();

        for (int i = 0; i < frames.size(); i++)
            resizedFrames.add(ImageProcessing.scale(
                    getFrame(i), w, h));

        return new SELayer(resizedFrames, frameLinkedContent, opacity,
                enabled, framesLinked, onionSkinMode, name);
    }

    public SELayer returnPadded(
            final int left, final int top, final int w, final int h
    ) {
        final List<GameImage> paddedFrames = new ArrayList<>();

        for (int i = 0; i < frames.size(); i++) {
            final GameImage frame = new GameImage(w, h);
            frame.draw(getFrame(i), left, top);

            paddedFrames.add(frame.submit());
        }

        return new SELayer(paddedFrames, frameLinkedContent, opacity,
                enabled, framesLinked, onionSkinMode, name);
    }

    public SELayer returnLinkedFrames(final int frameIndex) {
        return new SELayer(new ArrayList<>(frames), getFrame(frameIndex),
                opacity, enabled, true, onionSkinMode, name);
    }

    public SELayer returnUnlinkedFrames() {
        final int frameCount = frames.size();

        final List<GameImage> clonedFromLinked = new ArrayList<>();

        for (int i = 0; i < frameCount; i++)
            clonedFromLinked.add(frameLinkedContent);

        return new SELayer(clonedFromLinked, frameLinkedContent,
                opacity, enabled, false, onionSkinMode, name);
    }

    public SELayer returnChangedOpacity(final double opacity) {
        return new SELayer(new ArrayList<>(frames), frameLinkedContent,
                opacity, enabled, framesLinked, onionSkinMode, name);
    }

    public SELayer returnDisabled() {
        return new SELayer(new ArrayList<>(frames), frameLinkedContent,
                opacity, false, framesLinked, onionSkinMode, name);
    }

    public SELayer returnEnabled() {
        return new SELayer(new ArrayList<>(frames), frameLinkedContent,
                opacity, true, framesLinked, onionSkinMode, name);
    }

    public SELayer returnRenamed(final String name) {
        return new SELayer(new ArrayList<>(frames), frameLinkedContent,
                opacity, enabled, framesLinked, onionSkinMode, name);
    }

    public SELayer returnAddedFrame(
            final int addIndex, final int w, final int h
    ) {
        final List<GameImage> frames = new ArrayList<>(this.frames);

        frames.add(addIndex, new GameImage(w, h));

        return new SELayer(frames, frameLinkedContent, opacity, enabled,
                framesLinked, onionSkinMode, name);
    }

    public SELayer returnDuplicatedFrame(final int fromIndex) {
        final List<GameImage> frames = new ArrayList<>(this.frames);

        frames.add(fromIndex + 1, frames.get(fromIndex));

        return new SELayer(frames, frameLinkedContent, opacity, enabled,
                framesLinked, onionSkinMode, name);
    }

    public SELayer returnRemovedFrame(final int fromIndex) {
        final List<GameImage> frames = new ArrayList<>(this.frames);

        frames.remove(fromIndex);

        return new SELayer(frames, frameLinkedContent, opacity, enabled,
                framesLinked, onionSkinMode, name);
    }

    public GameImage getFrame(final int frameIndex) {
        return framesLinked ? frameLinkedContent : frames.get(frameIndex);
    }

    public GameImage renderFrame(final int frameIndex) {
        return renderFrame(frameIndex, opacity);
    }

    public GameImage renderFrame(final int frameIndex, final double opacity) {
        final GameImage frame = getFrame(frameIndex);

        final GameImage render = new GameImage(frame.getWidth(), frame.getHeight());

        for (int x = 0; x < render.getWidth(); x++) {
            for (int y = 0; y < render.getHeight(); y++) {
                final Color c = ImageProcessing.colorAtPixel(frame, x, y),
                        cp = new Color(c.getRed(), c.getGreen(), c.getBlue(),
                                (int)(c.getAlpha() * opacity));
                render.dot(cp, x, y);
            }
        }

        return render.submit();
    }

    public void setOnionSkinMode(final OnionSkinMode onionSkinMode) {
        this.onionSkinMode = onionSkinMode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean areFramesLinked() {
        return framesLinked;
    }

    public double getOpacity() {
        return opacity;
    }

    public OnionSkinMode getOnionSkinMode() {
        return onionSkinMode;
    }

    public String getName() {
        return name;
    }
}
