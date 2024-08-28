package com.jordanbunke.stipple_effect.preview;

import com.jordanbunke.delta_time.debug.GameDebugger;
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
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.PlaybackInfo;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.SEInterpreter;
import com.jordanbunke.stipple_effect.scripting.util.ScriptUtils;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;
import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.DynamicLabel;
import com.jordanbunke.stipple_effect.visual.menu_elements.IncrementalRangeElements;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static com.jordanbunke.stipple_effect.utility.Layout.*;

public abstract class Preview extends MenuElement implements PreviewPlayback {
    private static Preview INSTANCE;

    final SEContext c;
    private HeadFuncNode script;

    // playback
    private final PlaybackInfo playbackInfo;
    private int frameIndex, frameCount;

    // project render
    private GameImage[] content;
    final PreviewContainer container;

    // element movement
    private final Coord2D initialPos;
    private final Map<MenuElement, Coord2D> componentsPositions;
    private final Menu menu;

    static {
        INSTANCE = null;
    }

    public static void set(final SEContext c) {
        if (INSTANCE != null)
            INSTANCE.close();

        final boolean embedded = false; // TODO - fetch from setting

        INSTANCE = embedded ? EmbeddedPreview.make(c) : new WindowedPreview(c);
    }

    public static Preview get() {
        return INSTANCE;
    }

    static void kill() {
        INSTANCE = null;
    }

    protected Preview(
            final SEContext c, final Coord2D position, final Bounds2D dimensions
    ) {
        super(position, dimensions, Anchor.LEFT_TOP, true);

        this.c = c;
        script = null;

        frameIndex = 0;
        updateContent();

        initialPos = position;
        componentsPositions = new HashMap<>();

        playbackInfo = PlaybackInfo.forPreview();
        menu = makeMenu();

        container = new PreviewContainer(
                initialPos.displace(PREV_TL_BUFFER, PREV_CONTAINER_Y), this);
    }

    private Menu makeMenu() {
        final MenuBuilder mb = new MenuBuilder();

        final Coord2D initial = initialPos.displace(
                PREV_TL_BUFFER,
                PREV_TL_BUFFER);
        final Supplier<Boolean> hasMultipleFrames = () -> frameCount > 1;

        // SCRIPT
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
                                initial.displace(BUTTON_INC, 0),
                                () -> content != null && content.length > 0,
                                this::importPreview),
                        // TODO - import per layer
                        scriptText = labelAfterLastButton(
                                importButton, () -> text, text);

                addMenuComponents(removeButton, importButton, scriptText);

