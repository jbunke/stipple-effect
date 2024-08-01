package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.action.SEAction;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

public class ActionButton extends MenuElement {
    private final SEAction action;
    private final SEContext c;

    private final MenuElement stub, button;
    private MenuElement current;

    public ActionButton(
            final Coord2D position, final SEAction action, final SEContext c
    ) {
        this(position, Anchor.LEFT_TOP, action, c);
    }

    public ActionButton(
            final Coord2D position, final Anchor anchor,
            final SEAction action, final SEContext c
    ) {
        super(position, Layout.ICON_DIMS, anchor, true);

        this.action = action;
        this.c = c;

        stub = generateStub(position, anchor, action);
        button = IconButton.fromAction(position, anchor, action, c);

        update(0d);
    }

    private static StaticMenuElement generateStub(
            final Coord2D position, final Anchor anchor, final SEAction action
    ) {
        return new StaticMenuElement(position, Layout.ICON_DIMS, anchor,
                Settings.getTheme().logic.unclickableIcon(
                        GraphicsUtils.loadIcon(action.code)));
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        current.process(eventLogger);
    }

    @Override
    public void update(final double deltaTime) {
        current = action.getPrecondition().test(c)
                ? button : stub;

        current.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        current.render(canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {
        current.debugRender(canvas, debugger);
    }
}
