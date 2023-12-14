package com.jordanbunke.stipple_effect.state;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class ProjectState {
    private boolean checkpoint;

    private final int imageWidth, imageHeight;

    // LAYER
    private final List<SELayer> layers;
    private int layerEditIndex;

    public ProjectState(final GameImage image) {
        this(image.getWidth(), image.getHeight(), new ArrayList<>(List.of(
                new SELayer(image))), 0);
    }

    public ProjectState(final int imageWidth, final int imageHeight) {
        this(imageWidth, imageHeight, new ArrayList<>(List.of(
                new SELayer(imageWidth, imageHeight))), 0);
    }

    public ProjectState(
            final int imageWidth, final int imageHeight,
            final List<SELayer> layers, final int layerEditIndex
    ) {
        this(imageWidth, imageHeight, layers, layerEditIndex, true);
    }

    public ProjectState(
            final int imageWidth, final int imageHeight,
            final List<SELayer> layers, final int layerEditIndex,
            final boolean checkpoint
    ) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        this.layers = layers;
        this.layerEditIndex = layerEditIndex;

        this.checkpoint = checkpoint;
    }

    public GameImage draw() {
        final GameImage image = new GameImage(imageWidth, imageHeight);

        for (SELayer layer : layers) {
            if (layer.isEnabled()) {
                if (layer.getOpacity() == Constants.OPAQUE)
                    image.draw(layer.getContent());
                else
                    image.draw(layer.renderContent());
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

    public void markAsCheckpoint(final boolean processLastConsequence) {
        this.checkpoint = true;

        if (processLastConsequence)
            StippleEffect.get().getContext().getStateManager()
                    .processLastConsequence();
    }

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

    public boolean isCheckpoint() {
        return checkpoint;
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

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
}
