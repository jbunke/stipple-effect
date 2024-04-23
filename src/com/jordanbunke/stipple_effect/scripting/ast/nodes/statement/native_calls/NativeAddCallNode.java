package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class NativeAddCallNode extends StatementNode {
    private final ExpressionNode collection, toAdd, index;

    public NativeAddCallNode(
            final TextPosition position,
            final ExpressionNode collection,
            final ExpressionNode toAdd,
            final ExpressionNode index
    ) {
        super(position);

        this.collection = collection;
        this.toAdd = toAdd;
        this.index = index;
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
