package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;

public abstract class AssignableNode extends ExpressionNode {
    public AssignableNode(final TextPosition position) {
        super(position);
    }
}
