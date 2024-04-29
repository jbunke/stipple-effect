package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ASTNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public abstract class ExpressionNode extends ASTNode {
    public ExpressionNode(final TextPosition position) {
        super(position);
    }

    public abstract Object evaluate(final SymbolTable symbolTable);

    public abstract TypeNode getType(final SymbolTable symbolTable);
}
