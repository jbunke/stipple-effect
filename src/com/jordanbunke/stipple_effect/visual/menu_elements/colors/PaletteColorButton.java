package com.jordanbunke.stipple_effect.visual.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.events.GameEvent;
import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.menu.menu_elements.SelectableMenuElement;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.Layout;
import com.jordanbunke.stipple_effect.utility.Permissions;
import com.jordanbunke.stipple_effect.utility.action.KeyShortcut;
import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PaletteColorButton extends SelectableMenuElement {
    private static final int INVALID = -1;

    private static final List<PaletteColorButton> colorButtons = new ArrayList<>();
    private static int moving, wouldBeIndex;
    private static Coord2D onClickPos, lastMousePos;

    private final Color color;
    private final Palette palette;
    private final int index;

    private Coord2D lockedPos;

    private ColorSelection selection;
    private boolean included;

    private GameImage nh, hi;
    private boolean highlighted;

    static {
        moving = INVALID;
        wouldBeIndex = INVALID;
    }

    public enum ColorSelection {
        PRIMARY, SECONDARY, NEITHER;

        GameImage getOverlay() {
            return switch (this) {
                case PRIMARY ->
                        GraphicsUtils.loadIcon(ResourceCodes.PRIMARY_SELECTION);
                case SECONDARY ->
                        GraphicsUtils.loadIcon(ResourceCodes.SECONDARY_SELECTION);
                case NEITHER -> GameImage.dummy();
            };
        }
    }

    public PaletteColorButton(
            final Coord2D position, final Color color, final Palette palette,
            final int index
    ) {
        super(position, Layout.PALETTE_DIMS, Anchor.LEFT_TOP, true);

        highlighted = false;

        this.color = color;
        this.palette = palette;
        this.index = index;
        selection = determineSelection();
        included = palette.isIncluded(color);

        updateAssets();
    }

    private String colorText() {
        return "R: " + color.getRed() +
                ", G: " + color.getGreen() +
                ", B: " + color.getBlue() +
                ", A: " + color.getAlpha() +
                "\n{Click + Drag} to resort" +
                "\n{Shift + Click} to " + (included ? "ex" : "in") + "clude" +
                "\n{Ctrl + Click} to remove from palette";
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
                ResourceCodes.PALETTE_BUTTON_BACKGROUND);

        base.fillRectangle(color, 0, 0,
                Layout.PALETTE_DIMS.width(),
                Layout.PALETTE_DIMS.height());
        base.draw(selection.getOverlay());

        if (!included)
            base.draw(GraphicsUtils.loadIcon(ResourceCodes.EXCLUDED_FROM_PALETTE));

        if (index == moving)
            base.draw(GraphicsUtils.loadIcon(ResourceCodes.MOVING_PALETTE_COLOR));

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
        final Coord2D mousePos = eventLogger.getAdjustedMousePosition();
        final boolean mouseInBounds = mouseIsWithinBounds(mousePos);

        highlighted = !isMoving() && (isSelected() || mouseInBounds);

        if (mouseInBounds) {
            for (GameEvent e : eventLogger.getUnprocessedEvents()) {
                if (e instanceof GameMouseEvent me) {
                    switch (me.action) {
                        case CLICK -> {
                            me.markAsProcessed();

                            if (KeyShortcut.areModKeysPressed(false, true, eventLogger))
                                palette.toggleInclusion(color);
                            else if (KeyShortcut.areModKeysPressed(true, false, eventLogger))
                                StippleEffect.get().removeColorFromPalette(color);
                            else
                                setSelectedColor();

                            return;
                        }
                        case DOWN -> {
                            me.markAsProcessed();

                            setAsMovingButton(mousePos);
                        }
                    }
                }
            }
        }

        if (isMoving()) {
            lastMousePos = mousePos;

            for (GameEvent e : eventLogger.getUnprocessedEvents()) {
                if (e instanceof GameMouseEvent me &&
                        me.matchesAction(GameMouseEvent.Action.UP)) {
                    me.markAsProcessed();

                    releaseMovingButton();
                    return;
                }
            }

            updatePosition();
        }
    }

    private void updatePosition() {
        final int DIM = Layout.PALETTE_DIMS.width(),
                bpl = PaletteContainer.getButtonsPerLine(),
                x = index % bpl, y = index / bpl;
        int deltaX = (int) Math.round(
                (lastMousePos.x - onClickPos.x) / (double) DIM),
                deltaY = (int) Math.round(
                        (lastMousePos.y - onClickPos.y) / (double) DIM);

        if (moving == index) {
            int xp = MathPlus.bounded(0, x + deltaX, bpl - 1),
                    yp = Math.max(0, y + deltaY);

            wouldBeIndex = MathPlus.bounded(
                    0, (yp * bpl) + xp, colorButtons.size() - 1);

            xp = wouldBeIndex % bpl;
            yp = wouldBeIndex / bpl;

            deltaX = (xp - x) * DIM;
            deltaY = (yp - y) * DIM;
        } else {
            final int adjustedIndex = index + (moving > index
                    ? (wouldBeIndex > index ? 0 : 1)
                    : (wouldBeIndex < index ? 0 : -1));

            final int xp = adjustedIndex % bpl, yp = adjustedIndex / bpl;

            deltaX = (xp - x) * DIM;
            deltaY = (yp - y) * DIM;
        }

        setX(lockedPos.x + deltaX);
        setY(lockedPos.y + deltaY);
    }

    private static void releaseMovingButton() {
        final PaletteColorButton cb = colorButtons.get(moving);

        if (wouldBeIndex != cb.index) {
            cb.palette.moveToIndex(cb.color, wouldBeIndex);

            moving = INVALID;
            wouldBeIndex = INVALID;

            PaletteContainer.get().makeContainer(true);
        } else {
            moving = INVALID;
            wouldBeIndex = INVALID;
        }
    }

    private void setAsMovingButton(final Coord2D mousePos) {
        onClickPos = mousePos;
        moving = index;
        wouldBeIndex = index;

        updateAssets();

        for (final PaletteColorButton cb : colorButtons)
            cb.lockedPos = cb.getPosition();
    }

    private void setSelectedColor() {
        StippleEffect.get().setSelectedColor(color, ColorMath.LastHSVEdit.NONE);
    }

    @Override
    public void choose() {
        setSelectedColor();
    }

    @Override
    public void incrementX(final int deltaX) {
        if (isMoving())
            lockedPos = lockedPos.displace(deltaX, 0);
        else
            super.incrementX(deltaX);
    }

    @Override
    public void incrementY(final int deltaY) {
        if (isMoving())
            lockedPos = lockedPos.displace(0, deltaY);
        else
            super.incrementY(deltaY);
    }

    public static boolean isMoving() {
        return moving != INVALID;
    }

    public static void resetColorButtons() {
        colorButtons.clear();
    }

    public static void addColorButton(final PaletteColorButton cb) {
        colorButtons.add(cb);
    }
}
