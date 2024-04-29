package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.literal;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

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
        return new SimpleTypeNode(SimpleTypeNode.Type.INT);
    }
}
