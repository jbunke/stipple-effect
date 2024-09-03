package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class IsLinkedNode extends LayerExpressionNode {
    public static final String NAME = "is_linked";

    public IsLinkedNode(
            final TextPosition position, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(position, scope, args);
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        return layerRep(symbolTable).get().areCelsLinked();
    }

    @Override
    public BaseTypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getBool();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
