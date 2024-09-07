package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.graphics;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.ExtExpressionNode;
import com.jordanbunke.stipple_effect.scripting.util.Arguments;
import com.jordanbunke.stipple_effect.utility.Constants;

public abstract class GraphicsExpressionNode extends ExtExpressionNode {
    public GraphicsExpressionNode(
            final TextPosition position,
            final ExpressionNode[] args,
            final TypeNode... expectedTypes
    ) {
        super(position, new Arguments(args, expectedTypes));
    }

    @Override
    public String toString() {
        return "$" + Constants.GRAPHICS_NAMESPACE +
                "." + callName() + arguments;
    }
}
