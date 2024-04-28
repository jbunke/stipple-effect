package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class IfStatementNode extends StatementNode {
    private final ExpressionNode condition;
    private final StatementNode ifBody;
    private final IfStatementNode[] elseIfs;
    private final StatementNode elseBody;

    public IfStatementNode(
            final TextPosition position,
            final ExpressionNode condition, final StatementNode ifBody,
            final IfStatementNode[] elseIfs, final StatementNode elseBody
    ) {
        super(position);

        this.condition = condition;
        this.ifBody = ifBody;
        this.elseIfs = elseIfs;
        this.elseBody = elseBody;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        // TODO

        return FuncControlFlow.cont();
    }
}
