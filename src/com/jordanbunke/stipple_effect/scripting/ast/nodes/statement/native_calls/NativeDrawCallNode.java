package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class NativeDrawCallNode extends StatementNode {
    private final ExpressionNode canvas, superimposed, x, y;

    public NativeDrawCallNode(
            final TextPosition position,
            final ExpressionNode canvas,
            final ExpressionNode superimposed,
            final ExpressionNode x, final ExpressionNode y
    ) {
        super(position);

        this.canvas = canvas;
        this.superimposed = superimposed;
        this.x = x;
        this.y = y;
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
