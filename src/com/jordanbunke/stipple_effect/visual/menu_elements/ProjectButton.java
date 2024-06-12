package com.jordanbunke.stipple_effect.visual.menu_elements;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProjectButton extends SelectableListItemButton {
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
        edited = project.projectInfo.hasUnsavedChanges();

        updateImages();
    }

    public static ProjectButton make(
            final Coord2D position, final int index
    ) {
        final SEContext project = StippleEffect.get().getContexts().get(index);
        final String maxText = "* " + project.projectInfo
                .getFormattedName(false, true);

        // placeholder calculation
        final int paddedTextWidth = GraphicsUtils.uiText(SEColors.black())
                .addText(maxText).build().draw().getWidth() +
                Layout.PROJECT_NAME_BUTTON_PADDING_W;

        return new ProjectButton(position,
                new Bounds2D(paddedTextWidth, Layout.STD_TEXT_BUTTON_H),
                index, project, () -> StippleEffect.get().getContextIndex(),
                s -> StippleEffect.get().setContextIndex(s));
    }

    @Override
    public void update(double deltaTime) {
        final boolean wasSelected = isSelected(), wasEdited = edited;

        super.update(deltaTime);

        edited = project.projectInfo.hasUnsavedChanges();

        final boolean change = wasSelected != isSelected() ||
                wasEdited != edited;

        if (change)
            updateImages();
    }

    private void updateImages() {
        image = GraphicsUtils.drawTextButton(getWidth(),
                project.projectInfo.getFormattedName(true, true),
                isSelected());
        highlighted = GraphicsUtils.highlightButton(image);
    }

    @Override
    public void render(GameImage canvas) {
        draw(isHighlighted() && !isSelected() ? highlighted : image, canvas);
    }
}
