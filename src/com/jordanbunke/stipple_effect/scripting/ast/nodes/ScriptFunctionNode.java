package com.jordanbunke.stipple_effect.scripting.ast.nodes;

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

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }
}
