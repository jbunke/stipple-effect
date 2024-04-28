package com.jordanbunke.stipple_effect.scripting.ast.symbol_table;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;

public final class Variable {
    private final boolean mutable;
    private final ScrippleTypeNode type;
    private Object value;

    public Variable(
            final boolean mutable,
            final ScrippleTypeNode type,
            final Object value
    ) {
        this.mutable = mutable;
        this.type = type;
        this.value = value;
    }

    public Variable(
            final boolean mutable,
            final ScrippleTypeNode type
    ) {
        this(mutable, type, null);
    }

    public boolean isMutable() {
        return mutable;
    }

    public ScrippleTypeNode getType() {
        return type;
    }

    public Object get() {
        return value;
    }

    public void set(final Object value) {
        this.value = value;
    }
}
