package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ScrippleASTNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public abstract class ScrippleTypeNode extends ScrippleASTNode {
    public ScrippleTypeNode(final TextPosition position) {
        super(position);
    }

    @Override
    public final void semanticErrorCheck(final SymbolTable symbolTable) {}

    public abstract Object[] createArray(final int length);

    public abstract Class<?> valueClass();
}
