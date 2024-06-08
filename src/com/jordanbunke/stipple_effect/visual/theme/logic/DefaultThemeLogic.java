package com.jordanbunke.stipple_effect.visual.theme.logic;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.ResourceLoader;

import java.nio.file.Path;

public final class DefaultThemeLogic extends ThemeLogic {
    private static final Path SPLASH_FOLDER = Path.of("splash");

    private static final int SPLASH_FRAMES = 25, SPLASH_SCALE_UP = 3;
    private static final String SPLASH_FRAME_BASE = "logo_anim_";


    @Override
    public GameImage transformIcon(final GameImage asset) {
        return asset;
    }

    @Override
    public GameImage[] loadSplash() {
        final GameImage[] frames = new GameImage[SPLASH_FRAMES];

        for (int i = 0; i < SPLASH_FRAMES; i++) {
            final Path framePath = SPLASH_FOLDER.resolve(
                    SPLASH_FRAME_BASE + i + ".png");

            frames[i] = ImageProcessing.scale(
                    ResourceLoader.loadImageResource(framePath),
                    SPLASH_SCALE_UP);
        }

        return frames;
    }
}
