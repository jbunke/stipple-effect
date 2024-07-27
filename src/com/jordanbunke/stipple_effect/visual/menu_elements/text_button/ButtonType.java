package com.jordanbunke.stipple_effect.visual.menu_elements.text_button;

public enum ButtonType {
    STANDARD, STUB, DD_HEAD, DD_OPTION, DD_MENU_LEAF, DD_MENU_HEADER, CEL;

    public boolean isDropdown() {
        return switch (this) {
            case DD_HEAD, DD_OPTION -> true;
            default -> false;
        };
    }
}