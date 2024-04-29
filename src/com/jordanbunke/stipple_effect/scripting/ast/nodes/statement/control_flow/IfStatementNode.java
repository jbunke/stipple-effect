package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
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
        condition.semanticErrorCheck(symbolTable);
        ifBody.semanticErrorCheck(symbolTable);

        for (IfStatementNode elseIf : elseIfs)
            elseIf.semanticErrorCheck(symbolTable);
        elseBody.semanticErrorCheck(symbolTable);

        final SimpleTypeNode
                boolType = new SimpleTypeNode(SimpleTypeNode.Type.BOOL);
        final TypeNode condType = condition.getType(symbolTable);

        if (!condType.equals(boolType))
            ScrippleErrorListener.fireError(
                    here, // TODO - conditional is not a boolean
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

            return elseBody.execute(symbolTable);
        }
    }
}
