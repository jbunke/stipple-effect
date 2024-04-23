package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class ReturnStatementNode extends StatementNode {
    private final ExpressionNode expression;

    private ReturnStatementNode(
            final TextPosition position,
            final ExpressionNode expression
    ) {
        super(position);

        this.expression = expression;
    }

    public static ReturnStatementNode forVoid(
            final TextPosition position
    ) {
        return new ReturnStatementNode(position, null);
    }

    public static ReturnStatementNode forTyped(
            final TextPosition position, final ExpressionNode expression
    ) {
        return new ReturnStatementNode(position, expression);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }

    @Override
    public void execute(final SymbolTable symbolTable) {
        // TODO
    }
}
