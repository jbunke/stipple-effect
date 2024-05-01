package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
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
        loopCondition.semanticErrorCheck(symbolTable);
        loopBody.semanticErrorCheck(symbolTable);

        final SimpleTypeNode
                boolType = new SimpleTypeNode(SimpleTypeNode.Type.BOOL);
        final TypeNode condType = loopCondition.getType(symbolTable);

        if (!condType.equals(boolType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.COND_NOT_BOOL,
                    loopCondition.getPosition(), condType.toString());
    }

    private boolean evaluateCondition(final SymbolTable symbolTable) {
        return (boolean) loopCondition.evaluate(symbolTable);
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        FuncControlFlow status = FuncControlFlow.cont();

        while (evaluateCondition(symbolTable))
            status = loopBody.execute(symbolTable);

        return status;
    }

    @Override
    public String toString() {
        return "while (" + loopCondition + ")\n" + loopBody;
    }
}
