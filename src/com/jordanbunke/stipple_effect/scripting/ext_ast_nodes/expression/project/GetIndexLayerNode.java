package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.project;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

import java.util.List;

public final class GetIndexLayerNode extends GetLayerNode {
    public GetIndexLayerNode(
            final TextPosition position,
            final ExpressionNode scope, final ExpressionNode[] args
    ) {
        super(position, scope, args, TypeNode.getInt());
    }

    @Override
    public LayerRep evaluate(final SymbolTable symbolTable) {
        final SEContext project = getProject(symbolTable);
        final int index = (int) arguments.args()[0].evaluate(symbolTable);

        final List<SELayer> layers = project.getState().getLayers();

        if (index < 0 || index >= layers.size()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.INDEX_OUT_OF_BOUNDS,
                    arguments.args()[0].getPosition(), String.valueOf(index),
                    String.valueOf(layers.size()), String.valueOf(false));
            return null;
        }

        return new LayerRep(project, index);
    }
}
