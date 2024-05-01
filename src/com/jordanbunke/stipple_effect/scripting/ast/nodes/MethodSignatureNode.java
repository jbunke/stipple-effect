package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class MethodSignatureNode extends ASTNode {
    private final TypeNode returnType;
    private final ParametersNode parameters;

    public MethodSignatureNode(
            final TextPosition position, final ParametersNode parameters,
            final TypeNode returnType
    ) {
        super(position);

        this.returnType = returnType;
        this.parameters = parameters;
    }

    public MethodSignatureNode(
            final TextPosition position, final ParametersNode parameters
    ) {
        this(position, parameters, null);
    }

    public void execute(
            final SymbolTable symbolTable,
            final Object... args
    ) {
        parameters.populateArgs(symbolTable, args);
    }

    public boolean paramsMatch(final TypeNode[] spec) {
        final TypeNode[] actual = parameters.getTypes();

        if (actual.length != spec.length)
            return false;

        for (int i = 0; i < actual.length; i++)
            if (!actual[i].equals(spec[i]))
                return false;

        return true;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        parameters.semanticErrorCheck(symbolTable);

        if (returnType != null)
            returnType.semanticErrorCheck(symbolTable);
    }

    @Override
    public String toString() {
        return "(" + parameters + " -> " + returnType + ")";
    }

    public TypeNode getReturnType() {
        return returnType;
    }
}
