package com.jordanbunke.stipple_effect.layer;

import com.jordanbunke.stipple_effect.utility.action.ActionCodes;

public enum OnionSkinMode {
    NONE, PREVIOUS, NEXT, BOTH;

    public boolean doPrevious() {
        return this == PREVIOUS || this == BOTH;
    }

    public boolean doNext() {
        return this == NEXT || this == BOTH;
    }

    public String getIconCode() {
        return switch (this) {
            case NONE -> ActionCodes.ONION_SKIN_NONE;
            case PREVIOUS -> ActionCodes.ONION_SKIN_PREVIOUS;
            case NEXT -> ActionCodes.ONION_SKIN_NEXT;
            case BOTH -> ActionCodes.ONION_SKIN_BOTH;
        };
    }
}
