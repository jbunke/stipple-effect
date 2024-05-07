package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HelperFuncNode;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Arrays;

public final class FuncTypeNode extends TypeNode {
    private final TypeNode[] paramTypes;
    private final TypeNode returnType;

    public FuncTypeNode(
            final TypeNode[] paramTypes,
            final TypeNode returnType
    ) {
        this(TextPosition.N_A, paramTypes, returnType);
    }

    public FuncTypeNode(
            final TextPosition position,
            final TypeNode[] paramTypes,
            final TypeNode returnType
    ) {
        super(position);

        this.paramTypes = paramTypes;
        this.returnType = returnType;
    }

    public TypeNode[] getParamTypes() {
        return paramTypes;
    }

    public TypeNode getReturnType() {
        return returnType;
    }

    @Override
    public boolean hasSize() {
        return false;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof HelperFuncNode;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof FuncTypeNode f))
            return false;

        if (paramTypes.length != f.paramTypes.length)
            return false;

        for (int i = 0; i < paramTypes.length; i++)
            if (!paramTypes[i].equals(f.paramTypes[i]))
                return false;

        return returnType.equals(f.returnType);
    }

    @Override
    public int hashCode() {
        return paramTypes.length;
    }

    @Override
    public String toString() {
        final String params = switch (paramTypes.length) {
            case 0 -> "";
            case 1 -> paramTypes[0].toString();
            default -> Arrays.stream(paramTypes).map(TypeNode::toString)
                    .reduce((a, b) -> a + ", " + b).orElse("");
        };

        return "(" + params + " -> " + returnType + ")";
    }
}
