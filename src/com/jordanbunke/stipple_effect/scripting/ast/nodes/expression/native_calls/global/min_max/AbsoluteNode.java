package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.min_max;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class AbsoluteNode extends ExpressionNode {
    private final ExpressionNode n;

    public AbsoluteNode(
            final TextPosition position,
            final ExpressionNode n
    ) {
        super(position);

        this.n = n;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        n.semanticErrorCheck(symbolTable);

        final TypeNode nType = n.getType(symbolTable);

        if (!nType.isNum())
            ScriptErrorLog.fireError(ScriptErrorLog.Message.NAN,
                    n.getPosition(), "abs() argument", nType.toString());
    }

    @Override
    public Number evaluate(final SymbolTable symbolTable) {
        final Object val = n.evaluate(symbolTable);

        if (val instanceof Integer i)
            return Math.abs(i);
        else if (val instanceof Double d)
            return Math.abs(d);

        return null;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return n.getType(symbolTable);
    }

    @Override
    public String toString() {
        return "abs(" + n + ")";
    }
}
