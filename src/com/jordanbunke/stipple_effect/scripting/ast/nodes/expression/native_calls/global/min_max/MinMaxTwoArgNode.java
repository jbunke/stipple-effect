package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.min_max;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class MinMaxTwoArgNode extends ExpressionNode {
    private final boolean isMax;
    private final ExpressionNode a, b;

    public MinMaxTwoArgNode(
            final TextPosition position, final boolean isMax,
            final ExpressionNode a, final ExpressionNode b
    ) {
        super(position);

        this.isMax = isMax;
        this.a = a;
        this.b = b;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
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
    public Number evaluate(final SymbolTable symbolTable) {
        final Object aVal = a.evaluate(symbolTable),
                bVal = b.evaluate(symbolTable);

        if (aVal instanceof Integer ai && bVal instanceof Integer bi)
            return isMax ? Math.max(ai, bi) : Math.min(ai, bi);
        else if (aVal instanceof Double ad && bVal instanceof Double bd)
            return isMax ? Math.max(ad, bd) : Math.min(ad, bd);

        return null;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return a.getType(symbolTable);
    }

    @Override
    public String toString() {
        return callName() + "(" + a + ", " + b + ")";
    }

    private String callName() {
        return isMax ? "max" : "min";
    }
}
