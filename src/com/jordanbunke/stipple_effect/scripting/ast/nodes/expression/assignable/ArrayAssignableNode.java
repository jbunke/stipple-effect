package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptArray;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;

public final class ArrayAssignableNode extends CollectionAssignableNode {
    public ArrayAssignableNode(
            final TextPosition position,
            final String name,
            final ExpressionNode index
    ) {
        super(position, name, index);
    }

    @Override
    public ScriptArray evaluate(final SymbolTable symbolTable) {
        final Variable var = symbolTable.get(getName());
        return var != null ? (ScriptArray) var.get() : null;
    }
}
