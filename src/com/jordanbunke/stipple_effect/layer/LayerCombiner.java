package com.jordanbunke.stipple_effect.layer;

import com.jordanbunke.delta_time.image.GameImage;

public class LayerCombiner {
    public static SELayer combine(final SELayer above, final SELayer below) {
        final GameImage content = new GameImage(below.getContent());
        content.draw(above.renderContent());
        return new SELayer(content.submit(), below.getOpacity(), below.isEnabled());
    }
}
