package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.InitializationNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment.AssignmentNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class ForLoopNode extends StatementNode {
    private final InitializationNode initialization;
    private final ExpressionNode loopCondition;
    private final AssignmentNode incrementation;
    private final StatementNode loopBody;

    public ForLoopNode(
            final TextPosition position,
            final InitializationNode initialization,
            final ExpressionNode loopCondition,
            final AssignmentNode incrementation,
            final StatementNode loopBody
    ) {
        super(position);

        this.initialization = initialization;
        this.loopCondition = loopCondition;
        this.incrementation = incrementation;
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
