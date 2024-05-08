package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

import java.util.Arrays;

public final class PadNode extends SEExtStatementNode {
    public static final String NAME = "pad";

    public PadNode(
            final TextPosition position,
            final ExpressionNode[] args
    ) {
        super(position, args, ProjectTypeNode.get(),
                TypeNode.getInt(), TypeNode.getInt(),
                TypeNode.getInt(), TypeNode.getInt());
    }

    @Override
    public FuncControlFlow execute(SymbolTable symbolTable) {
        final Object[] dims = Arrays.stream(getArgs())
                .map(a -> a.evaluate(symbolTable))
                .toArray(Object[]::new);
        final SEContext project = (SEContext) dims[0];
        final int l = (int) dims[1], r = (int) dims[2],
                t = (int) dims[3], b = (int) dims[4];

        final int w = project.getState().getImageWidth(),
                h = project.getState().getImageHeight(),
                pw = l + w + r, ph = t + h + b;

        if (pw <= 0 || pw > Constants.MAX_CANVAS_W ||
                ph <= 0 || ph > Constants.MAX_CANVAS_H)
            StatusUpdates.scriptActionNotPermitted("pad the canvas dimensions",
                    "the prospective dimensions are invalid: width = " +
                            pw + ", height = " + ph, getPosition());
        else
            project.pad(l, r, t, b);

        return FuncControlFlow.cont();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
