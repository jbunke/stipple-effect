package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.literal;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class BoolLiteralNode extends LiteralNode {
    private final boolean value;

    public BoolLiteralNode(
            final TextPosition position,
            final boolean value
    ) {
        super(position);

        this.value = value;
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        return value;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return new SimpleTypeNode(SimpleTypeNode.Type.BOOL);
    }
}
