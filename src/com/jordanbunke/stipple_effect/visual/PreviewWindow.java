package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.contexts.ProgramContext;
import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseScrollEvent;
import com.jordanbunke.delta_time.events.GameWindowEvent;
import com.jordanbunke.delta_time.game.Game;
import com.jordanbunke.delta_time.game.GameManager;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.Menu;
import com.jordanbunke.delta_time.menus.MenuBuilder;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.MathPlus;
import com.jordanbunke.delta_time.window.GameWindow;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.PlaybackInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.IconButton;
import com.jordanbunke.stipple_effect.visual.menu_elements.IncrementalRangeElements;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class PreviewWindow implements ProgramContext {
    private static final int BORDER = 1,
            MENU_X_ALLOTMENT_PX = Layout.BUTTON_INC * 10,
            MENU_Y_ALLOTMENT_PX = (Layout.BUTTON_INC * 3) +
                    (Layout.CONTENT_BUFFER_PX - Layout.BUTTON_OFFSET);

    private static PreviewWindow INSTANCE = null;

    private GameWindow window;
    private final SEContext context;
    private final Game preview;
    private final Menu menu;
    private final PlaybackInfo playbackInfo;

    private int canvasW, canvasH, frameIndex, frameCount;
    private float zoom;
    private Coord2D mousePos;
    private GameImage project;

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

        frameIndex = 0;
        frameCount = this.context.getState().getFrameCount();
        updateProject();

        menu = makeMenu();
        window = makeWindow();
        preview = new Game(window, new GameManager(0, this));
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
                () -> context.getState().getFrameCount() > 1;

        final MenuElement firstFrame =
                GraphicsUtils.generateIconButton(
                        IconCodes.TO_FIRST_FRAME, initial,
                        hasMultipleFrames, () -> frameIndex = 0),
                previousFrame = GraphicsUtils.generateIconButton(
                        IconCodes.PREVIOUS, firstFrame.getRenderPosition()
                                .displace(Layout.BUTTON_INC, 0),
                        hasMultipleFrames, () -> frameIndex = frameIndex == 0
                                ? frameCount - 1 : frameIndex - 1),
                playStop = GraphicsUtils.generateIconToggleButton(
                        previousFrame.getRenderPosition()
                                .displace(Layout.BUTTON_INC, 0),
                        new String[] { IconCodes.PLAY, IconCodes.STOP },
                        new Runnable[] {
                                playbackInfo::play, playbackInfo::stop
                        }, () -> playbackInfo.isPlaying() ? 1 : 0, () -> {},
                        hasMultipleFrames, IconCodes.PLAY),
                nextFrame = GraphicsUtils.generateIconButton(
                        IconCodes.NEXT, playStop.getRenderPosition()
                                .displace(Layout.BUTTON_INC, 0),
                        hasMultipleFrames, () ->
                                frameIndex = (frameIndex + 1) % frameCount),
                lastFrame = GraphicsUtils.generateIconButton(
                        IconCodes.TO_LAST_FRAME, nextFrame.getRenderPosition()
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
                        IconCodes.LOOP);
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
        final int fpsButtonY = initial.y + Layout.BUTTON_INC;
        final IncrementalRangeElements<Integer> fps =
                IncrementalRangeElements.makeForInt(fpsReference, fpsButtonY,
                        (fpsButtonY - Layout.BUTTON_OFFSET) + Layout.TEXT_Y_OFFSET,
                        1, Constants.MIN_PLAYBACK_FPS, Constants.MAX_PLAYBACK_FPS,
                        playbackInfo::setFps, playbackInfo::getFps,
                        i -> i, sv -> sv, sv -> sv + " fps", "XXX fps");
        mb.addAll(fps.decButton, fps.incButton, fps.slider, fps.value);

        final IconButton decZoom = IconButton.make(IconCodes.DECREMENT,
                firstFrame.getRenderPosition()
                        .displace(0, Layout.BUTTON_INC * 2),
                this::zoomOut),
                incZoom = IconButton.make(IconCodes.INCREMENT,
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
            final String maxWidthCase
    ) {
        return new DynamicLabel(
                lastButton.getRenderPosition().displace(
                        Layout.BUTTON_DIM + Layout.CONTENT_BUFFER_PX,
                        -Layout.BUTTON_OFFSET + Layout.TEXT_Y_OFFSET),
                MenuElement.Anchor.LEFT_TOP, Constants.WHITE, getter,
                Layout.estimateDynamicLabelMaxWidth(maxWidthCase));
    }

    private void setZoom(final float zoom) {
        final float was = this.zoom;
        this.zoom = (float) MathPlus.bounded(Constants.NO_ZOOM,
                zoom, Constants.MAX_PREVIEW_ZOOM);

        if (was != this.zoom)
            updateWindow();
    }

    private void zoomIn() {
        setZoom(zoom * Constants.ZOOM_CHANGE_LEVEL);
    }

    private void zoomOut() {
        setZoom(zoom / Constants.ZOOM_CHANGE_LEVEL);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        menu.process(eventLogger);

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
            }
    }

    @Override
    public void update(final double deltaTime) {
        if (checkIfProjectHasBeenClosed()) {
            window.closeInstance();
            return;
        }

        menu.update(deltaTime);

        animate(deltaTime);
        windowUpdateCheck();
    }

    private void animate(final double deltaTime) {
        frameCount = context.getState().getFrameCount();
        frameIndex = frameIndex % frameCount;

        if (playbackInfo.isPlaying()) {
            final boolean nextFrameDue = playbackInfo.checkIfNextFrameDue(deltaTime);

            if (nextFrameDue)
                frameIndex = playbackInfo.nextAnimationFrameForPreview(
                        frameIndex, frameCount);
        }

        updateProject();
    }

    private void updateProject() {
        project = context.getState().draw(false, false, frameIndex);
    }

    private boolean checkIfProjectHasBeenClosed() {
        return !context.equals(StippleEffect.get().getContext());
    }

    private void windowUpdateCheck() {
        final int w = context.getState().getImageWidth(),
                h = context.getState().getImageHeight();

        if (canvasW != w || canvasH != h)
            updateWindow();
    }

    private void updateWindow() {
        window = makeWindow();
        preview.replaceWindow(window);
        preview.setCanvasSize(window.getWidth(), window.getHeight());
        window.setOnCloseBehaviour(preview::terminateExecution);

        StippleEffect.get().window.getEventLogger().unpressAllKeys();
    }

    private GameWindow makeWindow() {
        canvasW = context.getState().getImageWidth();
        canvasH = context.getState().getImageHeight();

        final int buffer = 2 * Layout.PREVIEW_WINDOW_BUFFER_PX,
                addOn = buffer + (2 * BORDER),
                xBase = addOn + MENU_X_ALLOTMENT_PX,
                yBase = addOn + MENU_Y_ALLOTMENT_PX;

        final int width = MathPlus.bounded(xBase,
                addOn + (int) (canvasW * zoom),
                Toolkit.getDefaultToolkit().getScreenSize().width),
                height = MathPlus.bounded(yBase,
                        yBase + (int) (canvasH * zoom),
                        Toolkit.getDefaultToolkit().getScreenSize().height);

        final GameWindow window = new GameWindow("Preview: " +
                context.projectInfo.getFormattedName(false, false),
                width, height, GraphicsUtils.loadIcon(IconCodes.PROGRAM),
                false, false, false);
        window.hideCursor();
        return window;
    }

    @Override
    public void render(final GameImage canvas) {
        canvas.fill(Constants.ACCENT_BACKGROUND_DARK);

        renderProject(canvas);
        menu.render(canvas);
        renderCursor(canvas);
    }

    private void renderProject(final GameImage canvas) {
        final int renderX = Layout.PREVIEW_WINDOW_BUFFER_PX,
                renderY = Layout.PREVIEW_WINDOW_BUFFER_PX + MENU_Y_ALLOTMENT_PX;

        final int w = (int) Math.max(1, canvasW * zoom),
                h = (int) Math.max(1, canvasH * zoom);

        final GameImage canvasContents = new GameImage(w + (2 * BORDER),
                h + (2 * BORDER));

        canvasContents.drawRectangle(Constants.BLACK, 2f, BORDER, BORDER, w, h);
        canvasContents.draw(context.getCheckerboard(), BORDER, BORDER, w, h);
        canvasContents.draw(project, BORDER, BORDER, w, h);

        canvas.draw(canvasContents.submit(), renderX, renderY);
    }

    private void renderCursor(final GameImage canvas) {
        canvas.draw(SECursor.fetchCursor(SECursor.MAIN_CURSOR),
                mousePos.x - (Layout.CURSOR_DIM / 2),
                mousePos.y - (Layout.CURSOR_DIM / 2));
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
