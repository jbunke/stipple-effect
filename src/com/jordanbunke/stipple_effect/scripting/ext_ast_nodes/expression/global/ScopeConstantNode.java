package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.utility.DialogVals;

public final class ScopeConstantNode extends ConstantNode {
    private final DialogVals.Scope scope;

    public ScopeConstantNode(
            final TextPosition position, final DialogVals.Scope scope
    ) {
        super(position);

        this.scope = scope;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        return scope.ordinal();
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getInt();
    }

    @Override
    protected String callName() {
        return scope.name();
    }
}
