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
import com.jordanbunke.stipple_effect.menu_elements.ColorButton;
import com.jordanbunke.stipple_effect.menu_elements.ColorSelector;
import com.jordanbunke.stipple_effect.tools.*;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StippleEffect implements ProgramContext {
    public static final int PRIMARY = 0, SECONDARY = 1;

    private static final Tool[] ALL_TOOLS = new Tool[] {
            Hand.get(), Zoom.get(),
            StipplePencil.get(), Pencil.get(), Brush.get(), Eraser.get(),
            ColorPicker.get()
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

    // colors
    private final Color[] colors;
    private int colorIndex;
    private Menu colorsMenu;

    static {
        INSTANCE = new StippleEffect();

        INSTANCE.colorsMenu = INSTANCE.buildColorsMenu();
    }

    public StippleEffect() {
        contexts = new ArrayList<>(List.of(
                new ImageContext(Constants.DEFAULT_IMAGE_WIDTH,
                        Constants.DEFAULT_IMAGE_HEIGHT)));
        contextIndex = 0;

        // default tool is the hand
        tool = Hand.get();
        toolButtonMenu = buildToolButtonMenu();

        colors = new Color[2];
        colors[PRIMARY] = Constants.BLACK;
        colors[SECONDARY] = Constants.WHITE;
        colorIndex = PRIMARY;
        colorsMenu = new MenuBuilder().build();

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

    private Menu buildColorsMenu() {
        final MenuBuilder mb = new MenuBuilder();

        for (int i = 0; i < colors.length; i++) {
            final int width = Constants.STD_TEXT_BUTTON_W;
            final int x = (Constants.TOOLS_W + Constants.WORKSPACE_W + Constants.COLOR_PICKER_W) -
                    (Constants.TOOL_NAME_X + ((colors.length - i) * (width + Constants.BUTTON_OFFSET)));
            final int y = Constants.CONTEXTS_H + Constants.LAYERS_H + Constants.BUTTON_OFFSET;

            mb.add(new ColorButton(new Coord2D(x, y), i));
        }

        mb.add(ColorSelector.make());

        return mb.build();
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
        final Coord2D position = new Coord2D(Constants.BUTTON_OFFSET,
                Constants.CONTEXTS_H + Constants.BUTTON_OFFSET +
                        (Constants.BUTTON_INC * index));

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
        // colors
        colorsMenu.process(eventLogger);

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
        // colors
        colorsMenu.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        // workspace
        final GameImage workspace = contexts.get(contextIndex).drawWorkspace();
        canvas.draw(workspace, Constants.TOOLS_W, Constants.CONTEXTS_H);
        // tools
        final GameImage tools = drawTools();
        canvas.draw(tools, 0, Constants.CONTEXTS_H);
        toolButtonMenu.render(canvas);
        // layers
        final GameImage layers = drawLayers();
        canvas.draw(layers, Constants.TOOLS_W + Constants.WORKSPACE_W,
                Constants.CONTEXTS_H);
        // colors
        final GameImage colors = drawColorsSegment();
        canvas.draw(colors, Constants.TOOLS_W + Constants.WORKSPACE_W,
                Constants.CONTEXTS_H + Constants.LAYERS_H);
        colorsMenu.render(canvas);
        // contexts
        final GameImage contexts = drawContexts();
        canvas.draw(contexts, 0, 0);
        // TODO - menu
        // frames
        final GameImage frames = drawFrames();
        canvas.draw(frames, Constants.CONTEXTS_W, 0);
        // TODO - menu
        // bottom bar - zoom, animation
        final GameImage bottomBar = drawBottomBar();
        canvas.draw(bottomBar, 0, Constants.CONTEXTS_H + Constants.WORKSPACE_H);

        // borders
        canvas.setColor(Constants.BLACK);
        canvas.drawLine(1f, Constants.CONTEXTS_W, 0,
                Constants.CONTEXTS_W, Constants.CONTEXTS_H);
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

    private GameImage drawColorsSegment() {
        final GameImage colors = new GameImage(Constants.COLOR_PICKER_W, Constants.COLOR_PICKER_H);
        colors.fillRectangle(Constants.LIGHT_GREY, 0, 0,
                Constants.COLOR_PICKER_W, Constants.COLOR_PICKER_H);

        final GameImage sectionTitle = GraphicsUtils.uiText(Constants.BLACK)
                .addText("Colors").build().draw();
        colors.draw(sectionTitle, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        // TODO

        return colors.submit();
    }

    private GameImage drawTools() {
        final GameImage tools = new GameImage(Constants.TOOLS_W, Constants.WORKSPACE_H);
        tools.fillRectangle(Constants.LIGHT_GREY, 0, 0,
                Constants.TOOLS_W, Constants.WORKSPACE_H);

        return tools.submit();
    }

    private GameImage drawLayers() {
        final GameImage layers = new GameImage(Constants.COLOR_PICKER_W, Constants.LAYERS_H);
        layers.fillRectangle(Constants.LIGHT_GREY, 0, 0,
                Constants.COLOR_PICKER_W, Constants.LAYERS_H);

        final GameImage sectionTitle = GraphicsUtils.uiText(Constants.BLACK)
                .addText("Layers").build().draw();
        layers.draw(sectionTitle, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        // TODO

        return layers.submit();
    }

    private GameImage drawContexts() {
        final GameImage contexts = new GameImage(Constants.CONTEXTS_W, Constants.CONTEXTS_H);
        contexts.fillRectangle(Constants.ACCENT_BACKGROUND, 0, 0,
                Constants.CONTEXTS_W, Constants.CONTEXTS_H);

        final GameImage sectionTitle = GraphicsUtils.uiText()
                .addText("Projects").build().draw();
        contexts.draw(sectionTitle, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        // TODO

        return contexts.submit();
    }

    private GameImage drawFrames() {
        final GameImage frames = new GameImage(Constants.FRAMES_W, Constants.CONTEXTS_H);
        frames.fillRectangle(Constants.ACCENT_BACKGROUND, 0, 0,
                Constants.FRAMES_W, Constants.CONTEXTS_H);

        final GameImage sectionTitle = GraphicsUtils.uiText()
                .addText("Frames").build().draw();
        frames.draw(sectionTitle, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        // TODO

        return frames.submit();
    }

    private GameImage drawBottomBar() {
        final GameImage bottomBar = new GameImage(Constants.CANVAS_W, Constants.BOTTOM_BAR_H);
        bottomBar.fillRectangle(Constants.ACCENT_BACKGROUND, 0, 0,
                Constants.CANVAS_W, Constants.BOTTOM_BAR_H);

        // target pixel
        final GameImage targetPixel = GraphicsUtils.uiText()
                .addText(contexts.get(contextIndex).getTargetPixelText())
                .build().draw();
        bottomBar.draw(targetPixel, Constants.TP_X, Constants.TEXT_Y_OFFSET);

        // image size
        final GameImage size = GraphicsUtils.uiText()
                .addText(contexts.get(contextIndex).getCanvasSizeText())
                .build().draw();
        bottomBar.draw(size, Constants.SIZE_X, Constants.TEXT_Y_OFFSET);

        // active tool
        final GameImage activeToolName = GraphicsUtils.uiText()
                .addText(tool.getBottomBarText()).build().draw();
        bottomBar.draw(activeToolName, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        // zoom
        final GameImage zoom = GraphicsUtils.uiText()
                .addText((contexts.get(contextIndex).getRenderInfo().getZoomText()))
                .build().draw();
        bottomBar.draw(zoom, Constants.ZOOM_PCT_X, Constants.TEXT_Y_OFFSET);

        // TODO - selection

        return bottomBar.submit();
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public Color getSelectedColor() {
        return colors[colorIndex];
    }

    public Color getPrimary() {
        return colors[PRIMARY];
    }

    public Color getSecondary() {
        return colors[SECONDARY];
    }

    public Tool getTool() {
        return tool;
    }

    public void setSelectedColor(final Color color) {
        colors[colorIndex] = color;
    }

    public void setColorIndex(final int colorIndex) {
        this.colorIndex = colorIndex;
        colorsMenu = buildColorsMenu();
    }

    public void setColorIndexAndColor(final int colorIndex, final Color color) {
        this.colorIndex = colorIndex;
        setSelectedColor(color);
        colorsMenu = buildColorsMenu();
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
