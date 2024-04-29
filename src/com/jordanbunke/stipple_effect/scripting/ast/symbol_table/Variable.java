package com.jordanbunke.stipple_effect.scripting.ast.symbol_table;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;

public final class Variable {
    private final boolean mutable;
    private final TypeNode type;
    private Object value;

    public Variable(
            final boolean mutable,
            final TypeNode type,
            final Object value
    ) {
        this.mutable = mutable;
        this.type = type;
        this.value = value;
    }

    public Variable(
            final boolean mutable,
            final TypeNode type
    ) {
        this(mutable, type, null);
    }

    public boolean isMutable() {
        return mutable;
    }

    public TypeNode getType() {
        return type;
    }

    public Object get() {
        return value;
    }

    public void set(final Object value) {
        this.value = value;
    }
}
