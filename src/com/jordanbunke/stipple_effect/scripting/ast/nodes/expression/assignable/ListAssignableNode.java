package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class ListAssignableNode extends CollectionAssignableNode {
    public ListAssignableNode(
            final TextPosition position,
            final String name,
            final ExpressionNode index
    ) {
        super(position, name, index);
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        // TODO
        return null;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        // TODO
        return null;
    }
}
