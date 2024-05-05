package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.rng;

import com.jordanbunke.delta_time.utility.math.RNG;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.min_max.NumBinOpNode;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class RandTwoArgNode extends NumBinOpNode {
    public RandTwoArgNode(
            final TextPosition position,
            final ExpressionNode min, final ExpressionNode max
    ) {
        super(position, min, max, RNG::randomInRange, RNG::randomInRange);
    }

    @Override
    protected String callName() {
        return "rand";
    }
}
