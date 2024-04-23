package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class NativeRemoveCallNode extends StatementNode {
    private final ExpressionNode collection, valueOrIndex;

    public NativeRemoveCallNode(
            final TextPosition position,
            final ExpressionNode collection,
            final ExpressionNode valueOrIndex
    ) {
        super(position);

        this.collection = collection;
        this.valueOrIndex = valueOrIndex;
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
