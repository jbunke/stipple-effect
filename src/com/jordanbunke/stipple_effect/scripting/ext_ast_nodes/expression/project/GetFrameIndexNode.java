package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.project;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class GetFrameIndexNode extends ProjectExpressionNode {
    public static final String NAME = "get_frame_index";

    public GetFrameIndexNode(
            final TextPosition position,
            final ExpressionNode scope, final ExpressionNode[] args
    ) {
        super(position, scope, args);
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        return getProject(symbolTable).getState().getFrameIndex();
    }

    @Override
    public BaseTypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getInt();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
