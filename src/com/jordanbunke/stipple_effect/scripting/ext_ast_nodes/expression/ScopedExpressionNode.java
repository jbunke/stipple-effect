package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.util.Arguments;
import com.jordanbunke.stipple_effect.scripting.util.Scope;

public abstract class ScopedExpressionNode extends ExtExpressionNode {
    protected final Scope scope;

    public ScopedExpressionNode(
            final TextPosition position,
            final ExpressionNode scope, final TypeNode expectedScopeType,
            final ExpressionNode[] args, final TypeNode... expectedArgTypes
    ) {
        super(position, new Arguments(args, expectedArgTypes));

        this.scope = new Scope(scope, expectedScopeType);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        super.semanticErrorCheck(symbolTable);
        scope.semanticErrorCheck(symbolTable, getPosition());
    }

    @Override
    public String toString() {
        return scope + callName() + arguments;
    }
}
