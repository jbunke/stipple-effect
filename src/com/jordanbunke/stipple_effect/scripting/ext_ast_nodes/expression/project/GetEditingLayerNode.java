package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.project;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public final class GetEditingLayerNode extends GetLayerNode {
    public GetEditingLayerNode(
            final TextPosition position,
            final ExpressionNode scope, final ExpressionNode[] args
    ) {
        super(position, scope, args);
    }

    @Override
    public LayerRep evaluate(final SymbolTable symbolTable) {
        final SEContext project = getProject(symbolTable);

        return new LayerRep(project, project.getState().getLayerEditIndex());
    }
}
