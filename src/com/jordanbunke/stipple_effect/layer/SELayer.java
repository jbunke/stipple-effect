package com.jordanbunke.stipple_effect.layer;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;

public final class SELayer {
    private final GameImage content;
    private final double opacity;
    private final boolean enabled;
    private final String name;

    public SELayer(final GameImage content) {
        this(content, Constants.OPAQUE, true, giveLayerDefaultName());
    }

    public SELayer(final int imageWidth, final int imageHeight) {
        this(new GameImage(imageWidth, imageHeight), Constants.OPAQUE,
                true, giveLayerDefaultName());
    }

    public SELayer(
            final GameImage content, final double opacity,
            final boolean enabled, final String name
    ) {
        this.content = content;
        this.opacity = opacity;
        this.enabled = enabled;
        this.name = name;
    }

    private static String giveLayerDefaultName() {
        try {
            final int size = StippleEffect.get().getContext().getState().getLayers().size();
            return "Layer " + (size + 1);
        } catch (NullPointerException e) {
            return "Base Layer";
        }
    }

    public SELayer duplicate() {
        return new SELayer(content, opacity, enabled, name + " (copy)");
    }

    public SELayer returnEdited(final GameImage edit) {
        final GameImage composed = new GameImage(content);
        composed.draw(edit);

        return new SELayer(composed.submit(), opacity, enabled, name);
    }

    public SELayer returnEdited(final boolean[][] eraserMask) {
        final GameImage after = new GameImage(content);

        for (int x = 0; x < after.getWidth(); x++) {
            for (int y = 0; y < after.getHeight(); y++) {
                if (eraserMask[x][y])
                    after.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
            }
        }

        return new SELayer(after.submit(), opacity, enabled, name);
    }

    public SELayer returnChangedOpacity(final double opacity) {
        return new SELayer(content, opacity, enabled, name);
    }

    // TODO - enable / disable implementation

    public SELayer returnRenamed(final String name) {
        return new SELayer(content, opacity, enabled, name);
    }

    public GameImage getContent() {
        return content;
    }

    public GameImage renderContent() {
        final GameImage render = new GameImage(content.getWidth(), content.getHeight());

        for (int x = 0; x < render.getWidth(); x++) {
            for (int y = 0; y < render.getHeight(); y++) {
                final Color c = ImageProcessing.colorAtPixel(content, x, y);
                final Color cp = new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(c.getAlpha() * opacity));
                render.dot(cp, x, y);
            }
        }

        return render.submit();
    }

    public double getOpacity() {
        return opacity;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
