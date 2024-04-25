package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class MethodSignatureNode extends ScrippleASTNode {
    private final ScrippleTypeNode returnType;
    private final ParametersNode parameters;

    public MethodSignatureNode(
            final TextPosition position, final ParametersNode parameters,
            final ScrippleTypeNode returnType
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

    @Override
    public void semanticErrorCheck(SymbolTable symbolTable) {
        // TODO
    }
}
