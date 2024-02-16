package com.jordanbunke.stipple_effect.state;

public enum Operation {
    // canvas sizing
    RESIZE, PAD, CROP_TO_SELECTION,
    // frame operations
    ADD_FRAME, DUPLICATE_FRAME, REMOVE_FRAME,
    MOVE_FRAME_BACK, MOVE_FRAME_FORWARD,
    // layer operations
    ADD_LAYER, DUPLICATE_LAYER, REMOVE_LAYER,
    MOVE_LAYER_DOWN, MOVE_LAYER_UP, MERGE_WITH_LAYER_BELOW,
    CHANGE_LAYER_NAME,
    // layer operations without menu redraw consequences
    LAYER_VISIBILITY_CHANGE, LAYER_LINKING_CHANGE, LAYER_OPACITY_CHANGE,
    // selection operations
    RESET_SELECTION_CONTENTS, MOVE_SELECTION_CONTENTS,
    STRETCH_SELECTION_CONTENTS, ROTATE_SELECTION_CONTENTS,
    REFLECT_SELECTION_CONTENTS, PASTE, RAISE, DROP,
    DELETE_SELECTION_CONTENTS,
    MOVE_SELECTION_BOUNDS, STRETCH_SELECTION_BOUNDS,
    ROTATE_SELECTION_BOUNDS, REFLECT_SELECTION_BOUNDS,
    DESELECT, SELECT,
    // canvas edit operations
    PALETTIZE, EDIT_IMAGE,
    // placeholder
    NONE;

    public boolean triggersCanvasAuxiliaryRedraw() {
        return switch (this) {
            case RESIZE, PAD, CROP_TO_SELECTION -> true;
            default -> false;
        };
    }

    public boolean triggersSelectionOverlayRedraw() {
        return switch (this) {
            case RESET_SELECTION_CONTENTS,
                    ROTATE_SELECTION_CONTENTS,
                    REFLECT_SELECTION_CONTENTS,
                    STRETCH_SELECTION_CONTENTS,
                    ROTATE_SELECTION_BOUNDS,
                    REFLECT_SELECTION_BOUNDS,
                    STRETCH_SELECTION_BOUNDS,
                    PASTE, DROP, SELECT -> true;
            default -> false;
        };
    }

    public boolean constitutesEdit() {
        return switch (this) {
            case NONE, RAISE, DROP, DESELECT, SELECT,
                    MOVE_SELECTION_BOUNDS,
                    ROTATE_SELECTION_BOUNDS,
                    STRETCH_SELECTION_BOUNDS,
                    REFLECT_SELECTION_BOUNDS -> false;
            default -> true;
        };
    }

    public ActionType getActionType() {
        return switch (this) {
            case ADD_FRAME, DUPLICATE_FRAME, REMOVE_FRAME,
                    MOVE_FRAME_BACK, MOVE_FRAME_FORWARD -> ActionType.FRAME;
            case ADD_LAYER, DUPLICATE_LAYER, REMOVE_LAYER,
                    MOVE_LAYER_DOWN, MOVE_LAYER_UP, MERGE_WITH_LAYER_BELOW,
                    CHANGE_LAYER_NAME -> ActionType.LAYER;
            default -> ActionType.CANVAS;
        };
    }
}
