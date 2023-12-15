package com.jordanbunke.stipple_effect.layer;

public enum OnionSkinMode {
    NONE, PREVIOUS, NEXT, BOTH;

    public boolean doPrevious() {
        return this == PREVIOUS || this == BOTH;
    }

    public boolean doNext() {
        return this == NEXT || this == BOTH;
    }
}
