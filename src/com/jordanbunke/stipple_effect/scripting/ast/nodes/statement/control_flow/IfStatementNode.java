package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Arrays;

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
        condition.semanticErrorCheck(symbolTable);
        ifBody.semanticErrorCheck(symbolTable);

        for (IfStatementNode elseIf : elseIfs)
            elseIf.semanticErrorCheck(symbolTable);
        if (elseBody != null)
            elseBody.semanticErrorCheck(symbolTable);

        final SimpleTypeNode
                boolType = new SimpleTypeNode(SimpleTypeNode.Type.BOOL);
        final TypeNode condType = condition.getType(symbolTable);

        if (!condType.equals(boolType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.COND_NOT_BOOL,
                    condition.getPosition(), condType.toString());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        if ((boolean) condition.evaluate(symbolTable))
            return ifBody.execute(symbolTable);
        else {
            for (IfStatementNode elseIf : elseIfs)
                if ((boolean) elseIf.condition.evaluate(symbolTable))
                    return elseIf.ifBody.execute(symbolTable);

            return elseBody != null
                    ? elseBody.execute(symbolTable)
                    : FuncControlFlow.cont();
        }
    }

    @Override
    public String toString() {
        final String elseIfStrings = elseIfs.length == 1
                ? elseIfs[0].toString()
                : Arrays.stream(elseIfs)
                .map(IfStatementNode::toString)
                .reduce((a, b) -> a + b).orElse("");

        return "if (" + condition + ")\n" + ifBody +
                (elseIfs.length > 0 ? "\n" : "") + elseIfStrings +
                (elseBody != null ? "\nelse\n" + elseBody : "");
    }
}
