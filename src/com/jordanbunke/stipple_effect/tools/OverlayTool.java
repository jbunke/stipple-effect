package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;

public interface OverlayTool {
    Coord2D getTopLeft();
    GameImage getSelectionOverlay();
    boolean isDrawing();
}
