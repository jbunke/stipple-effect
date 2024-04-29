package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class BodyStatementNode extends StatementNode {
    private final StatementNode[] statements;

    public BodyStatementNode(
            final TextPosition position,
            final StatementNode[] statements
    ) {
        super(position);

        this.statements = statements;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        final SymbolTable innerTable = new SymbolTable(this, symbolTable);

        for (StatementNode statement : statements)
            statement.semanticErrorCheck(innerTable);
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final SymbolTable innerTable = symbolTable.getChild(this);

        for (StatementNode statement : statements) {
            final FuncControlFlow potential = statement.execute(innerTable);

            if (!potential.cont)
                return potential;
        }

        return FuncControlFlow.cont();
    }
}
