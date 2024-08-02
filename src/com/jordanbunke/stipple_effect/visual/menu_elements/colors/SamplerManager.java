package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menu.menu_elements.invisible.InvisibleMenuElement;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.SamplerMode;

import java.util.Map;

public class SamplerManager extends InvisibleMenuElement {
    private final Map<SamplerMode, MenuElement> samplerContentMap;
    private MenuElement contents;

    public SamplerManager(final Map<SamplerMode, MenuElement> samplerContentMap) {
        super();

        this.samplerContentMap = samplerContentMap;
        update(0);
    }

    @Override
    public void update(final double deltaTime) {
        contents = samplerContentMap.get(Layout.getSamplerMode());

        contents.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        contents.render(canvas);
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        contents.process(eventLogger);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
