package com.jordanbunke.stipple_effect.layer;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.stipple_effect.state.ImageState;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;

public final class SELayer {
    private GameImage content;
    private double opacity;
    private boolean enabled;

    public SELayer(final int imageWidth, final int imageHeight) {
        this.content = new GameImage(imageWidth, imageHeight);
        this.opacity = Constants.OPAQUE;
        this.enabled = true;
    }

    public SELayer(
            final GameImage content, final double opacity, final boolean enabled
    ) {
        this.content = content;
        this.opacity = opacity;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }
}
