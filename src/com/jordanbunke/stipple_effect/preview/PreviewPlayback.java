package com.jordanbunke.stipple_effect.preview;

import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.stipple_effect.project.PlaybackInfo;

import static com.jordanbunke.stipple_effect.utility.action.SEAction.*;

public interface PreviewPlayback {
    void toFirstFrame();
    void toLastFrame();
    void previousFrame();
    void nextFrame();

    PlaybackInfo getPlaybackInfo();

    default void processKeys(final InputEventLogger eventLogger) {
        final PlaybackInfo playbackInfo = getPlaybackInfo();

        PREVIOUS_FRAME.shortcut.checkIfPressed(
                eventLogger, this::previousFrame);
        NEXT_FRAME.shortcut.checkIfPressed(
                eventLogger, this::nextFrame);

        TO_FIRST_FRAME.shortcut.checkIfPressed(
                eventLogger, this::toFirstFrame);
        TO_LAST_FRAME.shortcut.checkIfPressed(
                eventLogger, this::toLastFrame);

        TOGGLE_PLAYING.shortcut.checkIfPressed(
                eventLogger, playbackInfo::togglePlaying);
        CYCLE_PLAYBACK_MODE.shortcut.checkIfPressed(
                eventLogger, playbackInfo::cycleMode);
    }
}
