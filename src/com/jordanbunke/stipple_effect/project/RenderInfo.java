package com.jordanbunke.stipple_effect.project;

import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.tools.ToolWithBreadth;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.settings.Settings;

public class RenderInfo {
    private Coord2D anchor;
    private ZoomLevel zoomLevel;
    private boolean pixelGridOn;

    public RenderInfo(final int imageWidth, final int imageHeight) {
        this.anchor = new Coord2D(imageWidth / 2, imageHeight / 2);
        this.zoomLevel = ZoomLevel.NONE;
        this.pixelGridOn = Settings.isPixelGridOnByDefault();
    }

    public void zoomIn(final Coord2D targetPixel) {
        setZoomLevel(zoomLevel.in());
        adjustAnchorFromZoom(targetPixel);
    }

    public void zoomOut() {
        setZoomLevel(zoomLevel.out());
    }

    private void adjustAnchorFromZoom(final Coord2D targetPixel) {
        if (!targetPixel.equals(Constants.NO_VALID_TARGET)) {
            final Coord2D adjusted = new Coord2D(
                    (anchor.x + targetPixel.x) / 2,
                    (anchor.y + targetPixel.y) / 2);
            setAnchor(adjusted);
        }
    }

    public void setZoomLevel(final ZoomLevel zoomLevel) {
        final boolean redrawOverlays = this.zoomLevel != zoomLevel &&
                zoomLevel.z > Constants.ZOOM_FOR_OVERLAY;

        this.zoomLevel = zoomLevel;

        if (redrawOverlays)
            ToolWithBreadth.redrawToolOverlays();
    }

    public void setAnchor(final Coord2D anchor) {
        final ProjectState state = StippleEffect.get().getContext().getState();
        final int w = state.getImageWidth(), h = state.getImageHeight();

        this.anchor = new Coord2D(Math.max(0, Math.min(anchor.x, w)),
                Math.max(0, Math.min(anchor.y, h)));
    }

    public void incrementAnchor(final Coord2D delta) {
        setAnchor(anchor.displace(delta));
    }

    public void togglePixelGrid() {
        setPixelGrid(!pixelGridOn);
    }

    public void setPixelGrid(final boolean pixelGridOn) {
        this.pixelGridOn = pixelGridOn;

        StippleEffect.get().getContext().redrawPixelGrid();
    }

    public String getZoomText() {
        return zoomLevel.toString();
    }

    public float getZoomFactor() {
        return zoomLevel.z;
    }

    public ZoomLevel getZoomLevel() {
        return zoomLevel;
    }

    public Coord2D getAnchor() {
        return anchor;
    }

    public boolean isPixelGridOn() {
        return pixelGridOn;
    }
}
