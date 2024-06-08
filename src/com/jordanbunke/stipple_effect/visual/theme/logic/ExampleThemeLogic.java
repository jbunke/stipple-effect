package com.jordanbunke.stipple_effect.visual.theme.logic;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.awt.*;

public final class ExampleThemeLogic extends ThemeLogic {
    @Override
    Color transformIconPixel(GameImage asset, int x, int y) {
        final Color orig = asset.getColorAt(x, y);
        return GraphicsUtils.greyscale(orig);
    }

    @Override
    public GameImage unclickableIcon(GameImage baseIcon) {
        return pixelWiseTransformation(baseIcon,
                c -> new Color(c.getRed(), 0, 0, c.getAlpha()));
    }

    @Override
    public GameImage[] loadSplash() {
        return new DefaultThemeLogic().loadSplash();
    }
}
