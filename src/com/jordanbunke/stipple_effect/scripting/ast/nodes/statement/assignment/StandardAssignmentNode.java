package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.AssignableNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class StandardAssignmentNode extends AssignmentNode {
    private final ExpressionNode expression;

    public StandardAssignmentNode(
            final TextPosition position,
            final AssignableNode assignable,
            final ExpressionNode expression
    ) {
        super(position, assignable);

        this.expression = expression;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        // TODO
        getAssignable().update(symbolTable, expression.evaluate(symbolTable));

        return FuncControlFlow.cont();
    }
}
