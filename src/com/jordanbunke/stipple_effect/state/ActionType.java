package com.jordanbunke.stipple_effect.state;

import com.jordanbunke.stipple_effect.StippleEffect;

public enum ActionType {
    MAJOR, LAYER, FRAME, CANVAS;

    public void consequence() {
        switch (this) {
            case MAJOR -> StippleEffect.get().rebuildStateDependentMenus();
            case LAYER -> {
                StippleEffect.get().rebuildLayersMenu();
                StippleEffect.get().rebuildFlipbookMenu();
            }
            case FRAME -> {
                StippleEffect.get().rebuildFramesMenu();
                StippleEffect.get().rebuildFlipbookMenu();
            }
            // TODO: LAYER, FRAME -> StippleEffect.get().rebuildFlipbookMenu();
        }
    }
}
