package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

/**
 * This class represents a generalization of the DeltaScript {@code int} and {@code float} types
 * */
public final class NumTypeNode extends TypeNode {
    private static final NumTypeNode INSTANCE;

    private NumTypeNode() {
        super(TextPosition.N_A);
    }

    static {
        INSTANCE = new NumTypeNode();
    }

    public static NumTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean hasSize() {
        return false;
    }

    @Override
    public boolean complies(final Object o) {
        return false;
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof TypeNode &&
                TypeNode.getInt().equals(o) ||
                TypeNode.getFloat().equals(o);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
