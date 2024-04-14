package com.jordanbunke.stipple_effect;

import com.jordanbunke.delta_time.OnStartup;
import com.jordanbunke.delta_time._core.GameManager;
import com.jordanbunke.delta_time._core.Program;
import com.jordanbunke.delta_time._core.ProgramContext;
import com.jordanbunke.delta_time.debug.DebugChannel;
import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.events.GameKeyEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.io.GameImageIO;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.delta_time.menu.Menu;
import com.jordanbunke.delta_time.text.TextBuilder;
import com.jordanbunke.delta_time.utility.Version;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.delta_time.window.GameWindow;
import com.jordanbunke.stipple_effect.layer.OnionSkinMode;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.palette.ColorMenuMode;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.palette.PaletteLoader;
import com.jordanbunke.stipple_effect.project.ProjectInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.stip.ParserSerializer;
import com.jordanbunke.stipple_effect.tools.*;
import com.jordanbunke.stipple_effect.utility.*;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.math.StitchSplitMath;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.*;
import com.jordanbunke.stipple_effect.visual.color.SEColors;
import com.jordanbunke.stipple_effect.visual.color.Theme;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class StippleEffect implements ProgramContext {
    public static String
            PROGRAM_NAME = "Stipple Effect",
            NATIVE_STANDARD = "1.0", PALETTE_STANDARD = "1.0";
    private static Version VERSION = new Version(1, 0, 0);
    private static boolean IS_DEVBUILD = false;

    public static final int PRIMARY = 0, SECONDARY = 1;

    private static final String STATUS_UPDATE_CHANNEL = "Status";

    private static final StippleEffect INSTANCE;

    public final Program program;
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

    // bottom bar
    private Menu bottomBarMenu;

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

    // timer
    private int timerCounter, timerMillis;
    private boolean timerToggle;

    /**
     * This exists to ensure that the standard execution thread executes
     * the jobs that are scheduled, thus avoiding race conditions etc.
     * */
    private final Queue<Runnable> jobScheduler;

    static {
        OnStartup.run();
        Settings.read();
        readProgramFile();

        INSTANCE = new StippleEffect();

        DialogAssembly.setDialogToSplashScreen();

        // initially declared as empty method because
        // builders rely on calls to INSTANCE object
        INSTANCE.rebuildAllMenus();

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
                case Constants.VERSION_CODE -> {
                    try {
                        final Integer[] components = Arrays
                                .stream(value.split("\\."))
                                .map(Integer::parseInt).toArray(Integer[]::new);

                        final int MAJOR = 0, MINOR = 1, PATCH = 2,
                                BUILD = 3, HAS_BUILD_LENGTH = 4;

                        if (components.length == HAS_BUILD_LENGTH)
                            VERSION = new Version(components[MAJOR],
                                    components[MINOR], components[PATCH],
                                    components[BUILD]);
                        else if (components.length > PATCH)
                            VERSION = new Version(components[MAJOR],
                                    components[MINOR], components[PATCH]);
                    } catch (NumberFormatException e) {
                        GameError.send("Could not read program version from data file.");
                    }
                }
                case Constants.IS_DEVBUILD_CODE ->
                        IS_DEVBUILD = Boolean.parseBoolean(value);
                case Constants.NATIVE_STANDARD_CODE ->
                        NATIVE_STANDARD = value;
                case Constants.PALETTE_STANDARD_CODE ->
                        PALETTE_STANDARD = value;
            }
        }

        if (IS_DEVBUILD) {
            VERSION.incrementBuild();

            final Path toSave = Path.of("res").resolve(Constants.PROGRAM_FILE);

            final String write = ParserUtils.encloseSetting(
                    Constants.NAME_CODE, PROGRAM_NAME) +
                    ParserUtils.encloseSetting(Constants.VERSION_CODE,
                            VERSION.toString()) +
                    ParserUtils.encloseSetting(Constants.IS_DEVBUILD_CODE,
                            String.valueOf(IS_DEVBUILD)) +
                    ParserUtils.encloseSetting(
                            Constants.NATIVE_STANDARD_CODE, NATIVE_STANDARD) +
                    ParserUtils.encloseSetting(
                            Constants.PALETTE_STANDARD_CODE, PALETTE_STANDARD);

            FileIO.writeFile(toSave, write);
        }
    }

    public static String getVersion() {
        return "v" + VERSION + (IS_DEVBUILD ? " (devbuild)" : "");
    }

    public StippleEffect() {
        mousePos = new Coord2D();

        contexts = new ArrayList<>(List.of(new SEContext(
                Settings.getDefaultCanvasWPixels(),
                Settings.getDefaultCanvasHPixels())));
        contextIndex = 0;
        toImport = new ArrayList<>();

        // default tool is the hand
        tool = Hand.get();

        // colors
        colors = new Color[2];
        colors[PRIMARY] = SEColors.black();
        colors[SECONDARY] = SEColors.white();
        colorIndex = PRIMARY;

        colorMenuMode = ColorMenuMode.RGBA_HSV;
        palettes = PaletteLoader.loadOnStartup();
        paletteIndex = palettes.isEmpty() ? Constants.NO_SELECTION : 0;

        // menu stubs
        toolButtonMenu = MenuAssembly.stub();
        bottomBarMenu = MenuAssembly.stub();
        colorsMenu = MenuAssembly.stub();
        layersMenu = MenuAssembly.stub();
        framesMenu = MenuAssembly.stub();
        projectsMenu = MenuAssembly.stub();

        dialog = null;

        windowed = !Settings.isFullscreenOnStartup();
        window = makeWindow();
        final GameManager manager = new GameManager(0, this);

        program = new Program(window, manager, Constants.TICK_HZ, Constants.FPS);
        program.setCanvasSize(Layout.width(), Layout.height());
        program.setScheduleUpdates(false);

        millisSinceStatusUpdate = 0;
        statusUpdate = GameImage.dummy();

        toolTipMillisCounter = 0;
        provisionalToolTipCode = Constants.ICON_ID_GAP_CODE;
        toolTipCode = Constants.ICON_ID_GAP_CODE;
        toolTip = GameImage.dummy();

        timerCounter = 0;
        timerToggle = false;
        timerMillis = 0;

        jobScheduler = new LinkedList<>();

        configureDebugger();
    }

    public static StippleEffect get() {
        return INSTANCE;
    }

    public static void main(final String[] args) {
        if (args.length > 0)
            get().launchWithFile(Path.of(Arrays.stream(args)
                    .reduce("", (a, b) -> a + " " + b).trim()));
    }

    private void launchWithFile(final Path filepath) {
        jobScheduler.add(() -> verifyFilepath(filepath));
    }

    private void configureDebugger() {
        final GameDebugger debugger = program.getDebugger();

        debugger.muteChannel(GameDebugger.FRAME_RATE);
        debugger.addChannel(new DebugChannel(STATUS_UPDATE_CHANNEL,
                this::updateStatus, false, false));
    }

    public void sendStatusUpdate(final String message) {
        program.getDebugger().getChannel(STATUS_UPDATE_CHANNEL)
                .printMessage(message);
    }

    private void updateStatus(final String message) {
        millisSinceStatusUpdate = 0;

        final GameImage text = ParserUtils.generateStatusEffectText(message);
        final int w = text.getWidth() + (4 * Layout.BUTTON_BORDER_PX),
                h = text.getHeight() - (4 * Layout.BUTTON_BORDER_PX);

        final GameImage statusUpdate = new GameImage(w, h);
        statusUpdate.fillRectangle(
                Settings.getTheme().panelBackground.get(), 0, 0, w, h);
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
        final GameWindow window = new GameWindow(PROGRAM_NAME + " " + getVersion(),
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

    public void rebuildAllMenus() {
        rebuildStateDependentMenus();
        rebuildColorsMenu();
        rebuildToolButtonMenu();
    }

    public void rebuildStateDependentMenus() {
        rebuildLayersMenu();
        rebuildFramesMenu();
        rebuildProjectsMenu();
        rebuildBottomBarMenu();
    }

    public void rebuildColorsMenu() {
        colorsMenu = Layout.isColorsPanelShowing()
                ? MenuAssembly.buildColorsMenu() : MenuAssembly.stub();
    }

    public void rebuildToolButtonMenu() {
        toolButtonMenu = Layout.isToolbarShowing()
                ? MenuAssembly.buildToolButtonMenu() : MenuAssembly.stub();
    }

    public void rebuildBottomBarMenu() {
        bottomBarMenu = MenuAssembly.buildBottomBarMenu();
    }

    public void rebuildLayersMenu() {
        layersMenu = Layout.isLayersPanelShowing()
                ? MenuAssembly.buildLayersMenu() : MenuAssembly.stub();
    }

    public void rebuildFramesMenu() {
        framesMenu = Layout.isFramesPanelShowing()
                ? MenuAssembly.buildFramesMenu() : MenuAssembly.stub();
    }

    public void rebuildProjectsMenu() {
        projectsMenu = MenuAssembly.buildProjectsMenu();
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        mousePos = eventLogger.getAdjustedMousePosition();

        if (dialog == null) {
            if (!Permissions.isTyping())
                processNonStateKeyPresses(eventLogger);

            // bottom bar
            bottomBarMenu.process(eventLogger);
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
            getContext().process(eventLogger);
        } else {
            dialog.process(eventLogger);
        }
    }

    private void processNonStateKeyPresses(final InputEventLogger eventLogger) {
        if (eventLogger.isPressed(Key.CTRL) && eventLogger.isPressed(Key.SHIFT)) {
            // Ctrl + Shift + ?
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.S, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToSave);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.R, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToPad);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.A, GameKeyEvent.Action.PRESS),
                    Layout::togglePanels);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.C, GameKeyEvent.Action.PRESS),
                    this::toggleColorMenuMode);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.G, GameKeyEvent.Action.PRESS),
                    this::stitchOrSplit);
        } else if (eventLogger.isPressed(Key.CTRL)) {
            // Ctrl + ?
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.H, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToInfo);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.E, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToProgramSettings);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.N, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToNewProject);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.O, GameKeyEvent.Action.PRESS),
                    this::openProject);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.R, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToResize);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.P, GameKeyEvent.Action.PRESS),
                    () -> {
                        if (hasPaletteContents() &&
                                getSelectedPalette().isMutable())
                            DialogAssembly.setDialogToSavePalette(getSelectedPalette());
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.K, GameKeyEvent.Action.PRESS),
                    () -> {
                        if (tool.equals(TextTool.get()))
                            TextTool.get().toggleAlignment();
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.COMMA, GameKeyEvent.Action.PRESS),
                    this::selectPaletteColorToTheLeft);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.PERIOD, GameKeyEvent.Action.PRESS),
                    this::selectPaletteColorToTheRight);
        } else if (eventLogger.isPressed(Key.SHIFT)) {
            // Shift + ?
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.C, GameKeyEvent.Action.PRESS),
                    this::swapColors);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.O, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToOutline);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.A, GameKeyEvent.Action.PRESS),
                    this::addColorToPalette);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Z, GameKeyEvent.Action.PRESS),
                    this::removeColorFromPalette);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.D, GameKeyEvent.Action.PRESS),
                    () -> {
                        if (hasPaletteContents() &&
                                getSelectedPalette().isMutable())
                            DialogAssembly.setDialogToAddContentsToPalette(
                                    getSelectedPalette());
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.P, GameKeyEvent.Action.PRESS),
                    () -> {
                        if (hasPaletteContents())
                            DialogAssembly.setDialogToPalettize(
                                    getSelectedPalette());
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ESCAPE, GameKeyEvent.Action.PRESS),
                    DialogAssembly::setDialogToPanelManager);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.N, GameKeyEvent.Action.PRESS),
                    this::newPalette);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.M, GameKeyEvent.Action.PRESS),
                    () -> {
                        if (hasPaletteContents())
                            DialogAssembly.setDialogToSortPalette(
                                    getSelectedPalette());
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.E, GameKeyEvent.Action.PRESS),
                    () -> {
                        if (hasPaletteContents() &&
                                getSelectedPalette().isMutable())
                            DialogAssembly.setDialogToPaletteSettings(
                                    getSelectedPalette());
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.F, GameKeyEvent.Action.PRESS),
                    () -> {
                        if (tool.equals(TextTool.get()))
                            DialogAssembly.setDialogToNewFont();
                    });
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.COMMA, GameKeyEvent.Action.PRESS),
                    this::moveColorLeftInPalette);
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.PERIOD, GameKeyEvent.Action.PRESS),
                    this::moveColorRightInPalette);
        } else {
            // single key presses
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.ESCAPE, GameKeyEvent.Action.PRESS),
                    this::toggleFullscreen);

            // quick context select
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._1, GameKeyEvent.Action.PRESS),
                    () -> setContextIndex(0));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._2, GameKeyEvent.Action.PRESS),
                    () -> setContextIndex(1));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._3, GameKeyEvent.Action.PRESS),
                    () -> setContextIndex(2));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._4, GameKeyEvent.Action.PRESS),
                    () -> setContextIndex(3));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._5, GameKeyEvent.Action.PRESS),
                    () -> setContextIndex(4));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._6, GameKeyEvent.Action.PRESS),
                    () -> setContextIndex(5));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._7, GameKeyEvent.Action.PRESS),
                    () -> setContextIndex(6));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._8, GameKeyEvent.Action.PRESS),
                    () -> setContextIndex(7));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key._9, GameKeyEvent.Action.PRESS),
                    () -> setContextIndex(8));

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
                    GameKeyEvent.newKeyStroke(Key.D, GameKeyEvent.Action.PRESS),
                    () -> setTool(ShadeBrush.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.G, GameKeyEvent.Action.PRESS),
                    () -> setTool(GradientTool.get()));
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
                    GameKeyEvent.newKeyStroke(Key.L, GameKeyEvent.Action.PRESS),
                    () -> setTool(LineTool.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.T, GameKeyEvent.Action.PRESS),
                    () -> setTool(TextTool.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.W, GameKeyEvent.Action.PRESS),
                    () -> setTool(Wand.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.V, GameKeyEvent.Action.PRESS),
                    () -> setTool(BrushSelect.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.X, GameKeyEvent.Action.PRESS),
                    () -> setTool(BoxSelect.get()));
            eventLogger.checkForMatchingKeyStroke(
                    GameKeyEvent.newKeyStroke(Key.Y, GameKeyEvent.Action.PRESS),
                    () -> setTool(PolygonSelect.get()));
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

        while (!jobScheduler.isEmpty())
            jobScheduler.poll().run();

        if (dialog == null) {
            getContext().animate(deltaTime);

            final int millisThisTick = (int)
                    (Constants.MILLIS_IN_SECOND / Constants.TICK_HZ);

            // system timer
            timerMillis += millisThisTick;

            if (timerMillis >= Constants.MILLIS_PER_TIMER_INC) {
                timerMillis -= Constants.MILLIS_PER_TIMER_INC;

                timerCounter++;

                if (timerCounter >= Constants.TIMER_MAX_OUT) {
                    timerCounter = 0;
                    timerToggle = !timerToggle;
                }
            }

            // status update
            millisSinceStatusUpdate += millisThisTick;

            // bottom bar
            bottomBarMenu.update(deltaTime);
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
        final Theme t = Settings.getTheme();

        final String[] lines = ParserUtils.getToolTip(toolTipCode);
        final TextBuilder tb = GraphicsUtils.uiText(t.textLight.get());

        for (int l = 0; l < lines.length; l++) {
            final String[] segments = ParserUtils.extractHighlight(lines[l]);

            for (int i = 0; i < segments.length; i++) {
                if (i % 2 == 0)
                    tb.setColor(t.textLight.get());
                else
                    tb.setColor(t.textShortcut.get());

                tb.addText(segments[i]);
            }

            if (l + 1 < lines.length)
                tb.addLineBreak();
        }

        final GameImage text = tb.build().draw();
        final int w = text.getWidth() + (4 * Layout.BUTTON_BORDER_PX),
                h = text.getHeight() - (4 * Layout.BUTTON_BORDER_PX);

        final GameImage tt = new GameImage(w, h);
        tt.fillRectangle(t.panelBackground.get(), 0, 0, w, h);
        tt.draw(text, 2 * Layout.BUTTON_BORDER_PX, Layout.TEXT_Y_OFFSET);

        return tt.submit();
    }

    @Override
    public void render(final GameImage canvas) {
        final Coord2D wp = Layout.getWorkspacePosition(),
                tp = Layout.getToolsPosition(),
                tobp = Layout.getToolOptionsBarPosition(),
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

        // segment backgrounds

        // bottom bar - zoom, animation
        final GameImage bottomBar = drawBottomBar();
        canvas.draw(bottomBar, bbp.x, bbp.y);

        // tools
        if (Layout.isToolbarShowing()) {
            final GameImage tools = drawTools();
            canvas.draw(tools, tp.x, tp.y);

            if (tool.hasToolOptionsBar()) {
                final GameImage toolOptionsBar = drawToolOptionsBar();
                canvas.draw(toolOptionsBar, tobp.x, tobp.y);
            }
        }

        // layers
        if (Layout.isLayersPanelShowing()) {
            final GameImage layers = drawLayers();
            canvas.draw(layers, lp.x, lp.y);
        }

        // colors
        if (Layout.isColorsPanelShowing()) {
            final GameImage colors = drawColorsSegment();
            canvas.draw(colors, cp.x, cp.y);
        }

        // projects / contexts
        final GameImage projects = drawProjects();
        canvas.draw(projects, pp.x, pp.y);

        // frames
        if (Layout.isFramesPanelShowing()) {
            final GameImage frames = drawFrames();
            canvas.draw(frames, fp.x, fp.y);
        }

        // borders
        final float strokeWidth = 2f;

        canvas.setColor(Settings.getTheme().panelDivisions.get());
        canvas.drawLine(strokeWidth, fp.x, fp.y, fp.x, tobp.y); // projects and frame separation
        canvas.drawLine(strokeWidth, pp.x, tobp.y, Layout.width(), tobp.y); // top segments and middle separation
        canvas.drawLine(strokeWidth, tp.x, wp.y, lp.x, wp.y); // tool options bar and tools/workspace separation
        canvas.drawLine(strokeWidth, bbp.x, bbp.y, Layout.width(), bbp.y); // middle segments and bottom bar separation
        canvas.drawLine(strokeWidth, cp.x, cp.y, Layout.width(), cp.y); // layers and colors separation
        canvas.drawLine(strokeWidth, wp.x, wp.y, wp.x, bbp.y); // tools and workspace separation
        canvas.drawLine(strokeWidth, lp.x, lp.y, lp.x, bbp.y); // workspace/option bar and right segments separation

        // menu elements
        bottomBarMenu.render(canvas);
        toolButtonMenu.render(canvas);
        layersMenu.render(canvas);
        colorsMenu.render(canvas);
        projectsMenu.render(canvas);
        framesMenu.render(canvas);

        if (dialog != null) {
            canvas.fillRectangle(Settings.getTheme().dialogVeil.get(),
                    0, 0, Layout.width(), Layout.height());
            dialog.render(canvas);
        }

        // tool tip
        if (toolTipMillisCounter >= Constants.TOOL_TIP_MILLIS_THRESHOLD) {
            final boolean leftSide = mousePos.x <= Layout.getCanvasMiddle().x,
                    atBottom = mousePos.y > Layout.height() - (2 * toolTip.getHeight());
            final int x = mousePos.x + (leftSide
                    ? Layout.TOOL_TIP_OFFSET : -toolTip.getWidth()),
                    y = mousePos.y + (atBottom
                            ? -toolTip.getHeight() : Layout.TOOL_TIP_OFFSET);

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
        final GameImage colors = new GameImage(Layout.getColorsWidth(),
                Layout.getColorsHeight());
        colors.fillRectangle(Settings.getTheme().panelBackground.get(), 0, 0,
                Layout.getColorsWidth(), Layout.getColorsHeight());

        return colors.submit();
    }

    private GameImage drawTools() {
        final GameImage tools = new GameImage(Layout.getToolsWidth(),
                Layout.getToolsHeight());
        tools.fillRectangle(Settings.getTheme().panelBackground.get(), 0, 0,
                Layout.getToolsWidth(), Layout.getToolsHeight());

        return tools.submit();
    }

    private GameImage drawToolOptionsBar() {
        final GameImage toolOptionsBar = new GameImage(
                Layout.getToolOptionsBarWidth(),
                Layout.TOOL_OPTIONS_BAR_H);
        toolOptionsBar.fillRectangle(
                Settings.getTheme().panelBackground.get(), 0, 0,
                Layout.getToolOptionsBarWidth(), Layout.TOOL_OPTIONS_BAR_H);

        return toolOptionsBar.submit();
    }

    private GameImage drawLayers() {
        final GameImage layers = new GameImage(Layout.getLayersWidth(),
                Layout.getLayersHeight());
        layers.fillRectangle(
                Settings.getTheme().panelBackground.get(), 0, 0,
                Layout.getLayersWidth(), Layout.getLayersHeight());

        return layers.submit();
    }

    private GameImage drawProjects() {
        final GameImage projects = new GameImage(Layout.getProjectsWidth(),
                Layout.getTopPanelHeight());
        projects.fillRectangle(
                Settings.getTheme().panelBackground.get(), 0, 0,
                Layout.getProjectsWidth(), Layout.getTopPanelHeight());

        return projects.submit();
    }

    private GameImage drawFrames() {
        final GameImage frames = new GameImage(Layout.getFramesWidth(),
                Layout.getTopPanelHeight());
        frames.fillRectangle(
                Settings.getTheme().panelBackground.get(), 0, 0,
                Layout.getFramesWidth(), Layout.getTopPanelHeight());

        return frames.submit();
    }

    private GameImage drawBottomBar() {
        final GameImage bottomBar =
                new GameImage(Layout.width(), Layout.BOTTOM_BAR_H);
        bottomBar.fillRectangle(
                Settings.getTheme().panelBackground.get(), 0, 0,
                Layout.width(), Layout.BOTTOM_BAR_H);

        return bottomBar.submit();
    }

    public void stitchOrSplit() {
        if (getContext().getState().getFrameCount() > 1)
            DialogAssembly.setDialogToStitchFramesTogether();
        else
            DialogAssembly.setDialogToSplitCanvasIntoFrames();
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

    public int getTimerCounter() {
        return timerCounter;
    }

    public boolean isTimerToggle() {
        return timerToggle;
    }

    public void newProject() {
        addContext(new SEContext(DialogVals.getNewProjectWidth(),
                DialogVals.getNewProjectHeight()), true);
    }

    public void newPalette() {
        addPalette(new Palette("Palette " +
                (palettes.size() + 1), new Color[] {}), true);
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
            toImport.clear();
            toImport.addAll(Arrays.asList(filepaths).subList(1, filepaths.length));

            verifyFilepath(filepaths[0]);
        }
    }

    public void openFontTemplateProjects() {
        final String suffix = "." + Constants.NATIVE_FILE_SUFFIX;
        final Path folder = SEFonts.FONT_FOLDER.resolve("templates"),
                asciiPath = folder.resolve(Constants.ASCII_TEMPLATE + suffix),
                latinExtendedPath = folder.resolve(Constants.LATIN_EXTENDED_TEMPLATE + suffix);

        final String ascii = FileIO.readResource(
                ResourceLoader.loadResource(asciiPath), "ASCII Template"),
                latinExtended = FileIO.readResource(
                        ResourceLoader.loadResource(latinExtendedPath),
                        "Latin Extended Template");

        openNativeProject(ascii, asciiPath);
        openNativeProject(latinExtended, latinExtendedPath);

        clearDialog();
    }

    public void openPalette() {
        FileIO.setDialogToFilesOnly();
        final Optional<File> opened = FileIO.openFileFromSystem(
                new String[] {
                        PROGRAM_NAME + " palettes (." + Constants.PALETTE_FILE_SUFFIX + ")"
                },
                new String[][] {
                        new String[] { Constants.PALETTE_FILE_SUFFIX }
                });
        window.getEventLogger().unpressAllKeys();

        if (opened.isEmpty())
            return;

        verifyFilepath(opened.get().toPath());
    }

    private void verifyFilepath(final Path filepath) {
        final String fileName = filepath.getFileName().toString();

        if (fileName.endsWith(ProjectInfo.SaveType.NATIVE.getFileSuffix())) {
            final String contents = FileIO.readFile(filepath);
            openNativeProject(contents, filepath);

            processNextImport();
        } else if (isAcceptedRasterFormat(fileName)) {
            final GameImage image = GameImageIO.readImage(filepath);

            DialogAssembly.setDialogToOpenPNG(image, filepath);
        } else if (fileName.endsWith(Constants.PALETTE_FILE_SUFFIX)) {
            final String file = FileIO.readFile(filepath);

            if (file != null)
                addPalette(ParserSerializer.loadPalette(file), true);
            else
                StatusUpdates.openFailed(filepath);
        }
        // extend with else-ifs for additional file types classes (scripts, palettes)
    }

    public void openNativeProject(final String contents, final Path filepath) {
        if (contents != null) {
            final SEContext project = ParserSerializer.load(contents, filepath);
            addContext(project, true);
        } else
            StatusUpdates.openFailed(filepath);
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
        final int iw = DialogVals.getImportWidth(),
                ih = DialogVals.getImportHeight(),
                fw = DialogVals.getImportFrameWidth(),
                fh = DialogVals.getImportFrameHeight(),
                columns = StitchSplitMath.importColumns(iw),
                rows = StitchSplitMath.importRows(ih),
                frameCount = columns * rows;

        final GameImage resized = ImageProcessing.scale(image, iw, ih);
        final List<GameImage> frames = new ArrayList<>();

        final boolean isHorizontal = StitchSplitMath.isHorizontal();
        final BinaryOperator<Integer>
                horz = (a, b) -> a % b,
                vert = (a, b) -> a / b,
                xOp = isHorizontal ? horz : vert,
                yOp = isHorizontal ? vert : horz;
        final int fpd = isHorizontal ? columns : rows;

        for (int i = 0; i < frameCount; i++) {
            final GameImage frame = new GameImage(fw, fh);

            final int x = xOp.apply(i, fpd) * fw,
                    y = yOp.apply(i, fpd) * fh;

            frame.draw(resized, -1 * x, -1 * y);
            frames.add(frame.submit());
        }

        final SELayer firstLayer = new SELayer(frames,
                new GameImage(fw, fh), Constants.OPAQUE, true, false,
                OnionSkinMode.NONE, Constants.BASE_LAYER_NAME);
        final ProjectState initialState = ProjectState.makeFromRasterFile(
                fw, fh, firstLayer, frameCount);

        final SEContext project = new SEContext(filepath, initialState, fw, fh);
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
                !contexts.get(0).projectInfo.hasSaveAssociation()) {
            contexts.get(0).releasePixelGrid();
            contexts.remove(0);
        }

        contexts.add(context);

        if (setActive)
            setContextIndex(contexts.size() - 1);
        else
            rebuildProjectsMenu();
    }

    public void removeContext(final int index) {
        if (index >= 0 && index < contexts.size()) {
            contexts.get(index).releasePixelGrid();
            contexts.remove(index);

            if (contextIndex >= contexts.size())
                setContextIndex(contexts.size() - 1);
            else
                rebuildAllMenus();

            if (contexts.size() == 0)
                exitProgram();
        }
    }

    public void exitProgram() {
        Settings.write();
        System.exit(0);
    }

    public void addPalette(final Palette palette, final boolean setActive) {
        palettes.add(palette);

        if (setActive)
            setPaletteIndex(palettes.size() - 1);

        colorMenuMode = ColorMenuMode.PALETTE;
        rebuildColorsMenu();
    }

    public void deletePalette() {
        if (hasPaletteContents()) {
            palettes.remove(paletteIndex);

            if (paletteIndex >= palettes.size())
                setPaletteIndex(palettes.size() - 1);

            colorMenuMode = ColorMenuMode.PALETTE;
            rebuildColorsMenu();
        }
    }

    public boolean hasPaletteContents() {
        return paletteIndex != Constants.NO_SELECTION &&
                palettes != null && paletteIndex < palettes.size();
    }

    public Palette getSelectedPalette() {
        return hasPaletteContents() ? palettes.get(paletteIndex) : null;
    }

    public void addColorToPalette() {
        paletteSelectedColorAction(Palette::addColor,
                Palette::canAdd, StatusUpdates::addColorToPalette,
                (p, c) -> StatusUpdates.cannotColorPalette(true, p, c));
    }

    public void removeColorFromPalette() {
        paletteSelectedColorAction(Palette::removeColor,
                Palette::canRemove, StatusUpdates::removeColorFromPalette,
                (p, c) -> StatusUpdates.cannotColorPalette(false, p, c));
    }

    public void moveColorLeftInPalette() {
        paletteSelectedColorAction(Palette::moveLeft,
                Palette::canMoveLeft, StatusUpdates::moveLeftInPalette,
                (p, c) -> StatusUpdates.cannotShiftColorPalette(p, c, true));
    }

    public void moveColorRightInPalette() {
        paletteSelectedColorAction(Palette::moveRight,
                Palette::canMoveRight, StatusUpdates::moveRightInPalette,
                (p, c) -> StatusUpdates.cannotShiftColorPalette(p, c, false));
    }

    public void selectPaletteColorToTheLeft() {
        paletteSelectedColorAction(
                (p, c) -> setSelectedColor(p.nextLeft(c),
                        ColorMath.LastHSVEdit.NONE), Palette::isIncluded,
                (p, c) -> StatusUpdates.selectNextPaletteColor(p, c, true),
                (p, c) -> StatusUpdates.cannotSelectColorPalette(p, c, true));
    }

    public void selectPaletteColorToTheRight() {
        paletteSelectedColorAction(
                (p, c) -> setSelectedColor(p.nextRight(c),
                        ColorMath.LastHSVEdit.NONE), Palette::isIncluded,
                (p, c) -> StatusUpdates.selectNextPaletteColor(p, c, false),
                (p, c) -> StatusUpdates.cannotSelectColorPalette(p, c, false));
    }

    private void paletteSelectedColorAction(
            final BiConsumer<Palette, Color> f,
            final BiFunction<Palette, Color, Boolean> precondition,
            final BiConsumer<Palette, Color> statusUpdate,
            final BiConsumer<Palette, Color> notPermitted
    ) {
        final Palette p = getSelectedPalette();
        final Color c = getSelectedColor();

        if (hasPaletteContents() && precondition.apply(p, c)) {
            f.accept(p, c);

            if (Layout.isColorsPanelShowing() && colorMenuMode == ColorMenuMode.PALETTE)
                rebuildColorsMenu();
            else
                statusUpdate.accept(p, c);
        } else if (hasPaletteContents()) {
            // precondition failed
            notPermitted.accept(p, c);
        } else {
            StatusUpdates.noPalette();
        }
    }

    public void setContextIndex(final int contextIndex) {
        if (contextIndex >= 0 && contextIndex < contexts.size()) {
            // release resources
            if (this.contextIndex < contexts.size())
                getContext().releasePixelGrid();

            // assign
            this.contextIndex = contextIndex;

            // auxiliaries
            rebuildAllMenus();
            ToolWithBreadth.redrawToolOverlays();
            getContext().redrawCanvasAuxiliaries();
        }
    }

    public void incrementSelectedColorHue(
            final int deltaH
    ) {
        final Color c = getSelectedColor();
        int hue = ColorMath.hueGetter(c) + deltaH;

        while (hue > Constants.HUE_SCALE)
            hue -= Constants.HUE_SCALE;

        while (hue < 0)
            hue += Constants.HUE_SCALE;

        final Color newC = ColorMath.hueAdjustedColor(hue, c);

        setSelectedColor(newC, ColorMath.LastHSVEdit.HUE);

        StatusUpdates.colorSliderAdjustment("Hue", hue, newC);
    }

    public void incrementSelectedColorSaturation(
            final int deltaS
    ) {
        final Color c = getSelectedColor();
        final int sat = MathPlus.bounded(0,
                ColorMath.satGetter(c) + deltaS, Constants.SAT_SCALE);

        final Color newC = ColorMath.satAdjustedColor(sat, c);

        setSelectedColor(newC, ColorMath.LastHSVEdit.SAT);

        StatusUpdates.colorSliderAdjustment("Saturation", sat, newC);
    }

    public void incrementSelectedColorValue(
            final int deltaV
    ) {
        final Color c = getSelectedColor();
        final int value = MathPlus.bounded(0,
                ColorMath.valueGetter(c) + deltaV, Constants.VALUE_SCALE);

        final Color newC = ColorMath.valueAdjustedColor(value, c);

        setSelectedColor(newC, ColorMath.LastHSVEdit.VAL);

        StatusUpdates.colorSliderAdjustment("Value", value, newC);
    }

    public void incrementSelectedColorRGBA(
            final int deltaR, final int deltaG,
            final int deltaB, final int deltaAlpha
    ) {
        final Color c = getSelectedColor(), newC = new Color(
                MathPlus.bounded(0, c.getRed() + deltaR, Constants.RGBA_SCALE),
                MathPlus.bounded(0, c.getGreen() + deltaG, Constants.RGBA_SCALE),
                MathPlus.bounded(0, c.getBlue() + deltaB, Constants.RGBA_SCALE),
                MathPlus.bounded(0, c.getAlpha() + deltaAlpha, Constants.RGBA_SCALE)
        );

        setSelectedColor(newC, ColorMath.LastHSVEdit.NONE);

        final String slider;
        final int sliderVal;

        if (deltaR != 0) {
            slider = "Red";
            sliderVal = newC.getRed();
        } else if (deltaG != 0) {
            slider = "Green";
            sliderVal = newC.getGreen();
        } else if (deltaB != 0) {
            slider = "Blue";
            sliderVal = newC.getBlue();
        } else if (deltaAlpha != 0) {
            slider = "Alpha";
            sliderVal = newC.getAlpha();
        } else {
            slider = "???";
            sliderVal = 0;
        }

        StatusUpdates.colorSliderAdjustment(slider, sliderVal, newC);
    }

    public void setSelectedColor(final Color color, final ColorMath.LastHSVEdit lastHSVEdit) {
        colors[colorIndex] = color;
        ColorMath.setLastHSVEdit(lastHSVEdit, color);
    }

    public void setColorIndex(final int colorIndex) {
        this.colorIndex = colorIndex;

        ColorMath.setLastHSVEdit(ColorMath.LastHSVEdit.NONE, getSelectedColor());
    }

    public void setColorIndexAndColor(final int colorIndex, final Color color) {
        setColorIndex(colorIndex);
        setSelectedColor(color, ColorMath.LastHSVEdit.NONE);
    }

    public void swapColors() {
        final Color temp = colors[PRIMARY];
        colors[PRIMARY] = colors[SECONDARY];
        colors[SECONDARY] = temp;

        ColorMath.setLastHSVEdit(ColorMath.LastHSVEdit.NONE, getSelectedColor());

        if (!Layout.isColorsPanelShowing())
            StatusUpdates.swapColors();
    }

    public void toggleColorMenuMode() {
        colorMenuMode = colorMenuMode.toggle();

        if (Layout.isColorsPanelShowing())
            rebuildColorsMenu();
        else
            Layout.adjustPanels(() -> Layout.setColorsPanelShowing(true));
    }

    public void setPaletteIndex(final int paletteIndex) {
        this.paletteIndex = paletteIndex;
        rebuildColorsMenu();
    }

    private void toggleFullscreen() {
        windowed = !windowed;

        window = makeWindow();
        program.setCanvasSize(Layout.width(), Layout.height());
        program.replaceWindow(window);

        // redraw everything
        rebuildAllMenus();
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
        else if (was.equals(TextTool.get()) &&
                !tool.equals(TextTool.get()) && TextTool.get().isTyping())
            TextTool.get().setTyping(false);

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
