package com.jordanbunke.stipple_effect.state;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.context.SEContext;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectState {
    private boolean checkpoint;

    private final int imageWidth, imageHeight;

    // FRAME
    private final int frameCount;
    private int frameIndex;

    // LAYER
    private final List<SELayer> layers;
    private int layerEditIndex;

    // SELECTION
    private final Set<Coord2D> selection;

    public ProjectState(final int imageWidth, final int imageHeight) {
        this(imageWidth, imageHeight, new ArrayList<>(List.of(
                new SELayer(imageWidth, imageHeight))), 0, 1, 0, new HashSet<>());
    }

    public ProjectState(
            final int imageWidth, final int imageHeight,
            final List<SELayer> layers, final int layerEditIndex,
            final int frameCount, final int frameIndex,
            final Set<Coord2D> selection
    ) {
        this(imageWidth, imageHeight, layers, layerEditIndex,
                frameCount, frameIndex, selection, true);
    }

    public ProjectState(
            final int imageWidth, final int imageHeight,
            final List<SELayer> layers, final int layerEditIndex,
            final int frameCount, final int frameIndex,
            final Set<Coord2D> selection,
            final boolean checkpoint
    ) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        this.layers = layers;
        this.layerEditIndex = layerEditIndex;

        this.frameCount = frameCount;
        this.frameIndex = Math.max(0, Math.min(frameIndex, frameCount - 1));

        this.selection = selection;

        this.checkpoint = checkpoint;
    }

    public GameImage draw(final boolean includeOnionSkins, final int frameIndex) {
        final GameImage image = new GameImage(imageWidth, imageHeight);

        for (SELayer layer : layers) {
            if (layer.isEnabled()) {
                // onion skin previous
                if (includeOnionSkins && layer.getOnionSkinMode()
                        .doPrevious() && frameIndex > 0)
                    image.draw(layer.renderFrame(frameIndex - 1,
                            layer.getOpacity() * Constants.ONION_SKIN_OPACITY));

                // this layer
                if (layer.getOpacity() == Constants.OPAQUE)
                    image.draw(layer.getFrame(frameIndex));
                else
                    image.draw(layer.renderFrame(frameIndex));

                // onion skin next
                if (includeOnionSkins && layer.getOnionSkinMode()
                        .doNext() && frameIndex + 1 < frameCount)
                    image.draw(layer.renderFrame(frameIndex + 1,
                            layer.getOpacity() * Constants.ONION_SKIN_OPACITY));
            }
        }

        return image.submit();
    }

    public void setLayerEditIndex(final int layerEditIndex) {
        if (this.layerEditIndex != layerEditIndex &&
                layerEditIndex >= 0 && layerEditIndex < layers.size()) {
            this.layerEditIndex = layerEditIndex;
            StippleEffect.get().rebuildLayersMenu();
        }
    }

    public void markAsCheckpoint(
            final boolean processLastConsequence, final SEContext context
    ) {
        this.checkpoint = true;

        if (processLastConsequence)
            context.getStateManager().processLastConsequence();
    }

    // PRECONDITIONS

    // layer preconditions
    public boolean canAddLayer() {
        return layers.size() < Constants.MAX_NUM_LAYERS;
    }

    public boolean canRemoveLayer() {
        return layers.size() > 1;
    }

    public boolean canMoveLayerDown() {
        return layerEditIndex > 0;
    }

    public boolean canMoveLayerUp() {
        return layerEditIndex + 1 < layers.size();
    }

    // frame preconditions
    public boolean canAddFrame() {
        return frameCount < Constants.MAX_NUM_FRAMES;
    }

    public boolean canRemoveFrame() {
        return frameCount > 1;
    }


    // getters
    public boolean isCheckpoint() {
        return checkpoint;
    }

    public Set<Coord2D> getSelection() {
        return selection;
    }

    public SELayer getEditingLayer() {
        return layers.get(layerEditIndex);
    }

    public int getLayerEditIndex() {
        return layerEditIndex;
    }

    public List<SELayer> getLayers() {
        return layers;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getFrameIndex() {
        return frameIndex;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }


    // frame index manipulation
    public void setFrameIndex(final int frameIndex) {
        if (this.frameIndex != frameIndex &&
                frameIndex >= 0 && frameIndex < frameCount) {
            this.frameIndex = frameIndex;

            StippleEffect.get().rebuildFramesMenu();
        }
    }

    public void nextFrame() {
        frameIndex++;

        if (frameIndex >= frameCount)
            frameIndex = 0;

        StippleEffect.get().rebuildFramesMenu();
    }

    public void previousFrame() {
        frameIndex--;

        if (frameIndex < 0)
            frameIndex = frameCount - 1;

        StippleEffect.get().rebuildFramesMenu();
    }
}
