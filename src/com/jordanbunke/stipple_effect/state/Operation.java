package com.jordanbunke.stipple_effect.state;

public enum Operation {
    // canvas sizing
    RESIZE, PAD, CROP_TO_SELECTION,
    STITCH, SPLIT,
    // frame operations
    ADD_FRAME, DUPLICATE_FRAME, REMOVE_FRAME,
    MOVE_FRAME, CHANGE_FRAME_DURATION,
    // layer operations
    ADD_LAYER, DUPLICATE_LAYER, REMOVE_LAYER,
    MOVE_LAYER, MERGE_WITH_LAYER_BELOW, FLATTEN,
    CHANGE_LAYER_NAME, CHANGE_ONION_SKIN,
    // layer operations without menu redraw consequences
    LAYER_VISIBILITY_CHANGE, LAYER_LINKING_CHANGE, LAYER_OPACITY_CHANGE,
    // selection operations
    RESET_SELECTION_CONTENTS, MOVE_SELECTION_CONTENTS,
    TRANSFORM_SELECTION_CONTENTS,
    PASTE, PASTE_CELS, RAISE, DROP,
    DELETE_SELECTION_CONTENTS, DELETE_CELS,
    MOVE_SELECTION_BOUNDS, TRANSFORM_SELECTION_BOUNDS,
    DESELECT, SELECT,
    // canvas edit operations
    PALETTIZE, EDIT_IMAGE,
    // placeholder
    NONE;

    public boolean triggersCanvasAuxiliaryRedraw() {
        return switch (this) {
            case RESIZE, PAD, CROP_TO_SELECTION, STITCH, SPLIT -> true;
            default -> false;
        };
    }

    public boolean triggersSelectionOverlayRedraw() {
        return switch (this) {
            case RESET_SELECTION_CONTENTS,
                    TRANSFORM_SELECTION_CONTENTS,
                    TRANSFORM_SELECTION_BOUNDS,
                    PASTE, DROP, SELECT -> true;
            default -> false;
        };
    }

    public boolean triggersOverlayOffsetUpdate() {
        return switch (this) {
            case MOVE_SELECTION_BOUNDS,
                    MOVE_SELECTION_CONTENTS -> true;
            default -> false;
        };
    }

    public boolean constitutesEdit() {
        return switch (this) {
            case NONE, RAISE, DROP, DESELECT, SELECT,
                    MOVE_SELECTION_BOUNDS,
                    TRANSFORM_SELECTION_BOUNDS -> false;
            default -> true;
        };
    }

    public ActionType getActionType() {
        return switch (this) {
            case ADD_FRAME, DUPLICATE_FRAME, REMOVE_FRAME,
                    MOVE_FRAME, PASTE_CELS, DELETE_CELS -> ActionType.FRAME;
            case ADD_LAYER, DUPLICATE_LAYER, REMOVE_LAYER, MOVE_LAYER,
                    MERGE_WITH_LAYER_BELOW, FLATTEN,
                    CHANGE_LAYER_NAME -> ActionType.LAYER;
            case STITCH, SPLIT -> ActionType.MAJOR;
            default -> ActionType.CANVAS;
        };
    }

    @Override
    public String toString() {
        return String.valueOf(name().charAt(0)).toUpperCase() +
                name().substring(1).replace("_", " ").toLowerCase();
    }
}
