package com.jordanbunke.stipple_effect.visual.menu_elements.dialog;

import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.selection.Outliner;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.DialogVals;
import com.jordanbunke.stipple_effect.utility.Layout;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class OutlineTextbox extends DynamicTextbox {
    private OutlineTextbox(
            final Coord2D position, final Anchor anchor, final String initialText,
            final Consumer<String> setter, final Supplier<String> getter
    ) {
        super(position, (Layout.STD_TEXT_BUTTON_INC * 3) - Layout.BUTTON_OFFSET,
                anchor, "", initialText, "px",
                Textbox.getIntTextValidator(-Constants.MAX_OUTLINE_PX,
                        Constants.MAX_OUTLINE_PX), setter, getter, 3);
    }

    public static OutlineTextbox make(
            final OutlineDirectionWatcher watcher,
            final Outliner.Direction direction
    ) {
        final Anchor anchor = switch (direction) {
            case TL, L, BL -> Anchor.RIGHT_CENTRAL;
            case TR, R, BR -> Anchor.LEFT_CENTRAL;
            case B, T -> Anchor.CENTRAL;
        };

        final int unit = Layout.STD_TEXT_BUTTON_INC, half = unit / 2;

        final Coord2D shift = switch (direction) {
            case T -> new Coord2D(0, -unit);
            case B -> new Coord2D(0, unit);
            case TL, L, BL -> new Coord2D(-half - 1, 0);
            case TR, R, BR -> new Coord2D(half + 2, 0);
        };

        final Supplier<String> getter = () -> String.valueOf(
                DialogVals.getThisOutlineSide(direction.ordinal()));
        final Consumer<String> setter = s -> DialogVals.setThisOutlineSide(
                direction.ordinal(), Integer.parseInt(s));

        return new OutlineTextbox(watcher.getPosition().displace(shift),
                anchor, getter.get(), setter, getter);
    }
}
