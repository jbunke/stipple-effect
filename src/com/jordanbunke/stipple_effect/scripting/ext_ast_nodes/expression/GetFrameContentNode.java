package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.LayerTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public final class GetFrameContentNode extends SEExtExpressionNode {
    public static final String NAME = "get_frame_content";

    public GetFrameContentNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, LayerTypeNode.get(), TypeNode.getInt());
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Object[] vs = getValues(symbolTable);
        final LayerRep layer = (LayerRep) vs[0];
        final int frameIndex = (int) vs[1],
                fc = layer.project().getState().getFrameCount();

        if (layer.index() >= layer.project().getState().getLayers().size())
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    getArgs()[0].getPosition(),
                    "The layer object \"" + getArgs()[0] +
                            "\" no longer references a valid layer");
        else if (frameIndex < 0)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    getArgs()[1].getPosition(),
                    "The frame index (" + frameIndex + ") is negative");
        else if (frameIndex >= layer.project().getState().getFrameCount())
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    getArgs()[1].getPosition(), "The frame index (" +
                            frameIndex + ") is " + (frameIndex == fc
                            ? "greater than " : "equal to ") +
                            "the frame count (" + fc + ")");
        else
            return new GameImage(layer.project().getState().getLayers()
                    .get(layer.index()).getFrame(frameIndex));


        return null;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getImage();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
