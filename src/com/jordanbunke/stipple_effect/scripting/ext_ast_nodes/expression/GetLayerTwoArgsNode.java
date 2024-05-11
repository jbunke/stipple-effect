package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

import java.util.List;

public final class GetLayerTwoArgsNode extends GetLayerNode {
    public GetLayerTwoArgsNode(
            final TextPosition position,
            final ExpressionNode[] args
    ) {
        super(position, args, ProjectTypeNode.get(), TypeNode.getInt());
    }

    @Override
    public LayerRep evaluate(final SymbolTable symbolTable) {
        final SEContext project =
                (SEContext) getArgs()[0].evaluate(symbolTable);
        final int index = (int) getArgs()[1].evaluate(symbolTable);

        final List<SELayer> layers = project.getState().getLayers();

        if (index < 0 || index >= layers.size()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.INDEX_OUT_OF_BOUNDS,
                    getArgs()[1].getPosition(), String.valueOf(index),
                    String.valueOf(layers.size()), String.valueOf(false));
            return null;
        }

        return new LayerRep(project, index);
    }
}
