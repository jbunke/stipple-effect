package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class DoWhileLoopNode extends StatementNode {
    // TODO - visitor link and implementation
    public DoWhileLoopNode(
            final TextPosition position,
            final StatementNode loopBody,
            final ExpressionNode loopCondition
    ) {
        super(position);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {

    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        return null;
    }

    @Override
    public String toString() {
        return "";
    }
}
