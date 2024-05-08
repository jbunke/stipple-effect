package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public final class UnlinkFramesNode extends TrivialLayerOpNode {
    public static final String NAME = "unlink_frames";

    public UnlinkFramesNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args);
    }

    @Override
    protected void layerOp(final LayerRep layer) {
        layer.project().unlinkFramesInLayer(layer.index());
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
