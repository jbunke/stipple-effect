package com.jordanbunke.stipple_effect.layer;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.DialogVals;
import com.jordanbunke.stipple_effect.utility.math.StitchSplitMath;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public final class SELayer {
    private final List<GameImage> frames, renders, onionSkins;
    private final GameImage frameLinkedContent;
    private final double opacity;
    private final boolean enabled, framesLinked;
    private final String name;

    private OnionSkinMode onionSkinMode;

    public static SELayer fromPreviewContent(
            final GameImage[] content
    ) {
        final List<GameImage> frames = new ArrayList<>(Arrays.asList(content));

        return new SELayer(frames, content[0], Constants.OPAQUE,
                true, false, OnionSkinMode.NONE, giveLayerDefaultName());
    }

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

        renders = new ArrayList<>();
        onionSkins = new ArrayList<>();

        generateRenders();
        generateOnionSkins();
    }

    private void generateRenders() {
        if (framesLinked && opacity == Constants.OPAQUE)
            renders.add(frames.get(0));
        else if (framesLinked)
            renders.add(renderFrame(0));
        else if (opacity == Constants.OPAQUE)
            renders.addAll(frames);
        else {
            for (int i = 0; i < frames.size(); i++)
                renders.add(renderFrame(i));
        }
    }

    private void generateOnionSkins() {
        if (framesLinked || onionSkinMode == OnionSkinMode.NONE)
            return;

        for (int i = 0; i < frames.size(); i++)
            onionSkins.add(renderOnionSkin(i));
    }

    private static String giveLayerDefaultName() {
        final int size = StippleEffect.get().getContext().getState().getLayers().size();
        return Constants.SUBSEQUENT_LAYER_PREFIX + (size + 1);
    }

    public SELayer duplicate() {
        return new SELayer(new ArrayList<>(frames), frameLinkedContent, opacity,
                enabled, framesLinked, onionSkinMode, name + " (copy)");
    }

    public SELayer returnFrameReplaced(final GameImage edit, final int frameIndex) {
        final List<GameImage> frames = new ArrayList<>(this.frames);
        frames.set(frameIndex, edit);

        return new SELayer(frames, edit, opacity, enabled,
                framesLinked, onionSkinMode, name);
    }

    public SELayer returnStamped(
            final GameImage edit, final Selection selection, final int frameIndex
    ) {
        final List<GameImage> frames = new ArrayList<>(this.frames);
        final GameImage content = getFrame(frameIndex);

        final GameImage composed = new GameImage(content);
        final int w = composed.getWidth(), h = composed.getHeight();

        selection.pixelAlgorithm(w, h, (x, y) -> {
            final Color c = edit.getColorAt(x, y);
            composed.setRGB(x, y, c.getRGB());
        });

        composed.free();
        frames.set(frameIndex, composed);

        return new SELayer(frames, composed, opacity, enabled,
                framesLinked, onionSkinMode, name);
    }

    public SELayer returnPaintedOver(final GameImage edit, final int frameIndex) {
        final List<GameImage> frames = new ArrayList<>(this.frames);
        final GameImage content = getFrame(frameIndex);

        final GameImage composed = new GameImage(content);
        composed.draw(edit);
        composed.free();
        frames.set(frameIndex, composed);

        return new SELayer(frames, composed, opacity, enabled,
                framesLinked, onionSkinMode, name);
    }

    public SELayer returnErased(final boolean[][] eraserMask, final int frameIndex) {
        final List<GameImage> frames = new ArrayList<>(this.frames);
        final GameImage content = getFrame(frameIndex);

        final GameImage after = new GameImage(content);

        for (int x = 0; x < after.getWidth(); x++) {
            for (int y = 0; y < after.getHeight(); y++) {
                if (eraserMask[x][y])
                    after.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
            }
        }

        after.free();
        frames.set(frameIndex, after);

        return new SELayer(frames, after, opacity, enabled,
                framesLinked, onionSkinMode, name);
    }

    public SELayer returnResized(final int w, final int h) {
        final List<GameImage> resizedFrames = new ArrayList<>();

        for (int i = 0; i < frames.size(); i++)
            resizedFrames.add(ImageProcessing.scale(
                    getFrame(i), w, h));

        final GameImage resizedFLC = ImageProcessing.scale(
                frameLinkedContent, w, h);

        return new SELayer(resizedFrames, resizedFLC, opacity,
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

        final GameImage paddedFLC = new GameImage(w, h);
        paddedFLC.draw(frameLinkedContent, left, top);

        return new SELayer(paddedFrames, paddedFLC.submit(), opacity,
                enabled, framesLinked, onionSkinMode, name);
    }

    public SELayer returnStitched(
            final int fw, final int fh, final int fc
    ) {
        final int w = StitchSplitMath.stitchedWidth(fw, fc),
                h = StitchSplitMath.stitchedHeight(fh, fc),
                fpd = DialogVals.getFramesPerDim();

        final boolean isHorizontal = StitchSplitMath.isHorizontal();
        final BinaryOperator<Integer>
                horz = (a, b) -> a % b,
                vert = (a, b) -> a / b,
                xOp = isHorizontal ? horz : vert,
                yOp = isHorizontal ? vert : horz;

        final GameImage frame = new GameImage(w, h);

        for (int i = 0; i < fc; i++) {
            final int x = xOp.apply(i, fpd) * fw,
                    y = yOp.apply(i, fpd) * fh;

            frame.draw(getFrame(i), x, y);
        }

        frame.free();

        return new SELayer(new ArrayList<>(List.of(frame)), frame,
                opacity, enabled, true, onionSkinMode, name);
    }

    public SELayer returnSplit(
            final int w, final int h, final int fc
    ) {
        final int fw = DialogVals.getFrameWidth(),
                fh = DialogVals.getFrameHeight(),
                fx = StitchSplitMath.splitColumns(w),
                fy = StitchSplitMath.splitRows(h);

        final boolean isHorizontal = StitchSplitMath.isHorizontal();
        final BinaryOperator<Integer>
                horz = (a, b) -> a % b,
                vert = (a, b) -> a / b,
                xOp = isHorizontal ? horz : vert,
                yOp = isHorizontal ? vert : horz;

        final int fpd = isHorizontal ? fx : fy;

        final List<GameImage> frames = new ArrayList<>();
        final GameImage canvas = getFrame(0);

        for (int i = 0; i < fc; i++) {
            final GameImage frame = new GameImage(fw, fh);

            final int x = xOp.apply(i, fpd) * fw,
                    y = yOp.apply(i, fpd) * fh;

            frame.draw(canvas, -1 * x, -1 * y);
            frames.add(frame.submit());
        }

        return new SELayer(frames, frames.get(0),
                opacity, enabled, false, onionSkinMode, name);
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

    public SELayer returnFrameMovedBack(
            final int oldFrameIndex
    ) {
        final List<GameImage> frames = new ArrayList<>(this.frames);

        final GameImage frame = frames.get(oldFrameIndex);
        frames.remove(oldFrameIndex);
        frames.add(oldFrameIndex - 1, frame);

        return new SELayer(frames, frameLinkedContent, opacity, enabled,
                framesLinked, onionSkinMode, name);
    }

    public SELayer returnFrameMovedForward(
            final int oldFrameIndex
    ) {
        final List<GameImage> frames = new ArrayList<>(this.frames);

        final GameImage frame = frames.get(oldFrameIndex);
        frames.remove(oldFrameIndex);
        frames.add(oldFrameIndex + 1, frame);

        return new SELayer(frames, frameLinkedContent, opacity, enabled,
                framesLinked, onionSkinMode, name);
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

        frames.add(fromIndex + 1, new GameImage(frames.get(fromIndex)));

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

    public GameImage getRender(final int frameIndex) {
        if (opacity == Constants.OPAQUE)
            return getFrame(frameIndex);

        return renders.get(framesLinked ? 0 : frameIndex);
    }

    public GameImage getOnionSkin(final int frameIndex) {
        if (framesLinked || frameIndex >= onionSkins.size())
            return GameImage.dummy();

        return onionSkins.get(frameIndex);
    }

    private GameImage renderOnionSkin(final int frameIndex) {
        return renderFrame(frameIndex, opacity * Constants.ONION_SKIN_OPACITY);
    }

    private GameImage renderFrame(final int frameIndex) {
        return renderFrame(frameIndex, opacity);
    }

    private GameImage renderFrame(final int frameIndex, final double opacity) {
        final GameImage frame = getFrame(frameIndex);

        final GameImage render = new GameImage(frame.getWidth(), frame.getHeight());

        for (int x = 0; x < render.getWidth(); x++) {
            for (int y = 0; y < render.getHeight(); y++) {
                final Color c = frame.getColorAt(x, y);

                if (c.getAlpha() == 0)
                    continue;

                final Color cp = new Color(c.getRed(), c.getGreen(), c.getBlue(),
                        (int)(c.getAlpha() * opacity));
                render.setRGB(x, y, cp.getRGB());
            }
        }

        return render.submit();
    }

    public void setOnionSkinMode(final OnionSkinMode onionSkinMode) {
        this.onionSkinMode = onionSkinMode;

        if (onionSkins.isEmpty())
            generateOnionSkins();
    }

    public OnionSkinMode getOnionSkinMode() {
        return onionSkinMode;
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

    public String getName() {
        return name;
    }
}
