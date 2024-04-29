package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.TextPosition;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

public final class CollectionTypeNode extends TypeNode {
    private final Type type;
    private final TypeNode elementType;

    public enum Type {
        ARRAY, SET, LIST;

        @Override
        public String toString() {
            return switch (this) {
                case SET -> "{}";
                case LIST -> "<>";
                case ARRAY -> "[]";
            };
        }
    }

    public CollectionTypeNode(
            final Type type, final TypeNode elementType
    ) {
        super(TextPosition.N_A);

        this.type = type;
        this.elementType = elementType;
    }

    public TypeNode getElementType() {
        return elementType;
    }

    @Override
    public Class<?> valueClass() {
        return switch (type) {
            case LIST -> List.class;
            case SET -> Set.class;
            case ARRAY -> Array.newInstance(
                    elementType.valueClass(), 1).getClass();
        };
    }

    @Override
    public Object[] createArray(final int length) {
        return switch (type) {
            case SET -> new Set[length];
            case LIST -> new List[length];
            case ARRAY -> (Object[])
                    Array.newInstance(elementType.valueClass(), length);
        };
    }

    @Override
    public String toString() {
        return elementType + type.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof CollectionTypeNode that))
            return false;

        return this.type == that.type &&
                this.elementType.equals(that.elementType);
    }

    @Override
    public int hashCode() {
        return (type.ordinal() * 31) + elementType.hashCode();
    }
}
