package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.literal;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class StringLiteralNode extends LiteralNode {
    private final String value;

    public StringLiteralNode(
            final TextPosition position, final String value
    ) {
        super(position);

        this.value = value;
    }

    @Override
    public String evaluate(final SymbolTable symbolTable) {
        return value;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getString();
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
