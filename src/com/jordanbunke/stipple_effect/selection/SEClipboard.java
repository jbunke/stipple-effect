package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.stipple_effect.state.ProjectState;

public class SEClipboard {
    private static final SEClipboard INSTANCE;

    private SelectionContents contents;


    private SEClipboard() {
        contents = null;
    }

    static {
        INSTANCE = new SEClipboard();
    }

    public static SEClipboard get() {
        return INSTANCE;
    }

    public void sendSelectionToClipboard(final ProjectState state) {
        contents = switch (state.getSelectionMode()) {
            case BOUNDS -> new SelectionContents(
                    state.getActiveLayerFrame(), state.getSelection());
            case CONTENTS -> state.getSelectionContents();
        };
    }

    public boolean hasContents() {
        return contents != null;
    }

    public SelectionContents getContents() {
        return contents;
    }
}
