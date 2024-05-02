package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.collection_init;

import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptArray;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptCollection;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptList;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptSet;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

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
        Arrays.stream(initElements).forEach(
                e -> e.semanticErrorCheck(symbolTable));

        final TypeNode firstElemType = initElements[0].getType(symbolTable);

        for (int i = 1; i < initElements.length; i++) {
            final TypeNode type = initElements[i].getType(symbolTable);

            if (!type.equals(firstElemType))
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.INCONSISTENT_COL_TYPES,
                        initElements[i].getPosition(),
                        String.valueOf(i),
                        String.valueOf(firstElemType),
                        String.valueOf(type));
        }
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
        final TypeNode notRawType = Arrays.stream(initElements)
                .map(e -> e.getType(symbolTable))
                .reduce((a, b) -> !a.equals(
                                new SimpleTypeNode(SimpleTypeNode.Type.RAW))
                                ? a : b).orElse(
                                        initElements[0].getType(symbolTable));

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

        return "of " + opener + contents + closer;
    }
}
