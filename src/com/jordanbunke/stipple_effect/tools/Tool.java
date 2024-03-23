package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;

public abstract class Tool {
    private final GameImage icon, highlightedIcon, selectedIcon;

    public static Tool[] getAll() {
        return new Tool[] {
                Hand.get(), Zoom.get(),
                StipplePencil.get(), Pencil.get(),
                Brush.get(), ShadeBrush.get(), Eraser.get(),
                GradientTool.get(), LineTool.get(),
                TextTool.get(), Fill.get(), ColorPicker.get(),
                Wand.get(), BrushSelect.get(), BoxSelect.get(), PolygonSelect.get(),
                MoveSelection.get(), PickUpSelection.get()
        };
    }

    public static boolean canMoveSelectionBounds(
            final Tool tool
    ) {
        return tool.equals(MoveSelection.get()) ||
                tool.equals(BrushSelect.get()) ||
                tool.equals(BoxSelect.get()) ||
                tool.equals(PolygonSelect.get());
    }

    Tool() {
        this.icon = fetchIcon();

        this.highlightedIcon = new GameImage(GraphicsUtils.HIGHLIGHT_OVERLAY);
        highlightedIcon.draw(icon);
        highlightedIcon.free();

        this.selectedIcon = new GameImage(GraphicsUtils.SELECT_OVERLAY);
        selectedIcon.draw(icon);
        selectedIcon.free();
    }

    public abstract String getName();

    // stubs that can be extended for custom behaviours
    public void onMouseDown(final SEContext context, final GameMouseEvent me) {

    }

    public void onMouseUp(final SEContext context, final GameMouseEvent me) {

    }

    public void onClick(final SEContext context, final GameMouseEvent me) {

    }

    public void update(final SEContext context, final Coord2D mousePosition) {

    }

    public String convertNameToFilename() {
        return getName().replace(" ", "_").toLowerCase();
    }

    private GameImage fetchIcon() {
        return GraphicsUtils.loadIcon(convertNameToFilename());
    }

    public String getCursorCode() {
        return convertNameToFilename();
    }

    public String getBottomBarText() {
        return getName();
    }

    public GameImage getIcon() {
        return icon;
    }

    public GameImage getHighlightedIcon() {
        return highlightedIcon;
    }

    public GameImage getSelectedIcon() {
        return selectedIcon;
    }

    public boolean hasToolContentPreview() {
        return false;
    }

    public boolean previewScopeIsGlobal() {
        return false;
    }

    public GameImage getToolContentPreview() {
        return GameImage.dummy();
    }

    public boolean hasToolOptionsBar() {
        return false;
    }

    public MenuElementGrouping buildToolOptionsBar() {
        final int optionsBarTextX = Layout.getToolOptionsBarPosition().x +
                        Layout.CONTENT_BUFFER_PX;

        // options label
        final TextLabel optionsLabel = TextLabel.make(
                new Coord2D(optionsBarTextX, Layout.optionsBarTextY()),
                "Tool Options", Constants.WHITE);

        return new MenuElementGrouping(optionsLabel);
    }
}
