package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public abstract class ASTNode {
    private final TextPosition position;

    public ASTNode(final TextPosition position) {
        this.position = position;
    }

    public abstract void semanticErrorCheck(final SymbolTable symbolTable);

    public abstract String toString();

    public final TextPosition getPosition() {
        return position;
    }
}
