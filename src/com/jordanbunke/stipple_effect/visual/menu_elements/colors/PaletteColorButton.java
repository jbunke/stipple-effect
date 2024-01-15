package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.button.MenuButton;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.awt.*;

public class PaletteColorButton extends MenuButton {
    private final Color color;

    private ColorSelection selection;

    private GameImage nh, hi;

    public enum ColorSelection {
        PRIMARY, SECONDARY, NEITHER;

        GameImage getOverlay() {
            return switch (this) {
                case PRIMARY ->
                        GraphicsUtils.loadIcon(IconCodes.PRIMARY_SELECTION);
                case SECONDARY ->
                        GraphicsUtils.loadIcon(IconCodes.SECONDARY_SELECTION);
                case NEITHER -> GameImage.dummy();
            };
        }
    }

    public PaletteColorButton(
            final Coord2D position, final Color color
    ) {
        super(position, Layout.PALETTE_DIMS, Anchor.LEFT_TOP,
                true, () -> StippleEffect.get().setSelectedColor(color));

        this.color = color;
        selection = determineSelection();

        updateAssets();
    }

    private String colorText() {
        return "R: " + color.getRed() +
                ", G: " + color.getGreen() +
                ", B: " + color.getBlue() +
                "\nOpacity: " + color.getAlpha();
    }

    @Override
    public void update(double deltaTime) {
        if (isHighlighted())
            StippleEffect.get().sendToolTipUpdate(
                    Constants.COLOR_TOOL_TIP_PREFIX + colorText());

        final ColorSelection was = selection;

        selection = determineSelection();

        if (was != selection)
            updateAssets();
    }

    private ColorSelection determineSelection() {
        if (StippleEffect.get().getPrimary().equals(color))
            return ColorSelection.PRIMARY;
        else if (StippleEffect.get().getSecondary().equals(color))
            return ColorSelection.SECONDARY;

        return ColorSelection.NEITHER;
    }

    private void updateAssets() {
        final GameImage base = GraphicsUtils.loadIcon(
                IconCodes.PALETTE_BUTTON_BACKGROUND);

        base.fillRectangle(color, 0, 0,
                Layout.PALETTE_DIMS.x, Layout.PALETTE_DIMS.y);
        base.draw(selection.getOverlay(), 0, 0);

        nh = base.submit();
        hi = GraphicsUtils.drawHighlightedButton(nh);
    }

    @Override
    public void render(final GameImage canvas) {
        draw(isHighlighted() ? hi : nh, canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }
}
