package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public final class GetLayerOneArgNode extends GetLayerNode {
    public GetLayerOneArgNode(
            final TextPosition position,
            final ExpressionNode[] args
    ) {
        super(position, args, ProjectTypeNode.get());
    }

    @Override
    public LayerRep evaluate(final SymbolTable symbolTable) {
        final SEContext project =
                (SEContext) getArgs()[0].evaluate(symbolTable);

        return new LayerRep(project, project.getState().getLayerEditIndex());
    }
}
