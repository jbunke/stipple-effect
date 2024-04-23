package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class BodyStatementNode extends StatementNode {
    private final StatementNode[] contents;

    public BodyStatementNode(
            final TextPosition position,
            final StatementNode[] contents
    ) {
        super(position);

        this.contents = contents;
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
