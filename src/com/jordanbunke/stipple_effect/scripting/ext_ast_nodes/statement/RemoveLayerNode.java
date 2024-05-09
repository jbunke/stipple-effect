package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;

public final class RemoveLayerNode extends CondProjectOpNode {
    public static final String NAME = "remove_layer";

    public RemoveLayerNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args);
    }

    @Override
    protected boolean condition(final SEContext project) {
        return project.getState().getLayers().size() > 1;
    }

    @Override
    protected String attempt() {
        return "remove the current layer from the project";
    }

    @Override
    protected String failReason(final SEContext project) {
        return "the project only has a single layer";
    }

    @Override
    protected void operation(final SEContext project) {
        project.removeLayer();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
