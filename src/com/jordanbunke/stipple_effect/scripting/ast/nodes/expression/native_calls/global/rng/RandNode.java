package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.rng;

import com.jordanbunke.delta_time.utility.math.RNG;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class RandNode extends ExpressionNode {
    public RandNode(
            final TextPosition position
    ) {
        super(position);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {}

    @Override
    public Double evaluate(final SymbolTable symbolTable) {
        return RNG.randomInRange(0d, 1d);
    }

    @Override
    public TypeNode getType(SymbolTable symbolTable) {
        return TypeNode.getFloat();
    }

    @Override
    public String toString() {
        return "rand()";
    }
}
