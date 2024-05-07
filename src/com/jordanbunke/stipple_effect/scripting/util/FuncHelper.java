package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HelperFuncNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Arrays;

public final class FuncHelper {
    public static Object evaluate(
            final HelperFuncNode func,
            final ExpressionNode[] args,
            final SymbolTable symbolTable
    ) {
        final SymbolTable funcTable = symbolTable.getRoot().getChild(func);
        final Object[] argVals = Arrays.stream(args)
                .map(a -> a.evaluate(symbolTable)).toArray(Object[]::new);

        return func.execute(funcTable, argVals);
    }
}
