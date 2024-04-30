package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.collection_init;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptMap;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.MapTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class NewMapNode extends ExpressionNode {
    public NewMapNode(
            final TextPosition position
    ) {
        super(position);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {}

    public ScriptMap evaluate(final SymbolTable symbolTable) {
        return new ScriptMap();
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return new MapTypeNode(
                new SimpleTypeNode(SimpleTypeNode.Type.RAW),
                new SimpleTypeNode(SimpleTypeNode.Type.RAW));
    }
}
