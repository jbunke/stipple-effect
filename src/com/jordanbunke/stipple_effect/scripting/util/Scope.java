package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public record Scope(ExpressionNode caller, TypeNode expectedType) {
    public Object evaluate(final SymbolTable symbolTable) {
        return caller.evaluate(symbolTable);
    }

    public void semanticErrorCheck(
            final SymbolTable symbolTable, final TextPosition position
    ) {
        final TypeNode type = caller.getType(symbolTable);

        if (!type.equals(expectedType))
            ScriptErrorLog.fireError(ScriptErrorLog.Message.ARG_NOT_TYPE,
                    position, "function caller", expectedType.toString(),
                    type.toString());
    }

    @Override
    public String toString() {
        return caller.toString() + ".";
    }
}
