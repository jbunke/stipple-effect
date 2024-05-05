package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class CollectionTypeNode extends TypeNode {
    private final Type type;
    private final TypeNode elementType;

    public enum Type {
        ARRAY, SET, LIST;

        public String description() {
            return name().toLowerCase() + " - " + this;
        }

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

    public Type getType() {
        return type;
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

    @Override
    public boolean hasSize() {
        return true;
    }
}
