package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.awt.*;

public final class SimpleTypeNode extends TypeNode {
    private final Type type;

    public enum Type {
        BOOL, INT, FLOAT, CHAR, STRING, COLOR, IMAGE,
        /**
         * RAW is a special case for empty collection initializations
         * */
        RAW;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public SimpleTypeNode(final Type type) {
        super(TextPosition.N_A);

        this.type = type;
    }

    @Override
    public Class<?> valueClass() {
        return switch (type) {
            case BOOL -> Boolean.class;
            case INT -> Integer.class;
            case FLOAT -> Float.class;
            case CHAR -> Character.class;
            case STRING -> String.class;
            case COLOR -> Color.class;
            case IMAGE -> Image.class;
            case RAW -> Object.class;
        };
    }

    @Override
    public String toString() {
        return type.toString();
    }

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
