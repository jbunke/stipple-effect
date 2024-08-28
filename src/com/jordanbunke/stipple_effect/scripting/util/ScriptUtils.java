package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptSet;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.scripting.SEInterpreter;
import com.jordanbunke.stipple_effect.selection.Outliner;
import com.jordanbunke.stipple_effect.selection.Selection;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;

public class ScriptUtils {
    public static GameImage[] runPreviewScript(
            final GameImage[] input, final HeadFuncNode script
    ) {
        final boolean animScript = script.paramsMatch(
                new TypeNode[] { TypeNode.arrayOf(TypeNode.getImage()) }),
                imgReturn = script.getReturnType().equals(TypeNode.getImage());

        if (animScript) {
            // image[] -> image[] or image[] -> image
            final Object result = SEInterpreter.get().run(script, (Object) input);

            if (result instanceof GameImage image)
                return new GameImage[] { image };
            else if (result instanceof ScriptArray arr)
                return convertScriptImageArray(arr);

            return null;
        } else if (imgReturn) {
            // image -> image
            final GameImage[] output = new GameImage[input.length];

            for (int i = 0; i < output.length; i++) {
                final Object result = SEInterpreter.get().run(script, input[i]);

                if (result instanceof GameImage image)
                    output[i] = image;
                else
                    return null;
            }

            return output;
        } else if (input.length == 1) {
            // image -> image[]
            final Object result = SEInterpreter.get().run(script, input[0]);

            return (result instanceof ScriptArray arr)
                    ? convertScriptImageArray(arr) : null;
        }

        return null;
    }

    private static GameImage[] convertScriptImageArray(final ScriptArray arr) {
        final GameImage[] images = new GameImage[arr.size()];

        for (int i = 0; i < images.length; i++)
            images[i] = (GameImage) arr.get(i);

        return images;
    }

    public static Path scriptFolderToPath(final ScriptArray folder) {
        final String first = (String) folder.get(0);
        final String[] rest = new String[folder.size() - 1];

        for (int i = 0; i < rest.length; i++)
            rest[i] = (String) folder.get(i + 1);

        return Path.of(first, rest);
    }

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