                return new MenuElementGrouping(
                        removeButton, importButton, scriptText);
            } else {
                final MenuElement scriptButton =
                        GraphicsUtils.generateIconButton(
                                ResourceCodes.IMPORT_SCRIPT, initial,
                                () -> true, this::openPreviewScript),
                        scriptText = labelAfterLastButton(
                                scriptButton, () -> text, text);

                addMenuComponents(scriptButton, scriptText);

                return new MenuElementGrouping(scriptButton, scriptText);
            }
        });
        mb.addAll(smartScriptElement);

        // PLAYBACK
        final MenuElement firstFrame =
                GraphicsUtils.generateIconButton(
                        ResourceCodes.TO_FIRST_FRAME,
                        initial.displace(0, BUTTON_INC),
                        hasMultipleFrames, () -> frameIndex = 0),
                previousFrame = GraphicsUtils.generateIconButton(
                        ResourceCodes.PREVIOUS, firstFrame.getRenderPosition()
                                .displace(BUTTON_INC, 0),
                        hasMultipleFrames, () -> frameIndex = frameIndex == 0
                                ? frameCount - 1 : frameIndex - 1),
                playStop = GraphicsUtils.generateIconToggleButton(
                        previousFrame.getRenderPosition()
                                .displace(BUTTON_INC, 0),
                        new String[] { ResourceCodes.PLAY, ResourceCodes.STOP },
                        new Runnable[] {
                                playbackInfo::play, playbackInfo::stop
                        }, () -> playbackInfo.isPlaying() ? 1 : 0, () -> {},
                        hasMultipleFrames, ResourceCodes.PLAY),
                nextFrame = GraphicsUtils.generateIconButton(
                        ResourceCodes.NEXT, playStop.getRenderPosition()
                                .displace(BUTTON_INC, 0),
                        hasMultipleFrames, () ->
                                frameIndex = (frameIndex + 1) % frameCount),
                lastFrame = GraphicsUtils.generateIconButton(
                        ResourceCodes.TO_LAST_FRAME, nextFrame.getRenderPosition()
                                .displace(BUTTON_INC, 0),
                        hasMultipleFrames, () -> frameIndex = frameCount - 1);

        final PlaybackInfo.Mode[] validModes = new PlaybackInfo.Mode[] {
                PlaybackInfo.Mode.FORWARDS, PlaybackInfo.Mode.BACKWARDS,
                PlaybackInfo.Mode.LOOP, PlaybackInfo.Mode.PONG_FORWARDS
        };
        final MenuElement playbackModeButton =
                GraphicsUtils.generateIconToggleButton(
                        lastFrame.getRenderPosition().displace(
                                BUTTON_INC, 0),
                        Arrays.stream(validModes)
                                .map(PlaybackInfo.Mode::getIconCode)
                                .toArray(String[]::new),
                        Arrays.stream(validModes)
                                .map(mode -> (Runnable) () -> {})
                                .toArray(Runnable[]::new),
                        () -> playbackInfo.getMode().buttonIndex(),
                        playbackInfo::cycleMode, hasMultipleFrames,
                        ResourceCodes.LOOP);
        final DynamicLabel frameTracker = labelAfterLastButton(
                playbackModeButton,
                () -> (frameIndex + 1) + "/" + frameCount, "XXX/XXX");

        addMenuComponents(firstFrame, previousFrame, playStop,
                nextFrame, lastFrame, playbackModeButton, frameTracker);
        mb.addAll(firstFrame, previousFrame, playStop,
                nextFrame, lastFrame, playbackModeButton, frameTracker);

        // FPS
        final StaticMenuElement fpsReference = new StaticMenuElement(
                initial.displace(
                        -(BUTTON_DIM + CONTENT_BUFFER_PX),
                        BUTTON_INC), ICON_DIMS,
                MenuElement.Anchor.LEFT_TOP, GameImage.dummy());
        final int fpsButtonY = initial.y + BUTTON_INC * 2;
        final IncrementalRangeElements<Integer> fps =
                IncrementalRangeElements.makeForInt(fpsReference, fpsButtonY,
                        (fpsButtonY - BUTTON_OFFSET) + TEXT_Y_OFFSET,
                        1, Constants.MIN_PLAYBACK_FPS, Constants.MAX_PLAYBACK_FPS,
                        playbackInfo::setFps, playbackInfo::getFps,
                        i -> i, sv -> sv, sv -> sv + " FPS", "XXX FPS");

        addMenuComponents(fps.decButton, fps.incButton, fps.slider, fps.value);
        mb.addAll(fps.decButton, fps.incButton, fps.slider, fps.value);

        return mb.build();
    }

    private static DynamicLabel labelAfterLastButton(
            final MenuElement lastButton, final Supplier<String> getter,
            final String widestCase
    ) {
        return DynamicLabel.make(
                lastButton.getRenderPosition().displace(
                        BUTTON_DIM + CONTENT_BUFFER_PX,
                        -BUTTON_OFFSET + TEXT_Y_OFFSET),
                getter, DynamicLabel.getWidth(widestCase));
    }

    private void addMenuComponents(final MenuElement... components) {
        for (MenuElement component : components)
            componentsPositions.put(component, component.getPosition());
    }

    @Override
    public void setPosition(final Coord2D position) {
        super.setPosition(position);

        propagatePositionChange();
    }

    private void propagatePositionChange() {
        final Coord2D deltaPos = getPosition().displace(initialPos.scale(-1));

        for (MenuElement comp : componentsPositions.keySet())
            comp.setPosition(componentsPositions.get(comp).displace(deltaPos));
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        menu.process(eventLogger);

        if (frameCount > 1)
            processKeys(eventLogger);

        container.process(eventLogger);
    }

    @Override
    public void update(final double deltaTime) {
        if (!projectActive()) {
            close();
            return;
        }

        animate(deltaTime);

        menu.update(deltaTime);
        container.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        final GameImage bg = new GameImage(getWidth(), getHeight());
        bg.fill(Settings.getTheme().panelBackground);
        draw(bg.submit(), canvas);

        menu.render(canvas);
        container.render(canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {}

    protected abstract void close();
    protected abstract void executeOnFileDialogOpen();

    protected static Bounds2D minSize() {
        return new Bounds2D(PREV_MIN_W, PREV_MIN_H);
    }

    protected static Bounds2D maxSize() {
        return new Bounds2D(width() / 2,
                (int)(height() * 0.75));
    }

    private boolean projectActive() {
        return c.equals(StippleEffect.get().getContext());
    }

    private void animate(final double deltaTime) {
        final double duration = script == null
                ? c.getState().getFrameDurations().get(frameIndex)
                : Constants.DEFAULT_FRAME_DURATION;

        if (playbackInfo.isPlaying()) {
            final boolean nextFrameDue =
                    playbackInfo.checkIfNextFrameDue(deltaTime, duration);

            if (nextFrameDue)
                frameIndex = playbackInfo.nextAnimationFrameForPreview(
                        frameIndex, frameCount);
        }

        if (c.getSaveConfig().hasChangesSincePreview())
            updateContent();
    }

    private void updateContent() {
        c.getSaveConfig().logPreview();

        content = IntStream.range(0, c.getState().getFrameCount())
                .mapToObj(i -> c.getState()
                        .draw(false, false, i))
                .toArray(GameImage[]::new);

        if (script != null)
            runScript();

        updateFrameData();
    }

    private void runScript() {
        content = ScriptUtils.runPreviewScript(content, script);

        if (content == null)
            close();
    }

    private void updateFrameData() {
        frameCount = content.length;
        frameIndex %= frameCount;
    }

    private void openPreviewScript() {
        final Path filepath = StippleEffect.get().openScript();
        executeOnFileDialogOpen();

        if (filepath == null)
            return;

        final HeadFuncNode script =
                SEInterpreter.get().build(FileIO.readFile(filepath));

        if (SEInterpreter.validatePreviewScript(script, c))
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
        final SEContext project = ScriptUtils.projectFromScriptOutput(content);

        StippleEffect.get().scheduleJob(() ->
                StippleEffect.get().addContext(project, true));
    }

    GameImage getFrame() {
        return content[frameIndex];
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
