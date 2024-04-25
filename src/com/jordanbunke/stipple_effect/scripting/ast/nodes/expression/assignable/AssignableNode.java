package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;

public abstract class AssignableNode extends ExpressionNode {
    private final String name;

    public AssignableNode(
            final TextPosition position,
            final String name
    ) {
        super(position);

        this.name = name;
    }
}
