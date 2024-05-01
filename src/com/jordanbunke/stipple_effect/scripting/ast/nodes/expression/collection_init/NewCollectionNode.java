package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.collection_init;

import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptCollection;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptList;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptSet;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class NewCollectionNode extends ExpressionNode {
    private final CollectionTypeNode.Type collectionType;

    public NewCollectionNode(
            final TextPosition position,
            final CollectionTypeNode.Type collectionType
    ) {
        super(position);

        this.collectionType = collectionType;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {}

    @Override
    public ScriptCollection evaluate(final SymbolTable symbolTable) {
        if (collectionType == CollectionTypeNode.Type.SET)
            return new ScriptSet();

        return new ScriptList();
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return new CollectionTypeNode(collectionType,
                new SimpleTypeNode(SimpleTypeNode.Type.RAW));
    }

    @Override
    public String toString() {
        return "new" +
                (collectionType == CollectionTypeNode.Type.SET ? "{}" : "<>");
    }
}
