package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class CollectionTypeNode extends ScrippleTypeNode  {
    private final Type type;
    private final ScrippleTypeNode elementType;

    public enum Type {
        ARRAY, SET, LIST
    }

    public CollectionTypeNode(
            final Type type, final ScrippleTypeNode elementType
    ) {
        super(TextPosition.N_A);

        this.type = type;
        this.elementType = elementType;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }
}
