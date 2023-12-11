package com.jordanbunke.stipple_effect;

import com.jordanbunke.delta_time.contexts.ProgramContext;
import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.game.Game;
import com.jordanbunke.delta_time.game.GameManager;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.GameImageIO;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.Menu;
import com.jordanbunke.delta_time.menus.MenuBuilder;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.window.GameWindow;
import com.jordanbunke.stipple_effect.context.ImageContext;
import com.jordanbunke.stipple_effect.tools.*;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StippleEffect implements ProgramContext {
    private static final Tool[] ALL_TOOLS = new Tool[] {
            Hand.get(), Zoom.get(),
            StipplePencil.get(), Pencil.get(), Brush.get(),
            // TODO - populate
    };

    private static final StippleEffect INSTANCE;

    public final Game game;
    public GameWindow window;
    private boolean windowed;

    // contexts
    private final List<ImageContext> contexts;
    private int contextIndex;

    // tools
    private Menu toolButtonMenu;
    private Tool tool;

    // color picker
    private Color primary, secondary;

    static {
        INSTANCE = new StippleEffect();
    }

    public StippleEffect() {
        contexts = new ArrayList<>(List.of(
                new ImageContext(Constants.DEFAULT_IMAGE_WIDTH,
                        Constants.DEFAULT_IMAGE_HEIGHT)));
        contextIndex = 0;

        // default tool is the hand
        tool = Hand.get();
        toolButtonMenu = buildToolButtonMenu();

        primary = Constants.BLACK;
        secondary = Constants.WHITE;

        windowed = true;
        window = makeWindow();
        final GameManager manager = new GameManager(0, this);

        game = new Game(window, manager, Constants.TICK_HZ, Constants.FPS);
        game.setCanvasSize(Constants.CANVAS_W, Constants.CANVAS_H);
        game.getDebugger().muteChannel(GameDebugger.FRAME_RATE);
    }

    public static StippleEffect get() {
        return INSTANCE;
    }

    public static void main(final String[] args) {
        if (args.length > 0)
            launchWithFile(Path.of(Arrays.stream(args)
                    .reduce("", (a, b) -> a + b)));
    }

    private static void launchWithFile(final Path fp) {
        final GameImage image = GameImageIO.readImage(fp);
        get().contexts.add(new ImageContext(image));
        get().contexts.remove(0);
    }

    private GameWindow makeWindow() {
        final Coord2D size = determineWindowSize();
        return new GameWindow(Constants.PROGRAM_NAME,
                size.x, size.y,
                /* TODO - program icon */ GameImage.dummy(),
                true, false, !windowed);
    }

    private Coord2D determineWindowSize() {
        final int screenW = Toolkit.getDefaultToolkit().getScreenSize().width,
                screenH = Toolkit.getDefaultToolkit().getScreenSize().height;

        final int h = screenH - Constants.SCREEN_HEIGHT_BUFFER;
        final double scaleUp = h / (double)Constants.CANVAS_H;
        return windowed ? new Coord2D((int)(scaleUp * Constants.CANVAS_W), h) : new Coord2D(screenW, screenH);
    }

    private Menu buildToolButtonMenu() {
        final MenuBuilder mb = new MenuBuilder();

        for (int i = 0; i < ALL_TOOLS.length; i++) {
            mb.add(toolButtonFromTool(ALL_TOOLS[i], i));
        }

        return mb.build();
    }

    private SimpleMenuButton toolButtonFromTool(
            final Tool tool, final int index
    ) {
        final Coord2D position = new Coord2D(Constants.TOOL_ICON_OFFSET,
                Constants.CONTEXTS_H + Constants.TOOL_ICON_OFFSET +
                        (Constants.TOOL_ICON_INC * index));

        return new SimpleMenuButton(position, Constants.TOOL_ICON_DIMS,
                MenuElement.Anchor.LEFT_TOP, true, () -> setTool(tool),
                this.tool.equals(tool) ? tool.getSelectedIcon() : tool.getIcon(),
                tool.getHighlightedIcon());
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        // workspace
        contexts.get(contextIndex).process(eventLogger, tool);
        // tools
        toolButtonMenu.process(eventLogger);

        if (!(eventLogger.isPressed(Key.CTRL) || eventLogger.isPressed(Key.SHIFT))) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ESCAPE, GameKeyEvent.Action.PRESS),
                    this::toggleFullscreen
            );
        }
    }

    @Override
    public void update(final double deltaTime) {
        // tools
        toolButtonMenu.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        // workspace
        final GameImage workspace = contexts.get(contextIndex).drawWorkspace();
        canvas.draw(workspace, Constants.TOOLS_W, Constants.CONTEXTS_H);
        // tools
        toolButtonMenu.render(canvas);
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

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    private GameImage drawBottomBar() {
        final GameImage bottomBar = new GameImage(Constants.CANVAS_W, Constants.BOTTOM_BAR_H);
        bottomBar.fillRectangle(Constants.ACCENT_BACKGROUND, 0, 0,
                Constants.CANVAS_W, Constants.BOTTOM_BAR_H);

        // target pixel
        final GameImage targetPixel = GraphicsUtils.uiText()
                .addText(contexts.get(contextIndex).getTargetPixelText())
                .build().draw();
        bottomBar.draw(targetPixel, Constants.TP_X, Constants.BOTTOM_BAR_TEXT_Y_OFFSET);

        // image size
        final GameImage size = GraphicsUtils.uiText()
                .addText(contexts.get(contextIndex).getCanvasSizeText())
                .build().draw();
        bottomBar.draw(size, Constants.SIZE_X, Constants.BOTTOM_BAR_TEXT_Y_OFFSET);

        // active tool
        final GameImage activeToolName = GraphicsUtils.uiText()
                .addText(tool.getName()).build().draw();
        bottomBar.draw(activeToolName, Constants.TOOL_NAME_X, Constants.BOTTOM_BAR_TEXT_Y_OFFSET);

        // zoom
        final GameImage zoom = GraphicsUtils.uiText()
                .addText((contexts.get(contextIndex).getRenderInfo().getZoomText()))
                .build().draw();
        bottomBar.draw(zoom, Constants.ZOOM_PCT_X, Constants.BOTTOM_BAR_TEXT_Y_OFFSET);

        // TODO - selection

        return bottomBar.submit();
    }

    public Color getPrimary() {
        return primary;
    }

    public Color getSecondary() {
        return secondary;
    }

    private void toggleFullscreen() {
        windowed = !windowed;

        window = makeWindow();
        game.replaceWindow(window);
    }

    public void setTool(final Tool tool) {
        this.tool = tool;
        toolButtonMenu = buildToolButtonMenu();
    }
}
