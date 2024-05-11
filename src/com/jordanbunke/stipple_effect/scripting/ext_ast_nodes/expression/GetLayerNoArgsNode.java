package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public final class GetLayerNoArgsNode extends GetLayerNode {
    public GetLayerNoArgsNode(
            final TextPosition position,
            final ExpressionNode[] args
    ) {
        super(position, args);
    }

    @Override
    public LayerRep evaluate(final SymbolTable symbolTable) {
        final SEContext project = StippleEffect.get().getContext();

        return new LayerRep(project, project.getState().getLayerEditIndex());
    }
}
