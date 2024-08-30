package com.jordanbunke.stipple_effect.layer;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.visual.theme.SEColors;
import com.jordanbunke.stipple_effect.visual.theme.logic.ThemeLogic;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

import static com.jordanbunke.stipple_effect.utility.math.ColorMath.*;

public final class OnionSkin {
    public static final int DEF_LOOK = 1, MIN_LOOK = 0, MAX_LOOK = 6;
    public static final double RED = 0d, GREEN = 1 / 3d,
            DEF_FADE = 0.5, MIN_FADE = 0.1, MAX_FADE = 0.8;

    private static SkinType dSkinType;
    private static double dHueBack, dHueForward, dFadeFactor;
    private static int dLookBack, dLookForward;
    private static boolean dUnder;

    public final SkinType skinType;
    public final double hueBack, hueForward, fadeFactor;
    public final int lookBack, lookForward;
    public final boolean under;

    public enum SkinType {
        SIMPLE, OUTLINE, TINTED
    }

    static {
        reset();
    }

    private OnionSkin() {
        skinType = dSkinType;

        hueBack = dHueBack;
        hueForward = dHueForward;

        fadeFactor = dFadeFactor;
        lookBack = dLookBack;
        lookForward = dLookForward;

        under = dUnder;
    }

    public static void reset() {
        dSkinType = SkinType.SIMPLE;
        dHueBack = RED;
        dHueForward = GREEN;
        dFadeFactor = DEF_FADE;
        dLookBack = DEF_LOOK;
        dLookForward = DEF_LOOK;

        dUnder = true;
    }

    public static OnionSkin trivial() {
        reset();
        return load();
    }

    public static OnionSkin load() {
        return new OnionSkin();
    }

    public void prep() {
        dSkinType = skinType;

        dHueBack = hueBack;
        dHueForward = hueForward;

        dFadeFactor = fadeFactor;
        dLookBack = lookBack;
        dLookForward = lookForward;

        dUnder = under;
    }

    GameImage drawBase(final GameImage cel, final boolean back) {
        final double hue = back ? hueBack : hueForward;

        return switch (skinType) {
            case SIMPLE -> cel;
            case TINTED -> algo(c -> tint(c, hue), new HashMap<>(), cel, null);
            case OUTLINE -> outline(cel, hue);
        };
    }

    private static Color tint(final Color c, final double hue) {
        return fromHSV(hue, 1d, rgbToValue(c), c.getAlpha());
    }

    private static GameImage outline(final GameImage source, final double hue) {
        final int w = source.getWidth(), h = source.getHeight();
        final Color found = fromHSV(hue, 1d, 1d, Constants.RGBA_SCALE),
                notFound = SEColors.transparent();

        return ThemeLogic.contextualTransformation(source, (asset, x, y) -> {
            final Color orig = asset.getColorAt(x, y);

            if (orig.getAlpha() == 0)
                return orig;
            else {
                boolean transparentNeighbour = false;

                outer:
                for (int sx = Math.max(x - 1, 0); sx < Math.min(w, x + 2); sx++)
                    for (int sy = Math.max(y - 1, 0); sy < Math.min(h, y + 2); sy++) {
                        if (sx == x && sy == y)
                            continue;

                        if (asset.getColorAt(sx, sy).getAlpha() == 0) {
                            transparentNeighbour = true;
                            break outer;
                        }
                    }

                return transparentNeighbour ? found : notFound;
            }
        });
    }

    void populateOnionSkins(
            final int fc, final List<GameImage> host,
            final GameImage[] backBases, final GameImage[] forwardBases
    ) {
        final GameImage ref = backBases[0];
        final int w = ref.getWidth(), h = ref.getHeight(),
                range = Math.max(lookBack, lookForward);

        for (int f = 0; f < fc; f++) {
            final GameImage onionSkin = new GameImage(w, h);

            for (int prox = range; prox > 0; prox--) {
                final boolean doBack = f - prox >= 0 && prox <= lookBack,
                        doForward = f + prox < fc && prox <= lookForward;
                final int fBack = f - prox, fForward = f + prox;
                final double opacity = Math.pow(fadeFactor, prox);

                if (doBack)
                    onionSkin.draw(algo(c -> fade(c, opacity),
                            new HashMap<>(), backBases[fBack], null));

                if (doForward)
                    onionSkin.draw(algo(c -> fade(c, opacity),
                            new HashMap<>(), forwardBases[fForward], null));
            }

            host.add(onionSkin.submit());
        }
    }

    private static Color fade(final Color c, final double opacity) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(),
                (int) (c.getAlpha() * opacity));
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof OnionSkin os && skinType == os.skinType &&
                hueBack == os.hueBack && hueForward == os.hueForward &&
                fadeFactor == os.fadeFactor && under == os.under &&
                lookBack == os.lookBack && lookForward == os.lookForward;
    }

    @Override
    public int hashCode() {
        return ((MAX_LOOK + 1) * lookBack) + lookForward;
    }

    public static SkinType getDSkinType() {
        return dSkinType;
    }

    public static void setDSkinType(final SkinType dSkinType) {
        OnionSkin.dSkinType = dSkinType;
    }

    public static double getDHueBack() {
        return dHueBack;
    }

    public static void setDHueBack(final double dHueBack) {
        OnionSkin.dHueBack = dHueBack;
    }

    public static double getDHueForward() {
        return dHueForward;
    }

    public static void setDHueForward(final double dHueForward) {
        OnionSkin.dHueForward = dHueForward;
    }

    public static double getDFadeFactor() {
        return dFadeFactor;
    }

    public static void setDFadeFactor(final double dFadeFactor) {
        OnionSkin.dFadeFactor = dFadeFactor;
    }

    public static int getDLookBack() {
        return dLookBack;
    }

    public static void setDLookBack(final int dLookBack) {
        OnionSkin.dLookBack = dLookBack;
    }

    public static int getDLookForward() {
        return dLookForward;
    }

    public static void setDLookForward(final int dLookForward) {
        OnionSkin.dLookForward = dLookForward;
    }

    public static boolean isDUnder() {
        return dUnder;
    }

    public static void setDUnder(final boolean dUnder) {
        OnionSkin.dUnder = dUnder;
    }
}
