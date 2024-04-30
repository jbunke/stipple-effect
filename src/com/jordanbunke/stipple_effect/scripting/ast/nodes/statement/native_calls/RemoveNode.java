package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptCollection;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class RemoveNode extends StatementNode {
    private final ExpressionNode collection, index;

    public RemoveNode(
            final TextPosition position,
            final ExpressionNode collection,
            final ExpressionNode index
    ) {
        super(position);

        this.collection = collection;
        this.index = index;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        collection.semanticErrorCheck(symbolTable);
        index.semanticErrorCheck(symbolTable);

        final TypeNode
                colType = collection.getType(symbolTable),
                elemType = (colType instanceof CollectionTypeNode ct)
                        ? ct.getElementType() : null,
                iType = index.getType(symbolTable);
        final CollectionTypeNode.Type typeOfCol =
                (colType instanceof CollectionTypeNode ct)
                        ? ct.getType() : null;

        if (elemType == null || typeOfCol == null)
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.OWNER_NOT_COLLECTION,
                    collection.getPosition(), colType.toString());
        else if (typeOfCol != CollectionTypeNode.Type.LIST)
            ScrippleErrorListener.fireError(
                    here, // TODO - cannot remove element from anything but list
                    collection.getPosition());
        if (!iType.equals(new SimpleTypeNode(SimpleTypeNode.Type.INT)))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.INDEX_NOT_INT,
                    index.getPosition(), iType.toString());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final ScriptCollection c =
                (ScriptCollection) collection.evaluate(symbolTable);
        final Integer i = (Integer) index.evaluate(symbolTable);

        try {
            c.remove(i);
        } catch (IllegalArgumentException e) {
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.INDEX_OUT_OF_BOUNDS,
                    this.index.getPosition(), String.valueOf(i),
                    String.valueOf(c.size()), String.valueOf(false));
        }

        return FuncControlFlow.cont();
    }
}
