package com.jordanbunke.stipple_effect;

import com.jordanbunke.delta_time.contexts.ProgramContext;
import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.game.Game;
import com.jordanbunke.delta_time.game.GameManager;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.window.GameWindow;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.nio.file.Path;
import java.util.Arrays;

public class StippleEffect implements ProgramContext {
    private static final StippleEffect INSTANCE;

    public final Game game;
    public final GameWindow window;

    static {
        INSTANCE = new StippleEffect();
    }

    public StippleEffect() {
        window = new GameWindow(Constants.PROGRAM_NAME,
                Constants.CANVAS_W * Constants.SCALE_UP,
                Constants.CANVAS_H * Constants.SCALE_UP,
                /* TODO */ GameImage.dummy(),
                true, false, false);
        final GameManager manager = new GameManager(0, this);

        game = new Game(window, manager, Constants.TICK_HZ, Constants.FPS);
        game.setCanvasSize(Constants.CANVAS_W, Constants.CANVAS_H);
    }

    public static StippleEffect get() {
        return INSTANCE;
    }

    public static void main(final String[] args) {
        if (args.length == 0)
            launchWithoutFile();
        else
            launchWithFile(
                    Path.of(Arrays.stream(args).reduce("", (a, b) -> a + b))
            );
    }

    private static void launchWithoutFile() {
        // TODO
    }

    private static void launchWithFile(final Path fp) {
        // TODO
    }

    @Override
    public void process(final InputEventLogger eventLogger) {

    }

    @Override
    public void update(final double deltaTime) {

    }

    @Override
    public void render(final GameImage canvas) {

    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
