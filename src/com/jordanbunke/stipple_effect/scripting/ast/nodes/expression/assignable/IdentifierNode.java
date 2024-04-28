package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;

public final class IdentifierNode extends AssignableNode {
    public IdentifierNode(
            final TextPosition position, final String name
    ) {
        super(position, name);
    }

    @Override
    public void update(
            final SymbolTable symbolTable,
            final Object value
    ) {
        symbolTable.update(getName(), value);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        if (symbolTable.get(getName()) == null)
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.UNDEFINED_VAR,
                    getPosition(), getName());
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Variable var = symbolTable.get(getName());

        if (var == null)
            return null;

        final Object value = var.get();

        if (value == null)
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.UNINITIALIZED_VAR,
                    getPosition(), getName());

        return value;
    }

    @Override
    public ScrippleTypeNode getType(final SymbolTable symbolTable) {
        final Variable var = symbolTable.get(getName());

        // this if statement should never pass; consider refactoring
        if (var == null) {
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.UNDEFINED_VAR,
                    getPosition(), getName());
            return null;
        }

        return var.getType();
    }
}
