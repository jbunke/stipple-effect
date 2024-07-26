package com.jordanbunke.stipple_effect.state;

import com.jordanbunke.stipple_effect.StippleEffect;

public enum ActionType {
    MAJOR, LAYER, FRAME, CANVAS;

    public void consequence() {
        switch (this) {
            case MAJOR -> StippleEffect.get().rebuildStateDependentMenus();
            case LAYER, FRAME -> StippleEffect.get().rebuildFlipbookMenu();
        }
    }
}
