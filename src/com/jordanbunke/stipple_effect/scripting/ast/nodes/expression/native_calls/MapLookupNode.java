package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.MapTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Map;
import java.util.Set;

public final class MapLookupNode extends NativeFuncWithOwnerNode {
    private final ExpressionNode element;

    public MapLookupNode(
            TextPosition position,
            ExpressionNode owner,
            final ExpressionNode element
    ) {
        super(position, owner,
                Set.of(new MapTypeNode(
                        new SimpleTypeNode(SimpleTypeNode.Type.RAW),
                        new SimpleTypeNode(SimpleTypeNode.Type.RAW))),
                ScrippleErrorListener.Message.EXPECTED_MAP_FOR_CALL);

        this.element = element;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        element.semanticErrorCheck(symbolTable);

        super.semanticErrorCheck(symbolTable);

        final TypeNode
                ownerType = getOwner().getType(symbolTable),
                elemType = element.getType(symbolTable);

        if (ownerType instanceof MapTypeNode mapType) {
            final TypeNode keyType = mapType.getKeyType();

            if (!keyType.equals(elemType))
                ScrippleErrorListener.fireError(
                        here,
                        element.getPosition(), elemType.toString());
        }
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Object owner = getOwner().evaluate(symbolTable);
        final Object elemValue = element.evaluate(symbolTable);

        if (owner instanceof Map<?,?> map) {
            if (map.containsKey(elemValue))
                return map.get(elemValue);
            else
                ScrippleErrorListener.fireError(
                        ScrippleErrorListener.Message.MAP_DOES_NOT_CONTAIN_ELEMENT,
                        getPosition(), elemValue.toString());
        }

        return null;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return ((MapTypeNode) getOwner().getType(symbolTable)).getValueType();
    }
}
