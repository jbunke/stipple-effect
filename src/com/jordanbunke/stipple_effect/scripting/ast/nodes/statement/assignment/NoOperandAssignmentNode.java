package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment;

import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.AssignableNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
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
        super.semanticErrorCheck(symbolTable);

        final TypeNode assignableType = getAssignable().getType(symbolTable);
        final SimpleTypeNode
                intType = new SimpleTypeNode(SimpleTypeNode.Type.INT);

        if (!assignableType.equals(intType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.VAR_NOT_INT,
                    getPosition(), assignableType.toString());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final int value = (int) getAssignable().evaluate(symbolTable);
        getAssignable().update(symbolTable, value + (increment ? 1 : -1));

        return FuncControlFlow.cont();
    }

    @Override
    public String toString() {
        return getAssignable() + (increment ? "++" : "--") + ";";
    }
}
