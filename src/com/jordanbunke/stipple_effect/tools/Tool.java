package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menu.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

public abstract class Tool {
    private GameImage icon, highlightedIcon, selectedIcon;

    public static Tool[] getAll() {
        return new Tool[] {
                Hand.get(), Zoom.get(),
                StipplePencil.get(), Pencil.get(),
                Brush.get(), ShadeBrush.get(), ScriptBrush.get(), Eraser.get(),
                GradientTool.get(), LineTool.get(), ShapeTool.get(),
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
        refreshIcons();
    }

    public final void refreshIcons() {
        this.icon = fetchIcon();

        this.highlightedIcon = GraphicsUtils.highlightIconButton(icon);

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
        return new MenuElementGrouping();
    }

    Coord2D getFirstOptionLabelPosition() {
        return new Coord2D(Layout.getToolOptionsBarPosition().x +
                Layout.CONTENT_BUFFER_PX, Layout.optionsBarTextY());
    }
}
