package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.state.ProjectState;

import static com.jordanbunke.stipple_effect.visual.menu_elements.CelButton.*;

public class CelSelection {
    public final int celWidth, celHeight, frameRange, layersRange;
    private final GameImage[][] cels;

    public CelSelection(final SEContext c) {
        final int f0 = getFramesFrom(), f1 = getFramesTo(),
                l0 = getLayersFrom(), l1 = getLayersTo();

        final int fMin = Math.min(f0, f1), fMax = Math.max(f0, f1),
                lMin = Math.min(l0, l1), lMax = Math.max(l0, l1);

        final ProjectState s = c.getState();

        celWidth = s.getImageWidth();
        celHeight = s.getImageHeight();

        frameRange = (fMax - fMin) + 1;
        layersRange = (lMax - lMin) + 1;

        cels = new GameImage[layersRange][frameRange];

        for (int l = lMin; l <= lMax; l++)
            for (int f = fMin; f <= fMax; f++)
                cels[l][f] = s.getLayers().get(l).getFrame(f);
    }

    public boolean isCompatible(final ProjectState state) {
        return isSizeCompatible(state) && areLayersCompatible(state);
    }

    private boolean isSizeCompatible(final ProjectState state) {
        return celWidth == state.getImageWidth() &&
                celHeight == state.getImageHeight();
    }

    private boolean areLayersCompatible(final ProjectState state) {
        return layersRange <= (state.getLayers().size() -
                state.getLayerEditIndex());
    }

    public int framesToAppend(final ProjectState state) {
        final int frameDeficit = (state.getFrameCount() -
                state.getFrameIndex()) - frameRange;

        return Math.max(0, frameDeficit);
    }

    public GameImage getCel(final int layerIndex, final int frameIndex) {
        return cels[layerIndex][frameIndex];
    }
}
