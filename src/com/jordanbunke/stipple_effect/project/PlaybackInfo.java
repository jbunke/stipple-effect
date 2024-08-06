package com.jordanbunke.stipple_effect.project;

import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.EnumUtils;
import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;

public class PlaybackInfo {
    private static final double NANOS_IN_MILLI = 1e6;

    private boolean playing;
    private int fps, millisPerFrame, millisAccumulated;
    private double nanosAccumulated;
    private Mode mode;

    public enum Mode {
        FORWARDS, BACKWARDS, LOOP, PONG_FORWARDS, PONG_BACKWARDS;

        /**
         * Two enum values map to the same result, so this cannot be replaced with
         * {@link EnumUtils#next(Enum)}.
         * */
        public Mode next() {
            return switch (this) {
                case PONG_FORWARDS, PONG_BACKWARDS -> FORWARDS;
                default -> Mode.values()[ordinal() + 1];
            };
        }

        public int buttonIndex() {
            if (this == PONG_BACKWARDS)
                return PONG_FORWARDS.ordinal();

            return ordinal();
        }

        public String getIconCode() {
            return switch (this) {
                case PONG_FORWARDS, PONG_BACKWARDS -> ResourceCodes.PONG;
                case LOOP -> ResourceCodes.LOOP;
                case FORWARDS -> ResourceCodes.FORWARDS;
                case BACKWARDS -> ResourceCodes.BACKWARDS;
            };
        }
    }

    public PlaybackInfo() {
        this(Mode.FORWARDS);
    }

    private PlaybackInfo(final Mode mode) {
        this.playing = false;
        this.fps = Constants.DEFAULT_PLAYBACK_FPS;
        updateMillisPerFrame();
        this.mode = mode;

        millisAccumulated = 0;
        nanosAccumulated = 0d;
    }

    public static PlaybackInfo forPreview() {
        return new PlaybackInfo(Mode.LOOP);
    }

    public void play() {
        playing = true;
    }

    public void stop() {
        playing = false;
    }

    public void togglePlaying() {
        playing = !playing;
    }

    public void executeAnimation(
            final ProjectState state
    ) {
        switch (mode) {
            case LOOP -> state.nextFrame();
            case FORWARDS -> {
                if (state.getFrameIndex() + 1 < state.getFrameCount())
                    state.nextFrame();
                else
                    togglePlaying();
            }
            case BACKWARDS -> {
                if (state.getFrameIndex() > 0)
                    state.previousFrame();
                else
                    togglePlaying();
            }
            case PONG_FORWARDS -> {
                if (state.getFrameIndex() + 1 < state.getFrameCount())
                    state.nextFrame();
                else
                    setMode(Mode.PONG_BACKWARDS);
            }
            case PONG_BACKWARDS -> {
                if (state.getFrameIndex() > 0)
                    state.previousFrame();
                else
                    setMode(Mode.PONG_FORWARDS);
            }
        }
    }

    public int nextAnimationFrameForPreview(
            final int lastFrame, final int frameCount
    ) {
        final int next = (lastFrame + 1) % frameCount,
                previous = (lastFrame == 0 ? frameCount - 1 : lastFrame - 1);

        switch (mode) {
            case PONG_FORWARDS, FORWARDS -> {
                if (lastFrame + 1 < frameCount)
                    return next;

                if (mode == Mode.PONG_FORWARDS)
                    setMode(Mode.PONG_BACKWARDS);
                else
                    stop();
            }
            case PONG_BACKWARDS, BACKWARDS -> {
                if (lastFrame > 0)
                    return previous;

                if (mode == Mode.PONG_BACKWARDS)
                    setMode(Mode.PONG_FORWARDS);
                else
                    stop();
            }
            case LOOP -> {
                return next;
            }
        }

        return lastFrame;
    }

    private void updateMillisPerFrame() {
        millisPerFrame = Constants.MILLIS_IN_SECOND / fps;
    }

    public boolean checkIfNextFrameDue(
            final double deltaTime, final double frameDuration
    ) {
        final int millisForThisFrame = (int)(millisPerFrame * frameDuration);

        nanosAccumulated += deltaTime;

        while (nanosAccumulated >= NANOS_IN_MILLI) {
            nanosAccumulated -= NANOS_IN_MILLI;
            millisAccumulated++;
        }

        if (millisAccumulated >= millisForThisFrame) {
            millisAccumulated -= millisForThisFrame;
            return true;
        }

        return false;
    }

    public void setFps(final int fps) {
        final int was = this.fps;
        this.fps = Math.max(Constants.MIN_PLAYBACK_FPS,
                Math.min(fps, Constants.MAX_PLAYBACK_FPS));

        if (this.fps != was) {
            updateMillisPerFrame();
            millisAccumulated = 0;
            nanosAccumulated = 0d;
        }
    }

    public void setMode(final Mode mode) {
        this.mode = mode;
    }

    public void toggleMode() {
        this.mode = mode.next();
    }

    public void incrementFps(final int delta) {
        setFps(fps + delta);
    }

    public boolean isPlaying() {
        return playing;
    }

    public int getFps() {
        return fps;
    }

    public Mode getMode() {
        return mode;
    }
}
