package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.TextPosition;

import java.util.Map;

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

    public ScrippleTypeNode getKeyType() {
        return keyType;
    }

    public ScrippleTypeNode getValueType() {
        return valueType;
    }

    @Override
    public Class<?> valueClass() {
        return Map.class;
    }

    @Override
    public Object[] createArray(final int length) {
        return new Map[length];
    }

    @Override
    public String toString() {
        return "{" + keyType + ":" + valueType + "}";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof MapTypeNode that))
            return false;

        return this.keyType.equals(that.keyType) &&
                this.valueType.equals(that.valueType);
    }

    @Override
    public int hashCode() {
        return (31 * this.keyType.hashCode()) + this.valueType.hashCode();
    }
}
