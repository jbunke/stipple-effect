package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.min_max;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class MinMaxTwoArgNode extends NumBinOpNode {
    private final boolean isMax;

    public MinMaxTwoArgNode(
            final TextPosition position, final boolean isMax,
            final ExpressionNode a, final ExpressionNode b
    ) {
        super(position, a, b,
                isMax ? Math::max : Math::min,
                isMax ? Math::max : Math::min);

        this.isMax = isMax;
    }

    protected String callName() {
        return isMax ? "max" : "min";
    }
}
