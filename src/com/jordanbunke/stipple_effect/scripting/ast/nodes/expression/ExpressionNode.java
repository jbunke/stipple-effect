package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ScrippleASTNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public abstract class ExpressionNode extends ScrippleASTNode {
    public ExpressionNode(final TextPosition position) {
        super(position);
    }

    public abstract Object evaluate(final SymbolTable symbolTable);
}
