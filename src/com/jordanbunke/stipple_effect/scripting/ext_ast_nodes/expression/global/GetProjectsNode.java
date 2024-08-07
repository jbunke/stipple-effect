package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;

public final class GetProjectsNode extends GlobalExpressionNode {
    public static final String NAME = "get_projects";

    public GetProjectsNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args);
    }

    @Override
    public ScriptArray evaluate(final SymbolTable symbolTable) {
        return new ScriptArray(StippleEffect.get().getContexts()
                .stream().map(p -> p));
    }

    @Override
    public CollectionTypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.arrayOf(ProjectTypeNode.get());
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
