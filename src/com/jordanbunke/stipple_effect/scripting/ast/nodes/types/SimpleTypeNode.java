package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class SimpleTypeNode extends ScrippleTypeNode {
    private final Type type;

    public enum Type {
        BOOL, INT, FLOAT, CHAR, STRING, COLOR, IMAGE,
        /**
         * RAW is a special case for empty collection initializations
         * */
        RAW
    }

    public SimpleTypeNode(final Type type) {
        super(TextPosition.N_A);

        this.type = type;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {}

    @Override
    public boolean equals(final Object o) {
        return o instanceof SimpleTypeNode that && (this.type == that.type ||
                this.type == Type.RAW || that.type == Type.RAW);
    }

    @Override
    public int hashCode() {
        return type.ordinal();
    }
}
