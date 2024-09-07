package com.jordanbunke.stipple_effect.scripting.delegators;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.IllegalExpressionNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.math.MathFloatToFloatNode;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.EnumUtils;

import static com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.math.MathFloatToFloatNode.Function;

public final class MathNodeDelegator {
    public static ExpressionNode expression(
            final TextPosition position, final String fID,
            final ExpressionNode[] args
    ) {
        if (EnumUtils.matches(fID, Function.class, Function::toString))
            return new MathFloatToFloatNode(position,
                    Function.valueOf(fID.toUpperCase()), args);

        return new IllegalExpressionNode(position, "$" +
                Constants.MATH_NAMESPACE + " does not define a function \"" +
                fID + "()\"");
    }
}
