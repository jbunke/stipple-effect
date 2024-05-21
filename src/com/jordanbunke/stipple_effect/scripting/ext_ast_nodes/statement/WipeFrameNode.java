package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.LayerTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

import java.util.Arrays;

public final class WipeFrameNode extends LayerOpNode {
    public static final String NAME = "wipe_frame";

    public WipeFrameNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, LayerTypeNode.get(), TypeNode.getInt());
    }

    @Override
    protected void operation(
            final LayerRep layer, final SymbolTable symbolTable
    ) {
        final Object[] vs = getValues(symbolTable);
        final int frameIndex = (int) vs[1],
                fc = layer.project().getState().getFrameCount();

        final String attempt = "wipe the content of this layer frame";

        if (frameIndex < 0)
            StatusUpdates.scriptActionNotPermitted(attempt,
                    "the frame index (" + frameIndex + ") is negative",
                    getArgs()[1].getPosition());
        else if (frameIndex >= layer.project().getState().getFrameCount())
            StatusUpdates.scriptActionNotPermitted(attempt,
                    "the frame index (" + frameIndex + ") is " +
                            (frameIndex == fc ? "greater than " : "equal to ") +
                            "the frame count (" + fc + ")",
                    getArgs()[1].getPosition());
        else {
            final int w = layer.project().getState().getImageWidth(),
                    h = layer.project().getState().getImageHeight();

            final boolean[][] mask = new boolean[w][h];

            for (boolean[] column : mask) Arrays.fill(column, true);

            final SELayer old = layer.project().getState().getLayers()
                    .get(layer.index()),
                    wiped = old.returnErased(mask, frameIndex);

            layer.project().setLayerFromScript(wiped, layer.index());
        }
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
