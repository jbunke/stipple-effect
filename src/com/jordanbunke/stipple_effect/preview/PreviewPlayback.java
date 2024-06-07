package com.jordanbunke.stipple_effect.preview;

import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.stipple_effect.project.PlaybackInfo;
import com.jordanbunke.stipple_effect.utility.Constants;

public interface PreviewPlayback {
    void toFirstFrame();
    void toLastFrame();
    void previousFrame();
    void nextFrame();

    PlaybackInfo getPlaybackInfo();

    default void processKeys(final InputEventLogger eventLogger) {
        final PlaybackInfo playbackInfo = getPlaybackInfo();

        if (eventLogger.isPressed(Key.CTRL) && eventLogger.isPressed(Key.SHIFT)) {
            // CTRL + SHIFT + ?
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    this::previousFrame);
        } else if (eventLogger.isPressed(Key.CTRL)) {
            // CTRL + ?
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    this::nextFrame);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                    this::toFirstFrame);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                    this::toLastFrame);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ENTER, GameKeyEvent.Action.PRESS),
                    playbackInfo::toggleMode);
        } else if (eventLogger.isPressed(Key.SHIFT)) {
            // SHIFT + ?
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.LEFT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> playbackInfo.incrementFps(-Constants.PLAYBACK_FPS_INC));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.RIGHT_ARROW, GameKeyEvent.Action.PRESS),
                    () -> playbackInfo.incrementFps(Constants.PLAYBACK_FPS_INC));
        } else {
            // single key presses
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.SPACE, GameKeyEvent.Action.PRESS),
                    playbackInfo::togglePlaying);
        }
    }
}
