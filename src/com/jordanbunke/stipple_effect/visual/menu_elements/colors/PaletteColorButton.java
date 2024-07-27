package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.events.Key;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.SelectableMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.IconCodes;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.Permissions;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.awt.*;
import java.util.List;

public class PaletteColorButton extends SelectableMenuElement {
    private final Color color;
    private final Palette palette;

    private ColorSelection selection;
    private boolean included;

    private GameImage nh, hi;
    private boolean highlighted;

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
            final Coord2D position, final Color color, final Palette palette
    ) {
        super(position, Layout.PALETTE_DIMS, Anchor.LEFT_TOP, true);

        highlighted = false;

        this.color = color;
        this.palette = palette;
        selection = determineSelection();
        included = palette.isIncluded(color);

        updateAssets();
    }

    private String colorText() {
        return "R: " + color.getRed() +
                ", G: " + color.getGreen() +
                ", B: " + color.getBlue() +
                "\nOpacity: " + color.getAlpha() +
                "\n{Shift + Click} to " + (included ? "ex" : "in") + "clude";
    }

    @Override
    public void update(double deltaTime) {
        if (highlighted && Permissions.isCursorFree())
            StippleEffect.get().sendToolTipUpdate(
                    Constants.COLOR_TOOL_TIP_PREFIX + colorText());

        final ColorSelection oldSelection = selection;
        selection = determineSelection();

        final boolean wasIncluded = included;
        included = palette.isIncluded(color);

        if (oldSelection != selection || wasIncluded != included)
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
                Layout.PALETTE_DIMS.width(),
                Layout.PALETTE_DIMS.height());
        base.draw(selection.getOverlay());

        if (!included)
            base.draw(GraphicsUtils.loadIcon(IconCodes.EXCLUDED_FROM_PALETTE));

        nh = base.submit();
        hi = Settings.getTheme().logic.highlightButton(nh);
    }

    @Override
    public void render(final GameImage canvas) {
        draw(highlighted ? hi : nh, canvas);
    }

    @Override
    public void debugRender(final GameImage canvas, final GameDebugger debugger) {

    }

    @Override
    public void process(final InputEventLogger eventLogger) {
        final boolean mouseInBounds = mouseIsWithinBounds(
                eventLogger.getAdjustedMousePosition());

        highlighted = isSelected() || mouseInBounds;

        if (!mouseInBounds)
            return;

        final List<GameEvent> unprocessed = eventLogger.getUnprocessedEvents();
        for (GameEvent e : unprocessed) {
            if (e instanceof GameMouseEvent mouseEvent &&
                    mouseEvent.matchesAction(GameMouseEvent.Action.CLICK)) {
                mouseEvent.markAsProcessed();

                if (eventLogger.isPressed(Key.SHIFT))
                    palette.toggleInclusion(color);
                else
                    setSelectedColor();

                return;
            }
        }
    }

    private void setSelectedColor() {
        StippleEffect.get().setSelectedColor(color, ColorMath.LastHSVEdit.NONE);
    }

    @Override
    public void choose() {
        setSelectedColor();
    }
}
