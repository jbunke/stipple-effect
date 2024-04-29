package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.MapTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public final class ContainsNode extends NativeFuncWithOwnerNode {
    private final ExpressionNode element;

    public ContainsNode(
            final TextPosition position,
            final ExpressionNode owner,
            final ExpressionNode element
    ) {
        super(position, owner, Set.of(new MapTypeNode(
                new SimpleTypeNode(SimpleTypeNode.Type.RAW),
                                new SimpleTypeNode(SimpleTypeNode.Type.RAW)),
                        new CollectionTypeNode(CollectionTypeNode.Type.LIST,
                                new SimpleTypeNode(SimpleTypeNode.Type.RAW)),
                        new CollectionTypeNode(CollectionTypeNode.Type.SET,
                                new SimpleTypeNode(SimpleTypeNode.Type.RAW))),
                ScrippleErrorListener.Message.EXPECTED_SEARCHABLE_FOR_CALL);

        this.element = element;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        element.semanticErrorCheck(symbolTable);

        super.semanticErrorCheck(symbolTable);
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        final Object owner = getOwner().evaluate(symbolTable);
        final Object elemValue = element.evaluate(symbolTable);

        if (owner instanceof Map<?,?> map)
            return map.containsKey(elemValue);
        else if (owner instanceof Collection<?> c)
            return c.contains(elemValue);

        return false;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return new SimpleTypeNode(SimpleTypeNode.Type.BOOL);
    }
}
