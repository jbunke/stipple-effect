package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.rng;

import com.jordanbunke.delta_time.utility.math.RNG;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class ProbabilityNode extends ExpressionNode {
    private final ExpressionNode p;

    public ProbabilityNode(
            final TextPosition position,
            final ExpressionNode p
    ) {
        super(position);

        this.p = p;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        p.semanticErrorCheck(symbolTable);

        final TypeNode pType = p.getType(symbolTable);
        final BaseTypeNode floatType = TypeNode.getFloat();

        if (!pType.equals(floatType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    p.getPosition(), "Probability p",
                    floatType.toString(), pType.toString());
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        return RNG.prob((double) p.evaluate(symbolTable));
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getBool();
    }

    @Override
    public String toString() {
        return "prob(" + p + ")";
    }
}
