package com.jordanbunke.stipple_effect.preview;

import com.jordanbunke.delta_time._core.GameManager;
import com.jordanbunke.delta_time._core.Program;
import com.jordanbunke.delta_time._core.ProgramContext;
import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseScrollEvent;
import com.jordanbunke.delta_time.events.GameWindowEvent;
import com.jordanbunke.delta_time.events.WindowMovedEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.Menu;
import com.jordanbunke.delta_time.menu.MenuBuilder;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.ThinkingMenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.delta_time.window.GameWindow;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.PlaybackInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.SEInterpreter;
import com.jordanbunke.stipple_effect.scripting.util.ScriptUtils;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;
import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.SECursor;
import com.jordanbunke.stipple_effect.visual.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.IncrementalRangeElements;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.PreviewHousingBox;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class PreviewWindow implements ProgramContext, PreviewPlayback {
    public static final int BORDER = 1,
            MENU_X_ALLOTMENT_PX = Layout.BUTTON_INC * 10,
            MENU_Y_ALLOTMENT_PX = (Layout.BUTTON_INC * 4) +
                    (Layout.CONTENT_BUFFER_PX - Layout.BUTTON_OFFSET);

    private static PreviewWindow INSTANCE;
    private static int winX, winY;

    private GameWindow window;
    private final SEContext context;
    private final Program preview;
    private final Menu menu;
    private final PlaybackInfo playbackInfo;

    private int canvasW, canvasH, frameIndex, frameCount;
    private float zoom;
    private Coord2D mousePos;
    private GameImage[] content;

    private PreviewImage previewImage;
    private PreviewHousingBox housingBox;

    private HeadFuncNode script;

    static {
        INSTANCE = null;

        winX = 0;
        winY = 0;
    }

    public static void set(final SEContext context) {
        if (INSTANCE != null)
            INSTANCE.window.closeInstance();

        INSTANCE = new PreviewWindow(context);
    }

    private PreviewWindow(final SEContext context) {
        mousePos = new Coord2D();

        this.context = context;

        playbackInfo = PlaybackInfo.forPreview();
        zoom = Constants.NO_ZOOM;

        script = null;

        updateContent();
        animate(0d);

        menu = makeMenu();
        window = makeWindow();
        previewImage = new PreviewImage();
        housingBox = makeHousingBox();
        preview = new Program(window, new GameManager(0, this));
        preview.getDebugger().getChannel(GameDebugger.FRAME_RATE).mute();
        preview.setScheduleUpdates(false);
        window.setOnCloseBehaviour(preview::terminateExecution);
    }

    private Menu makeMenu() {
        final MenuBuilder mb = new MenuBuilder();

        final Coord2D initial = new Coord2D(
                Layout.PREVIEW_WINDOW_BUFFER_PX,
                Layout.PREVIEW_WINDOW_BUFFER_PX);
        final Supplier<Boolean> hasMultipleFrames =
                () -> frameCount > 1;

        final MenuElement smartScriptElement = new ThinkingMenuElement(() -> {
            final boolean hasScript = script != null;
            final String text = (hasScript ? "Running" : "No") + " script";

            if (hasScript) {
                final MenuElement removeButton =
                        GraphicsUtils.generateIconButton(
                                ResourceCodes.REMOVE_SCRIPT, initial,
                                () -> true, this::removeScript),
                        importButton = GraphicsUtils.generateIconButton(
                                ResourceCodes.IMPORT_PREVIEW,
                                initial.displace(Layout.BUTTON_INC, 0),
                                () -> content != null && content.length > 0,
                                this::importPreview),
                        scriptText = labelAfterLastButton(
                                importButton, () -> text, text);

                return new MenuElementGrouping(
                        removeButton, importButton, scriptText);
            } else {
                final MenuElement scriptButton =
                        GraphicsUtils.generateIconButton(
                                ResourceCodes.IMPORT_SCRIPT, initial,
                                () -> true, this::openPreviewScript),
                        scriptText = labelAfterLastButton(
                                scriptButton, () -> text, text);

                return new MenuElementGrouping(scriptButton, scriptText);
            }
        });
        mb.addAll(smartScriptElement);

        final MenuElement firstFrame =
                GraphicsUtils.generateIconButton(
                        ResourceCodes.TO_FIRST_FRAME,
                        initial.displace(0, Layout.BUTTON_INC),
                        hasMultipleFrames, () -> frameIndex = 0),
                previousFrame = GraphicsUtils.generateIconButton(
                        ResourceCodes.PREVIOUS, firstFrame.getRenderPosition()
                                .displace(Layout.BUTTON_INC, 0),
                        hasMultipleFrames, () -> frameIndex = frameIndex == 0
                                ? frameCount - 1 : frameIndex - 1),
                playStop = GraphicsUtils.generateIconToggleButton(
                        previousFrame.getRenderPosition()
                                .displace(Layout.BUTTON_INC, 0),
                        new String[] { ResourceCodes.PLAY, ResourceCodes.STOP },
                        new Runnable[] {
                                playbackInfo::play, playbackInfo::stop
                        }, () -> playbackInfo.isPlaying() ? 1 : 0, () -> {},
                        hasMultipleFrames, ResourceCodes.PLAY),
                nextFrame = GraphicsUtils.generateIconButton(
                        ResourceCodes.NEXT, playStop.getRenderPosition()
                                .displace(Layout.BUTTON_INC, 0),
                        hasMultipleFrames, () ->
                                frameIndex = (frameIndex + 1) % frameCount),
                lastFrame = GraphicsUtils.generateIconButton(
                        ResourceCodes.TO_LAST_FRAME, nextFrame.getRenderPosition()
                                .displace(Layout.BUTTON_INC, 0),
                        hasMultipleFrames, () -> frameIndex = frameCount - 1);

        final PlaybackInfo.Mode[] validModes = new PlaybackInfo.Mode[] {
                PlaybackInfo.Mode.FORWARDS, PlaybackInfo.Mode.BACKWARDS,
                PlaybackInfo.Mode.LOOP, PlaybackInfo.Mode.PONG_FORWARDS
        };
        final MenuElement playbackModeButton =
                GraphicsUtils.generateIconToggleButton(
                        lastFrame.getRenderPosition().displace(
                                Layout.BUTTON_INC, 0),
                        Arrays.stream(validModes)
                                .map(PlaybackInfo.Mode::getIconCode)
                                .toArray(String[]::new),
                        Arrays.stream(validModes)
                                .map(mode -> (Runnable) () -> {})
                                .toArray(Runnable[]::new),
                        () -> playbackInfo.getMode().buttonIndex(),
                        playbackInfo::toggleMode, hasMultipleFrames,
                        ResourceCodes.LOOP);
        final DynamicLabel frameTracker = labelAfterLastButton(
                playbackModeButton,
                () -> "Frm. " + (frameIndex + 1) + "/" + frameCount,
                "Frm. XXX/XXX");

        mb.addAll(firstFrame, previousFrame, playStop,
                nextFrame, lastFrame, playbackModeButton, frameTracker);

        final StaticMenuElement fpsReference = new StaticMenuElement(
                initial.displace(
                        -(Layout.BUTTON_DIM + Layout.CONTENT_BUFFER_PX),
                        Layout.BUTTON_INC), Layout.ICON_DIMS,
                MenuElement.Anchor.LEFT_TOP, GameImage.dummy());
        final int fpsButtonY = initial.y + Layout.BUTTON_INC * 2;
        final IncrementalRangeElements<Integer> fps =
                IncrementalRangeElements.makeForInt(fpsReference, fpsButtonY,
                        (fpsButtonY - Layout.BUTTON_OFFSET) + Layout.TEXT_Y_OFFSET,
                        1, Constants.MIN_PLAYBACK_FPS, Constants.MAX_PLAYBACK_FPS,
                        playbackInfo::setFps, playbackInfo::getFps,
                        i -> i, sv -> sv, sv -> sv + " FPS", "XXX FPS");
        mb.addAll(fps.decButton, fps.incButton, fps.slider, fps.value);

        final IconButton decZoom = IconButton.make(ResourceCodes.DECREMENT,
                firstFrame.getRenderPosition()
                        .displace(0, Layout.BUTTON_INC * 2),
                this::zoomOut),
                incZoom = IconButton.make(ResourceCodes.INCREMENT,
                        decZoom.getRenderPosition()
                                .displace(Layout.BUTTON_INC, 0),
                        this::zoomIn);
        final String ZOOM_SUFFIX = "x magnification";
        final DynamicLabel zoomText = labelAfterLastButton(incZoom,
                () -> ((int) zoom) + ZOOM_SUFFIX, "XX" + ZOOM_SUFFIX);
        mb.addAll(decZoom, incZoom, zoomText);

        return mb.build();
    }

    private static DynamicLabel labelAfterLastButton(
            final MenuElement lastButton, final Supplier<String> getter,
            final String widestCase
    ) {
        return DynamicLabel.make(
                lastButton.getRenderPosition().displace(
                        Layout.BUTTON_DIM + Layout.CONTENT_BUFFER_PX,
                        -Layout.BUTTON_OFFSET + Layout.TEXT_Y_OFFSET),
                getter, DynamicLabel.getWidth(widestCase));
    }

    private void setZoom(final float zoom) {
        final float was = this.zoom;
        this.zoom = (float) MathPlus.bounded(Constants.NO_ZOOM,
                zoom, Constants.MAX_PREVIEW_ZOOM);

        if (was != this.zoom)
            updateWindow();
    }

    private void zoomIn() {
        setZoom(zoom + 1f);
    }

    private void zoomOut() {
        setZoom(zoom - 1f);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        menu.process(eventLogger);
        housingBox.process(eventLogger);

        if (frameCount > 1)
            processKeys(eventLogger);

        mousePos = eventLogger.getAdjustedMousePosition();

        final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();

        for (GameEvent event : unprocessed)
            if (event instanceof GameWindowEvent gwe && gwe.action == GameWindowEvent.Action.CLOSING) {
                window.executeOnClose();
                gwe.markAsProcessed();
                return;
            } else if (event instanceof GameMouseScrollEvent mse) {
                final boolean mouseInBounds =
                        mousePos.x >= 0 && mousePos.x < window.getWidth() &&
                        mousePos.y >= 0 && mousePos.y < window.getHeight();

                if (mouseInBounds) {
                    mse.markAsProcessed();

                    if (Settings.getScrollClicks(mse.clicksScrolled,
                            Settings.Code.INVERT_ZOOM_DIRECTION) < 0)
                        zoomIn();
                    else
                        zoomOut();
                }
            } else if (event instanceof WindowMovedEvent wme) {
                wme.markAsProcessed();

                winX = wme.x;
                winY = wme.y;
            }
    }

    @Override
    public void update(final double deltaTime) {
        if (checkIfProjectHasBeenClosed()) {
            window.closeInstance();
            return;
        }

        menu.update(deltaTime);
        housingBox.update(deltaTime);

        animate(deltaTime);
        windowUpdateCheck();
    }

    private void animate(final double deltaTime) {
        final double duration = script == null
                ? context.getState().getFrameDurations().get(frameIndex)
                : Constants.DEFAULT_FRAME_DURATION;

        if (playbackInfo.isPlaying()) {
            final boolean nextFrameDue =
                    playbackInfo.checkIfNextFrameDue(deltaTime, duration);

            if (nextFrameDue)
                frameIndex = playbackInfo.nextAnimationFrameForPreview(
                        frameIndex, frameCount);
        }

        if (context.getSaveConfig().hasChangesSincePreview())
            updateContent();

        frameCount = content.length;
        frameIndex %= frameCount;
    }

    private void updateContent() {
        context.getSaveConfig().logPreview();

        content = IntStream.range(0, context.getState().getFrameCount())
                .mapToObj(i -> context.getState()
                        .draw(false, false, i))
                .toArray(GameImage[]::new);

        if (script != null)
            runScript();
    }

    private void runScript() {
        content = ScriptUtils.runPreviewScript(content, script);

        if (content == null)
            window.closeInstance();
        else
            frameIndex %= content.length;
    }

    private boolean checkIfProjectHasBeenClosed() {
        return !context.equals(StippleEffect.get().getContext());
    }

    private void windowUpdateCheck() {
        final int w = content[frameIndex].getWidth(),
                h = content[frameIndex].getHeight();

        if (canvasW != w || canvasH != h) {
            updateWindow();
            // refocus Stipple Effect window - only here, where resizing is implicit
            StippleEffect.get().window.focus();
        }
    }

    private void updateWindow() {
        window = makeWindow();
        previewImage = new PreviewImage();
        housingBox = makeHousingBox();
        preview.replaceWindow(window);
        preview.setCanvasSize(window.getWidth(), window.getHeight());
        window.setOnCloseBehaviour(preview::terminateExecution);

        StippleEffect.get().window.getEventLogger().unpressAllKeys();
    }

    private GameWindow makeWindow() {
        canvasW = content[frameIndex].getWidth();
        canvasH = content[frameIndex].getHeight();

        final int zoomW = (int) (zoom * canvasW),
                zoomH = (int) (zoom * canvasH);

        final int buffer = 2 * Layout.PREVIEW_WINDOW_BUFFER_PX,
                addOn = buffer + (2 * BORDER),
                yBase = addOn + MENU_Y_ALLOTMENT_PX,
                extraW = zoomW > Constants.MAX_CANVAS_W
                        ? Layout.PREVIEW_WINDOW_BUFFER_PX : 0,
                extraH = zoomH > Constants.MAX_CANVAS_H
                        ? Layout.PREVIEW_WINDOW_BUFFER_PX : 0;

        final int width = addOn + MathPlus.bounded(
                MENU_X_ALLOTMENT_PX, zoomW, Constants.MAX_CANVAS_W) + extraH,
                height = yBase + Math.min(zoomH, Constants.MAX_CANVAS_H) + extraW;

        final GameWindow window = new GameWindow("Preview: " +
                context.getSaveConfig().getFormattedName(false, false),
                width, height, GraphicsUtils.readIconAsset(ResourceCodes.PROGRAM),
                false, false, false);
        window.hideCursor();
        window.setPosition(winX, winY);

        return window;
    }

    private PreviewHousingBox makeHousingBox() {
        return PreviewHousingBox.make(previewImage,
                (int) (canvasW * zoom), (int) (canvasH * zoom));
    }

    @Override
    public void render(final GameImage canvas) {
        canvas.fill(Settings.getTheme().panelBackground);

        refreshPreviewImage();

        menu.render(canvas);
        housingBox.render(canvas);
        renderCursor(canvas);
    }

    private void refreshPreviewImage() {
        final int w = (int) Math.max(1, canvasW * zoom),
                h = (int) Math.max(1, canvasH * zoom);

        final GameImage canvasContents = new GameImage(w + (2 * BORDER),
                h + (2 * BORDER));

        canvasContents.drawRectangle(Settings.getTheme().panelDivisions, 2f, BORDER, BORDER, w, h);
        canvasContents.draw(context.getCheckerboard(), BORDER, BORDER, w, h);
        canvasContents.draw(content[frameIndex], BORDER, BORDER, w, h);

        previewImage.refresh(canvasContents.submit());
    }

    private void renderCursor(final GameImage canvas) {
        canvas.draw(SECursor.fetchCursor(SECursor.MAIN_CURSOR),
                mousePos.x - (Layout.CURSOR_DIM / 2),
                mousePos.y - (Layout.CURSOR_DIM / 2));
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    private void openPreviewScript() {
        final Path filepath = StippleEffect.get().openScript();
        window.getEventLogger().unpressAllKeys();

        if (filepath == null)
            return;

        final HeadFuncNode script =
                SEInterpreter.get().build(FileIO.readFile(filepath));

        if (SEInterpreter.validatePreviewScript(script, context))
            setScript(script);
        else if (script != null)
            StatusUpdates.invalidPreviewScript();
        else
            StatusUpdates.failedToCompileScript(filepath);
    }

    private void setScript(final HeadFuncNode script) {
        this.script = script;
        updateContent();
        animate(0d);
    }

    private void removeScript() {
        script = null;
        updateContent();
        animate(0d);
    }

    private void importPreview() {
        final int frameCount = content.length,
                w = content[0].getWidth(),
                h = content[0].getHeight();

        final ProjectState state = ProjectState.makeFromRasterFile(w, h,
                SELayer.fromPreviewContent(content), frameCount);
        final SEContext project = new SEContext(null, state, w, h);

        StippleEffect.get().scheduleJob(() ->
                StippleEffect.get().addContext(project, true));
    }

    @Override
    public void toFirstFrame() {
        frameIndex = 0;
    }

    @Override
    public void toLastFrame() {
        frameIndex = frameCount - 1;
    }

    @Override
    public void previousFrame() {
        frameIndex = frameIndex == 0 ? frameCount - 1 : frameIndex - 1;
    }

    @Override
    public void nextFrame() {
        frameIndex = (frameIndex + 1) % frameCount;
    }

    @Override
    public PlaybackInfo getPlaybackInfo() {
        return playbackInfo;
    }
}
