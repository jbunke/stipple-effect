package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public sealed abstract class CollectionAssignableNode extends AssignableNode
        permits ListAssignableNode, ArrayAssignableNode {
    private final ExpressionNode index;

    public CollectionAssignableNode(
            final TextPosition position,
            final String name,
            final ExpressionNode index
    ) {
        super(position, name);

        this.index = index;
    }

    @Override
    public void update(
            final SymbolTable symbolTable,
            final Object value
    ) {
        // TODO
    }
}
