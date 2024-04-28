package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.AssignableNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;

public abstract class AssignmentNode extends StatementNode {
    private final AssignableNode assignable;

    public AssignmentNode(
            final TextPosition position, final AssignableNode assignable
    ) {
        super(position);

        this.assignable = assignable;
    }

    public AssignableNode getAssignable() {
        return assignable;
    }
}
