package com.jordanbunke.stipple_effect.visual.menu_elements.dialog;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.preview.PreviewPlayback;
import com.jordanbunke.stipple_effect.project.PlaybackInfo;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.Arrays;

public class ActionPreviewer extends MenuElement implements PreviewPlayback {
    private final GameImage[] frames;
    private final double[] frameDurations;
    private final PlaybackInfo playbackInfo;
    private final int frameCount;

    private int frameIndex;

    public ActionPreviewer(
            final Coord2D position, final Bounds2D dimensions,
            final GameImage[] frames, final double[] frameDurations
    ) {
        super(position, dimensions, Anchor.CENTRAL_TOP, true);

        this.frames = frames;
        frameCount = frames.length;

        if (frameDurations.length != frameCount) {
            this.frameDurations = Arrays.stream(frames)
                    .mapToDouble(i -> Constants.DEFAULT_FRAME_DURATION).toArray();
        } else {
            this.frameDurations = frameDurations;
        }

        playbackInfo = PlaybackInfo.forPreview();

        frameIndex = 0;
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        if (frameCount > 1)
            processKeys(eventLogger);
    }

    @Override
    public void update(final double deltaTime) {
        animate(deltaTime);
    }

    private void animate(final double deltaTime) {
        if (playbackInfo.isPlaying()) {
            final boolean nextFrameDue =
                    playbackInfo.checkIfNextFrameDue(deltaTime, frameDurations[frameIndex]);

            if (nextFrameDue)
                frameIndex = playbackInfo.nextAnimationFrameForPreview(frameIndex, frameCount);
        }
    }

    @Override
    public void render(final GameImage canvas) {
        draw(frames[frameIndex], canvas);
    }

    @Override
    public void debugRender(GameImage canvas, GameDebugger debugger) {}

    public int getFrameIndex() {
        return frameIndex;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public PlaybackInfo getPlaybackInfo() {
        return playbackInfo;
    }

    public void toFirstFrame() {
        frameIndex = 0;
    }

    public void toLastFrame() {
        frameIndex = frameCount - 1;
    }

    public void previousFrame() {
        frameIndex = frameIndex == 0 ? frameCount - 1 : frameIndex - 1;
    }

    public void nextFrame() {
        frameIndex = (frameIndex + 1) % frameCount;
    }
}
