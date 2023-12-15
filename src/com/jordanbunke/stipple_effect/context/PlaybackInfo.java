package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;

public class PlaybackInfo {
    private static final double NANOS_IN_MILLI = 1e6;

    private boolean playing;
    private int millisPerFrame, millisAccumulated;
    private double nanosAccumulated;

    public PlaybackInfo() {
        this.playing = false;
        this.millisPerFrame = Constants.DEFAULT_MILLIS_PER_FRAME;

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
}
