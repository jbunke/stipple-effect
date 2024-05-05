package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement;

import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return switch (statements.length) {
            case 0 -> "";
            case 1 -> statements[0].toString();
            default -> "{\n" + Arrays.stream(statements)
                    .map(sn -> "\t" + sn)
                    .reduce((a, b) -> a + "\n" + b).orElse("") + "\n}";
        };
    }
}
