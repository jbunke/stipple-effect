package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Arrays;

public final class CollectionHelper {
    public static TypeNode getConcreteType(
            final ExpressionNode[] elements, final SymbolTable symbolTable
    ) {
        return Arrays.stream(elements)
                .map(e -> e.getType(symbolTable))
                .filter(t -> t instanceof BaseTypeNode st &&
                        !st.isWildcard())
                .reduce((a, b) -> a).orElse(
                        elements[0].getType(symbolTable));
    }

    public static void checkInteralTypeConsistency(
            final ExpressionNode[] elements, final SymbolTable symbolTable,
            final String initDescriptor
    ) {
        final TypeNode firstElemType = elements[0].getType(symbolTable);

        for (int i = 1; i < elements.length; i++) {
            final TypeNode type = elements[i].getType(symbolTable);

            if (!type.equals(firstElemType))
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.INCONSISTENT_COL_TYPES,
                        elements[i].getPosition(),
                        String.valueOf(i), initDescriptor,
                        String.valueOf(firstElemType),
                        String.valueOf(type));
        }
    }
}
