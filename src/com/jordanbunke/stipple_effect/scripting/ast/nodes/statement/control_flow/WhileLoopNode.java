package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class WhileLoopNode extends StatementNode {
    private final ExpressionNode loopCondition;
    private final StatementNode loopBody;

    public WhileLoopNode(
            final TextPosition position,
            final ExpressionNode loopCondition,
            final StatementNode loopBody
    ) {
        super(position);

        this.loopCondition = loopCondition;
        this.loopBody = loopBody;
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
