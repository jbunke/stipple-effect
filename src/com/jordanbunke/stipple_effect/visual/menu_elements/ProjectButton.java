package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.Alignment;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.ButtonType;
import com.jordanbunke.stipple_effect.visual.menu_elements.text_button.TextButton;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;
import com.jordanbunke.stipple_effect.visual.theme.logic.ThemeLogic;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProjectButton extends SelectableListItemButton {
    private static final int MAX_QUICK_SELECTABLE_INDEX = 8;

    private final SEContext project;

    private GameImage image, highlighted;

    private boolean edited;

    private ProjectButton(
            final Coord2D position, final Bounds2D dimensions,
            final int index, final SEContext project,
            final Supplier<Integer> selectedIndexGetter,
            final Consumer<Integer> selectFunction
    ) {
        super(position, dimensions, null, null, null,
                index, selectedIndexGetter, selectFunction);

        this.project = project;
        edited = project.getSaveConfig().hasUnsavedChanges();

        updateImages();
    }

    public static ProjectButton make(
            final Coord2D position, final int index
    ) {
        final SEContext project = StippleEffect.get().getContexts().get(index);
        final String maxText = "* " + project.getSaveConfig()
                .getFormattedName(false, true);

        // placeholder calculation
        final int paddedTextWidth = GraphicsUtils.uiText(SEColors.black())
                .addText(maxText).build().draw().getWidth() +
                Layout.PROJECT_NAME_BUTTON_PADDING_W;

        return new ProjectButton(position,
                new Bounds2D(paddedTextWidth, Layout.STD_TEXT_BUTTON_H),
                index, project, StippleEffect.get()::getContextIndex,
                StippleEffect.get()::setContextIndex);
    }

    @Override
    public void update(double deltaTime) {
        final boolean wasSelected = isSelected(), wasEdited = edited;

        super.update(deltaTime);

        edited = project.getSaveConfig().hasUnsavedChanges();

        final boolean change = wasSelected != isSelected() ||
                wasEdited != edited;

        if (change)
            updateImages();
    }

    private void updateImages() {
        final ThemeLogic tl = Settings.getTheme().logic;

        final boolean quickSelectable = index <= MAX_QUICK_SELECTABLE_INDEX;
        final int width = getWidth();
        final String text = project.getSaveConfig().
                getFormattedName(true, true);
        final TextButton base = TextButton.of(text, width,
                quickSelectable ? Alignment.RIGHT : Alignment.CENTER,
                ButtonType.STANDARD);

        image = tl.drawTextButton(base.sim(isSelected(), false));
        highlighted = tl.drawTextButton(base.sim(isSelected(), true));

        if (quickSelectable) {
            final int offset = -Layout.ICON_BUTTON_OFFSET_Y;
            final GameImage keyIcon = GraphicsUtils.loadNumkeyIcon(index + 1);

            image.draw(keyIcon, offset, offset);
            highlighted.draw(keyIcon, offset, offset);
            image.free();
            highlighted.free();
        }
    }

    @Override
    public void render(GameImage canvas) {
        draw(isHighlighted() && !isSelected() ? highlighted : image, canvas);
    }
}
