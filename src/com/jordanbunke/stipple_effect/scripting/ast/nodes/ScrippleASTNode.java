package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public abstract class ScrippleASTNode {
    private final TextPosition position;

    public ScrippleASTNode(final TextPosition position) {
        this.position = position;
    }

    public abstract void semanticErrorCheck(final SymbolTable symbolTable);

    public final TextPosition getPosition() {
        return position;
    }
}
