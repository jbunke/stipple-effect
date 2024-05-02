package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class HeadFuncNode extends ASTNode {

    private final MethodSignatureNode signature;
    private final StatementNode[] statements;

    public HeadFuncNode(
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
        signature.execute(symbolTable, args);

        // program execution
        for (StatementNode statement : statements) {
            final FuncControlFlow potential = statement.execute(symbolTable);

            if (!potential.cont)
                return potential.value;
        }

        return null;
    }

    public boolean paramsMatch(final TypeNode[] spec) {
        return signature.paramsMatch(spec);
    }

    public TypeNode getReturnType() {
        return signature.getReturnType();
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        signature.semanticErrorCheck(symbolTable);

        for (StatementNode statement : statements)
            statement.semanticErrorCheck(symbolTable);
    }

    @Override
    public String toString() {
        return "func" + signature;
    }
}
