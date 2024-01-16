package com.jordanbunke.stipple_effect;

import com.jordanbunke.delta_time.OnStartup;
import com.jordanbunke.delta_time.contexts.ProgramContext;
import com.jordanbunke.delta_time.debug.DebugChannel;
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
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.delta_time.menus.Menu;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.DeltaTimeGlobal;
import com.jordanbunke.delta_time.window.GameWindow;
import com.jordanbunke.stipple_effect.color_selection.ColorMenuMode;
import com.jordanbunke.stipple_effect.color_selection.Palette;
import com.jordanbunke.stipple_effect.color_selection.PaletteLoader;
import com.jordanbunke.stipple_effect.layer.OnionSkinMode;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.ProjectInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.stip.ParserSerializer;
import com.jordanbunke.stipple_effect.tools.*;
import com.jordanbunke.stipple_effect.utility.*;
import com.jordanbunke.stipple_effect.visual.DialogAssembly;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.MenuAssembly;
import com.jordanbunke.stipple_effect.visual.SECursor;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StippleEffect implements ProgramContext {
    public static String
            PROGRAM_NAME = "Stipple Effect",
            VERSION = "dev_build";

    public static final int PRIMARY = 0, SECONDARY = 1;

    private static final String STATUS_UPDATE_CHANNEL = "Status";

    private static final StippleEffect INSTANCE;

    public final Game game;
    public GameWindow window;
    private boolean windowed;

    // projects / contexts
    private final List<SEContext> contexts;
    private int contextIndex;
    private Menu projectsMenu;
    private final List<Path> toImport;

    // tools
    private Menu toolButtonMenu;
    private Tool tool;

    // colors
    private final Color[] colors;
    private int colorIndex;
    private ColorMenuMode colorMenuMode;
    private final List<Palette> palettes;
    private int paletteIndex;
    private Menu colorsMenu;

    // layers
    private Menu layersMenu;

    // frames
    private Menu framesMenu;

    // dialog
    private Menu dialog;

    // cursor
    private Coord2D mousePos;

    // status update
    private int millisSinceStatusUpdate;
    private GameImage statusUpdate;

    // tool tip
    private int toolTipMillisCounter;
    private String toolTipCode, provisionalToolTipCode;
    private GameImage toolTip;

    static {
        OnStartup.run();
        Settings.read();
        readProgramFile();

        INSTANCE = new StippleEffect();

        DialogAssembly.setDialogToSplashScreen();

        // initially declared as empty method because
        // builders rely on calls to INSTANCE object
        INSTANCE.toolButtonMenu = MenuAssembly.buildToolButtonMenu();
        INSTANCE.colorsMenu = MenuAssembly.buildColorsMenu();
        INSTANCE.rebuildStateDependentMenus();

        ToolWithBreadth.redrawToolOverlays();
    }

    private static void readProgramFile() {
        final String[] programFile = FileIO.readResource(ResourceLoader
                .loadResource(Constants.PROGRAM_FILE), "prg").split("\n");

        for (String line : programFile) {
            final String[] codeAndValue = ParserUtils.splitIntoCodeAndValue(line);

            if (codeAndValue.length != ParserUtils.DESIRED)
                continue;

            final String code = codeAndValue[ParserUtils.CODE],
                    value = codeAndValue[ParserUtils.VALUE];

            switch (code) {
                case Constants.NAME_CODE -> PROGRAM_NAME = value;
                case Constants.VERSION_CODE -> VERSION = value;
            }
        }
    }

    public StippleEffect() {
        mousePos = new Coord2D();

        contexts = new ArrayList<>(List.of(new SEContext(
                DialogVals.getNewProjectWidth(), DialogVals.getNewProjectHeight())));
        contextIndex = 0;
        toImport = new ArrayList<>();

        // default tool is the hand
        tool = Hand.get();

        // colors
        colors = new Color[2];
        colors[PRIMARY] = Constants.BLACK;
        colors[SECONDARY] = Constants.WHITE;
        colorIndex = PRIMARY;

        colorMenuMode = ColorMenuMode.RGBA_HSV;
        palettes = PaletteLoader.loadOnStartup();
        paletteIndex = palettes.isEmpty() ? Constants.NO_SELECTION : 0;

        // menu stubs
        toolButtonMenu = MenuAssembly.stub();
        colorsMenu = MenuAssembly.stub();
        layersMenu = MenuAssembly.stub();
        framesMenu = MenuAssembly.stub();
        projectsMenu = MenuAssembly.stub();

        dialog = null;

        windowed = !Settings.isFullscreenOnStartup();
        window = makeWindow();
        final GameManager manager = new GameManager(0, this);

        game = new Game(window, manager, Constants.TICK_HZ, Constants.FPS);
        game.setCanvasSize(Layout.width(), Layout.height());

        millisSinceStatusUpdate = 0;
        statusUpdate = GameImage.dummy();

        toolTipMillisCounter = 0;
        provisionalToolTipCode = Constants.ICON_ID_GAP_CODE;
        toolTipCode = Constants.ICON_ID_GAP_CODE;
        toolTip = GameImage.dummy();

        configureDebugger();
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

    private void configureDebugger() {
        final GameDebugger debugger = game.getDebugger();

        debugger.muteChannel(GameDebugger.FRAME_RATE);
        debugger.addChannel(new DebugChannel(STATUS_UPDATE_CHANNEL,
                this::updateStatus, false, false));
    }

    public void sendStatusUpdate(final String message) {
        game.getDebugger().getChannel(STATUS_UPDATE_CHANNEL)
                .printMessage(message);
    }

    private void updateStatus(final String message) {
        millisSinceStatusUpdate = 0;

        final GameImage text = GraphicsUtils.uiText(Constants.WHITE)
                .addText(message).build().draw();
        final int w = text.getWidth() + (4 * Layout.BUTTON_BORDER_PX),
                h = text.getHeight() - (4 * Layout.BUTTON_BORDER_PX);

        final GameImage statusUpdate = new GameImage(w, h);
        statusUpdate.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0, w, h);
        statusUpdate.draw(text, 2 * Layout.BUTTON_BORDER_PX,
                Layout.TEXT_Y_OFFSET);

        this.statusUpdate = statusUpdate.submit();
    }

    public void sendToolTipUpdate(final String code) {
        provisionalToolTipCode = code;
    }

    private GameWindow makeWindow() {
        final Coord2D size = determineWindowSize();
        Layout.setSize(size.x, size.y);
        final GameWindow window = new GameWindow(PROGRAM_NAME + " v" + VERSION,
                size.x, size.y, GraphicsUtils.loadIcon(IconCodes.PROGRAM),
                true, false, !windowed);
        window.hideCursor();
        return window;
    }

    private Coord2D determineWindowSize() {
        final int screenW = Toolkit.getDefaultToolkit().getScreenSize().width,
                screenH = Toolkit.getDefaultToolkit().getScreenSize().height;

        final int h = Math.max(screenH - Layout.SCREEN_H_BUFFER, Layout.MIN_WINDOW_H);

        return windowed
                ? new Coord2D((int)(h * (16 / 9.)), h)
                : new Coord2D(screenW, screenH);
    }

    public void rebuildAllMenusWithText() {
        rebuildStateDependentMenus();
        rebuildColorsMenu();
    }

    public void rebuildStateDependentMenus() {
        rebuildLayersMenu();
        rebuildFramesMenu();
        rebuildProjectsMenu();
    }

    public void rebuildColorsMenu() {
        colorsMenu = MenuAssembly.buildColorsMenu();
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

        if (dialog == null) {
            if (DeltaTimeGlobal.getStatusOf(Constants.TYPING_CODE)
                    .orElse(Boolean.FALSE) instanceof Boolean b && !b)
                processNonStateKeyPresses(eventLogger);

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

    private void processNonStateKeyPresses(final InputEventLogger eventLogger) {
        if (eventLogger.isPressed(Key.CTRL) && eventLogger.isPressed(Key.SHIFT)) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.S, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToSave
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.R, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToPad
            );
        } else if (eventLogger.isPressed(Key.CTRL)) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.H, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToInfo
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.E, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToProgramSettings
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.N, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToNewProject
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.O, GameKeyEvent.Action.PRESS),
                    this::openProject
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.R, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToResize
            );
        } else if (eventLogger.isPressed(Key.SHIFT)) {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.C, GameKeyEvent.Action.PRESS),
                    this::swapColors
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.O, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToOutline
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.A, GameKeyEvent.Action.PRESS),
                    this::addColorToPalette
            );
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.S, GameKeyEvent.Action.PRESS),
                    this::removeColorFromPalette
            );
        } else {
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ESCAPE, GameKeyEvent.Action.PRESS),
                    this::toggleFullscreen);

            // set tools
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Z, GameKeyEvent.Action.PRESS),
                    () -> setTool(Zoom.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.H, GameKeyEvent.Action.PRESS),
                    () -> setTool(Hand.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.O, GameKeyEvent.Action.PRESS),
                    () -> setTool(StipplePencil.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.P, GameKeyEvent.Action.PRESS),
                    () -> setTool(Pencil.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.B, GameKeyEvent.Action.PRESS),
                    () -> setTool(Brush.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.E, GameKeyEvent.Action.PRESS),
                    () -> setTool(Eraser.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.C, GameKeyEvent.Action.PRESS),
                    () -> setTool(ColorPicker.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.F, GameKeyEvent.Action.PRESS),
                    () -> setTool(Fill.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.W, GameKeyEvent.Action.PRESS),
                    () -> setTool(Wand.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.T, GameKeyEvent.Action.PRESS),
                    () -> setTool(BrushSelect.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.X, GameKeyEvent.Action.PRESS),
                    () -> setTool(BoxSelect.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.M, GameKeyEvent.Action.PRESS),
                    () -> setTool(MoveSelection.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.U, GameKeyEvent.Action.PRESS),
                    () -> setTool(PickUpSelection.get()));
        }
    }

    @Override
    public void update(final double deltaTime) {
        provisionalToolTipCode = Constants.ICON_ID_GAP_CODE;

        if (dialog == null) {
            getContext().animate(deltaTime);

            // status update
            millisSinceStatusUpdate +=
                    (int) (Constants.MILLIS_IN_SECOND / Constants.TICK_HZ);

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

        // tool tip update
        updateToolTip();
    }

    private void updateToolTip() {
        if (!provisionalToolTipCode.equals(Constants.ICON_ID_GAP_CODE) &&
                provisionalToolTipCode.equals(toolTipCode)) {
            toolTipMillisCounter +=
                    (int) (Constants.MILLIS_IN_SECOND / Constants.TICK_HZ);

            if (toolTipMillisCounter == Constants.TOOL_TIP_MILLIS_THRESHOLD)
                toolTip = drawToolTip();
        } else {
            toolTipMillisCounter = 0;
            toolTipCode = provisionalToolTipCode;
        }
    }

    private GameImage drawToolTip() {
        final String[] lines = ParserUtils.getToolTip(toolTipCode);
        final TextBuilder tb = GraphicsUtils.uiText(Constants.WHITE);

        for (int l = 0; l < lines.length; l++) {
            final String[] segments = ParserUtils.extractHighlight(lines[l]);

            for (int i = 0; i < segments.length; i++) {
                if (i % 2 == 0)
                    tb.setColor(Constants.WHITE);
                else
                    tb.setColor(Constants.HIGHLIGHT_1);

                tb.addText(segments[i]);
            }

            if (l + 1 < lines.length)
                tb.addLineBreak();
        }

        final GameImage text = tb.build().draw();
        final int w = text.getWidth() + (4 * Layout.BUTTON_BORDER_PX),
                h = text.getHeight() - (4 * Layout.BUTTON_BORDER_PX);

        final GameImage tt = new GameImage(w, h);
        tt.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0, w, h);
        tt.draw(text, 2 * Layout.BUTTON_BORDER_PX, Layout.TEXT_Y_OFFSET);

        return tt.submit();
    }

    @Override
    public void render(final GameImage canvas) {
        final Coord2D wp = Layout.getWorkspacePosition(),
                tp = Layout.getToolsPosition(),
                lp = Layout.getLayersPosition(),
                cp = Layout.getColorsPosition(),
                pp = Layout.getProjectsPosition(),
                fp = Layout.getFramesPosition(),
                bbp = Layout.getBottomBarPosition();

        // workspace
        final GameImage workspace = getContext().drawWorkspace();
        canvas.draw(workspace, wp.x, wp.y);

        if (millisSinceStatusUpdate < Constants.STATUS_UPDATE_DURATION_MILLIS)
            canvas.draw(statusUpdate, wp.x, wp.y);

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
        canvas.drawLine(strokeWidth, pp.x, tp.y, Layout.width(), tp.y); // top segments and middle separation
        canvas.drawLine(strokeWidth, bbp.x, bbp.y, Layout.width(), bbp.y); // middle segments and bottom bar separation
        canvas.drawLine(strokeWidth, cp.x, cp.y, Layout.width(), cp.y); // layers and colors separation
        canvas.drawLine(strokeWidth, wp.x, wp.y, wp.x, bbp.y); // tools and workspace separation
        canvas.drawLine(strokeWidth, lp.x, lp.y, lp.x, bbp.y); // workspace and right segments separation

        if (dialog != null) {
            canvas.fillRectangle(Constants.VEIL, 0, 0,
                    Layout.width(), Layout.height());
            dialog.render(canvas);
        }

        // tool tip
        if (toolTipMillisCounter >= Constants.TOOL_TIP_MILLIS_THRESHOLD) {
            final boolean leftSide = mousePos.x <= Layout.getCanvasMiddle().x,
                    atBottom = mousePos.y > Layout.height() - (2 * toolTip.getHeight());
            final int x = mousePos.x + (leftSide ? 0 : -toolTip.getWidth()),
                    y = mousePos.y + (atBottom ? -toolTip.getHeight() : 0);

            canvas.draw(toolTip, x, y);
        }

        // cursor
        final GameImage cursor = SECursor.fetchCursor(
                getContext().isInWorkspaceBounds() && dialog == null
                        ? tool.getCursorCode() : SECursor.MAIN_CURSOR);
        canvas.draw(cursor, mousePos.x - (Layout.CURSOR_DIM / 2),
                mousePos.y - (Layout.CURSOR_DIM / 2));
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    private GameImage drawColorsSegment() {
        final GameImage colors = new GameImage(Layout.COLOR_PICKER_W, Layout.getColorPickerHeight());
        colors.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0,
                Layout.COLOR_PICKER_W, Layout.getColorPickerHeight());

        return colors.submit();
    }

    private GameImage drawTools() {
        final GameImage tools = new GameImage(Layout.TOOLS_W, Layout.getWorkspaceHeight());
        tools.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0,
                Layout.TOOLS_W, Layout.getWorkspaceHeight());

        return tools.submit();
    }

    private GameImage drawLayers() {
        final GameImage layers = new GameImage(Layout.COLOR_PICKER_W, Layout.getLayersHeight());
        layers.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0,
                Layout.COLOR_PICKER_W, Layout.getLayersHeight());

        return layers.submit();
    }

    private GameImage drawProjects() {
        final GameImage projects = new GameImage(Layout.getProjectsWidth(), Layout.CONTEXTS_H);
        projects.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0,
                Layout.getProjectsWidth(), Layout.CONTEXTS_H);

        return projects.submit();
    }

    private GameImage drawFrames() {
        final GameImage frames = new GameImage(Layout.getFramesWidth(), Layout.CONTEXTS_H);
        frames.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0,
                Layout.getFramesWidth(), Layout.CONTEXTS_H);

        return frames.submit();
    }

    private GameImage drawBottomBar() {
        final GameImage bottomBar = new GameImage(Layout.width(), Layout.BOTTOM_BAR_H);
        bottomBar.fillRectangle(Constants.ACCENT_BACKGROUND_DARK, 0, 0,
                Layout.width(), Layout.BOTTOM_BAR_H);

        // target pixel
        final GameImage targetPixel = GraphicsUtils.uiText()
                .addText(getContext().getTargetPixelText())
                .build().draw();
        bottomBar.draw(targetPixel, Layout.getBottomBarTargetPixelX(),
                Layout.TEXT_Y_OFFSET);

        // image size
        final GameImage size = GraphicsUtils.uiText()
                .addText(getContext().getImageSizeText()).build().draw();
        bottomBar.draw(size, Layout.getBottomBarProjectCanvasSizeX(),
                Layout.TEXT_Y_OFFSET);

        // active tool
        final GameImage activeToolName = GraphicsUtils.uiText()
                .addText(tool.getBottomBarText()).build().draw();
        bottomBar.draw(activeToolName, Layout.CONTENT_BUFFER_PX, Layout.TEXT_Y_OFFSET);

        // zoom
        final GameImage zoom = GraphicsUtils.uiText()
                .addText(getContext().renderInfo.getZoomText())
                .build().draw();
        bottomBar.draw(zoom, Layout.getBottomBarZoomPercentageX(),
                Layout.TEXT_Y_OFFSET);

        // selection
        final GameImage selection = GraphicsUtils.uiText()
                .addText(getContext().getSelectionText()).build().draw();
        bottomBar.draw(selection, Layout.width() -
                        (Layout.CONTENT_BUFFER_PX + Layout.BUTTON_INC +
                                selection.getWidth()),
                Layout.TEXT_Y_OFFSET);

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

    public Color getColorAtIndex(final int index) {
        return colors[index];
    }

    public ColorMenuMode getColorMenuMode() {
        return colorMenuMode;
    }

    public int getPaletteIndex() {
        return paletteIndex;
    }

    public List<Palette> getPalettes() {
        return palettes;
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
        final Optional<File[]> opened = FileIO.openFilesFromSystem(
                new String[] {
                        PROGRAM_NAME + " projects (." + ProjectInfo.SaveType
                                .NATIVE.getFileSuffix() + ")",
                        "Accepted image types"
                },
                new String[][] {
                        new String[] { ProjectInfo.SaveType.NATIVE.getFileSuffix() },
                        Constants.ACCEPTED_RASTER_IMAGE_SUFFIXES
                });
        window.getEventLogger().unpressAllKeys();

        if (opened.isEmpty())
            return;

        final Path[] filepaths = Arrays.stream(opened.get())
                .map(File::toPath).toArray(Path[]::new);

        if (filepaths.length > 0) {
            verifyFilepath(filepaths[0]);

            toImport.clear();
            toImport.addAll(Arrays.asList(filepaths).subList(1, filepaths.length));
        }
    }

    private static void verifyFilepath(final Path filepath) {
        final String fileName = filepath.getFileName().toString();

        if (fileName.endsWith(ProjectInfo.SaveType.NATIVE.getFileSuffix()))
            get().openNativeProject(filepath);
        else if (isAcceptedRasterFormat(fileName)) {
            final GameImage image = GameImageIO.readImage(filepath);

            DialogAssembly.setDialogToOpenPNG(image, filepath);
        }
        // extend with else-ifs for additional file types classes (scripts, palettes)
    }

    public void openNativeProject(final Path filepath) {
        final String contents = FileIO.readFile(filepath);

        if (contents != null) {
            final SEContext project = ParserSerializer.load(contents, filepath);
            project.initializeRender();
            addContext(project, true);
        } else
            StatusUpdates.openFailed(filepath);

        processNextImport();
    }

    private static boolean isAcceptedRasterFormat(final String toCheck) {
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
                if (frames.size() < frameCount)
                    frames.add(new GameImage(resized.getSubimage(
                            x * fw, y * fh, fw, fh)));

        final SELayer firstLayer = new SELayer(frames,
                new GameImage(fw, fh), Constants.OPAQUE, true, false,
                OnionSkinMode.NONE, Constants.BASE_LAYER_NAME);
        final ProjectState initialState = ProjectState.makeFromRasterFile(
                fw, fh, firstLayer, frameCount);

        final SEContext project = new SEContext(
                new ProjectInfo(filepath), initialState, fw, fh);
        project.initializeRender();
        addContext(project, true);

        processNextImport();
    }

    public void processNextImport() {
        if (toImport.isEmpty())
            clearDialog();
        else
            verifyFilepath(toImport.remove(0));
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
            ToolWithBreadth.redrawToolOverlays();
        } else {
            rebuildProjectsMenu();
        }
    }

    public void removeContext(final int index) {
        if (index >= 0 && index < contexts.size()) {
            contexts.remove(index);

            if (contextIndex >= contexts.size())
                setContextIndex(contexts.size() - 1);

            if (contexts.size() == 0)
                exitProgram();

            rebuildStateDependentMenus();
        }
    }

    public void exitProgram() {
        Settings.write();
        System.exit(0);
    }

    public boolean hasPaletteContents() {
        return paletteIndex != Constants.NO_SELECTION &&
                palettes != null && paletteIndex < palettes.size();
    }

    public void addColorToPalette() {
        if (hasPaletteContents()) {
            palettes.get(paletteIndex).addColor(getSelectedColor());
            rebuildColorsMenu();
        }
    }

    public void removeColorFromPalette() {
        if (hasPaletteContents()) {
            palettes.get(paletteIndex).removeColor(getSelectedColor());
            rebuildColorsMenu();
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
    }

    public void setColorIndexAndColor(final int colorIndex, final Color color) {
        setColorIndex(colorIndex);
        setSelectedColor(color);
    }

    public void swapColors() {
        final Color temp = colors[PRIMARY];
        colors[PRIMARY] = colors[SECONDARY];
        colors[SECONDARY] = temp;
    }

    public void toggleColorMenuMode() {
        colorMenuMode = ColorMenuMode.values()[1 - colorMenuMode.ordinal()];
        rebuildColorsMenu();
    }

    public void setPaletteIndex(final int paletteIndex) {
        this.paletteIndex = paletteIndex;
        rebuildColorsMenu();
    }

    private void toggleFullscreen() {
        windowed = !windowed;

        window = makeWindow();
        game.setCanvasSize(Layout.width(), Layout.height());
        game.replaceWindow(window);

        // redraw everything
        rebuildAllMenusWithText();
        rebuildToolButtonMenu();
    }

    public void autoAssignPickUpSelection() {
        this.tool = PickUpSelection.get();
        getContext().redrawSelectionOverlay();
        rebuildToolButtonMenu();
    }

    public void setTool(final Tool tool) {
        final Tool was = this.tool;

        if (was.equals(PickUpSelection.get()) &&
                !tool.equals(PickUpSelection.get()))
            PickUpSelection.get().disengage(getContext());

        this.tool = tool;

        if (!was.equals(PickUpSelection.get()) &&
                tool.equals(PickUpSelection.get()))
            PickUpSelection.get().engage(getContext());

        getContext().redrawSelectionOverlay();
        rebuildToolButtonMenu();
    }

    public void clearDialog() {
        dialog = null;
        rebuildStateDependentMenus();
    }

    public void setDialog(final Menu dialog) {
        this.dialog = dialog;
    }
}
