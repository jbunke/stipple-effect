package com.jordanbunke.stipple_effect;

import com.jordanbunke.delta_time.contexts.ProgramContext;
import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.error.GameError;
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

    // projects / contexts
    private final List<ImageContext> contexts;
    private int contextIndex;
    private Menu projectsMenu;

    // tools
    private Menu toolButtonMenu;
    private Tool tool;

    // colors
    private final Color[] colors;
    private int colorIndex;
    private Menu colorsMenu;

    // layers
    private Menu layersMenu;

    // frames
    private Menu framesMenu;

    static {
        INSTANCE = new StippleEffect();

        // initially declared as empty method because
        // builders rely on calls to INSTANCE object
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

        layersMenu = buildLayersMenu();

        framesMenu = buildFramesMenu();

        projectsMenu = buildProjectsMenu();

        windowed = true; // TODO - read from settings on startup
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
        return windowed
                ? new Coord2D((int)(scaleUp * Constants.CANVAS_W), h)
                : new Coord2D(screenW, screenH);
    }

    public void rebuildStateDependentMenus() {
        layersMenu = buildLayersMenu();
        framesMenu = buildFramesMenu();
        projectsMenu = buildProjectsMenu();
    }

    public void rebuildLayersMenu() {
        layersMenu = buildLayersMenu();
    }

    private Menu buildProjectsMenu() {
        final MenuBuilder mb = new MenuBuilder();

        final String[] iconIDs = new String[] {
                "new_project",
                "open_file",
                "save",
                "save_as",
                "wip", // TODO - resize
                "wip" // TODO - pad
        };

        final boolean[] preconditions = new boolean[] {
                true, true, true, true, true, true
        };

        final Runnable[] behaviours = new Runnable[] {
                () -> {}, // TODO all
                () -> {},
                () -> {},
                () -> {},
                () -> {},
                () -> {}
        };

        populateButtonsIntoBuilder(mb, iconIDs, preconditions,
                behaviours, Constants.getProjectsPosition());

        // TODO - project previews themselves

        return mb.build();
    }

    private Menu buildFramesMenu() {
        final MenuBuilder mb = new MenuBuilder();

        final String[] iconIDs = new String[] {
                "new_frame",
                "duplicate_frame",
                "remove_frame",
                "play",
                "pause",
                "previous",
                "next"
        };

        final boolean[] preconditions = new boolean[] {
                true, // TODO - consider a setting check ... if frames are enabled for project
                true, // TODO rest
                true,
                true,
                true,
                true,
                true
        };

        final Runnable[] behaviours = new Runnable[] {
                () -> {}, // TODO all
                () -> {},
                () -> {},
                () -> {},
                () -> {},
                () -> {},
                () -> {}
        };

        populateButtonsIntoBuilder(mb, iconIDs, preconditions,
                behaviours, Constants.getFramesPosition());

        // TODO - frames themselves

        return mb.build();
    }

    private Menu buildLayersMenu() {
        final MenuBuilder mb = new MenuBuilder();

        final String[] iconIDs = new String[] {
                "new_layer",
                "duplicate_layer",
                "remove_layer",
                "move_layer_up",
                "move_layer_down",
                "combine_with_layer_below"
        };

        final boolean[] preconditions = new boolean[] {
                true,
                true,
                getContext().getState().canRemoveLayer(),
                getContext().getState().canMoveLayerUp(),
                getContext().getState().canMoveLayerDown(),
                // identical precondition for combine case
                getContext().getState().canMoveLayerDown()
        };

        final Runnable[] behaviours = new Runnable[] {
                () -> getContext().addLayer(),
                () -> getContext().duplicateLayer(),
                () -> getContext().removeLayer(),
                () -> getContext().moveLayerUp(),
                () -> getContext().moveLayerDown(),
                () -> getContext().combineWithLayerBelow()
        };

        populateButtonsIntoBuilder(mb, iconIDs, preconditions,
                behaviours, Constants.getLayersPosition());

        // TODO - layers themselves

        return mb.build();
    }

    private void populateButtonsIntoBuilder(
            final MenuBuilder mb, final String[] iconIDs,
            final boolean[] preconditions, final Runnable[] behaviours,
            final Coord2D segmentPosition
    ) {
        if (iconIDs.length != preconditions.length || iconIDs.length != behaviours.length) {
            GameError.send("Lengths of button assembly argument arrays did not match; " +
                    "buttons were not populated into menu builder.");
            return;
        }

        for (int i = 0; i < iconIDs.length; i++) {
            final Coord2D pos = segmentPosition
                    .displace(Constants.SEGMENT_TITLE_BUTTON_OFFSET_X,
                            Constants.BUTTON_OFFSET)
                    .displace(i * Constants.BUTTON_INC, 0);
            mb.add(GraphicsUtils.generateIconButton(iconIDs[i],
                    pos, preconditions[i], behaviours[i]));
        }
    }

    private Menu buildColorsMenu() {
        final MenuBuilder mb = new MenuBuilder();

        populateButtonsIntoBuilder(
                mb, new String[] { "swap_colors" }, new boolean[] { true },
                new Runnable[] { this::swapColors }, Constants.getColorsPosition()
        );

        for (int i = 0; i < colors.length; i++) {
            final int width = Constants.STD_TEXT_BUTTON_W;
            final Coord2D pos = Constants.getColorsPosition().displace(
                    Constants.COLOR_PICKER_W - (Constants.TOOL_NAME_X +
                            ((colors.length - i) * (width + Constants.BUTTON_OFFSET))),
                    Constants.BUTTON_OFFSET);

            mb.add(new ColorButton(pos, i));
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
        final Coord2D position = Constants.getToolsPosition().displace(
                Constants.BUTTON_OFFSET,
                Constants.BUTTON_OFFSET + (Constants.BUTTON_INC * index)
        );

        return new SimpleMenuButton(position, Constants.TOOL_ICON_DIMS,
                MenuElement.Anchor.LEFT_TOP, true, () -> setTool(tool),
                this.tool.equals(tool) ? tool.getSelectedIcon() : tool.getIcon(),
                tool.getHighlightedIcon());
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        // tools
        toolButtonMenu.process(eventLogger);
        // colors
        colorsMenu.process(eventLogger);
        // layers
        layersMenu.process(eventLogger);
        // frames
        framesMenu.process(eventLogger);
        // projects
        projectsMenu.process(eventLogger);

        // workspace
        getContext().process(eventLogger, tool);

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
        // layers
        layersMenu.update(deltaTime);
        // frames
        framesMenu.update(deltaTime);
        // projects
        projectsMenu.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        final Coord2D wp = Constants.getWorkspacePosition(),
                tp = Constants.getToolsPosition(),
                lp = Constants.getLayersPosition(),
                cp = Constants.getColorsPosition(),
                pp = Constants.getProjectsPosition(),
                fp = Constants.getFramesPosition(),
                bbp = Constants.getBottomBarPosition();

        // workspace
        final GameImage workspace = getContext().drawWorkspace();
        canvas.draw(workspace, wp.x, wp.y);
        // tools
        final GameImage tools = drawTools();
        canvas.draw(tools, tp.x, tp.y);
        toolButtonMenu.render(canvas);
        // layers
        final GameImage layers = drawLayers();
        canvas.draw(layers, lp.x, lp.y);
        layersMenu.render(canvas);
        // colors
        final GameImage colors = drawColorsSegment();
        canvas.draw(colors, cp.x, cp.y);
        colorsMenu.render(canvas);
        // projects / contexts
        final GameImage projects = drawProjects();
        canvas.draw(projects, pp.x, pp.y);
        projectsMenu.render(canvas);
        // frames
        final GameImage frames = drawFrames();
        canvas.draw(frames, fp.x, fp.y);
        framesMenu.render(canvas);
        // bottom bar - zoom, animation
        final GameImage bottomBar = drawBottomBar();
        canvas.draw(bottomBar, bbp.x, bbp.y);

        // borders
        final float strokeWidth = 2f;

        canvas.setColor(Constants.BLACK);
        canvas.drawLine(strokeWidth, fp.x, fp.y, fp.x, wp.y); // projects and frame separation
        canvas.drawLine(strokeWidth, pp.x, tp.y, Constants.CANVAS_W, tp.y); // top segments and middle separation
        canvas.drawLine(strokeWidth, bbp.x, bbp.y, Constants.CANVAS_W, bbp.y); // middle segments and bottom bar separation
        canvas.drawLine(strokeWidth, cp.x, cp.y, Constants.CANVAS_W, cp.y); // layers and colors separation
        canvas.drawLine(strokeWidth, wp.x, wp.y, wp.x, bbp.y); // tools and workspace separation
        canvas.drawLine(strokeWidth, lp.x, lp.y, lp.x, bbp.y); // workspace and right segments separation
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

        return layers.submit();
    }

    private GameImage drawProjects() {
        final GameImage projects = new GameImage(Constants.CONTEXTS_W, Constants.CONTEXTS_H);
        projects.fillRectangle(Constants.ACCENT_BACKGROUND, 0, 0,
                Constants.CONTEXTS_W, Constants.CONTEXTS_H);

        final GameImage sectionTitle = GraphicsUtils.uiText()
                .addText("Project").build().draw();
        projects.draw(sectionTitle, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        return projects.submit();
    }

    private GameImage drawFrames() {
        final GameImage frames = new GameImage(Constants.FRAMES_W, Constants.CONTEXTS_H);
        frames.fillRectangle(Constants.ACCENT_BACKGROUND, 0, 0,
                Constants.FRAMES_W, Constants.CONTEXTS_H);

        final GameImage sectionTitle = GraphicsUtils.uiText()
                .addText("Frames").build().draw();
        frames.draw(sectionTitle, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        return frames.submit();
    }

    private GameImage drawBottomBar() {
        final GameImage bottomBar = new GameImage(Constants.CANVAS_W, Constants.BOTTOM_BAR_H);
        bottomBar.fillRectangle(Constants.ACCENT_BACKGROUND, 0, 0,
                Constants.CANVAS_W, Constants.BOTTOM_BAR_H);

        // target pixel
        final GameImage targetPixel = GraphicsUtils.uiText()
                .addText(getContext().getTargetPixelText())
                .build().draw();
        bottomBar.draw(targetPixel, Constants.TP_X, Constants.TEXT_Y_OFFSET);

        // image size
        final GameImage size = GraphicsUtils.uiText()
                .addText(getContext().getCanvasSizeText())
                .build().draw();
        bottomBar.draw(size, Constants.SIZE_X, Constants.TEXT_Y_OFFSET);

        // active tool
        final GameImage activeToolName = GraphicsUtils.uiText()
                .addText(tool.getBottomBarText()).build().draw();
        bottomBar.draw(activeToolName, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        // zoom
        final GameImage zoom = GraphicsUtils.uiText()
                .addText((getContext().getRenderInfo().getZoomText()))
                .build().draw();
        bottomBar.draw(zoom, Constants.ZOOM_PCT_X, Constants.TEXT_Y_OFFSET);

        // TODO - selection

        // TODO - help shortcut (Ctrl + H) once behaviour implemented

        return bottomBar.submit();
    }

    public ImageContext getContext() {
        return contexts.get(contextIndex);
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

    private void swapColors() {
        final Color temp = colors[PRIMARY];
        colors[PRIMARY] = colors[SECONDARY];
        colors[SECONDARY] = temp;

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
