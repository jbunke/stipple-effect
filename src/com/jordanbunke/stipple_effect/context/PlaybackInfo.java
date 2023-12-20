package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.utility.Constants;

public class PlaybackInfo {
    private static final double NANOS_IN_MILLI = 1e6;

    private boolean playing;
    private int millisPerFrame, millisAccumulated;
    private double nanosAccumulated;
    private Mode mode;

    public enum Mode {
        FORWARDS, BACKWARDS, LOOP, PONG_FORWARDS, PONG_BACKWARDS;

        public Mode next() {
            return switch (this) {
                case PONG_FORWARDS, PONG_BACKWARDS -> FORWARDS;
                default -> Mode.values()[ordinal() + 1];
            };
        }
    }

    public PlaybackInfo() {
        this.playing = false;
        this.millisPerFrame = Constants.DEFAULT_MILLIS_PER_FRAME;
        this.mode = Mode.FORWARDS;

        millisAccumulated = 0;
        nanosAccumulated = 0d;
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

    public boolean checkIfNextFrameDue(final double deltaTime) {
        nanosAccumulated += deltaTime;

        while (nanosAccumulated > NANOS_IN_MILLI) {
            nanosAccumulated -= NANOS_IN_MILLI;
            millisAccumulated++;
        }

        if (millisAccumulated >= millisPerFrame) {
            millisAccumulated -= millisPerFrame;
            return true;
        }

        return false;
    }

    public void setMillisPerFrame(final int millisPerFrame) {
        this.millisPerFrame = Math.max(Constants.MIN_MILLIS_PER_FRAME,
                Math.min(millisPerFrame, Constants.MAX_MILLIS_PER_FRAME));
        millisAccumulated = 0;
        nanosAccumulated = 0d;
    }

    public void setMode(final Mode mode) {
        this.mode = mode;
    }

    public void toggleMode() {
        this.mode = mode.next();
    }

    public void incrementMillisPerFrame(final int delta) {
        setMillisPerFrame(millisPerFrame + delta);
        StippleEffect.get().rebuildFramesMenu();
    }

    public boolean isPlaying() {
        return playing;
    }

    public int getMillisPerFrame() {
        return millisPerFrame;
    }

    public Mode getMode() {
        return mode;
    }
}
