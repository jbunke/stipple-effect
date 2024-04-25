package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.literal;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
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
    public ScrippleTypeNode getType(final SymbolTable symbolTable) {
        return new SimpleTypeNode(SimpleTypeNode.Type.FLOAT);
    }
}
