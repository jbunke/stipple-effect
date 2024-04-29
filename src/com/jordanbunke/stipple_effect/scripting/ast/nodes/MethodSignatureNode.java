package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
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

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        parameters.semanticErrorCheck(symbolTable);

        if (returnType != null)
            returnType.semanticErrorCheck(symbolTable);
    }

    public TypeNode getReturnType() {
        return returnType;
    }
}
