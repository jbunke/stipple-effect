package com.jordanbunke.stipple_effect.layer;

import com.jordanbunke.delta_time.image.GameImage;

import java.util.ArrayList;
import java.util.List;

public class LayerHelper {
    public static SELayer merge(
            final SELayer above, final SELayer below,
            final int frameIndex, final int frameCount
    ) {
        final List<GameImage> cels = new ArrayList<>();

        for (int i = 0; i < frameCount; i++) {
            final GameImage cel = new GameImage(below.getCel(i));
            cel.draw(above.getRender(i));

            cels.add(cel.submit());
        }

        // if below layer is linked, merged layer should form linked
        // content from above layer on selected frame
        final GameImage linkedContent = cels.get(frameIndex);

        // override cel linking on below layer if above layer is unlinked
        // and frameCount > 1
        final boolean celsLinked = below.areCelsLinked() &&
                (above.areCelsLinked() || frameCount == 1);

        return new SELayer(cels, linkedContent, below.getOpacity(),
                below.isEnabled(), celsLinked, below.isOnionSkinOn(),
                below.getOnionSkin(), below.getName());
    }
}
