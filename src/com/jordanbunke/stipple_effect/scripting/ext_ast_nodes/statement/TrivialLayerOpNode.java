package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.LayerTypeNode;

public abstract class TrivialLayerOpNode extends LayerOpNode {
    public TrivialLayerOpNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, LayerTypeNode.get());
    }
}
