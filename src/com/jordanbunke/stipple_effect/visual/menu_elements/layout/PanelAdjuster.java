package com.jordanbunke.stipple_effect.visual.menu_elements.layout;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.SECursor;
import com.jordanbunke.stipple_effect.visual.theme.Theme;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class PanelAdjuster extends MenuElement {
    private static final int THICKNESS = 8;

    private boolean moving, highlighted;
    private int onClickDim;

    private final boolean vertical;
    private final Function<Coord2D, Integer> dimGetter;
    private final Supplier<Integer> valGetter;
    private final Consumer<Integer> valSetter;
    private final String cursor;

    private GameImage movingImg, highlightImg;
    private final Consumer<Integer> consequence;
    private int minusLeeway, plusLeeway;

    public PanelAdjuster(
            final Coord2D position, final int length,
            final boolean vertical,
            final int minusLeeway, final int plusLeeway,
            final Consumer<Integer> consequence
    ) {
        super(position, vertical ? new Bounds2D(length, THICKNESS)
                        : new Bounds2D(THICKNESS, length),
                vertical ? Anchor.LEFT_CENTRAL
                        : Anchor.CENTRAL_TOP, true);

        this.vertical = vertical;
        updateAssets();

        setMinusLeeway(minusLeeway);
        setPlusLeeway(plusLeeway);

        this.consequence = consequence;

        dimGetter = vertical ? c -> c.y : c -> c.x;
        valGetter = vertical ? this::getY : this::getX;
        valSetter = vertical ? this::setY : this::setX;
        cursor = vertical ? SECursor.SLIDE_VERT : SECursor.SLIDE_HORZ;
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        if (moving) {
            // movement update logic
            final int mouseDim = dimGetter.apply(eventLogger.getAdjustedMousePosition()),
                    current = valGetter.get();

            if (mouseDim != current) {
                if (mouseDim < onClickDim)
                    valSetter.accept(Math.max(onClickDim - minusLeeway, mouseDim));
                else if (mouseDim > onClickDim)
                    valSetter.accept(Math.min(onClickDim + plusLeeway, mouseDim));
                else
                    valSetter.accept(onClickDim);
            }

            // release logic
            for (GameEvent e : eventLogger.getUnprocessedEvents())
                if (e instanceof GameMouseEvent me &&
                        me.matchesAction(GameMouseEvent.Action.UP)) {
                    me.markAsProcessed();

                    moving = false;
                    consequence.accept(onClickDim - valGetter.get());

                    break;
                }
        } else {
            highlighted = mouseIsWithinBounds(eventLogger.getAdjustedMousePosition());

            if (highlighted) {
                for (GameEvent e : eventLogger.getUnprocessedEvents())
                    if (e instanceof GameMouseEvent me &&
                            me.matchesAction(GameMouseEvent.Action.DOWN)) {
                        me.markAsProcessed();

                        moving = true;
                        onClickDim = valGetter.get();

                        break;
                    }
            }
        }

        if (moving || highlighted)
            SECursor.setCursorCode(cursor);
    }

    @Override
    public void render(final GameImage canvas) {
        if (moving)
            draw(movingImg, canvas);
        else if (highlighted)
            draw(highlightImg, canvas);
    }

    @Override
    public void update(final double deltaTime) {}

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {}

    void setMinusLeeway(final int minusLeeway) {
        this.minusLeeway = Math.max(0, minusLeeway);
    }

    void setPlusLeeway(final int plusLeeway) {
        this.plusLeeway = Math.max(0, plusLeeway);
    }

    private void updateAssets() {
        final Theme t = Settings.getTheme();

        final int imgW = getWidth(), imgH = getHeight(),
                w = vertical ? imgW : imgW / 2,
                h = vertical ? imgH / 2 : imgH,
                x = vertical ? 0 : THICKNESS / 4,
                y = vertical ? THICKNESS / 4 : 0;

        movingImg = new GameImage(imgW, imgH);
        movingImg.fillRectangle(t.highlightOutline, x, y, w, h);
        movingImg.free();

        highlightImg = new GameImage(imgW, imgH);
        highlightImg.fillRectangle(t.buttonOutline, x, y, w, h);
        highlightImg.fillRectangle(t.highlightOverlay, x, y, w, h);
        highlightImg.free();
    }

    public void setLength(final int length) {
        if (vertical)
            setWidth(length);
        else
            setHeight(length);

        updateAssets();
    }
}
