package com.jordanbunke.stipple_effect;

import com.jordanbunke.delta_time.OnStartup;
import com.jordanbunke.delta_time.contexts.ProgramContext;
import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.game.Game;
import com.jordanbunke.delta_time.game.GameManager;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.io.GameImageIO;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.Menu;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.window.GameWindow;
import com.jordanbunke.stipple_effect.context.ProjectInfo;
import com.jordanbunke.stipple_effect.context.SEContext;
import com.jordanbunke.stipple_effect.layer.OnionSkinMode;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.tools.Hand;
import com.jordanbunke.stipple_effect.tools.PickUpSelection;
import com.jordanbunke.stipple_effect.tools.Tool;
import com.jordanbunke.stipple_effect.tools.ToolWithBreadth;
import com.jordanbunke.stipple_effect.utility.*;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class StippleEffect implements ProgramContext {
    public static final int PRIMARY = 0, SECONDARY = 1;

    private static final StippleEffect INSTANCE;

    public final Game game;
    public GameWindow window;
    private boolean windowed;

    // projects / contexts
    private final List<SEContext> contexts;
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

    // dialog
    private Menu dialog;

    // cursor
    private Coord2D mousePos;

    static {
        OnStartup.run();

        INSTANCE = new StippleEffect();

        // initially declared as empty method because
        // builders rely on calls to INSTANCE object
        INSTANCE.toolButtonMenu = MenuAssembly.buildToolButtonMenu();
        INSTANCE.colorsMenu = MenuAssembly.buildColorsMenu();
        INSTANCE.rebuildStateDependentMenus();

        ToolWithBreadth.redrawToolOverlays();
    }

    public StippleEffect() {
        mousePos = new Coord2D();

        contexts = new ArrayList<>(List.of(new SEContext(
                DialogVals.getNewProjectWidth(), DialogVals.getNewProjectHeight())));
        contextIndex = 0;

        // default tool is the hand
        tool = Hand.get();
        toolButtonMenu = MenuAssembly.stub();

        colors = new Color[2];
        colors[PRIMARY] = Constants.BLACK;
        colors[SECONDARY] = Constants.WHITE;
        colorIndex = PRIMARY;
        colorsMenu = MenuAssembly.stub();

        layersMenu = MenuAssembly.stub();

        framesMenu = MenuAssembly.stub();

        projectsMenu = MenuAssembly.stub();

        dialog = null;

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

    private static void launchWithFile(final Path filepath) {
        verifyFilepath(filepath);
    }

    private GameWindow makeWindow() {
        final Coord2D size = determineWindowSize();
        final GameWindow window = new GameWindow(Constants.PROGRAM_NAME,
                size.x, size.y,
                /* TODO - program icon */ GameImage.dummy(),
                true, false, !windowed);
        window.hideCursor();
        return window;
    }

    private Coord2D determineWindowSize() {
        final int screenW = Toolkit.getDefaultToolkit().getScreenSize().width,
                screenH = Toolkit.getDefaultToolkit().getScreenSize().height;

        final int h = screenH - Constants.SCREEN_H_BUFFER;
        final double scaleUp = h / (double)Constants.CANVAS_H;
        return windowed
                ? new Coord2D((int)(scaleUp * Constants.CANVAS_W), h)
                : new Coord2D(screenW, screenH);
    }

    public void rebuildStateDependentMenus() {
        rebuildLayersMenu();
        rebuildFramesMenu();
        rebuildProjectsMenu();
    }

    public void rebuildToolButtonMenu() {
        toolButtonMenu = MenuAssembly.buildToolButtonMenu();
    }

    public void rebuildLayersMenu() {
        layersMenu = MenuAssembly.buildLayersMenu();
    }

    public void rebuildFramesMenu() {
        framesMenu = MenuAssembly.buildFramesMenu();
    }

    public void rebuildProjectsMenu() {
        projectsMenu = MenuAssembly.buildProjectsMenu();
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        mousePos = eventLogger.getAdjustedMousePosition();

        if (!(eventLogger.isPressed(Key.CTRL) || eventLogger.isPressed(Key.SHIFT))) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ESCAPE, GameKeyEvent.Action.PRESS),
                    this::toggleFullscreen
            );
        }

        if (dialog == null) {
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
        } else {
            dialog.process(eventLogger);
        }
    }

    @Override
    public void update(final double deltaTime) {
        if (dialog == null) {
            getContext().animate(deltaTime);

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
        } else {
            dialog.update(deltaTime);
        }
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
        // bottom bar - zoom, animation
        final GameImage bottomBar = drawBottomBar();
        canvas.draw(bottomBar, bbp.x, bbp.y);
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

        // borders
        final float strokeWidth = 2f;

        canvas.setColor(Constants.BLACK);
        canvas.drawLine(strokeWidth, fp.x, fp.y, fp.x, wp.y); // projects and frame separation
        canvas.drawLine(strokeWidth, pp.x, tp.y, Constants.CANVAS_W, tp.y); // top segments and middle separation
        canvas.drawLine(strokeWidth, bbp.x, bbp.y, Constants.CANVAS_W, bbp.y); // middle segments and bottom bar separation
        canvas.drawLine(strokeWidth, cp.x, cp.y, Constants.CANVAS_W, cp.y); // layers and colors separation
        canvas.drawLine(strokeWidth, wp.x, wp.y, wp.x, bbp.y); // tools and workspace separation
        canvas.drawLine(strokeWidth, lp.x, lp.y, lp.x, bbp.y); // workspace and right segments separation

        if (dialog != null) {
            canvas.fillRectangle(Constants.VEIL, 0, 0,
                    Constants.CANVAS_W, Constants.CANVAS_H);
            dialog.render(canvas);
        }

        // cursor
        final GameImage cursor = SECursor.fetchCursor(
                getContext().isInWorkspaceBounds() && dialog == null
                        ? tool.getCursorCode() : SECursor.MAIN_CURSOR);
        canvas.draw(cursor, mousePos.x - (Constants.CURSOR_DIM / 2),
                mousePos.y - (Constants.CURSOR_DIM / 2));
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    private GameImage drawColorsSegment() {
        final GameImage colors = new GameImage(Constants.COLOR_PICKER_W, Constants.COLOR_PICKER_H);
        colors.fillRectangle(Constants.ACCENT_BACKGROUND_LIGHT, 0, 0,
                Constants.COLOR_PICKER_W, Constants.COLOR_PICKER_H);

        final GameImage sectionTitle = GraphicsUtils.uiText(Constants.BLACK)
                .addText("Colors").build().draw();
        colors.draw(sectionTitle, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        return colors.submit();
    }

    private GameImage drawTools() {
        final GameImage tools = new GameImage(Constants.TOOLS_W, Constants.WORKSPACE_H);
        tools.fillRectangle(Constants.ACCENT_BACKGROUND_LIGHT, 0, 0,
                Constants.TOOLS_W, Constants.WORKSPACE_H);

        return tools.submit();
    }

    private GameImage drawLayers() {
        final GameImage layers = new GameImage(Constants.COLOR_PICKER_W, Constants.LAYERS_H);
        layers.fillRectangle(Constants.ACCENT_BACKGROUND_LIGHT, 0, 0,
                Constants.COLOR_PICKER_W, Constants.LAYERS_H);

        final GameImage sectionTitle = GraphicsUtils.uiText(Constants.BLACK)
                .addText("Layers").build().draw();
        layers.draw(sectionTitle, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        return layers.submit();
    }

    private GameImage drawProjects() {
        final GameImage projects = new GameImage(Constants.CONTEXTS_W, Constants.CONTEXTS_H);
        projects.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0,
                Constants.CONTEXTS_W, Constants.CONTEXTS_H);

        final GameImage sectionTitle = GraphicsUtils.uiText()
                .addText("Project").build().draw();
        projects.draw(sectionTitle, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        return projects.submit();
    }

    private GameImage drawFrames() {
        final GameImage frames = new GameImage(Constants.FRAMES_W, Constants.CONTEXTS_H);
        frames.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0,
                Constants.FRAMES_W, Constants.CONTEXTS_H);

        final GameImage sectionTitle = GraphicsUtils.uiText()
                .addText("Frames").build().draw();
        frames.draw(sectionTitle, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        return frames.submit();
    }

    private GameImage drawBottomBar() {
        final GameImage bottomBar = new GameImage(Constants.CANVAS_W, Constants.BOTTOM_BAR_H);
        bottomBar.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0,
                Constants.CANVAS_W, Constants.BOTTOM_BAR_H);

        // target pixel
        final GameImage targetPixel = GraphicsUtils.uiText()
                .addText(getContext().getTargetPixelText())
                .build().draw();
        bottomBar.draw(targetPixel, Constants.TP_X, Constants.TEXT_Y_OFFSET);

        // image size
        final GameImage size = GraphicsUtils.uiText()
                .addText(getContext().getImageSizeText()).build().draw();
        bottomBar.draw(size, Constants.SIZE_X, Constants.TEXT_Y_OFFSET);

        // active tool
        final GameImage activeToolName = GraphicsUtils.uiText()
                .addText(tool.getBottomBarText()).build().draw();
        bottomBar.draw(activeToolName, Constants.TOOL_NAME_X, Constants.TEXT_Y_OFFSET);

        // zoom
        final GameImage zoom = GraphicsUtils.uiText()
                .addText(getContext().renderInfo.getZoomText())
                .build().draw();
        bottomBar.draw(zoom, Constants.ZOOM_PCT_X, Constants.TEXT_Y_OFFSET);

        // selection
        final GameImage selection = GraphicsUtils.uiText()
                .addText(getContext().getSelectionText()).build().draw();
        bottomBar.draw(selection, Constants.CANVAS_W -
                        (Constants.TOOL_NAME_X + Constants.BUTTON_INC +
                                selection.getWidth()),
                Constants.TEXT_Y_OFFSET);

        return bottomBar.submit();
    }

    public SEContext getContext() {
        return contexts.get(contextIndex);
    }

    public List<SEContext> getContexts() {
        return contexts;
    }

    public int getContextIndex() {
        return contextIndex;
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

    public void newProject() {
        addContext(new SEContext(DialogVals.getNewProjectWidth(),
                DialogVals.getNewProjectHeight()), true);
    }

    public void openProject() {
        FileIO.setDialogToFilesOnly();
        final Optional<File> opened = FileIO.openFileFromSystem(
                new String[] { "Accepted image types" },
                new String[][] { Constants.ACCEPTED_RASTER_IMAGE_SUFFIXES });

        if (opened.isEmpty())
            return;

        final Path filepath = opened.get().toPath();
        verifyFilepath(filepath);
    }

    private static void verifyFilepath(final Path filepath) {
        final String fileName = filepath.getFileName().toString();

        if (endsWithOneOf(fileName)) {
            final GameImage image = GameImageIO.readImage(filepath);

            DialogAssembly.setDialogToOpenPNG(image, filepath);
        }
        // TODO - extend with else-ifs for additional file types (.stef, potentially .jpg)
    }

    private static boolean endsWithOneOf(final String toCheck) {
        for (String suffix : Constants.ACCEPTED_RASTER_IMAGE_SUFFIXES)
            if (toCheck.endsWith(suffix))
                return true;

        return false;
    }

    public void newProjectFromFile(
            final GameImage image, final Path filepath
    ) {
        final int w = DialogVals.getResizeWidth(),
                h = DialogVals.getResizeHeight(),
                xDivs = DialogVals.getNewProjectXDivs(),
                yDivs = DialogVals.getNewProjectYDivs(),
                fw = w / xDivs, fh = h /  yDivs,
                frameCount = Math.min(Constants.MAX_NUM_FRAMES, xDivs * yDivs);

        final GameImage resized = ImageProcessing.scale(image, w, h);
        final List<GameImage> frames = new ArrayList<>();

        for (int y = 0; y < yDivs; y++)
            for (int x = 0; x < xDivs; x++)
                frames.add(new GameImage(resized.getSubimage(
                        x * fw, y * fh, fw, fh)));

        final SELayer firstLayer = new SELayer(frames,
                new GameImage(fw, fh), Constants.OPAQUE, true, false,
                OnionSkinMode.NONE, Constants.BASE_LAYER_NAME);
        final ProjectState initialState = ProjectState.makeFromFile(fw, fh,
                firstLayer, frameCount);

        final SEContext project = new SEContext(
                new ProjectInfo(filepath), initialState, fw, fh);

        addContext(project, true);
    }

    public void addContext(final SEContext context, final boolean setActive) {
        // close unmodified untitled project
        if (contexts.size() == 1 && !contexts.get(0).projectInfo
                .hasUnsavedChanges() &&
                !contexts.get(0).projectInfo.hasSaveAssociation())
            contexts.remove(0);

        contexts.add(context);

        if (setActive) {
            setContextIndex(contexts.size() - 1);
            rebuildStateDependentMenus();
        } else {
            rebuildProjectsMenu();
        }
    }

    public void removeContext(final int index) {
        if (index >= 0 && index < contexts.size()) {
            contexts.remove(index);

            if (contextIndex >= contexts.size())
                setContextIndex(contexts.size() - 1);

            if (contexts.size() == 0) {
                // TODO - global on close here
                System.exit(0);
            }

            rebuildStateDependentMenus();
        }
    }

    public void setContextIndex(final int contextIndex) {
        if (this.contextIndex != contextIndex &&
                contextIndex >= 0 && contextIndex < contexts.size()) {
            this.contextIndex = contextIndex;
            rebuildStateDependentMenus();
        }
    }

    public void incrementSelectedColorRGBA(
            final int deltaR, final int deltaG,
            final int deltaB, final int deltaAlpha
    ) {
        final Color c = colors[colorIndex];

        setSelectedColor(new Color(
                Math.max(0, Math.min(c.getRed() + deltaR, 255)),
                Math.max(0, Math.min(c.getGreen() + deltaG, 255)),
                Math.max(0, Math.min(c.getBlue() + deltaB, 255)),
                Math.max(0, Math.min(c.getAlpha() + deltaAlpha, 255))
        ));
    }

    public void setSelectedColor(final Color color) {
        colors[colorIndex] = color;
    }

    public void setColorIndex(final int colorIndex) {
        this.colorIndex = colorIndex;
        colorsMenu = MenuAssembly.buildColorsMenu();
    }

    public void setColorIndexAndColor(final int colorIndex, final Color color) {
        this.colorIndex = colorIndex;
        setSelectedColor(color);
        colorsMenu = MenuAssembly.buildColorsMenu();
    }

    public void swapColors() {
        final Color temp = colors[PRIMARY];
        colors[PRIMARY] = colors[SECONDARY];
        colors[SECONDARY] = temp;

        colorsMenu = MenuAssembly.buildColorsMenu();
    }

    private void toggleFullscreen() {
        windowed = !windowed;

        window = makeWindow();
        game.replaceWindow(window);
    }

    public void setTool(final Tool tool) {
        if (this.tool.equals(PickUpSelection.get()))
            PickUpSelection.get().disengage(getContext());

        this.tool = tool;

        if (tool.equals(PickUpSelection.get()))
            PickUpSelection.get().engage(getContext());

        getContext().redrawSelectionOverlay();
        toolButtonMenu = MenuAssembly.buildToolButtonMenu();
    }

    public void clearDialog() {
        dialog = null;
        rebuildStateDependentMenus();
    }

    public void setDialog(final Menu dialog) {
        this.dialog = dialog;
    }
}
