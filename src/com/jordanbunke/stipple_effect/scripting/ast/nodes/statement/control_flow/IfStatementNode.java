package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.BodyStatementNode;
import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Arrays;

public final class IfStatementNode extends StatementNode {
    private final ExpressionNode[] conditions;
    private final StatementNode[] bodies;
    private final StatementNode elseBody;

    public IfStatementNode(
            final TextPosition position,
            final ExpressionNode[] conditions, final StatementNode[] bodies,
            final StatementNode elseBody
    ) {
        super(position);

        this.conditions = conditions;
        this.bodies = bodies;
        this.elseBody = elseBody;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        final SimpleTypeNode
                boolType = new SimpleTypeNode(SimpleTypeNode.Type.BOOL);

        for (ExpressionNode condition : conditions) {
            condition.semanticErrorCheck(symbolTable);

            final TypeNode condType = condition.getType(symbolTable);

            if (!condType.equals(boolType))
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.COND_NOT_BOOL,
                        condition.getPosition(), condType.toString());
        }

        for (StatementNode body : bodies)
            body.semanticErrorCheck(symbolTable);
        if (elseBody != null)
            elseBody.semanticErrorCheck(symbolTable);
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final int l = conditions.length;

        for (int i = 0; i < l; i++)
            if ((boolean) conditions[i].evaluate(symbolTable))
                return bodies[i].execute(symbolTable);

        return elseBody != null
                ? elseBody.execute(symbolTable)
                : FuncControlFlow.cont();
    }

    @Override
    public String toString() {
        final int l = conditions.length;
        final String[] branches = new String[l];

        for (int i = 0; i < l; i++)
            branches[i] = "if (" + conditions[i] + ")\n" +
                    (bodies[i] instanceof BodyStatementNode ? "" : "\t") +
                    bodies[i];

        return Arrays.stream(branches)
                .reduce((a, b) -> a + "\nelse " + b).orElse(branches[0]) +
                (elseBody != null ? "\nelse\n" + elseBody : "");
    }
}
