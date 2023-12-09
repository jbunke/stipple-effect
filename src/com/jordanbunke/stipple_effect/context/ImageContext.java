package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.stipple_effect.layer.LayerCombiner;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.state.ImageState;
import com.jordanbunke.stipple_effect.state.StateManager;

import java.util.ArrayList;
import java.util.List;

public class ImageContext implements SEContext {
    private final StateManager<ImageState> states;

    public ImageContext(
            final int imageWidth, final int imageHeight
    ) {
        this.states = new StateManager<>(new ImageState(imageWidth, imageHeight));
    }

    // TODO - process all actions here and feed through state manager

    // TODO - TOOL

    // LAYER MANIPULATION
    // add layer
    public void addLayer() {
        // build resultant state
        final int w = states.getState().getImageWidth(),
                h = states.getState().getImageHeight();
        final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
        final int addIndex = states.getState().getLayerEditIndex() + 1;
        layers.add(addIndex, new SELayer(w, h));

        final ImageState result = new ImageState(w, h, layers, addIndex);
        states.performAction(result);
    }

    // remove layer
    public void removeLayer() {
        // pre-check
        if (states.getState().canRemoveLayer()) {
            // build resultant state
            final int w = states.getState().getImageWidth(),
                    h = states.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
            final int index = states.getState().getLayerEditIndex();
            layers.remove(index);

            final ImageState result = new ImageState(w, h, layers, index > 0 ? index - 1 : index);
            states.performAction(result);
        }
    }

    // move layer down
    public void moveLayerDown() {
        // pre-check
        if (states.getState().canMoveLayerDown()) {
            // build resultant state
            final int w = states.getState().getImageWidth(),
                    h = states.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
            final int removalIndex = states.getState().getLayerEditIndex(),
                    reinsertionIndex = removalIndex - 1;

            final SELayer toMove = layers.get(removalIndex);
            layers.remove(removalIndex);
            layers.add(reinsertionIndex, toMove);

            final ImageState result = new ImageState(w, h, layers, reinsertionIndex);
            states.performAction(result);
        }
    }

    // move layer up
    public void moveLayerUp() {
        // pre-check
        if (states.getState().canMoveLayerUp()) {
            // build resultant state
            final int w = states.getState().getImageWidth(),
                    h = states.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
            final int removalIndex = states.getState().getLayerEditIndex(),
                    reinsertionIndex = removalIndex + 1;

            final SELayer toMove = layers.get(removalIndex);
            layers.remove(removalIndex);
            layers.add(reinsertionIndex, toMove);

            final ImageState result = new ImageState(w, h, layers, reinsertionIndex);
            states.performAction(result);
        }
    }

    // combine with layer below
    public void combineWithLayerBelow() {
        // pre-check - identical pass case as can move layer down
        if (states.getState().canMoveLayerDown()) {
            // build resultant state
            final int w = states.getState().getImageWidth(),
                    h = states.getState().getImageHeight();
            final List<SELayer> layers = new ArrayList<>(states.getState().getLayers());
            final int aboveIndex = states.getState().getLayerEditIndex(),
                    belowIndex = aboveIndex - 1;

            final SELayer above = layers.get(aboveIndex),
                    below = layers.get(belowIndex);
            final SELayer combined = LayerCombiner.combine(above, below);
            layers.remove(above);
            layers.remove(below);
            layers.add(belowIndex, combined);

            final ImageState result = new ImageState(w, h, layers, belowIndex);
            states.performAction(result);
        }
    }
}
