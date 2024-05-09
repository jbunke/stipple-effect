package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public final class LinkFramesNode extends TrivialLayerOpNode {
    public static final String NAME = "link_frames";

    public LinkFramesNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args);
    }

    @Override
    protected void operation(
            final LayerRep layer, final SymbolTable symbolTable
    ) {
        layer.project().linkFramesInLayer(layer.index());
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
