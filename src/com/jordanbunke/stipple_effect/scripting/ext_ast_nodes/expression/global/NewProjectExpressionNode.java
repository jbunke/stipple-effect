package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.Arrays;

public final class NewProjectExpressionNode extends GlobalExpressionNode {
    public static final String NAME = "new_project";

    public NewProjectExpressionNode(
            final TextPosition position,
            final ExpressionNode[] args
    ) {
        super(position, args, TypeNode.getInt(), TypeNode.getInt());
    }

    @Override
    public SEContext evaluate(final SymbolTable symbolTable) {
        final Integer[] dims = Arrays.stream(arguments.args())
                .map(a -> (Integer) a.evaluate(symbolTable))
                .toArray(Integer[]::new);
        final int w = dims[0], h = dims[1];

        if (w <= 0 || w > Constants.MAX_CANVAS_W) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    getPosition(), "Width argument " + w +
                            " is out of bounds: 0 < w <= " +
                            Constants.MAX_CANVAS_W);
            return null;
        } else if (h <= 0 || h > Constants.MAX_CANVAS_H) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    getPosition(), "Height argument " + h +
                            " is out of bounds: 0 < h <= " +
                            Constants.MAX_CANVAS_H);
            return null;
        } else
            StippleEffect.get().addContext(new SEContext(w, h), true);

        return StippleEffect.get().getContext();
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
