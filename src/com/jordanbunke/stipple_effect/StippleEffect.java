package com.jordanbunke.stipple_effect;

import com.jordanbunke.delta_time.contexts.ProgramContext;
import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.game.Game;
import com.jordanbunke.delta_time.game.GameManager;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.GameImageIO;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.text.Text;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.window.GameWindow;
import com.jordanbunke.stipple_effect.context.ImageContext;
import com.jordanbunke.stipple_effect.context.Segment;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StippleEffect implements ProgramContext {
    private static final StippleEffect INSTANCE;

    public final Game game;
    public final GameWindow window;

    private final List<ImageContext> contexts;
    private int contextIndex;

    private Coord2D mousePosition;
    private Segment segment;

    static {
        INSTANCE = new StippleEffect();
    }

    public StippleEffect() {
        final Coord2D size = determineWindowSize();
        window = new GameWindow(Constants.PROGRAM_NAME,
                size.x, size.y,
                /* TODO - program icon */ GameImage.dummy(),
                true, false, false);
        final GameManager manager = new GameManager(0, this);

        game = new Game(window, manager, Constants.TICK_HZ, Constants.FPS);
        game.setCanvasSize(Constants.CANVAS_W, Constants.CANVAS_H);
        game.getDebugger().muteChannel(GameDebugger.FRAME_RATE);

        contexts = new ArrayList<>();
        contextIndex = 0;
    }

    private Coord2D determineWindowSize() {
        final int h = Toolkit.getDefaultToolkit().getScreenSize().height - Constants.SCREEN_HEIGHT_BUFFER;
        final double scaleUp = h / (double)Constants.CANVAS_H;
        return new Coord2D((int)(scaleUp * Constants.CANVAS_W), h);
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
        get().contexts.add(new ImageContext(Constants.DEFAULT_IMAGE_WIDTH,
                Constants.DEFAULT_IMAGE_HEIGHT));
    }

    private static void launchWithFile(final Path fp) {
        final GameImage image = GameImageIO.readImage(fp);
        get().contexts.add(new ImageContext(image));
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        // workspace
        contexts.get(contextIndex).process(eventLogger);
    }

    @Override
    public void update(final double deltaTime) {

    }

    @Override
    public void render(final GameImage canvas) {
        // workspace
        final GameImage workspace = contexts.get(contextIndex).drawWorkspace();
        canvas.draw(workspace, Constants.TOOLS_W, Constants.CONTEXTS_H);
        // tools
        // TODO
        // layers
        // TODO
        // color picker
        // TODO
        // contexts
        // TODO
        // bottom bar - zoom, animation
        final GameImage bottomBar = drawBottomBar();
        canvas.draw(bottomBar, 0, Constants.CONTEXTS_H + Constants.WORKSPACE_H);

        // borders
        canvas.setColor(Constants.BLACK);
        canvas.drawLine(1f, 0, Constants.CONTEXTS_H,
                Constants.CANVAS_W, Constants.CONTEXTS_H);
        canvas.drawLine(1f, 0, Constants.CONTEXTS_H + Constants.WORKSPACE_H,
                Constants.CANVAS_W, Constants.CONTEXTS_H + Constants.WORKSPACE_H);
        canvas.drawLine(1f, Constants.TOOLS_W + Constants.WORKSPACE_W,
                Constants.CONTEXTS_H + Constants.LAYERS_H, Constants.CANVAS_W,
                Constants.CONTEXTS_H + Constants.LAYERS_H);
        canvas.drawLine(1f, Constants.TOOLS_W, Constants.CONTEXTS_H,
                Constants.TOOLS_W, Constants.CONTEXTS_H + Constants.WORKSPACE_H);
        canvas.drawLine(1f, Constants.TOOLS_W + Constants.WORKSPACE_W,
                Constants.CONTEXTS_H, Constants.TOOLS_W + Constants.WORKSPACE_W,
                Constants.CONTEXTS_H + Constants.WORKSPACE_H);
    }

    private GameImage drawBottomBar() {
        final GameImage bottomBar = new GameImage(Constants.CANVAS_W, Constants.BOTTOM_BAR_H);
        bottomBar.fillRectangle(Constants.ACCENT_BACKGROUND, 0, 0,
                Constants.CANVAS_W, Constants.BOTTOM_BAR_H);

        final GameImage targetPixel = GraphicsUtils.uiText()
                .addText(contexts.get(contextIndex).getTargetPixelText())
                .build().draw();
        bottomBar.draw(targetPixel, Constants.TP_X, Constants.BOTTOM_BAR_TEXT_Y_OFFSET);
        final GameImage size = GraphicsUtils.uiText()
                .addText(contexts.get(contextIndex).getCanvasSizeText())
                .build().draw();
        bottomBar.draw(size, Constants.SIZE_X, Constants.BOTTOM_BAR_TEXT_Y_OFFSET);

        // TODO - zoom
        // TODO - animation frames

        return bottomBar.submit();
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
