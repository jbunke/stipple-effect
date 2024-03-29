package com.jordanbunke.stipple_effect.visual;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.ResourceLoader;

import java.nio.file.Path;

public class SplashLoader {
    private static final Path SPLASH_FOLDER = Path.of("splash");

    private static final int ANIM_FRAMES = 25, SCALE_UP = 3;
    private static final String BASE_NAME = "logo_anim_";

    public static GameImage[] loadAnimationFrames() {
        final GameImage[] frames = new GameImage[ANIM_FRAMES];

        for (int i = 0; i < ANIM_FRAMES; i++) {
            final Path framePath = SPLASH_FOLDER.resolve(BASE_NAME + i + ".png");

            frames[i] = ImageProcessing.scale(ResourceLoader
                    .loadImageResource(framePath), SCALE_UP);
        }

        return frames;
    }
}
