package com.jordanbunke.stipple_effect.layer;

import com.jordanbunke.delta_time.image.GameImage;

import java.util.ArrayList;
import java.util.List;

public class LayerCombiner {
    public static SELayer combine(
            final SELayer above, final SELayer below,
            final int frameCount
    ) {
        final List<GameImage> frames = new ArrayList<>();

        for (int i = 0; i < frameCount; i++) {
            final GameImage frame = new GameImage(below.getFrame(i));
            frame.draw(above.renderFrame(i));

            frames.add(frame.submit());
        }

        return new SELayer(frames, below.getOpacity(),
                below.isEnabled(), below.getName());
    }
}
