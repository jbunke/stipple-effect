package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.utility.Constants;

public abstract class ConstantExpressionNode extends GlobalExpressionNode {
    public ConstantExpressionNode(final TextPosition position) {
        super(position, new ExpressionNode[] {});
    }

    @Override
    public String toString() {
        return "$" + Constants.SCRIPT_GLOBAL_NAMESPACE + "." + callName();
    }
}
