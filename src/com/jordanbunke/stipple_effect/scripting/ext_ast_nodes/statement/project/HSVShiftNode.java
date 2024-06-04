package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.project;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.state.Operation;
import com.jordanbunke.stipple_effect.state.ProjectState;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.DialogVals;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

public final class HSVShiftNode extends ProjectStatementNode {
    public static final String NAME = "hsv_shift";

    public HSVShiftNode(
            final TextPosition position,
            final ExpressionNode scope, final ExpressionNode[] args
    ) {
        super(position, scope, args, TypeNode.getInt(),
                TypeNode.getBool(), TypeNode.getInt(),
                TypeNode.getFloat(), TypeNode.getFloat());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final SEContext project = getProject(symbolTable);

        final Object[] vs = arguments.getValues(symbolTable);
        final int scopeIndex = (int) vs[0], hShift = (int) vs[2];
        final boolean includeDisabled = (boolean) vs[1];
        final double sShift = (double) vs[3], vShift = (double) vs[4];
        final String attempt = "apply an HSV level shift";

        if (scopeIndex < 0 || scopeIndex >= DialogVals.Scope.values().length)
            StatusUpdates.scriptActionNotPermitted(attempt,
                    "the scope (" + scopeIndex +
                            ") is not a valid index for this enumeration",
                    arguments.args()[0].getPosition());
        else {
            if (hShift < Constants.MIN_HUE_SHIFT ||
                    hShift > Constants.MAX_HUE_SHIFT)
                StatusUpdates.scriptActionNotPermitted(attempt,
                        "the hue shift (" +
                                hShift + ") is out of bounds (" +
                                Constants.MIN_HUE_SHIFT + "<= h_shift <= " +
                                Constants.MAX_HUE_SHIFT + ")",
                        arguments.args()[2].getPosition());
            else if (sShift < Constants.MIN_SV_SCALE ||
                    sShift > Constants.MAX_SV_SCALE)
                StatusUpdates.scriptActionNotPermitted(attempt,
                        "the saturation shift (" + sShift +
                                ") is out of bounds (" +
                                Constants.MIN_SV_SCALE + "<= s_shift <= " +
                                Constants.MAX_SV_SCALE + ")",
                        arguments.args()[3].getPosition());
            else if (vShift < Constants.MIN_SV_SCALE ||
                    vShift > Constants.MAX_SV_SCALE)
                StatusUpdates.scriptActionNotPermitted(attempt,
                        "the value shift (" + vShift +
                                ") is out of bounds (" +
                                Constants.MIN_SV_SCALE + "<= v_shift <= " +
                                Constants.MAX_SV_SCALE + ")",
                        arguments.args()[3].getPosition());
            else {
                final DialogVals.Scope scope =
                        DialogVals.Scope.values()[scopeIndex];

                final DialogVals.Scope scopeWas = DialogVals.getScope();
                final boolean includeWas = DialogVals.isIncludeDisabledLayers();

                DialogVals.setScope(scope);
                DialogVals.setIncludeDisabledLayers(includeDisabled);

                DialogVals.setHueShift(hShift);
                DialogVals.setSatShift(sShift);
                DialogVals.setValueShift(vShift);

                final ProjectState res = project.prepHSVShift();
                project.getStateManager()
                        .performAction(res, Operation.EDIT_IMAGE);

                // reset to dialog values
                DialogVals.setScope(scopeWas);
                DialogVals.setIncludeDisabledLayers(includeWas);
            }
        }

        return FuncControlFlow.cont();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
