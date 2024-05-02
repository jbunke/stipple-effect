package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment;

import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.AssignableNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.IdentifierNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;

public abstract class AssignmentNode extends StatementNode {
    private final AssignableNode assignable;

    public AssignmentNode(
            final TextPosition position, final AssignableNode assignable
    ) {
        super(position);

        this.assignable = assignable;
    }

    public AssignableNode getAssignable() {
        return assignable;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        assignable.semanticErrorCheck(symbolTable);

        final Variable var = symbolTable.get(assignable.getName());

        if (assignable instanceof IdentifierNode &&
                var != null && !var.isMutable())
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.REASSIGN_FINAL,
                    assignable.getPosition(), assignable.getName());
    }
}
