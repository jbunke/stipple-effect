package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.global.GlobalStatementNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

import java.util.Arrays;

public final class ResizeNode extends GlobalStatementNode {
    public static final String NAME = "resize";

    public ResizeNode(
            final TextPosition position,
            final ExpressionNode[] args
    ) {
        super(position, args, ProjectTypeNode.get(),
                TypeNode.getInt(), TypeNode.getInt());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Object[] dims = Arrays.stream(getArgs())
                .map(a -> a.evaluate(symbolTable))
                .toArray(Object[]::new);
        final SEContext project = (SEContext) dims[0];
        final int w = (int) dims[1], h = (int) dims[2];

        if (w <= 0 || w > Constants.MAX_CANVAS_W ||
                h <= 0 || h > Constants.MAX_CANVAS_H)
            StatusUpdates.scriptActionNotPermitted("resize the project",
                    "the supplied dimensions are invalid: width = " +
                            w + ", height = " + h, getPosition());
        else
            project.resize(w, h);

        return FuncControlFlow.cont();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
