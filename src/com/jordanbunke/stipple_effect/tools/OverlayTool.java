package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;

public interface OverlayTool {
    GameImage getSelectionOverlay();
    boolean isDrawing();
}
