package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.PropertyNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.LayerTypeNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public final class LayerProjectPropertyNode extends PropertyNode {
    public static final String NAME = "project";

    public LayerProjectPropertyNode(
            final TextPosition position, final ExpressionNode scope
    ) {
        super(position, scope, LayerTypeNode.get());
    }

    @Override
    public SEContext evaluate(final SymbolTable symbolTable) {
        final LayerRep l = (LayerRep) scope.evaluate(symbolTable);
        return l.project();
    }

    @Override
    public ProjectTypeNode getType(final SymbolTable symbolTable) {
        return ProjectTypeNode.get();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
