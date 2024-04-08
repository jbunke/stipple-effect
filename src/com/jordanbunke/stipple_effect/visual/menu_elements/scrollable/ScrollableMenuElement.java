package com.jordanbunke.stipple_effect.visual.menu_elements.scrollable;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.InvisibleMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;

public class ScrollableMenuElement extends InvisibleMenuElement {

    private final MenuElement associated;
    private final Coord2D originalPosition;
    private Coord2D offset;

    public ScrollableMenuElement(final MenuElement associated) {
        this.associated = associated;
        this.originalPosition = new Coord2D(associated.getX(), associated.getY());
        this.offset = new Coord2D();
    }

    public void setPositionFromOffset(final Coord2D offset) {
        if (!offset.equals(this.offset)) {
            associated.setX(originalPosition.x + offset.x);
            associated.setY(originalPosition.y + offset.y);

            this.offset = offset;
        }
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        associated.process(eventLogger);
    }

    @Override
    public void render(GameImage canvas) {
        associated.render(canvas);
    }

    @Override
    public void update(final double deltaTime) {
        associated.update(deltaTime);
    }

    @Override
    public int getHeight() {
        return associated.getHeight();
    }

    @Override
    public int getWidth() {
        return associated.getWidth();
    }

    @Override
    public int getX() {
        return associated.getX();
    }

    @Override
    public int getY() {
        return associated.getY();
    }

    @Override
    public Anchor getAnchor() {
        return associated.getAnchor();
    }

    @Override
    public Coord2D getRenderPosition() {
        return associated.getRenderPosition();
    }

    @Override
    public int getRenderOrder() {
        return associated.getRenderOrder();
    }

    @Override
    public void setX(int x) {
        final int was = getX();
        super.setX(x);

        associated.setX(x + (associated.getX() - was));
    }

    @Override
    public void setY(int y) {
        final int was = getY();
        super.setY(y);

        associated.setY(y + (associated.getY() - was));
    }
}
