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

public class VerticalPanelAdjuster extends MenuElement {
    private static final int HEIGHT = 4;

    private boolean moving, highlighted;
    private int onClickY;

    private final GameImage movingImg, highlightImg;
    private final Consumer<Integer> consequence;
    private final int upLeeway, downLeeway;

    public VerticalPanelAdjuster(
            final Coord2D position, final int width,
            final int upLeeway, final int downLeeway,
            final Consumer<Integer> consequence
    ) {
        super(position, new Bounds2D(width, HEIGHT), Anchor.LEFT_CENTRAL, true);

        final Theme t = Settings.getTheme();

        movingImg = new GameImage(width, HEIGHT);
        movingImg.fill(t.highlightOutline);
        movingImg.free();

        highlightImg = new GameImage(width, HEIGHT);
        highlightImg.fill(t.buttonOutline);
        highlightImg.fill(t.highlightOverlay);
        highlightImg.free();

        this.upLeeway = Math.max(0, upLeeway);
        this.downLeeway = Math.max(0, downLeeway);

        this.consequence = consequence;
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        if (moving) {
            // movement update logic
            final int mouseY = eventLogger.getAdjustedMousePosition().y,
                    currentY = getY();

            if (mouseY != currentY) {
                if (mouseY < onClickY)
                    setY(Math.max(onClickY - upLeeway, mouseY));
                else if (mouseY > onClickY)
                    setY(Math.min(onClickY + downLeeway, mouseY));
                else
                    setY(onClickY);
            }

            // release logic
            for (GameEvent e : eventLogger.getUnprocessedEvents())
                if (e instanceof GameMouseEvent me &&
                        me.matchesAction(GameMouseEvent.Action.UP)) {
                    me.markAsProcessed();

                    moving = false;
                    consequence.accept(onClickY - getY());

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
                        onClickY = getY();

                        break;
                    }
            }
        }

        if (moving || highlighted)
            SECursor.setCursorCode(SECursor.SLIDE_VERT);
    }

    @Override
    public void update(final double deltaTime) {

    }

    @Override
    public void render(final GameImage canvas) {
        if (moving)
            draw(movingImg, canvas);
        else if (highlighted)
            draw(highlightImg, canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
