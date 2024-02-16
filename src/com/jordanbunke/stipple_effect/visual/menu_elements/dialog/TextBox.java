package com.jordanbunke.stipple_effect.visual.menu_elements.dialog;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.button.MenuButtonStub;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.DeltaTimeGlobal;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TextBox extends MenuButtonStub {
    private String text, lastText, prefix, suffix;
    private int cursorIndex, lastCursorIndex,
            selectionIndex, lastSelectionIndex;
    private boolean typing;

    private final Supplier<String> prefixGetter, suffixGetter;
    private final Supplier<Color> backgroundColorGetter;
    private final Function<String, Boolean> textValidator;
    private final Consumer<String> setter;
    private final int maxLength;

    private GameImage validImage, invalidImage, highlightedImage, typingImage;

    public TextBox(
            final Coord2D position, final int width,
            final Anchor anchor, final String initialText,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter,
            final int maxLength
    ) {
        this(position, width, anchor, () -> "", initialText, () -> "",
                textValidator, setter, () -> Constants.GREY, maxLength);
    }

    public TextBox(
            final Coord2D position, final int width, final Anchor anchor,
            final String prefix, final String initialText, final String suffix,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter,
            final int maxLength
    ) {
        this(position, width, anchor, () -> prefix, initialText, () -> suffix,
                textValidator, setter, () -> Constants.GREY, maxLength);
    }

    public TextBox(
            final Coord2D position, final int width, final Anchor anchor,
            final Supplier<String> prefixGetter, final String initialText,
            final Supplier<String> suffixGetter,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter,
            final Supplier<Color> backgroundColorGetter, final int maxLength
    ) {
        super(position, new Coord2D(width, Layout.STD_TEXT_BUTTON_H),
                anchor, true);

        this.prefixGetter = prefixGetter;
        this.suffixGetter = suffixGetter;

        text = initialText;
        cursorIndex = text.length();
        selectionIndex = cursorIndex;
        typing = false;

        this.backgroundColorGetter = backgroundColorGetter;
        this.textValidator = textValidator;
        this.setter = setter;
        this.maxLength = maxLength;

        updateAssets();
    }

    public static Function<String, Boolean> getIntTextValidator(
            final int min, final int max
    ) {
        return s -> {
            try {
                final int value = Integer.parseInt(s);

                return value >= min && value <= max;
            } catch (NumberFormatException e) {
                return false;
            }
        };
    }

    public static Function<String, Boolean> getIntTextValidator(
            final Function<Integer, Boolean> embeddedLogic
    ) {
        return s -> {
            try {
                final int value = Integer.parseInt(s);

                return embeddedLogic.apply(value);
            } catch (NumberFormatException e) {
                return false;
            }
        };
    }

    public static boolean validateAsFileName(final String text) {
        return validateAsFileName(text, false);
    }

    public static boolean validateAsOptionallyEmptyFilename(final String text) {
        return validateAsFileName(text, true);
    }

    public static boolean validateAsFileName(final String text, final boolean allowEmpty) {
        final Set<Character> illegalCharSet = Set.of(
                '/', '\\', ':', '*', '?', '"', '<', '>', '|');

        return (allowEmpty || !text.isEmpty()) && illegalCharSet.stream()
                .map(c -> text.indexOf(c) == -1)
                .reduce((a, b) -> a && b).orElse(false);
    }

    protected void updateAssets() {
        prefix = prefixGetter.get();
        suffix = suffixGetter.get();

        validImage = GraphicsUtils.drawTextBox(getWidth(), prefix,
                text, suffix, cursorIndex, selectionIndex, false,
                Constants.BLACK, backgroundColorGetter.get());
        invalidImage = GraphicsUtils.drawTextBox(getWidth(), prefix,
                text, suffix, cursorIndex, selectionIndex, false,
                Constants.INVALID, backgroundColorGetter.get());
        highlightedImage = GraphicsUtils.drawTextBox(getWidth(), prefix,
                text, suffix, cursorIndex, selectionIndex, true,
                Constants.BLACK, backgroundColorGetter.get());
        typingImage = GraphicsUtils.drawTextBox(getWidth(), prefix,
                text, suffix, cursorIndex, selectionIndex, false,
                Constants.HIGHLIGHT_1, backgroundColorGetter.get());

        lastText = text;
        lastCursorIndex = cursorIndex;
        lastSelectionIndex = selectionIndex;
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        super.process(eventLogger);
        processClickOff(eventLogger);
        processTyping(eventLogger);
    }

    private void processClickOff(final InputEventLogger eventLogger) {
        if (typing && !isHighlighted()) {
            final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();

            for (GameEvent e : unprocessed)
                if (e instanceof GameMouseEvent me && me.matchesAction(GameMouseEvent.Action.DOWN)) {
                    // DO NOT MARK AS PROCESSED!

                    typing = false;
                    clickedOffBehaviour();
                    DeltaTimeGlobal.setStatus(Constants.TYPING_CODE, false);
                    return;
                }
        }
    }

    private void processTyping(final InputEventLogger eventLogger) {
        final int DELETE = 127, LOWEST_PRINTABLE = 32;

        if (typing) {
            final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();

            for (GameEvent e : unprocessed)
                if (e instanceof GameKeyEvent keyEvent) {
                    if (keyEvent.matchesAction(GameKeyEvent.Action.PRESS)) {
                        switch (keyEvent.key) {
                            // No longer setting button input
                            case ENTER, TAB, ESCAPE -> {
                                keyEvent.markAsProcessed();

                                typing = false;
                                clickedOffBehaviour();
                                DeltaTimeGlobal.setStatus(Constants.TYPING_CODE, false);
                            }
                            // Remove character before cursor from input string
                            case BACKSPACE -> {
                                keyEvent.markAsProcessed();

                                if (hasSelection())
                                    collapseSelection();
                                else if (cursorIndex > 0) {
                                    text = text.substring(0, cursorIndex - 1) +
                                            text.substring(cursorIndex);
                                    cursorIndex--;
                                    selectionIndex = cursorIndex;
                                }

                                DeltaTimeGlobal.setStatus(Constants.TYPING_CODE, true);
                            }
                            // Removes character after cursor from input string
                            case DELETE -> {
                                keyEvent.markAsProcessed();

                                if (hasSelection())
                                    collapseSelection();
                                else if (cursorIndex < text.length()) {
                                    text = text.substring(0, cursorIndex) +
                                            text.substring(cursorIndex + 1);
                                }

                                DeltaTimeGlobal.setStatus(Constants.TYPING_CODE, true);
                            }
                            // moves cursor index back if possible
                            case LEFT_ARROW ->  {
                                keyEvent.markAsProcessed();

                                if (eventLogger.isPressed(Key.SHIFT)) {
                                    if (cursorIndex > 0)
                                        cursorIndex--;
                                } else {
                                    if (hasSelection())
                                        cursorIndex = leftIndex();
                                    else if (cursorIndex > 0)
                                        cursorIndex--;

                                    selectionIndex = cursorIndex;
                                }

                                DeltaTimeGlobal.setStatus(Constants.TYPING_CODE, true);
                            }
                            // moves cursor index forwards if possible
                            case RIGHT_ARROW -> {
                                keyEvent.markAsProcessed();

                                if (eventLogger.isPressed(Key.SHIFT)) {
                                    if (cursorIndex < text.length())
                                        cursorIndex++;
                                } else {
                                    if (hasSelection())
                                        cursorIndex = rightIndex();
                                    else if (cursorIndex < text.length())
                                        cursorIndex++;

                                    selectionIndex = cursorIndex;
                                }

                                DeltaTimeGlobal.setStatus(Constants.TYPING_CODE, true);
                            }
                        }

                        attemptAccept();
                    } else if (keyEvent.matchesAction(GameKeyEvent.Action.TYPE)) {
                        keyEvent.markAsProcessed();

                        final char c = keyEvent.character;

                        if (c == DELETE || c < LOWEST_PRINTABLE ||
                                text.length() + 1 - (rightIndex() - leftIndex()) > maxLength)
                            continue;

                        if (hasSelection())
                            collapseSelection();

                        text = text.substring(0, cursorIndex) + c +
                                text.substring(cursorIndex);
                        cursorIndex++;
                        selectionIndex = cursorIndex;

                        attemptAccept();
                        DeltaTimeGlobal.setStatus(Constants.TYPING_CODE, true);
                    }
                }
        }
    }

    @Override
    public void execute() {
        typing = !typing;

        if (typing)
            clickedOnBehaviour();
        else
            clickedOffBehaviour();

        DeltaTimeGlobal.setStatus(Constants.TYPING_CODE, typing);
    }

    private void clickedOnBehaviour() {
        cursorIndex = text.length();
        selectionIndex = 0;
    }

    private void clickedOffBehaviour() {
        attemptAccept();

        cursorIndex = text.length();
        selectionIndex = cursorIndex;
    }

    private void attemptAccept() {
        if (isValid())
            setter.accept(text);
    }

    @Override
    public void update(final double deltaTime) {
        if (!text.equals(lastText) || cursorIndex != lastCursorIndex ||
                selectionIndex != lastSelectionIndex ||
                !prefix.equals(prefixGetter.get()) ||
                !suffix.equals(suffixGetter.get()))
            updateAssets();
    }

    @Override
    public void render(final GameImage canvas) {
        draw(typing ? typingImage :
                        (isHighlighted() ? highlightedImage :
                                (isValid() ? validImage : invalidImage)),
                canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    public boolean isValid() {
        return textValidator.apply(text);
    }

    public boolean isTyping() {
        return typing;
    }

    private int leftIndex() {
        return Math.min(selectionIndex, cursorIndex);
    }

    private int rightIndex() {
        return Math.max(selectionIndex, cursorIndex);
    }

    private boolean hasSelection() {
        return selectionIndex != cursorIndex;
    }

    private void collapseSelection() {
        text = text.substring(0, leftIndex()) +
                text.substring(rightIndex());
        cursorIndex = leftIndex();
        selectionIndex = cursorIndex;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
