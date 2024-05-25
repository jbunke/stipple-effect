package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public abstract class PropertyNode extends ScopedExpressionNode {
    public PropertyNode(
            final TextPosition position,
            final ExpressionNode scope, final TypeNode expectedScopeType
    ) {
        super(position, scope, expectedScopeType, new ExpressionNode[] {});
    }

    @Override
    public final String toString() {
        return scope + callName();
    }
}
