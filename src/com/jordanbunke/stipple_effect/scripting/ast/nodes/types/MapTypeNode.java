package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Map;

public final class MapTypeNode extends TypeNode {
    private final TypeNode keyType;
    private final TypeNode valueType;

    public MapTypeNode(
            final TypeNode keyType, final TypeNode valueType
    ) {
        super(TextPosition.N_A);

        this.keyType = keyType;
        this.valueType = valueType;
    }

    public TypeNode getKeyType() {
        return keyType;
    }

    public TypeNode getValueType() {
        return valueType;
    }

    @Override
    public Class<?> valueClass() {
        return Map.class;
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

    @Override
    public boolean hasSize() {
        return true;
    }
}
