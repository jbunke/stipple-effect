package com.jordanbunke.stipple_effect.scripting.ast.nodes.function;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.ASTNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public sealed abstract class FuncNode extends ASTNode
        permits HeadFuncNode, HelperFuncNode {
    private final FuncSignatureNode signature;
    private final StatementNode body;

    FuncNode(
            final TextPosition position,
            final FuncSignatureNode signature,
            final StatementNode body
    ) {
        super(position);

        this.signature = signature;
        this.body = body;
    }

    public Object execute(
            final SymbolTable symbolTable, final Object[] args
    ) {
        signature.execute(symbolTable, args);
        final Object res = body.execute(symbolTable).value;

        if (res == null && signature.getReturnType() != null)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    getPosition(), "Function did not return a value;" +
                            " expected return type \"" +
                            signature.getReturnType() + "\"");

        return res;
    }

    public boolean paramsMatch(final TypeNode[] spec) {
        return signature.paramsMatch(spec);
    }

    public TypeNode getReturnType() {
        return signature.getReturnType();
    }

    public FuncTypeNode getType() {
        return signature.getType();
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        signature.semanticErrorCheck(symbolTable);
        body.semanticErrorCheck(symbolTable);
    }

    @Override
    public String toString() {
        return signature.toString();
    }
}
