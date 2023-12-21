package com.jordanbunke.stipple_effect.menu_elements.dialog;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.button.MenuButtonStub;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TextBox extends MenuButtonStub {
    private String text, lastText;
    private int cursorIndex, lastCursorIndex;
    private boolean typing;

    private final String prefix, suffix;
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
        this(position, width, anchor, "", initialText, "",
                textValidator, setter, () -> Constants.GREY, maxLength);
    }

    public TextBox(
            final Coord2D position, final int width, final Anchor anchor,
            final String prefix, final String initialText, final String suffix,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter,
            final int maxLength
    ) {
        this(position, width, anchor, prefix, initialText, suffix,
                textValidator, setter, () -> Constants.GREY, maxLength);
    }

    public TextBox(
            final Coord2D position, final int width, final Anchor anchor,
            final String prefix, final String initialText, final String suffix,
            final Function<String, Boolean> textValidator,
            final Consumer<String> setter,
            final Supplier<Color> backgroundColorGetter,
            final int maxLength
    ) {
        super(position, new Coord2D(width, Constants.STD_TEXT_BUTTON_H),
                anchor, true);

        this.prefix = prefix;
        this.suffix = suffix;

        text = initialText;
        cursorIndex = text.length();
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
        final Set<Character> illegalCharSet = Set.of(
                '/', '\\', ':', '*', '?', '"', '<', '>', '|');

        return !text.isEmpty() && illegalCharSet.stream()
                .map(c -> text.indexOf(c) == -1)
                .reduce((a, b) -> a && b).orElse(false);
    }

    protected void updateAssets() {
        validImage = GraphicsUtils.drawTextBox(getWidth(), prefix,
                text, suffix, cursorIndex, false, Constants.BLACK,
                backgroundColorGetter.get());
        invalidImage = GraphicsUtils.drawTextBox(getWidth(), prefix,
                text, suffix, cursorIndex, false, Constants.INVALID,
                backgroundColorGetter.get());
        highlightedImage = GraphicsUtils.drawTextBox(getWidth(), prefix,
                text, suffix, cursorIndex, true, Constants.BLACK,
                backgroundColorGetter.get());
        typingImage = GraphicsUtils.drawTextBox(getWidth(), prefix,
                text, suffix, cursorIndex, false, Constants.HIGHLIGHT_1,
                backgroundColorGetter.get());

        lastText = text;
        lastCursorIndex = cursorIndex;
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        super.process(eventLogger);
        processClickOff(eventLogger);
        processTyping(eventLogger);
    }

    private void processClickOff(final InputEventLogger eventLogger) {
        if (!isHighlighted()) {
            final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();

            for (GameEvent e : unprocessed)
                if (e instanceof GameMouseEvent me && me.matchesAction(GameMouseEvent.Action.CLICK)) {
                    // DO NOT MARK AS PROCESSED!

                    typing = false;
                    clickedOffBehaviour();
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
                            }
                            // Remove character before cursor from input string
                            case BACKSPACE -> {
                                keyEvent.markAsProcessed();

                                if (cursorIndex > 0) {
                                    text = text.substring(0, cursorIndex - 1) +
                                            text.substring(cursorIndex);
                                    cursorIndex--;
                                }
                            }
                            // Removes character after cursor from input string
                            case DELETE -> {
                                keyEvent.markAsProcessed();

                                if (cursorIndex < text.length()) {
                                    text = text.substring(0, cursorIndex) +
                                            text.substring(cursorIndex + 1);
                                }
                            }
                            // moves cursor index back if possible
                            case LEFT_ARROW ->  {
                                if (cursorIndex > 0)
                                    cursorIndex--;
                            }
                            // moves cursor index forwards if possible
                            case RIGHT_ARROW -> {
                                if (cursorIndex < text.length())
                                    cursorIndex++;
                            }
                        }

                        clickedOffBehaviour();
                    } else if (keyEvent.matchesAction(GameKeyEvent.Action.TYPE)) {
                        keyEvent.markAsProcessed();

                        final char c = keyEvent.character;

                        if (c == DELETE || c < LOWEST_PRINTABLE || text.length() >= maxLength)
                            continue;

                        text = text.substring(0, cursorIndex) + c +
                                text.substring(cursorIndex);
                        cursorIndex++;

                        clickedOffBehaviour();
                    }
                }
        }
    }

    @Override
    public void execute() {
        typing = !typing;

        if (!typing)
            clickedOffBehaviour();
    }

    private void clickedOffBehaviour() {
        if (isValid())
            setter.accept(text);
    }

    @Override
    public void update(final double deltaTime) {
        if (!text.equals(lastText) || cursorIndex != lastCursorIndex)
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

    public void setText(final String text) {
        this.text = text;
    }
}
