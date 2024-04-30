package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.declaration;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.IdentifierNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class InitializationNode extends DeclarationNode {
    private final ExpressionNode value;

    public InitializationNode(
            final TextPosition position, final boolean mutable,
            final TypeNode type, final IdentifierNode ident,
            final ExpressionNode value
    ) {
        super(position, mutable, type, ident);

        this.value = value;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        value.semanticErrorCheck(symbolTable);

        super.semanticErrorCheck(symbolTable);

        final TypeNode initType = value.getType(symbolTable);

        if (!initType.equals(getType()))
            ScrippleErrorListener.fireError(
                    here, // TODO - var type mismatch
                    value.getPosition(), getType().toString(),
                    initType.toString());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        super.execute(symbolTable);

        final Object v = value.evaluate(symbolTable);

        symbolTable.update(getIdent(), v);

        return FuncControlFlow.cont();
    }
}
