package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class NativeDefineCallNode extends StatementNode {
    private final ExpressionNode map, key, value;

    public NativeDefineCallNode(
            final TextPosition position,
            final ExpressionNode map,
            final ExpressionNode key,
            final ExpressionNode value
    ) {
        super(position);

        this.map = map;
        this.key = key;
        this.value = value;
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
