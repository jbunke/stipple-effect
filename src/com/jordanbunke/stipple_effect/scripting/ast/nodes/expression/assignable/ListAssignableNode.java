package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptList;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;

public final class ListAssignableNode extends CollectionAssignableNode {
    public ListAssignableNode(
            final TextPosition position,
            final String name,
            final ExpressionNode index
    ) {
        super(position, name, index);
    }

    @Override
    public ScriptList evaluate(final SymbolTable symbolTable) {
        final Variable var = symbolTable.get(getName());
        return var != null ? (ScriptList) var.get() : null;
    }
}
