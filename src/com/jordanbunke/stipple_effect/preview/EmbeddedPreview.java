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
    private static int embX, embY, embW, embH;

    private final EmbeddedTopBar topBar;
    private VerticalPanelAdjuster bottomAdjuster;
    private HorizontalPanelAdjuster leftAdjuster, rightAdjuster;

    private final int initialW, initialH;

    static {
        embX = 0;
        embY = 0;
        embW = PREV_MIN_W;
        embH = PREV_MIN_H;
    }

    EmbeddedPreview(final SEContext c) {
        super(c, new Coord2D(embX, embY + EMBED_PREV_TOP_BAR_Y),
                new Bounds2D(embW, embH));

        topBar = new EmbeddedTopBar(this);
        makeAdjusters();

        initialW = embW;
        initialH = embH;

        refresh();
    }

    private void makeAdjusters() {
        final int w = getWidth(), h = getHeight();

        bottomAdjuster = new VerticalPanelAdjuster(
                getPosition().displace(0, h), w,
                upLeeway(), downLeeway(), dh -> {
                    embH = getHeight() - dh;
                    setHeight(embH);
                    leftAdjuster.setLength(embH);
                    rightAdjuster.setLength(embH);

                    bottomAdjuster.setDownLeeway(downLeeway());
                    bottomAdjuster.setUpLeeway(upLeeway());
                });
        leftAdjuster = new HorizontalPanelAdjuster(
                getPosition(), h, sideGainLeeway(), sideLossLeeway(),
                dw -> {
                    incrementX(-dw);
                    topBar.incrementX(-dw);
                    bottomAdjuster.incrementX(-dw);

                    final Coord2D position = getPosition();
                    embX = position.x;
                    embY = position.y - EMBED_PREV_TOP_BAR_Y;

                    sideAdjustment(getWidth() + dw);

                    refresh();
                });
        rightAdjuster = new HorizontalPanelAdjuster(
                getPosition().displace(w, 0), h,
                sideLossLeeway(), sideGainLeeway(),
                dw -> sideAdjustment(getWidth() - dw));

        addMenuComponents(bottomAdjuster, leftAdjuster, rightAdjuster);
    }

    private void sideAdjustment(final int newW) {
        embW = newW;
        setWidth(embW);
        topBar.setWidth(embW);
        bottomAdjuster.setLength(embW);

        final int gain = sideGainLeeway(), loss = sideLossLeeway();

        leftAdjuster.setLeftLeeway(gain);
        leftAdjuster.setRightLeeway(loss);
        rightAdjuster.setLeftLeeway(loss);
        rightAdjuster.setRightLeeway(gain);
    }

    private int upLeeway() {
        return getHeight() - minSize().height();
    }

    private int downLeeway() {
        return maxSize().height() - getHeight();
    }

    private int sideLossLeeway() {
        return getWidth() - minSize().width();
    }

    private int sideGainLeeway() {
        return maxSize().width() - getWidth();
    }

    public void refresh() {
        // triggers bounding check
        topBar.setPosition(topBar.getPosition());
    }

    @Override
    public void setPosition(final Coord2D position) {
        super.setPosition(position);

        // panel adjuster fix
        bottomAdjuster.incrementY(embH - initialH);
        rightAdjuster.incrementX(embW - initialW);

        embX = position.x;
        embY = position.y - EMBED_PREV_TOP_BAR_Y;
    }

    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);

        topBar.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        super.render(canvas);

        final int w = getWidth(), h = getHeight();

        // border
        canvas.drawRectangle(Settings.getTheme().panelDivisions,
                2f, getX(), getY(), w, h);

        // adjuster
        bottomAdjuster.render(canvas);
        leftAdjuster.render(canvas);
        rightAdjuster.render(canvas);

        topBar.render(canvas);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        processExternal(eventLogger);

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
        bottomAdjuster.process(eventLogger);
        leftAdjuster.process(eventLogger);
        rightAdjuster.process(eventLogger);

        topBar.process(eventLogger);
    }

    public boolean inBounds(final Coord2D mousePos) {
        return mouseIsWithinBounds(mousePos) ||
                topBar.mouseIsWithinBounds(mousePos);
    }
}
