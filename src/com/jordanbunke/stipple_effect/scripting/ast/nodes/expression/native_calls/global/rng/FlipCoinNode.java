package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.rng;

import com.jordanbunke.delta_time.utility.math.RNG;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class FlipCoinNode extends ExpressionNode {
    public FlipCoinNode(TextPosition position) {
        super(position);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {}

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        return RNG.flipCoin();
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getBool();
    }

    @Override
    public String toString() {
        return "flip_coin()";
    }
}
