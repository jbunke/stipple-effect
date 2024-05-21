package com.jordanbunke.stipple_effect.preview;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;

public class PreviewImage extends MenuElement {
    private GameImage image;

    public PreviewImage() {
        super(new Coord2D(Layout.PREVIEW_WINDOW_BUFFER_PX,
                        Layout.PREVIEW_WINDOW_BUFFER_PX +
                                PreviewWindow.MENU_Y_ALLOTMENT_PX),
                new Bounds2D(Constants.MAX_CANVAS_W, Constants.MAX_CANVAS_H),
                Anchor.LEFT_TOP, true);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {

    }

    @Override
    public void update(final double deltaTime) {

    }

    public void refresh(final GameImage drawn) {
        this.image = drawn;
    }

    @Override
    public void render(final GameImage canvas) {
        draw(image, canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
