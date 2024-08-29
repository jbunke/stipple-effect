package com.jordanbunke.stipple_effect.preview;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.DeltaTimeGlobal;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.menu_elements.layout.*;

import static com.jordanbunke.stipple_effect.utility.Layout.*;
import static com.jordanbunke.stipple_effect.visual.SECursor.*;

public final class EmbeddedPreview extends Preview {
    // TODO:
    //  moveable top bar with title
    private static int embX, embY, embW, embH;

    private PanelAdjuster vertAdjuster, horzAdjuster;

    static {
        embX = 0;
        embY = 0;
        embW = PREV_MIN_W;
        embH = PREV_MIN_H;
    }

    EmbeddedPreview(final SEContext c) {
        super(c, new Coord2D(embX, embY + EMBED_PREV_TOP_BAR_Y),
                new Bounds2D(embW, embH));

        // TODO - define top bar
        makeAdjusters();
    }

    private void makeAdjusters() {
        final int w = getWidth(), h = getHeight();

        vertAdjuster = new VerticalPanelAdjuster(
                getPosition().displace(0, h),
                w, h - minSize().height(), maxSize().height() - h,
                dh -> {
                    embH = getHeight() - dh;
                    setHeight(embH);
                    makeAdjusters();
                });
        horzAdjuster = new HorizontalPanelAdjuster(
                getPosition().displace(w, 0), h,
                w - minSize().width(), maxSize().width() - w,
                dw -> {
                    embW = getWidth() - dw;
                    setWidth(embW);
                    // TODO - set top bar width
                    makeAdjusters();
                });
    }

    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);

        // TODO
    }

    @Override
    public void render(final GameImage canvas) {
        super.render(canvas);

        final int w = getWidth(), h = getHeight();

        // border
        canvas.drawRectangle(Settings.getTheme().panelDivisions,
                2f, getX(), getY(), w, h);

        // adjuster
        vertAdjuster.render(canvas);
        horzAdjuster.render(canvas);

        // TODO - render top bar
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        processExternal(eventLogger);

        // TODO - top bar

        super.process(eventLogger);

        // cursor
        final Coord2D mousePos = eventLogger.getAdjustedMousePosition();
        final String cursorCode = systemCursorCode();

        if (container.mouseIsWithinBounds(mousePos))
            setCursorCode(container.isPanning() ? HAND_GRAB : HAND_OPEN);
        else if (!(cursorCode.equals(SLIDE_VERT) || cursorCode.equals(SLIDE_HORZ)))
            setCursorCode(MAIN_CURSOR);

        DeltaTimeGlobal.setStatus(DeltaTimeGlobal.SC_CURSOR_CAPTURED, this);
    }

    public void processExternal(final InputEventLogger eventLogger) {
        vertAdjuster.process(eventLogger);
        horzAdjuster.process(eventLogger);
    }

    public boolean inBounds(final Coord2D mousePos) {
        // TODO - potentially adjust for top bar
        return mouseIsWithinBounds(mousePos);
    }

    @Override
    protected void close() {
        kill();
    }

    @Override
    protected void executeOnFileDialogOpen() {}
}
