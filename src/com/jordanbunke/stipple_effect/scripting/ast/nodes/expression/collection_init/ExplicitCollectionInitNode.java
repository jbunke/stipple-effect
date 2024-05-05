package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.collection_init;

import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptArray;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptCollection;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptList;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptSet;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.CollectionHelper;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Arrays;
import java.util.stream.Stream;

public final class ExplicitCollectionInitNode extends ExpressionNode {
    private final CollectionTypeNode.Type collectionType;
    private final ExpressionNode[] initElements;

    public ExplicitCollectionInitNode(
            final TextPosition position,
            final CollectionTypeNode.Type collectionType,
            final ExpressionNode[] initElements
    ) {
        super(position);

        this.collectionType = collectionType;
        this.initElements = initElements;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        for (ExpressionNode initElement : initElements)
            initElement.semanticErrorCheck(symbolTable);

        CollectionHelper.checkInteralTypeConsistency(
                initElements, symbolTable, collectionType.description());
    }

    @Override
    public ScriptCollection evaluate(final SymbolTable symbolTable) {
        final Stream<Object> elements =
                Arrays.stream(initElements).map(e -> e.evaluate(symbolTable));

        return switch (collectionType) {
            case ARRAY -> new ScriptArray(elements);
            case LIST -> new ScriptList(elements);
            case SET -> new ScriptSet(elements);
        };
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        final TypeNode notRawType =
                CollectionHelper.getConcreteType(initElements, symbolTable);

        return new CollectionTypeNode(collectionType, notRawType);
    }

    @Override
    public String toString() {
        final String opener = switch (collectionType) {
            case LIST -> "<";
            case SET -> "{";
            case ARRAY -> "[";
        }, closer = switch (collectionType) {
            case LIST -> ">";
            case SET -> "}";
            case ARRAY -> "]";
        };

        final String contents = initElements.length == 1
                ? initElements[0].toString()
                : Arrays.stream(initElements)
                .map(ExpressionNode::toString)
                .reduce((a, b) -> a + ", " + b).orElse("");

        return opener + contents + closer;
    }
}
