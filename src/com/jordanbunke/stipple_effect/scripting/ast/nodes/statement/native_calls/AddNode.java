package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptCollection;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptList;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Optional;

public final class AddNode extends StatementNode {
    private final ExpressionNode collection, toAdd, index;

    public AddNode(
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
        final CollectionTypeNode.Type typeOfCol =
                (colType instanceof CollectionTypeNode ct)
                        ? ct.getType() : null;

        if (elemType == null || typeOfCol == null)
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.EXPECTED_FOR_CALL,
                    collection.getPosition(), "add()",
                    "list - <>\" or \"set - {}", colType.toString());
        else if (!elemType.equals(addType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ELEMENT_DOES_NOT_MATCH_COL,
                    toAdd.getPosition(),
                    elemType.toString(), addType.toString());
        else if (typeOfCol == CollectionTypeNode.Type.ARRAY)
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ADD_TO_ARRAY,
                    collection.getPosition());
        if (!iType.equals(new SimpleTypeNode(SimpleTypeNode.Type.INT)))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.INDEX_NOT_INT,
                    index.getPosition(), iType.toString());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final ScriptCollection c =
                (ScriptCollection) collection.evaluate(symbolTable);
        final Object element = toAdd.evaluate(symbolTable);
        final Optional<Integer> i = index != null
                ? Optional.of((Integer) index.evaluate(symbolTable))
                : Optional.empty();

        if (i.isPresent() && c instanceof ScriptList l) {
            final int index = i.get();

            try {
                l.add(index, element);
            } catch (IllegalArgumentException e) {
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.INDEX_OUT_OF_BOUNDS,
                        this.index.getPosition(), String.valueOf(index),
                        String.valueOf(c.size()), String.valueOf(true));
            }
        } else
            c.add(element);

        return FuncControlFlow.cont();
    }

    @Override
    public String toString() {
        return collection + ".add(" + toAdd +
                (index != null ? ", " + index : "") + ");";
    }
}
