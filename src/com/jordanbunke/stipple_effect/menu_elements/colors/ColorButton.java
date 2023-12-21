package com.jordanbunke.stipple_effect.menu_elements.colors;

import com.jordanbunke.delta_time.debug.GameDebugger;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.menu_elements.button.MenuButton;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.awt.*;

public class ColorButton extends MenuButton {
    private final int index;
    private Color cAtLastCheck;

    private GameImage nh, h;

    public ColorButton(final Coord2D position, final int index) {
        super(position, new Coord2D(Constants.COLOR_TEXTBOX_W,
                        Constants.STD_TEXT_BUTTON_H), Anchor.CENTRAL_TOP,
                true, () -> StippleEffect.get().setColorIndex(index));

        this.index = index;
        cAtLastCheck = fetchColor();

        updateAssets();
    }

    private String generateButtonText() {
        final String r = Integer.toHexString(cAtLastCheck.getRed());
        final String g = Integer.toHexString(cAtLastCheck.getGreen());
        final String b = Integer.toHexString(cAtLastCheck.getBlue());

        return "#" + (r.length() == 1 ? ("0") + r : r) +
                (g.length() == 1 ? ("0") + g : g) +
                (b.length() == 1 ? ("0") + b : b);
    }

    private void updateAssets() {
        nh = GraphicsUtils.drawTextButton(getWidth(), generateButtonText(),
                index == StippleEffect.get().getColorIndex(), cAtLastCheck);
        h = GraphicsUtils.drawHighlightedButton(nh);
    }

    private Color fetchColor() {
        return index == 0
                ? StippleEffect.get().getPrimary()
                : StippleEffect.get().getSecondary();
    }

    @Override
    public void update(double deltaTime) {
        final Color c = fetchColor();

        if (!c.equals(cAtLastCheck)) {
            cAtLastCheck = c;
            updateAssets();
        }
    }

    @Override
    public void render(final GameImage canvas) {
        draw(isHighlighted() ? h : nh, canvas);
    }

    @Override
    public void debugRender(GameImage canvas, GameDebugger debugger) {

    }
}
