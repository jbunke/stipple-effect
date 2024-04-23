package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class MapTypeNode extends ScrippleTypeNode {
    private final ScrippleTypeNode keyType;
    private final ScrippleTypeNode valueType;

    public MapTypeNode(
            final ScrippleTypeNode keyType, final ScrippleTypeNode valueType
    ) {
        super(TextPosition.N_A);

        this.keyType = keyType;
        this.valueType = valueType;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }
}
