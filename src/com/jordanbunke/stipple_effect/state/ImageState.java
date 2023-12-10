package com.jordanbunke.stipple_effect.state;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ImageState {
    private final int imageWidth, imageHeight;

    // LAYER
    private final List<SELayer> layers;
    private int layerEditIndex;

    public ImageState(final GameImage image) {
        this(image.getWidth(), image.getHeight(), new ArrayList<>(List.of(
                new SELayer(image, Constants.OPAQUE, true))), 0);
    }

    public ImageState(final int imageWidth, final int imageHeight) {
        this(imageWidth, imageHeight, new ArrayList<>(List.of(
                new SELayer(imageWidth, imageHeight))), 0);
    }

    public ImageState(
            final int imageWidth, final int imageHeight,
            final List<SELayer> layers, final int layerEditIndex
    ) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        this.layers = layers;
        this.layerEditIndex = layerEditIndex;
    }

    public GameImage draw() {
        final GameImage image = new GameImage(imageWidth, imageHeight);

        for (int x = 0; x < imageWidth; x += Constants.CHECKER_INCREMENT) {
            for (int y = 0; y < imageHeight; y += Constants.CHECKER_INCREMENT) {
                final Color c = ((x / Constants.CHECKER_INCREMENT) +
                        (y / Constants.CHECKER_INCREMENT)) % 2 == 0
                        ? Constants.WHITE : Constants.LIGHT_GREY;

                image.fillRectangle(c, x, y, Constants.CHECKER_INCREMENT,
                        Constants.CHECKER_INCREMENT);
            }
        }

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
        if (layerEditIndex >= 0 && layerEditIndex < layers.size())
            this.layerEditIndex = layerEditIndex;
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
