package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.literal;

import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class FloatLiteralNode extends LiteralNode {
    private final double value;

    public FloatLiteralNode(
            final TextPosition position,
            final double value
    ) {
        super(position);

        this.value = value;
    }

    @Override
    public Double evaluate(final SymbolTable symbolTable) {
        return value;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return new SimpleTypeNode(SimpleTypeNode.Type.FLOAT);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
