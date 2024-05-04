package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class HeadFuncNode extends ASTNode {

    private final MethodSignatureNode signature;
    private final StatementNode body;

    public HeadFuncNode(
            final TextPosition position,
            final MethodSignatureNode signature,
            final StatementNode body
    ) {
        super(position);

        this.signature = signature;
        this.body = body;
    }

    public Object execute(
            final SymbolTable symbolTable,
            final Object... args
    ) {
        signature.execute(symbolTable, args);
        return body.execute(symbolTable).value;
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
        body.semanticErrorCheck(symbolTable);
    }

    @Override
    public String toString() {
        return "func" + signature;
    }
}
