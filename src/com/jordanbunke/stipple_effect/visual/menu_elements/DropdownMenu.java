package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menus.menu_elements.DeferredRenderMenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleToggleMenuButton;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.ScrollableMenuElement;
import com.jordanbunke.stipple_effect.visual.menu_elements.scrollable.VerticalScrollingMenuElement;

import java.util.Arrays;
import java.util.function.Supplier;

public class DropdownMenu extends DeferredRenderMenuElement {
    private boolean droppedDown;

    private final int length;
    private int index;

    private final String[] labels;

    private SimpleToggleMenuButton ddButton;
    private final VerticalScrollingMenuElement ddContainer;

    public DropdownMenu(
            final Coord2D position, final int width, final Anchor anchor,
            final int dropDownHAllowance,
            final String[] labels, final Runnable[] behaviours,
            final Supplier<Integer> initialIndexFunction
    ) {
        super(position, new Coord2D(width, Layout.STD_TEXT_BUTTON_H), anchor, true);

        droppedDown = false;

        length = labels.length;
        index = initialIndexFunction.get();

        this.labels = labels;

        updateDDButton();

        ddContainer = makeContainer(
                position.displace(0, Layout.STD_TEXT_BUTTON_H),
                new Coord2D(width, Math.min(dropDownHAllowance,
                        Layout.STD_TEXT_BUTTON_H * length)), behaviours
        );
    }

    private VerticalScrollingMenuElement makeContainer(
            final Coord2D position, final Coord2D dimensions,
            final Runnable[] behaviours
    ) {
        final MenuElement[] scrollables = new MenuElement[length];

        final int buttonWidth = getWidth() - Layout.SLIDER_OFF_DIM;

        for (int i = 0; i < length; i++) {
            final int index = i;

            final GameImage nhi = GraphicsUtils.drawTextButton(
                    buttonWidth, labels[i], false, Constants.DARK, true);

            scrollables[i] = new SimpleMenuButton(
                    position.displace(0, index * Layout.STD_TEXT_BUTTON_H),
                    new Coord2D(buttonWidth, Layout.STD_TEXT_BUTTON_H),
                    Anchor.LEFT_TOP, true,
                    () -> onSelection(index, behaviours[index]),
                    nhi, GraphicsUtils.drawHighlightedButton(nhi));
        }

        return new VerticalScrollingMenuElement(position, dimensions,
                Arrays.stream(scrollables)
                        .map(ScrollableMenuElement::new)
                        .toArray(ScrollableMenuElement[]::new),
                position.y + (length * Layout.STD_TEXT_BUTTON_H), 0);
    }

    private void onSelection(final int index, final Runnable behaviour) {
        behaviour.run();
        this.index = index;
        droppedDown = false;

        updateDDButton();
    }

    private void updateDDButton() {
        final String text = labels[index];

        final GameImage[] bases = new GameImage[] {
                GraphicsUtils.drawDropDownButton(getWidth(), text,
                        false, Constants.GREY),
                GraphicsUtils.drawDropDownButton(getWidth(), text,
                        true, Constants.GREY)
        };

        final GameImage[] highlighted = Arrays.stream(bases)
                .map(GraphicsUtils::drawHighlightedButton)
                .toArray(GameImage[]::new);

        ddButton = new SimpleToggleMenuButton(new Coord2D(getX(), getY()),
                new Coord2D(getWidth(), Layout.STD_TEXT_BUTTON_H),
                getAnchor(), true, bases, highlighted,
                new Runnable[] { () -> {}, () -> {} },
                () -> droppedDown ? 1 : 0, this::toggleDropDown);
    }

    public void toggleDropDown() {
        droppedDown = !droppedDown;
    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        ddButton.process(eventLogger);

        if (droppedDown)
            ddContainer.process(eventLogger);
    }

    @Override
    public void update(final double deltaTime) {
        ddButton.update(deltaTime);

        if (droppedDown)
            ddContainer.update(deltaTime);
    }

    @Override
    public void render(final GameImage canvas) {
        ddButton.render(canvas);

        if (droppedDown)
            ddContainer.render(canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
