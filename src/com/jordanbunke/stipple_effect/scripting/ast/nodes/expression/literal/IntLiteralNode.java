package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.literal;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class IntLiteralNode extends LiteralNode {
    private final int value;

    public IntLiteralNode(
            final TextPosition position,
            final int value
    ) {
        super(position);

        this.value = value;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        return value;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getInt();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
