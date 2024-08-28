package com.jordanbunke.stipple_effect.preview;

import com.jordanbunke.delta_time._core.GameManager;
import com.jordanbunke.delta_time._core.Program;
import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameWindowEvent;
import com.jordanbunke.delta_time.events.WindowMovedEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.window.GameWindow;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.SECursor;

import java.util.List;

public final class WindowedPreview extends Preview {
    private static int winX, winY, winW, winH;

    private Coord2D mousePos;
    private final GameWindow window;
    private final Program preview;

    static {
        winX = 0;
        winY = 0;
        winW = Layout.PREV_MIN_W;
        winH = Layout.PREV_MIN_H;
    }

    WindowedPreview(final SEContext c) {
        super(c, new Coord2D(), new Bounds2D(winW, winH));

        mousePos = new Coord2D();

        window = makeWindow();
        preview = new Program(window, new GameManager(0, this));

        configure();
    }

    private GameWindow makeWindow() {
        return new GameWindow("Preview: " +
                c.getSaveConfig().getFormattedName(false, false),
                winW, winH, GraphicsUtils.readIconAsset(ResourceCodes.PROGRAM),
                false, true, false);
    }

    private void configure() {
        preview.getDebugger().getChannel(GameDebugger.FRAME_RATE).mute();
        preview.setScheduleUpdates(false);

        window.hideCursor();
        window.setPosition(winX, winY);
        window.setSizeBounds(Preview.minSize(), Preview.maxSize());

        window.setOnCloseBehaviour(preview::terminateExecution);
        window.setPostResizeLogic((w, h) -> {
            setDimensions(new Bounds2D(w, h));

            winW = w;
            winH = h;

            preview.setCanvasSize(w, h);
        });
    }

    @Override
    public void render(final GameImage canvas) {
        super.render(canvas);
        renderCursor(canvas);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        super.process(eventLogger);

        mousePos = eventLogger.getAdjustedMousePosition();

        final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();

        for (GameEvent event : unprocessed)
            if (event instanceof GameWindowEvent gwe && gwe.action == GameWindowEvent.Action.CLOSING) {
                window.executeOnClose();
                gwe.markAsProcessed();
                return;
            } else if (event instanceof WindowMovedEvent wme) {
                wme.markAsProcessed();

                winX = wme.x;
                winY = wme.y;
            }
    }

    private void renderCursor(final GameImage canvas) {
        final boolean inContainer = container.mouseIsWithinBounds(mousePos);
        final String cursorCode = inContainer
                ? (container.isPanning() ? SECursor.HAND_GRAB : SECursor.HAND_OPEN)
                : SECursor.MAIN_CURSOR;

        canvas.draw(SECursor.fetchCursor(cursorCode),
                mousePos.x - (Layout.CURSOR_DIM / 2),
                mousePos.y - (Layout.CURSOR_DIM / 2));
    }

    @Override
    protected void close() {
        window.closeInstance();
        kill();
    }

    @Override
    protected void executeOnFileDialogOpen() {
        window.getEventLogger().unpressAllKeys();
    }
}
