package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global.GlobalExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.LayerTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public abstract class GetLayerNode extends GlobalExpressionNode {
    public static final String NAME = "get_layer";

    public GetLayerNode(
            final TextPosition position,
            final ExpressionNode[] args,
            final TypeNode... expectedTypes
    ) {
        super(position, args, expectedTypes);
    }

    @Override
    public abstract LayerRep evaluate(SymbolTable symbolTable);

    @Override
    public final LayerTypeNode getType(final SymbolTable symbolTable) {
        return LayerTypeNode.get();
    }

    @Override
    protected final String callName() {
        return NAME;
    }
}
