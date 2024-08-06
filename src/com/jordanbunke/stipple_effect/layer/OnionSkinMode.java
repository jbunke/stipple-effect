package com.jordanbunke.stipple_effect.layer;

import com.jordanbunke.stipple_effect.utility.action.ResourceCodes;

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
            case NONE -> ResourceCodes.ONION_SKIN_NONE;
            case PREVIOUS -> ResourceCodes.ONION_SKIN_PREVIOUS;
            case NEXT -> ResourceCodes.ONION_SKIN_NEXT;
            case BOTH -> ResourceCodes.ONION_SKIN_BOTH;
        };
    }
}
