package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.collection_init;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.ArrayList;
import java.util.HashSet;

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
    public Object evaluate(final SymbolTable symbolTable) {
        if (collectionType == CollectionTypeNode.Type.SET)
            return new HashSet<>();

        return new ArrayList<>();
    }

    @Override
    public ScrippleTypeNode getType(final SymbolTable symbolTable) {
        return new CollectionTypeNode(collectionType,
                new SimpleTypeNode(SimpleTypeNode.Type.RAW));
    }
}
