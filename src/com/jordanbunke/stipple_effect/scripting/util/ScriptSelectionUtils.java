package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptSet;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.selection.Outliner;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;

public class ScriptSelectionUtils {
    public static Selection convertSelection(
            final ScriptSet input, final int w, final int h
    ) {
        return convertSelection(input, (x, y) ->
                x >= 0 && x < w && y >= 0 && y < h);
    }

    public static Selection convertSelection(
            final ScriptSet input
    ) {
        return convertSelection(input, (x, y) -> true);
    }

    private static Selection convertSelection(
            final ScriptSet input, BiPredicate<Integer, Integer> coordCondition
    ) {
        final Set<Coord2D> pixels = new HashSet<>();

        input.stream().map(px -> (ScriptArray) px).forEach(a -> {
            final int x = (int) a.get(0), y = (int) a.get(1);

            if (coordCondition.test(x, y))
                pixels.add(new Coord2D(x, y));
        });

        return Selection.fromPixels(pixels);
    }

    public static boolean invalidSideMask(
            final int[] sideMask, final ExpressionNode culprit
    ) {
        final int expected = Outliner.Direction.values().length,
                MAX = Constants.MAX_OUTLINE_PX;

        if (sideMask.length != expected) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    culprit.getPosition(),
                    "Side mask array argument for outline operation has " +
                            sideMask.length + " elements instead of " + expected);
            return true;
        }

        for (int side : sideMask) {
            if (side < -MAX || side > MAX) {
                ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                        culprit.getPosition(),
                        "Side mask value " + side + " is out of bounds (" +
                                (-MAX) + " <= px <= " + MAX + ")");
                return true;
            }
        }

        return false;
    }
}
