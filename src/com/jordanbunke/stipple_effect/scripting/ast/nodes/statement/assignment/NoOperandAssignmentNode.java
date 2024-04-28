package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.AssignableNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class NoOperandAssignmentNode extends AssignmentNode {
    private final boolean increment;

    public NoOperandAssignmentNode(
            final TextPosition position,
            final AssignableNode assignable,
            final boolean increment
    ) {
        super(position, assignable);

        this.increment = increment;
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
