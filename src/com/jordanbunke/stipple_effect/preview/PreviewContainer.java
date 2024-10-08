package com.jordanbunke.stipple_effect.preview;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.events.GameMouseScrollEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.RenderInfo;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.SECursor;

import java.util.List;

import static com.jordanbunke.stipple_effect.project.RenderInfo.*;
import static com.jordanbunke.stipple_effect.utility.Layout.PREV_CONTAINER_Y;
import static com.jordanbunke.stipple_effect.utility.Layout.PREV_TL_BUFFER;
import static com.jordanbunke.stipple_effect.utility.action.SEAction.*;

public final class PreviewContainer extends MenuElement {
    private RenderInfo renderInfo;
    private GameImage frame;

    // hand auxiliaries
    private boolean panning;
    private Coord2D startMousePos, startAnchor;

    public PreviewContainer(
            final Coord2D position, final Preview preview
    ) {
        super(position, determineSizeFromPreview(preview),
                Anchor.LEFT_TOP, true);

        renderInfo = RenderInfo.forPreview();
        frame = GameImage.dummy();

        panning = false;
        startMousePos = new Coord2D();
        startAnchor = new Coord2D();
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        final Coord2D mousePos = eventLogger.getAdjustedMousePosition();
        final boolean inBounds = mouseIsWithinBounds(mousePos);
        final int w = frame.getWidth(), h = frame.getHeight();

        SNAP_TO_CENTER.shortcut.checkIfPressed(
                eventLogger, () -> renderInfo.center(w, h));

        if (panning) {
            final float z = renderInfo.getZoomFactor();

            final Coord2D shift = new Coord2D(
                    (int)((startMousePos.x - mousePos.x) / z),
                    (int)((startMousePos.y - mousePos.y) / z));

            final Coord2D anchor = new Coord2D(
                    startAnchor.x + shift.x, startAnchor.y + shift.y);
            renderInfo.setAnchor(anchor, w, h);

            if (!inBounds)
                panning = false;
        }

        if (inBounds) {
            final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();

            for (GameEvent event : unprocessed)
                if (event instanceof GameMouseScrollEvent mse) {
                    mse.markAsProcessed();

                    if (Settings.getScrollClicks(mse.clicksScrolled,
                            Settings.Code.INVERT_ZOOM_DIRECTION) < 0)
                        renderInfo.zoomInPreview();
                    else
                        renderInfo.zoomOutPreview();
                } else if (event instanceof GameMouseEvent me) {
                    if (me.matchesAction(GameMouseEvent.Action.DOWN)) {
                        panning = true;
                        startMousePos = mousePos;
                        startAnchor = renderInfo.getAnchor();
                        me.markAsProcessed();
                    } else if (me.matchesAction(GameMouseEvent.Action.UP)) {
                        panning = false;
                        me.markAsProcessed();
                    }
                }
        }

        if (panning || inBounds)
            SECursor.setCursorCode(panning
                    ? SECursor.HAND_GRAB : SECursor.HAND_OPEN);
    }

    @Override
    public void update(final double deltaTime) {
        final Preview preview = Preview.get();

        if (preview == null)
            return;

        setDimensions(determineSizeFromPreview(preview));
        updateFrame(preview.getFrame());
    }

    @Override
    public void render(final GameImage canvas) {
        final int w = getWidth(), h = getHeight(),
                fw = frame.getWidth(), fh = frame.getHeight();
        final float z = renderInfo.getZoomFactor();

        final GameImage container = new GameImage(w, h);
        container.fill(Settings.getTheme().workspaceBackground);

        // checkerboard?

        final Coord2D rp = renderInfo.localRenderPosition(w, h);
        final Coord2D[] bounds = renderBounds(rp, fw, fh, z);

        container.draw(frame.section(bounds[TL], bounds[BR]),
                bounds[TRP].x, bounds[TRP].y,
                (int)(bounds[DIM].x * z),
                (int)(bounds[DIM].y * z));

        draw(container.submit(), canvas);

        canvas.drawRectangle(Settings.getTheme().panelDivisions,
                2f, getX(), getY(), w, h);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {}

    private static Bounds2D determineSizeFromPreview(final Preview preview) {
        final Bounds2D pSize = preview.getDimensions();

        return new Bounds2D(pSize.width() - (2 * PREV_TL_BUFFER),
                pSize.height() - (PREV_TL_BUFFER + PREV_CONTAINER_Y));
    }

    private void updateFrame(final GameImage frame) {
        final int oldW = this.frame.getWidth(), oldH = this.frame.getHeight();

        this.frame = frame;

        final int w = frame.getWidth(), h = frame.getHeight();

        if (oldW != w || oldH != h)
            renderInfo.center(w, h);
        else
            renderInfo.updateAnchor(w, h);
    }

    boolean isPanning() {
        return panning;
    }

    GameImage getFrame() {
        return frame;
    }

    RenderInfo getRenderInfo() {
        return renderInfo;
    }

    void setFrame(final GameImage frame) {
        this.frame = frame;
    }

    void setRenderInfo(final RenderInfo renderInfo) {
        this.renderInfo = renderInfo;
    }
}
