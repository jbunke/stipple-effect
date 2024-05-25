package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.util.Arguments;

public abstract class ExtExpressionNode extends ExpressionNode {
    protected final Arguments arguments;

    public ExtExpressionNode(
            final TextPosition position, final Arguments arguments
    ) {
        super(position);

        this.arguments = arguments;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        arguments.semanticErrorCheck(symbolTable, getPosition());
    }

    protected abstract String callName();
}
