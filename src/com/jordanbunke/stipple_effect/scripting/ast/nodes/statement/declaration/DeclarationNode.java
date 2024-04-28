package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.declaration;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.IdentifierNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

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

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }

    @Override
    public void execute(final SymbolTable symbolTable) {
        // TODO
    }

    public ScrippleTypeNode getType() {
        return type;
    }
}
