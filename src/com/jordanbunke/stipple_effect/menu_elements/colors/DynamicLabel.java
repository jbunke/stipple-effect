package com.jordanbunke.stipple_effect.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.error.GameError;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.awt.*;
import java.util.concurrent.Callable;

public class DynamicLabel extends MenuElement {
    private final Callable<String> getter;

    private final Color textColor;

    private String label;
    private GameImage labelImage;

    public DynamicLabel(
            final Coord2D position, Anchor anchor, final Color textColor,
            final Callable<String> getter, final int widthAllowance
    ) {
        super(position, new Coord2D(widthAllowance, Constants.DYNAMIC_LABEL_H), anchor, true);

        this.textColor = textColor;

        this.getter = getter;
        label = fetchLabel();

        updateAssets();
    }

    private String fetchLabel() {
        try {
            return getter.call();
        } catch (Exception e) {
            GameError.send("Failed to fetch an updated label");
            return label;
        }
    }

    private void updateAssets() {
        final GameImage l = GraphicsUtils.uiText(textColor).addText(label).build().draw();
        labelImage = new GameImage(getWidth(), getHeight());

        final Coord2D offset = switch (getAnchor()) {
            case RIGHT_TOP, RIGHT_CENTRAL, RIGHT_BOTTOM ->
                    new Coord2D((labelImage.getWidth() - l.getWidth()), 0);
            case CENTRAL, CENTRAL_BOTTOM, CENTRAL_TOP ->
                    new Coord2D((labelImage.getWidth() - l.getWidth()) / 2, 0);
            default -> new Coord2D();
        };

        labelImage.draw(l, offset.x, offset.y);

        labelImage.free();
    }

    @Override
    public void process(final InputEventLogger eventLogger) {

    }

    @Override
    public void update(final double deltaTime) {
        final String fetched = fetchLabel();

        if (!label.equals(fetched)) {
            label = fetched;
            updateAssets();
        }
    }

    @Override
    public void render(final GameImage canvas) {
        draw(labelImage, canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
