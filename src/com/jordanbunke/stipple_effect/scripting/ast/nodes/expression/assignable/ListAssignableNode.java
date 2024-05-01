package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class ListAssignableNode extends CollectionAssignableNode {
    public ListAssignableNode(
            final TextPosition position,
            final String name,
            final ExpressionNode index
    ) {
        super(position, name, index);
    }

    @Override
    public String toString() {
        return getName() + "(" + getIndex() + ")";
    }
}
