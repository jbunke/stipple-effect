package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;

public final class IsSelectedNode extends SEExtExpressionNode {
    public static final String NAME = "is_selected";

    public IsSelectedNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, ProjectTypeNode.get(),
                TypeNode.getInt(), TypeNode.getInt());
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        final Object[] vs = getValues(symbolTable);
        final SEContext project = (SEContext) vs[0];
        final int x = (int) vs[1], y = (int) vs[2];

        return project.getState().getSelection().contains(new Coord2D(x, y));
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
