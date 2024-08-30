package com.jordanbunke.stipple_effect.layer;

import com.jordanbunke.delta_time.image.GameImage;

import java.util.ArrayList;
import java.util.List;

public class LayerHelper {
    public static SELayer merge(
            final SELayer above, final SELayer below,
            final int frameIndex, final int frameCount
    ) {
        final List<GameImage> frames = new ArrayList<>();

        for (int i = 0; i < frameCount; i++) {
            final GameImage frame = new GameImage(below.getFrame(i));
            frame.draw(above.getRender(i));

            frames.add(frame.submit());
        }

        // if below layer is frame-linked, merged layer should form linked
        // content from above layer on selected frame
        final GameImage frameLinkedContent = frames.get(frameIndex);

        // override frame linking on below layer if above layer is unlinked
        // and frameCount > 1
        final boolean framesLinked = below.areCelsLinked() &&
                (above.areCelsLinked() || frameCount == 1);

        return new SELayer(frames, frameLinkedContent, below.getOpacity(),
                below.isEnabled(), framesLinked, below.isOnionSkinOn(),
                below.getOnionSkin(), below.getName());
    }
}
