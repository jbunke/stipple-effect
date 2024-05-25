package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.ScopedExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.LayerTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public abstract class LayerExpressionNode extends ScopedExpressionNode {
    LayerExpressionNode(
            final TextPosition position, final ExpressionNode scope,
            final ExpressionNode[] args, final TypeNode... expectedArgTypes
    ) {
        super(position, scope, LayerTypeNode.get(), args, expectedArgTypes);
    }

    protected final LayerRep layerRep(final SymbolTable symbolTable) {
        return (LayerRep) scope.evaluate(symbolTable);
    }

    protected final SELayer evalLayer(final SymbolTable symbolTable) {
        final LayerRep l = layerRep(symbolTable);

        return l.project().getState().getLayers().get(l.index());
    }
}
