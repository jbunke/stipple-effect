package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.declaration.DeclarationNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class IteratorLoopNode extends StatementNode {
    private final DeclarationNode declaration;
    private final ExpressionNode collection;
    private final StatementNode loopBody;

    public IteratorLoopNode(
            final TextPosition position,
            final DeclarationNode declaration,
            final ExpressionNode collection,
            final StatementNode loopBody
    ) {
        super(position);

        this.declaration = declaration;
        this.collection = collection;
        this.loopBody = loopBody;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }

    @Override
    public void execute(final SymbolTable symbolTable) {
        // TODO
    }
}
