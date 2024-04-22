package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class ParametersNode extends ScrippleASTNode {
    private final DeclarationNode[] declarations;

    public ParametersNode(
            final TextPosition position, final DeclarationNode[] declarations
    ) {
        super(position);

        this.declarations = declarations;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }
}
