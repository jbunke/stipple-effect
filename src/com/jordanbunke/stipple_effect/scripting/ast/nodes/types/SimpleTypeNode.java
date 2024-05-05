package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class SimpleTypeNode extends TypeNode {
    private final Type type;

    public enum Type {
        BOOL, INT, FLOAT, CHAR, STRING, COLOR, IMAGE, WILDCARD;

        @Override
        public String toString() {
            if (this == WILDCARD)
                return "_";

            return name().toLowerCase();
        }

        private boolean isNum() {
            return switch (this) {
                case INT, FLOAT -> true;
                default -> false;
            };
        }
    }

    SimpleTypeNode(final Type type) {
        super(TextPosition.N_A);

        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof SimpleTypeNode that && (this.type == that.type ||
                this.type == Type.WILDCARD || that.type == Type.WILDCARD);
    }

    @Override
    public int hashCode() {
        return type.ordinal();
    }

    @Override
    public boolean isNum() {
        return type.isNum();
    }

    @Override
    public boolean hasSize() {
        return type == Type.STRING;
    }

    public boolean isWildcard() {
        return type == Type.WILDCARD;
    }
}
