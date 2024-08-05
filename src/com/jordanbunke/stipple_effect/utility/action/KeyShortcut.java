package com.jordanbunke.stipple_effect.utility.action;

import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.io.InputEventLogger;

import java.util.Arrays;

import static com.jordanbunke.delta_time.events.GameKeyEvent.Action.PRESS;
import static com.jordanbunke.delta_time.events.GameKeyEvent.newKeyStroke;
import static com.jordanbunke.delta_time.events.Key.CTRL;
import static com.jordanbunke.delta_time.events.Key.SHIFT;

public final class KeyShortcut {
    public final boolean ctrl, shift;
    public final GameKeyEvent keyStroke;

    public static KeyShortcut single(final Key key) {
        return new KeyShortcut(false, false, key);
    }

    public KeyShortcut(final boolean ctrl, final boolean shift, final Key key) {
        this.ctrl = ctrl;
        this.shift = shift;

        keyStroke = newKeyStroke(key, PRESS);
    }

    public static boolean areModKeysPressed(
            final boolean ctrl, final boolean shift,
            final InputEventLogger eventLogger
    ) {
        return ctrl == eventLogger.isPressed(CTRL) &&
                shift == eventLogger.isPressed(SHIFT);
    }

    public void checkIfPressed(
            final InputEventLogger eventLogger,
            final Runnable behaviour
    ) {
        if (areModKeysPressed(ctrl, shift, eventLogger))
            eventLogger.checkForMatchingKeyStroke(keyStroke, behaviour);
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof KeyShortcut that)
            return this.ctrl == that.ctrl && this.shift == that.shift &&
                this.keyStroke.equals(that.keyStroke);

        return false;
    }

    @Override
    public String toString() {
        return (ctrl ? format(CTRL) + " + " : "") +
                (shift ? format(SHIFT) + " + " : "") + format(keyStroke.key);
    }

    private static String format(final Key key) {
        return Arrays.stream(unwrap(key).split(" "))
                .map(KeyShortcut::capitalizeFirstLetter)
                .reduce("", (a, b) -> a.length() == 0 ? b : a + " " + b);
    }

    private static String capitalizeFirstLetter(final String key) {
        return key.charAt(0) + key.substring(1).toLowerCase();
    }

    private static String unwrap(final Key key) {
        final String b = "[ ", e = " ]", s = key.toString();

        if (s.startsWith(b) && s.endsWith(e))
            return s.substring(b.length(), s.length() - e.length());

        return s;
    }
}
