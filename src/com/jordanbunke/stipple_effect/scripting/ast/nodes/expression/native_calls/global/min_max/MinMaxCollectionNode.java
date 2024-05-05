package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.min_max;

import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptCollection;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.stream.Stream;

public final class MinMaxCollectionNode extends ExpressionNode {
    private final boolean isMax;
    private final ExpressionNode col;

    public MinMaxCollectionNode(
            final TextPosition position,
            final boolean isMax, final ExpressionNode col
    ) {
        super(position);

        this.isMax = isMax;
        this.col = col;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        col.semanticErrorCheck(symbolTable);

        final TypeNode colType = col.getType(symbolTable);

        if (!(colType instanceof CollectionTypeNode c))
            ScriptErrorLog.fireError(ScriptErrorLog.Message.ARG_NOT_TYPE,
                    col.getPosition(), "Collection",
                    "array - []\", \"list - <>\" or \"set - {}",
                    String.valueOf(colType));
        else if (!c.getElementType().isNum())
            ScriptErrorLog.fireError(ScriptErrorLog.Message.NAN,
                    col.getPosition(), "Collection element type",
                    c.getElementType().toString());
    }

    @Override
    public Number evaluate(final SymbolTable symbolTable) {
        final ScriptCollection c =
                (ScriptCollection) col.evaluate(symbolTable);
        final CollectionTypeNode colType =
                (CollectionTypeNode) col.getType(symbolTable);
        final TypeNode elemType = colType.getElementType();

        if (c.size() == 0) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CANNOT_REDUCE_EMPTY_COL,
                    col.getPosition());

            return null;
        }

        final Stream<Object> elements = c.stream();

        if (elemType.equals(TypeNode.getInt()))
            return elements.map(e -> (Integer) e)
                    .reduce(intID(), this::compute);
        else
            return elements.map(e -> (Double) e)
                    .reduce(doubleID(), this::compute);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        final TypeNode colType = col.getType(symbolTable);

        if (colType instanceof CollectionTypeNode c)
            return c.getElementType();

        return TypeNode.wildcard();
    }

    @Override
    public String toString() {
        return (isMax ? "max" : "min") + "(" + col + ")";
    }

    private int intID() {
        return isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    }

    private double doubleID() {
        return isMax ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

    private int compute(final int a, final int b) {
        return isMax ? Math.max(a, b) : Math.min(a, b);
    }

    private double compute(final double a, final double b) {
        return isMax ? Math.max(a, b) : Math.min(a, b);
    }
}
