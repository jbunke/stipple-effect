package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.io.InputEventLogger;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.layer.LayerCombiner;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.state.ImageState;
import com.jordanbunke.stipple_effect.state.StateManager;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class ImageContext {
    private static final Coord2D UNTARGETED = new Coord2D(-1, -1);

    private final StateManager<ImageState> states;
    private final RenderInfo renderInfo;

    private Coord2D targetPixel;

    public ImageContext(final GameImage image) {
        this(new ImageState(image), image.getWidth(), image.getHeight());
    }

    public ImageContext(
            final int imageWidth, final int imageHeight
    ) {
        this(new ImageState(imageWidth, imageHeight), imageWidth, imageHeight);
    }

    private ImageContext(
            final ImageState imageState, final int imageWidth, final int imageHeight
    ) {
        this.states = new StateManager<>(imageState);
        this.renderInfo = new RenderInfo(imageWidth, imageHeight);
        this.targetPixel = UNTARGETED;
    }

    public GameImage drawWorkspace() {
        final GameImage workspace = new GameImage(Constants.WORKSPACE_W, Constants.WORKSPACE_H);
        workspace.fillRectangle(Constants.BACKGROUND, 0, 0,
                Constants.WORKSPACE_W, Constants.WORKSPACE_H);

        final int scaleUp = renderInfo.getScaleUp();
        final Coord2D render = getImageRenderPositionInWorkspace();
        final GameImage image = ImageProcessing.scale(states.getState().draw(), scaleUp);
        workspace.draw(image, render.x, render.y);

        return workspace.submit();
    }

    public void process(final InputEventLogger eventLogger) {
        setTargetPixel(eventLogger);
    }

    private void setTargetPixel(final InputEventLogger eventLogger) {
        final Coord2D m = eventLogger.getAdjustedMousePosition();
        final Coord2D workshopM = new Coord2D(m.x - Constants.TOOLS_W, m.y - Constants.CONTEXTS_H);
        final boolean inWorkshopBounds =
                workshopM.x > 0 && workshopM.x < Constants.WORKSPACE_W &&
                        workshopM.y > 0 && workshopM.y < Constants.WORKSPACE_H;

        if (inWorkshopBounds) {
            final int w = states.getState().getImageWidth(),
                    h = states.getState().getImageHeight(),
                    scaleUp = renderInfo.getScaleUp();
            final Coord2D render = getImageRenderPositionInWorkspace(),
                    bottomLeft = new Coord2D(render.x + (scaleUp * w),
                            render.y + (scaleUp * h));
            final int targetX = (int)((double)((workshopM.x - render.x) / (bottomLeft.x - render.x)) * w),
                    targetY = (int)((double)((workshopM.y - render.y) / (bottomLeft.y - render.y)) * h);

            targetPixel = (targetX >= 0 && targetX < w && targetY >= 0 && targetY < h)
                    ? new Coord2D(targetX, targetY) : UNTARGETED;
        } else
            targetPixel = UNTARGETED;
    }

    private Coord2D getImageRenderPositionInWorkspace() {
        final int scaleUp = renderInfo.getScaleUp();
        final Coord2D anchor = renderInfo.getAnchor(),
                middle = new Coord2D(Constants.WORKSPACE_W / 2, Constants.WORKSPACE_H / 2);

        return new Coord2D(middle.x - (scaleUp * anchor.x),
                middle.y - (scaleUp * anchor.y));
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

    // GETTERS
    public String getTargetPixelText() {
        return targetPixel == UNTARGETED
                ? "--" : "TARGET: " + targetPixel.toString();
    }

    public String getCanvasSizeText() {
        return "SIZE: " + states.getState().getImageWidth() +
                " x " + states.getState().getImageHeight();
    }
}
