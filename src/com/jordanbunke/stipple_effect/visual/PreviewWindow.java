package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.contexts.ProgramContext;
import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameWindowEvent;
import com.jordanbunke.delta_time.game.Game;
import com.jordanbunke.delta_time.game.GameManager;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.window.GameWindow;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.IconCodes;

import java.util.List;

public class PreviewWindow implements ProgramContext {
    private static PreviewWindow INSTANCE = null;

    private GameWindow window;
    private final SEContext context;
    private final Game preview;

    public static void set(final SEContext context) {
        if (INSTANCE != null) {
            INSTANCE.window.closeInstance();
        }

        INSTANCE = new PreviewWindow(context);
    }

    private PreviewWindow(final SEContext context) {
        this.context = context;

        window = makeWindow();
        preview = new Game(window, new GameManager(0, this));
        preview.getDebugger().getChannel(GameDebugger.FRAME_RATE).mute();
        preview.setScheduleUpdates(false);
        window.setOnCloseBehaviour(preview::terminateExecution);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();

        for (GameEvent event : unprocessed)
            if (event instanceof GameWindowEvent gwe && gwe.action == GameWindowEvent.Action.CLOSING) {
                window.executeOnClose();
                gwe.markAsProcessed();
                return;
            }
    }

    @Override
    public void update(final double deltaTime) {
        if (checkIfProjectHasBeenClosed()) {
            window.closeInstance();
            return;
        }

        windowUpdateCheck();
    }

    private boolean checkIfProjectHasBeenClosed() {
        return !context.equals(StippleEffect.get().getContext());
    }

    private void windowUpdateCheck() {
        final int winWidth = window.getWidth(), winHeight = window.getHeight(),
                w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();

        if (winWidth != w || winHeight != h)
            updateWindow();
    }

    private void updateWindow() {
        window = makeWindow();
        preview.replaceWindow(window);
        preview.setCanvasSize(window.getWidth(), window.getHeight());
        window.setOnCloseBehaviour(preview::terminateExecution);
    }

    private GameWindow makeWindow() {
        final int w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();

        return new GameWindow("Preview: " +
                context.projectInfo.getFormattedName(false, false),
                w, h, GraphicsUtils.loadIcon(IconCodes.PROGRAM),
                false, false, false);
    }

    @Override
    public void render(final GameImage canvas) {
        canvas.draw(context.getState().draw(false, false,
                context.getState().getFrameIndex()), 0, 0);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
