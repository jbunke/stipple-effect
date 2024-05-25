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
import com.jordanbunke.stipple_effect.utility.DialogVals;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

import java.util.Arrays;

public final class StitchNode extends GlobalStatementNode {
    public static final String NAME = "stitch";

    public StitchNode(
            final TextPosition position,
            final ExpressionNode[] args
    ) {
        super(position, args, ProjectTypeNode.get(),
                TypeNode.getInt(), TypeNode.getBool());
    }

    @Override
    public FuncControlFlow execute(SymbolTable symbolTable) {
        final Object[] dims = Arrays.stream(getArgs())
                .map(a -> a.evaluate(symbolTable))
                .toArray(Object[]::new);
        final SEContext project = (SEContext) dims[0];
        final int fpd = (int) dims[1];
        final boolean horizontal = (boolean) dims[2];

        if (fpd <= 0)
            StatusUpdates.scriptActionNotPermitted(
                    "stitch the project's frames together",
                    "the number of frames per " +
                            (horizontal ? "row" : "column") + " was negative",
                    getArgs()[1].getPosition());
        else {
            final int fc = project.getState().getFrameCount(),
                    w = project.getState().getImageWidth(),
                    h = project.getState().getImageHeight(),
                    sw = w * (horizontal ? fpd : (fc / fpd)),
                    sh = h * (horizontal ? (fc / fpd) : fpd);

            if (sw <= 0 || w > Constants.MAX_CANVAS_W ||
                    sh <= 0 || h > Constants.MAX_CANVAS_H)
                StatusUpdates.scriptActionNotPermitted(
                        "stitch the project's frames together",
                        "the prospective dimensions are invalid: width = " +
                                sw + ", height = " + sh, getPosition());
            else {
                DialogVals.setSequenceOrder(horizontal
                        ? DialogVals.SequenceOrder.HORIZONTAL
                        : DialogVals.SequenceOrder.VERTICAL);
                DialogVals.setFramesPerDim(fpd);

                project.stitch();
            }
        }

        return FuncControlFlow.cont();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
