package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.project;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class IsAnimNode extends ProjectExpressionNode {
    public static final String NAME = "is_anim";

    public IsAnimNode(
            final TextPosition position,
            final ExpressionNode scope, final ExpressionNode[] args
    ) {
        super(position, scope, args);
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        return getProject(symbolTable).getState().getFrameCount() > 1;
    }

    @Override
    public BaseTypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getBool();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
