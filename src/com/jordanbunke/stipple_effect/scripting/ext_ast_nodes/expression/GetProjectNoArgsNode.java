package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;

public final class GetProjectNoArgsNode extends SEExtExpressionNode {
    public static final String NAME = "get_project";

    public GetProjectNoArgsNode(
            final TextPosition position,
            final ExpressionNode[] args
    ) {
        super(position, args);
    }

    @Override
    public SEContext evaluate(final SymbolTable symbolTable) {
        return StippleEffect.get().getContext();
    }

    @Override
    public ProjectTypeNode getType(SymbolTable symbolTable) {
        return ProjectTypeNode.get();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
