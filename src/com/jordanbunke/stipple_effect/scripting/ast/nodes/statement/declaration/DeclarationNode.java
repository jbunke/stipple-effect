package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.declaration;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.IdentifierNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;

public sealed class DeclarationNode extends StatementNode
        permits InitializationNode {
    private final boolean mutable;
    private final ScrippleTypeNode type;
    private final IdentifierNode ident;

    public DeclarationNode(
            final TextPosition position, final boolean mutable,
            final ScrippleTypeNode type, final IdentifierNode ident
    ) {
        super(position);

        this.mutable = mutable;
        this.type = type;
        this.ident = ident;
    }

    public boolean isMutable() {
        return mutable;
    }

    public String getIdent() {
        return ident.getName();
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        symbolTable.put(ident.getName(), new Variable(mutable, type));

        return FuncControlFlow.cont();
    }

    public ScrippleTypeNode getType() {
        return type;
    }
}
