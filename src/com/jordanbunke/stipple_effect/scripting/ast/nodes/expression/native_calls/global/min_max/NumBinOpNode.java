package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.min_max;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.function.BinaryOperator;

public abstract class NumBinOpNode extends ExpressionNode {
    private final ExpressionNode a, b;
    private final BinaryOperator<Integer> intOp;
    private final BinaryOperator<Double> floatOp;


    public NumBinOpNode(
            final TextPosition position,
            final ExpressionNode a, final ExpressionNode b,
            final BinaryOperator<Integer> intOp,
            final BinaryOperator<Double> floatOp
    ) {
        super(position);

        this.a = a;
        this.b = b;

        this.intOp = intOp;
        this.floatOp = floatOp;
    }

    @Override
    public final void semanticErrorCheck(final SymbolTable symbolTable) {
        a.semanticErrorCheck(symbolTable);
        b.semanticErrorCheck(symbolTable);

        final TypeNode aType = a.getType(symbolTable),
                bType = b.getType(symbolTable);

        if (!aType.equals(bType))
            ScriptErrorLog.fireError(ScriptErrorLog.Message.ARG_NOT_TYPE,
                    getPosition(), "Second " + callName(),
                    aType.toString(), bType.toString());
        if (!aType.isNum())
            ScriptErrorLog.fireError(ScriptErrorLog.Message.NAN,
                    a.getPosition(), "First " + callName() + " argument",
                    aType.toString());
    }

    @Override
    public final Number evaluate(final SymbolTable symbolTable) {
        final Object aVal = a.evaluate(symbolTable),
                bVal = b.evaluate(symbolTable);

        if (aVal instanceof Integer ai && bVal instanceof Integer bi)
            return intOp.apply(ai, bi);
        else if (aVal instanceof Double ad && bVal instanceof Double bd)
            return floatOp.apply(ad, bd);

        return null;
    }

    @Override
    public final TypeNode getType(final SymbolTable symbolTable) {
        return a.getType(symbolTable);
    }

    @Override
    public final String toString() {
        return callName() + "(" + a + ", " + b + ")";
    }

    protected abstract String callName();
}
