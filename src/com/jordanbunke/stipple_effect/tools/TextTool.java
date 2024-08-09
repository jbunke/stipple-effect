package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.fonts.Font;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.delta_time.utility.DeltaTimeGlobal;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.*;
import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;
import com.jordanbunke.stipple_effect.utility.action.SEAction;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.SEFonts;
import com.jordanbunke.stipple_effect.visual.menu_elements.*;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class TextTool extends Tool {

    private static final TextTool INSTANCE;

    // fields
    private boolean typing, wasTimerToggle;
    private final List<TextToolFont> fonts;
    private int fontIndex, fontScale;
    private String text, lastText;
    private Coord2D textPos;
    private Text.Orientation alignment;
    private GameImage textImage, toolContentPreview;

    static {
        INSTANCE = new TextTool();
    }

    private TextTool() {
        typing = false;
        fonts = Arrays.stream(SEFonts.Code.values())
                .map(c -> new TextToolFont(c.forButtonText(), c.associated()))
                .collect(Collectors.toList());
        fontIndex = 0;

        fontScale = Constants.MIN_FONT_SCALE;
        text = "";
        textPos = new Coord2D();
        alignment = Text.Orientation.LEFT;

        textImage = GameImage.dummy();
        toolContentPreview = GameImage.dummy();

        // update conditions
        wasTimerToggle = false;
        lastText = "";
    }

    public static TextTool get() {
        return INSTANCE;
    }

    @Override
    public void onClick(final SEContext context, final GameMouseEvent me) {
        final Coord2D tp = context.getTargetPixel();

        if (!tp.equals(Constants.NO_VALID_TARGET)) {
            final boolean wasTyping = typing;

            if (typing)
                if (me.button == GameMouseEvent.Button.MIDDLE)
                    setTyping(false);
                else
                    finish(context);

            if (!wasTyping || me.button == GameMouseEvent.Button.RIGHT)
                initialize(tp, context);

            me.markAsProcessed();
        }
    }

    private void initialize(final Coord2D tp, final SEContext context) {
        setTyping(true);
        text = "";
        textPos = tp.displace(0, fontScale * Layout.TEXT_CARET_Y_OFFSET);

        updateToolContentPreview(context);
        resetLast();
    }

    private void nextLine(final SEContext context) {
        finish(context);

        setTyping(true);
        text = "";
        textPos = textPos.displace(0, fontScale * Layout.TEXT_LINE_PX_H);

        updateToolContentPreview(context);
        resetLast();
    }

    private void resetLast() {
        lastText = text;
        wasTimerToggle = StippleEffect.get().isTimerToggle();
    }

    private void finish(final SEContext context) {
        final int w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();

        final GameImage edit = new GameImage(w, h);
        edit.draw(textImage, getFormattedTextX(), textPos.y);
        context.paintOverImage(edit.submit());

        context.getState().markAsCheckpoint(true);

        setTyping(false);
    }

    private void adjustTextPosition(
            final int deltaX, final int deltaY,
            final SEContext context
    ) {
        textPos = textPos.displace(deltaX, deltaY);

        updateToolContentPreview(context);
    }

    private int getFormattedTextX() {
        return switch (alignment) {
            case LEFT -> textPos.x;
            case CENTER -> textPos.x - (textImage.getWidth() / 2);
            case RIGHT -> textPos.x - textImage.getWidth();
        };
    }

    public void process(
            final SEContext context, final InputEventLogger eventLogger
    ) {
        final int DELETE = 127, LOWEST_PRINTABLE = 32;

        if (typing) {
            final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();

            for (GameEvent e : unprocessed)
                if (e instanceof GameKeyEvent keyEvent) {
                    if (keyEvent.matchesAction(GameKeyEvent.Action.PRESS)) {
                        switch (keyEvent.key) {
                            case ENTER -> {
                                keyEvent.markAsProcessed();
                                nextLine(context);
                            }
                            case LEFT_ARROW -> {
                                keyEvent.markAsProcessed();

                                if (eventLogger.isPressed(Key.SHIFT))
                                    adjustTextPosition(-1, 0, context);
                                else
                                    setFontIndex(fontIndex - 1);
                            }
                            case RIGHT_ARROW -> {
                                keyEvent.markAsProcessed();

                                if (eventLogger.isPressed(Key.SHIFT))
                                    adjustTextPosition(1, 0, context);
                                else
                                    setFontIndex(fontIndex + 1);
                            }
                            case UP_ARROW -> {
                                keyEvent.markAsProcessed();

                                if (eventLogger.isPressed(Key.SHIFT))
                                    adjustTextPosition(0, -1, context);
                                else
                                    setFontScale(fontScale - 1);
                            }
                            case DOWN_ARROW -> {
                                keyEvent.markAsProcessed();

                                if (eventLogger.isPressed(Key.SHIFT))
                                    adjustTextPosition(0, 1, context);
                                else
                                    setFontScale(fontScale + 1);
                            }
                            case K -> {
                                if (eventLogger.isPressed(Key.CTRL)) {
                                    keyEvent.markAsProcessed();
                                    toggleAlignment();
                                }
                            }
                            case ESCAPE -> {
                                if (!(eventLogger.isPressed(Key.SHIFT) &&
                                        eventLogger.isPressed(Key.CTRL))) {
                                    keyEvent.markAsProcessed();
                                    setTyping(false);
                                }
                            }
                            case BACKSPACE -> {
                                keyEvent.markAsProcessed();
                                backspace();
                            }
                        }
                    } else if (keyEvent.matchesAction(GameKeyEvent.Action.TYPE)) {
                        keyEvent.markAsProcessed();

                        final char c = keyEvent.character;

                        if (c != DELETE && c >= LOWEST_PRINTABLE)
                            typed(c);
                    }
                }
        }
    }

    private void backspace() {
        if (!text.isEmpty())
            text = text.substring(0, text.length() - 1);

        updateTextImage();
    }

    private void typed(final char appended) {
        text += appended;
        updateTextImage();
    }

    private void updateTextImage() {
        textImage = new TextBuilder(fontScale, Text.Orientation.LEFT,
                StippleEffect.get().getPrimary(), getFont())
                .addText(text).build().draw();
    }

    private Font getFont() {
        return fonts.get(fontIndex).font();
    }

    @Override
    public void update(final SEContext context, final Coord2D mousePosition) {
        if (typing) {
            // emergency cancellation
            if (!Permissions.isTyping()) {
                setTyping(false);
                return;
            }

            if (textChanged() || toggleChanged())
                updateToolContentPreview(context);

            resetLast();
        }
    }

    private boolean textChanged() {
        return !lastText.equals(text);
    }

    private boolean toggleChanged() {
        return !StippleEffect.get().isTimerToggle() == wasTimerToggle;
    }

    private void updateToolContentPreview(final SEContext context) {
        updateTextImage();

        final int w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();

        toolContentPreview = new GameImage(w, h);

        toolContentPreview.draw(textImage, getFormattedTextX(), textPos.y);

        final Color caretColor = StippleEffect.get().isTimerToggle()
                ? SEColors.white() : SEColors.black();
        final int caretX = getFormattedTextX() + textImage.getWidth();
        toolContentPreview.fillRectangle(caretColor, caretX, textPos.y,
                Layout.TEXT_CARET_W * fontScale,
                Layout.TEXT_CARET_H * fontScale);
    }

    @Override
    public String getName() {
        return "Text Tool";
    }

    @Override
    public String getCursorCode() {
        return super.getCursorCode() + (typing ? "_typing" : "");
    }

    public boolean isTyping() {
        return typing;
    }

    public void setTyping(final boolean typing) {
        this.typing = typing;

        DeltaTimeGlobal.setStatus(Constants.TYPING_CODE, this.typing);
    }

    public int getFontScale() {
        return fontScale;
    }

    public void setFontScale(final int fontScale) {
        this.fontScale = MathPlus.bounded(Constants.MIN_FONT_SCALE,
                fontScale, Constants.MAX_FONT_SCALE);

        updateTCPIfTyping();
    }

    public void setAlignment(final Text.Orientation alignment) {
        this.alignment = alignment;

        updateTCPIfTyping();
    }

    public void toggleAlignment() {
        setAlignment(EnumUtils.next(alignment));
    }

    public int getFontIndex() {
        return fontIndex;
    }

    public void setFontIndex(final int fontIndex) {
        this.fontIndex = MathPlus.bounded(0, fontIndex, fonts.size() - 1);

        StippleEffect.get().rebuildToolButtonMenu();
        updateTCPIfTyping();
    }

    public void addFont() {
        final String name = DialogVals.getFontName();
        final Font raw = SEFonts.buildNewFont();
        final TextToolFont font = new TextToolFont(name, raw);

        fonts.add(font);
        setFontIndex(fonts.size() - 1);
    }

    public void deleteFont() {
        fonts.remove(fontIndex);
        setFontIndex(fontIndex - 1);
    }

    private void updateTCPIfTyping() {
        if (typing)
            updateToolContentPreview(StippleEffect.get().getContext());
    }

    @Override
    public String getBottomBarText() {
        return (typing ? "[Typing] " : "") + "Text (" + fontScale + "x scale)";
    }

    @Override
    public boolean hasToolContentPreview() {
        return typing;
    }

    @Override
    public GameImage getToolContentPreview() {
        return toolContentPreview;
    }

    @Override
    public boolean hasToolOptionsBar() {
        return true;
    }

    @Override
    public MenuElementGrouping buildToolOptionsBar() {
        // scale label
        final TextLabel scaleLabel = TextLabel.make(
                getFirstOptionLabelPosition(), "Scale");

        final IncrementalRangeElements<Integer> scale =
                IncrementalRangeElements.makeForInt(scaleLabel,
                        Layout.optionsBarButtonY(), Layout.optionsBarTextY(),
                        1, Constants.MIN_FONT_SCALE, Constants.MAX_FONT_SCALE,
                        this::setFontScale, this::getFontScale,
                        i -> i, i -> i, fs -> fs + "x",
                        Constants.MAX_FONT_SCALE + "x");

        // alignment label
        final TextLabel alignmentLabel = TextLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(scale.value, true),
                Layout.optionsBarTextY()), "Alignment");

        // alignment toggle
        final IconToggleButton alignmentToggle = IconToggleButton.make(new Coord2D(
                Layout.optionsBarNextElementX(alignmentLabel, false),
                        Layout.optionsBarButtonY()),
                Arrays.stream(Text.Orientation.values())
                        .map(a -> a.name().toLowerCase() + "_aligned")
                        .toArray(String[]::new),
                Arrays.stream(Text.Orientation.values())
                        .map(a -> (Runnable) () -> setAlignment(EnumUtils.next(a)))
                        .toArray(Runnable[]::new), () -> alignment.ordinal(), () -> {});

        // font label
        final TextLabel fontLabel = TextLabel.make(new Coord2D(
                Layout.optionsBarNextElementX(alignmentToggle, true),
                        Layout.optionsBarTextY()), "Font");

        // font dropdown
        final Dropdown fontDropdown = Dropdown.forToolOptionsBar(
                Layout.optionsBarNextElementX(fontLabel, false),
                fonts.stream().map(TextToolFont::name).toArray(String[]::new),
                fonts.stream().map(ttf -> (Runnable) () ->
                                setFontIndex(fonts.indexOf(ttf)))
                        .toArray(Runnable[]::new), () -> fontIndex);

        // upload font button
        final MenuElement newFontButton = new ActionButton(
                new Coord2D(Layout.optionsBarNextButtonX(fontDropdown),
                        Layout.optionsBarButtonY()), SEAction.NEW_FONT, null);

        // delete font button
        final MenuElement deleteFontButton = GraphicsUtils
                .generateIconButton(ResourceCodes.DELETE_FONT, new Coord2D(
                        Layout.optionsBarNextButtonX(newFontButton),
                                Layout.optionsBarButtonY()),
                        () -> fontIndex >= SEFonts.Code.values().length,
                        this::deleteFont);

        return new MenuElementGrouping(super.buildToolOptionsBar(),
                scaleLabel, scale.decButton, scale.incButton,
                scale.slider, scale.value,
                alignmentLabel, alignmentToggle,
                fontLabel, fontDropdown, newFontButton, deleteFontButton);
    }
}
