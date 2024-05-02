package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptMap;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.MapTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

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
                        new SimpleTypeNode(SimpleTypeNode.Type.RAW))));

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
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.MAP_KEY_TYPE_MISMATCH,
                        element.getPosition(), elemType.toString());
        }
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Object owner = getOwner().evaluate(symbolTable);
        final Object elemValue = element.evaluate(symbolTable);

        if (owner instanceof ScriptMap map) {
            if (map.containsKey(elemValue))
                return map.get(elemValue);
            else
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.MAP_DOES_NOT_CONTAIN_ELEMENT,
                        getPosition(), elemValue.toString());
        }

        return null;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return ((MapTypeNode) getOwner().getType(symbolTable)).getValueType();
    }

    @Override
    String callName() {
        return "lookup()";
    }

    @Override
    public String toString() {
        final String base = super.toString();

        return base.substring(0, base.length() - 2) + "(" + element + ")";
    }
}
