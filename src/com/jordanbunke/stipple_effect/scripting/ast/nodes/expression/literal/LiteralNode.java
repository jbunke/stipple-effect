package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.literal;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public abstract class LiteralNode extends ExpressionNode {
    public LiteralNode(final TextPosition position) {
        super(position);
    }

    @Override
    public final void semanticErrorCheck(final SymbolTable symbolTable) {}
}
