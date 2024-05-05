package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.collection_init;

import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptMap;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.MapTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class NewMapNode extends ExpressionNode {
    private final TypeNode keyType, valType;

    public NewMapNode(
            final TextPosition position,
            final TypeNode keyType,
            final TypeNode valType
    ) {
        super(position);

        this.keyType = keyType;
        this.valType = valType;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {}

    public ScriptMap evaluate(final SymbolTable symbolTable) {
        return new ScriptMap();
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return new MapTypeNode(keyType, valType);
    }

    @Override
    public String toString() {
        return "new { " + keyType + " : " + valType + " }";
    }
}
