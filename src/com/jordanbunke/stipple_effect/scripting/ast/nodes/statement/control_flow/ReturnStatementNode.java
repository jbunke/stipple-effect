package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
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
        // TODO - get function level parent symbol table and ensure
        //  type of return expression matches method signature
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        return expression == null
                ? FuncControlFlow.returnVoid()
                : FuncControlFlow.returnVal(expression.evaluate(symbolTable));
    }
}
