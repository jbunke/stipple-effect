package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public final class NativeAddCallNode extends StatementNode {
    private final ExpressionNode collection, toAdd, index;

    public NativeAddCallNode(
            final TextPosition position,
            final ExpressionNode collection,
            final ExpressionNode toAdd,
            final ExpressionNode index
    ) {
        super(position);

        this.collection = collection;
        this.toAdd = toAdd;
        this.index = index;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        collection.semanticErrorCheck(symbolTable);
        toAdd.semanticErrorCheck(symbolTable);
        index.semanticErrorCheck(symbolTable);

        final TypeNode
                colType = collection.getType(symbolTable),
                elemType = (colType instanceof CollectionTypeNode ct)
                        ? ct.getElementType() : null,
                addType = toAdd.getType(symbolTable),
                iType = index.getType(symbolTable);

        if (elemType == null)
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.OWNER_NOT_COLLECTION,
                    collection.getPosition(), colType.toString());
        else if (!elemType.equals(addType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.ELEMENT_DOES_NOT_MATCH_COL,
                    toAdd.getPosition(),
                    elemType.toString(), addType.toString());
        if (!iType.equals(new SimpleTypeNode(SimpleTypeNode.Type.INT)))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.INDEX_NOT_INT,
                    index.getPosition(), iType.toString());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        // TODO - resolve warning
        final Collection<Object> c =
                (Collection<Object>) collection.evaluate(symbolTable);
        final Object element = toAdd.evaluate(symbolTable);
        final Optional<Integer> i = index != null
                ? Optional.of((Integer) index.evaluate(symbolTable))
                : Optional.empty();

        if (i.isPresent() && c instanceof List<Object> l) {
            final int index = i.get();

            try {
                l.add(index, element);
            } catch (IllegalArgumentException e) {
                ScrippleErrorListener.fireError(
                        ScrippleErrorListener.Message.INDEX_OUT_OF_BOUNDS,
                        this.index.getPosition(), String.valueOf(index),
                        String.valueOf(c.size()), String.valueOf(true));
            }
        } else
            c.add(element);

        return FuncControlFlow.cont();
    }
}
