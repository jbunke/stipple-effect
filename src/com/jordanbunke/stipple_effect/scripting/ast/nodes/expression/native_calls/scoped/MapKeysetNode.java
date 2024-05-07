package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.scoped;

import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptMap;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.MapTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Set;

public final class MapKeysetNode extends ScopedNativeCallNode {
    public MapKeysetNode(
            final TextPosition position,
            final ExpressionNode owner
    ) {
        super(position, owner, Set.of(new MapTypeNode(
                TypeNode.wildcard(), TypeNode.wildcard())));
    }

    @Override
    public Set<Object> evaluate(final SymbolTable symbolTable) {
        return ((ScriptMap) getScope().evaluate(symbolTable)).keySet();
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        final MapTypeNode mtn = (MapTypeNode) getScope().getType(symbolTable);

        return new CollectionTypeNode(CollectionTypeNode.Type.SET,
                mtn.getKeyType());
    }

    @Override
    String callName() {
        return "keys()";
    }
}
