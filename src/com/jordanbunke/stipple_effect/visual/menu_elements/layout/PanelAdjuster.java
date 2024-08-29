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

    private final Function<Coord2D, Integer> dimGetter;
    private final Supplier<Integer> valGetter;
    private final Consumer<Integer> valSetter;
    private final String cursor;

    private final GameImage movingImg, highlightImg;
    private final Consumer<Integer> consequence;
    private final int minusLeeway, plusLeeway;

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

        final Theme t = Settings.getTheme();

        final int imgW = vertical ? getWidth() : getWidth() / 2,
                imgH = vertical ? getHeight() / 2 : getHeight();

        movingImg = new GameImage(imgW, imgH);
        movingImg.fill(t.highlightOutline);
        movingImg.free();

        highlightImg = new GameImage(imgW, imgH);
        highlightImg.fill(t.buttonOutline);
        highlightImg.fill(t.highlightOverlay);
        highlightImg.free();

        this.minusLeeway = Math.max(0, minusLeeway);
        this.plusLeeway = Math.max(0, plusLeeway);

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
}
