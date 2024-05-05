package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public sealed class WhileLoopNode extends StatementNode
        permits DoWhileLoopNode {
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
        loopCondition.semanticErrorCheck(symbolTable);
        loopBody.semanticErrorCheck(symbolTable);

        final SimpleTypeNode boolType = TypeNode.getBool();
        final TypeNode condType = loopCondition.getType(symbolTable);

        if (!condType.equals(boolType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    loopCondition.getPosition(), "Condition",
                    boolType.toString(), condType.toString());
    }

    boolean evaluateCondition(final SymbolTable symbolTable) {
        return (boolean) loopCondition.evaluate(symbolTable);
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        FuncControlFlow status = FuncControlFlow.cont();

        while (evaluateCondition(symbolTable)) {
            status = loopBody.execute(symbolTable);

            if (!status.cont)
                return status;
        }

        return status;
    }

    @Override
    public String toString() {
        return "while (" + loopCondition + ")\n" + loopBody;
    }

    public ExpressionNode getLoopCondition() {
        return loopCondition;
    }

    public StatementNode getLoopBody() {
        return loopBody;
    }
}
