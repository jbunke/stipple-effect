package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class ScriptFunctionNode extends ScrippleASTNode {

    private final MethodSignatureNode signature;
    private final StatementNode[] statements;

    public ScriptFunctionNode(
            final TextPosition position, final MethodSignatureNode signature,
            final StatementNode[] statements
    ) {
        super(position);

        this.signature = signature;
        this.statements = statements;
    }

    public Object execute(
            final SymbolTable symbolTable,
            final Object... args
    ) {
        // TODO - populate arguments into symbol table for parameters

        // program execution
        for (StatementNode statement : statements) {
            final FuncControlFlow potential = statement.execute(symbolTable);

            if (!potential.cont)
                return potential.value;
        }

        return null;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
        signature.semanticErrorCheck(symbolTable);

        for (StatementNode statement : statements)
            statement.semanticErrorCheck(symbolTable);
    }
}
