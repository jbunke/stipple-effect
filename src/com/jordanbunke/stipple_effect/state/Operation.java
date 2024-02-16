package com.jordanbunke.stipple_effect.state;

public enum Operation {
    // canvas sizing
    RESIZE, PAD, CROP_TO_SELECTION,
    // frame operations
    ADDED_FRAME, DUPLICATED_FRAME, REMOVED_FRAME,
    MOVED_FRAME_BACK, MOVED_FRAME_FORWARD,
    // layer operations
    ADDED_LAYER, DUPLICATED_LAYER, REMOVED_LAYER,
    MOVED_LAYER_DOWN, MOVED_LAYER_UP, MERGED_WITH_LAYER_BELOW,
    // selection operations
    // TODO
    NONE;

    public boolean triggersCanvasAuxiliaryRedraw() {
        return switch (this) {
            case RESIZE, PAD, CROP_TO_SELECTION -> true;
            default -> false;
        };
    }
}
