package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public final class IsLinkedNode extends TrivialLayerGetterNode {
    public static final String NAME = "is_linked";

    public IsLinkedNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args);
    }

    @Override
    protected Boolean getter(final LayerRep layer) {
        return layer.project().getState().getLayers()
                .get(layer.index()).areFramesLinked();
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
