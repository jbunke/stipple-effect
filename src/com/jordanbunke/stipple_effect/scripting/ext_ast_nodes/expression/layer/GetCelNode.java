package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.layer;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public final class GetCelNode extends LayerExpressionNode {
    public static final String NAME = "get_cel";

    public GetCelNode(
            final TextPosition position, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(position, scope, args, TypeNode.getInt());
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final LayerRep layer = layerRep(symbolTable);
        final int frameIndex = (int) arguments.getValues(symbolTable)[0],
                fc = layer.project().getState().getFrameCount();

        if (layer.index() >= layer.project().getState().getLayers().size())
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    scope.caller().getPosition(),
                    "The layer object \"" + scope.caller() +
                            "\" no longer references a valid layer");
        else if (frameIndex < 0)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.args()[0].getPosition(),
                    "The frame index (" + frameIndex + ") is negative");
        else if (frameIndex >= fc)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.args()[0].getPosition(),
                    "The frame index (" + frameIndex + ") is " +
                            (frameIndex == fc ? "greater than " : "equal to ") +
                            "the frame count (" + fc + ")");
        else
            return new GameImage(layerRep(symbolTable).get().getCel(frameIndex));


        return null;
    }

    @Override
    public BaseTypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getImage();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
