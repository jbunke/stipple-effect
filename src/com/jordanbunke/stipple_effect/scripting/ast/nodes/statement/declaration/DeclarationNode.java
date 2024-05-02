package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.declaration;

import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.IdentifierNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;

public sealed class DeclarationNode extends StatementNode
        permits InitializationNode {
    private final boolean mutable;
    private final TypeNode type;
    private final IdentifierNode ident;

    public DeclarationNode(
            final TextPosition position, final boolean mutable,
            final TypeNode type, final IdentifierNode ident
    ) {
        super(position);

        this.mutable = mutable;
        this.type = type;
        this.ident = ident;
    }

    public final boolean isMutable() {
        return mutable;
    }

    public final String getIdent() {
        return ident.getName();
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // do not check identifier semantics here
        type.semanticErrorCheck(symbolTable);

        if (symbolTable.hasVarAtLevel(getIdent()))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.VAR_ALREADY_DEFINED,
                    ident.getPosition(), getIdent());
        else
            symbolTable.put(getIdent(), new Variable(mutable, type));
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        symbolTable.put(ident.getName(), new Variable(mutable, type));

        return FuncControlFlow.cont();
    }

    public TypeNode getType() {
        return type;
    }

    @Override
    public String toString() {
        return (mutable ? "" : "final ") + type + " " + ident;
    }
}
